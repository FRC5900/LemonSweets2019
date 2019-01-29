/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * This is a demo program showing the use of the RobotDrive class, specifically
 * it contains the code necessary to operate a robot with tank drive.
 */
public class Robot extends TimedRobot {
  private DifferentialDrive myRobot;
  private Joystick leftStick;
  private Joystick rightStick;
  private SpeedController Ball_thing; 
  private double IntakeSpeed;
  private double DriveSpeed;
  private double TurnSpeed;


  int MoveRobot_State;
  int MoveRobot_Counter;
  int MoveRobot_TimeToMove;
  int TurnRobot_TimeToMove;
  int TurnRobot_Counter;
  int TurnRobot_State;
  double MoveRobot_Speed;
  double TurnRobot_Speed;
  Boolean Forward;
  Boolean CW;
  
  
  @Override
  public void robotInit() {
    myRobot = new DifferentialDrive(new Talon(0), new Talon(1));
    leftStick = new Joystick(0);
    rightStick = new Joystick(1);
    Ball_thing = new Talon (2);
  }

  @Override
  public void teleopPeriodic() 
  {
    DriveSpeed = rightStick.getY();
    TurnSpeed = rightStick.getX();

    myRobot.arcadeDrive(-DriveSpeed, TurnSpeed);

    IntakeSpeed = leftStick.getY();
    Ball_thing.set( IntakeSpeed);

    if (rightStick.getRawButton(3) == true) {
      if ( MoveRobot_State == 0 ) {
        MoveRobot_State = 1;
        Forward = true;
        MoveRobot_TimeToMove = 50;
        MoveRobot_Counter = 0;
        MoveRobot_Speed = 0.5;
        SmartDashboard.putString("what" , "MoveRobot FWD");
      }  
    }

    if (rightStick.getRawButton(2) == true) {
      if ( MoveRobot_State == 0 ) {
        MoveRobot_State = 1;
        Forward = false;
        MoveRobot_TimeToMove = 50;
        MoveRobot_Counter = 0;
        MoveRobot_Speed = 0.5;
        SmartDashboard.putString("what" , "MoveRobot REV");
      }  
    }

    Move_Robot();

    if (rightStick.getRawButton(4) == true) {
      if ( TurnRobot_State == 0 ) {
        TurnRobot_State = 1;
        CW = false;
        TurnRobot_TimeToMove = 25;
        TurnRobot_Counter = 0;
        TurnRobot_Speed = 0.7;
        SmartDashboard.putString("what" , "TurnRobot CCW");
      }  
    }

    if (rightStick.getRawButton(5) == true) {
      if ( TurnRobot_State == 0 ) {
        TurnRobot_State = 1;
        CW = true;
        TurnRobot_TimeToMove = 25;
        TurnRobot_Counter = 0;
        TurnRobot_Speed = 0.7;
        SmartDashboard.putString("what" , "TurnRobot CW");
      }  
    }
    Turn_Robot();

  }

  public void Move_Robot(){
      switch (MoveRobot_State) {
        case 1:
          if (MoveRobot_Counter > MoveRobot_TimeToMove) {
            myRobot.tankDrive(0.0, 0.0);
            MoveRobot_Speed = 0.0;
            MoveRobot_State = 0;
            SmartDashboard.putString("what" , "Turn Complete");
          }
          else {
            if(Forward == true)
              myRobot.tankDrive(MoveRobot_Speed, MoveRobot_Speed);
            else
              myRobot.tankDrive(-MoveRobot_Speed, -MoveRobot_Speed);
            MoveRobot_Counter++;
          }
      }
  }

  public void Turn_Robot() {
    switch (TurnRobot_State) {
      case 1:
        if (TurnRobot_Counter > TurnRobot_TimeToMove) {
          myRobot.tankDrive(0.0, 0.0);
          TurnRobot_Speed = 0.0;
          TurnRobot_State = 0;
          SmartDashboard.putString("what" , "Turn Complete");
        }
        else {
          if(CW == true)
            myRobot.tankDrive(TurnRobot_Speed, -TurnRobot_Speed);
          else
            myRobot.tankDrive(-TurnRobot_Speed, TurnRobot_Speed);
          TurnRobot_Counter++;
        }
    } 
  }

}

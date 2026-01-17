// Copyright (c) FIRST and other WPILib contributors.

// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.backstage;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.RobotContainer;
import frc.robot.subsystems.PoseEstimatorSubsystem;

public class Robot extends TimedRobot {
    private Command autonomousCommand;
    private final RobotContainer robotContainer;
    private boolean first = false;

    public Robot() {
        robotContainer = new RobotContainer();
        robotContainer.swerveSubsystem.zeroHeading();
        robotContainer.swerveSubsystem.zeroHeading();
    }

    @Override
    public void robotInit() {
        robotContainer.swerveSubsystem.zeroHeading();
        robotContainer.swerveSubsystem.zeroHeading();
    }

    @Override
    public void robotPeriodic() {
        if (!first) {
            robotContainer.swerveSubsystem.zeroHeading();
            robotContainer.swerveSubsystem.zeroHeading();
            first = true;
        }
        SmartDashboard.putNumber("matchTime", DriverStation.getMatchTime());
        SmartDashboard.putNumber("voltage", RobotController.getBatteryVoltage());
        SmartDashboard.putString("robot_coords", robotContainer.getPose());
        CommandScheduler.getInstance().run();
    }


    @Override
    public void disabledInit() {
    }


    @Override
    public void disabledPeriodic() {
    }


    @Override
    public void autonomousInit() {
        robotContainer.swerveSubsystem.zeroHeading();
        robotContainer.swerveSubsystem.zeroHeading();
        PoseEstimatorSubsystem.updateVisionOdometry();
        autonomousCommand = robotContainer.getAutonomousCommand();

        if (autonomousCommand != null) {
            CommandScheduler.getInstance().schedule(autonomousCommand);
        }
    }


    @Override
    public void autonomousPeriodic() {
    }


    @Override
    public void teleopInit() {
        if (autonomousCommand != null) {
            autonomousCommand.cancel();
        }
    }

    @Override
    public void teleopPeriodic() {
    }


    @Override
    public void testInit() {
        CommandScheduler.getInstance().cancelAll();
    }


    @Override
    public void testPeriodic() {
    }


    @Override
    public void simulationInit() {
    }


    @Override
    public void simulationPeriodic() {
    }
}

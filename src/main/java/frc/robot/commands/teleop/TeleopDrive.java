// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.teleop;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandPS5Controller;
import frc.robot.RobotContainer;
import frc.robot.constants.SwerveConstants;
import frc.robot.subsystems.SwerveSubsystem;
import frc.robot.utils.swerve.SwerveDriveHelper;

public class TeleopDrive extends Command {
    private final SwerveSubsystem swerve;
    private final CommandPS5Controller joystick;
    private final Translation2d centerOfRotation;

    public TeleopDrive(SwerveSubsystem swerve, CommandPS5Controller joystick) {
        this.swerve = swerve;
        this.joystick = joystick;
        centerOfRotation = new Translation2d();
        addRequirements(swerve);
    }

    @Override
    public void execute() {

        ChassisSpeeds chassisSpeeds = new ChassisSpeeds(
                MathUtil.applyDeadband(-joystick.getLeftY(), 0.1) * (RobotContainer.isFieldRelative() ? 1 : -1) * (joystick.getHID().getL3Button() ? 0.5 : 1),
                MathUtil.applyDeadband(-joystick.getLeftX(), 0.1) * (RobotContainer.isFieldRelative() ? 1 : -1) * (joystick.getHID().getR3Button() ? 0.5 : 1),
                MathUtil.applyDeadband(-joystick.getRightX(), 0.01));
        chassisSpeeds = SwerveDriveHelper.updateChassisSpeeds(chassisSpeeds, () -> false, SwerveDriveHelper.DriveMode.NewDriver);
        chassisSpeeds = SwerveDriveHelper.joystickToRobotUnits(chassisSpeeds, SwerveConstants.maxSpeed, SwerveConstants.maxAngularVelocity);

        for (int i = 0; i < 4; i++) {
            String module = "";
            switch (i) {
                case 0 -> module = "FL";
                case 1 -> module = "FR";
                case 2 -> module = "BL";
                case 3 -> module = "BR";
            }
            SmartDashboard.putNumber(module, swerve.getSwerveModules()[i].getAbsAngleWithOffset().getDegrees());
        }
        swerve.drive(chassisSpeeds, true, RobotContainer.isFieldRelative(), centerOfRotation);
    }
}
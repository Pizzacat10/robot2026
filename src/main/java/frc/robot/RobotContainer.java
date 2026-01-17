package frc.robot;

import com.pathplanner.lib.auto.AutoBuilder;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.button.CommandPS5Controller;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.commands.ActionCommands;
import frc.robot.commands.auto.LimeLightFollowingCommand;
import frc.robot.commands.teleop.TeleopDrive;
import frc.robot.constants.OperatorConstants;
import frc.robot.subsystems.LimeLightFollowingSubsystems;
import frc.robot.subsystems.PoseEstimatorSubsystem;
import frc.robot.subsystems.SwerveAutoBuilder;
import frc.robot.subsystems.SwerveSubsystem;
import frc.robot.utils.LimelightHelpers;

import java.util.Map;

public class RobotContainer {

    /* Settings */
    private final Map<String, Boolean> robotSystems = Map.of(
            "swerve", true,
            "shooter", true,
            "climb", true
    );

    /* Controllers */
    private final CommandPS5Controller driverController =
            new CommandPS5Controller(OperatorConstants.DRIVER_CONTROLLER_PORT);

    /* Subsystems */
    public final SwerveSubsystem swerveSubsystem = new SwerveSubsystem();
    private final PoseEstimatorSubsystem estimatorSubsystem = new PoseEstimatorSubsystem(swerveSubsystem);
    private final LimeLightFollowingSubsystems limeLightFollowingSubsystems = new LimeLightFollowingSubsystems();

    /* Helpers */
    private final SwerveAutoBuilder autoBuilder = new SwerveAutoBuilder(swerveSubsystem);
    private final ActionCommands actions = new ActionCommands();
    private final SendableChooser<Command> autoChooser;
    private final LimelightHelpers limelightHelpers = new LimelightHelpers();

    /* Variables */
    private static boolean fieldRelative = true;
    private final Field2d field2d = new Field2d();


    public RobotContainer() {
        SmartDashboard.putData("commands", CommandScheduler.getInstance());
        autoBuilder.addCommand("Example", null);

        autoChooser = AutoBuilder.buildAutoChooser("DefaultAuto");
        autoChooser.addOption("Example Auto", null);
        SmartDashboard.putData("Auto Mode", autoChooser);
        SmartDashboard.putData("AutoPath", field2d);

        configureBindings();
    }


    private void configureBindings() {
        if (robotSystems.get("swerve")) {
            swerveSubsystem.setDefaultCommand(new TeleopDrive(
                    swerveSubsystem, driverController
            ));
        }

        limeLightFollowingSubsystems.setDefaultCommand(new LimeLightFollowingCommand(
                LimelightHelpers.getTX("",0),driverController, limeLightFollowingSubsystems
        ));
    }


    public Command getAutonomousCommand() {
        return autoChooser.getSelected();
    }

    /* Helper Methods */

    public String getPose() {
        Pose2d pose = swerveSubsystem.getPose();
        return String.format("(%f, %f, %f)", pose.getX(), pose.getY(), pose.getRotation().getDegrees());
    }

    public static boolean isFieldRelative() {
        return fieldRelative;
    }

    public static double getSpeed(double x, double t, double error, double maxSpeed, double minSpeed, double decRate, boolean flip) {
        int multiplier = x < t ? 1 : -1;
        if (flip) multiplier *= -1;
        if (Math.abs(x - t) < error) multiplier = 0;

        double totalSpeed = minSpeed + (maxSpeed - minSpeed) * (Math.abs(x - t) / (decRate + Math.abs(x - t)));
        return totalSpeed * multiplier;
    }

    private Trigger getPOVUp() {
        return new Trigger(() -> driverController.getHID().getPOV() == 0);
    }

    private Trigger getPOVRight() {
        return new Trigger(() -> driverController.getHID().getPOV() == 90);
    }

    private Trigger getPOVDown() {
        return new Trigger(() -> driverController.getHID().getPOV() == 180);
    }

    private Trigger getPOVLeft() {
        return new Trigger(() -> driverController.getHID().getPOV() == 270);
    }
}

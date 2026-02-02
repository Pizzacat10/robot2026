package frc.robot.commands.auto;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.RobotContainer;
import frc.robot.commands.teleop.TurretCommand;
import frc.robot.constants.CanConstants;
import frc.robot.subsystems.SuckingSubsystem;
import frc.robot.subsystems.SwerveSubsystem;
import frc.robot.subsystems.TurretSubystem;

import java.sql.Driver;
import java.util.Map;

public class TurretCommandAuto extends Command {

    private Map<String,Translation2d> hubs = Map.of(
            "b", CanConstants.HubPosBlue,
            "r", CanConstants.HubPosRed
    );

    private final TurretSubystem subsystem;
    private final SwerveSubsystem swerveSubsystem;
    private Double target;
    private double speed = -1;
    Translation2d hubPos = hubs.get(RobotContainer.getAlince());

    public TurretCommandAuto(TurretSubystem subsystem,SwerveSubsystem swerveSubsystem) {
        this.subsystem = subsystem;
        this.target = 0.0;
        this.swerveSubsystem = swerveSubsystem;
    }

    @Override
    public void initialize() {
            subsystem.reset();
    }

    @Override
    public void execute() {

        if(DriverStation.getGameSpecificMessage().charAt(0) == RobotContainer.getAlince()) {
            subsystem.shootingSpeed(1);
            subsystem.moveHood(0);
        }

        if (swerveSubsystem.getPose().getY() < hubPos.getY() && RobotContainer.getAlince() == 'b' || swerveSubsystem.getPose().getY() > hubPos.getY() && RobotContainer.getAlince() == 'r') {
            getTargetAngle(swerveSubsystem.getPose());
            speed = getSpeed(subsystem.getTurretPose(), target);
            subsystem.turn(speed);
        }
        else {
            speed = getSpeed(subsystem.getTurretPose(), 0);
            subsystem.turn(speed);
        }

    }

    @Override
    public void end(boolean interrupted) {
            subsystem.reset();
    }

    @Override
    public boolean isFinished() {
            return false;
    }

    public void getTargetAngle(Pose2d robotPos) {
        double fare = Math.abs(hubPos.getY() > robotPos.getY() ? hubPos.getY() - robotPos.getY() : robotPos.getY() - hubPos.getY());
        double close = Math.abs(hubPos.getX() > robotPos.getX() ? hubPos.getX() - robotPos.getX() : robotPos.getX() - hubPos.getX());
        double angle = Math.atan(fare / close);

        double angleDegrees = Math.toDegrees(angle);

        target = angleDegrees + robotPos.getRotation().getDegrees() > 360 ? (angleDegrees + robotPos.getRotation().getDegrees()) * CanConstants.OneTurnToDegrees - 360 : (angleDegrees + robotPos.getRotation().getDegrees()) * CanConstants.OneTurnToDegrees;
    }

    public double getSpeed(double x, double t) {
        return RobotContainer.getSpeed(x, t, 0.5, 0.3, 0, 10, false);
    }
}

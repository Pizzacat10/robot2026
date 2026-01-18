package frc.robot.commands.auto;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.SwerveSubsystem;

public class MoveCommand extends Command {

    private SwerveSubsystem swerveSubsystem;
    private Translation2d centerOfRotation = new Translation2d();
    private Timer timer;
    private final double time;

    public MoveCommand(SwerveSubsystem swerveSubsystem, double time) {
        this.swerveSubsystem = swerveSubsystem;
        this.timer = new Timer();
        this.time = time;

        addRequirements(swerveSubsystem);
    }

    @Override
    public void initialize() {
        timer.restart();
    }

    @Override
    public void execute() {

        swerveSubsystem.drive(new ChassisSpeeds(-1, 0, 0), true, false, centerOfRotation);
    }

    @Override
    public void end(boolean interrupted) {
        swerveSubsystem.drive(new ChassisSpeeds(), true, true, centerOfRotation);
    }

    @Override
    public boolean isFinished() {
        return timer.get() > time;
    }
}

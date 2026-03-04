package frc.robot.commands.debug;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.ClimbSubsystem;

public class ClimbDebugCommand extends Command {

    private final ClimbSubsystem climbSubsystem;

    public ClimbDebugCommand(ClimbSubsystem climbSubsystem) {
        this.climbSubsystem = climbSubsystem;

        addRequirements(climbSubsystem);
    }

    @Override
    public void initialize() {
        climbSubsystem.reset();
    }

    @Override
    public void execute() {
        climbSubsystem.debug();
    }

    @Override
    public void end(boolean interrupted) {
        climbSubsystem.reset();
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}

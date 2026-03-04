package frc.robot.commands.debug;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.SuckingSubsystem;

public class SuckingDebugCommand extends Command {

    private final SuckingSubsystem suckingSubsystem;

    public SuckingDebugCommand(SuckingSubsystem suckingSubsystem) {
        this.suckingSubsystem = suckingSubsystem;

        addRequirements(suckingSubsystem);
    }

    @Override
    public void initialize() {
        suckingSubsystem.reset();
    }

    @Override
    public void execute() {
        suckingSubsystem.debug();
    }

    @Override
    public void end(boolean interrupted) {
        suckingSubsystem.reset();
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}

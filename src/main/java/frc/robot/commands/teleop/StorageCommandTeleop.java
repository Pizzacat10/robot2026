package frc.robot.commands.teleop;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.StorageSubsystem;

import java.util.function.Supplier;

public class StorageCommandTeleop extends Command {

    private Supplier<Boolean> speed;
    private Supplier<Boolean> back;
    private StorageSubsystem subsystem;

    public StorageCommandTeleop(Supplier<Boolean> speed ,Supplier<Boolean> back, StorageSubsystem subsystem) {
        this.speed = speed;
        this.back = back;
        this.subsystem = subsystem;

        addRequirements(subsystem);
    }

    @Override
    public void initialize() {
        subsystem.MoveToTurret(0);
    }


    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        subsystem.MoveToTurret(speed.get() ? -1 : back.get() ? 1 : 0);
    }


    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        subsystem.MoveToTurret(0);
    }


    // Returns true when the command should end.
    @Override
    public boolean isFinished()
    {
        return false;
    }

}

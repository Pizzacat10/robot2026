package frc.robot.commands.teleop;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.StorageSubsystem;

import java.util.function.Supplier;

public class StorageCommand extends Command {

    private Supplier<Double> speed;
    private StorageSubsystem subsystem;

    public StorageCommand(Supplier<Double> speed,StorageSubsystem subsystem) {
        this.speed = speed;
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
        subsystem.MoveToTurret(speed.get());
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

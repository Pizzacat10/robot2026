package frc.robot.commands.teleop;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.ElevatorSubsystem;

import java.util.function.Supplier;

public class ElevatorCommand extends Command {
    
    private Supplier<Double> speed;
    private ElevatorSubsystem elevatorSubsystem;
    
    public ElevatorCommand(Supplier<Double> speed , ElevatorSubsystem elevatorSubsystem ) {
        this.speed = speed;
        this.elevatorSubsystem = elevatorSubsystem;

        addRequirements(elevatorSubsystem);
    }

    @Override
    public void initialize() {
        elevatorSubsystem.Move(0);
    }

    @Override
    public void execute() {
        elevatorSubsystem.Move(speed.get() > 0.5 ? 1 : speed.get() < -0.5 ? -1 : 0 );
    }


    @Override
    public void end(boolean interrupted) {
        elevatorSubsystem.Move(0);
    }


    @Override
    public boolean isFinished()
    {
        return false;
    }
}

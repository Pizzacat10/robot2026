package frc.robot.commands.teleop;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.ClimbSubsystem;

import java.util.function.Supplier;

public class ClimbCommandTeleop extends Command {
    
    private Supplier<Double> moveUp;
    private Supplier<Boolean> moveDown;
    private ClimbSubsystem climbSubsystem;
    
    public ClimbCommandTeleop(Supplier<Double> moveUp, Supplier<Boolean> moveDown , ClimbSubsystem climbSubsystem) {
        this.moveDown = moveDown;
        this.moveUp = moveUp;
        this.climbSubsystem = climbSubsystem;

        addRequirements(climbSubsystem);
    }

    @Override
    public void initialize() {
        climbSubsystem.move(0);
    }

    @Override
    public void execute() {
        climbSubsystem.move(moveUp.get() > 0.1 ? moveUp.get() : moveUp.get() < -0.1 ? moveUp.get() : 0);
        SmartDashboard.putNumber("hanger pos", climbSubsystem.getClimbPose());
    }

    @Override
    public void end(boolean interrupted) {
        climbSubsystem.move(0);
    }

    @Override
    public boolean isFinished()
    {
        return false;
    }
}

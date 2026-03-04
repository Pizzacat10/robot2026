package frc.robot.commands.teleop;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.ClimbSubsystem;

import java.util.function.Supplier;

public class ClimbCommandTeleop extends Command {
    
    private Supplier<Boolean> moveUp;
    private Supplier<Boolean> moveDown;
    private ClimbSubsystem climbSubsystem;
    
    public ClimbCommandTeleop(Supplier<Boolean> moveUp, Supplier<Boolean> moveDown , ClimbSubsystem climbSubsystem) {
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
        climbSubsystem.move(moveDown.get() ? -0.3 : moveUp.get() ? 0.3 : 0);
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

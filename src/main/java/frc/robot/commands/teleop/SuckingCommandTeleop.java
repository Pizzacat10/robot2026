package frc.robot.commands.teleop;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.constants.CanConstants;
import frc.robot.subsystems.SuckingSubsystem;

import java.util.function.Supplier;

public class SuckingCommandTeleop extends Command {
    private Supplier<Boolean> moveUp;
    private Supplier<Boolean> moveDown;

    private Supplier<Boolean> sucking;
    private SuckingSubsystem suckingSubsystem;

    private double closePose;
    private double openPose;

    public SuckingCommandTeleop(SuckingSubsystem suckingSubsystem, Supplier<Boolean> moveUp, Supplier<Boolean> moveDown, Supplier<Boolean> sucking) {
        this.moveUp = moveUp;
        this.moveDown = moveDown;

        this.sucking = sucking;

        this.suckingSubsystem = suckingSubsystem;

        addRequirements(suckingSubsystem);
    }

    @Override
    public void initialize() {
        suckingSubsystem.reset();
    }


    @Override
    public void execute() {
        SmartDashboard.putNumber("Arm pose", suckingSubsystem.getArmPose());
        suckingSubsystem.moveArm(moveUp.get() ? 0.3 : moveDown.get() ? -0.3 : 0);
        suckingSubsystem.suck(sucking.get() ? -1 : 0);
    }


    @Override
    public void end(boolean interrupted) {
        suckingSubsystem.reset();
    }


    @Override
    public boolean isFinished()
    {
        return false;
    }

}

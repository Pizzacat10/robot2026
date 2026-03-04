package frc.robot.commands.teleop;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.constants.CanConstants;
import frc.robot.subsystems.SuckingSubsystem;

import java.util.function.Supplier;

public class SuckingCommandTeleop extends Command {
    private Boolean moveUp;
    private Boolean moveDown;

    private Supplier<Boolean> sucking;
    private SuckingSubsystem suckingSubsystem;

    private double closePose;
    private double openPose;

    public SuckingCommandTeleop(SuckingSubsystem suckingSubsystem, Boolean moveUp, Boolean moveDown) {
        this.moveUp = moveUp;
        this.moveDown = moveDown;

        this.suckingSubsystem = suckingSubsystem;

        this.closePose = CanConstants.closeSate;
        this.openPose = CanConstants.openState;

        addRequirements(suckingSubsystem);
    }

    @Override
    public void initialize() {
        suckingSubsystem.reset();
    }


    @Override
    public void execute() {
        SmartDashboard.putNumber("Arm pose", suckingSubsystem.getArmPose());
        suckingSubsystem.moveArm(moveUp ? 0.1 : moveDown ? -0.1 : 0);
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

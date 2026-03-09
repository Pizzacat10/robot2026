package frc.robot.commands.teleop;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.commands.debug.TurretDebugCommand;
import frc.robot.subsystems.TurretSubystem;

import java.util.function.Supplier;

public class TurretCommandTeleop extends Command {
    private Supplier<Boolean> hoodUp;
    private Supplier<Boolean> hoodDown;

    private Supplier<Double> shootingSpeed;

    private Supplier<Boolean> spinLeft;
    private Supplier<Boolean> spinRight;

    private TurretSubystem subystem;

    public TurretCommandTeleop(TurretSubystem subystem, Supplier<Boolean> hoodUp, Supplier<Boolean> hoodDown, Supplier<Boolean> spinLeft, Supplier<Boolean> spinRight, Supplier<Double> shootingSpeed) {
        this.subystem = subystem;
        this.shootingSpeed = shootingSpeed;

        this.hoodUp = hoodUp;
        this.hoodDown = hoodDown;

        this.spinLeft = spinLeft;
        this.spinRight = spinRight;

        addRequirements(subystem);
    }

    @Override
    public void initialize() {
        subystem.reset();
    }

    @Override
    public void execute() {
        subystem.moveHood(hoodDown.get() ? -0.15 : hoodUp.get() ? 0.05 : 0);

        subystem.turn(spinLeft.get() ? 0.2 : spinRight.get() ? -0.2 : 0);

        subystem.shootingspeed(SmartDashboard.getNumber("shootSpeed", 0));

        SmartDashboard.putNumber("pose ", subystem.getHoodPose());
        SmartDashboard.putNumber("angle ", subystem.getHoodAngle());
        SmartDashboard.putNumber("speed ", (shootingSpeed.get() + 1) / 2);
    }

    @Override
    public void end(boolean interrupted) {
        subystem.reset();
    }

    @Override
    public boolean isFinished()
    {
        return false;
    }
}

package frc.robot.commands.teleop;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.TurretSubystem;

import java.util.function.Supplier;

public class TurretCommand extends Command {
    private Supplier<Double> hoodSpeeed;
    private Supplier<Double> shootingSpeed;
    private TurretSubystem subystem;

    public TurretCommand(TurretSubystem subystem, Supplier<Double> hoodSpeeed, Supplier<Double> shootingSpeed) {
        this.subystem = subystem;
        this.shootingSpeed = shootingSpeed;
        this.hoodSpeeed = hoodSpeeed;

        addRequirements(subystem);
    }

    @Override
    public void initialize() {
        subystem.reset();
    }

    @Override
    public void execute() {
        subystem.moveHood(hoodSpeeed.get());
        subystem.shootingSpeed(shootingSpeed.get());
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

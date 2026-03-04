package frc.robot.commands.debug;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.TurretSubystem;

public class TurretDebugCommand extends Command {

    private final TurretSubystem turretSubsystem;

    public TurretDebugCommand(TurretSubystem turretSubsystem) {
        this.turretSubsystem = turretSubsystem;

        addRequirements(turretSubsystem);
    }

    @Override
    public void initialize() {
        turretSubsystem.reset();
    }

    @Override
    public void execute() {
        turretSubsystem.debug();
    }

    @Override
    public void end(boolean interrupted) {
        turretSubsystem.reset();
    }

    @Override
    public boolean isFinished() {
        return false;
    }

}

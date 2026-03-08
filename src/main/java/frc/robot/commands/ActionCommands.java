package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import frc.robot.RobotContainer;
import frc.robot.commands.auto.ClimbCommandAuto;
import frc.robot.commands.auto.SuckingCommandAuto;
import frc.robot.commands.auto.TurretReachPointCommand;
import frc.robot.commands.conditions.IfCommand;
import frc.robot.commands.conditions.SwapCommand;
import frc.robot.subsystems.ClimbSubsystem;
import frc.robot.subsystems.SuckingSubsystem;
import frc.robot.subsystems.SwerveSubsystem;
import frc.robot.subsystems.TurretSubystem;

public class ActionCommands {

    private final SuckingSubsystem suckingSubsystem;
    private final ClimbSubsystem climbSubsystem;
    private final TurretSubystem turretSubystem;

    public ActionCommands(SuckingSubsystem suckingSubsystem, ClimbSubsystem climbSubsystem, TurretSubystem turretSubystem) {
        this.suckingSubsystem = suckingSubsystem;
        this.climbSubsystem = climbSubsystem;
        this.turretSubystem = turretSubystem;
    }

    public ParallelCommandGroup compact() {
        return new ParallelCommandGroup(
                new SwapCommand(
                        () -> RobotContainer.robotSystems.get("sucking"), new IfCommand(() -> true),
                        new SuckingCommandAuto(suckingSubsystem, "close")),
                new SwapCommand(
                        () -> RobotContainer.robotSystems.get("climb"), new IfCommand(() -> true),
                        new ClimbCommandAuto(climbSubsystem, "close")),
                new SwapCommand(
                        () -> RobotContainer.robotSystems.get("turret"), new IfCommand(() -> true),
                        new TurretReachPointCommand(turretSubystem, 0, 0))
        );
    }

}

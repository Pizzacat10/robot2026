package frc.robot.commands.auto;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.RobotContainer;
import frc.robot.subsystems.SuckingSubsystem;

import java.util.Map;
import java.util.Objects;

public class SuckingCommandAuto extends Command {
    private static String stage = "close";

    private final Map<String, Double> stages = Map.of(
      "open", 15.694,
      "close",0.0
    );

    private final SuckingSubsystem subsystem;
    private Double target;
    private double speed = -1;
    private final boolean forceStage;

    public  SuckingCommandAuto(SuckingSubsystem subsystem) {
        this.subsystem = subsystem;
        forceStage = false;
    }

    public SuckingCommandAuto(SuckingSubsystem subsystem, String stage) {
        this.subsystem = subsystem;
        this.forceStage = true;
        SuckingCommandAuto.stage = stage;
    }

    @Override
    public void initialize() {
        if (!forceStage) stage = getStage();
        this.target = stages.getOrDefault(stage, null);
        if (target == null) end(true);
        subsystem.reset();

        subsystem.suck(stage.equals("open") ? -1 : 0);
    }

    @Override
    public void execute() {
        speed = getSpeed(subsystem.getArmPose(), target);
        subsystem.moveArm(speed);
    }

    @Override
    public void end(boolean interrupted) {
        subsystem.autoReset();
    }

    @Override
    public boolean isFinished() {
        return speed == 0;
    }

    public double getSpeed(double x, double t) {
        return RobotContainer.getSpeed(x, t, 0.5, 0.5, 0, 10, false);
    }

    private String getStage() {
        return stage.equals("close") ? "open" : "close";
    }
}

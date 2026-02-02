package frc.robot.commands.auto;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.RobotContainer;
import frc.robot.subsystems.SuckingSubsystem;

import java.util.Map;

public class SuckingCommandAuto extends Command {
    private final Map<String, Double> stages = Map.of(
      "open", 13.0,
      "close",0.0
    );

    private final SuckingSubsystem subsystem;
    private Double target;
    private double speed = -1;


    public SuckingCommandAuto(SuckingSubsystem subsystem,String stage) {
        this.subsystem = subsystem;
        this.target = stages.getOrDefault(stage, null);
        if (target == null) end(true);
    }

    @Override
    public void initialize() {
        subsystem.reset();
    }

    @Override
    public void execute() {
        speed = getSpeed(subsystem.getArmPose(), target);
        subsystem.moveArm(speed);
    }

    @Override
    public void end(boolean interrupted) {
        subsystem.reset();
    }

    @Override
    public boolean isFinished() {
        return speed == 0;
    }

    public double getSpeed(double x, double t) {
        return RobotContainer.getSpeed(x, t, 0.5, 1, 0, 10, false);
    }
}

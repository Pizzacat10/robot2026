package frc.robot.commands.auto;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.RobotContainer;
import frc.robot.subsystems.ClimbSubsystem;

import java.util.Map;

public class ClimbCommandAuto extends Command {
    private static String stage = "close";
    private Map<String,Double> stages = Map.of(
            "close", 2.0,
            "open", 41.3
    );
    private ClimbSubsystem subsystem;
    private Double target;
    private double speed = -1;

    public ClimbCommandAuto(ClimbSubsystem subsystem) {
        this.subsystem = subsystem;

    }

    @Override
    public void initialize() {
        subsystem.reset();
        toggleStage();
        this.target = stages.getOrDefault(stage, null);
        if (target == null) end(true);
    }

    @Override
    public void execute() {
        speed = getSpeed(subsystem.getClimbPose(), target);
        subsystem.move(speed);
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
        return RobotContainer.getSpeed(x, t, 1, 0.5, 0, 1, false);
    }
    private void toggleStage() {
        stage = stage.equals("close") ? "open" : "close";
    }
}

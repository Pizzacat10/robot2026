package frc.robot.commands.auto;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.RobotContainer;
import frc.robot.subsystems.TurretSubystem;

import java.util.function.Supplier;

public class TurretReachPointCommand extends Command {
    private final TurretSubystem subsystem;
    private final double turn;
    private final double angle;

    private double angleSpeed = -1;
    private double turningSpeed = -1;

    public TurretReachPointCommand(TurretSubystem subsystem, double turn, double angle) {
        this.subsystem = subsystem;
        this.turn = turn;
        this.angle = angle;

        addRequirements(subsystem);
    }

    @Override
    public void initialize() {
        subsystem.reset();
    }

    @Override
    public void execute() {
        /* Angle */
        double pose = subsystem.angleToPose(angle);

        angleSpeed = getSpeed(subsystem.getHoodPose(),pose,0.2, 1,0.2,10);
        if (angleSpeed > 0) angleSpeed /= 9;
        subsystem.moveHood(angleSpeed);

        /* Turning */

        turningSpeed = getSpeed(subsystem.getTurretPose(), turn,0, 0.5,1,20);
        subsystem.turn(turningSpeed);
    }

    @Override
    public void end(boolean interrupted) {
        subsystem.reset();
    }

    @Override
    public boolean isFinished() {
        return turningSpeed == 0 && angleSpeed == 0;
    }

    public double getSpeed(double x, double t, double minSpeed, double maxSpeed,double error,double decRate) {
        return RobotContainer.getSpeed(x, t, error, maxSpeed, minSpeed, decRate, false);
    }
}

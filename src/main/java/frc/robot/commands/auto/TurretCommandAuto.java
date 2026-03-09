package frc.robot.commands.auto;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.RobotContainer;
import frc.robot.subsystems.TurretSubystem;

import java.util.function.Supplier;

public class TurretCommandAuto extends Command {

    private final TurretSubystem subsystem;
    private final Supplier<Double> rotationVelocity;
    private final Supplier<Double> up;
    private final Supplier<Double> down;

    public TurretCommandAuto(TurretSubystem subsystem, Supplier<Double> rotationVelocity, Supplier<Double> up, Supplier<Double> down) {
        this.subsystem = subsystem;
        this.rotationVelocity = rotationVelocity;
        this.up = up;
        this.down = down;

        addRequirements(subsystem);
    }

    @Override
    public void initialize() {
            subsystem.reset();
    }

    @Override
    public void execute() {
        /* Angle */
        double angle = SmartDashboard.getNumber("shootAngle",0);
        double pose = subsystem.angleToPose(angle);

//        double angleSpeed = getSpeed(subsystem.getHoodPose(),pose,0.2, 1,0.2,5);
        double angleSpeed = - ((up.get() + 1) / 2) * 0.5;
        double downSpeed = (down.get() + 1) / 2;
        if (downSpeed != 0) angleSpeed = downSpeed * 0.5;
        subsystem.moveHood(angleSpeed);

        /* Turning */

        double rotVelocity = rotationVelocity.get();
        double txCurrent = subsystem.getLimelight().getTX();
        double velocityTurn = (rotVelocity / 25);
        double txTarget = subsystem.getTx(subsystem.getLimelight().getTA());
        if (txTarget == 0) velocityTurn = 0;
        txTarget += velocityTurn;

        double turningSpeed = getSpeed(txCurrent, txTarget,0, 0.5,1,20);
        subsystem.turn(turningSpeed);

        /* Shooting */

        subsystem.shootingspeed(SmartDashboard.getNumber("shootSpeed", 0));
        /* Debug */

        SmartDashboard.putNumber("Ta", subsystem.getLimelight().getTA());
        SmartDashboard.putNumber("Turret angle", angle);
        SmartDashboard.putNumber("Turret pose", pose);
        SmartDashboard.putNumber("real Turret pose", subsystem.getHoodPose());
    }

    @Override
    public void end(boolean interrupted) {
        subsystem.reset();
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    public double getSpeed(double x, double t, double minSpeed, double maxSpeed,double error,double decRate) {
        return RobotContainer.getSpeed(x, t, error, maxSpeed, minSpeed, decRate, false);
    }
}

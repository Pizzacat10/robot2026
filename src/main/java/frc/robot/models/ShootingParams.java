package frc.robot.models;

public class ShootingParams {
    private final double rpm;
    private final double angle;

    public ShootingParams(double rpm, double angle) {
        this.rpm = rpm;
        this.angle = angle;
    }

    public double getRpm() {
        return rpm;
    }

    public double getAngle() {
        return angle;
    }
}

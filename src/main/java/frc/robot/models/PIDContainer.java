package frc.robot.models;

public class PIDContainer {
    public double kS;
    public double kV;
    public double kA;
    public double kG;

    public double kP;
    public double kI;
    public double kD;

    public String headingType;

    public PIDContainer(double kp, double ki, double kd, String headingType) {
        this.kS = 0.0;
        this.kV = 0.0;
        this.kA = 0.0;
        this.kG = 0.0;
        this.kP = kp;
        this.kI = ki;
        this.kD = kd;
        this.headingType = headingType;
    }
}
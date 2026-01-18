package frc.robot.subsystems;

import com.ctre.phoenix6.hardware.TalonFX;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.constants.CanConstants;

import static frc.robot.constants.SwerveConstants.maxSpeed;

public class LimeLightFollowingSubsystems extends SubsystemBase {

    private TalonFX turningMotor;
    private Encoder canCoder;

    private double maxAngle;
    private double minAngle;

    private double maxRange;
    private double minRange;

    public LimeLightFollowingSubsystems() {
        this.turningMotor = new TalonFX(0); // change to the id

        this.canCoder = new Encoder(0,0); // change to the id
        this.canCoder.reset();

        this.maxRange = 3;
        this.minRange = -3;

        this.maxAngle = CanConstants.MaxAngle;
        this.minAngle = CanConstants.MinAngle;
    }

    public void Turn(double speed) {
        turningMotor.set(speed);
    }

    // return the speed that the motor will be
    public double InRangeOfTag(double tx) {
        if (maxRange < tx) {
            return 0.3;
        } else if (minRange > tx) {
            return 0.3;
        }
        return 0;
    }

    public void reset() {
        if (minAngle < canCoder.get()) {
            Turn(getSpeed(canCoder.get(),0,10));
        }
    }

    public double getSpeed(double x, double t, double error) {
        double minSpeed = 0;
        double decRate = 2000;
        int multiplier = x < t ? 1 : -1;
        if (Math.abs(x - t) < error) multiplier = 0;

        double totalSpeed = minSpeed + (1 - minSpeed) * (Math.abs(x - t) / (decRate + Math.abs(x - t)));
        return totalSpeed * multiplier;
    }
}

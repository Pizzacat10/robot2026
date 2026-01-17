package frc.robot.subsystems;

import com.ctre.phoenix6.hardware.TalonFX;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import java.sql.DataTruncation;

public class LimeLightFollowingSubsystems extends SubsystemBase {

    private TalonFX motor;
    private Encoder canCoder;

    private double maxTurn; // use for when the coder give a positive number
    private double minTurn; // use for when the coder give a negative number

    private double maxRange;
    private double minRange;

    public LimeLightFollowingSubsystems() {
        this.motor = new TalonFX(0); // change to the id

        this.canCoder = new Encoder(0,0); // change to the id
        this.canCoder.reset();

        this.maxRange = 3;
        this.minRange = -3;
        this.maxTurn = 360;
        this.minTurn = 360;
    }

    public void InRotation() {
        if (canCoder.get() > maxTurn){
            motor.set(-0.3);
            while (true) {
                if (canCoder.get() >= 10) break;
            }
            motor.set(0);
        } else if(canCoder.get() < minTurn) {
            motor.set(-0.3);
            while (true) {
                if (canCoder.get() >= 10) break;
            }
            motor.set(0);
        }else motor.set(0);

    }

    public void Turn(double speed) {
        InRotation();
        motor.set(speed);
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
}

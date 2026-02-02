package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.MotControllerJNI;
import com.ctre.phoenix6.hardware.TalonFX;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.constants.CanConstants;

public class SuckingSubsystem extends SubsystemBase {
    private TalonFX motorArm;
    private TalonFX sucking;


    public SuckingSubsystem() {
        this.motorArm = new TalonFX(CanConstants.SuckingArmMotor,CanConstants.roborioBus);
        this.sucking = new TalonFX(CanConstants.suckingMotor,CanConstants.roborioBus);
    }

    public void suck(double value) {
        sucking.set(value);
    }

    public void moveArm(double speed) {
        motorArm.set(speed);
    }

    public double getArmPose() {return motorArm.getPosition().getValueAsDouble();}

    public void reset() {
        moveArm(0);
        suck(0);
    }

}

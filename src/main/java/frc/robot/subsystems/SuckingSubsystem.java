package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.MotControllerJNI;
import com.ctre.phoenix6.hardware.TalonFX;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.constants.CanConstants;
import frc.robot.models.motorHelper.MotorDefaultPID;
import frc.robot.models.motorHelper.MotorLib;

public class SuckingSubsystem extends SubsystemBase {
    private MotorLib motorArm;
    private MotorLib sucking;

    private MotorDefaultPID.Krakenx44 krakenx44 = new MotorDefaultPID.Krakenx44();
    private MotorDefaultPID.Krakenx60 krakenx60 = new MotorDefaultPID.Krakenx60();

    public SuckingSubsystem() {
        this.motorArm = new MotorLib(CanConstants.SuckingArmMotor,krakenx60,true,false);
        this.sucking = new MotorLib(CanConstants.suckingMotor,krakenx60,true,false);
    }

    public void suck(double speed) {
        sucking.set(sucking.speedToRPM(speed));
    }

    public void moveArm(double speed) {
        motorArm.set(motorArm.speedToRPM(speed));
    }

    public void debug() {
        SmartDashboard.putNumber("Sucking Arm Angle", getArmPose());
    }

    public double getArmPose() {return motorArm.getPosition().getValueAsDouble();}

    public void reset() {
        moveArm(0);
        suck(0);
    }

    public void autoReset() {
        moveArm(0);
    }

}

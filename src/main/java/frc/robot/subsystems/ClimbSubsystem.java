package frc.robot.subsystems;

import com.ctre.phoenix6.hardware.TalonFX;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.constants.CanConstants;
import frc.robot.models.motorHelper.MotorDefaultPID;
import frc.robot.models.motorHelper.MotorLib;

public class ClimbSubsystem extends SubsystemBase {

    private MotorLib motor;

    private MotorDefaultPID.Krakenx44 krakenx44 = new MotorDefaultPID.Krakenx44();
    private MotorDefaultPID.Krakenx60 krakenx60 = new MotorDefaultPID.Krakenx60();

    public ClimbSubsystem() {
        this.motor = new MotorLib(CanConstants.ElevatorMotor,krakenx60,false,false);
    }

    public double getClimbPose() { return motor.getPosition().getValueAsDouble();}

    public void reset() {
        move(0);
    }

    public void debug() {
        SmartDashboard.putNumber("Climb Position", getClimbPose());
    }

    public void move(double speed) {
        motor.set(motor.speedToRPM(speed));
    }

}

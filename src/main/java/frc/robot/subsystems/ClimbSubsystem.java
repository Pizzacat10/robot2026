package frc.robot.subsystems;

import com.ctre.phoenix6.hardware.TalonFX;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.constants.CanConstants;

public class ClimbSubsystem extends SubsystemBase {

    private TalonFX motor;

    public ClimbSubsystem() {
        this.motor = new TalonFX(CanConstants.ElevatorMotor);
        this.motor.setPosition(0);
    }

    public double getClimbPose() { return motor.getPosition().getValueAsDouble();}

    public void reset() {
        move(0);
    }

    public void debug() {
        SmartDashboard.putNumber("Climb Positon", getClimbPose());
    }

    public void move(double speed) {
        motor.set(speed);
    }

}

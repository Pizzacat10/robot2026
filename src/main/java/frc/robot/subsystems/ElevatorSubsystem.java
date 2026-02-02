package frc.robot.subsystems;

import com.ctre.phoenix6.hardware.TalonFX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.constants.CanConstants;

public class ElevatorSubsystem extends SubsystemBase {

    private TalonFX motor;

    public ElevatorSubsystem() {
        this.motor = new TalonFX(CanConstants.ElevatorMotor);
    }

    public void Move(double speed) {
        motor.set(speed);
    }

}

package frc.robot.subsystems;

import com.ctre.phoenix6.hardware.TalonFX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.constants.CanConstants;

public class StorageSubsystem extends SubsystemBase {

    private TalonFX upStorageMotor;
    private TalonFX downStorageMotor;

    public StorageSubsystem() {
        this.upStorageMotor = new TalonFX(CanConstants.StorageUp);
        this.downStorageMotor = new TalonFX(CanConstants.StorageDown);
    }

    public void MoveToTurret(double speed) {
        upStorageMotor.set(-speed);
        downStorageMotor.set(speed);
    }
}

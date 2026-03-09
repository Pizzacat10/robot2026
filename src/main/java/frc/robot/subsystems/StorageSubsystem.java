package frc.robot.subsystems;

import com.ctre.phoenix6.controls.VelocityVoltage;
import com.ctre.phoenix6.hardware.TalonFX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.constants.CanConstants;
import frc.robot.models.motorHelper.MotorDefaultPID;
import frc.robot.models.motorHelper.MotorLib;

public class StorageSubsystem extends SubsystemBase {

    private MotorLib upStorageMotor;
    private MotorLib downStorageMotor;

    private MotorDefaultPID.Krakenx60 krakenx60 = new MotorDefaultPID.Krakenx60();

    public StorageSubsystem() {
        this.upStorageMotor = new MotorLib(CanConstants.StorageUp,krakenx60,true,true);
        this.downStorageMotor = new MotorLib(CanConstants.StorageDown,krakenx60,true,false);
    }

    public void MoveToTurret(double speed) {
        upStorageMotor.set(upStorageMotor.speedToRPM(speed));
        downStorageMotor.set(downStorageMotor.speedToRPM(speed));
    }
}

package frc.robot.subsystems;

import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.NeutralModeValue;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.constants.CanConstants;

public class TurretSubystem extends SuckingSubsystem{
    private TalonFX shootingMotorDown;
    private TalonFX shootingMotorUp;
    private TalonFX hoodMoveMotor;
    private TalonFX turretSpin;

    public TurretSubystem() {
        this.shootingMotorDown = new TalonFX(CanConstants.ShootingMotorDown);
        this.hoodMoveMotor = new TalonFX(CanConstants.HoodMotor);
        TalonFXConfiguration configs = new TalonFXConfiguration();
        configs.MotorOutput.NeutralMode = NeutralModeValue.Brake;
        hoodMoveMotor.getConfigurator().apply(configs);
        this.shootingMotorUp = new TalonFX(CanConstants.ShootingMotorUp);
        this.turretSpin = new TalonFX(CanConstants.TurretSpin);
    }

    public double getTurretPose() {return turretSpin.getPosition().getValueAsDouble();}

    public double getHoodPose() {return hoodMoveMotor.getPosition().getValueAsDouble();}

    public void turn(double speed) {
        turretSpin.set(speed);
    }

    public void moveHood(double speed) {
        hoodMoveMotor.set(speed);
    }


    public void shootingSpeed(double speed) {
        shootingMotorDown.set(speed);
        shootingMotorUp.set(-speed);
    }

    public void reset() {
        moveHood(0);
        shootingSpeed(0);
        turn(0);
    }

}

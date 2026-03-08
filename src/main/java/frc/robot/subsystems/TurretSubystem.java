package frc.robot.subsystems;

import com.ctre.phoenix6.controls.VelocityVoltage;
import frc.robot.constants.CanConstants;
import frc.robot.models.Limelight;
import frc.robot.models.ShootingParams;
import frc.robot.models.motorHelper.MotorDefaultPID;
import frc.robot.models.motorHelper.MotorLib;

import java.util.Map;

public class TurretSubystem extends SuckingSubsystem{

    private final Map<Double, Double> targetTx = Map.of(
            0.65, 2.53,
            0.54,2.27,
            0.43, 0.79,
            0.213,0.55,
            0.192,-0.54,
            0.12, -0.79
    );

    private final Map<Double, ShootingParams> shootingParams = Map.of(
            0.5, new ShootingParams(1950, 20)
    );

    private final VelocityVoltage velocityVoltage = new VelocityVoltage(0);
    private MotorLib shootingMotorDown;
    private MotorLib shootingMotorUp;
    private MotorLib hoodMoveMotor;
    private MotorLib turretSpin;
    private final Limelight LL;

    private MotorDefaultPID.Krakenx44 krakenx44 = new MotorDefaultPID.Krakenx44();
    private MotorDefaultPID.Krakenx60 krakenx60 = new MotorDefaultPID.Krakenx60();

    public TurretSubystem() {
        this.shootingMotorDown = new MotorLib(CanConstants.TurretSpin, krakenx44 ,true,false);
        this.hoodMoveMotor = new MotorLib(CanConstants.TurretSpin, krakenx60 ,true,false);
        this.shootingMotorUp = new MotorLib(CanConstants.TurretSpin, krakenx44 ,true,true);
        this.turretSpin = new MotorLib(CanConstants.TurretSpin, krakenx44 ,true,false);
        this.LL = new Limelight("limelight-shooter");
    }

    public Limelight getLimelight() {return LL;}

    public double getTurretPose() {return turretSpin.getPosition().getValueAsDouble();}

    public double getHoodPose() {return hoodMoveMotor.getPosition().getValueAsDouble();}

    public double getHoodAngle() {
        double pose = getHoodPose()*-1;
        return pose * 36;
    }

    public double angleToPose(double angle) {
        return (angle / 36) * -1;
    }

    public void turn(double speed) {
        turretSpin.set(turretSpin.speedToRPM(speed));
    }

    public void moveHood(double speed) {
        hoodMoveMotor.set(hoodMoveMotor.speedToRPM(speed));
    }

    public void shootingSpeed(double rpm) {
        double rps = rpm / 60;

        shootingMotorDown.set(rps);
        shootingMotorUp.set(rps);
    }

    public void reset() {
        moveHood(0);
        shootingSpeed(0);
        turn(0);
    }

    public Double getTx(double ta) {
        for (Map.Entry<Double, Double> entry : targetTx.entrySet()) {
            if (Math.abs(ta - entry.getKey()) < 0.3) return entry.getValue();
        }
        return -999.0;
    }
}


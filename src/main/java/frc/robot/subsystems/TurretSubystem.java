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
            0.109,-0.96,
            0.2,-0.86,
            0.3,0.05,
            0.4,1.49,
            0.5,1.81,
            0.6,1.0,
            0.7,1.4,
            0.8,2.0
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
        this.shootingMotorDown = new MotorLib(CanConstants.ShootingMotorDown, krakenx44 ,true,false);
        this.hoodMoveMotor = new MotorLib(CanConstants.HoodMotor, krakenx60 ,true,true);
        this.shootingMotorUp = new MotorLib(CanConstants.ShootingMotorUp, krakenx44 ,true,true);
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

    public void shootingspeed(double rpm) {
        shootingMotorDown.set(rpm);
        shootingMotorUp.set(rpm);
    }

    public void reset() {
        moveHood(0);
        shootingspeed(0);
        turn(0);
    }

    public Double getTx(double ta) {
        for (Map.Entry<Double, Double> entry : targetTx.entrySet()) {
            if (Math.abs(ta - entry.getKey()) < 0.3) return entry.getValue();
        }
        return -999.0;
    }
}


package frc.robot.subsystems;

import com.ctre.phoenix6.hardware.TalonFX;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.constants.CanConstants;

import java.sql.Driver;

import static frc.robot.constants.SwerveConstants.maxSpeed;

public class LimeLightFollowingSubsystems extends SubsystemBase {

    private TalonFX turningMotor;

    private double maxAngle;
    private double minAngle;

    private Translation2d hubPos;

    public LimeLightFollowingSubsystems() {
        this.turningMotor = new TalonFX(CanConstants.TurningMotor);

        this.maxAngle = CanConstants.MaxAngle;
        this.minAngle = CanConstants.MinAngle;

        this.hubPos = DriverStation.getAlliance().get() == DriverStation.Alliance.Red ? CanConstants.HubPosRed : CanConstants.HubPosBlue;
    }

    public void Turn(double speed) {
        turningMotor.set(speed);
    }

    public void overLimet(double speed) {
        if (minAngle > turningMotor.getPosition().getValueAsDouble() || maxAngle < turningMotor.getPosition().getValueAsDouble())
            Turn(speed);
    }

    public double calcTargetAngle(Pose2d pos) {

        if  (pos.getX() > hubPos.getX()) return 0;

        double distensX = 0;
        double distensY = 0;

        distensX = pos.getX() > hubPos.getX() ? pos.getX() - hubPos.getX() : hubPos.getX() - pos.getX();
        distensY = pos.getY() > hubPos.getY() ? pos.getY() - hubPos.getY() : hubPos.getY() - pos.getY();

        double radians = Math.atan2(distensY,distensX);

        double angle = Math.toDegrees(radians);

        return pos.getY() > hubPos.getY() ? angle : 360 - angle;

    }

    public double getAngle() { return turningMotor.getPosition().getValueAsDouble() * 360; }

    public double getSpeed(double x, double t, double error) {
        double minSpeed = 0;
        double decRate = 2000;
        double multiplier = x < t ? 0.5 : -0.5;
        if (Math.abs(x - t) < error) multiplier = 0;

        double totalSpeed = minSpeed + (1 - minSpeed) * (Math.abs(x - t) / (decRate + Math.abs(x - t)));
        return totalSpeed * multiplier;
    }
}

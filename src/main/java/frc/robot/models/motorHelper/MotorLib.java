package frc.robot.models.motorHelper;

import com.ctre.phoenix6.StatusSignal;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.VelocityVoltage;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.NeutralModeValue;
import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.opencv.core.Mat;

public class MotorLib {

    private TalonFX motor;
    TalonFXConfiguration configuration = new TalonFXConfiguration();
    private VelocityVoltage velocityVoltage = new VelocityVoltage(0).withSlot(0);
    private double maxRPM;
    private boolean safeMode = false;
    /**
     * this is used to control the motor with rpm
     * this let you control the motor better and helps it maintain his velocity
     *
     * @param id the id of the motor
     * @param kP kP for the motor PID
     * @param kV kV for the motor PID
     * @param kS kV for the motor PID
     * @param brake is the motor will be in Brake mode
     * @param inverted is the motor inverted
     */
    public MotorLib(int id, double kP, double kV, double kS, double maxRPM, boolean brake, boolean inverted){
        this.motor = new TalonFX(id);
        this.maxRPM = maxRPM;

        this.configuration.Slot0.kP = kP;
        this.configuration.Slot0.kV = kV;
        this.configuration.Slot0.kS = kS;

        this.configuration.MotorOutput.Inverted = inverted ? InvertedValue.Clockwise_Positive : InvertedValue.CounterClockwise_Positive;
        this.configuration.MotorOutput.NeutralMode = brake ? NeutralModeValue.Brake : NeutralModeValue.Coast;

        this.motor.setPosition(0);
        this.motor.getConfigurator().apply(configuration);

    }

    public MotorLib(int id, MotorDefaultPID.Krakenx44 krakenx44 , boolean brake, boolean inverted){
        this.motor = new TalonFX(id);
        this.maxRPM = krakenx44.maxRPM();

        this.configuration.Slot0.kP = krakenx44.kP();
        this.configuration.Slot0.kV = krakenx44.kV();
        this.configuration.Slot0.kS = krakenx44.kS();

        this.configuration.MotorOutput.Inverted = inverted ? InvertedValue.Clockwise_Positive : InvertedValue.CounterClockwise_Positive;
        this.configuration.MotorOutput.NeutralMode = brake ? NeutralModeValue.Brake : NeutralModeValue.Coast;

        this.motor.setPosition(0);
        this.motor.getConfigurator().apply(configuration);
    }

    public MotorLib(int id, MotorDefaultPID.Krakenx60 krakenx60, boolean brake, boolean inverted){
        this.motor = new TalonFX(id);
        this.maxRPM = krakenx60.maxRPM();

        this.configuration.Slot0.kP = krakenx60.kP();
        this.configuration.Slot0.kV = krakenx60.kV();
        this.configuration.Slot0.kS = krakenx60.kS();

        this.configuration.MotorOutput.Inverted = inverted ? InvertedValue.Clockwise_Positive : InvertedValue.CounterClockwise_Positive;
        this.configuration.MotorOutput.NeutralMode = brake ? NeutralModeValue.Brake : NeutralModeValue.Coast;

        this.motor.setPosition(0);
        this.motor.getConfigurator().apply(configuration);
    }

    /**
     * turning speed to rpm
     * @param speed the power that you want to turn to rpm
     * @return give's you rpm
     */
    public double speedToRPM(double speed) {
        return speed * maxRPM;
    }

    /**
     * set the max speed to 50% of the motor max speed
     * can use the "getMotorData" function to see if your on safe mode or not or the "getSafeMode"
     * @param safeMode boolean that tell the motor if to be in a safe mode or not
     */
    public void setSafeMode(boolean safeMode) {
        this.safeMode = safeMode;
    }

    public boolean getSafeMode() {
        return safeMode;
    }

    /**
     * let you control your motor with rpm and not presents
     * if safe mode is on you can only use up to 50% of your max rpm
     * @param rpm motor rpm from maxRPM to -maxRPM (e.g. kraken x60 maxRPM = 6000 6000 to -6000)
     */
    public void set(double rpm) {
        double rps = rpm / 60;

        if (safeMode) {rps = Math.min(rpm,maxRPM);}

        motor.setControl(velocityVoltage.withVelocity(rps));
    }

    /**
     * let you see data about the motor
     *
     * @param name the name of your motor
     */
    public void getMotorData(String name){
        String MotorName = name + "/";
        SmartDashboard.putBoolean(MotorName + " safe mode", getSafeMode());
        SmartDashboard.putNumber(MotorName + " position", motor.getPosition().getValueAsDouble());
        SmartDashboard.putNumber(MotorName + " velocity", motor.getVelocity().getValueAsDouble());
        SmartDashboard.putNumber(MotorName + " torque", motor.getTorqueCurrent().getValueAsDouble());
        SmartDashboard.putNumber(MotorName + " supply voltage", motor.getSupplyVoltage().getValueAsDouble());
        SmartDashboard.putNumber(MotorName + " voltage", motor.getMotorVoltage().getValueAsDouble());
        SmartDashboard.putNumber(MotorName + " temp", motor.getDeviceTemp().getValueAsDouble());
        SmartDashboard.putBoolean(MotorName + " Voltage", motor.hasResetOccurred());
    }

    /**
     * it stops all power sending to the motor (use more as an end command)
     */
    public void stopMotor() {motor.stopMotor();}

    /**
     * real rpm of the motor
     * @return give the motor real rpm of the motor
     */
    public StatusSignal<AngularVelocity> getMotorSpeed() {return motor.getVelocity();}

    /**
     * the motor positions in turns
     * @return gives the motor positions
     */
    public StatusSignal<Angle> getPosition() {return motor.getPosition();}

    /**
     * let you set the position of the motor
     *
     * @param position the number of the turn that the motor is on
     */
    public void setPosition(int position) {motor.setPosition(position);}

    /**
     * set the position to 0
     */
    public void resetPosition() {setPosition(0);}
}

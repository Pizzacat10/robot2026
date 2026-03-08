package frc.robot.models.motorHelper;

public class MotorDefaultPID {

    public static class Krakenx60 {

        public static double kP() {return 0.11;}
        public static double kV() {return 0.12;}
        public static double kS() {return 0.25;}
        public static double maxRPM() {return 6000;}

    }

    public static class Krakenx44 {

        public static double kP() {return 0.096;}
        public static double kV() {return 0.12;}
        public static double kS() {return 0.25;}
        public static double maxRPM() {return 7500;}

    }

}

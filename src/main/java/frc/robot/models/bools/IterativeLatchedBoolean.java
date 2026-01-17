package frc.robot.models.bools;

public class IterativeLatchedBoolean {
    private boolean mLast = false;

    public boolean update(boolean newValue) {
        boolean ret = false;
        if (newValue && !mLast) {
            ret = true;
        }
        mLast = newValue;
        return ret;
    }
}
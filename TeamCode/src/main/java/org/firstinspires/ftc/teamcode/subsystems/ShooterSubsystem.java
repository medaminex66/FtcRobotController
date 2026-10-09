package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.Range;

public class ShooterSubsystem {

    private final DcMotorEx left, right;
    private double targetRpm = RobotConfig.SHOOTER_TARGET_RPM;
    private boolean spinning = false;

    public ShooterSubsystem(HardwareMap hardwareMap) {
        left = hardwareMap.get(DcMotorEx.class, RobotConfig.SHOOTER_LEFT);
        right = hardwareMap.get(DcMotorEx.class, RobotConfig.SHOOTER_RIGHT);

        left.setDirection(RobotConfig.SHOOTER_LEFT_DIRECTION);
        right.setDirection(RobotConfig.SHOOTER_RIGHT_DIRECTION);

        for (DcMotorEx m : new DcMotorEx[]{left, right}) {
            m.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
            m.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }
    }

    public void spinUp() {
        double ticksPerSecond = targetRpm * RobotConfig.SHOOTER_TICKS_PER_REV / 60.0;
        left.setVelocity(ticksPerSecond);
        right.setVelocity(ticksPerSecond);
        spinning = true;
    }

    public void stop() {
        left.setPower(0);
        right.setPower(0);
        spinning = false;
    }

    public boolean atSpeed() {
        return spinning
                && Math.abs(getLeftRpm() - targetRpm) < RobotConfig.SHOOTER_RPM_TOLERANCE
                && Math.abs(getRightRpm() - targetRpm) < RobotConfig.SHOOTER_RPM_TOLERANCE;
    }

    public void adjustRpm(double delta) {
        targetRpm = Range.clip(targetRpm + delta, 0, RobotConfig.SHOOTER_MAX_RPM);
    }

    public double getTargetRpm() {
        return targetRpm;
    }

    public double getLeftRpm() {
        return left.getVelocity() * 60.0 / RobotConfig.SHOOTER_TICKS_PER_REV;
    }

    public double getRightRpm() {
        return right.getVelocity() * 60.0 / RobotConfig.SHOOTER_TICKS_PER_REV;
    }
}

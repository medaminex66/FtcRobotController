package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class IntakeSubsystem {

    public enum State { IN, REVERSE, STOPPED }

    private final DcMotor motor;
    private State state = State.STOPPED;

    public IntakeSubsystem(HardwareMap hardwareMap) {
        motor = hardwareMap.get(DcMotor.class, RobotConfig.INTAKE);
        motor.setDirection(RobotConfig.INTAKE_DIRECTION);
        motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    public void in() {
        motor.setPower(RobotConfig.INTAKE_POWER);
        state = State.IN;
    }

    public void reverse() {
        motor.setPower(RobotConfig.INTAKE_REVERSE_POWER);
        state = State.REVERSE;
    }

    public void stop() {
        motor.setPower(0);
        state = State.STOPPED;
    }

    public State getState() {
        return state;
    }
}

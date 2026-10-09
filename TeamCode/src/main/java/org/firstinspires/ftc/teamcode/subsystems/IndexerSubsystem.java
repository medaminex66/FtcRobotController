package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class IndexerSubsystem {

    private final DcMotor motor;
    private boolean feeding = false;

    public IndexerSubsystem(HardwareMap hardwareMap) {
        motor = hardwareMap.get(DcMotor.class, RobotConfig.INDEXER);
        motor.setDirection(RobotConfig.INDEXER_DIRECTION);
        motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    public void feed() {
        motor.setPower(RobotConfig.INDEX_POWER);
        feeding = true;
    }

    public void stop() {
        motor.setPower(0);
        feeding = false;
    }

    public boolean isFeeding() {
        return feeding;
    }
}

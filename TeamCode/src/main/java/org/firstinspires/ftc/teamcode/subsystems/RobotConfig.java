package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotorSimple;

public final class RobotConfig {

    private RobotConfig() {}

    public static final String FR_WHEEL = "FRwheel";
    public static final String FL_WHEEL = "FLwheel";
    public static final String BR_WHEEL = "BRwheel";
    public static final String BL_WHEEL = "BLwheel";
    public static final String IMU = "imu";
    public static final String INTAKE = "intake";
    public static final String INDEXER = "Index";
    public static final String SHOOTER_LEFT = "shooter_left";
    public static final String SHOOTER_RIGHT = "shooter_right";

    public static final DcMotorSimple.Direction FR_DIRECTION = DcMotorSimple.Direction.REVERSE;
    public static final DcMotorSimple.Direction FL_DIRECTION = DcMotorSimple.Direction.FORWARD;
    public static final DcMotorSimple.Direction BR_DIRECTION = DcMotorSimple.Direction.REVERSE;
    public static final DcMotorSimple.Direction BL_DIRECTION = DcMotorSimple.Direction.FORWARD;
    public static final DcMotorSimple.Direction INTAKE_DIRECTION = DcMotorSimple.Direction.FORWARD;
    public static final DcMotorSimple.Direction INDEXER_DIRECTION = DcMotorSimple.Direction.FORWARD;
    public static final DcMotorSimple.Direction SHOOTER_LEFT_DIRECTION = DcMotorSimple.Direction.FORWARD;
    public static final DcMotorSimple.Direction SHOOTER_RIGHT_DIRECTION = DcMotorSimple.Direction.REVERSE;

    public static final double DEADZONE = 0.09;
    public static final double TURN_SCALE = 0.7;

    public static final double INTAKE_POWER = 1.0;
    public static final double INTAKE_REVERSE_POWER = -1.0;
    public static final double INDEX_POWER = -0.24;

    public static final double SHOOTER_TICKS_PER_REV = 28.0;
    public static final double SHOOTER_TARGET_RPM = 2350;
    public static final double SHOOTER_RPM_TOLERANCE = 50;
    public static final double SHOOTER_MAX_RPM = 6000;
    public static final double SHOOTER_RPM_STEP = 50;
}

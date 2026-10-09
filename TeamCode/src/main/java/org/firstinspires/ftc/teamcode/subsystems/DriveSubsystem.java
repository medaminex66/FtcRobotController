package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

public class DriveSubsystem {

    private final DcMotor frWheel, flWheel, brWheel, blWheel;
    private final IMU imu;
    private double heading;

    public DriveSubsystem(HardwareMap hardwareMap) {
        frWheel = hardwareMap.get(DcMotor.class, RobotConfig.FR_WHEEL);
        flWheel = hardwareMap.get(DcMotor.class, RobotConfig.FL_WHEEL);
        brWheel = hardwareMap.get(DcMotor.class, RobotConfig.BR_WHEEL);
        blWheel = hardwareMap.get(DcMotor.class, RobotConfig.BL_WHEEL);

        frWheel.setDirection(RobotConfig.FR_DIRECTION);
        flWheel.setDirection(RobotConfig.FL_DIRECTION);
        brWheel.setDirection(RobotConfig.BR_DIRECTION);
        blWheel.setDirection(RobotConfig.BL_DIRECTION);

        for (DcMotor m : new DcMotor[]{frWheel, flWheel, brWheel, blWheel}) {
            m.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            m.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        }

        imu = hardwareMap.get(IMU.class, RobotConfig.IMU);
        imu.initialize(new IMU.Parameters(
                new RevHubOrientationOnRobot(
                        RevHubOrientationOnRobot.LogoFacingDirection.RIGHT,
                        RevHubOrientationOnRobot.UsbFacingDirection.UP
                )
        ));
    }

    public void drive(double stickX, double stickY, double stickTurn) {
        heading = imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS);

        double x = applyDeadzone(Math.pow(stickX, 3));
        double y = applyDeadzone(Math.pow(stickY, 3));
        double rx = applyDeadzone(Math.pow(stickTurn, 3)) * RobotConfig.TURN_SCALE;

        double rotX = x * Math.cos(-heading) - y * Math.sin(-heading);
        double rotY = x * Math.sin(-heading) + y * Math.cos(-heading);

        double denom = Math.max(Math.abs(rotY) + Math.abs(rotX) + Math.abs(rx), 1);
        flWheel.setPower((rotY + rotX + rx) / denom);
        frWheel.setPower((rotY - rotX - rx) / denom);
        blWheel.setPower((rotY - rotX + rx) / denom);
        brWheel.setPower((rotY + rotX - rx) / denom);
    }

    public void resetHeading() {
        imu.resetYaw();
    }

    public double getHeadingDeg() {
        return Math.toDegrees(heading);
    }

    public void stop() {
        flWheel.setPower(0);
        frWheel.setPower(0);
        blWheel.setPower(0);
        brWheel.setPower(0);
    }

    private static double applyDeadzone(double v) {
        return (Math.abs(v) < RobotConfig.DEADZONE) ? 0.0 : v;
    }
}

package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

@TeleOp(name="Robot Centric Mecanum Drive", group="Linear Opmode")
public class RobotCentricDrive extends LinearOpMode {

    private static final double TURN_SCALE = 0.5;

    @Override
    public void runOpMode() {
        // Initialize motors
        DcMotorEx frontLeft  = hardwareMap.get(DcMotorEx.class, "FLwheel");
        DcMotorEx frontRight = hardwareMap.get(DcMotorEx.class, "FRwheel");
        DcMotorEx backLeft   = hardwareMap.get(DcMotorEx.class, "BLwheel");
        DcMotorEx backRight  = hardwareMap.get(DcMotorEx.class, "BRwheel");

        frontRight.setDirection(DcMotor.Direction.REVERSE);
        backRight.setDirection(DcMotor.Direction.REVERSE);

        waitForStart();

        while (opModeIsActive()) {

            double y = gamepad1.left_stick_y;
            double x = gamepad1.left_stick_x;
            double rx = gamepad1.right_stick_x * TURN_SCALE;

            double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);

            double frontLeftPower  = (y + x + rx) / denominator;
            double backLeftPower   = (y - x + rx) / denominator;
            double frontRightPower = (y - x - rx) / denominator;
            double backRightPower  = (y + x - rx) / denominator;

            frontLeft.setPower(frontLeftPower);
            backLeft.setPower(backLeftPower);
            frontRight.setPower(frontRightPower);
            backRight.setPower(backRightPower);
        }
    }
}

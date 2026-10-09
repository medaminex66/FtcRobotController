package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

/**
 * Push the robot by hand and watch the three odometry pod readings.
 * Odometry pods plug into a motor's encoder port, so each pod is read through
 * the name of the motor whose port it's in. Change the three names below to match.
 */
@TeleOp
public class OdoPodTest extends LinearOpMode {

    // Motor names whose ENCODER ports the pods are plugged into
    static final String LEFT_POD = "FLwheel";
    static final String RIGHT_POD = "FRwheel";
    static final String STRAFE_POD = "BRwheel";

    // goBILDA 4-Bar pod: 2000 ticks/rev, 32mm wheel. Swingarm pod (48mm wheel) = 336.9
    static final double TICKS_PER_INCH = 505.3;

    DcMotor left, right, strafe;

    @Override
    public void runOpMode() {
        left = hardwareMap.get(DcMotor.class, LEFT_POD);
        right = hardwareMap.get(DcMotor.class, RIGHT_POD);
        strafe = hardwareMap.get(DcMotor.class, STRAFE_POD);

        resetEncoders();

        telemetry.addLine("Push the robot by hand. Press A to reset.");
        telemetry.update();
        waitForStart();

        while (opModeIsActive()) {
            if (gamepad1.a) resetEncoders();

            int l = left.getCurrentPosition();
            int r = right.getCurrentPosition();
            int s = strafe.getCurrentPosition();

            telemetry.addLine("Push FORWARD: left & right should both go up");
            telemetry.addLine("Push SIDEWAYS: strafe should change, left/right ~0");
            telemetry.addLine();
            telemetry.addData("Left   (" + LEFT_POD + ")", "%d ticks  %.2f in", l, l / TICKS_PER_INCH);
            telemetry.addData("Right  (" + RIGHT_POD + ")", "%d ticks  %.2f in", r, r / TICKS_PER_INCH);
            telemetry.addData("Strafe (" + STRAFE_POD + ")", "%d ticks  %.2f in", s, s / TICKS_PER_INCH);
            telemetry.update();
        }
    }

    void resetEncoders() {
        for (DcMotor m : new DcMotor[]{left, right, strafe}) {
            m.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            m.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            m.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        }
    }
}

package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.Range;

@TeleOp
public class shootertest extends LinearOpMode {

    private static final double TICKS_PER_REV = 28.0;
    private static final double START_RPM = 500;

    private static final double INDEX_POWER = 1;

    private DcMotorEx shooter1, shooter2;
    private DcMotor Index;
    private double targetRpm = START_RPM;

    private boolean lastUp, lastDown, lastRb, lastLb;

    @Override
    public void runOpMode() {
        shooter1 = hardwareMap.get(DcMotorEx.class, "shooter_left");
        shooter2 = hardwareMap.get(DcMotorEx.class, "shooter_right");

        Index = hardwareMap.get(DcMotor.class, "Index");

        shooter2.setDirection(DcMotorSimple.Direction.REVERSE);

        shooter1.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        shooter2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        Index.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

        shooter1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        shooter2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        Index.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        telemetry.update();

        waitForStart();
        if (isStopRequested()) return;

        while (opModeIsActive()) {
            if (gamepad1.dpad_up && !lastUp)       targetRpm += 50;
            if (gamepad1.dpad_down && !lastDown)   targetRpm -= 50;
            if (gamepad1.right_bumper && !lastRb)  targetRpm += 200;
            if (gamepad1.left_bumper && !lastLb)   targetRpm -= 200;
            lastUp = gamepad1.dpad_up;
            lastDown = gamepad1.dpad_down;
            lastRb = gamepad1.right_bumper;
            lastLb = gamepad1.left_bumper;

            targetRpm = Range.clip(targetRpm, 0, 6000);

            boolean spin = gamepad1.right_trigger > 0.1;
            if (spin) {
                shooter1.setVelocity(targetRpm * TICKS_PER_REV / 60.0);
                shooter2.setVelocity(targetRpm * TICKS_PER_REV / 60.0);
            } else {
                shooter1.setPower(0);
                shooter2.setPower(0);
            }

            double rpm1 = shooter1.getVelocity() * 60.0 / TICKS_PER_REV;
            double rpm2 = shooter2.getVelocity() * 60.0 / TICKS_PER_REV;

            boolean atSpeed = spin
                    && Math.abs(rpm1 - targetRpm) < 50
                    && Math.abs(rpm2 - targetRpm) < 50;


            boolean feed = spin || gamepad2.left_bumper;
            Index.setPower(feed ? INDEX_POWER : 0);

            telemetry.addData("TARGET RPM", "%.0f", targetRpm);
            telemetry.addData("shooter1", "%.0f", rpm1);
            telemetry.addData("shooter2", "%.0f", rpm2);
            telemetry.addData("at speed", atSpeed ? "YES" : "no");
            telemetry.addData("indexer", feed ? "feeding" : "stopped");
            telemetry.update();
        }

        shooter1.setPower(0);
        shooter2.setPower(0);
        Index.setPower(0);
    }
}

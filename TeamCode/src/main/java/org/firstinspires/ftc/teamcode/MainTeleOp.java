package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.subsystems.DriveSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.IndexerSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.IntakeSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.RobotConfig;
import org.firstinspires.ftc.teamcode.subsystems.ShooterSubsystem;

@TeleOp(name = "Main TeleOp")
public class MainTeleOp extends OpMode {

    private static final double TRIGGER_THRESHOLD = 0.1;

    private DriveSubsystem drive;
    private IntakeSubsystem intake;
    private IndexerSubsystem indexer;
    private ShooterSubsystem shooter;

    @Override
    public void init() {
        for (LynxModule hub : hardwareMap.getAll(LynxModule.class)) {
            hub.setBulkCachingMode(LynxModule.BulkCachingMode.AUTO);
        }

        drive = new DriveSubsystem(hardwareMap);
        intake = new IntakeSubsystem(hardwareMap);
        indexer = new IndexerSubsystem(hardwareMap);
        shooter = new ShooterSubsystem(hardwareMap);
    }

    @Override
    public void start() {
        drive.resetHeading();
    }

    @Override
    public void loop() {
        if (gamepad1.options) {
            drive.resetHeading();
        }
        drive.drive(gamepad1.left_stick_x, -gamepad1.left_stick_y, gamepad1.right_stick_x);

        if (gamepad1.right_trigger > TRIGGER_THRESHOLD) {
            intake.in();
        } else if (gamepad1.left_trigger > TRIGGER_THRESHOLD) {
            intake.reverse();
        } else {
            intake.stop();
        }

        if (gamepad1.dpadUpWasPressed()) shooter.adjustRpm(RobotConfig.SHOOTER_RPM_STEP);
        if (gamepad1.dpadDownWasPressed()) shooter.adjustRpm(-RobotConfig.SHOOTER_RPM_STEP);

        if (gamepad1.right_bumper) {
            shooter.spinUp();
            if (shooter.atSpeed()) {
                indexer.feed();
            } else {
                indexer.stop();
            }
        } else {
            shooter.stop();
            indexer.stop();
        }

        telemetry.addData("Heading (deg)", "%.1f", drive.getHeadingDeg());
        telemetry.addData("Target RPM", "%.0f", shooter.getTargetRpm());
        telemetry.addData("Left RPM", "%.0f", shooter.getLeftRpm());
        telemetry.addData("Right RPM", "%.0f", shooter.getRightRpm());
        telemetry.addData("At speed", shooter.atSpeed() ? "YES" : "no");
        telemetry.addData("Indexer", indexer.isFeeding() ? "FEEDING" : "stopped");
        telemetry.addData("Intake", intake.getState());
        telemetry.update();
    }

    @Override
    public void stop() {
        drive.stop();
        intake.stop();
        indexer.stop();
        shooter.stop();
    }
}

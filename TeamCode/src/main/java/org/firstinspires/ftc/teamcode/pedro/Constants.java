package org.firstinspires.ftc.teamcode.pedro;

import com.pedropathing.algorithm.Foresight;
import com.pedropathing.algorithm.ForesightConfig;
import com.pedropathing.controllers.Controller;
import com.pedropathing.follower.Follower;
import com.pedropathing.math.Matrix;
import com.pedropathing.math.Vector2D;
import com.pedropathing.revhub.drivetrains.Mecanum;
import com.pedropathing.revhub.drivetrains.MecanumConfig;
import com.pedropathing.revhub.localizers.Encoder;
import com.pedropathing.revhub.localizers.RevHubIMU;
import com.pedropathing.revhub.localizers.ThreeWheelIMUConfig;
import com.pedropathing.revhub.localizers.ThreeWheelIMULocalizer;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Constants {
    public static MecanumConfig drivetrainConfig = new MecanumConfig(c -> {
        c.frontLeftName.set("FLwheel");
        c.frontRightName.set("FRwheel");
        c.backLeftName.set("BLwheel");
        c.backRightName.set("BRwheel");
        c.frontLeftDirection.set(DcMotorSimple.Direction.FORWARD);
        c.frontRightDirection.set(DcMotorSimple.Direction.REVERSE);
        c.backLeftDirection.set(DcMotorSimple.Direction.FORWARD);
        c.backRightDirection.set(DcMotorSimple.Direction.REVERSE);
    });
    public static ThreeWheelIMUConfig localizerConfig = new ThreeWheelIMUConfig(c -> {
        c.leftEncoderName.set("FRwheel");
        c.rightEncoderName.set("BRwheel");
        c.strafeEncoderName.set("FLwheel");
        c.imuName.set("imu");
        c.imu.set(new RevHubIMU(new RevHubOrientationOnRobot(
                RevHubOrientationOnRobot.LogoFacingDirection.RIGHT,
                RevHubOrientationOnRobot.UsbFacingDirection.UP
        )));
        c.leftPodY.set(4.969306753041992);
        c.rightPodY.set(-4.594780626677079);
        c.strafePodX.set(-4.559916305482691);
        c.forwardTicksToInches.set(0.001997637567343677);
        c.strafeTicksToInches.set(0.0020071011917541237);
        c.turnTicksToRadians.set(0.002031539327276286);
        c.leftEncoderDirection.set(Encoder.FORWARD);
        c.rightEncoderDirection.set(Encoder.FORWARD);
        c.strafeEncoderDirection.set(Encoder.REVERSE);
    });
    // TODO PLACEHOLDER: these are rough starting guesses so the Follower can be built, NOT tuned values.
    // Run the "Foresight Tuner" procedure in AutoTune (http://192.168.43.1:10158) on the robot and
    // paste the generated foresightConfig over this whole block before trusting any path.
    public static ForesightConfig foresightConfig = new ForesightConfig(
            c -> {
                Controller primaryTranslationalForward = Controller.proportional(0.1);
                Controller secondaryTranslationalForward = Controller.proportional(0.02);
                Controller primaryTranslationalLateral = Controller.proportional(0.1);
                Controller secondaryTranslationalLateral = Controller.proportional(0.02);

                c.forwardTranslational.set(Controller.piecewise(secondaryTranslationalForward).put(2.5, primaryTranslationalForward));
                c.strafeTranslational.set(Controller.piecewise(secondaryTranslationalLateral).put(2.5, primaryTranslationalLateral));

                c.coast.set(Controller.proportionalFeedforward(0.01));
                c.brake.set(Controller.proportionalFeedforward(0.01));

                c.headingFeedback.set(Controller.proportional(1.0));
                c.headingBrakeCoefficients.set(Vector2D.cartesian(0.1, 0.01));

                c.linearBrakeCoefficients.set(Matrix.diag(0.1, 0.1));
                c.quadraticBrakeCoefficients.set(Matrix.diag(0.001, 0.001));

                c.maxAchievableForwardVelocity.set(60.0);
                c.maxAchievableStrafeVelocity.set(45.0);
                c.naturalForwardDeceleration.set(30.0);
                c.naturalStrafeDeceleration.set(30.0);
            }
    );
    public static Follower create(HardwareMap h) {
        return new Follower(
                new ThreeWheelIMULocalizer(h, localizerConfig),
                new Mecanum(h, drivetrainConfig),
                new Foresight(foresightConfig)
        );
    }
}
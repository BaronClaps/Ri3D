package org.firstinspires.ftc.teamcode.opmode;

import static org.firstinspires.ftc.teamcode.config.util.FieldConstants.blueBucketStartPose;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.config.pedroPathing.follower.Follower;
import org.firstinspires.ftc.teamcode.config.runmodes.Teleop;


@TeleOp(name="Drive", group="A")
public class Drive extends OpMode {

    private Teleop teleop;

    @Override
    public void init() {
        teleop = new Teleop(hardwareMap, telemetry, new Follower(hardwareMap), blueBucketStartPose, false, gamepad1, gamepad2);
    }

    @Override
    public void start() {
        teleop.start();
    }

    @Override
    public void loop() {
        teleop.update();
    }

}

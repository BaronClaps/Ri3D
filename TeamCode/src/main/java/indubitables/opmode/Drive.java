package indubitables.opmode;

import static indubitables.config.util.FieldConstants.blueBucketStartPose;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import indubitables.config.pedroPathing.follower.Follower;
import indubitables.config.runmodes.Teleop;


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

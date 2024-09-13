package org.firstinspires.ftc.teamcode.opmode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.config.pedroPathing.follower.Follower;
import org.firstinspires.ftc.teamcode.config.runmodes.Auto;
import org.firstinspires.ftc.teamcode.config.util.BaronAuto;
import org.firstinspires.ftc.teamcode.config.util.FieldConstants;
import org.firstinspires.ftc.teamcode.config.util.action.Action;
import org.firstinspires.ftc.teamcode.config.util.action.Actions;

@Autonomous(name="BlueObservation", group="Blue")
public class BlueObservation extends OpMode {
    public int pathState;
    public Auto auto;

    @Override
    public void init() {
        auto = new Auto(hardwareMap, telemetry, new Follower(hardwareMap), true, false);
    }

    @Override
    public void init_loop() {
        auto.init_loop();
    }

    @Override
    public void start() {
        auto.start();
        //setPathState(0);
    }

    @Override
    public void loop() {
        auto.update();
        //pathUpdate();
    }

    public void pathUpdate() {
        switch (pathState) {
            case 0:
                auto.follower.followPath(auto.preload);
                setPathState(1);
                break;
            case 1:
                if(auto.pathNotBusy()) {
                    auto.follower.followPath(auto.element2);
                }
                setPathState(-1);
                break;
        }
    }

    public void setPathState(int x) {
        pathState = x;
    }
}

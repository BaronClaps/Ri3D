package org.firstinspires.ftc.teamcode.config.util;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.checkerframework.checker.units.qual.A;
import org.firstinspires.ftc.teamcode.config.pedroPathing.follower.Follower;
import org.firstinspires.ftc.teamcode.config.runmodes.Auto;
import org.firstinspires.ftc.teamcode.config.vision.Navigation;

public abstract class BaronAuto extends OpMode {
    public static int pathState;
    public Auto auto;

    @Override
    public void init() {
        auto = new Auto(hardwareMap, telemetry, new Follower(hardwareMap), true, true);
        auto.init();
    }

    @Override
    public void init_loop() {
        auto.init_loop();
    }

    @Override
    public void start() {
        auto.start();
    }

    @Override
    public void loop() {
        auto.update();
        pathUpdate();
    }

    @Override
    public void stop() {
    }


    abstract public void pathUpdate();
    abstract public void setPathState(int x);
}
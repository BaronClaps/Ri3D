package org.firstinspires.ftc.teamcode.config.util;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.config.pedroPathing.follower.Follower;
import org.firstinspires.ftc.teamcode.config.runmodes.Auto;

public abstract class BaronAuto extends OpMode {
    public static int pathState;
    public Auto auto;
    public boolean isBlue, isBucket;

    public void setAuto(boolean isBlue, boolean isBucket){
        this.isBlue = isBlue;
        this.isBucket = isBucket;
    }

    @Override
    public void init() {
        auto = new Auto(hardwareMap, telemetry, new Follower(hardwareMap), isBlue, isBucket);
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

    abstract public void pathUpdate();

    public void setPathState(int x) {
        pathState = x;
    }
}
package org.firstinspires.ftc.teamcode.opmode;

import org.firstinspires.ftc.teamcode.config.util.BaronAuto;

public class BucketAuto extends BaronAuto {

    @Override
    public void init() {
        super.init();
    }

    @Override
    public void init_loop() {
        super.init_loop();
    }

    @Override
    public void start() {
        super.start();
    }

    @Override
    public void loop() {
        super.loop();
    }

    @Override
    public void stop() {
        super.stop();
    }

    @Override
    public void pathUpdate() {
        switch (pathState) {
            case 0:
                break;
        }
    }

    @Override
    public void setPathState(int x) {
        pathState = x;
    }
}

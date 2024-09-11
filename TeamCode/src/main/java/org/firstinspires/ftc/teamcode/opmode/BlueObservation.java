package org.firstinspires.ftc.teamcode.opmode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.config.util.BaronAuto;
import org.firstinspires.ftc.teamcode.config.util.FieldConstants;
import org.firstinspires.ftc.teamcode.config.util.action.Action;
import org.firstinspires.ftc.teamcode.config.util.action.Actions;

@Autonomous(name="BlueObservation", group="Blue")
public class BlueObservation extends BaronAuto {

    @Override
    public void init() {
        super.setAuto(true, true);
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
                auto.followPath(auto.preload);
                break;
            case 1:
                if(auto.pathNotBusy()){
                    auto.followPath(auto.element1);
                }
                break;
        }
    }
}

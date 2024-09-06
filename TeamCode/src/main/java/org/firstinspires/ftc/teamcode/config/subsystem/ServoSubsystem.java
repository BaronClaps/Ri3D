package org.firstinspires.ftc.teamcode.config.subsystem;
import static org.firstinspires.ftc.teamcode.config.util.RobotConstants.*;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;



public class ServoSubsystem {

    private Servo s;

    public ServoSubsystem(HardwareMap hardwareMap) {
        s = hardwareMap.get(Servo.class, "s");
    }

    public void sPos(double pos) {
        s.setPosition(pos);
    }

    public void init() {
        s.setPosition(sInit);
    }

    public void start() {
        s.setPosition(sStart);
    }

}
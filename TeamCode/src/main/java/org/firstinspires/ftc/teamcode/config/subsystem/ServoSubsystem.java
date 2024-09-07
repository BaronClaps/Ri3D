package org.firstinspires.ftc.teamcode.config.subsystem;
import static org.firstinspires.ftc.teamcode.config.util.RobotConstants.*;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;



public class ServoSubsystem {

    private Servo claw;

    public ServoSubsystem(HardwareMap hardwareMap) {
        claw = hardwareMap.get(Servo.class, "claw");
    }

    public void clawPos(double clawPos) {
        claw.setPosition(clawPos);
    }

    public void init() {
        claw.setPosition(sInit);
    }

    public void start() {
        claw.setPosition(sStart);
    }

}
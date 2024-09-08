package org.firstinspires.ftc.teamcode.config.subsystem;
import static org.firstinspires.ftc.teamcode.config.util.RobotConstants.*;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;



public class ClawSubsystem {

    private Servo claw;

    public ClawSubsystem(HardwareMap hardwareMap) {
        claw = hardwareMap.get(Servo.class, "claw");
    }

    public void clawPos(double clawPos) {
        claw.setPosition(clawPos);
    }

    public void init() {
        claw.setPosition(1);
    }

    public void start() {
        claw.setPosition(0.5);
    }

}
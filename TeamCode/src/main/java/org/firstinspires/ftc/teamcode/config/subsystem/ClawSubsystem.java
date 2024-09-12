package org.firstinspires.ftc.teamcode.config.subsystem;
import static org.firstinspires.ftc.teamcode.config.util.RobotConstants.*;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;


public class ClawSubsystem {

    public enum ClawState {
        CLOSED, OPEN
    }

    private Servo claw;
    private ClawState state;

    public ClawSubsystem(HardwareMap hardwareMap, ClawState clawState) {
        claw = hardwareMap.get(Servo.class, "claw");
        this.state = clawState;
    }

    public void setPos(double clawPos) {
        claw.setPosition(clawPos);
    }

    public void setState(ClawState clawState) {
        if (clawState == ClawState.CLOSED) {
            claw.setPosition(clawClose);
            this.state = ClawState.CLOSED;
        } else if (clawState == ClawState.OPEN) {
            claw.setPosition(clawOpen);
            this.state = ClawState.OPEN;
        }
    }

    public void switchState() {
        if (state == ClawState.CLOSED) {
            setState(ClawState.OPEN);
        } else if (state == ClawState.OPEN) {
            setState(ClawState.CLOSED);
        }
    }

    public void init() {
        setState(ClawState.CLOSED);
    }

    public void start() {
        setState(ClawState.CLOSED);
    }



}
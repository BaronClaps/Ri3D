package org.firstinspires.ftc.teamcode.config.subsystem;
import static org.firstinspires.ftc.teamcode.config.util.RobotConstants.*;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.config.util.RobotConstants;


public class ClawSubsystem {

    public enum ClawState {
        CLOSED, OPEN
    }

    private Servo claw;
    private ClawState clawState;

    public ClawSubsystem(HardwareMap hardwareMap, ClawState clawState) {
        claw = hardwareMap.get(Servo.class, "claw");
        this.clawState = clawState;
    }

    public void clawPos(double clawPos) {
        claw.setPosition(clawPos);
    }

    public void init() {
        setClawState(ClawState.CLOSED);
    }

    public void start() {
        setClawState(ClawState.OPEN);
    }

    public void setClawState(ClawState clawState) {
        if (clawState == ClawState.CLOSED) {
            claw.setPosition(clawClose);
            this.clawState = ClawState.CLOSED;
        } else if (clawState == ClawState.OPEN) {
            claw.setPosition(clawOpen);
            this.clawState = ClawState.OPEN;
        }
    }

    public void switchClawState() {
        if (clawState == ClawState.CLOSED) {
            setClawState(ClawState.OPEN);
        } else if (clawState == ClawState.OPEN) {
            setClawState(ClawState.CLOSED);
        }
    }

}
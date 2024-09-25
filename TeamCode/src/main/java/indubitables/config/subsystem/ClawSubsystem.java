package indubitables.config.subsystem;
import static indubitables.config.util.RobotConstants.*;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import indubitables.config.util.action.Actions;
import indubitables.config.util.action.RunAction;


public class ClawSubsystem {

    public enum ClawState {
        CLOSED, OPEN
    }

    private Servo claw;
    private ClawState state;
    public RunAction openClaw, closeClaw;

    public ClawSubsystem(HardwareMap hardwareMap, ClawState clawState) {
        claw = hardwareMap.get(Servo.class, "claw");
        this.state = clawState;

        openClaw = new RunAction(this::openClaw);
        closeClaw = new RunAction(this::closeClaw);
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

    public void openClaw() {
        setState(ClawState.OPEN);
    }

    public void closeClaw() {
        setState(ClawState.CLOSED);
    }

    public void init() {
        Actions.runBlocking(closeClaw);
    }

    public void start() {
        Actions.runBlocking(closeClaw);
    }



}
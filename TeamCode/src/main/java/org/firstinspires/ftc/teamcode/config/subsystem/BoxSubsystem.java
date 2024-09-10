package org.firstinspires.ftc.teamcode.config.subsystem;

import static org.firstinspires.ftc.teamcode.config.util.RobotConstants.boxScoringPos;
import static org.firstinspires.ftc.teamcode.config.util.RobotConstants.boxTransferPos;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.config.util.action.RunAction;


public class BoxSubsystem {

    public enum BoxState {
        TRANSFER, SCORING
    }

    private Servo box;
    private BoxState state;
    public RunAction toTransfer, toScoring;

    public BoxSubsystem(HardwareMap hardwareMap, BoxState state) {
        box = hardwareMap.get(Servo.class, "box");
        this.state = state;

        toTransfer = new RunAction(this::toTransfer);
        toScoring = new RunAction(this::toScoring);
    }

    // State //
    public void setState(BoxState boxState) {
        if (boxState == BoxState.TRANSFER) {
            box.setPosition(boxTransferPos);
            this.state = BoxState.TRANSFER;
        } else if (boxState == BoxState.SCORING) {
            box.setPosition(boxScoringPos);
            this.state = BoxState.SCORING;
        }
    }

    public void switchState() {
        if (state == BoxState.TRANSFER) {
            setState(BoxState.SCORING);
        } else if (state == BoxState.SCORING) {
            setState(BoxState.TRANSFER);
        }
    }

    // Preset //

    public void toTransfer() {
        setState(BoxState.TRANSFER);
    }

    public void toScoring() {
        setState(BoxState.SCORING);
    }

    // Util //
    public void setPos(double BoxPos) {
        box.setPosition(BoxPos);
    }

    public double getPos() {
        return box.getPosition();
    }

    // Init + Start //
    public void init() {
        setState(BoxState.TRANSFER);
    }

    public void start() {
        setState(BoxState.TRANSFER);
    }

}
package org.firstinspires.ftc.teamcode.config.subsystem;
import static org.firstinspires.ftc.teamcode.config.util.RobotConstants.*;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.config.util.RobotConstants;


public class BoxSubsystem {

    public enum BoxState {
        TRANSFER, SCORING
    }

    private Servo box;
    private BoxState boxState;

    public BoxSubsystem(HardwareMap hardwareMap, BoxState BoxState) {
        box = hardwareMap.get(Servo.class, "box");
        this.boxState = BoxState;
    }

    public void BoxPos(double BoxPos) {
        box.setPosition(BoxPos);
    }

    public void init() {
        setBoxState(BoxState.TRANSFER);
    }

    public void start() {
        setBoxState(BoxState.TRANSFER);
    }

    public void setBoxState(BoxState BoxState) {
        if (BoxState == BoxState.TRANSFER) {
            box.setPosition(0);
            this.boxState = BoxState.TRANSFER;
        } else if (BoxState == BoxState.SCORING) {
            box.setPosition(1);
            this.boxState = BoxState.SCORING;
        }
    }

    public void switchBoxState() {
        if (boxState == BoxState.TRANSFER) {
            setBoxState(BoxState.SCORING);
        } else if (boxState == BoxState.SCORING) {
            setBoxState(BoxState.TRANSFER);
        }
    }

}
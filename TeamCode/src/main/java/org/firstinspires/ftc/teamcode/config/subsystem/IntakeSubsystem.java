package org.firstinspires.ftc.teamcode.config.subsystem;

import static org.firstinspires.ftc.teamcode.config.util.RobotConstants.clawClose;
import static org.firstinspires.ftc.teamcode.config.util.RobotConstants.clawOpen;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class IntakeSubsystem {

    public enum IntakeState {
        IN, OUT, STOP
    }

    private CRServo intakeSpin;
    private Servo intakePivot;
    private IntakeState intakeState;

    public IntakeSubsystem(HardwareMap hardwareMap, IntakeState intakeState) {
        intakeSpin = hardwareMap.get(CRServo.class, "intakeSpin");
        intakePivot = hardwareMap.get(Servo.class, "intakePivot");
        this.intakeState = intakeState;
    }

    public void setIntakeState(IntakeState intakeState) {
        if (intakeState == IntakeState.IN) {
            intakeSpin.setPower(1);
            this.intakeState = IntakeState.IN;
        } else if (intakeState == IntakeState.OUT) {
            intakeSpin.setPower(-.25);
            this.intakeState = IntakeState.OUT;
        } else if (intakeState == IntakeState.STOP){
            intakeSpin.setPower(0);
            this.intakeState = IntakeState.STOP;
        }
    }

    public void intakePivotTransfer() {
        intakePivot.setPosition(0.57);
    }

    public void intakePivotGround() {
        intakePivot.setPosition(1);
    }

    public void init() {
        setIntakeState(IntakeState.STOP);
        intakePivotTransfer();

    }
    public void start() {
        setIntakeState(IntakeState.STOP);
        intakePivotTransfer();
    }
}
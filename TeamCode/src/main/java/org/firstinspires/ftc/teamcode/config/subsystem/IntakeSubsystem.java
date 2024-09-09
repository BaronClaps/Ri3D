package org.firstinspires.ftc.teamcode.config.subsystem;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class IntakeSubsystem {

    private CRServo intakeSpin;

    public IntakeSubsystem(HardwareMap hardwareMap) {
        intakeSpin = hardwareMap.get(CRServo.class, "intakeSpin");
    }

    public void setIntakeSpin(double intakeSpinPow) {
        intakeSpin.setPower(intakeSpinPow);
    }

    public void intakeSpinIn() {
        intakeSpin.setPower(1);
    }

    public void intakeSpinOut() {
        intakeSpin.setPower(-1);
    }

    public void intakeSpinStop() {
        intakeSpin.setPower(0);
    }

    public void init() {
        intakeSpinStop();
    }
    public void start() {
        intakeSpinStop();
    }
}
package org.firstinspires.ftc.teamcode.config.subsystem;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.config.util.action.RunAction;

public class ExtendSubsystem {

    public DcMotor extend;
    private int pos;
    public RunAction toZero, toHalf, toFull;

    public ExtendSubsystem(HardwareMap hardwareMap) {
        extend = hardwareMap.get(DcMotor.class, "extend");
        extend.setDirection(DcMotorSimple.Direction.REVERSE);
        toZero = new RunAction(this::toZero);
        toHalf = new RunAction(this::toHalf);
        toFull = new RunAction(this::toFull);
    }

    public void manual(int extendPos, boolean negative) {
        if (!negative) {
            extend.setPower(1);
            extend.setTargetPosition(extend.getCurrentPosition() + extendPos);
            if (extend.getCurrentPosition() > this.pos) {
                extend.setPower(0);
            }
        }

        if (negative) {
            extend.setPower(-1);
            extend.setTargetPosition(extend.getCurrentPosition() - extendPos);
            if (extend.getCurrentPosition() < this.pos) {
                extend.setPower(0);
            }
        }
    }

    // Preset //
    public void preset(int pos) {
        updatePos();
        extend.setTargetPosition(pos);
        extend.setPower(1);
    }

    public void toZero() {
        updatePos();
        extend.setTargetPosition(10);
        extend.setPower(-1);

        if (extend.getCurrentPosition() < this.pos) {
            extend.setPower(0);
        }
    }

    public void toHalf() {
        updatePos();
        extend.setTargetPosition(450);
        extend.setPower(1);

        if (extend.getCurrentPosition() > this.pos) {
            extend.setPower(0);
        }
    }

    public void toFull() {
        updatePos();
        extend.setTargetPosition(700);
        extend.setPower(1);

        if (extend.getCurrentPosition() > this.pos) {
            extend.setPower(0);
        }
    }

    // Util //
    public double getPos() {
        return pos;
    }

    public void updatePos() {
        this.pos = extend.getCurrentPosition();
    }

    public void resetEncoder(){
        extend.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        extend.setMode(DcMotor.RunMode.RUN_TO_POSITION);

    }

    // Init + Start //
    public void init() {
        resetEncoder();
        extend.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        pos = extend.getCurrentPosition();
    }

    public void start() {}

}
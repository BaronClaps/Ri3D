package org.firstinspires.ftc.teamcode.config.subsystem;

import static java.lang.Math.abs;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class LiftSubsystem {

    public DcMotor lift;
    private int liftPos;

    public LiftSubsystem(HardwareMap hardwareMap) {
        lift = hardwareMap.get(DcMotor.class, "lift");
    }

    public void manualLift(int liftPos, boolean negative) {

        this.liftPos = lift.getCurrentPosition();

        if (!negative) {
            lift.setPower(1);
            lift.setTargetPosition(lift.getCurrentPosition() + liftPos);
            if (lift.getCurrentPosition() > this.liftPos) {
                lift.setPower(0);
            }
        }

        if (negative) {
            lift.setPower(-1);
            lift.setTargetPosition(lift.getCurrentPosition() - liftPos);
            if (lift.getCurrentPosition() < this.liftPos) {
                lift.setPower(0);
            }
        }
    }

    public void resetEncoder(){
        lift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);

    }

    public void init() {
        resetEncoder();
        lift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        liftPos = lift.getCurrentPosition();
    }

    public double getLiftPos() {
        return liftPos;
    }


    public void start() {

    }

}
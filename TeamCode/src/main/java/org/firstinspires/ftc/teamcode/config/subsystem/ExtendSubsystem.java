package org.firstinspires.ftc.teamcode.config.subsystem;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class ExtendSubsystem {

    public DcMotor extend;
    private int extendPos;

    public ExtendSubsystem(HardwareMap hardwareMap) {
        extend = hardwareMap.get(DcMotor.class, "extend");
    }

    public void manualExtend(int extendPos, boolean negative) {
        if (!negative) {
            extend.setPower(1);
            extend.setTargetPosition(extend.getCurrentPosition() + extendPos);
            if (extend.getCurrentPosition() > this.extendPos) {
                extend.setPower(0);
            }
        }

        if (negative) {
            extend.setPower(-1);
            extend.setTargetPosition(extend.getCurrentPosition() - extendPos);
            if (extend.getCurrentPosition() < this.extendPos) {
                extend.setPower(0);
            }
        }
    }

    public void resetEncoder(){
        extend.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        extend.setMode(DcMotor.RunMode.RUN_TO_POSITION);

    }

    public void init() {
        resetEncoder();
        extend.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        extendPos = extend.getCurrentPosition();
    }

    public double getExtendPos() {
        return extendPos;
    }


    public void start() {

    }

}
package org.firstinspires.ftc.teamcode.config.subsystem;

import static java.lang.Math.abs;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.config.util.action.RunAction;

public class LiftSubsystem {

    public DcMotor lift;
    private int pos;
    public RunAction toZero, toLowBucket, toHighBucket, toLowChamber, toHighChamber, toHumanPlayer;

    public LiftSubsystem(HardwareMap hardwareMap) {
        lift = hardwareMap.get(DcMotor.class, "lift");
        lift.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        lift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        toZero = new RunAction(this::toZero);
        toLowBucket = new RunAction(this::toLowBucket);
        toHighBucket = new RunAction(this::toHighBucket);
        toLowChamber = new RunAction(this::toLowChamber);
        toHighChamber = new RunAction(this::toHighChamber);
        toHumanPlayer = new RunAction(this::toHumanPlayer);
    }

    // Manual Control //
    public void manual(double n){ //(int liftPos, boolean negative) {
        lift.setPower(n);
        /*if (!negative) {
            lift.setPower(1);
            this.liftPos = this.liftPos + liftPos;
            lift.setTargetPosition(this.liftPos);
        }

        if (negative) {
            lift.setPower(-1);
            this.liftPos = lift.getCurrentPosition() - liftPos;
            lift.setTargetPosition(this.liftPos);
        }*/
    }

    public void manual(double n, int liftPos) {
        lift.setPower(n);
       updatePos();
        lift.setTargetPosition(liftPos + pos);
        if (lift.getCurrentPosition() > this.pos) {
            lift.setPower(0);
        }
    }

    public void toZero() {
        updatePos();
        lift.setTargetPosition(10);
        lift.setPower(1);
    }

    public void toLowBucket() {
        updatePos();
        lift.setPower(1);
        lift.setTargetPosition(3300);
    }

    public void toHighBucket() {
        updatePos();
        lift.setPower(1);
        lift.setTargetPosition(3300);
    }

    public void toLowChamber() {
        updatePos();
        lift.setPower(1);
        lift.setTargetPosition(3000);
    }

    public void toHighChamber() {
        updatePos();
        lift.setPower(1);
        lift.setTargetPosition(4000);
    }

    public void toHumanPlayer() {
        updatePos();
        lift.setPower(1);
        lift.setTargetPosition(2100);
    }

    // Util //

    public void resetEncoder(){
        lift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

    }

    public double getPos() {
        return pos;
    }

    public void updatePos() {
        pos = lift.getCurrentPosition();
    }

    // Init + Start //

    public void init() {
        resetEncoder();
        lift.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        lift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        pos = lift.getCurrentPosition();
    }

    public void start() {}

}
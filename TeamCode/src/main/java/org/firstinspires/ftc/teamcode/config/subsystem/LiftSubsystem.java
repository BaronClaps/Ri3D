package org.firstinspires.ftc.teamcode.config.subsystem;

import static java.lang.Math.abs;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.config.util.action.RunAction;

public class LiftSubsystem {

    public DcMotor lift;
    private int liftPos;
    public RunAction toZero, toLowBucket, toHighBucket, toLowChamber, toHighChamber, toHumanPlayer;

    public LiftSubsystem(HardwareMap hardwareMap) {
        lift = hardwareMap.get(DcMotor.class, "lift");

        toZero = new RunAction(this::toZero);
        toLowBucket = new RunAction(this::toLowBucket);
        toHighBucket = new RunAction(this::toHighBucket);
        toLowChamber = new RunAction(this::toLowChamber);
        toHighChamber = new RunAction(this::toHighChamber);
        toHumanPlayer = new RunAction(this::toHumanPlayer);
    }

    // Manual Control //
    public void manual(int liftPos, boolean negative) {
        updatePos();

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

    // Presets //
    public void preset(int liftPos) {
        updatePos();
        lift.setTargetPosition(liftPos);
        lift.setPower(1);
    }

    public void toZero() {
        updatePos();
        lift.setTargetPosition(10);
        lift.setPower(1);
    }

    public void toLowBucket() {
        updatePos();
        lift.setTargetPosition(3100);
        lift.setPower(1);
    }

    public void toHighBucket() {
        updatePos();
        lift.setTargetPosition(4500);
        lift.setPower(1);
    }

    public void toLowChamber() {
        updatePos();
        lift.setTargetPosition(2000);
        lift.setPower(1);
    }

    public void toHighChamber() {
        updatePos();
        lift.setTargetPosition(3500);
        lift.setPower(1);
    }

    public void toHumanPlayer() {
        updatePos();
        lift.setTargetPosition(1000);
        lift.setPower(1);
    }

    // Util //

    public void resetEncoder(){
        lift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);

    }

    public double getPos() {
        return liftPos;
    }

    public void updatePos() {
        this.liftPos = lift.getCurrentPosition();
    }

    // Init + Start //

    public void init() {
        resetEncoder();
        lift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        liftPos = lift.getCurrentPosition();
    }

    public void start() {}

}
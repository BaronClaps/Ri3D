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

    // Presets //
    public void preset(int liftPos, boolean negative) {
        updatePos();

        if (!negative) {
            lift.setPower(1);
            lift.setTargetPosition(liftPos);
            if (lift.getCurrentPosition() > this.liftPos) {
                lift.setPower(0);
            }
        }

        if (negative) {
            lift.setPower(-1);
            lift.setTargetPosition(liftPos);
            if (lift.getCurrentPosition() < this.liftPos) {
                lift.setPower(0);
            }
        }
    }

    public void toZero() {
        updatePos();
        lift.setTargetPosition(10);
        liftPos = 10;
        lift.setPower(-1);

        if (lift.getCurrentPosition() < liftPos) {
            lift.setPower(0);
        }
    }

    public void toLowBucket() {
        updatePos();
        lift.setTargetPosition(3300);
        liftPos = 3300;
        lift.setPower(1);

        if (lift.getCurrentPosition() > liftPos) {
            lift.setPower(0);
        }
    }

    public void toHighBucket() {
        updatePos();
        lift.setTargetPosition(3300);
        lift.setPower(1);

        if (lift.getCurrentPosition() > this.liftPos) {
            lift.setPower(0);
        }
    }

    public void toLowChamber() {
        updatePos();
        lift.setTargetPosition(3000);
        lift.setPower(1);

        if (lift.getCurrentPosition() > this.liftPos) {
            lift.setPower(0);
        }
    }

    public void toHighChamber() {
        updatePos();
        lift.setTargetPosition(4000);
        lift.setPower(1);

        if (lift.getCurrentPosition() > this.liftPos) {
            lift.setPower(0);
        }
    }

    public void toHumanPlayer() {
        updatePos();
        lift.setTargetPosition(2100);
        lift.setPower(1);

        if (lift.getCurrentPosition() > this.liftPos) {
            lift.setPower(0);
        }
    }

    // Util //

    public void resetEncoder(){
        lift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

    }

    public double getPos() {
        return liftPos;
    }

    public void updatePos() {
        liftPos = lift.getCurrentPosition();
    }

    // Init + Start //

    public void init() {
        resetEncoder();
        lift.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        lift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        liftPos = lift.getCurrentPosition();
    }

    public void start() {}

}
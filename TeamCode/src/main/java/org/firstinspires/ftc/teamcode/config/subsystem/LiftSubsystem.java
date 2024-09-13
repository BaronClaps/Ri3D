package org.firstinspires.ftc.teamcode.config.subsystem;

import static java.lang.Math.abs;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.config.pedroPathing.util.PIDFController;
import org.firstinspires.ftc.teamcode.config.util.action.RunAction;

public class LiftSubsystem {

    public DcMotor lift;
    private int pos;
    public RunAction toZero, toLowBucket, toHighBucket, toLowChamber, toHighChamber, toHumanPlayer;
    public PIDFController liftPIDF;
    public static int targetPos;
    public static double p = 0, i = 0, d = 0, f = 0;
    private final double ticks_in_degrees = 700 / 180.0;


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

    public void toZero() {
        updatePos();
        lift.setPower(1);
        lift.setTargetPosition(10);
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
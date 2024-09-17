package org.firstinspires.ftc.teamcode.config.subsystem;

import static org.firstinspires.ftc.teamcode.config.util.RobotConstants.*;
import static java.lang.Math.abs;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.controller.PIDController;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.robotcore.external.Telemetry;

import org.firstinspires.ftc.teamcode.config.pedroPathing.util.PIDFController;
import org.firstinspires.ftc.teamcode.config.util.RobotConstants;
import org.firstinspires.ftc.teamcode.config.util.action.RunAction;

public class LiftSubsystem {
    private Telemetry telemetry;

    public DcMotor lift;
    private int pos, initalPos;
    public RunAction toZero, toLowBucket, toHighBucket, toLowChamber, releaseLowChamber, toHighChamber, releaseHighChamber, toHumanPlayer;
    public PIDController liftPID;
    public static int target;
    public static double p = 0.04, i = 0, d = 0.000001;
    public static double f = 0.01;
    private final double ticks_in_degrees = 537.7 / 360.0;


    public LiftSubsystem(HardwareMap hardwareMap, Telemetry telemetry) {
        this.telemetry = telemetry;
        this.telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());
        lift = hardwareMap.get(DcMotor.class, "lift");
        lift.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        lift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        liftPID = new PIDController(p, i, d);

        toZero = new RunAction(this::toZero);
        toLowBucket = new RunAction(this::toLowBucket);
        toHighBucket = new RunAction(this::toHighBucket);
        toLowChamber = new RunAction(this::toLowChamber);
        releaseLowChamber = new RunAction(this::releaseLowChamber);
        toHighChamber = new RunAction(this::toHighChamber);
        releaseHighChamber = new RunAction(this::releaseHighChamber);
        toHumanPlayer = new RunAction(this::toHumanPlayer);
    }

    // Manual Control //
    public void manual(double n){ //(int liftPos, boolean negative) {
        lift.setPower(n);
    }

    public void setTarget(int b) {
        target = b;
    }

    public void addToTarget(int b) {
        target += b;
    }

    public void updatePIDF(){
        liftPID.setPID(p,i,d);
        updatePos();
        double pid = liftPID.calculate(pos, target);
        double ff = Math.cos(Math.toRadians(target/ticks_in_degrees)) * f;

        double power = pid + ff;

        lift.setPower(power);
        telemetry.addData("lift pos", pos);
        telemetry.addData("lift target", target);
    }

    public void toZero() {
        setTarget(liftZeroPos);
    }

    public void toLowBucket() {
        setTarget(liftToLowBucketPos);
    }

    public void toHighBucket() {
        setTarget(liftToHighBucketPos);
    }

    public void toLowChamber() {
        setTarget(liftToLowChamberPos);
    }

    public void releaseLowChamber() {
        setTarget(liftReleaseLowChamberPos);
    }

    public void toHighChamber() {
        setTarget(liftToHighChamberPos);
    }

    public void releaseHighChamber() {
        setTarget(liftReleaseHighChamberPos);
    }

    public void toHumanPlayer() {
        setTarget(liftToHumanPlayerPos);
    }

    // Util //
    public double getPos() {
        updatePos();
        return pos;
    }

    public void updatePos() {
        pos = lift.getCurrentPosition() - initalPos;
    }

    public boolean isAtTarget() {
        return Math.abs(pos - target) < 10;
    }

    public void resetEncoder() {
        lift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        lift.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    // Init + Start //

    public void init() {
        resetEncoder();
        initalPos = lift.getCurrentPosition();
        lift.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        lift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    public void start() {
        initalPos = lift.getCurrentPosition();
        setTarget(10);
    }

}
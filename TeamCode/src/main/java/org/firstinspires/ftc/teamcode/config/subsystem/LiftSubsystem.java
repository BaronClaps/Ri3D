package org.firstinspires.ftc.teamcode.config.subsystem;

import static java.lang.Math.abs;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.controller.PIDController;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.robotcore.external.Telemetry;

import org.firstinspires.ftc.teamcode.config.pedroPathing.util.PIDFController;
import org.firstinspires.ftc.teamcode.config.util.action.RunAction;

public class LiftSubsystem {
    private Telemetry telemetry;

    public DcMotor lift;
    private int pos;
    public RunAction toZero, toLowBucket, toHighBucket, toLowChamber, toHighChamber, toHumanPlayer;
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
        toHighChamber = new RunAction(this::toHighChamber);
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
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
    private int pos, initalPos;
    public RunAction toZero, toLowBucket, toHighBucket, toLowChamber, toHighChamber, toHighChamberRelease, toHumanPlayer;
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
        toHighChamberRelease = new RunAction(this::toHighChamberRelease);
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
        setTarget(0);
    }

    public void toLowBucket() {
        setTarget(4300);
    }

    public void toHighBucket() {
        setTarget(5000);
    }

    public void toLowChamber() {
        setTarget(3000);
    }

    public void toHighChamber() {
        setTarget(3000);
    }

    public void toHighChamberRelease() {
        setTarget(4000);
    }

    public void toHumanPlayer() {
        setTarget(500);
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

    // Init + Start //

    public void init() {
        initalPos = lift.getCurrentPosition();
        lift.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        lift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    public void start() {}

}
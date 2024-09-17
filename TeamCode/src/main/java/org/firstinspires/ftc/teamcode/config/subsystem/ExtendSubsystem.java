package org.firstinspires.ftc.teamcode.config.subsystem;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.controller.PIDController;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.robotcore.external.Telemetry;

import org.firstinspires.ftc.teamcode.config.util.action.RunAction;

public class ExtendSubsystem {
    private Telemetry telemetry;

    public DcMotor extend;
    private int pos, initalPos;
    public RunAction toZero, toHalf, toFull;

    public PIDController extendPID;
    public static int target;
    public static double p = 0.05, i = 0, d = 0.001;
    public static double f = 0.01;
    private final double ticks_in_degrees = 537.7 / 360.0;

    public ExtendSubsystem(HardwareMap hardwareMap, Telemetry telemetry) {
        this.telemetry = telemetry;
        this.telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());

        extend = hardwareMap.get(DcMotor.class, "extend");
        extend.setDirection(DcMotorSimple.Direction.REVERSE);
        extend.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        extend.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        extendPID = new PIDController(p, i, d);

        toZero = new RunAction(this::toZero);
        toHalf = new RunAction(this::toHalf);
        toFull = new RunAction(this::toFull);
    }

    public void manual(double n) {
        extend.setPower(n);
    }

    public void setTarget(int b) {
        target = b;
    }

    public void addToTarget(int b) {
        target += b;
    }

    public void updatePIDF(){
        extendPID.setPID(p,i,d);
        updatePos();
        double pid = extendPID.calculate(pos, target);
        double ff = Math.cos(Math.toRadians(target/ticks_in_degrees)) * f;

        double power = pid + ff;

        extend.setPower(power);
        telemetry.addData("extend pos", pos);
        telemetry.addData("extend target", target);
    }

    public void toZero() {
        setTarget(0);
    }

    public void toHalf() {
        setTarget(1000);
    }

    public void toFull() {
        setTarget(2000);
    }

    // Util //
    public double getPos() {
        updatePos();
        return pos;
    }

    public void updatePos() {
        pos = extend.getCurrentPosition() - initalPos;
    }

    public boolean isAtTarget() {
        return Math.abs(pos - target) < 10;
    }

    public void resetEncoder() {
        extend.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        extend.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    // Init + Start //
    public void init() {
        resetEncoder();
        extend.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        extend.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        initalPos = extend.getCurrentPosition();
        pos = extend.getCurrentPosition();
    }

    public void start() {
        initalPos = extend.getCurrentPosition();
        pos = extend.getCurrentPosition();
        setTarget(10);
    }

}
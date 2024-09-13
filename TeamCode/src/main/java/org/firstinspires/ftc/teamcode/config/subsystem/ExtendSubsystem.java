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
    private int pos;
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

    public void manual(double n){ //(int extendPos, boolean negative) {
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
        updatePos();
        extend.setTargetPosition(10);
        extend.setPower(-1);

        if (extend.getCurrentPosition() < this.pos) {
            extend.setPower(0);
        }
    }

    public void toHalf() {
        updatePos();
        extend.setTargetPosition(1000);
        extend.setPower(1);

        if (extend.getCurrentPosition() > this.pos) {
            extend.setPower(0);
        }
    }

    public void toFull() {
        updatePos();
        extend.setTargetPosition(2000);
        extend.setPower(1);

        if (extend.getCurrentPosition() > this.pos) {
            extend.setPower(0);
        }
    }

    // Util //
    public double getPos() {
        return pos;
    }

    public void updatePos() {
        this.pos = extend.getCurrentPosition();
    }

    public void resetEncoder(){
        extend.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

    // Init + Start //
    public void init() {
        resetEncoder();
        extend.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        extend.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        pos = extend.getCurrentPosition();
    }

    public void start() {}

}
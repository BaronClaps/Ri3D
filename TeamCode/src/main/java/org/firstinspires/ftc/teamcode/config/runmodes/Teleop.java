package org.firstinspires.ftc.teamcode.config.runmodes;

import org.firstinspires.ftc.teamcode.config.subsystem.ClawSubsystem;
import org.firstinspires.ftc.teamcode.config.subsystem.ExtendSubsystem;
import org.firstinspires.ftc.teamcode.config.subsystem.LiftSubsystem;
import org.firstinspires.ftc.teamcode.config.pedroPathing.follower.Follower;
import org.firstinspires.ftc.teamcode.config.pedroPathing.localization.Pose;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Teleop {

    private ClawSubsystem claw;
    private ClawSubsystem.ClawState clawState;
    private LiftSubsystem lift;
    private ExtendSubsystem extend;


    private Follower follower;
    private Pose startPose;

    private Telemetry telemetry;

    private Gamepad gamepad1;
    private Gamepad gamepad2;
    private Gamepad currentGamepad1 = new Gamepad();
    private Gamepad currentGamepad2 = new Gamepad();
    private Gamepad previousGamepad1 = new Gamepad();
    private Gamepad previousGamepad2 = new Gamepad();

    public double speed = 0.75;
    private boolean fieldCentric = true;


    public Teleop(HardwareMap hardwareMap, Telemetry telemetry, Follower follower, Pose startPose,  boolean fieldCentric, Gamepad gamepad1, Gamepad gamepad2) {
        claw = new ClawSubsystem(hardwareMap, clawState);
        lift = new LiftSubsystem(hardwareMap);
        extend = new ExtendSubsystem(hardwareMap);

        this.follower = follower;
        this.startPose = startPose;

        this.fieldCentric = fieldCentric;
        this.telemetry = telemetry;
        this.gamepad1 = gamepad1;
        this.gamepad2 = gamepad2;
    }

    public void init() {
        claw.init();
        lift.init();
        extend.init();
    }

    public void update() {
        previousGamepad1.copy(currentGamepad1);
        previousGamepad2.copy(currentGamepad2);
        currentGamepad1.copy(gamepad1);
        currentGamepad2.copy(gamepad2);

        if (gamepad1.right_bumper)
            speed = 1;
        else if (gamepad1.left_bumper)
            speed = 0.25;
        else
            speed = 0.75;

        if (gamepad2.left_trigger > .5)
            lift.manualLift(50, true);

        if (gamepad2.right_trigger > .5)
            lift.manualLift(50, false);

        if (gamepad2.left_bumper)
            extend.manualExtend(50, true);

        if (gamepad2.right_bumper)
            extend.manualExtend(50, false);

        if (currentGamepad2.a && !previousGamepad2.a)
            claw.switchClawState();

        follower.setTeleOpMovementVectors(-gamepad1.left_stick_y * speed, -gamepad1.left_stick_x * speed, -gamepad1.right_stick_x * speed, !fieldCentric);
        follower.update();

        telemetry.addData("X", follower.getPose().getX());
        telemetry.addData("Y", follower.getPose().getY());
        telemetry.addData("Heading", Math.toDegrees(follower.getPose().getHeading()));

        telemetry.addData("Estimated Lift Pos", lift.getLiftPos());
        telemetry.addData("Actual Lift Pos", lift.lift.getCurrentPosition());
        telemetry.addData("Estimated Extend Pos", extend.getExtendPos());
        telemetry.addData("Actual Extend Pos", extend.extend.getCurrentPosition());
        telemetry.addData("Claw State", clawState);
        telemetry.update();
    }

    public void start() {
        claw.start();
        lift.start();
        extend.start();
        follower.setPose(startPose);
        follower.startTeleopDrive();
    }

}
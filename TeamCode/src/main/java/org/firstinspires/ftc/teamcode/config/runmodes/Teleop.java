package org.firstinspires.ftc.teamcode.config.runmodes;

import org.firstinspires.ftc.teamcode.config.subsystem.ServoSubsystem;
import org.firstinspires.ftc.teamcode.config.subsystem.VisionSubsystem;
import org.firstinspires.ftc.teamcode.config.pedroPathing.follower.Follower;
import org.firstinspires.ftc.teamcode.config.pedroPathing.localization.Pose;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Teleop {

    private ServoSubsystem servoSubsystem;
    private VisionSubsystem visionSubsystem;

    private Follower follower;
    private Pose startPose;

    private Telemetry telemetry;

    private Gamepad gamepad1;
    private Gamepad gamepad2;
    private Gamepad currentGamepad1 = new Gamepad();
    private Gamepad currentGamepad2 = new Gamepad();
    private Gamepad previousGamepad1 = new Gamepad();
    private Gamepad previousGamepad2 = new Gamepad();

    private DcMotorEx leftFront;
    private DcMotorEx leftRear;
    private DcMotorEx rightFront;
    private DcMotorEx rightRear;

    public double speed = 0.75;

    private boolean toggleRumble = false;
    private boolean fieldCentric = true;

    Gamepad.RumbleEffect rumbleEffect = new Gamepad.RumbleEffect.Builder()
            .addStep(0.0, 1.0, 500)  //  Rumble right motor 100% for 500 mSec
            .addStep(0.0, 0.0, 300)  //  Pause for 300 mSec
            .addStep(1.0, 0.0, 250)  //  Rumble left motor 100% for 250 mSec
            .addStep(0.0, 0.0, 250)  //  Pause for 250 mSec
            .addStep(1.0, 0.0, 250)  //  Rumble left motor 100% for 250 mSec
            .build();

    Gamepad.RumbleEffect RLB = new Gamepad.RumbleEffect.Builder()
            .addStep(0.0, 1.0, 500)  //  Rumble right motor 100% for 500 mSec
            .addStep(1.0, 0.0, 500)  //  Rumble left motor 100% for 500 mSec
            .addStep(1.0, 1.0, 1000)  //  Rumble both motors 100% for 1000 mSec
            .build();

    Gamepad.LedEffect rgbEffect = new Gamepad.LedEffect.Builder()
            .addStep(1, 0, 0, 250) // Show red for 250ms
            .addStep(0, 1, 0, 250) // Show green for 250ms
            .addStep(0, 0, 1, 250) // Show blue for 250ms
            .addStep(1, 1, 1, 250) // Show white for 250ms
            .build();


    public Teleop(HardwareMap hardwareMap, Telemetry telemetry, Follower follower, Pose startPose,  boolean fieldCentric, Gamepad gamepad1, Gamepad gamepad2) {
        servoSubsystem = new ServoSubsystem(hardwareMap);
        visionSubsystem = new VisionSubsystem(hardwareMap, leftFront, rightFront, leftRear, rightRear, telemetry);

        this.follower = follower;
        this.startPose = startPose;

        this.fieldCentric = fieldCentric;
        this.telemetry = telemetry;
        this.gamepad1 = gamepad1;
        this.gamepad2 = gamepad2;

        leftFront = hardwareMap.get(DcMotorEx.class, "leftFront");
        leftRear = hardwareMap.get(DcMotorEx.class, "leftRear");
        rightRear = hardwareMap.get(DcMotorEx.class, "rightRear");
        rightFront = hardwareMap.get(DcMotorEx.class, "rightFront");

        leftFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftRear.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightRear.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    public void init() {
        servoSubsystem.init();
    }

    public void update() {
        previousGamepad1.copy(currentGamepad1);
        previousGamepad2.copy(currentGamepad2);
        currentGamepad1.copy(gamepad1);
        currentGamepad2.copy(gamepad2);

        if (gamepad1.right_trigger > 0.5)
            speed = 1;
        else if (gamepad1.left_trigger > 0.5)
            speed = 0.25;
        else
            speed = 0.75;

        follower.setTeleOpMovementVectors(-gamepad1.left_stick_y * speed, -gamepad1.left_stick_x * speed, -gamepad1.right_stick_x * speed, !fieldCentric);

        if (follower.getPose().getY() >= 5)
            gamepad1.setLedColor(1,0,0,Gamepad.LED_DURATION_CONTINUOUS);

        if (follower.getPose().getY() <= -5)
            gamepad1.setLedColor(0,1,0,Gamepad.LED_DURATION_CONTINUOUS);

        if (follower.getPose().getX() >= 5)
            gamepad1.setLedColor(0,0,1,Gamepad.LED_DURATION_CONTINUOUS);

        if (follower.getPose().getX() <= -5)
            gamepad1.setLedColor(0,1,1,Gamepad.LED_DURATION_CONTINUOUS);

        if (gamepad1.a)
            follower.setPose(new Pose (follower.getPose().getX(), follower.getPose().getY(), 0));

        if (gamepad1.dpad_down)
            follower.setPose(new Pose (0, 0, Math.toRadians(180)));

        if (gamepad1.dpad_up)
            follower.setPose(new Pose (0, 0, 0));

        if (gamepad1.left_bumper)
            gamepad1.setLedColor(0.25, 0.25, 0.25, 500);

        if (gamepad1.right_bumper)
            gamepad1.runLedEffect(rgbEffect);

        if (currentGamepad1.x && !previousGamepad1.x)
            toggleRumble = !toggleRumble;

        if (toggleRumble)
            gamepad1.runRumbleEffect(RLB);

        follower.update();

        telemetry.addData("X", follower.getPose().getX());
        telemetry.addData("Y", follower.getPose().getY());
        telemetry.addData("Heading", Math.toDegrees(follower.getPose().getHeading()));

        telemetry.addData("l.x", -gamepad1.left_stick_x);
        telemetry.addData("l.y", -gamepad1.left_stick_y);
        telemetry.addData("r.x", -gamepad1.right_stick_x);

        telemetry.addData("driveVector", follower.driveVector);
        telemetry.update();
    }

    public void start() {
        servoSubsystem.start();
        follower.setPose(startPose);
        follower.startTeleopDrive();
    }

}
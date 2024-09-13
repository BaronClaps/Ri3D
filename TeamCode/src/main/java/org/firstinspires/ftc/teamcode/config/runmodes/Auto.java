package org.firstinspires.ftc.teamcode.config.runmodes;

import static org.firstinspires.ftc.teamcode.config.util.FieldConstants.*;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.config.pedroPathing.pathGeneration.BezierCurve;
import org.firstinspires.ftc.teamcode.config.pedroPathing.pathGeneration.BezierPoint;
import org.firstinspires.ftc.teamcode.config.pedroPathing.pathGeneration.PathChain;
import org.firstinspires.ftc.teamcode.config.subsystem.BoxSubsystem;
import org.firstinspires.ftc.teamcode.config.subsystem.ClawSubsystem;
import org.firstinspires.ftc.teamcode.config.subsystem.ExtendSubsystem;
import org.firstinspires.ftc.teamcode.config.subsystem.IntakeSubsystem;
import org.firstinspires.ftc.teamcode.config.subsystem.LiftSubsystem;
import org.firstinspires.ftc.teamcode.config.subsystem.VisionSubsystem;
import org.firstinspires.ftc.teamcode.config.pedroPathing.follower.Follower;
import org.firstinspires.ftc.teamcode.config.pedroPathing.localization.Pose;
import org.firstinspires.ftc.teamcode.config.pedroPathing.pathGeneration.BezierLine;
import org.firstinspires.ftc.teamcode.config.pedroPathing.pathGeneration.Path;
import org.firstinspires.ftc.teamcode.config.pedroPathing.pathGeneration.PathBuilder;
import org.firstinspires.ftc.teamcode.config.pedroPathing.pathGeneration.Point;
import org.firstinspires.ftc.teamcode.config.util.action.ActionStorage;

public class Auto {

    private RobotStart startLocation;

    private ClawSubsystem claw;
    private ClawSubsystem.ClawState clawState;
    private LiftSubsystem lift;
    private ExtendSubsystem extend;
    private IntakeSubsystem intake;
    private IntakeSubsystem.IntakeSpinState intakeSpinState;
    private IntakeSubsystem.IntakePivotState intakePivotState;
    private BoxSubsystem box;
    private BoxSubsystem.BoxState boxState;

    public Follower follower;
    public Telemetry telemetry;

    private boolean isBlue;
    private boolean isBucket;

    public Path preload, element1, score1, element2, score2, element3, score3, park;
    private Pose startPose, preloadPose, element1Pose, element2Pose, element3Pose, elementScorePose, parkPose;

    public Auto(HardwareMap hardwareMap, Telemetry telemetry, Follower follower, boolean isBlue, boolean isBucket) {
        claw = new ClawSubsystem(hardwareMap, clawState);
        lift = new LiftSubsystem(hardwareMap, telemetry);
        extend = new ExtendSubsystem(hardwareMap, telemetry);
        intake = new IntakeSubsystem(hardwareMap, intakeSpinState, intakePivotState);
        box = new BoxSubsystem(hardwareMap, boxState);

        this.follower = follower;
        this.telemetry = telemetry;

        this.isBlue = isBlue;
        this.isBucket = isBucket;

        startLocation = isBlue ? (isBucket ? RobotStart.BLUE_BUCKET : RobotStart.BLUE_OBSERVATION) : (isBucket ? RobotStart.RED_BUCKET : RobotStart.RED_OBSERVATION);
    }

    public void init() {
        createPoses();
        buildPaths();
    }

    public void init_loop() {}

    public void start() {
        claw.start();
        lift.start();
        extend.start();
        intake.start();
        box.start();
    }

    public void update() {
        follower.update();
    }

    public void createPoses() {
        switch (startLocation) {
            case BLUE_BUCKET:
                startPose = blueBucketStart;
                //parkPose = blueBucketPark;
                break;

            case BLUE_OBSERVATION:
                startPose = blueObservationStart;
                //parkPose = blueObservationPark;
                break;

            case RED_BUCKET:
                startPose = redBucketStart;
                //parkPose = redBucketPark;
                break;

            case RED_OBSERVATION:
                startPose = redObservationStart;
                //parkPose = redObservationPark;
                break;
        }

    }

    public void buildPaths() {
        follower.setStartingPose(startPose);

        preload = new Path(new BezierLine(new Point(startPose), new Point(preloadPose)));
        preload.setLinearHeadingInterpolation(startPose.getHeading(), preloadPose.getHeading());

        park = new Path(new BezierCurve(new Point(elementScorePose), new Point(elementScorePose.getX(), 108, Point.CARTESIAN), new Point(parkPose.getX(), 108, Point.CARTESIAN)));
        park.setLinearHeadingInterpolation(elementScorePose.getHeading(), parkPose.getHeading());
    }



    public void followPath(Path path) {
        follower.followPath(path);
    }

    public void followPath(Path path, boolean holdEnd) {
        follower.followPath(path, holdEnd);
    }

    public void followPath(PathChain pathChain) {
        follower.followPath(pathChain);
    }

    public void followPath(PathChain pathChain, boolean holdEnd) {
        follower.followPath(pathChain, holdEnd);
    }

    public void holdPoint(BezierPoint point, double heading) {
        follower.holdPoint(point, heading);
    }

    public boolean pathNotBusy() {
        return !follower.isBusy();
    }

    public double getX() {
        return follower.getPose().getX();
    }

    public double getY() {
        return follower.getPose().getY();
    }

    public double getHeading() {
        return follower.getPose().getHeading();
    }
}
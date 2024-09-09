package org.firstinspires.ftc.teamcode.config.runmodes;

import static org.firstinspires.ftc.teamcode.config.util.FieldConstants.*;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.config.pedroPathing.pathGeneration.BezierCurve;
import org.firstinspires.ftc.teamcode.config.pedroPathing.pathGeneration.BezierPoint;
import org.firstinspires.ftc.teamcode.config.pedroPathing.pathGeneration.PathChain;
import org.firstinspires.ftc.teamcode.config.subsystem.ClawSubsystem;
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

    public VisionSubsystem vision;
    public ClawSubsystem claw;
    public ClawSubsystem.ClawState clawState;
    public Follower follower;

    public ActionStorage actionStorage;

    private boolean isBlue;
    private boolean isBucket;

    public Path purple;
    public PathChain yellow, park;
    private Pose startPose, purplePose, yellowPose, parkPose;

    public Auto(HardwareMap hardwareMap, Telemetry telemetry, Follower follower, boolean isBlue, boolean isBucket) {

        claw = new ClawSubsystem(hardwareMap, clawState);
        vision = new VisionSubsystem(hardwareMap, telemetry);

        this.follower = follower;

        this.isBlue = isBlue;
        this.isBucket = isBucket;

        startLocation = isBlue ? (isBucket ? RobotStart.BLUE_BUCKET : RobotStart.BLUE_OBSERVATION) : (isBucket ? RobotStart.RED_BUCKET : RobotStart.RED_OBSERVATION);

    }

    public void init() {
        createPoses();
        buildPaths();
    }

    public void init_loop() {

    }

    public void start() {

    }

    public void update() {
        follower.update();
    }

    public void createPoses() {
        switch (startLocation) {
            case BLUE_BUCKET:
                startPose = blueBucketStart;
                parkPose = blueBucketPark;
                break;

            case BLUE_OBSERVATION:
                startPose = blueObservationStart;
                parkPose = blueObservationPark;
                break;

            case RED_BUCKET:
                startPose = redBucketStart;
                parkPose = redBucketPark;
                break;

            case RED_OBSERVATION:
                startPose = redObservationStart;
                parkPose = redObservationPark;
                break;
        }

    }

    public void buildPaths() {
        follower.setStartingPose(startPose);

        purple = new Path(new BezierLine(new Point(startPose), new Point(purplePose)));
        purple.setLinearHeadingInterpolation(startPose.getHeading(), purplePose.getHeading());

        if (!isBucket) {
            yellow = new PathBuilder()
                    .addPath(new Path(new BezierLine(new Point(purplePose), new Point(yellowPose))))
                    .setConstantHeadingInterpolation(yellowPose.getHeading())
                    .build();
        } else {
            yellow = new PathBuilder()
                    .addPath(new Path(new BezierLine(new Point(purplePose), new Point(yellowPose))))
                    .setConstantHeadingInterpolation(yellowPose.getHeading())
                    .build();
        }

        park = new PathBuilder()
                .addPath(new Path(new BezierCurve(new Point(yellowPose), new Point(yellowPose.getX(), 108, Point.CARTESIAN), new Point(parkPose.getX(), 108, Point.CARTESIAN))))
                .setLinearHeadingInterpolation(yellowPose.getHeading(), parkPose.getHeading())
                .build();
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
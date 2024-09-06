package org.firstinspires.ftc.teamcode.config.runmodes;

import static org.firstinspires.ftc.teamcode.config.util.FieldConstants.*;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.config.pedroPathing.pathGeneration.BezierCurve;
import org.firstinspires.ftc.teamcode.config.pedroPathing.pathGeneration.BezierPoint;
import org.firstinspires.ftc.teamcode.config.pedroPathing.pathGeneration.PathChain;
import org.firstinspires.ftc.teamcode.config.subsystem.ServoSubsystem;
import org.firstinspires.ftc.teamcode.config.subsystem.VisionSubsystem;
import org.firstinspires.ftc.teamcode.config.pedroPathing.follower.Follower;
import org.firstinspires.ftc.teamcode.config.pedroPathing.localization.Pose;
import org.firstinspires.ftc.teamcode.config.pedroPathing.pathGeneration.BezierLine;
import org.firstinspires.ftc.teamcode.config.pedroPathing.pathGeneration.Path;
import org.firstinspires.ftc.teamcode.config.pedroPathing.pathGeneration.PathBuilder;
import org.firstinspires.ftc.teamcode.config.pedroPathing.pathGeneration.Point;
import org.firstinspires.ftc.teamcode.config.util.action.ActionStorage;
import org.firstinspires.ftc.teamcode.config.vision.Navigation;

public class Auto {

    public Navigation nav;
    private RobotStart startLocation;

    public VisionSubsystem vision;
    public ServoSubsystem servo;
    public Follower follower;

    public ActionStorage actionStorage;

    private boolean isBlue;
    private boolean isClose;

    public Path purple;
    public PathChain yellow, park;
    private Pose startPose, purplePose, yellowPose, parkPose;

    public Auto(HardwareMap hardwareMap, Telemetry telemetry, Follower follower, boolean isBlue, boolean isClose) {

        servo = new ServoSubsystem(hardwareMap);
        vision = new VisionSubsystem(hardwareMap, telemetry);

        this.follower = follower;


        this.isBlue = isBlue;
        this.isClose = isClose;

        startLocation = isBlue ? (isClose ? RobotStart.BLUE_CLOSE : RobotStart.BLUE_FAR) : (isClose ? RobotStart.RED_CLOSE : RobotStart.RED_FAR);

    }

    public void init() {
        nav = Navigation.LEFT;
    }

    public void init_loop() {
        vision.navUpdate(nav);
    }

    public void start() {
        vision.navUpdate(nav);
        createPoses();
        buildPaths();

    }

    public void update() {
        follower.update();
    }

    public void createPoses() {
        switch (startLocation) {
            case BLUE_CLOSE:
                startPose = blueCloseStart;
                parkPose = blueClosePark;

                switch(nav) {
                    case LEFT:
                        purplePose = blueCloseLeftPurple;
                        yellowPose = blueLeftYellow;
                    case CENTER:
                        purplePose = blueCloseCenterPurple;
                        yellowPose = blueCenterYellow;
                    case RIGHT:
                        purplePose = blueCloseRightPurple;
                        yellowPose = blueRightYellow;
                }
                break;

            case BLUE_FAR:
                startPose = blueFarStart;
                parkPose = blueFarPark;

                switch(nav) {
                    case LEFT:
                        purplePose = blueFarLeftPurple;
                        yellowPose = blueLeftYellow;
                    case CENTER:
                        purplePose = blueFarCenterPurple;
                        yellowPose = blueCenterYellow;
                    case RIGHT:
                        purplePose = blueFarRightPurple;
                        yellowPose = blueRightYellow;
                }
                break;

            case RED_CLOSE:
                startPose = redCloseStart;
                parkPose = redClosePark;

                switch(nav) {
                    case LEFT:
                        purplePose = redCloseLeftPurple;
                        yellowPose = redLeftYellow;
                    case CENTER:
                        purplePose = redCloseCenterPurple;
                        yellowPose = redCenterYellow;
                    case RIGHT:
                        purplePose = redCloseRightPurple;
                        yellowPose = redRightYellow;
                }
                break;

            case RED_FAR:
                startPose = redFarStart;
                parkPose = redFarPark;

                switch(nav) {
                    case LEFT:
                        purplePose = redFarLeftPurple;
                        yellowPose = redLeftYellow;
                    case CENTER:
                        purplePose = redFarCenterPurple;
                        yellowPose = redCenterYellow;
                    case RIGHT:
                        purplePose = redFarRightPurple;
                        yellowPose = redRightYellow;
                }
                break;
        }

    }

    public void buildPaths() {
        follower.setStartingPose(startPose);

        purple = new Path(new BezierLine(new Point(startPose), new Point(purplePose)));
        purple.setLinearHeadingInterpolation(startPose.getHeading(), purplePose.getHeading());

        if (!isClose) {
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
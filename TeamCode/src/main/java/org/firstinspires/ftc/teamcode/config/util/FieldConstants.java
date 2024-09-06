package org.firstinspires.ftc.teamcode.config.util;

import org.firstinspires.ftc.teamcode.config.pedroPathing.localization.Pose;

public class FieldConstants {

    public enum RobotStart {
        BLUE_CLOSE,
        BLUE_FAR,
        RED_CLOSE,
        RED_FAR
    }

    public static final Pose blueCloseStart = new Pose(8.5, 84, 0);
    public static final Pose blueFarStart= new Pose(8.5, 36, 0);
    public static final Pose redCloseStart = new Pose(144-8.5, 84, 0);
    public static final Pose redFarStart = new Pose(144-8.5, 36, 0);

    public static final Pose blueCloseLeftPurple = new Pose(-43, 30+72, Math.toRadians(270)); //51
    public static final Pose blueCloseCenterPurple = new Pose(-30+72, 22+72, Math.toRadians(270));
    public static final Pose blueCloseRightPurple = new Pose(-36+72, 8+72, Math.toRadians(270));
    public static final Pose blueFarLeftPurple = new Pose();
    public static final Pose blueFarCenterPurple = new Pose();
    public static final Pose blueFarRightPurple = new Pose();
    public static final Pose redCloseLeftPurple = new Pose(144-43, 30+72, Math.toRadians(270)); //51
    public static final Pose redCloseCenterPurple = new Pose(144-(-30+72), 22+72, Math.toRadians(270));
    public static final Pose redCloseRightPurple = new Pose(144-(-36+72), 8+72, Math.toRadians(270));
    public static final Pose redFarLeftPurple = new Pose();
    public static final Pose redFarCenterPurple = new Pose();
    public static final Pose redFarRightPurple = new Pose();

    public static final Pose blueLeftYellow = new Pose(30, 123.5, Math.toRadians(270)); //41
    public static final Pose blueCenterYellow = new Pose(36, 51.5+72, Math.toRadians(270));
    public static final Pose blueRightYellow = new Pose(42, 51.5+72, Math.toRadians(270));
    public static final Pose redLeftYellow = new Pose(144-42, 51.5+72, Math.toRadians(270));
    public static final Pose redCenterYellow = new Pose(144-36, 51.5+72, Math.toRadians(270));
    public static final Pose redRightYellow = new Pose(144-30, 123.5, Math.toRadians(270));

    public static final Pose blueClosePark = new Pose(12, 132, Math.toRadians(270));
    public static final Pose blueFarPark = new Pose(60, 132, Math.toRadians(270));
    public static final Pose redClosePark = new Pose(144-12, 132, Math.toRadians(270));
    public static final Pose redFarPark = new Pose(144-60, 132, Math.toRadians(270));

}
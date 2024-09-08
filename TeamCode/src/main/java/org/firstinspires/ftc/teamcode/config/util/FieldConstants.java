package org.firstinspires.ftc.teamcode.config.util;

import org.firstinspires.ftc.teamcode.config.pedroPathing.localization.Pose;

public class FieldConstants {

    public enum RobotStart {
        BLUE_BUCKET,
        BLUE_OBSERVATION,
        RED_BUCKET,
        RED_OBSERVATION
    }

    public static final Pose blueBucketStart = new Pose(8.5, 84, 0);
    public static final Pose blueObservationStart = new Pose(8.5, 36, 0);
    public static final Pose redBucketStart = new Pose(144-8.5, 84, 0);
    public static final Pose redObservationStart = new Pose(144-8.5, 36, 0);

    public static final Pose blueBucketLeftPurple = new Pose(-43, 30+72, Math.toRadians(270)); //51

    public static final Pose blueBucketPark = new Pose(12, 132, Math.toRadians(270));
    public static final Pose blueObservationPark = new Pose(60, 132, Math.toRadians(270));
    public static final Pose redBucketPark = new Pose(144-12, 132, Math.toRadians(270));
    public static final Pose redObservationPark = new Pose(144-60, 132, Math.toRadians(270));

}
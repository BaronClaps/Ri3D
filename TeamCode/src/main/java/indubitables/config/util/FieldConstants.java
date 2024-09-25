package indubitables.config.util;

import com.acmerobotics.dashboard.config.Config;

import indubitables.config.pedroPathing.localization.Pose;

@Config
public class FieldConstants {

    public enum RobotStart {
        BLUE_BUCKET,
        BLUE_OBSERVATION,
        RED_BUCKET,
        RED_OBSERVATION
    }

    public static final Pose blueBucketStartPose = new Pose(8, 79.5, Math.toRadians(180));
    public static final Pose blueObservationStartPose = new Pose(8, 36, Math.toRadians(180));
    public static final Pose redBucketStartPose = new Pose(144-8, 79.5, 0);
    public static final Pose redObservationStartPose = new Pose(144-8, 36, 0);

    // Blue Preload Poses
    public static final Pose blueBucketPreloadPose = new Pose(34.5, 79.5, Math.toRadians(180));

    // Blue Bucket Sample Poses
    public static final Pose blueBucketLeftSamplePose = new Pose(34.75, 113.5, Math.toRadians(66));
    public static final Pose blueBucketLeftSampleControlPose = new Pose(32, 108);
    public static final Pose blueBucketMidSamplePose = new Pose(33, 125.5, Math.toRadians(73));
    public static final Pose blueBucketMidSampleControlPose = new Pose(47.5, 110);
    public static final Pose blueBucketRightSamplePose = new Pose(33, 133, Math.toRadians(74));
    public static final Pose blueBucketRightSampleControlPose = new Pose(46, 101);

    public static final Pose blueBucketScorePose = new Pose(16, 128, Math.toRadians(-45));

    public static final Pose blueBucketParkPose = new Pose(65, 97.75, Math.toRadians(90));
    public static final Pose blueBucketParkControlPose = new Pose(60.25, 123.5);


}
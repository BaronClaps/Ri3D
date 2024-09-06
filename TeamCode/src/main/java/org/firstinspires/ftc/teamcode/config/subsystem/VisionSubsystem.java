package org.firstinspires.ftc.teamcode.config.subsystem;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.config.vision.Navigation;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagPoseFtc;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;
import java.util.List;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.hardware.camera.controls.ExposureControl;
import org.firstinspires.ftc.robotcore.external.hardware.camera.controls.GainControl;
import java.util.concurrent.TimeUnit;
import org.firstinspires.ftc.robotcore.external.Telemetry;

public class VisionSubsystem {
    private static final boolean USE_WEBCAM = true;  // true for webcam, false for phone camera
    private AprilTagProcessor aprilTag;
    private VisionPortal visionPortal;
    private ServoSubsystem servoSubsystem;
    private boolean targetFound = false;
    private AprilTagDetection desiredTag = null;
    private AprilTagPoseFtc tagPose;
    private static final double currentPosition = 0.5;
    private double targetPosition;
    private DcMotor leftFrontDrive;
    private DcMotor rightFrontDrive;
    private DcMotor leftBackDrive;
    private DcMotor rightBackDrive;
    private Telemetry telemetry;
    private double turn = 0;
    private WebcamName webcam1;
    private double x;


    public VisionSubsystem(HardwareMap hardwareMap, DcMotor leftFrontDrive, DcMotor rightFrontDrive, DcMotor leftBackDrive, DcMotor rightBackDrive, Telemetry telemetry) {
        servoSubsystem = new ServoSubsystem(hardwareMap);
        this.leftFrontDrive = leftFrontDrive;
        this.rightFrontDrive = rightFrontDrive;
        this.leftBackDrive = leftBackDrive;
        this.rightBackDrive = rightBackDrive;
        this.telemetry = telemetry;
        webcam1 = hardwareMap.get(WebcamName.class, "webcam1");
    }

    public VisionSubsystem(HardwareMap hardwareMap, Telemetry telemetry) {
        servoSubsystem = new ServoSubsystem(hardwareMap);
        this.telemetry = telemetry;
        webcam1 = hardwareMap.get(WebcamName.class, "webcam1");
    }

    public void navUpdate(Navigation navigation) {
        detectProp(x);

        if (x < 100) {
            navigation = Navigation.LEFT;
        } else if (x > 100 && x < 200) {
            navigation = Navigation.CENTER;
        } else if (x > 200) {
            navigation = Navigation.RIGHT;
        }
    }

    public void detectProp(double x) {
        x = 10;
    }

    public void aprilInit() {
        initAprilTag();
        //setManualExposure(100, 1);
    }

    public void aprilUpdate() {
        servoAlignToTag(-1);
        servoSubsystem.sPos(targetPosition);
        telemetryAprilTag();
    }

    public void initAprilTag() {
        // Create the AprilTag processor by using a builder.
        aprilTag = new AprilTagProcessor.Builder().build();
        aprilTag.setDecimation(3);
        visionPortal = new VisionPortal.Builder()
                .setCamera(webcam1)
                .addProcessor(aprilTag)
                .build();
    }

    public void setManualExposure(int exposureMS, int gain) {
        if (visionPortal == null) {
            return;
        }

        ExposureControl exposureControl = visionPortal.getCameraControl(ExposureControl.class);
        if (exposureControl.getMode() != ExposureControl.Mode.Manual) {
            exposureControl.setMode(ExposureControl.Mode.Manual);
        }
        exposureControl.setExposure((long) exposureMS, TimeUnit.MILLISECONDS);
        GainControl gainControl = visionPortal.getCameraControl(GainControl.class);
        gainControl.setGain(gain);

    }

    public void servoAlignToTag(int DESIRED_TAG_ID) {
        List<AprilTagDetection> currentDetections = aprilTag.getDetections();

        for (AprilTagDetection detection : currentDetections) {
            if (detection.metadata != null) {
                if ((DESIRED_TAG_ID < 0) || (detection.id == DESIRED_TAG_ID)) {
                    targetFound = true;
                    desiredTag = detection;
                    tagPose = detection.ftcPose;
                    break;
                }
            }
        }

        if (targetFound) {
            if (Math.abs(tagPose.bearing) >= 0.1) {
                targetPosition = currentPosition + (tagPose.bearing * ((double) 1 / 450)); // 1/112.5
            } else {
                targetPosition = currentPosition;
            }
        } else {
            targetPosition = currentPosition;
        }
    }

    public void robotAlignToTag(int DESIRED_TAG_ID) {
        List<AprilTagDetection> currentDetections = aprilTag.getDetections();

        for (AprilTagDetection detection : currentDetections) {
            if (detection.metadata != null) {
                if ((DESIRED_TAG_ID < 0) || (detection.id == DESIRED_TAG_ID)) {
                    targetFound = true;
                    desiredTag = detection;
                    tagPose = detection.ftcPose;
                    break;
                }
            }
        }

        if (targetFound) {
            if (Math.abs(tagPose.bearing) >= 0.1) {
                double headingError = desiredTag.ftcPose.bearing;
                turn = Range.clip(headingError * 0.025, -0.65, 0.65);
            } else {
                turn = 0;
            }
        } else {
            turn = 0;
        }

        aprilUpdate();
    }

    public void turnRobot(double yaw) {
        double leftFrontPower = -yaw;
        double rightFrontPower = yaw;
        double leftBackPower = -yaw;
        double rightBackPower = yaw;

        // Normalize wheel powers to be less than 1.0
        double max = Math.max(Math.abs(leftFrontPower), Math.abs(rightFrontPower));
        max = Math.max(max, Math.abs(leftBackPower));
        max = Math.max(max, Math.abs(rightBackPower));

        if (max > 1.0) {
            leftFrontPower /= max;
            rightFrontPower /= max;
            leftBackPower /= max;
            rightBackPower /= max;
        }

        // Send powers to the wheels.
        leftFrontDrive.setPower(leftFrontPower);
        rightFrontDrive.setPower(rightFrontPower);
        leftBackDrive.setPower(leftBackPower);
        rightBackDrive.setPower(rightBackPower);
    }

    private void telemetryAprilTag() {

        List<AprilTagDetection> currentDetections = aprilTag.getDetections();
        telemetry.addData("# AprilTags Detected", currentDetections.size());

        // Step through the list of detections and display info for each one.
        for (AprilTagDetection detection : currentDetections) {
            if (detection.metadata != null) {
                telemetry.addLine(String.format("\n==== (ID %d) %s", detection.id, detection.metadata.name));
                telemetry.addLine(String.format("XYZ %6.1f %6.1f %6.1f  (inch)", detection.ftcPose.x, detection.ftcPose.y, detection.ftcPose.z));
                telemetry.addLine(String.format("PRY %6.1f %6.1f %6.1f  (deg)", detection.ftcPose.pitch, detection.ftcPose.roll, detection.ftcPose.yaw));
                telemetry.addLine(String.format("RBE %6.1f %6.1f %6.1f  (inch, deg, deg)", detection.ftcPose.range, detection.ftcPose.bearing, detection.ftcPose.elevation));
            } else {
                telemetry.addLine(String.format("\n==== (ID %d) Unknown", detection.id));
                telemetry.addLine(String.format("Center %6.0f %6.0f   (pixels)", detection.center.x, detection.center.y));
            }
        }   // end for() loop

        // Add "key" information to telemetry
        telemetry.addLine("\nkey:\nXYZ = X (Right), Y (Forward), Z (Up) dist.");
        telemetry.addLine("PRY = Pitch, Roll & Yaw (XYZ Rotation)");
        telemetry.addLine("RBE = Range, Bearing & Elevation");

    }   // end method telemetryAprilTag()

}
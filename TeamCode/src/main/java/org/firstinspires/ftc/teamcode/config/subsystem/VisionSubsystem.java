package org.firstinspires.ftc.teamcode.config.subsystem;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.config.pedroPathing.localization.Pose;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagPoseFtc;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;
import java.util.List;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.hardware.camera.controls.ExposureControl;
import org.firstinspires.ftc.robotcore.external.hardware.camera.controls.GainControl;
import java.util.concurrent.TimeUnit;
import org.firstinspires.ftc.robotcore.external.Telemetry;

public class VisionSubsystem {

    public enum limelightState {
        yellow,
        red,
        blue,
        aprilTag,
        none
    }

    private Telemetry telemetry;

    public limelightState state;
    private Limelight3A limelight;
    private LLResult result;

    private int pipeline = 0;
    private double x = 0;
    private double y = 0;


    public VisionSubsystem(HardwareMap hardwareMap, Telemetry telemetry) {
        this.telemetry = telemetry;
        limelight = hardwareMap.get(Limelight3A.class, "limelight");
        limelight.setPollRateHz(100); // per sec
    }

    public void start() {
        limelight.start();
        limelight.pipelineSwitch(pipeline);
    }

    public void switchPipeline(limelightState state) {
        switch (state) {
            case yellow:
                pipeline = 0;
                break;
            case red:
                pipeline = 1;
                break;
            case blue:
                pipeline = 2;
                break;
            case aprilTag:
                pipeline = 3;
                break;
        }

        limelight.pipelineSwitch(pipeline);

        if (state == limelightState.none) {
            limelight.stop();
        }
    }

    /*public Pose aprilTagPose(Pose currentPose, int id) {
        switchPipeline(limelightState.aprilTag);
        List<LLResultTypes.FiducialResult> fiducials = result.getFiducialResults();
        for (LLResultTypes.FiducialResult fiducial : fiducials) {
            if (fiducial.getFiducialId() == id) {
                x = detection.getTargetXDegrees();
                y = detection.getTargetYDegrees();
                double StrafeDistance_3D = fiducial.getRobotPoseTargetSpace().getY();
            }
        }
        return new Pose(x, y, heading);
    }*/

    public void update() {
        result = limelight.getLatestResult();
    }

}
package org.firstinspires.ftc.teamcode.config.subsystem;

import static org.firstinspires.ftc.teamcode.config.util.RobotConstants.lInit;
import static org.firstinspires.ftc.teamcode.config.util.RobotConstants.lStart;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class LiftScoreSubsystem {

    private DcMotor lift;

    public LiftScoreSubsystem(HardwareMap hardwareMap) {
        lift = hardwareMap.get(DcMotor.class, "lift");
    }

    public void liftPow(double liftPow) {
        lift.setPower(liftPow);
    }

    public void init() {
        lift.setPower(lInit);
    }

    public void start() {
        lift.setPower(lStart);
    }

}
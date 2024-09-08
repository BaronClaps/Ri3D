package org.firstinspires.ftc.teamcode.config.subsystem;

import static org.firstinspires.ftc.teamcode.config.util.RobotConstants.sInit;
import static org.firstinspires.ftc.teamcode.config.util.RobotConstants.sStart;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;


public class IntakeSubsystem {

    private Servo intakeWheels;

    public IntakeSubsystem(HardwareMap hardwareMap) {
        intakeWheels = hardwareMap.get(Servo.class, "intakeWheels");
    }

    public void intakeWheelPos(double intakeWheelPos) {
        intakeWheels.setPosition(intakeWheelPos);
    }

    public void init() {
    }

    public void start() {
    }

}
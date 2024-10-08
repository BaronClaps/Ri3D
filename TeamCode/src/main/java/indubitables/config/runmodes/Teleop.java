package indubitables.config.runmodes;

import static indubitables.config.util.RobotConstants.intakeSpinInPwr;
import static indubitables.config.util.RobotConstants.intakeSpinOutPwr;

import indubitables.config.subsystem.BoxSubsystem;
import indubitables.config.subsystem.ClawSubsystem;
import indubitables.config.subsystem.ExtendSubsystem;
import indubitables.config.subsystem.IntakeSubsystem;
import indubitables.config.subsystem.LiftSubsystem;
import indubitables.config.pedroPathing.follower.Follower;
import indubitables.config.pedroPathing.localization.Pose;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import indubitables.config.util.action.Action;
import indubitables.config.util.action.Actions;
import indubitables.config.util.action.ParallelAction;
import indubitables.config.util.action.RunAction;
import indubitables.config.util.action.SequentialAction;
import indubitables.config.util.action.SleepAction;

public class Teleop {

    private ClawSubsystem claw;
    private ClawSubsystem.ClawState clawState;
    private LiftSubsystem lift;
    private ExtendSubsystem extend;
    private IntakeSubsystem intake;
    private IntakeSubsystem.IntakeSpinState intakeSpinState;
    private IntakeSubsystem.IntakePivotState intakePivotState;
    private BoxSubsystem box;
    private BoxSubsystem.BoxState boxState;

    public RunAction stopDrive, startDrive;

    private Follower follower;
    private Pose startPose;

    private Telemetry telemetry;

    private Gamepad gamepad1;
    private Gamepad gamepad2;
    private Gamepad currentGamepad1 = new Gamepad();
    private Gamepad currentGamepad2 = new Gamepad();
    private Gamepad previousGamepad1 = new Gamepad();
    private Gamepad previousGamepad2 = new Gamepad();

    public double speed = 0.75;
    private boolean fieldCentric = true;


    public Teleop(HardwareMap hardwareMap, Telemetry telemetry, Follower follower, Pose startPose,  boolean fieldCentric, Gamepad gamepad1, Gamepad gamepad2) {
        claw = new ClawSubsystem(hardwareMap, clawState);
        lift = new LiftSubsystem(hardwareMap, telemetry);
        extend = new ExtendSubsystem(hardwareMap, telemetry);
        intake = new IntakeSubsystem(hardwareMap, intakeSpinState, intakePivotState);
        box = new BoxSubsystem(hardwareMap, boxState);


        this.follower = follower;
        this.startPose = startPose;

        this.fieldCentric = fieldCentric;
        this.telemetry = telemetry;
        this.gamepad1 = gamepad1;
        this.gamepad2 = gamepad2;

        stopDrive = new RunAction(this::stopDrive);
        startDrive = new RunAction(this::startDrive);
    }

    public void init() {
        claw.init();
        lift.init();
        extend.init();
        intake.init();
        box.init();
    }

    public void update() {
        previousGamepad1.copy(currentGamepad1);
        previousGamepad2.copy(currentGamepad2);
        currentGamepad1.copy(gamepad1);
        currentGamepad2.copy(gamepad2);

        if (gamepad1.right_bumper)
            speed = 1;
        else if (gamepad1.left_bumper)
            speed = 0.25;
        else
            speed = 0.75;

        lift.manual(gamepad2.right_trigger - gamepad2.left_trigger);

        if (gamepad2.left_bumper)
            extend.manual(-1);
        else if (gamepad2.right_bumper)
            extend.manual(1);
        else
            extend.manual(0);

        if (currentGamepad2.a && !previousGamepad2.a)
            claw.switchState();

        if (gamepad2.b) {
            intake.spin.setPower(intakeSpinInPwr);
            intake.setSpinState(IntakeSubsystem.IntakeSpinState.IN, true);
        } else if (gamepad2.dpad_down) {
            intake.spin.setPower(intakeSpinOutPwr);
            intake.setSpinState(IntakeSubsystem.IntakeSpinState.OUT, true);
        } else {
            intake.setSpinState(IntakeSubsystem.IntakeSpinState.STOP, true);
            intake.spin.setPower(0);
        }

        if (currentGamepad1.a && !previousGamepad1.a)
            box.switchState();

        if (currentGamepad2.x && !previousGamepad2.x)
            intake.switchPivotState();

        if (currentGamepad1.b && !previousGamepad1.b)
            intake.setPivotState(IntakeSubsystem.IntakePivotState.TRANSFER);

        if(currentGamepad2.y && !previousGamepad2.y)
            Actions.runBlocking(transfer());

        follower.setTeleOpMovementVectors(-gamepad1.left_stick_y * speed, -gamepad1.left_stick_x * speed, -gamepad1.right_stick_x * speed, !fieldCentric);
        follower.update();

        telemetry.addData("X", follower.getPose().getX());
        telemetry.addData("Y", follower.getPose().getY());
        telemetry.addData("Heading", Math.toDegrees(follower.getPose().getHeading()));

        telemetry.addData("Actual Lift Pos", lift.lift.getCurrentPosition());
        telemetry.addData("Actual Extend Pos", extend.extend.getCurrentPosition());
        telemetry.addData("Claw State", clawState);
        telemetry.addData("Intake Spin State", intakeSpinState);
        telemetry.addData("Intake Pivot State", intakePivotState);
        telemetry.addData("Box State", boxState);
        telemetry.update();
    }

    public void start() {
        claw.start();
        lift.start();
        extend.start();
        intake.start();
        box.start();
        follower.setPose(startPose);
        follower.startTeleopDrive();
    }

    private void startDrive() {
        follower.startTeleopDrive();
    }

    private void stopDrive(){
        follower.breakFollowing();
    }

    public Action transfer() {
        return new SequentialAction(
                stopDrive,
                intake.spinStop,
                new ParallelAction(
                        intake.pivotTransfer,
                        //extend.toZero,
                        //lift.toZero,
                        box.toTransfer),
                intake.spinOut,
                new SleepAction(1),
                intake.spinIn,
                intake.spinStop,
                startDrive
        );
    }

}
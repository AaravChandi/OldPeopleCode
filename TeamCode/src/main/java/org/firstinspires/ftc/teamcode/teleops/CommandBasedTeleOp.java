package org.firstinspires.ftc.teamcode.teleops;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.BaseRobot;
import org.firstinspires.ftc.teamcode.shplib.commands.RunCommand;
import org.firstinspires.ftc.teamcode.shplib.commands.Trigger;
import org.firstinspires.ftc.teamcode.shplib.utility.Clock;
import org.firstinspires.ftc.teamcode.subsystems.ClawSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.LiftSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.ShooterSubsystem;

@TeleOp
public class CommandBasedTeleOp extends BaseRobot {
    private double debounce;
    private double driveBias = 0.4;

    @Override
    public void init() {
        super.init();

        // Default command runs when no other commands are scheduled for the subsystem
        drive.setDefaultCommand(
                new RunCommand(
                        () -> drive.mecanum(driveBias*-gamepad1.left_stick_y, driveBias*-gamepad1.left_stick_x, driveBias*-gamepad1.right_stick_x)
                )
        );
        /*drive.setDefaultCommand(
                new RunCommand(
                        () -> drive.enablePositionPID()
                )
        );*/
    }

    @Override
    public void start() {
        super.start();

        debounce = Clock.now();
        // Add anything that needs to be run a single time when the OpMode starts
    }

    @Override
    public void loop() {
        // Allows CommandScheduler.run() to be called - DO NOT DELETE!
        super.loop();
        new Trigger(gamepad1.y,
                new RunCommand(( () -> {drive.imu.initialize(hardwareMap,
                        RevHubOrientationOnRobot.LogoFacingDirection.RIGHT,
                        RevHubOrientationOnRobot.UsbFacingDirection.UP);})));
        //drive.setDriveBias(arm.getDriveBias());
        new Trigger(gamepad1.a,
                new RunCommand(( () -> {
                    if (!Clock.hasElapsed(debounce, 0.5)) return;
                    debounce = Clock.now();
                    if (claw.getState()=="PICKUP")
                        claw.setState(ClawSubsystem.State.DROP);
                    else
                        claw.setState(ClawSubsystem.State.PICKUP);



                })));

        new Trigger(gamepad1.right_bumper,
                new RunCommand(( () -> {
                    shooter.setState(ShooterSubsystem.State.SHOOT);

                })));
        new Trigger(!gamepad1.right_bumper,
                new RunCommand(( () -> {
                    shooter.setState(ShooterSubsystem.State.LOAD);

                })));
        new Trigger(gamepad1.dpad_up,
                new RunCommand(( () -> {
                    lift.setState(LiftSubsystem.State.LIFTING);

                })));
        new Trigger(gamepad1.dpad_down,
                new RunCommand(( () -> {
                    lift.setState(LiftSubsystem.State.RELEASING);

                })));
        new Trigger(!gamepad1.dpad_down && !gamepad1.dpad_up,
                new RunCommand(( () -> {
                    lift.setState(LiftSubsystem.State.PAUSED);

                })));
        new Trigger(gamepad1.dpad_left || gamepad1.dpad_right,
                new RunCommand(( () -> {
                    lift.setState(LiftSubsystem.State.CARRY);

                })));
        new Trigger(gamepad1.right_trigger>0.5,
                new RunCommand(( () -> {
                    driveBias = 0.8;

                })));
        new Trigger(gamepad1.right_trigger<0.5,
                new RunCommand(( () -> {
                    driveBias = 0.4;

                })));


        /*new Trigger(gamepad1.right_trigger>0.5, new RunCommand(( () -> intake.setState(IntakeSubsystem.State.INTAKING))));
        new Trigger(gamepad1.left_trigger>0.5, new RunCommand(( () -> intake.setState(IntakeSubsystem.State.OUTTAKING))));
        new Trigger(gamepad1.left_trigger<0.5 && gamepad1.right_trigger<0.5, new RunCommand(( () -> intake.setState(IntakeSubsystem.State.PAUSED))));*/


        /*

        new Trigger(gamepad1.x, new RunCommand(() -> {
            arm.setState(ArmSubsystem.State.STACK);
        }));

        new Trigger(gamepad1.dpad_up, new RunCommand(() -> {
            arm.setState(ArmSubsystem.State.HIGH);
        }));

        new Trigger(gamepad1.dpad_right, new RunCommand(() -> {
            arm.setState(ArmSubsystem.State.MIDDLE);
        }));

        new Trigger(gamepad1.dpad_left, new RunCommand(() -> {
            arm.setState(ArmSubsystem.State.LOW);
        }));

        new Trigger(gamepad1.dpad_down, new RunCommand(() -> {
            arm.setState(ArmSubsystem.State.BOTTOM);
        }));

         */
    }
}

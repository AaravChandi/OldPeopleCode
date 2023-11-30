package org.firstinspires.ftc.teamcode.teleops;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.BaseRobot;
import org.firstinspires.ftc.teamcode.commands.DropConeCommand;
import org.firstinspires.ftc.teamcode.shplib.commands.CommandScheduler;
import org.firstinspires.ftc.teamcode.shplib.commands.RunCommand;
import org.firstinspires.ftc.teamcode.shplib.commands.Trigger;
import org.firstinspires.ftc.teamcode.shplib.commands.WaitCommand;
import org.firstinspires.ftc.teamcode.shplib.utility.Clock;
import org.firstinspires.ftc.teamcode.subsystems.ArmSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.IntakeSubsystem;

@TeleOp
public class CommandBasedTeleOp extends BaseRobot {
    private double debounce;

    @Override
    public void init() {
        super.init();

        // Default command runs when no other commands are scheduled for the subsystem
        drive.setDefaultCommand(
                new RunCommand(
                        () -> drive.mecanum(-gamepad1.left_stick_y, -gamepad1.left_stick_x, -gamepad1.right_stick_x)
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


        new Trigger(gamepad1.right_trigger>0.5, new RunCommand(( () -> intake.setState(IntakeSubsystem.State.INTAKING))));
        new Trigger(gamepad1.left_trigger>0.5, new RunCommand(( () -> intake.setState(IntakeSubsystem.State.OUTTAKING))));


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

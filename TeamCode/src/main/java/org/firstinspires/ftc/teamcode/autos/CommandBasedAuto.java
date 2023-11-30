package org.firstinspires.ftc.teamcode.autos;

import static org.firstinspires.ftc.teamcode.Constants.Drive.kMotorNames;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.BaseRobot;
import org.firstinspires.ftc.teamcode.commands.DriveByCommand;
import org.firstinspires.ftc.teamcode.commands.DriveCommand;
import org.firstinspires.ftc.teamcode.commands.DropConeCommand;
import org.firstinspires.ftc.teamcode.commands.EncoderStraightDriveCommand;
import org.firstinspires.ftc.teamcode.commands.EncoderTurnDriveCommand;
import org.firstinspires.ftc.teamcode.commands.RaiseToHighCommand;
import org.firstinspires.ftc.teamcode.shplib.commands.CommandScheduler;
import org.firstinspires.ftc.teamcode.shplib.commands.RunCommand;
import org.firstinspires.ftc.teamcode.shplib.commands.WaitCommand;
import org.firstinspires.ftc.teamcode.subsystems.ArmSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.DriveSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.IntakeSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.VisionSubsystem;

@Autonomous(preselectTeleOp = "CommandBasedTeleOp")
public class CommandBasedAuto extends BaseRobot {
//    SHPMecanumAutoDrive autoDrive;
    DriveSubsystem drive;
    VisionSubsystem vision;

    public int location;

    @Override
    public void init() {
        super.init();
//        PositionPID pid = new PositionPID(0.15);
//        pid.setErrorTolerance(100);
//        autoDrive = new SHPMecanumAutoDrive(hardwareMap, kMotorNames, 0.15, 0.0, 0.0);
//        autoDrive.enableFF(new FFController(0.01));
        drive = new DriveSubsystem(hardwareMap);
        vision = new VisionSubsystem(hardwareMap);
        location = 0;

    }

    @Override
    public void start() {
        super.start();

        CommandScheduler myCommand = CommandScheduler.getInstance();

       myCommand.scheduleCommand(
                new RunCommand(() -> {
                    location = vision.getLocation();
                })
                        .then(new EncoderStraightDriveCommand(drive,"forward",10))
                        .then(new RunCommand(() -> {
                            if (location == 1) {
                                myCommand.scheduleCommand(new EncoderTurnDriveCommand(drive,"ccw", 90));
                            }
                            if (location == 3) {
                                myCommand.scheduleCommand(new EncoderTurnDriveCommand(drive,"cw", 90));
                            }
                            /*
                            if (location == 1) {
                                myCommand.scheduleCommand(new DriveCommand(drive,0, 0, -0.5, 2));
                            }
                            if (location == 2) {
                                myCommand.scheduleCommand(new DriveCommand(drive,0, 0, 0, 2));
                            }
                            if (location == 3) {
                                myCommand.scheduleCommand(new DriveCommand(drive,0, 0, 0.5, 2));
                            }*/
                        }))
                        .then( new RunCommand(() -> {
                            intake.setState(IntakeSubsystem.State.OUTTAKING);
                        }))
                        .then(new WaitCommand(0.5))
                        .then( new RunCommand(() -> {
                            intake.setState(IntakeSubsystem.State.PAUSED);
                        }))

//                        .then(new DriveByCommand(autoDrive, 3000, 3000, -3000, -3000))
//                        .then(new DriveByCommand(autoDrive, -2000, 2000, 2000, -2000))

        );
    }

    @Override
    public void loop() {
        super.loop();
//        telemetry.addData("auto drive at setpoint", autoDrive.atPositionSetpoint() ? "true" : "false");
    }
}

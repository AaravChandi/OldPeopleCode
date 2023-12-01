package org.firstinspires.ftc.teamcode.commands;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.shplib.commands.Command;
import org.firstinspires.ftc.teamcode.shplib.utility.Clock;
import org.firstinspires.ftc.teamcode.subsystems.DriveSubsystem;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.robotcore.util.Range;

public class EncoderTurnDriveCommand extends Command {
    private final DriveSubsystem drive;
    private double startTime;
    private double degrees;
    private double fullDegrees;
    public double initialHeading;
    double leftY; double leftX; double rightX;

    double targetX, targetY;
    String direction;
    public static double TICKS_PER_REV = 8192;
    public static double WHEEL_RADIUS = 1; // in

    public EncoderTurnDriveCommand(DriveSubsystem drive, String direction, double degrees) {
        // You MUST call the parent class constructor and pass through any subsystems you use
        super(drive);
        this.drive = drive;
        this.leftY = 0;
        this.leftX = 0;
        this.degrees = degrees;
        this.direction = direction;
        //initialHeading = drive.imu.getYaw(AngleUnit.DEGREES);
        //TODO: switch cc/cw input to based on +/- of degrees where positive is clockwise

        if(direction.equals("cw")) {
            this.rightX = 0.01;
            //fullDegrees = drive.imu.getYaw(AngleUnit.DEGREES) + degrees;
        }
        else {
            this.rightX = -0.01;
            //fullDegrees = -drive.imu.getYaw(AngleUnit.DEGREES) - degrees;
        }


    }


    // Called once when the command is initially schedule

    public void init() {

    }

    // Called repeatedly until isFinished() returns true
    //@Override

    double percentage = 0;
    double difference = 0;

    //@Override
    public void execute(){
        if (direction.equals("cw")) {
            drive.mecanum(0, 0, 0.02);
        }
        else {
            drive.mecanum(0, 0, -0.02);
        }
    }

    // Called once after isFinished() returns true
    @Override
    public void end() {
        drive.mecanum(0,0,0);
    }

    // Specifies whether or not the command has finished
    // Returning true causes execute() to be called once
    //TODO: IMU IS WEIRD VALUES ARHGILSHDG BIOSAalifuhdlafbohub
    @Override
    public boolean isFinished() {
        if(direction.equals("cw")) //>0 means turning CW
            return drive.imu.getYaw(AngleUnit.DEGREES)>fullDegrees*0.98;
        else //turning CCW
            return drive.imu.getYaw(AngleUnit.DEGREES)<fullDegrees*1.02;

    }
}

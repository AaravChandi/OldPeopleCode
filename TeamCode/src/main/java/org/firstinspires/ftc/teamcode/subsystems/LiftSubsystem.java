package org.firstinspires.ftc.teamcode.subsystems;

import static org.firstinspires.ftc.teamcode.Constants.Intake.kIntakeName;
import static org.firstinspires.ftc.teamcode.Constants.Intake.kLiftName;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.shplib.commands.Subsystem;
import org.firstinspires.ftc.teamcode.shplib.hardware.SHPMotor;

public class LiftSubsystem extends Subsystem {
    // Declare devices
    // Example:
    private final SHPMotor lift;

    public enum State {
        // Define states
        // Example:
        // ENABLED, DISABLED
        INTAKING,
        OUTTAKING,
        CARRY
    }

    private State state;

    public LiftSubsystem(HardwareMap hardwareMap) {
        // Initialize devices
        // Example:
        lift = new SHPMotor(hardwareMap, kLiftName);
        lift.resetEncoder();
        // Set initial state
        // Example:
        setState(State.INTAKING);
    }

    public void setState(State state) {
        this.state = state;
    }

    public String getState(){ return state.toString(); }
    // Add control methods
    // Example:
    // private void setPower(double power) { motor.setPower(power); }

    @Override
    public void periodic(Telemetry telemetry) {
        // Add logging if needed
        // Example:
        telemetry.addData("Intake State: ", getState());

        // Handle states
        // Example:
        switch (state) {
            case INTAKING:
                lift.setPosition(0);
                break;
            case OUTTAKING:
                lift.setPosition(1000);
                break;
            case CARRY:
                lift.setPosition(500);
                break;
        }

        // OR

//        if (state == State.ENABLED) {
//            setPower(1.0);
//        } else if (state == State.DISABLED) {
//            setPower(0.0);
//        }
    }
}
// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import static frc.robot.Constants.*;
import static frc.robot.subsystems.elevator.ElevatorConstants.*;
import static frc.robot.subsystems.drive.DriveConstants.*;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import edu.wpi.first.wpilibj2.command.sysid.SysIdRoutine;
import frc.robot.subsystems.drive.Drive;
import frc.robot.subsystems.drive.GyroIO;
import frc.robot.subsystems.drive.ModuleIO;
import frc.robot.subsystems.drive.ModuleIOReal;
import frc.robot.subsystems.drive.ModuleIOSim;
import frc.robot.subsystems.elevator.Elevator;
import frc.robot.subsystems.elevator.ElevatorIOReal;
import frc.robot.subsystems.elevator.ElevatorIOSim;

public class RobotContainer {
    private final Drive drive;
    private final Elevator elevator;

    private final CommandXboxController controller = new CommandXboxController(driverControllerPort);

    public RobotContainer() {
        switch (currentMode) {
            case REAL:
                // drive = new Drive(
                //     new GyroIO() {},
                //     new ModuleIOSim(),
                //     new ModuleIOSim(),
                //     new ModuleIOSim(),
                //     new ModuleIOSim()
                // );
                drive = new Drive(
                    new GyroIO() {},
                    new ModuleIOReal(frontLeftDriveId, frontLeftTurnId),
                    new ModuleIOReal(frontRightDriveId, frontRightDriveId),
                    new ModuleIOReal(backLeftDriveId, backLeftTurnId),
                    new ModuleIOReal(backRightDriveId, backRightTurnId)
                );
                elevator = new Elevator(new ElevatorIOReal(leftMotorId, rightMotorId));
                break;
            default:
            case SIM:
            case REPLAY:
                drive = new Drive(
                    new GyroIO() {},
                    new ModuleIOSim(),
                    new ModuleIOSim(),
                    new ModuleIOSim(),
                    new ModuleIOSim()
                );
                elevator = new Elevator(new ElevatorIOSim());
                break;
        }

        configureBindings();
    }

    private void configureBindings() {
        drive.setDefaultCommand(
            new RunCommand(
                () -> drive.setSpeeds(
                    new ChassisSpeeds(
                        MathUtil.applyDeadband(-controller.getLeftY(), 0.1) * speed, 
                        MathUtil.applyDeadband(-controller.getLeftX(), 0.1) * speed, 
                        MathUtil.applyDeadband(-controller.getRightX(), 0.1) * speed
                    )
                ),
                drive
            )
        );

        elevator.setDefaultCommand(
            new RunCommand(
                () -> elevator.setPosition(controller.povDown().getAsBoolean() ? 1.2 : (controller.povLeft().getAsBoolean() ? 0.9 : (controller.povUp().getAsBoolean() ? 0.6 : (controller.povRight().getAsBoolean() ? 0.3 : 0.0)))),
                elevator
            )
        );

        // controller.a().whileTrue(new RunCommand(() -> elevator.reset(), elevator));

        // elevator.getCurrentCommand().cancel();
 
        // controller.a().whileTrue(elevator.sysIdRoutine());
    }

    public Command getAutonomousCommand() {
        return Commands.print("No autonomous command configured");
    }
}

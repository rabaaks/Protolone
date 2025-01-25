// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import static frc.robot.Constants.*;
import static frc.robot.subsystems.elevator.ElevatorConstants.startingHeight;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import edu.wpi.first.wpilibj2.command.sysid.SysIdRoutine;
import frc.robot.subsystems.elevator.Elevator;
import frc.robot.subsystems.elevator.ElevatorIOReal;
import frc.robot.subsystems.elevator.ElevatorIOSim;

public class RobotContainer {
    private final Elevator elevator;

    private final CommandXboxController controller = new CommandXboxController(driverControllerPort);

    public RobotContainer() {
        switch (currentMode) {
            case REAL:
                elevator = new Elevator(new ElevatorIOReal());
                break;
            default:
            case SIM:
            case REPLAY:
        }

        configureBindings();
    }

    private void configureBindings() {


        elevator.setDefaultCommand(
             new RunCommand(
                 () -> elevator.setPosition(controller.povUp().getAsBoolean() ? 0.3 : 0.0),
                 elevator
             )
         );
    }

    public Command getAutonomousCommand() {
        return Commands.print("No autonomous command configured");
    }
}

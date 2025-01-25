// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.subsystems.Carriage;
import edu.wpi.first.wpilibj2.command.Command;

/** An example command that uses an example subsystem. */
public class CarriageCommand extends Command {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private Carriage m_Carriage;
  private double m_speed;


  /**
   * Creates a new ExampleCommand.
   *
   * @param subsystem The subsystem used by this command.
   */
  public CarriageCommand(Carriage subsystem, double speed) {
    m_Carriage = subsystem;
    m_speed = speed;
    // Use addRequirements() here to declare subsystem dependencies.
  }

  public void initialize() {
    Carriage.setspeed(m_speed); // Set the shooter speed
}

// Called repeatedly while the command is active (not needed for velocity control)
  @Override
  public void execute() {
  }

// Called once when the command ends or is interrupted
  @Override
  public void end(boolean interrupted) {
    // Optionally stop the shooter or set it to a safe state if interrupted
    Carriage.setspeed(0.0);
}

}

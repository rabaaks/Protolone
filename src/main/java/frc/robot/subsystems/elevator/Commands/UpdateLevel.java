// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.elevator.Commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.elevator.Elevator;

/* You should consider using the more terse Command factories API instead https://docs.wpilib.org/en/stable/docs/software/commandbased/organizing-command-based.html#defining-commands */
public class UpdateLevel extends Command {
  private final Elevator elevator;
  private int level;
  private boolean upOrDown;

  public UpdateLevel(int level, boolean upOrDown) {
    elevator = Elevator.getInstance();
    addRequirements(elevator);
    this.level = level;
    this.upOrDown = upOrDown;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if (upOrDown) {
      level++;
    }
    else {
      level--;
    }
    level %= 5;
    if (level == 0) {
      elevator.setPosition(0);
    }
    else if (level == 1) {
      elevator.setPosition(0.4);
    }
    else if (level == 2) {
      elevator.setPosition(0.6);
    }
    else if (level == 3) {
      elevator.setPosition(1);
    }
    else if (level == 4) {
      elevator.setPosition(1.4);
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}

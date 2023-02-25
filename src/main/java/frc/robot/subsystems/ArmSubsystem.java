// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.CANSparkMax.IdleMode;
import frc.robot.Constants.ArmConstants;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ArmSubsystem extends SubsystemBase {
  // The motors for the arm and the intake
  CANSparkMax arm = new CANSparkMax(7, MotorType.kBrushless);
  CANSparkMax intake = new CANSparkMax(6, MotorType.kBrushless);

  // Creates a new ArmSubsystem
  public ArmSubsystem() {
    /*
     * Set the arm and intake to brake mode to help hold position.
     * If either one is reversed, change that here too. Arm out is defined
     * as positive, arm in is negative.
     */
    arm.setInverted(false);
    arm.setIdleMode(IdleMode.kBrake);
    arm.setSmartCurrentLimit(ArmConstants.ARM_CURRENT_LIMIT_A);
    intake.setInverted(false);
    intake.setIdleMode(IdleMode.kBrake);
  }

  /**
   * Set the arm output power. Positive is out, negative is in.
   * 
   * @param percent
   */
  public void setArmMotor(double percent) {
    arm.set(percent);
    SmartDashboard.putNumber("arm power (%)", percent);
    SmartDashboard.putNumber("arm motor current (amps)", arm.getOutputCurrent());
    SmartDashboard.putNumber("arm motor temperature (C)", arm.getMotorTemperature());
  }

  /**
   * Set the arm output power.
   * 
   * @param percent desired speed
   * @param amps current limit
   */
  public void setIntakeMotor(double percent, int amps) {
    intake.set(percent);
    intake.setSmartCurrentLimit(amps);
    SmartDashboard.putNumber("intake power (%)", percent);
    SmartDashboard.putNumber("intake motor current (amps)", intake.getOutputCurrent());
    SmartDashboard.putNumber("intake motor temperature (C)", intake.getMotorTemperature());
  }

  public void raiseArm() {
    setArmMotor(ArmConstants.ARM_OUTPUT_POWER);
  }

  public void lowerArm() {
    setArmMotor(-ArmConstants.ARM_OUTPUT_POWER);
  }

  public void idleArm() {
    setArmMotor(0.0);
  }

  public void cubeInConeOut() {
    ArmConstants.intakePower = ArmConstants.INTAKE_OUTPUT_POWER;
    ArmConstants.intakeAmps = ArmConstants.INTAKE_CURRENT_LIMIT_A;
    ArmConstants.lastGamePiece = ArmConstants.CUBE;
    setIntakeMotor(ArmConstants.intakePower, ArmConstants.intakeAmps);
  }

  public void coneInCubeOut() {
    ArmConstants.intakePower = -ArmConstants.INTAKE_OUTPUT_POWER;
    ArmConstants.intakeAmps = ArmConstants.INTAKE_CURRENT_LIMIT_A;
    ArmConstants.lastGamePiece = ArmConstants.CONE;
    setIntakeMotor(ArmConstants.intakePower, ArmConstants.intakeAmps);
  }

  public void holdPiece() {
    if (ArmConstants.lastGamePiece == ArmConstants.CUBE) {
      ArmConstants.intakePower = ArmConstants.INTAKE_HOLD_POWER;
      ArmConstants.intakeAmps = ArmConstants.INTAKE_HOLD_CURRENT_LIMIT_A;
    } else if (ArmConstants.lastGamePiece == ArmConstants.CONE) {
      ArmConstants.intakePower = -ArmConstants.INTAKE_HOLD_POWER;
      ArmConstants.intakeAmps = ArmConstants.INTAKE_HOLD_CURRENT_LIMIT_A;
    } else {
      ArmConstants.intakePower = 0.0;
      ArmConstants.intakeAmps = 0;
    }
    setIntakeMotor(ArmConstants.intakePower, ArmConstants.intakeAmps);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }
}
/* Copyright (c) 2017 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
//import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
//import com.qualcomm.robotcore.util.Range;
//import com.qualcomm.robotcore.hardware.Servo;


/**
 * This file contains an minimal example of a Linear "OpMode". An OpMode is a 'program' that runs in either
 * the autonomous or the teleop period of an FTC match. The names of OpModes appear on the menu
 * of the FTC Driver Station. When an selection is made from the menu, the corresponding OpMode
 * class is instantiated on the Robot Controller and executed.
 *
 * This particular OpMode just executes a basic Tank Drive Teleop for a two wheeled robot
 * It includes all the skeletal structure that all linear OpModes contain.
 *
 * Use Android Studios to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this opmode to the Driver Station OpMode list
 */

@TeleOp(name="Basic: MecanumDrive", group="Linear Opmode")
@Disabled
public class MecanumDrive extends LinearOpMode {

    // Declare OpMode members.
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor frontLeft;
    private DcMotor frontRight;
    private DcMotor backRight;
    private DcMotor backLeft;
    private DcMotor linearLift;
    private DcMotor backFlipper;
    private DcMotor blockFeederLeft;
    private DcMotor blockFeederRight;

    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();
        Servo leftServo;
        Servo rightServo;
        Servo backServo;
        double leftServoPosition;
        double rightServoPosition;
        double backServoPosition;
        // Initialize the hardware variables. Note that the strings used here as parameters
        // to 'get' must correspond to the names assigned during the robot configuration
        // step (using the FTC Robot Controller app on the phone).
        frontLeft  = hardwareMap.get(DcMotor.class, "frontLeft");
        frontRight = hardwareMap.get(DcMotor.class, "frontRight");
        backRight  = hardwareMap.get(DcMotor.class, "backRight");
        backLeft = hardwareMap.get(DcMotor.class, "backLeft");
        linearLift = hardwareMap.get(DcMotor.class, "linearLift");
        blockFeederLeft = hardwareMap.get(DcMotor.class, "blockFeederLeft");
        blockFeederRight = hardwareMap.get(DcMotor.class, "blockFeederRight");
        backFlipper = hardwareMap.get(DcMotor.class, "backFlipper");
        leftServo = hardwareMap.get(Servo.class, "leftServo");
        rightServo = hardwareMap.get(Servo.class, "rightServo");
        backServo = hardwareMap.get(Servo.class, "backServo");


// I left off here right after setting the servos to the hardware map


        // Most robots need the motor on one side to be reversed to drive forward
        // Reverse the motor that runs backwards when connected directly to the battery
        frontLeft.setDirection(DcMotor.Direction.REVERSE);
        backLeft.setDirection(DcMotor.Direction.REVERSE);
        frontRight.setDirection(DcMotor.Direction.FORWARD);
        backRight.setDirection(DcMotor.Direction.FORWARD);
        linearLift.setDirection((DcMotor.Direction.FORWARD));
        backFlipper.setDirection((DcMotor.Direction.REVERSE));
        blockFeederLeft.setDirection((DcMotor.Direction.FORWARD));
        blockFeederRight.setDirection((DcMotor.Direction.FORWARD));
        leftServoPosition = 0.0;
        rightServoPosition = 0.8;
        backServoPosition = 0.0;
        leftServo.setPosition(leftServoPosition);
        rightServo.setPosition(rightServoPosition);
        backServo.setPosition(backServoPosition);

        // Wait for the game to start (driver presses PLAY)
        waitForStart();
        runtime.reset();

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {

            // Setup a variable for each drive wheel to save power level for telemetry
            double frontLeftPower;
            double frontRightPower;
            double backLeftPower;
            double backRightPower;
            double linearLiftPower;
            double backFlipperPower;
            double blockFeederPowerIn;
            double blockFeederPowerOut;

            frontLeftPower = gamepad1.left_stick_y + 0.6*gamepad1.right_stick_x + gamepad1.left_trigger + -gamepad1.right_trigger;
            frontRightPower = gamepad1.left_stick_y + 0.61*-gamepad1.right_stick_x + -gamepad1.left_trigger + gamepad1.right_trigger;
            backLeftPower = gamepad1.left_stick_y + 0.5*-gamepad1.right_stick_x + gamepad1.left_trigger + -gamepad1.right_trigger;
            backRightPower = gamepad1.left_stick_y + 0.67*gamepad1.right_stick_x + -gamepad1.left_trigger + gamepad1.right_trigger;
            linearLiftPower = 0.5*gamepad2.left_stick_y;
            backFlipperPower = 0.3*gamepad2.right_stick_y;
            blockFeederPowerIn = 0.5*gamepad2.right_trigger;
            blockFeederPowerOut = 0.5*-gamepad2.left_trigger;

            if (gamepad2.left_bumper) {
                rightServoPosition = 0.0;
                rightServo.setPosition(rightServoPosition);
            }
            else {
                rightServoPosition = 0.8;
                rightServo.setPosition(rightServoPosition);
            }

            if (gamepad2.right_bumper) {
                leftServoPosition = 0.5;
                leftServo.setPosition(leftServoPosition);
            }
            else {
                leftServoPosition = 0.0;
                leftServo.setPosition(leftServoPosition);
            }

            if (gamepad2.y) {
                backServoPosition = 0.5;
                backServo.setPosition(backServoPosition);
            }
            else{
                backServoPosition = 0.0;
                backServo.setPosition(backServoPosition);
            }

            if (gamepad2.a) {
                backServoPosition = 0.0;
                backServo.setPosition(backServoPosition);
            }

//
            // Send calculated power to wheels

            frontRight.setPower(frontRightPower);
            backLeft.setPower(backLeftPower);
            backRight.setPower(backRightPower);
            frontLeft.setPower(frontLeftPower);
            linearLift.setPower(0.5*linearLiftPower);
            backFlipper.setPower(-backFlipperPower);
            blockFeederLeft.setPower(blockFeederPowerIn + blockFeederPowerOut);
            blockFeederRight.setPower(blockFeederPowerIn + blockFeederPowerOut);

            // Show the elapsed game time and wheel power.
            telemetry.addData("Status", "Run Time: " + runtime.toString());
            telemetry.addData("Motors", "left (%.2f), right (%.2f)", frontLeftPower, frontRightPower, backLeftPower, backRightPower);
            telemetry.update();
        }
    }
}

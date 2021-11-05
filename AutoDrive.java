import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import java.lang.annotation.Target;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@Autonomous(name="LangAutor", group="Iterative Opmode")

public class AutoDrive extends LinearOpMode {
    
    private DcMotor leftFront = null;
    private DcMotor rightFront = null;
    private DcMotor leftBack = null;
    private DcMotor rightBack = null;
    

    
    private DcMotor carousel; //Carousel motor
    private DcMotor linearSlide; //Carousel motor
    Servo  gripper; // Gripper servo
  
  //Convert from the counts per revolution of the encoder to counts per inch
  static final double HD_COUNTS_PER_REV = 28;
  static final double DRIVE_GEAR_REDUCTION = 20.15293;
  static final double WHEEL_CIRCUMFERENCE_MM = 90 * Math.PI;
  static final double DRIVE_COUNTS_PER_MM = (HD_COUNTS_PER_REV * DRIVE_GEAR_REDUCTION) / WHEEL_CIRCUMFERENCE_MM;
  static final double DRIVE_COUNTS_PER_IN = DRIVE_COUNTS_PER_MM * 25.4;
  
  int rFTarget;
  int lFTarget;
  int rBTarget;
  int lBTarget;
  
  //@Override
  
  public void action(int rBDis, int lBDis,int rFDis,int lFDis, float pow ){ //back and front are swapped
      // FIRST ACTION
      
      // Create target positions
      rFTarget = rightFront.getCurrentPosition() + (int)(rFDis * DRIVE_COUNTS_PER_IN);
      lFTarget = leftFront.getCurrentPosition() + (int)(lFDis * DRIVE_COUNTS_PER_IN);
      rBTarget = rightBack.getCurrentPosition() + (int)(rBDis * DRIVE_COUNTS_PER_IN);
      lBTarget = leftBack.getCurrentPosition() + (int)(lBDis * DRIVE_COUNTS_PER_IN);
      
      // set target position
      rightFront.setTargetPosition(rFTarget);
      leftFront.setTargetPosition(lFTarget);
      rightBack.setTargetPosition(rBTarget);
      leftBack.setTargetPosition(lBTarget);
      
      
      
      //switch to run to position mode
      leftFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
      rightFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
      leftBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);
      rightBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);
      
      //run to position at the desiginated power
      leftFront.setPower(pow);
      rightFront.setPower(pow);
      leftBack.setPower(pow);
      rightBack.setPower(pow);
      
      // wait until both motors are no longer busy running to position
      while (opModeIsActive() && (leftFront.isBusy() || rightFront.isBusy() ||leftBack.isBusy() || rightBack.isBusy())) {
      }
      
      // set motor power back to 0 
      leftFront.setPower(0);
      rightFront.setPower(0);
      leftBack.setPower(0);
      rightBack.setPower(0);
  }
  
  public void runOpMode() {
        
        leftFront  = hardwareMap.get(DcMotor.class, "lf");
        leftFront.setDirection(DcMotor.Direction.REVERSE);
        rightFront  = hardwareMap.get(DcMotor.class, "rf");
        leftBack = hardwareMap.get(DcMotor.class, "lb");
        rightBack = hardwareMap.get(DcMotor.class, "rb");
        leftBack.setDirection(DcMotor.Direction.REVERSE);
    
        carousel = hardwareMap.get(DcMotor.class, "carousel");
        linearSlide = hardwareMap.get(DcMotor.class, "slide");
        gripper = hardwareMap.get(Servo.class,"gripper");
    
    

    
    waitForStart();
    if (opModeIsActive()) {
        
        action(-20,-20,-20,-20,0.2f); //rFDis, lFDis, rBDis, lBDis, pow
        action(20,20,-20,-20,0.2f); //rFDis, lFDis, rBDis, lBDis, pow
        
        }
     }
 }

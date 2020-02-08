/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.revrobotics.ColorMatch;
import com.revrobotics.ColorMatchResult;
import com.revrobotics.ColorSensorV3;

import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Color;

/*
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */

public class Robot extends TimedRobot {
  /**
   * Change the I2C port below to match the connection of your color sensor
   */
  private final I2C.Port i2cPort = I2C.Port.kOnboard;

  /**
   * A Rev Color Sensor V3 object is constructed with an I2C port as a 
   * parameter. The device will be automatically initialized with default 
   * parameters.
   */
  private final ColorSensorV3 m_colorSensor = new ColorSensorV3(i2cPort);
  private final ColorMatch m_colorMatcher = new ColorMatch();
  private final Color kBlueTarget = ColorMatch.makeColor(0.143, 0.427, 0.429);
  private final Color kGreenTarget = ColorMatch.makeColor(0.197, 0.561, 0.240);
  private final Color kRedTarget = ColorMatch.makeColor(0.421, 0.393, 0.183);
  private final Color kYellowTarget = ColorMatch.makeColor(0.361, 0.524, 0.113);

  public static  int B=0;
  public static  int Y=1;
  public static  int R=2;
  public static int G=3;

  String colors[] = {"Blue", "Yellow", "Red","Green"};  //It is spin some direction from the bottom of the control panel
  int color_number=-1;
  int rec_color=-1;
  int temp_color_number=-1;

  public void check(){
    if(color_number>3){
      color_number=0;
    }

    if(rec_color>3){
      rec_color=0;
    }
  }

  @Override
  public void robotInit() {
    m_colorMatcher.addColorMatch(kBlueTarget);
    m_colorMatcher.addColorMatch(kGreenTarget);
    m_colorMatcher.addColorMatch(kRedTarget);
    m_colorMatcher.addColorMatch(kYellowTarget);


    int proximity = m_colorSensor.getProximity();
    SmartDashboard.putNumber("Proximity", proximity);
    Color detectedColor = m_colorSensor.getColor();
    ColorMatchResult match = m_colorMatcher.matchClosestColor(detectedColor);
    String rec_colorString = "initialized string";


    if (proximity >= 180)
    {

      /*color_number_sec=color_number+1;

      if(color_number_sec>3){
        color_number_sec=0;
      }
      */
      
      if (match.color == kBlueTarget) {
        rec_color=G;
        temp_color_number=B;
        rec_colorString = colors[rec_color];
      } else if (match.color == kRedTarget) {
        rec_color=Y;
        temp_color_number=R;
        rec_colorString = colors[rec_color];
      } else if (match.color == kGreenTarget) {
        rec_color=R;
        temp_color_number=G;
        rec_colorString = colors[rec_color];
      } else if (match.color == kYellowTarget) {
        rec_color=B;
        temp_color_number=Y;
        rec_colorString = colors[rec_color];
      }
      else
      {
        rec_colorString = "Unknown";
      } 
      
  }
  else
  {
    rec_colorString = "Too Far Away";
  }

    
  }
  @Override
  public void robotPeriodic() {
    /**
     * The method GetColor() returns a normalized color value from the sensor and can be
     * useful if outputting the color to an RGB LED or similar. To
     * read the raw color, use GetRawColor().
     * 
     * The color sensor works best when within a few inches from an object in
     * well lit conditions (the built in LED is a big help here!). The farther
     * an object is the more light from the surroundings will bleed into the 
     * measurements and make it difficult to accurately determine its color.
     */

     //clockwise bottom
     //counterclockwise top
    

    int proximity = m_colorSensor.getProximity();
    SmartDashboard.putNumber("Proximity", proximity);
    Color detectedColor = m_colorSensor.getColor();
    ColorMatchResult match = m_colorMatcher.matchClosestColor(detectedColor);
    String colorString = "initialized string";
//establishing previous color
    // if (match.color == kBlueTarget) {
    //   rec_color=3;
    // } else if (match.color == kRedTarget) {
    //   rec_color=1;
    // } else if (match.color == kGreenTarget) {
    //   rec_color=2;
    // } else if (match.color == kYellowTarget) {
    //   rec_color=0;
    // }
//checks that BYRG
    check();
    if(proximity>180){
      if(temp_color_number!=color_number){
        if (match.color == kBlueTarget) {
          color_number=B;
          temp_color_number=B;
          colorString = colors[color_number];
        } else if (match.color == kRedTarget) {
          color_number=R;
          temp_color_number=R;
          colorString = colors[color_number];
        } else if (match.color == kGreenTarget) {
          // if(rec_color == B||rec_color==Y){
          //   color_number=Y;
          //   rec_color=B;
          // }else{
          //   color_number=G;
          //   rec_color=R;
          // }
          // colorString = colors[color_number];
  
          if(rec_color+1!=temp_color_number){
            color_number=Y;
            rec_color=B;
            temp_color_number=Y;
          }else{
            color_number=G;
            rec_color=R;
            temp_color_number=G;
          }
        } else if (match.color == kYellowTarget) {
        //   if(rec_color == R||rec_color==G){
        //     color_number = G;
        //     rec_color=R;
        //   }else{
        //     color_number=Y;
        //     rec_color=B;
        //   }
        //   colorString = colors[color_number];
        // }

        if(rec_color+1!=temp_color_number){
          color_number=G;
          rec_color=R;
          temp_color_number=G;
        }else{
          color_number=Y;
          rec_color=B;
          temp_color_number=Y;
        }
      }
        else
        {
          colorString = "Unknown";
        } 
      }
      else
      {
        colorString = "Too Far Away";
      }
      }
      

    /**
     * The sensor returns a raw IR value of the infrared light detected.
     */
    double IR = m_colorSensor.getIR();

    /**
     * Open Smart Dashboard or Shuffleboard to see the color detected by the 
     * sensor.
     */
    SmartDashboard.putNumber("Red", detectedColor.red);
    SmartDashboard.putNumber("Green", detectedColor.green);
    SmartDashboard.putNumber("Blue", detectedColor.blue);
    SmartDashboard.putNumber("IR", IR);
    // SmartDashboard.putNumber("Red", detectedColor.red);
    // SmartDashboard.putNumber("Green", detectedColor.green);
    // SmartDashboard.putNumber("Blue", detectedColor.blue);
    SmartDashboard.putNumber("Confidence", match.confidence);
    SmartDashboard.putString("Detected Color", colorString);
    SmartDashboard.putString("Previous Color", colors[rec_color]);

    SmartDashboard.putNumber("Proximity", proximity);

  }
}
/**
 * file: EuclidAlgorithm.java
 * author: Gentjan Gjeci
 * course: MSCS 630
 * assignment: Lab2 part1
 * due date: February 4, 2017
 * version: 1.0
 * 
 * This file has the declaration of the Euclid Algorithm.
 */

import java.util.Scanner;
import java.lang.Long;
import java.lang.Math;
import java.util.List;
import java.util.ArrayList;
import java.util.Vector;

/**
 * EuclidAlgorithm
 * 
 * This class implements eucligAlg to find the gcd(a,b) of two longs
 */
public class EuclidAlgorithm {
  //Field/s
  private static List<String[]> input = new ArrayList<String[]>();

 /**
   * scannInput
   *
   * This function scans user input to get the two required values a and b 
   * and calls euclidAlg(a,b) to compute the gcd(a,b) of the input values.
   */ 
  public static void scannInput(){
    Scanner sc = new Scanner(System.in);
    String line = "";
    String [] inputs = null;
    long divident = 0L;
    long divisor = 0L;
    while(sc.hasNextLine() && !(line = sc.nextLine()).equals("")){
      //get input
      line = line.trim();
      inputs = line.split(" ");
      if(inputs.length > 2){
        System.out.println("Error: More then two inputs");
        continue;
      }
      input.add(inputs);    
    }
    //Input finished, do the calculation
    for(String[] inp: input){
      divident = Long.parseLong(inp[0]);
      divisor = Long.parseLong(inp[1]);
      long answer = euclidAlg(divident,divisor);
      System.out.println(answer);
    }  
    
  }
  
  /**
   * euclidAlg
   *
   * Finds the gdc of two positive numbers
   *
   * Parameters:
   *  a: positive number
   *  b: positive number
   *
   * Return value: gcd of a and b
   */
  public static long euclidAlg(long a, long b){
    long tmp = 0, reminder = 0, tmpReminder = 0;
    if(!(a >= b)){
      //Swap the values
      tmp = a;
      a = b;
      b = tmp;
    }
    //Try first reminder
    reminder = a % b;
    //See if we have mod 0 for the inputs
    if(reminder == 0){
      return b;
    }
    //Continue with finding the gdc
    while (reminder != 0){
      //Keep deviding
      tmpReminder = b % reminder;
      if(tmpReminder == 0){
        return reminder;
      }
      b = reminder;
      reminder = tmpReminder;
    }
    return reminder;
  }

  public static void main (String [] args){
    scannInput();
  }
}





















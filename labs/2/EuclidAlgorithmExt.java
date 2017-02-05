/**
 * file: EuclidAlgorithmExt.java
 * author: Gentjan Gjeci
 * course: MSCS 630
 * assignment: Lab2 part2
 * due date: February 4, 2017
 * version: 1.0
 * 
 * This file has the declaration of the Euclid Algorithm Extended.
 */

import java.util.Scanner;
import java.lang.Long;
import java.lang.Math;
import java.util.List;
import java.util.ArrayList;
import java.util.Vector;
/**
 * EuclidAlgorithmExt
 * 
 * This class implements eucligAlgExt to find the gcd(a,b) of two longs
 * and also the x and y of the equation d=ax+by
 */
public class EuclidAlgorithmExt{
  private static List<String[]> input = new ArrayList<String[]>();

 /**
   * scannInput
   *
   * This function scans user input to get the two required values a and b 
   * and calls euclidAlgExt(a,b) to compute the gcd(a,b) of the input values and
   * also x and y to satisfy the equation d=ax+by
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
      long [] answer = euclidAlgExt(divident,divisor);
      for(long ans: answer){
        System.out.print(ans + " ");
      }
      System.out.println();
    }  
    
  }

  /**
   * substractVectors
   *
   * Substract vector and return the resulting vector
   *
   * Parameters:
   *    vectorOne: First vector
   *    vectorTwo: Second vector
   *
   *  Return value: return the vector from the difference between vectorOne - vectorTwo 
   * 
   */
  public static Vector<Long> substractVectors(Vector<Long> vectorOne, Vector<Long> vectorTwo){
    if (vectorOne.capacity() != vectorTwo.capacity())
      throw new IllegalArgumentException("Vector capacity error");
    //Same size, use any size for capacity
    int capacity = vectorOne.capacity();
    Vector<Long> subVector = new Vector<Long>(capacity);//substraction vector
    for (int i = 0; i < capacity; i++){
      long element = (vectorOne.get(i)) - (vectorTwo.get(i));
      subVector.add(element);
    }
    return subVector;
  }

  /**
   * multiplyVector
   *
   * Multiply vector by a constant and return the resulting vector
   *
   * Parameters:
   *    vec: The vector to be multiplied
   *    constant: constant to multiply the vector by
   *
   *  Return value: return the vector from the multiplication vec * constant 
   * 
   */
  public static Vector<Long> multiplyVector(Vector<Long> vec, long constant){
    Vector<Long> mVector = new Vector<Long>(3);//multiplication vector
    int capacity = vec.capacity();
    for(int i = 0; i < capacity; i++){
      long element = vec.get(i) * constant;
      mVector.add(element);
    }
    return mVector;
  }

  /**
   * euclidAlgExt
   *
   * Finds the gdc of two positive numbers, as well as x and y 
   * to satisfy equation d=ax+by
   *
   * Parameters:
   *  a: positive number
   *  b: positive number
   *
   * Return value: array with d,x,y
   */
  public static long[] euclidAlgExt(long a, long b){
    long [] answer = new long[3];
    Vector<Long> uVector = new Vector<Long>(3);//vector U
    Vector<Long> vVector = new Vector<Long>(3);//vector V
    Vector<Long> wVector = null;//vector W
    
    uVector.add(a);
    uVector.add(1L);
    uVector.add(0L);

    vVector.add(b);
    vVector.add(0L);
    vVector.add(1L);

    while(vVector.get(0) > 0L){
      long x = (long)Math.floor(uVector.get(0)/vVector.get(0));
      Vector<Long> mVector = multiplyVector(vVector,x);
      wVector = substractVectors(uVector,mVector);
      uVector = vVector;//U=V
      vVector = wVector;//V=W
    }
    for(int i = 0, n = uVector.capacity(); i < n; i++){
      answer[i] = uVector.get(i);
    }
    return answer;
  }
  public static void main (String [] args){
    scannInput();
  }
}

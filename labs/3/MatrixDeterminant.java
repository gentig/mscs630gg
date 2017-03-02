/**
 * file: MatrixDeterminant.java
 * author: Gentjan Gjeci
 * course: MSCS630
 * assignment: lab3
 * due date: 2 27, 2017
 * version: 1.0
 *
 * This file contains the declaration of the
 * MatrixDeterminant to find the determinant of an n x n matrix.
 */

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * MatrixDeterminant
 *
 * This class implements a function to find the determinant
 * of a n x n matrix.
 */
public class MatrixDeterminant {
  //Fields
  private static List<Integer> input = new ArrayList<>();
  private static List<String[]> mConfig = new ArrayList<>();
  /**
   * cofModDet
   *
   * This function computes the determinant of an n x n matrix
   *
   * Parameters:
   *   m: the modulo that the matrix should be converted to
   *   A: two dimensional array
   *
   * Return value: the modulo m of n
   */
  private static int cofModDet(int m, int[][] A){
    int det = detOfA(A);
    det = nToModuloM(det, m);
    return det; //return the determinant
  }
  /**
   * detOfA
   *
   * This function computes the determinant of an n x n matrix
   *
   * Parameters:
   *   A: two dimensional array
   *
   * Return value: the modulo m of n
   */
  private static int detOfA(int[][] A){
    int determinant = 0;
    if(A.length == 1){
      determinant = A[0][0];
    }else if (A.length == 2){
      determinant = A[0][0]*A[1][1] - A[1][0]*A[0][1];
    }
    else {
      int mLength = A.length - 1;
      int[][] M = new int[mLength][mLength];//sub Matrix
      int counter = 0;
      /*Get all sub matrices and until we have a 2 x 2 matrix keep calling
      * this function recursively on each submatrix.*/
      for (int m = 0; m < A.length; m++) {
        for (int i = 0; i < A.length; i++) {
          if (i == 0) {
            continue;
          }
          counter = 0;
          for (int j = 0; j < A.length; j++) {
            if (j == m) {
              continue;
            }
            M[i - 1][counter++] = A[i][j];//build sub array
          }
        }
        //Alternating between subtraction and addition
        determinant += Math.pow(-1,1+m+1)* A[0][m] * detOfA(M);
      }
    }
    return determinant;
  }

  /**
   * nToModuloM
   *
   * This function computes the mod of an number,
   * negative or positive
   *
   * Parameters:
   *   aNumber: the number
   *   modulo: the modulo
   *
   * Return value: the remainder
   */
  private static int nToModuloM(int aNumber, int modulo) {
    int remainder = aNumber % modulo;
    if (remainder < 0) {
      remainder += modulo;
    }
    return remainder;
  }

  /**
   * printMatrix
   *
   * This function prints a matrix
   *
   * Parameters:
   *   A: a two dimensional array
   *
   */
  public static void printMatrix(int [][] A){
    for (int i = 0; i < A.length; i++){
      for (int j = 0; j < A.length; j++){
        System.out.print(A[i][j] + " ");
      }
      System.out.println();
    }
    System.out.println("========================");
  }

  /**
   * scannInput
   *
   * This function scans user input to get the required values
   */
    public static void scannInput(){
      Scanner sc = new Scanner(System.in);
      String line;
      String [] inputs;
      int modulo = 0, size = 0, counter = 0;
      //Get the first two inputs m and n
      if(sc.hasNextLine() && !(line = sc.nextLine()).equals("")){
        line = line.trim();
        inputs = line.split(" ");
        if(inputs.length > 2){
          System.out.println("Error: More then two inputs");
          return;
        }
        mConfig.add(inputs);
        size = Integer.parseInt(mConfig.get(0)[1]);
        modulo = Integer.parseInt(mConfig.get(0)[0]);
      }
      while(sc.hasNextLine() && !(line = sc.nextLine()).equals("")){
        //get input
        line = line.trim();
        inputs = line.split(" ");
        if (inputs.length != size){
          System.out.println("Int per Line not equal to n");
          break;
        }else if (counter >= size){
          System.out.println("Lines exceed n. Extra info will be omitted");
          break;
        }
        //Continue normally here
        for (int i = 0; i < inputs.length; i++) {
          input.add(Integer.parseInt(inputs[i]));
        }
        counter++;
      }
      int check = (int) Math.pow(size,2);
      if(input.size() != check){
        System.out.println("Error");
        return;
      }
      int [][] aM = buildMatrix(size,input);
      System.out.println(cofModDet(modulo, aM));
    }

  /**
   * buildMatrix
   *
   * This function prints a matrix
   *
   * Parameters:
   *   size: size of the two dimensional array n x n
   *   ls: List
   *
   *   Return:
   *    return a n x n matrix
   *
   *   Return value: a two dimensional array
   */
    private static int[][] buildMatrix(int size, List<Integer> ls){
      int[][] a = new int[size][size];
      for(int i = 0; i < size; i++){
        for(int j =0; j < size; j++){
          a[i][j]= ls.get(j +( size * i));
        }
      }
      return a;
    }
  /*=========================Main Entry=====================*/
  public static void main(String [] args){
    //------------------- TESTING - DEV---------------------

    //------------------- PRODUCTION -----------------------
    scannInput();
  }
}

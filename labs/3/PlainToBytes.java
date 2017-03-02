/**
 * file: PlainToBytes.java
 * author: Gentjan Gjeci
 * course: MSCS630
 * assignment: lab3 part 2
 * due date: 2 27, 2017
 * version: 1.0
 *
 * This file contains the declaration of the
 * PlainToBytes to put plain text to an matrix of bytes.
 */

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * PlainToBytes
 *
 * This class implements a function to put plain text to an matrix of
 * bytes.
 */
public class PlainToBytes {
  final static int FULL_MATRIX = 15;//starting at 0
  final static int MATRIX_SIZE = 4;//n x n
  final static int INPUT_LINES = 2;
  final static int FIRST_INPUT_LINE = 0;
  final static int SECOND_INPUT_LINE = 1;
  private static List<String> input = new ArrayList<>();
  private static int adtVal;

  /**
   * getHexMatP
   *
   * This function creates a matrix from string and returns a matrix 4 x 4
   *
   * Parameters:
   *   s: filler
   *   p: String input
   *
   * Return: int matrix
   */
  public static int[][] getHexMatP(char s, String p){
    int outI = 0, outJ = 0, counter = 0,length = 0;
    int[][] a = new int[MATRIX_SIZE][MATRIX_SIZE];
    for (int i = 0; i < p.length(); i++){
      int c = (int) p.charAt(i);//get the letter
      if(outI == MATRIX_SIZE){
        //Switch
        outI = 0;
        outJ++;
      }
      a[outI][outJ] = c;
      outI++;
      counter++;
      //If no more input string fill the rest of the matrix
      if(counter != FULL_MATRIX && i == p.length()-1){
        while (counter <= FULL_MATRIX){
          if(outI > 3){
            //Switch
            outI = 0;
            outJ++;
          }
          a[outI][outJ] = s;
          outI++;
          counter++;
        }
        printMatrixHex(a);
      }
    }
    return a;
  }

  /**
   * stringSplit
   *
   * This function splits text in length of 16
   *
   * Parameters:
   *   str: string input
   *   size: size of substring
   *
   * Return: List of strings
   */
  public static List<String> stringSplit(String str, int size) {
    List<String> ret = new ArrayList<>((str.length() + size - 1) / size);

    for (int start = 0; start < str.length(); start += size) {
      ret.add(str.substring(start, Math.min(str.length(), start + size)));
    }
    return ret;
  }

  /**
   * buildPMatrix
   *
   * This function builds a matrix 4 x n, n can be any number depending
   * on the length of the input string
   *
   * Parameters:
   *   s: filler
   *   p: string input
   *
   *  Return:
   *    return full matrix
   */
  public static int[][] buildPMatrix(char s, String p){
    int quotient = (int) Math.ceil((double) p.length() / 4);
    int adt = (quotient + 4 - 1) / 4 * 4;//formula to get 4 x n size for any string length
    adtVal = adt;
    int outI = 0, outJ = 0;
    int a[][] = new int[4][adt];
    prepMatrix(a,s,adt);
    for (int i = 0; i < p.length(); i++){
      int c = (int) p.charAt(i);
      if(outI > 3){
        outI = 0;
        outJ++;
      }
      a[outI][outJ] = c;
      outI++;
    }
    return a;
  }
  /**
   * prepMatrix
   *
   * This function prepares the matrix
   *
   * Parameters:
   *   A: a two dimensional array
   *   s: character to fill empty spots
   *   length: columns of the matrix
   *
   */
  public static void prepMatrix(int [][] A, char s, int length){
    for (int i = 0; i < 4; i++){
      for (int j = 0; j < length; j++){
        A[i][j] = s;
      }
    }
  }

  /* printMatrixHex
   *
   * Prints matrix values in hex
   *
   * Parameters:
   *   A: a two dimensional array
   */
  public static void printMatrixHex(int [][] A){
    for (int i = 0; i < 4; i++){
      for (int j = 0; j < A.length; j++){
        String hex = Integer.toHexString(A[i][j]);
        hex = hex.toUpperCase();
        System.out.print(hex + " ");
      }
      System.out.println();
    }
    System.out.println();
  }

  /**
   * scannInput
   *
   * This function scans user input to get the required values
   */
  public static void scannInput(){
    Scanner sc = new Scanner(System.in);
    String line;
    //Get the first two inputs m and n
    while(sc.hasNextLine() && !(line = sc.nextLine()).equals("")) {
      //get input
      input.add(line);
      if (input.size() == INPUT_LINES) {
        //Done with inputs, start calculating
        //Error checking
        if (input.get(FIRST_INPUT_LINE).trim().length() > 1) {
          System.out.println("Error: We need single char at line 1!");
          return;
        } else {
          List<String> inputList = stringSplit(input.get(SECOND_INPUT_LINE), FULL_MATRIX + 1);
          for (String str : inputList) {
            getHexMatP(input.get(FIRST_INPUT_LINE).charAt(0), str);
          }
        }
        input = new ArrayList<>();
      }
    }
  }

  public static void main(String[] args){
    scannInput();
  }
}
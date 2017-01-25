/**
 * file: StringToInt.java
 * author: Gentjan Gjeci
 * course: MSCS 630L
 * assignment: lab1
 * due date: January 25, 2017
 * version: 1.0
 * 
 * This file contains the declaration of the StringToInt conversion.
 */
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.HashMap;
import java.util.Map;
import java.util.Arrays;

/**
 * StringToInt
 * 
 * This class implements the functions to ask user for some
 * string input and convert that to the corresponding int 
 */
public class StringToInt{
  //Field/s
  static final int MAX_LINES = 50;//must be 50
  static final int MAX_WORDS = 500;//must be 500
  static final int MAX_LETTERS = 45;//must be 45
  static final int ALPHABET = 25;
  private Map<Character, Integer> map;
  private char[] alph;
  
  //Constructor/s
  public StringToInt(){
    this.initMap();  
  }
  //Method/s

 /**
  * initMap
  *
  * This function initializes the alphabet mapping char to int
  * 
  * Parameters:
  *   none
  * 
  * Return value: none
  */
  public void initMap(){
    this.map = new HashMap<Character, Integer>();
    this.alph = new char[]{'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};
    for(int i = 0; i < alph.length; i++){
      this.map.put(this.alph[i], i);
    }
  }

 /**
  * printInput
  *
  * This function prints the list
  * 
  * Parameters:
  *   List<String> lines
  * 
  * Return value: none
  */ 
  public void printInput(List<String> lines){
    for (String str : lines){
      System.out.println(str);
    }
  }

 /**
  * checkLines
  *
  * This function checks for the List size
  * 
  * Parameters:
  *   List<String> lines
  * 
  * Return value: number of lines
  */ 
  public int checkLines(List<String> lines){
    return lines.size();//we start from zero
  }

 /**
  * checkWords
  *
  * This function checks for the number of words in a line
  * 
  * Parameters:
  *   String str
  * 
  * Return value: mix, true or false
  */ 
  public boolean checkWords(String str){
    String strTrim = str.trim();
    String [] wordCount = strTrim.split(" ");
    if(wordCount.length > MAX_WORDS){
      return false;
    }
    return true;
  }

 /**
  * checkLetters
  *
  * This function checks for the length of the words
  * 
  * Parameters:
  *   String str
  * 
  * Return value: mix, true or false
  */  
  public boolean checkLetters(String str){
    List<String> wordLengthErr = new ArrayList<>();
    String strTrim = str.trim();
    String [] words = strTrim.split(" ");
    for(String word: words){
      if(word.length() > MAX_LETTERS){
        wordLengthErr.add(word);
      }
    }
    if(!wordLengthErr.isEmpty()){
      return false;
    }
    return true;
  }

 /**
  * convertIntegers
  *
  * This function converts from List<Integer> to int[] premitive
  * 
  * Parameters:
  *   List<Integer> integers
  * 
  * Return value: int[]
  */ 
  public static int[] convertIntegers(List<Integer> integers){
    int[] ret = new int[integers.size()];
    for (int i=0; i < ret.length; i++){
      ret[i] = integers.get(i).intValue();
    }
    return ret;
  }
  
 /**
  * str2int
  *
  * This function takes user input string and convert each letter of that string to corresponding number
  * 
  * Parameters:
  *   String string
  * 
  * Return value: int[]
  */ 
  public int[] str2int(String string){
    List<Integer> integers = new ArrayList<>(); 
    int [] conversion = null;
    for(char c : string.toCharArray()){
      if(Character.isSpaceChar(c)){
        integers.add(26);
      }else{
        c = Character.toLowerCase(c);
        for(char ac: this.alph){
          if(c == ac){
            integers.add(this.map.get(c));
          }
        }
      }
    }
    conversion = convertIntegers(integers);
    int [] test = {1,2,3,4};
    return conversion;
  }

 /**
  * scanInput
  *
  * This function gets user input and starts the conversion from string to int
  * 
  * Parameters:
  *   none
  * 
  * Return value: none
  */ 
  public void scanInput(){
    List<String> lines = new ArrayList<>();
    List<String> errWords = new ArrayList<>();
    List<String> errLetters = new ArrayList<>();
    int numOfLines = 0;//counter for the lines
    String str = "";
    System.out.println("Enter input (input entry terminated by empty line):");
    System.out.println("Lines: 1<=L<=50, Words: 1<=M<=500, Letters: 1<=N<=45");
    try (Scanner scanner = new Scanner (System.in)){
      while (scanner.hasNextLine () && !(str = scanner.nextLine()).equals("")){
        if(checkWords(str)){
          if(checkLetters(str)){
            //Number of words and letters in each word are correct
            lines.add(str);
          }else{
            //Number or words wrong
            errLetters.add(str);
          }
        }else{
          //Number of letters is wrong
          errWords.add(str);
        }
        numOfLines++;
        if(numOfLines >= MAX_LINES){
          break;
        }
      }
    }catch(Exception e){
      e.printStackTrace();
    }
    //Display results
    for(String string: lines){
      int[] cnv = str2int(string);
      for(int in: cnv){
        System.out.print(in+" ");
      }
      System.out.println();      
    }
    //If there are errors notify the user 
    if(!errWords.isEmpty()){
      System.out.println("Error: Words limit error in the following line/s.");
      printInput(errWords);
    }
    if(!errLetters.isEmpty()){
      System.out.println("Error: Letters limit error in the words of the following line/s");
      printInput(errLetters);
    }          
  }
 /**
  * main
  *
  * This function is the entry execution point for the program
  * 
  * Parameters:
  *   String [] args
  * 
  * Return value: none
  */
  
  public static void main (String[] args){
    StringToInt sti = new StringToInt();
    sti.scanInput();
  }
}

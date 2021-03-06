/**
 * file: DriverAES.java
 * author: Gentjan Gjeci
 * course: MSCS630
 * assignment: lab4
 * due date: 04-05-2017
 * version: 1.0
 * <p>
 * This file has driver class to display all AES round keys.
 */
import java.util.Scanner;

/**
 * DriverAES
 *
 * This is the driver class which creates round keys for AES
 */
public class DriverAES {
  public static void main(String[] args){
    AESCipher asCph = new AESCipher();
    Scanner sc = new Scanner(System.in);
    String line;
    String [] sHexSkey = new String[2];
    int counter = 0;
    //Get user input
    System.out.println("Enter sHex and kHex: ");
    while(sc.hasNextLine() && !(line = sc.nextLine()).equals("")) {
          line = line.trim();
          sHexSkey[counter] = line;
          if(counter == 1) {
            break;
          }
          counter++;
    }
    if (sHexSkey.length == 2){
      asCph.init(sHexSkey[0],sHexSkey[1]);
    }else{
      System.out.println("Only one sHex and sKey are allowed:");
    }
  }
}

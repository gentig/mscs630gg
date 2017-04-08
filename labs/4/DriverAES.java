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
    //Get user input
    System.out.println("Enter the key: ");
    while(sc.hasNextLine() && !(line = sc.nextLine()).equals("")) {
        line = line.trim();
        asCph.init(line);
    }
  }
}

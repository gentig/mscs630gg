import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by genti on 4/6/2017.
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

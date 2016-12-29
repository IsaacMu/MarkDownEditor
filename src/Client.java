import code.GUI;

import java.io.IOException;
import java.util.Scanner;

/**
 * Created by MuLi on 12/27/16.
 */
public class Client {

    public static void main(String[] args){
        Scanner input=new Scanner(System.in);
        String host=input.nextLine();
        int id = input.nextInt();
        int port=input.nextInt();

        try {
            new GUI(host,port,id);
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}


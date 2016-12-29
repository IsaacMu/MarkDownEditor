import code.Payload;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.*;
import java.util.*;

/**
 * Created by MuLi on 12/28/16.
 */
public class Server extends JFrame{

    private JTextArea jta = new JTextArea();
    public static void main(String[] args) throws IOException{
        new Server();
    }

    public Server() throws IOException {

        setLayout(new BorderLayout());
        add(new JScrollPane(jta), BorderLayout.CENTER);

        setTitle("Markdown Server");
        setSize(500,300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        Scanner input=new Scanner(System.in);

        int a=input.nextInt();
        ServerSocket serverSocket=new ServerSocket(a);
        jta.append("Server started at" + new Date() + a +'\n');

        int ClientNo=1;
        while(true){
            Socket socket1=serverSocket.accept();
            Socket socket2=serverSocket.accept();
            HandleAClient client1=new HandleAClient(socket1,socket2);
            HandleAClient client2=new HandleAClient(socket2,socket1);
            new Thread(client1).start();
            ClientNo++;
            new Thread(client2).start();
            ClientNo++;
        }
    }
}

class HandleAClient implements Runnable{
    private Socket send,get;

    public HandleAClient(Socket get,Socket send){
        this.send=send;
        this.get=get;
    }

    @Override
    public void run() {
        try {
            ObjectInputStream input=new ObjectInputStream(get.getInputStream());
            ObjectOutputStream output=new ObjectOutputStream(send.getOutputStream());

            while (true){
                Payload payload=(Payload) input.readObject();
                output.writeObject(payload);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}

package code;
/**
 * Created by MuLi on 12/8/16.
 */
import javax.swing.*;
import javax.swing.text.EditorKit;
import java.awt.*;
import java.io.IOException;

public class GUI {

    public GUI(String host,int port,int id) throws IOException{
        JFrame frame = new JFrame("CSCW MarkDownEditor" + id);

        JPanel menu=new JPanel();
        menu.setBounds(0,0,200,800);
        menu.setLayout(null);
        JLabel menuLabel=new JLabel("menu",JLabel.CENTER);
        menuLabel.setBounds(0,0,200,50);
        LeftMenu leftMenu=new LeftMenu();
        menu.add(menuLabel,BorderLayout.CENTER);
        menu.add(leftMenu,BorderLayout.CENTER);
        frame.add(menu);


        JPanel showpart=new JPanel();
        showpart.setBounds(630,0,350,800);
        showpart.setLayout(null);
        JLabel previewLabel=new JLabel("preview",JLabel.CENTER);
        previewLabel.setBounds(0,0,350,50);
        Preview preview=new Preview(leftMenu);
        showpart.add(previewLabel);
        showpart.add(preview);
        frame.add(showpart);


        JPanel editpart=new JPanel();
        editpart.setBounds(240,0,350,800);
        editpart.setLayout(null);
        JLabel editLabel=new JLabel("editor",JLabel.CENTER);
        editLabel.setBounds(0,0,350,50);
        Editor editor=new Editor(preview,leftMenu,host,port,id);
        editpart.add(editLabel);
        editpart.add(editor);
        frame.add(editpart);


        MyMenuBar menuBar=new MyMenuBar(editor,preview);
        frame.setJMenuBar(menuBar);


        frame.setResizable(false);
        frame.setLayout(null);
        frame.setSize(980,720);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

}

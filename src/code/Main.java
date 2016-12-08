package code;

import javax.swing.*;
import javax.swing.text.EditorKit;

public class Main {

    public static void main(String[] args) {
        JFrame frame = new JFrame("MarkDownEditor");

        JPanel menu=new JPanel();
        menu.setBounds(10,0,305,600);
        menu.setLayout(null);
        JPanel editpart=new JPanel();
        editpart.setBounds(315,0,400,600);
        editpart.setLayout(null);
        JPanel showpart=new JPanel();
        showpart.setBounds(715,0,285,600);
        showpart.setLayout(null);


        JLabel previewLabel=new JLabel("preview",JLabel.CENTER);
        previewLabel.setBounds(0,0,300,50);
        Preview preview=new Preview();
        showpart.add(previewLabel);
        showpart.add(preview);

        JLabel menuLabel=new JLabel("menu",JLabel.CENTER);
        menuLabel.setBounds(0,0,300,50);
        LeftMenu leftMenu=new LeftMenu();
        menu.add(menuLabel);
        menu.add(leftMenu);

        JLabel editLabel=new JLabel("editor",JLabel.CENTER);
        editLabel.setBounds(0,0,300,50);
        Editor editor=new Editor(preview,leftMenu);
        editpart.add(editLabel);
        editpart.add(editor);

        frame.add(menu);
        frame.add(editpart);
        frame.add(showpart);

        MyMenuBar menuBar=new MyMenuBar(editor,preview);
        frame.setJMenuBar(menuBar);

        frame.setLayout(null);
        frame.setSize(1000,700);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

}

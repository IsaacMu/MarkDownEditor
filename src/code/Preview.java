package code;

import javax.swing.*;
import javax.swing.text.html.HTMLEditorKit;

/**
 * Created by Isacclee on 12/8/16.
 */
public class Preview extends JScrollPane{
    private JEditorPane htmlPane;
    private HTMLEditorKit kit;

    public Preview(){
        this.htmlPane=new JEditorPane();
        this.kit=new HTMLEditorKit();

    }
}

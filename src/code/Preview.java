package code;

import org.jsoup.Jsoup;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.StyleSheet;
import java.awt.*;

/**
 * Created by MuLi on 12/8/16.
 */
public class Preview extends JScrollPane{
    private JEditorPane htmlPane;
    private HTMLEditorKit kit;
    private JScrollBar scrollBar;
    public Preview(final LeftMenu leftMenu){
        this.htmlPane=new JEditorPane();
        this.kit=new HTMLEditorKit();
        this.htmlPane.setEditorKit(this.kit);
        this.htmlPane.setContentType("text/html");
        this.htmlPane.setEditable(false);
        this.htmlPane.setFont(new Font("TimesRoman",Font.PLAIN,14));
        this.scrollBar=this.getVerticalScrollBar();
        this.getViewport().add(this.htmlPane);
        this.setBounds(5,40,345,600);

        leftMenu.getList().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                int index = leftMenu.getList().getSelectedIndex();
                System.out.println(leftMenu.getRowArr().get(index));
                scrollBar.setValue(leftMenu.getRowArr().get(index)*20);
            }
        });
    }

    public void setContent(String html){
        this.htmlPane.setText(html);
    }

    public String getXHTML(){
        String html=this.htmlPane.getText();
        return Jsoup.parse(html).html();
    }
    public void importCss(String css){
        StyleSheet styleSheet=this.kit.getStyleSheet();
        styleSheet.addRule(css);
    }
}

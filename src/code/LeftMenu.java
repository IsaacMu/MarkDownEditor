package code;

import com.petebevin.markdown.MarkdownProcessor;
import org.jsoup.Jsoup;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.regex.Pattern;

/**
 * Created by Isacclee on 12/8/16.
 */
public class LeftMenu extends JScrollPane{
    private MarkdownProcessor processor;
    private ArrayList<Integer> arrayList;
    private JList list;
    private DefaultListModel model;

    public LeftMenu(){
        DefaultListModel model=new DefaultListModel();
        this.model=model;
        this.processor=new MarkdownProcessor();
        this.arrayList=new ArrayList<>();
        this.list=new JList(model);
        this.list.setFont(new Font("TimesRoman", Font.PLAIN,18));
        this.list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        this.getViewport().add(this.list);
        this.setBounds(5,40,200,480);
    }


    public void update(String markdown) throws IOException {
        this.model.removeAllElements();
        this.arrayList.clear();
        BufferedReader content=new BufferedReader(new StringReader(markdown));
        String temp="";
        int row=0;
        int i=0;
        Pattern pattern=Pattern.compile("<h[1-5]>.*");
        while((temp=content.readLine())!=null) {
            temp = this.processor.markdown(temp);
            if (pattern.matcher(temp).find()) {
                temp= Jsoup.parse(temp).body().text();
                this.arrayList.add(row);
                this.model.add(i++,temp);
            }
            row++;

        }
    }

    public JList getList(){
        return this.list;
    }

    public ArrayList<Integer> getRowArr(){
        return this.arrayList;
    }
}

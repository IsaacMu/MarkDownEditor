package code;

import com.petebevin.markdown.MarkdownProcessor;
import org.docx4j.wml.P;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Created by MuLi on 12/8/16.
 */
public class Editor extends JScrollPane{
    private JEditorPane editor;
    private MarkdownProcessor processor;
    private JScrollBar scrollBar;

    private Socket socket;
    private ObjectOutputStream outputStream;
    public static int[] flag=new int[1000];     //prevent socket emit loop 0:no emit 1:have emit 2:prevent repeat emit
    private int id;

    public Editor(final Preview preview, final LeftMenu leftMenu,String host,int port,final int id) throws IOException{
        this.processor=new MarkdownProcessor();
        this.editor=new JTextPane();
        this.scrollBar=this.getVerticalScrollBar();
        this.editor.setFont(new Font("TimesRoman", Font.PLAIN,14));
        this.getViewport().add(this.editor);
        this.setBounds(0,40,350,600);

        System.out.print(host);
        this.socket=new Socket(host,port);
        this.outputStream=new ObjectOutputStream(socket.getOutputStream());
        this.id = id;

        HandleSocket task=new HandleSocket(socket,this,this.id,preview);
        new Thread(task).start();

        this.editor.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                try{
                    generateHTML(preview);
                    leftMenu.update(editor.getText());
                    if(Editor.flag[id]==2){
                        Editor.flag[id]=0;
                        return;
                    }
                    if(Editor.flag[id]==1){
                        Editor.flag[id]=2;
                        return;
                    }
                    Payload payload=new Payload(0,editor.getText());
                    outputStream.writeObject(payload);
                    outputStream.flush();
                }catch (IOException e1){
                    e1.printStackTrace();
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                try{
                    generateHTML(preview);
                    leftMenu.update(editor.getText());
                    if(Editor.flag[id]==2){
                        Editor.flag[id]=0;
                        return;
                    }
                    if(Editor.flag[id]==1){
                        Editor.flag[id]=2;
                        return;
                    }
                    Payload payload=new Payload(0,editor.getText());
                    outputStream.writeObject(payload);
                    outputStream.flush();
                }catch (IOException e1){
                    e1.printStackTrace();
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                try{
                    generateHTML(preview);
                    leftMenu.update(editor.getText());
                    if(Editor.flag[id]==2){
                        Editor.flag[id]=0;
                        return;
                    }
                    if(Editor.flag[id]==1){
                        Editor.flag[id]=2;
                        return;
                    }
                    Payload payload=new Payload(0,editor.getText());
                    outputStream.writeObject(payload);
                    outputStream.flush();
                }catch (IOException e1){
                    e1.printStackTrace();
                }
            }
        });

        leftMenu.getList().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                int index = leftMenu.getList().getSelectedIndex();
                System.out.println(leftMenu.getRowArr().get(index));
                scrollBar.setValue(leftMenu.getRowArr().get(index)*20);
            };
        });
    }

    public String getContent(){
        return this.editor.getText();
    }
    public void generateHTML(Preview preview)throws IOException{
        String markdown=this.editor.getText();
        preview.setContent(this.processor.markdown(markdown));
    }

    public JEditorPane getEditor(){
        return this.editor;
    }
}
class HandleSocket implements Runnable{
    private Socket socket;
    private Editor editor;
    private int id;
    private Preview preview;

    public HandleSocket(Socket socket,Editor editor,int id,Preview preview){
        this.socket=socket;
        this.editor=editor;
        this.id=id;
        this.preview=preview;
    }

    @Override
    public void run() {
        try{
            ObjectInputStream inputStream=new ObjectInputStream(socket.getInputStream());

            while(true){
                Payload payload= (Payload) inputStream.readObject();
                if (payload.getType()==0){
                    Editor.flag[id]=1;
                    System.out.println(payload.getContent());
                    this.editor.getEditor().setText(payload.getContent());
                }
                else{
                    this.preview.importCss(payload.getContent());
                    this.editor.generateHTML(this.preview);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
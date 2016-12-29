package code;


import org.docx4j.convert.in.xhtml.XHTMLImporterImpl;
import org.docx4j.model.structure.PageSizePaper;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;


/**
 * Created by MuLi on 12/8/16.
 */
public class MyMenuBar extends JMenuBar{
    private JFileChooser saveJfc,openJfc;

    public MyMenuBar(final Editor editor, final Preview preview ){
        this.openJfc=new JFileChooser("./");
        this.saveJfc=new JFileChooser("./");
        this.openJfc.setFileSelectionMode(JFileChooser.FILES_ONLY|JFileChooser.OPEN_DIALOG);
        this.saveJfc.setFileSelectionMode(JFileChooser.SAVE_DIALOG);

        this.saveJfc.setAcceptAllFileFilterUsed(false);

        this.openJfc.addChoosableFileFilter(new MyFileFilter(".css","css file"));
        this.openJfc.addChoosableFileFilter(new MyFileFilter(".md","md file"));
        this.openJfc.addChoosableFileFilter(new MyFileFilter(".docx","docx file"));

        JMenu fileMenu = new JMenu("file");
        JMenuItem importFile= new JMenuItem("Import file",'I');
        JMenuItem exportDocx= new JMenuItem("Export as a docx",'E');
        JMenuItem exitItem= new JMenuItem("exit");
        JMenuItem saveItem= new JMenuItem("save");

        importFile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I,ActionEvent.CTRL_MASK));
        exportDocx.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E,ActionEvent.CTRL_MASK));
        exitItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q,ActionEvent.CTRL_MASK));
        saveItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,ActionEvent.CTRL_MASK));

        fileMenu.add(saveItem);
        fileMenu.add(importFile);
        fileMenu.add(exportDocx);
        fileMenu.add(exitItem);

        this.add(fileMenu);


        // import css event
        importFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openJfc.setDialogTitle("导入.md或.css文件");
                int option=openJfc.showDialog(null,"导入");
                if(option==JFileChooser.APPROVE_OPTION) {
                    File file = openJfc.getSelectedFile();
                    if (file.isFile()) try {
                        BufferedReader content = new BufferedReader(
                                new InputStreamReader(
                                        new FileInputStream(file)
                                )
                        );
                        String temp, text = "";
                        while ((temp = content.readLine()) != null) {
                            text += temp + "\n";
                        }
                        if (file.getName().endsWith(".css")) {       //if css
                            preview.importCss(text);        //import css
                            editor.generateHTML(preview);      //refresh htmlPan
                        } else if (file.getName().endsWith(".md")) {       //if markdown
                            editor.getEditor().setText(text);
                            editor.getEditor().setCaretPosition(0);
                        } else {
                            JOptionPane.showMessageDialog(null, "请导入.css或.md文件", "警告", JOptionPane.INFORMATION_MESSAGE);
                        }
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });

        //save markdown file
        saveItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveJfc.setSelectedFile(new File("save"));
                saveJfc.setDialogTitle("存为markdown文件");
                int option=saveJfc.showDialog(null,"保存");
                if(option==JFileChooser.APPROVE_OPTION) {
                    File file = saveJfc.getSelectedFile();
                    String path = "";
                    if (file.isDirectory()) {
                        path = file.getAbsolutePath() + "/save.md";
                    } else {
//                        System.out.println(file.getAbsolutePath());
                        path = file.getAbsolutePath();
                        if (!path.toLowerCase().endsWith(".md")) {
                            path += ".md";
                        }
                    }
                    try {
                        String content=editor.getContent();
                        File f = new File(path);
                        if(!f.exists()){
                            f.createNewFile();
                        }
                        FileWriter writer = new FileWriter(f);
                        writer.write(content);
                        writer.flush();
                        writer.close();
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });
        //export docx event
        exportDocx.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveJfc.setSelectedFile(new File("output"));
                saveJfc.setDialogTitle("导出为word文件");
                int option=saveJfc.showDialog(null,"导出");
                if(option==JFileChooser.APPROVE_OPTION) {
                    File file = saveJfc.getSelectedFile();
                    String path = "";
                    if (file.isDirectory()) {
                        path = file.getAbsolutePath() + "/output.docx";
                    } else {
                        System.out.println(file.getAbsolutePath());
                        path = file.getAbsolutePath();
                        if (!path.toLowerCase().endsWith(".docx")) {
                            path += ".docx";
                        }
                    }
                    try {

                        //A4  horizontal direction true
                        WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage.createPackage(PageSizePaper.A4, true);
                        XHTMLImporterImpl xhtmlImporter = new XHTMLImporterImpl(wordMLPackage);
                        wordMLPackage.getMainDocumentPart().getContent().addAll(xhtmlImporter.convert(preview.getXHTML(), null));

                        wordMLPackage.save(new File(path));     //export docx file
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });

        exitItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }
}

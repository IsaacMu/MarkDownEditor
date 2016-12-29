package code;

import javax.swing.filechooser.FileFilter;
import java.io.File;


 /**
 * Created by MuLi on 12/8/16.
 */
public class MyFileFilter extends FileFilter{
    private String ends;
    private String description;

    public MyFileFilter(String ends,String description){
        this.ends=ends;
        this.description=description;
    }

    public boolean accept(File f){
        if(f.isDirectory()) return true;
        String fileName=f.getName();
        return fileName.toLowerCase().endsWith(this.ends);
    }

    public String getDescription(){
        return this.description;
    }
}

package code;

import java.io.Serializable;

public class Payload implements Serializable{
    private int type;       //0 markdown text    1 css
    private String content;


    public Payload(int type, String content){
        this.type=type;
        this.content=content;
    }

    public int getType(){
        return this.type;
    }

    public String getContent(){
        return this.content;
    }
}

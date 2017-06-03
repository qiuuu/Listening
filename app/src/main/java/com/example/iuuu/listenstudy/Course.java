package com.example.iuuu.listenstudy;

/**
 * Created by iuuu on 16/10/18.
 */
public class Course {
    private String title;
    private String content;
    private String path;
    public Course(){

    }


    public Course(String title){
        this.title = title;

    }
    public String getTitle(){
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
    /*
    public Course(String content){
        this.content = content;

    }
    public String getContent(){
        return content;
    }*/

}

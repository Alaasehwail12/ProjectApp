package com.example.projectlabandroid;

public class Course {

    private String CNum;
    private String Ctitle;
    private String CTopics;
    private String prerequisites;
    private byte [] photo;


    public Course(){

    }

    public Course(String CNum, String ctitle, String CTopics, String prerequisites, byte[] photo) {
        this.CNum = CNum;
        Ctitle = ctitle;
        this.CTopics = CTopics;
        this.prerequisites = prerequisites;
        this.photo = photo;
    }

    public String getCNum() {
        return CNum;
    }

    public void setCNum(String CNum) {
        this.CNum = CNum;
    }

    public String getCtitle() {
        return Ctitle;
    }

    public void setCtitle(String ctitle) {
        Ctitle = ctitle;
    }

    public String getCTopics() {
        return CTopics;
    }

    public void setCTopics(String CTopics) {
        this.CTopics = CTopics;
    }

    public String getPrerequisites() {
        return prerequisites;
    }

    public void setPrerequisites(String prerequisites) {
        this.prerequisites = prerequisites;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    @Override
    public String toString() {
        return "Course{" +
                "CNum='" + CNum + '\'' +
                ", Ctitle='" + Ctitle + '\'' +
                ", CTopics='" + CTopics + '\'' +
                ", prerequisites='" + prerequisites + '\'' +
                ", photo=" + photo +
                '}';
    }
}
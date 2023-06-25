package com.example.projectlabandroid;

import java.sql.Time;
import java.util.Arrays;
import java.util.Date;

public class Course {

    private String CNum;
    private String Ctitle;
    private String CTopics;
    private String prerequisites;
    private byte [] photo;

    private String deadline;

    private String startDateCourse;

    private String schedule;

    private String venue;


    public Course(){

    }

    public Course(String CNum, String ctitle, String CTopics, String prerequisites, byte[] photo,String deadline,
                  String startDateCourse,String schedule,String venue) {
        this.CNum = CNum;
        Ctitle = ctitle;
        this.CTopics = CTopics;
        this.prerequisites = prerequisites;
        this.photo = photo;
        this.deadline=deadline;
        this.startDateCourse=startDateCourse;
        this.schedule=schedule;
        this.venue=venue;
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

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public String getStartDateCourse() {
        return startDateCourse;
    }

    public void setStartDateCourse(String startDateCourse) {
        this.startDateCourse = startDateCourse;
    }

    public String getSchedule() {
        return schedule;
    }

    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    @Override
    public String toString() {
        return "Course{" +
                "CNum='" + CNum + '\'' +
                ", Ctitle='" + Ctitle + '\'' +
                ", CTopics='" + CTopics + '\'' +
                ", prerequisites='" + prerequisites + '\'' +
                ", photo=" + Arrays.toString(photo) +
                ", deadline=" + deadline +
                ", startDateCourse=" + startDateCourse +
                ", schedule=" + schedule +
                ", venue='" + venue + '\'' +
                '}';
    }
}
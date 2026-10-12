package com.comp3074.mirpour.jamshaid.studentplanner;

public class CourseItem {
    public int id;
    public String name;
    public String day;
    public String startTime;
    public String endTime;

    public CourseItem(int id, String name, String day, String startTime, String endTime) {
        this.id = id;
        this.name = name;
        this.day = day;
        this.startTime = startTime;
        this.endTime = endTime;
    }
}

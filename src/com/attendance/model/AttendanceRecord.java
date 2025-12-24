package com.attendance.model;

import java.util.Date;

public class AttendanceRecord {
    private int id, studentId;
    private String studentName, rollNo;
    private Date date;
    private String status;

    public AttendanceRecord(int id, int studentId, String studentName, String rollNo, Date date, String status) {
        this.id = id;
        this.studentId = studentId;
        this.studentName = studentName;
        this.rollNo = rollNo;
        this.date = date;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public int getStudentId() {
        return studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public String getRollNo() {
        return rollNo;
    }

    public Date getDate() {
        return date;
    }

    public String getStatus() {
        return status;
    }
}

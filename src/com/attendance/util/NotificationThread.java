package com.attendance.util;
public class NotificationThread extends Thread{
    private String studentName;
    public NotificationThread(String studentName){this.studentName=studentName;}
    public void run(){System.out.println("Notification sent to: "+studentName);}
}

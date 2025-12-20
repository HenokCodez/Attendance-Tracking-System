# Attendance Tracking System

A simple Java Servlet and JSP based application for tracking student attendance.

## Project Structure

```
AttendanceTrackingSystem/
│
├─ src/
│  ├─ com/attendance/controller/   // Servlets
│  ├─ com/attendance/model/        // POJOs
│  ├─ com/attendance/util/         // Utilities
│
├─ WebContent/
│  ├─ index.jsp
│  ├─ admin.jsp
│  ├─ report.jsp
│  ├─ css/
│  ├─ js/
│
├─ WEB-INF/
│  ├─ web.xml
```

## Setup

1. Import the project into your IDE (Eclipse, IntelliJ).
2. Ensure you have a Servlet Container (e.g., Apache Tomcat) configured.
3. Configure the database in `src/com/attendance/util/DBConnection.java`.
4. Add necessary JARs (`servlet-api.jar`, JDBC driver) to your classpath/build path.
5. Deploy and run on the server.

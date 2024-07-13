package lk.ijse.studentmanagement.controller;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lk.ijse.studentmanagement.dto.Student;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.UUID;

@WebServlet(urlPatterns = "/student")
public class StudentController extends HttpServlet {
    Connection connection;
    static String GET_STUDENT = "SELECT*FROM student";
    static String SAVE_STUDENT = "INSERT INTO student (id,name,email,city,level) VALUES (?,?,?,?,?)";
    static String UPDATE_STUDENT = "UPDATE student SET name = ? , email = ? , city = ? , level = ? WHERE id = ?";
    static String DELETE_STUDENT = "DELETE student WHERE id = ?";

    @Override
    public void init() throws ServletException {
        try {
            var driver = getServletContext().getInitParameter("driver-class");
            var dbUrl = getServletContext().getInitParameter("dbURL");
            var userName = getServletContext().getInitParameter("dbUserName");
            var password = getServletContext().getInitParameter("dbPassword");
            Class.forName(driver);
            this.connection =  DriverManager.getConnection(dbUrl,userName,password);
        }catch (ClassNotFoundException | SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //Todo: Save student
        if(!req.getContentType().toLowerCase().startsWith("application/json")|| req.getContentType() == null){
            //send error
            resp.sendError(HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE);
        }
        String id  = UUID.randomUUID().toString();
        Jsonb jsonb = JsonbBuilder.create();
        Student studentDTO = jsonb.fromJson(req.getReader(), Student.class);
        studentDTO.setId(id);
        System.out.println(studentDTO);
        // Persist Data
        try {
            var ps = connection.prepareStatement(SAVE_STUDENT);
            ps.setString(1, studentDTO.getId());
            ps.setString(2, studentDTO.getName());
            ps.setString(3, studentDTO.getEmail());
            ps.setString(4, studentDTO.getCity());
            ps.setString(4, studentDTO.getLevel());
            if(ps.executeUpdate() != 0){
                resp.getWriter().write("Student Saved");
                resp.setStatus(HttpServletResponse.SC_CREATED  );
            }else {
                resp.getWriter().write("Student Not Saved");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if(!req.getContentType().toLowerCase().startsWith("application/json")|| req.getContentType() == null){
            //send error
            resp.sendError(HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE);
        }
        try {
            var ps = this.connection.prepareStatement(UPDATE_STUDENT);
            var studentId = req.getParameter("id");
            Jsonb jsonb = JsonbBuilder.create();
            var UpdateStudent = new Student();
            ps.setString(1, UpdateStudent.getName());
            ps.setString(2, UpdateStudent.getEmail());
            ps.setString(3, UpdateStudent.getCity());
            ps.setString(4, UpdateStudent.getLevel());
            ps.setString(5, studentId);
            if(ps.executeUpdate() != 0){
                resp.getWriter().write("Student Update");
                resp.setStatus(HttpServletResponse.SC_CREATED  );
            }else {
                resp.getWriter().write("Student Not Updated");
                resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!req.getContentType().toLowerCase().startsWith("application/json") || req.getContentType() == null) {
            //send error
            resp.sendError(HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE);
        }
        try {
            var ps = this.connection.prepareStatement(DELETE_STUDENT);
            var studentId = req.getParameter("id");
            Jsonb jsonb = JsonbBuilder.create();
            ps.setString(5, studentId);
            if(ps.executeUpdate() != 0){
                resp.getWriter().write("Student Deleted");
                resp.setStatus(HttpServletResponse.SC_CREATED  );
            }else {
                resp.getWriter().write("Student Not Deleted");
                resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if(!req.getContentType().toLowerCase().startsWith("application/json")|| req.getContentType() == null){
            //send error
            resp.sendError(HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE);
        }
        String id  = UUID.randomUUID().toString();
        Jsonb jsonb = JsonbBuilder.create();
        Student studentDTO = jsonb.fromJson(req.getReader(), Student.class);
        studentDTO.setId(id);
        System.out.println(studentDTO);
        // Persist Data
        try {
            var ps = connection.prepareStatement(GET_STUDENT);
            if(ps.executeUpdate() != 0){
                resp.getWriter().write("Student Saved");
                resp.setStatus(HttpServletResponse.SC_CREATED  );
            }else {
                resp.getWriter().write("Student Not Saved");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}


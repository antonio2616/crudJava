/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ciber.javafxcrud.database;

import java.sql.ResultSet;
import java.sql.Statement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author PC-7
 */
public class AppQuery {
    
    private DBConnection c = new DBConnection();
    
    public void addStudent(com.ciber.javafxcrud.model.Student student){
        try {
            c.getDBConn();
            java.sql.PreparedStatement ps = c.getCon().prepareStatement("insert into student (nombre, apellidopaterno, apellidomaterno)values(?,?,?)");
            ps.setString(1, student.getNombre());
            ps.setString(2, student.getApellidopaterno());
            ps.setString(3, student.getApellidomaterno());
            ps.execute();
            ps.close();
            c.closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
    
    public ObservableList<com.ciber.javafxcrud.model.Student> getStudentList(){
        ObservableList<com.ciber.javafxcrud.model.Student> studentList = FXCollections.observableArrayList();
        try {
            String query = "select * from student order by apellidopaterno asc";
            c.getDBConn();
            Statement st = c.getCon().createStatement();
            ResultSet rs = st.executeQuery(query);
            com.ciber.javafxcrud.model.Student s;
            while (rs.next()){
                s = new com.ciber.javafxcrud.model.Student(rs.getInt("id"), rs.getString("nombre"), rs.getString("apellidopaterno"), rs.getString("apellidomaterno"));
                studentList.add(s);
            }
            rs.close();
            st.close();
            c.closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return studentList;
    }
    
    public void updateStudent (com.ciber.javafxcrud.model.Student student){
        
        try {
            c.getDBConn();
            java.sql.PreparedStatement ps = c.getCon().prepareStatement("UPDATE `student`\n" +
                    "SET \n" +
                    "`nombre` = ?, \n" +
                    "`apellidopaterno` = ?, \n" +
                    "`apellidomaterno` = ? \n" +
                    "WHERE `id` = ?");
            ps.setString(1, student.getNombre());
            ps.setString(2, student.getApellidopaterno());
            ps.setString(3, student.getApellidomaterno());
            ps.setInt(4, student.getId());
            ps.execute();
            ps.close();
            c.closeConnection();
            
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    
    public void deleteStudent(com.ciber.javafxcrud.model.Student student){
        try {
            c.getDBConn();
            java.sql.PreparedStatement ps = c.getCon().prepareStatement("DELETE FROM `student` \n "
            + "WHERE id = ?;");
            ps.setInt(1, student.getId());
            ps.execute();
            ps.close();
            c.closeConnection();
            
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
    }
}

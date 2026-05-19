/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.g3studentmanagementsystem;

import java.awt.event.*;
import javax.swing.*;


public class AddStudentData implements ActionListener{
    
    private AddStudentPage addstudents;
    
    public AddStudentData(AddStudentPage addstudents){
        this. addstudents = addstudents;
        
    }

    //Add Button Function
    private void addButton(){
        
        String studId = addstudents.txtStudentId.getText();
        String name = addstudents.txtName.getText();
        String section = addstudents.txtSection.getText();
        String gender = addstudents.txtGender.getText();
        String birthDate = addstudents.txtBirthDate.getText();
        String email = addstudents.txtEmail.getText();
        String grades = addstudents.txtGrades.getText();
        
        //Add Student Validation
       if (studId.isEmpty() || name.isEmpty() || section.isEmpty() || gender.isEmpty() || birthDate.isEmpty() || email.isEmpty() || grades.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please fill all fields!");
            return;
       }
       else{
           JOptionPane.showMessageDialog(null, "Student Added Successfully...");
           
           clearInput();
       }
    }
    
      //Clear Input Function
    private void clearInput(){
        
        addstudents.txtStudentId.setText("");
        addstudents.txtName.setText("");
        addstudents.txtSection.setText("");
        addstudents.txtGender.setText("");
        addstudents.txtBirthDate.setText("");
        addstudents.txtEmail.setText("");
        addstudents.txtGrades.setText("");
    }
    
    //Cancel Button Function
    private void cancelButton(){
        
        int confirmMessage = JOptionPane.showConfirmDialog(null, "Are you sure want to cancel?","Confirm", JOptionPane.YES_NO_OPTION);
        
        if (confirmMessage == JOptionPane.YES_NO_OPTION){
            addstudents.dispose();
        }
    }
    
    
     @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addstudents.btnAdd){
	   addButton();
    } 
        else if (e.getSource() == addstudents.btnCancel){
           cancelButton(); 
    }
    
}
    

    }
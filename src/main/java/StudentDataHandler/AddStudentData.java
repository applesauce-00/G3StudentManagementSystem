package StudentDataHandler;


import com.mycompany.g3studentmanagementsystem.AddStudentPage;
import java.awt.event.*;
import javax.swing.*;


public class AddStudentData implements ActionListener{
    
    private AddStudentPage addstudents;
    
    public AddStudentData(AddStudentPage addstudent){
        this. addstudents = addstudents;
        
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
    
    //Cancel Button Function
    private void cancelButton(){
        
        int confirmMessage = JOptionPane.showConfirmDialog(null, "Are you sure want to cancel?","Confirm", JOptionPane.YES_NO_OPTION);
        
        if (confirmMessage == JOptionPane.YES_NO_OPTION){
            addstudents.dispose();
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

     }
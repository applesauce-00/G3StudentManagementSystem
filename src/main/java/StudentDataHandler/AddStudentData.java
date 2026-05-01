package StudentDataHandler;


import com.mycompany.g3studentmanagementsystem.AddStudentPage;
import java.awt.event.*;
import javax.swing.*;


public class AddStudentData implements ActionListener{
    
    private AddStudentPage addstudent;
    
    public AddStudentData(AddStudentPage addstudent){
        this. addstudent = addstudent;
        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addstudent.btnAdd){
	   addButton();
    } 
        else if (e.getSource() == addstudent.btnCancel){
           cancelButton(); 
    }
    
}
    //Add Button Function
    private void addButton(){
        
        String studId = addstudent.txtStudentId.getText();
        String name = addstudent.txtName.getText();
        String section = addstudent.txtSection.getText();
        String gender = addstudent.txtGender.getText();
        String birthDate = addstudent.txtBirthDate.getText();
        String email = addstudent.txtEmail.getText();
        String grades = addstudent.txtGrades.getText();
        
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
            addstudent.dispose();
        }
    }
    
    //Clear Input Function
    private void clearInput(){
        
        addstudent.txtStudentId.setText("");
        addstudent.txtName.setText("");
        addstudent.txtSection.setText("");
        addstudent.txtGender.setText("");
        addstudent.txtBirthDate.setText("");
        addstudent.txtEmail.setText("");
        addstudent.txtGrades.setText("");
        
    }

     }
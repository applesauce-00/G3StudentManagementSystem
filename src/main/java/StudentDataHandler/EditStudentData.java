package StudentDataHandler;

import com.mycompany.g3studentmanagementsystem.EditStudentPage;
import java.awt.event.*;
import javax.swing.*;

public class EditStudentData implements ActionListener{
    
    private EditStudentPage editstudents;
    
    public EditStudentData (EditStudentPage editstudent){
        this. editstudents = editstudents;
        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == editstudents.btnEdit){
	   editButton();
    } 
        else if (e.getSource() == editstudents.btnCancel){
           cancelButton(); 
    }
    }
    
    //Edit Button Function
    private void editButton(){
        
        String studId = editstudents.txtStudentId.getText();
        String name = editstudents.txtName.getText();
        String section = editstudents.txtSection.getText();
        String gender = editstudents.txtGender.getText();
        String birthDate = editstudents.txtBirthDate.getText();
        String email = editstudents.txtEmail.getText();
        String grades = editstudents.txtGrades.getText();
        
        //Validation
        if (studId.isEmpty() || name.isEmpty() || section.isEmpty() || gender.isEmpty() || birthDate.isEmpty() || email.isEmpty() || grades.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please fill all fields!");
            return;
       }
       else{
           JOptionPane.showMessageDialog(null, "Changes saved successfully!");
           
           clearInput();
       }
    }
    
    //Cancel Button Function
    private void cancelButton(){
        
        int confirmMessage = JOptionPane.showConfirmDialog(null, "Are you sure want to cancel?","Confirm", JOptionPane.YES_NO_OPTION);
        
        if (confirmMessage == JOptionPane.YES_NO_OPTION){
            editstudents.dispose();
        }
    }
    
    //Clear Input Function
    private void clearInput(){
        
        editstudents.txtStudentId.setText("");
        editstudents.txtName.setText("");
        editstudents.txtSection.setText("");
        editstudents.txtGender.setText("");
        editstudents.txtBirthDate.setText("");
        editstudents.txtEmail.setText("");
        editstudents.txtGrades.setText("");
        
    }
    
}

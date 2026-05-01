package StudentDataHandler;

import com.mycompany.g3studentmanagementsystem.EditStudentPage;
import java.awt.event.*;
import javax.swing.*;

public class EditStudentData implements ActionListener{
    
    private EditStudentPage editstudent;
    
    public EditStudentData (EditStudentPage editstudent){
        this. editstudent = editstudent;
        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == editstudent.btnEdit){
	   editButton();
    } 
        else if (e.getSource() == editstudent.btnCancel){
           cancelButton(); 
    }
    }
    
    //Edit Button Function
    private void editButton(){
        
        String studId = editstudent.txtStudentId.getText();
        String name = editstudent.txtName.getText();
        String section = editstudent.txtSection.getText();
        String gender = editstudent.txtGender.getText();
        String birthDate = editstudent.txtBirthDate.getText();
        String email = editstudent.txtEmail.getText();
        String grades = editstudent.txtGrades.getText();
        
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
            editstudent.dispose();
        }
    }
    
    //Clear Input Function
    private void clearInput(){
        
        editstudent.txtStudentId.setText("");
        editstudent.txtName.setText("");
        editstudent.txtSection.setText("");
        editstudent.txtGender.setText("");
        editstudent.txtBirthDate.setText("");
        editstudent.txtEmail.setText("");
        editstudent.txtGrades.setText("");
        
    }
    
}

package school;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import org.bson.Document;

public class StudentGUI extends JFrame {
    private JTextField txtAddress;
    private JTextField txtClassId;
    private JTextField txtContactNo;
    private JTextField txtCourseId;
    private JTextField txtDOB;
    private JTextField txtEmailId;
    private JTextField txtFirstName;
    private JTextField txtLastName;
    private JTextField txtStudentId;
    private JTextField txtTeacherId;
    private JButton btnInsert;
    private JButton btnDelete;
    private JButton btnUpdate;
    private JButton btnFind;

    private MongoClient mongoClient;
    private MongoDatabase database;
    private MongoCollection<Document> collection;

    public StudentGUI() {
        // Set up the main frame
        setTitle("Student Information");
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create labels
        JLabel lblAddress = new JLabel("Address:");
        JLabel lblClassId = new JLabel("Class ID:");
        JLabel lblContactNo = new JLabel("Contact No:");
        JLabel lblCourseId = new JLabel("Course ID:");
        JLabel lblEmailId = new JLabel("Email ID:");
        JLabel lblFirstName = new JLabel("First Name:");
        JLabel lblLastName = new JLabel("Last Name:");
        JLabel lblStudentId = new JLabel("Student ID:");
        JLabel lblTeacherId = new JLabel("Teacher ID:");

        // Create text fields
        txtAddress = new JTextField();
        txtClassId = new JTextField();
        txtContactNo = new JTextField();
        txtCourseId = new JTextField();
        txtEmailId = new JTextField();
        txtFirstName = new JTextField();
        txtLastName = new JTextField();
        txtStudentId = new JTextField();
        txtTeacherId = new JTextField();

        // Create buttons
        btnInsert = new JButton("Insert");
        btnDelete = new JButton("Delete");
        btnUpdate = new JButton("Update");
        btnFind = new JButton("Find");

        // Add action listeners to buttons
        btnInsert.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                insertStudentData();
            }
        });

        btnDelete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deleteStudentData();
            }
        });

        btnUpdate.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateStudentData();
            }
        });

        btnFind.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                findStudentData();
            }
        });

        // Create a panel for the input fields
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(9, 2));
        
        inputPanel.add(lblStudentId);
        inputPanel.add(txtStudentId);
        
        inputPanel.add(lblFirstName);
        inputPanel.add(txtFirstName);
        
        inputPanel.add(lblLastName);
        inputPanel.add(txtLastName);
        
        inputPanel.add(lblAddress);
        inputPanel.add(txtAddress);
        
        inputPanel.add(lblContactNo);
        inputPanel.add(txtContactNo);
        
        inputPanel.add(lblEmailId);
        inputPanel.add(txtEmailId);
        
        inputPanel.add(lblClassId);
        inputPanel.add(txtClassId);
        
        inputPanel.add(lblCourseId);
        inputPanel.add(txtCourseId);
        
        inputPanel.add(lblTeacherId);
        inputPanel.add(txtTeacherId);

        // Create a panel for the buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(btnInsert);
        buttonPanel.add(btnDelete);
        buttonPanel.add(btnUpdate);
        buttonPanel.add(btnFind);

        // Create the main content pane
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());
        contentPane.add(inputPanel, BorderLayout.CENTER);
        contentPane.add(buttonPanel, BorderLayout.SOUTH);

        // Connect to MongoDB
        mongoClient = MongoClients.create("mongodb://localhost:27017");
        database = mongoClient.getDatabase("school");
        collection = database.getCollection("students");
    }

    private void insertStudentData() {
        // Get the input values from text fields
        String address = txtAddress.getText();
        int classId = Integer.parseInt(txtClassId.getText());
        int contactNo = Integer.parseInt(txtContactNo.getText());
        int courseId = Integer.parseInt(txtCourseId.getText());
        String emailId = txtEmailId.getText();
        String firstName = txtFirstName.getText();
        String lastName = txtLastName.getText();
        int studentId = Integer.parseInt(txtStudentId.getText());
        int teacherId = Integer.parseInt(txtTeacherId.getText());

        // Create a document to insert into the collection
        Document document = new Document();
        document.append("student_id", studentId)
                .append("first_name", firstName)
                .append("last_name", lastName)
                .append("address", address)
                .append("email_id", emailId)
                .append("contact_no", contactNo)
                .append("class_id", classId)
                .append("course_id", courseId)
                .append("teacher_id", teacherId);

        // Insert the document into the collection
        collection.insertOne(document);

        JOptionPane.showMessageDialog(this, "Student data inserted successfully");
    }

    private void deleteStudentData() {
        // Get the input value from the studentId text field
        int studentId = Integer.parseInt(txtStudentId.getText());

        // Delete the document from the collection based on the studentId
        collection.deleteOne(Filters.eq("student_id", studentId));

        JOptionPane.showMessageDialog(this, "Student data deleted successfully");
    }

    private void updateStudentData() {
        // Get the input values from text fields
        String firstName = txtFirstName.getText();
        String lastName = txtLastName.getText();
        String address = txtAddress.getText();
        int classId = Integer.parseInt(txtClassId.getText());
        int contactNo = Integer.parseInt(txtContactNo.getText());
        int courseId = Integer.parseInt(txtCourseId.getText());
        String emailId = txtEmailId.getText();
        int studentId = Integer.parseInt(txtStudentId.getText());
        int teacherId = Integer.parseInt(txtTeacherId.getText());

        // Create a document with updated values
        Document updatedDocument = new Document();
        updatedDocument.append("first_name", firstName)
                .append("last_name", lastName)
                .append("address", address)
                .append("email_id", emailId)
                .append("contact_no", contactNo)
                .append("class_id", classId)
                .append("course_id", courseId)
                .append("teacher_id", teacherId);

        // Update the document in the collection based on the studentId
        collection.updateOne(Filters.eq("student_id", studentId), new Document("$set", updatedDocument));

        JOptionPane.showMessageDialog(this, "Student data updated successfully");
    }

    private void findStudentData() {
        // Get the input value from the studentId text field
        int studentId = Integer.parseInt(txtStudentId.getText());

        // Find the
        Document document = collection.find(Filters.eq("student_id", studentId)).first();
    if (document != null) {
        // Display the found student data
        txtAddress.setText(document.getString("address"));
        txtClassId.setText(String.valueOf(document.getInteger("class_id")));
        txtContactNo.setText(document.getString("contact_no"));
        txtCourseId.setText(String.valueOf(document.getInteger("course_id")));
        txtEmailId.setText(document.getString("email_id"));
        txtFirstName.setText(document.getString("first_name")); // Set first name
        txtLastName.setText(document.getString("last_name")); // Set last name
        txtTeacherId.setText(String.valueOf(document.getInteger("teacher_id")));

        JOptionPane.showMessageDialog(this, "Student data found");
    } else {
        JOptionPane.showMessageDialog(this, "Student data not found");
    }
    }
}

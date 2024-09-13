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

public class TeacherGUI extends JFrame {
    private JTextField txtAddress;
    private JTextField txtFirstName;
    private JTextField txtLastName;
    private JTextField txtDOB;
    private JTextField txtEmailId;
    private JTextField txtContactNo;
    private JTextField txtTeacherId;
    private JTextField txtQualification;
    private JTextField txtDeptId;
    private JButton btnInsert;
    private JButton btnDelete;
    private JButton btnUpdate;
    private JButton btnFind;

    private MongoClient mongoClient;
    private MongoDatabase database;
    private MongoCollection<Document> collection;

    public TeacherGUI() {
        setTitle("Teacher Information");
        setSize(400, 500);
        setLayout(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JLabel lblTeacherId = new JLabel("Teacher ID:");
        lblTeacherId.setBounds(30, 30, 100, 25);
        add(lblTeacherId);
        txtTeacherId = new JTextField();
        txtTeacherId.setBounds(140, 30, 200, 25);
        add(txtTeacherId);

        JLabel lblFirstName = new JLabel("First Name:");
        lblFirstName.setBounds(30, 70, 100, 25);
        add(lblFirstName);
        txtFirstName = new JTextField();
        txtFirstName.setBounds(140, 70, 200, 25);
        add(txtFirstName);

        JLabel lblLastName = new JLabel("Last Name:");
        lblLastName.setBounds(30, 110, 100, 25);
        add(lblLastName);
        txtLastName = new JTextField();
        txtLastName.setBounds(140, 110, 200, 25);
        add(txtLastName);

        JLabel lblAddress = new JLabel("Address:");
        lblAddress.setBounds(30, 150, 100, 25);
        add(lblAddress);
        txtAddress = new JTextField();
        txtAddress.setBounds(140, 150, 200, 25);
        add(txtAddress);

        JLabel lblDOB = new JLabel("Date of Birth:");
        lblDOB.setBounds(30, 190, 100, 25);
        add(lblDOB);
        txtDOB = new JTextField();
        txtDOB.setBounds(140, 190, 200, 25);
        add(txtDOB);

        JLabel lblEmailId = new JLabel("Email ID:");
        lblEmailId.setBounds(30, 230, 100, 25);
        add(lblEmailId);
        txtEmailId = new JTextField();
        txtEmailId.setBounds(140, 230, 200, 25);
        add(txtEmailId);

        JLabel lblContactNo = new JLabel("Contact No:");
        lblContactNo.setBounds(30, 270, 100, 25);
        add(lblContactNo);
        txtContactNo = new JTextField();
        txtContactNo.setBounds(140, 270, 200, 25);
        add(txtContactNo);

        JLabel lblQualification = new JLabel("Qualification:");
        lblQualification.setBounds(30, 310, 100, 25);
        add(lblQualification);
        txtQualification = new JTextField();
        txtQualification.setBounds(140, 310, 200, 25);
        add(txtQualification);

        JLabel lblDeptId = new JLabel("Department ID:");
        lblDeptId.setBounds(30, 350, 100, 25);
        add(lblDeptId);
        txtDeptId = new JTextField();
        txtDeptId.setBounds(140, 350, 200, 25);
        add(txtDeptId);

        btnInsert = new JButton("Insert");
        btnInsert.setBounds(30, 390, 80, 30);
        add(btnInsert);
        btnInsert.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                insertTeacherData();
            }
        });

        btnDelete = new JButton("Delete");
        btnDelete.setBounds(120, 390, 80, 30);
        add(btnDelete);
        btnDelete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deleteTeacherData();
            }
        });

        btnUpdate = new JButton("Update");
        btnUpdate.setBounds(210, 390, 80, 30);
        add(btnUpdate);
        btnUpdate.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateTeacherData();
            }
        });

        btnFind = new JButton("Find");
        btnFind.setBounds(300, 390, 80, 30);
        add(btnFind);
        btnFind.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                findTeacherData();
            }
        });

        // Connect to MongoDB
        mongoClient = MongoClients.create("mongodb://localhost:27017");
        database = mongoClient.getDatabase("school");
        collection = database.getCollection("teacher");
    }

    private void insertTeacherData() {
        String address = txtAddress.getText();
        String firstName = txtFirstName.getText();
        String lastName = txtLastName.getText();
        String dob = txtDOB.getText();
        String emailId = txtEmailId.getText();
        String contactNo = txtContactNo.getText();
        int teacherId = Integer.parseInt(txtTeacherId.getText());
        String qualification = txtQualification.getText();
        int deptId = Integer.parseInt(txtDeptId.getText());

        Document document = new Document();
        document.append("address", address)
                .append("first_name", firstName)
                .append("last_name", lastName)
                .append("dob", dob)
                .append("email_id", emailId)
                .append("contact_no", contactNo)
                .append("teacher_id", teacherId)
                .append("qualification", qualification)
                .append("dept_id", deptId);

        collection.insertOne(document);

        JOptionPane.showMessageDialog(this, "Teacher data inserted successfully");
    }

    private void deleteTeacherData() {
        int teacherId = Integer.parseInt(txtTeacherId.getText());

        collection.deleteOne(Filters.eq("teacher_id", teacherId));

        JOptionPane.showMessageDialog(this, "Teacher data deleted successfully");
    }

    private void updateTeacherData() {
        String address = txtAddress.getText();
        String firstName = txtFirstName.getText();
        String lastName = txtLastName.getText();
        String dob = txtDOB.getText();
        String emailId = txtEmailId.getText();
        String contactNo = txtContactNo.getText();
        int teacherId = Integer.parseInt(txtTeacherId.getText());
        String qualification = txtQualification.getText();
        int deptId = Integer.parseInt(txtDeptId.getText());

        Document updatedDocument = new Document();
        updatedDocument.append("address", address)
                .append("firs_tname", firstName)
                .append("last_name", lastName)
                .append("dob", dob)
                .append("email_id", emailId)
                .append("contact_no", contactNo)
                .append("qualification", qualification)
                .append("dept_id", deptId);

        collection.updateOne(Filters.eq("teacher_id", teacherId), new Document("$set", updatedDocument));

        JOptionPane.showMessageDialog(this, "Teacher data updated successfully");
    }

    private void findTeacherData() {
        int teacherId = Integer.parseInt(txtTeacherId.getText());

        Document document = collection.find(Filters.eq("teacher_id", teacherId)).first();

        if (document != null) {
            txtAddress.setText(document.getString("address"));
            txtFirstName.setText(document.getString("first_name"));
            txtLastName.setText(document.getString("last_name"));
            txtDOB.setText(document.getString("dob"));
            txtEmailId.setText(document.getString("email_id"));
            txtContactNo.setText(document.getString("contact_no"));
            txtQualification.setText(document.getString("qualification"));
            txtDeptId.setText(String.valueOf(document.getInteger("dept_id")));

            JOptionPane.showMessageDialog(this, "Teacher data found");
        } else {
            JOptionPane.showMessageDialog(this, "Teacher data not found");
        }
    }

    public void closeMongoClient() {
        if (mongoClient != null) {
            mongoClient.close();
        }
    }
}

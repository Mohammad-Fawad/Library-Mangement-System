package school;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.bson.Document;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
// import com.mongodb.client.model.Filters;
// import com.mongodb.client.model.Updates;
// import com.mongodb.client.model.Projections;

public class StaffGUI extends JFrame implements ActionListener {
    private JTextField txtPersonId, txtFirstName, txtLastName, txtDob, txtAddress, txtEmailId, txtContactNo, txtStaffId,
            txtPosition, txtQualification, txtDeptId;
    private JButton btnInsert, btnDelete, btnUpdate, btnFind;
    private MongoClient mongoClient;
    private MongoDatabase database;
    private MongoCollection<Document> collection;

    public StaffGUI() {
        setTitle("Staff Form");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 520);
        setLocationRelativeTo(null);
        setLayout(null);

        JLabel lblPersonId = new JLabel("Person ID:");
        lblPersonId.setBounds(30, 30, 100, 25);
        add(lblPersonId);

        txtPersonId = new JTextField();
        txtPersonId.setBounds(140, 30, 200, 25);
        add(txtPersonId);

        // Add other JLabel and JTextField components...

        btnInsert = new JButton("Insert");
        btnInsert.setBounds(50, 480, 100, 30);
        btnInsert.addActionListener(this);
        add(btnInsert);

        btnDelete = new JButton("Delete");
        btnDelete.setBounds(160, 480, 100, 30);
        btnDelete.addActionListener(this);
        add(btnDelete);

        btnUpdate = new JButton("Update");
        btnUpdate.setBounds(270, 480, 100, 30);
        btnUpdate.addActionListener(this);
        add(btnUpdate);

        btnFind = new JButton("Find");
        btnFind.setBounds(270, 30, 100, 25);
        btnFind.addActionListener(this);
        add(btnFind);

        setVisible(true);

        // Connect to MongoDB
        mongoClient = MongoClients.create("mongodb://localhost:27017");
        database = mongoClient.getDatabase("school");
        collection = database.getCollection("staff");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnInsert) {
            saveStaffData();
        } else if (e.getSource() == btnDelete) {
            deleteStaffData();
        } else if (e.getSource() == btnUpdate) {
            updateStaffData();
        } else if (e.getSource() == btnFind) {
            findStaffData();
        }
    }

    private void saveStaffData() {
        // Get the input values from text fields
        int personId = Integer.parseInt(txtPersonId.getText());
        String firstName = txtFirstName.getText();
        String lastName = txtLastName.getText();
        String dob = txtDob.getText();
        String address = txtAddress.getText();
        String emailId = txtEmailId.getText();
        String contactNo = txtContactNo.getText();
        int staffId = Integer.parseInt(txtStaffId.getText());
        String position = txtPosition.getText();
        String qualification = txtQualification.getText();
        int deptId = Integer.parseInt(txtDeptId.getText());

        // Create a document to insert into the collection
        Document document = new Document();
        document.append("person_id", personId)
                .append("firstname", firstName)
                .append("lastname", lastName)
                .append("dob", dob)
                .append("address", address)
                .append("email_id", emailId)
                .append("contact_no", contactNo)
                .append("staff_id", staffId)
                .append("position", position)
                .append("qualification", qualification)
                .append("dept_id", deptId);

        // Insert the document into the collection
        collection.insertOne(document);

        JOptionPane.showMessageDialog(this, "Staff data inserted successfully!");
    }

    private void deleteStaffData() {
        int staffId = Integer.parseInt(txtStaffId.getText());

        // Create the filter to find the document to delete
        Document filter = new Document("staff_id", staffId);

        // Delete the document from the collection
        collection.deleteOne(filter);

        JOptionPane.showMessageDialog(this, "Staff data deleted successfully!");
    }

    private void updateStaffData() {
        int staffId = Integer.parseInt(txtStaffId.getText());

        // Create the filter to find the document to update
        Document filter = new Document("staff_id", staffId);

        // Get the updated values from text fields
        String firstName = txtFirstName.getText();
        String lastName = txtLastName.getText();
        String dob = txtDob.getText();
        String address = txtAddress.getText();
        String emailId = txtEmailId.getText();
        String contactNo = txtContactNo.getText();
        String position = txtPosition.getText();
        String qualification = txtQualification.getText();
        int deptId = Integer.parseInt(txtDeptId.getText());

        // Create the update document with the updated values
        Document update = new Document("$set", new Document()
                .append("firstname", firstName)
                .append("lastname", lastName)
                .append("dob", dob)
                .append("address", address)
                .append("email_id", emailId)
                .append("contact_no", contactNo)
                .append("position", position)
                .append("qualification", qualification)
                .append("dept_id", deptId));

        // Update the document in the collection
        collection.updateOne(filter, update);

        JOptionPane.showMessageDialog(this, "Staff data updated successfully!");
    }

    private void findStaffData() {
        int staffId = Integer.parseInt(txtStaffId.getText());

        // Create the filter to find the document
        Document filter = new Document("staff_id", staffId);

        // Perform the find operation and retrieve the matching document
        Document result = collection.find(filter).first();

        if (result != null) {
            // Extract the data from the document
            int personId = result.getInteger("person_id");
            String firstName = result.getString("firstname");
            String lastName = result.getString("lastname");
            String dob = result.getString("dob");
            String address = result.getString("address");
            String emailId = result.getString("email_id");
            String contactNo = result.getString("contact_no");
            String position = result.getString("position");
            String qualification = result.getString("qualification");
            int deptId = result.getInteger("dept_id");

            // Display the data in the text fields
            txtPersonId.setText(Integer.toString(personId));
            txtFirstName.setText(firstName);
            txtLastName.setText(lastName);
            txtDob.setText(dob);
            txtAddress.setText(address);
            txtEmailId.setText(emailId);
            txtContactNo.setText(contactNo);
            txtPosition.setText(position);
            txtQualification.setText(qualification);
            txtDeptId.setText(Integer.toString(deptId));
        } else {
            JOptionPane.showMessageDialog(this, "Staff data not found for the provided Staff ID.",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

}

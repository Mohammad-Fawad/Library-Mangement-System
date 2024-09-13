package school;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.bson.Document;

public class DepartmentGUI extends JFrame {
    private JTextField deptIdField, deptNameField;
    private JButton insertButton, deleteButton, updateButton, findButton;
    private MongoClient mongoClient;
    private MongoCollection<Document> department;

    public DepartmentGUI() {
        setTitle("Department Details");
        setSize(300, 200);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JLabel deptIdLabel = new JLabel("Department ID:");
        deptIdLabel.setBounds(30, 30, 100, 25);
        add(deptIdLabel);
        
        deptIdField = new JTextField();
        deptIdField.setBounds(140, 30, 150, 25);
        add(deptIdField);
        
        JLabel deptNameLabel = new JLabel("Department Name:");
        deptNameLabel.setBounds(30, 65, 110, 25);
        add(deptNameLabel);
        
        deptNameField = new JTextField();
        deptNameField.setBounds(140, 65, 150, 25);
        add(deptNameField);

        insertButton = new JButton("Insert");
        insertButton.setBounds(30, 110, 80, 30);
        add(insertButton);
        insertButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                insertDepartmentData();
            }
        });

        deleteButton = new JButton("Delete");
        deleteButton.setBounds(115, 110, 80, 30);
        add(deleteButton);
        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deleteDepartmentData();
            }
        });

        updateButton = new JButton("Update");
        updateButton.setBounds(200, 110, 80, 30);
        add(updateButton);
        updateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateDepartmentData();
            }
        });

        findButton = new JButton("Find");
        findButton.setBounds(285, 110, 80, 30);
        add(findButton);
        findButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                findDepartmentData();
            }
        });

        // Connect to MongoDB
        mongoClient = MongoClients.create("mongodb://localhost:27017");
        MongoDatabase database = mongoClient.getDatabase("school");
        department = database.getCollection("department");
    }

    public void insertDepartmentData() {
        int deptId = Integer.parseInt(deptIdField.getText());
        String deptName = deptNameField.getText();

        Document document = new Document("dept_id", deptId)
                .append("deptname", deptName);

        department.insertOne(document);

        JOptionPane.showMessageDialog(this, "Department data inserted successfully.");
    }

    public void deleteDepartmentData() {
        int deptId = Integer.parseInt(deptIdField.getText());

        department.deleteOne(Filters.eq("dept_id", deptId));

        JOptionPane.showMessageDialog(this, "Department data deleted successfully.");
    }

    public void updateDepartmentData() {
        int deptId = Integer.parseInt(deptIdField.getText());
        String deptName = deptNameField.getText();

        Document filter = new Document("dept_id", deptId);
        Document update = new Document("$set", new Document("deptname", deptName));

        department.updateOne(filter, update);

        JOptionPane.showMessageDialog(this, "Department data updated successfully.");
    }

    public void findDepartmentData() {
        int deptId = Integer.parseInt(deptIdField.getText());

        Document document = department.find(Filters.eq("dept_id", deptId)).first();

        if (document != null) {
            String deptName = document.getString("deptname");

            deptNameField.setText(deptName);

            JOptionPane.showMessageDialog(this, "Department data found!");
        } else {
            JOptionPane.showMessageDialog(this, "No department found with the provided ID.");
        }
    }

    public void closeMongoClient() {
        if (mongoClient != null) {
            mongoClient.close();
        }
    }
}

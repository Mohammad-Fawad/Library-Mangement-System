package school;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public class ClassGUI extends JFrame {
    private JTextField classIdField, sectionField, capacityField, teacherIdField;
    private JButton insertButton, deleteButton, updateButton, findButton;

    private final String DATABASE_NAME = "school";
    private final String COLLECTION_NAME = "class";
    private MongoClient mongoClient;
    private MongoDatabase database;
    private MongoCollection<Document> collection;

    public ClassGUI() {
        setTitle("Class Details");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(7, 2));

        JLabel classIdLabel = new JLabel("Class ID:");
        classIdField = new JTextField();
        JLabel sectionLabel = new JLabel("Section:");
        sectionField = new JTextField();
        JLabel capacityLabel = new JLabel("Capacity:");
        capacityField = new JTextField();
        JLabel teacherIdLabel = new JLabel("Teacher ID:");
        teacherIdField = new JTextField();

        insertButton = new JButton("Insert");
        insertButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                insertClassData();
            }
        });

        deleteButton = new JButton("Delete");
        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deleteClassData();
            }
        });

        updateButton = new JButton("Update");
        updateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateClassData();
            }
        });

        findButton = new JButton("Find");
        findButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                findClassData();
            }
        });

        panel.add(classIdLabel);
        panel.add(classIdField);
        panel.add(sectionLabel);
        panel.add(sectionField);
        panel.add(capacityLabel);
        panel.add(capacityField);
        panel.add(teacherIdLabel);
        panel.add(teacherIdField);
        panel.add(insertButton);
        panel.add(deleteButton);
        panel.add(updateButton);
        panel.add(findButton);

        add(panel);

        // Initialize MongoDB connection and collection
        mongoClient = MongoClients.create("mongodb://localhost:27017");
        database = mongoClient.getDatabase(DATABASE_NAME);
        collection = database.getCollection(COLLECTION_NAME);
    }

    public void insertClassData() {
        // Get the input values from text fields
        int classId = Integer.parseInt(classIdField.getText());
        String section = sectionField.getText();
        int capacity = Integer.parseInt(capacityField.getText());
        int teacherId = Integer.parseInt(teacherIdField.getText());

        // Create a document with the class data
        Document document = new Document("class_id", classId)
                .append("section", section)
                .append("capacity", capacity)
                .append("teacher_id", teacherId);

        // Insert the document into the collection
        collection.insertOne(document);

        JOptionPane.showMessageDialog(this, "Class data inserted successfully");
    }

    public void deleteClassData() {
        // Get the input values from text fields
        int classId = Integer.parseInt(classIdField.getText());

        // Create a filter for the class ID
        Document filter = new Document("class_id", classId);

        // Delete the document from the collection
        collection.deleteOne(filter);

        JOptionPane.showMessageDialog(this, "Class data deleted successfully");
    }

    public void updateClassData() {
        // Get the input values from text fields
        int classId = Integer.parseInt(classIdField.getText());
        String section = sectionField.getText();
        int capacity = Integer.parseInt(capacityField.getText());
        int teacherId = Integer.parseInt(teacherIdField.getText());

        // Create a filter for the class ID
        Document filter = new Document("class_id", classId);

        // Create an update document with the new values
        Document update = new Document("$set", new Document("section", section)
                .append("capacity", capacity)
                .append("teacher_id", teacherId));

        // Update the document in the collection
        collection.updateOne(filter, update);

        JOptionPane.showMessageDialog(this, "Class data updated successfully");
    }

    public void findClassData() {
        // Get the input values from text fields
        int classId = Integer.parseInt(classIdField.getText());

        // Create a filter for the class ID
        Document filter = new Document("class_id", classId);

        // Find the document in the collection
        Document document = collection.find(filter).first();

        if (document != null) {
            // Display the found class data
            sectionField.setText(document.getString("section"));
            capacityField.setText(Integer.toString(document.getInteger("capacity")));
            teacherIdField.setText(Integer.toString(document.getInteger("teacher_id")));
            JOptionPane.showMessageDialog(this, "Class data found");
        } else {
            JOptionPane.showMessageDialog(this, "Class data not found");
        }
    }

    public void close() {
        // Close the MongoDB connection
        mongoClient.close();
    }

    public static void main(String[] args) {
        ClassGUI classGUI = new ClassGUI();
        classGUI.setVisible(true);
    }
}

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

public class AttendanceGUI extends JFrame {
    private JTextField attendanceIdField, dateField, statusField, studentIdField, courseIdField;
    private JButton insertButton, deleteButton, updateButton, findButton;

    private final String DATABASE_NAME = "school";
    private final String COLLECTION_NAME = "attendance";
    private MongoClient mongoClient;
    private MongoDatabase database;
    private MongoCollection<Document> collection;

    public AttendanceGUI() {
        setTitle("Attendance Details");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(7, 2));

        JLabel attendanceIdLabel = new JLabel("Attendance ID:");
        attendanceIdField = new JTextField();
        JLabel dateLabel = new JLabel("Date:");
        dateField = new JTextField();
        JLabel statusLabel = new JLabel("Status:");
        statusField = new JTextField();
        JLabel studentIdLabel = new JLabel("Student ID:");
        studentIdField = new JTextField();
        JLabel teacherIdLabel = new JLabel("Course ID:");
        courseIdField = new JTextField();

        insertButton = new JButton("Insert");
        insertButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                insertAttendanceData();
            }
        });

        deleteButton = new JButton("Delete");
        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deleteAttendanceData();
            }
        });

        updateButton = new JButton("Update");
        updateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateAttendanceData();
            }
        });

        findButton = new JButton("Find");
        findButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                findAttendanceData();
            }
        });

        panel.add(attendanceIdLabel);
        panel.add(attendanceIdField);
        panel.add(dateLabel);
        panel.add(dateField);
        panel.add(statusLabel);
        panel.add(statusField);
        panel.add(studentIdLabel);
        panel.add(studentIdField);
        panel.add(teacherIdLabel);
        panel.add(courseIdField);
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

    public void insertAttendanceData() {
        // Get the input values from text fields
        int attendanceId = Integer.parseInt(attendanceIdField.getText());
        String date = dateField.getText();
        String status = statusField.getText();
        int studentId = Integer.parseInt(studentIdField.getText());
        int courseId = Integer.parseInt(courseIdField.getText());

        // Create a document with the attendance data
        Document document = new Document("attendance_id", attendanceId)
                .append("date", date)
                .append("status", status)
                .append("student_id", studentId)
                .append("course_id", courseId);

        // Insert the document into the collection
        collection.insertOne(document);

        JOptionPane.showMessageDialog(this, "Attendance data inserted successfully");
    }

    public void deleteAttendanceData() {
        // Get the input value from the text field
        int attendanceId = Integer.parseInt(attendanceIdField.getText());

        // Create a filter for the attendance ID
        Document filter = new Document("attendance_id", attendanceId);

        // Delete the document from the collection
        collection.deleteOne(filter);

        JOptionPane.showMessageDialog(this, "Attendance data deleted successfully");
    }

    public void updateAttendanceData() {
        // Get the input values from text fields
        int attendanceId = Integer.parseInt(attendanceIdField.getText());
        String date = dateField.getText();
        String status = statusField.getText();
        int studentId = Integer.parseInt(studentIdField.getText());
        int course_id = Integer.parseInt(courseIdField.getText());

        // Create a filter for the attendance ID
        Document filter = new Document("attendance_id", attendanceId);

        // Create an update document with the new values
        Document update = new Document("$set", new Document("date", date)
                .append("status", status)
                .append("student_id", studentId)
                .append("course_id", course_id));

        // Update the document in the collection
        collection.updateOne(filter, update);

        JOptionPane.showMessageDialog(this, "Attendance data updated successfully");
    }

    public void findAttendanceData() {
        // Get the input value from the text field
        int attendanceId = Integer.parseInt(attendanceIdField.getText());

        // Create a filter for the attendance ID
        Document filter = new Document("attendance_id", attendanceId);

        // Find the document in the collection
        Document document = collection.find(filter).first();

        if (document != null) {
            // Display the found attendance data
            dateField.setText(document.getString("date"));
            statusField.setText(document.getString("status"));
            studentIdField.setText(Integer.toString(document.getInteger("student_id")));
            courseIdField.setText(Integer.toString(document.getInteger("course_id")));
            JOptionPane.showMessageDialog(this, "Attendance data found");
        } else {
            JOptionPane.showMessageDialog(this, "Attendance data not found");
        }
    }

    public void close() {
        // Close the MongoDB connection
        mongoClient.close();
    }
}


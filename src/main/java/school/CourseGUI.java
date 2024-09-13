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

public class CourseGUI extends JFrame {
    private JTextField courseIdField, nameField, descriptionField, teacherIdField;
    private JButton insertButton, deleteButton, updateButton, findButton;
    private MongoClient mongoClient;
    private MongoCollection<Document> course;

    public CourseGUI() {
        setTitle("Course Details");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 2));

        JLabel courseIdLabel = new JLabel("Course ID:");
        courseIdField = new JTextField();
        JLabel nameLabel = new JLabel("Name:");
        nameField = new JTextField();
        JLabel descriptionLabel = new JLabel("Description:");
        descriptionField = new JTextField();
        JLabel teacherIdLabel = new JLabel("Teacher ID:");
        teacherIdField = new JTextField();

        insertButton = new JButton("Insert");
        insertButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                insertCourseData();
            }
        });

        deleteButton = new JButton("Delete");
        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deleteCourseData();
            }
        });

        updateButton = new JButton("Update");
        updateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateCourseData();
            }
        });

        findButton = new JButton("Find");
        findButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                findCourseData();
            }
        });

        panel.add(courseIdLabel);
        panel.add(courseIdField);
        panel.add(nameLabel);
        panel.add(nameField);
        panel.add(descriptionLabel);
        panel.add(descriptionField);
        panel.add(teacherIdLabel);
        panel.add(teacherIdField);
        panel.add(insertButton);
        panel.add(deleteButton);
        panel.add(updateButton);
        panel.add(findButton);

        add(panel);

        // Connect to MongoDB
        mongoClient = MongoClients.create("mongodb://localhost:27017");
        MongoDatabase database = mongoClient.getDatabase("school");
        course = database.getCollection("course");
    }

    public void insertCourseData() {
        
        int courseId = Integer.parseInt(courseIdField.getText());
        String name = nameField.getText();
        String description = descriptionField.getText();
        int teacherId = Integer.parseInt(teacherIdField.getText());

        Document document = new Document("course_id", courseId)
                .append("name", name)
                .append("description", description)
                .append("teacher_id", teacherId);

        course.insertOne(document);

        JOptionPane.showMessageDialog(this, "Course data inserted successfully.");
    }

    public void deleteCourseData() {
        int courseId = Integer.parseInt(courseIdField.getText());

        course.deleteOne(Filters.eq("course_id", courseId));

        JOptionPane.showMessageDialog(this, "Course data deleted successfully.");
    }

    public void updateCourseData() {
        int courseId = Integer.parseInt(courseIdField.getText());
        String name = nameField.getText();
        String description = descriptionField.getText();
        int teacherId = Integer.parseInt(teacherIdField.getText());

        Document filter = new Document("course_id", courseId);
        Document update = new Document("$set", new Document("name", name)
                .append("description", description)
                .append("teacher_id", teacherId));

        course.updateOne(filter, update);

        JOptionPane.showMessageDialog(this, "Course data updated successfully.");
    }

    public void findCourseData() {
        int courseId = Integer.parseInt(courseIdField.getText());

        Document Course = course.find(Filters.eq("course_id", courseId)).first();

        if (Course != null) {
            nameField.setText(Course.getString("name"));
            descriptionField.setText(Course.getString("description"));
            teacherIdField.setText(String.valueOf(Course.getInteger("teacher_id")));
        } else {
            JOptionPane.showMessageDialog(this, "No course found with the provided ID.");
        }
    }

    public void closeMongoClient() {
        if (mongoClient != null) {
            mongoClient.close();
        }
    }
}

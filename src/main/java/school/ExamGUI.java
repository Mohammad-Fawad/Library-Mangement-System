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

public class ExamGUI extends JFrame {
    private JTextField examIdField, dateField, timeField, roomField, deptIdField;
    private JButton insertButton, deleteButton, updateButton, findButton;
    private MongoClient mongoClient;
    private MongoCollection<Document> exam;

    public ExamGUI() {
        setTitle("Exam Details");
        setSize(400, 300);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel examIdLabel = new JLabel("Exam ID:");
        examIdLabel.setBounds(30, 30, 100, 25);
        add(examIdLabel);
        
        examIdField = new JTextField();
        examIdField.setBounds(140, 30, 200, 25);
        add(examIdField);
        
        JLabel dateLabel = new JLabel("Date:");
        dateLabel.setBounds(30, 70, 100, 25);
        add(dateLabel);
        
        dateField = new JTextField();
        dateField.setBounds(140, 70, 200, 25);
        add(dateField);
        
        JLabel timeLabel = new JLabel("Time:");
        timeLabel.setBounds(30, 110, 100, 25);
        add(timeLabel);
        
        timeField = new JTextField();
        timeField.setBounds(140, 110, 200, 25);
        add(timeField);
        
        JLabel roomLabel = new JLabel("Assigned Room:");
        roomLabel.setBounds(30, 150, 100, 25);
        add(roomLabel);
        
        roomField = new JTextField();
        roomField.setBounds(140, 150, 200, 25);
        add(roomField);
        
        JLabel deptIdLabel = new JLabel("Department ID:");
        deptIdLabel.setBounds(30, 190, 100, 25);
        add(deptIdLabel);
        
        deptIdField = new JTextField();
        deptIdField.setBounds(140, 190, 200, 25);
        add(deptIdField);

        insertButton = new JButton("Insert");
        insertButton.setBounds(30, 230, 80, 30);
        add(insertButton);
        insertButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                insertExamData();
            }
        });

        deleteButton = new JButton("Delete");
        deleteButton.setBounds(120, 230, 80, 30);
        add(deleteButton);
        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deleteExamData();
            }
        });

        updateButton = new JButton("Update");
        updateButton.setBounds(210, 230, 80, 30);
        add(updateButton);
        updateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateExamData();
            }
        });

        findButton = new JButton("Find");
        findButton.setBounds(300, 230, 80, 30);
        add(findButton);
        findButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                findExamData();
            }
        });

        

        // Connect to MongoDB
        mongoClient = MongoClients.create("mongodb://localhost:27017");
        MongoDatabase database = mongoClient.getDatabase("school");
        exam = database.getCollection("examschedule");
        
    }

    public void insertExamData() {
        int examId = Integer.parseInt(examIdField.getText());
        String date = dateField.getText();
        String time = timeField.getText();
        String room = roomField.getText();
        int deptId = Integer.parseInt(deptIdField.getText());

        Document document = new Document("exam_id", examId)
                .append("date", date)
                .append("time", time)
                .append("assigned_room", room)
                .append("dept_id", deptId);

        exam.insertOne(document);

        JOptionPane.showMessageDialog(this, "Exam data inserted successfully.");
    }

    public void deleteExamData() {
        int examId = Integer.parseInt(examIdField.getText());

        exam.deleteOne(Filters.eq("exam_id", examId));

        JOptionPane.showMessageDialog(this, "Exam data deleted successfully.");
    }

    public void updateExamData() {
        int examId = Integer.parseInt(examIdField.getText());
        String date = dateField.getText();
        String time = timeField.getText();
        String room = roomField.getText();
        int deptId = Integer.parseInt(deptIdField.getText());

        Document filter = new Document("exam_id", examId);
        Document update = new Document("$set", new Document("date", date)
                .append("time", time)
                .append("assigned_room", room)
                .append("dept_id", deptId));

        exam.updateOne(filter, update);

        JOptionPane.showMessageDialog(this, "Exam data updated successfully.");
    }

    public void findExamData() {
        int examId = Integer.parseInt(examIdField.getText());

        Document document = exam.find(Filters.eq("exam_id", examId)).first();

        if (document != null) {
            String date = document.getString("date");
            String time = document.getString("time");
            String room = document.getString("assigned_room");

            dateField.setText(date);
            timeField.setText(time);
            roomField.setText(room);
            
            deptIdField.setText(Integer.toString(document.getInteger("dept_id")));

            JOptionPane.showMessageDialog(this, "Exam data found!");
        } else {
            JOptionPane.showMessageDialog(this, "No exam found with the provided ID.");
        }
    }

    public void closeMongoClient() {
        if (mongoClient != null) {
            mongoClient.close();
        }
    }
}

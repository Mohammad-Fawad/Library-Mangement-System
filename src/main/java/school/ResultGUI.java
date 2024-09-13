package school;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.bson.Document;

public class ResultGUI extends JFrame implements ActionListener {
    private JTextField txtResultId;
    private JTextField txtStudentId;
    private JTextField txtExamId;
    private JTextField txtTotalMarks;
    private JButton btnSave, btnDelete, btnUpdate, btnFind;
    private MongoClient mongoClient;
    private MongoCollection<Document> result;

    public ResultGUI() {
        setTitle("Result Entry");
        setSize(400, 300);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel lblResultId = new JLabel("Result ID:");
        lblResultId.setBounds(30, 30, 100, 25);
        add(lblResultId);

        txtResultId = new JTextField();
        txtResultId.setBounds(140, 30, 200, 25);
        add(txtResultId);

        JLabel lblStudentId = new JLabel("Student ID:");
        lblStudentId.setBounds(30, 80, 100, 25);
        add(lblStudentId);

        txtStudentId = new JTextField();
        txtStudentId.setBounds(140, 80, 200, 25);
        add(txtStudentId);

        JLabel lblExamId = new JLabel("Exam ID:");
        lblExamId.setBounds(30, 130, 100, 25);
        add(lblExamId);

        txtExamId = new JTextField();
        txtExamId.setBounds(140, 130, 200, 25);
        add(txtExamId);

        JLabel lblTotalMarks = new JLabel("Total Marks:");
        lblTotalMarks.setBounds(30, 180, 100, 25);
        add(lblTotalMarks);

        txtTotalMarks = new JTextField();
        txtTotalMarks.setBounds(140, 180, 200, 25);
        add(txtTotalMarks);

        btnSave = new JButton("Save");
        btnSave.setBounds(30, 230, 80, 30);
        btnSave.addActionListener(this);
        add(btnSave);

        btnDelete = new JButton("Delete");
        btnDelete.setBounds(120, 230, 80, 30);
        btnDelete.addActionListener(this);
        add(btnDelete);

        btnUpdate = new JButton("Update");
        btnUpdate.setBounds(210, 230, 80, 30);
        btnUpdate.addActionListener(this);
        add(btnUpdate);

        btnFind = new JButton("Find");
        btnFind.setBounds(300, 230, 80, 30);
        btnFind.addActionListener(this);
        add(btnFind);

        // Connect to MongoDB
        mongoClient = MongoClients.create("mongodb://localhost:27017");
        MongoDatabase database = mongoClient.getDatabase("school");
        result = database.getCollection("result");

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnSave) {
            saveResultData();
        } else if (e.getSource() == btnDelete) {
            deleteResultData();
        } else if (e.getSource() == btnUpdate) {
            updateResultData();
        } else if (e.getSource() == btnFind) {
            findResultData();
        }
    }

    private void saveResultData() {
        try {
            int resultId = Integer.parseInt(txtResultId.getText());
            int studentId = Integer.parseInt(txtStudentId.getText());
            int examId = Integer.parseInt(txtExamId.getText());
            int totalMarks = Integer.parseInt(txtTotalMarks.getText());

            Document document = new Document("result_id", resultId)
                    .append("student_id", studentId)
                    .append("exam_id", examId)
                    .append("total_marks", totalMarks);

            result.insertOne(document);

            JOptionPane.showMessageDialog(this, "Result data saved successfully!");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                    "Please enter valid integer values for Result ID, Student ID, Exam ID, and Total Marks.",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteResultData() {
        try {
            int resultId = Integer.parseInt(txtResultId.getText());

            result.deleteOne(Filters.eq("result_id", resultId));

            JOptionPane.showMessageDialog(this, "Result data deleted successfully!");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter a valid integer value for Result ID.",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateResultData() {
        try {
            int resultId = Integer.parseInt(txtResultId.getText());
            int studentId = Integer.parseInt(txtStudentId.getText());
            int examId = Integer.parseInt(txtExamId.getText());
            int totalMarks = Integer.parseInt(txtTotalMarks.getText());

            Document filter = new Document("result_id", resultId);
            Document update = new Document("$set", new Document("student_id", studentId)
                    .append("exam_id", examId)
                    .append("total_marks", totalMarks));

            result.updateOne(filter, update);

            JOptionPane.showMessageDialog(this, "Result data updated successfully!");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                    "Please enter valid integer values for Result ID, Student ID, Exam ID, and Total Marks.",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void findResultData() {
        try {
            int resultId = Integer.parseInt(txtResultId.getText());

            Document document = result.find(Filters.eq("result_id", resultId)).first();

            if (document != null) {
                int studentId = document.getInteger("student_id");
                int examId = document.getInteger("exam_id");
                int totalMarks = document.getInteger("total_marks");

                txtStudentId.setText(String.valueOf(studentId));
                txtExamId.setText(String.valueOf(examId));
                txtTotalMarks.setText(String.valueOf(totalMarks));

                JOptionPane.showMessageDialog(this, "Result data found!");
            } else {
                JOptionPane.showMessageDialog(this, "No result found with the provided Result ID.");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter a valid integer value for Result ID.",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void closeMongoClient() {
        if (mongoClient != null) {
            mongoClient.close();
        }
    }
}

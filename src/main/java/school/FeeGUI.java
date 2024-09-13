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

public class FeeGUI extends JFrame {
    private JTextField txtPaymentNo;
    private JTextField txtPaymentDate;
    private JTextField txtPaymentAmount;
    private JTextField txtStudentId;
    private JButton insertButton, deleteButton, updateButton, findButton;
    private MongoClient mongoClient;
    private MongoCollection<Document> fee;

    public FeeGUI() {
        setTitle("Fee Payment Information");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JLabel lblPaymentNo = new JLabel("Payment No:");
        JLabel lblPaymentDate = new JLabel("Payment Date:");
        JLabel lblPaymentAmount = new JLabel("Payment Amount:");
        JLabel lblStudentId = new JLabel("Student ID:");

        txtPaymentNo = new JTextField();
        txtPaymentDate = new JTextField();
        txtPaymentAmount = new JTextField();
        txtStudentId = new JTextField();

        insertButton = new JButton("Insert");
        insertButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                insertFeeData();
            }
        });

        deleteButton = new JButton("Delete");
        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deleteFeeData();
            }
        });

        updateButton = new JButton("Update");
        updateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateFeeData();
            }
        });

        findButton = new JButton("Find");
        findButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                findFeeData();
            }
        });

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(4, 2));
        inputPanel.add(lblPaymentNo);
        inputPanel.add(txtPaymentNo);
        inputPanel.add(lblPaymentDate);
        inputPanel.add(txtPaymentDate);
        inputPanel.add(lblPaymentAmount);
        inputPanel.add(txtPaymentAmount);
        inputPanel.add(lblStudentId);
        inputPanel.add(txtStudentId);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(insertButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(findButton);

        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());
        contentPane.add(inputPanel, BorderLayout.CENTER);
        contentPane.add(buttonPanel, BorderLayout.SOUTH);

        // Connect to MongoDB
        mongoClient = MongoClients.create("mongodb://localhost:27017");
        MongoDatabase database = mongoClient.getDatabase("school");
        fee = database.getCollection("feepayment");

        setVisible(true);
    }

    private void insertFeeData() {
        String paymentNo = txtPaymentNo.getText();
        String paymentDate = txtPaymentDate.getText();
        String paymentAmount = txtPaymentAmount.getText();
        String studentId = txtStudentId.getText();

        Document document = new Document("payment_no", paymentNo)
                .append("payment_date", paymentDate)
                .append("payment_amount", paymentAmount)
                .append("student_id", studentId);

        fee.insertOne(document);

        JOptionPane.showMessageDialog(this, "Fee payment data inserted successfully.");
    }

    private void deleteFeeData() {
        String paymentNo = txtPaymentNo.getText();

        fee.deleteOne(Filters.eq("payment_no", paymentNo));

        JOptionPane.showMessageDialog(this, "Fee payment data deleted successfully.");
    }

    private void updateFeeData() {
        String paymentNo = txtPaymentNo.getText();
        String paymentDate = txtPaymentDate.getText();
        String paymentAmount = txtPaymentAmount.getText();
        String studentId = txtStudentId.getText();

        Document filter = new Document("payment_no", paymentNo);
        Document update = new Document("$set", new Document("payment_date", paymentDate)
                .append("payment_amount", paymentAmount)
                .append("student_id", studentId));

        fee.updateOne(filter, update);

        JOptionPane.showMessageDialog(this, "Fee payment data updated successfully.");
    }

    private void findFeeData() {
        String paymentNo = txtPaymentNo.getText();

        Document document = fee.find(Filters.eq("payment_no", paymentNo)).first();

        if (document != null) {
            String paymentDate = document.getString("payment_date");
            String paymentAmount = document.getString("payment_amount");
            String studentId = document.getString("student_id");

            txtPaymentDate.setText(paymentDate);
            txtPaymentAmount.setText(paymentAmount);
            txtStudentId.setText(studentId);

            JOptionPane.showMessageDialog(this, "Fee payment data found!");
        } else {
            JOptionPane.showMessageDialog(this, "No fee payment found with the provided payment number.");
        }
    }

    public void closeMongoClient() {
        if (mongoClient != null) {
            mongoClient.close();
        }
    }
}

package school;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainGUI extends JFrame {
    private JButton btnAttendance;
    private JButton btnClass;
    private JButton btnCourse;
    private JButton btnDepartment;
    private JButton btnExamSchedule;
    private JButton btnFeePayment;
    private JButton btnResult;
    private JButton btnNonTeachingStaff;
    private JButton btnStudent;
    private JButton btnTeacher;

    public MainGUI() {
        // Set up the main frame
        setTitle("Database Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(500, 500));
        setResizable(true);

        // Create a panel for button layout
        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
        panel.setBackground(Color.LIGHT_GRAY);

        // Create buttons
        btnAttendance = createButton("Attendance");
        btnClass = createButton("Class");
        btnCourse = createButton("Course");
        btnDepartment = createButton("Department");
        btnExamSchedule = createButton("Exam Schedule");
//        btnFeePayment = createButton("Fee Payment");
        btnResult = createButton("Result");
//        btnNonTeachingStaff = createButton("Non-Teaching Staff");
        btnStudent = createButton("Student");
        btnTeacher = createButton("Teacher");

        // Add action listeners to buttons
        btnAttendance.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                 AttendanceGUI gui = new AttendanceGUI();
                 gui.setVisible(true);
            }
        });

        btnClass.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                 ClassGUI classGUI = new ClassGUI();
                 classGUI.setVisible(true);
            }
        });

        btnCourse.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                 CourseGUI courseGUI = new CourseGUI();
                 courseGUI.setVisible(true);
            }
        });

        btnDepartment.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                 DepartmentGUI departmentGUI = new DepartmentGUI();
                 departmentGUI.setVisible(true);
            }
        });

        btnExamSchedule.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                 ExamGUI examScheduleGUI = new ExamGUI();
                 examScheduleGUI.setVisible(true);
            }
        });

//        btnFeePayment.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                 FeeGUI feePaymentGUI = new FeeGUI();
//                 feePaymentGUI.setVisible(true);
//            }
//        });

        btnResult.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                 ResultGUI resultGUI = new ResultGUI();
                 resultGUI.setVisible(true);
            }
        });

//        btnNonTeachingStaff.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                 StaffGUI nonTeachingStaffGUI = new StaffGUI();
//                 nonTeachingStaffGUI.setVisible(true);
//            }
//        });

        btnStudent.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                 StudentGUI studentGUI = new StudentGUI();
                 studentGUI.setVisible(true);
            }
        });

        btnTeacher.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                 TeacherGUI teacherGUI = new TeacherGUI();
                 teacherGUI.setVisible(true);
            }
        });

        // Add buttons to the panel
        panel.add(btnDepartment);
        panel.add(btnTeacher);
        panel.add(btnCourse);
        panel.add(btnStudent);
        panel.add(btnClass);
        panel.add(btnAttendance);
        panel.add(btnExamSchedule);
        panel.add(btnResult);
//        panel.add(btnFeePayment);
        
//        panel.add(btnNonTeachingStaff);
        
        

        // Set the panel as the content pane
        setContentPane(panel);
        pack();
        setVisible(true);
    }

    // Helper method to create styled buttons
    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(100, 30));
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        return button;
    }
}
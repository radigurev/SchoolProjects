package org.example.Controllers.Modals;

import org.example.Controllers.MainPage;
import org.example.Database.Connection;
import org.example.Entity.Employee;
import org.example.Entity.Position;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class EmployeeModal extends JFrame {
    private JPanel panel1;
    private JTextField firstName;
    private JTextField familyName;
    private JTextField age;
    private JComboBox position;
    private JButton updateButton;
    private JButton deleteButton;

    public EmployeeModal(Employee employee) {

        setContentPane(panel1);
        pack();

        var em = Connection.GetConnection();

        em.getTransaction().begin();
        var cb = em.getCriteriaBuilder();
        var cq = cb.createQuery(Position.class);
        var root = cq.from(Position.class);
        var allPT = cq.select(root);
        var Positions = em.createQuery(allPT).getResultList();
        em.getTransaction().commit();

        for (var Position :
                Positions) {
            position.addItem(Position.getName());
        }

        position.setSelectedIndex(0);

        firstName.setText(employee.getFirstName());
        familyName.setText(employee.getLastName());
        age.setText(Integer.toString(employee.getAge()));

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                MainPage.EditModalOpen = false;
                e.getWindow().dispose();
            }
        });

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                em.getTransaction().begin();
                employee.setAge(Integer.parseInt(age.getText()));
                employee.setFirstName(firstName.getText());
                employee.setLastName(familyName.getText());
                em.merge(employee);
                em.getTransaction().commit();
                MainPage.updateTable();
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                em.getTransaction().begin();
                var ewe = em.find(Employee.class, employee.getId());
                em.remove(ewe);
                em.getTransaction().commit();
                MainPage.updateTable();
                dispose();
            }
        });

    }
}

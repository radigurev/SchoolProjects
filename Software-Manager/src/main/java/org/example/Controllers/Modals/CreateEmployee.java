package org.example.Controllers.Modals;

import org.example.Controllers.MainPage;
import org.example.Database.Connection;
import org.example.Entity.Employee;
import org.example.Entity.Position;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class CreateEmployee extends JFrame {
    private JPanel panel1;
    private JTextField firstName;
    private JTextField lastName;
    private JTextField age;
    private JComboBox position;
    List<Position> positions;
    private JButton submitButton;

    public CreateEmployee() {
        var em = Connection.GetConnection();
        setContentPane(panel1);
        pack();

        em.getTransaction().begin();
        var cb = em.getCriteriaBuilder();
        var cq = cb.createQuery(Position.class);
        var root = cq.from(Position.class);
        var allPT = cq.select(root);
        positions = em.createQuery(allPT).getResultList();
        em.getTransaction().commit();

        for (var Position: positions) {
           position.addItem(Position.getName());
        }
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                var employee = new Employee();
                employee.setFirstName(firstName.getText());
                employee.setLastName(lastName.getText());
                employee.setAge(Integer.parseInt(age.getText()));
                String selected = (String) position.getSelectedItem();
                for (var Position : positions){
                    if (selected.equals(Position.getName())) {
                        employee.setPosition(Position);
                        break;
                    }
                }
                em.getTransaction().begin();
                em.persist(employee);
                em.getTransaction().commit();

               MainPage.updateTable();

                dispose();

            }
        });
    }


}
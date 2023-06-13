package org.example.Controllers.Modals;

import org.example.Controllers.SoftwareList;
import org.example.Database.Connection;
import org.example.Entity.Employee;
import org.example.Entity.Project;
import org.example.Entity.ProjectType;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;

public class CreateProject extends JFrame {

    private JTextField ProjectName;
    private JTextField HoursPredicted;
    private JTextField DueDate;
    private JComboBox Leader;
    private JComboBox ProjectType;
    private JPanel panel1;
    private JButton addButton;

    public CreateProject() {

        setContentPane(panel1);
        pack();

        var em = Connection.GetConnection();

        em.getTransaction().begin();
        var cb = em.getCriteriaBuilder();
        var cq = cb.createQuery(Employee.class);
        var root = cq.from(Employee.class);
        var allPT = cq.select(root);
        var Employees = em.createQuery(allPT).getResultList();
        em.getTransaction().commit();

        em.getTransaction().begin();
        var cb1 = em.getCriteriaBuilder();
        var cq1 = cb1.createQuery(ProjectType.class);
        var root1 = cq1.from(ProjectType.class);
        var allPT1 = cq1.select(root1);
        var Types = em.createQuery(allPT1).getResultList();
        em.getTransaction().commit();

        for (var Type : Types) {
            this.ProjectType.addItem(Type.getName());
        }

        for(var Employee : Employees) {
            this.Leader.addItem(String.format("%s %s",Employee.getFirstName(),Employee.getLastName()));
        }

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Project project = new Project();

                DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                        .appendPattern("yyyy-MM-dd[ HH:mm:ss]")
                        .parseDefaulting(ChronoField.HOUR_OF_DAY, 0)
                        .parseDefaulting(ChronoField.MINUTE_OF_HOUR, 0)
                        .parseDefaulting(ChronoField.SECOND_OF_MINUTE, 0)
                        .toFormatter();

                LocalDateTime localDateTime1 = LocalDateTime.parse(DueDate.getText(), formatter);

                project.setName(ProjectName.getText());
                project.setHours(Integer.parseInt(HoursPredicted.getText()));
                project.setDueDate(localDateTime1);
                project.setLeader(Employees.get(Leader.getSelectedIndex()));
                project.setType(Types.get(ProjectType.getSelectedIndex()));

                em.getTransaction().begin();
                em.persist(project);
                em.getTransaction().commit();

                SoftwareList.updateTable();

                dispose();
            }
        });
    }
}

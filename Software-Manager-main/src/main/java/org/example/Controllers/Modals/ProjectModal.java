package org.example.Controllers.Modals;

import org.example.Controllers.SoftwareList;
import org.example.Database.Connection;
import org.example.Entity.Project;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ProjectModal extends JFrame {
    private JTextField projectName;
    private JTextField timePredicted;
    private JTextField leader;
    private JTextField finalDate;
    private JPanel pane1;
    private JButton updateButton;
    private JButton deleteButton;

    public ProjectModal(Project project) {
        setContentPane(pane1);
        pack();

        var em = Connection.GetConnection();

        leader.setEditable(false);
        finalDate.setEditable(false);
        leader.setText(String.format("%s %s",project.getLeader().getFirstName(),project.getLeader().getLastName()));
        finalDate.setText(project.getDueDate().toLocalDate().toString());
        projectName.setText(project.getName());
        timePredicted.setText(Long.toString(project.getHours()));

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                SoftwareList.ModalOpen = false;
                e.getWindow().dispose();
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                em.getTransaction().begin();
                var entity = em.find(Project.class,project.getId());
                em.remove(entity);
                em.getTransaction().commit();
                SoftwareList.updateTable();
                dispose();
            }
        });

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                em.getTransaction().begin();
                var Project = em.find(Project.class,project.getId());
                Project.setName(projectName.getText());
                Project.setHours(Long.parseLong(timePredicted.getText()));
                em.merge(Project);
                em.getTransaction().commit();
                SoftwareList.updateTable();
//                SoftwareList.updateRow(Project);
            }
        });
    }
}

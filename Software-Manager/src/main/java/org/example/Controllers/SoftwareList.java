package org.example.Controllers;

import org.example.Controllers.Modals.CreateProject;
import org.example.Controllers.Modals.ProjectModal;
import org.example.Database.Connection;
import org.example.Entity.Employee;
import org.example.Entity.Project;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SoftwareList extends JFrame {
    private static JFrame frame;
    private JPanel panel1;
    private JTable SoftwareTable;
    private JButton SubmitButton;

    private static int selectedRow = 0;

    public static DefaultTableModel dm;

    public static boolean ModalOpen = false;

    public SoftwareList() {
        setContentPane(panel1);
        dm = new DefaultTableModel();
        String[] columnNames = new String[]{"", "Project Name", "Type project", "Leader", "Due Date", "Hours Predicted"};
        dm = new DefaultTableModel();

        for (String columnName : columnNames) {
            dm.addColumn(columnName);
        }
        SoftwareTable.setModel(dm);

        SoftwareTable.getColumnModel().getColumn(0).setMinWidth(0);
        SoftwareTable.getColumnModel().getColumn(0).setMaxWidth(0);
        SoftwareTable.getColumnModel().getColumn(0).setWidth(0);

        var em = Connection.GetConnection();

        em.getTransaction().begin();
        var cb = em.getCriteriaBuilder();
        var cq = cb.createQuery(Project.class);
        var root = cq.from(Project.class);
        var allPT = cq.select(root);
        var Projects = em.createQuery(allPT).getResultList();
        em.getTransaction().commit();
        for (var Project : Projects) {
            dm.addRow(new Object[]{
                    Project.getId(), Project.getName(), Project.getType().getName()
                    , String.format("%s %s", Project.getLeader().getFirstName(), Project.getLeader().getLastName())
                    , Project.getDueDate(), Project.getHours()
            });


            SoftwareTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
                @Override
                public void valueChanged(ListSelectionEvent e) {
                    if (!SoftwareList.ModalOpen) {
                        var a = SoftwareTable.getValueAt(SoftwareTable.getSelectedRow(), 0);
                        selectedRow = SoftwareTable.getSelectedRow();
                        Project currProject = null;
                        em.getTransaction().begin();
                        var Projects = em.createQuery(allPT).getResultList();
                        em.getTransaction().commit();
                        for (var Project : Projects) {
                            if (Project.getId() == Integer.parseInt(a.toString())) {
                                currProject = Project;
                                break;
                            }
                        }

                        ProjectModal modal = new ProjectModal(currProject);
                        modal.setVisible(true);
                        SoftwareList.ModalOpen = true;
                    }
                }
            });
        }
        SubmitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CreateProject newProject = new CreateProject();
                newProject.setVisible(true);
            }
        });
    }

    public static void updateTable() {
        dm.setRowCount(0);
        var em = Connection.GetConnection();
        em.getTransaction().begin();
        var cb = em.getCriteriaBuilder();
        var cq = cb.createQuery(Project.class);
        var root = cq.from(Project.class);
        var allPT = cq.select(root);
        var projects = em.createQuery(allPT).getResultList();
        em.getTransaction().commit();
        for (var Project : projects) {
            dm.addRow(new Object[]{
                    Project.getId(), Project.getName(), Project.getType().getName()
                    , String.format("%s %s", Project.getLeader().getFirstName(), Project.getLeader().getLastName())
                    , Project.getDueDate(), Project.getHours()
            });
        }
    }

    public static void updateRow(Project project) {
        dm.setValueAt(project.getName(), selectedRow, 1);
        dm.setValueAt(project.getHours(), selectedRow, 5);
    }
}

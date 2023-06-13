package org.example.Controllers;

import org.example.Controllers.Modals.CreateEmployee;
import org.example.Controllers.Modals.EmployeeModal;
import org.example.Database.Connection;
import org.example.Entity.Employee;
import org.example.Entity.Position;
import org.example.Entity.ProjectStatus;
import org.example.Entity.ProjectType;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.sql.Array;

public class MainPage extends JFrame {

    private static JFrame frame;
    private JPanel panel1;
    private JButton button1;
    private JTable FirstName;

    public static boolean EditModalOpen = false;

    public static DefaultTableModel tableModel;

    public MainPage() {
        String[] columnNames = new String[]{"", "First Name", "Last Name", "Age", "Position"};
        tableModel = new DefaultTableModel();

        for (String columnName : columnNames) {
            tableModel.addColumn(columnName);
        }
        FirstName.setModel(tableModel);

        var em = Connection.GetConnection();

        em.getTransaction().begin();
        var cb = em.getCriteriaBuilder();
        var cq = cb.createQuery(Employee.class);
        var root = cq.from(Employee.class);
        var allPT = cq.select(root);
        var Employees = em.createQuery(allPT).getResultList();
        em.getTransaction().commit();
        for (var Employee : Employees) {
            tableModel.addRow(new Object[]{
                    Employee.getId(), Employee.getFirstName(), Employee.getLastName(), Integer.toString(Employee.getAge()), Employee.getPosition().getName()
            });
        }
        FirstName.getColumnModel().getColumn(0).setMinWidth(0);
        FirstName.getColumnModel().getColumn(0).setMaxWidth(0);
        FirstName.getColumnModel().getColumn(0).setWidth(0);
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CreateEmployee modal = new CreateEmployee();
                modal.setVisible(true);
            }
        });


        FirstName.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent event) {
                if (!MainPage.EditModalOpen) {
                    var a = FirstName.getValueAt(FirstName.getSelectedRow(), 0);

                    Employee currEmployee = null;

                    em.getTransaction().begin();
                    var Employees = em.createQuery(allPT).getResultList();
                    em.getTransaction().commit();

                    for (var Employee : Employees) {
                        if (Employee.getId() == Integer.parseInt(a.toString())) {
                            currEmployee = Employee;
                            break;
                        }
                    }
                    EmployeeModal modal = new EmployeeModal(currEmployee);
                    modal.setVisible(true);

                    MainPage.EditModalOpen = true;
                }
            }
        });

//        getContentPane().add(FirstName);
    }

    public static void main(String[] args) {

        var em = Connection.GetConnection();
        em.getTransaction().begin();
        var cb = em.getCriteriaBuilder();
        var cq = cb.createQuery(ProjectType.class);
        var root = cq.from(ProjectType.class);
        var allPT = cq.select(root);

        var PT = em.createQuery(allPT).getResultList();

        var cq2 = cb.createQuery(ProjectStatus.class);
        var root2 = cq2.from(ProjectStatus.class);
        var allPS = cq2.select(root2);

        var PS = em.createQuery(allPS).getResultList();

        var cq3 = cb.createQuery(Position.class);
        var root3 = cq3.from(Position.class);
        var allP = cq3.select(root3);

        var P = em.createQuery(allP).getResultList();

        if (PT.size() == 0) {
            ProjectType type1 = new ProjectType();
            type1.setName("Software");
            ProjectType type2 = new ProjectType();
            type2.setName("Support");
            ProjectType type3 = new ProjectType();
            type3.setName("Part of project");

            em.persist(type1);
            em.persist(type2);
            em.persist(type3);
        }

        if (PS.size() == 0) {
            ProjectStatus status1 = new ProjectStatus();
            status1.setName("In progress");
            ProjectStatus status2 = new ProjectStatus();
            status2.setName("Review");
            ProjectStatus status3 = new ProjectStatus();
            status3.setName("Support");
            ProjectStatus status4 = new ProjectStatus();
            status4.setName("Delayed");

            em.persist(status1);
            em.persist(status2);
            em.persist(status3);
            em.persist(status4);
        }

        if (P.size() == 0) {
            Position position1 = new Position();
            position1.setName("Lead Developer");
            Position position2 = new Position();
            position2.setName("Developer");
            Position position3 = new Position();
            position3.setName("Tester");
            Position position4 = new Position();
            position4.setName("Project Manager");
            Position position5 = new Position();
            position5.setName("Client");

            em.persist(position1);
            em.persist(position2);
            em.persist(position3);
            em.persist(position4);
            em.persist(position5);
        }

        em.getTransaction().commit();
////////////////////////////////////////////////////


        JMenuBar menuBar;
        JMenu menu, submenu;
        JMenuItem menuItem;
        JRadioButtonMenuItem rbMenuItem;
        JCheckBoxMenuItem cbMenuItem;

//Create the menu bar.
        menuBar = new JMenuBar();

//Build the first menu.
        menu = new JMenu("Software");
        menu.addMenuListener(new MenuListener() {
            @Override
            public void menuSelected(MenuEvent e) {
                SoftwareList softwareList = new SoftwareList();
                frame.setContentPane(softwareList.getContentPane());
                frame.revalidate();
            }

            @Override
            public void menuDeselected(MenuEvent e) {

            }

            @Override
            public void menuCanceled(MenuEvent e) {

            }
        });
        menu.setMnemonic(KeyEvent.VK_A);
        menuBar.add(menu);
        menu = new JMenu("Employees");
        menu.addMenuListener(new MenuListener() {
            @Override
            public void menuSelected(MenuEvent e) {
                frame.setContentPane(new MainPage().panel1);
                frame.revalidate();
            }

            @Override
            public void menuDeselected(MenuEvent e) {

            }

            @Override
            public void menuCanceled(MenuEvent e) {

            }
        });
        menu.setMnemonic(KeyEvent.VK_C);
        menuBar.add(menu);


// //////////////////////////////

        DefaultTableModel MyTablemodel = new DefaultTableModel();
        //Create New Table
        JTable MyTableView = new JTable(MyTablemodel);
        // Add jTable in jScollPane for Showing Column Header
        JScrollPane MyScrollPane = new JScrollPane(MyTableView);
        //Add Column Name
        //Add jFrame for showing Table inside frame

        frame = new JFrame("name");
        frame.setJMenuBar(menuBar);
        frame.setContentPane(new MainPage().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack(); // намалява до размери, за да няма празно място
        frame.setVisible(true);
    }

    public static void updateTable() {

        tableModel.setRowCount(0);

        var em = Connection.GetConnection();
        em.getTransaction().begin();
        var cb = em.getCriteriaBuilder();
        var cq = cb.createQuery(Employee.class);
        var root = cq.from(Employee.class);
        var allPT = cq.select(root);
        var Employees = em.createQuery(allPT).getResultList();
        em.getTransaction().commit();
        for (var Employee : Employees) {
            tableModel.addRow(new Object[]{
                    Employee.getId(), Employee.getFirstName(), Employee.getLastName(), Integer.toString(Employee.getAge()), Employee.getPosition().getName()
            });
        }
    }
}

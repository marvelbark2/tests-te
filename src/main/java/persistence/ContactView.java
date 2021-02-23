package persistence;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class ContactView extends JFrame{
    public ContactView() throws Exception {
        DataSource source = new DataSource(20);
        Contacts contacts = new Contacts(source);

        Map<List<String>, List<String[]>> all = contacts.all();

        String[] cols = null;
        String[][] data = null;
        for (List<String> key: all.keySet()) {
            cols = key.toArray(new String[0]);
            data = all.get(key).toArray(new String[0][0]);
        }
        JTable table = new JTable(data, cols);
        table.setBounds(30,40,200,300);
        JTableHeader header = table.getTableHeader();
        header.setBackground(Color.black);
        header.setForeground(Color.yellow);
        JScrollPane sp=new JScrollPane(table);
        JPanel panel = new JPanel(new GridLayout(2, 1));
        JPanel p1 = new JPanel(new GridLayout(2, 2));
        String[][] finalData = data;
        table.getModel().addTableModelListener(
                evt -> {
                    List<String> val = new ArrayList(Arrays.asList(finalData[evt.getFirstRow()]));
                    int id = Integer.parseInt(val.remove(0));
                    contacts.update(id, val.toArray(new String[0]));
                });
        JButton close = new JButton("DELETE");
        close.addActionListener(e -> {
            System.out.println(table.getModel().getValueAt(table.getSelectedRow(), 0));
        });
        table.removeColumn(table.getColumnModel().getColumn(0));

        TitledBorder contactsList = new TitledBorder("Contacts List");
        TitledBorder delete = new TitledBorder("Delete Contact");
        close.setBounds(50,200,50,50);
        close.setBorder(delete);
        sp.setBorder(contactsList);
        p1.add(sp);
        p1.add(close);

        p1.add(new JTextField("Hello"));
        panel.add(p1);
        add(panel);
        setSize(600,400);
        setVisible(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }


}

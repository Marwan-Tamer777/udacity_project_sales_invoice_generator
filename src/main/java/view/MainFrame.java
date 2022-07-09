/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import javax.swing.*;
/**
 *
 * @author dell
 */
public class MainFrame extends JFrame{
    
    public MainFrame(String name){
        super(name);
        setSize(1500,500);
        setLocation(0,0);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridLayout(1,2));
        
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("File");
        menuBar.add(menu);
        JMenuItem load = new JMenuItem("Load File");
        load.setMnemonic(KeyEvent.VK_L);
        load.setAccelerator(KeyStroke.getKeyStroke(
        KeyEvent.VK_L, ActionEvent.ALT_MASK));
        menu.add(load);
        JMenuItem save = new JMenuItem("Save File");
        save.setMnemonic(KeyEvent.VK_S);
        save.setAccelerator(KeyStroke.getKeyStroke(
        KeyEvent.VK_S, ActionEvent.ALT_MASK));
        menu.add(save);
        this.setJMenuBar(menuBar);
        
        JPanel leftPanel=new JPanel();
        //leftPanel.setLayout(new GridLayout(1,2));
        String[][] table1Data = {
            {"1","2022-12-12","ahmed","131.21"},
            {"2","2022-12-12","ali","4214"},
            {"3","2022-12-12","mohamed","512.13"}
        };
        String[] table1Headers ={"No.","Date","Customer","Total"};
        JTable leftTable = new JTable(table1Data,table1Headers);
        leftPanel.add(new JScrollPane(leftTable));
        JButton createBtn=new JButton("Create New Invoice");       
        JButton deleteBtn=new JButton("Delete Current Selected Invoice");   
        leftPanel.add(createBtn);
        leftPanel.add(deleteBtn);  
        this.add(leftPanel); 
        
        JPanel rightPanel=new JPanel(); 
        rightPanel.setLayout(new GridLayout(6,2));
        JTextField invoiceNumber = new JTextField();
        JTextField invoiceDate = new JTextField();
        JTextField invoiceCustomerName = new JTextField();
        JTextField invoiceTotalCost = new JTextField();
        rightPanel.add(new JLabel("Invoice Number"));
        rightPanel.add(invoiceNumber);
        rightPanel.add(new JLabel("Invoice Date"));
        rightPanel.add(invoiceDate);
        rightPanel.add(new JLabel("Customer Name"));
        rightPanel.add(invoiceCustomerName);
        rightPanel.add(new JLabel("Total"));
        rightPanel.add(invoiceTotalCost);
                String[][] table2Data = {
            {"1","Ice cream","5","2","10"},
            {"2","TV","500","1","500"},
            {"1","battery","1.25","8","10"},
        };
        String[] table2Headers ={"No.","Item Name","Item Price","Item Count","Total"};
        JTable rightTable = new JTable(table2Data,table2Headers);
        leftPanel.add(new JScrollPane(rightTable));
        JButton saveEditBtn=new JButton("Save");       
        JButton cancelEditBtn=new JButton("Cancel");   
        leftPanel.add(saveEditBtn);
        leftPanel.add(cancelEditBtn);  
        
        this.add(rightPanel); 
    }
}

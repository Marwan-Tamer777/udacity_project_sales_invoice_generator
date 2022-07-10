/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;
import controller.*;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import model.FileOperations;
/**
 *
 * @author dell
 */
public class MainFrame extends JFrame{
    
    public MainFrame(String name){
        //creating the Home Frame.
        super(name);
        setLocation(0,0);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridLayout(1,2));
        
        //creating all the controller objects to add as event listeners;
        ButtonsActions buttonsListener = new ButtonsActions();
        MenuItemsActions menuListener = new MenuItemsActions();
        TableItemsActions tableListener = new TableItemsActions();
        
        //Creating and adding the menu bar.
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("File");
        menuBar.add(menu);
        JMenuItem load = new JMenuItem("Load File");
        load.setActionCommand("L");
        load.addActionListener(menuListener);
        load.setMnemonic(KeyEvent.VK_L);
        load.setAccelerator(KeyStroke.getKeyStroke(
        KeyEvent.VK_L, ActionEvent.ALT_MASK));
        menu.add(load);
        JMenuItem save = new JMenuItem("Save File");
        save.setActionCommand("S");
        save.addActionListener(menuListener);
        save.setMnemonic(KeyEvent.VK_S);
        save.setAccelerator(KeyStroke.getKeyStroke(
        KeyEvent.VK_S, ActionEvent.ALT_MASK));
        menu.add(save);
        this.setJMenuBar(menuBar);
        
        
        //creating the left panel and its sub containers with mockdata.
        JPanel leftPanel=new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel,BoxLayout.Y_AXIS));
        leftPanel.setBorder(new EmptyBorder(new Insets(10, 15, 10, 15)));
        String[][] table1Data = FileOperations.getInvoicesTableData();
        String[] table1Headers = FileOperations.getInvoicesTableHeaders();
        JTable leftTable = new JTable(table1Data,table1Headers);
        leftPanel.add(new JLabel("Invoices Table"));
        leftPanel.add(new JScrollPane(leftTable));
        
        JPanel leftButtonsPanel = new JPanel();
        leftButtonsPanel.setLayout(new BoxLayout(leftButtonsPanel,BoxLayout.X_AXIS));
        JButton createBtn=new JButton("Create New Invoice"); 
        createBtn.setActionCommand("C");
        createBtn.addActionListener(buttonsListener);
        JButton deleteBtn=new JButton("Delete Current Selected Invoice"); 
        deleteBtn.setActionCommand("D");
        deleteBtn.addActionListener(buttonsListener);
        leftButtonsPanel.add(createBtn);
        leftButtonsPanel.add(deleteBtn);
        leftPanel.add(leftButtonsPanel);
        this.add(leftPanel); 
        
        
        //creating the right panel and its sub container with mock data.
        JPanel rightPanel=new JPanel(); 
        rightPanel.setLayout(new BoxLayout(rightPanel,BoxLayout.Y_AXIS));
        leftPanel.setBorder(new EmptyBorder(new Insets(10, 15, 10, 15)));

        JPanel rightInputsPanel = new JPanel();
        rightInputsPanel.setLayout(new GridLayout(4,2));
        JTextField invoiceNumber = new JTextField();
        JTextField invoiceDate = new JTextField();
        JTextField invoiceCustomerName = new JTextField();
        JTextField invoiceTotalCost = new JTextField();
        rightInputsPanel.add(new JLabel("Invoice Number"));
        rightInputsPanel.add(invoiceNumber);
        rightInputsPanel.add(new JLabel("Invoice Date"));
        rightInputsPanel.add(invoiceDate);
        rightInputsPanel.add(new JLabel("Customer Name"));
        rightInputsPanel.add(invoiceCustomerName);
        rightInputsPanel.add(new JLabel("Total"));
        rightInputsPanel.add(invoiceTotalCost);
        rightPanel.add(rightInputsPanel);
        
        String[][] table2Data = FileOperations.getInvoicesItemsTableData();
        String[] table2Headers = FileOperations.getInvoicesItemsTableHeaders();
        JTable rightTable = new JTable(table2Data,table2Headers);
        rightPanel.add(new JLabel("Invoice Items"));
        rightPanel.add(new JScrollPane(rightTable));
        
        JPanel rightButtonsPanel = new JPanel();
        rightButtonsPanel.setLayout(new BoxLayout(rightButtonsPanel,BoxLayout.X_AXIS));
        JButton saveEditBtn=new JButton("Save");  
        saveEditBtn.setActionCommand("SE");
        saveEditBtn.addActionListener(buttonsListener);
        JButton cancelEditBtn=new JButton("Cancel");  
        cancelEditBtn.setActionCommand("CE");
        cancelEditBtn.addActionListener(buttonsListener);
        rightButtonsPanel.add(saveEditBtn);
        rightButtonsPanel.add(cancelEditBtn);  
        rightPanel.add(rightButtonsPanel);
        this.add(rightPanel);
        
        this.pack();
    }
}

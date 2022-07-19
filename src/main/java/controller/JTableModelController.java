/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.util.Arrays;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;
import model.FileOperations;
import view.MainFrame;

/*
    this Class is used as a modified TableModel to manages both tables instances and user actions.
 */
public class JTableModelController extends AbstractTableModel implements ListSelectionListener,TableModelListener,TableModel{

    private String[] columnNames;
    private Object[][] data;
    private int tableType;
    private JTable table;
    public static final int INVOICES_TABLE =1;
    public static final int INVOICE_ITEMS_TABLE =2;
    
    public JTableModelController(int type){
        tableType = type;
        
        if(FileOperations.getInvoices() == null){
            data= new String [0][0];
            if(type == INVOICES_TABLE){
                columnNames = FileOperations.getInvoicesTableHeaders();
            } else if (type == INVOICE_ITEMS_TABLE){
                columnNames = FileOperations.getInvoicesItemsTableHeaders();
            }
            
            table = new JTable(this);
            table.setCellSelectionEnabled(true);
            table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            table.getModel().addTableModelListener(this);
            table.getSelectionModel().addListSelectionListener(this);
            return;
        }
        
        if(tableType == INVOICES_TABLE){
            data= FileOperations.getInvoicesTableData();
            columnNames = FileOperations.getInvoicesTableHeaders();
        } else if (tableType == INVOICE_ITEMS_TABLE){
            data= FileOperations.getInvoicesItemsTableData();
            columnNames = FileOperations.getInvoicesItemsTableHeaders();
        }
        
        table = new JTable(this);
        table.setCellSelectionEnabled(true);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.getModel().addTableModelListener(this);
        table.getSelectionModel().addListSelectionListener(this);
    }
    
    //Copy Constrcutor to update table Data
    public JTableModelController(String [] [] d,int type){
        tableType = type;
        
        if(type == INVOICES_TABLE){
            columnNames = FileOperations.getInvoicesTableHeaders();
        } else if (type == INVOICE_ITEMS_TABLE){
            columnNames = FileOperations.getInvoicesItemsTableHeaders();
        }
        
        data = d;
    }
    
    public JTable getTable(){
        return table;
    }
    public int getColumnCount() {
        return columnNames.length;
    }

    public int getRowCount() {
        return data.length;
    }

    public String getColumnName(int col) {
        return columnNames[col];
    }

    public Object getValueAt(int row, int col) {
        return data[row][col];
    }

    public Class getColumnClass(int c) {
        return getValueAt(0, c).getClass();
    }

    /*
     * Don't need to implement this method unless your table's
     * editable.
     */
    public boolean isCellEditable(int row, int col) {
        //Note that the data/cell address is constant,
        //no matter where the cell appears onscreen.
        return false;
//        if(tableType == INVOICES_TABLE){
//            return false;
//        } else if(col==0){
//            return false;
//        } else {
//            return true;
//        }
    }
    
    public void setValueAt(Object value, int row, int col) {
        data[row][col] = value;
        fireTableCellUpdated(row, col);
    }

    //when the user selects a certain entry from the table, depending on the table either.
    //1- the program will either updates the invoice items table with all the invcoie items from a certain invoice.
    //2- the program will populates the text field with the invoice details when selecting an invoice item.
    @Override
      public void valueChanged(ListSelectionEvent e) {
         
        String[] selectedData = null;

        int selectedRow = table.getSelectedRow();
        int columnCount = table.getColumnCount();
        selectedData = new String[columnCount];
        
        //guard if the action listener is fired while no row is being selected.
        if(selectedRow == -1){return;}
          for (int j = 0; j < columnCount; j++) {

            selectedData[j] = (String) table.getValueAt(selectedRow,j);
          }
        
        if(tableType == INVOICE_ITEMS_TABLE){
            //MainFrame.updateTextFields(FileOperations.getInvoiceData(Integer.parseInt(selectedData[0])));
            MainFrame.viewInvoicesTable.setModel(
                    new JTableModelController(FileOperations.getInvoicesTableData(),INVOICES_TABLE));
        } else if (tableType == INVOICES_TABLE){
            MainFrame.updateTextFields(FileOperations.getInvoiceData(Integer.parseInt(selectedData[0])));
            MainFrame.viewInvoicesItemsTable.setModel(
                    new JTableModelController(FileOperations.getInvoicesItemsTableData(Integer.parseInt(selectedData[0])),INVOICE_ITEMS_TABLE));

        } 
      }

    @Override
    public void tableChanged(TableModelEvent e) {
        int row = e.getFirstRow();
        int column = e.getColumn();
        TableModel model = (TableModel)e.getSource();
        String columnName = model.getColumnName(column);
        Object data = model.getValueAt(row, column);

        System.out.print("data "+data);
        // Do something with the data...
    }
    
}

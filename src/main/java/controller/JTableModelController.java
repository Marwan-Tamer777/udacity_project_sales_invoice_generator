/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;
import model.FileOperations;

/**
 *
 * @author dell
 */
public class JTableModelController extends AbstractTableModel implements ListSelectionListener,TableModelListener{

    private String[] columnNames;
    private Object[][] data;
    private int tableType;
    private JTable table;
    public static final int INVOICES_TABLE =1;
    public static final int INVOICE_ITEMS_TABLE =2;
    
    public JTableModelController(int type){
        tableType = type;
        
        if(type == INVOICES_TABLE){
            data= FileOperations.getInvoicesTableData();
            columnNames = FileOperations.getInvoicesTableHeaders();
        } else if (type == INVOICE_ITEMS_TABLE){
            data= FileOperations.getInvoicesItemsTableData();
            columnNames = FileOperations.getInvoicesItemsTableHeaders();
        }
        table = new JTable(this);
        table.getModel().addTableModelListener(this);
        table.getSelectionModel().addListSelectionListener(this);
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
        if(tableType == INVOICES_TABLE){
            return false;
        } else if(col==0){
            return false;
        } else {
            return true;
        }
    }
    
    public void setValueAt(Object value, int row, int col) {
        data[row][col] = value;
        fireTableCellUpdated(row, col);
    }

    @Override
      public void valueChanged(ListSelectionEvent e) {
        String selectedData = null;

        int[] selectedRow = table.getSelectedRows();
        int[] selectedColumns = table.getSelectedColumns();

        for (int i = 0; i < selectedRow.length; i++) {
          for (int j = 0; j < selectedColumns.length; j++) {
            selectedData = (String) table.getValueAt(selectedRow[i], selectedColumns[j]);
          }
        }
        System.out.println("Selected: " + selectedData);
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

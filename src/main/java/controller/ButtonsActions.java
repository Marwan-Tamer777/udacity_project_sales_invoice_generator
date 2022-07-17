/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import model.FileOperations;
import view.MainFrame;

/**
 *
 * @author dell
 */
public class ButtonsActions implements ActionListener{

    @Override
    public void actionPerformed(ActionEvent e) {
        switch(e.getActionCommand()){
            case "C" -> {
                FileOperations.createInvoiceDialog();
                System.out.println("Create Invoice");
            }
            case "D"->{
                int indexInvoice = MainFrame.viewInvoicesTable.getSelectedRow();
                int indexInvoiceItem = MainFrame.viewInvoicesItemsTable.getSelectedRow();
                //System.out.print("data: "+indexInvoice+" "+indexInvoiceItem);
                //System.out.println();
                if(indexInvoice ==-1 && indexInvoiceItem == -1){
                    break;
                } else if (indexInvoice ==-1){
                    
                    FileOperations.deleteInvoiceItemByTableIndex(
                        (String)MainFrame.viewInvoicesItemsTable.getValueAt(indexInvoiceItem, 0),
                        (String)MainFrame.viewInvoicesItemsTable.getValueAt(indexInvoiceItem, 1)  
                    );
                } else if(indexInvoiceItem == -1){
                    FileOperations.deleteInvoiceByTableIndex(indexInvoice);
                }
                
                System.out.println("Delete Invoice");
            }
            case "SE"->{
                String[] data = MainFrame.getTextFieldsValues();
                FileOperations.updateInvoice(data);
                System.out.println("Save Edited Invoice: ");
            }
            case "CE"->{
                String[] data = FileOperations.getInvoiceData(Integer.parseInt(MainFrame.getTextFieldsValues()[0]));
                MainFrame.updateTextFields(data);
                System.out.println("Cancel Edit");
            }
        }
    }
    
}

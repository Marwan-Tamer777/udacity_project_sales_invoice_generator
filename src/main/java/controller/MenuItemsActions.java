/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import model.FileOperations;
import model.InvoiceHeader;
import view.MainFrame;

/**
 *
 * @author dell
 */
public class MenuItemsActions  implements ActionListener{

    @Override
    public void actionPerformed(ActionEvent e) {
        switch(e.getActionCommand()){
            case "L" -> {
                System.out.println("Load File");
                FileOperations.ReadFile();
                MainFrame.updateTables();
            }
            case "S"->{
                System.out.println("Save File");
                ArrayList<InvoiceHeader> ihs = FileOperations.getInvoicces();
                FileOperations.WriteFile(ihs);
            }
        }
    }
    
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.FileOperations;
import model.InvoiceHeader;
import model.InvoiceLine;

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
            }
            case "S"->{
                System.out.println("Save File");
                ArrayList<InvoiceHeader> ihs = FileOperations.ReadFile();
//                InvoiceHeader ih = new InvoiceHeader(5,"2023-10-12","Abdul");
//                InvoiceLine il = new InvoiceLine(5,"Clock",30,2);
//                ih.addInvoiceLine(il);
//                ihs.add(ih);
                FileOperations.WriteFile(ihs);
            }
        }
    }
    
}

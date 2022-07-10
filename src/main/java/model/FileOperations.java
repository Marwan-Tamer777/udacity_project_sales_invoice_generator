/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import controller.MenuItemsActions;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author dell
 */
public class FileOperations {
    public static ArrayList<InvoiceHeader> ReadFile(){
                //parsing a CSV file into BufferReader class constructor 
                ArrayList <InvoiceHeader> invoices = new ArrayList<InvoiceHeader>();
                String line;
                BufferedReader br= null;  
                
                try {
                    br = new BufferedReader(new FileReader("InvoiceHeader.csv"));
                    //skip first line of headers and making an array of InvoiceHeaders.
                    br.readLine();
                    while ((line = br.readLine()) != null){
                        String[] invoiceRow = line.split(",");
                        invoices.add(new InvoiceHeader(Integer.parseInt(invoiceRow[0]),invoiceRow[1],invoiceRow[2]));
                    }
                } catch (IOException ex) {
                    Logger.getLogger(MenuItemsActions.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                try {
                    br = new BufferedReader(new FileReader("InvoiceLine.csv"));
                    //skip first line of headers and adding each InvoiceLine to its InvoiceHeader.
                    br.readLine();
                    while ((line = br.readLine()) != null){
                        String[] invoiceLineRow = line.split(",");
                        
                        for(int i =0;i<invoices.size();i++){
                            if(invoices.get(i).getInvoiceNum() == Integer.parseInt(invoiceLineRow[0])){
                                invoices.get(i).addInvoiceLine(
                                        new InvoiceLine(Integer.parseInt(invoiceLineRow[0]),invoiceLineRow[1],Integer.parseInt(invoiceLineRow[2]),Integer.parseInt(invoiceLineRow[3]))
                                );
                            }
                        }
                    }
                } catch (IOException ex) {
                    Logger.getLogger(MenuItemsActions.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                //printing the data that was parsed from the CSV file.
                for(int i =0;i<invoices.size();i++){
                    System.out.print(invoices.get(i).getInvoiceNum() +" "+invoices.get(i).getInvoiceDate() +" "+
                            invoices.get(i).getCustomerName());
                    System.out.println();
                    
                    for(int x =0;x<invoices.get(i).getInvoiceLines().size();x++){
                        InvoiceLine il = invoices.get(i).getInvoiceLines().get(x);
                        System.out.print(il.getInvoiceNum() +" "+il.getItemName()+" "+il.getItemPrice() + " " + il.getItemCount());
                         System.out.println();
                    }
                    System.out.println();
                }
                
        return invoices;
    }
    
    
    public static void WriteFile(ArrayList<InvoiceHeader> dataRows){
        
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import controller.MenuItemsActions;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author dell
 */
public class FileOperations {
    
    private static ArrayList<InvoiceHeader> INVOICES;
    private static final String[] INVOICES_HEADERS ={"No.","Date","Customer","Total"};
    private static final String[] INVOICES_ITEMS_HEADERS ={"No.","Item Name","Item Price","Item Count","Total"};
    
    
    public static ArrayList<InvoiceHeader> ReadFile(){
                //parsing a CSV file into BufferReader class constructor 
                ArrayList <InvoiceHeader> invoices = new ArrayList<>();
                String line;
                BufferedReader br= null;  
                
                //Try and catch to open the "InvoiceHeader.csv" file and reads from it.
                try {
                    br = new BufferedReader(new FileReader("InvoiceHeader.csv"));
                    //skip first line of headers and making an array of InvoiceHeaders.
                    br.readLine();
                    
                    //if the file exists, it will be parsed into the InvoiceHeader Array line by line.
                    while ((line = br.readLine()) != null){
                        String[] invoiceRow = line.split(",");
                        invoices.add(new InvoiceHeader(Integer.parseInt(invoiceRow[0]),invoiceRow[1],invoiceRow[2]));
                    }
                } catch (IOException ex) {
                    Logger.getLogger(MenuItemsActions.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                //Try and catch to open the "InvoiceLine.csv" file and reads from it.
                try {
                    br = new BufferedReader(new FileReader("InvoiceLine.csv"));
                    //skip first line of headers and adding each InvoiceLine to its InvoiceHeader.
                    br.readLine();
                    
                    //starts checking if each line corresponds to an invoice Header, if yes
                    // the line will be parsed into an InvoiceLine and added to the Invoiceheader.
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
        INVOICES = invoices;
        return invoices;
    }
    
    
    public static void WriteFile(ArrayList<InvoiceHeader> dataRows){
        //cvs file headers for both InvoiceHeader and InvoiceLine files.
        String[] invoicesHeadersHeaders = {"invoiceNum","invoiceDate","CustomerName"};
        String[] invoicesLinesHeaders = {"invoiceNum","itemName","itemPrice","Count"};

        FileWriter fileWriter = null;
        
        try {
            fileWriter = new FileWriter(new File("InvoiceHeader.csv"));
            //write the first line headers here.
            StringBuilder headers = new StringBuilder();
            for (String invoicesHeadersHeader : invoicesHeadersHeaders) {
                headers.append(invoicesHeadersHeader);
                headers.append(',');
            }
            headers.append("\n");
            fileWriter.write(headers.toString()); 
        
            //iteratres over the given data and writes them into the CSV invoice Header file.
            for (InvoiceHeader data : dataRows) {
                StringBuilder line = new StringBuilder();
                line.append(data.getInvoiceNum());line.append(",");
                line.append(data.getInvoiceDate());line.append(",");
                line.append(data.getCustomerName());line.append(",");
                line.append("\n");
                fileWriter.write(line.toString());   
            }

            fileWriter.close();
        } catch (IOException ex) {
            Logger.getLogger(FileOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            fileWriter = new FileWriter(new File("InvoiceLine.csv"));
            //write headers first line here.
            StringBuilder headers = new StringBuilder();
            for (String invoicesLineHeader : invoicesLinesHeaders) {
                headers.append(invoicesLineHeader);
                headers.append(',');
            }
            headers.append("\n");
            fileWriter.write(headers.toString()); 
        
            //iteratres over the given data and writes them into the CSV invoice Line File.
            for (InvoiceHeader data : dataRows) {
                for(InvoiceLine lineDetails : data.getInvoiceLines()){
                    StringBuilder line = new StringBuilder();
                    line.append(lineDetails.getInvoiceNum());line.append(",");
                    line.append(lineDetails.getItemName());line.append(",");
                    line.append(lineDetails.getItemPrice());line.append(",");
                    line.append(lineDetails.getItemCount());line.append(",");
                    line.append("\n");
                    fileWriter.write(line.toString());   
                }
            }

            fileWriter.close();
        } catch (IOException ex) {
            Logger.getLogger(FileOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
    
    
    //A Static array of headers for Singleton design pattern for easier Modifcation.
    public static String[] getInvoicesTaableHeaders(){
        return INVOICES_HEADERS;
    }
    
    public static String[][] getInvoicesTableData(){
        String[][] data;
        int total = 0;
        data = new String[INVOICES.size()][INVOICES_HEADERS.length];
        
        for(int i=0;i<INVOICES.size();i++){
            InvoiceHeader ih = INVOICES.get(i);
            data[i][0] = String.valueOf(ih.getInvoiceNum());
            data[i][1] = ih.getInvoiceDate();
            data[i][2] = ih.getCustomerName();
            for(InvoiceLine il: ih.getInvoiceLines()){
                total+=il.getItemPrice()*il.getItemCount();
            }
            data[i][3] = String.valueOf(total);
            total =0;
        }
        
        return data;
    }
    
    
    /*
        achicve single responsiblity by using the getTableForm from InvoiceHeader and Line respectivly.
    */
    
    //A Static array of headers for Singleton design pattern for easier Modifcation.
    public static String[] getInvoicesItemsTableHeaders(){
        return INVOICES_ITEMS_HEADERS;
    }
    
    //{"No.","Item Name","Item Price","Item Count","Total"};
    public static String[][] getInvoicesItemsTableData(){
        String[][] data;
        int itemsCount =0;
        for(InvoiceHeader invoice: INVOICES){
            itemsCount+=invoice.getInvoiceLines().size();
        }
        data = new String[itemsCount][INVOICES_ITEMS_HEADERS.length];
        itemsCount =0;
        for(int i=0;i<INVOICES.size();i++){
            InvoiceHeader ih = INVOICES.get(i);
            for(InvoiceLine il: ih.getInvoiceLines()){
                data[itemsCount][0] = String.valueOf(il.getInvoiceNum());
                data[itemsCount][1] = il.getItemName();
                data[itemsCount][2] = String.valueOf(il.getItemPrice());
                data[itemsCount][3] = String.valueOf(il.getItemCount());
                data[itemsCount][4] = String.valueOf(il.getItemPrice()*il.getItemCount());
                itemsCount++;
            }
            
        }
        
        return data;
    }
}

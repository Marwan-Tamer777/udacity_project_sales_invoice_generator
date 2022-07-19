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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Box;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import view.MainFrame;

/*
    This is the main model file which is used as the middleware between any userAction and change in the data system.
    -it manages the data in the form of InvoiceHeader ArrayList.
    -it reads and write to thhe csv Files using.
    -send and recieve the data from other components in the app as String arrays and modifes on the Invoices Data Object.
 */
public class FileOperations {
    
    private static ArrayList<InvoiceHeader> INVOICES;
    private static final String[] INVOICES_HEADERS ={"No.","Date","Customer","Total"};
    private static final String[] INVOICES_ITEMS_HEADERS ={"No.","Item Name","Item Price","Item Count","Total"};
    private static String invoiceHeaderPath;
    private static String invoiceLinesPath;
    
    
    public static ArrayList<InvoiceHeader> ReadFile(){
                //parsing a CSV file into BufferReader class constructor 
                ArrayList <InvoiceHeader> invoices = new ArrayList<>();
                String line;
                BufferedReader br= null;  
                JFileChooser jfc = new JFileChooser();
                JOptionPane.showConfirmDialog(null, 
                        "Please Choose the 'InvoiceHeader.csv' file!", "Choose File", JOptionPane.DEFAULT_OPTION);  
                jfc.showOpenDialog(jfc);
                invoiceHeaderPath = jfc.getSelectedFile().getPath();
                
                //Try and catch to open the "InvoiceHeader.csv" file and reads from it.
                try {
                    if(!jfc.getSelectedFile().getName().equals("InvoiceHeader.csv")){
                        throw new Exception("Please make sure to choose a file with the correct name and format of InvoiceHeader.csv");
                    }
                    br = new BufferedReader(new FileReader(invoiceHeaderPath));
                    //skip first line of headers and making an array of InvoiceHeaders.
                    //br.readLine();
                    
                    //if the file exists, it will be parsed into the InvoiceHeader Array line by line.
                    while ((line = br.readLine()) != null){
                        String[] invoiceRow = line.split(",");
                        new SimpleDateFormat("DDmmyyyy").parse(invoiceRow[1]);
                        invoices.add(new InvoiceHeader(Integer.parseInt(invoiceRow[0]),invoiceRow[1],invoiceRow[2]));
                    }
                } catch (IOException ex) {
                    JOptionPane.showConfirmDialog(null, new JLabel("No header file found 'InvoiceHeader.csv'"),"ERROR ", JOptionPane.DEFAULT_OPTION);
                    Logger.getLogger(MenuItemsActions.class.getName()).log(Level.SEVERE, null, ex);
                    System.exit(0);
                } catch (ParseException ex) {
                    JOptionPane.showConfirmDialog(null, new JLabel("Some Data is in the wrong format in 'InvoiceHeader.csv' file"),"ERROR in data format", JOptionPane.OK_CANCEL_OPTION);
                    Logger.getLogger(MenuItemsActions.class.getName()).log(Level.SEVERE, null, ex);
                    System.exit(0);    
                } catch (Exception ex){
                    JOptionPane.showConfirmDialog(null, new JLabel(ex.getMessage()),"ERROR in file format", JOptionPane.DEFAULT_OPTION);
                    return null;
                }
                
                
                JOptionPane.showConfirmDialog(null, 
                        "Please Choose the 'InvoiceLine.csv' file!", "Choose File", JOptionPane.DEFAULT_OPTION);                    
                jfc.showOpenDialog(jfc);
                invoiceLinesPath = jfc.getSelectedFile().getPath();
                //Try and catch to open the "InvoiceLine.csv" file and reads from it.
                try {

                    if(!jfc.getSelectedFile().getName().equals("InvoiceLine.csv")){
                        throw new Exception("Please make sure to choose a file with the correct name and format of InvoiceLine.csv");
                    }
                    br = new BufferedReader(new FileReader(invoiceLinesPath));
                    //skip first line of headers and adding each InvoiceLine to its InvoiceHeader.
                    //br.readLine();
                    
                    //starts checking if each line corresponds to an invoice Header, if yes
                    // the line will be parsed into an InvoiceLine and added to the Invoiceheader.
                    while ((line = br.readLine()) != null){
                        String[] invoiceLineRow = line.split(",");
                        
                        for(int i =0;i<invoices.size();i++){
                            if(invoices.get(i).getInvoiceNum() == Integer.parseInt(invoiceLineRow[0])){
                                invoices.get(i).addInvoiceLine(
                                        new InvoiceLine(Integer.parseInt(invoiceLineRow[0]),invoiceLineRow[1],Double.parseDouble(invoiceLineRow[2]),Integer.parseInt(invoiceLineRow[3]))
                                );
                            }
                        }
                    }
                } catch (IOException ex) {
                    JOptionPane.showConfirmDialog(null, new JLabel("No file found 'InvoiceLine.csv'"),"ERROR", JOptionPane.DEFAULT_OPTION);
                    Logger.getLogger(MenuItemsActions.class.getName()).log(Level.SEVERE, null, ex);
                    System.exit(0);
                } catch (Exception ex) {
                    JOptionPane.showConfirmDialog(null, new JLabel(ex.getMessage()),"ERROR in file format", JOptionPane.DEFAULT_OPTION);
                }
                
                //printing the data that was parsed from the CSV file.
//                for(int i =0;i<invoices.size();i++){
//                    System.out.print(invoices.get(i).getInvoiceNum() +" "+invoices.get(i).getInvoiceDate() +" "+
//                            invoices.get(i).getCustomerName());
//                    System.out.println();
//                    
//                    for(int x =0;x<invoices.get(i).getInvoiceLines().size();x++){
//                        InvoiceLine il = invoices.get(i).getInvoiceLines().get(x);
//                        System.out.print(il.getInvoiceNum() +" "+il.getItemName()+" "+il.getItemPrice() + " " + il.getItemCount());
//                         System.out.println();
//                    }
//                    System.out.println();
//                }
        INVOICES = invoices;
        return invoices;
    }
    
    
    public static void WriteFile(ArrayList<InvoiceHeader> dataRows){
        //cvs file headers for both InvoiceHeader and InvoiceLine files.
        String[] invoicesHeadersHeaders = {"invoiceNum","invoiceDate","CustomerName"};
        String[] invoicesLinesHeaders = {"invoiceNum","itemName","itemPrice","Count"};

        FileWriter fileWriter = null;
        
        try {
            fileWriter = new FileWriter(new File(invoiceHeaderPath));
            //write the first line headers here.
//            StringBuilder headers = new StringBuilder();
//            for (String invoicesHeadersHeader : invoicesHeadersHeaders) {
//                headers.append(invoicesHeadersHeader);
//                headers.append(',');
//            }
//            headers.append("\n");
//            fileWriter.write(headers.toString()); 
        
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
            JOptionPane.showConfirmDialog(null, new JLabel("No header file found 'InvoiceHeader.csv'"),"ERROR", JOptionPane.OK_CANCEL_OPTION);
            Logger.getLogger(MenuItemsActions.class.getName()).log(Level.SEVERE, null, ex);
            System.exit(0);
        }
        
        try {
            fileWriter = new FileWriter(new File(invoiceLinesPath));
            //write headers first line here.
//            StringBuilder headers = new StringBuilder();
//            for (String invoicesLineHeader : invoicesLinesHeaders) {
//                headers.append(invoicesLineHeader);
//                headers.append(',');
//            }
//            headers.append("\n");
//            fileWriter.write(headers.toString()); 
        
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
            JOptionPane.showConfirmDialog(null, new JLabel("No file found 'InvoiceLine.csv'"),"ERROR", JOptionPane.OK_CANCEL_OPTION);
            Logger.getLogger(MenuItemsActions.class.getName()).log(Level.SEVERE, null, ex);
            System.exit(0);
        }
        
        
    }
    
    
    //A Static Source of the Data for the whole application.
    public static ArrayList<InvoiceHeader> getInvoices(){
        return INVOICES;
    }
    
    public static InvoiceHeader getInvoice(int invoiceNo){
        for(InvoiceHeader invoice: INVOICES){
            if(invoice.getInvoiceNum() == invoiceNo){
                return invoice;
            }
        }
        return null;
    }
    
    //A Static array of headers for Singleton design pattern for easier Modifcation.
    public static String[] getInvoicesTableHeaders(){
        return INVOICES_HEADERS;
    }
    
    //reformats the invoices data into a 2D array for the Jtable and rest of components to use.
    public static String[][] getInvoicesTableData(){
        String[][] data;
        data = new String[INVOICES.size()][INVOICES_HEADERS.length];
        
        for(int i=0;i<INVOICES.size();i++){
            data[i] = INVOICES.get(i).getTableFormat();
        }
        
        return data;
    }
    
    
    //A Static array of headers for Singleton design pattern for easier Modifcation.
    public static String[] getInvoicesItemsTableHeaders(){
        return INVOICES_ITEMS_HEADERS;
    }
    
    //reformats all of invoices items data into a 2D array for the Jtable and rest of components to use.
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
                data[itemsCount]= il.getTableFormat();
                itemsCount++;
            }
        }
        
        return data;
    }
    
    //reformats a single instance of invoice item into a 2D array for the Jtable and rest of components to use.
    public static String[][] getInvoicesItemsTableData(int invoiceNo){
        String[][] data;
        int itemsCount = 0;
        InvoiceHeader ih = null;

        for(InvoiceHeader invoice: INVOICES){
            if(invoice.getInvoiceNum() == invoiceNo){
                ih = invoice;
                itemsCount=ih.getInvoiceLines().size();
            }
        }
        
        data = new String[itemsCount][INVOICES_ITEMS_HEADERS.length];
        itemsCount =0;
        
        for(InvoiceLine il: ih.getInvoiceLines()){
            data[itemsCount]= il.getTableFormat();
            itemsCount++;
        }        
        return data;
    }
    
    //finds and return a certain Invoice data after reformatting.
    public static String[] getInvoiceData(int invoiceNo){
        String[] data = null;
        int itemsCount = 0;

        for(InvoiceHeader invoice: INVOICES){
            if(invoice.getInvoiceNum() == invoiceNo){
                data = invoice.getTableFormat();
            }
        }
             
        return data;
    }
    
    //updates an invoice data given the input, and calls the mainframe update method to reflect the
    //chnages on the GUI
    public static void updateInvoice(String[] data){
        for(InvoiceHeader ih: INVOICES){
            if(ih.getInvoiceNum() == Integer.parseInt(data[0])){
                ih.setInvoiceDate(data[1]);
                ih.setCustomerName(data[2]);
            }
        }
        MainFrame.updateTables();
    }
    
    
    public static InvoiceHeader addInvoice(String[] data){
        InvoiceHeader invoice = new InvoiceHeader(
        INVOICES.get(INVOICES.size()-1).getInvoiceNum()+1, data[0], data[1]);
        
        INVOICES.add(invoice);
        return invoice;
    }
    
    public static void deleteInvoice(String invoiceNo){
        for(InvoiceHeader invoice: INVOICES){
            if(invoice.getInvoiceNum() == Integer.parseInt(invoiceNo)){
                INVOICES.remove(invoice);
            }
        }
        MainFrame.updateTables();
    }
    
    public static void deleteInvoiceByTableIndex(int invoiceNo){
        INVOICES.remove(invoiceNo);
        MainFrame.updateTables();
    }
    
    public static void deleteInvoiceItemByTableIndex(String invoiceIndex,String invoiceItem){
        for(InvoiceHeader ih: INVOICES){ 
            if(ih.getInvoiceNum() == Integer.parseInt(invoiceIndex)){          
                for(InvoiceLine il: ih.getInvoiceLines()){
                    if(il.getItemName().equals(invoiceItem)){
                        ih.removeInvoiceLine(il);
                        break;
                    }
                }
            }
        }
        MainFrame.updateTables();
    }
    
    
    public static void createInvoiceDialog(){
        JTextField dateField = new JTextField(10);
        JTextField nameField = new JTextField(50);

        JPanel myPanel = new JPanel();
        myPanel.add(new JLabel("date in DD-MM-YYYY format:"));
        myPanel.add(dateField);
        myPanel.add(Box.createHorizontalStrut(15)); // a spacer
        myPanel.add(new JLabel("Customer Name:"));
        myPanel.add(nameField);

        String[] data = new String[2];
        int result = JOptionPane.showConfirmDialog(null, myPanel,
        "Please Enter the invoice data and Customer Name", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            data[0] = dateField.getText();
            data[1] = nameField.getText();
            userInvoiceItemDialog(addInvoice(data));
            MainFrame.updateTables();
        }
    }
    
    
    public static void userInvoiceItemDialog(InvoiceHeader invoice){
        JTextField itemNameField = new JTextField(50);
        JTextField itemPriceField = new JTextField(4);
        JTextField itemCountField = new JTextField(5);

        JPanel myPanel = new JPanel();
        myPanel.add(new JLabel("Item name:"));
        myPanel.add(itemNameField);
        myPanel.add(Box.createHorizontalStrut(15));
        myPanel.add(new JLabel("Item Price:"));
        myPanel.add(itemPriceField);
        myPanel.add(Box.createHorizontalStrut(15));
        myPanel.add(new JLabel("Item Count:"));
        myPanel.add(itemCountField);

        String[] data = new String[3];
        
        int result= JOptionPane.showOptionDialog(null, 
        myPanel, 
        "Please input the purchase details", 
        JOptionPane.OK_CANCEL_OPTION, 
        JOptionPane.INFORMATION_MESSAGE, 
        null, 
        new String[]{"input another after this?", "last item."},
        "default");
       
        data[0] = itemNameField.getText();
        data[1] = itemPriceField.getText();
        data[2] = itemCountField.getText();
            
        InvoiceLine il = new InvoiceLine(invoice.getInvoiceNum(),data[0],Double.parseDouble(data[1]),Integer.parseInt(data[2]));
        invoice.addInvoiceLine(il);
        if (result == JOptionPane.OK_OPTION) {
            userInvoiceItemDialog(invoice);
        } 
    }
    
}

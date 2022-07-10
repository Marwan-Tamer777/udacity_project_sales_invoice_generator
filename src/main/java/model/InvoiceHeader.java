/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.ArrayList;
import model.FileOperations;


/**
 *
 * @author dell
 */
public class InvoiceHeader {
    private int invoiceNum;
    private String invoiceDate;
    private String customerName;
    private ArrayList<InvoiceLine> invoiceLines;
    
    public InvoiceHeader(){}
    
    public InvoiceHeader(int i,String d,String c){
        setInvoiceNum(i);
        setInvoiceDate(d);
        setCustomerName(c);
        invoiceLines = new ArrayList<InvoiceLine>();
    }
    
    public int getInvoiceNum(){
        return invoiceNum;
    }
    
    public String getInvoiceDate(){
        return invoiceDate;
    }
    
    public String getCustomerName(){
        return customerName;
    }
    
    public ArrayList<InvoiceLine> getInvoiceLines(){
        return invoiceLines;
    }
    
    public void setInvoiceNum(int i){
        invoiceNum = i;
    }
    
    public void setInvoiceDate(String d){
        invoiceDate = d;
    }
    
    public void setCustomerName(String c){
        customerName = c;
    }
    
    public void addInvoiceLine(InvoiceLine il){
        invoiceLines.add(il);
    }
    
    public String[] getTableFormat(){
        String[] data = new String[FileOperations.getInvoicesTableHeaders().length];
        int total =0;
        data[0] = String.valueOf(getInvoiceNum());
        data[1] = getInvoiceDate();
        data[2] = getCustomerName();
        for(InvoiceLine il: getInvoiceLines()){
            total+=il.getItemPrice()*il.getItemCount();
        }
        data[3] = String.valueOf(total);
        return data;
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author dell
 */
public class InvoiceLine {
    private int invoiceNum;
    private String itemName;
    private int itemPrice;
    private int itemCount;
    
    public InvoiceLine(){
        
    }
    
    public InvoiceLine(int i,String n,int p,int c){
        setInvoiceNum(i);
        setItemName(n);
        setItemPrice(p);
        setItemCount(c);
    }
    
    public int getInvoiceNum(){
        return invoiceNum;
    }
    
    public String getItemName(){
        return itemName;
    }
    
    public int getItemPrice(){
        return itemPrice;
    }
    
    public int getItemCount(){
        return itemCount;
    }
    
    public void setInvoiceNum(int i){
        invoiceNum = i;
    }
    
    public void setItemName(String n){
        itemName = n;
    }
    
    public void setItemPrice(int p){
        itemPrice = p;
    }
    
    public void setItemCount(int c){
        itemCount = c;
    }
}

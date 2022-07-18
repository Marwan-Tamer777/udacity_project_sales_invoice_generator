/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */

package com.mycompany.udacity_project_sales_invoice_generator;

import java.util.ArrayList;
import model.FileOperations;
import model.InvoiceHeader;
import model.InvoiceLine;
import view.MainFrame;

/**
 *
 * @author dell
 */
public class Udacity_project_sales_invoice_generator {

    public static void main(String[] args) {
        System.out.println("Hello World!");
        
        MainFrame view = new MainFrame("udacity_project_sales_invoice_generator");
        view.setVisible(true);
    }
    
    
    public static void readFileTest(){
        ArrayList<InvoiceHeader> invoices= FileOperations.getInvoicces();
        for(int i=0;i<invoices.size();i++){
            InvoiceHeader ih = invoices.get(i);
            System.out.print("Invoice"+ih.getInvoiceNum()+"Num");
            System.out.println();
            System.out.println("{");
            System.out.print("Invoice"+ih.getInvoiceNum()+"Data (" + ih.getInvoiceDate() + "), " + ih.getCustomerName());
            System.out.println();
            for(InvoiceLine il: ih.getInvoiceLines()){
                System.out.print(il.getItemName() + ", "+il.getItemPrice() + ", "+ il.getItemCount());
                System.out.println();
            }
            System.out.println();
            System.out.println("}");
            System.out.println();
        }
    }
}

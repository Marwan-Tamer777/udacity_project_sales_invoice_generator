/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */

package com.mycompany.udacity_project_sales_invoice_generator;

import model.FileOperations;
import view.MainFrame;

/**
 *
 * @author dell
 */
public class Udacity_project_sales_invoice_generator {

    public static void main(String[] args) {
        System.out.println("Hello World!");
        FileOperations.ReadFile();
        MainFrame view = new MainFrame("udacity_project_sales_invoice_generator");
        view.setVisible(true);
        /*
        test method for fileOPerations readFile
        */
    }
}

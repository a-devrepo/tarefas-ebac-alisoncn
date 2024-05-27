package com.javabackend;

import com.javabackend.view.MainForm;

/**
 *
 * @author alison
 */
public class Clientes {

    public static void main(String[] args) {

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
               MainForm form =  new MainForm();
               form.setLocationRelativeTo(null);
               form.loadData();
               form.setVisible(true);
            }
        });
    }

}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package com.myproject.Form;

public class CHAO extends javax.swing.JDialog {

    public CHAO(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        initProgressBar();
        setLocationRelativeTo(null);
    }

    private void initProgressBar() {
        new Thread(() -> {
            for (int i = 0; i <= 100; i++) {
                try {
                    Thread.sleep(5); // Tốc độ chạy thanh tiến trình
                    pgbChao.setValue(i);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            // Khi chạy hết thanh tiến trình thì hiển thị form đăng nhập
            dispose();
        }).start();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated
    // Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pgbChao = new javax.swing.JProgressBar();
        jLabel1 = new javax.swing.JLabel();

        setUndecorated(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        getContentPane().add(pgbChao, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 610, 1390, 70));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/myproject/Image/background.jpg"))); // NOI18N
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            CHAO dialog = new CHAO(new javax.swing.JFrame(), true);
            dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosing(java.awt.event.WindowEvent e) {
                    System.exit(0);
                }
            });
            dialog.setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JProgressBar pgbChao;
    // End of variables declaration//GEN-END:variables
}

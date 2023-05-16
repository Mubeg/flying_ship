/*
 * Copyright (C) 2023 nerett
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package flyingship.gui;

//import javax.swing.*;
//import java.awt.*;

/**
 *
 * @author nerett
 */
public class GamePanel extends javax.swing.JPanel implements java.awt.event.ActionListener {

    private final int PANELSIZE = 600;
    private final int MAXLETS = 400;
    private final String imgDir = "img/";
    
    private java.awt.Image shipTexture;
    private java.awt.Image letTexture;
    private int shipX;
    private int shipY;
    private int[] letX = new int[MAXLETS];
    private int[] letY = new int[MAXLETS];
    private int nLets;
    private javax.swing.Timer tickTimer;
    private boolean inGame;
    
    /**
     * Creates new form GamePanel
     */
    public GamePanel() {
        initComponents();
        
        setBackground(java.awt.Color.black);
        loadTextures();
        initRendering();
        
        //setPreferredSize();
        //setMaximumSize();
        setSize(1280, 720); //! TODO refactor
    }
    
    public final void loadTextures() {
        javax.swing.ImageIcon shipIcon = new javax.swing.ImageIcon(imgDir + "ship.png");
        javax.swing.ImageIcon letIcon = new javax.swing.ImageIcon(imgDir + "let.png");
        shipTexture = shipIcon.getImage();
        letTexture = letIcon.getImage();
    }
    
    public final void initRendering() {
        shipX = 200;
        shipY = 200;
        nLets = 0;
        inGame = true;
        
        tickTimer = new javax.swing.Timer(100, this);
        tickTimer.start();
    }
    
    public void loadPosData() {
        //!TODO implement JNI receive
        
        shipX = 300;
        shipY = 350;
    }
    
    public void sendCursor() {
        //!TODO implement JNI send
        
        java.awt.Point cursorLocation = java.awt.MouseInfo.getPointerInfo().getLocation();
        double cursorX = cursorLocation.getX();
        double cursorY = cursorLocation.getY();
        
        //System.out.println(cursorX);
    }

    @Override
    public void actionPerformed(java.awt.event.ActionEvent e) {
        if (inGame) {
            sendCursor();
            loadPosData();
        }
        
        //System.out.println("Repaint0");
        repaint();
    }
    
    @Override
    protected void paintComponent(java.awt.Graphics g) {
        
        System.out.println("Repaint1");
        
        super.paintComponent(g);
        
        System.out.println("Repaint2");
        
        //if (inGame) {
            g.drawImage(shipTexture, shipX, shipY, this);
            
            for (int i = 0; i < nLets; i++) {
                g.drawImage(letTexture, letX[i], letY[i],this);
            }
        //}
    }
    
    /*
    @Override
    public void paint(java.awt.Graphics g) {
        
        System.out.println("Repaint1");
        
        super.paint(g);
        
        System.out.println("Repaint2");
        
        if (inGame) {
            g.drawImage(shipTexture, shipX, shipY, this);
            
            for (int i = 0; i < nLets; i++) {
                g.drawImage(letTexture, letX[i], letY[i],this);
            }
        }
    }
    */
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}

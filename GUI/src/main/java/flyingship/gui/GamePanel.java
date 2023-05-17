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
//import JNI.flyingship.src.*;
import JNI.flyingship.src.Messenger;
import JNI.flyingship.src.Message;
import JNI.flyingship.src.MessagesTypes;
import JNI.flyingship.src.SenderIds;

/**
 *
 * @author nerett
 */
public class GamePanel extends javax.swing.JPanel implements java.awt.event.ActionListener {

    private final int SIZEOF_INT = 4;
    
    private final int UPDATEINTERVAL = 100;
    private final int MAXLETS = 400;
    private final String imgDir = "img/";
    
    private java.awt.Image shipTexture;
    private java.awt.Image letTexture;
    
    private GameEntity ship;
    private GameEntity[] lets;
    
    private Thread handler;
    private Thread cursorUpdater;

    private int nLets;
    
    private javax.swing.Timer tickTimer;
    private boolean inGame;
    
    private Messenger messenger;
    
    /**
     * Creates new form GamePanel
     */
    public GamePanel() {
        initComponents();
        
        setBackground(java.awt.Color.black);
        loadTextures();
        initRendering();
    }
    
    public final void loadTextures() {
        javax.swing.ImageIcon shipIcon = new javax.swing.ImageIcon(imgDir + "ship.png");
        javax.swing.ImageIcon letIcon = new javax.swing.ImageIcon(imgDir + "let.png");
        shipTexture = shipIcon.getImage();
        letTexture = letIcon.getImage();
    }
    
    public final void initRendering() {
        
        ship = new GameEntity(shipTexture, 100, 100, 70, 70);
        lets = new GameEntity[MAXLETS];
        for (int i = 0; i < MAXLETS; i++) {
            lets[i] = new GameEntity(letTexture, 200, 200, 50, 50);
        }
        
        messenger = new Messenger(SenderIds.Frontend.value());
        
        handler = new Thread(new GameMsgHandler(this, messenger));
        handler.start();
        cursorUpdater = new Thread(new GameCursorUpdater(messenger));
        cursorUpdater.start();
        
        nLets = 0;
        inGame = true;
        
        tickTimer = new javax.swing.Timer(UPDATEINTERVAL, this);
        tickTimer.start();
    }
    
    public synchronized void setShip(int x, int y, int width, int height) {
        
        ship.move(x, y);
        ship.resize(width, height);
    }
    
    public synchronized void setShip(int centralX, int centralY, int radius) {
        
        int diameter = 2 * radius;
        int x = centralX - radius;
        int y = centralY - radius;
        
        ship.move(x, y);
        ship.resize(diameter, diameter);
    }
    
    public synchronized void setLets(int start, int nLets, int[] parameters) {
        
        this.nLets = nLets;
        for(int i = start; i < nLets+start; i+=3) {
            setLet(i, parameters[i], parameters[i+1], parameters[i+2]);
        }
    }
    
    public synchronized void setLet(int i, int centralX, int centralY, int radius) {
        
        int diameter = 2 * radius;
        int x = centralX - radius;
        int y = centralY - radius;
        
        lets[i].move(x, y);
        lets[i].resize(diameter,diameter);
    }
    
    public synchronized GameEntity getShip() {
        return ship;
    }
    
    public synchronized GameEntity getLet(int i) {
        return lets[i];
    }
    
    public synchronized void endGame() {
        inGame = false;
    }
    
    public synchronized boolean inGame() {
        return inGame;
    }

    @Override
    public void actionPerformed(java.awt.event.ActionEvent e) {
        
        /*
        if (inGame() == true) {
        }
        */
        
        repaint();
    }
    
    @Override
    protected void paintComponent(java.awt.Graphics g) {
        
        super.paintComponent(g);
                
        if (inGame() == true) {
            getShip().draw(g, this);
            
            for (int i = 0; i < nLets; i++) {
                getLet(i).draw(g, this);
            }
        }
        
        System.out.println("[GUI] paintComponent nLets = " + nLets);
        System.out.println("[GUI] paintComponent");
        for(int i = 0; i < nLets; i++) {
            getLet(i).print();
        }
    }
    
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

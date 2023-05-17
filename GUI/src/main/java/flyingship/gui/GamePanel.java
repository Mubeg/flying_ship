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
        
        nLets = 0;
        inGame = true;
        
        tickTimer = new javax.swing.Timer(UPDATEINTERVAL, this);
        tickTimer.start();
    }
    
    public void loadPosData() {
        
        /*
        Message message = messenger.getMessage();
        while ( message != null) {
            
            
            message = messenger.getMessage();
        }
        */
        
        //!TODO debug
        /*
        byte[] byteData = message.get_data();
        java.nio.ByteBuffer byteBuffer = java.nio.ByteBuffer.wrap(byteData);
        java.nio.IntBuffer intBuffer = byteBuffer.asIntBuffer();
        int[] intData = new int[(MAXLETS+1)*4+1];
        intBuffer.get(intData);
        */
        
        //!TODO implement
        /*
        ship.move(,);
        ship.resize(,);
        
        nLets = ;
        for (int i = 0; i < nLets; i++) {
            lets[i].move(,);
            lets[i].resize(,);
        }
        */
    }
    
    public void sendCursor() {
        
        java.awt.Point cursorLocation = java.awt.MouseInfo.getPointerInfo().getLocation();
        int cursorX = (int)cursorLocation.getX();
        int cursorY = (int)cursorLocation.getY();
        
        
        int[] cursorPos = { cursorX, cursorY };

        java.nio.ByteBuffer byteBuffer = java.nio.ByteBuffer.allocate(cursorPos.length * SIZEOF_INT); //!TODO debug
        byteBuffer.order(java.nio.ByteOrder.LITTLE_ENDIAN);
        java.nio.IntBuffer intBuffer = byteBuffer.asIntBuffer();
        intBuffer.put(cursorPos);
        
        /*
        byte[] arr = byteBuffer.array();
        System.out.println(java.util.Arrays.toString(arr));
        */
        
        Message message = new Message(MessagesTypes.SendInfo.value(), SenderIds.Backend.value(), byteBuffer.array());
        //messenger.sendMessage(message);
        
        //System.out.println(cursorX);
        //ship.move((int)cursorX, (int)cursorY);
    }
    
    public synchronized void setShip(int x, int y, int width, int height) {
        
        ship.move(x, y);
        ship.resize(width, height);
    }
    
    public synchronized void setLets(int start, int nLets, int[] parameters) {
        
        this.nLets = nLets;
        for(int i = start; i < nLets+start; i+=4) {
            lets[i].move(parameters[i], parameters[i+1]);
            lets[i].resize(parameters[i+2], parameters[i+3]);
        }
    }
    
    public synchronized GameEntity getShip() {
        return ship;
    }
    
    public synchronized GameEntity getLet(int i) {
        return lets[i];
    }

    @Override
    public void actionPerformed(java.awt.event.ActionEvent e) {
        if (inGame) {
            sendCursor();
            loadPosData();
        }
        
        repaint();
    }
    
    @Override
    protected void paintComponent(java.awt.Graphics g) {
        
        super.paintComponent(g);
        
        //System.out.println("Repaint");
        
        if (inGame) {
            getShip().draw(g, this);
            
            for (int i = 0; i < nLets; i++) {
                getLet(i).draw(g, this);
            }
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

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

import JNI.flyingship.src.Messenger;
import JNI.flyingship.src.Message;
import JNI.flyingship.src.MessagesTypes;
import JNI.flyingship.src.SenderIds;

/**
 *
 * @author nerett
 */
public class GameMsgHandler implements Runnable {
    
    final static MessagesTypes[] types = MessagesTypes.values();
    final static SenderIds[] senders = SenderIds.values();
    
    private final int MAXLETS = 400;
    private final int SIZEOF_INT = 4;
    
    GamePanel panel;
    Messenger messenger;
    
    public GameMsgHandler(GamePanel parentPanel, Messenger parentMessenger) {
        
        this.panel = parentPanel;
        this.messenger = parentMessenger;
    }
    
    @Override
    public void run() {
        
        Message message;
        while(true) {
            
            message = messenger.getMessage();
            //if(message != null){
                handleMessage(message);
            //}
            
            try {
                java.lang.Thread.sleep(500);
            }
            catch(Exception e){
                System.out.println("[GUI] GameMsgHandler sleep exception");
            }
        }
    }
    
    protected void handleMessage(Message message) {
        
        if(message == null) {
            
            System.out.println("[GUI] handleMessage: null message");
            return;
        }
        
        System.out.println("[GUI] handleMessage: new message");
        System.out.println("[GUI] handleMessage: sender: " + message.sender);
        System.out.println("[GUI] handleMessage: type: " + message.type);
        System.out.println("[GUI] handleMessage: data: " + message.get_data());
        
        switch(types[message.type]) {
            case BadType:
                break;
            case RequestSettings:
                break;
            case SettingsUpdate:
                break;
            case Pause:
                break;
            case Stop:
                break;
            case Resume:
                break;
            case UpdateFrame:
                System.out.println("[GUI] handleMessage UpdateFrame switch");
                handleUpdateFrame(message);
                break;
            case Checkin:
                break;
            case ReCheckin:
                break;
            case RequestInfo:
                break;
            case SendInfo:
                break;
            case End:
                break;
            case StartGame:
                break;
            case PlayerLogIn:
                break;
            case UpdateCursor:
                break;
            case GameOver:
                handleGameOver(message);
                break;
            default:
                break;
        }
    }
    
    protected void handleUpdateFrame(Message message) {
        
        System.out.println("[GUI] handleUpdateFrame new message");
        
        int[] intData = message.get_data();
        
        panel.setShip(intData[0], intData[1], intData[2], intData[3]);
        panel.setLets(5, intData[4], intData);
    }
    
    protected void handleGameOver(Message message) {
        
        panel.endGame();
    }
    
    protected int[] byteToInt(byte[] byteData) {
        
        java.nio.ByteBuffer byteBuffer = java.nio.ByteBuffer.wrap(byteData);
        java.nio.IntBuffer intBuffer = byteBuffer.asIntBuffer();
        int[] intData = new int[Message.DATA_LEN*SIZEOF_INT];
        intBuffer.get(intData);
        
        return intData;
    }
}

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
public class GameCursorUpdater implements Runnable {
    
    private static int SIZEOF_INT = 4;
    
    Messenger messenger;
    
    public GameCursorUpdater(Messenger parentMessenger) {
        
        this.messenger = parentMessenger;
    }
    
    @Override
    public void run() {
        
        while(true) {
            sendCursor();
            
            try {
                java.lang.Thread.sleep(300);
            }
            catch(Exception e){
                System.out.println("[GUI] GameCursorUpdater sleep exception");
            }
        }
    }
    
    public void sendCursor() {
        
        java.awt.Point cursorLocation = java.awt.MouseInfo.getPointerInfo().getLocation();
        int cursorX = (int)cursorLocation.getX();
        int cursorY = (int)cursorLocation.getY();
        
        
        int[] cursorPos = { cursorX, cursorY };
        
        Message message = new Message(MessagesTypes.UpdateCursor.value(), SenderIds.Backend.value(), cursorPos);
        messenger.sendMessage(message);
        //ship.move((int)cursorX, (int)cursorY);
    }
    
}

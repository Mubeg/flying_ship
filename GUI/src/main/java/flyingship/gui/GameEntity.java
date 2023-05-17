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

/**
 *
 * @author nerett
 */
public class GameEntity {
    
    private java.awt.Image texture;
    
    private int x;
    private int y;
    private int radius;
    
    public GameEntity(java.awt.Image texture, int x, int y, int radius) {
        setTexture(texture);
        
        transform(x, y, radius);
    }
    
    public GameEntity() {
        this.texture = null;
        
        transform(0, 0, 0);
    }
    
    public final void setTexture(java.awt.Image texture) {
        this.texture = texture;
    }
    
    public final void move(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    public final void resize(int radius) {
        this.radius = radius;
    }
    
    public final void transform(int x, int y, int radius) {
        move(x, y);
        resize(radius);
    }
    
    public int diameter() {
        return 2 * radius;
    }
    
    public int cornerX() {
        return x - radius;
    }
    
    public int cornerY() {
        return y - radius;
    }
    
    public void draw(java.awt.Graphics g, java.awt.image.ImageObserver observer) {
        g.drawImage(texture, cornerX(), cornerY(), diameter(), diameter(), observer);
    }
    
    public void print() {
        System.out.println("[GUI] print" );
        System.out.println("[GUI] texture" + texture);
        System.out.println("[GUI] x =" + x );
        System.out.println("[GUI] y =" + y );
        System.out.println("[GUI] radius" + radius );
    }
}

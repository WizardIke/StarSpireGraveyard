package CoreClasses;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;

/**
 * Created by Isaac on 14/01/2017.
 */
public class TextBox {
    public String text = "";
    public float posX, posY;
    public float widthInPixels;
    public float heightInPixels;
    public boolean active = false;
    public float flashTime;
    private Rectangle2D.Double outLine;

    public TextBox(float posX, float posY, float lengthInPixels, float heightInPixels) {
        this.posX = posX;
        this.posY = posY;
        this.widthInPixels = lengthInPixels;
        this.heightInPixels = heightInPixels;
        this.outLine = new Rectangle2D.Double(posX, posY - heightInPixels, widthInPixels, heightInPixels);
    }

    public void update(float dt) {
        if(active) {
            flashTime += dt;
            if(flashTime >= 0.5f) {
                flashTime = -0.5f;
            }
        }
    }

    public void render(Graphics2D graphics) {
        if(flashTime < 0.0) {
            graphics.setClip(outLine);
            graphics.drawString(text + '|', posX + (heightInPixels * 0.15f), posY - (heightInPixels * 0.2f));
        }
        else {
            graphics.setClip(outLine);
            graphics.drawString(text, posX + (heightInPixels * 0.15f), posY - (heightInPixels * 0.2f));
        }
        graphics.setClip(null);
        graphics.draw(outLine);
    }

    public void mousePressed(MouseEvent event) {
        if(event.getX() > posX && event.getX() < posX + widthInPixels &&
                event.getY() >  posY - heightInPixels && event.getY() < posY) {
           active = true;
           flashTime = -0.9f;
        }
        else {
            active = false;
            flashTime = 1.0f;
        }
    }

    public void keyPressed(KeyEvent event) {
        if(active) {
            if(event.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
                if(text.length() != 0) {
                    text = text.substring(0, text.length() - 1);
                }
            }
            else if(event.getKeyCode() == KeyEvent.VK_ENTER) {
                active = false;
                flashTime = 1.0f;
            }
            else {
                char key = event.getKeyChar();
                if((key >= 'A' && key <= 'Z') || (key >= 'a' && key <= 'z') || key == ' ' || (key >= '0' && key <= '9')
                        || key == '.') {
                    text += key;
                }
            }
        }
    }
}

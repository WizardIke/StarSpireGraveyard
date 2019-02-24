package Components;

import CoreClasses.Main;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import static java.lang.Math.sqrt;

/**
 * Created by Isaac on 1/09/2017.
 */
public class NecromancerPlayerController {

    public void mousePressed(Main main, MouseEvent event, SpellBook spellBook, Position position, GameObject ths) {
        if (event.getButton() == MouseEvent.BUTTON1) {
            if (spellBook.spells[0].isCastable(ths)) {
                castRaiseSkeleton(main, event.getX(), event.getY(), spellBook, position, ths);
            }
        }
    }

    public void keyPressed(Main main, KeyEvent event, SpellBook spellBook, Position position, GameObject ths) {
        switch (event.getKeyCode()) {
            case KeyEvent.VK_1 :
                if(spellBook.spells[1].isCastable(ths)){
                    spellBook.spells[1].cast(main, ths);
                }
                break;
            case KeyEvent.VK_2 :
                if(spellBook.spells[2].isCastable(ths)){
                    spellBook.spells[2].cast(main, ths);
                }
                break;
            case KeyEvent.VK_3 :
                if(spellBook.spells[3].isCastable(ths)){
                    castRaiseSkeletons(main, ((float) MouseInfo.getPointerInfo().getLocation().x - main.window.frame.getLocation().x -
                                    main.window.frame.getInsets().left),
                            ((float) MouseInfo.getPointerInfo().getLocation().y - main.window.frame.getLocation().y -
                                    main.window.frame.getInsets().top), spellBook, position, ths);
                }
                break;
        }
    }

    public void update(Main main, SpellBook spellBook, Position position, GameObject ths) {
        if(main.input.mouse1Down && spellBook.spells[0].isCastable(ths)) {
            castRaiseSkeleton(main, ((float) MouseInfo.getPointerInfo().getLocation().x - main.window.frame.getLocation().x -
                            main.window.frame.getInsets().left),
                    ((float) MouseInfo.getPointerInfo().getLocation().y - main.window.frame.getLocation().y -
                            main.window.frame.getInsets().top), spellBook, position, ths);
        }
    }

    private void castRaiseSkeletons(Main main, float targetX, float targetY, SpellBook spellBook, Position position,
                                    GameObject ths){
        float dirX = (targetX - 0.5f * main.settings.screenSizeX) / main.settings.screenSizeX;
        float dirY = (targetY - 0.5f * main.settings.screenSizeY) / main.settings.screenSizeX;
        float length = (float)sqrt(dirX * dirX + dirY * dirY);
        if(length > spellBook.spells[3].getCastRange()){
            dirX = dirX / length * spellBook.spells[3].getCastRange();
            dirY = dirY / length * spellBook.spells[3].getCastRange();
        }
        spellBook.spells[3].cast(main, ths, position.x, position.y,
                position.x + dirX, position.y + dirY);
    }

    private void castRaiseSkeleton(Main main, float targetX, float targetY, SpellBook spellBook, Position position,
                                   GameObject ths) {
        float dirX = (targetX - 0.5f * main.settings.screenSizeX) / main.settings.screenSizeX;
        float dirY = (targetY - 0.5f * main.settings.screenSizeY) / main.settings.screenSizeX;
        float length = (float)sqrt(dirX * dirX + dirY * dirY);
        if(length > spellBook.spells[0].getCastRange()){
            dirX = dirX / length * spellBook.spells[0].getCastRange();
            dirY = dirY / length * spellBook.spells[0].getCastRange();
        }
        spellBook.spells[0].cast(main, ths, position.x, position.y,
                position.x + dirX, position.y + dirY);
    }
}

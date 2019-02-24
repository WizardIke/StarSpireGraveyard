package Components;

import CoreClasses.Main;

import java.awt.*;
import java.awt.event.MouseEvent;

/**
 * Created by Isaac on 1/09/2017.
 */
public class FireMagePlayerController {

    public void mousePressed(Main main, MouseEvent event, SpellBook spellBook, Position position, GameObject ths) {
        if (event.getButton() == MouseEvent.BUTTON1) {
            if (spellBook.spells[0].isCastable(ths)) {
                castLeftSpell(main, event.getX(), event.getY(), position, spellBook, ths);
            }
        }
    }

    private void castLeftSpell(Main main, float targetX, float targetY, Position position, SpellBook spellBook,
                                 GameObject ths) {
        float startPositionX = position.x;
        float startPositionY = position.y;
        spellBook.spells[0].cast(main, ths, startPositionX, startPositionY,
                startPositionX + (targetX - 0.5f * main.settings.screenSizeX) / main.settings.screenSizeX,
                startPositionY + (targetY - 0.5f * main.settings.screenSizeY) / main.settings.screenSizeX);
    }

    public void update(Main main, SpellBook spellBook, Position position, GameObject ths) throws Exception{
        if(main.input.mouse1Down && spellBook.spells[0].isCastable(ths)) {
            castLeftSpell(main, ((float) MouseInfo.getPointerInfo().getLocation().x - main.window.frame.getLocation().x -
                            main.window.frame.getInsets().left),
                    ((float) MouseInfo.getPointerInfo().getLocation().y - main.window.frame.getLocation().y -
                            main.window.frame.getInsets().top), position, spellBook, ths);
        }
    }
}

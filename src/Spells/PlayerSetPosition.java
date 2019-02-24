package Spells;

import Components.GameObject;
import Components.Position;
import CoreClasses.Main;

import java.io.DataOutputStream;
import java.io.IOException;

public class PlayerSetPosition implements Spell {
    @Override
    public void cast(Main main, GameObject caster, GameObject target) {
        Position casterPosition = caster.getPosition();
        main.viewMatrix.setToTranslation((-casterPosition.x + 0.5) * main.settings.screenSizeX,
                (-casterPosition.y + 0.5 * main.settings.screenSizeY / main.settings.screenSizeX) * main.settings.screenSizeX);
    }

    @Override
    public void save(DataOutputStream saveData) throws IOException{
        saveData.writeInt(TalentTypes.PlayerSetPosition);
    }
}

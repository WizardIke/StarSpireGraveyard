package Spells;

import Components.GameObject;
import Components.Position;
import CoreClasses.Main;

import java.io.DataOutputStream;
import java.io.IOException;

import static CoreClasses.NetworkMessageTypes.gameObject;
import static CoreClasses.NetworkMessageTypes.setPos;

public class PlayerSetPositionHost implements Spell {
    @Override
    public void cast(Main main, GameObject caster, GameObject target) {
        Position casterPosition = caster.getPosition();
        main.viewMatrix.setToTranslation((-casterPosition.x + 0.5) * main.settings.screenSizeX,
                (-casterPosition.y + 0.5 * main.settings.screenSizeY / main.settings.screenSizeX) * main.settings.screenSizeX);

        final DataOutputStream networkOut = main.networkConnection.getNetworkOut();
        try {
            networkOut.writeInt(14);
            networkOut.writeByte(gameObject);
            networkOut.writeInt(main.gameObjects.indexOf(caster));
            networkOut.writeByte(setPos);
            networkOut.writeFloat(casterPosition.x);
            networkOut.writeFloat(casterPosition.y);
        } catch (IOException e) {
            main.connectionLost();
        }
    }

    @Override
    public void save(DataOutputStream saveData) throws IOException{
        saveData.writeInt(TalentTypes.PlayerSetPositionHost);
    }
}
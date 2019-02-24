package Creatures;

import Components.*;
import CoreClasses.*;
import Spells.FireBoltSpellClient;
import Spells.PlayerSetPosition;
import Spells.Spell;

import java.awt.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import static CoreClasses.NetworkMessageTypes.*;

/**
 * Created by Isaac on 25/01/2017.
 */
public class FireMageClient implements Creature {
    protected static final float startingMaxHealth = 100f;
    protected static final float startingSpeed = 0.25f;
    protected static final float radius = 0.02f;
    protected static final float mass = 70.0f;

    protected final static float armorToughness = 0.1f;
    protected static final float startingHealthRegen = 0.9f;
    protected static final float startingResistance = 0.2f;

    protected Name name;
    protected PlayerMovementNetworked movement;
    protected RegeneratingHealth health;
    protected Renderer renderer;
    protected SpellBook spellBook;
    private FireMagePlayerController controller = new FireMagePlayerController();
    protected int awesomeness;


    public FireMageClient(final Main main, final String name, final float posX, final float posY)
            throws java.io.IOException{
        spellBook = new SpellBook(new Spell[]{new FireBoltSpellClient(0.4f, 0.5f, 2.0f, 10.0f)});
        this.name = new Name(name);
        this.movement = new PlayerMovementNetworked(new TriggerHitBox(this, new PlayerSetPosition(),
                new Position(posX, posY), radius, mass), startingSpeed);
        health = new RegeneratingHealth(new Resistances(startingResistance, startingResistance,
                startingResistance, startingResistance, startingResistance,
                startingResistance, startingResistance), armorToughness,startingMaxHealth,
                startingMaxHealth, startingHealthRegen);
        awesomeness = 0;
        renderer = new Renderer(main.sprites.fireMage, 0.4f);

        main.gameObjects.add(this);
        final DataOutputStream networkOut = main.networkConnection.getNetworkOut();
        networkOut.writeInt(10 + 2 * name.length());
        networkOut.writeByte(NetworkMessageTypes.gameObject);
        networkOut.writeInt(main.gameObjects.indexOf(this));
        networkOut.writeByte(setName);
        networkOut.writeInt(name.length());
        networkOut.writeChars(name);
    }

    public FireMageClient(Main main, DataInputStream saveData)
            throws java.io.IOException{
        spellBook = new SpellBook(saveData);
        name = new Name(saveData);
        movement = new PlayerMovementNetworked(this, saveData);
        health = new RegeneratingHealth(saveData);
        awesomeness = saveData.readInt();
        renderer = new Renderer(main.sprites.fireMage, 0.4f);

        final DataOutputStream networkOut = main.networkConnection.getNetworkOut();
        networkOut.writeInt(10 + 2 * name.name.length());
        networkOut.writeByte(NetworkMessageTypes.gameObject);
        networkOut.writeInt(main.gameObjects.indexOf(this));
        networkOut.writeByte(setName);
        name.save(networkOut);
    }

    @Override
    public void handleMessage(Main main, DataInputStream networkData) throws Exception {
        int message = networkData.read();
        switch(message){
            case castSpell:
                int message2 = networkData.read();
                switch(message2){
                    default :
                        main.connectionLost();
                        break;
                }
                break;
            case setAwesomeness:
                awesomeness = networkData.readInt();
                break;
            case setX: {
                float x = networkData.readFloat();
                main.viewMatrix.translate((movement.hitbox.position.x - x) * main.settings.screenSizeX, 0.0);
                movement.hitbox.position.x = x;
                break;
            }
            case setY: {
                float y = networkData.readFloat();
                main.viewMatrix.translate(0.0, (movement.hitbox.position.y - y) * main.settings.screenSizeX);
                movement.hitbox.position.y = y;
                break;
            }
            case setHealth :
                this.health.health = networkData.readFloat();
                break;
            case die:
                main.loose();
                break;
            case setName: {
                name = new Name(networkData);
                break;
            }
            case setPos : {
                float x = networkData.readFloat();
                float y = networkData.readFloat();
                main.viewMatrix.translate((movement.hitbox.position.x - x) * main.settings.screenSizeX,
                        (movement.hitbox.position.y - y) * main.settings.screenSizeX);
                movement.hitbox.position.x = x;
                movement.hitbox.position.y = y;
                break;
            }
            default :
                main.connectionLost();
                break;
        }
    }

    @Override
    public int getType() {
        return SaveType.FireMageClientPlayer;
    }

    @Override
    public void save(Main main, DataOutputStream saveData) throws IOException {
        saveData.writeInt(SaveType.FireMageClientPlayer);
        spellBook.save(saveData);
        name.save(saveData);
        movement.save(saveData);
        health.save(saveData);
        saveData.writeInt(awesomeness);
    }

    @Override
    public HitBox getHitbox()
    {
        return movement.hitbox;
    }

    @Override
    public void render(final Main main, final Graphics2D graphics) {
        final Position position = movement.hitbox.position;
        float dirX, dirY;
        boolean moving;
        if(main.input.dDown) {
            // Moving Right
            dirX = 1f;
            dirY = 0f;
            moving = true;
        } else if(main.input.aDown) {
            // Moving Left
            dirX = -1f;
            dirY = 0f;
            moving = true;
        } else if(main.input.wDown) {
            // Moving Up
            dirX = 0f;
            dirY = 1f;
            moving = true;
        } else if(main.input.sDown) {
            // Moving Down
            dirX = 0f;
            dirY = -1f;
            moving = true;
        } else {
            // Face down if not moving
            dirX = 0f;
            dirY = 1f;
            moving = false;
        }
        renderer.render(main, graphics, position, movement.hitbox.radius, dirX, dirY, moving);
    }

    @Override
    public Health getHealth() {
        return health;
    }

    @Override
    public int getAwesomeness(){
        return this.awesomeness;
    }

    @Override
    public void update(final Main main, final float frameTime) throws Exception {
        spellBook.update(main, frameTime);
        renderer.update(main, frameTime, this);
        movement.update(main, frameTime, this);
        health.update(frameTime);
        controller.update(main, spellBook, movement.hitbox.position, this);
    }

    @Override
    public Position getPosition() {
        return movement.hitbox.position;
    }

    @Override
    public Faction getFaction(){
        return Faction.Mage;
    }
}

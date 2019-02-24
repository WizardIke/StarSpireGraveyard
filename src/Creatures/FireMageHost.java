package Creatures;

import Components.*;
import CoreClasses.*;
import GameLevels.VictoryLevel;
import Spells.FireBoltSpellHost;
import Spells.Spell;

import java.awt.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import static CoreClasses.NetworkMessageTypes.*;

/**
 * Created by Isaac on 25/01/2017.
 */
public class FireMageHost implements Creature {
    protected static final float startingMaxHealth = 100f;
    private static final float startingHealthRegen = 0.9f;
    protected static final float mass = 70.0f;
    private static final float startingSpeed = 0.25f;
    private static final float radius = 0.02f;
    private static final float startingResistance = 0.2f;
    private final static float armorToughness = 0.1f;

    protected String name;
    protected Movement movement;
    protected PlayerHealthHost health;
    protected int awesomeness;
    private Renderer renderer;
    private SpellBook spellBook;

    public FireMageHost(Main main, float posX, float posY) throws java.io.IOException {
        spellBook = new SpellBook(new Spell[]{
                new FireBoltSpellHost(0.4f, 0.5f, 2.0f, 10.0f)});
        movement = new Movement(new CircleHitbox(this, new Position(posX, posY), radius, mass), startingSpeed);
        health = new PlayerHealthHost(new Resistances(startingResistance, startingResistance,
                startingResistance, startingResistance, startingResistance,
                startingResistance, startingResistance), armorToughness, startingMaxHealth,
                startingMaxHealth, startingHealthRegen);
        awesomeness = 0;
        renderer = new Renderer(main.sprites.fireMage, 0.4f);
        main.gameObjects.add(this);
    }

    public FireMageHost(Main main, DataInputStream saveData) throws java.io.IOException{
        spellBook = new SpellBook(saveData);
        health = new PlayerHealthHost(saveData);
        awesomeness = saveData.readInt();
        movement = new Movement(this, saveData);
        renderer = new Renderer(main.sprites.fireMage, 0.4f);
    }

    @Override
    public void handleMessage(Main main, DataInputStream networkData) throws Exception{
        int message = networkData.read();
        switch(message){
            case castSpell:
                int message2 = networkData.readByte();
                switch(message2){
                    case NetworkMessageTypes.fireBolt :
                        float dirX = networkData.readFloat();
                        float dirY = networkData.readFloat();
                        spellBook.spells[0].cast(main, this, movement.hitbox.position.x + dirX * movement.hitbox.radius,
                                movement.hitbox.position.y + dirY * movement.hitbox.radius, dirX, dirY);
                        break;
                    default:
                        main.connectionLost();
                        break;
                }
                break;
            case setAwesomeness:
                awesomeness = networkData.readInt();
                break;
            case setX:
                movement.hitbox.position.x = networkData.readFloat();
                break;
            case setY:
                movement.hitbox.position.y = networkData.readFloat();
                break;
            case setPos :
                movement.hitbox.position.x = networkData.readFloat();
                movement.hitbox.position.y = networkData.readFloat();
                break;
            case die:
                main.gameLevel = new VictoryLevel(main);
                break;
            case setName:
                int length = networkData.readInt();
                char[] name = new char[length];
                for(int i = 0; i < length; ++i){
                    name[i] = networkData.readChar();
                }
                this.name = new String(name);
                break;
            default :
                main.connectionLost();
                break;
        }
    }

    @Override
    public void gainAwesomeness(Main main, int amount){
        awesomeness += amount;

        final DataOutputStream networkOut = main.networkConnection.getNetworkOut();
        try {
            networkOut.writeInt(10);
            networkOut.writeByte(NetworkMessageTypes.gameObject);
            networkOut.writeInt(main.gameObjects.indexOf(this));
            networkOut.writeByte(setAwesomeness);
            networkOut.writeInt(awesomeness);
        } catch (IOException e) {
            main.connectionLost();
        }
    }

    @Override
    public void fullUpdate(Main main, DataOutputStream networkOut) throws java.io.IOException{
        networkOut.writeInt(10);
        networkOut.writeByte(NetworkMessageTypes.gameObject);
        networkOut.writeInt(main.gameObjects.indexOf(this));
        networkOut.writeByte(setHealth);
        networkOut.writeFloat(health.health);
    }

    @Override
    public int getType(){
        return SaveType.FireMageHostEnemyPlayer;
    }

    @Override
    public void save(Main main, DataOutputStream saveData) throws IOException {
        saveData.writeInt(SaveType.FireMageHostEnemyPlayer);
        spellBook.save(saveData);
        health.save(saveData);
        saveData.writeInt(awesomeness);
        movement.save(saveData);
    }

    @Override
    public void update(Main main, float frameTime) throws Exception {
        renderer.update(main, frameTime, this);
        spellBook.update(main, frameTime);
    }

    @Override
    public HitBox getHitbox()
    {
        return movement.hitbox;
    }

    @Override
    public void render(Main main, Graphics2D graphics) {
        final CircleHitbox hitbox = movement.hitbox;
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
        renderer.render(main, graphics, hitbox.position, hitbox.radius, dirX, dirY, moving);
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
    public Position getPosition() {
        return movement.hitbox.position;
    }

    @Override
    public Faction getFaction(){
        return Faction.Mage;
    }
}

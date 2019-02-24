package Creatures;

import Components.*;
import CoreClasses.*;
import Spells.*;

import java.awt.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import static CoreClasses.NetworkMessageTypes.*;

/**
 * Created by Isaac on 25/01/2017.
 */
public class NecromancerClient implements Creature {
    private static final float startingMaxHealth = 100.0f;
    private static final float radius = 0.02f;
    private static final float mass = 70.0f;

    private static final float armorToughness = 0.1f;
    private static final float startingHealthRegen = 0.9f;

    private static final float startingFireResistance = 1.0f;
    private static final float startingColdResistance = 1.0f;
    private static final float startingLightningResistance = 1.0f;
    private static final float startingArcaneResistance = 1.0f;
    private static final float startingBludgeoningResistance = 1.0f;
    private static final float startingPiecingResistance = 1.0f;
    private static final float startingSlashingResistance = 1.0f;

    private CircleHitbox hitbox;
    private Name name = new Name("");
    private RegeneratingHealth health;
    private Renderer renderer;
    private SpellBook spellBook;
    private int awesomeness;

    public NecromancerClient(Main main, float posX, float posY) throws java.io.IOException {
        spellBook = new SpellBook(necromancerClientPlayerSpells());
        hitbox = new CircleHitbox(this, new Position(posX, posY), radius, mass);
        awesomeness = 0;
        health = new RegeneratingHealth(new Resistances(startingFireResistance,
                startingColdResistance, startingLightningResistance, startingArcaneResistance,
                startingBludgeoningResistance, startingPiecingResistance, startingSlashingResistance),
                armorToughness, startingMaxHealth, startingMaxHealth, startingHealthRegen);
        renderer = new Renderer(main.sprites.necromancer, 0.4f);
        main.gameObjects.add(this);
    }

    public NecromancerClient(Main main, DataInputStream saveData) throws java.io.IOException{
        spellBook = new SpellBook(saveData);
        name = new Name(saveData);
        hitbox = new CircleHitbox(this, saveData);
        health = new RegeneratingHealth(saveData);
        awesomeness = saveData.readInt();
        renderer = new Renderer(main.sprites.necromancer, 0.4f);
    }

    private static Spell[] necromancerClientPlayerSpells(){
        Spell[] spells = new Spell[4];
        spells[0] = new RaiseSkeletonClient(5.0f);
        spells[1] = new RandomTeleport(50.0f, new DoNothing());
        spells[2] = new SummonSkeletonHordeClient(75.0f, 4,
                4, 5,3);
        spells[3] = new RaiseSkeletonsClient(20.0f, 5, 8);
        return spells;
    }

    @Override
    public void handleMessage(Main main, DataInputStream networkData) throws Exception{
        int message = networkData.read();
        switch(message){
            case castSpell:
                int message2 = networkData.read();
                switch(message2){
                    case raiseSkeleton :
                        spellBook.spells[0].cast(main, this, 0.0f, 0.0f, networkData.readFloat(),
                                networkData.readFloat());
                        break;
                    case raiseSkeletons :
                        spellBook.spells[3].handleMessage(main, networkData, this);
                        break;
                    case summonSkeletonHorde :
                        spellBook.spells[2].handleMessage(main, networkData, this);
                        break;
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
                hitbox.position.x = x;
                break;
            }
            case setY: {
                float y = networkData.readFloat();
                hitbox.position.y = y;
                break;
            }
            case setPos : {
                float x = networkData.readFloat();
                float y = networkData.readFloat();
                hitbox.position.x = x;
                hitbox.position.y = y;
                break;
            }
            case die:
                main.win();
                break;
            case setName: {
                name = new Name(networkData);
                break;
            }
            case setHealth :
                health.health = networkData.readFloat();
                break;
            default:
                main.connectionLost();
                break;
        }
    }

    @Override
    public int getType() {
        return SaveType.NecromancerClientEnemyPlayer;
    }

    @Override
    public HitBox getHitbox()
    {
        return hitbox;
    }

    @Override
    public void render(Main main, Graphics2D graphics) {
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
    public void save(Main main, DataOutputStream saveData) throws IOException {
        saveData.writeInt(SaveType.NecromancerClientEnemyPlayer);
        spellBook.save(saveData);
        name.save(saveData);
        hitbox.save(saveData);
        saveData.writeInt(awesomeness);
        health.save(saveData);
    }

    @Override
    public Health getHealth() {
        return health;
    }

    @Override
    public void update(Main main, float frameTime) throws Exception {
        spellBook.update(main, frameTime);
        renderer.update(main, frameTime, this);
    }

    @Override
    public Position getPosition() {
        return hitbox.position;
    }

    @Override
    public Faction getFaction() {
        return Faction.Necromancer;
    }
}

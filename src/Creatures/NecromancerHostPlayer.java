package Creatures;

import Components.*;
import CoreClasses.*;
import Spells.*;

import java.awt.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import static CoreClasses.NetworkMessageTypes.*;
import static java.lang.Math.sqrt;

/**
 * Created by Isaac on 25/01/2017.
 */
public class NecromancerHostPlayer implements Creature {
    private static final float startingMaxHealth = 100.0f;
    private static final float startingSpeed = 0.25f;
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

    private PlayerMovementNetworked movement;
    private Name name;
    private int awesomeness;
    private PlayerHealthHost health;
    private NecromancerPlayerController controller = new NecromancerPlayerController();
    private Renderer renderer;
    private SpellBook spellBook;

    public NecromancerHostPlayer(Main main, String name, float posX, float posY) throws java.io.IOException{
        spellBook = new SpellBook(necromancerHostPlayerSpells());
        movement = new PlayerMovementNetworked(new TriggerHitBox(this, new PlayerSetPositionHost(),
                new Position(posX, posY), radius, mass), startingSpeed);
        this.name = new Name(name);
        awesomeness = 0;
        health = new PlayerHealthHost(new Resistances(startingFireResistance,
                startingColdResistance, startingLightningResistance, startingArcaneResistance,
                startingBludgeoningResistance, startingPiecingResistance, startingSlashingResistance),
                armorToughness, startingMaxHealth, startingMaxHealth, startingHealthRegen);
        renderer = new Renderer(main.sprites.necromancer, 0.4f);

        main.gameObjects.add(this);
        final DataOutputStream networkOut = main.networkConnection.getNetworkOut();
        networkOut.writeInt(10 + 2 * name.length());
        networkOut.writeByte(NetworkMessageTypes.gameObject);
        networkOut.writeInt(main.gameObjects.indexOf(this));
        networkOut.writeByte(setName);
        networkOut.writeInt(name.length());
        networkOut.writeChars(name);
    }

    public NecromancerHostPlayer(Main main, DataInputStream saveData) throws java.io.IOException{
        spellBook = new SpellBook(saveData);
        movement = new PlayerMovementNetworked(this, saveData);
        name = new Name(saveData);
        awesomeness = saveData.readInt();
        health = new PlayerHealthHost(saveData);
        renderer = new Renderer(main.sprites.necromancer, 0.4f);

        final DataOutputStream networkOut = main.networkConnection.getNetworkOut();
        networkOut.writeInt(10 + 2 * name.name.length());
        networkOut.writeByte(NetworkMessageTypes.gameObject);
        networkOut.writeInt(main.gameObjects.indexOf(this));
        networkOut.writeByte(setName);
        name.save(networkOut);
    }

    private static Spell[] necromancerHostPlayerSpells(){
        Spell[] spells = new Spell[4];
        spells[0] = new RaiseSkeletonHost(5.0f);
        spells[1] = new RandomTeleport(50.0f, new PlayerSetPositionHost());
        spells[2] = new SummonSkeletonHordeHost(75.0f, 4,
                4, 5,3);
        spells[3] = new RaiseSkeletonsHost(20.0f, 5, 8);
        return spells;
    }


    @Override
    public void gainAwesomeness(Main main, int amount){
        final DataOutputStream networkOut = main.networkConnection.getNetworkOut();
        try {
            awesomeness += amount;
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
    public int getType() {
        return SaveType.NecromancerHostPlayer;
    }

    @Override
    public HitBox getHitbox()
    {
        return movement.hitbox;
    }

    @Override
    public void render(Main main, Graphics2D graphics) {
        CircleHitbox hitbox = movement.hitbox;
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
        saveData.writeInt(SaveType.NecromancerHostPlayer);
        spellBook.save(saveData);
        movement.save(saveData);
        name.save(saveData);
        saveData.writeInt(awesomeness);
        health.save(saveData);
    }

    @Override
    public Health getHealth() {
        return health;
    }

    @Override
    public Position getPosition() {
        return movement.hitbox.position;
    }

    @Override
    public void update(Main main, float dt) throws Exception {
        renderer.update(main, dt, this);
        spellBook.update(main, dt);
        movement.update(main, dt, this);
        controller.update(main, spellBook, movement.hitbox.position, this);
    }

    @Override
    public Faction getFaction() {
        return Faction.Necromancer;
    }
}

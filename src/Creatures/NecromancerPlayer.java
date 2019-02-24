package Creatures;

import Components.*;
import CoreClasses.*;
import Spells.*;

import java.awt.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;

/**
 * Created by Isaac on 24/01/2017.
 */
public class NecromancerPlayer implements Creature {
    private static final float radius = 0.02f;
    private static final float mass = 70.0f;
    private static final float startingSpeed = 0.25f;

    private static final float armorToughness = 0.1f;
    private static final float startingHealthRegen = 0.9f;
    private static final float startingMaxHealth = 100.0f;

    private static final float startingFireResistance = 1.0f;
    private static final float startingColdResistance = 1.0f;
    private static final float startingLightningResistance = 1.0f;
    private static final float startingArcaneResistance = 1.0f;
    private static final float startingBludgeoningResistance = 1.0f;
    private static final float startingPiecingResistance = 1.0f;
    private static final float startingSlashingResistance = 1.0f;


    private Name name;
    private PlayerMovement movement;
    private PlayerHealth health;
    private NecromancerPlayerController controller;
    private Renderer renderer;
    private SpellBook spellBook;
    private int awesomeness;

    public NecromancerPlayer(Main main, String name, float posX, float posY)
            throws java.io.IOException {
        spellBook = new SpellBook(necromancerPlayerSpells());
        this.name = new Name(name);
        this.awesomeness = 0;
        this.movement = new PlayerMovement(new CircleHitbox(this, new Position(posX, posY), radius, mass), startingSpeed);
        this.health = new PlayerHealth(new Resistances(startingFireResistance,
                startingColdResistance, startingLightningResistance, startingArcaneResistance,
                startingBludgeoningResistance, startingPiecingResistance, startingSlashingResistance),
                armorToughness, startingMaxHealth, startingMaxHealth, startingHealthRegen);
        controller = new NecromancerPlayerController();
        renderer = new Renderer(main.sprites.necromancer, 0.4f);
        main.gameObjects.add(this);
    }

    public NecromancerPlayer(Main main, DataInputStream saveData) throws java.io.IOException{
        spellBook = new SpellBook(saveData);
        awesomeness = saveData.readInt();
        name = new Name(saveData);
        movement = new PlayerMovement(new CircleHitbox(this, saveData), saveData);
        health = new PlayerHealth(saveData);
        controller = new NecromancerPlayerController();
        renderer = new Renderer(main.sprites.necromancer, 0.4f);
    }

    protected static Spell[] necromancerPlayerSpells(){
        Spell[] spells = new Spell[4];
        spells[0] = new RaiseSkeleton(5.0f);
        spells[1] = new RandomTeleport(50.0f, new PlayerSetPosition());
        spells[2] = new SummonSkeletonHorde(75.0f, 4,
                4, 5,3);
        spells[3] = new RaiseSkeletons(20.0f, 5, 8);
        return spells;
    }

    @Override
    public void save(Main main, DataOutputStream saveData) throws java.io.IOException{
        saveData.writeInt(SaveType.NecromancerPlayer);
        spellBook.save(saveData);
        saveData.writeInt(awesomeness);
        name.save(saveData);
        movement.hitbox.save(saveData);
        movement.save(saveData);
        health.save(saveData);
    }

    @Override
    public void update(Main main, float dt) throws Exception {
        spellBook.update(main, dt);
        renderer.update(main, dt, this);
        movement.update(main, dt);
        controller.update(main, spellBook, movement.hitbox.position, this);
    }

    @Override
    public String getName(){
        return name.name;
    }

    @Override
    public void gainAwesomeness(Main main, int amount){
        this.awesomeness += amount;
    }

    @Override
    public int getAwesomeness(){
        return this.awesomeness;
    }

    @Override
    public void gainXp(float amount){}

    @Override
    public Faction getFaction(){
        return Faction.Necromancer;
    }

    @Override
    public int getType() {
        return SaveType.NecromancerPlayer;
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
    public Position getPosition() {
        return movement.hitbox.position;
    }
}

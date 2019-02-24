package Creatures;

import Components.*;
import CoreClasses.*;
import Spells.*;

import java.awt.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Created by Isaac on 24/01/2017.
 */
public class FireMagePlayer implements Creature {
    private static final float startingMaxHealth = 100.0f;
    private static final float startingSpeed = 0.25f;
    private static final float startingFireBoltSpellDamage = 10.0f;
    private static final float radius = 0.02f;
    private static final float mass = 70.0f;

    private final static float armorToughness = 0.1f;
    private static final float startingHealthRegen = 0.9f;
    private static final float startingResistance = 0.2f;


    private Name name;
    private PlayerMovement movement;
    private PlayerHealth health;
    private FireMagePlayerController controller;
    private Renderer renderer;
    private SpellBook spellBook;
    private int awesomeness;

    public FireMagePlayer(Main main, String name, float posX, float posY) throws java.io.IOException{
        this.spellBook = new SpellBook(new Spell[]{
                new FireBoltSpell(0.4f, 0.5f, 2.0f, startingFireBoltSpellDamage)});
        this.name = new Name(name);
        this.movement = new PlayerMovement(new TriggerHitBox(this, new PlayerSetPosition(),
                new Position(posX, posY), radius, mass), startingSpeed);
        this.health = new PlayerHealth(new Resistances(startingResistance, startingResistance,
                startingResistance, startingResistance, startingResistance,
                startingResistance, startingResistance), armorToughness, startingMaxHealth,
                startingMaxHealth, startingHealthRegen);
        awesomeness = 0;
        controller = new FireMagePlayerController();
        renderer = new Renderer(main.sprites.fireMage, 0.4f);
        main.gameObjects.add(this);
    }

    public FireMagePlayer(Main main, final DataInputStream saveData) throws java.io.IOException{
        spellBook = new SpellBook(saveData);
        name = new Name(saveData);
        movement = new PlayerMovement(new TriggerHitBox(this, saveData), saveData);
        health = new PlayerHealth(saveData);
        awesomeness = saveData.readInt();
        controller = new FireMagePlayerController();
        renderer = new Renderer(main.sprites.fireMage, 0.4f);
    }

    @Override
    public void update(Main main, float frameTime) throws Exception{
        renderer.update(main, frameTime, this);
        spellBook.update(main, frameTime);
        movement.update(main, frameTime);
        health.update(frameTime);
        controller.update(main, spellBook, movement.hitbox.position, this);
    }

    @Override
    public String getName(){
        return name.name;
    }

    @Override
    public void gainXp(float amount){}

    @Override
    public Faction getFaction(){
        return Faction.Mage;
    }

    @Override
    public int getType() {
        return SaveType.FireMagePlayer;
    }

    @Override
    public void save(Main main, DataOutputStream saveData) throws IOException {
        saveData.writeInt(SaveType.FireMagePlayer);
        spellBook.save(saveData);
        name.save(saveData);
        movement.hitbox.save(saveData);
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
    public void gainAwesomeness(Main main, int amount){
        this.awesomeness += amount;
    }

    @Override
    public int getAwesomeness(){
        return this.awesomeness;
    }

    @Override
    public Position getPosition() {
        return movement.hitbox.position;
    }
}

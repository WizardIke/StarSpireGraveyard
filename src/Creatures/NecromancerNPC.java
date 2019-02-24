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
public class NecromancerNPC implements Creature {
    private final static double moveReactionTime = 0.6f;
    private static final float startingMaxHealth = 100.0f;
    private static final float startingSpeed = 0.2f;
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

    private float xp, xpToNextLevel;
    private int level;
    private double moveReactionCooldown;
    private float directionX, directionY;
    private boolean tooClose;
    private Movement movement;
    private NPCHealth health;
    private Renderer renderer;
    private SpellBook spellBook;


    public NecromancerNPC(Main main, float posX, float posY) throws java.io.IOException {
        spellBook = new SpellBook(createSpells());
        this.xp = 0.f;
        this.xpToNextLevel = 50.f;
        this.level = 1;
        this.moveReactionCooldown = 0.0;
        this.tooClose = false;
        this.movement = new Movement(new CircleHitbox(this, new Position(posX, posY), radius, mass), startingSpeed);
        this.health = new NPCHealth(new Resistances(startingFireResistance,
                startingColdResistance, startingLightningResistance,
                startingArcaneResistance, startingBludgeoningResistance,
                startingPiecingResistance, startingSlashingResistance),
                armorToughness, startingMaxHealth, startingMaxHealth, startingHealthRegen);
        renderer = new Renderer(main.sprites.necromancer, 0.4f);
        main.gameObjects.add(this);
    }

    public NecromancerNPC(Main main, DataInputStream saveData) throws java.io.IOException{
        spellBook = new SpellBook(saveData);
        xp = saveData.readFloat();
        xpToNextLevel = saveData.readFloat();
        level = saveData.readInt();
        moveReactionCooldown = saveData.readDouble();
        tooClose = saveData.readBoolean();
        movement = new Movement(this, saveData);
        health = new NPCHealth(saveData);
        renderer = new Renderer(main.sprites.necromancer, 0.4f);
    }

    private static Spell[] createSpells() {
        Spell[] spells = new Spell[4];
        spells[0] = new RaiseSkeleton(5.0f);
        spells[1] = new RandomTeleport(50.0f, new DoNothing());
        spells[2] = new SummonSkeletonHorde(75.0f, 4,
                4, 5,3);
        spells[3] = new RaiseSkeletons(20.0f, 5, 8);
        return spells;
    }

    @Override
    public void save(Main main, DataOutputStream saveData) throws java.io.IOException{
        saveData.writeInt(SaveType.NecromancerNPC);
        spellBook.save(saveData);
        saveData.writeFloat(xp);
        saveData.writeFloat(xpToNextLevel);
        saveData.writeInt(level);
        saveData.writeDouble(moveReactionCooldown);
        saveData.writeBoolean(tooClose);
        movement.save(saveData);
        health.save(saveData);
    }

    private void handleAI(Main main, double dt){
        final AI.Target target = AI.findTarget(this, main.gameObjects);
        if(target != null) {
            float directionX = target.dispacementX / target.distance;
            float directionY = target.dispacementY / target.distance;

            if(spellBook.spells[0].isCastable(this)) {
                if(target.distance <= spellBook.spells[0].getCastRange()) {
                    spellBook.spells[0].cast(main, this, 0.0f, 0.0f,
                            movement.hitbox.position.x + target.dispacementX,
                            movement.hitbox.position.y + target.dispacementY);
                } else {
                    spellBook.spells[0].cast(main, this, 0.0f, 0.0f,
                            directionX * spellBook.spells[0].getCastRange() + movement.hitbox.position.x,
                            directionY * spellBook.spells[0].getCastRange() + movement.hitbox.position.y);
                }
            }

            if(!tooClose && target.distance < 0.5) {
                tooClose = true;
            }
            else if(tooClose && target.distance > 0.6) {
                tooClose = false;
                moveReactionCooldown = moveReactionTime;
            }
            else if(tooClose) {
                if(moveReactionCooldown > 0.0) {
                    moveReactionCooldown -= dt;
                }
                else {
                    //move away from enemy
                    movement.hitbox.position.x -= movement.speed * directionX * dt;
                    movement.hitbox.position.y -= movement.speed * directionY * dt;
                }
            }

            if(spellBook.spells[1].isCastable(this) && target.distance < 0.5) {
                spellBook.spells[1].cast(main, this);
            }
            if(spellBook.spells[2].isCastable(this)) {
                spellBook.spells[2].cast(main, this);
            }
            if(spellBook.spells[3].isCastable(this)) {
                if(target.distance <= spellBook.spells[3].getCastRange()) {
                    final Position targetPosition = target.creature.getPosition();
                    spellBook.spells[3].cast(main, this, 0.0f, 0.0f,
                            targetPosition.x, targetPosition.y);
                } else {
                    spellBook.spells[3].cast(main, this, 0.0f, 0.0f,
                            directionX * spellBook.spells[3].getCastRange() + movement.hitbox.position.x,
                            directionY * spellBook.spells[3].getCastRange() + movement.hitbox.position.x);
                }
            }
        }
    }

    @Override
    public void update(Main main, float frameTime) throws Exception{
        spellBook.update(main, frameTime);
        renderer.update(main, frameTime, this);
        handleAI(main, frameTime);
    }

    private void levelUp(){
        ++level;
        health.maxHealth *= 1.035264924f;
        health.health *= 1.035264924f;
        spellBook.spells[0].setCastTime(spellBook.spells[0].getCastTime() * 0.95f);
        spellBook.spells[2].setCastTime(spellBook.spells[2].getCastTime() * 0.95f);
        spellBook.spells[3].setCastTime(spellBook.spells[3].getCastTime() * 0.95f);
        xp -= xpToNextLevel;
        xpToNextLevel *= 1.05f;
        //switch(level){
        //  case 2 :
        //    break;
        //}
    }

    @Override
    public void gainXp(float amount){
        xp += amount;
        if(xp >= xpToNextLevel){
            levelUp();
        }
    }

    @Override
    public void gainAwesomeness(Main main, int amount){}

    @Override
    public Faction getFaction(){
        return Faction.Necromancer;
    }

    @Override
    public int getType() {
        return SaveType.NecromancerNPC;
    }

    @Override
    public HitBox getHitbox()
    {
        return movement.hitbox;
    }

    @Override
    public void render(Main main, Graphics2D graphics) {
        final CircleHitbox hitbox = movement.hitbox;
        renderer.render(main, graphics, hitbox.position, hitbox.radius, directionX, directionY, true);
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

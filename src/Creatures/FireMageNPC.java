package Creatures;

import Components.*;
import CoreClasses.*;
import Spells.FireBoltSpell;
import Spells.Spell;

import java.awt.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;

/**
 * Created by Isaac on 24/01/2017.
 */
public class FireMageNPC implements Creature {
    protected static final float startingSpeed = 0.2f;
    private static final float startingMaxHealth = 100f;
    protected static final float radius = 0.02f;
    protected static final float mass = 70.0f;

    protected final static float armorToughness = 0.1f;
    protected static final float startingHealthRegen = 0.9f;
    protected static final float startingResistance = 0.2f;

    protected float xp, xpToNextLevel;
    protected int level;
    protected Movement movement;
    protected NPCHealth health;
    protected Renderer renderer;
    protected SpellBook spellBook;
    private float directionX, directionY;

    public FireMageNPC(Main main, float posX, float posY) throws java.io.IOException {
        this.spellBook = new SpellBook(new Spell[]{new FireBoltSpell(0.4f, 0.5f, 2.0f, 10.0f)});
        this.xp = 0.0f;
        this.xpToNextLevel = 50.f;
        this.movement = new Movement(new CircleHitbox(this, new Position(posX, posY), radius, mass), startingSpeed);
        health = new NPCHealth(new Resistances(startingResistance, startingResistance,
                startingResistance, startingResistance, startingResistance,
                startingResistance, startingResistance), armorToughness, startingMaxHealth, startingMaxHealth,
                startingHealthRegen);
        renderer = new Renderer(main.sprites.fireMage, 0.4f);
        main.gameObjects.add(this);
    }

    public FireMageNPC(Main main, DataInputStream saveData) throws java.io.IOException{
        spellBook = new SpellBook(saveData);
        this.xp = saveData.readFloat();
        this.xpToNextLevel = saveData.readFloat();
        this.level = saveData.readInt();
        this.movement = new Movement(this, saveData);
        health = new NPCHealth(saveData);
        renderer = new Renderer(main.sprites.fireMage, 0.4f);
    }

    @Override
    public void update(Main main, float frameTime) throws Exception{
        renderer.update(main, frameTime, this);
        spellBook.update(main, frameTime);
        handleAI(main, frameTime);
    }

    private void handleAI(Main main, float frameTime){
        //handle finding player, shooting and dodging enemies
        AI.Target target = AI.findTarget(this, main.gameObjects);
        if(target != null) {
            directionX = target.dispacementX / target.distance;
            directionY = target.dispacementY / target.distance;
            if(spellBook.spells[0].isCastable(this)) {
                spellBook.spells[0].cast(main, this, movement.hitbox.position.x, movement.hitbox.position.y,
                        movement.hitbox.position.x + target.dispacementX,
                        movement.hitbox.position.y + target.dispacementY);
            }

            if(target.distance <= spellBook.spells[0].getCastRange() * 0.5){
                float distanceTraveled = movement.speed * frameTime;
                movement.hitbox.position.x -= directionX * distanceTraveled;
                movement.hitbox.position.y -= directionY * distanceTraveled;
            }
            else if(target.distance > spellBook.spells[0].getCastRange()){
                float distanceTraveled = movement.speed * frameTime;
                movement.hitbox.position.x += directionX * distanceTraveled;
                movement.hitbox.position.y += directionY * distanceTraveled;
            }
        }
    }

    private void levelUp(){
        ++level;
        health.maxHealth *= 1.035264924;
        health.health *= 1.035264924;
        spellBook.spells[0].setCastTime(spellBook.spells[0].getCastTime() * 0.95f);
        xp -= xpToNextLevel;
        xpToNextLevel *= 1.05f;
    }

    @Override
    public void gainXp(float amount){
        xp += amount;
        if(xp >= xpToNextLevel){
            levelUp();
        }
    }

    @Override
    public Faction getFaction(){
        return Faction.Mage;
    }

    @Override
    public void save(Main main, DataOutputStream saveData) throws java.io.IOException {
        saveData.writeInt(SaveType.FireMageNPC);
        spellBook.save(saveData);
        saveData.writeFloat(xp);
        saveData.writeFloat(xpToNextLevel);
        saveData.writeInt(level);
        movement.save(saveData);
        health.save(saveData);
    }

    @Override
    public int getType() {
        return SaveType.FireMageNPC;
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

package Components;

import CoreClasses.Main;
import Spells.*;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Created by Isaac on 31/08/2017.
 */
public class SpellBook {
    public Spell[] spells;
    public SpellBook(Spell[] spells) {
        this.spells = spells;
    }
    public SpellBook(final DataInputStream saveData) throws IOException {
        int numSpells = saveData.readInt();
        spells = new Spell[numSpells];
        for(int i = 0; i < numSpells; ++i) {
            spells[i] = loadSpell(saveData);
        }
    }

    public static Spell loadSpell(DataInputStream saveData) throws IOException {
        int debug = saveData.readInt();
        switch(debug) {
            case TalentTypes.FireBoltSpell:
                return new FireBoltSpell(saveData);
            case TalentTypes.FireBoltSpellClient:
                return new FireBoltSpellClient(saveData);
            case TalentTypes.FireBoltSpellHost:
                return new FireBoltSpellHost(saveData);
            case TalentTypes.RaiseSkeleton:
                return new RaiseSkeleton(saveData);
            case TalentTypes.RaiseSkeletonHost:
                return new RaiseSkeletonHost(saveData);
            case TalentTypes.RaiseSkeletonClient:
                return new RaiseSkeletonClient(saveData);
            case TalentTypes.RaiseSkeletons:
                return new RaiseSkeletons(saveData);
            case TalentTypes.RaiseSkeletonsHost:
                return new RaiseSkeletonHost(saveData);
            case TalentTypes.RaiseSkeletonsClient:
                return new RaiseSkeletonsClient(saveData);
            case TalentTypes.RandomTeleport:
                return new RandomTeleport(saveData);
            case TalentTypes.SummonSkeletonHorde:
                return new SummonSkeletonHorde(saveData);
            case TalentTypes.SummonSkeletonHordeHost:
                return new SummonSkeletonHorde(saveData);
            case TalentTypes.SummonSkeletonHordeClient:
                return new SummonSkeletonHordeClient(saveData);
            case TalentTypes.PlayerSetPosition:
                return new PlayerSetPosition();
            case TalentTypes.PlayerSetPositionHost:
                return new PlayerSetPositionHost();
            case TalentTypes.DoNothing:
                return new DoNothing();
            case TalentTypes.Explode:
                return new Explode(saveData);
            case TalentTypes.Bite:
                return new Bite(saveData);
            case TalentTypes.BiteClient:
                return new BiteClient(saveData);
            case TalentTypes.ExplodeHost:
                return new ExplodeHost(saveData);
        }
        return null;
    }

    public void update(final Main main, final float frameTime) {
        for(Spell spell : spells) {
            spell.update(main, frameTime);
        }
    }

    public void save(final DataOutputStream saveData) throws IOException {
        saveData.writeInt(spells.length);
        for(Spell spell : spells) {
            spell.save(saveData);
        }
    }
}

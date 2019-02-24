package Spells;

import java.io.DataOutputStream;
import java.io.IOException;

public class DoNothing implements Spell {
    @Override
    public void save(DataOutputStream saveData) throws IOException {
        saveData.writeInt(TalentTypes.DoNothing);
    }
}

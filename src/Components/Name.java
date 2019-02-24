package Components;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Created by Isaac on 20/10/2017.
 */
public class Name {
    public String name;

    public Name(String name) {
        this.name = name;
    }

    public Name(DataInputStream saveData) throws IOException {
        int length = saveData.readInt();
        char[] name = new char[length];
        for(int i = 0; i < length; ++i){
            name[i] = saveData.readChar();
        }
        this.name = new String(name);
    }

    public void save(DataOutputStream saveData) throws IOException {
        saveData.writeInt(name.length());
        saveData.writeChars(name);
    }
}

package CoreClasses;

import java.io.Closeable;
import java.io.DataOutputStream;

/**
 * Created by Isaac on 29/01/2017.
 */
public interface NetworkConnection extends Closeable{
    void update(Main main, double frameTime) throws Exception;
    DataOutputStream getNetworkOut();
}

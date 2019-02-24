package CoreClasses;

import java.io.Closeable;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Created by Isaac on 29/01/2017.
 */
public class ClientConnection implements NetworkConnection, Closeable {
    private Socket clientSocket;
    private DataInputStream networkIn;
    private DataOutputStream networkOut;
    private int nextMessageLength = 4;
    private boolean hasMessageLength = false;

    public ClientConnection(Socket clientSocket) throws java.io.IOException{
        this.clientSocket = clientSocket;
        this.networkIn = new DataInputStream(clientSocket.getInputStream());
        this.networkOut = new DataOutputStream(clientSocket.getOutputStream());
    }

    @Override
    public DataOutputStream getNetworkOut(){
        return this.networkOut;
    }

    @Override
    public void update(Main main, double dt){
        try {
            while(networkIn.available() >= nextMessageLength){
                if(hasMessageLength){
                    int message = networkIn.readByte();
                    switch(message){
                        case NetworkMessageTypes.gameObject: {
                            final int index = networkIn.readInt();
                            main.gameObjects.get(index).handleMessage(main, networkIn);
                            break;
                        }
                        default :
                            main.connectionLost();
                            return;
                    }
                    nextMessageLength = 4;
                    hasMessageLength = false;
                }
                else{
                    nextMessageLength = networkIn.readInt();
                    hasMessageLength = true;
                }

            }
        } catch (Exception e) {
            main.connectionLost();
        }
    }

    @Override
    public void close(){
        try {
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

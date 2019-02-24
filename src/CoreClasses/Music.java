package CoreClasses;

import javax.sound.sampled.*;
import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Plays ambient music in a loop until <code>close</code> is called
 * Created by Isaac on 19/01/2017.
 */
public class Music extends Thread implements Closeable{
    private SourceDataLine currentMusicLine;
    private File directory;
    private boolean interrupted = false;

    public Music(File directory){
        super("Music thread");
        super.setDaemon(true);
        this.directory = directory;
        final AudioFormat audioFormat = new AudioFormat(44100, 16, 2, true, false);
        final DataLine.Info info = new DataLine.Info(SourceDataLine.class, audioFormat);
        try {
            currentMusicLine = (SourceDataLine) AudioSystem.getLine(info);
            currentMusicLine.open(audioFormat);
            currentMusicLine.start();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    private void playAudioFile(File file){
        try (final AudioInputStream audio = AudioSystem.getAudioInputStream(file)){
            byte[] audioBuffer = new byte[8820]; //50ms of data so SourceDataLine.write doesn't block to long
            int bytesRead = 0;
            while(bytesRead != -1 && !this.isInterrupted()){
                bytesRead = audio.read(audioBuffer);
                currentMusicLine.write(audioBuffer,0, audioBuffer.length);  //SourceDataLine.write eats interrupts
            }
            if(bytesRead != -1){
                //thread was interrupted stop playing music
                currentMusicLine.stop();
                currentMusicLine.close();
            }
        } catch (UnsupportedAudioFileException | IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run(){
        final ThreadLocalRandom randomNumGen = ThreadLocalRandom.current();
        File[] files = directory.listFiles();
        if(files == null || files.length == 0) {
            System.out.println("ERROR: Failed to start music");
            return;
        }
        int song = randomNumGen.nextInt(files.length);
        int previousSong = song;
        while(!isInterrupted()){
            playAudioFile(files[song]);
            song = randomNumGen.nextInt(files.length - 1);
            if(song >= previousSong){
                ++song;
            }
            previousSong = song;
        }
    }

    @Override
    public synchronized void interrupt(){ //bc SourceDataLine.write breaks interrupt
        this.interrupted = true;
    }

    @Override
    public synchronized boolean isInterrupted(){//bc SourceDataLine.write breaks isInterrupted
        return this.interrupted;
    }

    @Override
    public synchronized void close(){
        this.interrupted = true;
    }
}

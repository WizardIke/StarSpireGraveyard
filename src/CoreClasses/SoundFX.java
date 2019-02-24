package CoreClasses;

import javax.sound.sampled.*;
import java.io.Closeable;
import java.io.File;
import java.nio.BufferOverflowException;

/**
 * Created by Isaac on 22/01/2017.
 */
public class SoundFX implements Closeable{
        public final AudioClip FireboltCastSound;
        public final AudioClip explosion;
        public final AudioClip bite;
        public final AudioClip beep;


    public static class PlayerWalkingOnDirt implements Closeable{
        private Clip step1, step2;
        private Clip currentStep;
        private boolean stop = false;
        public PlayerWalkingOnDirt(){
            try (AudioInputStream playerWalkingStream = AudioSystem.getAudioInputStream(
                    new File("data/sound fx/Footstep04.wav"))){
                step1 = AudioSystem.getClip();
                step1.open(playerWalkingStream);
                step1.addLineListener((LineEvent event) -> {
                    if(event.getType() == LineEvent.Type.STOP){
                        currentStep = step2;
                        step2.setFramePosition(0);
                        if(!stop){
                            step2.start();
                        }
                    }
                });
            } catch (LineUnavailableException | UnsupportedAudioFileException | java.io.IOException e) {
                e.printStackTrace();
            }
            try (AudioInputStream playerWalkingStream = AudioSystem.getAudioInputStream(
                    new File("data/sound fx/Footstep_Dirt_03_converted.wav"))){
                step2 = AudioSystem.getClip();
                step2.open(playerWalkingStream);
                step2.addLineListener((LineEvent event) -> {
                    if(event.getType() == LineEvent.Type.STOP){
                        currentStep = step1;
                        step1.setFramePosition(0);
                        if(!stop){
                            step1.start();
                        }
                    }
                });
            } catch (LineUnavailableException | UnsupportedAudioFileException | java.io.IOException e) {
                e.printStackTrace();
            }
            this.currentStep = step1;
        }

        public void play(){
            stop = false;
            currentStep.start();
        }

        public void stop(){
            stop = true;
        }

        @Override
        public void close(){
            step1.close();
            step2.close();
        }
    }
    public final PlayerWalkingOnDirt playerWalkingOnDirt = new PlayerWalkingOnDirt();

    public SoundFX()throws Exception{
        FireboltCastSound = new AudioClip(new File("data/sound fx/Waving_Torch_converted (1).wav"));
        explosion = new AudioClip(new File("data/sound fx/Memo.wav"));
        bite = new AudioClip(new File("data/sound fx/bite.wav"));
        beep = new AudioClip(new File("data/sound fx/beep.wav"));
    }

    @Override
    public void close(){
        playerWalkingOnDirt.close();
    }

    /**
     * Class used to store audio data
     */
    public static class AudioClip {
        private AudioFormat mFormat;
        private byte[] mData;
        private int mLength;

        public Clip getNewClip() throws javax.sound.sampled.LineUnavailableException {
            Clip clip = AudioSystem.getClip();
            clip.open(mFormat, mData, 0, mLength);
            return clip;
        }

        public AudioFormat getAudioFormat() {
            return mFormat;
        }

        public byte[] getData() {
            return mData;
        }

        public int getBufferSize() {
            return mLength;
        }

        public AudioClip(File file)throws java.io.IOException, javax.sound.sampled.UnsupportedAudioFileException {
            try (AudioInputStream audio = AudioSystem.getAudioInputStream(file)){
                init(audio);
            }
        }

        public AudioClip(AudioInputStream stream)throws java.io.IOException {
            init(stream);
        }

        private void init(AudioInputStream stream)throws java.io.IOException {
            mFormat = stream.getFormat();
            long length = stream.getFrameLength() * mFormat.getFrameSize();

            if(length > Integer.MAX_VALUE){
                throw new BufferOverflowException();
            }
            mLength = (int)length;
            mData = new byte[mLength];

            // Read data
            int bytesRead = stream.read(mData);
            if(bytesRead != mLength){
                throw new java.io.IOException();
            }
        }

        public void play() {
            try {
                // Create a Clip
                Clip clip = AudioSystem.getClip();

                // Load data
                clip.open(mFormat, mData, 0, mLength);

                //Ike added this
                clip.addLineListener((LineEvent event) -> {
                    if(event.getType() == LineEvent.Type.STOP){
                        event.getLine().close();
                    }
                });

                // Play Clip
                clip.start();
            } catch(LineUnavailableException e){
                e.printStackTrace();
            }
        }

        public void play(float volume) {
            try {
                Clip clip = AudioSystem.getClip();
                clip.open(mFormat, mData, 0, mLength);

                FloatControl control = (FloatControl)clip.getControl(FloatControl.Type.MASTER_GAIN);
                control.setValue(volume);

                clip.addLineListener((LineEvent event) -> {
                    if(event.getType() == LineEvent.Type.STOP){
                        event.getLine().close();
                    }
                });

                // Play Clip
                clip.start();
            } catch(LineUnavailableException e){
                e.printStackTrace();
            }
        }
    }
}

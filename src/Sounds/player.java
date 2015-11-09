package Sounds;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Player implements LineListener {
    
    /*
     * Play music
     * only wav format
     */
     
    /**
     * this flag indicates whether the playback completes or not.
     */
    boolean playCompleted = false;
     
    /**
     * Play a given audio file.
     * @param audioFilePath Path of the audio file.
     */
    public void playDie() {
        File audioFile = new File("./data/Sounds/Die.wav");
        int audioLength;
        try {
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
 
            AudioFormat format = audioStream.getFormat();
 
            DataLine.Info info = new DataLine.Info(Clip.class, format);
 
            Clip audioClip = (Clip) AudioSystem.getLine(info);
 
            audioClip.addLineListener(this);
 
            audioClip.open(audioStream);
            audioLength = (int) (audioClip.getMicrosecondLength()/1650);
            
            audioClip.setFramePosition(0);
            audioClip.start();
             
           while (!playCompleted) {
                // wait for the playback completes
                try {
                    System.out.println(audioLength);
                    //en millisecondes
                    Thread.sleep(audioLength);
                    audioClip.stop();
                   // audioClip.close();
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
            //audioClip.flush();
           // audioClip.drain();
            //audioClip.close();
           audioClip.stop();
             
        } catch (UnsupportedAudioFileException ex) {
            System.out.println("The specified audio file is not supported.");
            ex.printStackTrace();
        } catch (LineUnavailableException ex) {
            System.out.println("Audio line for playing back is unavailable.");
            ex.printStackTrace();
        } catch (IOException ex) {
            System.out.println("Error playing the audio file.");
            ex.printStackTrace();
        }
        
        
    }
    
    public void playGoodAnswer() {
        File audioFile = new File("./data/Sounds/GoodAnswer.wav");
        int audioLength;
        try {
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
 
            AudioFormat format = audioStream.getFormat();
 
            DataLine.Info info = new DataLine.Info(Clip.class, format);
 
            Clip audioClip = (Clip) AudioSystem.getLine(info);
 
            audioClip.addLineListener(this);
 
            audioClip.open(audioStream);
            audioLength = (int) (audioClip.getMicrosecondLength()/1650);
             
            audioClip.setFramePosition(0);
            audioClip.start();
             
           while (!playCompleted) {
                // wait for the playback completes
                try {
                    System.out.println(audioLength);
                    //en millisecondes
                    Thread.sleep(audioLength);
                    audioClip.stop();
                   // audioClip.close();
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
            //audioClip.flush();
           // audioClip.drain();
            //audioClip.close();
           audioClip.stop();
            
             
        } catch (UnsupportedAudioFileException ex) {
            System.out.println("The specified audio file is not supported.");
            ex.printStackTrace();
        } catch (LineUnavailableException ex) {
            System.out.println("Audio line for playing back is unavailable.");
            ex.printStackTrace();
        } catch (IOException ex) {
            System.out.println("Error playing the audio file.");
            ex.printStackTrace();
        }
        
        
    }
    
    public void playBadAnswer() {
        File audioFile = new File("./data/Sounds/BadAnswer.wav");
        int audioLength;
        try {
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
 
            AudioFormat format = audioStream.getFormat();
 
            DataLine.Info info = new DataLine.Info(Clip.class, format);
 
            Clip audioClip = (Clip) AudioSystem.getLine(info);
 
            audioClip.addLineListener(this);
 
            audioClip.open(audioStream);
            audioLength = (int) (audioClip.getMicrosecondLength()/1650);
             
            audioClip.setFramePosition(0);
            audioClip.start();
             
           while (!playCompleted) {
                // wait for the playback completes
                try {
                    System.out.println(audioLength);
                    //en millisecondes
                    Thread.sleep(audioLength);
                    audioClip.stop();
                   // audioClip.close();
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
            //audioClip.flush();
           // audioClip.drain();
            //audioClip.close();
           audioClip.stop();
            
             
        } catch (UnsupportedAudioFileException ex) {
            System.out.println("The specified audio file is not supported.");
            ex.printStackTrace();
        } catch (LineUnavailableException ex) {
            System.out.println("Audio line for playing back is unavailable.");
            ex.printStackTrace();
        } catch (IOException ex) {
            System.out.println("Error playing the audio file.");
            ex.printStackTrace();
        }
        
        
    }
    
    public void playTwitter() {
        File audioFile = new File("./data/Sounds/Twitter.wav");
        int audioLength;
        try {
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
 
            AudioFormat format = audioStream.getFormat();
 
            DataLine.Info info = new DataLine.Info(Clip.class, format);
 
            Clip audioClip = (Clip) AudioSystem.getLine(info);
 
            audioClip.addLineListener(this);
 
            audioClip.open(audioStream);
            audioLength = (int) (audioClip.getMicrosecondLength()/1650);
             
            audioClip.setFramePosition(0);
            audioClip.start();
             
           while (!playCompleted) {
                // wait for the playback completes
                try {
                    System.out.println(audioLength);
                    //en millisecondes
                    Thread.sleep(audioLength);
                    audioClip.stop();
                   // audioClip.close();
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
            //audioClip.flush();
           // audioClip.drain();
            //audioClip.close();
           audioClip.stop();
            
             
        } catch (UnsupportedAudioFileException ex) {
            System.out.println("The specified audio file is not supported.");
            ex.printStackTrace();
        } catch (LineUnavailableException ex) {
            System.out.println("Audio line for playing back is unavailable.");
            ex.printStackTrace();
        } catch (IOException ex) {
            System.out.println("Error playing the audio file.");
            ex.printStackTrace();
        }
        
        
    }
    
    public void playOhYeah() {
        File audioFile = new File("./data/Sounds/OhYeah.wav");
        int audioLength;
        try {
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
 
            AudioFormat format = audioStream.getFormat();
 
            DataLine.Info info = new DataLine.Info(Clip.class, format);
 
            Clip audioClip = (Clip) AudioSystem.getLine(info);
 
            audioClip.addLineListener(this);
 
            audioClip.open(audioStream);
            audioLength = (int) (audioClip.getMicrosecondLength()/1650);
             
            audioClip.setFramePosition(0);
            audioClip.start();
             
           while (!playCompleted) {
                // wait for the playback completes
                try {
                    System.out.println(audioLength);
                    //en millisecondes
                    Thread.sleep(audioLength);
                    audioClip.stop();
                   // audioClip.close();
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
            //audioClip.flush();
           // audioClip.drain();
            //audioClip.close();
           audioClip.stop();
            
             
        } catch (UnsupportedAudioFileException ex) {
            System.out.println("The specified audio file is not supported.");
            ex.printStackTrace();
        } catch (LineUnavailableException ex) {
            System.out.println("Audio line for playing back is unavailable.");
            ex.printStackTrace();
        } catch (IOException ex) {
            System.out.println("Error playing the audio file.");
            ex.printStackTrace();
        }
        
        
    }
     
    /**
     * Listens to the START and STOP events of the audio line.
     */
    @Override
    public void update(LineEvent event) {
        LineEvent.Type type = event.getType();
         
        if (type == LineEvent.Type.START) {
            playCompleted = false;
            System.out.println("Playback started.");
             
        } else if (type == LineEvent.Type.STOP) {
            playCompleted = true;
            System.out.println("Playback completed.");
        }
 
    }
 
//    public static void main(String[] args) {
//        String audioFilePath = "data/Sounds/GoodAnswer.wav";
//        Player player = new Player();
//        player.play(audioFilePath);
//    }
    
}
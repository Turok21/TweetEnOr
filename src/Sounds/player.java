package Sounds;

import java.io.File;
import java.io.IOException;

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
	protected File audioFileDie;
	protected AudioInputStream audioStreamDie;
	protected AudioFormat formatDie;
	protected DataLine.Info infoDie;
	protected Clip audioClipDie;
	int audioLengthDie;
	
	protected File audioFileGoodAnswer;
	protected AudioInputStream audioStreamGoodAnswer;
	protected AudioFormat formatGoodAnswer;
	protected DataLine.Info infoGoodAnswer;
	protected Clip audioClipGoodAnswer;
	int audioLengthGoodAnswer;
	
	protected File audioFileBadAnswer;
	protected AudioInputStream audioStreamBadAnswer;
	protected AudioFormat formatBadAnswer;
	protected DataLine.Info infoBadAnswer;
	protected Clip audioClipBadAnswer;
	int audioLengthBadAnswer;
	
	protected File audioFileTwitter;
	protected AudioInputStream audioStreamTwitter;
	protected AudioFormat formatTwitter;
	protected DataLine.Info infoTwitter;
	protected Clip audioClipTwitter;
	int audioLengthTwitter;
	
	protected File audioFileOhYeah;
	protected AudioInputStream audioStreamOhYeah;
	protected AudioFormat formatOhYeah;
	protected DataLine.Info infoOhYeah;
	protected Clip audioClipOhYeah;
	int audioLengthOhYeah;
	
	boolean playCompleted;
	
	public Player (){
		
		playCompleted = false;
		
		audioFileDie = new File("./data/Sounds/Die.wav");
    	try{
			audioStreamDie = AudioSystem.getAudioInputStream(audioFileDie);
	       formatDie = audioStreamDie.getFormat();
	        infoDie = new DataLine.Info(Clip.class, formatDie);
	        audioClipDie = (Clip) AudioSystem.getLine(infoDie);
	        audioClipDie.addLineListener(this);
	        audioClipDie.open(audioStreamDie);
	        audioLengthDie = (int) (audioClipDie.getMicrosecondLength()/1650);
		} catch (UnsupportedAudioFileException ex) {
	        System.out.println("Die : The specified audio file is not supported.");
	        ex.printStackTrace();
	    } catch (LineUnavailableException ex) {
	        System.out.println("Die : Audio line for playing back is unavailable.");
	        ex.printStackTrace();
	    } catch (IOException ex) {
	        System.out.println("Die : Error playing the audio file.");
	        ex.printStackTrace();
	    }
    	
    	
    	audioFileGoodAnswer = new File("./data/Sounds/GoodAnswer.wav");
    	try{
			audioStreamGoodAnswer = AudioSystem.getAudioInputStream(audioFileGoodAnswer);
	       formatGoodAnswer = audioStreamGoodAnswer.getFormat();
	        infoGoodAnswer = new DataLine.Info(Clip.class, formatGoodAnswer);
	        audioClipGoodAnswer = (Clip) AudioSystem.getLine(infoGoodAnswer);
	        audioClipGoodAnswer.addLineListener(this);
	        audioClipGoodAnswer.open(audioStreamGoodAnswer);
	        audioLengthGoodAnswer = (int) (audioClipDie.getMicrosecondLength()/1650);
		} catch (UnsupportedAudioFileException ex) {
	        System.out.println("GoodAnswer : The specified audio file is not supported.");
	        ex.printStackTrace();
	    } catch (LineUnavailableException ex) {
	        System.out.println("GoodAnswer : Audio line for playing back is unavailable.");
	        ex.printStackTrace();
	    } catch (IOException ex) {
	        System.out.println("GoodAnswer : Error playing the audio file.");
	        ex.printStackTrace();
	    }
    	
    	
    	audioFileBadAnswer = new File("./data/Sounds/BadAnswer.wav");
    	try{
			audioStreamBadAnswer = AudioSystem.getAudioInputStream(audioFileBadAnswer);
	       formatBadAnswer = audioStreamBadAnswer.getFormat();
	        infoBadAnswer = new DataLine.Info(Clip.class, formatBadAnswer);
	        audioClipBadAnswer = (Clip) AudioSystem.getLine(infoBadAnswer);
	        audioClipBadAnswer.addLineListener(this);
	        audioClipBadAnswer.open(audioStreamBadAnswer);
	        audioLengthBadAnswer = (int) (audioClipBadAnswer.getMicrosecondLength()/1650);
		} catch (UnsupportedAudioFileException ex) {
	        System.out.println("BadAnswer : The specified audio file is not supported.");
	        ex.printStackTrace();
	    } catch (LineUnavailableException ex) {
	        System.out.println("BadAnswer : Audio line for playing back is unavailable.");
	        ex.printStackTrace();
	    } catch (IOException ex) {
	        System.out.println("BadAnswer : Error playing the audio file.");
	        ex.printStackTrace();
	    }
    	
    	audioFileTwitter = new File("./data/Sounds/Twitter.wav");
    	try{
			audioStreamTwitter = AudioSystem.getAudioInputStream(audioFileTwitter);
	       formatTwitter = audioStreamTwitter.getFormat();
	        infoTwitter = new DataLine.Info(Clip.class, formatTwitter);
	        audioClipTwitter = (Clip) AudioSystem.getLine(infoTwitter);
	        audioClipTwitter.addLineListener(this);
	        audioClipTwitter.open(audioStreamTwitter);
	        audioLengthTwitter = (int) (audioClipTwitter.getMicrosecondLength()/1650);
		} catch (UnsupportedAudioFileException ex) {
	        System.out.println("Twitter : The specified audio file is not supported.");
	        ex.printStackTrace();
	    } catch (LineUnavailableException ex) {
	        System.out.println("Twitter : Audio line for playing back is unavailable.");
	        ex.printStackTrace();
	    } catch (IOException ex) {
	        System.out.println("Twitter : Error playing the audio file.");
	        ex.printStackTrace();
	    }
    	
    	
    	audioFileOhYeah = new File("./data/Sounds/OhYeah.wav");
    	try{
			audioStreamOhYeah = AudioSystem.getAudioInputStream(audioFileOhYeah);
	       formatOhYeah = audioStreamOhYeah.getFormat();
	        infoOhYeah = new DataLine.Info(Clip.class, formatOhYeah);
	        audioClipOhYeah = (Clip) AudioSystem.getLine(infoOhYeah);
	        audioClipOhYeah.addLineListener(this);
	        audioClipOhYeah.open(audioStreamOhYeah);
	        audioLengthOhYeah = (int) (audioClipOhYeah.getMicrosecondLength()/1650);
		} catch (UnsupportedAudioFileException ex) {
	        System.out.println("OhYeah : The specified audio file is not supported.");
	        ex.printStackTrace();
	    } catch (LineUnavailableException ex) {
	        System.out.println("OhYeah : Audio line for playing back is unavailable.");
	        ex.printStackTrace();
	    } catch (IOException ex) {
	        System.out.println("OhYeah : Error playing the audio file.");
	        ex.printStackTrace();
	    }
    	
    	
	}
	
    /**
     * this flag indicates whether the playback completes or not.
     */
    
     
    /**
     * Play a given audio file.
     * @param audioFilePath Path of the audio file.
     */
    public void playDie() {
        try {
        	playCompleted = false;
            audioClipDie.setFramePosition(0);
            audioClipDie.start();
             
           while (!playCompleted) {
                // wait for the playback completes
                try {
                	System.out.println(audioLengthDie);
                	//en millisecondes
                    Thread.sleep(audioLengthDie);
                    audioClipDie.stop();
                   // audioClipDie.close();
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
           // audioClipDie.flush();
            //audioClipDie.drain();
            //audioClip.close();
           //audioClipDie.stop();
             
        }finally{
           	audioClipDie.stop();
           	audioClipDie.setFramePosition(0);
           	//audioClipDie.close();
           
          }
    }
    
    public void playGoodAnswer() {
        try {
        	playCompleted = false;
            audioClipGoodAnswer.setFramePosition(0);
            audioClipGoodAnswer.start();
             
           while (!playCompleted) {
                // wait for the playback completes
                try {
                	System.out.println(audioLengthGoodAnswer);
                	//en millisecondes
                    Thread.sleep(audioLengthGoodAnswer);
                    audioClipGoodAnswer.stop();
                   // audioClip.close();
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
            //audioClip.flush();
           // audioClip.drain();
            //audioClip.close();
        }finally{
        	audioClipGoodAnswer.stop();
        	audioClipGoodAnswer.setFramePosition(0);
          }
        
        
    }
    
    public void playBadAnswer() {
 
        try {
        	playCompleted = false;
            audioClipBadAnswer.setFramePosition(0);
            audioClipBadAnswer.start();
             
           while (!playCompleted) {
                // wait for the playback completes
                try {
                	System.out.println(audioLengthBadAnswer);
                	//en millisecondes
                    Thread.sleep(audioLengthBadAnswer);
                    audioClipBadAnswer.stop();
                   // audioClip.close();
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        }finally{
        	audioClipBadAnswer.stop();
        	audioClipBadAnswer.setFramePosition(0);
          }
        
        
    }
    
    public void playTwitter() {
    	
        try {
            
        	playCompleted = false;
            audioClipTwitter.setFramePosition(0);
            audioClipTwitter.start();
             
           while (!playCompleted) {
                // wait for the playback completes
                try {
                	System.out.println(audioLengthTwitter);
                	//en millisecondes
                    Thread.sleep(audioLengthTwitter);
                    audioClipTwitter.stop();
                   // audioClip.close();
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }}finally{
            	audioClipTwitter.stop();
            	audioClipTwitter.setFramePosition(0);
              }
        
        
    }
    
    public void playOhYeah() {
        try {
        	playCompleted = false;
            audioClipOhYeah.setFramePosition(0);
            audioClipOhYeah.start();
             
           while (!playCompleted) {
                // wait for the playback completes
                try {
                	System.out.println(audioLengthOhYeah);
                	//en millisecondes
                    Thread.sleep(audioLengthOhYeah);
                    audioClipOhYeah.stop();
                   // audioClip.close();
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        }finally{
        	audioClipOhYeah.stop();
        	audioClipOhYeah.setFramePosition(0);
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
//    	String audioFilePath = "data/Sounds/GoodAnswer.wav";
//        Player player = new Player();
//        player.play(audioFilePath);
//    }
    
}

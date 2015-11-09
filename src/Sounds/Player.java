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
	
	//Declaration pour le son perte de vie
	protected File audioFileDie;
	protected AudioInputStream audioStreamDie;
	protected AudioFormat formatDie;
	protected DataLine.Info infoDie;
	protected Clip audioClipDie;
	
	//Declaration pour le son bonne réponse
	protected File audioFileGoodAnswer;
	protected AudioInputStream audioStreamGoodAnswer;
	protected AudioFormat formatGoodAnswer;
	protected DataLine.Info infoGoodAnswer;
	protected Clip audioClipGoodAnswer;
	
	//Declaration pour le son mauvaise réponse
	protected File audioFileBadAnswer;
	protected AudioInputStream audioStreamBadAnswer;
	protected AudioFormat formatBadAnswer;
	protected DataLine.Info infoBadAnswer;
	protected Clip audioClipBadAnswer;
	
	//Declaration pour le son Twitter
	protected File audioFileTwitter;
	protected AudioInputStream audioStreamTwitter;
	protected AudioFormat formatTwitter;
	protected DataLine.Info infoTwitter;
	protected Clip audioClipTwitter;
	
	//Déclaration pour le son Gagné
	protected File audioFileOhYeah;
	protected AudioInputStream audioStreamOhYeah;
	protected AudioFormat formatOhYeah;
	protected DataLine.Info infoOhYeah;
	protected Clip audioClipOhYeah;
	
	//Déclaration pour le son Perdu
	protected File audioFilePerdu;
	protected AudioInputStream audioStreamPerdu;
	protected AudioFormat formatPerdu;
	protected DataLine.Info infoPerdu;
	protected Clip audioClipPerdu;
	
	boolean playCompleted;
	
	public Player (){
		
		playCompleted = false;
		
		//Son perte de vie
		audioFileDie = new File("./data/Sounds/Die.wav"); //creation d'un fichier
    	try{
			audioStreamDie = AudioSystem.getAudioInputStream(audioFileDie); //creation du stream
	       formatDie = audioStreamDie.getFormat(); // =wav
	        infoDie = new DataLine.Info(Clip.class, formatDie);
	        audioClipDie = (Clip) AudioSystem.getLine(infoDie);
	        audioClipDie.addLineListener(this); //Listener pour savoir si le son est termine de jouer ou non
	        audioClipDie.open(audioStreamDie); //ouverture du stream
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
    	
    	audioFilePerdu = new File("./data/Sounds/Perdu.wav");
    	try{
			audioStreamPerdu = AudioSystem.getAudioInputStream(audioFilePerdu);
	       formatPerdu = audioStreamPerdu.getFormat();
	        infoPerdu = new DataLine.Info(Clip.class, formatPerdu);
	        audioClipPerdu = (Clip) AudioSystem.getLine(infoPerdu);
	        audioClipPerdu.addLineListener(this);
	        audioClipPerdu.open(audioStreamPerdu);
		} catch (UnsupportedAudioFileException ex) {
	        System.out.println("Perdu : The specified audio file is not supported.");
	        ex.printStackTrace();
	    } catch (LineUnavailableException ex) {
	        System.out.println("Perdu : Audio line for playing back is unavailable.");
	        ex.printStackTrace();
	    } catch (IOException ex) {
	        System.out.println("Perdu : Error playing the audio file.");
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
            audioClipDie.start(); //lancer le son
             
           while (!playCompleted) {
                // wait for the playback completes
                try {
                	//en millisecondes  audioLengthDie
                    Thread.sleep(1/1000); //attendre la fin du son
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
             
        }finally{
           	audioClipDie.stop(); //arret de la lecture 
           	audioClipDie.setFramePosition(0); //remettre le cruseur au debut
           
          }
    }
    
    public void playGoodAnswer() {
        try {
        	playCompleted = false;
            audioClipGoodAnswer.start();
             
           while (!playCompleted) {
                // wait for the playback completes
                try {
                	//en millisecondes
                	Thread.sleep(1/1000);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        }finally{
        	audioClipGoodAnswer.stop();
        	audioClipGoodAnswer.setFramePosition(0);
          }
        
        
    }
    
    public void playBadAnswer() {
 
        try {
        	playCompleted = false;
            audioClipBadAnswer.start();
             
           while (!playCompleted) {
                // wait for the playback completes
                try {
                	Thread.sleep(1/1000);
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
            audioClipTwitter.start();
             
           while (!playCompleted) {
                // wait for the playback completes
                try {
                	//en millisecondes
                	Thread.sleep(1/1000);
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
            audioClipOhYeah.start();
             
           while (!playCompleted) {
                // wait for the playback completes
                try {
                	Thread.sleep(1/1000);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        }finally{
        	audioClipOhYeah.stop();
        	audioClipOhYeah.setFramePosition(0);
          }
        
    }
    
    
    public void playPerdu() {
        try {
        	playCompleted = false;
            audioClipPerdu.start();
             
           while (!playCompleted) {
                // wait for the playback completes
                try {
                	Thread.sleep(1/1000);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        }finally{
        	audioClipPerdu.stop();
        	audioClipPerdu.setFramePosition(0);
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

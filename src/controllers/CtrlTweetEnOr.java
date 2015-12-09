package controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JProgressBar;

import ihm.components.Shared_component;
import twitter4j.TwitterException;
import utils.KeyWord;
import utils.TweetParser;
import utils.TweetWord;
import utils.WordComparator;

/**
 * Created by Arié on 12/10/2015.
 */

public class CtrlTweetEnOr {
    private KeyWord      _keyWords;
    private List<String> _invalidWords;
    private List<String> _validWords;
    List<TweetWord>  _liste_word_pondere;
    private Shared_component _shared;
    private String _word;
    
    public CtrlTweetEnOr(String word,Shared_component shared) throws TwitterException, Exception {
    	// Create "cache" directory if does not exist
    	_shared = shared;
    	_word = word;
    	File directory = new File("cache");
    	directory.mkdirs();
    	
    	
    	File file =  new File("cache/" + word + ".ser");
    	long threeDays = 260000000L;
    	if(file.exists() && file.lastModified() > new java.util.Date().getTime() - threeDays) {
    		_shared.txt_line1.settext("Récupération en local des tweets en cours ...");
			try {
				ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
				this._keyWords = (KeyWord)ois.readObject();
				ois.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			_shared._progressbar.setValue(_shared._progressbar.getMaximum());
    	}
    	else {
			try {
				this._keyWords = TweetParser.findWords(word,_shared);
				if(_keyWords.getListWords().size() > 0){
					ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file));
					oos.writeObject(this._keyWords) ;
					oos.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
				throw e;
			}
    	}
        this._invalidWords = new ArrayList<>();
        this._validWords = new ArrayList<>();
        listeWordsPonderee();
    }

    public CtrlTweetEnOr(KeyWord p_keyW) throws TwitterException, Exception {
        // Create "cache" directory if does not exist
//        _shared = shared;
        _word = p_keyW.getWord();
        this._keyWords = p_keyW;
        this._invalidWords = new ArrayList<>();
        this._validWords = new ArrayList<>();
        listeWordsPonderee();
    }

    public KeyWord getKeyWords() {
        return _keyWords;
    }
    
    public String getWord()
    {
    	return _word;
    }

    /**
     * @param word {String} Mot proposé par l'utilisateur et à verifier
     * @return {TweetWord} <code>true</code> Mot trouvé {TrueWord, ponderation}
     * <code>false</code> Mot trouvé {null, -1}
     * <code>false + alreadyProposed</code> Mot trouvé {null, -2}
     * <code>true + alreadyProposed</code> Mot trouvé {TrueWord, -3}
     */
    public TweetWord isMotValid(String word) {
        for (TweetWord nextTrue : this._keyWords.getListWords()) {
            if (WordComparator.wordCompare(word, nextTrue.getWord())) { /** Si le mot est juste **/
                for (String nextProposed : this._validWords) {
                    if (WordComparator.wordCompare(word, nextProposed)) { /** Si juste et déja proposé **/
                        return new TweetWord(nextTrue.getWord(), -3);
                    }
                }
                /** Si juste, et jamais proposé **/
                this._validWords.add(nextTrue.getWord());
                return nextTrue;
            }
        }
        /** Si le mot est faux **/
        for (String nextProposed : this._invalidWords) {
            if (WordComparator.wordCompare(word, nextProposed)) { /** Si faux et déja proposé **/
                return new TweetWord(null, -2);
            }
        }
        /** Si faux et jamais proposé **/
        this._invalidWords.add(word);
        return new TweetWord(null, -1);
    }

    /**
     * les mot avec leur ponderation
     * @return
     */
    
    private void listeWordsPonderee() {
    	List<TweetWord>  tw = this._keyWords.getListWords();
    	_liste_word_pondere = new ArrayList<TweetWord>();
        for( TweetWord twTmp :  tw){
        	_liste_word_pondere.add((isMotValid(twTmp.getWord())));
        }
     
        _invalidWords.clear();
        _validWords.clear();
	}
    public List<TweetWord> getListWords() {
    	
    	return _liste_word_pondere;
    }

    public boolean isMotAlreadyUse(String Mot) {
        // TODO CPE : loop sur l'iterateur et verification de MOT sur chacun des words de proposedWord
        return false;
    }

    private static boolean netIsAvailable() {                                                                                                                                                                                                 
        try {                                                                                                                                                                                                                                 
            final URL url = new URL("https://twitter.com/");                                                                                                                                                                                 
            final URLConnection conn = url.openConnection();                                                                                                                                                                                  
            conn.connect();                                                                                                                                                                                                                   
            return true;                                                                                                                                                                                                                      
        } catch (MalformedURLException e) {                                                                                                                                                                                                   
            throw new RuntimeException(e);                                                                                                                                                                                                    
        } catch (IOException e) {                                                                                                                                                                                                             
            return false;                                                                                                                                                                                                                     
        }         
    }
    
    public static void main(String[] args) {
        System.out.println("CLASS CtrlTweetEnOr IS NOW RUNNING !");
        CtrlTweetEnOr ctrl;
		try {
			ctrl = new CtrlTweetEnOr("Ski",new Shared_component());
	        System.out.println("aaaaaaa " + ctrl.isMotValid("aaaaaaa"));
	        System.out.println("aaaaaaa " + ctrl.isMotValid("aaaaaaa"));
	        System.out.println("bbbbbbbbbb " + ctrl.isMotValid("bbbbbbbbbb"));
	        System.out.println("alpha " + ctrl.isMotValid("AlPha"));
	        System.out.println("alpha " + ctrl.isMotValid("alpha"));
	        System.out.println("beta " + ctrl.isMotValid("beta"));
		} catch (TwitterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


    }
}

package controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

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
    
    public CtrlTweetEnOr(String word) {
        this._keyWords = TweetParser.findWords(word);
        this._invalidWords = new ArrayList<>();
        this._validWords = new ArrayList<>();
    }

    public KeyWord getKeyWords() {
        return _keyWords;
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

    public List<TweetWord> getListWords() {
        return this._keyWords.getListWords();
    }

    public boolean isMotAlreadyUse(String Mot) {
        // TODO CPE : loop sur l'iterateur et verification de MOT sur chacun des words de proposedWord
        return false;
    }

    public static void main(String[] args) {
        System.out.println("CLASS CtrlTweetEnOr IS NOW RUNNING !");
        CtrlTweetEnOr ctrl = new CtrlTweetEnOr("Ski");

        System.out.println("aaaaaaa " + ctrl.isMotValid("aaaaaaa"));
        System.out.println("aaaaaaa " + ctrl.isMotValid("aaaaaaa"));
        System.out.println("bbbbbbbbbb " + ctrl.isMotValid("bbbbbbbbbb"));
        System.out.println("alpha " + ctrl.isMotValid("AlPha"));
        System.out.println("alpha " + ctrl.isMotValid("alpha"));
        System.out.println("beta " + ctrl.isMotValid("beta"));
    }
}

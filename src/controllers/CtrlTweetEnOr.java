package controllers;

import utils.KeyWord;
import utils.TweetWord;
import utils.WordComparator;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by Arié on 12/10/2015.
 */

public class CtrlTweetEnOr {
    private KeyWord      _keyWords;
    private List<String> _proposedWords;

    public CtrlTweetEnOr(String word) {
        // TODO ARIE : DELETE
        TweetWord alpha = new TweetWord("alpha", 10);
        TweetWord beta = new TweetWord("beta", 20);
        TweetWord gamma = new TweetWord("gamma", 30);
        TweetWord delta = new TweetWord("delta", 40);

        List<TweetWord> myTweetList = new ArrayList<>();
        myTweetList.add(alpha);
        myTweetList.add(beta);
        myTweetList.add(gamma);
        myTweetList.add(delta);
        // TODO ARIE : Fin delete

        this._keyWords = new KeyWord(word, myTweetList);
        this._proposedWords = new ArrayList<>();
    }

    public KeyWord getKeyWords() {
        return _keyWords;
    }

    public List<String> getProposedWords() {
        return _proposedWords;
    }

    public String getOneProposedWords(int index) {
        return this._proposedWords.get(index);
    }

    public void addWordsToList(String newWord) {
        _proposedWords.add(newWord);
    }

    public void foundWords() {
        // TODO CPE : trouver les mots pour le this.keyWords
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
                for (String nextProposed : this._proposedWords) {
                    if (WordComparator.wordCompare(word, nextProposed)) { /** Si juste et déja proposé **/
                        return new TweetWord(nextTrue.getWord(), -3);
                    }
                }
                /** Si juste, et jamais proposé **/
                this._proposedWords.add(nextTrue.getWord());
                return nextTrue;
            }
        }
        /** Si le mot est faux **/
        for (String nextProposed : this._proposedWords) {
            if (WordComparator.wordCompare(word, nextProposed)) { /** Si faux et déja proposé **/
                return new TweetWord(null, -2);
            }
        }
        /** Si faux et jamais proposé **/
        this._proposedWords.add(word);
        return new TweetWord(null, -1);
    }

    public List<TweetWord> getListWords() {
        return this._keyWords.getListWords();
    }

    public boolean isMotAlreadyUse(String Mot) {
        ListIterator listIterator = this._proposedWords.listIterator();
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

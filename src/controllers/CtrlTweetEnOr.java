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

    public TweetWord isMotValid(String word) {
        ListIterator<TweetWord> listIterator = this._keyWords.getListWords().listIterator();
        while (listIterator.hasNext()) {
            TweetWord next = listIterator.next();
            if (WordComparator.wordCompare(word, next.getWord())) {
                return next;
            }
        }
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
}

package controllers;

import utils.KeyWord;
import utils.TweetWord;
import utils.WordComparator;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by Arié on 12/10/2015.
 */

public class CtrlTweetEnOr {
    private KeyWord keyWords;
    private List<String> proposedWords;

    public CtrlTweetEnOr(KeyWord keyWords) {
        this.keyWords = keyWords;
    }

    public KeyWord getKeyWords() {
        return keyWords;
    }

    public List<String> getProposedWords() {
        return proposedWords;
    }

    public String getOneProposedWords(int index) {
        return this.proposedWords.get(index);
    }

    public void addWordsToList(String newWord) {
        proposedWords.add(newWord);
    }

    public void foundWords() {
        // TODO CPE : trouver les mots pour le this.keyWords
    }

    public TweetWord isMotValid(String word) {
        ListIterator<TweetWord> listIterator = this.keyWords.getListWords().listIterator();
        while (listIterator.hasNext()) {
            TweetWord next = listIterator.next();
            // TODO : Arie supprimer le println
//            System.out.println(next + " " + WordComparator.wordCompare(word, next.getWord()));
            if (WordComparator.wordCompare(word, next.getWord())) {
                return next;
            }
        }
        return new TweetWord(null, -1);
    }

    public List<TweetWord> getListWords() {
        return this.keyWords.getListWords();
    }

    public boolean isMotAlreadyUse(String Mot) {
        ListIterator listIterator = this.proposedWords.listIterator();
        // TODO CPE : loop sur l'iterateur et verification de MOT sur chacun des words de proposedWord
        return false;
    }
}

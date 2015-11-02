package utils;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Arié on 12/10/2015.
 */
public class KeyWord implements Serializable {
	private static final long serialVersionUID = 1350092881346723535L;
	
    private String          _word;
    private List<TweetWord> _listWords;

    public KeyWord(String word, List<TweetWord> listWords) {
        this._word = word;
        this._listWords = listWords;
    }

    public String getWord() {
        return this._word;
    }

    public List<TweetWord> getListWords() {
        return _listWords;
    }

    public TweetWord getOneTweetWord(int index) {
        return this._listWords.get(index);
    }

    public TweetWord isMotValid(String mot) {
        TweetWord wordToReturn = new TweetWord("hello", 100); // TODO CPE : a modifier apres code;
        return wordToReturn;
    }

    @Override
    public String toString() {
        return "KeyWord{" +
                "_word='" + _word + '\'' +
                ", _listWords=" + _listWords +
                '}';
    }
}

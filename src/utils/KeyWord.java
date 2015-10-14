package utils;

import java.util.List;

/**
 * Created by Arié on 12/10/2015.
 */
public class KeyWord {
    private String _word;
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
}

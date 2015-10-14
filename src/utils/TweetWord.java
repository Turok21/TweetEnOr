package utils;

/**
 * Created by Ari� on 12/10/2015.
 */
public class TweetWord {
    private String _word;
    private int _ponderation;

    public TweetWord(String word, int ponderation) {
        if (ponderation < 0 || ponderation > 100)
            throw new IllegalArgumentException("input error - illegal ponderation value");
        else {
            this._word = word;
            this._ponderation = ponderation;
        }
    }

    public String getWord() {
        return this._word;
    }

    public int getPonderation() {
        return this._ponderation;
    }

    @Override
    public String toString() {
        return "TweetWord{word=" + _word + ", ponderation=" + _ponderation + "}";
    }
}

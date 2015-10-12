package imh;

/**
 * Created by Arié on 12/10/2015.
 */
public class TweetWord {
    private String word;
    private int ponderation;

    public TweetWord(String word, int ponderation) {
        this.word = word;
        this.ponderation = ponderation;
    }

    public String getWord() {
        return this.word;
    }

    public int getPonderation() {
        return this.ponderation;
    }
}

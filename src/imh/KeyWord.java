package imh;

import com.sun.org.apache.xpath.internal.compiler.Keywords;
import java.util.List;

/**
 * Created by Arié on 12/10/2015.
 */
public class KeyWord extends Keywords {
    private String word;
    private List<TweetWord> words;

    public KeyWord(String word, List<TweetWord> words) {
        this.word = word;
        this.words = words;
    }

    public String getWord() {
        return this.word;
    }

    public List<TweetWord> getWords() {
        return words;
    }

    public TweetWord getOneWord(int index) {
        return this.words.get(index);
    }

    public TweetWord isMotValid(String mot) {
        TweetWord wordToReturn = new TweetWord("hello", 100); // TODO CPE : a modifier apres code;
        return wordToReturn;
    }
}

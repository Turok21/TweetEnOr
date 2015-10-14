package utils;

import java.util.ArrayList;
import java.util.List;
//import Twitter4J;

/**
 * Created by Arié on 12/10/2015.
 */
public abstract class TweetParser {
    public KeyWord foundWords(String keyWords) {
        // TODO CPE algo recuperation
        // todo delete
        List<TweetWord> myTweetWordList = new ArrayList<TweetWord>();
        myTweetWordList.add(new TweetWord("alpha", 10));
        myTweetWordList.add(new TweetWord("beta", 20));
        myTweetWordList.add(new TweetWord("gamma", 30));
        myTweetWordList.add(new TweetWord("delta", 40));
        // TODO fin delete
        return new KeyWord("hello", myTweetWordList);
    }
}

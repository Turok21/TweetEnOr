import java.util.ArrayList;
import java.util.List;

import utils.TweetParser;
import utils.TweetWord;
import utils.WordComparator;
//import controllers.*;
//import utils.*;

public class Main {

    public static void main(String[] args) {
    	TweetParser.findWords("ski");
    	
    	
    	
    	
    	
    	
        System.out.println("Hello World!");

        TweetWord alpha = new TweetWord("alpha", 10);
        TweetWord beta = new TweetWord("AlPha", 20);
        TweetWord gamma = new TweetWord("gamma", 30);
        TweetWord delta = new TweetWord("delta", 40);

        List<TweetWord> myTweetWordList = new ArrayList<TweetWord>();
        myTweetWordList.add(alpha);
        myTweetWordList.add(beta);
        myTweetWordList.add(gamma);
        myTweetWordList.add(delta);

        TweetWord tweet = new TweetWord("hello", 85);
        System.out.println(tweet);
        System.out.println(myTweetWordList);

        System.out.println(WordComparator.wordCompare(alpha.getWord(), beta.getWord()));
    }
}

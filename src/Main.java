import utils.TweetWord;
import java.util.ArrayList;
import java.util.List;
//import controllers.*;
//import utils.*;

public class Main {

    public static void main(String[] args) {
        System.out.println("Hello World!");

        List<TweetWord> myTweetWordList = new ArrayList<TweetWord>();
        myTweetWordList.add(new TweetWord("alpha", 10));
        myTweetWordList.add(new TweetWord("beta", 20));
        myTweetWordList.add(new TweetWord("gamma", 30));
        myTweetWordList.add(new TweetWord("delta", 40));

        TweetWord tweet = new TweetWord("hello", 121);
        System.out.println(tweet);
    }
}

import controllers.CtrlTweetEnOr;
import utils.KeyWord;
import utils.TweetWord;
import utils.WordComparator;
import java.util.ArrayList;
import java.util.List;
//import controllers.*;
//import utils.*;

public class Main {

    public static void main(String[] args) {
        System.out.println("CLASS MAIN IS NOW RUNNING !");

        TweetWord alpha = new TweetWord("alpha", 10);
        TweetWord beta = new TweetWord("beta", 20);
        TweetWord gamma = new TweetWord("gamma", 30);
        TweetWord delta = new TweetWord("delta", 40);

        List<TweetWord> myTweetList = new ArrayList<>();
        myTweetList.add(alpha);
        myTweetList.add(beta);
        myTweetList.add(gamma);
        myTweetList.add(delta);

        KeyWord myKeyWord = new KeyWord("Ski", myTweetList);
        CtrlTweetEnOr ctrlTweet = new CtrlTweetEnOr(myKeyWord);

//        System.out.println(tweet);
//        System.out.println(myTweetWordList);
//        System.out.println(WordComparator.wordCompare(alpha.getWord(), beta.getWord()));

        System.out.println(ctrlTweet.isMotValid("wazaa"));
    }
}

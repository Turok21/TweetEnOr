import imh.TweetWord;

public class Main {

    public static void main(String[] args) {
        System.out.println("Hello World!");

        TweetWord tweet = new TweetWord("hello it's arie", 121);
        System.out.println(tweet.getWord() + " " + tweet.getPonderation());
    }
}

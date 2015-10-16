package utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

/**
 * Created by Ari� on 12/10/2015.
 */
public abstract class TweetParser {
	private static List<String> stopwords = new ArrayList<String>(Arrays.asList(
		"alors", "au", "aucuns", "aussi", "autre", "avant", "avec", "avoir", "bon", 
		"car", "ce", "cela", "ces", "ceux", "chaque", "ci", "comme", "comment", "dans", 
		"des", "du", "dedans", "dehors", "depuis", "devrait", "doit", "donc", "dos", 
		"début", "elle", "elles", "en", "encore", "essai", "est", "et", "eu", "fait", 
		"faites", "fois", "font", "hors", "ici", "il", "ils", "je", "juste", "la", "le", 
		"les", "leur", "là", "ma", "maintenant", "mais", "mes", "mine", "moins", "mon", 
		"mot", "même", "ni", "nommés", "notre", "nous", "ou", "où", "par", "parce", "pas", 
		"peut", "peu", "plupart", "pour", "pourquoi", "quand", "que", "quel", "quelle", 
		"quelles", "quels", "qui", "sa", "sans", "ses", "seulement", "si", "sien", "son", 
		"sont", "sous", "soyez", "sur", "ta", "tandis", "tellement", "tels", "tes", "ton", 
		"tous", "tout", "trop", "très", "tu", "voient", "vont", "votre", "vous", "vu", 
		"ça", "étaient", "état", "étions", "été", "être", "RT", "via"
	));
	
    public static KeyWord findWords(String keyWords) {
        List<TweetWord> myTweetWordList = new ArrayList<TweetWord>();
        List<String> listTweets = getTweets(keyWords);
        List<String> words = new ArrayList<String>();
        for (String tweet : listTweets) {
        	for(String word: tweet.split(" ")){
        		words.add(word.replace("#", "").replace("@", "").replace(",", ""));
        	}
        }
        
        List<TweetWord> tweetWords = toTweetWord(words);
        
        return new KeyWord(keyWords, myTweetWordList);
    }
    
    private static List<String> getTweets(String keyWord) {
    	TwitterFactory tf = new TwitterFactory(config().build()); 
        Twitter twitter = tf.getInstance(); //création de l'objet twitter 
        Query query = new Query(keyWord);
        query.count(100);
        QueryResult result = null;
        List<String> listTweets = new ArrayList<String>();
		try {
			result = twitter.search(query);
		} catch (TwitterException e) {
			e.printStackTrace();
		}
        for (Status status : result.getTweets()) {
        	listTweets.add(status.getText());
        }
        return listTweets;
    }
    
    private static List<TweetWord> toTweetWord(List<String> words) {
    	List<TweetWord> tweetWords = new ArrayList<>();
    	List<String> cleanedWords = cleanWords(words);
    	Map<String, Integer> ponderatedWords = new HashMap<>();
    	// generate map<word, occurences>
    	for (String word : cleanedWords) {
    		int count = ponderatedWords.containsKey(word) ? ponderatedWords.get(word) : 0;
    		ponderatedWords.put(word, count + 1);
        }
    	
    	System.out.println(ponderatedWords);
    	
    	return tweetWords;
    }
    

    
    
    
    private static List<String> cleanWords(List<String> words) {
    	List<String> cleanedWords = new ArrayList<String>();
    	// Remove useless words
    	for (String word : words) {
    		if(!stopwords.contains(word)) {
    			cleanedWords.add(word);
    		}
        }
    	return cleanedWords;
    }
    
    
    private static ConfigurationBuilder config(){
    	Map<String, String> env = System.getenv();
        ConfigurationBuilder conf = new ConfigurationBuilder(); // connexion au twitter
        conf.setOAuthConsumerKey(env.get("CONSUMER_KEY"));
        conf.setOAuthConsumerSecret(env.get("CONSUMER_SECRET")); 
        conf.setOAuthAccessToken("1215866521-LjqUbYcP7n9zCHctz9pGbUiA8UMYKHUY3eCHOuM"); 
        conf.setOAuthAccessTokenSecret("zQn3niHYwOmtgyu0KMOYO5SQjJDZbbu7AbR7TcrpenRfc"); 
        return conf;
   }


	public static void main(String argc[]){
		KeyWord keyw = findWords("ski");
	}
}


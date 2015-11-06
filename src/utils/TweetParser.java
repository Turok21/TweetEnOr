package utils;

import java.io.UnsupportedEncodingException;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.annolab.tt4j.TokenHandler;
import org.annolab.tt4j.TreeTaggerWrapper;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

/**
 * Created by Arié on 12/10/2015.
 */
public abstract class TweetParser {
    private static List<String> stopwords = new ArrayList<String>(Arrays.asList(
		"laquelle", "lesquelles", "depuis", "toujours",
		"qui", "que", "quoi", "dont", "ou", "quel", "quels", "quelle", "quelles",
		"alors", "au", "aucuns", "aussi", "autre", "avant", "avec", "avoir", "bon",
		"cela", "ces", "ceux", "chaque", "ci", "comme", "comment", "dans",
		"en", "encore", "aux", "cet", "cette",
		"fois", "hors", "ici", "juste", "maintenant", "moins",
		"même", "notre", "par", "parce", "pas",
		"peu", "plupart", "pour", "pourquoi", "quand", "quel", "quelle",
		"sans", "seulement", "si", "sien",
		"sous", "sur", "tandis", "tellement", "tels",
		"tous", "tout", "trop", "tres", "vu",
		"jui", "RT", "via", "the", // Specific Twitter words
		"francais", "france", // As we search in french tweet only, these words appear too much
		"itele", "lefigaro", "bfmtv", "lemonde", "tpmp", "lpj" // Spoil too much tweet with their hashtag
    ));

    private static int nbTweetsToGet = 1000;

    private static String urlToken = "urltoken";
    
    private static List<String> excludedTypes = new ArrayList<String>(Arrays.asList(
    		"PRP", "NUM", "PUN", "SENT", "DET", "VER", "KON", "PRO"
    ));

    public static KeyWord findWords(String keyWord) {
    	// Récupération des tweets
    	List<String> listTweets = getTweets(keyWord);
    	final List<String> words = new ArrayList<>();
    	
    	
    	// Démarrage du moteur d'analyse des mots
    	String treeTaggerPath = System.getenv().get("TREE_TAGGER_PATH");
		System.setProperty("treetagger.home", treeTaggerPath);
		TreeTaggerWrapper<String> tt = new TreeTaggerWrapper<String>();
		try {
		    tt.setModel("french-utf8.par");
		    tt.setHandler(new TokenHandler<String>() {
		      // Fonction appelée pour chaque mot du tweet
		      public void token(String token, String pos, String lemma) {
		    	  if(!excludedTypes.contains(pos.split(":")[0])) {
		    		  words.add(token);
		    	  }
		      }
		    });
		    for (String tweet : listTweets) {
		    	tweet = cleanTweet(tweet);
		    	tt.process(tweet.split("'|\\s+"));
	        }
		  }
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
		  tt.destroy();
		}
		
		
		List<String> cleanedWords = cleanWords(words, keyWord);

        Map<String, Integer> topWords = listWordToPonderatedMap(cleanedWords);
        // ponderation
        List<TweetWord> tweetWords = mapPonderatedToTweetWord(topWords);
        return new KeyWord(keyWord, tweetWords);
    }

    private static List<String> getTweets(String keyWord) {
        TwitterFactory tf = new TwitterFactory(config().build());
        Twitter twitter = tf.getInstance(); //création de l'objet twitter 
        List<String> listTweets = new ArrayList<>();

        Query query = new Query(keyWord + " exclude:retweets");
        query.setLang("fr"); // On ne récup que les tweet en français
        query.count(100);
        QueryResult result;
        try {
            result = twitter.search(query);
            do {
                for (Status status : result.getTweets()) {
                    listTweets.add(status.getText());
                }
                query = result.nextQuery();
                if (query != null) {
                    result = twitter.search(query);
                }
            }
            while (query != null && listTweets.size() < nbTweetsToGet);
        } catch (TwitterException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        return listTweets;
    }

    private static Map<String, Integer> listWordToPonderatedMap(List<String> words) {
        Map<String, Integer> ponderatedWords = new HashMap<>();
        /** generate map<word, occurences> */
        for (String word : words) {
            int count = ponderatedWords.containsKey(word) ? ponderatedWords.get(word) : 0;
            ponderatedWords.put(word, count + 1);
        }

        /** Tri de la map */
        Map<String, Integer> sortPonderatedWord = sortByComparator(ponderatedWords);

        Map<String, Integer> sortMergedPonderatedWord = mergeSimilarWords(sortPonderatedWord);
        
        /** Recuperation des 10 mots les plus représentatif */
        Map<String, Integer> topPonderatedWord = new HashMap<>();
        int nbWord = 10;
        for (Map.Entry<String, Integer> entry : sortMergedPonderatedWord.entrySet()) {
            if (nbWord <= 0) break;
            topPonderatedWord.put(entry.getKey(), entry.getValue());
            nbWord--;
        }
        return topPonderatedWord;
    }
    
    private static Map<String, Integer> mergeSimilarWords(Map<String, Integer> sortedMapWords) {
    	Map<String, Integer> mergedMapWords = new HashMap<>();
    	
    	for (String word : new ArrayList<>(sortedMapWords.keySet())) {
    		boolean match = false;
    		for (String mergedWord : new ArrayList<>(mergedMapWords.keySet())) {
    			if(WordComparator.wordCompare(word, mergedWord)) {
    				match = true;
    				// add old and new ponderation
    				int newPonderation =  mergedMapWords.get(mergedWord) + sortedMapWords.get(word);
    				mergedMapWords.put(mergedWord, newPonderation);
    				break;
    			}
    		}
    		if(!match) {
    			// No merge available, create new entry
    			mergedMapWords.put(word, sortedMapWords.get(word));
    		}
    	}
    	// Sort again if order has changed
    	return sortByComparator(mergedMapWords);
    }

    private static List<TweetWord> mapPonderatedToTweetWord(Map<String, Integer> tweetList) {
        double total = 0;
        /** recuperation du total pour le produit en croix **/
        List<TweetWord> listTweetWord = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : tweetList.entrySet()) {
            total += entry.getValue();
        }

        /** Creation des objects TweetWord, et ajout dans la listTweetWord **/
        double value;
        int totalSet = 0;
        for (Map.Entry<String, Integer> entry : tweetList.entrySet()) {
            value = entry.getValue() * 100 / total;
            value = Math.round(value);
            totalSet += value;
            TweetWord tweetWord = new TweetWord(entry.getKey(), (int) value);
            listTweetWord.add(tweetWord);
        }

        if (totalSet != 100) {
            int difference = 100 - totalSet;
            Iterator<TweetWord> iMap = listTweetWord.iterator();
            while (iMap.hasNext() && difference != 0) {
                TweetWord word = iMap.next();
                word.setPonderation(word.getPonderation() + 1);
                difference--;
            }
        }

        return listTweetWord;
    }

    private static List<String> cleanWords(List<String> strings, String keyWord) {
        List<String> cleanedWords = new ArrayList<>();
        for (String word : strings) {
            word = cleanWord(word);
            // Remove useless words
            if (stopwords.contains(word) || word.length() <= 2 || word.equals(keyWord.toLowerCase()) || word.equals(urlToken)) {
                continue;
            }
            cleanedWords.add(word);
        }
        return cleanedWords;
    }

    public static String cleanWord(String word) {
    	word = Normalizer.normalize(word, Normalizer.Form.NFD);
        return word.toLowerCase().replaceAll("[^a-z]|^-", "");
    }

    private static ConfigurationBuilder config() {
        Map<String, String> env = System.getenv();
        ConfigurationBuilder conf = new ConfigurationBuilder(); // connexion au twitter
        conf.setOAuthConsumerKey(env.get("CONSUMER_KEY"));
        conf.setOAuthConsumerSecret(env.get("CONSUMER_SECRET"));
        conf.setOAuthAccessToken("1215866521-LjqUbYcP7n9zCHctz9pGbUiA8UMYKHUY3eCHOuM");
        conf.setOAuthAccessTokenSecret("zQn3niHYwOmtgyu0KMOYO5SQjJDZbbu7AbR7TcrpenRfc");
        return conf;
    }

    private static Map<String, Integer> sortByComparator(Map<String, Integer> unsortMap) {
        List<Map.Entry<String, Integer>> list = new LinkedList<>(unsortMap.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
            public int compare(Map.Entry<String, Integer> o1,
                               Map.Entry<String, Integer> o2) {
                return (o2.getValue()).compareTo(o1.getValue());
            }
        });
        Map<String, Integer> sortedMap = new LinkedHashMap<>();
        for (Iterator<Map.Entry<String, Integer>> it = list.iterator(); it.hasNext(); ) {
            Map.Entry<String, Integer> entry = it.next();
            sortedMap.put(entry.getKey(), entry.getValue());
        }
        return sortedMap;
    }

    
    private static String cleanTweet(String tweet) {
    	String cleanedTweet = removeEmojiAndSymbolFromString(tweet);
    	cleanedTweet = cleanedTweet
    			.replaceAll("http(s)?://[^ ]+", urlToken)
    			.replaceAll("#", "")
    			.replaceAll("\n", " ")
    			.replaceAll("\r", " ")
    			.replaceAll("\"", "")
    			.replaceAll("@[^ ]+", "")
    			.replaceAll("…", "");
    	return cleanedTweet;
    }
    
    private static String removeEmojiAndSymbolFromString(String content) {
        String utf8tweet = "";
        try {
            byte[] utf8Bytes = content.getBytes("UTF-8");

            utf8tweet = new String(utf8Bytes, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Pattern unicodeOutliers = Pattern.compile(
            "[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]",
            Pattern.UNICODE_CASE |
            Pattern.CANON_EQ |
            Pattern.CASE_INSENSITIVE
        );
        Matcher unicodeOutlierMatcher = unicodeOutliers.matcher(utf8tweet);

        utf8tweet = unicodeOutlierMatcher.replaceAll(" ");
        return utf8tweet;
    }
    
    
    public static void main(String argc[]) {
        KeyWord keyw = findWords("ski");
    }
}


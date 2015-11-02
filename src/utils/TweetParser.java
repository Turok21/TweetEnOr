package utils;

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
    	      "le", "la", "les",
    	      "ma", "mon", "mes", "tes", "ses", "sa", "son", "leurs", "leur", "laquelle", "lesquelles",
    	      "qui", "que", "quoi", "dont", "ou", "quel", "quels", "quelle", "quelles",
    	      "mais", "ou", "et", "donc", "or", "ni", "car",
    	      "se", "ca", "ce",
    	      "je", "tu", "il", "elle", "on", "nous", "vous", "ils", "elles",
    	      "lui", "eux",
    	      "aux", "cet", "cette",
    	      "alors", "au", "aucuns", "aussi", "autre", "avant", "avec", "avoir", "bon",
    	      "cela", "ces", "ceux", "chaque", "ci", "comme", "comment", "dans",
    	      "des", "du", "depuis", "devrait", "doit", "dos",
    	      "en", "encore", "essai", "et", "eu", "fait",
    	      "faites", "fois", "font", "hors", "ici", "juste", "maintenant", "moins",
    	      "mot", "même", "notre", "par", "parce", "pas",
    	      "peut", "peu", "plupart", "pour", "pourquoi", "quand", "quel", "quelle",
    	      "sans", "seulement", "si", "sien",
    	      "sont", "sous", "soyez", "sur", "ta", "tandis", "tellement", "tels", "tes", "ton",
    	      "tous", "tout", "trop", "tres", "voient", "vont", "votre", "vu",
    	      "etaient", "etions", "ete", "etre", "RT", "via", "de", "une", "the",
    	      "suis", "es", "est", "etes", "sommes", "sont", 
    	      "ai", "as", "a", "avons", "avez", "ont"
    	));

    private static int nbTweetsToGet = 4000;

    public static KeyWord findWords(String keyWords) {
        List<String> listTweets = getTweets(keyWords);
        List<String> words = new ArrayList<>();
        for (String tweet : listTweets) {
            words.addAll(cleanWords(tweet.split(" |'"), keyWords));
        }

        Map<String, Integer> topWords = listWordToPonderatedMap(words);
        // ponderation
        List<TweetWord> tweetWords = mapPonderatedToTweetWord(topWords);
        return new KeyWord(keyWords, tweetWords);
    }

    private static List<String> getTweets(String keyWord) {
        TwitterFactory tf = new TwitterFactory(config().build());
        Twitter twitter = tf.getInstance(); //création de l'objet twitter 
        List<String> listTweets = new ArrayList<>();

        Query query = new Query(keyWord);
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
        System.out.println(ponderatedWords);

        /** Tri de la map */
        Map<String, Integer> sortPonderatedWord = sortByComparator(ponderatedWords);

        /** Recuperation des 10 mots les plus représentatif */
        Map<String, Integer> topPonderatedWord = new HashMap<>();
        int nbWord = 10;
        for (Map.Entry<String, Integer> entry : sortPonderatedWord.entrySet()) {
            if (nbWord <= 0) break;
            topPonderatedWord.put(entry.getKey(), entry.getValue());
            nbWord--;
        }
        return topPonderatedWord;
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
        double totalSet = 0;
        for (Map.Entry<String, Integer> entry : tweetList.entrySet()) {
            value = entry.getValue() * 100 / total;
            value = Math.round(value);
            totalSet += value;
            TweetWord tweetWord = new TweetWord(entry.getKey(), (int) value);
            listTweetWord.add(tweetWord);
        }
        if (totalSet != 100) System.out.println("Total des points != 100 / care ");

        return listTweetWord;
    }

    private static List<String> cleanWords(String[] strings, String keyword) {
        List<String> cleanedWords = new ArrayList<>();
        for (String word : strings) {
            word = cleanWord(word);
            // Remove useless words
            if (!stopwords.contains(word) && word.length() > 2 && !word.equals(keyword.toLowerCase()) && !word.startsWith("http") && !word.contains("httpstco")) {
                cleanedWords.add(word);
            }
        }
        return cleanedWords;
    }

    public static String cleanWord(String word) {
    	word = Normalizer.normalize(word, Normalizer.Form.NFD);
        return word.toLowerCase().replaceAll("[^a-z]", "").replace("\n", "").replace("\r", "");
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

    public static void main(String argc[]) {
        KeyWord keyw = findWords("ski");
//        System.out.println(keyw);
    }
}


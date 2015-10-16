package utils;

import java.util.*;

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
            "ça", "étaient", "état", "étions", "été", "être", "RT", "via", "de", "une"
    ));

    private static int nbTweetsToGet = 2000;

    public static KeyWord findWords(String keyWords) {
        List<String> listTweets = getTweets(keyWords);
        List<String> words = new ArrayList<>();
        for (String tweet : listTweets) {
            words.addAll(cleanWords(tweet.split(" "), keyWords));
        }
        Map<String, Integer> topWords = toTweetWord(words);
        printMap(topWords);
        // ponderation
        List<TweetWord> tweetWords = editPonderation(topWords);
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

    private static Map<String, Integer> toTweetWord(List<String> words) {
        Map<String, Integer> ponderatedWords = new HashMap<>();
        /** generate map<word, occurences> */
        for (String word : words) {
            int count = ponderatedWords.containsKey(word) ? ponderatedWords.get(word) : 0;
            ponderatedWords.put(word, count + 1);
        }

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

    private static List<TweetWord> editPonderation(Map<String, Integer> tweetList) {
        int total = 0;
        List<TweetWord> listTweetWord = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : tweetList.entrySet()) {
            total += entry.getValue();
        }
        for (Map.Entry<String, Integer> entry : tweetList.entrySet()) {
            TweetWord tweetWord = new TweetWord(entry.getKey(), entry.getValue() * 100 / total);
            listTweetWord.add(tweetWord);
        }
        return listTweetWord;
    }

    private static List<String> cleanWords(String[] strings, String keyword) {
        List<String> cleanedWords = new ArrayList<>();
        for (String word : strings) {
            word = cleanWord(word);
            // Remove useless words
            if (!stopwords.contains(word) && word.length() > 2 && !word.equals(keyword) && !word.startsWith("http")) {
                cleanedWords.add(word);
            }
        }
        return cleanedWords;
    }

    public static String cleanWord(String word) {
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

    // TODO CPE : to delete
    public static void printMap(Map<String, Integer> map) {
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            System.out.println("[Key] : " + entry.getKey()
                    + " [Value] : " + entry.getValue());
        }
    }
    // TODO CPE : fin to delete

    public static void main(String argc[]) {
        KeyWord keyw = findWords("ski");
        System.out.println(keyw);
    }

}


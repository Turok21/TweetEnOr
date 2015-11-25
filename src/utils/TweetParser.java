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


import ihm.components.Shared_component;
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
		"sans", "seulement", "si", "sien", "meme", "plus","bien",
		"sous", "sur", "tandis", "tellement", "tels",
		"tous", "tout", "trop", "tres", "vu", "sinon",
		"jui", "RT", "via", "the", "tweet", "jsuis", "dun", // Specific Twitter words
		"francais", "france", // As we search in french tweet only, these words appear too much
		"itele", "lefigaro", "bfmtv", "lemonde", "tpmp", "lpj", "lesechos", // Spoil too much tweet with their hashtag
		"demain","aujourd","hui"
    ));

    private static int nbTweetsToGet = 1000;        // Nombre de tweet a récuperer (par partie) pour générer les mots
    private static String language = "fr"; // On ne récup que les tweets en français
    private static int nbTweetParRequest = 100; // 100 tweets/requete (qui est en réalité le maximum de Twitter
    private static int nbWordToGet = 10; // Nombre de mot a récuperer au final pour jouer

    private static String urlToken = "urltoken";    // Token identifiable permettant lors du traitemant de garder la structure grammaticale des phrases

    private static List<String> excludedTypes = new ArrayList<>(Arrays.asList(
    		"PRP", "NUM", "PUN", "SENT", "DET", "VER", "KON", "PRO"
    ));     // natures des mots à exclure (en utilisant la librairie "TreeTagger")

	public static KeyWord findWords(String keyWord, final Shared_component shared) throws Exception {
    	// Récupération des tweets
    	List<String> listTweets = getTweets(keyWord, shared);

    	final List<String> words = new ArrayList<>();

    	// Démarrage du moteur d'analyse des mots
    	String treeTaggerPath = System.getenv().get("TREE_TAGGER_PATH");
		System.setProperty("treetagger.home", treeTaggerPath);
		TreeTaggerWrapper<String> tt = new TreeTaggerWrapper<>();
		try {
		    tt.setModel("french-utf8.par");
		    tt.setHandler(new TokenHandler<String>() {
		      // Fonction appelée pour chaque mot du tweet  (Separation, reconnaissance du type et analyse des mots)
		      public void token(String token, String pos, String lemma) {
		    	  if(!excludedTypes.contains(pos.split(":")[0])) {
		    		  // Si la nature du mot n'est pas à exclure, on le rejoute dans la liste
		    		  words.add(token);
		    	  }
		      }
		    });

            // Mise à jour de la barre de progression (Sur une echelle de X Tweet)
		    shared._progressbar.setMaximum(listTweets.size());
	    	shared._progressbar.setValue(0);

            // boucle sur les tweets
		    for (String tweet : listTweets) {
		    	// On nettoie de tweet (suppréssion des Emoji et autres symboles inutiles
		    	tweet = cleanTweet(tweet);
		    	// Convertion de la phrase en une liste de mots et analyse de la phrase
		    	tt.process(tweet.split("‘|’|'|\\s+"));

                // MAJ de la barre de progression en meme temps des tweets
		    	shared._progressbar.setValue(shared._progressbar.getValue()+1);
		    	shared.txt_line1.settext("analyse sémantique, syntaxique et grammaticale des tweets... " + shared._progressbar.getValue() + "/" + shared._progressbar.getMaximum() + " (2/3)");

	        }
		  }
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
		  tt.destroy();
		}

		// Nettoyage des mots (suppréssion des majuscules, accents, caractères spéciaux)
		List<String> cleanedWords = cleanWords(words, keyWord);

        // Récupération des top words utilisé dans les tweets  (issue de cleanedWords et avec la barre de progression)
        Map<String, Integer> topWords = listWordToPonderatedMap(cleanedWords,shared);

        // Convertion de la map en list de TweetWord ponderation (et vérifie que la pondération soit bien égale à 100)
        List<TweetWord> tweetWords = mapPonderatedToTweetWord(topWords);
        return new KeyWord(keyWord, tweetWords);
    }

    /**
     * Récupere une list de mots
     * @param keyWord {String}  mot cible utilisé pour la recherche
     * @param shared {Shared_component} bar de progression utilisé pour l'affiche
     * @return {List<String>} List des tweets issues des requetes
     * @throws TwitterException 
     */
    private static List<String> getTweets(String keyWord, Shared_component shared) throws TwitterException, Exception {
    	// Récupération des identifiants Twitter
    	
        TwitterFactory tf = new TwitterFactory(config().build());
        Twitter twitter = tf.getInstance();
        List<String> listTweets = new ArrayList<>();
        shared._progressbar.setMaximum(nbTweetsToGet);
        shared._progressbar.setValue(0);
        shared.txt_line1.settext("Récupération des tweets ... " + shared._progressbar.getValue() + "/"+shared._progressbar.getMaximum() + " (1/3)");
        Query query = new Query(keyWord + " exclude:retweets");

        query.setLang(language); // On ne récup que les tweets en français
        query.count(nbTweetParRequest); // 100 tweets/requete
        QueryResult result;
        try {
            result = twitter.search(query);
            if(result.getCount()  >= nbTweetsToGet)
            {
	            do {
	                for (Status status : result.getTweets()) {
	                    listTweets.add(status.getText());
	                    shared._progressbar.setValue(shared._progressbar.getValue()+1);
	                    shared.txt_line1.settext("Récupération des tweets ... " + shared._progressbar.getValue() + "/"+shared._progressbar.getMaximum() + " (1/3)");
	                }
	                query = result.nextQuery();
	                if (query != null) {
	                    result = twitter.search(query);
	                }
	
	            }
	            while (query != null && listTweets.size() < nbTweetsToGet);
            }else{
            	return listTweets;
            }
        } catch (TwitterException e1) {
            // TODO Auto-generated catch block
            //e1.printStackTrace();
        	throw e1;
        } catch (Exception e1){
        	throw e1;
        }
        return listTweets;
    }

    /**
     * Recuperer les 10 premiers mot utilisé pour avoir keyWord
     * @param words {List<String>} :
     * @param shared {Shared_component}  : barre de progression a utiliser pour l'affichage
     * @return {Map<String, Integer>} 10 top words avec leur pondération
     */
    private static Map<String, Integer> listWordToPonderatedMap(List<String> words, Shared_component shared) {
        Map<String, Integer> ponderatedWords = new HashMap<>();
        // Génération d'une map<mot, nombre_occurence)
        for (String word : words) {
            int count = ponderatedWords.containsKey(word) ? ponderatedWords.get(word) : 0;
            ponderatedWords.put(word, count + 1);
        }

        // Tri de la map par ordre décroissant d'occurence
        Map<String, Integer> sortPonderatedWord = sortByComparator(ponderatedWords);

        // Fusion des mots similaires dans la map
        Map<String, Integer> sortMergedPonderatedWord = mergeSimilarWords(sortPonderatedWord,shared);

        //Recuperation des X mots les plus représentatifs  (X = nbWordToGet)
        Map<String, Integer> topPonderatedWord = new HashMap<>();
        int nbWord = nbWordToGet;
        for (Map.Entry<String, Integer> entry : sortMergedPonderatedWord.entrySet()) {
            if (nbWord <= 0) break;
            topPonderatedWord.put(entry.getKey(), entry.getValue());
            nbWord--;
        }
        return topPonderatedWord;
    }

    /**
     * Fusion des mots proche en augmentant leur pondération en fonction
     * @param sortedMapWords {Map<String, Integer>} Map de String pondération pré-triée a utilisé
     * @param shared {Shared_component}  : barre de progression a utiliser pour l'affichage
     * @return {Map<String, Integer>}
     */
    private static Map<String, Integer> mergeSimilarWords(Map<String, Integer> sortedMapWords, Shared_component shared) {
    	Map<String, Integer> mergedMapWords = new HashMap<>();

    	shared._progressbar.setValue(0);
    	shared._progressbar.setMinimum(0);
    	shared._progressbar.setMaximum(sortedMapWords.size());
    	for (String word : new ArrayList<>(sortedMapWords.keySet())) {
    		// On parcourt la map triée des mots.
    		// La map triée nous permet de s'assurer qu'en cas de fusion de 2 mots, on garde l'orthographe du plus utilisé
    		boolean match = false;
    		for (String mergedWord : new ArrayList<>(mergedMapWords.keySet())) {
    			// Pour chaque mot, on vérifie si un équivalent est déjà présent
    			if(WordComparator.wordCompare(word, mergedWord)) {
    				match = true;
    				// aSi oui, on ajoute la pondération du mot courant à celle du mot équivalent
    				int newPonderation =  mergedMapWords.get(mergedWord) + sortedMapWords.get(word);
    				mergedMapWords.put(mergedWord, newPonderation);
    				break;
    			}
    		}
    		if(!match) {
    			// Si aucun mot équivalent n'a été trouvé, on ajoute de mot à la map
    			mergedMapWords.put(word, sortedMapWords.get(word));
    		}
    		shared._progressbar.setValue(shared._progressbar.getValue()+1);
    		shared.txt_line1.settext("Générations des mots... " + shared._progressbar.getValue() + "/" + shared._progressbar.getMaximum() + " (3/3)");

    	}
    	// On tri de nouveau la map , et return le résultat
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
        // On veut que la somme des points attribué à chaque mot soit égale à 100
        for (Map.Entry<String, Integer> entry : tweetList.entrySet()) {
            value = entry.getValue() * 100 / total;
            value = Math.round(value);
            totalSet += value;
            TweetWord tweetWord = new TweetWord(entry.getKey(), (int) value);
            listTweetWord.add(tweetWord);
        }
        // Il arrive que la somme des points ne soit pas égale à 100, a cause des arrondis
        // Dans ce cas, on arrtibut les points manquant aux x premiers de la liste. X étant le nombre de points manquants
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
            // On exclut les mots dont on ne veut pas (cf la liste stopwords)
            // On les mots de deux lettres ou moins
            // On exclu les urls
            if (stopwords.contains(word) || word.length() <= 2 || word.equals(keyWord.toLowerCase()) || word.equals(urlToken)) {
                continue;
            }
            cleanedWords.add(word);

        }
        return cleanedWords;
    }

    public static String cleanWord(String word) {
    	// On supprime les accents, on retire tous les caratères spéciaux sauf les - et on met tout au minuscule
    	word = Normalizer.normalize(word, Normalizer.Form.NFD);
        return word.toLowerCase().replaceAll("[^a-z]|^-", "");
    }

    private static ConfigurationBuilder config() {
    	// Configuration du module chargé de requeter l'API Twitter
        Map<String, String> env = System.getenv();
        ConfigurationBuilder conf = new ConfigurationBuilder();
        conf.setOAuthConsumerKey(env.get("CONSUMER_KEY"));
        conf.setOAuthConsumerSecret(env.get("CONSUMER_SECRET"));
        conf.setOAuthAccessToken("1215866521-LjqUbYcP7n9zCHctz9pGbUiA8UMYKHUY3eCHOuM");
        conf.setOAuthAccessTokenSecret("zQn3niHYwOmtgyu0KMOYO5SQjJDZbbu7AbR7TcrpenRfc");
        return conf;
    }

    private static Map<String, Integer> sortByComparator(Map<String, Integer> unsortMap) {
    	// Methode de tri d'une map par valeur décroissante
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
    			// On remplace les url par un token identifiable (pour garder la structure grammaticale de la phrase)
    			.replaceAll("http(s)?://[^ ]+", " " + urlToken) // On ajoute un espace car Twitter permet d'acoller une mot et u
    			.replaceAll("#", "") // On enlève les hashtag devant les mots
    			.replaceAll("\n", " ") // On enlève les retours ligne
    			.replaceAll("\r", " ") // On enlève les retours ligne
    			.replaceAll("\"", "") // On enlève les guillemets *
                .replaceAll("@[^ ]+", "") // On enlève les mensions (@le_compte_d_une_personne)
    			.replaceAll("…", ""); // On enlève le caractère 3-points très utilisé sur twitter (un seul char au leu de 3)
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
        KeyWord keyw;
        
		try {
			keyw = findWords("ski",new Shared_component());
			System.out.println(keyw);
		
		} catch(Exception e){
			if(e instanceof IllegalStateException)
			{
				System.out.println("sdffffffffff");
			}
			e.printStackTrace();
		}
       
    }
}


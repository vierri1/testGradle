package realExamle;

import org.apache.log4j.Logger;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Set;

public class WordFInderUser {
    private WordFinder wordFinder;
    private Logger logger = Logger.getLogger(WordFInderUser.class);

    public WordFInderUser(WordFinder wordFinder) {
        this.wordFinder = wordFinder;
    }

    public void doWord(String resource, String word) throws MalformedURLException {
        URL url;
        try {
            url = new URL(resource);
        } catch (MalformedURLException ex) {
            logger.error(ex.getMessage());
            throw ex;
        }
        Set<String> sentences = wordFinder.getSentences(url);
        if (sentences != null) {
            for (String sentence : sentences) {
                if (wordFinder.checkIfWordInSentence(sentence, word)) {
                    wordFinder.writeSentenceToResult(sentence);
                }
            }
        }
    }
}

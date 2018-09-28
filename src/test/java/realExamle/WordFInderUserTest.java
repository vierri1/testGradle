package realExamle;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class WordFInderUserTest {

    private final String FIRST_SENTENCE = "Java is good";
    private final String SECOND_SENTENCE = "Java is very good";
    private final String URL = "file://any";
    private final String WORD = "";
    private WordFInderUser wordFInderUser;
    private WordFinder wordFinder = Mockito.mock(WordFinder.class);

    @BeforeEach
    void setUp() {
        wordFInderUser = new WordFInderUser(wordFinder);
    }

    @Test
    void doWordNullTest() {
        when(wordFinder.getSentences(any())).thenReturn(null);
        assertDoesNotThrow(() -> wordFInderUser.doWord(URL, WORD));
    }

    @Test
    void doWordEmptySet() {
        when(wordFinder.getSentences(any())).thenReturn(Collections.emptySet());
        try {
            wordFInderUser.doWord(URL, WORD);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        verify(wordFinder, times(0)).checkIfWordInSentence(any(), any());
        verify(wordFinder, times(0)).writeSentenceToResult(any());
    }

    @Test
    void doWordTrueCheck() {
        when(wordFinder.getSentences(any())).thenReturn(new HashSet<>(Arrays.asList(FIRST_SENTENCE, SECOND_SENTENCE)));
        when(wordFinder.checkIfWordInSentence(FIRST_SENTENCE, WORD)).thenReturn(true);
        when(wordFinder.checkIfWordInSentence(SECOND_SENTENCE, WORD)).thenReturn(false);
        try {
            wordFInderUser.doWord(URL, WORD);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        verify(wordFinder, times(1)).writeSentenceToResult(FIRST_SENTENCE);
        verify(wordFinder, times(0)).writeSentenceToResult(SECOND_SENTENCE);
    }

    @Test
    void doWordURLForming() {
        ArgumentCaptor<URL> argumentCaptor = ArgumentCaptor.forClass(URL.class);
        try {
            wordFInderUser.doWord(URL, WORD);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        verify(wordFinder).getSentences(argumentCaptor.capture());
        assertEquals(URL, argumentCaptor.getValue().toString());
    }

    @Test
    void doWordBadUrl() {
        assertThrows(MalformedURLException.class, () -> wordFInderUser.doWord("", WORD));
    }
}
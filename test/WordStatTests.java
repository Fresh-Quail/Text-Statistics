import org.junit.*;
import static org.junit.Assert.*;
import java.io.FileNotFoundException;

public class WordStatTests {

    @Test
    public void testWordCount_PairCount() throws FileNotFoundException {

        WordStat empty = new WordStat("C:\\Users\\Aibra\\Alcove\\Mound\\P4\\WordStat Resources\\Empty.txt");
        WordStat justOneWord = new WordStat("C:\\Users\\Aibra\\Alcove\\Mound\\P4\\WordStat Resources\\JustOneWord.txt");
        WordStat justOneWordPair = new WordStat("C:\\Users\\Aibra\\Alcove\\Mound\\P4\\WordStat Resources\\JustOneWordPair.txt");
        WordStat manyJustOneWords = new WordStat("C:\\Users\\Aibra\\Alcove\\Mound\\P4\\WordStat Resources\\ManyJustOneWords.txt");
        WordStat manyWordCounts = new WordStat("C:\\Users\\Aibra\\Alcove\\Mound\\P4\\WordStat Resources\\ManyWordCounts.txt");
        WordStat manyWords = new WordStat("C:\\Users\\Aibra\\Alcove\\Mound\\P4\\WordStat Resources\\ManyWords.txt");
        WordStat multipleWordPairs = new WordStat("C:\\Users\\Aibra\\Alcove\\Mound\\P4\\WordStat Resources\\MultipleWordPairs.txt");

        //Tests a given empty source
        assertEquals(0, empty.wordCount(""));
        assertEquals(0, empty.wordCount("Empty"));
        assertEquals(0, empty.wordPairCount("", ""));
        assertEquals(0, empty.wordPairCount("Empty", ""));
        assertEquals(0, empty.wordPairCount("", "Empty"));
//Tests one
        //Tests a source with one word or wordpair
        assertEquals(1, justOneWord.wordCount("JustOneWord"));
        assertEquals(0, justOneWord.wordCount("String"));
        assertEquals(0, justOneWord.wordCount(""));
        assertEquals(0, justOneWord.wordCount("None"));

        assertEquals(0, justOneWord.wordPairCount("JustOneWord", ""));
        assertEquals(0, justOneWord.wordPairCount("", "JustOneWord"));
        assertEquals(0, justOneWordPair.wordPairCount("justone wordpair", ""));
        assertEquals(1, justOneWordPair.wordPairCount("justone", "WordPair"));

            //Tests normalizing entries
        assertEquals(1, justOneWord.wordCount("justoneword"));
        assertEquals(1, justOneWord.wordCount("_'*!!$%JustoneWord$%^@$"));
//Tests many
        //Tests many of the same word or word pairs
        assertEquals(6, manyJustOneWords.wordCount("JustOneWord"));
        assertEquals(0, manyJustOneWords.wordCount(""));

        assertEquals(5, manyJustOneWords.wordPairCount("JustOneWord", "Justoneword"));
        assertEquals(0, manyJustOneWords.wordPairCount("JustOneWord", ""));
        assertEquals(0, manyJustOneWords.wordPairCount("", "JustOneWord"));

        //Tests multiple words with the same count
        assertEquals(1, manyWords.wordCount("This"));
        assertEquals(1, manyWords.wordCount("file"));
        assertEquals(1, manyWords.wordCount("has"));
        assertEquals(1, manyWords.wordCount("many"));
        assertEquals(1, manyWords.wordCount("or"));
        assertEquals(1, manyWords.wordCount("more"));
        assertEquals(1, manyWords.wordCount("words"));
        assertEquals(0, manyWords.wordCount(""));
        assertEquals(0, manyWords.wordCount("None"));

        //Tests multiple words/wordPairs of different counts
        assertEquals(6, manyWordCounts.wordCount("many"));
        assertEquals(5, manyWordCounts.wordCount("This"));
        assertEquals(4, manyWordCounts.wordCount("text"));
        assertEquals(3, manyWordCounts.wordCount("file"));
        assertEquals(2, manyWordCounts.wordCount("has"));
        assertEquals(1, manyWordCounts.wordCount("words"));
        assertEquals(0, manyWordCounts.wordCount(""));

        assertEquals(5, manyWordCounts.wordPairCount("many", "many"));
        assertEquals(4, manyWordCounts.wordPairCount("This", "this"));
        assertEquals(3, manyWordCounts.wordPairCount("text", "text"));
        assertEquals(2, manyWordCounts.wordPairCount("file", "file"));
        assertEquals(1, manyWordCounts.wordPairCount("has", "has"));
        assertEquals(0, manyWordCounts.wordPairCount("words", "words"));
        assertEquals(0, manyWordCounts.wordPairCount("", ""));
//Make sure all unique word pairs are caught, front to back
        assertEquals(1, manyWordCounts.wordPairCount("This", "text"));
        assertEquals(1, manyWordCounts.wordPairCount("text", "file"));
        assertEquals(1, manyWordCounts.wordPairCount("file", "has"));
        assertEquals(1, manyWordCounts.wordPairCount("has", "many"));
        assertEquals(1, manyWordCounts.wordPairCount("many", "words"));
//Makes sure the word pairs aren't reversible
        assertEquals(0, manyWordCounts.wordPairCount("text", "This"));
        assertEquals(0, manyWordCounts.wordPairCount("file", "text"));
        assertEquals(0, manyWordCounts.wordPairCount("has", "file"));
        assertEquals(0, manyWordCounts.wordPairCount("many", "has"));
        assertEquals(0, manyWordCounts.wordPairCount("words", "many"));

        //Tests if word counts and word pair counts are correctly counted - i.e at the beginning, middle, & end
        assertEquals(3, multipleWordPairs.wordCount("ape"));
        assertEquals(8, multipleWordPairs.wordCount("bat"));
        assertEquals(3, multipleWordPairs.wordCount("cat"));

        assertEquals(2, multipleWordPairs.wordPairCount("ape", "bat"));
        assertEquals(2, multipleWordPairs.wordPairCount("bat", "cat"));
        assertEquals(1, multipleWordPairs.wordPairCount("cat", "cat"));
        assertEquals(4, multipleWordPairs.wordPairCount("bat", "bat"));
        assertEquals(2, multipleWordPairs.wordPairCount("cat", "bat"));

    }

    @Test
    public void testWordRank_PairRank() throws FileNotFoundException {
        WordStat empty = new WordStat("C:\\Users\\Aibra\\Alcove\\Mound\\P4\\WordStat Resources\\Empty.txt");
        WordStat justOneWord = new WordStat("C:\\Users\\Aibra\\Alcove\\Mound\\P4\\WordStat Resources\\JustOneWord.txt");
        WordStat justOneWordPair = new WordStat("C:\\Users\\Aibra\\Alcove\\Mound\\P4\\WordStat Resources\\JustOneWordPair.txt");
        WordStat manyJustOneWords = new WordStat("C:\\Users\\Aibra\\Alcove\\Mound\\P4\\WordStat Resources\\ManyJustOneWords.txt");
        WordStat manyWordCounts = new WordStat("C:\\Users\\Aibra\\Alcove\\Mound\\P4\\WordStat Resources\\ManyWordCounts.txt");
        WordStat manyWords = new WordStat("C:\\Users\\Aibra\\Alcove\\Mound\\P4\\WordStat Resources\\ManyWords.txt");
        WordStat multipleWordPairs = new WordStat("C:\\Users\\Aibra\\Alcove\\Mound\\P4\\WordStat Resources\\MultipleWordPairs.txt");

//Tests a given empty source
        assertEquals(0, empty.wordRank(""));
        assertEquals(0, empty.wordRank("Empty"));
        assertEquals(0, empty.wordPairRank("", ""));
        assertEquals(0, empty.wordPairRank("Empty", ""));
        assertEquals(0, empty.wordPairRank("", "Empty"));

//Tests one
        //Tests a source with one word or wordpair
        assertEquals(1, justOneWord.wordRank("JustOneWord"));
        assertEquals(0, justOneWord.wordRank("String"));
        assertEquals(0, justOneWord.wordRank(""));
        assertEquals(0, justOneWord.wordRank("None"));

        assertEquals(0, justOneWordPair.wordPairRank("JustOneWord", ""));
        assertEquals(0, justOneWordPair.wordPairRank("", "JustOneWord"));

        //Tests normalizing entries
        assertEquals(1, justOneWord.wordRank("justoneword"));
        assertEquals(1, justOneWord.wordRank("_'*!!$%JustoneWord$%^@$"));

//Tests many
        //Tests many similar words - one rank
        assertEquals(1, manyJustOneWords.wordRank("JustOneWord"));
        assertEquals(0, manyJustOneWords.wordRank(""));

        assertEquals(1, manyJustOneWords.wordPairRank("JustOneWord", "Justoneword"));
        assertEquals(0, manyJustOneWords.wordPairRank("JustOneWord", ""));
        assertEquals(0, manyJustOneWords.wordPairRank("", "JustOneWord"));

        //Tests many - different words - one rank
        assertEquals(1, manyWords.wordRank("This"));
        assertEquals(1, manyWords.wordRank("file"));
        assertEquals(1, manyWords.wordRank("has"));
        assertEquals(1, manyWords.wordRank("many"));
        assertEquals(1, manyWords.wordRank("or"));
        assertEquals(1, manyWords.wordRank("more"));
        assertEquals(1, manyWords.wordRank("words"));
        assertEquals(0, manyWords.wordRank(""));
        assertEquals(0, manyWords.wordRank("None"));

        //Tests many - different words - multiple ranks
        assertEquals(1, manyWordCounts.wordRank("many"));
        assertEquals(2, manyWordCounts.wordRank("This"));
        assertEquals(3, manyWordCounts.wordRank("text"));
        assertEquals(4, manyWordCounts.wordRank("file"));
        assertEquals(5, manyWordCounts.wordRank("has"));
        assertEquals(6, manyWordCounts.wordRank("words"));
        assertEquals(0, manyWordCounts.wordRank(""));

        assertEquals(1, manyWordCounts.wordPairRank("many", "many"));
        assertEquals(2, manyWordCounts.wordPairRank("This", "this"));
        assertEquals(3, manyWordCounts.wordPairRank("text", "text"));
        assertEquals(4, manyWordCounts.wordPairRank("file", "file"));
        assertEquals(5, manyWordCounts.wordPairRank("has", "has"));
        assertEquals(0, manyWordCounts.wordPairRank("words", "words"));
        assertEquals(0, manyWordCounts.wordPairRank("", ""));

        //Tests duplicate word ranks
        assertEquals(2, multipleWordPairs.wordRank("ape"));
        assertEquals(1, multipleWordPairs.wordRank("bat"));
        assertEquals(2, multipleWordPairs.wordRank("cat"));
        assertEquals(4, multipleWordPairs.wordRank("control"));

        //Tests duplicate wordPair ranks
        assertEquals(2, multipleWordPairs.wordPairRank("ape", "bat"));
        assertEquals(2, multipleWordPairs.wordPairRank("bat", "cat"));
        assertEquals(6, multipleWordPairs.wordPairRank("cat", "cat"));
        assertEquals(1, multipleWordPairs.wordPairRank("bat", "bat"));
        assertEquals(2, multipleWordPairs.wordPairRank("cat", "bat"));

//Make sure all unique word paair ranks are caught, front to back - Tests for non-unique ranks
        assertEquals(5, manyWordCounts.wordPairRank("This", "text"));
        assertEquals(5, manyWordCounts.wordPairRank("text", "file"));
        assertEquals(5, manyWordCounts.wordPairRank("file", "has"));
        assertEquals(5, manyWordCounts.wordPairRank("has", "many"));
        assertEquals(5, manyWordCounts.wordPairRank("many", "words"));
    }

    @Test
    public void testMostCommonWords_WordPairs() throws FileNotFoundException
    {
        WordStat empty = new WordStat("C:\\Users\\Aibra\\Alcove\\Mound\\P4\\WordStat Resources\\Empty.txt");
        WordStat justOneWord = new WordStat("C:\\Users\\Aibra\\Alcove\\Mound\\P4\\WordStat Resources\\JustOneWord.txt");
        WordStat justOneWordPair = new WordStat("C:\\Users\\Aibra\\Alcove\\Mound\\P4\\WordStat Resources\\JustOneWordPair.txt");
        WordStat manyJustOneWords = new WordStat("C:\\Users\\Aibra\\Alcove\\Mound\\P4\\WordStat Resources\\ManyJustOneWords.txt");
        WordStat manyWordCounts = new WordStat("C:\\Users\\Aibra\\Alcove\\Mound\\P4\\WordStat Resources\\ManyWordCounts.txt");
        WordStat manyWords = new WordStat("C:\\Users\\Aibra\\Alcove\\Mound\\P4\\WordStat Resources\\ManyWords.txt");

        //Tests empty
        assertArrayEquals(new String[]{}, empty.mostCommonWords(0));
        assertArrayEquals(new String[]{}, empty.mostCommonWords(1));
        assertArrayEquals(new String[]{}, empty.mostCommonWords(4));

        assertArrayEquals(new String[]{}, empty.mostCommonWordPairs(0));
        assertArrayEquals(new String[]{}, empty.mostCommonWordPairs(1));
        assertArrayEquals(new String[]{}, empty.mostCommonWordPairs(4));

        //Tests one-------------------------------------------------------------------
        //Tests if a zero length array is correctly returned
        assertArrayEquals(new String[]{}, justOneWord.mostCommonWords(0));
        assertArrayEquals(new String[]{}, justOneWord.mostCommonWordPairs(0));

        //Tests just one word
        assertArrayEquals(new String[]{"justoneword"}, justOneWord.mostCommonWords(1));
        assertArrayEquals(new String[]{"justoneword"}, justOneWord.mostCommonWords(3));
        assertArrayEquals(new String[]{}, justOneWord.mostCommonWordPairs(3));

        assertArrayEquals(new String[]{"justone wordpair"}, justOneWordPair.mostCommonWordPairs(3));

        //Tests multiple one words and one word pairs
        assertArrayEquals(new String[]{"justoneword"}, manyJustOneWords.mostCommonWords(1));
        assertArrayEquals(new String[]{"justoneword"}, manyJustOneWords.mostCommonWords(3));

        assertArrayEquals(new String[]{"justoneword justoneword"}, manyJustOneWords.mostCommonWordPairs(3));

        //Tests many different words - tests alphabetical
        assertArrayEquals(new String[]{"file", "has", "many", "more", "or", "text", "this", "words"}, manyWords.mostCommonWords(11));
        assertArrayEquals(new String[]{"file has", "has many", "many or", "more words", "or more", "text file", "this text"}, manyWords.mostCommonWordPairs(11));

        //Tests many of many different words - tests alphabetical - sorted by value when values are different
        assertArrayEquals(new String[]{"many"}, manyWordCounts.mostCommonWords(1));
        assertArrayEquals(new String[]{"many", "this", "text"}, manyWordCounts.mostCommonWords(3));
        assertArrayEquals(new String[]{"many", "this", "text", "file", "has", "words"}, manyWordCounts.mostCommonWords(11));

        assertArrayEquals(new String[]{"many many"}, manyWordCounts.mostCommonWordPairs(1));
        assertArrayEquals(new String[]{"many many", "this this", "text text"}, manyWordCounts.mostCommonWordPairs(3));
        assertArrayEquals(new String[]{"many many", "this this", "text text", "file file", "file has", "has has", "has many", "many words", "text file", "this text"}, manyWordCounts.mostCommonWordPairs(21));
    }

    @Test
    public void testLeastCommonWords_WordPairs() throws FileNotFoundException {
        WordStat empty = new WordStat("C:\\Users\\Aibra\\Alcove\\Mound\\P4\\WordStat Resources\\Empty.txt");
        WordStat justOneWord = new WordStat("C:\\Users\\Aibra\\Alcove\\Mound\\P4\\WordStat Resources\\JustOneWord.txt");
        WordStat justOneWordPair = new WordStat("C:\\Users\\Aibra\\Alcove\\Mound\\P4\\WordStat Resources\\JustOneWordPair.txt");
        WordStat manyJustOneWords = new WordStat("C:\\Users\\Aibra\\Alcove\\Mound\\P4\\WordStat Resources\\ManyJustOneWords.txt");
        WordStat manyWordCounts = new WordStat("C:\\Users\\Aibra\\Alcove\\Mound\\P4\\WordStat Resources\\ManyWordCounts.txt");
        WordStat manyWords = new WordStat("C:\\Users\\Aibra\\Alcove\\Mound\\P4\\WordStat Resources\\ManyWords.txt");

        //Tests empty
        assertArrayEquals(new String[]{}, empty.leastCommonWords(0));
        assertArrayEquals(new String[]{}, empty.leastCommonWords(1));
        assertArrayEquals(new String[]{}, empty.leastCommonWords(4));

        assertArrayEquals(new String[]{}, empty.leastCommonWordPairs(0));
        assertArrayEquals(new String[]{}, empty.leastCommonWordPairs(1));
        assertArrayEquals(new String[]{}, empty.leastCommonWordPairs(4));

        //Tests one-------------------------------------------------------------------
        //Tests if a zero length array is correctly returned
        assertArrayEquals(new String[]{}, justOneWord.leastCommonWords(0));
        assertArrayEquals(new String[]{}, justOneWord.leastCommonWordPairs(0));

        //Tests just one word
        assertArrayEquals(new String[]{"justoneword"}, justOneWord.leastCommonWords(1));
        assertArrayEquals(new String[]{"justoneword"}, justOneWord.leastCommonWords(3));
        assertArrayEquals(new String[]{}, justOneWord.leastCommonWordPairs(3));

        assertArrayEquals(new String[]{"justone wordpair"}, justOneWordPair.leastCommonWordPairs(1));
        assertArrayEquals(new String[]{"justone wordpair"}, justOneWordPair.leastCommonWordPairs(3));

        //Tests multiple one words and one word pairs
        assertArrayEquals(new String[]{}, manyJustOneWords.leastCommonWords(0));
        assertArrayEquals(new String[]{"justoneword"}, manyJustOneWords.leastCommonWords(1));
        assertArrayEquals(new String[]{"justoneword"}, manyJustOneWords.leastCommonWords(3));

        assertArrayEquals(new String[]{"justoneword justoneword"}, manyJustOneWords.leastCommonWordPairs(3));

        //Tests many different words - same counts - tests alphabetic order
        assertArrayEquals(new String[]{}, manyWords.leastCommonWords(0));
        assertArrayEquals(new String[]{"file", "has", "many", "more", "or", "text", "this", "words"}, manyWords.leastCommonWords(11));
        assertArrayEquals(new String[]{"file has", "has many", "many or", "more words", "or more", "text file", "this text"}, manyWords.leastCommonWordPairs(11));

        //Tests many of many different words - different counts
        assertArrayEquals(new String[]{}, manyWordCounts.leastCommonWords(0));
        assertArrayEquals(new String[]{"words"}, manyWordCounts.leastCommonWords(1));
        assertArrayEquals(new String[]{"words", "has", "file"}, manyWordCounts.leastCommonWords(3));
        assertArrayEquals(new String[]{"words", "has", "file", "text", "this", "many"}, manyWordCounts.leastCommonWords(11));

        assertArrayEquals(new String[]{"file has"}, manyWordCounts.leastCommonWordPairs(1));
        assertArrayEquals(new String[]{"file has", "has has", "has many"}, manyWordCounts.leastCommonWordPairs(3));
        assertArrayEquals(new String[]{"file has", "has has", "has many", "many words", "text file", "this text", "file file", "text text", "this this", "many many"}, manyWordCounts.leastCommonWordPairs(21));

    }

    @Test
    public void testMostCommonCollocs() throws FileNotFoundException
    {
        WordStat empty = new WordStat("C:\\Users\\Aibra\\Alcove\\Mound\\P4\\WordStat Resources\\Empty.txt");
        WordStat justOneWord = new WordStat("C:\\Users\\Aibra\\Alcove\\Mound\\P4\\WordStat Resources\\JustOneWord.txt");
        WordStat manyWordCounts = new WordStat("C:\\Users\\Aibra\\Alcove\\Mound\\P4\\WordStat Resources\\ManyWordCounts.txt");
        WordStat manyWords = new WordStat("C:\\Users\\Aibra\\Alcove\\Mound\\P4\\WordStat Resources\\ManyWords.txt");

        //Tests empty---------------------------------------------------------------------------------------
        assertArrayEquals(new String[]{}, empty.mostCommonCollocs(0, "word", -1));
        assertArrayEquals(new String[]{}, empty.mostCommonCollocs(0, "word", 1));
        assertArrayEquals(new String[]{}, empty.mostCommonCollocs(5, "word", -1));
        assertArrayEquals(new String[]{}, empty.mostCommonCollocs(5, "word", 1));

        assertArrayEquals(new String[]{}, empty.mostCommonCollocs(0, "", -1));
        assertArrayEquals(new String[]{}, empty.mostCommonCollocs(0, "", 1));
        assertArrayEquals(new String[]{}, empty.mostCommonCollocs(3, "", -1));
        assertArrayEquals(new String[]{}, empty.mostCommonCollocs(3, "", 1));

        //Tests one-----------------------------------------------------------------------------------------------------
        //Tests just one word
        assertArrayEquals(new String[]{}, justOneWord.mostCommonCollocs(0, "", -1));
        assertArrayEquals(new String[]{}, justOneWord.mostCommonCollocs(0, "", 1));
        assertArrayEquals(new String[]{}, justOneWord.mostCommonCollocs(3, "", -1));
        assertArrayEquals(new String[]{}, justOneWord.mostCommonCollocs(3, "", 1));

        assertArrayEquals(new String[]{}, justOneWord.mostCommonCollocs(0, "justoneword", -1));
        assertArrayEquals(new String[]{}, justOneWord.mostCommonCollocs(0, "justoneword", 1));
        assertArrayEquals(new String[]{}, justOneWord.mostCommonCollocs(5, "justoneword", -1));
        assertArrayEquals(new String[]{}, justOneWord.mostCommonCollocs(5, "justoneword", 1));

        //Tests many----------------------------------------------------------------------------------------------------
        //Tests returning an empty string
        assertArrayEquals(new String[]{}, manyWordCounts.mostCommonCollocs(0, "", -1));
        assertArrayEquals(new String[]{}, manyWordCounts.mostCommonCollocs(0, "", 1));
        assertArrayEquals(new String[]{}, manyWordCounts.mostCommonCollocs(0, "words", -1));
        assertArrayEquals(new String[]{}, manyWordCounts.mostCommonCollocs(0, "this", 1));

        //Tests when all collocations occur once
        //Tests first
        //Tests alphabetical - words of the same count are sorted alphabetically
        //Tests returning correct array size
        assertArrayEquals(new String[]{"file"}, manyWords.mostCommonCollocs(1, "this", 1));
        assertArrayEquals(new String[]{"file", "has", "many", "more", "or", "text", "words"}, manyWords.mostCommonCollocs(15, "this", 1));

        //Tests middle
        assertArrayEquals(new String[]{"file"}, manyWords.mostCommonCollocs(1, "has", -1));
        assertArrayEquals(new String[]{"many"}, manyWords.mostCommonCollocs(1, "has", 1));

        assertArrayEquals(new String[]{"file", "text", "this"}, manyWords.mostCommonCollocs(15, "has", -1));
        assertArrayEquals(new String[]{"many", "more", "or", "words"}, manyWords.mostCommonCollocs(15, "has", 1));

        //Tests last
        assertArrayEquals(new String[]{"file"}, manyWords.mostCommonCollocs(1, "words", -1));
        assertArrayEquals(new String[]{"file", "has", "many", "more", "or", "text", "this"}, manyWords.mostCommonCollocs(15, "words", -1));

        //Tests when collocations occur multiple times
        //Tests alphabetical - words of different counts are not sorted alphabetically
        //Tests first
        assertArrayEquals(new String[]{}, manyWordCounts.mostCommonCollocs(1, "this", -1));

        assertArrayEquals(new String[]{"many"}, manyWordCounts.mostCommonCollocs(1, "this", 1));
        assertArrayEquals(new String[]{"many", "text", "this"}, manyWordCounts.mostCommonCollocs(3, "this", 1));
        assertArrayEquals(new String[]{"many", "text", "this", "file", "has", "words"}, manyWordCounts.mostCommonCollocs(15, "this", 1));

        //Tests middle
        assertArrayEquals(new String[]{"this"}, manyWordCounts.mostCommonCollocs(1, "file", -1));
        assertArrayEquals(new String[]{"many"}, manyWordCounts.mostCommonCollocs(1, "file", 1));

        assertArrayEquals(new String[]{"this", "text"}, manyWordCounts.mostCommonCollocs(6, "file", -1));
        assertArrayEquals(new String[]{"many", "file", "has", "words"}, manyWordCounts.mostCommonCollocs(6, "file", 1));

        //Tests end
        assertArrayEquals(new String[]{}, manyWordCounts.mostCommonCollocs(1, "words", 1));

        assertArrayEquals(new String[]{"many"}, manyWordCounts.mostCommonCollocs(1, "words", -1));
        assertArrayEquals(new String[]{"many", "this", "text"}, manyWordCounts.mostCommonCollocs(3, "words", -1));
        assertArrayEquals(new String[]{"many", "this", "text", "file", "has"}, manyWordCounts.mostCommonCollocs(15, "words", -1));

    }

    @Test
    public void testmostCommonCollocsExc() throws FileNotFoundException
    {
        WordStat empty = new WordStat("C:\\Users\\Aibra\\Alcove\\Mound\\P4\\WordStat Resources\\Empty.txt");
        WordStat justOneWord = new WordStat("C:\\Users\\Aibra\\Alcove\\Mound\\P4\\WordStat Resources\\JustOneWord.txt");
        WordStat manyWordCounts = new WordStat("C:\\Users\\Aibra\\Alcove\\Mound\\P4\\WordStat Resources\\ManyWordCounts.txt");
        WordStat manyWords = new WordStat("C:\\Users\\Aibra\\Alcove\\Mound\\P4\\WordStat Resources\\ManyWords.txt");
//--------------------------These tests simply test similar functionality to mostCommonCollocs()----------------------//
        //Tests empty---------------------------------------------------------------------------------------
        assertArrayEquals(new String[]{}, empty.mostCommonCollocsExc(0, "word", -1, new String[]{}));
        assertArrayEquals(new String[]{}, empty.mostCommonCollocsExc(0, "word", 1, new String[]{}));
        assertArrayEquals(new String[]{}, empty.mostCommonCollocsExc(5, "word", -1, new String[]{}));
        assertArrayEquals(new String[]{}, empty.mostCommonCollocsExc(5, "word", 1, new String[]{}));

        assertArrayEquals(new String[]{}, empty.mostCommonCollocsExc(0, "", -1, new String[]{}));
        assertArrayEquals(new String[]{}, empty.mostCommonCollocsExc(0, "", 1, new String[]{}));
        assertArrayEquals(new String[]{}, empty.mostCommonCollocsExc(3, "", -1, new String[]{}));
        assertArrayEquals(new String[]{}, empty.mostCommonCollocsExc(3, "", 1, new String[]{}));

        //Tests one-----------------------------------------------------------------------------------------------------
        //Tests just one word
        assertArrayEquals(new String[]{}, justOneWord.mostCommonCollocsExc(0, "", -1, new String[]{}));
        assertArrayEquals(new String[]{}, justOneWord.mostCommonCollocsExc(0, "", 1, new String[]{}));
        assertArrayEquals(new String[]{}, justOneWord.mostCommonCollocsExc(3, "", -1, new String[]{}));
        assertArrayEquals(new String[]{}, justOneWord.mostCommonCollocsExc(3, "", 1, new String[]{}));

        assertArrayEquals(new String[]{}, justOneWord.mostCommonCollocsExc(0, "justoneword", -1, new String[]{}));
        assertArrayEquals(new String[]{}, justOneWord.mostCommonCollocsExc(0, "justoneword", 1, new String[]{}));
        assertArrayEquals(new String[]{}, justOneWord.mostCommonCollocsExc(5, "justoneword", -1, new String[]{}));
        assertArrayEquals(new String[]{}, justOneWord.mostCommonCollocsExc(5, "justoneword", 1, new String[]{}));

        //Tests many----------------------------------------------------------------------------------------------------
        //Tests returning an empty string, no exclusions
        assertArrayEquals(new String[]{}, manyWordCounts.mostCommonCollocsExc(0, "", -1, new String[]{}));
        assertArrayEquals(new String[]{}, manyWordCounts.mostCommonCollocsExc(0, "", 1, new String[]{}));
        assertArrayEquals(new String[]{}, manyWordCounts.mostCommonCollocsExc(0, "words", -1, new String[]{}));
        assertArrayEquals(new String[]{}, manyWordCounts.mostCommonCollocsExc(0, "this", 1, new String[]{}));
        //Tests returning an empty string, with exclusions
        assertArrayEquals(new String[]{}, manyWordCounts.mostCommonCollocsExc(0, "", -1, new String[]{"file"}));
        assertArrayEquals(new String[]{}, manyWordCounts.mostCommonCollocsExc(0, "", 1, new String[]{"file"}));
        assertArrayEquals(new String[]{}, manyWordCounts.mostCommonCollocsExc(0, "words", -1, new String[]{"file"}));
        assertArrayEquals(new String[]{}, manyWordCounts.mostCommonCollocsExc(0, "this", 1, new String[]{"file"}));


//-----------------------------------------These tests test exclusions-----------------------------------------------//
        //Tests when all collocations occur once
        //Tests alphabetical - words of the same count are sorted alphabetically
        //Tests returning correct array size - no exclusions
        assertArrayEquals(new String[]{"file"}, manyWords.mostCommonCollocsExc(1, "this", 1, new String[]{}));
        assertArrayEquals(new String[]{"file", "has", "many", "more", "or", "text", "words"}, manyWords.mostCommonCollocsExc(15, "this", 1, new String[]{}));
        //With exclusions
        //Tests one exclusion
        assertArrayEquals(new String[]{"file"}, manyWords.mostCommonCollocsExc(1, "this", 1, new String[]{"text"}));
        assertArrayEquals(new String[]{"has"}, manyWords.mostCommonCollocsExc(1, "this", 1, new String[]{"file"}));
        assertArrayEquals(new String[]{}, manyWords.mostCommonCollocsExc(15, "this", 1, new String[]{"this", "file", "has", "many", "more", "or", "text", "words"}));

        //Tests many exclusions
        assertArrayEquals(new String[]{"has", "many", "more"}, manyWords.mostCommonCollocsExc(3, "this", 1, new String[]{"file"}));
        assertArrayEquals(new String[]{"has", "more", "or"}, manyWords.mostCommonCollocsExc(3, "this", 1, new String[]{"file", "many"}));
        assertArrayEquals(new String[]{}, manyWords.mostCommonCollocsExc(15, "this", 1, new String[]{"this", "file", "has", "many", "more", "or", "text", "words"}));

        //Tests when collocations occur multiple times
        //Tests alphabetical - words of different counts are not sorted alphabetically
        //Tests first - no exclusions
        assertArrayEquals(new String[]{}, manyWordCounts.mostCommonCollocsExc(1, "this", -1, new String[]{}));

        assertArrayEquals(new String[]{"many"}, manyWordCounts.mostCommonCollocsExc(1, "this", 1, new String[]{}));
        assertArrayEquals(new String[]{"many", "text", "this"}, manyWordCounts.mostCommonCollocsExc(3, "this", 1, new String[]{}));
        assertArrayEquals(new String[]{"many", "text", "this", "file", "has", "words"}, manyWordCounts.mostCommonCollocsExc(15, "this", 1, new String[]{}));

        //Tests one exclusion
        assertArrayEquals(new String[]{}, manyWordCounts.mostCommonCollocsExc(1, "this", -1, new String[]{"many"}));

        assertArrayEquals(new String[]{"many"}, manyWordCounts.mostCommonCollocsExc(1, "this", 1, new String[]{"file"}));
        assertArrayEquals(new String[]{"text"}, manyWordCounts.mostCommonCollocsExc(1, "this", 1, new String[]{"many"}));

        assertArrayEquals(new String[]{"many", "text", "this"}, manyWordCounts.mostCommonCollocsExc(3, "this", 1, new String[]{"file"}));
        assertArrayEquals(new String[]{"many", "this", "file"}, manyWordCounts.mostCommonCollocsExc(3, "this", 1, new String[]{"text"}));
        assertArrayEquals(new String[]{"many", "text", "this", "file", "has"}, manyWordCounts.mostCommonCollocsExc(15, "this", 1, new String[]{"words"}));

        //Tests many exclusions
        assertArrayEquals(new String[]{}, manyWordCounts.mostCommonCollocsExc(1, "this", -1, new String[]{"file", "words"}));

        assertArrayEquals(new String[]{"this"}, manyWordCounts.mostCommonCollocsExc(1, "this", 1, new String[]{"many", "text"}));

        assertArrayEquals(new String[]{"file", "has", "words"}, manyWordCounts.mostCommonCollocsExc(3, "this", 1, new String[]{"many", "this", "text"}));

        assertArrayEquals(new String[]{"many", "text", "this"}, manyWordCounts.mostCommonCollocsExc(15, "this", 1, new String[]{"file", "has", "words"}));
        assertArrayEquals(new String[]{}, manyWordCounts.mostCommonCollocsExc(15, "this", 1, new String[]{"many", "text", "this", "file", "has", "words"}));
    }

    @Test
    public void generateWordString() throws FileNotFoundException {
        WordStat empty = new WordStat("C:\\Users\\Aibra\\Alcove\\Mound\\P4\\WordStat Resources\\Empty.txt");
        WordStat justOneWord = new WordStat("C:\\Users\\Aibra\\Alcove\\Mound\\P4\\WordStat Resources\\JustOneWord.txt");
        WordStat manyWordCounts = new WordStat("C:\\Users\\Aibra\\Alcove\\Mound\\P4\\WordStat Resources\\ManyWordCounts.txt");
        WordStat manyWords = new WordStat("C:\\Users\\Aibra\\Alcove\\Mound\\P4\\WordStat Resources\\ManyWords.txt");
        WordStat multipleWordPairs = new WordStat("C:\\Users\\Aibra\\Alcove\\Mound\\P4\\WordStat Resources\\MultipleWordPairs.txt");

        //Tests empty
        assertEquals("word", empty.generateWordString(0, "word"));
        assertEquals("word", empty.generateWordString(0, "word"));
        assertEquals("word", empty.generateWordString(5, "word"));
        assertEquals("word", empty.generateWordString(5, "word"));

        assertEquals("", empty.generateWordString(0, ""));
        assertEquals("", empty.generateWordString(0, ""));
        assertEquals("", empty.generateWordString(3, ""));
        assertEquals("", empty.generateWordString(3, ""));

        //Tests one word source
        assertEquals("", justOneWord.generateWordString(0, ""));
        assertEquals("", justOneWord.generateWordString(0, ""));
        assertEquals("", justOneWord.generateWordString(3, ""));
        assertEquals("", justOneWord.generateWordString(3, ""));

        assertEquals("justoneword", justOneWord.generateWordString(0, "justoneword"));
        assertEquals("justoneword", justOneWord.generateWordString(0, "justoneword"));
        assertEquals("justoneword", justOneWord.generateWordString(5, "justoneword"));
        assertEquals("justoneword", justOneWord.generateWordString(5, "justoneword"));

        //Tests a many word source
        assertEquals("", manyWordCounts.generateWordString(0, ""));
        assertEquals("", manyWordCounts.generateWordString(0, ""));
        assertEquals("words", manyWordCounts.generateWordString(0, "words"));
        assertEquals("this", manyWordCounts.generateWordString(0, "this"));

        //Tests when all collocations occur once
        //Tests first
        assertEquals("this text", manyWords.generateWordString(1, "this"));
        assertEquals("this text file has many or more words", manyWords.generateWordString(15, "this"));

        //Tests middle
        assertEquals("has many", manyWords.generateWordString(1, "has"));
        assertEquals("has many or more words", manyWords.generateWordString(15, "has"));

        //Tests last
        assertEquals("words", manyWords.generateWordString(1, "words"));
        assertEquals("words", manyWords.generateWordString(15, "words"));

        //Tests when collocations occur multiple times
        //Tests alphabetical - words of different counts are not sorted alphabetically
        //Tests first
        assertEquals("this many", manyWordCounts.generateWordString(1, "this"));

        assertEquals("this many many many", manyWordCounts.generateWordString(3, "this"));
        assertEquals("this many many many many many many words", manyWordCounts.generateWordString(15, "this"));

        //Tests middle
        assertEquals("file many", manyWordCounts.generateWordString(1, "file"));
        assertEquals("file many many many", manyWordCounts.generateWordString(3, "file"));

        assertEquals("file many many many many many many", manyWordCounts.generateWordString(6, "file"));
        assertEquals("file many many many many many many words", manyWordCounts.generateWordString(14, "file"));

        //Tests end
        assertEquals("words", manyWordCounts.generateWordString(1, "words"));

        assertEquals("words", manyWordCounts.generateWordString(3, "words"));
        assertEquals("words", manyWordCounts.generateWordString(15, "words"));
    }





}

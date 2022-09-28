import org.junit.*;
import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;


public class TokenizerTests {

    @Test
    public void testTokenizerFileSource() throws FileNotFoundException {
        //Tests zero words
        ArrayList<String> arrayList = new ArrayList<>();
        assertEquals(0, new Tokenizer("C:\\Users\\Aibra\\Alcove\\Mound\\P4\\test resources\\Empty.txt").wordList().size());
        assertEquals(0, new Tokenizer("C:\\Users\\Aibra\\Alcove\\Mound\\P4\\test resources\\EmptyLines.txt").wordList().size());
        assertEquals(0, new Tokenizer("C:\\Users\\Aibra\\Alcove\\Mound\\P4\\test resources\\Spaces&Lines.txt").wordList().size());
        assertEquals(0, new Tokenizer("C:\\Users\\Aibra\\Alcove\\Mound\\P4\\test resources\\Special.txt").wordList().size());

        //Tests one word
        assertEquals(List.of("oneword"), new Tokenizer("C:\\Users\\Aibra\\Alcove\\Mound\\P4\\test resources\\One.txt").wordList());
        assertEquals(List.of("oneword", "oneword","oneword", "nospaces", "justlines"), new Tokenizer("C:\\Users\\Aibra\\Alcove\\Mound\\P4\\test resources\\1WordManyLines.txt").wordList());
        assertEquals(List.of("oneword", "oneword", "oneword", "with", "spaces"), new Tokenizer("C:\\Users\\Aibra\\Alcove\\Mound\\P4\\test resources\\Leading&TrailingSpaces.txt").wordList());

        //Tests many words
        assertEquals(List.of("manywords", "with", "no", "punctuation", "are", "correctly", "normalized", "including", "multiple", "spaces", "between", "words"), new Tokenizer("C:\\Users\\Aibra\\Alcove\\Mound\\P4\\test resources\\Many.txt").wordList());
        assertEquals(List.of("manywords", "with", "no", "punctuation", "are", "correctly", "normalized", "including", "multiple", "spaces", "and", "special", "characters", "between", "words"), new Tokenizer("C:\\Users\\Aibra\\Alcove\\Mound\\P4\\test resources\\Many&Special.txt").wordList());


    }

    @Test
    public void testTokenizerStringArray()
    {
        //Tests zero words
        assertEquals(0, new Tokenizer(new String[] {}).wordList().size());
        assertEquals(0, new Tokenizer(new String[] {""}).wordList().size());
        assertEquals(0, new Tokenizer(new String[] {"", "", ""}).wordList().size());
        assertEquals(0, new Tokenizer(new String[]{"!”#$%&’()*+,-./:;<=>?@[\\]^_`{|}"}).wordList().size());
        assertEquals(0, new Tokenizer(new String[]{"!”#$%&’()*+,-./:;<=>?@[\\]^_`{|}", "!”#$%&’()*+,", "””###$$$%"}).wordList().size());

        //Tests one word
        assertEquals(List.of("oneword"), new Tokenizer(new String[] {"OneWord"}).wordList());
        assertEquals(List.of("oneword", "oneword", "oneword"), new Tokenizer(new String[] {"OneWord", "OneWord", "OneWord"}).wordList());
        assertEquals(List.of("oneword", "oneword", "oneword", "oneword", "oneword"), new Tokenizer(new String[] {"   OneWord   ", "   OneWord", "OneWord   ", "!”#$%&’()*+,-./:;<=>?@[\\]^_`{|}OneWord", "OneWord!”#$%&’()*+,-./:;<=>?@[\\]^_`{|}"}).wordList());

        //Tests many words
        assertEquals(List.of("manywords", "with", "no", "punctuation", "are", "correctly", "normalized", "including", "multiple", "spaces", "between", "words"), new Tokenizer(new String[]{"ManyWords with no punctuation are correctly normalized     including     multiple spaces  between    words\n"}).wordList());
        assertEquals(List.of("manywords", "with", "no", "punctuation", "are", "correctly", "normalized", "including", "multiple", "spaces", "between", "words"), new Tokenizer(new String[]{"ManyWords with no punctuation are correctly normalized  ", "   including     multiple spaces  between    words\n"}).wordList());
        assertEquals(List.of("manywords", "with", "no", "punctuation", "are", "correctly", "normalized", "including", "multiple", "spaces", "and", "special", "characters", "between", "words"), new Tokenizer(new String[]{"!@#!@#%@#$^ManyWords^^%#$with#@$!no punctuation, are correctly normalized     including     multiple spaces and%$^@$%@ special@#% characters  between    words!!!\n"}).wordList());
        assertEquals(List.of("manywords", "with", "no", "punctuation", "are", "correctly", "normalized", "including", "multiple", "spaces", "and", "special", "characters", "between", "words"), new Tokenizer(new String[]{"!@#!@#%@#$^ManyWords^^%#$with#@$!no punctuation, are correctly", " normalized     including     multiple spaces and%$^", "@$%@ special@#% characters  between    words!!!\n"}).wordList());

    }

}

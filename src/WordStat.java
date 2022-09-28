import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class WordStat extends HashTable{
    //Stores and normalizes text from the given source
    private Tokenizer tokenizer;

    //Stores the given file or array statistics for the class in their respective hash tables
    private HashTable wordCounts = new HashTable();
    private HashTable wordRank = new HashTable();
    private HashTable wordPairCounts = new HashTable();
    private HashTable wordPairRank = new HashTable();

    //Stores the wordCounts of each unique word in the given array
    private HashEntry[] rankedWordList;
    private HashEntry[] rankedWordPairList;

    //A comparator that sorts hash entries first by value, then alphabetically
    private Comparator<HashEntry> alphaNumeric = (o1, o2) -> {
            //Only sorts alphabetically if both entries have the same word count
            if(o2.getValue() - o1.getValue() == 0)
            {
                //If o1's key is longer, iterate through o2 and compare it to o1
                if(o1.getKey().length() > o2.getKey().length())
                {
                    //Iterates through o2's key and compares it alphabetiically to o1's key
                    for (int j = 0; j < o2.getKey().length(); j++)
                    {
                        if(o1.getKey().charAt(j) - o2.getKey().charAt(j) != 0)
                            return o1.getKey().charAt(j) - o2.getKey().charAt(j);
                    }
                }
                //Otherwise, loop through o1 and compare it to o2
                else
                {
                    //Iterates through o1's key and compares it alphabetiically to o2's key
                    for (int j = 0; j < o1.getKey().length(); j++)
                    {
                        if(o1.getKey().charAt(j) - o2.getKey().charAt(j) != 0)
                            return o1.getKey().charAt(j) - o2.getKey().charAt(j);
                    }
                }

                //If the keys are alphabetically identical up to one of their lengths, return the shorter key
                return o1.getKey().length() - o2.getKey().length();
            }
            return o2.getValue() - o1.getValue();
    };
    //A comparator that sorts hash entries first by value, then alphabetically, but for word pairs
    private Comparator<HashEntry> alphaNumericPairs = (o1, o2) -> {
        //Only sorts alphabetically if both entries have the same word count
        if(o2.getValue() - o1.getValue() == 0)
        {
            String[] wordPair1 = o1.getKey().split(" ");
            String[] wordPair2 = o2.getKey().split(" ");
            //If o1's first word is longer, iterate through o2's first word and compare it to o1
            if(wordPair1[0].length() > wordPair2[0].length())
            {
                //Iterates through o2's first word and compares it alphabetiically to o1's first word
                for (int j = 0; j < wordPair2[0].length(); j++)
                {
                    if(wordPair1[0].charAt(j) - wordPair2[0].charAt(j) != 0)
                        return wordPair1[0].charAt(j) - wordPair2[0].charAt(j);
                }
            }
            //Otherwise, loop through o1 and compare it to o2
            else
            {
                //Iterates through o1's first word and compares it alphabetiically to o2's first word
                for (int j = 0; j < wordPair1[0].length(); j++)
                {
                    if(wordPair1[0].charAt(j) - wordPair2[0].charAt(j) != 0)
                        return wordPair1[0].charAt(j) - wordPair2[0].charAt(j);
                }
            }
            //If the keys are alphabetically identical up to one of their lengths, return the shorter key
            if(wordPair1[0].length() - wordPair2[0].length() !=0)
                return wordPair1[0].length() - wordPair2[0].length();

            //-------------------Does the same thing, but for the second words in the word pairs----------------------//

            //If o1's second word is longer, iterate through o2's second word and compare it to o1
            if(wordPair1[1].length() > wordPair2[1].length())
            {
                //Iterates through o2's second word and compares it alphabetiically to o1's second word
                for (int j = 0; j < wordPair2[1].length(); j++)
                {
                    if(wordPair1[1].charAt(j) - wordPair2[1].charAt(j) != 0)
                        return wordPair1[1].charAt(j) - wordPair2[1].charAt(j);
                }
            }
            //Otherwise, loop through o1 and compare it to o2
            else
            {
                //Iterates through o1's second word and compares it alphabetiically to o2's second word
                for (int j = 0; j < wordPair1[1].length(); j++)
                {
                    if(wordPair1[1].charAt(j) - wordPair2[1].charAt(j) != 0)
                        return wordPair1[1].charAt(j) - wordPair2[1].charAt(j);
                }
            }
            //If the keys are alphabetically identical up to one of their lengths, return the shorter key
            if(wordPair1[1].length() - wordPair2[1].length() !=0)
                return wordPair1[1].length() - wordPair2[1].length();
        }

        //If they values are not the same, compare by value
        return o2.getValue() - o1.getValue();
    };

    /**
     * Computes the statistics from the file name.
     */
    public WordStat(String fileName) throws FileNotFoundException
    {
        //Stores the unique words and word pairs that occur in the given source
        ArrayList<String> uniqueWordList = new ArrayList<>();
        ArrayList<String> uniqueWordPairList = new ArrayList<>();

        tokenizer = new Tokenizer(fileName);
        //Goes through each word in the given source
        //Creates an array of all the unique words and word pairs
        //Counts the number of occurrences of each unique word
        for (int i = 0; i < tokenizer.wordList().size(); i++)
        {
            wordCounts.update(tokenizer.wordList().get(i), wordCounts.get(tokenizer.wordList().get(i)) + 1);
            if(wordCounts.get(tokenizer.wordList().get(i)) == 0)
                uniqueWordList.add(tokenizer.wordList().get(i));
            if(i < tokenizer.wordList().size() - 1)
            {
                wordPairCounts.update(tokenizer.wordList().get(i) + " " + tokenizer.wordList().get(i + 1), wordPairCounts.get(tokenizer.wordList().get(i) + " " + tokenizer.wordList().get(i + 1)) + 1);
                if(wordPairCounts.get(tokenizer.wordList().get(i) + " " + tokenizer.wordList().get((i + 1))) == 0)
                    uniqueWordPairList.add(tokenizer.wordList().get(i) + " " + tokenizer.wordList().get(i + 1));
            }
        }
//-------------------------------Creates unique word arrays----------------------------------------//
        //Stores the wordCounts of each unique word in the given source
        rankedWordList = new HashEntry[uniqueWordList.size()];
        rankedWordPairList = new HashEntry[uniqueWordPairList.size()];

        //Takes the final word count of each unique word in the source from the Hash Table
        //And stores it in an array
        for (int i = 0; i < uniqueWordList.size(); i++)
        {
            rankedWordList[i] = new HashEntry(uniqueWordList.get(i), wordCounts.get(uniqueWordList.get(i)));
        }

        //Takes the final word-pair count of each unique word-pair in the source from the Hash Table
        //And stores it in an array
        for (int i = 0; i < uniqueWordPairList.size(); i++)
        {
            rankedWordPairList[i] = new HashEntry(uniqueWordPairList.get(i), wordPairCounts.get(uniqueWordPairList.get(i)));
        }

//--------------------------------Sorting-----------------------------------------------------//
        
        //Sorts the array from greatest wordCount to smallest wordCount
        Arrays.sort(rankedWordList, alphaNumeric);
        
        //Sorts the array from greatest wordPairCount to smallest wordPairCount
        Arrays.sort(rankedWordPairList, alphaNumericPairs);

//-----------------------------Hashes Ranks----------------------------------------------------//
        //Counts the wordRank for every unique word
        for (int i = 0; i < uniqueWordList.size(); i++)
        {
            //Goes through through the array, finding the wordCount corresponding to the unique word
            //The first occurrence in the array, at 'j', indicates the word's rank
            for(int j = 0; j < rankedWordList.length ; j++)
            {
                //Looks for first occurrence of the unique word's wordCount in the array
                if(wordRank.get(uniqueWordList.get(i)) == -1 && rankedWordList[j].getValue() == wordCounts.get(uniqueWordList.get(i)))
                    wordRank.put(uniqueWordList.get(i), j);
            }
        }

        //Counts the wordPairRank for every unique word-pair
        for (int i = 0; i < uniqueWordPairList.size(); i++)
        {
            //Goes through through the array, finding the wordPairCount corresponding to each unique word-pair
            //The first occurrence in the array, at 'j', indicates the word pair's rank
            for (int j = 0; j < rankedWordPairList.length; j++)
            {
                //Looks for first occurrence of the unique word pair's wordPairCount in the array
                if (wordPairRank.get(uniqueWordPairList.get(i)) == -1 && rankedWordPairList[j].getValue() == wordPairCounts.get(uniqueWordPairList.get(i)))
                    wordPairRank.put(uniqueWordPairList.get(i), j);
            }
        }
    }

    /**
     * Computes the statistics from the given String.
     */
    public WordStat(String[] string)
    {
        //Stores the unique words and word pairs that occur in the given source
        ArrayList<String> uniqueWordList = new ArrayList<>();
        ArrayList<String> uniqueWordPairList = new ArrayList<>();

        tokenizer = new Tokenizer(string);
        //Goes through each word in the given source
        //Creates an array of all the unique words and word pairs
        //Counts the number of occurrences of each unique word
        for (int i = 0; i < tokenizer.wordList().size(); i++)
        {
            wordCounts.update(tokenizer.wordList().get(i), wordCounts.get(tokenizer.wordList().get(i)) + 1);
            if(wordCounts.get(tokenizer.wordList().get(i)) == 0)
                uniqueWordList.add(tokenizer.wordList().get(i));
            if(i < tokenizer.wordList().size() - 1)
            {
                wordPairCounts.update(tokenizer.wordList().get(i) + " " + tokenizer.wordList().get(i + 1), wordPairCounts.get(tokenizer.wordList().get(i) + " " + tokenizer.wordList().get(i + 1)) + 1);
                if(wordPairCounts.get(tokenizer.wordList().get(i) + " " + tokenizer.wordList().get((i + 1))) == 0)
                    uniqueWordPairList.add(tokenizer.wordList().get(i) + " " + tokenizer.wordList().get(i + 1));
            }
        }
//-------------------------------Creates unique word arrays----------------------------------------//
        //Stores the wordCounts of each unique word in the given source
        rankedWordList = new HashEntry[uniqueWordList.size()];
        rankedWordPairList = new HashEntry[uniqueWordPairList.size()];

        //Takes the final word count of each unique word in the source from the Hash Table
        //And stores it in an array
        for (int i = 0; i < uniqueWordList.size(); i++)
        {
            rankedWordList[i] = new HashEntry(uniqueWordList.get(i), wordCounts.get(uniqueWordList.get(i)));
        }

        //Takes the final word-pair count of each unique word-pair in the source from the Hash Table
        //And stores it in an array
        for (int i = 0; i < uniqueWordPairList.size(); i++)
        {
            rankedWordPairList[i] = new HashEntry(uniqueWordPairList.get(i), wordPairCounts.get(uniqueWordPairList.get(i)));
        }

//--------------------------------Sorting-----------------------------------------------------//

        //Sorts the array from greatest wordCount to smallest wordCount
        Arrays.sort(rankedWordList, alphaNumeric);

        //Sorts the array from greatest wordPairCount to smallest wordPairCount
        Arrays.sort(rankedWordPairList, alphaNumericPairs);

//-----------------------------Hashes Ranks----------------------------------------------------//
        //Counts the wordRank for every unique word
        for (int i = 0; i < uniqueWordList.size(); i++)
        {
            //Goes through through the array, finding the wordCount corresponding to the unique word
            //The first occurrence in the array, at 'j', indicates the word's rank
            for(int j = 0; j < rankedWordList.length ; j++)
            {
                //Looks for first occurrence of the unique word's wordCount in the array
                if(wordRank.get(uniqueWordList.get(i)) == -1 && rankedWordList[j].getValue() == wordCounts.get(uniqueWordList.get(i)))
                    wordRank.put(uniqueWordList.get(i), j);
            }
        }

        //Counts the wordPairRank for every unique word-pair
        for (int i = 0; i < uniqueWordPairList.size(); i++)
        {
            //Goes through through the array, finding the wordPairCount corresponding to each unique word-pair
            //The first occurrence in the array, at 'j', indicates the word pair's rank
            for (int j = 0; j < rankedWordPairList.length; j++)
            {
                //Looks for first occurrence of the unique word pair's wordPairCount in the array
                if (wordPairRank.get(uniqueWordPairList.get(i)) == -1 && rankedWordPairList[j].getValue() == wordPairCounts.get(uniqueWordPairList.get(i)))
                    wordPairRank.put(uniqueWordPairList.get(i), j);
            }
        }
    }

    /**
     * Returns the number of times the given word appears
     */
    public int wordCount(String word)
    {
        return wordCounts.get(word.replaceAll("\\W+|_", "").toLowerCase()) + 1;
    }

    /**
     * Returns the number of times the given word pair appears
     */
    public int wordPairCount(String w1, String w2)
    {
        return wordPairCounts.get(w1.replaceAll("\\W+|_", "").toLowerCase() + " " + w2.replaceAll("\\W+|_", "").toLowerCase()) + 1;
    }

    /**
     * Returns the rank of the given word
     */
    public int wordRank(String word)
    {
        return wordRank.get(word.replaceAll("\\W+|_", "").toLowerCase()) + 1;
    }

    /**
     * Returns the rank of the given word pair
     */
    public int wordPairRank(String w1, String w2)
    {
        return wordPairRank.get(w1.replaceAll("\\W+|_", "").toLowerCase() + " " + w2.replaceAll("\\W+|_", "").toLowerCase()) + 1;
    }

    /**
     * Returns a String array of the 'k' most common words
     */
    public String[] mostCommonWords(int k)
    {
        //If k is larger than existing elements, only existing elements are read
        if(rankedWordList.length < k)
            k = rankedWordList.length;

        String[] array = new String[k];

        //Stores the number of existing or requested elements in the array
        //More common words are at the start of rankedWordList
        for (int i = 0; i < k; i++)
            array[i] = rankedWordList[i].getKey();

        return array;
    }

    /**
     * Returns a String array of the 'k' least common words
     */
    public String[] leastCommonWords(int k)
    {
        //Returns an empty string if the list is empty
        //Avoids index of out bounds exception in the loop
        if(rankedWordList.length == 0)
            return new String[]{};

        //If k is larger than existing elements, only existing elements are read
        if(rankedWordList.length < k)
            k = rankedWordList.length;

        //Represents the string to be returned
        String[] array = new String[k];

        //Stores the number of existing or requested elements in the array
        //Less common words are at the end of rankedWordList
        int i = 0;
        int j = rankedWordList.length - 1;
        int n = j - 1;

        //This loop appends the least common words to an array, in alphabetic order
        //Only iterates the loop again if the array has not been completed
        while(i < k)
        {
            //Since the list is sorted both alphabetically and by value
            //The list must be appended from left to right for sections of the same value
            //This loop finds the next border of elements with the same value
            while (n > -1 && rankedWordList[n].getValue() == rankedWordList[j].getValue())
            {
                n--;
            }

            //This loop appends the subsection of the array with the same value to the array to be returned
            for (int m = n + 1; m <= j; m++)
            {
                //Only appends the subsection as long as its smaller than k
                if (i < k)
                {
                    array[i] = rankedWordList[m].getKey();
                    i++;
                }
            }
            //Moves the two markers for the border down
            j = n--;
        }
        return array;
    }

    /**
     * Returns a String array of the 'k' most common word pairs
     */
    public String[] mostCommonWordPairs(int k)
    {
        //If k is larger than existing elements, only existing elements are read
        if(rankedWordPairList.length < k)
            k = rankedWordPairList.length;

        //Represents the string array to be returned
        String[] array = new String[k];

        //Stores the number of existing or requested elements in the array
        //More common word pairs are at the start of rankedWordList
        for (int i = 0; i < k; i++)
            array[i] = rankedWordPairList[i].getKey();

        return array;
    }

    /**
     * Returns a String array of the 'k' least common word pairs - NOVEL METHOD
     */
    public String[] leastCommonWordPairs(int k)
    {
        //Returns an empty string if the list is empty
        //Avoids index of out bounds exception in the loop
        if(rankedWordPairList.length == 0)
            return new String[]{};

        //If k is larger than existing elements, only existing elements are read
        if(rankedWordPairList.length < k)
            k = rankedWordPairList.length;

        //Represents the string to be returned
        String[] array = new String[k];

        //Stores the number of existing or requested elements in the array
        //Less common words are at the end of rankedWordPairList
        int i = 0;
        int j = rankedWordPairList.length - 1;
        int n = j - 1;

        //This loop appends the least common word pairs to an array, in alphabetic order
        //Only iterates the loop again if the array has not been completed
        while(i < k)
        {
            //Since the list is sorted both alphabetically and by value
            //The list must be appended from left to right for sections of the same value
            //This loop finds the next border of elements with the same value
            while (n > -1 && rankedWordPairList[n].getValue() == rankedWordPairList[j].getValue())
            {
                n--;
            }

            //This loop appends the subsection of the array with the same value to the array to be returned
            for (int m = n + 1; m <= j; m++)
            {
                //Only appends the subsection as long as its smaller than k
                if (i < k)
                {
                    array[i] = rankedWordPairList[m].getKey();
                    i++;
                }
            }
            //Moves the two markers for the border down
            j = n--;
        }
        return array;
    }

    /**
     * Returns the k most common words with given relative position, 'i', to the given base word
     */
    public String[] mostCommonCollocs(int k, String baseWord,  int i)
    {
        //Normalizes the given baseWord
        baseWord = baseWord.replaceAll("\\W+|_", "").toLowerCase();
        //Represents the unique words that occur after or before the baseWord
        ArrayList<String> restOfWords = new ArrayList<>();
        //Represents the counts of the unique words that occur before or after the baseWord
        HashEntry[] restOfWordsCounts;
        //Represents the most common words proceeding/preceding baseWord
        HashTable collocTable = new HashTable();

        //Represents the indice of the baseWord
        int bsWdIndice = 0;

            //Iterates through the list of unique words until baseWord is found
            while (bsWdIndice < tokenizer.wordList().size() && !tokenizer.wordList().get(bsWdIndice).equals(baseWord))

                bsWdIndice++;
        //If baseword does not exist, return an empty array
        if(bsWdIndice == tokenizer.wordList().size())
        return new String[]{};
//---------------------------------Hashing words & Creating unique word list------------------------------------------//
        if(i > 0)
        {
            //Counts the frequency of each word occurence after the baseWord
            for (int n = bsWdIndice + 1; n < tokenizer.wordList().size(); n++)
            {
                collocTable.update(tokenizer.wordList().get(n), collocTable.get(tokenizer.wordList().get(n)) + 1);
                //Creates a list of unique words that proceed/precede the baseWord
                if(collocTable.get(tokenizer.wordList().get(n)) == 0)
                    restOfWords.add(tokenizer.wordList().get(n));
            }
        }
        else if(i < 0)
        {
            //Counts the frequency of each word occurence before the baseWord
            for (int n = 0; n < bsWdIndice; n++)
            {
                collocTable.update(tokenizer.wordList().get(n), collocTable.get(tokenizer.wordList().get(n)) + 1);
                //Creates a list of unique words that proceed/precede the baseWord
                if(collocTable.get(tokenizer.wordList().get(n)) == 0)
                    restOfWords.add(tokenizer.wordList().get(n));
            }
        }
//--------------------------------------------------------------------------------------------------------------------//
        //Assigns restOfWordsCounts a hash entry of each unique word before/after baseword, with their frequency
        restOfWordsCounts = new HashEntry[restOfWords.size()];
        for (int n = 0; n < restOfWordsCounts.length; n++)
            restOfWordsCounts[n] = new HashEntry(restOfWords.get(n), collocTable.get(restOfWords.get(n)));

//--------------Sorts the array of the word's wordCounts from largest to smallest & alphabetically--------------------//

        Arrays.sort(restOfWordsCounts, alphaNumeric);

//----------------------------------------Array to be returned-------------------------------------------------------//
        //Determines the required size of the array to be returned
        if(k > restOfWordsCounts.length)
            k = restOfWordsCounts.length;

        //Creates the string array of most common collocations
        String[] mostCommon = new String[k];
        for(int n = 0; n < k; n++)
        {
                mostCommon[n] = restOfWordsCounts[n].getKey();
        }
        return mostCommon;
    }

    /**
     * Returns a string composed of the given word followed by k most common words
     */
    public String generateWordString(int k, String startWord)
    {
        //Normalizes the given startWord
        startWord = startWord.replaceAll("\\W+|_", "").toLowerCase();
        //Represents the unique words that occur after or before the startWord
        ArrayList<String> restOfWords = new ArrayList<>();
        //Represents the most common words proceeding/preceding startWord
        HashTable collocTable = new HashTable();
        //Represents the indice of the startWord
        int stWdIndice = 0;
        //Creates a String builder to build the string to be returned
        StringBuilder builder = new StringBuilder(startWord);
        //Represents the largest item found iterating through the text after the startWord
        HashEntry largestItem;

        //As long as there are more words required in the string, continue looking for the next largest word occurence
        while(k > 0)
        {
            //Rebuilds the hashTable for another iteration and clears the arraylist
            collocTable = new HashTable();
            restOfWords.clear();

            //Iterates through the list of unique words until startWord is found
            while (stWdIndice < tokenizer.wordList().size() && !tokenizer.wordList().get(stWdIndice).equals(startWord))
                stWdIndice++;

            //If word does not exist or is at the end of the text, there are no more words after 'startWord'
            if (stWdIndice >= tokenizer.wordList().size() - 1)
                return builder.toString();

            //Counts the frequency of each word occurence after the baseWord
            for (int n = ++stWdIndice; n < tokenizer.wordList().size(); n++)
            {
                collocTable.update(tokenizer.wordList().get(n), collocTable.get(tokenizer.wordList().get(n)) + 1);
                //Creates a list of unique words that proceed/precede the baseWord
                if (collocTable.get(tokenizer.wordList().get(n)) == 0)
                    restOfWords.add(tokenizer.wordList().get(n));
            }

            largestItem = new HashEntry(restOfWords.get(0), collocTable.get(restOfWords.get(0)));

            for (int n = 0; n < restOfWords.size(); n++)
            {
                if (largestItem.getValue() < collocTable.get(restOfWords.get(n)))
                    largestItem = new HashEntry(restOfWords.get(n), collocTable.get(restOfWords.get(n)));
            }

            builder.append(" ");
            builder.append(largestItem.getKey());
            startWord = largestItem.getKey();
            //Decrements k as another word is added to the final string to be returned
            k--;
        }

        return builder.toString();
    }

    /**
     *  Returns the k most common words with given relative position, 'i', to the given base word, excluding word in exclusions
     */
    public String[] mostCommonCollocsExc(int k, String baseWord, int i, String[] exclusions)
    {
        //Normalizes the given baseWord
        baseWord = baseWord.replaceAll("\\W+|_", "").toLowerCase();
        //Represents the unique words that occur after or before the baseWord
        ArrayList<String> restOfWords = new ArrayList<>();
        //Represents the counts of the unique words that occur before or after the baseWord
        HashEntry[] restOfWordsCounts;
        //Represents the most common words proceeding/preceding baseWord
        HashTable collocTable = new HashTable();
        //Represents the indice of the baseWord
        int bsWdIndice = 0;
        //Represents whether or not a word is an element of 'exclusions'
        boolean excluded = false;

        //Iterates through the list of unique words until baseWord is found
        while (bsWdIndice < tokenizer.wordList().size() && !tokenizer.wordList().get(bsWdIndice).equals(baseWord))
            bsWdIndice++;
        //If baseword does not exist, return an empty array
        if(bsWdIndice == tokenizer.wordList().size())
            return new String[]{};
//---------------------------------Hashing words & Creating unique word list------------------------------------------//
        if(i > 0)
        {
            //Counts the frequency of each word occurence after the baseWord
            for (int n = bsWdIndice + 1; n < tokenizer.wordList().size(); n++)
            {
                collocTable.update(tokenizer.wordList().get(n), collocTable.get(tokenizer.wordList().get(n)) + 1);
                //Creates a list of unique words that proceed/precede the baseWord, exluding exlusions
                if (collocTable.get(tokenizer.wordList().get(n)) == 0)
                {
                    excluded = false;
                    //Iterates through exclusions seeing if the given word is excluded
                    for (int j = 0; j < exclusions.length && !excluded; j++)
                    {
                        if(tokenizer.wordList().get(n).equals(exclusions[j]))
                            excluded = true;
                    }
                    //Adds the word to the unique word list of collocations if it is not excluded
                    if(!excluded)
                        restOfWords.add(tokenizer.wordList().get(n));
                }
            }
        }
        else if(i < 0)
        {
            //Counts the frequency of each word occurence before the baseWord
            for (int n = 0; n < bsWdIndice; n++)
            {
                collocTable.update(tokenizer.wordList().get(n), collocTable.get(tokenizer.wordList().get(n)) + 1);
                //Creates a list of unique words that proceed/precede the baseWord
                if (collocTable.get(tokenizer.wordList().get(n)) == 0)
                {
                    excluded = false;
                    //Iterates through exclusions seeing if the given word is excluded
                    for (int j = 0; j < exclusions.length && !excluded; j++)
                    {
                        if(tokenizer.wordList().get(n).equals(exclusions[j]))
                            excluded = true;
                    }
                    //Adds the word to the unique word list of collocations if it is not excluded
                    if(!excluded)
                        restOfWords.add(tokenizer.wordList().get(n));
                }
            }
        }
//--------------------------------------------------------------------------------------------------------------------//
        //Assigns restOfWordsCounts a hash entry of each unique word before/after baseword, with their frequency, exclusions accounted for
        restOfWordsCounts = new HashEntry[restOfWords.size()];
        for (int n = 0; n < restOfWordsCounts.length; n++)
            restOfWordsCounts[n] = new HashEntry(restOfWords.get(n), collocTable.get(restOfWords.get(n)));

//--------------Sorts the array of the word's wordCounts from largest to smallest & alphabetically--------------------//

        Arrays.sort(restOfWordsCounts, alphaNumeric);

//----------------------------------------Array to be returned-------------------------------------------------------//
        //Determines the required size of the array to be returned
        if(k > restOfWordsCounts.length)
            k = restOfWordsCounts.length;

        //Creates the string array of most common collocations
        String[] mostCommon = new String[k];
        for(int n = 0; n < k; n++)
        {
            mostCommon[n] = restOfWordsCounts[n].getKey();
        }
        return mostCommon;

    }
    public static void main(String[] args)
    {
        WordStat wordStat = new WordStat(new String[]{"Hi, Phil Swift here with Flex Tape! The super-strong waterproof tape! " +
                "That can instantly patch, bond, seal, and repair! Flex tape is no ordinary tape; its triple thick adhesive virtually welds itself to the surface, " +
                "instantly stopping the toughest leaks. Leaky pipes can cause major damage, but Flex Tape grips on tight and bonds instantly! " +
                "Plus, Flex Tape’s powerful adhesive is so strong, it even works underwater! Now you can repair leaks in pools and spas in water without draining them! " +
                "Flex Tape is perfect for marine, campers and RVs! Flex Tape is super strong, and once it's on, it holds on tight! " +
                "And for emergency auto repair, Flex Tape keeps its grip, even in the toughest conditions! " +
                "Big storms can cause big damage, but Flex Tape comes super wide, so you can easily patch large holes. " +
                "To show the power of Flex Tape, I sawed this boat in half! And repaired it with only Flex Tape! " +
                "Not only does Flex Tape’s powerful adhesive hold the boat together, but it creates a super strong water tight seal, so the inside is completly dry! Yee-doggy! " +
                "Just cut, peel, stick and seal! Imagine everything you can do with the power of Flex Tape!"});

        System.out.println("The number of times Phil says 'damage': " + wordStat.wordCount("damage"));
        System.out.println("The number of time Phil says 'Flex Seal': " + wordStat.wordPairCount("Flex", "Tape"));
        System.out.println("The rank of 'damage' and 'Flex Seal' respectively: " + wordStat.wordRank("damage") + " :: " + wordStat.wordPairRank("Flex", "Tape"));

        System.out.println("Here we can see the 5 most common word pairs: " + Arrays.toString(wordStat.mostCommonWordPairs(5)));
        System.out.println("And here we can see the 5 least common word pairs: " + Arrays.toString(wordStat.leastCommonWordPairs(5)));

        System.out.println("Here we can see the 5 most common words: " + Arrays.toString(wordStat.mostCommonWords(5)));
        System.out.println("And here we can see the 5 least common words: " + Arrays.toString(wordStat.leastCommonWords(5)));

        System.out.println("Here's the 5 most common words after Phil says power: " + Arrays.toString(wordStat.mostCommonCollocs(5, "power", 1)));
        System.out.println("And heres the 5 most common words before he says power: " + Arrays.toString(wordStat.mostCommonCollocs(5, "power", -1)));

        System.out.println("Here are the 5 most common collocations not including when Phil says 'Flex Tape', after he says power: " + Arrays.toString(wordStat.mostCommonCollocsExc(5, "power", 1, new String[]{"Flex", "Tape"})));
        System.out.println("Here are the 3 most common words after each most common word after Phil says power: " + wordStat.generateWordString(3, "power"));
    }
}

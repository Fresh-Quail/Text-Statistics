import java.io.File;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Tokenizer {
    //Stores the list of words read from a given file
    private ArrayList<String> wordList = new ArrayList<>();

    /**
     * Reads the given file into an array of normalized words
     */
    public Tokenizer(String fileName) throws FileNotFoundException {
        Scanner sc = new Scanner(new File(fileName));

        String[] strArray;
        //Goes through every line in the given text file
        while(sc.hasNextLine())
        {
            //Normalizes the next line in the file, only appending word characters
            strArray = sc.nextLine().replaceAll("'", "").toLowerCase().split("\\W+|_");

            //Takes the normalized words and adds it to the wordList
            for (int i = 0; i < strArray.length; i++)
            {
                if (!strArray[i].equals(""))
                    wordList.add(strArray[i]);
            }
        }
    }

    /**
     * Reads the given string array into an array of normalized words
     */
    public Tokenizer(String[] str)
    {
        String[] string;
        for(int i = 0; i < str.length; i++)
        {
            //Normalizes the element of the array into an array of normalized words
            string = str[i].replaceAll("'", "").toLowerCase().split("\\W+|_");

            //Takes the normalized words and adds it to the wordList
            for (int j = 0; j < string.length; j++)
            {
                if (!string[j].equals(""))
                    wordList.add(string[j]);
            }
        }
    }

    public ArrayList<String> wordList()
    {
        return wordList;
    }
}

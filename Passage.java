/**
 * The <code>Passage</code> is a hash table which maps a String (word) to an 
 * Integer value (occurences of that word). This will represent a single text 
 * file and all of its words’ occurrences.
 *
 * @author Brian Chau
 *    email brian.chau@stonybrook.edu
 *    Stony Brook ID: 116125954
 *    Recitation: 02
 **/
import java.util.HashSet;
import java.util.Hashtable;
import java.io.File;
import java.util.Scanner;

public class Passage extends Hashtable<String, Integer>{
    private String title;
    private int wordCount;
    private Hashtable<String, Double> similarTitle;
    private static HashSet<String> stopWords;

    /**
     * Construtor which sets the title of the passage
     * and calls the parseFile() method
     * 
     * @param title
     *    The title of the passage
     * @param file
     *    The file to be read by parseFile
     */
    public Passage(String title, File file){
        this.title = title;
        if(stopWords == null){
            generateStopWords();
        }
        parseFile(file);
    }

    /**
     * Reads the given text file and counts each word’s occurrence, 
     * excluding stop words, and inserts them into the table
     * 
     * @param file
     *    The file to be parsed
     */
    public void parseFile(File file){
        try{
            Scanner reader = new Scanner(file);
            while(reader.hasNext()){
                String nextWord = reader.next();
                nextWord.replace("[^a-x]", "");
                if(nextWord.equals("")){
                    continue;
                }

            }
            reader.close();
        } catch (Exception e) {
            System.out.println("File not found.");
        }
    }

    private void generateStopWords(){
        File stopWordsFile = new File("./StopWords.txt");
        try{
            Scanner reader = new Scanner(stopWordsFile);
            while(reader.hasNextLine()){
                String stopWord = reader.nextLine();
                stopWords.add(stopWord);
            } 
            reader.close();
        } catch(Exception e){
            System.out.println("Can't find the stop words. Please put in stop words file");
            e.printStackTrace();
        }
    }
}
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
import java.util.Set;
import java.io.File;
import java.io.FileNotFoundException;
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
                wordCount++;
                nextWord.replace("[^a-zA-Z\\d]", "");
                if(!nextWord.equals("")){
                    put(nextWord, getOrDefault(nextWord, 0) + 1);
                }
                
            }
            reader.close();
        } catch (Exception e) {
            System.out.println("File not found.");
            e.printStackTrace();
        }
    }

    /**
     * Returns the relative frequency of a given word in the passage
     * 
     * @param word
     *    The word to be checked
     * @return
     *    The relative frequency of the word
     */
    public double getWordFrequency(String word){
        double ans = ((double) getOrDefault(word, 0))/wordCount;
        return ans;
    }

    /**
     * Returns the alls the words contained in the passage
     * 
     * @return
     *    A set of all words in the passage
     */
    public Set<String> getWords(){
        return keySet();
    }

    /**
     * Returns the title of the passage
     * 
     * @return
     *    The title of the passage
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title of the passage 
     * 
     * @param title
     *    The new title of the passage
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Returns the word count of the passage
     * 
     * @return
     *    The word count of the passage
     */
    public int getWordCount() {
        return wordCount;
    }

    /**
     * Sets the word count of the passage 
     * 
     * @param wordCount
     *    The new word count of the passage
     */
    public void setWordCount(int wordCount) {
        this.wordCount = wordCount;
    }

    /**
     * Returns all similar titles
     * 
     * @return
     *    The hashmap of similar titles
     */
    public Hashtable<String, Double> getSimilarTitle() {
        return similarTitle;
    }

    /**
     * Sets the similar title hashtable 
     * 
     * @param similarTitle
     *    The new hashtable of similar titles
     */
    public void setSimilarTitle(Hashtable<String, Double> similarTitle) {
        this.similarTitle = similarTitle;
    }

    /**
     * Returns string of similar titles and their percentage similarity
     * 
     * @return
     *    The string of similar titles and their precentage similarity
     */
    public String toString(){
        String repString = "";
        for(String title : similarTitle.keySet()){
            repString += title + "(" + ((int) (similarTitle.get(title) * 100)) + "%), ";
        }
        return repString;
    }

    public static double cosineSimilarity(Passage p1, Passage p2){
        double ans = 0;
        double norm1 = 0;
        double norm2 = 0;
        double wordCount1 = p1.getWordCount();
        double wordCount2 = p2.getWordCount();
        for(String word: p1.keySet()){
            double freq1 = p1.get(word)/wordCount1;
            norm1 += Math.pow(freq1, 2);
            if(p2.containsKey(word)){
                ans += freq1 * p2.get(word)/wordCount2;
            }
        }
        norm1 = Math.sqrt(norm1);
        for(String word: p2.keySet()){
            norm2 += Math.pow(p2.get(word)/wordCount2, 2);
        }
        norm2 = Math.sqrt(norm2);
        ans /= (norm1 * norm2);
        return ans;
    }

    private void generateStopWords(){
        File stopWordsFile = new File("./StopWords.txt");
        stopWords = new HashSet<String>();
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

    public static void main(String[] args){
        File passage1 = new File("./passageFolder/CallOfCthulhu.txt");
        File passage2 = new File("./passageFolder/Frankenstein.txt");
        System.out.println(passage1);
        Passage p1 = new Passage("CallOfCthulu", passage1);
        Passage p2 = new Passage("Frankenstein", passage2);
        System.out.println(cosineSimilarity(p1, p2));
    }
}
/**
 * The <code>FrequencyList</code>
 *
 * @author Brian Chau
 *    email brian.chau@stonybrook.edu
 *    Stony Brook ID: 116125954
 *    Recitation: 02
 **/
import java.util.Hashtable;
import java.util.ArrayList;

public class FrequencyList{
    String word;
    ArrayList<Double> frequencies;
    Hashtable<String, Integer> passageIndices;
    public FrequencyList(String word, ArrayList<Passage> passages){
        this.word = word;
        for(Passage p: passages){
            addPassage(p);
        }
    }

    /**
     * Adds a frequency from a passage to the list 
     * 
     * @param p
     *    The passge to add
     */
    public void addPassage(Passage p){
        passageIndices.put(p.getTitle(), frequencies.size());
        frequencies.add(p.getWordFrequency(word));
    }

    /**
     * Returns the frequency of the word in a given passage
     * 
     * @param p
     *    The passage from which to get the frquency from
     * @return
     *    The frequency of the word in passage p
     */
    public double getFrequency(Passage p){
        return frequencies.get(passageIndices.get(p.getTitle()));
    }
}
/**
 * The <code>FrequencyTable</code> contain a Collection of FrequencyLists and a static method which
 * builds each FrequencyList from a list of Passage objects, as well as a method to access the
 * frequency of a word in a given Passage
 *
 * @author Brian Chau
 *    email brian.chau@stonybrook.edu
 *    Stony Brook ID: 116125954
 *    Recitation: 02
 **/
import java.util.ArrayList;
import java.util.HashSet;

public class FrequencyTable extends ArrayList<FrequencyList>{
    /**
     * Iterates through passages and constructs FrequencyLists
     * with each Passage’s appropriate word frequencies
     *
     * <dt>Postcondition
     *    <dd>A new FrequencyTable object has been constructed,
     *        containing a Collection of FrequencyLists with information
     *        from all Passages</dd>
     *
     * @param passages
     *    A collection containing the Passage objects to be parsed through
     * @return
     *    The FrequencyTable constructed from each Passage in passages.
     */
    public static FrequencyTable buildTable(ArrayList<Passage> passages){
        FrequencyTable table = new FrequencyTable();
        HashSet<String> seen = new HashSet<String>();
        for(Passage p: passages){
            for(String word: p.getWords()){
                if(!seen.contains(word)){
                    seen.add(word);
                    FrequencyList newList = new FrequencyList(word, passages);
                    table.add(newList);
                }
            }
        }
        return table;
    }

    /**
     * Adds a Passage into the FrequencyTable and updates the FrequencyLists accordingly
     *
     * <dt>Precondition
     *    <dd>The Passage p is neither null nor empty.</dd>
     *
     * <dt>Postcondtiion
     *    <dd>p’s values for each of its keys have been appropriately
     *        mapped into each FrequencyList in the table.</dd>
     *
     * @param p
     *    The Passage being iterated over and integrated into the table.
     * @throws IllegalArgumentException
     *    If the given Passage is null or empty.
     */
    public void addPassage(Passage p) throws IllegalArgumentException{
        if(p == null){
            throw new IllegalArgumentException("Passage is null");
        }
        for(String word: p.getWords()){
            if(contains(word)){
                get(indexOf(word)).addPassage(p);
            }
            else{
                FrequencyList newList = new FrequencyList(word, p);
            }
        }
    }

    /**
     * Returns the frequency of the given word in the given Passage
     *
     * @param word
     *    The word which the method will use to look for the frequency
     * @param p
     *    The passage which the frequency will be from
     * @return
     *    The frequency of the given word in the given Passage
     */
    public double getFrequency(String word, Passage p){
        if(!contains(word)){
            return 0;
        }
        return get(indexOf(word)).getFrequency(p);
    }
}
import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Scanner;
import java.io.File;
import java.util.Arrays;
/**
 * The <code>TextAnalyzer</code>
 *
 * @author Brian Chau
 *    email brian.chau@stonybrook.edu
 *    Stony Brook ID: 116125954
 *    Recitation: 02
 **/
public class TextAnalyzer{
    private static FrequencyTable frequencyTable;
    public static void main(String[] args){
        Scanner input = new Scanner(System.in);
        double percentage = getSimilarityPercentage(input);
        ArrayList<Passage> passages = new ArrayList<>();
        System.out.print("Enter the directory of a folder of text files: ");
        String directory = input.nextLine();
        System.out.println("Reading texts...");
        File[] directoryOfFiles = new File(directory).listFiles();
        for(File i: directoryOfFiles){
            Passage newPassage = new Passage(i.getName().replace(".txt", ""), i);
            for(Passage p: passages){
                Passage.cosineSimilarity(newPassage, p);
            }
            passages.add(newPassage);
        }
        String title = String.format("%-25s| Similarities (%s)", "Text (title)", "%");
        System.out.println(title);
        printDivider();
        for(Passage p: passages){
            printSimilarities(p);
            printDivider();
        }
        for(int i = 0; i < passages.size(); i++){
            Passage p = passages.get(i);
            Hashtable<String, Double> similarities = p.getSimilarTitle();
            Object[] potential = similarities.keySet().toArray();
            Arrays.sort(potential);
            for(int j = i; j < potential.length; j++){
                String check =(String) potential[j];
                if(similarities.get(check) > percentage){
                    printSimilar(p.getTitle(), check, ((int) Math.round(similarities.get(check) * 100)));
                }
            }
        }
    }

    private static double getSimilarityPercentage(Scanner input){
        System.out.print("Enter the similarity percentage: ");
        String percentageString = input.nextLine();
        try{
            double percentage = Double.parseDouble(percentageString);
            if(percentage > 1 || percentage < 0){
                System.out.println("The percentage is not valid.");
                getSimilarityPercentage(input);
            }
            return percentage;
        } catch(Exception e) {
            System.out.println("Not a number. Please try again.");
            return getSimilarityPercentage(input);
        }
    }

    private static void printDivider(){
        String line = "_".repeat(80);
        System.out.println(line);
    }

    private static void printSimilarities(Passage p){
        String[] brokenUp = breakUp(p.toString(), 54);
        String firstLine = String.format("%-25s|%-54s", p.getTitle(), brokenUp[0]);
        System.out.println(firstLine);
        while(brokenUp[1] != null){
            brokenUp = breakUp(brokenUp[1], 54);
            String nextLine = String.format("%26s%-54s", "|", brokenUp[0]);
            System.out.println(nextLine);
        }
    }

    private static void printSimilar(String title1, String title2, int percentage){
        String line = String.format("'%s' and '%s' may have the same author (%d", title1, title2, percentage);
        line += "% similar).";
        System.out.println(line);
    }

    private static String[] breakUp(String line, int length){
        String[] portions = new String[2];
        String firstPortion = "";
        String word = "";
        int delimiter = line.indexOf(", ");
        if(line.length() > length && delimiter != -1){
            word = line.substring(0, delimiter + 1);
            while(delimiter != -1 && word.length() < length){
                firstPortion += word;
                line = line.substring(delimiter + 2);
                length -= word.length();
                delimiter = line.indexOf(", ");
                word = line.substring(0, delimiter + 2);
            }
            if(delimiter == -1){
                portions[0] = firstPortion + line;
                portions[1] = null;
                return portions;
            }
            portions[0] = firstPortion;
            portions[1] = line;
        }
        else{
            portions[0] = line;
            portions[1] = null;
        }
        return portions;
    }
}
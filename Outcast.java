import java.io.IOException;
import edu.princeton.cs.algs4.*;;

public class Outcast {
    WordNet wordnet; // constructor takes in a word net object

    public Outcast(WordNet wordnet) {
        // initialize
        this.wordnet = wordnet;
    }

    // given an array of WordNet nouns, return an outcast
    public String outcast(String[] nouns) {
        int outcast_id = 0;
        int max = 0;

        int len = nouns.length;
        int[] values = new int[len];

        for (int i = 0; i < len; i++) {
            for (int j = 0; j < len; j++) {
                values[i] += wordnet.distance(nouns[i], nouns[j]);
                
            }

            if (values[i] > max) {
                max = values[i];
                outcast_id = i;
            }
        }

        //for (int k = 0; k < len - 1; k++)
        return nouns[outcast_id];
    }

    // Unit Test client
    public static void main(String[] args) throws IOException { // throw because WordNet throws
        WordNet wordnet = new WordNet(args[0], args[1]);
        Outcast outcast = new Outcast(wordnet);
        for (int t = 2; t < args.length; t++) {
            In in = new In(args[t]);
            String[] nouns = in.readAllStrings();
            StdOut.println(args[t] + ": " + outcast.outcast(nouns));
        }
    }
}
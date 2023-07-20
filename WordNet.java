import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import edu.princeton.cs.algs4.*;

public class WordNet {

     // Hashie the hash table contains word keys with a bag list of id values
     private LinearProbingHashST<String, Bag<Integer>> hashie = new LinearProbingHashST<>();

     // Henry the hash table contains id keys with related words
     private LinearProbingHashST<Integer, Bag<String>> henry = new LinearProbingHashST<>();

     // Instance of shortest common ancestor
     ShortestCommonAncestor sappie;

     public WordNet(String synsets, String hypernyms) throws IOException /* "throw" required for FileReader */ {

          if (synsets == null || hypernyms == null)
               throw new IllegalArgumentException("Missing input file(s)");

          // Read in all synsets (and do something with them)
          BufferedReader input = new BufferedReader(new FileReader(synsets));
          String line = input.readLine();

          int line_count = 0;
          while (line != null) {
               line_count += 1;
               String parts[] = line.split(",");
               int synId = Integer.parseInt(parts[0]);
               String synStr = parts[1];
               String[] synset = synStr.split(" ");

               for (int i = 0; i < synset.length; i++) {

                    // fills hashie with key=word, value=IDs
                    if (hashie.contains(synset[i])) {
                         hashie.get(synset[i]).add(synId);
                    } else {
                         hashie.put(synset[i], new Bag<>());
                         hashie.get(synset[i]).add(synId);
                    }

                    
                    // fills henry with key=id, value=words
                    if (henry.contains(synId)) {
                         henry.get(synId).add(synset[i]);
                    } else {
                         henry.put(synId, new Bag<>());
                         henry.get(synId).add(synset[i]);
                    }

               }

               // Read next line from file
               line = input.readLine();
          } input.close();

          // initialize a digraph to contain all the hypernet ids
          Digraph graph = new Digraph(line_count);

          // instance of shortest common ancestor
          sappie = new ShortestCommonAncestor(graph);

          // Read in all hypernyms with some similar code
          BufferedReader inputTwo = new BufferedReader(new FileReader(hypernyms));
          String lineTwo = inputTwo.readLine();

          while (lineTwo != null) {
               String parts[] = lineTwo.split(",");
               int hynId = Integer.parseInt(parts[0]);
               
               for (int i = 1; i < parts.length; i++) {
                    int id = Integer.parseInt(parts[i]);
                    graph.addEdge(hynId, id);
               }

               // Read next line from file and ..
               lineTwo = inputTwo.readLine();
          } inputTwo.close();
     }

     // all WordNet nouns
     public Iterable<String> nouns() {
          return hashie.keys();
     }

     // is the word a WordNet noun?
     public boolean isNoun(String word) {
          if (hashie.contains(word))
               return true;
          return false;
     }

     // a synset (second field of synsets.txt) that is a shortest common ancestor
     // of noun1 and noun2 (defined below)
     public String sca(String noun1, String noun2) {
          if (!isNoun(noun1) || !isNoun(noun2)) {
               throw new IllegalArgumentException("One or both nouns are not in set.");
          }
          
          Bag<Integer> ids1 = hashie.get(noun1);
          Bag<Integer> ids2 = hashie.get(noun2);
          int v = sappie.ancestor(ids1, ids2);

          String result = "";
          Bag<String> synset = henry.get(v);
          for (String string : synset) {
               result = result.concat(string + " ");
          }
          
          return result;
     }

     // distance between noun1 and noun2 (defined below)
     public int distance(String noun1, String noun2) {
          if (!isNoun(noun1) || !isNoun(noun2))
               throw new IllegalArgumentException("One or both nouns are not in set.");
          
          Bag<Integer> ids2 = hashie.get(noun2);
          Bag<Integer> ids1 = hashie.get(noun1);
          
          return sappie.length(ids2, ids1);
     }

     // do unit testing of this class
     public static void main(String[] args) throws IOException { // "throw" because the constructor throws.
          WordNet wnet = new WordNet("synsets.txt", "hypernyms.txt");
          
          // how to test
          StdOut.println(wnet.isNoun("AIDS"));
          // StdOut.println(wnet.sca("", "bird"));
          StdOut.println(wnet.distance("Ambrose", "entity"));


     }
}
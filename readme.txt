/******************************************************************************
 *  Name: Aspen Morgan
 *
 *  Hours to complete assignment (optional): 15 hours
 *
 ******************************************************************************/

Programming Assignment 3: WordNet


/******************************************************************************
 *  Describe concisely the data structure(s) you used to store the 
 *  information in synsets.txt. Why did you make this choice?
 *****************************************************************************/

I stored the information in synsets.txt in two different hash tables.
The first table (hashie) saves a word for the key and holds the words synids
in a bag in the value. The second table (henry) saves a synid as a key and holds the 
corresponding synset ids in a bag in the value.

I first implemented hashie. It was useful to plug in a word and get ids because of
functions like distance(String noun1, String noun2) in WordNet. Since digraphs can 
only take in integer values for the vertices, the digraph is made up of synid values. 
Inputing a word, getting its ids, then plugging those into the digraph is perfect in 
combination with shortest common ancestor.

The second table, henry, became useful in the sca(String noun1, String noun2) 
function. The output needs to be the string of synsets, so a hash table
connecting id keys to synset string values made sense.

/******************************************************************************
 *  Describe concisely the data structure(s) you used to store the 
 *  information in hypernyms.txt. Why did you make this choice?
 *****************************************************************************/

I stored the information in hypernyms.txt directly in a digraph. The hypernyms
file is made up of synids and it can be used to connected the synids in a 
meaningful way.

/******************************************************************************
 *  Describe concisely your algorithm to compute the shortest common
 *  ancestor in ShortestCommonAncestor. For each method in the API, what
 *  is the order of growth of the worst-case running time as a function
 *  of the number of vertices V and the number of edges E in the digraph?
 *  For each method, what is the order of growth of the best-case running time?
 *
 *  If you use hashing, you may assume the uniform hashing assumption
 *  so that put() and get() take constant time.
 *
 *  Be careful! If you use something like a BreadthFirstDirectedPaths object, 
 *  don't forget to count the time needed to initialize the marked[],
 *  edgeTo[], and distTo[] arrays.
 *****************************************************************************/

Description:

                                              running time
method                               best case            worst case
------------------------------------------------------------------------
length(int v, int w)                 ~ V + E              ~ V + E

ancestor(int v, int w)               ~ V + E              ~ V + E

length(Iterable<Integer> v,          ~ V*(V + E)          ~ V^2(V + E)                             
       Iterable<Integer> w)

ancestor(Iterable<Integer> v,        ~ V*(V + E)          ~ V^2(V + E)
         Iterable<Integer> w)


NOTE: V* represents a small subset of the graph so its essentially linear time.

Worst case on complex length and ancestor assume subsets each make up opposite
halves of the entire graph.

/******************************************************************************
 *  Known bugs / limitations.
 *****************************************************************************/

I think the time complexity of my shortest common ancestor class is outside of 
the performance requirements.

/******************************************************************************
 *  Describe whatever help (if any) that you received.
 *  Don't include readings, lectures, but do include any 
 *  help from people (including course staff, TAs,
 *  classmates, and friends) and attribute them by name.
 *****************************************************************************/

I discussed the assignment with Jayce Holdsambeck and Andy Wolf.
Lindsay also gave me the pointer of making a hash table with key = word and
value = synids.

/******************************************************************************
 *  Describe any serious problems you encountered.                    
 *****************************************************************************/


/******************************************************************************
 *  List any other comments here. Feel free to provide any feedback   
 *  on how much you learned from doing the assignment, and whether    
 *  you enjoyed doing it.                                             
 *****************************************************************************/

This was a tough lab but it was the most eye-opening. It was pretty fun
at times, too.
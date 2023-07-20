import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.*;;

public class ShortestCommonAncestor {
    
    // Make digraph accessible to all functions
    private Digraph graph;

    
    // constructor takes a rooted DAG as argument
    public ShortestCommonAncestor(Digraph G) {
        if (G == null)
            throw new NullPointerException("Problem with digraph input.");

        this.graph = G;
        DirectedCycle testing = new DirectedCycle(graph);
        if (testing.hasCycle()) {
            throw new IllegalArgumentException("Input digraph is not a DAG");
        }
    }

    // length of shortest ancestral path between v and w
    public int length(int v, int w) {
        // error/corner case handling
        if (v < 0 || w < 0) {
            throw new IndexOutOfBoundsException("Problem with vertex inputs.");
        } else if (v == w) {
            return 0;
        }
        
        int ann = ancestor(v, w);
        int dist1 = new UserBFS(graph, v).distTo(ann);
        int dist2 = new UserBFS(graph, w).distTo(ann);

        // if (dist1 < 0) dist1 = 0;
        // if (dist2 < 0) dist2 = 0;

        return dist1 + dist2;
    }

    // a shortest common ancestor of vertices v and w
    public int ancestor(int v, int w) {
        // error/corner case handling
        if (v < 0 || w < 0) {
            throw new IndexOutOfBoundsException("Problem with vertex inputs.");
        } else if (v == w) {
            return v;
        }

        // Run BFS on vertex1 and vertex2
        UserBFS one = new UserBFS(graph, v);
        UserBFS two = new UserBFS(graph, w);

        int min = Integer.MAX_VALUE;
        int ancestor = 0;
        
        for (int i = 0; i < graph.V(); i++) {
            int dist1 = one.distTo(i);
            int dist2 = two.distTo(i);
            int d = dist1 + dist2;

            if (one.hasPathTo(i) && two.hasPathTo(i) && d < min) {
                min = d;
                ancestor = i;
            }
        }

        return ancestor;
    }

    // length of shortest ancestral path of vertex subsets A and B
    public int length(Iterable<Integer> subsetA, Iterable<Integer> subsetB) {
        if (subsetA == null || subsetB == null) {
            throw new IllegalArgumentException("Problem with vertex input.");
        } 

        int annie = ancestor(subsetA, subsetB);
        int min1 = Integer.MAX_VALUE;
        int min2 = Integer.MAX_VALUE;

        for (int i : subsetA) {
            int dist1 = new UserBFS(graph, i).distTo(annie);
            if (dist1 < min1 && dist1 >= 0) min1 = dist1;
        }
        for (int j : subsetB) {
            int dist2 = new UserBFS(graph, j).distTo(annie);   
            if (dist2 < min2 && dist2 >= 0) min2 = dist2; 
        }

        // Output shortest length of all pairs
        return min1 + min2;
    }

    // a shortest common ancestor of vertex subsets A and B
    public int ancestor(Iterable<Integer> subsetA, Iterable<Integer> subsetB) {
        // Output shortest common ancestor of subsetA[i] with subsetB[i]
        if (subsetA == null || subsetB == null) {
            throw new IllegalArgumentException("Problem with vertex input.");
        } 

        for (int i : subsetA) {
            for (int j : subsetB) {
                if (i == j) {
                    return i;
                }
            }
        }

        int min = Integer.MAX_VALUE;
        int anna = 0;
        for (int i : subsetA) {
            for (int j: subsetB) {
                
                int dist = length(i, j);
                
                if (dist < min && dist >= 0) {
                    anna = ancestor(i, j);
                    min = dist;
                }
            }
        }
        
        return anna;
    }

    // do unit testing of this class
    public static void main(String[] args) {

        // Build unit tests
        if (args.length < 1) {
            manualUnitTest();
        } else {
            In in = new In(args[0]);
            Digraph G = new Digraph(in);
            ShortestCommonAncestor sca = new ShortestCommonAncestor(G);
            while (!StdIn.isEmpty()) {
                int v = StdIn.readInt();
                int w = StdIn.readInt();
                int length = sca.length(v, w);
                int ancestor = sca.ancestor(v, w);  
                StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
            }
        }
    }

    // Unit test made by me
    public static void manualUnitTest() {
        // Basic tree test
        int numVertices = 12;// or whatever
        Digraph d1 = new Digraph(numVertices);
        d1.addEdge(6, 3); // add a bunch of these, to form some tree-like shape, e.g.:
        d1.addEdge(7, 3);
        d1.addEdge(3, 1);
        d1.addEdge(4, 1);
        d1.addEdge(5, 1);
        d1.addEdge(8, 5);
        d1.addEdge(9,5);
        d1.addEdge(10,9);
        d1.addEdge(11,9);
        d1.addEdge(1,0);
        d1.addEdge(2,0);
        /*
         *  
         *    
         *   
         *  
         * 
         */

        ShortestCommonAncestor sca = new ShortestCommonAncestor(d1);
        int x = 3; 
        int y = 1; 
        int w = 8; 
        int z = 11; 
        int a = 6;
        int b = 2;

        StdOut.println("Testing Case: 1");
        StdOut.println("length: " + sca.length(x, y));
        StdOut.println("ancestor: " + sca.ancestor(x, y));
        StdOut.println();
        StdOut.println("Testing Case: 2");
        StdOut.println("length: " + sca.length(w, z));
        StdOut.println("ancestor: " + sca.ancestor(w, z));
        StdOut.println();
        StdOut.println("Testing Case: 3");
        StdOut.println("length: " + sca.length(a, b));
        StdOut.println("ancestor: " + sca.ancestor(a, b));
        StdOut.println();

        // testing sets with some iterable type
        // ({1,2},{3,4})
        Bag<Integer> b1 = new Bag<Integer>();
        Bag<Integer> b2 = new Bag<Integer>();

        b1.add(x);
        b1.add(y);
        b2.add(w);
        b2.add(z);

        StdOut.println("Testing Case: 5");
        StdOut.println("length: " + sca.length(b1, b2));
        StdOut.println("ancestor: " + sca.ancestor(b1, b2));
    }
}
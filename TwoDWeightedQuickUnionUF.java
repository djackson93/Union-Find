/******************************************************************************
 * Compilation:  javac TwoDWeightedQuickUnionUF.java
 * ---------------------------------------------------------------------------
 * This class is a slight variant from the "WeightedQuickUnionUF.java" given 
 * from the book, "Algorithms - fourth edition" written by Robert Sedgewick and
 * Kevin Wayne, on page 228. Not only does it add in path compression, but it
 * adds in the method id() for displaying the id[] array and largestComp() for
 * displaying the largest component of a certain element.
 * 
 * Author: @Dakota Jackson
 * ****************************************************************************
 */
import java.util.Arrays;

public class TwoDWeightedQuickUnionUF {
    
    private int [] id;   //Parent link (site indexed)
    private int [] sz;   //Size of the component of roots (site indexed)
    private int count;   //number of components
    
    
public TwoDWeightedQuickUnionUF(int n) {
    
    //count starts out as n as each node is its own component
    count = n;
    id = new int[n];
    //Array to keep track of the sizes of each component
    // - Starts at 1 for each initialized node 
    sz = new int[n]; 

    for (int i = 0; i < n; i++) {
        id[i] = i;    
        sz[i] = 1;
    }
}
    
 public int getCount() {
     return count;
}
 
 public void id() {
     
    StdOut.println(Arrays.toString(id));
 }
 
 /* Returns the largest size of the largest component(forest)
  *  - Scans through indexes of sz[] to find the largest int value that
  *    represents the largest component
  * 
  * @return int value for the largest component
  */
 public int largestComp() {
     
     int largest = 0;
  
     for (int i = 0; i < sz.length; i++) {
         if (sz[i] > largest) {
          largest = sz[i];   
         }
     }
     return largest;
 }
 
 public boolean connected(int p, int q) {
     return find(p) == find(q);
 }
 
 /* Returns the root node of id[p]
  * 
  * @param an integer value p representing index p of id[]
  * @return int value p representing the root node of index entered 
  */
 public int find(int p) {
     /* while the id[p] != p, it is not the root,
      * so change p to the id[p] an check again 
      * (thus going up the chain towards root)
      */
     while(p != id[p]) {
         /* this line was added for path compression
          *  -While we are checking the id[p] and it is not the root,
          *   we can go ahead and point the id[p] to the id of the id of p
          *   (the root)
          */
         id[p] = id[id[p]]; 
         p = id[p];
     }
     return p;
 }
 
 /* Unionizes 2 components if they are not already unionized 
  * 
  * @param an int p and int q representing two indexes of id[]
  */
 public void union(int p, int q) {

     int i = find(p);
     int j = find(q);
  
     
     if(i == j) return;  //if they are equal, do nothing
     
        
     //Makes the smaller root point to the larger one for algorithm optimization 
     if (sz[i] < sz[j]) {
         id[i] = j;
         sz[j] += sz[i]; //sz now represents merged together
     } else {
         id[j] = i;
         sz[i] += sz[j];
     }
     //decrement count as two components combining into 1 meand total is now 1 less
     count --;
 }
} 
 
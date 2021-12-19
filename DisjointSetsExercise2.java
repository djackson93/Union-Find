
/******************************************************************************
 * Compilation:  javac DisjointSetsExercise2.java
 * Dependencies: TwoDWeightedQuickUnionUF.java
 * ----------------------------------------------------------------------------
 * Exercise: Describe and analyze an algorithm to compute the size of the 
 * largest connected component of black pixels in an n × n bitmap 
 * B[1 .. n, 1 .. n]. 
 * ----------------------------------------------------------------------------
 * 
 * Program takes in an integer value 'n' input from user and creates an
 * n x n bitmap (and draws it on screen for visual representation). The program 
 * uses a 1-dimensional array of n * n length and uses arithmetic to make 
 * conversions to fit and check where an element [x][y] in a 2D array would be
 * in a 1D array.
 * 
 * 
 * Author: @Dakota Jackson
 * ****************************************************************************
 */
import java.util.*;
import edu.princeton.cs.algs4.*;

public class DisjointSetsExercise2 {
    
    /* Fills the bitmap elements with the color they will be (randomly assigned)
     *  with the key int value 2 = black, 1 = white (changed from default values)
     *  to help test bugs.
     * 
     * @param sizeOfBM to indicate how big the array will be (sizeOfBM^2)
     * @return an array called bitmap with it's elements assigned black or white 
     */
    public static int[] createBitmap(int sizeOfBM) {
      
        int bitmap[] = new int[sizeOfBM * sizeOfBM];
        
        for (int i = 0; i < sizeOfBM; i++) {
            for(int j = 0; j < sizeOfBM; j++) {
                double rand = Math.random();
                if (rand <= 0.4) {
                    //insert black square
                    bitmap[(i * sizeOfBM) + j] = 2; 
                } else {
                    //Insert white square
                    bitmap[(i * sizeOfBM) + j] = 1;
                }
        }
    }
        return bitmap;
    }
    
    /* Takes in the created bitmap of ints representing colors and draws them on screen
     *  - the sizes of the squares are determined by how many squares will be displayed
     *  - the X and Y scales are also dynamic based on amount of squares needed displayed
     * 
     *@param Array of type int  
     */
    public static void drawBitmap (int bitmap[]) {
        
       int sizeOfBM = (int)Math.sqrt(bitmap.length);

       double szOfSquare =  (720/sizeOfBM) ;
       StdDraw.setCanvasSize(720, 720);
        
       StdDraw.setXscale(0 - (szOfSquare /2), 720 + (szOfSquare /2));
       StdDraw.setYscale(0 - (szOfSquare /2), 720 + (szOfSquare /2));
           
      for (int i = 0; i < sizeOfBM; i++) {
            for(int j = 0; j < sizeOfBM; j++) {
                if (bitmap[(i * sizeOfBM) + j] == 2) {
                    //Draw the black square
                    //StdOut.println(bitmap[(i * sizeOfBM) + j]);
                     StdDraw.filledSquare((j * szOfSquare) + szOfSquare/2, (i * szOfSquare) + szOfSquare/2, szOfSquare/2);
                     
                } else if (bitmap[(i * sizeOfBM) + j] == 1) {
                    //Draw the white square
                   //StdOut.println(bitmap[(i * sizeOfBM) + j]);
                    StdDraw.square((j * szOfSquare) + szOfSquare/2, (i * szOfSquare) + szOfSquare/2, szOfSquare/2);
                    
                } else {
                     //for error testing
                     StdDraw.setPenColor(StdDraw.RED); 
                     StdDraw.filledSquare((i * szOfSquare) + (szOfSquare /2),(j * szOfSquare) + (szOfSquare /2) , szOfSquare);     
                }
        }
    }
  }

    /* Runs through the bitmap checking East and North neighbors to see if they are
     * a black square as well. If they are, unionizes them. 
     *  - besides official return, also outputs the id[] array to show roots
     *  - and outputs the total # of components
     *
     *@param an int array [] representing the bitmap
     *@return an integer value representing the largest component of black squares
     */
    public static int largestComp(int bitmap[]) {
        
        TwoDWeightedQuickUnionUF uf = new TwoDWeightedQuickUnionUF(bitmap.length);
        int sizeOfBM = (int)Math.sqrt(bitmap.length);
        
        //start at (0,0) and check neighbors color
        for (int i = 0; i < sizeOfBM; i++) {
            for(int j = 0; j < sizeOfBM; j++) {
                //if index is black,check if neighbors are also black, union them
                if(bitmap[(i * sizeOfBM) + j] == 2) {
                    
                  //Check Right Neighbor (if not on farthest right side)
                  if ((((i * sizeOfBM) + j) % sizeOfBM) != sizeOfBM - 1) {
                   if ((i * sizeOfBM) + (j +  1) < bitmap.length) {
                    if(bitmap[(i * sizeOfBM) + (j +  1)] == 2) {
                         uf.union((i * sizeOfBM) + j, (i * sizeOfBM) + (j +  1));
                         /* Used this line for testing; took out because of cludder on output
                          * StdOut.println("Unionized: (" + ((i * sizeOfBM) + j) + "," + ((i * sizeOfBM) + (j +  1)) + ")");
                          */
                         
                    }
                  }
                 
                  } //Check Neighbor above
                  if(((i + 1) * sizeOfBM) + j < bitmap.length) {
                   if(bitmap[((i + 1) * sizeOfBM) + j] == 2) {
                    uf.union((i * sizeOfBM) + j, ((i + 1) * sizeOfBM) + j);
                    /* Used this line for testing; took out because of cludder on output
                     * StdOut.println("Unionized: (" + ((i * sizeOfBM) + j) + "," + (((i + 1) * sizeOfBM) + j) + ")");
                     */
                    
                        }
                    }
                }
            }
        }
        
        uf.id();
        StdOut.println("\nThe amount of components there are total are " + 
                       uf.getCount() + ". (white squares count as 1 alone)");
        return uf.largestComp();
        
    }
    
    //Flag[x] used for bug testing             
    public static void main(String [] args) { 
    
        int sizeOfBM;
        
        //takes in a user inputted integer for the n x n size of the bitmap
        StdOut.println("Please enter an integer value 'n' between 1-100 for the size of the n x n bitmap: ");
        sizeOfBM = StdIn.readInt();
        
        //creates a 2D Array (as our bitmap) using the user inputted dimensions dimensions
        int bitmap [] = createBitmap(sizeOfBM);
        
        //StdOut.println("Flag 1");
        
        //Need to draw the bitmap on screen
        drawBitmap(bitmap);
        //StdOut.println(Arrays.toString(bitmap));
        
        //StdOut.println("Flag 2");        
        
        //Find the largest connected compoment of black pixels in the bitmap using union-find
        StdOut.println("\nThe largest component of black squares is " + largestComp(bitmap) + ".");
        
        //StdOut.println("Flag 3"); 
         
         
      
        
    }
}
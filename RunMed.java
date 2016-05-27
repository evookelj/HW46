/*****************************************************
 * class RunMed
 * Implements an online algorithm to track the median of a growing dataset
 * Maintains 2 heaps: minheap for upper half of dataset, maxheap for lower half
 * Median will always be one of these:
 *  - max of left heap  (lower range)
 *  - min of right heap (upper range)
 *  - mean of heap roots
 *****************************************************/

public class RunMed {

    //instance vars
    private ALMaxHeap leftHeap;  //for lower range of dataset
    private ALMinHeap rightHeap; //for upper range of dataset
    private int sizeL;
    private int sizeR;

    /*****************************************************
     * default constructor  ---  inits empty heap
     *****************************************************/
    public RunMed() { 
	leftHeap = new ALMaxHeap();
	rightHeap = new ALMinHeap();
    }//O(1)

    /*****************************************************
     * double getMedian()  ---  returns median of dataset
     *****************************************************/
    public double getMedian() {
	if ((sizeR+sizeL)%2 == 1) {
	    if (sizeR > sizeL) { return rightHeap.peekMin(); }
	    return leftHeap.peekMax();
	} else {
            if (isEmpty()) {
                // No sensible numeric value can be reported
                return Double.NaN;
            }
	    return (rightHeap.peekMin()+leftHeap.peekMax())/2.0;
	}
    }//O(1)

    /*****************************************************
     * insert(int)  ---  adds a new element to the dataset
     * postcondition: dataset is maintained such that 
     *                getMedian() can run in constant time
     *****************************************************/
    public void insert( int addVal ) {
        if (leftHeap.isEmpty() || addVal <= leftHeap.peekMax()) {
            leftHeap.add(addVal);
            sizeL++;
        } else {
            rightHeap.add(addVal);
            sizeR++;
        }
	if (sizeR>sizeL) {
	    while (sizeR-sizeL > 1) {
                leftHeap.add(rightHeap.removeMin());
                sizeR--;
                sizeL++;
            }
	} else {
	    while (sizeL - sizeR > 1) {
                rightHeap.add(leftHeap.removeMax());
                sizeL--;
                sizeR++;
            }
	}

    }//O(?)

    /*****************************************************
     * boolean isEmpty()  ---  tells whether a median may be calculated
     * postcondition: dataset structure unchanged
     *****************************************************/
    public boolean isEmpty() { return (leftHeap.isEmpty()
				       && rightHeap.isEmpty());
    }//O(1)

    //main method for testing
    public static void main( String[] args ) {


        RunMed med = new RunMed();
        med.insert(1);
	System.out.println( med.getMedian() ); //1
        med.insert(3);
	System.out.println( med.getMedian() ); //2
        med.insert(5);
	System.out.println( med.getMedian() ); //3
        med.insert(7);
	System.out.println( med.getMedian() ); //4
        System.out.println("left: " + med.leftHeap + "\nright:" + med.rightHeap + "\n");
        med.insert(9);
	System.out.println( med.getMedian() ); //5
        System.out.println("left: " + med.leftHeap + "\nright:" + med.rightHeap + "\n");
        /*~~~V~~~~~~~~~~~~move~me~down~~~~~~~~~~~~~V~~~
	~~~~~|~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~|~~~*/
    }//end main()

}//end class RunMed

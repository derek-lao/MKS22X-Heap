import java.util.*;
import java.lang.*;

public class MyHeap{
  private static int nthRow(int index){
    return (int)(Math.log(index + 2) / Math.log(2));
  }
  private static boolean isInRow(int row, int index){
    if(row == nthRow(index)) return true;
    else return false;
  }
  private static int getLeftIndex(int index){
    return index * 2 + 1;
  }
  private static int getRightIndex(int index){
    return index * 2 + 2;
  }
  private static int getParentIndex(int index){
    return (index - 1) / 2;
  }
  private static int leafThreshold(int size){
    // if 15 elements total, your limit would be 8, and if index is less than or equal to 6, we're good,
    return (int)(Math.pow(2 , (int)(Math.log(size) / Math.log(2))) - 2);
  }
  // - size  is the number of elements in the data array.
  // - push the element at index i downward into the correct position. This will swap with the larger of the child nodes provided that child is larger. This stops when a leaf is reached, or neither child is larger. [ should be O(logn) ]
  // - precondition: index is between 0 and size-1 inclusive
  // - precondition: size is between 1 and data.length inclusive.
  private static void pushDown(int[] data,int size,int index){
    // System.out.println("pushDown activated!");
    // System.out.println("Size is " + size + " Index is " + index);
    int currentIndex = index;
    int leftIndex = getLeftIndex(currentIndex);
    int rightIndex = getRightIndex(currentIndex); // make sure both are changing when the currentIndex is changed
    int threshold = leafThreshold(size);
    if(size > 1)
    {
      // System.out.println("made it past 1");
      while((leftIndex < size && data[currentIndex] < data[leftIndex] ||
        rightIndex < size && data[currentIndex] < data[rightIndex]) && currentIndex <= threshold)
      {
        // System.out.println("made it past 2");
        int holder = 0;
        if(getRightIndex(currentIndex) < size && data[getLeftIndex(currentIndex)] <= data[getRightIndex(currentIndex)])
        {//swap right
          // System.out.println("made it past 3");
          holder = data[getRightIndex(currentIndex)];
          data[getRightIndex(currentIndex)] = data[currentIndex];
          data[currentIndex] = holder;
          currentIndex = getRightIndex(currentIndex);
          leftIndex = getLeftIndex(currentIndex);
          rightIndex = getRightIndex(currentIndex);
        }
        else
        {//swap left
          // System.out.println("made it past 4");
          holder = data[getLeftIndex(currentIndex)];
          data[getLeftIndex(currentIndex)] = data[currentIndex];
          data[currentIndex] = holder;
          currentIndex = getLeftIndex(currentIndex);
          leftIndex = getLeftIndex(currentIndex);
          rightIndex = getRightIndex(currentIndex);
        }
      }
    }
  }

  // - push the element at index i up into the correct position. This will swap it with the parent node until the parent node is larger or the root is reached. [ should be O(logn) ]
  // - precondition: index is between 0 and data.length-1 inclusive.
  private static void pushUp(int[] data,int index){
    int holder = 0;
    int currentIndex = index;
    int parentIndex = getParentIndex(currentIndex);
    while(currentIndex != 0 && data[parentIndex] < data[currentIndex])
    {
      holder = data[parentIndex];
      data[parentIndex] = data[currentIndex];
      data[currentIndex] = holder;
      currentIndex = getParentIndex(currentIndex);
      parentIndex = getParentIndex(currentIndex);
    }
  }


  // - convert the array into a valid heap. [ should be O(n) ]
  public static void heapify(int[] data){
    for(int i = data.length - 1 ; i > -1 ; i --)
    {
      pushDown(data,data.length,i);
      // System.out.println(i + " pushDown has been called, here is the result");
      // System.out.println(Arrays.toString(data));
      // System.out.println(toString4Rows(data));
    }
  }

  // - sort the array by converting it into a heap then removing the largest value n-1 times. [ should be O(nlogn) ]
  public static void heapsort(int[] data){
    // System.out.println("Began sort, just to make sure heapify works, here is the data before heapify in heapSort");
    // System.out.println(Arrays.toString(data));
    // System.out.println(toString4Rows(data));
    heapify(data);
    // System.out.println("It has been heapified within the heapsort, here is the final result");
    // System.out.println(Arrays.toString(data));
    // System.out.println(toString4Rows(data));
    for(int i = 0 ; i < data.length - 1 ; i ++)
    {
      // System.out.println(i + " My current array and heap before movement is");
      // System.out.println(Arrays.toString(data));
      // System.out.println(toString4Rows(data));
      int holder = data[data.length - 1 - i];
      data[data.length - 1 - i] = data[0];
      data[0] = holder;
      // System.out.println(i + " Just swapped, have not pushDown yet");
      // System.out.println(Arrays.toString(data));
      // System.out.println(toString4Rows(data));
      pushDown(data,data.length - 1 - i,0);
      // System.out.println(i + " My current array and heap after the pushDown");
      // System.out.println(Arrays.toString(data));
      // System.out.println(toString4Rows(data));
      // System.out.println("\n\n\n");
    }
  }


  private static String toString4Rows(int[] data){//only prints 4 level arrays, and only takes 2 digit numbers
    String thing = "";
    String space = "  ";
    for(int i = 0 ; i < 7 ; i ++)
    {
      thing += space;
    }
    thing += data[0] + "\n";
    for(int i = 0 ; data.length < 1 || i < 3 ; i ++)
    {
      thing += space;
    }
    thing += data[1];
    for(int i = 0 ; data.length < 2 || i < 7 ; i ++)
    {
      thing += space;
    }
    thing += data[2] + "\n";
    for(int i = 0 ; data.length < 3 + i || i < 4 ; i ++)
    {
      String block = space + data[3 + i] + space;
      thing += block + space;
    }
    thing += "\n";
    for(int i = 0 ; data.length < 7 + i || i < 8 ; i ++)
    {
      String block = data[7 + i] + space;
      thing += block;
    }
    return thing;
  }
  public static void main(String[] args){
    int[] data = new int[15];
    for(int i = 0 ; i < 15 ; i ++)
    {
      data[i] = (int) ((Math.random() * 1000000) % 100);
    }
    // int[] data = {8, 42, 93, 77, 48, 83, 49, 48, 96, 93, 85, 17, 86, 85, 98};
    System.out.println("Here is the original data");
    System.out.println(Arrays.toString(data));
    System.out.println(toString4Rows(data));
    // System.out.println("Push down 10 below");
    // pushDown(data,data.length,0);
    // System.out.println(toString4Rows(data));
    // System.out.println("Push up 80 below");
    // pushUp(data,11);
    // System.out.println(toString4Rows(data));
    // System.out.println("I am going to heapify this array now");
    // heapify(data);
    // System.out.println("It has been heapified, here is the final result");
    // System.out.println(Arrays.toString(data));
    // System.out.println(toString4Rows(data));
    System.out.println("I am going to sort this array now");
    heapsort(data);
    System.out.println("sorted, the printed array should be sorted, the printed heap should be broken");
    System.out.println(Arrays.toString(data));
    System.out.println(toString4Rows(data));
  }

  // public static void main(String[] args){
  //   System.out.println("Size\t\tMax Value\theap/builtin ratio ");
  //   int[]MAX_LIST = {1000000000,500,10};
  //   for(int MAX : MAX_LIST){
  //     for(int size = 31250; size < 2000001; size*=2){
  //       long qtime=0;
  //       long btime=0;
  //       //average of 5 sorts.
  //       for(int trial = 0 ; trial <=5; trial++){
  //         int []data1 = new int[size];
  //         int []data2 = new int[size];
  //         for(int i = 0; i < data1.length; i++){
  //           data1[i] = (int)(Math.random()*MAX);
  //           data2[i] = data1[i];
  //         }
  //         long t1,t2;
  //         t1 = System.currentTimeMillis();
  //         MyHeap.heapsort(data2);
  //         t2 = System.currentTimeMillis();
  //         qtime += t2 - t1;
  //         t1 = System.currentTimeMillis();
  //         Arrays.sort(data1);
  //         t2 = System.currentTimeMillis();
  //         btime+= t2 - t1;
  //         if(!Arrays.equals(data1,data2)){
  //           System.out.println("FAIL TO SORT!");
  //           System.exit(0);
  //         }
  //       }
  //       System.out.println(size +"\t\t"+MAX+"\t"+1.0*qtime/btime);
  //       // System.out.println("Numerator is " + qtime);
  //       // System.out.println("Denominator is " + btime);
  //     }
  //     System.out.println();
  //   }
  // }
}

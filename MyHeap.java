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
  private static boolean isLeaf(int size,int index){
    // if 15 elements total, your limit would be 8, and if index is less than or equal to 6, we're good,
    if(index > Math.pow(2 , (int)(Math.log(size) / Math.log(2))) - 2)
    return true;
    else
    return false;
  }
  // - size  is the number of elements in the data array.
  // - push the element at index i downward into the correct position. This will swap with the larger of the child nodes provided that child is larger. This stops when a leaf is reached, or neither child is larger. [ should be O(logn) ]
  // - precondition: index is between 0 and size-1 inclusive
  // - precondition: size is between 1 and data.length inclusive.
  private static void pushDown(int[] data,int size,int index){
    if(!isLeaf(size,index))
    {
      if(data[index] < data[getLeftIndex(index)] || data[index] < data[getRightIndex(index)])
      {
        int holder = 0;
        if(data[getLeftIndex(index)] > data[getRightIndex(index)])
        {
          holder = data[getLeftIndex(index)];
          data[getLeftIndex(index)] = data[index];
          data[index] = holder;
          pushDown(data,size,getLeftIndex(index));
        }
        else
        {
          holder = data[getRightIndex(index)];
          data[getRightIndex(index)] = data[index];
          data[index] = holder;
          pushDown(data,size,getRightIndex(index));
        }
      }
    }
  }

  // - push the element at index i up into the correct position. This will swap it with the parent node until the parent node is larger or the root is reached. [ should be O(logn) ]
  // - precondition: index is between 0 and data.length-1 inclusive.
  private static void pushUp(int[] data,int index){
    int holder = 0;
    if(index != 0 && data[getParentIndex(index)] < data[index])
    {
      holder = data[getParentIndex(index)];
      data[getParentIndex(index)] = data[index];
      data[index] = holder;
      pushUp(data,getParentIndex(index));
    }
  }


  // - convert the array into a valid heap. [ should be O(n) ]
  public static void heapify(int[] data){}

  // - sort the array by converting it into a heap then removing the largest value n-1 times. [ should be O(nlogn) ]
  public static void heapsort(int[] data){}


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
    thing += data[2];
    for(int i = 0 ; data.length < 3 + i || i < 4 ; i ++)
    {
      String block = space + data[3 + i] + space;
      thing += block;
    }
    for(int i = 0 ; data.length < 7 + i || i < 8 ; i ++)
    {
      String block = data[7 + i] + space;
      thing += block;
    }
    return thing;
  }
  public static void main(String[] args){
    int[] data = new int[16];
    for(int i = 0 ; i < 16 ; i ++)
    {
      data[i] = (int) ((Math.random() * 1000000) % 100);
    }
    System.out.println(Arrays.toString(data));
    System.out.println(toString4Rows(data));
  }

}

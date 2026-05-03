package structures;

import java.util.Comparator;

public class MyMergeSort {

    
    public static <T> void sort(T[] array, Comparator<T> comparator) {
        if (array == null || array.length <= 1) {
            return; // noting to sort
        }
        
       
        @SuppressWarnings("unchecked")
        T[] temp = (T[]) new Object[array.length];
        
        // starting recurrsion
        mergeSort(array, temp, 0, array.length - 1, comparator);
    }

    private static <T> void mergeSort(T[] array, T[] temp, int left, int right, Comparator<T> comparator) {
        if (left < right) {
            int mid = left + (right - left) / 2;
            
            // sorting left half
            mergeSort(array, temp, left, mid, comparator);
            // sorting right half
            mergeSort(array, temp, mid + 1, right, comparator);
            // integrating left part and right part
            merge(array, temp, left, mid, right, comparator);
        }
    }

    private static <T> void merge(T[] array, T[] temp, int left, int mid, int right, Comparator<T> comparator) {
        for (int i = left; i <= right; i++) {
            temp[i] = array[i];
        }

        int i = left;  // indexing left array
        int j = mid + 1; 
        int k = left;  //indexing right array

        // comparing left and right
        while (i <= mid && j <= right) {
            if (comparator.compare(temp[i], temp[j]) <= 0) {
                array[k] = temp[i];
                i++;
            } else {
                array[k] = temp[j];
                j++;
            }
            k++;
        }

        
        // adding left over element of left array
        while (i <= mid) {
            array[k] = temp[i];
            k++;
            i++;
        }
    }
}
/**
 * SortingClass Gürkan Bıyk 2020510019 2020510019_Gurkan_Biyik_HW1_Code.java
 */
// sort methods
// SortingClass.quickSort(array, "FirstElement");
// SortingClass.quickSort(array, "RandomElement");
// SortingClass.quickSort(array, "MidOfFirstMidLastElement");
// SortingClass.mergeSort(array, "TwoParts");
// SortingClass.mergeSort(array, "ThreeParts");
public class SortingClass {

    // private constructor to prevent instantiation
    private SortingClass() {
        throw new IllegalStateException("Utility class");
    }

    public static void main(String[] args) {

        // SIMPLE TEST
        // int[] array = randomIntegers(10);
        // printArray(array);
        // mergeSort(array, "ThreeParts");
        // printArray(array);


        // quick test for the sorting methods
        // including 4 type of arrays with 3 different sizes
        // go line 47 to change the sorting method
        test();
    }

    // test the sorting methods, change line 47 to test different sorting methods
    private static void test() {
        // test arrays
        int[][] arrays = { equalIntegers(1_000), equalIntegers(10_000), equalIntegers(100_000),
                randomIntegers(1_000), randomIntegers(10_000), randomIntegers(100_000),
                increasingIntegers(1_000), increasingIntegers(10_000), increasingIntegers(100_000),
                decreasingIntegers(1_000), decreasingIntegers(10_000), decreasingIntegers(100_000) };

        for (int i = 0; i < arrays.length; i++) {
            long time = System.currentTimeMillis();

            ///////////////////////////
            quickSort(arrays[i], "MidOfFirstMidLastElement");
            ///////////////////////////

            time = System.currentTimeMillis() - time;

            // print the result
            System.out.println((i < 3 ? "Equal      integers with "
                    : i < 6 ? "Random     integers with "
                    : i < 9 ? "Increasing integers with "
                    : "Decreasing integers with ")
                    + String.format("%6d", arrays[i].length) + " elements: " + time + " ms");

        }
    }

    public static void mergeSort(int[] arrayToSort, String numberOfPartitions) {
        int lowIndex = 0;
        int highIndex = arrayToSort.length - 1;
        switch (numberOfPartitions) {
            case "TwoParts":
                mergeSortTwoPart(arrayToSort, lowIndex, highIndex);
                break;
            case "ThreeParts":
                mergeSortThreePart(arrayToSort, lowIndex, highIndex);
                break;
            default:
                mergeSortTwoPart(arrayToSort, lowIndex, highIndex);
                break;
        }
    }

    public static void quickSort(int[] arrayToSort, String pivotType) {
        int low = 0;
        int high = arrayToSort.length - 1;
        switch (pivotType) {
            case "FirstElement":
                quickSortFirstElement(arrayToSort, low, high);
                break;
            case "RandomElement":
                quickSortRandomElement(arrayToSort, low, high);
                break;
            case "MidOfFirstMidLastElement":
                quickSortMidOfFirstMidLastElement(arrayToSort, low, high);
                break;
            default:
                quickSortLastElement(arrayToSort, low, high);
                break;
        }
    }

    public static void printArray(int[] array) {
        for (int i = 0; i < array.length; i++) {
            System.out.print(array[i] + " ");
        }
        System.out.println();
    }

    public static int[] equalIntegers(int quantity) {
        int[] array = new int[quantity];
        for (int i = 0; i < quantity; i++) {
            array[i] = 1;
        }
        return array;
    }

    public static int[] randomIntegers(int quantity) {
        int[] array = new int[quantity];
        for (int i = 0; i < quantity; i++) {
            array[i] = (int) (Math.random() * quantity);
        }
        return array;
    }

    public static int[] increasingIntegers(int quantity) {
        int[] array = new int[quantity];
        for (int i = 0; i < quantity; i++) {
            array[i] = i;
        }
        return array;
    }

    public static int[] decreasingIntegers(int quantity) {
        int[] array = new int[quantity];
        for (int i = 0; i < quantity; i++) {
            array[i] = quantity - i;
        }
        return array;
    }

    // exchange two elements in the array by their index
    private static void exchange(int[] array, int index1, int index2) {
        int temp = array[index1];
        array[index1] = array[index2];
        array[index2] = temp;
    }

    private static void quickSortFirstElement(int[] array, int lowIndex, int highIndex) {
        if (lowIndex < highIndex) { // means that there are at least two elements in the array
            int q = partitionFirstElement(array, lowIndex, highIndex); // q is the index of the pivot
            quickSortFirstElement(array, lowIndex, q - 1); // recursive the part of the array before the pivot
            quickSortFirstElement(array, q + 1, highIndex); // recursive the part of the array after the pivot
        }
    }

    private static void quickSortLastElement(int[] array, int lowIndex, int highIndex) {
        if (lowIndex < highIndex) { // means that there are at least two elements in the array
            int q = partitionLastElement(array, lowIndex, highIndex); // q is the index of the pivot
            quickSortLastElement(array, lowIndex, q - 1); // recursive the part of the array before the pivot
            quickSortLastElement(array, q + 1, highIndex); // recursive the part of the array after the pivot
        }
    }

    private static void quickSortRandomElement(int[] array, int lowIndex, int highIndex) {
        if (lowIndex < highIndex) { // means that there are at least two elements in the array
            int q = partitionRandomElement(array, lowIndex, highIndex); // q is the index of the pivot
            quickSortRandomElement(array, lowIndex, q - 1); // recursive the part of the array before the pivot
            quickSortRandomElement(array, q + 1, highIndex); // recursive the part of the array after the pivot
        }
    }

    private static void quickSortMidOfFirstMidLastElement(int[] array, int lowIndex, int highIndex) {
        if (lowIndex < highIndex) { // means that there are at least two elements in the array
            int q = partitionMidOfFirstMidLastElement(array, lowIndex, highIndex); // q is the index of the pivot
            // recursive the part of the array before the pivot
            quickSortMidOfFirstMidLastElement(array, lowIndex, q - 1);
            // recursive the part of the array after the pivot
            quickSortMidOfFirstMidLastElement(array, q + 1, highIndex);
        }
    }

    private static int partitionLastElement(int[] array, int lowIndex, int highIndex) {
        // the pivot is the last element of the array,
        // assign it to a variable to compare with the other elements
        int pivot = array[highIndex];

        int i = lowIndex;

        // looping from first element to the element before the pivot
        // and compare the elements with the pivot
        for (int j = lowIndex; j < highIndex; j++) {
            // if the element is smaller than the pivot,
            // move it to the left side of the array
            if (array[j] < pivot) {
                exchange(array, i, j);
                i++; // next position
            }
        }

        // exchange the pivot with the element equal or bigger than the pivot
        // so that the pivot is take static position in the sorted array
        exchange(array, highIndex, i);

        // return the index of the pivot that divides the array into two parts
        return i;
    }

    private static int partitionFirstElement(int[] array, int lowIndex, int highIndex) {
        // the pivot is the last element of the array,
        // assign it to a variable to compare with the other elements
        // and store the pivot to replace
        int pivot = array[lowIndex];

        int i = lowIndex;
        int j = highIndex;

        while (i < j) {
            // loop until find an element that is smaller than the pivot
            while (i < j && array[j] >= pivot) {
                j--;
            }

            // replace it to the left side of the array
            array[i] = array[j];

            // loop until find an element that is bigger than the pivot
            while (i < j && array[i] < pivot) {
                i++;
            }

            // replace it to the right side of the array
            array[j] = array[i];
        }

        // replace the pivot to the position that divides the array into two parts
        array[i] = pivot;
        return i;
    }

    private static int partitionRandomElement(int[] array, int lowIndex, int highIndex) {
        // select a random element as the pivot
        int randomIndex = (int) (Math.random() * (highIndex - lowIndex + 1)) + lowIndex;

        // assign it to a variable to compare with the other elements
        // and store the pivot to replace
        int pivot = array[randomIndex];

        int i = lowIndex;
        int j = highIndex;

        while (i < j) {
            // loop until find an element that is smaller than the pivot
            while (i < j && array[j] >= pivot) {
                j--;
            }

            // replace it to the left side of the array
            array[i] = array[j];

            // loop until find an element that is bigger than the pivot
            while (i < j && array[i] < pivot) {
                i++;
            }

            // replace it to the right side of the array
            array[j] = array[i];
        }

        // replace the pivot to the position that divides the array into two parts
        array[i] = pivot;
        return i;
    }

    private static int partitionMidOfFirstMidLastElement(int[] array, int lowIndex, int highIndex) {
        // choose the pivot middle of the first, middle and last element
        // exchange it with the first element
        int midIndex = (lowIndex + highIndex) / 2;

        // find the middle element of the first, middle and last element
        int pivotIndex;
        // if the first element is the biggest
        if (array[lowIndex] > array[midIndex] && array[lowIndex] > array[highIndex]) {
            // if the middle element is the bigger than the last element
            if (array[midIndex] > array[highIndex]) { // (3,2,1)
                pivotIndex = midIndex; // the middle element is the pivot
            }
            // if the last element is the bigger than or equal the middle element
            else { // (3,1,2) or (3,1,1)
                pivotIndex = highIndex; // the last element is the pivot
            }
        }
        // if the middle element is the biggest
        else if (array[midIndex] > array[lowIndex] && array[midIndex] > array[highIndex]) {
            // if the first element is the bigger than the last element
            if (array[lowIndex] > array[highIndex]) { // (2,3,1)
                pivotIndex = lowIndex; // the first element is the pivot
            }
            // if the last element is the bigger than or equal the first element
            else { // (1,3,2) or (2,3,2)
                pivotIndex = highIndex; // the last element is the pivot
            }
        }
        // if the last element is the biggest (3,3,4), (2,3,4), (3,2,4)
        // or all the elements are equal (3,3,3)
        else {
            // if the first element is the bigger than the middle element (3,2,4)
            if (array[lowIndex] > array[midIndex]) {
                pivotIndex = lowIndex; // the first element is the pivot
            }
            // all equal (3,3,3)
            // or the middle and first element is equal (3,3,4)
            // or the middle is bigger than the first element (2,3,4)
            else {
                pivotIndex = midIndex; // the middle element is the pivot
            }
        }

        // assign it to a variable to compare with the other elements
        // and store the pivot to replace
        int pivot = array[pivotIndex];

        int i = lowIndex;
        int j = highIndex;

        while (i < j) {
            // loop until find an element that is smaller than the pivot
            while (i < j && array[j] >= pivot) {
                j--;
            }

            // replace it to the left side of the array
            array[i] = array[j];

            // loop until find an element that is bigger than the pivot
            while (i < j && array[i] < pivot) {
                i++;
            }

            // replace it to the right side of the array
            array[j] = array[i];
        }

        // replace the pivot to the position that divides the array into two parts
        array[i] = pivot;
        return i;
    }

    private static void mergeSortTwoPart(int[] arrayToSort, int lowIndex, int highIndex) {
        // if the array has more than one element
        if (lowIndex < highIndex) {
            int midIndex = (lowIndex + highIndex) / 2; // divide index for two parts
            mergeSortTwoPart(arrayToSort, lowIndex, midIndex); // sort the left part
            mergeSortTwoPart(arrayToSort, midIndex + 1, highIndex); // sort the right part
            mergeTwoPart(arrayToSort, lowIndex, midIndex, highIndex); // merge the two sorted arrays
        }
    }

    private static void mergeTwoPart(int[] arrayToSort, int lowIndex, int midIndex, int highIndex) {
        // create two arrays to hold the left and right parts of the array
        int[] leftArray = new int[midIndex - lowIndex + 1];
        int[] rightArray = new int[highIndex - midIndex];

        // copy the left part of the array into the left array
        for (int i = 0; i < leftArray.length; i++) {
            leftArray[i] = arrayToSort[lowIndex + i];
        }

        // copy the right part of the array into the right array
        for (int i = 0; i < rightArray.length; i++) {
            rightArray[i] = arrayToSort[midIndex + 1 + i];
        }

        // comparing the elements of the left and right arrays one by one and
        // copying the smaller element into the original array
        int i = 0;
        int j = 0;
        int k = lowIndex;
        // loop while end of either array is not reached
        while (i < leftArray.length && j < rightArray.length) {
            // if the left array element is smaller
            // copy it into the original array
            if (leftArray[i] <= rightArray[j]) {
                arrayToSort[k] = leftArray[i];
                i++;
            }
            // else copy the right array element into the original array
            else {
                arrayToSort[k] = rightArray[j];
                j++;
            }
            k++;
        }

        // if there are any remaining elements in the left array
        // copy them into the original array
        while (i < leftArray.length) {
            arrayToSort[k] = leftArray[i];
            i++;
            k++;
        }

        // if there are any remaining elements in the right array
        // copy them into the original array
        while (j < rightArray.length) {
            arrayToSort[k] = rightArray[j];
            j++;
            k++;
        }
    }

    private static void mergeSortThreePart(int[] arrayToSort, int lowIndex, int highIndex) {
        if (lowIndex < highIndex) {
            int divIndex1 = lowIndex + (highIndex - lowIndex) / 3; // first boundary divider index
            int divIndex2 = divIndex1 + 1 + (highIndex - lowIndex) / 3; // second boundary divider index
            mergeSortThreePart(arrayToSort, lowIndex, divIndex1); // sort the left part
            mergeSortThreePart(arrayToSort, divIndex1 + 1, divIndex2); // sort the middle part
            mergeSortThreePart(arrayToSort, divIndex2 + 1, highIndex); // sort the right part
            mergeThreePart(arrayToSort, lowIndex, divIndex1, divIndex2, highIndex); // merge the three sorted arrays
        }
    }

    private static void mergeThreePart(int[] arrayToSort, int lowIndex, int divIndex1, int divIndex2, int highIndex) {
        // create three arrays to hold the left, middle and right parts of the array
        int[] leftArray = new int[divIndex1 - lowIndex + 1];
        int[] middleArray = new int[divIndex2 - divIndex1];
        int[] rightArray = new int[highIndex - divIndex2];

        // copy the left part of the array into the left array
        for (int i = 0; i < leftArray.length; i++) {
            leftArray[i] = arrayToSort[lowIndex + i];
        }

        // copy the middle part of the array into the middle array
        for (int i = 0; i < middleArray.length; i++) {
            middleArray[i] = arrayToSort[divIndex1 + 1 + i];
        }

        // copy the right part of the array into the right array
        for (int i = 0; i < rightArray.length; i++) {
            rightArray[i] = arrayToSort[divIndex2 + 1 + i];
        }

        // comparing the elements of the left, middle and right arrays one by one and
        // copying the smaller element into the original array
        int i = 0;
        int j = 0;
        int k = 0;
        int l = lowIndex;
        // loop while end of any array is not reached
        while (i < leftArray.length && j < middleArray.length && k < rightArray.length) {
            // if the left array element is smaller
            // copy it into the original array
            if (leftArray[i] <= middleArray[j] && leftArray[i] <= rightArray[k]) {
                arrayToSort[l] = leftArray[i];
                i++;
            }
            // if the middle array element is smaller
            // copy it into the original array
            else if (middleArray[j] <= leftArray[i] && middleArray[j] <= rightArray[k]) {
                arrayToSort[l] = middleArray[j];
                j++;
            }
            // else copy the right array element into the original array
            else {
                arrayToSort[l] = rightArray[k];
                k++;
            }
            l++;
        }

        // if there are any remaining elements in the left and middle arrays
        // copy them into the original array
        while (i < leftArray.length && j < middleArray.length) {
            // if the left array element is smaller
            // copy it into the original array
            if (leftArray[i] <= middleArray[j]) {
                arrayToSort[l] = leftArray[i];
                i++;
            }
            // else copy the middle array element into the original array
            else {
                arrayToSort[l] = middleArray[j];
                j++;
            }
            l++;
        }

        // if there are any remaining elements in the left and right arrays
        // copy them into the original array
        while (i < leftArray.length && k < rightArray.length) {
            // if the left array element is smaller
            // copy it into the original array
            if (leftArray[i] <= rightArray[k]) {
                arrayToSort[l] = leftArray[i];
                i++;
            }
            // else copy the right array element into the original array
            else {
                arrayToSort[l] = rightArray[k];
                k++;
            }
            l++;
        }

        // if there are any remaining elements in the middle and right arrays
        // copy them into the original array
        while (j < middleArray.length && k < rightArray.length) {
            // if the middle array element is smaller
            // copy it into the original array
            if (middleArray[j] <= rightArray[k]) {
                arrayToSort[l] = middleArray[j];
                j++;
            }
            // else copy the right array element into the original array
            else {
                arrayToSort[l] = rightArray[k];
                k++;
            }
            l++;
        }

        // if there are any remaining elements in the left array
        // copy them into the original array
        while (i < leftArray.length) {
            arrayToSort[l] = leftArray[i];
            i++;
            l++;
        }

        // if there are any remaining elements in the middle array
        // copy them into the original array
        while (j < middleArray.length) {
            arrayToSort[l] = middleArray[j];
            j++;
            l++;
        }

        // if there are any remaining elements in the right array
        // copy them into the original array
        while (k < rightArray.length) {
            arrayToSort[l] = rightArray[k];
            k++;
            l++;
        }

    }
}
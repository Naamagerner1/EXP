public class MedianDS {
    MinTreeHeap L;
    MaxTreeHeap S;

    public MedianDS(int[] A){
        int n = A.length ;
        int[] ACopy = new int[n+1];
        ACopy[0] = -1;
        for (int i = 1; i<n+1; i++){
            ACopy[i] = A[i-1];
        }
        int median = select(A, (n+1)/2);
        int SSize = (n+1)/2;
        int LSize = n/2;
        int[] S_A = new int[SSize];
        int[] L_A = new int[LSize];
        int min_i=0, max_i=0;
        for (int j = 0; j<n; j++) {
            if (median >= A[j]) {
                S_A[max_i] = A[j];
                max_i++;
            }
            else {
                L_A[min_i] = A[j];
                min_i++;
            }
        }
        S = MaxTreeHeap.BuildHeapT(S_A);
        L = MinTreeHeap.BuildHeapT(L_A);
    }

    public int partition(int[] A, int p, int r, int x){
        int n = r-p+1;
        int[] B = new int[n];
        int left = 0;
        int right = n-1;
        for (int i=0; i<n; i++){
            if (A[i+p] > x){
                B[right] = A[i+p];
                right--;
            }
            else if (A[i+p] < x){
                B[left] = A[i+p];
                left++;
            }
        }
        int q = left;
        B[q] = x;
        for (int i=0; i<n; i++){
            A[i+p] = B[i];
        }
        return p+q-1;
    }



/* old - compile
    public int partition(int[] A, int p, int r, int x){
        int n = r-p+1;
        int[] B = new int[n+1]; //////////////////////////////////////////////
        int left = 1;
        int right = n;
        for (int i=0; i<n; i++){
            if (A[i+p] > x){
                B[right] = A[i+p]; /////////////////////////////////
                right--;
            }
            else if (A[i+p] < x){
                B[left] = A[i+p]; ///////////////////////
                left++;
            }
        }
        int q = left;
        B[q] = x;
        for (int i=1; i<n+1; i++){
            A[i-1+p] = B[i];
        }
        return p+q-1;
    }*/

    public void swap(int[] A, int a, int b){
        int temp = A[a];
        A[a] = b;
        A[b] = temp;
    }

    public int select(int[] A, int i) {
        int n = A.length;
        if (n == 1) {
            return A[0];
        }
        boolean haveResidual = false;
        int residual = 0;
        int numGroups;
        if (n % 5 == 0) {
            numGroups = n / 5;
        } else {
            numGroups = (n / 5) + 1;
            haveResidual = true;
            residual = n % 5;
        }
        int[] mediansForGroups = new int[numGroups];
        findMedianForEachGroup(numGroups, haveResidual, residual, A, mediansForGroups); //find median for each group
        int x = select(mediansForGroups, numGroups / 2);
        int q = partition(A, 0,n-1,x);
        if (i==q){ //////////////////////////////////
            return x;
        }
        else if (i<q){
            int [] ACopy = new int[q-1]; /////////////////////////////////////////////////
            for (int j = 0; j<q-1;j++){
                ACopy[j] = A[j];
            }
            return select(ACopy, i); //////////////////////
        }
        else {
            int [] ACopy = new int[n-q];
            for (int j = q+1, k = 0 ; j<n ; j++, k++){
                ACopy[k] = A[j];
            }
            return select(ACopy, i-q); ////////////////////////////
        }
    }

    public void findMedianForEachGroup(int munGroups, boolean haveResidual, int residual, int[] A, int[] mediansForGroups){
        int n = A.length;
        for (int j=0; j<munGroups; j++){
            int[] tempArr;
            if (haveResidual && (j == munGroups-1)){
                tempArr = new int[residual];
            }
            else{
                tempArr = new int[5];
            }
            int tempIndex = 0;
            for (int originalIndex = 5*j; originalIndex<5*(j+1); originalIndex++){
                if (originalIndex < n){
                    tempArr[tempIndex] = A[originalIndex];
                    tempIndex++;
                }
            }
            if (haveResidual && (j == munGroups-1)){
                if (residual-1 < 0){
                    System.out.println("ERROREEEEEEEERERERERERERRR");
                }
                mediansForGroups[j] = naiveSelect(tempArr,residual/2); ////////////////////////////////
            }
            else {
                mediansForGroups[j] = naiveSelect(tempArr,2);
            }
        }
    }

    public void merge(int arr[], int l, int m, int r)
    {
        int n1 = m - l + 1;
        int n2 = r - m;
        int L[] = new int[n1];
        int R[] = new int[n2];
        for (int i = 0; i < n1; ++i)
            L[i] = arr[l + i];
        for (int j = 0; j < n2; ++j)
            R[j] = arr[m + 1 + j];
        int i = 0, j = 0;
        int k = l;
        while (i < n1 && j < n2) {
            if (L[i] <= R[j]) {
                arr[k] = L[i];
                i++;
            }
            else {
                arr[k] = R[j];
                j++;
            }
            k++;
        }
        while (i < n1) {
            arr[k] = L[i];
            i++;
            k++;
        }
        while (j < n2) {
            arr[k] = R[j];
            j++;
            k++;
        }
    }

    public void mergeSort(int arr[], int l, int r)
    {
        if (l < r) {
            int m =(r+l)/2;
            mergeSort(arr, l, m);
            mergeSort(arr, m + 1, r);
            merge(arr, l, m, r);
        }
    }

    public int naiveSelect(int[] A, int i){
        mergeSort(A,0,A.length-1);
        return A[i];
    }







/*
    public int partition(int[] A, int p, int r, int x){
        int n = r-p+1;
        int[] B = new int[n+1];
        int left = 1;
        int right = n;
        for (int i=0; i<n; i++){
            if (A[i+p] > x){
                B[right] = A[i+p];
                right--;
            }
            else if (A[i+p] < x){
                B[left] = A[i+p];
                left++;
            }
        }
        int q = left;
        B[q] = x;
        for (int i=1; i<n+1; i++){
            A[i-1+p] = B[i];
        }
        return p+q-1;
    }

    public int partition2(int[] A, int left, int right, int x){
        int xCurrIndex = 0;
        for (int i=left; i<right; i++){
            if (A[i] == x){
                xCurrIndex = i;
                break;
            }
        }
        int temp = A[xCurrIndex];
        A[xCurrIndex] = right;
        A[right] = temp;
        //swap(A, xCurrIndex, right);
        int q = left;
        for (int i=left; i<right-1; i++){
            if (A[i] < x){
                swap(A,q,i);
                q++;
            }
        }
        swap(A,right,q);
        return q;
    }

    public void swap(int[] A, int a, int b){
        int temp = A[a];
        A[a] = b;
        A[b] = temp;
    }

    public int select(int[] A, int i) {
        int n = A.length;
        if (n == 1) {
            return A[0];
        }
        boolean haveResidual = false;
        int residual = 0;
        int numGroups;
        if (n % 5 == 0) {
            numGroups = n / 5;
        } else {
            numGroups = (n / 5) + 1;
            haveResidual = true;
            residual = n % 5;
        }
        int[] mediansForGroups = new int[numGroups];
        findMedianForEachGroup(numGroups, haveResidual, residual, A, mediansForGroups); //find median for each group
        int x = select(mediansForGroups, numGroups / 2);
        int q = partition(A, 0,n-1,x);
        if (i==q){
            return x;
        }
        else if (i<q){
            int [] ACopy = new int[q];
            for (int j = 0; j<q;j++){
                ACopy[j] = A[j];
            }
            return select(ACopy, i);
        }
        else {
            int [] ACopy = new int[n-q+1];
            for (int j = q+1, k = 0 ; j<n ; j++, k++){
                ACopy[k] = A[j];
            }
            return select(ACopy, i-q);
        }
    }

    public void findMedianForEachGroup(int munGroups, boolean haveResidual, int residual, int[] A, int[] mediansForGroups){
        int n = A.length;
        for (int j=0; j<munGroups; j++){
            int[] tempArr;
            if (haveResidual && (j == munGroups-1)){
                tempArr = new int[residual];
            }
            else{
                tempArr = new int[5];
            }
            int tempIndex = 0;
            for (int originalIndex = 5*j; originalIndex<5*(j+1); originalIndex++){
                if (originalIndex < n){
                    tempArr[tempIndex] = A[originalIndex];
                    tempIndex++;
                }
            }
            if (haveResidual && (j == munGroups-1)){
                mediansForGroups[j] = naiveSelect(tempArr,(residual-1)/2);
            }
            else {
                mediansForGroups[j] = naiveSelect(tempArr,2);
            }
        }
    }

    public void merge(int arr[], int l, int m, int r)
    {
        int n1 = m - l + 1;
        int n2 = r - m;
        int L[] = new int[n1];
        int R[] = new int[n2];
        for (int i = 0; i < n1; ++i)
            L[i] = arr[l + i];
        for (int j = 0; j < n2; ++j)
            R[j] = arr[m + 1 + j];
        int i = 0, j = 0;
        int k = l;
        while (i < n1 && j < n2) {
            if (L[i] <= R[j]) {
                arr[k] = L[i];
                i++;
            }
            else {
                arr[k] = R[j];
                j++;
            }
            k++;
        }
        while (i < n1) {
            arr[k] = L[i];
            i++;
            k++;
        }
        while (j < n2) {
            arr[k] = R[j];
            j++;
            k++;
        }
    }

    public void mergeSort(int arr[], int l, int r)
    {
        if (l < r) {
            int m = (l + r)/2;
            mergeSort(arr, l, m);
            mergeSort(arr, m + 1, r);
            merge(arr, l, m, r);
        }
    }

    public int naiveSelect(int[] A, int i){
        mergeSort(A,0,A.length-1);
        return A[i];
    }*/


    public void insert(int x){
        if (x < findMedian()){
            S.HeapInsert(x);
        }
        else{
            L.HeapInsert(x);
        }
        if (L.getSize() == S.getSize() + 1){
            int r = L.HeapExtractMin();
            S.HeapInsert(r);
        }
        else if (L.getSize() == S.getSize() - 2) {
            int r = S.HeapExtractMax();
            L.HeapInsert(r);
        }
    }

    public void delMedian(){
        S.HeapExtractMax();
        if (L.getSize() == S.getSize() + 1){
            int r = L.HeapExtractMin();
            S.HeapInsert(r);
        }
    }

    public int findMedian(){
        return S.getRoot().getData();
    }

}

import java.io.DataOutputStream;
import java.io.IOException;

public class MinTreeHeap {
    private int[] A;
    private int n;


    public MinTreeHeap(int [] A){ ///////////////////////האם לשים במערך שגודלו T max ? //////////////////
        this.A = A; //////////////move the indexes to start in 1?
        n = A.length;
    }

    public static void Heapify(int[] A, int i){
        int l = 2*i;
        int n = A.length;
        int smallest;
        if ((l <= n) && (A[l] < A[i])){
            smallest = l ;
        }
        else {
            smallest = i ;
        }
        int r = 2*i + 1;
        if ((r <= n) && (A[r] < A[smallest])){
            smallest = r ;
        }
        if (smallest != i) {
            int temp = A[i];
            A[i] = A[smallest];
            A[smallest] = temp;
            Heapify(A, smallest);
        }
    }

    public static MinTreeHeap BuildHeapT(int[] A){
        int n = A.length;
        for (int i = n; i>0; i--){
            Heapify(A,i);
        }
        return new MinTreeHeap(A);
    }

    public void Heap_Decrease_key(int[] A, int i, int k) {
        if (k > A[i]) {
            return;  //Error new key is larger than current key
        }
        A[i] = k;
        int parent;
        if (i % 2 == 0) {
            parent = i / 2;
        } else {
            parent = (i - 1) / 2;
        }
        while (i > 1 && A[i] < A[parent]) {
            int temp = A[i];
            A[i] = A[parent];
            A[parent] = temp;
            i = parent;
        }
    }

    public void HeapInsert (int k){ /////////////////////////////////////////////////////
            int n = A.length;
            int s = n + 1;
            Integer myInf = Integer.MAX_VALUE;
            A[s] = myInf;
            n = s;
            Heap_Decrease_key(A, s, k);
    }

    public int HeapExtractMin(){
        if (n < 1){
            return -1; ///////////////////////////////////////////////////////////////////////
        }
        int min = A[1];
        A[1] = A[n-1];
        n =-1;
        Heapify(A,1);
        return min;
    }

    public void printByLayer(DataOutputStream out){
        int j = 1;
        for (int i = 1; i<n; i++){
            int temp = 2^i;
            while (j < temp){
                try {
                    out.writeInt(A[j]);
                }
                catch (IOException e){
                    return;
                }
                j++;
            }
            String h = System.lineSeparator();
            out.writeBytes(h);  /////////////////////////////////////////////////////
        }
    }




}

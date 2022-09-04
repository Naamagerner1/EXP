import java.io.DataOutputStream;
import java.io.IOException;

import static java.lang.System.out;

public class Main {
    public static void main(String[] args) throws IOException {
        //int[] A = {5,23,1,2,3,4,9,8,7,6,24,25,26,11,12,13,10,14,15,16,17,18,22,21,20,19};
        int[] A = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26};
        MinTreeHeap min =  MinTreeHeap.BuildHeapT(A);
        min.HeapInsert(27);
        min.HeapInsert(28);
        min.HeapInsert(40);
        min.HeapInsert(35);
        min.HeapInsert(60);
        min.HeapInsert(65);

        DataOutputStream outStream = new DataOutputStream(out);
        min.printByLayer(outStream);
    }
}

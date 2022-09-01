import java.io.DataOutputStream;
import java.io.IOException;

public class MaxTreeHeap {  // can we connect Min and Max , they have similar print function
    private Vertex root;
    private int size;

    public MaxTreeHeap(int[] A){
        this.size = A.length;
    }

    public Vertex getRoot(){
        return root;
    }
    public int getSize(){
        return size;
    }

    public static void Heapify(int[] A, int i){
        int l = 2*i;
        int n = A.length;
        int bigest;
        if ((l <= n) && (A[l] > A[i])){
            bigest = l ;
        }
        else {
            bigest = i ;
        }
        int r = 2*i + 1;
        if ((r <= n) && (A[r] > A[bigest])){
            bigest = r ;
        }
        if (bigest != i) {
            int temp = A[i];
            A[i] = A[bigest];
            A[bigest] = temp;
            Heapify(A, bigest);
        }
    }

    public static MaxTreeHeap BuildHeapT(int[] A){
        int n = A.length;
        int[] ACopy = new int[n+1];
        for (int i=1; i<n+1; i++){
            ACopy[i] = A[i-1];
        }
        for (int i = n; i>0; i--){
            Heapify(ACopy,i);
        }
        Vertex[] B = new Vertex[n+1] ;
        B[0] = null;
        for (int i=1; i<n+1; i++) {
            int data = A[i-1];
            B[i] = new Vertex(data);
            B[i].setIndex(i);
        }
        for (int i=1; i<n+1; i++) {
            if (i % 2 == 0) {
                B[i].setParent(B[i / 2]);
            }
            else {
                B[i].setParent(B[(i - 1) / 2]);
            }
            B[i].setLeft(B[2*i]);
            B[i].setRight(B[2*i + 1]);
        }
        B[1].setParent(null);
        MaxTreeHeap H = new MaxTreeHeap(A);
        H.root = B[1];
        return H;
    }

    public void HeapInsert (int k){
        size++;
        int[] pathLastToRoot = new int[size];
        int limit = findPathLastToRoot(pathLastToRoot);
        Vertex newVertex = findAndAddLast(limit, pathLastToRoot);
        Heap_Increase_key(newVertex, size, k);
    }

    public void Heap_Increase_key(Vertex curr, int i, int k) {
        if (k < curr.getData()) {
            return;  //Error new key is larger than current key
        }
        curr.setData(k);

        while (i > 1 && curr.getData() > curr.getParent().getData()) {
            Vertex parent = curr.getParent();
            int temp = curr.getData();
            curr.setData(parent.getData());
            parent.setData(temp);

            i = parent.getIndex();
        }
    }

    public int findPathLastToRoot(int[] pathLastToRoot) {
        int limit = 0;
        for (int i = size; i > 0; i = i / 2) {
            pathLastToRoot[limit] = i % 2;
            limit++;
        }
        return limit;
    }

    public Vertex findAndAddLast (int limit, int[] pathLastToRoot){
        Integer myMinusInf = Integer.MIN_VALUE;
        Vertex newVertex = new Vertex(myMinusInf);
        newVertex.setIndex(size);

        Vertex last = root;
        while (limit>0){                          //find the last vertex
            if (limit==1){
                if (pathLastToRoot[limit] == 0) {
                    last.setLeft(newVertex);
                }
                else {
                    last.setRight(newVertex);
                }
                newVertex.setParent(last);
            }
            if (pathLastToRoot[limit] == 0){
                last = last.getLeft();
            }
            else{
                last = last.getRight();
            }
            limit--;
        }
        return newVertex;
    }

    public int HeapExtractMax(){
        if (size < 1){
            return -1;
        }
        int max = root.getData();

        int[] pathLastToRoot = new int[size];
        int limit = findPathLastToRoot(pathLastToRoot); //find the path from last to root

        Vertex last = root;
        while (limit>0){                          //find the last vertex
            if (pathLastToRoot[limit] == 0){
                last = last.getLeft();
            }
            else{
                last = last.getRight();
            }
            limit--;
        }
        root.setData(last.getData());       //move last to be root
        if (size % 2 ==0) {                 //remove last form tree
            last.getParent().setLeft(null);
        }
        else {
            last.getParent().setRight(null);
        }
        last.setParent(null);
        size--;
        HeapifyTree(root);
        return max;
    }

    public void HeapifyTree(Vertex vertex){
        Vertex bigest;
        if (vertex.getLeft() != null && vertex.getLeft().getData() > vertex.getData()){
            bigest = vertex.getLeft();
        }
        else {
            bigest = vertex;
        }
        if (vertex.getRight() != null && vertex.getRight().getData() > vertex.getData()){
            bigest = vertex.getRight();
        }
        if (bigest != vertex){
            int temp = bigest.getData();
            bigest.setData(vertex.getData());
            vertex.setData(temp);
            HeapifyTree(bigest);
        }
    }

    private static void inOrder(Vertex node, int[] heapArr) {
        if (node == null) {
            return;
        }
        inOrder(node.getLeft(),heapArr);
        heapArr[node.getIndex()-1] = node.getData();
        inOrder(node.getRight(),heapArr);
    }

    public void printByLayer(DataOutputStream out) throws IOException{
        int[] heapArr = new int[size];
        inOrder(root, heapArr);

        out.writeInt(heapArr[0]);
        out.writeBytes(System.lineSeparator());
        int j = 1;
        for (int i = 2; i<size; i++){
            int limitIndex = 2^i;
            if (j > size){
                break;
            }
            while (j < limitIndex){
                out.writeInt(heapArr[j]);
                if (j+1 != limitIndex){
                    out.writeBytes(",");
                }
                j++;
            }
            out.writeBytes(System.lineSeparator());
        }
    }

}

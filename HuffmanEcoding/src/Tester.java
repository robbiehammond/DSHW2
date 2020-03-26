import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class Tester {

    public static void main(String[] args) throws IOException {
        HuffmanTree huff = new HuffmanTree();
        huff.makeNodes("inputFile.txt");
        huff.CreateTree();
        //start at top of tree with no code
        huff.encode(huff.nodeTree.peek(), "");
        huff.printEncoding();
        huff.encodeFile();
    }
}


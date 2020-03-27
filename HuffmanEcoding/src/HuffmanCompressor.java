import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class HuffmanCompressor {

    public static void main(String[] args) throws IOException {
        beginHuffman("inputFile", "outputFile");
    }

    public static void beginHuffman(String inputFile, String outputFile) throws IOException {
        HuffmanTree huff = new HuffmanTree();
        huff.makeNodes(inputFile + ".txt");
        huff.CreateTree();
        //start at top of tree with no code
        huff.encode(huff.nodeTree.peek(), "");
        huff.printEncoding();
        huff.encodeFile(outputFile + ".txt");
    }
    //use hashmap to make nodes
    //get args working
}


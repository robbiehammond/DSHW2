/**
 * @author Robbie Hammond
 * EECS Data Structures Spring 2020
 */

import java.io.IOException;

public class HuffmanCompressor {

    public static void main(String[] args) throws IOException {
        //args[0] = inputFile, args[1] = outputFile (if the program is being run as the directions say)
        beginHuffman(args[0], args[1]);
        }

    //method to start the encoding process
    public static void beginHuffman(String inputFile, String outputFile) throws IOException {
        //make new tree
        HuffmanTree huff = new HuffmanTree();
        //make the nodes from inputFile text
        huff.makeNodes(inputFile + ".txt");
        //create node tree
        huff.CreateTree();
        //start at top of tree with nothing encoded, encode each letter
        huff.encode(huff.nodeTree.peek(), "");
        //print character encoding to Letter Encoding.txt
        huff.printEncoding();
        //print encoded text to outputFile
        huff.encodeFile(outputFile + ".txt");
    }
}


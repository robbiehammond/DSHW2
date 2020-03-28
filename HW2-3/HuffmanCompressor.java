/**
 * @author Robbie Hammond
 * EECS Data Structures Spring 2020
 */


public class HuffmanCompressor {

    public static void main(String[] args) {
        //args[0] = inputFile, args[1] = outputFile
        huffmanCoder(args[0], args[1]);
    }


    //method to start the encoding process
    public static String huffmanCoder(String inputFileName, String outputFileName) {

        String success = "file successfully encoded. Check the outputFile for the encoded document, Letter Encoding for the encoding," +
                " and savings for the estimated space reduction of this example document.";

        String failure = "Something went wrong. Please make sure the inputFile\n" +
                "file is in the same directory as the classes.";

        //make new tree
        HuffmanTree huff = new HuffmanTree();
        //make the nodes from inputFile text
        try {
            huff.makeNodes(inputFileName + ".txt");
            //create node tree
            huff.CreateTree();
            //start at top of tree with nothing encoded, encode each letter
            huff.encode(huff.nodeTree.peek(), "");
            //print character encoding to Letter Encoding.txt
            huff.printEncoding();
            //print encoded text to outputFile
            huff.encodeFile(inputFileName + ".txt", outputFileName + ".txt");
            System.out.println(success);
            return success;
        }

        //if the inputFile cannot be found or something else goes wrong, tell the user.
        catch (Exception e) {
            System.out.println(failure);
            return failure;
        }
    }
}


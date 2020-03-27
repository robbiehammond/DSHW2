import java.io.*;
import java.util.*;

public class HuffmanTree {

    //stores the nodes right after creation
    ArrayList<HuffmanNode> nodeArray;
    //stores nodes after merging process
    PriorityQueue<HuffmanNode> nodeTree;
    //stores characters and their respective encoding
    HashMap<Character, String> map = new HashMap<>();

    //node class
    public static class HuffmanNode {

        Character inChar;
        int frequency;
        HuffmanNode left;
        HuffmanNode right;

        public HuffmanNode(Character inChar, int frequency) {
            this.inChar = inChar;
            this.frequency = frequency;
            left = null;
            right = null;
        }

        //used to simplify encoding process
        public boolean isLeafNode() {
            //if a node has no children, it is a leaf
            if (this.left == null && this.right == null)
                return true;
            else
                return false;
        }
    }

    //sorting comparator by node frequency
    public static class SortbyFreq implements Comparator<HuffmanNode> {
        @Override
        public int compare(HuffmanNode o1, HuffmanNode o2) {
            return o1.frequency - o2.frequency;
        }
    }


    //if hashmap is necessary, just make one at the beginning of the method, keep everything up to "if (getFrequencies(contents, (char)curChar) > 0)", then under that, add the current letter to the map and then find the frequncy associated with getFreq.
    //add both to hashmap, repeat for each character. Finally, put all contents of hashmap into nodes at the end of method by running through the map.
    public void makeNodes(String inputFile) throws IOException {
        String contents = fileContents(inputFile);
        ArrayList<HuffmanNode> list = new ArrayList<>();
        int curChar = 0;
        int index = 0;
        for (int i = 0; i <= 255; i++) {
            curChar++;
            //if the character we're on appears at least once, add it to the list
            if (getFrequencies(contents, (char) curChar) > 0) {
                HashMap<Character, Integer> tempMap = new HashMap<>();
                tempMap.put((char)curChar, getFrequencies(contents, (char) curChar));
                list.add(index, new HuffmanNode((char) curChar, getFrequencies(contents, (char) curChar)));
                index++;
            }
        }
        //store the list
        nodeArray = list;
    }

    //put two nodes together to create the huffman tree
    public HuffmanNode mergeNodes(HuffmanNode leftNode, HuffmanNode rightNode) {
        //merged node
        HuffmanNode parent = new HuffmanNode(null, leftNode.frequency + rightNode.frequency);
        //set its children to be the merged nodes
        parent.left = leftNode;
        parent.right = rightNode;
        return parent;
    }


    public void CreateTree() {
        //queue (used as min-heap) to build tree
        PriorityQueue<HuffmanNode> queue = new PriorityQueue<>(new SortbyFreq());
        //begin by putting all nodes in
        queue.addAll(nodeArray);

        //while there are at least 2 nodes
        while (queue.size() > 1) {
            //save and extract smallest two nodes
            HuffmanNode leftNode = queue.peek();
            queue.poll();
            HuffmanNode rightNode = queue.peek();
            queue.poll();

            //merge them together and add them back on list
            queue.add(mergeNodes(leftNode, rightNode));
        }
        //store new tree
        nodeTree = queue;
    }


    //map each character with its proper code based on tree structure
    public void encode(HuffmanNode root, String code) {
        //if it is a leaf node, it is a character
        if (root.isLeafNode()) {
            map.put(root.inChar, code);
            return;
        }
        //if not, recursively traverse down tree
        //change these up a bit
        encode(root.left, code + "0");
        encode(root.right, code + "1");

    }


    //display encoding into txt file
    public void printEncoding() throws IOException {
        FileWriter writer = new FileWriter("Letter Encoding.txt");
        //Disclaimer
        writer.write("Character Encoding is as follows. ");
        writer.write("Note that the blank spaces are not mistakes, but rather actual characters," +
                " as spaces, new lines, and carriage returns are scanned and encoded.\nAs proof," +
                " I put the ascii code directly next to each respective character. Here is the encoding: ");
        //for each entry in the map, get the encoding and then print the character and encoding
        for (Map.Entry<Character, String> entry : map.entrySet()) {
            int ascii = (int)entry.getKey();
            writer.write(entry.getKey()  +  " (" + ascii + ") " + " : " + entry.getValue() + '\n');
        }
        writer.close();
    }

    //scan file again and encode each letter
    public void encodeFile(String outputFile) throws IOException {
        String original = fileContents("inputFile.txt");
        FileWriter writer = new FileWriter(outputFile);
        //look at each character in the original txt file
        for (int i = 0; i < original.length(); i++) {
            Character curLetter = original.charAt(i);
            //get this letter's code
            String code = map.get(curLetter);
            //add that code to the text document
            writer.write(code);
        }
        writer.close();
    }


    //simple helper methods below this point

    //reads the file contents and converts it to a string.
    public String fileContents(String InputFile) throws IOException {
            FileReader fr = new FileReader(InputFile);
            int i;
            //start with nothing
            String st = "";
            //scan while there are characters left to scan
            while ((i=fr.read()) != -1) {
                st += (char)i;
        }
            return st;
    }

    //find frequencies of each letter in the inputFile
    public int getFrequencies(String contents, char letter) throws IOException {
        int count = 0;
            //check each string index, see if it matches current character
        for (int j = 0; j < contents.length(); j++) {
                //if it matches, raise frequency count
            if (contents.charAt(j) == (char)letter) {
                   count++;
            }
        }
        return count;
    }
}


import java.io.*;
import java.util.*;

public class HuffmanTree {

    //stores the nodes right after creation
    ArrayList<HuffmanNode> nodeArray = new ArrayList<>();

    //stores nodes after merging process
    PriorityQueue<HuffmanNode> nodeTree = new PriorityQueue<>(new SortbyFreq());

    //maps characters to their respective encoding
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
            //otherwise it is not
            else
                return false;
        }
    }

    //sorting comparator, sorts by node frequency
    public static class SortbyFreq implements Comparator<HuffmanNode> {
        @Override
        public int compare(HuffmanNode o1, HuffmanNode o2) {
            return o1.frequency - o2.frequency;
        }
    }


    //creates the nodes based on the contents of the inputFile
    public void makeNodes(String inputFile) throws IOException {
        String contents = fileContents(inputFile);

        //HashMap to temporarily store characters and their respective frequency
        HashMap<Character, Integer> tempMap = new HashMap<>();

        //current letter that is being scanned for in the original document
        int curChar = 0;

        //scan the text for standard ascii characters, storing their respective frequency
        for (int i = 0; i <= 255; i++) {
            curChar++;
            //if the character we're on appears at least once, add it to the map
            if (getFrequency(contents, (char) curChar) > 0) {
                tempMap.put((char)curChar, getFrequency(contents, (char) curChar));
            }
        }
        //for each map entry, make a new node with that entry's character and frequency
        tempMap.forEach((k, v) -> nodeArray.add(new HuffmanNode(k, v)));
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
        //if it is a leaf node, it is a character, so map it with it's appropriate code
        if (root.isLeafNode()) {
            map.put(root.inChar, code);
        }
        //if not, recursively traverse down tree appropriately
        else {
            encode(root.left, code + "0");
            encode(root.right, code + "1");
        }
    }


    //display encoding into .txt file
    public void printEncoding() throws IOException {
        FileWriter writer = new FileWriter("Letter Encoding.txt");

        //Disclaimer
        writer.write("Character Encoding is as follows. ");
        writer.write("Note that the blank space is not a mistake, but rather an actual character,\n" +
                "as spaces are scanned and encoded. In addition," +
                " the characters that consist\nof brackets and numbers are simply special characters (new line and carriage return)" +
                " scanned through\nthe method provided by Yuchen on canvas. Here is the encoding" +
                " in the format of character : frequency : encoding -\n");

        //for each entry in the map, get the encoding and then print the character, frequency, and huffman encoding
        for (Map.Entry<Character, String> entry : map.entrySet()) {
            //the character of the current entry
            char curChar = entry.getKey();
            int curFreq = 0;

            //search nodeArray for the appropriate node to get it's frequency, then store it
            for (int i = 0; i < nodeArray.size(); i++) {
                if (nodeArray.get(i).inChar == curChar) {
                    curFreq = nodeArray.get(i).frequency;
                }
            }

            //print everything in readable format
            writer.write(escapeSpecialCharacter(String.valueOf(entry.getKey()))  + " : " + curFreq + " : " + entry.getValue() + '\n');
        }
        writer.close();
    }

    //scan file again and encode each letter
    public void encodeFile(String inputFile, String outputFile) throws IOException {
        //original text
        String original = fileContents(inputFile);

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


    //simpler helper methods below


    //read the file contents and store it to a string.
    public String fileContents(String InputFile) throws IOException {
            FileReader fr = new FileReader(InputFile);

            int index;

            //start with nothing
            String st = "";
            //scan while there are characters left to scan, add to end of string
            while ((index=fr.read()) != -1) {
                st += (char)index;
        }
            return st;
    }

    //find frequencies of each letter in the inputFile
    public int getFrequency(String contents, char letter) throws IOException {
        int count = 0;

        //check each string index, see if it matches inputted character
        for (int j = 0; j < contents.length(); j++) {
            //if it matches, raise frequency count
            if (contents.charAt(j) == letter) {
                   count++;
            }
        }
        return count;
    }

    //Yuchen's method to format special characters
    public static String escapeSpecialCharacter(String x) {
        StringBuilder sb = new StringBuilder();
        for (char c : x.toCharArray()) {
            if (c >= 32 && c < 127) sb.append(c);
            else sb.append(" [0x" + Integer.toOctalString(c) + "]");
        }
        return sb.toString();
    }
}

package com.silauras;

import com.silauras.huffman.Huffman;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Map;
import java.util.Scanner;

public class Main {
    private static final Scanner userInput = new Scanner(System.in);
    private static final Long[] dictionary = new Long[256];
    // cause 2^8 is 256, and also it will be size of our dictionary
    //In the file it will have size 512 bytes, because we need to know count of bites

    public static void main(String[] args) {
        Arrays.fill(dictionary, 0L);
        while (true) {
            System.out.println("Would you code or encode file? c/e");
            switch (userInput.nextLine()) {
                case "c":
                    System.out.println("Write file path: ");
                    codeFile(userInput.nextLine());
                    return;
                case "e":
                    return;
                default:
                    break;
            }
        }
    }

    private static void codeFile(String fileName) {
        try {
            byte[] bytes = Files.readAllBytes(Paths.get(fileName));
            for (byte value : bytes) {
                dictionary[value-Byte.MIN_VALUE]++;
            }
            Huffman huffman = new Huffman(dictionary);
            System.out.println(huffman.getDictionary().toString());
            File file = new File(fileName + ".hfm");
            file.createNewFile();

            FileWriter fileWriter = new FileWriter(file);
            Map<Byte, String> huffmanDictionary = huffman.getDictionary();
            for (int i = 0; i < bytes.length; i++) {
                if (i % 100 == 0) {
                    System.out.println("Processed " + i + " bytes of " + bytes.length);
                }
                try {


                fileWriter.write(huffmanDictionary.get(bytes[i]));
                }catch (NullPointerException e){
                    System.out.println(i);
                    System.out.println(bytes[i] );
                }
            }
            fileWriter.flush();
            fileWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void encodeFile() {

    }


}

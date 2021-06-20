package com.serg.VigenereProgram;


import edu.duke.DirectoryResource;
import edu.duke.FileResource;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;

public class Tester {
    public void testCeasarCipher(){
        FileResource fr = new FileResource("com/serg/VigenereProgram/VigenereTestData/oslusiadas_key17.txt");
        String messageFromFile = fr.asString();
//        String messageFromFile = "Can you imagine life WITHOUT the internet AND computers in your pocket?";
        int testKey = 15;
        CaesarCipher cs = new CaesarCipher(testKey);
        String enc = cs.encrypt(messageFromFile);
        System.out.println("----".repeat(10)+"test testCeasarCipher enc String" +"----".repeat(10));
        System.out.println("----".repeat(10)+"Source String" +"----".repeat(10));
        System.out.println(messageFromFile);
        System.out.println("----".repeat(10)+"Enc String" +"----".repeat(10));
        System.out.println(enc);
        CaesarCracker cc = new CaesarCracker('a');
        int dkey = cc.getKey(enc);
        System.out.println("Founded key is "+dkey+ "----".repeat(10)+"Test key is "+ testKey);
        String brokendString = cc.decrypt(enc);
        System.out.println("----".repeat(10)+"Broken String" +"----".repeat(10));
        System.out.println(brokendString);

    }
    public void testVigenereCipher(){
        String key = "rome";
        int[] arrayKey = new int[key.length()];
        for (int i=0;i<arrayKey.length;i++){
            arrayKey[i]=  key.toLowerCase().charAt(i)-'a';
        }
        VigenereCipher vc = new VigenereCipher(arrayKey);
    }
    public void testSliceString(){
        VigenereBreaker vb = new VigenereBreaker();
        String s = "abcdefghijklm";
        int whichSlice =4;
        int totalSlices =5;
        String resString = vb.sliceString(s,whichSlice,totalSlices);
        System.out.println(resString);
    }
    public void testTryKeyLength(){
        FileResource fr = new FileResource("com/serg/VigenereProgram/messages/secretmessage2.txt");
        String encString =fr.asString();
        VigenereBreaker vb = new VigenereBreaker();
        char pLetter ='a';
        int keyLength = 38;
        int[] keySet = vb.tryKeyLength(encString,keyLength, pLetter);
        for (int i=0;i<keyLength;i++){
            char keyLetter = (char) ((int) 'a'+ keySet[i]);
            System.out.print("|"+i+" is idx "+keySet[i]+" is keyValue;");
        }
        System.out.println();
        for (int i=0;i<keyLength;i++) System.out.print((char) ((int) 'a'+ keySet[i]));
    }

    public void testreadDictionary(){
        FileResource fr = new FileResource("com/serg/VigenereProgram/dictionaries/English");
        VigenereBreaker vb = new VigenereBreaker();
        HashSet<String> engDict = vb.readDictionary(fr);
        FileResource fileResource = new FileResource("com/serg/VigenereProgram/data/smallHamlet.txt");
        String messStr = fileResource.asString();
        int numOfWords = vb.countWords(messStr,engDict);
        System.out.println("number of defined words is "+numOfWords);
    }
    public void testBreakForLanguage(){
        FileResource fr = new FileResource("com/serg/VigenereProgram/dictionaries/English");
        VigenereBreaker vb = new VigenereBreaker();
        HashSet<String> engDict = vb.readDictionary(fr);
        FileResource fileResource = new FileResource("com/serg/VigenereProgram/messages/secretmessage2.txt");
        String messStr = fileResource.asString();
        char mLetter = 'e';
        String decMess = vb.breakForLanguage(messStr,engDict, mLetter);
        System.out.println(decMess);
    }
    public void testBreakVigenere(){
        VigenereBreaker vb = new VigenereBreaker();
        vb.breakVigenere();
    }
    public void testlastTask(){
        FileResource fr = new FileResource("com/serg/VigenereProgram/dictionaries/Italian");
        String encString =fr.asString();
        VigenereBreaker vb = new VigenereBreaker();
        char pLetter ='e';
        int keyLength = 38;
        int[] keySet = vb.tryKeyLength(encString,keyLength, pLetter);

        FileResource frDict = new FileResource("com/serg/VigenereProgram/dictionaries/English");
        HashSet<String> engDict = vb.readDictionary(frDict);
        VigenereCipher vc = new VigenereCipher(keySet);
        String decString = vc.decrypt(encString);

        int numOfWords = vb.countWords(decString,engDict);
        System.out.println("number of defined words is "+numOfWords);
    }
    public void testMostCommonCharIn(){
        VigenereBreaker vb = new VigenereBreaker();
        FileResource frDict = new FileResource("com/serg/VigenereProgram/dictionaries/Portuguese");
        HashSet<String> engDict = vb.readDictionary(frDict);
        char mostCommonCh = vb.mostCommonCharIn(engDict);
        System.out.println("the most common char is "+ mostCommonCh);
    }
    public void testBreakForAllLangs(){
        VigenereBreaker vb = new VigenereBreaker();
        FileResource fileResource = new FileResource("com/serg/VigenereProgram/messages/secretmessage2.txt");
        String messStr = fileResource.asString();
        DirectoryResource dirDict = new DirectoryResource();
        HashMap<String,HashSet<String>> languages = new HashMap<String, HashSet<String>>();
        for (File file: dirDict.selectedFiles()){
            FileResource fr = new FileResource(file);
            HashSet<String> lngDict = vb.readDictionary(fr);
            languages.put(file.getName().toLowerCase(),lngDict);
        }
        vb.breakForAllLangs(messStr,languages);
    }
}

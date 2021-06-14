package com.serg.Codon;

import edu.duke.DirectoryResource;
import edu.duke.FileResource;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class WordsInFiles {
    private HashMap<String, ArrayList> map;
    private HashMap<String,Integer> uniqueWords;
    public WordsInFiles(){
        map = new HashMap<String, ArrayList>();
        uniqueWords = new HashMap<String, Integer>();
        // map - ключ имя файла Значение моассив из слов
        // uniqueWords Ключ слово Значение количество повторений

    }
    private void addWordsFromFile (File f){
        FileResource fr = new FileResource(f);
        ArrayList<String> myWords= new ArrayList<String>();
        for (String word: fr.words())
            myWords.add(word);
        map.put(f.getName(),myWords);
    }
    private void buildWordFileMap(){
        map.clear();
        DirectoryResource dr = new DirectoryResource();
        for (File f: dr.selectedFiles()){
            addWordsFromFile(f);
        }
    }
    private int maxNumber(){
        uniqueWords.clear();
        for (String f: map.keySet()){
            ArrayList<String> list = map.get(f);
            for (int k=0;k<list.size();k++){
                String word = list.get(k);
                if (!uniqueWords.containsKey(word)){
                    uniqueWords.put(word,1);
                    }
                else {
                    uniqueWords.replace(word,uniqueWords.get(word)+1);
                }
            }
        }
//        for (String w: uniqueWords.keySet()){
//            System.out.println("Word: "+ w+ " -"+ "\t"+ uniqueWords.get(w));
//        }

        return Collections.max(uniqueWords.values());
    }
    private ArrayList<String> wordsInNumFiles(int number){
        ArrayList<String> list = new ArrayList<String>();
        for (String w: uniqueWords.keySet()){
            int counter =0;
            for (String fnmae: map.keySet()){
                if (map.get(fnmae).contains(w)) counter++;
            }
            if (counter==number) list.add(w);
        }
        return list;
    }
    private void printFilesIn(String word){
        for (String fname: map.keySet()){
            if (map.get(fname).contains(word))
                System.out.println(fname);
        }
    }
    public void tester(){
        buildWordFileMap();
        int maxWordCounts= maxNumber();
        System.out.println("Max word occurs is "+maxWordCounts);
        int nunFiles =4;
        ArrayList<String>wInNumF = wordsInNumFiles(nunFiles);
        System.out.println(wInNumF.size());
//        for (String w: wInNumF)
//            System.out.println(w);
        String testWord= "tree";
        printFilesIn(testWord);
    }

}

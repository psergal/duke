package com.serg.Codon;

import edu.duke.FileResource;

import java.util.HashMap;

public class CodonCounts {
    private HashMap<String,Integer> map;

    public CodonCounts(){
        map = new HashMap<String, Integer>();

    }
    private void buildCodonMap(int start, String dna){
        map.clear();
        dna = dna.substring(start);
        String[] geneList;
        geneList = dna.split("(?<=\\G.{3})") ;
        for (String gene: geneList){
            if (gene.length()<3) break;
            if (!map.containsKey(gene))
                map.put(gene,1);
            else
                map.put(gene,map.get(gene)+1);
        }
    }
    private String getMostCommonCodon(){
        int maxCodon = 0;
        String finalCodon = null;
        for (String codon: map.keySet()){
            if (map.get(codon)>maxCodon) {
                maxCodon = map.get(codon);
                finalCodon = codon;
            }
        }
        return finalCodon;
    }
    private void printCodonCounts(int start, int stop){
        for (String codon: map.keySet()){
            if (map.get(codon)>= start && map.get(codon)<=stop)
            System.out.println(map.get(codon)+"\t"+codon);
        }
    }
    public void testBuildCodonMap (){
        FileResource fr = new FileResource("com/serg/Codon/data/dnaMystery2.txt");
        String geneChain = fr.asString().toUpperCase();
//        String geneChain = "CGTTCAAGTTCAA";

        for (int k=0; k<=2; k++) {
            buildCodonMap(k, geneChain);
            System.out.println("the total number of unique codon is "+ map.size());
            String mostCommonCodon = getMostCommonCodon();
            System.out.println("most common codon is - " + mostCommonCodon+ " and max counts is "+ map.get(mostCommonCodon));
            int start = 6;
            int stop = 100;
            printCodonCounts(start, stop);
        }
    }
}

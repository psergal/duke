package com.serg.GladLibMap;

import edu.duke.FileResource;
import edu.duke.URLResource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class GladLibMap {
	private ArrayList<String> usedWords;
	private HashMap<String,ArrayList> myMap;
	private HashMap<String,Integer> usedCategories;

	private Random myRandom;

	private static String dataSourceURL = "http://dukelearntoprogram.com/course3/data";
	private static String dataSourceDirectory = "com/serg/GladList/datalong";

	public GladLibMap(){
		myMap = new HashMap<String, ArrayList>();
		usedCategories = new HashMap<String, Integer>();
		initializeFromSource(dataSourceDirectory);
		usedWords = new ArrayList<String>();


		usedWords.clear();
		myRandom = new Random();

	}

	public GladLibMap(String source){
		initializeFromSource(source);
		myRandom = new Random();
	}
	
	private void initializeFromSource(String source) {

		String[] categories = {"adjective","noun","color","country","name","animal","timeframe","verb","fruit"};
		for (int k=0;k<categories.length;k++) {
			String resource = source + "/" + categories[k] + ".txt";
			myMap.put(categories[k]+"List",readIt(resource));
			usedCategories.put(categories[k],0);
		}
	}
	
	private String randomFrom(ArrayList<String> source){
		int index = myRandom.nextInt(source.size());
		return source.get(index);
	}
	private int totalWordsInMap(){
		int wordCounter =0;
		for (String fname: myMap.keySet())
			wordCounter+=myMap.get(fname).size();
		return wordCounter;
	}
	private int totalWordsConsidered(){
		int allWordsUsedInCats=0;
		for (String cat: usedCategories.keySet()){
			if (usedCategories.get(cat)>0)
				allWordsUsedInCats+=myMap.get(cat+"List").size();
		}
		return allWordsUsedInCats;
	}

	private String getSubstitute(String label) {
		if (label.equals("number"))
			return ""+myRandom.nextInt(50)+5;
		if (myMap.containsKey(label+"List"))
			return randomFrom(myMap.get(label+"List"));
		return "**UNKNOWN**";
	}
	
	private String processWord(String w) {
		int first = w.indexOf("<");
		int last = w.indexOf(">", first);
		if (first == -1 || last == -1) {
			return w;
		}
		String prefix = w.substring(0, first);
		String suffix = w.substring(last + 1);
		while (true) {
			String cat = w.substring(first + 1, last);
			String sub = getSubstitute(cat);
			if (usedWords.indexOf(sub)==-1){
				usedWords.add(sub);
				if (!cat.equals("number"))
					usedCategories.put(cat,usedCategories.get(cat)+1);
				return prefix+sub+suffix;
			}
			else System.out.println("hit the same "+ sub+ "\t"+ w);
		}
	}
	
	private void printOut(String s, int lineWidth){
		int charsWritten = 0;
		for(String w : s.split("\\s+")){
			if (charsWritten + w.length() > lineWidth){
				System.out.println();
				charsWritten = 0;
			}
			System.out.print(w+" ");
			charsWritten += w.length() + 1;
		}
	}
	
	private String fromTemplate(String source){
		String story = "";
		if (source.startsWith("http")) {
			URLResource resource = new URLResource(source);
			for(String word : resource.words()){
				story = story + processWord(word) + " ";
			}
		}
		else {
			FileResource resource = new FileResource(source);
			for(String word : resource.words()){
				story = story + processWord(word) + " ";
			}
		}
		return story;
	}
	
	private ArrayList<String> readIt(String source){
		ArrayList<String> list = new ArrayList<String>();
		if (source.startsWith("http")) {
			URLResource resource = new URLResource(source);
			for(String line : resource.lines()){
				list.add(line);
			}
		}
		else {
			FileResource resource = new FileResource(source);
			for(String line : resource.lines()){
				list.add(line);
			}
		}
		return list;
	}
	
	public void makeStory(){
	    System.out.println("\n");
		String story = fromTemplate("com/serg/GladList/data/madtemplate2.txt");
		printOut(story, 60);
		for(String usedWord: usedWords){
			System.out.println(usedWord);
		}
		int totalWords = totalWordsInMap();
		System.out.println("total number of words in al categories are "+ totalWords);
		int wInUsedCats=totalWordsConsidered();
		System.out.println("total Words Considered is "+wInUsedCats);
	}


}

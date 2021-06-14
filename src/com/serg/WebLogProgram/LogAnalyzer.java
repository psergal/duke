package com.serg.WebLogProgram;
/**
 * Write a description of class LogAnalyzer here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import edu.duke.FileResource;

import java.util.ArrayList;
import java.util.HashMap;

public class LogAnalyzer
{
     private ArrayList<LogEntry> records;
     
     public LogAnalyzer() {
         records = new ArrayList<LogEntry>();
     }
        
     public void readFile(String filename) {
         FileResource fr = new FileResource(filename);
         for (String line: fr.lines()){
             records.add(WebLogParser.parseEntry(line));
         }
     }
     public int countUniqueIps (){
         ArrayList<String> uniqueIps = new ArrayList<String>();
         for (LogEntry logEntry: records){
             if(!uniqueIps.contains(logEntry.getIpAddress()))
                 uniqueIps.add(logEntry.getIpAddress());
         }
         return uniqueIps.size();
     }
        
     public void printAll() {
         for (LogEntry le : records) {
             System.out.println(le);
         }
     }
     //print those LogEntrys that have a status code greater than num
     public void printAllHigherThanNum(int num){
         for (LogEntry logEntry: records){
             if (logEntry.getStatusCode()>num)
                 System.out.println(logEntry);
         }
     }
     //method accesses the web logs in records and returns an ArrayList
     // of Strings of unique IP addresses that had access on the given day
     public ArrayList<String> uniqueIPVisitsOnDay(String someday){
         ArrayList<String> listOfDayUniqIPs = new ArrayList<String>();
         for (LogEntry logEntry: records){
             String logDate = logEntry.getAccessTime().toString();
             if(logDate.contains(someday) && !listOfDayUniqIPs.contains(logEntry.getIpAddress())){
                 listOfDayUniqIPs.add(logEntry.getIpAddress());
             }
         }
        return listOfDayUniqIPs;
     }
     public int countUniqueIPsInRange(int low, int high){
         ArrayList<String> uniqRangeIps = new ArrayList<String>();
         for (LogEntry le : records){
             int statCode = le.getStatusCode();
             if (statCode>=low && statCode<=high){
                 if (!uniqRangeIps.contains(le.getIpAddress())) uniqRangeIps.add(le.getIpAddress());
             }
         }
         return uniqRangeIps.size();
     }
     public HashMap<String,Integer> countVisitsperIP(){
         HashMap<String,Integer> counts = new HashMap<String, Integer>();
         for (LogEntry le: records){
             String ip = le.getIpAddress();
             if (! counts.containsKey(ip)) counts.put(ip,1);
             else counts.put(ip,counts.get(ip)+1);
         }
         return counts;
     }
     public int mostNumberVisitsByIP (HashMap<String,Integer> visits){
         int maxVisits=0;
         for (String ip: visits.keySet())
             maxVisits = Math.max(maxVisits,visits.get(ip));
         return maxVisits;
     }
     public ArrayList<String> iPsMostVisits(HashMap<String,Integer> visits){
         int maxVisits=0;
         for (String ip: visits.keySet())
             maxVisits = Math.max(maxVisits,visits.get(ip));
         ArrayList<String> ipList = new ArrayList<String>();
         for (String ip: visits.keySet()){
             if (visits.get(ip)==maxVisits)
                 ipList.add(ip);
         }
         return ipList;
     }
     public HashMap<String,ArrayList<String>> iPsForDays(){
         HashMap<String,ArrayList<String>> ipPerDays = new HashMap<String, ArrayList<String>>();
         for (LogEntry le: records){
             String dayinM = le.getAccessTime().toString().substring(4,10);
             if (! ipPerDays.containsKey(dayinM)) {
                 ArrayList<String> ipList =new ArrayList<String>();
                 ipList.add(le.getIpAddress());
                 ipPerDays.put(dayinM,ipList);
             }
             else {
                 ArrayList<String> ipList = ipPerDays.get(dayinM);
                 ipList.add(le.getIpAddress());
                 ipPerDays.put(dayinM,ipList);
             }
         }
         return ipPerDays;
     }
     public String dayWithMostIPVisits(HashMap<String,ArrayList<String>> ipPerDays){
         int dayCounter =0;
         String mostDay = null;
         for (String day: ipPerDays.keySet()){
             int currDayCount = ipPerDays.get(day).size();
             if (currDayCount>dayCounter){
                 mostDay=day;
                 dayCounter=currDayCount;
             }
         }
         return mostDay;
     }
}

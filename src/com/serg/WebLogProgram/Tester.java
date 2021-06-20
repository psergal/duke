package com.serg.WebLogProgram;
/**
 * Write a description of class Tester here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import java.util.*;

public class Tester
{
    public void testLogEntry() {
        LogEntry le = new LogEntry("1.2.3.4", new Date(), "example request", 200, 500);
        System.out.println(le);
        LogEntry le2 = new LogEntry("1.2.100.4", new Date(), "example request 2", 300, 400);
        System.out.println(le2);
    }
    public void testUniqIps(){
        LogAnalyzer la = new LogAnalyzer();
        la.readFile("src/com/serg/WebLogProgram/weblog2_log");
        int uniqIPs = la.countUniqueIps();
        System.out.println("There are "+ uniqIPs+ " IPs");
    }

    public void testLogAnalyzer() {
        // complete method
        LogAnalyzer la = new LogAnalyzer();
        la.readFile("src/com/serg/WebLogProgram/weblog1_log");
        //la.printAll();
        int code = 400;
        System.out.println("----".repeat(10)+"greater than "+ code+"----".repeat(10));
        la.printAllHigherThanNum(code);
        System.out.println("----".repeat(15));
    }
    public void testUniqueIPVisitsOnDay(){
        String oneDay = "Sep 27";
        LogAnalyzer la = new LogAnalyzer();
        la.readFile("src/com/serg/WebLogProgram/weblog2_log");
        System.out.println("----".repeat(10)+"Test for day "+ oneDay+"----".repeat(10));
        ArrayList<String> uniqIpList = la.uniqueIPVisitsOnDay(oneDay);
        for (String ip: uniqIpList)
            System.out.println(ip);
        System.out.println("Size of Array:ist is "+uniqIpList.size());
    }
    public void testCountUniqueIPsInRange(){
        int from = 200;
        int to = 299;
        LogAnalyzer la = new LogAnalyzer();
        la.readFile("src/com/serg/WebLogProgram/weblog2_log");
        System.out.println("----".repeat(10)+"Test for range  from "+ from+ " to "+to+"----".repeat(10));
        int uniqIpRangeCount = la.countUniqueIPsInRange(from,to);
        System.out.println("There are "+ uniqIpRangeCount + " in a range");
    }
    public void testCountVisitsperIP(){
        LogAnalyzer la = new LogAnalyzer();
        la.readFile("src/com/serg/WebLogProgram/weblog2_log");
        System.out.println("----".repeat(10)+"Test counts unique Visits per IP" +"----".repeat(10));
        HashMap<String,Integer> visitorList = la.countVisitsperIP();
        for (String ip: visitorList.keySet()){
            System.out.println(ip+"\t"+ visitorList.get(ip));
        }
        System.out.println("----".repeat(10)+"The number of unique Visits per IP is "+ visitorList.size() +"----".repeat(10));
        int maxVisits=la.mostNumberVisitsByIP(visitorList);
        System.out.println("----".repeat(10)+"The most number of visits per IP is "+ maxVisits +"----".repeat(10));
        System.out.println("----".repeat(10)+"The list of most visited IP "+"----".repeat(10));
        ArrayList<String> ipListMost = la.iPsMostVisits(visitorList);
        for (String ip: ipListMost) System.out.println(ip);

    }
    //IpsForDays returns a HashMap<String, ArrayList<String>> that uses records and maps days from web logs
    // to an ArrayList of IP addresses that occurred on that day (including repeated IP addresses)

    // his method returns the day that has the most IP address visits. If there is a tie, then return any such day
    public void testIpsForDays(){
        LogAnalyzer la = new LogAnalyzer();
        la.readFile("src/com/serg/WebLogProgram/weblog2_log");
        System.out.println("----".repeat(10)+"Test list of IP per day" +"----".repeat(10));
        HashMap<String,ArrayList<String>> daysIpList = la.iPsForDays();
        for (String day: daysIpList.keySet()){
            System.out.println("----".repeat(5)+day+"----".repeat(5));
            ArrayList<String> ipList=daysIpList.get(day);
            for (String ip:ipList) System.out.println(ip);
        }
        System.out.println("----".repeat(10)+"Test day With Most IP Visits" +"----".repeat(10));
        String dayWithMostIP = la.dayWithMostIPVisits(daysIpList);
        System.out.println("This is a date "+ dayWithMostIP);
    }
    public void testIPsWithMostVisitsOnDay(){
        LogAnalyzer la = new LogAnalyzer();
        la.readFile("src/com/serg/WebLogProgram/weblog2_log");
        System.out.println("----".repeat(10)+"test IPs With Most Visits OnDay" +"----".repeat(10));
        HashMap<String,ArrayList<String>> daysIpList = la.iPsForDays();
        String oneday = "Sep 29";
        ArrayList<String> daysMostIps = la.iPsWithMostVisitsOnDay(daysIpList,oneday);
        System.out.println(daysMostIps);
    }
}

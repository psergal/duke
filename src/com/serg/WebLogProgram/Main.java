package com.serg.WebLogProgram;

public class Main {
    public static void main(String[] args) {
        //System.out.println("----".repeat(10)+"test log entry"+"----".repeat(10));
        Tester tester = new Tester();
        //tester.testLogEntry();
        System.out.println("----".repeat(10)+"test log analyzer"+ "----".repeat(10));
        tester.testLogAnalyzer();
        tester.testUniqIps();
        tester.testUniqueIPVisitsOnDay();
        tester.testCountUniqueIPsInRange();
        tester.testCountVisitsperIP();
        tester.testIpsForDays();

    }
}

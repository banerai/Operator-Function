/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package operator.function;

import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Arjun Banerjee
 */
/* This code was created because of my obsession of looking at the clock
 * and trying to figure out, given a certain digit in the clock, how many operations
 * it would take for the other digits in the clock to result in that given digit.
 * I am taking any integer whose size is greater than / equal to 3 digits and
 * finding how many operations it would take to reach a certain digit in that 
 * number with the other digits.
*/
public class OperatorFunction {
    //Arraylist that will take the digits and do operations on them
    //Operations will modify the arraylist itself
    private static ArrayList<Integer> numbers = new ArrayList<Integer>();
    //"writtenOut" provides how the operations were done for each number
    //and the digit's writtenOut will be called once reached
    private static ArrayList<String> writtenOut = new ArrayList<String>();
    //saves the sizes of "numbers"
    private static ArrayList<Integer> counts = new ArrayList<Integer>();
    //counts number of operations
    private static int count = 0;
    //stores the desired digit in number
    private static int digit = 0;
    //stores the operation sequence
    private static String operation = "";
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Scanner one = new Scanner(System.in);
        System.out.println("State number.");
        int number = one.nextInt();
        System.out.println("Designate index.");
        int index = one.nextInt();
        System.out.println();
        starter(number, index);
        modifier();
        System.out.println("\nOperator Function " + index + "(" + number + ") = " + count + ".\nOperation is " + operation + " = " + digit);
    }
    
    //puts the number you gave inside "numbers"
    //int index is the desired index of the digit to reach in number
    public static void starter(int number, int index){
        String numberString = "";
        String store = "";
        numberString += number;
        Scanner sc = new Scanner(numberString);
        for(int i = 0; i < numberString.length(); i++){
            numbers.add(Integer.parseInt("" + numberString.charAt(i)));
            writtenOut.add("" + numberString.charAt(i));
        }
        digit = numbers.get(index - 1);
        numbers.remove(index - 1);
        writtenOut.remove(index - 1);
        //initialize counts
        for(int i = 0; i < numbers.size(); i++){
            counts.add(0);
        }
        System.out.println("Original Set: " + numbers);
    }
    
    //Recursive operation that modifies "numbers" with operations from other
    //the other numbers in "numbers" until reaching the desired digit
    public static void modifier(){
        //this will break the recursion and initialize final variables once the desired index of number is reached
        for(int i = 0; i < numbers.size(); i++){
            if(numbers.get(i) == digit){
                count = counts.get(i);
                operation = writtenOut.get(i);
                return;
            }
        }
        //all operations within "numbers" added to a temporary arraylist "temp"
        //before scanning for redundance
        ArrayList<Integer> temp = new ArrayList<Integer>();
        ArrayList<Integer> tempCounts = new ArrayList<Integer>();
        ArrayList<String> tempString = new ArrayList<String>();
        for(int i = 0; i < numbers.size(); i++){
            for(int j = 0; j < numbers.size(); j++){
                if(j != i){
                    temp.add(numbers.get(i) + numbers.get(j));
                    //adding steps to "tempString"
                    tempString.add("(" + writtenOut.get(i) + " + " + writtenOut.get(j) + ")");
                    temp.add(numbers.get(i) * numbers.get(j));
                    tempString.add("(" + writtenOut.get(i) + " * " + writtenOut.get(j) + ")");
                    temp.add(numbers.get(i) - numbers.get(j));
                    tempString.add("(" + writtenOut.get(i) + " - " + writtenOut.get(j) + ")");
                    //adding previous counts in + the new operation
                    for(int k = 0; k < 3; k++){
                        tempCounts.add(counts.get(i) + counts.get(j) + 1);
                    }
                    if(numbers.get(j) != 0){
                        if(numbers.get(i) % numbers.get(j) == 0){
                            //seperate if clauses
                            temp.add(numbers.get(i) / numbers.get(j));
                            tempString.add("(" + writtenOut.get(i) + " / " + writtenOut.get(j) + ")");
                            tempCounts.add(counts.get(i) + counts.get(j) + 1);
                        }
                        temp.add(numbers.get(i) % numbers.get(j));
                        tempString.add("(" + writtenOut.get(i) + " % " + writtenOut.get(j) + ")");
                        tempCounts.add(counts.get(i) + counts.get(j) + 1);
                    }
                } 
            }
        }
        scan(temp, tempCounts, tempString);
        //data recieved
        System.out.println("Numbers" + ": " + numbers);
        System.out.println("Operations: " + writtenOut);
        System.out.println("Counts: " + counts);
        //recurse
        modifier();
    }
    
    //Scans for whether the numbers in "temp" exist in "numbers" and then adds
    //the new numbers to "numbers"
    public static void scan(ArrayList<Integer> temp, ArrayList<Integer> tempCounts, ArrayList<String> tempString){
        //removes redundancies in temp itself
        for(int i = 0; i < temp.size(); i++){
            for(int j = 0; j < temp.size(); j++){
                if(temp.get(j) == temp.get(i) && j != i){
                    temp.remove(j);
                    tempCounts.remove(j);
                    tempString.remove(j);
                    j--;
                }
            }
        }
        //modifies "temp" compared to "numbers" and adds new data to it
        for(int i = 0; i < numbers.size(); i++){
            for(int j = 0; j < temp.size(); j++){
                if(temp.get(j) == numbers.get(i)){
                    temp.remove(j);
                    tempCounts.remove(j);
                    tempString.remove(j);
                    j--;
                }
            }
        }
        //adds the new "temp" to "numbers"
        for(int i = 0; i < temp.size(); i++){
            numbers.add(temp.get(i));
            counts.add(tempCounts.get(i));
            writtenOut.add(tempString.get(i));
        }
    }
    
}

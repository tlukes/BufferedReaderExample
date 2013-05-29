/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bufferedreaderexample;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;

/**
 *
 * @author Tyson Lukes
 * 5/27/13
 *  
DEVELOPER TEST:
 
1) In the attached file (w_data.dat), you’ll find daily weather data.   
* Download this text file, then write a program to output the day number (column one) with 
* the smallest temperature spread (the maximum temperature is the second column, the minimum the third column).
 
2) The attached soccer.dat file contains the results from the English Premier League.  
* The columns labeled ‘F’ and ‘A’ contain the total number of goals scored for and against each team in that season 
* (so Arsenal scored 79 goals against opponents, and had 36 goals scored against them). 
* Write a program to print the name of the team with the smallest difference in ‘for’ and ‘against’ goals.
 
Is the way you wrote the second program influenced by writing the first?
 */
public class BufferedReaderExample {
    static String Description;
    static int Spread;
    static int DescriptionValue; //column to look for to get the description day or team name
    static int MinValue; //column 1 we want to look at
    static int MaxValue; //column 2 we want to look at
    static int startValue; //beginning rows of data we want to look at
    static int stopValue; //last row of data in files
    static boolean bSoccerFile;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Description = "";
        Spread = -1;
        //set up values to process the weather data file
        DescriptionValue = 0;
        MinValue = 1;
        MaxValue = 2;
        //start looking at these lines for weather data
        startValue = 7;
        stopValue = 37;
        bSoccerFile = false;
        ProcessFile("C:\\Projects\\BufferedReaderExample\\w_data.dat");
        DisplayResults();
        
        //set up for soccer file process
        DescriptionValue = 1;
        MinValue = 6;
        MaxValue = 8;
        //start looking at these lines for soccer data
        startValue = 4;
        stopValue = 25;
        bSoccerFile = true;
        ProcessFile("C:\\Projects\\BufferedReaderExample\\soccer.dat");
        DisplayResults();
    }
    
    //ProcessFile - opens the file and reads each string to pass along for parsing out important data
    private static void ProcessFile(String sFile){
        int iLineNumber = 0;
        BufferedReader br = null;
        
        try {
            String sCurrentLine;
            br = new BufferedReader(new FileReader(sFile)); 
            while ((sCurrentLine = br.readLine()) != null) {
                iLineNumber++;
                 if (!"".equals(sCurrentLine) && (iLineNumber >= startValue) && (iLineNumber < stopValue)){
                        ProcessString(sCurrentLine);
                 }//end if      
            }//end while
        }catch (IOException e) {
            e.printStackTrace();
	} finally {
            try {
                if (br != null){
                    br.close();
                }//end if
            } catch (IOException ex) {
		ex.printStackTrace();
            }
        }
    }//end ProcessFile
    
    //Display results for either file being processed
    private static void DisplayResults(){
        if (bSoccerFile){
           //smallest difference in ‘for’ and ‘against’ goals
           System.out.println("Results for soccer.dat:");
           System.out.println("Team " + Description + " had a scoring difference of: " + Spread);
        } else {
           //the smallest temperature spread
           System.out.println("Results for w_data.dat:");
           System.out.println("Day " + Description + " had a mean temp of: " + Spread); 
        }
        System.out.println();
    }
    
    /*
     * ProcessString, handles getting the data out of the string being passed to it
     * sDescription can either represent the Day info from weather or city info from soccer file
     */
    private static void ProcessString(String sData){
        String sToken = "";
        StringTokenizer st;
        st = new StringTokenizer(sData);
        int iCount = 0;
        String sDescription = "";
        int iMax = 0;
        int iMin = 0;
        if (CountTokens(sData) > 1) { //find out how many tokens are in the string
            while (st.hasMoreElements()) {
           sToken = st.nextElement().toString();
           if (iCount == DescriptionValue){
               sDescription = sToken;
           }
           if (isInteger (sToken)) {
             if (iCount == MinValue) {
               iMin = GetValues(sToken);  
            } else if(iCount == MaxValue){
                iMax = GetValues(sToken);
            } 
           }//end if
            iCount++;  
            }
        DetermineSpread(sDescription, iMax, iMin);
        }//end if CountTokens
        
    }
   
    //DetermineSpread - calculates the value out to determine if it should be saved
    private static void DetermineSpread(String sDescrip, int iMax, int iMin){
        int iSpread = 0;
        iSpread = Math.abs(iMax - iMin); //getting the absolute value
        if ((iSpread < Spread) | (Spread == -1)){ //if Spread is -1 then it's the first value so just store it
            Description = sDescrip;
            Spread = iSpread;
        }
    }
    //GetValues - determines the values needed to process the value
    private static int GetValues(String sData) {
        int iReturnValue = -1;
        
        try {
            iReturnValue = Integer.parseInt(sData);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        } finally {
           //iReturnValue = -1; 
        } 
       
        return iReturnValue;
    }
    
    //check if the value is an integer
    private static boolean isInteger(String sData) {
        boolean bIsInteger;
        try {
            Integer.parseInt(sData);
            bIsInteger = true;
        }catch (NumberFormatException e) {
            bIsInteger = false;
        }
        return bIsInteger;
    }
    
    //method to count the number of tokens in a given string
    private static int CountTokens(String sData){
        StringTokenizer st;
        st = new StringTokenizer(sData);
        String sToken;
        int iCount = 0;
        while (st.hasMoreElements()) {
            //advance to the next element for counting
            sToken = st.nextElement().toString();
            iCount++;
        }
        return iCount;
    }
}//end class BufferedReader


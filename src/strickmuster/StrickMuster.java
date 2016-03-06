/*
 * free for use for everybody without any restrictions  - no special licens selected
 * use at your own risk
 * Author: A. Jehle
 */
package strickmuster;

/**
 *
 * @author andi
 */
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

/**
 * Assumes UTF-8 encoding. JDK 7+.
 */
public class StrickMuster {

  public static void main(String... aArgs) throws IOException {
    StrickMuster parser = new StrickMuster("src/strickmuster.txt");
    parser.processLineByLine();
    log("Done.");
  }
  
  /**
   Constructor.
   @param aFileName full name of an existing, readable file.
  */
  public StrickMuster(String aFileName){
    fFilePath = Paths.get(aFileName);
  }
  
  
  /** Template method that calls {@link #processLine(String)}.  */
  public final void processLineByLine() throws IOException {
    try (Scanner scanner =  new Scanner(fFilePath, ENCODING.name())){
      while (scanner.hasNextLine()){
        String myLine = processLine(scanner.nextLine());
        System.out.println(myLine);
      }      
    }
  }
  
  /** 
   Overridable method for processing lines in different ways.
    
  
     * @param aLine
     * @return 
  */
  protected String processLine(String aLine){
    //use a second Scanner to parse the content of each line 
    char [] linearray = new char[70];   
   
    Scanner scanner = new Scanner(aLine);
    scanner.useDelimiter(" ");
    if (scanner.hasNext()){
      //assumes the line has a certain structure
      String name = scanner.next();
      //check if new line
      int row = getIntFromString(name,"\\.");
      if (row != 0) {
          //System.out.println();
                // check if Masche xMli orxMre
                int linecount = 0;
                while (scanner.hasNext()) {
                    String masche = scanner.next();
                    int anzMaschen = getIntFromString(masche, "M");
                    if (anzMaschen != 0) {
                        //now we have Maschen
                        Boolean links = isValueInString(masche, "M", "li");

                  for (int i=0;i<anzMaschen;i++) {
                      if (row%2 == 0) {
                         if (links) {
                                    linearray[linecount + 5] = ' ';
                                } else {
                                    linearray[linecount + 5] = 'X';
                                }
                            } else {
                                if (links) {
                                    linearray[64 - linecount] = 'X';
                                } else {
                                    linearray[64 - linecount] = ' ';
                                }
                                //System.out.print("X");
                            }
                            linecount++;
                        }
                    }
                }
            }

 
    }
    else {
      log("Empty or invalid line. Unable to process.");
    }
    return String.copyValueOf(linearray);
    }

    // PRIVATE 
    private final Path fFilePath;
    private final static Charset ENCODING = StandardCharsets.UTF_8;

    private static void log(Object aObject) {
        System.out.println(String.valueOf(aObject));
    }

    private int getIntFromString(String name, String delim) {
        Scanner scanner = new Scanner(name);
        Scanner useDelimiter = scanner.useDelimiter(delim);
        if (scanner.hasNextInt()) {
            //String rowString = scanner.next();
            int row = scanner.nextInt();

            //System.out.println();
            //System.out.print(row); System.out.print(". :");
            return row;
        }
        return 0;
    }

    private boolean isValueInString(String name, String delim, String value) {
        Boolean testBool = false;
        Scanner scanner = new Scanner(name);
        Scanner useDelimiter = scanner.useDelimiter(delim);
        if (scanner.hasNext()) {
            //String rowString = scanner.next();
            String dummy = scanner.next();
            String checkString = scanner.next();
            if (checkString.equalsIgnoreCase(value)) {
                testBool = true;
            }
        }
        return testBool;
    }
}

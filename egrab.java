import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

/** 
 * Grabs the name associated to an emailid on the ecs.soton people domain.
 * Scrubs the html of the website for the name and returns said name if found.
 */
public class egrab {
  public static void main(String args[]) throws IOException {
    //url is the website location with the email id appended
    URL url = new URL("https://www.ecs.soton.ac.uk/people/" + args[0]);
    //inputStream created for the url
    InputStream is = (InputStream) url.getContent();
    //buffered reader br is used
    BufferedReader br = new BufferedReader((new InputStreamReader(is)));
    //line will store each line of the html
    String line = null;
    //sb will store every line in the html we care about
    StringBuffer sb = new StringBuffer();
    //line is set to every line off the html
    //Every line is checked until line is null, signifying the end of the html is reached
    while((line = br.readLine()) != null) {
      //if the line contains the name of the person
      if (line.contains("property=\"name\">"))
      {
        //append it to sb
        sb.append(line);
      }
    }
    //html stores the html lines that stored the name of the person, in string form
    String htmlContent = sb.toString();
    //nameIndexStart stores the starting index of the persons name
    //Their name will begin 18 characters after the phrase
    int nameIndexStart = htmlContent.indexOf("property=\"name\">") + 18;
    //if property="name"> was found in the string
    if (nameIndexStart > 18) {
      //character holds the char at a given index in htmlContent
      char character = htmlContent.charAt(nameIndexStart);
      //nameIndexEnd will store the index at the end of the name
      //It is first set to nameIndexStart
      int nameIndexEnd = nameIndexStart;
      //while character has not reached the end of the name
      while(character != '<') {
        //increment nameIndexEnd
        nameIndexEnd++;
        //set character to the next char is the name
        character = htmlContent.charAt(nameIndexEnd);
        
      }
      //substring name is retrieved using the teo indexes
      String name = htmlContent.substring(nameIndexStart, nameIndexEnd);
      //the name has been found and is printed
      System.out.println(name);
    }
    //If nameIndexStart is 0 or -1, the name couldn't be found in htmlContent
    else {
      //Failure to retrieve the name is output
      System.out.println("Could not find an associated name");
    }
  }
}
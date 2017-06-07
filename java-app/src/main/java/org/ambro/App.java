package org.ambro;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.LineNumberReader;
import java.net.URL;
import java.util.Properties;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
    	// Read from the packaged properties file.
    	try {
			InputStream istream = App.class.getResourceAsStream("/src/main/resources/config.txt");
			Properties props = new Properties();
			props.load(istream);
			String name = props.getProperty("name");
	        System.out.println( "Hello, "+name+"!" );
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
    }
}

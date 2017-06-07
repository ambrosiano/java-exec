package org.ambro;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

import org.geotools.data.shapefile.ShapefileDataStore;
import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.data.simple.SimpleFeatureSource;

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
    		// Read a single file from an input stream.
			InputStream istream = App.class.getResourceAsStream("/config.txt");
			Properties props = new Properties();
			props.load(istream);
			String name = props.getProperty("name");
	        System.out.println( "Hello, "+name+"!" );
	        
	        // Read a shapefile from a URL.
	        URL url = App.class.getResource("/coastline/states.shp");
			ShapefileDataStore inDataStore = new ShapefileDataStore(url);
			SimpleFeatureSource inFeatureStore = (SimpleFeatureSource) inDataStore.getFeatureSource();
			SimpleFeatureCollection inFeatureCollection = inFeatureStore.getFeatures();
			System.out.println(inFeatureCollection.size()+" shapefile features read.");
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
    }
}

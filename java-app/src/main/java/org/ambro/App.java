package org.ambro;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Properties;

import org.geotools.data.shapefile.ShapefileDataStore;
import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.data.simple.SimpleFeatureIterator;
import org.geotools.data.simple.SimpleFeatureSource;
import org.opengis.feature.simple.SimpleFeature;

import com.vividsolutions.jts.geom.Geometry;

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
	        System.out.println( "Hello, "+name+"." );
	        
	        // Read a shapefile from a URL.
	        URL url = App.class.getResource("/coastline/conus.shp");
			ShapefileDataStore inDataStore = new ShapefileDataStore(url);
			SimpleFeatureSource inFeatureStore = (SimpleFeatureSource) inDataStore.getFeatureSource();
			SimpleFeatureCollection inFeatureCollection = inFeatureStore.getFeatures();
			System.out.println(inFeatureCollection.size()+" shapefile features read.");
			Collection<Geometry> geometries = createGeometries(inDataStore);
			System.out.println(geometries.size()+" geometries found.");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
    }
    
	private static Collection<Geometry>  createGeometries(ShapefileDataStore inDataStore){
		Collection<Geometry> geometries = null;
		if(inDataStore!=null){
			try {
				SimpleFeatureSource inFeatureStore = (SimpleFeatureSource) inDataStore.getFeatureSource();
				SimpleFeatureCollection inFeatureCollection = inFeatureStore.getFeatures();
				geometries = new ArrayList<Geometry>();
				SimpleFeatureIterator features = inFeatureCollection.features();
				while(features.hasNext()){
					SimpleFeature feature = features.next();
					Geometry featureGeom = (Geometry) feature.getAttribute("the_geom");
					// Expecting that shapefiles of polygons will contain MultiPolygon instances.
					for(int i=0;i<featureGeom.getNumGeometries();i++){
						Geometry geom = featureGeom.getGeometryN(i);
						geometries.add(geom);
					}			
				}
				features.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return geometries;
	}

}

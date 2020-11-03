package clases;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import com.opencsv.CSVReader;


public class Lectura {

	private String file;
	public Lectura()
	{

	}
	
	public ArrayList read() throws IOException
	{
		ArrayList arraylist;
		ArrayList dataSet = new ArrayList();
		
		FileReader filereader = new FileReader("fashion-1.csv"); 
		CSVReader csvReader = new CSVReader(filereader);
	    String[] nextRecord = csvReader.readNext(); 
	    
	    Prenda p;
	    int pixel;
	    while ((nextRecord = csvReader.readNext()) != null)
	    {
	    		p= new Prenda();
	    		arraylist= new ArrayList();
	    		p.setLbl(Integer.parseInt(nextRecord[0]));
		    	for (int i = 1 ; i < 785 ; i++)
		    	{
		    		pixel=Integer.parseInt(nextRecord[i]);
		    		arraylist.add(pixel);
		    	}
		    	

		    	p.setPixeles(arraylist);
		    	
		    	dataSet.add(p);
	    	
	    }
	    
	    filereader = new FileReader("fashion-2.csv");
	    csvReader = new CSVReader(filereader);
	    nextRecord = csvReader.readNext(); 
	    
	    while ((nextRecord = csvReader.readNext()) != null)
	    {
	    		p= new Prenda();
	    		arraylist= new ArrayList();
	    		p.setLbl(Integer.parseInt(nextRecord[0]));
		    	for (int i = 1 ; i < 785 ; i++)
		    	{
		    		pixel=Integer.parseInt(nextRecord[i]);
		    		arraylist.add(pixel);
		    	}
		    	

		    	p.setPixeles(arraylist);
		    	
		    	dataSet.add(p);
	    	
	    }
	    
	    return dataSet;

	}
	

	
}

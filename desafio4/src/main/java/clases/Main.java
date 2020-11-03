package clases;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import perceptron.Perceptron;

public class Main  {

	public static void main(String[] args) throws IOException {
		
		Lectura l = new Lectura();
		ArrayList dataSet = l.read();
		
		
		//System.out.println(dataSet.size());
		//70000 total.
		
		
		ArrayList<double[]> entradas = new ArrayList<double[]>();
	    ArrayList<double[]> salidas = new ArrayList<double[]>();

		
        
        //el vector de pixeles
	    //usar 50000 de entrenamiento
        for (int i=0;i<50000;i++)
        {
        	entradas.add(new double[748]);
        	salidas.add(new double [10]);
        	Prenda prenda=(Prenda) dataSet.get(i);
        	add(prenda,entradas.get(i),salidas.get(i));
        	//para ver como va
        }
        System.out.println("Datos ingresados");
        
        Perceptron p=new Perceptron(new int[]{entradas.get(0).length, 512, salidas.get(0).length});
        System.out.println("Perceptron definido");
        
        p.Entrenar(entradas, salidas, 0.5, 2	);
        System.out.println("Entrenamiento realizado");
        double[] output=(double[])entradas.get(0);
        
        double[] o=p.Activacion(output);
      //  System.out.println(p.Activacion(o));
        
        for(int j = 50000; j < 70000; j++)
        {
            entradas.add(new double[748]);
            salidas.add(new double[10]);
            Prenda prenda=(Prenda) dataSet.get(j);
            ArrayList pixeles=prenda.getPixeles();
            for (int i=0;i<748;i++)
    		{
    			entradas.get(entradas.size()-1)[i]=(Integer)pixeles.get(i);
    		}
            
            output=(double[])entradas.get(entradas.size()-1);
            o=p.Activacion(output);
            showLbl(o);
        }

	}
	
	
	public static void add(Prenda p,double[] entrada,double[] salida)
	{
		int i;
		//SE OBTIENEN LOS PIXELES
		ArrayList pixeles=p.getPixeles();
		
		for (i=0;i<748;i++)
		{
			entrada[i]=(Integer)pixeles.get(i);
		}
		int label=p.getLbl();
		
		//CON ESTO SE INDICA LA SALIDA
		for (i=0;i<10;i++)
		{
			if (i!=label)
			{
				salida[i]=0;
			}
			else
			{
				salida[i]=1;
			}
		}
		
	}
	
	public static void showLbl(double[] o)
	{
		int i;
		double mayor = o[0];
		int i_mayor = 0;
		for (i=1;i<10;i++)
		{
			if (o[i]> mayor)
			{
				mayor = o[i];
				i_mayor = i;
			}
		}
		System.out.println("Label: "+i_mayor);
	}
}


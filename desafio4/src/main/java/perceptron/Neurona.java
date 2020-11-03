package perceptron;

import java.util.Random;

public class Neurona 
{
	 public double umbral;
	 public double[] pesos;
	 double sumaPonderada;
	 
	 //*CONSTRUCTOR
	 public Neurona(int numEntradas, Random r)
	 {
		 	//SE ASIGNA UN VALOR PEQUEÑO A LOS PESOS AL MOMENTO DE CREAR LAS NEURONAS
	        umbral = r.nextDouble()/1000;
	        //System.out.println("r.nextdouble dio : "+umbral);
	        pesos = new double[numEntradas];
	        for(int i = 0; i < pesos.length; i++)
	        {
	            pesos[i] = 0.0;//r.nextDouble()/1000;
	            //System.out.println("r.nextdouble dio : "+pesos[i]);
	        }
	 }
	 
	 //*SIGMOIDE
	 public double Sigmoide(double x)
	 {

		 return 1 / (1 + Math.exp(-x));
	 }
	 

	 //*FUNCION ACTIVACION
	 public double Activacion(double[] entradas)
	 {
		 sumaPonderada = umbral;

		 for(int i = 0; i < entradas.length; i++)
		 {
			 sumaPonderada += entradas[i] * pesos[i]; 
	     }

		 return Sigmoide(sumaPonderada);
	 }
}

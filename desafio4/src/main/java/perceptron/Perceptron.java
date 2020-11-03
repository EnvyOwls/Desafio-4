package perceptron;

import java.util.ArrayList;
import java.util.Random;

public class Perceptron 
{
	 public ArrayList<Capa> capas;
	 ArrayList<double[]> sigmas;
	 ArrayList<double[][]> deltas;

	 //*CONSTRUCTOR
	 public Perceptron(int[] numNeuronasPorCapa)
	 {
		 capas = new ArrayList<Capa>();
		 Random r = new Random();

		 for(int i = 0; i < numNeuronasPorCapa.length; i++)
		 {
			 if(i == 0)
			 {
				 capas.add(new Capa(numNeuronasPorCapa[i], numNeuronasPorCapa[i], r));
			 }
			 else
			 {
				 capas.add(new Capa(numNeuronasPorCapa[i - 1], numNeuronasPorCapa[i], r));
			 }
		 }
	 }
	 
	 public double Sigmoide(double x)
	 {
		 return 1 / (1 + Math.exp(-x));
	 }

	public double SigmoideDerivada(double x)
	{
	    double y = Sigmoide(x);
	    return y*(1 - y);
	}
	
	//*RETORNA SALIDAS DE LA RED
	public double[] Activacion(double[] entradas)
	{
	    double[] salidas = new double[0];
	    for(int i = 0; i < capas.size(); i++)
	    {
	        salidas = capas.get(i).Activacion(entradas);
	        entradas = salidas;
	    }
	    return salidas;
	}
	
	//*CALCULO DE ERROR 
	public double Error(double[] salidaReal, double[] salidaEsperada){
	    double err = 0;
	    for(int i = 0; i < salidaReal.length; i++)
	    {
	        err += (1/salidaReal.length)*(Math.pow(salidaReal[i] - salidaEsperada[i], 2));
	    }

	    return err;
	}

	public double ErrorTotal(ArrayList<double[]> entradas, ArrayList<double[]> salidaEsperada){
	    double err = 0;
	    for(int i = 0; i < entradas.size(); i++)
	    {
	        err += Error(Activacion(entradas.get(i)), salidaEsperada.get(i));
	    }

	    return err;
	}
	//*INICIALIZAR DELTAS
	public void initDeltas(){
	    deltas = new ArrayList<double[][]>();

	    for(int i = 0; i < capas.size(); i++)
	    {
	        deltas.add(new double[capas.get(i).neuronas.size()][capas.get(i).neuronas.get(0).pesos.length]);
	        for(int j = 0; j < capas.get(i).neuronas.size(); j++)
	        {
	            for(int k = 0; k < capas.get(i).neuronas.get(0).pesos.length; k++)
	            {
	                deltas.get(i)[j][k] = 0;
	            }
	        }
	    }
	}
	//*CALCULO SIGMAS
	public void calcSigmas(double[] salidaEsperada)
	{
	    sigmas = new ArrayList<double[]>();
	    for(int i = 0; i <  capas.size(); i++)
	    {
	        sigmas.add(new double[capas.get(i).neuronas.size()]);
	    }

	    for(int i = capas.size() - 1; i >= 0; i--)
	    {
	        for(int j = 0; j < capas.get(i).neuronas.size(); j++)
	        {
	            if(i == capas.size() - 1)
	            {
	                double y = capas.get(i).salidas[j];
	                sigmas.get(i)[j] = (y - salidaEsperada[j]) * SigmoideDerivada(y);
	            }
	            else
	            {
	                double sum = 0;
	                for(int k = 0; k < capas.get(i + 1).neuronas.size(); k++)
	                {
	                    sum += capas.get(i + 1).neuronas.get(k).pesos[j] * sigmas.get(i + 1)[k];
	                }
	                sigmas.get(i)[j] = SigmoideDerivada(capas.get(i).neuronas.get(j).sumaPonderada) * sum;
	            }
	        }
	    }
	}
	//*CALC DELTAS
	public void calcDeltas()
	{
	    for(int i = 1; i < capas.size(); i++)
	    {
	        for(int j = 0; j < capas.get(i).neuronas.size(); j++)
	        {
	            for(int k = 0; k < capas.get(i).neuronas.get(j).pesos.length; k++)
	            {
	                deltas.get(i)[j][k] += sigmas.get(i)[j] * capas.get(i - 1).salidas[k];
	            }
	        }
	    }
	}
	
	//*MANEJO DE PESOS
	public void actPesos(double alfa)
	{
	    for(int i = 0; i < capas.size(); i++)
	    {
	        for(int j = 0; j < capas.get(i).neuronas.size(); j++)
	        {
	            for(int k = 0; k < capas.get(i).neuronas.get(j).pesos.length; k++)
	            {
	                capas.get(i).neuronas.get(j).pesos[k] -= alfa * deltas.get(i)[j][k];
	            }
	        }
	    }
	}

	public void actUmbrales(double alfa)
	{
		for(int i = 0; i < capas.size(); i++)
		{
			for(int j = 0; j < capas.get(i).neuronas.size(); j++)
			{
				capas.get(i).neuronas.get(j).umbral -= alfa * sigmas.get(i)[j];
			}
		}
	}
	//*FINNALY, BACKPROPAGATION
	public void BackPropagation(ArrayList<double[]> entradas, ArrayList<double[]> salidaEsperada, double alfa)
	{
	    initDeltas();
	    for(int i = 0; i < entradas.size(); i++)
	    {
	        Activacion(entradas.get(i));
	        calcSigmas(salidaEsperada.get(i));
	        calcDeltas();
	        actUmbrales(alfa);
	    }
	    actPesos(alfa);
	}
	
	//*ENTRENAMIENTO
	public void Entrenar(ArrayList<double[]> entradasPruebas, ArrayList<double[]> salidasPruebas, double alfa, double maxError)
	{
	    double err;//= 99999999;

	    do//while(err > maxError)
	    {
	        BackPropagation(entradasPruebas, salidasPruebas, alfa);
	        err = ErrorTotal(entradasPruebas, salidasPruebas);
	        System.out.println(err);
	    }while(err > maxError);
	}
}

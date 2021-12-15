package elevador;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Random;
import java.util.Set;


public class Predio 
{
	public static int andarAlvo;
	public static volatile int andarAtual;
	public Pedestre[] passageiros;
	public Elevador elevador;
	
	public Predio() throws Exception
	{
		elevador = new Elevador(this);
		passageiros = new Pedestre[2];
		
		elevador.join();
		
		
		
		Random r1 = new Random();
		Random r2 = new Random();
		for(int i = 0; i < passageiros.length; i++)
		{
			int andarInicial = r1.nextInt(3);
			int andarAlvo = r2.nextInt(3);
			while(andarAlvo == andarInicial)
				andarAlvo = r2.nextInt(3);
			
			passageiros[i] = new Pedestre(i, andarInicial, andarAlvo, this);
		}
		elevador.start();
		
		IniciarPassageiros();
	}
	
	public void IniciarPassageiros()
	{
		for(int i = 0; i < passageiros.length; i++)
		{
			passageiros[i].start();
		}
	}
}

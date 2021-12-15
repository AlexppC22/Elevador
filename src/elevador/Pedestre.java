package elevador;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.Semaphore;

public class Pedestre extends Thread
{
	int id;
	int andarInicial;
	int andarAlvo;
	public boolean noAndarAlvo;
	public boolean noElevador;
	public Predio predio;
	
	static Semaphore semaforo = new Semaphore(1);
	public Elevador elevador;
	
	public Pedestre(int _id, int _andarInicial, int _andarAlvo, Predio predio)
	{
		this.id = _id;
		this.predio = predio;
		this.elevador = predio.elevador;
		this.andarInicial = _andarInicial;
		this.andarAlvo = _andarAlvo;
	}
	
	@Override
	public void run() 
	{
		ChamarElevador();	
	}
	
	public void ChamarElevador()
	{
		if(this.noAndarAlvo) return;
		try {
			semaforo.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		elevador.ChamarElevador(andarInicial);
	}
	
	public void EntrarNoElevador()
	{
		if(elevador.temPassageiro)
		{
			System.out.println("ERRO - Elevador está cheio");
			return;
		}
			
		if(!elevador.portaAberta)
		{
			System.out.println("ERRO - Portas estão fechadas"); 
			return;
		}
		System.out.println("Passageiro "+id+" entrou no elevador. Quer ir para "+andarAlvo);
		elevador.temPassageiro = true;
		noElevador = true;
		elevador.ChamarElevador(andarAlvo);
	}
	
	public void SairDoElevador()
	{
		System.out.println("Passageiro "+id+" saiu no elevador no andar "+andarAlvo);
		if(elevador.portaAberta)
		{
			elevador.temPassageiro = false;
			this.noElevador = false;
			this.noAndarAlvo = true;
		}
			
		else if(!elevador.temPassageiro)
			System.out.println("ERRO - Elevador está vazio");
		else
			System.out.println("ERRO - Portas estão fechadas");
		
		semaforo.release();
		elevador.ChecarPassageiros();
	}
	
	//public static int passageiroSemaforo()
	//{
		//return 6;
	//}
}

package elevador;

public class Elevador extends Thread
{
	public int andaresTotal;
	public boolean portaAberta;
	public boolean seMovendo;
	public boolean temPassageiro;
	public int andarAtual = 0;
	public int andarAlvo = -1;
	public Predio predio;
	
	public Elevador(Predio predio)
	{
		this.predio = predio;
	}
	
	
	public void AbrirPorta()
	{
		System.out.println("Elevador abriu a porta");
		portaAberta = true;
	}
	
	public void FecharPorta()
	{
		System.out.println("Elevador fechou a porta");
		portaAberta = false;
	}
	
	public void VisitarAndar()
	{
		System.out.println("Elevador está no andar errado");
		if(portaAberta)
			FecharPorta();
		while(andarAtual != andarAlvo)
		{
			seMovendo = true;
			if (andarAtual < andarAlvo)
				andarAtual++;
			else if (andarAlvo != -1)
				andarAtual--;
			System.out.println("Elevador está no andar " + andarAtual);
		}
		
		System.out.println("Elevador está no andar certo");
		if(!portaAberta)
			AbrirPorta();
		if(portaAberta)
			ChecarPassageiros();
	}
	
	public void ChecarPassageiros()
	{
		if(temPassageiro)
		{
			System.out.println("Elevador está no andar certo e tem passageiro");
			for(int i = 0; i < predio.passageiros.length; i++)
			{
				if(predio.passageiros[i].noElevador)
				{
					predio.passageiros[i].SairDoElevador();
					temPassageiro = false;
				}
			}
		}
		else
		{
			System.out.println("Passageiro deve entrar no elevador");
			for(int i = 0; i < predio.passageiros.length; i++)
			{
				if(!temPassageiro && predio.passageiros[i].andarInicial == andarAtual
						&& !predio.passageiros[i].noAndarAlvo)
				{
					predio.passageiros[i].EntrarNoElevador();
					temPassageiro = true;
				}
				else
				{
					System.out.println("Nenhum");
				}	
			}
			
		}
			
	}
	
	public void ChamarElevador(int andarAlvo)
	{
		System.out.println("Elevador chamado no andar " + andarAlvo);
		this.andarAlvo = andarAlvo;
		System.out.println("Elevador está no andar " + andarAtual);
		this.run();
	}
	

	@Override
	public void run() 
	{
		if(andarAtual != andarAlvo)
		{
			VisitarAndar();	
		}
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

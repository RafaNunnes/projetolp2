import java.util.concurrent.Semaphore;

public class TaxiBuffer {
	
	Semaphore empty, full;
	int size, inBuf, outBuf, qtd;		//qtd = quantidade de taxis atualmente
	String[] buffer;
	
	public TaxiBuffer(int n)
	{
		size = n;							//tamanho do buffer
		empty = new Semaphore(size);		//inicialmente todos estao vazios
		full = new Semaphore(0);
		inBuf = 0;							//posicao inicial dos buffers
		outBuf = 0;		
		buffer = new String[size];
		for (int i = 0; i < size; i++)		//seta os valores do buffer para null
		{
			buffer[i] = null;
		}
		qtd = 0;
	}
	
	public void deposit()		
	{
		try {
			empty.acquire();
		} catch (InterruptedException e) {}
		
		//Secao critica		
		System.out.println("chegou taxi na posicao " + inBuf);
		inBuf = (inBuf + 1) % size; 		//atualiza local de deposito		
		qtd++;
		//Secao critica
		
		full.release();
	}
	
	public void fetch()			
	{		
		try {
			full.acquire();
		} catch (InterruptedException e) {}
		
		//Secao critica		
		System.out.println("Saiu taxi da posicao " + outBuf);
		outBuf = (outBuf + 1) % size;
		qtd--;
		//Secao critica
		
		empty.release();		
	}
	
	public boolean vazia()			//checa se a fila de taxis esta vazia
	{
		if (qtd == 0)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
}

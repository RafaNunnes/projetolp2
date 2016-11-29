public class TaxiGenerator extends Thread{

	TaxiBuffer buffer;
	String value;

	public TaxiGenerator(TaxiBuffer b, String v)
	{
		buffer = b;
		value = v;
	}

	public void run()
	{
		while(true)
		{
			buffer.deposit();						// tinha value + String.valueOf(i++) dentro do deposit
			try {
				Thread.sleep(15000);					//espera 5 segundos para depositar novo taxi					
			} catch (InterruptedException e) {}
		}
	}
}

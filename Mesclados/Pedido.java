import java.io.*;
import java.util.Scanner;

public class Pedido {
	FileReader arq;
	BufferedReader file;
	File save;
	String conta[];
	int ponteiro;
	double valorFinal;
	boolean delivery;
	String endereco;
	
	public Pedido(){
		conta = new String[30];
		ponteiro = 0;
		valorFinal = 0.0;
		delivery = false;
		endereco = null;
	}
	
	public String exibeMenu(){
		String codigo, nome, valor, menu="";
		Scanner scanner = null;
		try {
			scanner = new Scanner(new FileReader("menu.txt")).useDelimiter("#|\\n");
		} catch (FileNotFoundException e) { e.printStackTrace(); }
		
		while (scanner.hasNext()) {
			codigo = scanner.next();
			nome = scanner.next();
			valor = scanner.next();
			
			menu+= codigo + "   "+ nome + "      " + valor;
		}
		
		scanner.close();
		return menu + "\n" + "Insira o id dos produtos que deseja comprar: ";
	}
	
	public String fazerPedido(int id){
		Scanner scanner = null;
		String sucesso = null;
		try {
			scanner = new Scanner(new FileReader("menu.txt")).useDelimiter("#|\\n");
		} catch (FileNotFoundException e) { e.printStackTrace(); }
		
		while (scanner.hasNext()) {
			String codigo = scanner.next();
			int cod = Integer.parseInt(codigo);
			
			if(cod == id){
				String nome = scanner.next();
				String valor = scanner.next();
				conta[ponteiro] = nome + "    " + valor;
				valorFinal += Double.parseDouble(valor);
				sucesso = "Compra efetuada: " + conta[ponteiro];
				
			}
			if(scanner.hasNext()){ scanner.nextLine();}
		}
		scanner.close();
		if(sucesso!=null){
			ponteiro++;
			return sucesso;
		}else{
			return "\nId inválido, tente novamente!\n";
		}
		
	}
	
	public String entrega(String end){
		endereco = end;
		delivery = true;
		return "Endereco confirmado!\n"
				+ "Qual a forma de pagamento?";
	}
	
	public String fecharPedido(String fPagamento){
		/*for(int i=0; i<ponteiro; i++){
			System.out.print(conta[i]+"\n");
		}
		
		if(delivery){
			System.out.println("\nPedido será entregue no endereco: "+ endereco);
		}
		
		System.out.println("\nForma de pagamento: " + fPagamento);
		
		System.out.println("\nTotal a pagar: "+ "R$ "+valorFinal);*/
		
		String nFiscal = "";
		
		for(int i=0; i<ponteiro; i++){
			nFiscal += conta[i]+"\n";
		}
		if(delivery){
			nFiscal += "\nPedido será entregue no endereco:" + endereco;
		}
		
		nFiscal += "\nForma de pagamento: " + fPagamento +
				   "\nTotal a pagar: "+ "R$ "+valorFinal;
		
		return nFiscal;
	}
	
}

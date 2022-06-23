import java.util.Scanner;

public class AlgGrafos {
	public static void main(String args[]) throws Exception {
		String ANSI_RESET = "\u001B[0m";
		String ANSI_CYAN = "\u001B[36m";
		Scanner scan1 = new Scanner(System.in);

		Graph g1 = new Graph();

		g1.open_text();
		g1.print();
		int totalVertex = g1.vertex_set.size() - 1;
		System.out.println(ANSI_CYAN
				+ "\n*Caso o grafo possua um caminho euleriano, o vértice inicial será\nescolhido automaticamente entre os 2 vértices de grau ímpar existentes.\n");
		System.out.println(ANSI_RESET + "Escolha um vértice entre " + g1.vertex_set.get(0).id + " e "
				+ g1.vertex_set.get(totalVertex).id + " para iniciar.");

		g1.eulerPath(scan1.nextInt());

		scan1.close();
	}

}

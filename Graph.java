import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.io.FileReader;
import java.io.BufferedReader;

public class Graph {
	// vertex set
	private String ANSI_GREEN = "\u001B[32m";
	private String ANSI_RESET = "\u001B[0m";
	private String ANSI_YELLOW = "\u001B[33m";
	private String ANSI_RED = "\u001B[31m";

	protected HashMap<Integer, Vertex> vertex_set;
	protected int time;
	protected boolean cycle, load = false;

	public Graph() {
		vertex_set = new HashMap<Integer, Vertex>();
	}

	public void print() {
		if (vertex_set.values().isEmpty())
			System.out.println("Inicialize o grafo para usar essa opção");
		else {
			System.out.printf("\n\nDados do Grafo, grau máximo %d", this.max_degree());

			for (Vertex v : vertex_set.values())
				v.print();
			System.out.println("\n");
		}
	}

	// Carregamento do grafo de um arquivo .txt
	public void open_text() {
		String arq_ent = "myfiles/grafo01.txt";
		String thisLine = null;
		vertex_set = new HashMap<Integer, Vertex>();
		String pieces[];
		try {
			FileReader file_in = new FileReader(arq_ent);
			BufferedReader br1 = new BufferedReader(file_in);
			int v1, v2;
			while ((thisLine = br1.readLine()) != null) {
				// retira excessos de espaços em branco
				thisLine = thisLine.replaceAll("\\s+", " ");
				pieces = thisLine.split(" ");
				v1 = Integer.parseInt(pieces[0]);

				if (vertex_set.get(v1) == null)
					this.add_vertex(v1);
				for (int i = 2; i < pieces.length; i++) {
					v2 = Integer.parseInt(pieces[i]);
					// pode ser a primeira ocorrência do v2
					if (vertex_set.get(v2) == null)
						this.add_vertex(v2);
					this.add_edge(v1, v2);
				}
			}
			br1.close();
			file_in.close();
			if (!load)
				System.out.println(ANSI_GREEN + "Arquivo carregado com sucesso!" + ANSI_RESET);

			load = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Adicionar um novo vertice
	public void add_vertex(int id) {
		if (id < 1 || this.vertex_set.get(id) == null) {
			Vertex v = new Vertex(id);
			vertex_set.put(v.id, v);
			reset();
		} else
			System.out.println("Id inválido ou já utilizado!");
	}

	// Adicionar aresta
	public void add_edge(Integer id1, Integer id2) {
		Vertex v1 = vertex_set.get(id1);
		Vertex v2 = vertex_set.get(id2);
		if (v1 == null || v2 == null) {
			System.out.printf("Vértice inexistente!");
			return;
		}
		v1.add_neighbor(v2);
		v2.add_neighbor(v1);
		reset();
	}

	// Remover aresta
	public void del_edge(Vertex v1, Vertex v2) {

		v1.del_neighbor(v2);
		v2.del_neighbor(v1);
	}

	// Gray maximo deu um vertice de G
	public int max_degree() {
		int max = -1;
		for (Vertex v1 : vertex_set.values()) {
			if (v1.degree() > max)
				max = v1.degree();
		}
		return max;
	}

	// Reseta os atributos
	protected void reset() {
		time = 0;
		for (Vertex v1 : vertex_set.values())
			v1.reset();
	}

	// Verifica a conectividade de um grafo
	public boolean is_connected() {
		for (Vertex v1 : vertex_set.values()) {
			int conected = DFS(v1);
			if (conected != vertex_set.size())
				return false;
			break;
		}
		return true;
	}

	// Busca em profundidade contando os vertices conectados.
	public int DFS(Vertex v1) {
		v1.d = ++time;
		int cont = 1;
		for (Vertex neig : v1.nbhood.values()) {
			if (neig.d == null) {
				neig.parent = v1;
				cont = cont + DFS(neig);
			}
		}
		return cont;
	}

	// Verificação da quantidade de vetores com grau impar no grafo
	public int getOddVerticesCount() {
		int oddCount = 0;
		for (Vertex v1 : vertex_set.values()) {
			if (v1.degree() % 2 == 1) {
				oddCount++;
			}
		}
		return oddCount;
	}

	// Verificação para encontrar o vertice de grau impar para o inicio do caminho
	public Vertex getEulerPathOrigin() {
		for (Vertex v1 : vertex_set.values()) {
			if (v1.degree() % 2 == 1) {
				return v1;
			}
		}
		throw new RuntimeException("Erro ao encontrar a origem paro o caminho Euleriano");
	}

	// Verificação se o grafo possui um Circuito/Caminho Euleriano
	public void eulerPath(int from) {
		if (vertex_set.values().isEmpty())
			System.out.println("Inicialize o grafo para usar essa opção");
		else {
			List<Integer> path = new ArrayList<Integer>();
			int oddCount = getOddVerticesCount();
			Boolean conected = is_connected();
			if (oddCount == 0 && conected) {// Circuito Euleriano
				path.add(vertex_set.get(from).id);
				cycle = true;
				getEulerPath(path);
			} else if (oddCount == 2 && conected) {// Caminho Euleriano
				path.add(getEulerPathOrigin().id);
				cycle = false;
				getEulerPath(path);
			} else {
				System.out.println(ANSI_RED + "O grafo nao possui circuito nem caminhos euleriano.");
			}

		}

	}

	// Função principal da busca e impressão do Circuito/Caminho Euleriano
	public void getEulerPath(List<Integer> path) {

		FleuryAlgorithm(vertex_set.get(path.get(0)), path);
		String seta = "->";
		if (cycle)
			System.out.printf("\nCircuito Euleriano: " + ANSI_YELLOW + "[");
		else
			System.out.printf("\n*O grafo nao possui Circuito Euleriano, mas possui caminho:\n\nCaminho Euleriano:"
					+ ANSI_YELLOW + " [");

		for (int i = 0; i < path.size(); i++) {
			if (i == path.size() - 1)
				seta = "";
			System.out.printf(" %d " + seta, path.get(i));
		}
		System.out.printf("]\n" + ANSI_RESET);
		open_text();
	}

	// Algoritimo de Fleury
	public void FleuryAlgorithm(Vertex v1, List<Integer> path) {
		List<Vertex> vizinhos = new ArrayList<>();
		vizinhos.addAll(v1.nbhood.values());
		for (Vertex neig : vizinhos) {
			if (!isBridge(v1, neig)) {
				path.add(neig.id);
				this.del_edge(v1, neig);
				FleuryAlgorithm(neig, path);
				break;
			}
		}
	}

	// Verificação se é uma aresta de corte (ponte)
	public boolean isBridge(Vertex from, Vertex to) {
		if (from.nbhood.size() == 1)
			return false;

		this.reset();
		int bridgeCount = DFS(to);
		this.del_edge(from, to);
		this.reset();
		int nonBridgeCount = DFS(to);
		this.add_edge(from.id, to.id);

		return nonBridgeCount < bridgeCount;
	}

}

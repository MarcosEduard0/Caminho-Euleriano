import java.util.HashMap;

public class Vertex implements Comparable<Vertex> {
    protected Integer id;
    // outgoing neighbors
    protected HashMap<Integer, Vertex> nbhood;
    protected Vertex parent, root;
    protected Integer dist, d, f;
    // usado na Ordenação topológica
    protected int size;
    // componentes conexas: menor tempo de descoberta alcançavel
    // usando no máximo uma aresta de retorno dos descendentes
    protected Integer low;

    public Vertex(int id) {
        // id >= 1
        this.id = id;
        nbhood = new HashMap<Integer, Vertex>();
        parent = null;
        dist = d = null;
    }

    public void print() {
        System.out.print("\nO vértice " + id + " possui os vizinhos: ");
        for (Vertex v : nbhood.values())
            System.out.print(" " + v.id);
    }

    // usado na Ordenação topológica
    @Override
    public int compareTo(Vertex otherVertex) {
        if (otherVertex.size > this.size)
            return 1;
        else
            return -1;
    }

    protected void reset() {
        parent = null;
        d = null;
        f = null;
        dist = null;
    }

    public void add_neighbor(Vertex viz) {
        nbhood.put(viz.id, viz);
    }

    public void del_neighbor(Vertex viz) {
        nbhood.remove(viz.id);
    }

    public int degree() {
        return nbhood.size();
    }

    public void discover(Vertex parent) {
        this.parent = parent;
        this.dist = parent.dist + 1;
    }

    protected Vertex get_root() {
        if (parent == null)
            root = this;
        else
            root = parent.get_root();
        return root;
    }

}

/*
 * @author Éllen Oliveira Silva Neves (20202BSI0071) e Carlos Breno Norato Rosa (20202BSI0233)
 */

public class Aresta<T> implements Comparable<Aresta<T>>{
    
    private Vertice<T> destino;
    private float peso;

    public Aresta(Vertice<T> destino, float peso) { //lista de adjacências
        this.destino = destino;
        this.peso = peso;
    }

    public Aresta(float peso) {
        this.peso = peso;
    }

    public Vertice<T> getDestino() {
        return destino;
    }

    public void setDestino(Vertice<T> destino) {
        this.destino = destino;
    }

    public float getPeso() {
        return peso;
    }

    public void setPeso(float peso) {
        this.peso = peso;
    }

    @Override
    public int compareTo(Aresta<T> a) {
        if (a.getPeso() == this.peso && a.getDestino() == this.getDestino())
            return 0; // se for igual
        else
            return 1; // se não for
    }

}

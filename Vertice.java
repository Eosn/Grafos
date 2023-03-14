import java.util.ArrayList;

/*
 * @author Éllen Oliveira Silva Neves (20202BSI0071) e Carlos Breno Norato Rosa (20202BSI0233)
 */

public class Vertice<T> {
    
    private T valor;
    private ArrayList<Aresta<T>> destinos = new ArrayList<Aresta<T>>(); //lista de adjacências

    public Vertice (T valor) {
        this.valor = valor;
    }

    public T getValor() {
        return this.valor;
    }

    public void setValor(T valor) {
        this.valor = valor;
    }

    public ArrayList<Aresta<T>> getDestinos() {
        return destinos;
    }

    public void setDestinos(ArrayList<Aresta<T>> destinos) {
        this.destinos = destinos;
    }

    public void adicionaDestino(Aresta<T> destino) {
        this.destinos.add(destino);
    }
}

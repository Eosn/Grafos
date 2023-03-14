// Tentativa de imitar 1% do poder do Python

/*
 * @author Éllen Oliveira Silva Neves e Carlos Breno Norato Rosa
 */

public class Tupla<T, J> {
    private T primeiroValor;
    private J segundoValor;

    public Tupla(T objecT, J objectJ) {
        this.primeiroValor = objecT;
        this.segundoValor = objectJ;
    }

    public Tupla() {}

    public T getPrimeiroValor() {
        return primeiroValor;
    }

    public J getSegundoValor() {
        return segundoValor;
    }

    public void setPrimeiroValor(T primeiroValor) {
        this.primeiroValor = primeiroValor;
    }

    public void setSegundoValor(J segundoValor) {
        this.segundoValor = segundoValor;
    }
}

/*
 * @author Éllen Oliveira Silva Neves (20202BSI0071) e Carlos Breno Norato Rosa (20202BSI0233)
 */

public class Cidade implements Comparable<Cidade>{

    private int codigo;
    private String nome;

    public Cidade() {}

    public Cidade (int codigo, String nome) {
        this.codigo = codigo;
        this.nome = nome;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
    
    @Override
    public String toString() {
        return "Código: " + this.codigo + "\nNome: " + this.nome;
    }

    @Override
    public int compareTo(Cidade c) {
        if (c.getCodigo() == this.codigo && c.getNome() == this.nome)
            return 0; // se for igual
        else 
            return 1; // se for diferente
    }

}

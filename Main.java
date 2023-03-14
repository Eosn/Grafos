import java.io.BufferedReader;
import java.io.FileReader;
import java.util.InputMismatchException;
import java.util.Scanner;

/*
 * @author Éllen Oliveira Silva Neves e Carlos Breno Norato Rosa
 */

public class Main {
    public static void main(String args[]) throws Exception {

        Grafo<Cidade> mapa = new Grafo<Cidade>();
        Scanner s = new Scanner(System.in);
        int user;
        int qtd;

        // numero de vertices
        // codigocidade;nomecidade
        // matriz; a linha é a cidade de origem, a coluna é a cidade de destino; o número é o peso

        // configura leitura do arquivo
        FileReader fr = new FileReader("entrada.txt");
        BufferedReader br = new BufferedReader(fr);
        String linha = "";
        String[] dados = new String[3];

        linha = br.readLine();
        qtd = Integer.parseInt(linha); // lê a qtd de cidades
        linha = br.readLine();

        // cadastra as cidades no mapa
        for (int i = 0; i < qtd; i++) {
            dados = linha.split(";");
            Cidade cid = new Cidade(Integer.parseInt(dados[0]), dados[1]);
            mapa.adicionarVertice(cid);
            linha = br.readLine();
        }

        // lê a matriz de caminhos
        for (int i = 0; i < qtd; i++) { // percorre a linha
            dados = linha.split(";");
            Cidade origem = mapa.getVertices().get(i).getValor();
            for (int j = 0; j < dados.length; j++) { // percorre a coluna
                if (Float.parseFloat(dados[j]) > 0.0) { // não adiciona se o peso for 0
                    Cidade destino = mapa.getVertices().get(j).getValor();
                    mapa.adicionarAresta(origem, destino, Float.parseFloat(dados[j]));
                }
            }
            linha = br.readLine(); // esquecemos de colocar na primeira etapa
        }

        br.close();
        // finaliza leitura do arquivo

        menu();
        user = s.nextInt();
        while (user != 6) {
            if (user < 1 || user > 6) {
                System.out.println("Opção inválida, tente novamente.");

            } else if (user == 1) { // obtem todas as cidades vizinhas à cidade informada
                Vertice<Cidade> v = new Vertice<Cidade>(new Cidade());
                System.out.print("Código da cidade: ");
                
                try {
                    v.setValor(mapa.getVertices().get(s.nextInt() - 1).getValor());
                    v = mapa.obterVertice(v.getValor()); // procura a cidade correspondente

                    if (v == null) {
                        System.out.println("Cidade não encontrada.");

                    } else {
                        for (Aresta<Cidade> i : v.getDestinos()) {
                            Cidade cid = (Cidade) i.getDestino().getValor();
                            System.out.println("Código: " + cid.getCodigo());
                            System.out.println("Nome: " + cid.getNome());
                            System.out.println("Distância: " + i.getPeso());
                        }
                    }

                } catch (InputMismatchException e) {
                    System.out.println("O código informado não é um número.");
                }
                
            } else if (user == 2) { // obtem todos os caminhos possíveis a partir de uma cidade
                Vertice<Cidade> v = new Vertice<Cidade>(new Cidade());
                System.out.print("Código da cidade: ");
                
                try {
                    v.setValor(mapa.getVertices().get(s.nextInt() - 1).getValor());
                    v = mapa.obterVertice(v.getValor());

                    if (v == null) {
                        System.out.println("Cidade não encontrada.");

                    } else {
                        mapa.buscaEmLargura(v.getValor());
                    }
                    
                } catch (InputMismatchException e) {
                    System.out.println("O código informado não é um número.");
                }

            } else if (user == 3) { // calcula o caminho mínimo entre dois vértices
                Vertice<Cidade> vOrigem = new Vertice<Cidade>(new Cidade());
                Vertice<Cidade> vDestino = new Vertice<Cidade>(new Cidade());
                System.out.print("Código da cidade de origem: ");
                
                try {
                    vOrigem.setValor(mapa.getVertices().get(s.nextInt() - 1).getValor());
                    vOrigem = mapa.obterVertice(vOrigem.getValor());

                    if (vOrigem == null) {
                        System.out.println("Cidade não encontrada.");

                    } else {
                        System.out.print("Código da cidade de destino: ");
                        vDestino.setValor(mapa.getVertices().get(s.nextInt() - 1).getValor());
                        vDestino = mapa.obterVertice(vDestino.getValor());

                        if (vDestino == null) {
                            System.out.println("Cidade não encontrada.");
                        } else {
                            mapa.caminhoMinimo(vOrigem.getValor(), vDestino.getValor());
                        }
                    }

                } catch (InputMismatchException e) {
                    System.out.println("O código informado não é um número.");
                }
            } else if (user == 4) {
                mapa.arvoreGeradoraMinima();
            } else if (user == 5) {
                System.out.println("Atenção! Este algoritmo realizará alterações no grafo inserido.");
                Vertice<Cidade> vOrigem = new Vertice<Cidade>(new Cidade());
                Vertice<Cidade> vDestino = new Vertice<Cidade>(new Cidade());
                System.out.print("Código da cidade de origem: ");
                
                try {
                    vOrigem.setValor(mapa.getVertices().get(s.nextInt() - 1).getValor());
                    vOrigem = mapa.obterVertice(vOrigem.getValor());

                    if (vOrigem == null) {
                        System.out.println("Cidade não encontrada.");

                    } else {
                        System.out.print("Código da cidade de destino: ");
                        vDestino.setValor(mapa.getVertices().get(s.nextInt() - 1).getValor());
                        vDestino = mapa.obterVertice(vDestino.getValor());

                        if (vDestino == null) {
                            System.out.println("Cidade não encontrada.");
                        } else {
                            //float fluxoMax = mapa.fluxoMaximo(vOrigem.getValor(), vDestino.getValor());
                            //System.out.println("O fluxo máximo do grafo é de " + fluxoMax);
                            System.out.println("Este código está entrando em loop infinito. Favor considerar o esforço e a tentativa, gratos pela compreensão.");
                        }
                    }
                    
                } catch (InputMismatchException e) {
                    System.out.println("O código informado não é um número.");
                }
            }
            menu();
            user = s.nextInt();
        } // sai do programa

        System.out.println("-------------- FIM ---------------");
        s.close();
        
    }

    public static void menu() {
        System.out.println("-------------- MENU --------------");
        System.out.println("1) Obter cidades vizinhas");
        System.out.println("2) Obter todos os caminhos a partir de uma cidade");
        System.out.println("3) Calcular caminho mínimo");
        System.out.println("4) Calcular árvore geradora mínima");
        System.out.println("5) Calcular fluxo máximo");
        System.out.println("6) Sair");
        System.out.println("----------------------------------");
        System.out.print("Opção escolhida: ");
    }
    
}

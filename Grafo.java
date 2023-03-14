import java.util.ArrayList;
import java.util.HashMap;

/*
 * @author Éllen Oliveira Silva Neves (20202BSI0071) e Carlos Breno Norato Rosa (20202BSI0233)
 */

public class Grafo<T extends Comparable<T>> {
    
    private ArrayList<Vertice<T>> vertices = new ArrayList<Vertice<T>>();

    public Grafo() {}

    public Vertice<T> adicionarVertice(T valor) { // passa a cidade
        Vertice<T> novo = new Vertice<T>(valor);
        this.vertices.add(novo);
        return novo;
    }

    public Vertice<T> obterVertice(T valor) { // passa a cidade
        Vertice<T> v;

        for (int i = 0; i < this.vertices.size(); i++) {
            v = this.vertices.get(i);
            if (v.getValor().equals(valor))
                return v;
        }

        return null; // se não existe vértice com o valor
    }

    public void adicionarAresta(T origem, T destino, float peso) { // passa as cidades
        Vertice<T> verticeOrigem, verticeDestino;

        //busca o vertice com o valor de origem; se nao é preexistente, cria
        verticeOrigem = obterVertice(origem);
        if (verticeOrigem == null)
            verticeOrigem = adicionarVertice(origem);

        //busca vertice com valor de destino; se não é preexistente, cria
        verticeDestino = obterVertice(destino);
        if (verticeDestino == null)
            verticeDestino = adicionarVertice(destino);

        //adiciona aresta à lista de adjacências
        verticeOrigem.adicionaDestino(new Aresta<T>(verticeDestino, peso));
    }

    public void buscaEmLargura(T origem) { // busca a partir de um determinado vertice e faz o caminho até todas as cidades possíveis
        ArrayList<Vertice<T>> marcados = new ArrayList<Vertice<T>>();
        ArrayList<Vertice<T>> fila = new ArrayList<Vertice<T>>();

        //coloca o vértice de partida na fila
        Vertice<T> atual = obterVertice(origem);
        fila.add(atual);

        while (fila.size() > 0) { // enquanto houver vértice na fila
            atual = fila.get(0); // pega o próximo, marca como visitado e imprime
            fila.remove(0);
            marcados.add(atual);
            System.out.println(atual.getValor());

            ArrayList<Aresta<T>> destinos = atual.getDestinos(); //se o nó adjacente ainda não tiver sido visitado, coloca na fila
            Vertice<T> proximo;
            for (int i = 0; i < destinos.size(); i++) {
                proximo = destinos.get(i).getDestino();
                if (!marcados.contains(proximo) && !fila.contains(proximo)) {
                    fila.add(proximo);
                }
            }
        }
    }

    public void caminhoMinimo(T origem, T destino) { // imprime caminho mínimo e a distância total
        Vertice<T> noAtual = obterVertice(origem);
        ArrayList<Vertice<T>> nosAbertos = new ArrayList<Vertice<T>>(); // nós com ordem de proximidade já definida (menor distânica desde o nó de origem)
        float distanciaTotal = 0;

        HashMap<Vertice<T>, Float> dist = new HashMap<>(); // dicionário de distâncias
        HashMap<Vertice<T>, Vertice<T>> prev = new HashMap<>(); // dicionário de predecessores
        for (Vertice<T> v : this.vertices) { // atribui distância infinita a todos os vértices e inicializa
            dist.put(v, Float.POSITIVE_INFINITY);
            prev.put(v, null);
            nosAbertos.add(v);
        }
        dist.put(noAtual, 0.0f); // última inicialização: a distância do nó inicial ao nó inicial é zero

        while (!nosAbertos.isEmpty()) { // enquanto ainda não percorreu todos os nós
            Vertice<T> camMin = nosAbertos.get(0); // pega um nó qualquer
            for (Vertice<T> candidato : nosAbertos) // itera sobre todos os nós não percorridos
                if (dist.get(candidato) < dist.get(camMin)) // minimiza distância
                    camMin = candidato; // atribui o nó não percorrido com a menor distância
            nosAbertos.remove(camMin);

            for (Aresta<T> aresta : camMin.getDestinos()) { // percorre as arestas aos nós adjacentes
                Vertice<T> vizinho = aresta.getDestino(); // obtém o nó adjacente
                if (nosAbertos.contains(vizinho)) { // apenas os nós adjacentes ainda não percorridos
                    float distanciaAlternativa = dist.get(camMin) + aresta.getPeso(); // verifica a distância pelo caminho em teste
                    if (distanciaAlternativa < dist.get(vizinho)) { // se o caminho testado for menor que o anterior
                        dist.put(vizinho, distanciaAlternativa); // atualiza a distância e
                        prev.put(vizinho, camMin); // atualiza seu predecessor
                    }
                }
            }
                
        }

        ArrayList<Vertice<T>> caminho = new ArrayList<>(); // caminho em ordem inversa
        noAtual = obterVertice(destino); // começa do destino
        if (prev.get(noAtual) != null || noAtual == obterVertice(origem)) { // ignora casos triviais (grafo desconexo ou origem e destino idênticos)
            while (noAtual != null) { // enquanto não chegar ao início
                caminho.add(noAtual); // adiciona o nó em ordem inversa
                noAtual = prev.get(noAtual); // pega o nó anterior
            }
        }

        distanciaTotal = dist.get(obterVertice(destino)); // obtém distância até a origem

        for (int i = caminho.size()-1; i >= 0; i--) {
            System.out.println(caminho.get(i).getValor()); // imprime na tela em ordem reversa da reversa (da origem ao destino)
        }
        System.out.println("Distância total: " + distanciaTotal); // imprime distância total
        
    }

    public void arvoreGeradoraMinima() { // algoritmo de prim
        ArrayList<Vertice<T>> nosVisitados = new ArrayList<Vertice<T>>();
        ArrayList<Tupla<Vertice<T>, Aresta<T>>> arestasVisitadas = new ArrayList<Tupla<Vertice<T>, Aresta<T>>>(); // (verticeOrigem, aresta)
        Vertice<T> verticeAtual = this.vertices.get(0);
        Float pesoTotal = 0.f;

        nosVisitados.add(verticeAtual); // adiciona o vertice inicial aos visitados
        while (!contemMesmosVertices(nosVisitados)) { // itera enquanto não tiver iterado todos os vertices
            Aresta<T> arestaAtual = new Aresta<T>(Float.MAX_VALUE);
            for (Vertice<T> vertice : nosVisitados) { // para cada vertice visitado, percorre todas as arestas adjacentes
                for (Aresta<T> aresta : vertice.getDestinos()) {
                    if (!nosVisitados.contains(aresta.getDestino()) && aresta.getPeso() < arestaAtual.getPeso()) { // atualiza se o vertice de destino não for visitado e o peso da aresta for menor que o da recém visitada
                        arestaAtual = aresta;
                        verticeAtual = vertice;
                    }
                }
            }
            nosVisitados.add(arestaAtual.getDestino()); // adiciona o vértice de destino da aresta com menor caminho
            arestasVisitadas.add(new Tupla(verticeAtual, arestaAtual));
            pesoTotal += arestaAtual.getPeso();
        }
        
        for (Tupla<Vertice<T>, Aresta<T>> tupla : arestasVisitadas) { // imprime dados na tela
            System.out.println("Dados do vértice de origem: ");
            System.out.println(tupla.getPrimeiroValor().getValor());
            System.out.println("Dados do vértice de destino: ");
            System.out.println(tupla.getSegundoValor().getDestino().getValor());
            System.out.println("Peso da aresta: " + tupla.getSegundoValor().getPeso());
            System.out.println();
        }
        System.out.println("Peso total: " + pesoTotal);
        
    }

    protected boolean contemMesmosVertices(ArrayList<Vertice<T>> lista) {
        for (Vertice<T> vertice : this.vertices) {
            if (!lista.contains(vertice)) {
                return false;
            }
        }
        return true;
    }

    public float fluxoMaximo(T origem, T destino) { // algoritmo de ford-fulkerson
        // ATENÇÃO: este algoritmo modifica o grafo inserido
        float fluxoMax = 0;

        ArrayList<Tupla<Vertice<T>, Aresta<T>>> caminho = achaCaminho(origem, destino); // pega sempre caminho mínimo

        while (!caminho.isEmpty()) {
            // achando a capacidade de fluxo mínima de ida
            float fluxoCaminho = Float.MAX_VALUE;
            for (Tupla<Vertice<T>, Aresta<T>> tpAresta : caminho) {
                if (tpAresta.getSegundoValor().getPeso() < fluxoCaminho) {
                    fluxoCaminho = tpAresta.getSegundoValor().getPeso(); // menor valor
                }
            }

            for (int i = 0; i < 2; i++) { // atualiza a capacidade de arestas duas vezes, uma para ida e outra para volta

                // atualizando a capacidade das arestas
                for (Tupla<Vertice<T>, Aresta<T>> tpAresta : caminho) {
                    for (Vertice<T> vertice : this.vertices) {
                        if (vertice.getValor().compareTo(tpAresta.getPrimeiroValor().getValor()) == 0) {
                            ArrayList<Aresta<T>> arestas = vertice.getDestinos();

                            for (int j = 0; j < arestas.size(); j++) { // atualiza as capacidades
                                Aresta<T> aresta = arestas.get(j);
                                if (aresta.compareTo(tpAresta.getSegundoValor()) == 0) {
                                    aresta.setPeso(aresta.getPeso() - fluxoCaminho);
                                    if (aresta.getPeso() == 0.f) {
                                        arestas.remove(aresta);
                                    }
                                }
                            }

                            vertice.setDestinos(arestas);

                        }
                    }

                }

                // inverte o caminho
                ArrayList<Tupla<Vertice<T>, Aresta<T>>> caminhoInvertido = new ArrayList<>();
                for (Tupla<Vertice<T>, Aresta<T>> parteCaminho : caminho) {
                    Vertice<T> destinoViraOrigem = parteCaminho.getSegundoValor().getDestino();
                    Aresta<T> origemViraDestino = new Aresta<T>(0.f);
                    for (Aresta<T> aresta : destinoViraOrigem.getDestinos()) {
                        if (aresta.getDestino().getValor().compareTo(destinoViraOrigem.getValor()) == 0) {
                            origemViraDestino = aresta;
                        }
                        
                    }
                    caminhoInvertido.add(new Tupla<Vertice<T>, Aresta<T>>(destinoViraOrigem, origemViraDestino)); 
                }
                
            }

            fluxoMax += fluxoCaminho;
            caminho = achaCaminho(origem, destino);
        }

        return fluxoMax;
    
    }

    public ArrayList<Tupla<Vertice<T>, Aresta<T>>> achaCaminho(T origem, T destino) { // dijkstra alterado
        Vertice<T> noAtual = obterVertice(origem);
        Vertice<T> vDestino = obterVertice(destino);
        ArrayList<Vertice<T>> nosAbertos = new ArrayList<Vertice<T>>();

        HashMap<Vertice<T>, Float> dist = new HashMap<>();
        HashMap<Vertice<T>, Vertice<T>> prev = new HashMap<>();
        for (Vertice<T> v : this.vertices) {
            dist.put(v, Float.POSITIVE_INFINITY);
            prev.put(v, null);
            nosAbertos.add(v);
        }
        dist.put(noAtual, 0.0f);

        while (nosAbertos.contains(vDestino)) { // enquanto ainda não achou o destino
            Vertice<T> camMin = nosAbertos.get(0); 
            for (Vertice<T> candidato : nosAbertos)
                if (dist.get(candidato) < dist.get(camMin))
                    camMin = candidato; 
            nosAbertos.remove(camMin);

            for (Aresta<T> aresta : camMin.getDestinos()) { 
                Vertice<T> vizinho = aresta.getDestino(); 
                if (nosAbertos.contains(vizinho)) {
                    float distanciaAlternativa = dist.get(camMin) + aresta.getPeso();
                    if (distanciaAlternativa < dist.get(vizinho)) { 
                        dist.put(vizinho, distanciaAlternativa); 
                        prev.put(vizinho, camMin);
                    }
                }
            }     
        }

        ArrayList<Vertice<T>> caminhoVertices = new ArrayList<>();
        noAtual = obterVertice(destino);
        if (prev.get(noAtual) != null || noAtual == obterVertice(origem)) {
            while (noAtual != null) {
                caminhoVertices.add(noAtual);
                noAtual = prev.get(noAtual);
            }
        }

        // até aqui permanece igual ao algoritmo de caminho mínimo

        ArrayList<Tupla<Vertice<T>, Aresta<T>>> caminho = new ArrayList<>();

        Tupla<Vertice<T>, Aresta<T>> fluxo = new Tupla<>(); // tupla(nó inicial, aresta(peso, nó final))
        for (int i = 0; i < caminhoVertices.size()-1; i++) {
            Vertice<T> v = caminhoVertices.get(i); // nó inicial
            fluxo.setPrimeiroValor(v);
            for (Aresta<T> a : v.getDestinos()) {
                if (a.getDestino().getValor().compareTo(caminhoVertices.get(i+1).getValor()) == 0) {
                    fluxo.setSegundoValor(a); // aresta correspondente ao caminho desejado
                }
            }
            caminho.add(fluxo);
        }

        return caminho;
    }

    public void setVertices(ArrayList<Vertice<T>> vertices) {
        this.vertices = vertices;
    }

    public ArrayList<Vertice<T>> getVertices() {
        return vertices;
    }

}

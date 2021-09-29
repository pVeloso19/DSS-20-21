package ArmazemLN.Robots;


import ArmazemLN.Armazenamento.Localizacao;

import java.util.*;

/**
 * Classe que representa o Mapa do armazém, representado por um grafo.
 *
 * @author Carlos Preto (a89587)
 * @author Maria João Moreira (a89540)
 * @author Pedro Veloso (a89557)
 * @author Rui Fernandes (a89138)
 */
public class Mapa {
    /**
     * Variável de instância única da classe Mapa.
     */
    private static Mapa singleton = null;

    /**
     * Nodo de receção 1.
     */
    private final Integer nodoRececaoR1 = 1;

    /**
     * Nodo de receção 2.
     */
    private final Integer nodoRececaoR2 = 17;

    /**
     * Nodo relativo à queue da zona de entrega.
     */
    private final Integer nodoEntrega = 15;

    /**
     * Nodo relativo à queue da zona de receção.
     */
    private final Integer queueRececao = 18;

    /**
     * Mapa do armazém, representado como um grafo.
     */
    private Map<Integer, Set<Integer>> mapaArmazem;

    /**
     * Construtor por omissão para objetos da classe Mapa.
     */
    public Mapa() {
        this.mapaArmazem = new HashMap<>();

        for (int i = 1; i <= 17; i++) {
            this.mapaArmazem.put(i, new HashSet<>());
        }

        this.mapaArmazem.get(1).add(2);
        this.mapaArmazem.get(1).add(17);

        this.mapaArmazem.get(2).add(1);
        this.mapaArmazem.get(2).add(3);
        this.mapaArmazem.get(2).add(8);

        this.mapaArmazem.get(3).add(2);
        this.mapaArmazem.get(3).add(4);

        this.mapaArmazem.get(4).add(3);
        this.mapaArmazem.get(4).add(5);

        this.mapaArmazem.get(5).add(4);
        this.mapaArmazem.get(5).add(6);

        this.mapaArmazem.get(6).add(5);
        this.mapaArmazem.get(6).add(7);

        this.mapaArmazem.get(7).add(6);
        this.mapaArmazem.get(7).add(14);

        this.mapaArmazem.get(8).add(2);
        this.mapaArmazem.get(8).add(9);

        this.mapaArmazem.get(9).add(8);
        this.mapaArmazem.get(9).add(10);

        this.mapaArmazem.get(10).add(9);
        this.mapaArmazem.get(10).add(11);

        this.mapaArmazem.get(11).add(10);
        this.mapaArmazem.get(11).add(12);

        this.mapaArmazem.get(12).add(11);
        this.mapaArmazem.get(12).add(13);

        this.mapaArmazem.get(13).add(12);
        this.mapaArmazem.get(13).add(14);
        this.mapaArmazem.get(13).add(16);

        this.mapaArmazem.get(14).add(7);
        this.mapaArmazem.get(14).add(13);
        this.mapaArmazem.get(14).add(15);

        this.mapaArmazem.get(15).add(14);

        this.mapaArmazem.get(16).add(13);

        this.mapaArmazem.get(17).add(1);

    }

    /**
     * Implementação do padrão Singleton.
     *
     * @return Devolve a instância única desta classe.
     */
    public static Mapa getInstance() {
        if (Mapa.singleton == null) {
            Mapa.singleton = new Mapa();
        }
        return Mapa.singleton;
    }

    /**
     * Método que, dado o identificador de um robot, determina qual a localização de entrada em que este opera.
     *
     * @param codRobot Identificador do robot.
     * @return Localização de entrada em que o robot opera.
     */
    public Localizacao getLocalizacaoEntrada(String codRobot) {
        if (codRobot.equals("R1"))
            return new Localizacao(nodoRececaoR1);
        else
            return new Localizacao(nodoRececaoR2);
    }

    /**
     * Método que determina o caminho que um robot deve percorrer de modo a chegar de uma origem a um destino.
     *
     * @param source Localização de origem.
     * @param target Localização de destino.
     * @return Percurso que o robot deve percorrer.
     */
    private List<Integer> getCaminhoDSP(Integer source, Integer target) {
        Set<Integer> q = new HashSet<>();
        Map<Integer, Integer> dist = new HashMap<>();
        Map<Integer, Integer> prev = new HashMap<>();

        for (Integer v : this.mapaArmazem.keySet()) {
            dist.put(v, Integer.MAX_VALUE);
            q.add(v);
        }
        dist.put(source, 0);
        boolean found = false;
        while (!q.isEmpty() && found == false) {
            Integer u = q.stream().sorted(Comparator.comparingInt(v -> dist.get(v))).findFirst().get();

            if (!u.equals(target)) {
                q.remove(u);
                for (Integer v : this.mapaArmazem.get(u)) {
                    int alt = dist.get(u) + 1;
                    if (alt < dist.get(v)) {
                        dist.put(v, alt);
                        prev.put(v, u);
                    }
                }
            } else {
                found = true;
            }
        }

        List<Integer> res = new ArrayList<>();
        Integer u = target;
        if (prev.containsKey(u) || u.equals(source)) {
            while (prev.containsKey(u)) {
                res.add(0, u);
                u = prev.get(u);
            }
        }

        return res;
    }

    /**
     * Método que determina o percurso entre entre a localização atual e um determinado destino.
     *
     * @param localizacaoAtual Localização atual.
     * @param destino          Localização de Destino.
     * @return Percurso entre entre a localização atual e um determinado destino.
     */
    public Percurso calculaPercurso(Localizacao localizacaoAtual, Localizacao destino) {
        return new Percurso(this.getCaminhoDSP(localizacaoAtual.getNodo(), destino.getNodo()));
    }

    /**
     * Método que devolve a Queue associada à zona de receção.
     *
     * @return Queue associada à zona de receção.
     */
    public Localizacao getQueueRececao() {
        return new Localizacao(this.queueRececao);
    }

    /**
     * Método que devolve as localizações de entrada do armazém.
     *
     * @return Localizações de entrada do armazém.
     */
    public List<Localizacao> getLocalizacoesEntrada() {
        List<Localizacao> res = new ArrayList<>();
        res.add(new Localizacao(this.nodoRececaoR1));
        res.add(new Localizacao(this.nodoRececaoR2));
        return res;
    }
}

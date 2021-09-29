package ArmazemLN.Robots;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe que representa um percurso percorrido por um robot.
 *
 * @author Carlos Preto (a89587)
 * @author Maria João Moreira (a89540)
 * @author Pedro Veloso (a89557)
 * @author Rui Fernandes (a89138)
 */
public class Percurso {
    /**
     * Lista de nodos a percorrer.
     */
    private List<Integer> nodosPercorrer;

    /**
     * Construtor parametrizado para objetos da classe Percurso.
     *
     * @param nodosPercorrer Lista com os nodos que devem ser percorridos pelo robot.
     */
    public Percurso(List<Integer> nodosPercorrer) {
        this.setNodosPercorrer(nodosPercorrer);
    }

    /**
     * Construtor de cópia para objetos da classe Percurso.
     *
     * @param p Instância da classe Percurso a partir da qual se instancia um novo objeto.
     */
    public Percurso(Percurso p) {
        this.nodosPercorrer = p.getNodosPercorrer();
    }

    /**
     * Método que devolve quais os nodos a percorrer.
     *
     * @return Lista com os nodos que devem ser percorridos pelo robot.
     */
    public List<Integer> getNodosPercorrer() {
        List<Integer> res = new ArrayList<>();
        for (Integer nodo : this.nodosPercorrer) {
            res.add(nodo);
        }
        return res;
    }

    /**
     * Método que altera quais os nodos que o robot deve percorrer.
     *
     * @param nodosPercorrer Lista com os nodos que devem ser percorridos pelo robot.
     */
    public void setNodosPercorrer(List<Integer> nodosPercorrer) {
        this.nodosPercorrer = new ArrayList<>();
        for (Integer nodo : nodosPercorrer) {
            this.nodosPercorrer.add(nodo);
        }
    }

    /**
     * Implementação do método toString.
     *
     * @return Representação textual do objeto sobre o qual o método é invocado.
     */
    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        if (this.nodosPercorrer.size() > 0) {
            sb.append("Percurso: ");
            for (int i = 0; i < this.nodosPercorrer.size(); i++) {
                sb.append(this.nodosPercorrer.get(i));
                if (i + 1 < this.nodosPercorrer.size())
                    sb.append(" -> ");
            }
            sb.append(";");
        } else {
            sb.append("Sem Percurso;");
        }
        return sb.toString();
    }

    /**
     * Implementação do método clone.
     *
     * @return Cópia do objeto sobre o qual o método é invocado.
     */
    @Override
    public Percurso clone() {
        return new Percurso(this);
    }

    public Percurso avancaPercurso() {
        if (this.nodosPercorrer.size() > 0) {
            this.nodosPercorrer.remove(0);
        }
        return new Percurso(this.nodosPercorrer);
    }

    /**
     * Método que determina se existe um percurso, isto é, se existem nodos a percorrer.
     *
     * @return true caso existam nodos a percorrer, false caso contrário.
     */
    public boolean havePercurso() {
        return this.nodosPercorrer.size() != 0;
    }

    /**
     * Método que determina o próxima localização do percurso.
     *
     * @return Próxima localização do percurso.
     */
    public Integer getProxLocalizacao() {
        return this.nodosPercorrer.get(0);
    }
}

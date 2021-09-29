package ArmazemLN.Armazenamento;

/**
 * Classe que representa uma localização do armazém.
 *
 * @author Carlos Preto (a89587)
 * @author Maria João Moreira (a89540)
 * @author Pedro Veloso (a89557)
 * @author Rui Fernandes (a89138)
 */
public class Localizacao {
    /**
     * Inteiro que representa um nodo do grafo que representa o mapa armazém.
     */
    private Integer nodo;

    /**
     * Construtor parametrizado para objetos da classe Nodo.
     *
     * @param nodo Inteiro que representa um nodo.
     */
    public Localizacao(Integer nodo) {
        this.nodo = nodo;
    }

    /**
     * Consturtor de cópia para objetos da classe Nodo.
     *
     * @param l Instância da classe nodo a partir da qual se instancia um novo objeto.
     */
    public Localizacao(Localizacao l) {
        this.nodo = l.getNodo();
    }

    /**
     * Método que devolve o inteiro que representa o nodo.
     *
     * @return Inteiro que representa um nodo.
     */
    public Integer getNodo() {
        return this.nodo;
    }

    /**
     * Implementação do método clone.
     *
     * @return Cópia do objeto sobre o qual o método é invocado.
     */
    @Override
    public Localizacao clone() {
        return new Localizacao(this);
    }

    /**
     * Implementação do método toString.
     *
     * @return Representação textual do objeto sobre o qual o método é invocado.
     */
    @Override
    public String toString() {
        return "Nodo Nº: " + this.nodo + ";";
    }

    /**
     * Implementação do método equals.
     *
     * @param obj Objeto com o qual se testa a igualdade.
     * @return true caso o objeto argumento for igual ao objeto sobre o qual o método é chamado,
     * false caso contrário
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        Localizacao l = (Localizacao) obj;
        return (this.nodo == l.getNodo());
    }
}
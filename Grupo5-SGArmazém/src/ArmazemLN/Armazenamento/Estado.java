package ArmazemLN.Armazenamento;

/**
 * Classe que representa o estado de uma prateleira.
 *
 * @author Carlos Preto (a89587)
 * @author Maria João Moreira (a89540)
 * @author Pedro Veloso (a89557)
 * @author Rui Fernandes (a89138)
 */
public enum Estado {
    /**
     * Identifica uma prateleira livre.
     */
    LIVRE(0),

    /**
     * Identifica uma prateleira ocupada.
     */
    OCUPADA(1),

    /**
     * Identifica uma prateleira à espera de uma palete.
     */
    ESPERA(2);

    /**
     * Valor associado ao estado.
     */
    private final int valor;

    /**
     * Método que dado um inteiro devolve o estado associado a esse inteiro.
     *
     * @param valor Valor associado ao estado.
     */
    Estado(int valor) {
        this.valor = valor;
    }
}

package ArmazemLN.Armazenamento;

/**
 * Classe que representa um tipo de matéria prima (perecível ou não perecível).
 *
 * @author Carlos Preto (a89587)
 * @author Maria João Moreira (a89540)
 * @author Pedro Veloso (a89557)
 * @author Rui Fernandes (a89138)
 */
public enum MateriaPrima {
    /**
     * Identifica uma matéria prima perecível.
     */
    PERECIVEL(0),

    /**
     * Identifica uma matéria prima não perecivel.
     */
    NAOPERECIVEL(1);


    /**
     * Valor associado ao tipo de matéria prima.
     */
    private final int valor;

    /**
     * Método que dado um inteiro devolve o tipo de matéria prima associado a esse inteiro.
     *
     * @param valor Valor associado ao tipo de matéria prima.
     */
    MateriaPrima(int valor) {
        this.valor = valor;
    }
}

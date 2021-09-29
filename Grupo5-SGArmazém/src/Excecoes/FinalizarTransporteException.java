package Excecoes;

/**
 * Classe que representa a exceção lançada quando ocorrem erros relativos à finalização do transporte de carga, por
 * exemplo, quando se notifica um robot para finalizar o transporte de uma carga quando este não transporta nenhuma
 * palete.
 *
 * @author Carlos Preto (a89587)
 * @author Maria João Moreira (a89540)
 * @author Pedro Veloso (a89557)
 * @author Rui Fernandes (a89138)
 */
public class FinalizarTransporteException extends Exception {
    /**
     * Construtor por omissão para objetos da classe FinalizarTransporteException.
     */
    public FinalizarTransporteException() {
        super();
    }

    /**
     * Construtor parametrizado para objetos da classe FinalizarTransporteException.
     *
     * @param erro Mensagem de erro.
     */
    public FinalizarTransporteException(String erro) {
        super(erro);
    }
}

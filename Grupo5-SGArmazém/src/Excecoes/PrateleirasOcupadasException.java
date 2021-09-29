package Excecoes;

/**
 * Classe que representa a exceção lançada quando ocorrem erros relativos à ocupação de prateleiras, por
 * exemplo, quando tenta armazenar uma palete numa prateleira que já está ocupada ou não existam prateleiras
 * livres no armazém.
 *
 * @author Carlos Preto (a89587)
 * @author Maria João Moreira (a89540)
 * @author Pedro Veloso (a89557)
 * @author Rui Fernandes (a89138)
 */
public class PrateleirasOcupadasException extends Exception {
    /**
     * Construtor por omissão para objetos da classe PrateleirasOcupadasException.
     */
    public PrateleirasOcupadasException() {
        super();
    }

    /**
     * Construtor parametrizado para objetos da classe PrateleirasOcupadasException.
     *
     * @param erro Mensagem de erro.
     */
    public PrateleirasOcupadasException(String erro) {
        super(erro);
    }
}

package Excecoes;

/**
 * Classe que representa a exceção lançada quando ocorrem erros relativos à recolha de paletes, por exemplo caso não
 * exista uma palete para transportar ou não seja encontrado um destino.
 *
 * @author Carlos Preto (a89587)
 * @author Maria João Moreira (a89540)
 * @author Pedro Veloso (a89557)
 * @author Rui Fernandes (a89138)
 */
public class RecolhaPaleteException extends Exception{
    /**
     * Construtor por omissão para objetos da classe RecolhaPaleteException.
     */
    public RecolhaPaleteException(){
        super();
    }

    /**
     * Construtor parametrizado para objetos da classe RecolhaPaleteException.
     *
     * @param erro Mensagem de erro.
     */
    public RecolhaPaleteException(String erro){
        super(erro);
    }
}

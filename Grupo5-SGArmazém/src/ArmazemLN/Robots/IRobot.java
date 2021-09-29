package ArmazemLN.Robots;

import ArmazemLN.Armazenamento.Localizacao;
import ArmazemLN.Armazenamento.Palete;
import Excecoes.FinalizarTransporteException;

import java.util.List;

/**
 * Interface que especifica quais os métodos que devem ser implementada pela classe SSRobot_Facade.
 *
 * @author Carlos Preto (a89587)
 * @author Maria João Moreira (a89540)
 * @author Pedro Veloso (a89557)
 * @author Rui Fernandes (a89138)
 */
public interface IRobot {
    /**
     * Método que encontra um robot disponível para transportar uma palete.
     *
     * @return Robot caso exista pelo menos um robot disponível, null caso contrário.
     */
    Robot encontrarRobotLivre();

    /**
     * Método que altera a disponibilidade de um robot.
     *
     * @param codRobot Código do robot.
     * @param d        Disponibilidade.
     */
    void mudarDisponibilidade(String codRobot, Boolean d);

    /**
     * Método que devolve um robot dado o seu identificador.
     *
     * @param codRobot Código do robot.
     * @return Robot.
     */
    Robot getRobot(String codRobot);

    /**
     * Método responsável por terminar o transporte de uma palete.
     *
     * @param codRobot Código do robot.
     * @return null.
     * @throws FinalizarTransporteException Exceção lançada caso o robot não esteja a transportar qualquer palete.
     */
    Palete encerrarTransporte(String codRobot) throws FinalizarTransporteException;

    /**
     * Método que devolve a localizaçao base de um robot.
     *
     * @param codRbot Código do robot.
     * @return Localizaçao base do robot.
     */
    Localizacao getLocalizacaoBase(String codRbot);

    /**
     * Método que devolve a localizaçao de entrada em que um robot opera.
     *
     * @param codRobot Código do robot.
     * @return Localizaçao de entrada em que o robot opera.
     */
    Localizacao getLocalizacaoEntrada(String codRobot);

    /**
     * Método responsável por fazer com que um robot retorne a sua localização base.
     *
     * @param codRobot Código do robot.
     */
    void mandarRobotBase(String codRobot);

    /**
     * Método que permite adicionar um robot ao armazém.
     *
     * @param r Robot a adicionar.
     */
    void putRobot(Robot r);

    /**
     * Método que altera a localização de um robot.
     *
     * @param codRobot Código do robot.
     * @param l        Nova localização do robot.
     */
    void setLocalizacao(String codRobot, Localizacao l);

    /**
     * Método que devolve a Queue associada à zona de receção.
     *
     * @return Queue associada à zona de receção.
     */
    Localizacao getLocalizacaoQueueRececao();

    /**
     * Método que devolve as localizações de entrada do armazém.
     *
     * @return Localizações de entrada do armazém.
     */
    List<Localizacao> getLocalizacoesEntrada();

    /**
     * Método que determina se existe um robot com um determinado código.
     *
     * @param codRobot Código do robot a verificar.
     * @return true caso exista um robot com esse código, false caso contrário.
     */
    boolean existeCodRobot(String codRobot);

    /**
     * Método que determina se um robot tem um percurso associado.
     *
     * @param codRobot Código do robot.
     * @return true caso o robot tenha um percurso associado, false caso contrário.
     */
    boolean havePercurso(String codRobot);

    /**
     * Método que determina se o robot está num local de carga
     *
     * @param codRobot Código do robot.
     * @return true caso o robot esteja num local de carga, false caso contrário.
     */
    boolean robotEstaLocalCarga(String codRobot);

    /**
     * Método que determina se um robot tem uma palete associada.
     *
     * @param codRobot Código do robot.
     * @return true caso o robot tenha uma palete associada, false caso contrário.
     */
    boolean temPaleteAssociada(String codRobot);

    /**
     * Método que finaliza o percurso que um robot está a percorrer.
     *
     * @param codRobot Código do robot que se pretende que finalize o percurso.
     */
    void finalizaPercurso(String codRobot);

    /**
     * Método que determina o percurso que um robot está a percorrer
     * @param codRobot codigo do robot que necessita do percurso
     * @return o percurso que o robot percorre
     */
    Percurso getPercursoRobot(String codRobot);
}

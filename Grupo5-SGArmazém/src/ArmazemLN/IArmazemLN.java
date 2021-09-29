package ArmazemLN;

import ArmazemLN.Armazenamento.Localizacao;
import ArmazemLN.Armazenamento.Palete;
import ArmazemLN.Robots.Percurso;
import Excecoes.FinalizarTransporteException;
import Excecoes.PrateleirasOcupadasException;
import Excecoes.RecolhaPaleteException;

import java.util.Map;

/**
 * Interface que especifica quais os métodos que devem ser implementados pela classe ArmazemLN.
 *
 * @author Carlos Preto (a89587)
 * @author Maria João Moreira (a89540)
 * @author Pedro Veloso (a89557)
 * @author Rui Fernandes (a89138)
 */
public interface IArmazemLN {
    /**
     * Método responsável por processar a leitura de um QR code.
     *
     * @param nome
     * @param tamanho
     * @param tipo    Tipo da matéria prima.
     * @throws PrateleirasOcupadasException Exceção lançada caso não existam prateleiras livres.
     */
    void processaLeituraQR(String nome, Double tamanho, int tipo) throws PrateleirasOcupadasException;

    /**
     * Método que notifica um robot relativamente à recolha de uma palete.
     *
     * @param codRobot Código do robot.
     * @throws RecolhaPaleteException Exceção lançada caso não exista uma palete para transportar ou não seja
     *                                encontrado um destino.
     */
    void notificarRecolha(String codRobot) throws RecolhaPaleteException;

    /**
     * Método que notifica o robot relativamente à entrega de uma palete.
     *
     * @param codRobot Código do robot.
     * @throws FinalizarTransporteException Exceção lançada caso o robot não esteja a transportar nenhuma palete.
     */
    void notificarEntrega(String codRobot) throws FinalizarTransporteException;

    /**
     * Método que lista a localização de cada uma das paletes.
     *
     * @return Paletes e respetiva localização.
     */
    Map<Palete, String> listarLocalizacoes();

    /**
     * Método que altera a localização de um robot.
     *
     * @param codRobot Código do robot a notificar.
     * @param l        Nova localização do robot.
     */
    void setLocalizacaoRobot(String codRobot, Localizacao l);

    /**
     * Método que determina se existe um robot com um dado código.
     *
     * @param codRobot Código do robot cuja existência se pretede verificar.
     * @return true caso o robot exista, false caso contrário.
     */
    boolean existeCodRobot(String codRobot);

    /**
     * Método de determina se um robot tem um percurso associado.
     *
     * @param codRobot Código do robot.
     * @return true caso o robot tenha um percurso associado, false caso contrário.
     */
    boolean existePercursoPercorrer(String codRobot);

    /**
     * Método que determina se um robot está num local de carga.
     *
     * @param codRobot Código do robot.
     * @return true caso o robot esteja num local de carga, false caso contrário.
     */
    boolean robotEstaLocalCarga(String codRobot);

    /**
     * Método que determina se um dado robot tem uma palete associada.
     *
     * @param codRobot Código do robot.
     * @return true caso o robot tenha uma palete associada, false caso contrário.
     */
    boolean robotTemPaleteAssociada(String codRobot);

    /**
     * Método que determina o percurso que um robot está a percorrer
     * @param codRobot codigo do robot que necessita do percurso
     * @return o percurso que o robot percorre
     */
    Percurso getPercurso(String codRobot);
}

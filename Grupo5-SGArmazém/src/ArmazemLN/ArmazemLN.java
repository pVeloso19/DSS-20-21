package ArmazemLN;

import ArmazemLN.Armazenamento.IArmazenamento;
import ArmazemLN.Armazenamento.Localizacao;
import ArmazemLN.Armazenamento.Palete;
import ArmazemLN.Armazenamento.SSArmazenamento_Facade;
import ArmazemLN.Robots.IRobot;
import ArmazemLN.Robots.Percurso;
import ArmazemLN.Robots.Robot;
import ArmazemLN.Robots.SSRobot_Facade;
import Excecoes.FinalizarTransporteException;
import Excecoes.PrateleirasOcupadasException;
import Excecoes.RecolhaPaleteException;

import java.util.Map;

/**
 * Classe que implementa a lógica de negócio do armazém.
 *
 * @author Carlos Preto (a89587)
 * @author Maria João Moreira (a89540)
 * @author Pedro Veloso (a89557)
 * @author Rui Fernandes (a89138)
 */
public class ArmazemLN implements IArmazemLN {
    /**
     * Subsistema referente ao armazenamento.
     */
    private IArmazenamento armazenamento;

    /**
     * Subsistema referente aos robots.
     */
    private IRobot robots;

    /**
     * Construtor por omissão para objetos da classe ArmazemLN.
     */
    public ArmazemLN() {
        this.armazenamento = new SSArmazenamento_Facade();
        this.robots = new SSRobot_Facade();
    }

    /**
     * Método responsável por processar a leitura de um QR code.
     *
     * @param nome
     * @param tamanho
     * @param tipo    Tipo da matéria prima.
     * @throws PrateleirasOcupadasException Exceção lançada caso não existam prateleiras livres.
     */
    public void processaLeituraQR(String nome, Double tamanho, int tipo) throws PrateleirasOcupadasException {
        Robot r = this.robots.encontrarRobotLivre();
        Localizacao entrada = (r != null) ? this.robots.getLocalizacaoEntrada(r.getCodRobot()) : this.robots.getLocalizacaoQueueRececao();
        Palete p = this.armazenamento.daEntradaPalete(nome, tamanho, tipo, entrada);
        boolean encontrou = this.armazenamento.encontrarPrateleira(p);

        if (!encontrou)
            throw new PrateleirasOcupadasException("Nenhuma prateleira livre no armazém");

        if (r != null) {
            this.notificarRobot(r.getCodRobot(), entrada);
        } else {
            this.armazenamento.adicionarPaleteQueueRececao(p);
        }
    }

    /**
     * Método responsável por notificar um robot para o transporte de uma palete.
     *
     * @param codRobot Código do robot a notificar.
     * @param l        Localização de destino.
     */
    public void notificarRobot(String codRobot, Localizacao l) {
        Robot r = this.robots.getRobot(codRobot);
        r.setDisponibilidade(false);
        r.calculaPercurso(l);
        this.robots.putRobot(r);
    }

    /**
     * Método que notifica um robot relativamente à recolha de uma palete.
     *
     * @param codRobot Código do robot.
     * @throws RecolhaPaleteException Exceção lançada caso não exista uma palete para transportar ou não seja
     *                                encontrado um destino.
     */
    public void notificarRecolha(String codRobot) throws RecolhaPaleteException {
        Robot r = this.robots.getRobot(codRobot);
        r.finalizaPercurso();

        Localizacao localizacaoRobot = r.getLocalizacao();

        Palete p = this.armazenamento.getPaleteBYLocalizacao(localizacaoRobot);

        if (p == null)
            throw new RecolhaPaleteException("Sem palete para carregar.");

        r.associarPaleteRobot(p);
        this.robots.putRobot(r);

        Localizacao proxLocal = this.armazenamento.encontrarPrateleiraPrometida(p);
        if (proxLocal == null)
            throw new RecolhaPaleteException("Sem destino encontrado.");

        this.notificarRobot(codRobot, proxLocal);
    }

    /**
     * Método que notifica o robot relativamente à entrega de uma palete.
     *
     * @param codRobot Código do robot.
     * @throws FinalizarTransporteException Exceção lançada caso o robot não esteja a transportar nenhuma palete.
     */
    public void notificarEntrega(String codRobot) throws FinalizarTransporteException {
        this.robots.finalizaPercurso(codRobot);

        Palete p = this.robots.encerrarTransporte(codRobot);

        this.armazenamento.adicionarPaletePrateleira(p);
        Localizacao l = this.armazenamento.retiraFilaRececao(this.robots.getLocalizacaoEntrada(codRobot));

        if(l != null){
            this.notificarRobot(codRobot,l);
        }else{
            this.robots.mudarDisponibilidade(codRobot,true);
        }
    }

    /**
     * Método que lista a localização de cada uma das paletes.
     *
     * @return Paletes e respetiva localização.
     */
    public Map<Palete, String> listarLocalizacoes() {
        return this.armazenamento.listarPaletesLocalizacao(this.robots.getLocalizacoesEntrada(), this.robots.getLocalizacaoQueueRececao());
    }

    /**
     * Método que altera a localização de um robot.
     *
     * @param codRobot Código do robot a notificar.
     * @param l        Nova localização do robot.
     */
    public void setLocalizacaoRobot(String codRobot, Localizacao l) {
        this.robots.setLocalizacao(codRobot, l);
    }

    /**
     * Método que determina se existe um robot com um dado código.
     *
     * @param codRobot Código do robot cuja existência se pretede verificar.
     * @return true caso o robot exista, false caso contrário.
     */
    public boolean existeCodRobot(String codRobot) {
        return this.robots.existeCodRobot(codRobot);
    }

    /**
     * Método de determina se um robot tem um percurso associado.
     *
     * @param codRobot Código do robot.
     * @return true caso o robot tenha um percurso associado, false caso contrário.
     */
    public boolean existePercursoPercorrer(String codRobot) {
        return this.robots.havePercurso(codRobot);
    }

    /**
     * Método que determina se um robot está num local de carga.
     *
     * @param codRobot Código do robot.
     * @return true caso o robot esteja num local de carga, false caso contrário.
     */
    public boolean robotEstaLocalCarga(String codRobot) {
        return this.robots.robotEstaLocalCarga(codRobot);
    }

    /**
     * Método que determina se um dado robot tem uma palete associada.
     *
     * @param codRobot Código do robot.
     * @return true caso o robot tenha uma palete associada, false caso contrário.
     */
    public boolean robotTemPaleteAssociada(String codRobot) {
        return this.robots.temPaleteAssociada(codRobot);
    }

    /**
     * Método que determina o percurso que um robot está a percorrer
     * @param codRobot codigo do robot que necessita do percurso
     * @return o percurso que o robot percorre
     */
    public Percurso getPercurso(String codRobot) {
        return this.robots.getPercursoRobot(codRobot);
    }
}

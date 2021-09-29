package ArmazemLN.Robots;

import ArmazemLN.Armazenamento.Localizacao;
import ArmazemLN.Armazenamento.Palete;
import Data.RobotsDAO;
import Excecoes.FinalizarTransporteException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Facade relativa ao subsistema referente a robots.
 *
 * @author Carlos Preto (a89587)
 * @author Maria João Moreira (a89540)
 * @author Pedro Veloso (a89557)
 * @author Rui Fernandes (a89138)
 */
public class SSRobot_Facade implements IRobot {
    /**
     * Mapa do armazém.
     */
    private Mapa mapa;

    /**
     * Robots que operam no armazém.
     */
    private Map<String, Robot> robot;

    /**
     * Construtor por omissão para objetos da classe SSRobot_Facade.
     */
    public SSRobot_Facade() {
        this.mapa = new Mapa();
        this.robot = RobotsDAO.getInstance();
    }

    /**
     * Método que encontra um robot disponível para transportar uma palete.
     *
     * @return Robot caso exista pelo menos um robot disponível, null caso contrário.
     */
    public Robot encontrarRobotLivre() {
        Iterator<Robot> it = this.robot.values().iterator();
        boolean encontrou = false;
        Robot res = null;
        while (it.hasNext() && !encontrou) {
            res = it.next();
            encontrou = res.getDisponibilidade();
        }
        return (encontrou) ? res : null;
    }

    /**
     * Método que altera a disponibilidade de um robot.
     *
     * @param codRobot Código do robot.
     * @param d        Disponibilidade.
     */
    public void mudarDisponibilidade(String codRobot, Boolean d) {
        Robot r = this.robot.get(codRobot);
        if (r != null) {
            r.setDisponibilidade(d);
            this.robot.put(r.getCodRobot(), r);
        }
    }

    /**
     * Método que devolve um robot dado o seu identificador.
     *
     * @param codRobot Código do robot.
     * @return Robot.
     */
    public Robot getRobot(String codRobot) {
        return this.robot.get(codRobot);
    }

    /**
     * Método responsável por terminar o transporte de uma palete.
     *
     * @param codRobot Código do robot.
     * @return null.
     * @throws FinalizarTransporteException Exceção lançada caso o robot não esteja a transportar qualquer palete.
     */
    public Palete encerrarTransporte(String codRobot) throws FinalizarTransporteException {
        Robot r = this.robot.get(codRobot);
        if (r != null) {
            Palete p = r.encerraTransporte();
            this.robot.put(r.getCodRobot(), r);
            return p;
        }
        return null;
    }

    /**
     * Método que devolve a localizaçao base de um robot.
     *
     * @param codRbot Código do robot.
     * @return Localizaçao base do robot.
     */
    public Localizacao getLocalizacaoBase(String codRbot) {
        Robot r = this.robot.get(codRbot);
        Localizacao l = null;
        if (r != null) {
            l = r.getLocalizacaoBase();
        }
        return l;
    }

    /**
     * Método que devolve a localizaçao de entrada em que um robot opera.
     *
     * @param codRobot Código do robot.
     * @return Localizaçao de entrada em que o robot opera.
     */
    public Localizacao getLocalizacaoEntrada(String codRobot) {
        return this.mapa.getLocalizacaoEntrada(codRobot);
    }

    /**
     * Método que devolve a Queue associada à zona de receção.
     *
     * @return Queue associada à zona de receção.
     */
    public Localizacao getLocalizacaoQueueRececao() {
        return this.mapa.getQueueRececao();
    }

    /**
     * Método que devolve as localizações de entrada do armazém.
     *
     * @return Localizações de entrada do armazém.
     */
    public List<Localizacao> getLocalizacoesEntrada() {
        return this.mapa.getLocalizacoesEntrada();
    }

    /**
     * Método que determina se existe um robot com um determinado código.
     *
     * @param codRobot Código do robot a verificar.
     * @return true caso exista um robot com esse código, false caso contrário.
     */
    public boolean existeCodRobot(String codRobot) {
        return (this.robot.get(codRobot) != null);
    }

    /**
     * Método que finaliza o percurso que um robot está a percorrer.
     *
     * @param codRobot Código do robot que se pretende que finalize o percurso.
     */
    public void finalizaPercurso(String codRobot) {
        Robot r = this.robot.get(codRobot);
        if (r != null) {
            r.finalizaPercurso();
            this.robot.put(r.getCodRobot(), r);
        }
    }

    /**
     * Método que determina o percurso que um robot está a percorrer
     * @param codRobot codigo do robot que necessita do percurso
     * @return o percurso que o robot percorre
     */
    public Percurso getPercursoRobot(String codRobot) {
        Robot r = this.robot.get(codRobot);
        if(r!=null)
            return r.getPercurso();

        return new Percurso(new ArrayList<>());
    }

    /**
     * Método que determina se um robot tem um percurso associado.
     *
     * @param codRobot Código do robot.
     * @return true caso o robot tenha um percurso associado, false caso contrário.
     */
    public boolean havePercurso(String codRobot) {
        boolean res = false;
        Robot r = this.robot.get(codRobot);
        if (r != null) {
            res = r.havePercurso();
        }
        return res;
    }

    /**
     * Método que determina se o robot está num local de carga
     *
     * @param codRobot Código do robot.
     * @return true caso o robot esteja num local de carga, false caso contrário.
     */
    public boolean robotEstaLocalCarga(String codRobot) {
        Robot r = this.robot.get(codRobot);
        return (!r.getDisponibilidade() && r.getCodPalete() == null);
    }

    /**
     * Método que determina se um robot tem uma palete associada.
     *
     * @param codRobot Código do robot.
     * @return true caso o robot tenha uma palete associada, false caso contrário.
     */
    public boolean temPaleteAssociada(String codRobot) {
        Robot r = this.robot.get(codRobot);
        return (r.getCodPalete() != null);
    }

    /**
     * Método responsável por fazer com que um robot retorne a sua localização base.
     *
     * @param codRobot Código do robot.
     */
    public void mandarRobotBase(String codRobot) {
        Robot r = this.robot.get(codRobot);
        if (r != null) {
            r.retornarBase();
            this.robot.put(r.getCodRobot(), r);
        }
    }

    /**
     * Método que permite adicionar um robot ao armazém.
     *
     * @param r Robot a adicionar.
     */
    public void putRobot(Robot r) {
        this.robot.put(r.getCodRobot(), r);
    }

    /**
     * Método que altera a localização de um robot.
     *
     * @param codRobot Código do robot.
     * @param l        Nova localização do robot.
     */
    public void setLocalizacao(String codRobot, Localizacao l) {
        Robot r = this.robot.get(codRobot);
        if (r != null) {
            r.setLoccalizacaoAtual(l);
            this.robot.put(r.getCodRobot(), r);
        }
    }
}

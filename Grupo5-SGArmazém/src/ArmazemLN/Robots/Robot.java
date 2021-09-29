package ArmazemLN.Robots;

import ArmazemLN.Armazenamento.Localizacao;
import ArmazemLN.Armazenamento.Palete;
import Data.PaleteDAO;
import Excecoes.FinalizarTransporteException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Classe que representa um robot.
 *
 * @author Carlos Preto (a89587)
 * @author Maria João Moreira (a89540)
 * @author Pedro Veloso (a89557)
 * @author Rui Fernandes (a89138)
 */
public class Robot {
    /**
     * Código do robot.
     */
    private String codRobot;

    /**
     * Disponibilidade do robot.
     */
    private boolean disponibilidade;

    /**
     * Mapa do armazém.
     */
    private Mapa mapa;

    /**
     * Localização base do robot.
     */
    private Localizacao loccalizacaoBase;

    /**
     * Localização atual do robot.
     */
    private Localizacao loccalizacaoAtual;

    /**
     * Percurso que o robot deve percorrer.
     */
    private Percurso percurso;

    /**
     * Código da palete que o robot transporta.
     */
    private String codPalete;

    /**
     * DAO relativo às paletes.
     */
    private Map<String, Palete> palete;

    /**
     * Construtor parametrizado para objetos da classe Robot.
     *
     * @param codRobot          Código do robot.
     * @param disponibilidade   Disponibilidade do robot.
     * @param loccalizacaoBase  Localização base do robot.
     * @param loccalizacaoAtual Localização atual do robot.
     * @param percurso          Percurso que o robot deve percorrer.
     * @param codPalete         Código da palete.
     */
    public Robot(String codRobot, boolean disponibilidade, Localizacao loccalizacaoBase, Localizacao loccalizacaoAtual,
                 Percurso percurso, String codPalete) {
        this.codRobot = codRobot;
        this.disponibilidade = disponibilidade;
        this.mapa = Mapa.getInstance();
        this.loccalizacaoBase = loccalizacaoBase.clone();
        this.loccalizacaoAtual = loccalizacaoAtual.clone();
        this.percurso = percurso.clone();
        this.codPalete = codPalete;
        this.palete = PaleteDAO.getInstance();
    }

    /**
     * Construtor de cópia para objetos da classe Robot.
     *
     * @param r Instância da classe Robot a partir da qual se instancia um novo objeto
     */
    public Robot(Robot r) {
        this.codRobot = r.getCodRobot();
        this.disponibilidade = r.getDisponibilidade();
        this.mapa = Mapa.getInstance();
        this.loccalizacaoBase = r.getLocalizacaoBase();
        this.loccalizacaoAtual = r.getLoccalizacaoAtual();
        this.percurso = r.getPercurso();
        this.codPalete = r.getCodPalete();
        this.palete = PaleteDAO.getInstance();
    }

    /**
     * Método que devolve o código do robot.
     *
     * @return ódigo do robot.
     */
    public String getCodRobot() {
        return this.codRobot;
    }

    /**
     * Método que determina se um robot está livre.
     *
     * @return Robot caso este esteja livre, null caso contrário.
     */
    public Robot estaLivre() {
        if (disponibilidade) {
            return this;
        } else {
            return null;
        }
    }

    /**
     * Método que altera a disponibilidade de um robot.
     *
     * @param b Nova disponibilidade do robot.
     */
    public void setDisponibilidade(boolean b) {
        this.disponibilidade = b;
    }

    /**
     * Método que calcula o percuros que o robot deve percorrer de modo a chegar a uma localização de destino a partir
     * da sua localização atual.
     *
     * @param l Localização de destino.
     */
    public void calculaPercurso(Localizacao l) {
        Percurso p = this.mapa.calculaPercurso(this.loccalizacaoAtual, l);
        this.percurso = (p != null) ? p.clone() : null;
    }

    /**
     * Método que devolve a localização atual do robot.
     *
     * @return Localização atual do robot.
     */
    public Localizacao getLocalizacao() {
        return this.loccalizacaoAtual;
    }

    /**
     * Método que associa uma palete ao robot.
     *
     * @param p Palete a associar ao robot.
     */
    public void associarPaleteRobot(Palete p) {
        this.codPalete = p.getCodPalete();
        p.setLocalizacao(null);
        this.palete.put(this.codPalete, p);
    }

    /**
     * Método que permite que um robot volte a sua localização base.
     */
    public void retornarBase() {
        this.disponibilidade = true;
        this.percurso = this.mapa.calculaPercurso(this.loccalizacaoAtual, this.loccalizacaoBase);
    }

    /**
     * Método que encerra o transporte de uma palete.
     *
     * @return Palete que estava a ser transportada.
     * @throws FinalizarTransporteException Exceção lançada caso o robot não esteja a transportar nenhuma palete.
     */
    public Palete encerraTransporte() throws FinalizarTransporteException {
        if (this.codPalete == null)
            throw new FinalizarTransporteException("Robot não está a transportar nenhuma carga.");

        Palete p = this.palete.get(this.codPalete);
        this.codPalete = null;
        return p;
    }

    /**
     * Método que devolve a localização base do robot.
     *
     * @return Localização base do robot.
     */

    public Localizacao getLocalizacaoBase() {
        return this.loccalizacaoBase;
    }

    /**
     * Método que altera o código do robot.
     *
     * @param codRobot Novo código do robot.
     */
    public void setCodRobot(String codRobot) {
        this.codRobot = codRobot;
    }

    /**
     * Método que devolve a disponibilidade do robot.
     *
     * @return Disponibilidade do robot.
     */
    public boolean getDisponibilidade() {
        return this.disponibilidade;
    }

    /**
     * Método que devolve a localização base do robot.
     *
     * @return Localização base do robot.
     */
    public Localizacao getLoccalizacaoBase() {
        return loccalizacaoBase.clone();
    }

    /**
     * Método que altera a localização base do robot.
     *
     * @param loccalizacaoBase Nova localização base do robot.
     */
    public void setLoccalizacaoBase(Localizacao loccalizacaoBase) {
        this.loccalizacaoBase = loccalizacaoBase.clone();
    }

    /**
     * Método que devolve a localização atual do robot.
     *
     * @return Localização atual do robot.
     */
    public Localizacao getLoccalizacaoAtual() {
        return this.loccalizacaoAtual.clone();
    }

    /**
     * Método que altera a localização atual do robot.
     *
     * @param loccalizacaoAtual Nova localização atual do robot.
     */
    public void setLoccalizacaoAtual(Localizacao loccalizacaoAtual) {
        this.loccalizacaoAtual = loccalizacaoAtual.clone();
    }

    /**
     * Método que devolve o percurso que o robot deve percorrer.
     *
     * @return Percurso que o robot deve percorrer.
     */
    public Percurso getPercurso() {
        return (this.percurso != null) ? this.percurso.clone() : null;
    }

    /**
     * Método que altera o percurso que o robot deve percorrer.
     *
     * @param percurso Novo percurso que o robot deve percorrer.
     */
    public void setPercurso(Percurso percurso) {
        this.percurso = percurso.clone();
    }

    /**
     * Método que devolve o código da palete que o robot está a transportar.
     *
     * @return Código da palete que o robot está a transportar.
     */
    public String getCodPalete() {
        return this.codPalete;
    }

    /**
     * Método que altera o código da palete que o robot está a transportar.
     *
     * @param codPalete Novo código da palete que o robot está a transportar.
     */
    public void setCodPalete(String codPalete) {
        this.codPalete = codPalete;
    }

    /**
     * Implementação do método clone.
     *
     * @return Cópia do objeto sobre o qual o método é invocado.
     */
    @Override
    public Robot clone() {
        return new Robot(this);
    }

    /**
     * Implementação do método toString.
     *
     * @return Representação textual do objeto sobre o qual o método é invocado.
     */
    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("Robot: " + this.codRobot + " | ");
        if (!this.disponibilidade)
            sb.append("Transporta: " + this.codPalete + ";");
        else
            sb.append("Livre;");
        return sb.toString();
    }

    /**
     * Método que devolve a próxima localização no percurso do robot.
     *
     * @return Próxima localização no percurso do robot.
     */
    public Localizacao getProximaLocalizacao() {
        return new Localizacao(this.percurso.getProxLocalizacao());
    }

    /**
     * Método que determina se um robot tem um percurso associado.
     *
     * @return true caso o robot tenha um percurso associado, false caso contrário.
     */
    public boolean havePercurso() {
        return this.percurso.havePercurso();
    }

    /**
     * Método que finaliza o percurso associado a um robot.
     */
    public void finalizaPercurso() {
        List<Integer> nodos = this.percurso.getNodosPercorrer();
        Integer ultimoNodo = nodos.get(nodos.size() - 1);
        this.percurso.setNodosPercorrer(new ArrayList<>());
        this.loccalizacaoAtual = new Localizacao(ultimoNodo);
    }
}

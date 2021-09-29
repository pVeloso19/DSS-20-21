package ArmazemLN.Armazenamento;

import Data.*;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Facade relativa ao subsistema referente ao armazenamento do armazém.
 *
 * @author Carlos Preto (a89587)
 * @author Maria João Moreira (a89540)
 * @author Pedro Veloso (a89557)
 * @author Rui Fernandes (a89138)
 */
public class SSArmazenamento_Facade implements IArmazenamento {
    /**
     * Zona de armazenamento do armazém.
     */
    private ZonaArmazenamento zonaArmazenamento;

    /**
     * Paletes na zona de entrega do armazém.
     */
    private List<Palete> paletesEntrega;

    /**
     * Paletes da zona de receção do armazém.
     */
    private List<Palete> rececao;

    /**
     * Produtos em falta.
     */
    private List<String> produtosFalta;

    /**
     * Paletes.
     */
    private Map<String, Palete> paletes;

    /**
     * DAO relativo ao próximo código das paletes.
     */
    private ProxCodesDAO proxCodes;

    /**
     * Construtor por omissão para objetos da classe SSArmazenamento_Facade.
     */
    public SSArmazenamento_Facade() {
        this.paletes = PaleteDAO.getInstance();
        this.zonaArmazenamento = new ZonaArmazenamento();
        this.paletesEntrega = PaleteEntregaDAO.getInstance();
        this.rececao = RececaoDAO.getInstance();
        this.produtosFalta = PaletesFaltaDAO.getInstance();
        this.proxCodes = new ProxCodesDAO();
    }

    /**
     * Método que lista a localização de cada palete.
     *
     * @param entrada     Lista com as localizações de entrada.
     * @param filaEntrada Localização da fila de entrada.
     * @return Paletes e respetivas localizações.
     */
    public Map<Palete, String> listarPaletesLocalizacao(List<Localizacao> entrada, Localizacao filaEntrada) {
        Map<Palete, String> localizacoes = new HashMap<>();

        for (Palete p : this.paletes.values()) {

            String localTextual = "Robot";

            if (p.getPrateleira() != null) {
                localTextual = p.getPrateleira().getCodPrateleira();
            } else {
                Localizacao l = p.getLocalizacao();
                if (l != null) {
                    if (entrada.stream().map(Localizacao::getNodo).anyMatch(ls -> ls.equals(l.getNodo()))) {
                        localTextual = "Receção";
                    }
                    if (l.equals(filaEntrada)) {
                        localTextual = "Queue Receção";
                    }
                }
            }
            localizacoes.put(p.clone(), localTextual);
        }
        return localizacoes;
    }

    /**
     * Método responsável por dar entrada de uma palete.
     *
     * @param nome    Nome do produto da palete.
     * @param tamanho Tamanho da palete.
     * @param tipo    Tipo de matéria prima.
     * @param entrada Localização de entrada.
     * @return Palete cuja entrada foi registada.
     */
    public Palete daEntradaPalete(String nome, Double tamanho, int tipo, Localizacao entrada) {
        String cod = String.format("p%d", this.proxCodes.getProxCode());
        Palete p = new Palete(cod, nome, tamanho, MateriaPrima.NAOPERECIVEL, entrada);
        this.paletes.put(cod, p);
        return p;
    }

    /**
     * Método que adiciona uma palete à queue de receção.
     *
     * @param p Palete a adicionar.
     */
    public void adicionarPaleteQueueRececao(Palete p) {
        this.rececao.add(p);
    }

    /**
     * Método que devolve uma palete numa determinada localização.
     *
     * @param l Localização.
     * @return Palete nessa localização, null caso não exista nenhuma palete nessa localização.
     */
    public Palete getPaleteBYLocalizacao(Localizacao l) {
        List<Palete> lista = this.paletes.values().stream().collect(Collectors.toList());
        Iterator<Palete> it = lista.iterator();
        boolean encontrou = false;
        Palete p = null;

        while (it.hasNext() && !encontrou) {
            p = it.next();
            Localizacao temp = p.getLocalizacao();
            if (temp != null) {
                encontrou = temp.equals(l);
            }
        }

        if (!encontrou)
            p = null;

        return p;
    }

    /**
     * Método que determina se existe uma prateleira disponível para armazenar uma palete.
     *
     * @param p Palete que se pretende armazenar.
     * @return true caso exista uma prateleira disponível, false caso contrário.
     */
    public boolean encontrarPrateleira(Palete p) {
        return this.zonaArmazenamento.encontrarPrateleiraDisponivel(p);
    }

    /**
     * Método que encontra uma prateleira prometida a uma palete.
     *
     * @param p Palete a armazenar.
     * @return Localizaão da prateleira.
     */
    public Localizacao encontrarPrateleiraPrometida(Palete p) {
        return this.zonaArmazenamento.encontrarPrateleiraPrometida(p);
    }

    /**
     * Método responsável por adicionar uma palete a uma prateleira.
     *
     * @param p Palete a armazenar.
     */
    public void adicionarPaletePrateleira(Palete p) {
        Prateleira prat = this.zonaArmazenamento.ocupaPrateleira(p);

        if (prat == null)
            throw new NullPointerException("Prateleira nao encontrada");

        p.setCodPrateleira(prat.getCodPrateleira());
        p.setLocalizacao(prat.getLocalizacao());
        this.paletes.put(p.getCodPalete(), p);
    }

    /**
     * Método que remove uma palete da fila de receção.
     *
     * @param l Nova localização da palete.
     * @return null
     */
    public Localizacao retiraFilaRececao(Localizacao l) {
        Palete p = this.rececao.remove(0);
        if (p != null) {
            p.setLocalizacao(l);
            this.paletes.put(p.getCodPalete(), p);
            return p.getLocalizacao();
        }
        return null;
    }
}

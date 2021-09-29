package ArmazemLN.Armazenamento;

import Data.PrateleiraDAO;

import java.util.Map;

/**
 * Classe que representa uma palete.
 *
 * @author Carlos Preto (a89587)
 * @author Maria João Moreira (a89540)
 * @author Pedro Veloso (a89557)
 * @author Rui Fernandes (a89138)
 */
public class Palete {

    /**
     * Código da palete.
     */
    private String codPalete;

    /**
     * Nome do produto da palete.
     */
    private String nomeProduto;

    /**
     * Tamanho da palete.
     */
    private Double tamanho;

    /**
     * Disponibilidade da Palete.
     */
    private boolean disponivel;

    /**
     * Código da prateleira em que a palete é colocada.
     */
    private String codPrateleira;

    /**
     * DAO relativo às prateleiras.
     */
    private Map<String, Prateleira> prateleira;

    /**
     * Matéria prima da palete (perecível ou não perecível).
     */
    private MateriaPrima materiaPrima;

    /**
     * Localização da palete.
     */
    private Localizacao localizacao;

    /**
     * Construtor parametrizado para objetos da classe Palete.
     *
     * @param codPalete    Código da palete.
     * @param nomeProduto  Nome do produto da palete.
     * @param tamanho      Tamanho da palete.
     * @param materiaPrima Matéria prima da palete (perecível ou não perecível).
     * @param l            Localização da palete.
     */
    public Palete(String codPalete, String nomeProduto, Double tamanho, MateriaPrima materiaPrima, Localizacao l) {
        this.codPalete = codPalete;
        this.nomeProduto = nomeProduto;
        this.tamanho = tamanho;
        this.disponivel = true;
        this.codPrateleira = null;
        this.prateleira = PrateleiraDAO.getInstance();
        this.materiaPrima = materiaPrima;
        this.localizacao = l;
    }

    /**
     * Construtor parametrizado para objetos da classe Palete.
     *
     * @param codPalete     Código da palete.
     * @param nomeProduto   Nome do produto da palete.
     * @param tamanho       Tamanho da palete.
     * @param disponivel    Disponibilidade da Palete.
     * @param codPrateleira Código da prateleira em que a palete é colocada.
     * @param materiaPrima  Matéria prima da palete (perecível ou não perecível).
     * @param localizacao   Localização da palete.
     */
    public Palete(String codPalete, String nomeProduto, Double tamanho, boolean disponivel, String codPrateleira,
                  MateriaPrima materiaPrima, Localizacao localizacao) {
        this.codPalete = codPalete;
        this.nomeProduto = nomeProduto;
        this.tamanho = tamanho;
        this.disponivel = disponivel;
        this.codPrateleira = codPrateleira;
        this.prateleira = PrateleiraDAO.getInstance();
        this.materiaPrima = materiaPrima;
        this.localizacao = (localizacao != null) ? localizacao.clone() : null;
    }

    /**
     * Consturotr Parametrizado para objetos da classe Palete.
     *
     * @param p Instância da classe Palete a partir da qual se instancia um novo objeto.
     */
    public Palete(Palete p) {
        this.codPalete = p.getCodPalete();
        this.nomeProduto = p.getNomeProduto();
        this.tamanho = p.getTamanho();
        this.disponivel = p.getDisponibilidade();
        this.codPrateleira = p.getCodPrateleira();
        this.prateleira = PrateleiraDAO.getInstance();
        this.materiaPrima = p.getMateriaPrima();
        this.localizacao = p.getLocalizacao();
    }

    /**
     * Método que altera o código da palete.
     *
     * @param codPalete Novo código da palete.
     */
    public void setCodPalete(String codPalete) {
        this.codPalete = codPalete;
    }

    /**
     * Método que devolve o nome do produto.
     *
     * @return Nome do produto.
     */
    public String getNomeProduto() {
        return this.nomeProduto;
    }

    /**
     * Método que altera o nome do produto da palete.
     *
     * @param nomeProduto Novo nome do produto da palete.
     */
    public void setNomeProduto(String nomeProduto) {
        this.nomeProduto = nomeProduto;
    }

    /**
     * Método que altera o tamanho da palete.
     *
     * @param tamanho Novo tamanho da palete.
     */
    public void setTamanho(Double tamanho) {
        this.tamanho = tamanho;
    }

    /**
     * Método que devolve a disponibilidade da palete.
     *
     * @return Disponibilidade da palete.
     */
    public boolean getDisponibilidade() {
        return this.disponivel;
    }

    /**
     * Método que altera a disponibilidade da palete.
     *
     * @param disponivel Nova disponibilidade da palete.
     */
    public void setDisponivel(boolean disponivel) {
        this.disponivel = disponivel;
    }

    /**
     * Método que devolve o código da prateleira em que a palete é colocada.
     *
     * @return Código da prateleira em que a palete é colocada.
     */
    public String getCodPrateleira() {
        return this.codPrateleira;
    }

    /**
     * Método que altera o código da prateleira em que a palete é colocada.
     *
     * @param codPrateleira Novo código da prateleira em que a palete é colocada.
     */
    public void setCodPrateleira(String codPrateleira) {
        this.codPrateleira = codPrateleira;
    }

    /**
     * Método que altera o DAO associado às prateleiras.
     *
     * @param prateleira Novo DAO associado às prateleiras.
     */
    public void setPrateleira(Map<String, Prateleira> prateleira) {
        this.prateleira = prateleira;
    }

    /**
     * Método que devolve o tipo de matéria prima da palete (perecível ou não perecível).
     *
     * @return Tipo de matéria prima da palete.
     */
    public MateriaPrima getMateriaPrima() {
        return materiaPrima;
    }

    /**
     * Método que altera o tipo de matéria prima da palete (perecível ou não perecível).
     *
     * @param materiaPrima Novo tipo de matéria prima.
     */
    public void setMateriaPrima(MateriaPrima materiaPrima) {
        this.materiaPrima = materiaPrima;
    }

    /**
     * Método que altera a localização da palete.
     *
     * @param localizacao Nova localização da palete.
     */
    public void setLocalizacao(Localizacao localizacao) {
        if (localizacao != null)
            this.localizacao = localizacao.clone();
        else
            this.localizacao = null;
    }

    /**
     * Método que devolve o código da palete.
     *
     * @return Código da palete.
     */
    public String getCodPalete() {
        return this.codPalete;
    }

    /**
     * Método que devolve a localização de uma palete.
     *
     * @return Localização da palete.
     */
    public Localizacao getLocalizacao() {
        return (this.localizacao != null) ? this.localizacao.clone() : null;
    }

    /**
     * Método que devolve o tamanho da palete.
     *
     * @return Tamanho da palete.
     */
    public Double getTamanho() {
        return this.tamanho;
    }

    /**
     * Método que devolve o código da prateleira em que a palete é colocada.
     *
     * @return Tamanho da prateleira em que a palete é colocada.
     */
    public Prateleira getPrateleira() {
        return this.prateleira.get(this.codPrateleira);
    }

    /**
     * Implementação do método clone.
     *
     * @return Cópia do objeto sobre o qual o método é invocado.
     */
    @Override
    public Palete clone() {
        return new Palete(this);
    }

    /**
     * Implementação do método toString.
     *
     * @return Representação textual do objeto sobre o qual o método é invocado.
     */
    @Override
    public String toString() {
        String d = "Disponivel;";
        if (!this.disponivel)
            d = "Indisponivel;";
        return "Nome: " + this.nomeProduto + " | Tamanho: " + this.tamanho + " | " + d;
    }
}

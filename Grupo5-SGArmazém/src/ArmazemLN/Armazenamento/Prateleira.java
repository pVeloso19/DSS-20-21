package ArmazemLN.Armazenamento;

/**
 * Classe que representa uma prateleira.
 *
 * @author Carlos Preto (a89587)
 * @author Maria João Moreira (a89540)
 * @author Pedro Veloso (a89557)
 * @author Rui Fernandes (a89138)
 */
public class Prateleira {
    /**
     * Código da prateleira.
     */
    private String codPrateleira;

    /**
     * Tamanho da prateleira.
     */
    private Double tamanho;

    /**
     * Código da palete que está na prateleira.
     */
    private String codPalete;

    /**
     * Estado da prateleira.
     */
    private Estado estado;

    /**
     * Localização da prateleira.
     */
    private Localizacao localizacao;

    /**
     * Construtor parametrizado para objetos da classe Prateleira.
     *
     * @param codPrateleira Código da prateleira.
     * @param tamanho       Tamanho da prateleira.
     * @param codPalete     Código da palete que está na prateleira.
     * @param estado        Estado da prateleira.
     * @param localizacao   Localização da prateleira.
     */
    public Prateleira(String codPrateleira, Double tamanho, String codPalete, Estado estado, Localizacao localizacao) {
        this.codPrateleira = codPrateleira;
        this.tamanho = tamanho;
        this.codPalete = codPalete;
        this.estado = estado;
        this.localizacao = localizacao.clone();
    }

    /**
     * Consturtor de cópia para objetos da classe Prateleira.
     *
     * @param p Instância da classe Prateleira a partir da qual se instancia um novo objeto.
     */
    public Prateleira(Prateleira p) {
        this.codPrateleira = p.getCodPrateleira();
        this.tamanho = p.getTamanho();
        this.codPalete = p.getCodPalete();
        this.estado = p.getEstado();
        this.localizacao = p.getLocalizacao();
    }

    /**
     * Método que devolve o código da prateleira.
     *
     * @return CDódigo da prateleira.
     */
    public String getCodPrateleira() {
        return this.codPrateleira;
    }

    /**
     * Método que devolve o tamanho da prateleira.
     *
     * @return Tamanho da prateleira.
     */
    public Double getTamanho() {
        return this.tamanho;
    }

    /**
     * Método que devolve o estado da prateleira.
     *
     * @return Estado da prateleira.
     */
    public Estado getEstado() {
        return this.estado;
    }

    /**
     * Método que altera o código da palete.
     *
     * @param codPalete
     */
    public void setCodPalete(String codPalete) {
        this.codPalete = codPalete;
    }

    /**
     * Método que altera o estado da prateleira.
     *
     * @param estado Novo estado da prateleira.
     */
    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    /**
     * Método que devolve o código da palete que está na prateleira.
     *
     * @return Código da palete que está na prateleira.
     */
    public String getCodPalete() {
        return this.codPalete;
    }

    /**
     * Método que devolve a localização da prateleira.
     *
     * @return Localização da prateleira.
     */
    public Localizacao getLocalizacao() {
        return this.localizacao.clone();
    }

    /**
     * Método que altera o código da prateleira.
     *
     * @param codPrateleira Novo código da prateleira.
     */
    public void setCodPrateleira(String codPrateleira) {
        this.codPrateleira = codPrateleira;
    }

    /**
     * Método que altera o tamanho da prateleira.
     *
     * @param tamanho Novo tamanho da prateleira.
     */
    public void setTamanho(Double tamanho) {
        this.tamanho = tamanho;
    }

    /**
     * Método que altera a localização da prateleira.
     *
     * @param localizacao Nova localização da prateleira.
     */
    public void setLocalizacao(Localizacao localizacao) {
        this.localizacao = localizacao.clone();
    }

    /**
     * Implementação do método clone.
     *
     * @return Cópia do objeto sobre o qual o método é invocado.
     */
    @Override
    public Prateleira clone() {
        return new ArmazemLN.Armazenamento.Prateleira(this);
    }

    /**
     * Implementação do método toString.
     *
     * @return Representação textual do objeto sobre o qual o método é invocado.
     */
    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("Prateleira: " + this.codPrateleira + " | ");
        sb.append("Estado: ");
        switch (this.estado) {
            case ESPERA:
                sb.append("Espera;");
                break;
            case OCUPADA:
                sb.append("Ocupada;");
                break;
            default:
                sb.append("Livre;");
                break;
        }
        return sb.toString();
    }

}

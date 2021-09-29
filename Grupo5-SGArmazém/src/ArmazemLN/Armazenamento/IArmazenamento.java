package ArmazemLN.Armazenamento;

import java.util.List;
import java.util.Map;

/**
 * Interface que especifica quais os métodos que devem ser implementados pela classe SSArmazenamento_Facade.
 *
 * @author Carlos Preto (a89587)
 * @author Maria João Moreira (a89540)
 * @author Pedro Veloso (a89557)
 * @author Rui Fernandes (a89138)
 */
public interface IArmazenamento {
    /**
     * Método que lista a localização de cada palete.
     *
     * @param l1 Lista com as localizações de entrada.
     * @param l2 Localização da fila de entrada.
     * @return Paletes e respetivas localizações.
     */
    Map<Palete, String> listarPaletesLocalizacao(List<Localizacao> l1, Localizacao l2);

    /**
     * Método responsável por dar entrada de uma palete.
     *
     * @param nome    Nome do produto da palete.
     * @param tamanho Tamanho da palete.
     * @param tipo    Tipo de matéria prima.
     * @param entrada Localização de entrada.
     * @return Palete cuja entrada foi registada.
     */
    Palete daEntradaPalete(String nome, Double tamanho, int tipo, Localizacao entrada);

    /**
     * Método que adiciona uma palete à queue de receção.
     *
     * @param p Palete a adicionar.
     */
    void adicionarPaleteQueueRececao(Palete p);

    /**
     * Método que devolve uma palete numa determinada localização.
     *
     * @param l Localização.
     * @return Palete nessa localização, null caso não exista nenhuma palete nessa localização.
     */
    Palete getPaleteBYLocalizacao(Localizacao l);


    /**
     * Método que determina se existe uma prateleira disponível para armazenar uma palete.
     *
     * @param p Palete que se pretende armazenar.
     * @return true caso exista uma prateleira disponível, false caso contrário.
     */
    boolean encontrarPrateleira(Palete p);

    /**
     * Método que encontra uma prateleira prometida a uma palete.
     *
     * @param p Palete a armazenar.
     * @return Localizaão da prateleira.
     */
    Localizacao encontrarPrateleiraPrometida(Palete p);

    /**
     * Método responsável por adicionar uma palete a uma prateleira.
     *
     * @param p Palete a armazenar.
     */
    void adicionarPaletePrateleira(Palete p);

    /**
     * Método que remove uma palete da fila de receção.
     *
     * @param l Nova localização da palete.
     * @return null
     */
    Localizacao retiraFilaRececao(Localizacao l);
}

package ArmazemLN.Armazenamento;

import Data.PrateleiraDAO;

import java.util.Iterator;
import java.util.Map;

/**
 * Classe que representa uma zona de armazenamento.
 *
 * @author Carlos Preto (a89587)
 * @author Maria João Moreira (a89540)
 * @author Pedro Veloso (a89557)
 * @author Rui Fernandes (a89138)
 */
public class ZonaArmazenamento {
    /**
     * Prateleiras da zona de armazenamento.
     */
    private Map<String, Prateleira> prateleiras;

    /**
     * Construtor por omissão para objetos da classe ZonaArmazenamento.
     */
    public ZonaArmazenamento() {
        this.prateleiras = PrateleiraDAO.getInstance();
    }

    /**
     * Método que encontra uma prateleira disponível para colocar uma determina palete.
     *
     * @param p Palete que se pretende armazenar.
     * @return true caso seja possível encontrar uma prateleira capaz de armazenar a palete, false caso contrário.
     */
    public boolean encontrarPrateleiraDisponivel(Palete p) {
        Double tam = p.getTamanho();
        Iterator<Prateleira> it = this.prateleiras.values().iterator();
        boolean encontrou = false;

        while (it.hasNext() && !encontrou) {
            Prateleira prat = it.next();
            Double tamPrateleira = prat.getTamanho();
            Estado e = prat.getEstado();
            if (tamPrateleira >= tam && e == Estado.LIVRE) {
                encontrou = true;
                prat.setEstado(Estado.ESPERA);
                prat.setCodPalete(p.getCodPalete());

                this.prateleiras.put(prat.getCodPrateleira(), prat);
            }
        }

        return encontrou;
    }

    /**
     * Método que encontra uma prateleira onde deve ser coloada uma determinada palete.
     *
     * @param p Palete que se prentede armazenar.
     * @return Localização da prateleira onde a palete será armazenada, null caso a prateleira não exista.
     */
    public Localizacao encontrarPrateleiraPrometida(Palete p) {
        Iterator<Prateleira> it = this.prateleiras.values().iterator();
        boolean encontrou = false;
        Localizacao res = null;

        while (it.hasNext() && !encontrou) {
            Prateleira prat = it.next();
            if (prat.getCodPalete() != null && prat.getCodPalete().equals(p.getCodPalete())) {
                encontrou = true;
                res = prat.getLocalizacao();
            }
        }
        return res;
    }

    /**
     * Método responsável por ocupar uma prateleira com uma determinada palete.
     *
     * @param p Palete que se pretende armazenar.
     * @return Prateleira em que se armazena a palete.
     */
    public Prateleira ocupaPrateleira(Palete p) {
        Iterator<Prateleira> it = this.prateleiras.values().iterator();
        boolean encontrou = false;
        Prateleira prat = null;

        while (it.hasNext() && !encontrou) {
            prat = it.next();
            if (prat.getCodPalete() != null && prat.getCodPalete().equals(p.getCodPalete())) {
                encontrou = true;
                prat.setEstado(Estado.OCUPADA);
                this.prateleiras.put(prat.getCodPrateleira(), prat);
            }
        }
        return (encontrou) ? prat : null;
    }
}

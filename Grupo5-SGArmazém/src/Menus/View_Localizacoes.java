package Menus;

import ArmazemLN.Armazenamento.Palete;

import java.util.*;

/**
 * Classe responsável que implementa um menu relativo à localização das paletes.
 *
 * @author Carlos Preto (a89587)
 * @author Maria João Moreira (a89540)
 * @author Pedro Veloso (a89557)
 * @author Rui Fernandes (a89138)
 */
public class View_Localizacoes {

    /**
     * Varíavel de classe para suportar leitura
     */
    private static Scanner input = new Scanner(System.in);

    /**
     * Opção selecionada
     */
    private String selectedOption;

    /**
     * Constructor for objects of class Menu
     */
    public View_Localizacoes() {
        this.selectedOption = null;
    }

    /**
     * Método para apresentar o menu e ler uma opção.
     *
     * @param d      Dados a mostrar.
     * @param inicio Inicio.
     * @param salto  Salto.
     */
    public void executa(Map<Palete, String> d, int inicio, int salto) {
        do {
            showLocalizacoes(d, inicio, salto);
            this.selectedOption = readOption();
        } while (this.selectedOption == null);
    }

    /**
     * Método que apresenta o menu sob a forma de páginas.
     *
     * @param d      Dados a apresentar.
     * @param inicio Inicio.
     * @param salto  Sato.
     */
    private void showLocalizacoes(Map<Palete, String> d, int inicio, int salto) {
        int i = 0;
        int fim = inicio + salto;

        for (Map.Entry<Palete, String> m : d.entrySet()) {
            if (i >= inicio && i < fim) {
                System.out.println("Código: " + m.getKey().getCodPalete() +
                        " | " +
                        "Nome: " + m.getKey().getNomeProduto() +
                        " | " +
                        "Localização: " + m.getValue());
            }
            i++;
        }
        System.out.println("(0) Sair | (+) Avançar página | (-) Retroceder página");
    }

    /**
     * Método responsável por ler uma opção válida.
     *
     * @return Opção escolhida.
     */
    private String readOption() {

        String option = null;

        System.out.print("Opção: ");
        try {
            option = input.next().trim();

            if (option.compareToIgnoreCase("0") != 0 && !option.equals("+") && !option.equals("-")) {
                System.out.println("Opção Inválida!!!");
                option = null;
            }
        } catch (InputMismatchException e) { // Não foi inscrito um int
            option = null;
            System.out.println(e.toString());
        }
        return option;
    }

    /**
     * Método para obter a última opção lida
     */
    public String getLastOption() {
        return this.selectedOption;
    }
}

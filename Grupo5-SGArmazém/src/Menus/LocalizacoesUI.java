package Menus;

import ArmazemLN.Armazenamento.Palete;

import java.util.Map;
import java.util.Scanner;

/**
 * Classe que implementa uma user interface, em modo texto, relativa às localizações das paletes.
 *
 * @author Carlos Preto (a89587)
 * @author Maria João Moreira (a89540)
 * @author Pedro Veloso (a89557)
 * @author Rui Fernandes (a89138)
 */
public class LocalizacoesUI {
    /**
     * Dados a mostrar.
     */
    private Map<Palete,String> dadosMostrar;

    /**
     * Menu relativo a localizações.
     */
    private View_Localizacoes menuLocalizacoes;

    /**
     * Scanner que permite fazer a leitura.
     */
    private Scanner input;

    /**
     * Inicio.
     */
    private int inicio;

    /**
     * Salto
     */
    private int salto;

    /**
     * Construtor parametrizado para objetos da classe LocalizacoesUI.
     *
     * @param dado Dados a mostrar
     */
    public LocalizacoesUI(Map<Palete,String> dado) {
        this.menuLocalizacoes = new View_Localizacoes();
        this.dadosMostrar = dado;
        this.input = new Scanner(System.in);

        this.inicio = 0;
        this.salto = 1;
    }

    /**
     * Método responsável pela execução do menu.
     */
    public void run() {
        do {
            this.menuLocalizacoes.executa(this.dadosMostrar,this.inicio,this.salto);

            String opc = this.menuLocalizacoes.getLastOption();

            if(opc.equals("+")){
                if(this.inicio+this.salto < this.dadosMostrar.size()) this.inicio += this.salto;
            }

            if(opc.equals("-")){
                if(this.inicio-this.salto >= 0) this.inicio -= this.salto;
            }

        } while (this.menuLocalizacoes.getLastOption().compareToIgnoreCase("0")!=0); // A opção 0 é usada para sair do menu.
    }
}

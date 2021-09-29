/*
 * DISCLAIMER: Este código foi criado para discussão e edição durante as aulas práticas de DSS,
 * representando uma solução em construção. Como tal, não deverá ser visto como uma solução
 * canónica, ou mesmo acabada.
 * É disponibilizado para auxiliar o processo de estudo. Os alunos são encorajados a testar
 * adequadamente o código fornecido e a procurar soluções alternativas, à medida que forem
 * adquirindo mais conhecimentos.
 */
package Menus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * Esta classe implementa um menu em modo texto.
 *
 * @author Pedro Veloso
 * @author Carlos Preto
 *
 * @version v2.0 (20201215)
 */
public class Menu {

    // Interfaces auxiliares

    /** Functional interface para handlers. */
    public interface MenuHandler {
        void execute();
    }

    /** Functional interface para ShowInfo. */
    public interface MenuShowInfo {
        void execute();
    }

    /** Functional interface para pré-condições. */
    public interface MenuPreCondition {
        boolean validate();
    }

    // Varíável de classe para suportar leitura

    private static Scanner is = new Scanner(System.in);

    // Variáveis de instância

    private List<String> opcoes;                // Lista de opções
    private List<MenuPreCondition> disponivel;  // Lista de pré-condições
    private List<MenuHandler> handlers;         // Lista de handlers

    private List<MenuShowInfo> info;            // Lista de info para mostrar

    private String nome;
    // Construtor

    /**
     * Constructor for objects of class Menu
     */
    public Menu(String[] opcoes, String nome) {
        this.opcoes = Arrays.asList(opcoes);
        this.disponivel = new ArrayList<>();
        this.handlers = new ArrayList<>();
        this.opcoes.forEach(s-> {
            this.disponivel.add(()->true);
            this.handlers.add(()->System.out.println("\nATENÇÃO: Opção não implementada!"));
        });
        this.nome = nome;

        this.info = new ArrayList<>();
    }

    // Métodos de instância

    /**
     * Correr o menu.
     *
     * Termina com a opção 0 (zero).
     */
    public void run() {
        int op;
        do {
            show();
            op = readOption();
            // testar pré-condição
            if (op>0 && !this.disponivel.get(op-1).validate()) {
                System.out.println("Opção indisponível! Tente novamente.");
            } else if (op>0) {
                // executar handler
                this.handlers.get(op-1).execute();
            }
        } while (op != 0);
    }

    /**
     * Método que regista uma uma pré-condição numa opção do menu.
     *
     * @param i índice da opção (começa em 1)
     * @param b pré-condição a registar
     */
    public void setPreCondition(int i, MenuPreCondition b) {
        this.disponivel.set(i-1,b);
    }

    /**
     * Método para registar um handler numa opção do menu.
     *
     * @param i indice da opção  (começa em 1)
     * @param h handlers a registar
     */
    public void setHandler(int i, MenuHandler h) {
        this.handlers.set(i-1, h);
    }

    // Métodos auxiliares

    /** Apresentar o menu */
    private void show() {
        System.out.println("\n *** Menu"+nome+" *** ");
        for (int i=0; i<this.opcoes.size(); i++) {
            System.out.print(i+1);
            System.out.print(" - ");
            System.out.println(this.disponivel.get(i).validate()?this.opcoes.get(i):"---");
        }
        System.out.println("0 - Sair");
        this.showInfo();
    }

    /** Apresentar a informação extra necessaria */
    private void showInfo(){
        if(!this.info.isEmpty()){
            System.out.print("*************");
            for (int i = 0; i < this.nome.length(); i++) {
                System.out.print("*");
            }
            System.out.print("\n");

            for (MenuShowInfo opt : this.info) {
                opt.execute();
            }

            System.out.print("*************");
            for (int i = 0; i < this.nome.length(); i++) {
                System.out.print("*");
            }
            System.out.print("\n");
        }
    }

    /**
     * Método para registar um handler numa informação extra a mostrar.
     *
     * @param h handlers a registar
     */
    public void setShowInfo(MenuShowInfo h) {
        this.info.add(h);
    }

    /** Ler uma opção válida */
    private int readOption() {
        int op;
        //Scanner is = new Scanner(System.in);

        System.out.print("Opção: ");
        try {
            String line = is.nextLine();
            op = Integer.parseInt(line);
        }
        catch (NumberFormatException e) { // Não foi inscrito um int
            op = -1;
        }
        if (op<0 || op>this.opcoes.size()) {
            System.out.println("Opção Inválida!!!");
            op = -1;
        }
        return op;
    }
}

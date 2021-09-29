/*
 *  DISCLAIMER: Este código foi criado para discussão e edição durante as aulas práticas de DSS, representando
 *  uma solução em construção. Como tal, não deverá ser visto como uma solução canónica, ou mesmo acabada.
 *  É disponibilizado para auxiliar o processo de estudo. Os alunos são encorajados a testar adequadamente o
 *  código fornecido e a procurar soluções alternativas, à medida que forem adquirindo mais conhecimentos.
 */
package Menus;

import ArmazemLN.Armazenamento.Palete;
import ArmazemLN.IArmazemLN;
import ArmazemLN.ArmazemLN;
import Excecoes.FinalizarTransporteException;
import Excecoes.PrateleirasOcupadasException;
import Excecoes.RecolhaPaleteException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.Scanner;

/**
 * Classe que implementa uma User Interface em modo texto.
 *
 * @author Carlos Preto (a89587)
 * @author Maria João Moreira (a89540)
 * @author Pedro Veloso (a89557)
 * @author Rui Fernandes (a89138)
 */
public class TextUI {
    /**
     * Model, que tem a 'lógica de negócio'.
     */
    private IArmazemLN model;

    /**
     * Scanner para leitura.
     */
    private Scanner scin;

    /**
     * BufferedReader para leitura.
     */
    private BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

    /**
     * Construtor para objetos da classe TextUI. Cria os menus e a camada de negócio.
     */
    public TextUI() {
        this.model = new ArmazemLN();
        scin = new Scanner(System.in);
    }

    /**
     * Método que executa o menu principal e invoca o método correspondente à opção seleccionada.
     */
    public void run() {
        System.out.println("Bem vindo ao Sistema de Gestão de Armazém!");
        this.menuPrincipal();
        System.out.println("Até breve...");
    }

    // Métodos auxiliares - Estados da UI

    /**
     * Estado - Menu Principal
     */
    private void menuPrincipal() {
        Menu menu = new Menu(new String[]{
                "Leitor QR-Code",
                "Robot",
                "Gestor"
        }, "");

        // Registar pré-condições das transições
        //menu.setPreCondition(2, ()->this.model.haAlunos() && this.model.haTurmas());

        // Registar os handlers
        menu.setHandler(1, () -> leitorQRCode());
        menu.setHandler(2, () -> leitorRobotCodigo());
        menu.setHandler(3, () -> leitorGestor());

        menu.run();
    }

    /**
     * Estado - Leitor de QR-Code
     */
    private void leitorQRCode() {
        Menu menu = new Menu(new String[]{
                "Adicionar palete"
        }, " QR-Code");

        // Registar os handlers
        menu.setHandler(1, () -> adicionarPalete());

        menu.run();
    }

    /**
     * Estado - Adicionar uma palete no sistema
     */
    private void adicionarPalete() {
        try{
            System.out.println("Insira a linha de código QR (Nome Tamanho Tipo)");
            String inputQR = in.readLine();

            String[] res = inputQR.split(" ");
            if(res.length==3){
                boolean correuBem = true;
                String name = res[0].trim();

                Double size = 0.0;
                try {
                    size = Double.parseDouble(res[1].trim());
                }catch (NumberFormatException e){
                    System.out.println("Formato utilizado no tamanho é inválido");
                    correuBem = false;
                }

                int type = 0;
                if(correuBem){
                    try {
                        type = Integer.parseInt(res[2].trim());
                        if(type!=0 && type!=1){
                            System.out.println("Tipo Inválido (Use 0 ou 1)");
                            correuBem = false;
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Formato utilizado no tipo é inválido");
                        correuBem = false;
                    }
                }

                if(correuBem){
                    try {
                        this.model.processaLeituraQR(name, size, type);
                        System.out.println("Leitura realizada com Sucesso!");
                    } catch (PrateleirasOcupadasException e) {
                        System.out.println(e.getMessage());
                    }
                }

            }else{
                System.out.println("Formato inválido");
            }
        }catch (IOException e){
            System.out.println("Erro ao ler o QR-Code: "+e.getMessage());
        }
    }

    /**
     * Método responsável por ler um código de um robot.
     */
    private void leitorRobotCodigo() {
        boolean certo = false;
        String codRobot = null;
        do {
            System.out.printf("Insira Código do Robot: ");
            codRobot = scin.next().trim();
            codRobot = codRobot.toUpperCase();
            certo = this.model.existeCodRobot(codRobot);
            if (!certo)
                System.out.println(" Código Inválido.");
        } while (!certo);

        this.leitorRobot(codRobot);
    }

    /**
     * Menu relativo a um determinado robot.
     *
     * @param codRobot Código do robot.
     */
    private void leitorRobot(String codRobot) {
        String nome = " Robot "+codRobot;
        Menu menu = new Menu(new String[]{
                "Notificar Recolha",
                "Notificar Entrega"
        },nome);

        //Pré-Condições
        menu.setPreCondition(1, ()->this.model.robotEstaLocalCarga(codRobot));
        menu.setPreCondition(2, ()->this.model.robotTemPaleteAssociada(codRobot));

        //Informação a mostrar (Percurso que está a percorrer)
        menu.setShowInfo(()->showPercurso(codRobot));

        // Registar os handlers
        menu.setHandler(1, ()->execute_notificarRecolha(codRobot));
        menu.setHandler(2, ()->execute_notificarEntrega(codRobot));

        menu.run();
    }

    /**
     * Função que apresenta o percurso que o robot esta a percorrer
     * @param codRobot codigo do robot qyue necessita da informação
     */
    private void showPercurso(String codRobot){
        System.out.println(this.model.getPercurso(codRobot).toString());
    }


    /**
     * Método que imprime uma mensagem relativa a notificação de recolha.
     *
     * @param codRobot Código do robot.
     */
    private void execute_notificarRecolha(String codRobot) {
        try {
            this.model.notificarRecolha(codRobot);
            System.out.println("Notificação Completada com Sucesso!");
        } catch (RecolhaPaleteException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Método que imprime uma mensagem relativa a notificação de entrega.
     *
     * @param codRobot Código do robot.
     */
    private void execute_notificarEntrega(String codRobot) {

        try {
            this.model.notificarEntrega(codRobot);
            System.out.println("Notificação Completada com Sucesso!");
        } catch (FinalizarTransporteException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Menu relativo ao gestor do armazém.
     */
    private void leitorGestor() {
        Menu menu = new Menu(new String[]{
                "Listar Localizações"
        }, " Gestor");

        // Registar os handlers
        menu.setHandler(1, () -> execute_listarLocalizacoes());

        menu.run();
    }

    /**
     * Método que lista as localizações das paletes.
     */
    private void execute_listarLocalizacoes() {
        Map<Palete, String> localizacoes = model.listarLocalizacoes();

        if(!localizacoes.isEmpty()){
            new LocalizacoesUI(localizacoes).run();
        }else{
            System.out.println("O armazém não tem nenhuma palete armazenada.");
        }
    }
}

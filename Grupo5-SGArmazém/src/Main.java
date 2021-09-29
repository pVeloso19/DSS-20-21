import Menus.TextUI;

/**
 * Classe responsável pela execução do programa.
 *
 * @author Carlos Preto (a89587)
 * @author Maria João Moreira (a89540)
 * @author Pedro Veloso (a89557)
 * @author Rui Fernandes (a89138)
 */
public class Main {

    /**
     * Método responsável pela execução do programa.
     *
     * @param args
     */
    public static void main(String[] args) {
        try {
            new TextUI().run();
        }
        catch (Exception e) {
            System.out.println("Não foi possível arrancar: "+e.getMessage());
        }
        System.exit(0);
    }
}

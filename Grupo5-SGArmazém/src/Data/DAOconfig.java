package Data;

public class DAOconfig {

    /*
     * Classe responsavel por defenir os dados sobre a base de dados
     * criada para suportar o sistema.
     */

    /** Nome do usuário da BD criada  */
    static final String USERNAME = "root";
    /** Password do usuário da BD criada  */
    static final String PASSWORD = "Pedro1234";
    /** Nome da BD criada  */
    private static final String DATABASE = "ArmazemDSS";

    /** Driver para o SGBD escolhido (mariadb ou mysql)  */
    private static final String DRIVER = "jdbc:mysql";            // Usar para MySQL
    //private static final String DRIVER = "jdbc:mariadb";        // Usar para MariaDB

    /** URL utilizado para conectar á BD  */
    static final String URL = DRIVER+"://localhost:3306/"+DATABASE;
}

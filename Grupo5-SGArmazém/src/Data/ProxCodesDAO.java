package Data;

import java.sql.*;

/**
 * DAO relativo ao próximo código das paletes.
 *
 * @author Carlos Preto (a89587)
 * @author Maria João Moreira (a89540)
 * @author Pedro Veloso (a89557)
 * @author Rui Fernandes (a89138)
 */
public class ProxCodesDAO {
    private int proxCode;

    private static ProxCodesDAO singleton = null;

    /**
     * Construtor para objetos da classe ProxCodesDAO.
     */
    public ProxCodesDAO() {
        try (Connection conn =
                     DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement()) {
            String sql = "CREATE TABLE IF NOT EXISTS `ArmazemDSS`.`ProxCode` (\n" +
                    "  `ProxCode` INT NOT NULL,\n" +
                    "  PRIMARY KEY (`ProxCode`))\n" +
                    "ENGINE = InnoDB;";
            stm.executeUpdate(sql);

            try (ResultSet rs = stm.executeQuery("SELECT count(*) from ProxCode;")) {
                int i = 0;
                if (rs.next()) {
                    i = rs.getInt(1);
                }

                if (i == 0) {
                    stm.executeUpdate("INSERT INTO ProxCode VALUES (0)");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }

        try (Connection conn =
                     DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery("SELECT * FROM ProxCode")) {
            if (rs.next()) {
                this.proxCode = rs.getInt("ProxCode");
            }
        } catch (Exception e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
    }

    /**
     * Implementação do padrão Singleton
     *
     * @return devolve a instância única desta classe
     */
    public static ProxCodesDAO getInstance() {
        if (ProxCodesDAO.singleton == null) {
            ProxCodesDAO.singleton = new ProxCodesDAO();
        }
        return ProxCodesDAO.singleton;
    }

    /**
     * Método que devolve o próximo código da palete.
     *
     * @return Próximo código da palete.
     */
    public int getProxCode() {
        int r = this.proxCode;
        this.proxCode++;
        try (Connection conn =
                     DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement()) {
            stm.executeUpdate("TRUNCATE ProxCode");
            stm.executeUpdate("INSERT INTO ProxCode VALUES ('" + this.proxCode + "')");
        } catch (SQLException e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return r;
    }
}

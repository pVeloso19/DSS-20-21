package Data;

import ArmazemLN.Armazenamento.Localizacao;
import ArmazemLN.Robots.Percurso;
import ArmazemLN.Robots.Robot;

import java.sql.*;
import java.util.*;

/**
 * DAO para Robots.
 *
 * @author Carlos Preto (a89587)
 * @author Maria João Moreira (a89540)
 * @author Pedro Veloso (a89557)
 * @author Rui Fernandes (a89138)
 */
public class RobotsDAO implements Map<String, Robot> {
    private static RobotsDAO singleton = null;

    /**
     * Construtor para objetos da classe RobotsDAO.
     */
    public RobotsDAO() {
        try (Connection conn =
                     DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement()) {
            String sql = "CREATE TABLE IF NOT EXISTS `ArmazemDSS`.`Robot` (\n" +
                    "  `CodRobot` VARCHAR(10) NOT NULL,\n" +
                    "  `Disponibilidade` TINYINT NOT NULL,\n" +
                    "  `Localizacao` INT NOT NULL,\n" +
                    "  `Base` INT NOT NULL,\n" +
                    "  `CodPalete` VARCHAR(10) NULL,\n" +
                    "  PRIMARY KEY (`CodRobot`),\n" +
                    "  INDEX `fk_Robot_Palete1_idx` (`CodPalete` ASC) VISIBLE,\n" +
                    "  CONSTRAINT `fk_Robot_Palete1`\n" +
                    "    FOREIGN KEY (`CodPalete`)\n" +
                    "    REFERENCES `ArmazemDSS`.`Palete` (`CodPalete`)\n" +
                    "    ON DELETE NO ACTION\n" +
                    "    ON UPDATE NO ACTION)\n" +
                    "ENGINE = InnoDB;";
            stm.executeUpdate(sql);

            sql = "CREATE TABLE IF NOT EXISTS `ArmazemDSS`.`Percurso` (\n" +
                    "  `PosPercurso` INT NOT NULL,\n" +
                    "  `CodRobot` VARCHAR(10) NOT NULL,\n" +
                    "  `Nodo` INT NOT NULL,\n" +
                    "  PRIMARY KEY (`PosPercurso`, `CodRobot`),\n" +
                    "  INDEX `fk_Percurso_Robot1_idx` (`CodRobot` ASC) VISIBLE,\n" +
                    "  CONSTRAINT `fk_Percurso_Robot1`\n" +
                    "    FOREIGN KEY (`CodRobot`)\n" +
                    "    REFERENCES `ArmazemDSS`.`Robot` (`CodRobot`)\n" +
                    "    ON DELETE NO ACTION\n" +
                    "    ON UPDATE NO ACTION)\n" +
                    "ENGINE = InnoDB;";
            stm.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
    }

    /**
     * Implementação do padrão Singleton
     *
     * @return devolve a instância única desta classe
     */
    public static RobotsDAO getInstance() {
        if (RobotsDAO.singleton == null) {
            RobotsDAO.singleton = new RobotsDAO();
        }
        return RobotsDAO.singleton;
    }

    /**
     * Método que devolve o número de robots na base dados.
     *
     * @return Número de robots na base de dados.
     */
    @Override
    public int size() {
        int i = 0;
        try (Connection conn =
                     DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery("SELECT count(*) FROM Robot")) {
            if (rs.next()) {
                i = rs.getInt(1);
            }
        } catch (Exception e) {
            // Erro a criar tabela...
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return i;
    }

    /**
     * Método que verifica se existem robots na base de dados.
     *
     * @return true caso existam robots na base dados, false caso contrário.
     */
    @Override
    public boolean isEmpty() {
        return (this.size() == 0);
    }

    /**
     * Método que verifica se o ID de um robot existe na base de dados.
     *
     * @param key ID do robot.
     * @return true caso o robot exista na base dados, false caso contrário.
     */
    @Override
    public boolean containsKey(Object key) {
        boolean r;
        try (Connection conn =
                     DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement();
             ResultSet rs =
                     stm.executeQuery("SELECT CodRobot FROM Robot WHERE CodRobot='" + key.toString() + "'")) {
            r = rs.next();
        } catch (SQLException e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return r;
    }

    /**
     * Método que verifica se um robot existe na base de dados.
     *
     * @param value Robot.
     * @return true caso o robot exista na base dados, false caso contrário.
     */
    @Override
    public boolean containsValue(Object value) {
        Robot robot = (Robot) value;
        return this.containsKey(robot.getCodRobot());
    }

    /**
     * Método que devolve um robot dado o seu identificador.
     *
     * @param key ID do robot.
     * @return Robot caso exista, null caso contrário.
     */
    @Override
    public Robot get(Object key) {
        Robot res = null;

        String codRobot = null;
        boolean disponibilidade = false;
        int nodoBase = 0;
        int nodoAtual = 0;
        String codPalete = null;
        List<Integer> percurso = new ArrayList<>();

        try (Connection conn =
                     DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery("SELECT * FROM Robot WHERE CodRobot = '" + key + "'")) {
            while (rs.next()) {
                codRobot = rs.getString("CodRobot");
                disponibilidade = rs.getBoolean("Disponibilidade");
                nodoBase = rs.getInt("Base");
                nodoAtual = rs.getInt("Localizacao");
                codPalete = rs.getString("CodPalete");

            }
        } catch (Exception e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }

        try (Connection conn =
                     DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery("SELECT * FROM Percurso WHERE CodRobot='" + codRobot + "'")) {
            while (rs.next()) {
                percurso.add(rs.getInt("PosPercurso"), rs.getInt("Nodo"));
            }
        } catch (Exception e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }

        if (codRobot != null) {
            res = new Robot(codRobot, disponibilidade,
                    new Localizacao(nodoBase), new Localizacao(nodoAtual),
                    new Percurso(percurso), codPalete);
        }

        return res;
    }

    /**
     * Insere um robot na base de dados.
     *
     * @param key ID do robot.
     * @param r   Robot.
     * @return null.
     */
    @Override
    public Robot put(String key, Robot r) {
        try (Connection conn =
                     DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement()) {

            String codPalete = (r.getCodPalete() != null) ? "'" + r.getCodPalete() + "'" : null;
            String sql = "INSERT INTO robot\n" +
                    "(CodRobot,Disponibilidade,Localizacao,Base,CodPalete)\n" +
                    "VALUES \n" +
                    "('" + r.getCodRobot() + "'," + r.getDisponibilidade() + "," + r.getLocalizacao().getNodo() + "," + r.getLocalizacaoBase().getNodo() + "," + codPalete + ")\n" +
                    "ON DUPLICATE KEY UPDATE \n" +
                    "CodRobot= VALUES(CodRobot),\n" +
                    "        Disponibilidade= VALUES(Disponibilidade),\n" +
                    "        Localizacao= VALUES(Localizacao),\n" +
                    "        Base= VALUES(Base),\n" +
                    "        CodPalete= VALUES(CodPalete);";

            stm.executeUpdate(sql);
            int pos = 0;
            Percurso p = r.getPercurso();
            if (p != null) {
                sql = "DELETE FROM percurso WHERE CodRobot = '" + r.getCodRobot() + "';";
                stm.executeUpdate(sql);
                for (int i : p.getNodosPercorrer()) {
                    sql = "INSERT INTO percurso\n" +
                            "(PosPercurso,CodRobot,Nodo)\n" +
                            "    VALUES\n" +
                            "(" + pos + ",'" + r.getCodRobot() + "'," + i + ")" +
                            "ON DUPLICATE KEY UPDATE \n" +
                            "    PosPercurso = VALUES(PosPercurso),\n" +
                            "    CodRobot = VALUES(CodRobot),\n" +
                            "    Nodo = VALUES(Nodo);";
                    stm.executeUpdate(sql);
                    pos++;
                }
            }
        } catch (SQLException e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return null;
    }

    /**
     * Método remove (Método não implementado).
     *
     * @param key
     * @return
     */
    @Override
    public Robot remove(Object key) {
        throw new NullPointerException("public Robot remove(Object key) not implemented!");
    }

    /**
     * Método putAll (Método não implementado).
     *
     * @param m
     */
    @Override
    public void putAll(Map<? extends String, ? extends Robot> m) {
        throw new NullPointerException("public void putAll(Map<? extends String, ? extends Robot> m) not implemented!");
    }

    /**
     * Método clear (Método não implementado).
     */
    @Override
    public void clear() {
        throw new NullPointerException("public void clear() not implemented!");
    }

    /**
     * Método keySet (Método não implementado).
     *
     * @return
     */
    @Override
    public Set<String> keySet() {
        throw new NullPointerException("public Set<String> keySet() not implemented!");
    }

    /**
     * Método que devolve todos os robots na base de dados.
     *
     * @return Robots na base de dados.
     */
    @Override
    public Collection<Robot> values() {
        List<Robot> res = new ArrayList<>();

        String codRobot = null;
        boolean disponibilidade = false;
        int nodoBase = 0;
        int nodoAtual = 0;
        String codPalete = null;

        try (Connection conn =
                     DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery("SELECT * FROM Robot;")) {
            while (rs.next()) {
                codRobot = rs.getString("CodRobot");
                disponibilidade = rs.getBoolean("Disponibilidade");
                nodoBase = rs.getInt("Base");
                nodoAtual = rs.getInt("Localizacao");
                codPalete = rs.getString("CodPalete");

                res.add(new Robot(codRobot, disponibilidade,
                        new Localizacao(nodoBase), new Localizacao(nodoAtual),
                        new Percurso(new ArrayList<>()), codPalete));

            }
        } catch (Exception e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }

        for (Robot r : res) {
            List<Integer> percurso = new ArrayList<>();
            try (Connection conn =
                         DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
                 Statement stm = conn.createStatement();
                 ResultSet rs = stm.executeQuery("SELECT * FROM Percurso WHERE CodRobot='" + r.getCodRobot() + "'")) {
                while (rs.next()) {
                    percurso.add(rs.getInt("PosPercurso"), rs.getInt("Nodo"));
                }
            } catch (Exception e) {
                // Database error!
                e.printStackTrace();
                throw new NullPointerException(e.getMessage());
            }

            r.setPercurso(new Percurso(percurso));
        }

        return res;
    }

    /**
     * Método entrySet (Método não implementado).
     *
     * @return
     */
    @Override
    public Set<Entry<String, Robot>> entrySet() {
        throw new NullPointerException("public Set<Entry<String, Robot>> entrySet() not implemented!");
    }
}

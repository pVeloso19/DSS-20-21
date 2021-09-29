package Data;

import ArmazemLN.Armazenamento.Estado;
import ArmazemLN.Armazenamento.Localizacao;
import ArmazemLN.Armazenamento.Prateleira;

import java.sql.*;
import java.util.*;

/**
 * DAO para Prateleiras.
 *
 * @author Carlos Preto (a89587)
 * @author Maria João Moreira (a89540)
 * @author Pedro Veloso (a89557)
 * @author Rui Fernandes (a89138)
 */
public class PrateleiraDAO implements Map<String, Prateleira> {
    private static PrateleiraDAO singleton = null;

    /**
     * Construtor para objetos da classe PrateleiraDAO.
     */
    private PrateleiraDAO() {
        try (Connection conn =
                     DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement()) {
            String sql = "CREATE TABLE IF NOT EXISTS `ArmazemDSS`.`Prateleiras` (\n" +
                    "  `CodPrateleira` VARCHAR(10) NOT NULL,\n" +
                    "  `Tamanho` DOUBLE NOT NULL,\n" +
                    "  `Estado` VARCHAR(1) NOT NULL,\n" +
                    "  `Localizacao` INT NOT NULL,\n" +
                    "  `CodPalete` VARCHAR(10) NULL,\n" +
                    "  PRIMARY KEY (`CodPrateleira`),\n" +
                    "  INDEX `fk_Prateleiras_Palete1_idx` (`CodPalete` ASC) VISIBLE,\n" +
                    "  CONSTRAINT `fk_Prateleiras_Palete1`\n" +
                    "    FOREIGN KEY (`CodPalete`)\n" +
                    "    REFERENCES `ArmazemDSS`.`Palete` (`CodPalete`)\n" +
                    "    ON DELETE NO ACTION\n" +
                    "    ON UPDATE NO ACTION)\n" +
                    "ENGINE = InnoDB;";
            stm.executeUpdate(sql);
            ;
        } catch (SQLException e) {
            // Erro a criar tabela...
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
    }

    /**
     * Implementação do padrão Singleton
     *
     * @return devolve a instância única desta classe
     */
    public static PrateleiraDAO getInstance() {
        if (PrateleiraDAO.singleton == null) {
            PrateleiraDAO.singleton = new PrateleiraDAO();
        }
        return PrateleiraDAO.singleton;
    }

    /**
     * Método que devolve o número de prateleiras na base dados.
     *
     * @return Número de prateleiras na base de dados.
     */
    @Override
    public int size() {
        int i = 0;
        try (Connection conn =
                     DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery("SELECT count(*) FROM Prateleiras")) {
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
     * Método que verifica se existem prateleiras na base de dados.
     *
     * @return true caso existam prateleiras na base dados, false caso contrário.
     */
    @Override
    public boolean isEmpty() {
        return (this.size() == 0);
    }

    /**
     * Método que verifica se o ID de uma prateleira existe na base de dados.
     *
     * @param key ID da prateleira.
     * @return true caso a prateleira exista na base dados, false caso contrário.
     */
    @Override
    public boolean containsKey(Object key) {
        boolean r;
        try (Connection conn =
                     DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement();
             ResultSet rs =
                     stm.executeQuery("SELECT CodPrateleira FROM Prateleiras WHERE CodPrateleira='" + key.toString() + "'")) {
            r = rs.next();
        } catch (SQLException e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return r;
    }

    /**
     * Método que verifica se uma prateleira existe na base de dados.
     *
     * @param value Prateleira.
     * @return true caso a prateleira exista na base dados, false caso contrário.
     */
    @Override
    public boolean containsValue(Object value) {
        Prateleira p = (Prateleira) value;
        return this.containsKey(p.getCodPrateleira());
    }

    /**
     * Método que devolve uma prateleira dado o seu identificador.
     *
     * @param key ID da prateleira.
     * @return Prateleira caso exista, null caso contrário.
     */
    @Override
    public Prateleira get(Object key) {
        Prateleira p = null;
        try (Connection conn =
                     DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery("SELECT * FROM Prateleiras WHERE CodPrateleira='" + key + "'")) {
            if (rs.next()) {  // A chave existe na tabela

                String s = rs.getString("Estado");
                Estado estado = null;
                if (s.equals("O"))
                    estado = Estado.OCUPADA;
                if (s.equals("L"))
                    estado = Estado.LIVRE;
                if (s.equals("E"))
                    estado = Estado.ESPERA;

                p = new Prateleira(rs.getString("CodPrateleira"), rs.getDouble("Tamanho"),
                        rs.getString("CodPalete"), estado,
                        new Localizacao(rs.getInt("Localizacao")));
            }
        } catch (SQLException e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return p;
    }

    /**
     * Insere uma prateleira na base de dados.
     *
     * @param key   ID da prateleira.
     * @param value Prateleira.
     * @return null.
     */
    @Override
    public Prateleira put(String key, Prateleira value) {
        Prateleira res = null;
        try (Connection conn =
                     DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement()) {

            String estado = "L";
            if (value.getEstado() == Estado.ESPERA)
                estado = "E";
            if (value.getEstado() == Estado.OCUPADA)
                estado = "O";

            String codPalete = (value.getCodPalete() != null) ? "'" + value.getCodPalete() + "'" : null;
            String sql = "INSERT INTO prateleiras\n" +
                    "(CodPrateleira,Tamanho,Estado,Localizacao,CodPalete)\n" +
                    "VALUES \n" +
                    "('" + value.getCodPrateleira() + "'," + value.getTamanho() + ",'" + estado + "'," + value.getLocalizacao().getNodo() + "," + codPalete + ")" +
                    "ON DUPLICATE KEY UPDATE\n" +
                    "        CodPrateleira = VALUES(CodPrateleira),\n" +
                    "        Tamanho = VALUES(Tamanho),\n" +
                    "        Estado = VALUES(Estado),\n" +
                    "        Localizacao = VALUES(Localizacao),\n" +
                    "        CodPalete = VALUES(CodPalete);";
            stm.executeUpdate(sql);
        } catch (SQLException e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return res;
    }

    /**
     * Remove uma prateleira da base de dados dado o seu ID.
     *
     * @param key ID da prateleira.
     * @return Prateleira que foi removida.
     */
    @Override
    public Prateleira remove(Object key) {
        Prateleira t = this.get(key);
        try (Connection conn =
                     DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement()) {
            stm.executeUpdate("DELETE FROM Prateleiras WHERE CodPrateleira='" + key + "'");
        } catch (Exception e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return t;
    }

    /**
     * Adiciona um conjunto de prateleiras à base de dados.
     *
     * @param m Conjunto de prateleiras a adicionar.
     */
    @Override
    public void putAll(Map<? extends String, ? extends Prateleira> m) {
        for (Prateleira t : m.values()) {
            this.put(t.getCodPrateleira(), t);
        }
    }

    /**
     * Método que remove todas as prateleiras da base de dados.
     */
    @Override
    public void clear() {
        try (Connection conn =
                     DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement()) {
            stm.executeUpdate("TRUNCATE Prateleiras");
        } catch (SQLException e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
    }

    /**
     * Método keySet (Método não implementado)
     *
     * @return
     */
    @Override
    public Set<String> keySet() {
        throw new NullPointerException("Not implemented!");
    }

    /**
     * Método que devolve todas as prateleiras na base de dados.
     *
     * @return Prateleiras na base de dados.
     */
    @Override
    public Collection<Prateleira> values() {
        Collection<Prateleira> col = new HashSet<>();
        try (Connection conn =
                     DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery("SELECT * FROM Prateleiras")) {
            while (rs.next()) {

                String s = rs.getString("Estado");
                Estado estado = null;
                if (s.equals("O"))
                    estado = Estado.OCUPADA;
                if (s.equals("L"))
                    estado = Estado.LIVRE;
                if (s.equals("E"))
                    estado = Estado.ESPERA;

                col.add(new Prateleira(rs.getString("CodPrateleira"), rs.getDouble("Tamanho"),
                        rs.getString("CodPalete"), estado,
                        new Localizacao(rs.getInt("Localizacao"))));
            }
        } catch (Exception e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return col;
    }

    /**
     * Método que devolve todas as prateleiras na base de dados e o respetivo ID.
     *
     * @return Prateleiras e o respetivo ID.
     */
    @Override
    public Set<Entry<String, Prateleira>> entrySet() {

        Map<String, Prateleira> map = new HashMap<>();

        try (Connection conn =
                     DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery("SELECT * FROM Prateleiras")) {
            while (rs.next()) {   // Utilizamos o get para construir as turmas
                String s = rs.getString("Estado");
                Estado estado = null;
                if (s.equals("O"))
                    estado = Estado.OCUPADA;
                if (s.equals("L"))
                    estado = Estado.LIVRE;
                if (s.equals("E"))
                    estado = Estado.ESPERA;

                Prateleira p = new Prateleira(rs.getString("CodPrateleira"), rs.getDouble("Tamanho"),
                        rs.getString("CodPalete"), estado,
                        new Localizacao(rs.getInt("Localizacao")));
                map.put(p.getCodPrateleira(), p);
            }
        } catch (Exception e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return map.entrySet();
    }
}

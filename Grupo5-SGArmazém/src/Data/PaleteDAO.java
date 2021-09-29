package Data;

import ArmazemLN.Armazenamento.Localizacao;
import ArmazemLN.Armazenamento.MateriaPrima;
import ArmazemLN.Armazenamento.Palete;

import java.sql.*;
import java.util.*;

/**
 * DAO para paletes.
 *
 * @author Carlos Preto (a89587)
 * @author Maria João Moreira (a89540)
 * @author Pedro Veloso (a89557)
 * @author Rui Fernandes (a89138)
 */
public class PaleteDAO implements Map<String, Palete> {

    private static PaleteDAO singleton = null;

    /**
     * Construtor para objetos da classe PaleteDAO.
     */
    private PaleteDAO() {
        try (Connection conn =
                     DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement()) {
            String sql = "CREATE TABLE IF NOT EXISTS `ArmazemDSS`.`Palete` (\n" +
                    "  `CodPalete` VARCHAR(10) NOT NULL,\n" +
                    "  `NomePalete` VARCHAR(15) NOT NULL,\n" +
                    "  `Tamanho` DOUBLE NOT NULL,\n" +
                    "  `Disponibilidade` TINYINT NOT NULL,\n" +
                    "  `MateriaPrima` VARCHAR(1) NOT NULL,\n" +
                    "  `Localizacao` INT NULL,\n" +
                    "  `CodPrateleira` VARCHAR(10) NULL,\n" +
                    "  PRIMARY KEY (`CodPalete`))\n" +
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
    public static PaleteDAO getInstance() {
        if (PaleteDAO.singleton == null) {
            PaleteDAO.singleton = new PaleteDAO();
        }
        return PaleteDAO.singleton;
    }

    /**
     * Método que devolve o número de paletes na base dados.
     *
     * @return Número de paletes na base de dados.
     */
    @Override
    public int size() {
        int i = 0;
        try (Connection conn =
                     DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery("SELECT count(*) FROM Palete")) {
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
     * Método que verifica se existem paletes na base de dados.
     *
     * @return true caso existam paletes na base dados, false caso contrário.
     */
    @Override
    public boolean isEmpty() {
        return (this.size() == 0);
    }

    /**
     * Método que verifica se o ID de uma palete existe na base de dados.
     *
     * @param key ID da palete.
     * @return true caso a palete exista na base dados, false caso contrário.
     */
    @Override
    public boolean containsKey(Object key) {
        boolean r;
        try (Connection conn =
                     DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement();
             ResultSet rs =
                     stm.executeQuery("SELECT CodPalete FROM Palete WHERE CodPalete='" + key.toString() + "'")) {
            r = rs.next();
        } catch (SQLException e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return r;
    }

    /**
     * Método que verifica se uma palete existe na base de dados.
     *
     * @param value Palete.
     * @return true caso a palete exista na base dados, false caso contrário.
     */
    @Override
    public boolean containsValue(Object value) {
        Palete p = (Palete) value;
        return this.containsKey(p.getCodPalete());
    }

    /**
     * Método que devolve uma palete dado o seu identificador.
     *
     * @param key ID da palete.
     * @return Palete caso exista, null caso contrário.
     */
    @Override
    public Palete get(Object key) {
        Palete p = null;
        try (Connection conn =
                     DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery("SELECT * FROM Palete WHERE CodPalete='" + key + "'")) {
            if (rs.next()) {  // A chave existe na tabela
                MateriaPrima m = null;
                String temp = rs.getString("MateriaPrima");
                if (temp.equals('N'))
                    m = MateriaPrima.NAOPERECIVEL;
                if (temp.equals('P'))
                    m = MateriaPrima.PERECIVEL;

                Integer nodo = rs.getInt("Localizacao");
                Localizacao l = null;
                if (nodo != 0)
                    l = new Localizacao(nodo);

                p = new Palete(rs.getString("CodPalete"), rs.getString("NomePalete"),
                        rs.getDouble("Tamanho"), rs.getBoolean("Disponibilidade"),
                        rs.getString("CodPrateleira"), m, l);
            }
        } catch (SQLException e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return p;
    }

    /**
     * Insere uma palete na base de dados.
     *
     * @param key ID da palete.
     * @param p   Palete.
     * @return null.
     */
    @Override
    public Palete put(String key, Palete p) {
        Palete res = null;
        try (Connection conn =
                     DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement()) {

            String m = (p.getMateriaPrima() == MateriaPrima.PERECIVEL) ? "P" : "N";
            Integer nodo = (p.getLocalizacao() != null) ? p.getLocalizacao().getNodo() : null;
            String CodPrateleira = (p.getPrateleira() != null) ? "'" + p.getPrateleira().getCodPrateleira() + "'" : null;

            String sql = "INSERT INTO palete\n" +
                    "\t(CodPalete,NomePalete,Tamanho,Disponibilidade,MateriaPrima,Localizacao,CodPrateleira)\n" +
                    "    VALUES\n" +
                    "    ('" + p.getCodPalete() + "','" + p.getNomeProduto() + "'," + p.getTamanho() +
                    "," + p.getDisponibilidade() + ",'" + m + "'," + nodo +
                    "," + CodPrateleira + ")" +
                    "ON DUPLICATE KEY UPDATE\n" +
                    "    CodPalete = VALUES(CodPalete),\n" +
                    "    NomePalete = VALUES(NomePalete),\n" +
                    "    Tamanho = VALUES(Tamanho),\n" +
                    "    Disponibilidade = VALUES(Disponibilidade),\n" +
                    "    MateriaPrima = VALUES(MateriaPrima),\n" +
                    "    Localizacao = VALUES(Localizacao),\n" +
                    "    CodPrateleira = VALUES(CodPrateleira);";
            stm.executeUpdate(sql);
        } catch (SQLException e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return res;
    }

    /**
     * Remove uma palete da base de dados dado o seu ID.
     *
     * @param key ID da palete.
     * @return Palete que foi removida.
     */
    @Override
    public Palete remove(Object key) {
        Palete t = this.get(key);
        try (Connection conn =
                     DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement()) {
            stm.executeUpdate("DELETE FROM Palete WHERE CodPalete='" + key + "'");
        } catch (Exception e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return t;
    }

    /**
     * Adiciona um conjunto de paletes à base de dados.
     *
     * @param m Conjunto de paletes a adicionar.
     */
    @Override
    public void putAll(Map<? extends String, ? extends Palete> m) {
        for (Palete t : m.values()) {
            this.put(t.getCodPalete(), t);
        }
    }

    /**
     * Método que remove todas as paletes da base de dados.
     */
    @Override
    public void clear() {
        try (Connection conn =
                     DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement()) {
            stm.executeUpdate("TRUNCATE Palete");
        } catch (SQLException e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
    }

    /**
     * Método keySet (Método não implementado).
     *
     * @return
     */
    @Override
    public Set<String> keySet() {
        throw new NullPointerException("Not implemented!");
    }

    /**
     * Método que devolve todas as paletes na base de dados.
     *
     * @return paletes na base de dados.
     */
    @Override
    public Collection<Palete> values() {
        Collection<Palete> col = new HashSet<>();
        try (Connection conn =
                     DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery("SELECT * FROM Palete")) {
            while (rs.next()) {  // A chave existe na tabela
                MateriaPrima m = null;
                String temp = rs.getString("MateriaPrima");
                if (temp.equals('N'))
                    m = MateriaPrima.NAOPERECIVEL;
                if (temp.equals('P'))
                    m = MateriaPrima.PERECIVEL;

                Integer nodo = rs.getInt("Localizacao");
                Localizacao l = null;
                if (nodo != 0)
                    l = new Localizacao(nodo);

                col.add(new Palete(rs.getString("CodPalete"), rs.getString("NomePalete"),
                        rs.getDouble("Tamanho"), rs.getBoolean("Disponibilidade"),
                        rs.getString("CodPrateleira"), m, l));
            }
        } catch (Exception e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return col;
    }

    /**
     * Método que devolve todas as paletes na base de dados e o respetivo ID.
     *
     * @return Paletes e o respetivo ID.
     */
    @Override
    public Set<Entry<String, Palete>> entrySet() {

        Map<String, Palete> map = new HashMap<>();

        try (Connection conn =
                     DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery("SELECT * FROM Palete")) {
            while (rs.next()) {  // A chave existe na tabela
                MateriaPrima m = null;
                String temp = rs.getString("MateriaPrima");
                if (temp.equals('N'))
                    m = MateriaPrima.NAOPERECIVEL;
                if (temp.equals('P'))
                    m = MateriaPrima.PERECIVEL;

                Palete p = new Palete(rs.getString("CodPalete"), rs.getString("NomePalete"),
                        rs.getDouble("Tamanho"), rs.getBoolean("Disponibilidade"),
                        rs.getString("CodPrateleira"), m,
                        new Localizacao(rs.getInt("Localizacao")));
                map.put(p.getCodPalete(), p);
            }
        } catch (Exception e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return map.entrySet();
    }
}

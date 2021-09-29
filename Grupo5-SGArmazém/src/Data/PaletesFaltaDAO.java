package Data;

import java.sql.*;
import java.util.*;

/**
 * DAO relativo às paletes em falta.
 *
 * @author Carlos Preto (a89587)
 * @author Maria João Moreira (a89540)
 * @author Pedro Veloso (a89557)
 * @author Rui Fernandes (a89138)
 */
public class PaletesFaltaDAO implements List<String> {
    private static PaletesFaltaDAO singleton = null;

    private List<String> paletesFalta;

    /**
     * Construtor para objetos da classe PaletesFaltaDAO.
     */
    private PaletesFaltaDAO() {
        this.paletesFalta = new ArrayList<>();
        try (Connection conn =
                     DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement()) {
            String sql = "CREATE TABLE IF NOT EXISTS `ArmazemDSS`.`PaletesFalta` (\n" +
                    "  `NomePalete` VARCHAR(10) NOT NULL,\n" +
                    "  PRIMARY KEY (`NomePalete`))\n" +
                    "ENGINE = InnoDB;";
            stm.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }

        try (Connection conn =
                     DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery("SELECT * FROM PaletesFalta")) {
            while (rs.next()) {   // Utilizamos o get para construir as turmas
                this.paletesFalta.add(rs.getString("NomePalete"));
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
    public static PaletesFaltaDAO getInstance() {
        if (PaletesFaltaDAO.singleton == null) {
            PaletesFaltaDAO.singleton = new PaletesFaltaDAO();
        }
        return PaletesFaltaDAO.singleton;
    }

    /**
     * Método que devolve o número de paletes em falta.
     *
     * @return Número de paletes em falta.
     */
    @Override
    public int size() {
        return this.paletesFalta.size();
    }

    /**
     * Método que verifica se existem paletes em falta.
     *
     * @return true caso existam paletes em falta, false caso contrário.
     */
    @Override
    public boolean isEmpty() {
        return this.paletesFalta.isEmpty();
    }

    /**
     * Método que determina se uma palete está em falta.
     *
     * @param o Palete cuja existência se pretende verificar.
     * @return true caso a palete esteja em falta, false caso contrário.
     */
    @Override
    public boolean contains(Object o) {
        return this.contains(o);
    }

    /**
     * Método iterator.
     *
     * @return Iterador.
     */
    @Override
    public Iterator<String> iterator() {
        return this.paletesFalta.iterator();
    }

    /**
     * Método que devolve um array com as paletes.
     *
     * @return Array com as paletes.
     */
    @Override
    public Object[] toArray() {
        return this.paletesFalta.toArray();
    }

    /**
     * Método que devolve um array com as paletes.
     *
     * @return Array com as paletes.
     */
    @Override
    public <T> T[] toArray(T[] a) {
        return this.paletesFalta.toArray(a);
    }

    /**
     * Método que adiciona uma palete às paletes em falta.
     *
     * @param s Código da palete a adicionar.
     * @return true.
     */
    @Override
    public boolean add(String s) {
        try (Connection conn =
                     DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement()) {
            stm.executeUpdate(
                    "INSERT INTO PaletesFalta VALUES ('" + s + "')");
        } catch (SQLException e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return this.add(s);
    }

    /**
     * Método que remove uma palete das paletes em falta.
     *
     * @param o Palete a remover.
     * @return true caso a palete exista, false caso contrário.
     */
    @Override
    public boolean remove(Object o) {
        String s = (String) o;
        try (Connection conn =
                     DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement()) {
            stm.executeUpdate("DELETE FROM PaletesFalta WHERE NomePalete='" + s + "'");
        } catch (SQLException e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return this.remove(o);
    }

    /**
     * Método que verifica se um conjunto de paletes está em falta.
     *
     * @param c Conjunto de paletes cuja existência se pretende verificar.
     * @return true caso as paletes estejam em falta, false caso contrário.
     */
    @Override
    public boolean containsAll(Collection<?> c) {
        return this.containsAll(c);
    }

    /**
     * Método addAll (Método não implementado).
     *
     * @param c
     * @return
     */
    @Override
    public boolean addAll(Collection<? extends String> c) {
        throw new NullPointerException("public boolean addAll(Collection<? extends String> c) not implemented!");
    }

    /**
     * Método addAll (Método não implementado).
     *
     * @param index
     * @param c
     * @return
     */
    @Override
    public boolean addAll(int index, Collection<? extends String> c) {
        throw new NullPointerException("public boolean addAll(Collection<? extends String> c) not implemented!");
    }

    /**
     * Método removeAll (Método não implementado).
     *
     * @param c
     * @return
     */
    @Override
    public boolean removeAll(Collection<?> c) {
        throw new NullPointerException("public boolean removeAll(Collection<?> c) not implemented!");
    }

    /**
     * Método retainAll (Método não implementado).
     *
     * @param c
     * @return
     */
    @Override
    public boolean retainAll(Collection<?> c) {
        throw new NullPointerException("public boolean retainAll(Collection<?> c)not implemented!");
    }

    /**
     * Método que remove todas as paletes das paletes em falta.
     */
    @Override
    public void clear() {
        try (Connection conn =
                     DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement()) {
            stm.executeUpdate("TRUNCATE PaletesFalta");
        } catch (SQLException e) {
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        this.paletesFalta.clear();
    }

    /**
     * Método que devolve uma palete dado o seu índice.
     *
     * @param index Indice da palete.
     * @return Palete caso exista, null caso contrário.
     */
    public String get(int index) {
        return this.paletesFalta.get(index);
    }

    /**
     * Método set (Método não implementado).
     *
     * @param index
     * @param element
     * @return
     */
    @Override
    public String set(int index, String element) {
        throw new NullPointerException("public String set(int index, String element) not implemented!");
    }

    /**
     * Método que adiciona uma palete às paletes em falta.
     *
     * @param index   Indice onde se faz a inserção.
     * @param element Código da palete.
     */
    @Override
    public void add(int index, String element) {
        this.add(element);
        this.add(index, element);
    }

    /**
     * Método que remove uma palete das paletes em falta.
     *
     * @param index Indice do elemento a remover.
     * @return Elemento removido.
     */
    @Override
    public String remove(int index) {
        String s = this.paletesFalta.get(index);
        try (Connection conn =
                     DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement()) {
            stm.executeUpdate("DELETE FROM PaletesFalta WHERE NomePalete='" + s + "'");
        } catch (Exception e) {
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return this.paletesFalta.remove(index);
    }

    /**
     * Método que devolve o índice do código de uma determinada palete.
     *
     * @param o Código da palete.
     * @return Indice.
     */
    @Override
    public int indexOf(Object o) {
        return this.indexOf(o);
    }

    /**
     * Método que devolve o índice da última ocorrência do código de uma dada palete.
     *
     * @param o Código da palete.
     * @return Indice.
     */
    @Override
    public int lastIndexOf(Object o) {
        return this.paletesFalta.lastIndexOf(o);
    }

    /**
     * Método listIterator.
     *
     * @return Iterador.
     */
    @Override
    public ListIterator<String> listIterator() {
        return this.paletesFalta.listIterator();
    }

    /**
     * Método listIterator.
     *
     * @param index Indice do primeiro elemento a ser retornado pelo iterador.
     * @return Iterador.
     */
    @Override
    public ListIterator<String> listIterator(int index) {
        return this.paletesFalta.listIterator(index);
    }

    /**
     * Método que devolve uma sublista dados os seus limites inferior e superior, respetivamente.
     *
     * @param fromIndex Limite inferior.
     * @param toIndex   Limite superior.
     * @return Sublista compreendida entre os limites inferior e superior.
     */
    @Override
    public List<String> subList(int fromIndex, int toIndex) {
        return this.subList(fromIndex, toIndex);
    }
}

package Data;

import ArmazemLN.Armazenamento.Palete;

import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * DAO para paletes na zona de receção.
 *
 * @author Carlos Preto (a89587)
 * @author Maria João Moreira (a89540)
 * @author Pedro Veloso (a89557)
 * @author Rui Fernandes (a89138)
 */
public class RececaoDAO implements List<Palete> {
    private List<String> listaCodPalete;
    private Map<String, Palete> paletes;

    private static RececaoDAO singleton = null;

    /**
     * Construtor para objetos da classe RececaoDAO.
     */
    public RececaoDAO() {
        this.paletes = PaleteDAO.getInstance();
        this.listaCodPalete = new ArrayList<>();
        try (Connection conn =
                     DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement()) {
            String sql = "CREATE TABLE IF NOT EXISTS `ArmazemDSS`.`PaletesRececao` (\n" +
                    "  `PosFila` INT NOT NULL,\n" +
                    "  `CodPalete` VARCHAR(10) NULL,\n" +
                    "  PRIMARY KEY (`PosFila`),\n" +
                    "  INDEX `fk_PaletesRececao_Palete1_idx` (`CodPalete` ASC) VISIBLE,\n" +
                    "  CONSTRAINT `fk_PaletesRececao_Palete1`\n" +
                    "    FOREIGN KEY (`CodPalete`)\n" +
                    "    REFERENCES `ArmazemDSS`.`Palete` (`CodPalete`)\n" +
                    "    ON DELETE NO ACTION\n" +
                    "    ON UPDATE NO ACTION)\n" +
                    "ENGINE = InnoDB;";
            stm.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }

        try (Connection conn =
                     DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery("SELECT * FROM PaletesRececao")) {
            while (rs.next()) {   // Utilizamos o get para construir as turmas
                this.listaCodPalete.add(rs.getString("CodPalete"));
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
    public static RececaoDAO getInstance() {
        if (RececaoDAO.singleton == null) {
            RececaoDAO.singleton = new RececaoDAO();
        }
        return RececaoDAO.singleton;
    }

    /**
     * Método que devolve o número de paletes na zona de receção.
     *
     * @return Número de paletes na zona de receção.
     */
    @Override
    public int size() {
        return this.listaCodPalete.size();
    }

    /**
     * Método que verifica se existem paletes na zona de receção.
     *
     * @return true caso existam paletes na zona de entregas, false caso receção.
     */
    @Override
    public boolean isEmpty() {
        return this.listaCodPalete.isEmpty();
    }

    /**
     * Método que determina se uma palete existe na zona de receção.
     *
     * @param o Palete cuja existência se pretende verificar.
     * @return true caso a palete exista, false caso contrário.
     */
    @Override
    public boolean contains(Object o) {
        return this.listaCodPalete.contains(((Palete) o).getCodPalete());
    }

    /**
     * Método iterator.
     *
     * @return Iterador.
     */
    @Override
    public Iterator<Palete> iterator() {
        List<Palete> lista = new ArrayList<>();
        for (String s : this.listaCodPalete) {
            lista.add(this.paletes.get(s));
        }
        return lista.iterator();
    }

    /**
     * Método que devolve um array com as paletes.
     *
     * @return Array com as paletes.
     */
    @Override
    public Object[] toArray() {
        List<Palete> lista = new ArrayList<>();
        for (String s : this.listaCodPalete) {
            lista.add(this.paletes.get(s));
        }
        return lista.toArray();
    }

    /**
     * Método toArray (Método não implementado).
     *
     * @param a
     * @param <T>
     * @return
     */
    @Override
    public <T> T[] toArray(T[] a) {
        throw new NullPointerException("public <T> T[] toArray(T[] a) not implemented!");
    }

    /**
     * Método que adiciona uma palete à zona de receção.
     *
     * @param palete Palete a adicionar.
     * @return true.
     */
    @Override
    public boolean add(Palete palete) {
        boolean r = this.listaCodPalete.add(palete.getCodPalete());
        int pos = this.listaCodPalete.indexOf(palete.getCodPalete());
        try (Connection conn =
                     DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement()) {
            stm.executeUpdate(
                    "INSERT INTO PaletesRececao VALUES ('" + pos + "', '" + palete.getCodPalete() + "')");
        } catch (SQLException e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return r;
    }

    /**
     * Método que remove uma palete da zona de receção.
     *
     * @param o Palete a remover.
     * @return true caso a palete exista, false caso contrário.
     */
    @Override
    public boolean remove(Object o) {
        Palete p = (Palete) o;
        try (Connection conn =
                     DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement()) {
            stm.executeUpdate("DELETE FROM PaletesRececao WHERE PosFila='" + this.indexOf(p.getCodPalete()) + "'");
        } catch (Exception e) {
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return this.listaCodPalete.remove(p.getCodPalete());
    }

    /**
     * Método que verifica se um conjunto de paletes existe na zona de receção.
     *
     * @param c Conjunto de paletes cuja existência se pretende verificar.
     * @return true caso as paletes existam, false caso contrário.
     */
    @Override
    public boolean containsAll(Collection<?> c) {
        List<String> temp = ((Collection<Palete>) c).stream().map(p -> p.getCodPalete()).collect(Collectors.toList());
        return this.listaCodPalete.containsAll(temp);
    }

    /**
     * Método que adiciona um conjunto de paletes à zona de receção.
     *
     * @param c Conjunto de paletes a adicionar.
     * @return true caso a lista tenha sofrido alterações, false caso contrário.
     */
    @Override
    public boolean addAll(Collection<? extends Palete> c) {
        Collection<Palete> temp = (Collection<Palete>) c;
        for (Palete p : c) {
            this.add(p);
        }
        List<String> tempList = temp.stream().map(p -> p.getCodPalete()).collect(Collectors.toList());
        return this.listaCodPalete.addAll(tempList);
    }

    /**
     * Método addAll (Método não implementado).
     *
     * @param index
     * @param c
     * @return
     */
    @Override
    public boolean addAll(int index, Collection<? extends Palete> c) {
        throw new NullPointerException("public boolean addAll(int index, Collection<? extends Palete> c) not implemented!");
    }

    /**
     * Método que remove um conjunto de paletes da zona de receção.
     *
     * @param c Conjunto de paletes a remover.
     * @return true caso a lista tenha sofrido alterações, false caso contrário.
     */
    @Override
    public boolean removeAll(Collection<?> c) {
        Collection<Palete> temp = (Collection<Palete>) c;
        List<String> tempList = new ArrayList<>();
        for (Palete p : temp) {
            this.remove(p);
            tempList.add(p.getCodPalete());
        }
        return this.listaCodPalete.removeAll(tempList);
    }

    /**
     * Método retainAll (Método não implementado).
     *
     * @param c
     * @return
     */
    @Override
    public boolean retainAll(Collection<?> c) {
        throw new NullPointerException("public boolean retainAll(Collection<?> c) not implemented!");
    }

    /**
     * Método que remove todas as paletes da zona de receção.
     */
    @Override
    public void clear() {
        try (Connection conn =
                     DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement()) {
            stm.executeUpdate("TRUNCATE PaletesRececao");
        } catch (SQLException e) {
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        this.listaCodPalete.clear();
    }

    /**
     * Método que devolve uma palete dado o seu índice.
     *
     * @param index Indice da palete.
     * @return Palete caso exista, null caso contrário.
     */
    @Override
    public Palete get(int index) {
        Palete p = null;
        if (index < this.listaCodPalete.size()) {
            try (Connection conn =
                         DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
                 Statement stm = conn.createStatement();
                 ResultSet rs = stm.executeQuery("SELECT * FROM PaletesRececao WHERE PosFila='" + index + "'")) {
                if (rs.next()) {  // A chave existe na tabela
                    String cod = rs.getString("CodPalete");
                    p = this.paletes.get(cod);
                }
            } catch (SQLException e) {
                // Database error!
                e.printStackTrace();
                throw new NullPointerException(e.getMessage());
            }
        }
        return p;
    }

    /**
     * Método set (Método não implementado).
     *
     * @param index
     * @param element
     * @return
     */
    @Override
    public Palete set(int index, Palete element) {
        throw new NullPointerException("public Palete set(int index, Palete element) not implemented!");
    }

    /**
     * Método add (Método não implementado).
     *
     * @param index
     * @param element
     */
    @Override
    public void add(int index, Palete element) {
        throw new NullPointerException("public void add(int index, Palete element) not implemented!");
    }

    /**
     * Remove uma palete.
     *
     * @param index ID da palete.
     * @return Palete que foi removida.
     */
    @Override
    public Palete remove(int index) {
        Palete p = null;
        if (index < this.listaCodPalete.size()) {
            p = this.get(index);
            try (Connection conn =
                         DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
                 Statement stm = conn.createStatement()) {
                stm.executeUpdate("DELETE FROM PaletesRececao WHERE PosFila='" + index + "'");
            } catch (Exception e) {
                e.printStackTrace();
                throw new NullPointerException(e.getMessage());
            }
            this.listaCodPalete.remove(index);
        }
        return p;
    }

    /**
     * Método que devolve o índice em que um elemento ocorre.
     *
     * @param o Elemento que se pretende determinar o índice em que ocorre.
     * @return Indice em que o elemento ocorre.
     */
    public int indexOf(Object o) {
        return this.listaCodPalete.indexOf(((Palete) o).getCodPalete());
    }

    /**
     * Método lastIndexOf (Método não implementado).
     *
     * @param o
     * @return
     */
    @Override
    public int lastIndexOf(Object o) {
        throw new NullPointerException("public int lastIndexOf(Object o) not implemented!");
    }

    /**
     * Método listIterator.
     *
     * @return Iterador.
     */
    @Override
    public ListIterator<Palete> listIterator() {
        List<Palete> res = new ArrayList<>();
        for (String s : this.listaCodPalete) {
            res.add(this.paletes.get(s));
        }
        return res.listIterator();
    }

    /**
     * Método listIterator (Método não implementado).
     *
     * @param index
     * @return
     */
    @Override
    public ListIterator<Palete> listIterator(int index) {
        throw new NullPointerException("public ListIterator<Palete> listIterator(int index) not implemented!");
    }

    /**
     * Método subList (Método não implementado).
     *
     * @param fromIndex
     * @param toIndex
     * @return
     */
    @Override
    public List<Palete> subList(int fromIndex, int toIndex) {
        throw new NullPointerException("public List<Palete> subList(int fromIndex, int toIndex) not implemented!");
    }
}

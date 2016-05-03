package ua.netcracker.hr_system.model.dao.daoInterface;

import java.io.Serializable;
import java.util.Collection;

/**
 * Basic interface to implement DAO.
 * PK = primary key (may be composite)
 * T = type of entity
 *
 * @author Bersik (Serhii Kisilchuk)
 * @version 1.0
 */
public interface DAO<PK extends Serializable, T> {
    /**
     * Get all entities
     *
     * @return all entities
     */
    Collection<T> findAll();

    /**
     * Find entity by primary key
     *
     * @param id
     * @return entity
     */
    T find(int id);

    /**
     * Insert (save) entity
     *
     * @param entity entity
     * @return if insert was successful - true; else - false
     */
    boolean insert(T entity);

    /**
     * Update entity
     *
     * @param entity entity
     * @return if update was successful - true; else - false
     */
    boolean update(T entity);

    /**
     * Remove entity
     *
     * @param elem entity
     * @return if remove was successful - true; else - false
     */
    boolean remove(T elem);
}

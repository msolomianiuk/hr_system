package ua.netcracker.model.dao;

import java.util.Collection;

/**
 * Basic interface to implement DAO.
 * PK = primary key (may be composite)
 * T = type of entity
 *
 * @author Bersik (Serhii Kisilchuk)
 * @version 1.0
 */
public interface DAO<T> {
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

}

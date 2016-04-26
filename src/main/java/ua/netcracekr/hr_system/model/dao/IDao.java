package ua.netcracekr.hr_system.model.dao;

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
public interface IDao<PK extends Serializable, T> {
    /**
     * Get all entities
     *
     * @return all entities
     */
    Collection<T> findAll();

    /**
     * Find entity by primary key
     *
     * @param key primary key
     * @return entity
     */
    T find(PK key);

    /**
     * Insert (save) entity
     *
     * @param elem entity
     * @return if insert was successful - true; else - false
     */
    boolean insert(T elem);

    /**
     * Update entity
     *
     * @param elem entity
     * @return if update was successful - true; else - false
     */
    boolean update(T elem);

    /**
     * Remove entity
     *
     * @param elem entity
     * @return if remove was successful - true; else - false
     */
    boolean remove(T elem);
}

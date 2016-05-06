package ua.netcracker.model.service;

import java.util.Collection;

/**
 * Created by Legion on 26.04.2016.
 */

public interface EntityService<E> {

    public E findById(int id);
    public Collection <E> findAllSetting();
    public void saveOrUpdate (E e);
    public void delete (long id);

}

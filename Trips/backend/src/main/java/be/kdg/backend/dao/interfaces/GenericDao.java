package be.kdg.backend.dao.interfaces;

import java.io.Serializable;
import java.util.List;

/**
 * Created with IntelliJ IDEA 12.
 * User: Jonas Liekens
 * Date: 22/02/13
 * Time: 15:08
 * Copyright @ Soulware.be
 */
public interface GenericDao<E, ID extends Serializable> {
    public void add(E entity);
    public void remove(E entity);
    public void update(E entity);
    public E find(ID id);
    public List<E> list();
}

package be.kdg.backend.services.interfaces;

import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.Serializable;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Bart
 * Date: 26/02/13
 * Time: 14:23
 * To change this template use File | Settings | File Templates.
 */
public interface GenericService<E, ID extends Serializable> {

    @Transactional
    public void add(E entity);

    @Transactional
    public void remove(E entity);

    @Transactional
    public void update(E entity);

    @Transactional
    public E get(ID id);
}

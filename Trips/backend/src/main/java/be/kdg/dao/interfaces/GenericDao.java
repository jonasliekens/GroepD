package be.kdg.dao.interfaces;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * Author: Nick De Waele
 * Date: 8/02/13
 */
public interface GenericDao<E, K>  {

    	void add(E entity);

    	void update(E entity);

    	void remove(E entity);

    	E find(K key);

    	List<E> list();

}

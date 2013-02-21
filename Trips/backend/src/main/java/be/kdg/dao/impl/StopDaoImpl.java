package be.kdg.dao.impl;

import be.kdg.dao.interfaces.StopDao;
import be.kdg.entities.Stop;
import org.springframework.stereotype.Repository;

/**
 * Created with IntelliJ IDEA.
 * User: Bart
 * Date: 21/02/13
 * Time: 14:28
 * To change this template use File | Settings | File Templates.
 */
@Repository("stopDao")
public class StopDaoImpl extends HibernateDao<Stop,Integer> implements StopDao {
}

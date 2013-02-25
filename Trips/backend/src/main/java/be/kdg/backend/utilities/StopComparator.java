package be.kdg.backend.utilities;

import be.kdg.backend.entities.Stop;

import java.util.Comparator;

/**
 * Created with IntelliJ IDEA.
 * User: Maarten
 * Date: 22/02/13
 * Time: 10:53
 */
public class StopComparator implements Comparator<Stop> {
    @Override
    public int compare(Stop o1, Stop o2) {
        return o1.getOrder().compareTo(o2.getOrder());
    }
}

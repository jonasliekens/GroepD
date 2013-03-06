package be.kdg.backend.utilities;

import be.kdg.backend.entities.Message;

import java.util.Comparator;

/**
 * User: Bart Verhavert
 * Date: 6/03/13 08:51
 */
public class MessageComparator implements Comparator<Message> {
    @Override
    public int compare(Message o1, Message o2) {
        return o1.getDate().compareTo(o2.getDate());
    }
}

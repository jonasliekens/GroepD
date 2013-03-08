package be.kdg.backend.services.impl;

import be.kdg.backend.dao.interfaces.ChatDao;
import be.kdg.backend.dao.interfaces.UserDao;
import be.kdg.backend.entities.Chat;
import be.kdg.backend.entities.Message;
import be.kdg.backend.entities.User;
import be.kdg.backend.services.interfaces.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * User: Bart Verhavert
 * Date: 5/03/13 18:01
 */
@Service("chatService")
public class ChatServiceImpl implements ChatService {
    @Qualifier("chatDaoImpl")
    @Autowired(required = true)
    private ChatDao chatDao;

    @Qualifier("userDaoImpl")
    @Autowired(required = true)
    private UserDao userDao;
    @Override
    public void add(Chat entity) {
        chatDao.add(entity);
    }

    @Override
    public void remove(Chat entity) {
        chatDao.remove(entity);
    }

    @Override
    public void update(Chat entity) {
        chatDao.update(entity);
    }

    @Override
    public Chat get(Integer integer) {
        return chatDao.findById(integer);
    }

    /*
    *   This method gets the chat that belongs to the given users. If no chat exists for these users, a new one will be created.
    */
    @Override
    public Chat get(List<User> users) {
        Chat chat;
        Integer i = 0;
        Set<Chat> chatsSet = users.get(i++).getChats();
        List<Chat> chats = new ArrayList<Chat>();

        // Add all the chats from the first user to the local list
        if(chatsSet.size() > 0) {
            chats.addAll(chatsSet);

            // Now loop through all the other users and compare their chats with the chats from the first user
            // Remove all the chats from the list who are not in the list of the user in the loop
            for(; i < users.size(); i++) {
                // retainAll uses the equals and hashCode method of Chat
                chats.retainAll(users.get(i).getChats());
            }
        }

        // If there are already one or more chats for these users, get the correct one
        if(chats.size() > 0) {
            chat = chats.get(0); //TODO: This only works for conversations between 2 people, for larger chat groups we have to add some more logic in this if statement
        }

        // Otherwise create a new one
        //TODO: For group conversations we have to change the else into an if because the previous if statement can still remove all the chats from the list
        else {
            chat = new Chat();
            chatDao.add(chat);

            for(User user : users) {
                chat.addParticipant(user);
            }
            chatDao.update(chat);
        }

        return chat;
    }

    @Override
    public void sendMessage(Integer chatId, Message message) {
        Chat chat = chatDao.findById(chatId);

        chat.addMessage(message);

        chatDao.update(chat);
    }

    @Override
    public List<Chat> findAllChatsByUserId(Integer id) {
        return chatDao.findAllChatsByUserId(id);
    }

    @Override
    public List<Message> findAllMessagesByChatId(Integer id) {
        return chatDao.findAllMessagesByChatId(id);
    }

    @Override
    public List<Chat> getAllChats() {
        return chatDao.findAll();
    }
}

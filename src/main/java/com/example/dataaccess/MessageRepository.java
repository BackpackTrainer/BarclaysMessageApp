package com.example.dataaccess;

import com.example.entity.Message;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends CrudRepository<Message, Long> {

    public Iterable<Message> findBySenderId(Long id);

    public Iterable<Message> findMessagesBySenderEmail(String senderEmail);

}

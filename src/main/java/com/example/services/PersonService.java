package com.example.services;

import com.example.entity.Person;
import org.springframework.stereotype.Service;

@Service
public interface PersonService {
    public Person addPerson(Person p);

    Iterable<Person> findAllPersons();
}

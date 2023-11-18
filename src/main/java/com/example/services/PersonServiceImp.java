package com.example.services;

import com.example.dataaccess.PersonRepository;
import com.example.entity.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public class PersonServiceImp implements  PersonService{

    private PersonRepository personRepository;
    @Override
    public Person addPerson(Person p) {
        return personRepository.save(p);
    }

    @Autowired
    public void setPersonRepository(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }
}

package com.example.services;

import com.example.dataaccess.PersonRepository;
import com.example.entity.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Primary
public class PersonServiceImp implements  PersonService{

    private PersonRepository personRepository;
    @Override
    public Person addPerson(Person person) {
        String name = person.getName();
        Optional<Person> prospectivePerson = personRepository.findByName(name);
        if(prospectivePerson.isEmpty())  {
            return personRepository.save(person);
        }
        return prospectivePerson.get();
    }

    @Override
    public Iterable<Person> findAllPersons() {
        return personRepository.findAll();
    }

    @Autowired
    public void setPersonRepository(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }
}

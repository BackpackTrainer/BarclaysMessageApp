package com.example.service.basicPersonServiceUnitTests;

import com.example.dataaccess.PersonRepository;
import com.example.entity.Person;
import com.example.services.PersonServiceImp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;

public class PersonServiceUnitTests {

    private PersonServiceImp uut;
    private PersonRepository personRepository;

    @BeforeEach
    public void setUp() {
        uut = new PersonServiceImp();
        personRepository = mock(PersonRepository.class);
        uut.setPersonRepository(personRepository);
    }

    @Test
    public  void addPerson()  {
        String name = "Bryan";
        String email = "bryan@gmail.com";
        Person person = new Person(name, email);

        uut.addPerson(person);

        Mockito.verify(personRepository, times(1)).save(person);
    }
}

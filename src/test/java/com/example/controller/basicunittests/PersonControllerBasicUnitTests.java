package com.example.controller.basicunittests;

import com.example.controllers.MessageController;
import com.example.controllers.PersonController;
import com.example.entity.Person;
import com.example.services.MessageService;
import com.example.services.PersonService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.URISyntaxException;

import static org.mockito.Mockito.*;

public class PersonControllerBasicUnitTests {

    PersonController uut;
    PersonService personService;

    @BeforeEach
    public void setUp() {
        uut = new PersonController();
        personService = mock(PersonService.class);
        uut.setPersonService(personService);
    }

    @Test
    public void testAddingAPerson() throws URISyntaxException {
        Person person = new Person();

        uut.addPerson(person);

        verify(personService, times(1)).addPerson(person);
    }
}

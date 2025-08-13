package br.com.technsou.service;

import br.com.technsou.dto.v1.PersonDTO;
import br.com.technsou.exception.RequiredObjectIsNullException;
import br.com.technsou.exception.ResourceNotFoundException;
import br.com.technsou.model.Person;
import br.com.technsou.repository.PersonRepository;
import br.com.technsou.unitetests.mapper.mocks.MockPerson;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class PersonServiceTest {

    MockPerson input;

    @InjectMocks
    PersonService service;

    @Mock
    PersonRepository repository;

    @BeforeEach
    void setUp() {
        input = new MockPerson();
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findAll() {
        List<Person> list = input.mockEntityList();
        when(repository.findAll()).thenReturn(list);
        List<PersonDTO> people = service.findAll();

        assertNotNull(people);
        assertEquals(14, people.size());

        assertsTestes(people.get(1), "1");
        assertsTestes(people.get(3), "3");
        assertsTestes(people.get(7), "7");
    }

    @Test
    void findById() {
        Person person = input.mockEntity(1);
        person.setId(1L);
        when(repository.findById(1L)).thenReturn(Optional.of(person));
        var result = service.findById(1l);
        assertsTestes(result, "1");
    }

    @Test
    void create() {
        Person person = input.mockEntity(1);
        Person persisted = person;
        persisted.setId(1L);

        PersonDTO dto = input.mockDTO(1);

        when(repository.save(person)).thenReturn(persisted);

        var result = service.create(dto);

        assertsTestes(result, "1");
    }

    @Test
    void testCreateWithNullPerson() {
        Exception exception = assertThrows(RequiredObjectIsNullException.class, () -> {
            service.create(null);
        });
        String expectedMessage = "It is not allowed to persist a null object!";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void update() {
        Person person = input.mockEntity(1);
        Person persisted = person;
        persisted.setId(1L);

        PersonDTO dto = input.mockDTO(1);

        when(repository.findById(1L)).thenReturn(Optional.of(person));
        when(repository.save(person)).thenReturn(persisted);

        var result = service.update(dto);

        assertsTestes(result, "1");
    }

    @Test
    void testUpdateWithNullPerson() {
        Exception exception = assertThrows(RequiredObjectIsNullException.class, () -> {
            service.update(null);
        });
        String expectedMessage = "It is not allowed to persist a null object!";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void delete() {
        Person person = input.mockEntity(1);
        person.setId(1L);
        when(repository.findById(1L)).thenReturn(Optional.of(person));

        service.delete(1l);
        verify(repository, times(1)).findById(anyLong());
        verify(repository, times(1)).delete(any(Person.class));
        verifyNoMoreInteractions(repository);
    }

    @Test
    void testDeleteWithNullPerson() {
        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
            service.delete(null);
        });
        String expectedMessage = "No records found for this ID!";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }


    private void assertsTestes(PersonDTO result, String id) {
        assertNotNull(result);
        assertNotNull(result.getId());
        assertEquals("First Name Test" + id, result.getFirstName());
        assertEquals("Last Name Test" + id, result.getLastName());
        assertEquals("Address Test" + id, result.getAddress());
        assertEquals("Female", result.getGender());

        assertNotNull(result.getLinks().stream()
                .anyMatch(l -> l.getRel().value().equals("self")
                        && l.getHref().endsWith("/api/person/v1/" + id)
                        && l.getType().equals("GET")
                )
        );

        assertNotNull(result.getLinks().stream()
                .anyMatch(l -> l.getRel().value().equals("findAll")
                        && l.getHref().endsWith("/api/person/v1")
                        && l.getType().equals("GET")
                )
        );

        assertNotNull(result.getLinks().stream()
                .anyMatch(l -> l.getRel().value().equals("created")
                        && l.getHref().endsWith("/api/person/v1/")
                        && l.getType().equals("POST")
                )
        );

        assertNotNull(result.getLinks().stream()
                .anyMatch(l -> l.getRel().value().equals("update")
                        && l.getHref().endsWith("/api/person/v1/")
                        && l.getType().equals("PUT")
                )
        );

        assertNotNull(result.getLinks().stream()
                .anyMatch(l -> l.getRel().value().equals("delete")
                        && l.getHref().endsWith("/api/person/v1/" + id)
                        && l.getType().equals("DELETE")
                )
        );
    }
}
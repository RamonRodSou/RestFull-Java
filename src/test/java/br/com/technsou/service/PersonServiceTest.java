package br.com.technsou.service;

import br.com.technsou.dto.v1.PersonDTO;
import br.com.technsou.model.Person;
import br.com.technsou.repository.PersonRepository;
import br.com.technsou.unitetests.mapper.mocks.MockPerson;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

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
    }

    @Test
    void findById() {
        Person person = input.mockEntity(1);
        person.setId(1L);
        when(repository.findById(1L)).thenReturn(Optional.of(person));
        var result = service.findById(1l);
        assertNotNull(result);
        assertNotNull(result.getId());
        assertEquals("First Name Test1", result.getFirstName());
        assertEquals("Last Name Test1", result.getLastName());
        assertEquals("Address Test1", result.getAddress());
        assertEquals("Female", result.getGender());

        assertNotNull(result.getLinks().stream()
                .anyMatch(l -> l.getRel().value().equals("self")
                        && l.getHref().endsWith("/api/person/v1/1")
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
                        && l.getHref().endsWith("/api/person/v1/1")
                        && l.getType().equals("DELETE")
                )
        );
    }

    @Test
    void create() {
        Person person = input.mockEntity(1);
        Person persisted = person;
        persisted.setId(1L);

        PersonDTO dto = input.mockDTO(1);

        when(repository.save(person)).thenReturn(persisted);

        var result = service.create(dto);

        assertNotNull(result);
        assertNotNull(result.getId());
        assertEquals("First Name Test1", result.getFirstName());
        assertEquals("Last Name Test1", result.getLastName());
        assertEquals("Address Test1", result.getAddress());
        assertEquals("Female", result.getGender());

        assertNotNull(result.getLinks().stream()
                .anyMatch(l -> l.getRel().value().equals("self")
                        && l.getHref().endsWith("/api/person/v1/1")
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
                        && l.getHref().endsWith("/api/person/v1/1")
                        && l.getType().equals("DELETE")
                )
        );
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

        assertNotNull(result);
        assertNotNull(result.getId());
        assertEquals("First Name Test1", result.getFirstName());
        assertEquals("Last Name Test1", result.getLastName());
        assertEquals("Address Test1", result.getAddress());
        assertEquals("Female", result.getGender());

        assertNotNull(result.getLinks().stream()
                .anyMatch(l -> l.getRel().value().equals("self")
                        && l.getHref().endsWith("/api/person/v1/1")
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
                        && l.getHref().endsWith("/api/person/v1/1")
                        && l.getType().equals("DELETE")
                )
        );
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
}
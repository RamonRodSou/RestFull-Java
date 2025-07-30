package br.com.technsou.service;

import br.com.technsou.exception.ResourceNotFoundException;
import br.com.technsou.model.Person;
import br.com.technsou.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Logger;

@Service
public class PersonService {

    private final AtomicLong counter = new AtomicLong();

    @Autowired
    private PersonRepository repository;

    private Logger logger = Logger.getLogger(PersonService.class.getName());

    public List<Person> findAll(){
        logger.info("Finding all Persons");
        return repository.findAll();
    }

    public Person findById(Long id){
        logger.info("Finding one Person");
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
    }

    public Person create(Person person){
        logger.info("Creating one Person!");
        return repository.save(person);
    }

    public Person update(Person person){
        logger.info("Updating one Person!");
        Person entity = repository.findById(person.getId()).orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
        return repository.save(entityUpdate(person, entity));
    }

    public void delete(Long id){
        logger.info("Deleting one Person!");
        Person entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
        repository.delete(entity);
    }

    private Person entityUpdate(Person person, Person entity) {
        entity.setFistName(person.getFistName());
        entity.setLastName(person.getLastName());
        entity.setAddress(person.getAddress());
        entity.setGender(person.getGender());
        return entity;
    }
}

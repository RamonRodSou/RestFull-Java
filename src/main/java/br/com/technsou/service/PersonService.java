package br.com.technsou.service;

import br.com.technsou.dto.v1.PersonDTO;
import br.com.technsou.exception.ResourceNotFoundException;
import static br.com.technsou.mapper.ObjectMapper.parseListObjects;
import static br.com.technsou.mapper.ObjectMapper.parseObject;

import br.com.technsou.mapper.custom.PersonMapper;
import br.com.technsou.model.Person;
import br.com.technsou.repository.PersonRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import java.util.concurrent.atomic.AtomicLong;

@Service
public class PersonService {

    private final AtomicLong counter = new AtomicLong();

    @Autowired
    private PersonRepository repository;

    @Autowired
    private PersonMapper converter;

    private Logger logger = LoggerFactory.getLogger(PersonService.class.getName());

    public List<PersonDTO> findAll(){
        logger.info("Finding all Persons");
        return parseListObjects(repository.findAll(), PersonDTO.class);
    }
//
//    public List<PersonDTOV2> findAllV2(){
//        logger.info("Finding all Persons");
//        return parseListObjects(repository.findAll(), PersonDTOV2.class);
//    }

    public PersonDTO findById(Long id){
        logger.info("Finding one Person");
        var entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
        return parseObject(entity, PersonDTO.class);
    }

    public PersonDTO create(PersonDTO person){
        logger.info("Creating one Person!");
        var entity = parseObject(person, Person.class);
        return parseObject(repository.save(entity), PersonDTO.class);
    }

//    public PersonDTOV2 createV2(PersonDTOV2 person){
//        logger.info("Creating one Person V2!");
//        var entity = converter.convertDTOToEntity(person);
//        return converter.convertEntityToDTO(repository.save(entity));
//    }

    public PersonDTO update(PersonDTO person){
        logger.info("Updating one Person!");
        Person entity = repository.findById(person.getId()).orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
        var entityUpdated = entityUpdate(person, entity);
        return parseObject(repository.save(entityUpdated), PersonDTO.class);
    }

    public void delete(Long id){
        logger.info("Deleting one Person!");
        Person entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
        repository.delete(entity);
    }

    private Person entityUpdate(PersonDTO person, Person entity) {
        entity.setFirstName(person.getFirstName());
        entity.setLastName(person.getLastName());
        entity.setAddress(person.getAddress());
        entity.setGender(person.getGender());
        entity.setBirthDate(person.getBirthDate());
        entity.setPhoneNumber(person.getPhoneNumber());
        return entity;
    }
}

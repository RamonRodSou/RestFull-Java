package br.com.technsou.mapper.custom;

import br.com.technsou.dto.v2.PersonDTOV2;
import br.com.technsou.model.Person;
import org.springframework.stereotype.Service;

@Service
public class PersonMapper {
    public PersonDTOV2 convertEntityToDTO(Person person) {
        PersonDTOV2 dto = new PersonDTOV2();
        dto.setId(person.getId());
        dto.setFirstName(person.getFirstName());
        dto.setLastName(person.getLastName());
        dto.setBirthDate(person.getBirthDate());
        dto.setAddress(person.getAddress());
        dto.setGender(person.getGender());
        return dto;
    }

    public Person convertDTOToEntity(PersonDTOV2 person) {
        Person entity = new Person();
        entity.setFirstName(person.getFirstName());
        entity.setLastName(person.getLastName());
        entity.setBirthDate(person.getBirthDate());
        entity.setAddress(person.getAddress());
        entity.setGender(person.getGender());
        return entity;
    }
}

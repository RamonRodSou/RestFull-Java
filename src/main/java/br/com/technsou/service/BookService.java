package br.com.technsou.service;

import br.com.technsou.controllers.BookController;
import br.com.technsou.dto.v1.BookDTO;
import br.com.technsou.exception.RequiredObjectIsNullException;
import br.com.technsou.exception.ResourceNotFoundException;
import br.com.technsou.model.Book;
import br.com.technsou.repository.BookRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static br.com.technsou.mapper.ObjectMapper.parseListObjects;
import static br.com.technsou.mapper.ObjectMapper.parseObject;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class BookService {

    @Autowired
    private BookRepository repository;

    private Logger logger = LoggerFactory.getLogger(BookService.class.getName());

    public List<BookDTO> findAll(){
        logger.info("Finding all Books");
        var books = parseListObjects(repository.findAll(), BookDTO.class);
        books.forEach(this::addHateoasLinks);
        return books;
    }

    public BookDTO findById(Long id){
        logger.info("Finding one Book");
        var entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
        var dto = parseObject(entity, BookDTO.class);
        addHateoasLinks(dto);
        return dto;
    }

    public BookDTO create(BookDTO book){

        if(book == null) throw new RequiredObjectIsNullException();

        logger.info("Creating one Book!");
        var entity = parseObject(book, Book.class);

        var dto = parseObject(repository.save(entity), BookDTO.class);
        addHateoasLinks(dto);
        return dto;
    }

    public BookDTO update(BookDTO book){

        if(book == null) throw new RequiredObjectIsNullException();

        logger.info("Updating one Book!");
        Book entity = repository.findById(book.getId()).orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
        var entityUpdated = entityUpdate(book, entity);

        var dto = parseObject(repository.save(entityUpdated), BookDTO.class);
        addHateoasLinks(dto);
        return dto;
    }

    public void delete(Long id){

        logger.info("Deleting one Book!");
        Book entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
        repository.delete(entity);
    }

    private static Book entityUpdate(BookDTO book, Book entity) {

        entity.setAuthor(book.getAuthor());
        entity.setTitle(book.getTitle());
        entity.setPrice(book.getPrice());
        entity.setLaunchDate(book.getLaunchDate());
        return entity;
    }

    private void addHateoasLinks(BookDTO dto) {

        dto.add(linkTo(methodOn(BookController.class).findbyId(dto.getId())).withSelfRel().withType("GET"));
        dto.add(linkTo(methodOn(BookController.class).findAll()).withRel("findlAll").withType("GET"));
        dto.add(linkTo(methodOn(BookController.class).create(dto)).withRel("create").withType("POST"));
        dto.add(linkTo(methodOn(BookController.class).update(dto)).withRel("update").withType("PUT"));
        dto.add(linkTo(methodOn(BookController.class).delete(dto.getId())).withRel("delete").withType("DELETE"));
    }
}

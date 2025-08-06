package br.com.technsou.dto.v1;


//import com.fasterxml.jackson.annotation.JsonIgnore;
//import com.fasterxml.jackson.annotation.JsonProperty;
//import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import br.com.technsou.serializer.GenderSerializer;
import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;


import java.io.Serializable;
import java.util.Date;

//@JsonPropertyOrder({"id", "first_name", "last_name", "address", "gender"})
@JsonFilter("PersonFilter")
public class PersonDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long id;

    //@JsonProperty("first_name")
    private String firstName;

    //@JsonProperty("last_name")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String lastName;

    private String address;

    //@JsonIgnore
    @JsonSerialize(using = GenderSerializer.class)
    private String gender;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date birthDate;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String phoneNumber;

    private String sensitiveData;

    public PersonDTO() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String fistName) {
        this.firstName = fistName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getBirthDate() { return birthDate;}

    public void setBirthDate(Date birthDate) { this.birthDate = birthDate;}

    public String getPhoneNumber() { return phoneNumber; }

    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public String getSensitiveData() {
        return sensitiveData;
    }

    public void setSensitiveData(String sensitiveData) {
        this.sensitiveData = sensitiveData;
    }
}

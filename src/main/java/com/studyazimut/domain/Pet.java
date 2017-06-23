package com.studyazimut.domain;

import org.springframework.hateoas.Identifiable;
import javax.persistence.*;

@Entity
@Table(name = "pets")
public class Pet implements Identifiable<Long> {
  
  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  @Column(name = "pet_id")
  private long id;
  
  @Column(name = "pet_name", nullable = false, length = 100)
  private String petName;
  
  @Column(name = "pet_type")
  private Type petType;
  
  public enum Type {
    
    DOG,
    
    CAT
    
  }
  
  @Version
  private Integer version;
  
  public Pet() {
  }
  
  public Pet(String petName, Type petType) {
    this.petName = petName;
    this.petType = petType;
  }
  
  @Override
  public Long getId() {
    return id;
  }
  
  public void setid(long id) {
    this.id = id;
  }
  
  public String getPetName() {
    return petName;
  }
  
  public void setPetName(String petName) {
    this.petName = petName;
  }
  
  public Type getPetType() {
    return petType;
  }
  
  public void setPetType(Type petType) {
    this.petType = petType;
  }
  
  public Integer getVersion() {
    return version;
  }
  
  public void setVersion(Integer version) {
    this.version = version;
  }
  
  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    
    Pet pet = (Pet) o;
    
    if (id != pet.id) return false;
    if (!petName.equals(pet.petName)) return false;
    return petType == pet.petType;
  }
  
  @Override
  public int hashCode() {
    int result = (int) (id ^ (id >>> 32));
    result = 31 * result + petName.hashCode();
    result = 31 * result + petType.hashCode();
    return result;
  }
  
  @Override
  public String toString() {
    return "Pet{" +
            "id=" + id +
            ", petName='" + petName + '\'' +
            ", petType=" + petType +
            '}';
  }
}

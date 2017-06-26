package com.studyazimut.processor;

import com.studyazimut.domain.Pet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.support.RepositoryEntityLinks;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceProcessor;
import org.springframework.stereotype.Component;

@Component
public class PetResourceProcessor implements ResourceProcessor<Resource<Pet>> {
  
  @Autowired
  private RepositoryEntityLinks entityLinks;
  
  @Override
  public Resource<Pet> process(Resource<Pet> resource) {
    final Pet pet = resource.getContent();
    resource.add(entityLinks.linkToSingleResource(pet)
            .withRel("update"));
    return resource;
  }
}

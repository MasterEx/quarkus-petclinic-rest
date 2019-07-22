/*
 * Copyright 2019 Periklis Ntanasis
 * Copyright 2016-2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.masterex.petclinic.rest;

import com.github.masterex.petclinic.model.Pet;
import com.github.masterex.petclinic.service.ClinicService;
import java.net.URI;
import java.util.Collection;
import java.util.Set;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

/**
 * @author Vitaliy Fedoriv
 * @author Periklis Ntanasis <pntanasis@gmail.com>
 */
@Path("/api/pets")
public class PetRestController {

    private final ClinicService clinicService;

    private final Validator validator;

    @Inject
    public PetRestController(ClinicService clinicService, Validator validator) {
        this.clinicService = clinicService;
        this.validator = validator;
    }

    @GET
    @Path("/{petId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPet(@PathParam("petId") int petId) {
        Pet pet = this.clinicService.findPetById(petId);
        if (pet == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok().entity(pet).build();
    }

    @GET
    @Path("")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPets() {
        Collection<Pet> pets = this.clinicService.findAllPets();
        if (pets.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok().entity(pets).build();
    }

    @GET
    @Path("/pettypes")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPetTypes() {
        return Response.ok().entity(this.clinicService.findPetTypes()).build();
    }

    @POST
    @Path("")
    @Produces(MediaType.APPLICATION_JSON)
    public Response addPet(Pet pet) {
        Set<ConstraintViolation<Pet>> violations = validator.validate(pet);
        if (!violations.isEmpty() || (pet == null)) {
            BindingErrorsResponse errors = new BindingErrorsResponse<>(violations);
            return Response.status(Response.Status.BAD_REQUEST)
                    .header("errors", errors.toJSON())
                    .build();
        }
        this.clinicService.savePet(pet);
        URI uri = UriBuilder.fromPath("/api/pets/{id}").build(pet.getId());
        return Response.created(uri).entity(pet).build();
    }

    @PUT
    @Path("/{petId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response updatePet(@PathParam("petId") int petId, Pet pet) {
        Set<ConstraintViolation<Pet>> violations = validator.validate(pet);
        if (!violations.isEmpty() || (pet == null)) {
            BindingErrorsResponse errors = new BindingErrorsResponse<>(violations);
            return Response.status(Response.Status.BAD_REQUEST)
                    .header("errors", errors.toJSON())
                    .build();
        }
        Pet currentPet = this.clinicService.findPetById(petId);
        if (currentPet == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        currentPet.setBirthDate(pet.getBirthDate());
        currentPet.setName(pet.getName());
        currentPet.setType(pet.getType());
        currentPet.setOwner(pet.getOwner());
        this.clinicService.savePet(currentPet);
        return Response.status(Response.Status.NO_CONTENT).entity(currentPet).build();
    }

    @DELETE
    @Path("/{petId}")
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Response deletePet(@PathParam("petId") int petId) {
        Pet pet = this.clinicService.findPetById(petId);
        if (pet == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        this.clinicService.deletePet(pet);
        return Response.status(Response.Status.NO_CONTENT).build();
    }

}

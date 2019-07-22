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

import com.github.masterex.petclinic.model.PetType;
import com.github.masterex.petclinic.service.ClinicService;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.ConstraintViolation;
import javax.validation.Valid;
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
 *
 * @author Periklis Ntanasis <pntanasis@gmail.com>
 */
@Path("/api/pettypes")
public class PetTypeRestController {

    private final ClinicService clinicService;

    private final Validator validator;

    @Inject
    public PetTypeRestController(ClinicService clinicService, Validator validator) {
        this.clinicService = clinicService;
        this.validator = validator;
    }

    @GET
    @Path("")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllPetTypes() {
        Collection<PetType> petTypes = new ArrayList<>();
        petTypes.addAll(this.clinicService.findAllPetTypes());
        if (petTypes.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok().entity(petTypes).build();
    }

    @GET
    @Path("/{petTypeId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPetType(@PathParam("petTypeId") int petTypeId) {
        PetType petType = this.clinicService.findPetTypeById(petTypeId);
        if (petType == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok().entity(petType).build();
    }

    @POST
    @Path("")
    @Produces(MediaType.APPLICATION_JSON)
    public Response addPetType(@Valid PetType petType) {
        Set<ConstraintViolation<PetType>> violations = validator.validate(petType);
        if (!violations.isEmpty() || (petType == null)) {
            BindingErrorsResponse errors = new BindingErrorsResponse<>(violations);
            return Response.status(Response.Status.BAD_REQUEST)
                    .header("errors", errors.toJSON())
                    .build();
        }
        this.clinicService.savePetType(petType);
        URI uri = UriBuilder.fromPath("/api/pettypes/{id}").build(petType.getId());
        return Response.created(uri).entity(petType).build();
    }

    @PUT
    @Path("/{petTypeId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response updatePetType(@PathParam("petTypeId") int petTypeId, @Valid PetType petType) {
        Set<ConstraintViolation<PetType>> violations = validator.validate(petType);
        if (!violations.isEmpty() || (petType == null)) {
            BindingErrorsResponse errors = new BindingErrorsResponse<>(violations);
            return Response.status(Response.Status.BAD_REQUEST)
                    .header("errors", errors.toJSON())
                    .build();
        }
        PetType currentPetType = this.clinicService.findPetTypeById(petTypeId);
        if (currentPetType == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        currentPetType.setName(petType.getName());
        this.clinicService.savePetType(currentPetType);
        return Response.status(Response.Status.NO_CONTENT).entity(currentPetType).build();
    }

    @DELETE
    @Path("/{petTypeId}")
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Response deletePetType(@PathParam("petTypeId") int petTypeId) {
        PetType petType = this.clinicService.findPetTypeById(petTypeId);
        if (petType == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        this.clinicService.deletePetType(petType);
        return Response.status(Response.Status.NO_CONTENT).build();
    }

}

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

import com.github.masterex.petclinic.model.Specialty;
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
import javax.ws.rs.Consumes;
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
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

/**
 * @author Vitaliy Fedoriv
 * @author Periklis Ntanasis <pntanasis@gmail.com>
 */
@Path("/api/specialties")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SpecialtyRestController {

    private final ClinicService clinicService;

    private final Validator validator;

    @Inject
    public SpecialtyRestController(ClinicService clinicService, Validator validator) {
        this.clinicService = clinicService;
        this.validator = validator;
    }

    @GET
    @Path("")
    @APIResponse(content = @Content(schema = @Schema(type = SchemaType.ARRAY, implementation = Specialty.class)))
    @Tag(name = "Specialty Rest Controller", description = "specialty-rest-controller")
    public Response getAllSpecialties() {
        Collection<Specialty> specialties = new ArrayList<>();
        specialties.addAll(this.clinicService.findAllSpecialties());
        if (specialties.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok().entity(specialties).build();
    }

    @GET
    @Path("/{specialtyId}")
    @APIResponse(content = @Content(schema = @Schema(implementation = Specialty.class)))
    @Tag(name = "Specialty Rest Controller", description = "specialty-rest-controller")
    public Response getSpecialty(@PathParam("specialtyId") int specialtyId) {
        Specialty specialty = this.clinicService.findSpecialtyById(specialtyId);
        if (specialty == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok().entity(specialty).build();
    }

    @POST
    @Path("")
    @APIResponse(content = @Content(schema = @Schema(implementation = Specialty.class)))
    @Tag(name = "Specialty Rest Controller", description = "specialty-rest-controller")
    public Response addSpecialty(Specialty specialty) {
        Set<ConstraintViolation<Specialty>> violations = validator.validate(specialty);
        if (!violations.isEmpty() || (specialty == null)) {
            BindingErrorsResponse errors = new BindingErrorsResponse<>(violations);
            return Response.status(Response.Status.BAD_REQUEST)
                    .header("errors", errors.toJSON())
                    .build();
        }
        this.clinicService.saveSpecialty(specialty);
        URI uri = UriBuilder.fromPath("/api/specialtys/{id}").build(specialty.getId());
        return Response.created(uri).entity(specialty).build();
    }

    @PUT
    @Path("/{specialtyId}")
    @APIResponse(content = @Content(schema = @Schema(implementation = Specialty.class)))
    @Tag(name = "Specialty Rest Controller", description = "specialty-rest-controller")
    public Response updateSpecialty(@PathParam("specialtyId") int specialtyId, @Valid Specialty specialty) {
        Set<ConstraintViolation<Specialty>> violations = validator.validate(specialty);
        if (!violations.isEmpty() || (specialty == null)) {
            BindingErrorsResponse errors = new BindingErrorsResponse<>(violations);
            return Response.status(Response.Status.BAD_REQUEST)
                    .header("errors", errors.toJSON())
                    .build();
        }
        Specialty currentSpecialty = this.clinicService.findSpecialtyById(specialtyId);
        if (currentSpecialty == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        currentSpecialty.setName(specialty.getName());
        this.clinicService.saveSpecialty(currentSpecialty);
        return Response.status(Response.Status.NO_CONTENT).entity(currentSpecialty).build();
    }

    @DELETE
    @Path("/{specialtyId}")
    @Transactional
    @Tag(name = "Specialty Rest Controller", description = "specialty-rest-controller")
    public Response deleteSpecialty(@PathParam("specialtyId") int specialtyId) {
        Specialty specialty = this.clinicService.findSpecialtyById(specialtyId);
        if (specialty == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        this.clinicService.deleteSpecialty(specialty);
        return Response.status(Response.Status.NO_CONTENT).build();
    }

}

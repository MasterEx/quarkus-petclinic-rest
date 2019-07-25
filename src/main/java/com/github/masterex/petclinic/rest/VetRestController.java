/*
 * Copyright 2019 Periklis Ntanasis
 * Copyright 2016-2018 the original author or authors.
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
import com.github.masterex.petclinic.model.Vet;
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
@Path("/api/vets")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class VetRestController {

    private final ClinicService clinicService;

    private final Validator validator;

    @Inject
    public VetRestController(ClinicService clinicService, Validator validator) {
        this.clinicService = clinicService;
        this.validator = validator;
    }

    @GET
    @APIResponse(content = @Content(schema = @Schema(type = SchemaType.ARRAY, implementation = Vet.class)))
    @Tag(name = "Vet Rest Controller", description = "vet-rest-controller")
    public Response getAllVets() {
        Collection<Vet> vets = new ArrayList<>();
        vets.addAll(this.clinicService.findAllVets());
        if (vets.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(vets).build();
    }

    @GET
    @Path("/{vetId}")
    @APIResponse(content = @Content(schema = @Schema(implementation = Vet.class)))
    @Tag(name = "Vet Rest Controller", description = "vet-rest-controller")
    public Response getVet(@PathParam("vetId") int vetId) {
        Vet vet = this.clinicService.findVetById(vetId);
        if (vet == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(vet).build();
    }

    @POST
    @APIResponse(content = @Content(schema = @Schema(implementation = Vet.class)))
    @Tag(name = "Vet Rest Controller", description = "vet-rest-controller")
    public Response addVet(Vet vet) {
        Set<ConstraintViolation<Vet>> violations = validator.validate(vet);
        if (!violations.isEmpty() || (vet == null)) {
            BindingErrorsResponse errors = new BindingErrorsResponse<>(violations);
            return Response.status(Response.Status.BAD_REQUEST)
                    .header("errors", errors.toJSON())
                    .build();
        }
        this.clinicService.saveVet(vet);
        URI uri = UriBuilder.fromPath("/api/vets/{id}").build(vet.getId());
        return Response.created(uri).entity(vet).build();
    }

    @PUT
    @Path("/{vetId}")
    @APIResponse(content = @Content(schema = @Schema(implementation = Vet.class)))
    @Tag(name = "Vet Rest Controller", description = "vet-rest-controller")
    public Response updateVet(@PathParam("vetId") int vetId, @Valid Vet vet) {
        Set<ConstraintViolation<Vet>> violations = validator.validate(vet);
        if (!violations.isEmpty() || (vet == null)) {
            BindingErrorsResponse errors = new BindingErrorsResponse<>(violations);
            return Response.status(Response.Status.BAD_REQUEST)
                    .header("errors", errors.toJSON())
                    .build();
        }
        Vet currentVet = this.clinicService.findVetById(vetId);
        if (currentVet == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        currentVet.setFirstName(vet.getFirstName());
        currentVet.setLastName(vet.getLastName());
        currentVet.clearSpecialties();
        for (Specialty spec : vet.getSpecialties()) {
            currentVet.addSpecialty(spec);
        }
        this.clinicService.saveVet(currentVet);
        return Response.status(Response.Status.NO_CONTENT).entity(currentVet).build();
    }

    @Transactional
    @DELETE
    @Path("/{vetId}")
    @Tag(name = "Vet Rest Controller", description = "vet-rest-controller")
    public Response deleteVet(@PathParam("vetId") int vetId) {
        Vet vet = this.clinicService.findVetById(vetId);
        if (vet == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        this.clinicService.deleteVet(vet);
        return Response.status(Response.Status.NO_CONTENT).build();
    }
}

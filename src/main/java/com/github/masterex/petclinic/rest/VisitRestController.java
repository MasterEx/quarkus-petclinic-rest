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

import com.github.masterex.petclinic.model.Visit;
import com.github.masterex.petclinic.service.ClinicService;
import java.net.URI;
import java.util.ArrayList;
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
@Path("/api/visits")
public class VisitRestController {

    private final ClinicService clinicService;

    private final Validator validator;

    @Inject
    public VisitRestController(ClinicService clinicService, Validator validator) {
        this.clinicService = clinicService;
        this.validator = validator;
    }

    @GET
    @Path("")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllVisits() {
        Collection<Visit> visits = new ArrayList<>();
        visits.addAll(this.clinicService.findAllVisits());
        if (visits.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok().entity(visits).build();
    }

    @GET
    @Path("/{visitId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getVisit(@PathParam("visitId") int visitId) {
        Visit visit = this.clinicService.findVisitById(visitId);
        if (visit == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok().entity(visit).build();
    }

    @POST
    @Path("")
    @Produces(MediaType.APPLICATION_JSON)
    public Response addVisit(Visit visit) {
        Set<ConstraintViolation<Visit>> violations = validator.validate(visit);
        if (!violations.isEmpty() || (visit == null)) {
            BindingErrorsResponse errors = new BindingErrorsResponse<>(violations);
            return Response.status(Response.Status.BAD_REQUEST)
                    .header("errors", errors.toJSON())
                    .build();
        }
        this.clinicService.saveVisit(visit);
        URI uri = UriBuilder.fromPath("/api/visits/{id}").build(visit.getId());
        return Response.created(uri).entity(visit).build();
    }

    @PUT
    @Path("/{visitId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateVisit(@PathParam("visitId") int visitId, Visit visit) {
        Set<ConstraintViolation<Visit>> violations = validator.validate(visit);
        if (!violations.isEmpty() || (visit == null)) {
            BindingErrorsResponse errors = new BindingErrorsResponse<>(violations);
            return Response.status(Response.Status.BAD_REQUEST)
                    .header("errors", errors.toJSON())
                    .build();
        }
        Visit currentVisit = this.clinicService.findVisitById(visitId);
        if (currentVisit == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        currentVisit.setDate(visit.getDate());
        currentVisit.setDescription(visit.getDescription());
        currentVisit.setPet(visit.getPet());
        this.clinicService.saveVisit(currentVisit);
        return Response.status(Response.Status.NO_CONTENT).entity(currentVisit).build();
    }

    @DELETE
    @Path("/{visitId}")
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Response deleteVisit(@PathParam("visitId") int visitId) {
        Visit visit = this.clinicService.findVisitById(visitId);
        if (visit == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        this.clinicService.deleteVisit(visit);
        return Response.status(Response.Status.NO_CONTENT).build();
    }

}

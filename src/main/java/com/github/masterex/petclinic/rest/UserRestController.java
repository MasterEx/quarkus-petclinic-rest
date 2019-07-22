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

import com.github.masterex.petclinic.model.User;
import com.github.masterex.petclinic.service.ClinicService;
import com.github.masterex.petclinic.service.UserService;
import java.util.Set;
import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author Periklis Ntanasis <pntanasis@gmail.com>
 */
@Path("/api/users")
public class UserRestController {

    private final UserService userService;

    private final Validator validator;

    @Inject
    public UserRestController(UserService userService, Validator validator) {
        this.userService = userService;
        this.validator = validator;
    }

    @POST
    @Path("")
    @Produces(MediaType.APPLICATION_JSON)
    public Response addOwner(User user) throws Exception {
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        if (!violations.isEmpty() || (user == null)) {
            BindingErrorsResponse errors = new BindingErrorsResponse<>(violations);
            return Response.status(Response.Status.BAD_REQUEST)
                    .header("errors", errors.toJSON())
                    .build();
        }

        this.userService.saveUser(user);
        return Response.status(Response.Status.CREATED).entity(user).build();
    }

}

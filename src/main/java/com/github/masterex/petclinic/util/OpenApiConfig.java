/*
 * Copyright 2019 Periklis Ntanasis
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
package com.github.masterex.petclinic.util;

import javax.ws.rs.core.Application;
import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.info.Contact;
import org.eclipse.microprofile.openapi.annotations.info.Info;
import org.eclipse.microprofile.openapi.annotations.info.License;

/**
 * OpenApi configuration.
 *
 * @author Periklis Ntanasis <pntanasis@gmail.com>
 */
@OpenAPIDefinition(info = @Info(
        title = "REST Petclinic backend Api Documentation",
        description = "This is REST API documentation of the Spring Petclinic backend. If authentication is enabled, when calling the APIs use admin/admin",
        version = "1.0.0",
        contact = @Contact(
                name = "Periklis Ntanasis",
                email = "pntanasis@gmail.com",
                url = "https://github.com/MasterEx/quarkus-petclinic-rest"),
        license = @License(name = "Apache 2.0",
                url = "http://www.apache.org/licenses/LICENSE-2.0")
)
)
public class OpenApiConfig extends Application {

}

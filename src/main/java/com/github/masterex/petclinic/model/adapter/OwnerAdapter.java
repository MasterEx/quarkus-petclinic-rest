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
package com.github.masterex.petclinic.model.adapter;

import com.github.masterex.petclinic.model.Owner;
import java.text.Format;
import java.text.SimpleDateFormat;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.bind.adapter.JsonbAdapter;

/**
 * Transforms from JSON to Owner and vice-versa.
 *
 * @author Periklis Ntanasis <pntanasis@gmail.com>
 */
public class OwnerAdapter implements JsonbAdapter<Owner, JsonObject> {

    Format formatter = new SimpleDateFormat("yyyy/MM/dd");

    @Override
    public JsonObject adaptToJson(Owner obj) throws Exception {
        JsonArrayBuilder pets = Json.createArrayBuilder();
        obj.getPets().stream().forEach(pet -> {
            JsonArrayBuilder visits = Json.createArrayBuilder();
            pet.getVisits().stream().forEach(visit -> {
                visits.add(Json.createObjectBuilder()
                        .add("id", visit.getId())
                        .add("date", formatter.format(visit.getDate()))
                        .add("description", visit.getDescription())
                        .add("pet", visit.getPet().getId())
                        .build());
            });
            pets.add(
                    Json.createObjectBuilder()
                            .add("id", pet.getId())
                            .add("name", pet.getName())
                            .add("birthDate", formatter.format(pet.getBirthDate()))
                            .add("type", Json.createObjectBuilder()
                                    .add("id", pet.getType().getId())
                                    .add("name", pet.getType().getName()))
                            .add("owner", pet.getOwner().getId())
                            .add("visits", visits.build())
                            .build()
            );
        });
        return Json.createObjectBuilder()
                .add("id", obj.getId())
                .add("firstName", obj.getFirstName())
                .add("lastName", obj.getLastName())
                .add("address", obj.getAddress())
                .add("city", obj.getCity())
                .add("telephone", obj.getTelephone())
                .add("pets", pets.build())
                .build();
    }

    @Override
    public Owner adaptFromJson(JsonObject obj) throws Exception {
        Owner owner = new Owner();
        if (!obj.isNull("id")) {
            owner.setId(obj.getInt("id"));
        }
        owner.setFirstName(obj.getString("firstName"));
        owner.setLastName(obj.getString("lastName"));
        owner.setAddress(obj.getString("address"));
        owner.setCity(obj.getString("city"));
        owner.setTelephone(obj.getString("telephone"));
        return owner;
    }

}

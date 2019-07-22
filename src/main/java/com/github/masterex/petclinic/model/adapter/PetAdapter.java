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
import com.github.masterex.petclinic.model.Pet;
import com.github.masterex.petclinic.model.PetType;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.bind.adapter.JsonbAdapter;

/**
 * Transforms from JSON to Pet and vice-versa.
 *
 * @author Periklis Ntanasis <pntanasis@gmail.com>
 */
public class PetAdapter implements JsonbAdapter<Pet, JsonObject> {

    SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");

    @Override
    public JsonObject adaptToJson(Pet obj) throws Exception {

        JsonArrayBuilder array = Json.createArrayBuilder();

        obj.getVisits().stream().forEach(visit -> {
            array.add(
                    Json.createObjectBuilder()
                            .add("id", visit.getId())
                            .add("date", formatter.format(visit.getDate()))
                            .add("description", visit.getDescription())
                            .add("pet", visit.getPet().getId())
                            .build()
            );
        });

        return Json.createObjectBuilder()
                .add("id", obj.getId())
                .add("name", obj.getName())
                .add("birthDate", formatter.format(obj.getBirthDate()))
                .add("type",
                        Json.createObjectBuilder()
                                .add("id", obj.getType().getId())
                                .add("name", obj.getType().getName())
                                .build())
                .add("owner",
                        Json.createObjectBuilder()
                                .add("id", obj.getOwner().getId())
                                .add("firstName", obj.getOwner().getFirstName())
                                .add("lastName", obj.getOwner().getLastName())
                                .add("address", obj.getOwner().getAddress())
                                .add("city", obj.getOwner().getCity())
                                .add("telephone", obj.getOwner().getTelephone())
                                .build())
                .add("visits", array.build())
                .build();
    }

    @Override
    public Pet adaptFromJson(JsonObject obj) throws Exception {
        Pet pet = new Pet();
        Owner owner = null;
        if (!obj.isNull("owner")) {
            owner = new Owner();
            owner.setId(obj.getJsonObject("owner").getInt("id"));
            owner.setFirstName(obj.getJsonObject("owner").getString("firstName"));
            owner.setLastName(obj.getJsonObject("owner").getString("lastName"));
            owner.setAddress(obj.getJsonObject("owner").getString("address"));
            owner.setCity(obj.getJsonObject("owner").getString("city"));
            owner.setTelephone(obj.getJsonObject("owner").getString("telephone"));
        }
        PetType petType = null;
        if (!obj.isNull("type")) {
            petType = new PetType();
            petType.setId(obj.getJsonObject("type").getInt("id"));
            petType.setName(obj.getJsonObject("type").getString("name"));
        }
        Date birthDate = null;
        String name = obj.getString("name");
        String birthDateStr = obj.getString("birthDate");
        try {
            birthDate = formatter.parse(birthDateStr);
        } catch (ParseException e) {
            throw new IOException(e);
        }

        if (!obj.isNull("id")) {
            pet.setId(obj.getInt("id"));
        }
        pet.setName(name);
        pet.setBirthDate(birthDate);
        pet.setOwner(owner);
        pet.setType(petType);
        return pet;
    }

}

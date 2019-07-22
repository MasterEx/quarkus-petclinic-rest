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

import com.github.masterex.petclinic.model.BaseEntity;
import com.github.masterex.petclinic.model.Owner;
import com.github.masterex.petclinic.model.Pet;
import com.github.masterex.petclinic.model.Role;
import com.github.masterex.petclinic.model.User;
import com.github.masterex.petclinic.model.Vet;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import javax.json.bind.config.PropertyVisibilityStrategy;

/**
 * Hides some fields from the JSON marshalling. Equivalent to Jackson's
 * JsonIgnore annotation type.
 *
 * @author Periklis Ntanasis <pntanasis@gmail.com>
 */
public class CustomVisibilityStrategy implements PropertyVisibilityStrategy {

    @Override
    public boolean isVisible(Field field) {
        return true;
    }

    @Override
    public boolean isVisible(Method method) {
        boolean visibility = true;
        visibility = checkVisibility(visibility, method, BaseEntity.class, "isNew");
        visibility = checkVisibility(visibility, method, Pet.class, "getVisitsInternal");
        visibility = checkVisibility(visibility, method, Owner.class, "getPetsInternal");
        visibility = checkVisibility(visibility, method, Role.class, "getUser");
        visibility = checkVisibility(visibility, method, User.class, "addRole");
        visibility = checkVisibility(visibility, method, Vet.class, "getSpecialtiesInternal", "getNrOfSpecialties");
        return visibility;
    }

    private boolean checkVisibility(boolean visibility, Method method, Class<?> clazz, String... methodNames) {
        for (String methodName : methodNames) {
            if (method.getDeclaringClass() == clazz) {
                if (methodName.equals(method.getName())) {
                    return false;
                }
            }
        }
        return visibility;
    }

}

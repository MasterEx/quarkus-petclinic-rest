/*
 * Copyright 2019 Periklis Ntanasis
 * Copyright 2002-2017 the original author or authors.
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
package com.github.masterex.petclinic.repository;

import com.github.masterex.petclinic.model.PetType;
import java.util.Collection;

/**
 * Repository for <code>PetType</code> domain objects.
 *
 * @author Vitaliy Fedoriv
 * @author Periklis Ntanasis <pntanasis@gmail.com>
 */
public interface PetTypeRepository {

    /**
     * Retrieve a <code>PetType</code> from the data store by id.
     *
     * @param id the id to search for
     * @return the <code>PetType</code> if found
     */
    PetType findById(int id);

    /**
     * Retrieve <code>PetType</code>s from the data store, returning all owners
     *
     * @return a <code>Collection</code> of <code>PetType</code>s (or an empty
     * <code>Collection</code> if none found)
     */
    Collection<PetType> findAll();

    /**
     * Save an <code>PetType</code> to the data store, either inserting or
     * updating it.
     *
     * @param petType the <code>PetType</code> to save
     * @see BaseEntity#isNew
     */
    void save(PetType petType);

    /**
     * Delete an <code>PetType</code> to the data store by <code>PetType</code>.
     *
     * @param petType the <code>PetType</code> to delete
     *
     */
    void delete(PetType petType);

}

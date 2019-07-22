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

import com.github.masterex.petclinic.model.Specialty;
import java.util.Collection;

/**
 * Repository for <code>Specialty</code> domain objects.
 *
 * @author Vitaliy Fedoriv
 * @author Periklis Ntanasis <pntanasis@gmail.com>
 */
public interface SpecialtyRepository {

    /**
     * Retrieve a <code>Specialty</code> from the data store by id.
     *
     * @param id the id to search for
     * @return the <code>Specialty</code> if found
     */
    Specialty findById(int id);

    /**
     * Retrieve <code>Specialty</code>s from the data store, returning all
     * owners
     *
     * @return a <code>Collection</code> of <code>Specialty</code>s (or an empty
     * <code>Collection</code> if none found)
     */
    Collection<Specialty> findAll();

    /**
     * Save an <code>Specialty</code> to the data store, either inserting or
     * updating it.
     *
     * @param specialty the <code>Specialty</code> to save
     * @see BaseEntity#isNew
     */
    void save(Specialty specialty);

    /**
     * Delete an <code>Specialty</code> to the data store by
     * <code>Specialty</code>.
     *
     * @param specialty the <code>Specialty</code> to delete
     *
     */
    void delete(Specialty specialty);

}

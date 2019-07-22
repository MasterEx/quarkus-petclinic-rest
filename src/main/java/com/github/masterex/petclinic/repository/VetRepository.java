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

import com.github.masterex.petclinic.model.Vet;
import java.util.Collection;

/**
 * Repository class for <code>Vet</code> domain objects All method names are
 * compliant with Spring Data naming conventions so this interface can easily be
 * extended for Spring Data See here:
 * http://static.springsource.org/spring-data/jpa/docs/current/reference/html/jpa.repositories.html#jpa.query-methods.query-creation
 *
 * @author Ken Krebs
 * @author Juergen Hoeller
 * @author Sam Brannen
 * @author Michael Isvy
 * @author Vitaliy Fedoriv
 * @author Periklis Ntanasis <pntanasis@gmail.com>
 */
public interface VetRepository {

    /**
     * Retrieve all <code>Vet</code>s from the data store.
     *
     * @return a <code>Collection</code> of <code>Vet</code>s
     */
    Collection<Vet> findAll();

    /**
     * Retrieve a <code>Vet</code> from the data store by id.
     *
     * @param id the id to search for
     * @return the <code>Vet</code> if found
     */
    Vet findById(int id);

    /**
     * Save an <code>Vet</code> to the data store, either inserting or updating
     * it.
     *
     * @param vet the <code>Vet</code> to save
     * @see BaseEntity#isNew
     */
    void save(Vet vet);

    /**
     * Delete an <code>Vet</code> to the data store by <code>Vet</code>.
     *
     * @param vet the <code>Vet</code> to delete
     *
     */
    void delete(Vet vet);

}

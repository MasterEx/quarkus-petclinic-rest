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
package com.github.masterex.petclinic.repository.jpa;

import com.github.masterex.petclinic.model.Vet;
import com.github.masterex.petclinic.repository.VetRepository;
import java.util.Collection;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * JPA implementation of the {@link VetRepository} interface.
 *
 * @author Mike Keith
 * @author Rod Johnson
 * @author Sam Brannen
 * @author Michael Isvy
 * @author Vitaliy Fedoriv
 * @author Periklis Ntanasis <pntanasis@gmail.com>
 */
@ApplicationScoped
public class JpaVetRepositoryImpl implements VetRepository {

    private final EntityManager em;

    @Inject
    public JpaVetRepositoryImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public Vet findById(int id) {
        return this.em.find(Vet.class, id);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Collection<Vet> findAll() {
        return this.em.createQuery("SELECT v FROM Vet v").getResultList();
    }

    @Override
    public void save(Vet vet) {
        if (vet.getId() == null) {
            this.em.persist(vet);
        } else {
            this.em.merge(vet);
        }
    }

    @Override
    public void delete(Vet vet) {
        this.em.remove(this.em.contains(vet) ? vet : this.em.merge(vet));
    }

}

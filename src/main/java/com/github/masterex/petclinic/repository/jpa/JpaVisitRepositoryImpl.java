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

import com.github.masterex.petclinic.model.Visit;
import com.github.masterex.petclinic.repository.VisitRepository;
import java.util.Collection;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * JPA implementation of the ClinicService interface using EntityManager.
 *
 * @author Mike Keith
 * @author Rod Johnson
 * @author Sam Brannen
 * @author Michael Isvy
 * @author Vitaliy Fedoriv
 * @author Periklis Ntanasis <pntanasis@gmail.com>
 */
@ApplicationScoped
public class JpaVisitRepositoryImpl implements VisitRepository {

    private final EntityManager em;

    @Inject
    public JpaVisitRepositoryImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public void save(Visit visit) {
        if (visit.getId() == null) {
            this.em.persist(visit);
        } else {
            this.em.merge(visit);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Visit> findByPetId(Integer petId) {
        Query query = this.em.createQuery("SELECT v FROM Visit v where v.pet.id= :id");
        query.setParameter("id", petId);
        return query.getResultList();
    }

    @Override
    public Visit findById(int id) {
        return this.em.find(Visit.class, id);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Collection<Visit> findAll() {
        return this.em.createQuery("SELECT v FROM Visit v").getResultList();
    }

    @Override
    public void delete(Visit visit) {
        this.em.createQuery("DELETE FROM Visit visit WHERE id=" + visit.getId()).executeUpdate();
    }

}

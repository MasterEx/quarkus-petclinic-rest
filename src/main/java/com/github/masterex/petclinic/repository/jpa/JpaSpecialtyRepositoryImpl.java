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
package com.github.masterex.petclinic.repository.jpa;

import com.github.masterex.petclinic.model.Specialty;
import com.github.masterex.petclinic.repository.SpecialtyRepository;
import java.util.Collection;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;

/**
 * @author Vitaliy Fedoriv
 * @author Periklis Ntanasis <pntanasis@gmail.com>
 */
@ApplicationScoped
public class JpaSpecialtyRepositoryImpl implements SpecialtyRepository {

    private final EntityManager em;

    @Inject
    public JpaSpecialtyRepositoryImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public Specialty findById(int id) {
        return this.em.find(Specialty.class, id);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Collection<Specialty> findAll() {
        return this.em.createQuery("SELECT s FROM Specialty s").getResultList();
    }

    @Override
    public void save(Specialty specialty) {
        if (specialty.getId() == null) {
            this.em.persist(specialty);
        } else {
            this.em.merge(specialty);
        }
    }

    @Override
    public void delete(Specialty specialty) {
        Integer specId = specialty.getId();
        this.em.createNativeQuery("DELETE FROM vet_specialties WHERE specialty_id=" + specId).executeUpdate();
        this.em.createQuery("DELETE FROM Specialty specialty WHERE id=" + specId).executeUpdate();
    }

}

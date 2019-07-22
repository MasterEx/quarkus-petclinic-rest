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

import com.github.masterex.petclinic.model.Pet;
import com.github.masterex.petclinic.model.PetType;
import com.github.masterex.petclinic.repository.PetRepository;
import java.util.Collection;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;

/**
 * JPA implementation of the {@link PetRepository} interface.
 *
 * @author Mike Keith
 * @author Rod Johnson
 * @author Sam Brannen
 * @author Michael Isvy
 * @author Vitaliy Fedoriv
 * @author Periklis Ntanasis <pntanasis@gmail.com>
 */
@ApplicationScoped
public class JpaPetRepositoryImpl implements PetRepository {

    private final EntityManager em;

    @Inject
    public JpaPetRepositoryImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<PetType> findPetTypes() {
        return this.em.createQuery("SELECT ptype FROM PetType ptype ORDER BY ptype.name").getResultList();
    }

    @Override
    public Pet findById(int id) {
        return this.em.find(Pet.class, id);
    }

    @Override
    public void save(Pet pet) {
        if (pet.getId() == null) {
            this.em.persist(pet);
        } else {
            this.em.merge(pet);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public Collection<Pet> findAll() {
        return this.em.createQuery("SELECT p FROM Pet p").getResultList();
    }

    @Override
    public void delete(Pet pet) {
        String petId = pet.getId().toString();
        this.em.createQuery("DELETE FROM Visit v WHERE pet_id=" + petId).executeUpdate();
        this.em.createQuery("DELETE FROM Pet p WHERE p.id=" + petId).executeUpdate();
        if (em.contains(pet)) {
            em.remove(pet);
        }
    }

}

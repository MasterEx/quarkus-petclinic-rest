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
package com.github.masterex.petclinic.service;

import com.github.masterex.petclinic.model.Owner;
import com.github.masterex.petclinic.model.Pet;
import com.github.masterex.petclinic.model.PetType;
import com.github.masterex.petclinic.model.Specialty;
import com.github.masterex.petclinic.model.Vet;
import com.github.masterex.petclinic.model.Visit;
import com.github.masterex.petclinic.repository.OwnerRepository;
import com.github.masterex.petclinic.repository.PetRepository;
import com.github.masterex.petclinic.repository.PetTypeRepository;
import com.github.masterex.petclinic.repository.SpecialtyRepository;
import com.github.masterex.petclinic.repository.VetRepository;
import com.github.masterex.petclinic.repository.VisitRepository;
import java.util.Collection;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

/**
 *
 * @author Periklis Ntanasis <pntanasis@gmail.com>
 */
@ApplicationScoped
public class ClinicServiceImpl implements ClinicService {

    private final PetRepository petRepository;
    private final VetRepository vetRepository;
    private final OwnerRepository ownerRepository;
    private final VisitRepository visitRepository;
    private final SpecialtyRepository specialtyRepository;
    private final PetTypeRepository petTypeRepository;

    @Inject
    public ClinicServiceImpl(
            PetRepository petRepository,
            VetRepository vetRepository,
            OwnerRepository ownerRepository,
            VisitRepository visitRepository,
            SpecialtyRepository specialtyRepository,
            PetTypeRepository petTypeRepository) {
        this.petRepository = petRepository;
        this.vetRepository = vetRepository;
        this.ownerRepository = ownerRepository;
        this.visitRepository = visitRepository;
        this.specialtyRepository = specialtyRepository;
        this.petTypeRepository = petTypeRepository;
    }

    @Override
    @Transactional
    public Collection<Pet> findAllPets() {
        return petRepository.findAll();
    }

    @Override
    @Transactional
    public void deletePet(Pet pet) {
        petRepository.delete(pet);
    }

    @Override
    @Transactional
    public Visit findVisitById(int visitId) {
        Visit visit = visitRepository.findById(visitId);
        return visit;
    }

    @Override
    @Transactional
    public Collection<Visit> findAllVisits() {
        return visitRepository.findAll();
    }

    @Override
    @Transactional
    public void deleteVisit(Visit visit) {
        visitRepository.delete(visit);
    }

    @Override
    @Transactional
    public Vet findVetById(int id) {
        Vet vet = vetRepository.findById(id);
        return vet;
    }

    @Override
    @Transactional
    public Collection<Vet> findAllVets() {
        return vetRepository.findAll();
    }

    @Override
    @Transactional
    public void saveVet(Vet vet) {
        vetRepository.save(vet);
    }

    @Override
    @Transactional
    public void deleteVet(Vet vet) {
        vetRepository.delete(vet);
    }

    @Override
    @Transactional
    public Collection<Owner> findAllOwners() {
        return ownerRepository.findAll();
    }

    @Override
    @Transactional
    public void deleteOwner(Owner owner) {
        ownerRepository.delete(owner);
    }

    @Override
    @Transactional
    public PetType findPetTypeById(int petTypeId) {
        PetType petType = petTypeRepository.findById(petTypeId);
        return petType;
    }

    @Override
    @Transactional
    public Collection<PetType> findAllPetTypes() {
        return petTypeRepository.findAll();
    }

    @Override
    @Transactional
    public void savePetType(PetType petType) {
        petTypeRepository.save(petType);
    }

    @Override
    @Transactional
    public void deletePetType(PetType petType) {
        petTypeRepository.delete(petType);
    }

    @Override
    @Transactional
    public Specialty findSpecialtyById(int specialtyId) {
        Specialty specialty = specialtyRepository.findById(specialtyId);
        return specialty;
    }

    @Override
    @Transactional
    public Collection<Specialty> findAllSpecialties() {
        return specialtyRepository.findAll();
    }

    @Override
    @Transactional
    public void saveSpecialty(Specialty specialty) {
        specialtyRepository.save(specialty);
    }

    @Override
    @Transactional
    public void deleteSpecialty(Specialty specialty) {
        specialtyRepository.delete(specialty);
    }

    @Override
    @Transactional
    public Collection<PetType> findPetTypes() {
        return petRepository.findPetTypes();
    }

    @Override
    @Transactional
    public Owner findOwnerById(int id) {
        Owner owner = ownerRepository.findById(id);
        return owner;
    }

    @Override
    @Transactional
    public Pet findPetById(int id) {
        Pet pet = petRepository.findById(id);
        return pet;
    }

    @Override
    @Transactional
    public void savePet(Pet pet) {
        petRepository.save(pet);

    }

    @Override
    @Transactional
    public void saveVisit(Visit visit) {
        visitRepository.save(visit);

    }

    @Override
    @Transactional
    public Collection<Vet> findVets() {
        return vetRepository.findAll();
    }

    @Override
    @Transactional
    public void saveOwner(Owner owner) {
        ownerRepository.save(owner);

    }

    @Override
    @Transactional
    public Collection<Owner> findOwnerByLastName(String lastName) {
        return ownerRepository.findByLastName(lastName);
    }

    @Override
    @Transactional
    public Collection<Visit> findVisitsByPetId(int petId) {
        return visitRepository.findByPetId(petId);
    }

}

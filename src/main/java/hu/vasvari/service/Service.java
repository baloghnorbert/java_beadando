package hu.vasvari.service;


import hu.vasvari.model.Owner;
import hu.vasvari.model.Pet;
import hu.vasvari.model.Vaccination;
import hu.vasvari.repository.Repository;

import java.util.Collections;
import java.util.List;

import static java.util.Objects.isNull;

public class Service {

    private final Repository repo;

    public Service(Repository repo) {
        this.repo = repo;
    }

    public void addOwnerWithPet(Owner owner, Pet pet) {
        repo.addOwnerWithPet(owner, pet);
    }

    public void addPetToOwner(Integer ownerId, Pet pet) {
        repo.addPetToOwner(ownerId, pet);
    }

    public void addVaccineToPet(int petId, Vaccination vacc) {
        repo.addVaccineToPet(petId, vacc);
    }

    public List<Owner> findAllOwners() {
        return repo.findAllOwners();
    }

    public List<Pet> findAllPetByOwner(Owner owner) {
        return findAllPetByOwner(owner.getOwnerId());
    }

    public List<Pet> findAllPetByOwner(int ownerId) {
        return repo.findAllPetByOwner(ownerId);
    }

    public List<Vaccination> findAllVaccByPet(Pet pet) {
        return isNull(pet) ? Collections.emptyList() : repo.findAllVaccByPetId(pet.getPetId());
    }

    public void updatePet(Pet pet) {
        repo.updatePet(pet);
    }
}

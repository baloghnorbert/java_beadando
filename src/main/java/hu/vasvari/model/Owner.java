package hu.vasvari.model;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Owner {

    public static final String ID = "owner_id";
    public static final String KEY = "owner_key";
    public static final String NAME = "name";
    public static final String MAIL = "mail";
    public static final String PRIMARY_PHONE = "primaryPhone";
    public static final String SECONDARY_PHONE = "secondaryPhone";

    private Integer ownerId;
    private String ownerKey;
    private String name;
    private String mail;
    private String primaryPhone;
    private String secondaryPhone;
    private List<Pet> pets;

    public Owner() {
        this.pets = new ArrayList<>();
    }

    public Owner(Integer ownerId, String ownerKey, String name,
                 String mail, String primaryPhone, String secondaryPhone) {
        this.ownerId = ownerId;
        this.ownerKey = ownerKey;
        this.name = name;
        this.mail = mail;
        this.primaryPhone = primaryPhone;
        this.secondaryPhone = secondaryPhone;
        this.pets = new ArrayList<>();
    }

    public Owner(String ownerKey, String name, String mail,
                 String primaryPhone, String secondaryPhone) {
        this.ownerKey = ownerKey;
        this.name = name;
        this.mail = mail;
        this.primaryPhone = primaryPhone;
        this.secondaryPhone = secondaryPhone;
        this.pets = new ArrayList<>();
    }

    public static List<String> getOwnerHeaders() {
        return Arrays.asList("Azonosító", "Név", "E-mail", "Telefonszám", "Telefonszám 2:");
    }

    public Integer getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Integer ownerId) {
        this.ownerId = ownerId;
    }

    public void addNewPet(Pet pet) {
        pets.add(pet);
    }

    public String getOwnerKey() {
        return ownerKey;
    }

    public void setOwnerKey(String ownerKey) {
        this.ownerKey = ownerKey;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPrimaryPhone() {
        return primaryPhone;
    }

    public void setPrimaryPhone(String primaryPhone) {
        this.primaryPhone = primaryPhone;
    }

    public String getSecondaryPhone() {
        return secondaryPhone;
    }

    public void setSecondaryPhone(String secondaryPhone) {
        this.secondaryPhone = secondaryPhone;
    }

    public List<Pet> getPets() {
        return pets;
    }

    public void setPets(List<Pet> pets) {
        this.pets = pets;
    }

    @Override
    public String toString() {
        return name + "[" + primaryPhone + "]";
    }
}


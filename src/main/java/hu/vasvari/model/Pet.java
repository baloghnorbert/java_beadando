package hu.vasvari.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;


public class Pet {

    public static final String ID = "pet_id";
    public static final String KIND = "kind";
    public static final String NAME = "name";
    public static final String TYPE = "type";
    public static final String COLOR = "color";
    public static final String BIRTH_DATE = "bith_date";
    public static final String WEIGHT = "weight";
    public static final String CHIP_NUMBER = "chipNumber";

    private Integer petId;
    private String name;
    private String kind;
    private String type;
    private String color;
    private LocalDate birthDate;
    private BigDecimal weight;
    private String chipNumber;
    private List<Vaccination> vaccinations;

    public Pet() {
        this.vaccinations = new ArrayList<>();
    }

    public Pet(Integer petId, String name, String kind, String type, String color, LocalDate birthDate,
               BigDecimal weight, String chipNumber) {
        this.petId = petId;
        this.name = name;
        this.kind = kind;
        this.type = type;
        this.color = color;
        this.birthDate = birthDate;
        this.weight = weight;
        this.chipNumber = chipNumber;
        this.vaccinations = new ArrayList<>();
    }

    public Pet(String name, String kind, String type, String color, LocalDate birthDate, BigDecimal weight,
               String chipNumber) {
        this.name = name;
        this.kind = kind;
        this.type = type;
        this.color = color;
        this.birthDate = birthDate;
        this.weight = weight;
        this.chipNumber = chipNumber;
    }

    public static List<String> getPetHeaders() {
        return Arrays.asList("Név", "Fajta", "Típus", "Szín", "Születési dátum", "Súly", "Chipszám");
    }

    public Integer getPetId() {
        return petId;
    }

    public void setPetId(Integer petId) {
        this.petId = petId;
    }

    public void addNewVaccionation(Vaccination vacc) {
        vaccinations.add(vacc);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public java.sql.Date getBirthSQLDate() {
        if (birthDate == null) {
            return null;
        }
        Date from = Date.from(birthDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        return new java.sql.Date(from.getTime());
    }

    public String getBirthDateString() {
        return birthDate.toString();
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    public String getChipNumber() {
        return chipNumber;
    }

    public void setChipNumber(String chipNumber) {
        this.chipNumber = chipNumber;
    }

    public List<Vaccination> getVaccinations() {
        return vaccinations;
    }

    public void setVaccinations(List<Vaccination> vaccinations) {
        this.vaccinations = vaccinations;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return name + "[" + type + "]";
    }

}


package hu.vasvari.model;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class Vaccination {

    public static final String ID = "vacc_id";
    public static final String VACCINE_DATE = "vaccine_date";
    public static final String VALIDATION_DATE = "valid_date";
    public static final String REASON = "reason";

    private Integer vaccId;
    private LocalDate vaccinationDate;
    private LocalDate validDate;
    private String reason;

    public Vaccination() {
    }

    public Vaccination(LocalDate vaccinationDate, LocalDate validDate, String reason) {
        this.vaccinationDate = vaccinationDate;
        this.validDate = validDate;
        this.reason = reason;
    }

    public Vaccination(Integer vaccId, LocalDate vaccinationDate, LocalDate validDate, String reason) {
        this.vaccId = vaccId;
        this.vaccinationDate = vaccinationDate;
        this.validDate = validDate;
        this.reason = reason;
    }

    public static List<String> getVaccinationHeaders() {
        return Arrays.asList("Beadás dátuma", "Érvényesség dátuma", "Oka");
    }

    public Integer getVaccId() {
        return vaccId;
    }

    public void setVaccId(Integer vaccId) {
        this.vaccId = vaccId;
    }

    public LocalDate getVaccinationDate() {
        return vaccinationDate;
    }

    public void setVaccinationDate(LocalDate vaccinationDate) {
        this.vaccinationDate = vaccinationDate;
    }

    public String getVaccinationDateString() {
        return vaccinationDate.toString();
    }

    public LocalDate getValidDate() {
        return validDate;
    }

    public void setValidDate(LocalDate validDate) {
        this.validDate = validDate;
    }

    public String getValidDateString() {
        return validDate.toString();
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

}



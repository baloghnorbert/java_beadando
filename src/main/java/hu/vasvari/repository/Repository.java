package hu.vasvari.repository;


import hu.vasvari.model.Owner;
import hu.vasvari.model.Pet;
import hu.vasvari.model.Vaccination;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static java.util.Optional.ofNullable;

public class Repository {

    private static final String URL = "jdbc:mysql://localhost:3306/kinyir";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";

    private Connection conn = null;

    public Repository() {
        initDatabase();
    }

    private void initDatabase() {
        try {
//            Class.forName("com.mysql.jdbc.Driver");
            this.conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException ex) {
            System.out.println("Valami baj van a connection létrehozásával.\n" + ex);
        }
    }

    public void addOwnerWithPet(Owner owner, Pet pet) {
        saveOwner(owner);
        savePet(pet);
        savetoKt(owner, pet);
    }

    private void saveOwner(final Owner owner) {
        try {
            String sql = "INSERT INTO owner (owner_key, name, mail,primaryPhone,secondaryPhone) VALUES (?,?,?,?,?)";
            PreparedStatement preparedStatement = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, owner.getOwnerKey());
            preparedStatement.setString(2, owner.getName());
            preparedStatement.setString(3, owner.getMail());
            preparedStatement.setString(4, owner.getPrimaryPhone());
            preparedStatement.setString(5, owner.getSecondaryPhone());
            preparedStatement.execute();

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            generatedKeys.next();
            owner.setOwnerId(generatedKeys.getInt(1));
        } catch (SQLException ex) {
            System.out.println("Valami baj van a tulajdonos hozzáadásakor");
        }
    }

    public void savePet(final Pet pet) {
        try {
            String sql = "INSERT INTO pet(name, kind, type, color, bith_date, chipNumber) VALUES (?,?,?,?,?,?)";
            PreparedStatement preparedStatement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, pet.getName());
            preparedStatement.setString(2, pet.getKind());
            preparedStatement.setString(3, pet.getType());
            preparedStatement.setString(4, pet.getColor());
            preparedStatement.setDate(5, pet.getBirthSQLDate());
            preparedStatement.setString(6, pet.getChipNumber());
            preparedStatement.execute();

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            generatedKeys.next();
            pet.setPetId(generatedKeys.getInt(1));

        } catch (SQLException ex) {
            System.out.println("Valami baj van a tulajdonos hozzáadásakor");
        }
    }

    private void savetoKt(final Owner owner, final Pet pet) {
        try {
            String sql = "INSERT INTO kt_owner_pet( owner_id, pet_id) VALUES (?,?)";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, owner.getOwnerId());
            preparedStatement.setInt(2, pet.getPetId());
            preparedStatement.execute();
        } catch (SQLException ex) {
            System.out.println("Valami baj van a kt_owner_pet hozzáadásakor");
        }
    }

    private void savetoKt(int ownerId, int petId) {
        try {
            String sql = "INSERT INTO kt_owner_pet( owner_id, pet_id) VALUES (?,?)";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, ownerId);
            preparedStatement.setInt(2, petId);
            preparedStatement.execute();
        } catch (SQLException ex) {
            System.out.println("Valami baj van a kt_owner_pet hozzáadásakor");
        }
    }

    private Owner findOwner(String key, String name) {
        String sql = "SELECT * FROM owner o WHERE o.name = ? AND o.owner_key = ?";
        System.out.println(key + " " + name);
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, key);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                return generateOwner(rs);
            }
        } catch (SQLException ex) {
            System.out.println(
                    "Valami baj van a owner megtalálása közben \n" + ex);
        }
        return null;
    }

    private Owner generateOwner(ResultSet rs) throws SQLException {
        return new Owner(
                rs.getInt(Owner.ID),
                rs.getString(Owner.KEY),
                rs.getString(Owner.NAME),
                rs.getString(Owner.MAIL),
                rs.getString(Owner.PRIMARY_PHONE),
                rs.getString(Owner.SECONDARY_PHONE));
    }

    public Pet findPetByChipNumber(String chipNumber) {
        String sql = "SELECT * FROM pet p WHERE p.chipNumber = ?";

        try {
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, chipNumber);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                return generatePet(rs);
            }
        } catch (SQLException ex) {
            System.out.println(
                    "Valami baj van a owner megtalálása közben \n" + ex);
        }
        return null;
    }

    private Pet generatePet(ResultSet rs) throws SQLException {
        return new Pet(rs.getInt(Pet.ID),
                rs.getString(Pet.NAME),
                rs.getString(Pet.KIND),
                rs.getString(Pet.TYPE),
                rs.getString(Pet.COLOR),
                getLocalDate(rs.getDate(Pet.BIRTH_DATE)),
                BigDecimal.valueOf(rs.getDouble(Pet.WEIGHT)),
                rs.getString(Pet.CHIP_NUMBER));
    }

    private LocalDate getLocalDate(Date date) {
        return ofNullable(date)
                .map(d -> new java.sql.Date(d.getTime()).toLocalDate())
                .orElse(null);
    }


    public void addPetToOwner(int ownerId, final Pet pet) {
        savePet(pet);
        savetoKt(ownerId, pet.getPetId());
    }

    public void addVaccineToPet(int petid, Vaccination vacc) {

    }

    public List<Owner> findAllOwners() {
        String sql = "SELECT * FROM owner";
        List<Owner> owners = new ArrayList<>();
        try {
            ResultSet rs = conn.createStatement().executeQuery(sql);
            while (rs.next()) {
                owners.add(generateOwner(rs));
            }
        } catch (SQLException ex) {
            System.out.println("Valami baj van a userek kiolvasásakor \n " + ex);
        }
        return owners;
    }
//
//	public Owner findOwnerbyPet(Pet pet) {
//		return owners.stream().filter(o -> o.getPets().contains(pet))
//				.findFirst().orElse(null);
//	}

    public List<Pet> findAllPetByOwner(int ownerId) {
        String sql = "SELECT p.* FROM kt_owner_pet kt LEFT JOIN pet p ON p.pet_id = kt.pet_id WHERE kt.owner_id = ?";
        List<Pet> pets = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, ownerId);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                pets.add(generatePet(rs));
            }
        } catch (SQLException ex) {
            System.out.println("Valami baj van a userek kiolvasásakor \n " + ex);
        }
        return pets;
    }

    public List<Vaccination> findAllVaccByPetId(int petId) {
        String sql = "SELECT v.* FROM kt_pet_vacine kt LEFT JOIN vaccine v ON v.vacc_id = kt.vacc_id WHERE kt.pet_id = ?";
        List<Vaccination> vaccs = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, petId);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                vaccs.add(generateVaccination(rs));
            }
        } catch (SQLException ex) {
            System.out.println("Valami baj van a userek kiolvasásakor \n" + ex);
        }
        return vaccs;
    }

    private Vaccination generateVaccination(ResultSet rs) throws SQLException {
        return new Vaccination(rs.getInt(Vaccination.ID),
                getLocalDate(rs.getDate(Vaccination.VACCINE_DATE)),
                getLocalDate(rs.getDate(Vaccination.VALIDATION_DATE)),
                rs.getString(Vaccination.REASON));
    }

    public void updatePet(Pet pet) {
        String sql = "UPDATE pet p  SET  p.name =? , p.type = ? , p.bith_date =? , p.chipNumber=? , weight = ? WHERE p.pet_id = ?";

        try {
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, pet.getName());
            preparedStatement.setString(2, pet.getType());
            preparedStatement.setString(3, pet.getBirthDateString());
            preparedStatement.setString(4, pet.getChipNumber());
            preparedStatement.setDouble(5, ofNullable(pet.getWeight()).map(BigDecimal::doubleValue).orElse(null));
            preparedStatement.setInt(6, pet.getPetId());
            preparedStatement.executeUpdate();

        } catch (SQLException ex) {
            System.out.println("Valami baj van a userek kiolvasásakor \n" + ex);
        }
    }
}


package hu.vasvari.controller;

import hu.vasvari.model.Owner;
import hu.vasvari.model.Pet;
import hu.vasvari.model.Vaccination;
import hu.vasvari.repository.Repository;
import hu.vasvari.service.Service;
import hu.vasvari.util.ColumnName;
import hu.vasvari.util.ImgResource;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

import java.math.BigDecimal;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

import static hu.vasvari.util.ColumnName.COLOR;
import static hu.vasvari.util.ColumnName.KOTELEZO_KITOLTES;
import static hu.vasvari.util.ColumnName.KOTELEZO_VALASZTAS;
import static hu.vasvari.util.ColumnName.PET_CHIP_NUMBER;
import static hu.vasvari.util.ColumnName.PET_KIND;
import static hu.vasvari.util.ColumnName.PET_NAME;
import static hu.vasvari.util.ColumnName.VACC_DATE;
import static hu.vasvari.util.ColumnName.VACC_REASON;
import static hu.vasvari.util.ColumnName.VACC_VALID;
import static hu.vasvari.util.ImgResource.EXPORT_PIC;
import static hu.vasvari.util.ImgResource.LIST_PIC;
import static hu.vasvari.util.ImgResource.WARNING_PIC;
import static java.util.Objects.isNull;
import static org.apache.commons.lang3.StringUtils.isBlank;

@SuppressWarnings({"unchecked"})
public class ViewController implements Initializable {

    private final String MENU_TREE = "Műveletek";
    private final String MENU_NEW_CLIENT = "ÚJ páciens";
    private final String MENU_PETS = "Állatok";
    private final String MENU_VARNING = "Értesítendők";
    private final String MENU_EXPORT = "Exportálás";
    private final String MENU_EXIT = "Kilépés";

    private final ObservableList<Owner> ownerData = FXCollections.observableArrayList();

    @FXML
    private StackPane menuPane;
    @FXML
    private SplitPane mainSplitPane;
    @FXML
    private AnchorPane mainAnchor, newAnchorPane, petAnchorPane, vaAnchorPane;
    @FXML
    private Pane exportPane, addNewClientPane, felvitelTabPane, petsTabPene, vaccTabPene, addVaccPane, petsPane,
            oldVaccPane, warningPane;
    @FXML
    private TableView<Vaccination> vaccTable;
    @FXML
    private TableView petsTable;
    private TableView<ObservableList<Pet>> oldVaccTable;
    @FXML
    private TextField exportFileName, ownerClientID, ownerName, ownerMail, phonePrimary, phoneSecondary, petName,
            petKind, petType, petColor, petWeight, petChipNumber, newPetName, newPetKind, newPetType, newPetColor,
            newPetWeight, newPetChipNumber;
    @FXML
    private Button exportBtn, addNewPetBtn, newVaccBtn, vaccCancelBtn, saveVaccBtn, storeClientBtn;
    @FXML
    private ComboBox<Owner> ownerCombobox;
    @FXML
    private ComboBox<Pet> petsCombobox;
    @FXML
    private TabPane tabPane;
    @FXML
    private Tab newTab, petsTab, vaccTab;
    @FXML
    private Label exportRequireLabel, warningLabel;
    @FXML
    private DatePicker vaccDate, validDate, petBirthDate, newPetBirthDate;
    // private Exporter exporter;
    private List<Owner> owners;
    private Service service;
    private Owner selectedOwner;
    private Pet selectedPet;

    @FXML
    public void exportAction(ActionEvent event) {
        var fileName = exportFileName.getText().replaceAll("\\s+", "");
        if (isBlank(fileName)) {
            exportRequireLabel.setVisible(true);
            exportFileName.requestFocus();
            return;
        }
        owners.add(new Owner("k", "teszt", "mail", "7777", "66"));
        //  exporter.exportToExcel(fileName, owners);
        // export hívás
    }

    @FXML
    public void ownerComboAction(ActionEvent event) {
        selectedOwner = ((ComboBox<Owner>) event.getSource()).getValue();
        System.out.println("selected owner: " + selectedOwner);

        disableTabs(selectedOwner == null);
        initPetCombobox();
        refreshPetCombobox(selectedOwner);
        refreshPetTable(selectedOwner);
        refreshVaccTable(selectedPet);
    }

    private void disableTabs(boolean isSelected) {
        petsTab.setDisable(isSelected);
        vaccTab.setDisable(isSelected);
    }

    @FXML
    public void addNewPetAction(ActionEvent event) {
        if (isNull(selectedOwner)) {
            showWarningPane(KOTELEZO_VALASZTAS.getMsg());
            return;
        }
        if (isValidFill(requiredFieldsInAddPet())) {
            service.addPetToOwner(selectedOwner.getOwnerId(), addNewPet());
            clearAll();
            refreshPetTable(selectedOwner.getOwnerId());
        }
    }

    private boolean isValidFill(List<TextField> textfields) {
        boolean hasEmptyField = textfields.stream()
                .anyMatch(field -> isNull(field.getText()) || "".equals(field.getText().trim()));

        if (hasEmptyField) {
            showWarningPane(KOTELEZO_KITOLTES.getMsg());
            return false;
        }
        return true;
    }

    private void showWarningPane(String labelMsg) {
        mainSplitPane.setDisable(true);
        mainSplitPane.setOpacity(0.4);
        warningPane.setVisible(true);
        warningLabel.setText(labelMsg);
    }

    @FXML
    public void petsComboAction(ActionEvent event) {
        selectedPet = ((ComboBox<Pet>) event.getSource()).getValue();
        System.out.println("selected pet: " + selectedPet);
    }

    @FXML
    public void addNewVacc(ActionEvent event) {

    }

    @FXML
    public void vaccCancelAction(ActionEvent event) {

    }

    @FXML
    public void warningOk(ActionEvent event) {
        mainSplitPane.setDisable(false);
        mainSplitPane.setOpacity(1);
        warningPane.setVisible(false);
    }

    @FXML
    public void addVaccAction(ActionEvent event) {
    }

    @FXML
    public void storeNewClient(ActionEvent event) {
        if (isValidFill(requiredFieldsInNewClient())) {
            store();
        }
    }

    private void store() {
        service.addOwnerWithPet(createOwner(), createPet());
        clearAll();
    }

    private Pet createPet() {
        return new Pet(petName.getText(), petKind.getText(), petType.getText(), petColor.getText(),
                petBirthDate.getValue(), getIntFromStringDefaultNull(petWeight.getText()), petChipNumber.getText());
    }

    private Pet addNewPet() {
        return new Pet(newPetName.getText(), newPetKind.getText(), newPetType.getText(), newPetColor.getText(),
                newPetBirthDate.getValue(), getIntFromStringDefaultNull(newPetWeight.getText()),
                newPetChipNumber.getText());
    }

    private BigDecimal getIntFromStringDefaultNull(String str) {
        try {
            return BigDecimal.valueOf(Double.parseDouble(str));
        } catch (NumberFormatException ex) {
            // TODO: handle exception
        }
        return null;
    }

    private Owner createOwner() {
        return new Owner(ownerClientID.getText(), ownerName.getText(), ownerMail.getText(), phonePrimary.getText(),
                phoneSecondary.getText());
    }

    private void clearAll() {
        allTextFields().forEach(TextField::clear);
        getAllPicker().forEach(p -> p.setValue(null));
    }

    private List<TextField> requiredFieldsInNewClient() {
        return Arrays.asList(ownerClientID, ownerName, phonePrimary, petName, petKind, petChipNumber);
    }

    private List<TextField> requiredFieldsInAddPet() {
        return Arrays.asList(newPetName, newPetKind, newPetChipNumber);
    }

    private List<TextField> allTextFields() {
        return Arrays.asList(exportFileName, ownerClientID, ownerName, ownerMail, phonePrimary, phoneSecondary, petName,
                petKind, petType, petColor, petWeight, petChipNumber, newPetName, newPetKind, newPetType, newPetColor,
                newPetWeight, newPetChipNumber);
    }

    private List<DatePicker> getAllPicker() {
        return Arrays.asList(vaccDate, validDate, petBirthDate, newPetBirthDate);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initMenu();
        //   exporter = new Exporter("KINYIR");
        owners = new ArrayList<>();
        service = new Service(new Repository());

        disableTabs(true);
        initOwnerCombobox();
        initPetsTable();
        initVaccinesTable();
    }

    private void initMenu() {
        TreeItem<String> rootMenu = new TreeItem<>("Menü");
        TreeView<String> treeView = new TreeView<>(rootMenu);
        treeView.setShowRoot(false);

        TreeItem<String> treeMenu = new TreeItem<>(MENU_TREE);
        treeMenu.setExpanded(true);

        treeMenu.getChildren().addAll(createTreeItem(MENU_NEW_CLIENT, LIST_PIC), createTreeItem(MENU_PETS, LIST_PIC),
                createTreeItem(MENU_VARNING, WARNING_PIC), createTreeItem(MENU_EXPORT, EXPORT_PIC));

        rootMenu.getChildren().addAll(treeMenu, createTreeItem(MENU_EXIT));
        menuPane.getChildren().add(treeView);

        treeView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            String selectedMenu = newValue.getValue();
            if (isBlank(selectedMenu)) {
                return;
            }
            exportRequireLabel.setVisible(false);
            switch (selectedMenu) {
                case MENU_TREE:
                    newValue.setExpanded(true);
                    break;
                case MENU_NEW_CLIENT:
                    enableSelectedPane(addNewClientPane);
                    break;
                case MENU_EXPORT:
                    enableSelectedPane(exportPane);
                    break;
                case MENU_PETS:
                    enableSelectedPane(petsPane);
                    break;
                case MENU_VARNING:
                    enableSelectedPane(oldVaccPane);
                    break;
                case MENU_EXIT:
                    Platform.exit();
                    System.exit(0);
                    break;
            }
        });
    }

    private List<Pane> getAllPanes() {
        return Arrays.asList(exportPane, addNewClientPane, addVaccPane, petsPane, oldVaccPane);
    }

    private void enableSelectedPane(Pane selectedPane) {
        getAllPanes().forEach(pane -> pane.setVisible(false));
        selectedPane.setVisible(true);
    }

    private Node getIcon(ImgResource src) {
        return new ImageView(new Image(Objects.requireNonNull(getClass().getResource(src.getSrc())).toString()));
    }

    private TreeItem<String> createTreeItem(String label) {
        return createTreeItem(label, null);
    }

    private TreeItem<String> createTreeItem(String label, ImgResource imgSrc) {
        if (isNull(imgSrc)) {
            return new TreeItem<>(label);
        }
        return new TreeItem<>(label, getIcon(imgSrc));
    }

    private void initOwnerCombobox() {
        ownerCombobox = (ComboBox<Owner>) petsPane.lookup("#ownerCombobox");
        ownerCombobox.getItems().addAll(FXCollections.observableArrayList(service.findAllOwners()));
        ownerCombobox.setVisible(true);
        ownerCombobox.setVisibleRowCount(5);
    }

    private void initPetCombobox() {
        TabPane tabPane = (TabPane) petsPane.lookup("#tabPane");
        petsCombobox = (ComboBox<Pet>) tabPane.getTabs().get(2).getContent().lookup("#petsCombobox");

        petsCombobox.setVisible(true);
        petsCombobox.setVisibleRowCount(5);
    }

    public void refreshOwnerCombobox() {
        ownerCombobox.getItems().clear();
        ownerCombobox.getItems().addAll(FXCollections.observableArrayList(service.findAllOwners()));
    }

    public void refreshPetCombobox(Owner owner) {
        petsCombobox.getItems().clear();
        ObservableList<Pet> pets = FXCollections.observableArrayList(owner.getPets());
        petsCombobox.getItems().addAll(pets.isEmpty() ? service.findAllPetByOwner(owner.getOwnerId()): pets);
    }

    private void initPetsTable() {
        TableColumn<Pet, String> nameCol = createPetColumn(PET_NAME),
                kindCol = createPetColumn(PET_KIND),
                typeCol = createPetColumn(ColumnName.TYPE),
                colorCol = createPetColumn(COLOR),
                chipCol = createPetColumn(PET_CHIP_NUMBER);

        nameCol.setEditable(true);
        nameCol.setOnEditCommit(t -> {
            var actualPet = t.getTableView().getItems().get(t.getTablePosition().getRow());
            actualPet.setName(t.getNewValue());

            service.updatePet(actualPet);
        });

        petsTable.setPlaceholder(new Label("No rows to display"));
        petsTable.getColumns().addAll(nameCol, kindCol, typeCol, colorCol, chipCol);
        ownerData.addAll(service.findAllOwners());
    }

    private void refreshPetTable(Owner owner) {
        refreshPetTable(owner.getOwnerId());
    }

    private void refreshPetTable(int ownerId) {
        petsTable.setItems(FXCollections.observableArrayList(service.findAllPetByOwner(ownerId)));
    }

    private void initVaccinesTable() {
        vaccTable.setPlaceholder(new Label("No rows to display"));
        vaccTable.getColumns().addAll(createVaccColumn(VACC_DATE),
                createVaccColumn(VACC_VALID), createVaccColumn(VACC_REASON));
    }

    private void refreshVaccTable(Pet pet) {
        vaccTable.setItems(FXCollections.observableArrayList(service.findAllVaccByPet(pet)));
    }

    private TableColumn<Pet, String> createPetColumn(ColumnName columnName) {
        return createColumn(Pet.class, 90, columnName);
    }

    private <T> TableColumn<T, String> createColumn(Class<T> clazz, int minWidth, ColumnName columnName) {
        TableColumn<T, String> col = new TableColumn<>(columnName.getMsg());
        col.setMinWidth(minWidth);
        col.setCellFactory(TextFieldTableCell.forTableColumn());
        col.setCellValueFactory(new PropertyValueFactory<>(columnName.getProperty()));
        return col;
    }

    private TableColumn<Vaccination, String> createVaccColumn(ColumnName columnName) {
        return createColumn(Vaccination.class, 130, columnName);
    }

}



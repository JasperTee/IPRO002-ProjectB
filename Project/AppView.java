import javafx.scene.control.TextFormatter.Change;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;

import java.util.ArrayList;
import java.util.List;

public class AppView {
    AppController controller;
    AppModel model;
    Stage primaryStage;

    AppView(AppController controller, AppModel model, Stage primaryStage){
        this.controller = controller;
        this.model = model;
        this.primaryStage = primaryStage;
    }
    Scene welcomePage(){
        Label welcomeLabel = new Label("Welcome to Management Housing Project");
        Label usernameLabel = new Label("Username");
        Label passwordLabel = new Label("Password");

        TextField usernameTextField = new TextField();
        usernameTextField.setPromptText("Enter your username");
        TextField passwordTextField = new TextField();
        passwordTextField.setPromptText("Enter your password");

        Button signUpBtn = new Button("Create a new account");
        signUpBtn.setOnAction(event -> createSignUpWindow());
        Button submitBtn = new Button("Login");
        submitBtn.setOnAction(event -> {
            // catch user input
            String username = usernameTextField.getText().trim();
            String password = passwordTextField.getText().trim();
            if (!username.isEmpty() && !password.isEmpty()){
                // call controller to process the login request
                if (controller.loginRequest(username,password)){
                    primaryStage.setScene(homePageUser());
                } else {
                    createNotificationWindow("The username or password is incorrect!!!");
                }
            } else {
                createNotificationWindow("the username or password can not be empty!!!");
            }
        });

        HBox welcomeRow = new HBox(welcomeLabel);
        welcomeRow.setAlignment(Pos.TOP_CENTER);
        HBox usernameLoginRow = new HBox(6, usernameLabel, usernameTextField);
        usernameLoginRow.setAlignment(Pos.CENTER);
        HBox passwordLoginRow = new HBox(6, passwordLabel, passwordTextField);
        passwordLoginRow.setAlignment(Pos.CENTER);
        VBox buttonsRow = new VBox(10,signUpBtn);
        buttonsRow.setAlignment(Pos.CENTER);
        VBox userLoginRow = new VBox(5, usernameLoginRow,passwordLoginRow,submitBtn);
        userLoginRow.setAlignment(Pos.CENTER);

        VBox root = new VBox(50,welcomeRow,userLoginRow,buttonsRow);
        root.setAlignment(Pos.CENTER);
        return new Scene(root,600,600);
    }
    Scene homePageUser() {
        Label userProfile = new Label(model.getCurrentUser().toString());
        Label homePageLabel = new Label("HOME PAGE");

        Button accountBtn = new Button("Account");
        accountBtn.setOnAction(event -> primaryStage.setScene(accountUserPage()));
        Button mailBoxBtn = new Button("MailBox" + "(" + model.getCurrentUser().getMails().size() + ")");
        mailBoxBtn.setOnAction(event -> primaryStage.setScene(mailBoxPage()));
        Button marketBtn = new Button("Market");
        marketBtn.setOnAction(event -> primaryStage.setScene(marketPage()));
        Button managementBtn = new Button("Management");
        managementBtn.setOnAction(event -> {
            if (model.getCurrentUser() instanceof Agent) {
                primaryStage.setScene(managementAgentPage());
            } else if (model.getCurrentUser() instanceof Client) {
                primaryStage.setScene(managementClientPage());
            }
        });
        Button logOutBtn = new Button("Log out");
        // process log out request and call controller to inform model that current user is logging out
        logOutBtn.setOnAction(event -> {
            primaryStage.setScene(welcomePage());
            controller.logOutRequest();
        });

        VBox buttonsCol = new VBox(5, accountBtn,mailBoxBtn,managementBtn,marketBtn);
        VBox userCol = new VBox(5,userProfile,logOutBtn);
        HBox mainRow = new HBox(20, userCol,buttonsCol);
        mainRow.setAlignment(Pos.CENTER);
        VBox root = new VBox(5, homePageLabel, mainRow);
        root.setAlignment(Pos.CENTER);
        return new Scene(root,600,600);
    }
    Scene accountUserPage() {
        Label accountPageLabel = new Label("ACCOUNT PAGE");
        Label name = new Label();
        name.textProperty().bind(model.getCurrentUser().getPropertyName());
        Label nameLabel = new Label("Name: ");
        Label address = new Label();
        address.textProperty().bind(model.getCurrentUser().getPropertyAddress().asString());
        Label addressslabel = new Label("Address: ");
        Label phoneNumber = new Label();
        phoneNumber.textProperty().bind(model.getCurrentUser().getPropertyPhoneNumber());
        Label phoneNumberLabel = new Label("Phone Number: ");
        Label username = new Label();
        username.textProperty().bind(model.getCurrentUser().getPropertyUsername());
        Label usernameLabel = new Label("Username: ");
        Label password = new Label();
        password.setText("********");
        Label passwordLabel = new Label("Password: ");

        Button visiblePassBtn = new Button("Show");
        visiblePassBtn.setOnAction(event -> {
            if (visiblePassBtn.getText().equals("Show")){
                visiblePassBtn.setText("Hide");
                password.setText(model.getCurrentUser().getPassword());
            } else {
                visiblePassBtn.setText("Show");
                password.setText("********");
            }
        });
        Button editBtn = new Button("Edit Profile");
        editBtn.setOnAction(event -> createEditUserWindow());
        Button backBtn = new Button("Back");
        backBtn.setOnAction(event -> primaryStage.setScene(homePageUser()));

        HBox nameRow = new HBox(20, nameLabel,name);
        nameRow.setAlignment(Pos.CENTER);
        HBox addressRow = new HBox(20, addressslabel,address);
        addressRow.setAlignment(Pos.CENTER);
        HBox phoneNumberRow = new HBox(20, phoneNumberLabel,phoneNumber);
        phoneNumberRow.setAlignment(Pos.CENTER);
        HBox usernameRow = new HBox(20, usernameLabel,username);
        usernameRow.setAlignment(Pos.CENTER);
        HBox passwordRow = new HBox(20, passwordLabel,password, visiblePassBtn);
        passwordRow.setAlignment(Pos.CENTER);
        HBox buttonsRow = new HBox(40, editBtn,backBtn);
        buttonsRow.setAlignment(Pos.CENTER);

        VBox root = new VBox(10,accountPageLabel,nameRow,addressRow,phoneNumberRow,usernameRow,passwordRow,buttonsRow);
        root.setAlignment(Pos.CENTER);

        return new Scene(root,600,600);
    }
    Scene mailBoxPage(){
        Label mailboxLabel = new Label("MAIL BOX");

        TableView<Mail> mailTable = new TableView<>();
        TableColumn<Mail, String> senderCol = new TableColumn<>("Sender");
        senderCol.setCellValueFactory(cellData -> cellData.getValue().getSender());
        senderCol.setMinWidth(200);
        TableColumn<Mail,String> tittleCol = new TableColumn<>("Tittle");
        tittleCol.setCellValueFactory(cellData -> cellData.getValue().getTittle());
        tittleCol.setMinWidth(400);
        mailTable.setItems(model.getCurrentUser().getMails());
        mailTable.getColumns().addAll(tittleCol,senderCol);
        Button viewBtn = new Button("View Detail");
        viewBtn.setOnAction(event -> {
            int index = mailTable.getSelectionModel().getSelectedIndex();
            if (index != -1 ){
                createViewMailWindow(model.getCurrentUser().getMails().get(index));
            } else {
                createNotificationWindow("Please choose the mail that you want to view detail!");
            }
        });
        Button deleteBtn = new Button("Delete mail");
        deleteBtn.setOnAction(event -> {
            int index = mailTable.getSelectionModel().getSelectedIndex();
            if (index != - 1 ){
                // call controller to process removing this mail
                controller.removeMail(index);
            } else {
                createNotificationWindow("Please choose the mail that you want to remove!");
            }
        });
        Button  sendMailBtn = new Button("Send mail");
        sendMailBtn.setOnAction(event -> createSendEmailWindow());
        Button backBtn = new Button("Back");
        backBtn.setOnAction(event -> primaryStage.setScene(homePageUser()));

        HBox buttonsRow = new HBox(20, viewBtn,deleteBtn,sendMailBtn);
        buttonsRow.setAlignment(Pos.CENTER);
        VBox mainRow = new VBox(5, mailTable, buttonsRow);
        mainRow.setAlignment(Pos.CENTER);

        VBox root = new VBox(30, mailboxLabel,mainRow,backBtn);
        root.setAlignment(Pos.CENTER);
        return new Scene(root,600, 600);
    }
    Scene marketPage(){
        Label marketLabel = new Label("MARKET");

        ListView<Property> propertyListView = new ListView<>();

        propertyListView.setItems(Property.getPropertyObservableList());

        Button viewBtn = new Button("View Detail");
        viewBtn.setOnAction(event -> {
            int index = propertyListView.getSelectionModel().getSelectedIndex();
            if ( index != -1 ){
                createViewPropertyWindow(Property.getPropertyObservableList().get(index));
            } else {
                createNotificationWindow("Please choose the property that you want to view all of details");
            }
        });
        Button sortBtn = new Button("Sort properties");
        sortBtn.setOnAction(event -> createSortOptionsWindow());
        Button backBtn = new Button("Back");
        backBtn.setOnAction(event -> primaryStage.setScene(homePageUser()));

        VBox marketCol = new VBox(5,marketLabel,propertyListView);
        marketCol.setAlignment(Pos.CENTER);
        HBox functionRow = new HBox(30, viewBtn, sortBtn);
        functionRow.setAlignment(Pos.CENTER);

        VBox root = new VBox(25,marketCol, functionRow, backBtn);
        root.setAlignment(Pos.CENTER);

        return new Scene(root,500,300);

    }
    Scene managementAgentPage(){
        Agent agent = (Agent) model.getCurrentUser();
        Label inventoryLabel = new Label("Your Inventory");

        TableView<Property> inventoryTable = new TableView<>();
        inventoryTable.setItems(agent.getInventory());
        TableColumn<Property, String> propertyIDCol = new TableColumn<>("Property ID");
        propertyIDCol.setCellValueFactory(cellData -> cellData.getValue().getPropertyPropertyID());
        propertyIDCol.setMinWidth(250);
        TableColumn<Property, String> numberOfTenantsCol = new TableColumn<>("Number Of Tenants");
        numberOfTenantsCol.setCellValueFactory(cellData -> cellData.getValue().getPropertyCountTenants().asString());
        numberOfTenantsCol.setMinWidth(150);
        TableColumn<Property,String> rentPriceCol = new TableColumn<>("Rent Price (Per Week)");
        rentPriceCol.setCellValueFactory(cellData -> cellData.getValue().getPropertyPrice().asString());
        rentPriceCol.setMinWidth(200);
        inventoryTable.getColumns().addAll(propertyIDCol,numberOfTenantsCol,rentPriceCol);

        Button viewDetailBtn = new Button("View Detail Property");
        viewDetailBtn.setOnAction(event -> {
            int index = inventoryTable.getSelectionModel().getSelectedIndex();
            if (index != -1){
                createViewPropertyWindow(agent.getInventory().get(index));
            } else {
                createNotificationWindow("Please choose one of property to view detail");
            }
        });
        Button deletePropertyBtn = new Button("Delete Property");
        deletePropertyBtn.setOnAction(event -> {
            int index = inventoryTable.getSelectionModel().getSelectedIndex();
            if (index != -1){
                // call controller to process removing this property
                controller.removeProperty(index);
            } else {
                createNotificationWindow("Please choose one of property to delete");
            }
        });
        Button editPropertyInfoBtn = new Button("Edit Property");
        editPropertyInfoBtn.setOnAction(event -> {
            int index = inventoryTable.getSelectionModel().getSelectedIndex();
            if (index != -1){
                createEditPropertyWindow(agent.getInventory().get(inventoryTable.getSelectionModel().getSelectedIndex()));
            } else {
                createNotificationWindow("Please choose one of property to edit");
            }
        });
        Button addPropertyBtn = new Button("Add new Property");
        addPropertyBtn.setOnAction(event -> createAddNewPropertyWindow());
        Button backBtn = new Button("Back");
        backBtn.setOnAction(event -> primaryStage.setScene(homePageUser()));

        VBox inventoryArea = new VBox(inventoryLabel,inventoryTable);
        inventoryArea.setAlignment(Pos.CENTER);
        HBox functionRow = new HBox(10,addPropertyBtn,editPropertyInfoBtn,deletePropertyBtn);
        functionRow.setAlignment(Pos.CENTER);
        VBox functionArea = new VBox(5,functionRow,viewDetailBtn);
        functionArea.setAlignment(Pos.CENTER);

        VBox root = new VBox(20,inventoryArea,functionArea,backBtn);
        root.setAlignment(Pos.CENTER);
        return new Scene(root,600,400);
    }
    Scene managementClientPage(){
        Client client = (Client) model.getCurrentUser();
        Label leaseLabel = new Label("Your leaases");

        TableView<Property> leasesTable = new TableView<>();
        leasesTable.setItems(client.getLeases());
        TableColumn<Property, String> propertyIDCol = new TableColumn<>("Property ID");
        propertyIDCol.setCellValueFactory(cellData -> cellData.getValue().getPropertyPropertyID());
        propertyIDCol.setMinWidth(250);
        TableColumn<Property, String> ownerIDCol = new TableColumn<>("OwnerID");
        ownerIDCol.setCellValueFactory(cellData -> cellData.getValue().getOwner().getPropertyUserID());
        ownerIDCol.setMinWidth(150);
        TableColumn<Property,String> rentPriceCol = new TableColumn<>("Rent Price (Per Week)");
        rentPriceCol.setCellValueFactory(cellData -> cellData.getValue().getPropertyPrice().asString());
        rentPriceCol.setMinWidth(200);
        leasesTable.getColumns().addAll(propertyIDCol,ownerIDCol,rentPriceCol);

        Button makeContractBtn = new Button("Make Contract");
        makeContractBtn.setOnAction(event -> createMakeContractWindow());
        Button cancelContractBtn = new Button("Cancel Contract");
        cancelContractBtn.setOnAction(event -> {
            int index = leasesTable.getSelectionModel().getSelectedIndex();
            if (index != -1){
                // call controller for cancel request
                controller.cancelContractRequest(client.getLeases().get(index));
                createNotificationWindow("Cancelled Rental Agreement of this property!");
            } else {
                createNotificationWindow("Please choose one of property to cancel ");
            }
        });
        Button viewDetailBtn = new Button("View Detail of Property");
        viewDetailBtn.setOnAction(event -> {
            int index = leasesTable.getSelectionModel().getSelectedIndex();
            if (index != -1){
                createViewPropertyWindow(client.getLeases().get(index));
            } else {
                createNotificationWindow("Please choose one of property to view detail");
            }
        });
        Button backBtn = new Button("Back");
        backBtn.setOnAction(event -> primaryStage.setScene(homePageUser()));

        VBox tableArea = new VBox(5,leaseLabel,leasesTable);
        tableArea.setAlignment(Pos.CENTER);
        HBox functionRow = new HBox(20,makeContractBtn,cancelContractBtn,viewDetailBtn);
        functionRow.setAlignment(Pos.CENTER);

        VBox root = new VBox(20,tableArea,functionRow,backBtn);
        root.setAlignment(Pos.CENTER);

        return new Scene(root,600,400);
    }
    
    protected void createMakeContractWindow(){
        Stage stage = new Stage();
        stage.setTitle("Rent Contract");
        stage.initOwner(primaryStage);
        stage.initModality(Modality.APPLICATION_MODAL);

        Label propertyIDLabel = new Label("PropertyID: ");
        Label propertyInforLabel = new Label("Property' Detail");
        Label propertyInFor = new Label();

        TextField propertyIDTextField = new TextField();
        propertyIDTextField.textProperty().addListener(newText -> {
            Property property = controller.checkPropertyID(propertyIDTextField.getText().trim());
            if (property != null){
                propertyInFor.setText(property.detailInfo());
            } else {
                propertyInFor.setText("Please Enter a PropertyID to view details");
            }
        });

        Button signBtn = new Button("Sign");
        signBtn.setOnAction(event -> {
            String propertyID = propertyIDTextField.getText().trim();
            String error = controller.makeContractRequest(propertyID);
            if (error.isEmpty()){
                createNotificationWindow("Congratulations!!! You have just signed a new rental contract!");
                stage.close();
            } else {
                createNotificationWindow(error);
            }
        });
        Button closeBtn = new Button("Close");
        closeBtn.setOnAction(event -> stage.close());

        HBox propertyIDRow = new HBox(15, propertyIDLabel,propertyIDTextField);
        propertyIDRow.setAlignment(Pos.CENTER);
        VBox propertyInforArea = new VBox(5,propertyInforLabel,propertyInFor);
        propertyInforArea.setAlignment(Pos.CENTER);
        HBox buttonsRow = new HBox(15,signBtn,closeBtn);
        buttonsRow.setAlignment(Pos.CENTER);

        VBox root = new VBox(30,propertyIDRow,propertyInforArea,buttonsRow);
        root.setAlignment(Pos.CENTER);

        Scene scene = new Scene(root,500,450);

        stage.setScene(scene);
        stage.show();
    }
    protected void createEditPropertyWindow(Property property){
        Stage stage = new Stage();
        stage.setTitle("Edit Property Information");
        stage.initOwner(primaryStage);
        stage.initModality(Modality.APPLICATION_MODAL);

        Label sizeOfPropertyLabel= new Label("Size of Property: ");
        Label locationLabel = new Label("Location: ");
        Label numberOfBedroomsLabel = new Label("Number Of Bedrooms: ");
        Label numberOfBathroomsLabel = new Label("Number Of Bathrooms: ");
        Label numberOfKitchensLabel = new Label("Number Of Kitchens: ");
        Label priceLabel = new Label("Price: ");
        Label optionsForApartmentLabel = new Label("Options for Apartment");
        Label floorNumberLabel = new Label("Floor Number: ");
        Label roomNumebrLabel = new Label("Room Number: ");
        Label estraFeeLabel = new Label("Satra Fee: ");
        Label optionsForHouseLabel = new Label("Options For House");
        Label numberOfFloorLabel = new Label("Number Of Floors: ");
        Label gardenLabel = new Label("Garden: ");

        TextField sizeOfPropertyTextField = new TextField();
        configTextFieldForDoubles(sizeOfPropertyTextField);
        sizeOfPropertyTextField.setPromptText("Enter size of property");
        TextField locationTextField = new TextField();
        locationTextField.setPromptText("Enter with lowercase (EX: central,...)");
        locationTextField.setMinWidth(250);
        TextField numberOfBedroomsTextField = new TextField();
        configTextFieldForInts(numberOfBedroomsTextField);
        numberOfBedroomsTextField.setPromptText("Enter number of bedrooms");
        numberOfBedroomsTextField.setMinWidth(250);
        TextField numberOfBathroomsTextField = new TextField();
        configTextFieldForInts(numberOfBathroomsTextField);
        numberOfBathroomsTextField.setMinWidth(250);
        numberOfBathroomsTextField.setPromptText("Enter number of bathrooms");
        TextField numberOfKitchensTextField = new TextField();
        numberOfKitchensTextField.setMinWidth(250);
        configTextFieldForInts(numberOfKitchensTextField);
        numberOfKitchensTextField.setPromptText("Enter number of kitchens");
        TextField priceTextField = new TextField();
        configTextFieldForDoubles(priceTextField);
        priceTextField.setPromptText("Enter rent price");
        TextField floorNumberTextField = new TextField();
        floorNumberTextField.setMinWidth(250);
        configTextFieldForInts(floorNumberTextField);
        floorNumberTextField.setPromptText("Enter floor number");
        TextField roomNumberTextField = new TextField();
        configTextFieldForInts(roomNumberTextField);
        roomNumberTextField.setPromptText("Enter room number");
        TextField extraFeeTextField = new TextField();
        configTextFieldForDoubles(extraFeeTextField);
        extraFeeTextField.setPromptText("Enter extra fee");
        TextField numberOfFloorTextField = new TextField();
        configTextFieldForInts(numberOfFloorTextField);
        numberOfFloorTextField.setPromptText("Enter number of floors");

        ToggleGroup gardenToggleGroup = new ToggleGroup();

        RadioButton yesGardenBtn = new RadioButton("Yes");
        yesGardenBtn.setToggleGroup(gardenToggleGroup);
        RadioButton noGardenBtn = new RadioButton("No");
        noGardenBtn.setToggleGroup(gardenToggleGroup);

        Button submitBtn = new Button("Submit");
        submitBtn.setOnAction(event -> {
            String size = sizeOfPropertyTextField.getText().trim();
            String location = locationTextField.getText().trim();
            String numberOfBedrooms = numberOfBedroomsTextField.getText().trim();
            String numberOfBathrooms = numberOfBathroomsTextField.getText().trim();
            String numberOfKitchens = numberOfKitchensTextField.getText().trim();
            String rentPrice = priceTextField.getText().trim();
            String floorNumber = floorNumberTextField.getText().trim();
            String roomNumber = roomNumberTextField.getText().trim();
            String extraFee = extraFeeTextField.getText().trim();
            String numberOfFloors = numberOfFloorTextField.getText().trim();

            List<String> errors = new ArrayList<>();
            if (size.isEmpty() || location.isEmpty() || numberOfBedrooms.isEmpty() || numberOfBathrooms.isEmpty() ||
                    numberOfKitchens.isEmpty() || rentPrice.isEmpty()) {
                createNotificationWindow("Please fill in all required fields!");
            } else {
                if (property instanceof Apartment) {
                    if (floorNumber.isEmpty() || roomNumber.isEmpty() || extraFee.isEmpty()) {
                        createNotificationWindow("Please fill in all apartment-specific fields!");
                        return;
                    }
                    errors = controller.editPropertyRequest( property, size,  location, numberOfBedrooms, numberOfBathrooms,  numberOfKitchens,  rentPrice,  floorNumber, roomNumber,  extraFee,  numberOfFloors, true);
                } else if (property instanceof House) {
                    if (numberOfFloors.isEmpty() || !yesGardenBtn.isSelected() && !noGardenBtn.isSelected()) {
                        createNotificationWindow("Please fill in all house-specific fields!");
                        return;
                    }
                    errors = controller.editPropertyRequest(property, size,  location, numberOfBedrooms, numberOfBathrooms,  numberOfKitchens,  rentPrice,  floorNumber, roomNumber,  extraFee,  numberOfFloors, yesGardenBtn.isSelected());
                }
                if (errors.isEmpty()){
                    createNotificationWindow("Edited Property");
                    stage.close();
                } else {
                    String errorString = "";
                    for (String error : errors){
                        errorString += "\n" + error;
                    }
                    createNotificationWindow(errorString);
                }
            }
        });
        Button closeBtn = new Button("Close");
        closeBtn.setOnAction(event -> stage.close());

        HBox sizeOfProperty = new HBox(5,sizeOfPropertyLabel,sizeOfPropertyTextField);
        HBox location = new HBox(5,locationLabel,locationTextField);
        HBox numberOfBedrooms = new HBox(5,numberOfBedroomsLabel,numberOfBedroomsTextField);
        HBox numberOfBathrooms = new HBox(5,numberOfBathroomsLabel,numberOfBathroomsTextField);
        HBox numberOfKitchens = new HBox(5,numberOfKitchensLabel,numberOfKitchensTextField);
        HBox price = new HBox(5,priceLabel,priceTextField);
        HBox floorNumber = new HBox(5,floorNumberLabel,floorNumberTextField);
        HBox roomNumber = new HBox(5,roomNumebrLabel,roomNumberTextField);
        HBox extraFee = new HBox(5,estraFeeLabel,extraFeeTextField);
        HBox numberOfFloors = new HBox(5,numberOfFloorLabel,numberOfFloorTextField);
        HBox garden = new HBox(10, gardenLabel,yesGardenBtn,noGardenBtn);
        HBox buttonsRow = new HBox(20, submitBtn,closeBtn);
        buttonsRow.setAlignment(Pos.CENTER);
        VBox houseOptionsArea = new VBox(10, optionsForHouseLabel,numberOfFloors,garden);
        houseOptionsArea.setAlignment(Pos.CENTER_LEFT);
        VBox apartmentOptionsArea = new VBox(10,optionsForApartmentLabel,floorNumber,roomNumber,extraFee);
        apartmentOptionsArea.setAlignment(Pos.TOP_LEFT);
        VBox generalArea = new VBox(10,sizeOfProperty,location,numberOfBedrooms,numberOfBathrooms,numberOfKitchens,price);
        generalArea.setAlignment(Pos.CENTER_RIGHT);
        HBox optionsArea = new HBox();
        if (property instanceof House){
            optionsArea.getChildren().add(houseOptionsArea);
        } else {
            optionsArea.getChildren().add(apartmentOptionsArea);
        }
        optionsArea.setAlignment(Pos.CENTER);
        HBox inforArea = new HBox(10,generalArea,optionsArea);

        VBox root = new VBox(30, inforArea,buttonsRow);
        root.setAlignment(Pos.CENTER);

        Scene scene = new Scene(root,700,500);

        stage.setScene(scene);
        stage.show();
    }
    protected void createAddNewPropertyWindow(){
        Stage stage = new Stage();
        stage.setTitle("Add New Property");
        stage.initOwner(primaryStage);
        stage.initModality(Modality.APPLICATION_MODAL);

        Label propertyTypeLabel = new Label("Property Type: ");
        Label sizeOfPropertyLabel= new Label("Size of Property: ");
        Label locationLabel = new Label("Location: ");
        Label numberOfBedroomsLabel = new Label("Number Of Bedrooms: ");
        Label numberOfBathroomsLabel = new Label("Number Of Bathrooms: ");
        Label numberOfKitchensLabel = new Label("Number Of Kitchens: ");
        Label maximumOfTenantsLabel = new Label("Maximum Number Of Tenants: ");
        Label priceLabel = new Label("Price: ");
        Label optionsForApartmentLabel = new Label("Options for Apartment");
        Label floorNumberLabel = new Label("Floor Number: ");
        Label roomNumebrLabel = new Label("Room Number: ");
        Label estraFeeLabel = new Label("Satra Fee: ");
        Label optionsForHouseLabel = new Label("Options For House");
        Label numberOfFloorLabel = new Label("Number Of Floors: ");
        Label gardenLabel = new Label("Garden: ");

        TextField sizeOfPropertyTextField = new TextField();
        configTextFieldForDoubles(sizeOfPropertyTextField);
        sizeOfPropertyTextField.setPromptText("Enter size of property");
        TextField locationTextField = new TextField();
        locationTextField.setPromptText("Enter with lowercase (EX: central,...)");
        locationTextField.setMinWidth(250);
        TextField numberOfBedroomsTextField = new TextField();
        configTextFieldForInts(numberOfBedroomsTextField);
        numberOfBedroomsTextField.setPromptText("Enter number of bedrooms");
        numberOfBedroomsTextField.setMinWidth(250);
        TextField numberOfBathroomsTextField = new TextField();
        configTextFieldForInts(numberOfBathroomsTextField);
        numberOfBathroomsTextField.setMinWidth(250);
        numberOfBathroomsTextField.setPromptText("Enter number of bathrooms");
        TextField numberOfKitchensTextField = new TextField();
        numberOfKitchensTextField.setMinWidth(250);
        configTextFieldForInts(numberOfKitchensTextField);
        numberOfKitchensTextField.setPromptText("Enter number of kitchens");
        TextField maximumOfTenantsTextField = new TextField();
        maximumOfTenantsTextField.setMinWidth(250);
        configTextFieldForInts(maximumOfTenantsTextField);
        maximumOfTenantsTextField.setPromptText("Enter maximum number of tenants");
        TextField priceTextField = new TextField();
        configTextFieldForDoubles(priceTextField);
        priceTextField.setPromptText("Enter rent price");
        TextField floorNumberTextField = new TextField();
        floorNumberTextField.setMinWidth(250);
        configTextFieldForInts(floorNumberTextField);
        floorNumberTextField.setPromptText("Enter floor number");
        TextField roomNumberTextField = new TextField();
        configTextFieldForInts(roomNumberTextField);
        roomNumberTextField.setPromptText("Enter room number");
        TextField extraFeeTextField = new TextField();
        configTextFieldForDoubles(extraFeeTextField);
        extraFeeTextField.setPromptText("Enter extra fee");
        TextField numberOfFloorTextField = new TextField();
        configTextFieldForInts(numberOfFloorTextField);
        numberOfFloorTextField.setPromptText("Enter number of floors");

        ToggleGroup propertyTypeToggleGroup = new ToggleGroup();
        ToggleGroup gardenToggleGroup = new ToggleGroup();

        RadioButton houseTypeBtn = new RadioButton("House");
        houseTypeBtn.setToggleGroup(propertyTypeToggleGroup);
        RadioButton apartmentTypeBtn = new RadioButton("Apartment");
        apartmentTypeBtn.setToggleGroup(propertyTypeToggleGroup);
        RadioButton yesGardenBtn = new RadioButton("Yes");
        yesGardenBtn.setToggleGroup(gardenToggleGroup);
        RadioButton noGardenBtn = new RadioButton("No");
        noGardenBtn.setToggleGroup(gardenToggleGroup);

        Button submitBtn = new Button("Submit");
        submitBtn.setOnAction(event -> {
            if (!houseTypeBtn.isSelected() && !apartmentTypeBtn.isSelected()) {
                createNotificationWindow("Please select type of property!");
                return;
            }
            String size = sizeOfPropertyTextField.getText().trim();
            String location = locationTextField.getText().trim();
            String numberOfBedrooms = numberOfBedroomsTextField.getText().trim();
            String numberOfBathrooms = numberOfBathroomsTextField.getText().trim();
            String numberOfKitchens = numberOfKitchensTextField.getText().trim();
            String maximumTenants = maximumOfTenantsTextField.getText().trim();
            String rentPrice = priceTextField.getText().trim();
            String floorNumber = floorNumberTextField.getText().trim();
            String roomNumber = roomNumberTextField.getText().trim();
            String extraFee = extraFeeTextField.getText().trim();
            String numberOfFloors = numberOfFloorTextField.getText().trim();

            List<String> errors = new ArrayList<>();
            if (size.isEmpty() || location.isEmpty() || numberOfBedrooms.isEmpty() || numberOfBathrooms.isEmpty() ||
                    numberOfKitchens.isEmpty() || maximumTenants.isEmpty() || rentPrice.isEmpty()) {
                createNotificationWindow("Please fill in all required fields!");
            } else {
                if (apartmentTypeBtn.isSelected()) {
                    String propertyType = "Apartment";
                    if (floorNumber.isEmpty() || roomNumber.isEmpty() || extraFee.isEmpty()) {
                        createNotificationWindow("Please fill in all apartment-specific fields!");
                        return;
                    }
                    errors = controller.addPropertyRequest(propertyType, size,  location, numberOfBedrooms, numberOfBathrooms,  numberOfKitchens,  maximumTenants,  rentPrice,  floorNumber, roomNumber,  extraFee,  numberOfFloors, true);
                } else if (houseTypeBtn.isSelected()) {
                    String propertyType = "House";
                    if (numberOfFloors.isEmpty() || !yesGardenBtn.isSelected() && !noGardenBtn.isSelected()) {
                        createNotificationWindow("Please fill in all house-specific fields!");
                        return;
                    }
                    errors = controller.addPropertyRequest(propertyType, size,  location, numberOfBedrooms, numberOfBathrooms,  numberOfKitchens,  maximumTenants,  rentPrice,  floorNumber, roomNumber,  extraFee,  numberOfFloors, yesGardenBtn.isSelected());
                }
                if (errors.isEmpty()){
                    createNotificationWindow("Added Property");
                    stage.close();
                } else {
                    String errorString = "";
                    for (String error : errors){
                        errorString += "\n" + error;
                    }
                    createNotificationWindow(errorString);
                }
            }

        });
        Button closeBtn = new Button("Close");
        closeBtn.setOnAction(event -> stage.close());

        HBox propertyType = new HBox(2,propertyTypeLabel,houseTypeBtn,apartmentTypeBtn);
        HBox sizeOfProperty = new HBox(5,sizeOfPropertyLabel,sizeOfPropertyTextField);
        HBox location = new HBox(5,locationLabel,locationTextField);
        HBox numberOfBedrooms = new HBox(5,numberOfBedroomsLabel,numberOfBedroomsTextField);
        HBox numberOfBathrooms = new HBox(5,numberOfBathroomsLabel,numberOfBathroomsTextField);
        HBox numberOfKitchens = new HBox(5,numberOfKitchensLabel,numberOfKitchensTextField);
        HBox maximumNumberOfTenants = new HBox(5,maximumOfTenantsLabel,maximumOfTenantsTextField);
        HBox price = new HBox(5,priceLabel,priceTextField);
        HBox floorNumber = new HBox(5,floorNumberLabel,floorNumberTextField);
        HBox roomNumber = new HBox(5,roomNumebrLabel,roomNumberTextField);
        HBox extraFee = new HBox(5,estraFeeLabel,extraFeeTextField);
        HBox numberOfFloors = new HBox(5,numberOfFloorLabel,numberOfFloorTextField);
        HBox garden = new HBox(10, gardenLabel,yesGardenBtn,noGardenBtn);
        HBox buttonsRow = new HBox(20, submitBtn,closeBtn);
        buttonsRow.setAlignment(Pos.CENTER);
        VBox houseOptionsArea = new VBox(10, optionsForHouseLabel,numberOfFloors,garden);
        houseOptionsArea.setAlignment(Pos.CENTER_LEFT);
        VBox apartmentOptionsArea = new VBox(10,optionsForApartmentLabel,floorNumber,roomNumber,extraFee);
        apartmentOptionsArea.setAlignment(Pos.TOP_LEFT);
        VBox generalArea = new VBox(10,propertyType,sizeOfProperty,location,maximumNumberOfTenants,numberOfBedrooms,numberOfBathrooms,numberOfKitchens,price);
        generalArea.setAlignment(Pos.TOP_RIGHT);
        HBox optionsArea = new HBox(30,houseOptionsArea,apartmentOptionsArea);
        optionsArea.setAlignment(Pos.TOP_CENTER);
        VBox inforArea = new VBox(10,generalArea,optionsArea);

        VBox root = new VBox(30, inforArea,buttonsRow);
        root.setAlignment(Pos.CENTER);

        Scene scene = new Scene(root,700,500);

        stage.setScene(scene);
        stage.show();
    }
    protected void createSortOptionsWindow(){
        Stage stage = new Stage();
        stage.setTitle("Sort Options");
        stage.initOwner(primaryStage);
        stage.initModality(Modality.APPLICATION_MODAL);

        Label optionsLabel = new Label("Options");
        Label sortlabel = new Label("Sort");

        ToggleGroup sortOptionsToggleGroup = new ToggleGroup();
        ToggleGroup ascenOrDescenTogglegroup = new ToggleGroup();

        RadioButton sizeBtn = new RadioButton("Size of Properties");
        sizeBtn.setToggleGroup(sortOptionsToggleGroup);
        RadioButton bedRoomsBtn = new RadioButton("Number Of Bedrooms");
        bedRoomsBtn.setToggleGroup(sortOptionsToggleGroup);
        RadioButton bathRoomsBtn = new RadioButton("Number Of Bathrooms");
        bathRoomsBtn.setToggleGroup(sortOptionsToggleGroup);
        RadioButton kitchensBtn = new RadioButton("Number Of Kitchens");
        kitchensBtn.setToggleGroup(sortOptionsToggleGroup);
        RadioButton priceBtn = new RadioButton("Rent Price");
        priceBtn.setToggleGroup(sortOptionsToggleGroup);

        RadioButton ascendingBtn = new RadioButton("Ascending");
        ascendingBtn.setToggleGroup(ascenOrDescenTogglegroup);
        RadioButton descendingBtn = new RadioButton("descending");
        descendingBtn.setToggleGroup(ascenOrDescenTogglegroup);

        Button applyBtn = new Button("Apply");
        applyBtn.setOnAction(event -> {
            if (ascendingBtn.isSelected() || descendingBtn.isSelected()){
                if (sizeBtn.isSelected()) {
                    controller.sortPropertiesRequest("Size", ascendingBtn.isSelected());
                    stage.close();
                } else if (bedRoomsBtn.isSelected()){
                    controller.sortPropertiesRequest("Bedrooms", ascendingBtn.isSelected());
                    stage.close();
                } else if (bathRoomsBtn.isSelected()){
                    controller.sortPropertiesRequest("Bathrooms", ascendingBtn.isSelected());
                    stage.close();
                } else if (kitchensBtn.isSelected()){
                    controller.sortPropertiesRequest("Kitchens", ascendingBtn.isSelected());
                    stage.close();
                } else if (priceBtn.isSelected()){
                    controller.sortPropertiesRequest("Price", ascendingBtn.isSelected());
                    stage.close();
                } else {
                    createNotificationWindow("Please choose one of these options to sort properties");
                }
            } else {
                createNotificationWindow("Please choose options to show how properties are sorted (Ascending or Descending)");
            }
        });
        Button closeBtn = new Button("Close");
        closeBtn.setOnAction(event -> stage.close());

        HBox sortRow = new HBox(10, ascendingBtn,descendingBtn);
        sortRow.setAlignment(Pos.CENTER);
        VBox options = new VBox(5,sizeBtn,bedRoomsBtn,bathRoomsBtn,kitchensBtn,priceBtn);
        options.setAlignment(Pos.CENTER);
        VBox optionsArea = new VBox(5, optionsLabel,options);
        optionsArea.setAlignment(Pos.CENTER);
        VBox sortArea = new VBox(5,sortlabel,sortRow);
        sortArea.setAlignment(Pos.CENTER);
        HBox mainArea = new HBox(10, optionsArea,sortArea);
        mainArea.setAlignment(Pos.CENTER);
        HBox buttonsRow = new HBox(30,applyBtn,closeBtn);
        buttonsRow.setAlignment(Pos.CENTER);

        VBox root = new VBox(30,mainArea,buttonsRow);
        root.setAlignment(Pos.CENTER);

        Scene scene = new Scene(root, 350,300);

        stage.setScene(scene);
        stage.show();

    }
    protected void createEditUserWindow(){
        Stage stage = new Stage();
        stage.setTitle("Edit User Information");
        stage.initOwner(primaryStage);
        stage.initModality(Modality.APPLICATION_MODAL);

        Label nameLabel = new Label("Name");
        Label addressLabel = new Label("Address (Areas in Sydney)");
        Label phoneNumberLabel = new Label("Phone Number");
        Label usernameLabel = new Label("Username");
        Label passwordLabel = new Label("Password");
        Label confirmPasswordLabel = new Label("Confirm password");
        Label roleLabel = new Label("Your are:");

        TextField nameTextField = new TextField();
        nameTextField.setPromptText("Type in your name (Ex: John, Emily,..)");
        TextField addressTextField = new TextField();
        addressTextField.setPromptText("Please type in with lowercase (Ex: central, st peters,...");
        TextField phoneNumberTextField = new TextField();
        phoneNumberTextField.setPromptText("Please type in a string of numbers (Ex: 04xxxxxxxx)");
        configTextFieldForInts(phoneNumberTextField);
        TextField usernameTextField = new TextField();
        usernameTextField.setPromptText("Ex: jt253, john123,...");
        TextField passwordtextField = new TextField();
        TextField confirmPasswordTextField = new TextField();

        Button submitBtn = new Button("Submit");
        submitBtn.setOnAction(event -> {
            String name = nameTextField.getText().trim();
            String address = addressTextField.getText().trim();
            String phoneNumber = phoneNumberTextField.getText().trim();
            String username = usernameTextField.getText().trim();
            String password = passwordtextField.getText().trim();
            String confirmPassword = confirmPasswordTextField.getText().trim();
            List<String> errors = controller.editInforRequest(name,address,phoneNumber,username,password,confirmPassword);
            if (errors.isEmpty()){
                createNotificationWindow("Completed edit!");
                stage.close();
            } else {
                String errorString = "";
                for (String error : errors){
                    errorString += "\n" + error;
                }
                createNotificationWindow(errorString);
            }
        });
        Button cancelBtn = new Button("Cancel");
        cancelBtn.setOnAction(event -> stage.close());

        VBox setName = new VBox(5, nameLabel, nameTextField);
        setName.setAlignment(Pos.CENTER_LEFT);
        VBox setAddress = new VBox(5, addressLabel,addressTextField);
        setAddress.setAlignment(Pos.CENTER_LEFT);
        VBox setPhoneNumber = new VBox(5,phoneNumberLabel,phoneNumberTextField);
        setPhoneNumber.setAlignment(Pos.CENTER_LEFT);
        VBox setUsername = new VBox(5, usernameLabel, usernameTextField);
        setUsername.setAlignment(Pos.CENTER_LEFT);
        VBox setPassword = new VBox(5, passwordLabel, passwordtextField);
        setPassword.setAlignment(Pos.CENTER_LEFT);
        VBox setConfirmPassword = new VBox(5, confirmPasswordLabel, confirmPasswordTextField);
        setConfirmPassword.setAlignment(Pos.CENTER_LEFT);
        HBox buttonsRow = new HBox(10, submitBtn,cancelBtn);
        buttonsRow.setAlignment(Pos.CENTER);

        VBox root = new VBox(25, setName, setAddress, setPhoneNumber, setUsername, setPassword, setConfirmPassword, buttonsRow);

        Scene scene = new Scene(root,500,600);

        stage.setScene(scene);
        stage.show();
    }
    protected void createNotificationWindow(String announcement){
        Stage stage = new Stage();
        stage.setTitle("Notification");
        stage.initOwner(primaryStage);

        Label annoucementLabel = new Label(announcement);

        Button closeBtn = new Button("Close");
        closeBtn.setOnAction(e -> stage.close());

        VBox root = new VBox(20, annoucementLabel, closeBtn);
        root.setAlignment(Pos.CENTER);

        Scene scene = new Scene(root,400,400);
        stage.setScene(scene);
        stage.show();

    }
    protected void createSignUpWindow(){
        Stage stage = new Stage();
        stage.setTitle("Sign Up");
        stage.initOwner(primaryStage);
        stage.initModality(Modality.APPLICATION_MODAL);

        Label nameLabel = new Label("Name");
        Label addressLabel = new Label("Address (Areas in Sydney)");
        Label phoneNumberLabel = new Label("Phone Number");
        Label usernameLabel = new Label("Username");
        Label passwordLabel = new Label("Password");
        Label confirmPasswordLabel = new Label("Confirm password");
        Label roleLabel = new Label("Your are:");

        TextField nameTextField = new TextField();
        nameTextField.setPromptText("Type in your name (Ex: John, Emily,..)");
        TextField addressTextField = new TextField();
        addressTextField.setPromptText("Please type in with lowercase (Ex: central, st peters,...");
        TextField phoneNumberTextField = new TextField();
        phoneNumberTextField.setPromptText("Please type in a string of numbers (Ex: 04xxxxxxxx)");
        configTextFieldForInts(phoneNumberTextField);
        TextField usernameTextField = new TextField();
        usernameTextField.setPromptText("Ex: jt253, john123,...");
        TextField passwordtextField = new TextField();
        TextField confirmPasswordTextField = new TextField();

        ToggleGroup roletoggleGroup = new ToggleGroup();
        RadioButton agentToggleGroupBtn = new RadioButton("Agent");
        agentToggleGroupBtn.setToggleGroup(roletoggleGroup);
        RadioButton clientToggleGroupBtn = new RadioButton("Client");
        clientToggleGroupBtn.setToggleGroup(roletoggleGroup);

        Button submitBtn = new Button("Submit");
        submitBtn.setOnAction(event -> {
            String name = nameTextField.getText().trim();
            String address = addressTextField.getText().trim();
            String phoneNumber = phoneNumberTextField.getText().trim();
            String username = usernameTextField.getText().trim();
            String password = passwordtextField.getText().trim();
            String confirmPassword = confirmPasswordTextField.getText().trim();
            String role;

            if (name.isEmpty() || address.isEmpty() || phoneNumber.isEmpty() || username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()){
                createNotificationWindow("Please enter all of the information");
            }
            if (agentToggleGroupBtn.isSelected()){
                role = "Agent";
            } else if (clientToggleGroupBtn.isSelected()) {
                role = "Client";
            } else {
                role = null;
                createNotificationWindow("Please choose your role");
            }
            if (role != null) {
                List<String> errors = controller.signUpRequest(name,address,phoneNumber,username,password,confirmPassword,role);
                if (errors.isEmpty()){
                    createNotificationWindow("Your Account has been created!\nThanks for choosing to use our services");
                    stage.close();
                } else {
                    String errorString = "";
                    for (String error : errors){
                        errorString += "\n" + error;
                    }
                    createNotificationWindow(errorString);
                }
            }
        });
        Button cancelBtn = new Button("Cancel");
        cancelBtn.setOnAction(event -> stage.close());

        VBox setName = new VBox(5, nameLabel, nameTextField);
        setName.setAlignment(Pos.CENTER_LEFT);
        VBox setAddress = new VBox(5, addressLabel,addressTextField);
        setAddress.setAlignment(Pos.CENTER_LEFT);
        VBox setPhoneNumber = new VBox(5,phoneNumberLabel,phoneNumberTextField);
        setPhoneNumber.setAlignment(Pos.CENTER_LEFT);
        VBox setUsername = new VBox(5, usernameLabel, usernameTextField);
        setUsername.setAlignment(Pos.CENTER_LEFT);
        VBox setPassword = new VBox(5, passwordLabel, passwordtextField);
        setPassword.setAlignment(Pos.CENTER_LEFT);
        VBox setConfirmPassword = new VBox(5, confirmPasswordLabel, confirmPasswordTextField);
        setConfirmPassword.setAlignment(Pos.CENTER_LEFT);
        HBox roleBtns = new HBox(40, agentToggleGroupBtn,clientToggleGroupBtn);
        roleBtns.setAlignment(Pos.CENTER);
        VBox setRole = new VBox(5,roleLabel,roleBtns);
        setRole.setAlignment(Pos.CENTER);
        HBox buttonsRow = new HBox(10, submitBtn,cancelBtn);
        buttonsRow.setAlignment(Pos.CENTER);

        VBox root = new VBox(25, setName, setAddress, setPhoneNumber, setUsername, setPassword, setConfirmPassword, setRole, buttonsRow);

        Scene scene = new Scene(root,500,600);

        stage.setScene(scene);
        stage.show();
    }
    protected void createViewMailWindow(Mail mail){
        Stage stage = new Stage();
        stage.setTitle("View Detail Mail");
        stage.initOwner(primaryStage);

        Label fromLabel = new Label("From: " + mail.getSender().get());
        fromLabel.setAlignment(Pos.CENTER_RIGHT);
        Label toLabel = new Label("To: " + mail.getRecipient().get());
        toLabel.setAlignment(Pos.CENTER_RIGHT);
        Label tittleLabel = new Label("Tittle: " + mail.getTittle().get());
        tittleLabel.setAlignment(Pos.CENTER_RIGHT);
        Label contentLabel = new Label("Content:\n" + mail.getContent().get());
        contentLabel.setAlignment(Pos.CENTER);

        Button closeBtn = new Button("Close");
        closeBtn.setOnAction(event -> stage.close());

        VBox headRow = new VBox(5, fromLabel,toLabel,tittleLabel);

        VBox root = new VBox(30,headRow,contentLabel,closeBtn);

        Scene scene = new Scene(root, 450,300);
        stage.setScene(scene);
        stage.show();
    }
    protected void createSendEmailWindow() {
        Stage stage = new Stage();
        stage.setTitle("New Mail");
        stage.initOwner(primaryStage);
        stage.initModality(Modality.APPLICATION_MODAL);

        Label fromLabel = new Label("From: " + model.getCurrentUser().getUserID() + " (Me)");
        Label toLabel = new Label("To: ");
        Label tittleLabel = new Label("Tittle: ");
        Label contentLabel = new Label("Content: ");

        TextField toTextField = new TextField();
        toTextField.setPromptText("Type in the ID of recipient");
        TextField tittleTextField = new TextField();
        tittleTextField.setPromptText("Enter short tittle");
        TextArea contentTextArea = new TextArea();
        contentTextArea.setPromptText("Enter your content");

        Button submitBtn = new Button("Submit");
        submitBtn.setOnAction(event -> {
            String to = toTextField.getText().trim();
            String tittle = tittleTextField.getText().trim();
            String content = contentTextArea.getText().trim();

            List<String> errors = controller.sendMailRequest(to, tittle, content);
            if (errors.isEmpty()) {
                createNotificationWindow("Sent Successfully!!!");
                stage.close();
            } else {
                String errorString = "";
                for (String error : errors) {
                    errorString += "\n" + error;
                }
                createNotificationWindow(errorString);
            }
        });
        Button closeBtn = new Button("Close");
        closeBtn.setOnAction(event -> stage.close());

        HBox toRow = new HBox(10, toLabel, toTextField);
        HBox tittleRow = new HBox(10, tittleLabel, tittleTextField);
        HBox buttonsRow = new HBox(10, submitBtn, closeBtn);
        buttonsRow.setAlignment(Pos.CENTER);
        VBox contentCol = new VBox(5, contentLabel, contentTextArea);
        VBox headRow = new VBox(10, fromLabel, toRow, tittleRow);


        VBox root = new VBox(20, headRow, contentCol, buttonsRow);
        Scene scene = new Scene(root, 500, 300);
        stage.setScene(scene);
        stage.show();
    }
    protected void createViewPropertyWindow(Property property){
        Stage stage = new Stage();
        stage.setTitle("View Detail Property");
        stage.initOwner(primaryStage);

        Label detaillabel = new Label(property.detailInfo());

        Button closeBtn = new Button("Close");
        closeBtn.setOnAction(event -> stage.close());

        VBox root = new VBox(30,detaillabel,closeBtn);
        root.setAlignment(Pos.CENTER);
        Scene scene = new Scene(root,400,350);
        stage.setScene(scene);
        stage.show();
    }
    
    protected void configTextFieldForDoubles(TextField field) {
        field.setTextFormatter(new TextFormatter<Integer>((Change c) -> {
            if (c.getControlNewText().matches("(\\d+.?\\d*)?")) {
                return c;
            }
            return null;
        }));
    }
    protected void configTextFieldForInts(TextField field) {
        field.setTextFormatter(new TextFormatter<Integer>((Change c) -> {
            if (c.getControlNewText().matches("\\d*")) {
                return c;
            }
            return null;
        }));
    }
}

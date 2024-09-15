import java.util.*;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

enum Address {
    TOWN_HALL("Town Hall"),
    WYNYARD("Wynyard"),
    CIRCULAR_QUAY("Circular Quay"),
    ST_JAMES("St James"),
    MUSEUM("Museum"),
    CENTRAL("Central"),
    REDFERN("Redfern"),
    NEWTOWN("Newtown"),
    STANMORE("Stanmore"),
    PETERSHAM("Petersham"),
    LEWISHAM("Lewisham"),
    SUMMER_HILL("Summer Hill"),
    ASHFIELD("Ashfield"),
    CROYDON("Croydon"),
    BURWOOD("Burwood"),
    STRATHFIELD("Strathfield"),
    NOT_PREFER("Not Prefer"),
    PARRAMATTA("Parramatta"),
    WESTMEAD("Westmead"),
    AUBURN("Auburn"),
    GRANVILLE("Granville"),
    MERRYLANDS("Merrylands"),
    LIDCOMBE("Lidcombe"),
    HOMEBUSH("Homebush"),
    RHODES("Rhodes"),
    CONCORD("Concord"),
    DRUMMOYNE("Drummoyne"),
    ROZELLE("Rozelle"),
    BALMAIN("Balmain"),
    GLEBE("Glebe"),
    ULTIMO("Ultimo"),
    SURRY_HILLS("Surry Hills"),
    DARLINGHURST("Darlinghurst"),
    PADDINGTON("Paddington"),
    BONDI("Bondi"),
    COOGEE("Coogee"),
    MAROUBRA("Maroubra"),
    RANDWICK("Randwick"),
    KENSINGTON("Kensington"),
    MASCOT("Mascot"),
    ROSEBERY("Rosebery"),
    ZETLAND("Zetland"),
    WATERLOO("Waterloo");

    private final String displayName;

    Address(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}

public class AppModel {
    private User currentUser;
    private Map<String, Address> area;

    protected AppModel() {
        area = new HashMap<>();

        List<User> listUsers = new ArrayList<>(Arrays.asList(
                // Agents
                new Agent("Cô Chủ Nhà", Address.CENTRAL, "0452471326", "a", "a"),
                new Agent("Adam West", Address.TOWN_HALL, "0412345671", "adamwest", "adampass123"),
                new Agent("Betty Young", Address.CIRCULAR_QUAY, "0412345672", "bettyyoung", "bettypass789"),
                new Agent("Carl Zane", Address.WYNYARD, "0412345673", "carlzane", "carlpass456"),
                new Agent("Diana Brown", Address.ST_JAMES, "0412345674", "dianabrown", "dianapass123"),
                new Agent("Evan Clark", Address.MUSEUM, "0412345675", "evanclark", "evanpass456"),
                new Agent("Fiona Davis", Address.CENTRAL, "0412345676", "fionadavis", "fionapass123"),
                new Agent("George Evans", Address.REDFERN, "0412345677", "georgeevans", "georgepass456"),
                new Agent("Hannah Fox", Address.NEWTOWN, "0412345678", "hannahfox", "hannahpass789"),
                new Agent("Ian Green", Address.STANMORE, "0412345679", "iangreen", "iangreen123"),
                new Agent("Jane Hall", Address.PETERSHAM, "0412345680", "janehall", "janepass456"),
                new Agent("Kyle Iron", Address.LEWISHAM, "0412345681", "kyleiron", "kylepass123"),
                // Real Estate Clients
                new Client("Anh Thuê Trọ", Address.CENTRAL, "0452471325", "b", "b"),
                new Client("Kathy Lewis", Address.LEWISHAM, "0412345691", "kathylewis", "kathypass123"),
                new Client("Larry Martin", Address.SUMMER_HILL, "0412345692", "larrymartin", "larrypass789"),
                new Client("Mona Nelson", Address.ASHFIELD, "0412345693", "monanelson", "monapass456"),
                new Client("Nina O'Brien", Address.CROYDON, "0412345694", "ninaobrien", "ninapass123"),
                new Client("Oscar Price", Address.BURWOOD, "0412345695", "oscarprice", "oscarpass456"),
                new Client("Paul Quinn", Address.STRATHFIELD, "0412345696", "paulquinn", "paulpass123"),
                new Client("Quincy Roberts", Address.NOT_PREFER, "0412345697", "quincyroberts", "quincy123"),
                new Client("Rachel Smith", Address.TOWN_HALL, "0412345698", "rachelsmith", "rachelpass456"),
                new Client("Steve Thomas", Address.CIRCULAR_QUAY, "0412345699", "stevethomas", "stevepass789"),
                new Client("Harry Phung", Address.CENTRAL, "0412345700", "harry", "123456789"),
                new Client("Tina Upton", Address.WYNYARD, "0412345701", "tinaupton", "tinapass456")
        ));
        List<Property> listProperties = new ArrayList<>(Arrays.asList(
                new House(300.5, Address.ASHFIELD, 3, 2, 2, 3, 1, true, 950),
                new Apartment(300.5, Address.ASHFIELD, 2, 2, 2, 3, 12, 304, 50, 920),
                new House(450.0, Address.BURWOOD, 4, 3, 3, 4, 2, true, 1800),
                new Apartment(375.0, Address.CROYDON, 3, 2, 1, 2, 1, 23, 30, 1650),
                new House(320.0, Address.STRATHFIELD, 2, 1, 1, 2, 1, false, 1400),
                new Apartment(500.0, Address.NEWTOWN, 5, 4, 3, 4, 3, 123, 60, 1900),
                new House(285.0, Address.LEWISHAM, 2, 1, 1, 1, 0, false, 900),
                new Apartment(330.0, Address.SUMMER_HILL, 2, 2, 2, 3, 1, 356, 45, 1200),
                new House(460.0, Address.STANMORE, 5, 3, 2, 4, 2, false, 1750),
                new Apartment(410.0, Address.PETERSHAM, 3, 2, 2, 2, 20, 234, 56, 1600),
                new House(385.0, Address.CENTRAL, 5, 3, 1, 3, 1, false, 1950),
                new Apartment(470.0, Address.MUSEUM, 4, 3, 2, 3, 34, 563, 67, 1850)
        ));
        area.put("town hall", Address.TOWN_HALL);
        area.put("wynyard", Address.WYNYARD);
        area.put("circular quay", Address.CIRCULAR_QUAY);
        area.put("st james", Address.ST_JAMES);
        area.put("museum", Address.MUSEUM);
        area.put("central", Address.CENTRAL);
        area.put("redfern", Address.REDFERN);
        area.put("newtown", Address.NEWTOWN);
        area.put("stanmore", Address.STANMORE);
        area.put("petersham", Address.PETERSHAM);
        area.put("lewisham", Address.LEWISHAM);
        area.put("summer hill", Address.SUMMER_HILL);
        area.put("ashfield", Address.ASHFIELD);
        area.put("croydon", Address.CROYDON);
        area.put("burwood", Address.BURWOOD);
        area.put("strathfield", Address.STRATHFIELD);
        area.put("parramatta", Address.PARRAMATTA);
        area.put("westmead", Address.WESTMEAD);
        area.put("auburn", Address.AUBURN);
        area.put("granville", Address.GRANVILLE);
        area.put("merrylands", Address.MERRYLANDS);
        area.put("lidcombe", Address.LIDCOMBE);
        area.put("homebush", Address.HOMEBUSH);
        area.put("rhodes", Address.RHODES);
        area.put("concord", Address.CONCORD);
        area.put("drummoyne", Address.DRUMMOYNE);
        area.put("rozelle", Address.ROZELLE);
        area.put("balmain", Address.BALMAIN);
        area.put("glebe", Address.GLEBE);
        area.put("ultimo", Address.ULTIMO);
        area.put("surry hills", Address.SURRY_HILLS);
        area.put("darlinghurst", Address.DARLINGHURST);
        area.put("paddington", Address.PADDINGTON);
        area.put("bondi", Address.BONDI);
        area.put("coogee", Address.COOGEE);
        area.put("maroubra", Address.MAROUBRA);
        area.put("randwick", Address.RANDWICK);
        area.put("kensington", Address.KENSINGTON);
        area.put("mascot", Address.MASCOT);
        area.put("rosebery", Address.ROSEBERY);
        area.put("zetland", Address.ZETLAND);
        area.put("waterloo", Address.WATERLOO);
        area.put("not prefer", Address.NOT_PREFER);

        for (int i = 0; i < listProperties.size() && i < listUsers.size(); i++) {
            if (listUsers.get(i) instanceof Agent) {
                Agent user = (Agent) listUsers.get(i);
                listProperties.get(i).setOwner(user);
                user.getInventory().add(listProperties.get(i));
            }
        }
    }

    protected Map<String, Address> getArea() {
        return area;
    }
    protected User getCurrentUser() {
        return currentUser;
    }
    // welcomePage
    // check input from controller with existed database and send the result to the controller
    protected boolean login(String username, String password) {
        for (User user : User.getUsersList().values()) {
            if (username.equals(user.getUsername())) {
                if (password.equals(user.getPassword())) {
                    currentUser = user;
                    return true;
                }
            }
        }
        return false;
    }
    //check input from controller and send the result is list of string which is errors when model try to sign up this information
    protected List<String> signUp(String name, String address, int phoneNumber, String username, String password, String confirmPassword, String role) {
        List<String> errors = new ArrayList<>();
        if (checkPhoneNumber(phoneNumber).equals("Valid") && checkAddress(address) && checkUsername(username) && checkPassword(password, confirmPassword)) {
            if (role.equals("Agent")) {
                new Agent(name, area.get(address), "0" + phoneNumber, username, password);
            } else {
                new Agent(name, area.get(address), "0" + phoneNumber, username, password);
            }
            return errors;
        }
        if (!checkPhoneNumber(phoneNumber).equals("Valid")) {
            errors.add("Phone Number is " + checkPhoneNumber(phoneNumber) + "!!!");
        }
        if (!checkAddress(address)) {
            errors.add("INVALID Address!!!");
        }
        if (!checkUsername(username)) {
            errors.add("This username has been EXISTED!!!");
        }
        if (!checkPassword(password, confirmPassword)) {
            errors.add("Password does not match");
        }
        return errors;
    }
    //set currentUser attribute is null when controller requires log out
    protected void logOut() {
        currentUser = null;
    }
    //process information from controller and send list of String if there are some errors happens to controller in order to print out on view
    protected List<String> editCurrentUserInfor(String name, String address, int phoneNumber, String username, String password, String confirmPassword) {
        List<String> errors = new ArrayList<>();
        if (checkPhoneNumber(phoneNumber).equals("Valid") && checkAddress(address) && checkUsername(username) && checkPassword(password, confirmPassword)) {
            if (!name.equals(currentUser.getName())) {
                currentUser.setName(name);
            }
            if (area.get(address) != currentUser.getAddress()) {
                currentUser.setAddress(area.get(address));
            }
            if (!("0" + phoneNumber).equals(currentUser.getPhoneNumber())) {
                currentUser.setPhoneNumber("0" + phoneNumber);
            }
            if (!username.equals(currentUser.getUsername())) {
                currentUser.setUsername(username);
            }
            if (!password.equals(currentUser.getPassword())) {
                currentUser.setPassword(password);
            }
        }
        if (!checkPhoneNumber(phoneNumber).equals("Valid")) {
            errors.add("Phone Number is " + checkPhoneNumber(phoneNumber) + "!!!");
        }
        if (!checkAddress(address)) {
            errors.add("INVALID Address!!!");
        }
        if (!checkUsername(username)) {
            errors.add("This username has been EXISTED!!!");
        }
        if (!checkPassword(password, confirmPassword)) {
            errors.add("Password does not match");
        }
        return errors;
    }
    //process information from controller and send list of String if there are some errors happens to controller in order to print out on view
    protected List<String> sendMail(String recipient, String tittle, String content) {
        List<String> errors = new ArrayList<>();
        if (checkUserID(recipient) && tittle.length() < 30) {
            User.getUsersList().get(recipient).getMails().add(new Mail(tittle, currentUser.getUserID(), recipient, content));
        }
        if (!checkUserID(recipient)) {
            errors.add("The ID of recipient is not found!!!");
        }
        if (tittle.length() >= 30) {
            errors.add("The tittle is over than 30 characters!!!");
        }
        return errors;
    }
    //sort properties followed by user options
    protected void sortProperties(String options, boolean ascending){
        if (options.equals("Size")){
            Property.sortPropertiesBySize(ascending);
        } else if (options.equals("Bedrooms")){
            Property.sortPropertiesByBedrooms(ascending);
        } else if (options.equals("Bathrooms")){
            Property.sortPropertiesByBathrooms(ascending);
        } else if (options.equals("Kitchens")){
            Property.sortPropertiesByKitchens(ascending);
        } else if (options.equals("Price")){
            Property.sortPropertiesByPrice(ascending);
        }
    }
    //process information from controller and send list of String if there are some errors happens to controller in order to print out on view
    protected List<String> addProperty(String propertyType,double size, String location,int bedrooms,int bathrooms, int kitchens, int maximumNumberOfTenants, double rentPrice, int floorNumber,int roomNumber, double extraFee, int numberOfFloors, boolean garden ){
        List<String> errors = new ArrayList<>();
        if (size <= 0){
            errors.add("Size can not be negative or zero");
        }
        boolean isFound = false;
        for (String area : area.keySet()){
            if (location.equals(area)){
                isFound = true;
                break;
            }
        }
        if (!isFound) {
            errors.add("Does not found this location!");
        }
        if (bedrooms < 0 || bathrooms < 0 || kitchens < 0){
            errors.add("Rooms can not be negative!");
        } else if (bathrooms + bedrooms + kitchens == 0){
            errors.add("This Property does not have any rooms!");
        }
        if (maximumNumberOfTenants <= 0){
            errors.add("Maximum number of tenants can not be negative or zero");
        }
        if (rentPrice < 0){
            errors.add("Rent price can not be negative");
        }
        if (extraFee < 0){
            errors.add("Extra fee can not be negative");
        }
        if (numberOfFloors < 0){
            errors.add("Number of floors can not be negative");
        }
        if (floorNumber < 0 || roomNumber < 0){
            errors.add("Floor and Room number can not be negative");
        }
        if (errors.isEmpty()){
            ((Agent) currentUser).addPropertyToInventory(propertyType, size, area.get(location), bedrooms, bathrooms,  kitchens,  maximumNumberOfTenants,  rentPrice,  floorNumber, roomNumber,  extraFee,  numberOfFloors,  garden );
        }
        return errors;
    }
    //process information from controller and send list of String if there are some errors happens to controller in order to print out on view
    protected List<String> editProperty(Property property,double size, String location,int bedrooms,int bathrooms, int kitchens, double rentPrice, int floorNumber,int roomNumber, double extraFee, int numberOfFloors, boolean garden){
        List<String> errors = new ArrayList<>();
        if (size <= 0){
            errors.add("Size can not be negative or zero");
        }
        boolean isFound = false;
        for (String area : area.keySet()){
            if (location.equals(area)){
                isFound = true;
                break;
            }
        }
        if (!isFound) {
            errors.add("Does not found this location!");
        }
        if (bedrooms < 0 || bathrooms < 0 || kitchens < 0){
            errors.add("Rooms can not be negative!");
        } else if (bathrooms + bedrooms + kitchens == 0){
            errors.add("This Property does not have any rooms!");
        }
        if (rentPrice < 0){
            errors.add("Rent price can not be negative");
        }
        if (extraFee < 0){
            errors.add("Extra fee can not be negative");
        }
        if (numberOfFloors < 0){
            errors.add("Number of floors can not be negative");
        }
        if (floorNumber < 0 || roomNumber < 0){
            errors.add("Floor and Room number can not be negative");
        }
        if (errors.isEmpty()){
            property.setSizeOfProperty(size);
            property.setAddress(area.get(location));
            property.setPropertyNumberOfBedRooms(bedrooms);
            property.setPropertyNumberOfBathRooms(bathrooms);
            property.setPropertyNumberOfKitchens(kitchens);
            property.setPropertyPrice(rentPrice);
            if (property instanceof Apartment){
                ((Apartment) property).setFloorNumber(floorNumber);
                ((Apartment) property).setRoomNumber(roomNumber);
                ((Apartment) property).setExtraFee(extraFee);
            } else if (property instanceof House){
                ((House) property).setNumberOfFloor(numberOfFloors);
                ((House) property).setGarden(garden);
            }
        }
        return errors;
    }
    //process information from controller and send list of String if there are some errors happens to controller in order to print out on view
    protected String makeContract(String id){
        boolean isFound = false;
        Property target = null;
        for (Property property : Property.getPropertyObservableList()){
            if (id.equals(property.getPropertyID())){
                target = property;
                isFound = true;
                break;
            }
        }
        if (isFound){
            boolean hasProperty = false;
            for (Property lease : ((Client) currentUser).getLeases()){
                if (lease.getPropertyID().equals(target.getPropertyID())){
                    hasProperty = true;
                    break;
                }
            }
            if (!hasProperty){
                if (target.getCountTenants() >= target.getNumberOfTenants()){
                    return "This Property is full!";
                } else {
                    ((Client) currentUser).makeContract(target);
                    target.getOwner().getMails().add(new Mail("The property " + target.getPropertyID() + " has a new tenant!","System",target.getOwner().getUserID(),"We are pleased to inform you that a new tenant has been successfully\n registered for your property "+ target.getPropertyID() +"\n. Below are the details of the new tenant:"+ currentUser.toString()+ "\nnKind regards,\nThe Customer Service Team"));
                    target.setCountTenants(target.getCountTenants() + 1);
                    target.getTenants().add((Client) currentUser);
                    currentUser.getMails().add(new Mail("Welcome to Your New Home!","System",target.getOwner().getUserID(),"Congratulations! We are excited to inform you that your application to\n rent the property "+ target.getPropertyID() +" has been successfully processed.\nKind regards,\nThe Customer Service Team"));
                    return "";
                }
            } else {
                return "You have already rented this property!";
            }
        }
        return "Does not found this PropertyID";
    }
    //process information from controller and send list of String if there are some errors happens to controller in order to print out on view
    protected void cancelContract(Property property){
        Client client = (Client) currentUser;
        client.getLeases().remove(property);
        property.getTenants().remove(client);
        property.setCountTenants(property.getCountTenants() - 1);
        currentUser.getMails().add(new Mail("Cancellation of Your Rental Agreement","System",client.getUserID(),"We have received your request to cancel the rental agreement for the property with ID: "+  property.getPropertyID() +"\nYour cancellation has been successfully processed.\n" + "We are sorry to see you go and hope to assist you in the future.\n" + "Kind regards,\n" + "The Customer Service Team"));
        property.getOwner().getMails().add(new Mail("Tenant Cancellation of Rental Agreement","System",client.getUserID(),"We would like to inform you that the tenant "+client.getUserID() +" has requested to cancel the rental agreement.\n" + "We understand this may be an inconvenience, and we are here to help you find a new tenant as quickly as possible.\n" + "If you have any questions or need further assistance, please feel free to contact our customer service team.\n" + "Kind regards,\n" + "The Customer Service Team"));
    }

    //are used in main method to check information of users, current user and properties
    protected boolean checkAddress(String address) {
        for (String s : area.keySet()) {
            if (address.equals(s)) {
                return true;
            }
        }
        return false;
    }
    protected String checkPhoneNumber(int phoneNumber) {
        if (phoneNumber > 400000000 && phoneNumber < 500000000) {
            String phoneNumberString = "0" + phoneNumber;
            for (User user : User.getUsersList().values()) {
                if ( currentUser != null) {
                    if (phoneNumberString.equals(currentUser.getPhoneNumber())) {
                        return "Valid";
                    }
                }
                if (phoneNumberString.equals(user.getPhoneNumber())) {
                    return "Existed";
                }
            }
            return "Valid";
        }
        return "Invalid";
    }
    protected boolean checkUsername(String username) {
        for (User user : User.getUsersList().values()) {
            if (currentUser != null) {
                if (username.equals(currentUser.getUsername())) {
                    return true;
                }
            }
            if (username.equals(user.getUsername())){
                return false;
            }
        }
        return true;
    }
    protected boolean checkPassword(String password, String confirmPassword) {
        return password.equals(confirmPassword);
    }
    protected boolean checkUserID(String id) {
        for (String userID : User.getUsersList().keySet()) {
            if (id.equals(userID)) {
                return true;
            }
        }
        return false;
    }
    protected Property checkPropertyID(String id){
        for (Property property : Property.getPropertyObservableList()){
            if (id.equals(property.getPropertyID())){
                return property;
            }
        }
        return null;
    }
}
abstract class Property {
    private User owner;
    private List<Client> tenants;
    private SimpleIntegerProperty numberOfTenants;
    private SimpleIntegerProperty countTenants;
    private SimpleDoubleProperty price;
    private final SimpleStringProperty propertyID;
    private SimpleDoubleProperty sizeOfProperty;
    private SimpleObjectProperty<Address> address;
    private SimpleIntegerProperty numberOfBedRooms;
    private SimpleIntegerProperty numberOfKitchens;
    private SimpleIntegerProperty numberOfBathRooms;

    private static ObservableList<Property> propertyObservableList = FXCollections.observableArrayList();

    protected static final Comparator<Property> comparatorSize = Comparator.comparing(Property::getSizeOfProperty);
    protected static final Comparator<Property> comparatorBedrooms = Comparator.comparing(Property::getNumberOfBedRooms);
    protected static final Comparator<Property> comparatorBathrooms = Comparator.comparing(Property::getNumberOfBathRooms);
    protected static final Comparator<Property> comparatorKitchens = Comparator.comparing(Property::getNumberOfKitchens);
    protected static final Comparator<Property> comparatorPrice = Comparator.comparing(Property::getPrice);

    protected Property(User owner, double sizeOfProperty, Address address, int numberOfTenants, int numberOfBedRooms, int numberOfKitchens, int numberOfBathRooms, double price) {
        this.owner = owner;
        this.sizeOfProperty = new SimpleDoubleProperty(sizeOfProperty);
        this.address = new SimpleObjectProperty<>(address);
        this.tenants = new ArrayList<>();
        this.countTenants = new SimpleIntegerProperty(0);
        this.numberOfTenants = new SimpleIntegerProperty(numberOfTenants);
        this.numberOfBedRooms = new SimpleIntegerProperty(numberOfBedRooms);
        this.numberOfKitchens = new SimpleIntegerProperty(numberOfKitchens);
        this.numberOfBathRooms = new SimpleIntegerProperty(numberOfBathRooms);
        this.price = new SimpleDoubleProperty(price);
        this.propertyID = new SimpleStringProperty(setPropertyID());
        Property.propertyObservableList.add(this);
    }
    protected Property(double sizeOfProperty, Address address, int numberOfTenants, int numberOfBedRooms, int numberOfKitchens, int numberOfBathRooms, double price) {
        this.sizeOfProperty = new SimpleDoubleProperty(sizeOfProperty);
        this.address = new SimpleObjectProperty<>(address);
        this.countTenants = new SimpleIntegerProperty(0);
        this.tenants = new ArrayList<>();
        this.numberOfTenants = new SimpleIntegerProperty(numberOfTenants);
        this.numberOfBedRooms = new SimpleIntegerProperty(numberOfBedRooms);
        this.numberOfKitchens = new SimpleIntegerProperty(numberOfKitchens);
        this.numberOfBathRooms = new SimpleIntegerProperty(numberOfBathRooms);
        this.price = new SimpleDoubleProperty(price);
        this.propertyID = new SimpleStringProperty(setPropertyID());
        Property.propertyObservableList.add(this);
    }

    protected SimpleDoubleProperty getPropertySizeOfProperty() {
        return sizeOfProperty;
    }
    protected SimpleObjectProperty<Address> getPropertyAddress() {
        return address;
    }
    protected User getOwner() {
        return owner;
    }
    protected SimpleIntegerProperty getPropertyNumberOfBathRooms() {
        return numberOfBathRooms;
    }
    protected SimpleIntegerProperty getPropertyNumberOfBedRooms() {
        return numberOfBedRooms;
    }
    protected SimpleIntegerProperty getPropertyNumberOfKitchens() {
        return numberOfKitchens;
    }
    protected SimpleIntegerProperty getPropertyNumberOfTenants() {
        return numberOfTenants;
    }
    protected SimpleDoubleProperty getPropertyPrice() {
        return price;
    }
    protected SimpleStringProperty getPropertyPropertyID() {
        return propertyID;
    }
    protected SimpleIntegerProperty getPropertyCountTenants() {
        return countTenants;
    }

    protected void setSizeOfProperty(double size) {
        this.sizeOfProperty.set(size);
    }
    protected void setAddress(Address address) {
        this.address = new SimpleObjectProperty<>(address);
    }
    protected void setOwner(User owner) {
        this.owner = owner;
    }
    protected void setPropertyNumberOfBathRooms(int numberOfBathRooms) {
        this.numberOfBathRooms.set(numberOfBathRooms);
    }
    protected void setPropertyNumberOfBedRooms(int numberOfBedRooms) {
        this.numberOfBedRooms.set(numberOfBedRooms);
    }
    protected void setPropertyNumberOfKitchens(int numberOfKitchens) {
        this.numberOfKitchens.set(numberOfKitchens);
    }
    protected void setPropertyPrice(double price) {
        this.price.set(price);
    }
    protected void setCountTenants(int countTenants) {
        this.countTenants.set(countTenants);
    }
    protected String setPropertyID() {
        String idsetup;
        while (true) {
            Random rand = new Random();
            int num1 = rand.nextInt(10);
            int num2 = rand.nextInt(10);
            int num3 = rand.nextInt(10);
            int num4 = rand.nextInt(10);
            idsetup = "PR" + num1 + num2 + num3 + num4;
            boolean isFound = false;
            for (Property property : Property.propertyObservableList){
                if (idsetup.equals(property.getPropertyID())){
                    isFound = true;
                    break;
                }
            }
            if (isFound){
                continue;
            }
            break;
        }
        return idsetup;
    }

    protected double getSizeOfProperty() {
        return getPropertySizeOfProperty().get();
    }
    protected String getPropertyID() {
        return propertyID.get();
    }
    protected int getNumberOfBathRooms() {
        return getPropertyNumberOfBathRooms().get();
    }
    protected int getNumberOfBedRooms() {
        return getPropertyNumberOfBedRooms().get();
    }
    protected int getNumberOfKitchens() {
        return getPropertyNumberOfKitchens().get();
    }
    protected int getNumberOfTenants() {
        return getPropertyNumberOfTenants().get();
    }
    protected double getPrice() {
        return getPropertyPrice().get();
    }
    protected Address getAddress() {
        return getPropertyAddress().get();
    }
    protected int getCountTenants() {
        return getPropertyCountTenants().get();
    }
    protected List<Client> getTenants() {
        return tenants;
    }

    protected static void sortPropertiesBySize(boolean ascending){
        Collections.sort(getPropertyObservableList(),Property.comparatorSize);
        if (!ascending) {
            Collections.reverse(getPropertyObservableList());
        }
    }
    protected static void sortPropertiesByBedrooms(boolean ascending){
        Collections.sort(getPropertyObservableList(),Property.comparatorBedrooms);
        if (!ascending) {
            Collections.reverse(getPropertyObservableList());
        }
    }
    protected static void sortPropertiesByBathrooms(boolean ascending){
        Collections.sort(getPropertyObservableList(),Property.comparatorBathrooms);
        if (!ascending) {
            Collections.reverse(getPropertyObservableList());
        }
    }
    protected static void sortPropertiesByKitchens(boolean ascending){
        Collections.sort(getPropertyObservableList(),Property.comparatorKitchens);
        if (!ascending) {
            Collections.reverse(getPropertyObservableList());
        }
    }
    protected static void sortPropertiesByPrice(boolean ascending){
        Collections.sort(getPropertyObservableList(),Property.comparatorPrice);
        if (!ascending) {
            Collections.reverse(getPropertyObservableList());
        }
    }
    protected static ObservableList<Property> getPropertyObservableList() {
        return propertyObservableList;
    }

    abstract String detailInfo();

    @Override
    public String toString() {
        return "" + getSizeOfProperty() + "m^2     " + "$" + getPrice() + "/week";
    }
}
class Apartment extends Property {
    private SimpleIntegerProperty floorNumber;
    private SimpleIntegerProperty roomNumber;
    private SimpleDoubleProperty extraFee; // Strata levies

    protected Apartment(User owner, double sizeOfProperty, Address address,int numberOfTenants, int numberOfBedRooms, int numberOfKitchens, int numberOfBathRooms,int floorNumber, int roomNumber, double extraFee, double price){
        super(owner, sizeOfProperty, address,numberOfTenants , numberOfBedRooms, numberOfKitchens, numberOfBathRooms,price);
        this.floorNumber = new SimpleIntegerProperty(floorNumber);
        this.roomNumber = new SimpleIntegerProperty(roomNumber);
        this.extraFee = new SimpleDoubleProperty(extraFee);
    }
    protected Apartment(double sizeOfProperty, Address address,int numberOfTenants, int numberOfBedRooms, int numberOfKitchens, int numberOfBathRooms, int floorNumber, int roomNumber, double extraFee, double price){
        super(sizeOfProperty, address,numberOfTenants , numberOfBedRooms, numberOfKitchens, numberOfBathRooms, price);
        this.floorNumber = new SimpleIntegerProperty(floorNumber);
        this.roomNumber = new SimpleIntegerProperty(roomNumber);
        this.extraFee = new SimpleDoubleProperty(extraFee);
    }

    SimpleIntegerProperty getPropertyFloorNumber(){
        return floorNumber;
    }
    SimpleIntegerProperty getPropertyRoomNumber(){
        return roomNumber;
    }
    SimpleDoubleProperty getPropertyExtraFee(){
        return extraFee;
    }

    protected int getFloorNumber() {
        return getPropertyFloorNumber().get();
    }
    protected int getRoomNumber(){
        return getPropertyRoomNumber().get();
    }
    protected double getExtraFee(){
        return getPropertyExtraFee().get();
    }

    protected void setFloorNumber(int floorNumber){
        this.floorNumber = new SimpleIntegerProperty(floorNumber);
    }
    protected void setRoomNumber(int roomNumber){
        this.roomNumber = new SimpleIntegerProperty(roomNumber);
    }
    protected void setExtraFee(double extraFee){
        this.extraFee = new SimpleDoubleProperty(extraFee);
    }

    @Override
    String detailInfo() {
        return "Property Type:     Apartment (" + getPropertyID() + ")\nOwner: " + getOwner().getName() + " (" + getOwner().getUserID() + ")" +  "\nSize:     " + getSizeOfProperty() + "m^2" + "\nArea:     " + getAddress() + "\nTenants:     " + getCountTenants() + "/" + getNumberOfTenants() + " ppl\nNumber Of Bedrooms:     " + getNumberOfBedRooms() + " rooms\nNumber Of Bathrooms:     " + getNumberOfBathRooms() + " rooms\nNumber Of Kitchens:     " + getNumberOfKitchens() + " rooms\nFloor Number:     " + getFloorNumber() + "\nRoom Number:     " + getRoomNumber() + "\nExtra Fee:     $" + getExtraFee() + "\nPrice:     $" + getPrice() + "/week";
    }
    @Override
    public String toString() {
        return "Apartment     " + super.toString();
    }
}
class House extends Property {
    private SimpleIntegerProperty numberOfFloor;
    private SimpleBooleanProperty garden;

    protected House(User owner, double sizeOfProperty, Address address, int numberOfTenants, int numberOfBedRooms, int numberOfKitchens, int numberOfBathRooms, int numberOfFloor, boolean garden, double price) {
        super(owner, sizeOfProperty, address, numberOfTenants, numberOfBedRooms, numberOfKitchens, numberOfBathRooms, price);
        this.numberOfFloor = new SimpleIntegerProperty(numberOfFloor);
        this.garden = new SimpleBooleanProperty(garden);
    }
    protected House(double sizeOfProperty, Address address, int numberOfTenants, int numberOfBedRooms, int numberOfKitchens, int numberOfBathRooms, int numberOfFloor, boolean garden, double price) {
        super(sizeOfProperty, address, numberOfTenants, numberOfBedRooms, numberOfKitchens, numberOfBathRooms, price);
        this.numberOfFloor = new SimpleIntegerProperty(numberOfFloor);
        this.garden = new SimpleBooleanProperty(garden);
    }

    protected SimpleIntegerProperty getPropertyNumberOfFloor(){
        return numberOfFloor;
    }
    protected SimpleBooleanProperty getPropertyGarden(){
        return garden;
    }

    protected int getNumberOfFloor(){
        return getPropertyNumberOfFloor().get();
    }
    protected boolean getGarden(){
        return getPropertyGarden().get();
    }

    protected void setNumberOfFloor(int numberOfFloor){
        this.numberOfFloor = new SimpleIntegerProperty(numberOfFloor);
    }
    protected void setGarden(boolean garden){
        this.garden = new SimpleBooleanProperty(garden);
    }

    @Override
    String detailInfo() {
        return "Property Type:     House (" + getPropertyID() + ")" + "\nOwner:     " + getOwner().getName() + " (" + getOwner().getUserID() + ")" +  "\nSize:     " + getSizeOfProperty() + "m^2" + "\nArea:     " + getAddress() + "\nTenants:     " + getCountTenants() + "/" + getNumberOfTenants() + " ppl\nNumber Of Bedrooms:     " + getNumberOfBedRooms() + " rooms\nNumber Of Bathrooms:     " + getNumberOfBathRooms() + " rooms\nNumber Of Kitchens:     " + getNumberOfKitchens() + " rooms\nNumber Of Floor:     " + getNumberOfFloor() + " floors\nGarden:     " + getGarden()+ "\nPrice:     $" + getPrice() + "/week";
    }
    @Override
    public String toString() {
        return "House     " + super.toString();
    }
}
abstract class User {
    private SimpleStringProperty name;
    private SimpleObjectProperty<Address> address;
    private SimpleStringProperty phoneNumber;
    private final SimpleStringProperty userID;
    private ObservableList<Mail> mails;
    private SimpleStringProperty username;
    private SimpleStringProperty password;

    private static HashMap<String, User> usersList = new HashMap<>();

    User(String name, Address address, String phoneNumber, String username, String password){
        this.name = new SimpleStringProperty(name);
        this.address = new SimpleObjectProperty<>(address);
        this.phoneNumber = new SimpleStringProperty(phoneNumber);
        this.username = new SimpleStringProperty(username);
        this.password = new SimpleStringProperty(password);
        this.userID = new SimpleStringProperty(setUserID());
        this.mails = FXCollections.observableArrayList();
        User.usersList.put(getUserID(), this);

        mails.add(new Mail("Welcome to our project!", "System", this.getUserID(), "Hi " + this.name.get() + ",\n\nThank you for using our application. We sincerely hope it enhances your \nexperience and provides valuable support in your endeavors.\n\nKind Regards,\nProject Development Team"));
    }

    protected SimpleStringProperty getPropertyName() {
        return name;
    }
    protected SimpleObjectProperty<Address> getPropertyAddress() {
        return address;
    }
    protected SimpleStringProperty getPropertyPhoneNumber() {
        return phoneNumber;
    }
    protected SimpleStringProperty getPropertyUserID() {
        return userID;
    }
    protected SimpleStringProperty getPropertyUsername() {
        return username;
    }
    protected SimpleStringProperty getPropertyPassword() {
        return password;
    }

    protected void setName(String name) {
        this.name.set(name);
    }
    protected void setAddress(Address address) {
        this.address.set(address);
    }
    protected void setPhoneNumber(String phoneNumber) {
        this.phoneNumber.set(phoneNumber);
    }
    protected void setUsername(String username) {
        this.username.set(username);
    }
    protected void setPassword(String password) {
        this.password.set(password);
    }

    protected String getName() {
        return getPropertyName().get();
    }
    protected Address getAddress() {
        return getPropertyAddress().get();
    }
    protected String getPhoneNumber() {
        return getPropertyPhoneNumber().get();
    }
    protected String getUserID() {
        return getPropertyUserID().get();
    }
    protected ObservableList<Mail> getMails() {
        return mails;
    }
    protected String getUsername() {
        return getPropertyUsername().get();
    }
    protected String getPassword() {
        return getPropertyPassword().get();
    }
    protected static HashMap<String, User> getUsersList() {
        return usersList;
    }

    protected void removeMail(int index){
        mails.remove(index);
    }

    abstract String setUserID();

    public String toString(){
        return "\nName: " + getName() + "\nAddress: " + getAddress() + "\nPhone Number: " + getPhoneNumber() + "\nUserID: " + getUserID();
    }
}
class Agent extends User {
    private ObservableList<Property> inventory;

    protected Agent(String name, Address address, String phoneNumber, String username, String password) {
        super(name, address, phoneNumber, username, password);
        inventory = FXCollections.observableArrayList();
    }

    protected ObservableList<Property> getInventory() {
        return inventory;
    }

    protected void removeProperty(int index) {
        Property.getPropertyObservableList().remove(inventory.get(index));
        for (Client client : inventory.get(index).getTenants()){
            client.getLeases().remove(inventory.get(index));
            client.getMails().add(new Mail("Cancellation of Your Rental Agreement","System",client.getUserID(),"We have received owner request to cancel the\n rental agreement for the property with ID: "+  inventory.get(index).getPropertyID() +"\nYour cancellation has been successfully processed.\n" + "We are sorry to see you go and hope to assist you in the future.\n" + "Kind regards,\n" + "The Customer Service Team"));
        }
        this.getMails().add(new Mail("Remove your property out of market","System",this.getUserID(),"We have received your request to remove the property with ID: "+  inventory.get(index).getPropertyID() +"\nThe request has been successfully processed.\n" + "Kind regards,\n" + "The Customer Service Team"));
        inventory.remove(index);

    }
    protected void addPropertyToInventory(String propertyType, double size, Address location, int bedrooms, int bathrooms, int kitchens, int maximumNumberOfTenants, double rentPrice, int floorNumber, int roomNumber, double extraFee, int numberOfFloors, boolean garden) {
        if (propertyType.equals("House")) {
            inventory.add(new House(this, size, location, maximumNumberOfTenants, bedrooms, kitchens, bathrooms, numberOfFloors, garden, rentPrice));
        } else if (propertyType.equals("Apartment")) {
            inventory.add(new Apartment(this, size, location, maximumNumberOfTenants, bedrooms, kitchens, bathrooms, floorNumber, roomNumber, extraFee, rentPrice));
        }
    }

    @Override
    protected String setUserID() {
        String idsetup;
        while (true) {
            Random rand = new Random();
            int num1 = rand.nextInt(10);
            int num2 = rand.nextInt(10);
            int num3 = rand.nextInt(10);
            int num4 = rand.nextInt(10);
            idsetup = "AG" + num1 + num2 + num3 + num4;
            boolean isFound = false;
            for (String id : User.getUsersList().keySet()) {
                if (idsetup.equals(id)) {
                    isFound = true;
                    break;
                }
            }
            if (isFound) {
                continue;
            }
            break;
        }
        return idsetup;
    }
}
class Client extends User {
    private ObservableList<Property> leases;

    protected Client(String name, Address address, String phoneNumber, String username, String password) {
        super(name, address, phoneNumber, username, password);
        leases = FXCollections.observableArrayList();
    }

    protected  ObservableList<Property> getLeases() {
        return leases;
    }
    protected void makeContract(Property property){
        leases.add(property);
    }

    @Override
    protected String setUserID() {
        String idsetup;
        while (true) {
            Random rand = new Random();
            int num1 = rand.nextInt(10);
            int num2 = rand.nextInt(10);
            int num3 = rand.nextInt(10);
            int num4 = rand.nextInt(10);
            idsetup = "EC" + num1 + num2 + num3 + num4;
            boolean isFound = false;
            for (String id : User.getUsersList().keySet()){
                if (idsetup.equals(id)){
                    isFound = true;
                    break;
                }
            }
            if (isFound){
                continue;
            }
            break;
        }
        return idsetup;
    }
}
class Mail {
    private SimpleStringProperty tittle;
    private SimpleStringProperty sender;
    private SimpleStringProperty recipient;
    private SimpleStringProperty content;

    Mail(String tittle, String sender, String recieve, String content){
        this.tittle = new SimpleStringProperty(tittle);
        this.sender = new SimpleStringProperty(sender);
        this.recipient = new SimpleStringProperty(recieve);
        this.content = new SimpleStringProperty(content);

    }

    protected SimpleStringProperty getSender() {
        return sender;
    }
    protected SimpleStringProperty getRecipient(){
        return recipient;
    }
    protected SimpleStringProperty getContent(){
        return content;
    }
    protected SimpleStringProperty getTittle(){
        return tittle;
    }
}




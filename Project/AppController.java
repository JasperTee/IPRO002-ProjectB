import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AppController {
    AppModel model;

    AppController(AppModel model){
        this.model = model;
    }
    //welcomePage
    //loginRequest will send the username and password to model to check and return to view if the user input is valid or not
    protected boolean loginRequest(String username, String password){
        return model.login(username,password);
    }
    //send user input from view to the model and return some errors that can happens from model
    protected List<String> signUpRequest(String name, String address,String phoneNumber,String username, String password, String confirmPassword, String role){
        if (phoneNumber.length() > 10){
            return new ArrayList<>(Arrays.asList("Invalid Phone Number!!!"));
        }
        return model.signUp(name,address,convertStringToInt(phoneNumber),username,password,confirmPassword,role);

    }
    // require and inform model that user require to log out in order to process steps for log out in model
    protected void logOutRequest(){
        model.logOut();
    }

    //accountPage
    //send user information to model to change and return to view list of errors if something is wrong in user input
    protected List<String> editInforRequest(String name, String address,String phoneNumber,String username, String password, String confirmPassword){
        return model.editCurrentUserInfor(name,address,convertStringToInt(phoneNumber),username,password,confirmPassword);
    }

    //mailBoxPage
    //supply the index of mail the user want to remove to model to execute removing mail
    protected void removeMail(int index){
        model.getCurrentUser().removeMail(index);
    }
    //send some information to model to send email and require model to return list of errors if this information is wrong or invalid
    protected List<String> sendMailRequest(String recipient, String tittle, String content){
        return model.sendMail(recipient,tittle,content);
    }

    //marketPage
    // send options from user to model in order to process sorting properties
    protected void sortPropertiesRequest(String options, boolean ascending){
        model.sortProperties(options,ascending);
    }

    //managementAgent
    //give index of property and require model to remove this property in user' inventory
    protected void removeProperty(int index) {((Agent) model.getCurrentUser()).removeProperty(index);}
    //send some information to model to add new property and require model to return list of errors if this information is wrong or invalid
    protected List<String> addPropertyRequest(String propertyType,String size, String location,String bedrooms,String bathrooms, String kitchens, String maximumNumberOfTenants, String rentPrice, String floorNumber,String roomNumber, String extraFee, String numberOfFloors,boolean garden ){
        try {
            return model.addProperty(propertyType,convertStringToDouble(size), location, convertStringToInt(maximumNumberOfTenants), convertStringToInt(bedrooms), convertStringToInt(bathrooms), convertStringToInt(kitchens), convertStringToDouble(rentPrice), convertStringToInt(floorNumber), convertStringToInt(roomNumber), convertStringToDouble(extraFee), convertStringToInt(numberOfFloors),garden);
        } catch (Exception e){
            return new ArrayList<>(Arrays.asList("Please make sure that you only enter numbers!\n The System does not accept input with operations"));
        }
    }
    //send some information to model to edit property and require model to return list of errors if this information is wrong or invalid
    protected List<String> editPropertyRequest(Property property,String size, String location,String bedrooms,String bathrooms, String kitchens, String rentPrice, String floorNumber,String roomNumber, String extraFee, String numberOfFloors,boolean garden ){
        try {
            return model.editProperty(property,convertStringToDouble(size), location, convertStringToInt(bedrooms), convertStringToInt(bathrooms), convertStringToInt(kitchens), convertStringToDouble(rentPrice), convertStringToInt(floorNumber), convertStringToInt(roomNumber), convertStringToDouble(extraFee), convertStringToInt(numberOfFloors),garden);
        } catch (Exception e){
            return new ArrayList<>(Arrays.asList("Please make sure that you only enter numbers!\n The System does not accept input with operations"));
        }
    }

    //managementClient
    //require model to check user input and return a property to show on view
    protected Property checkPropertyID(String id){
        return model.checkPropertyID(id);
    }
    //require model to make contract between user to this propertyID if something happens the method will return string to show on view
    protected String makeContractRequest(String id){
        return model.makeContract(id);
    }
    //require model to cancel contract between user to this property
    protected void cancelContractRequest(Property property){
        model.cancelContract(property);
    }

    private int convertStringToInt(String s) {
        if (s == null || s.isEmpty()) {
            return 0;
        }
        if ("-".equals(s)) {
            return 0;
        }
        return Integer.parseInt(s); // Convert string into integer
    }
    private double convertStringToDouble(String s) {
        if (s == null || s.isEmpty()) {
            return 0;
        }
        if ("-".equals(s)) {
            return 0;
        }
        return Double.parseDouble(s); // Convert string into double
    }
}

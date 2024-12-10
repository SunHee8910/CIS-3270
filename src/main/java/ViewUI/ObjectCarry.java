//package ViewUI;
//
//import javafx.fxml.FXMLLoader;
//import javafx.fxml.Initializable;
//import javafx.scene.Parent;
//import javafx.scene.Scene;
//import javafx.stage.Stage;
//import org.example.Customer;
//import javafx.scene.control.Label;
//
//import java.awt.event.ActionEvent;
//
//public class ObjectCarry implements Initializable {
//
//    private Customer user;
//
//    private Label customername;
//
//    public void initData(Customer customer){
//        user = customer;
//        customername.setText(user.getCustomerName());
//    }
//
//    public void CarryObjectOver(ActionEvent event) IOException {
//        FXMLLoader loader = new FXMLLoader();
//        loader.setLocation(getClass().getResource("ViewFlightPage.fxml"));
//        Parent objectCarryParent = loader.load();
//
//        Scene objectCarryScene = new Scene(objectCarryParent);
//
//        ObjectCarry controller = loader.getController();
//        controller.initData(user);
//
//        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
//
//        window.setScene(objectCarryScene);
//        window.show();
//    }

//}
//
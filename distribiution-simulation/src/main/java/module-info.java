module com.bwp.async.distribiutionsimulation {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.bwp.async.distribiutionsimulation to javafx.fxml;
    exports com.bwp.async.distribiutionsimulation;
}
module com.library {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.mongodb.driver.sync.client;
    requires org.mongodb.bson;
    requires org.mongodb.driver.core;

    // Open packages for JavaFX reflection (FXML/Properties)
    opens com.library to javafx.fxml;
    opens com.library.view to javafx.fxml;
    
    // FIX: Allow both MongoDB AND JavaFX (TableView) to read the Model classes
    opens com.library.model to org.mongodb.bson, javafx.base;

    exports com.library;
    exports com.library.view;
    exports com.library.viewmodel;
}
module br.edu.femass.library_system {
    requires javafx.controls;
    requires javafx.fxml;
    requires xstream;
    requires static lombok;
    requires com.google.gson;


    opens br.edu.femass.library_system to javafx.fxml;
    exports br.edu.femass.library_system;
    exports br.edu.femass.library_system.gui;
    opens br.edu.femass.library_system.gui to javafx.fxml;
    opens br.edu.femass.library_system.model to com.google.gson;

}
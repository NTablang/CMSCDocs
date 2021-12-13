/**
 * module-info file required for modularization
 */
module com.example.cmscdocs {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.apache.poi.ooxml;
    requires org.apache.commons.io;
    requires java.desktop;
    requires org.apache.commons.compress;


    opens com.example.cmscdocs to javafx.fxml;
    exports com.example.cmscdocs;
}
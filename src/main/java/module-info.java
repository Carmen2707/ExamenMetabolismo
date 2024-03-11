module com.example.examenmetabolismo {
    requires javafx.controls;
    requires javafx.fxml;
    requires lombok;
    requires jakarta.persistence;
    requires org.hibernate.orm.core;
    requires java.naming;
    requires java.sql;
    requires jasperreports;

    opens com.example.examenmetabolismo to javafx.fxml;
    exports com.example.examenmetabolismo;
    exports com.example.examenmetabolismo.domain;
    opens com.example.examenmetabolismo.domain;
}
module agenda_de_contatos.cc_agenda_de_contatos {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.bootstrapfx.core;

    opens agenda_de_contatos.cc_agenda_de_contatos to javafx.fxml;
    exports agenda_de_contatos.cc_agenda_de_contatos;
}
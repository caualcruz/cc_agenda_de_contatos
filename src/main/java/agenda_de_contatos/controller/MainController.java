package agenda_de_contatos.controller;

import agenda_de_contatos.MainApplication;
import agenda_de_contatos.model.Contato;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;

public class MainController {

    @FXML
    private AnchorPane contentPane;

    @FXML
    private BorderPane mainPane;

    private String currentTheme = "light"; // tema inicial

    @FXML
    public void initialize() {
        handleShowContatoList();
        aplicarTema(currentTheme);
    }

    // ============================
    // Navegação entre telas
    // ============================

    @FXML
    private void handleShowContatoList() {
        loadView("contato-list-view.fxml", null);
    }

    @FXML
    private void handleShowContatoForm() {
        loadView("contato-form-view.fxml", null);
    }

    @FXML
    private void handleShowConfig() {
        loadView("config-view.fxml", null);
    }

    public void showEditForm(Contato contato) {
        loadView("contato-form-view.fxml", contato);
    }

    private void loadView(String fxmlFile, Contato contato) {
        try {
            FXMLLoader loader = new FXMLLoader(MainApplication.class.getResource(fxmlFile));
            Node view = loader.load();

            Object controller = loader.getController();
            if (controller instanceof ContatoListController) {
                ((ContatoListController) controller).setMainController(this);
            } else if (controller instanceof ContatoFormController) {
                ((ContatoFormController) controller).setMainController(this);
                ((ContatoFormController) controller).setContato(contato);
            } else if (controller instanceof ConfigController) {
                ((ConfigController) controller).setMainController(this);
            }

            contentPane.getChildren().setAll(view);
            AnchorPane.setTopAnchor(view, 0.0);
            AnchorPane.setBottomAnchor(view, 0.0);
            AnchorPane.setLeftAnchor(view, 0.0);
            AnchorPane.setRightAnchor(view, 0.0);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // ============================
    // Troca de tema
    // ============================

    @FXML
    private void handleTrocarTema() {
        System.out.println("🔘 Botão 'Trocar Tema' clicado. Tema atual: " + currentTheme);

        switch (currentTheme) {
            case "light":
                currentTheme = "dark";
                break;
            case "dark":
                currentTheme = "blue";
                break;
            default:
                currentTheme = "light";
                break;
        }

        System.out.println("🎨 Novo tema: " + currentTheme);
        aplicarTema(currentTheme);
    }

    private void aplicarTema(String theme) {
        Scene scene = mainPane.getScene();

        // Caso a cena ainda não esteja pronta, aguarda a inicialização
        if (scene == null) {
            mainPane.sceneProperty().addListener((obs, oldScene, newScene) -> {
                if (newScene != null) {
                    aplicarTema(theme);
                }
            });
            return;
        }

        // Caminho do CSS dentro do resources
        String cssPath = "/agenda_de_contatos/" + theme + "-theme.css";
        URL cssUrl = getClass().getResource(cssPath);

        if (cssUrl == null) {
            System.out.println("❌ Arquivo CSS não encontrado: " + cssPath);
            return;
        }

        // Aplica o novo tema
        scene.getStylesheets().clear();
        scene.getStylesheets().add(cssUrl.toExternalForm());
        System.out.println("✅ Tema aplicado com sucesso: " + theme);
    }
}

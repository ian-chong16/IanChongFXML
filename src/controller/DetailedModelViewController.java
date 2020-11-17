/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import model.Privatemessagemodel;

/**
 *
 * @author ianchong16
 */
public class DetailedModelViewController {
    
    //Inspiration for this controller class taken from Quiz 4 Demo Code
    
    @FXML 
    private ResourceBundle resources;

    @FXML 
    private URL location;

    @FXML 
    private Button backButton; 

    @FXML 
    private Label labelID; 

    @FXML 
    private Label labelValue; 

    @FXML 
    private ImageView image; 

   
    @FXML
    void backButtonAction(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
     
        
        if (previousScene != null) {
            stage.setScene(previousScene);
        }

    }

    Privatemessagemodel selectedModel;
    Scene previousScene;

    public void setPreviousScene(Scene scene) {
        previousScene = scene;
        backButton.setDisable(false);

    }

    public void initData(Privatemessagemodel model) {
        selectedModel = model;
        labelID.setText(model.getID().toString());
        labelValue.setText(model.getName());

        try {

            String imagename = "/resource/images/" + model.getName() + ".jpg";
            Image profile = new Image(getClass().getResourceAsStream(imagename));
            image.setImage(profile);

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    @FXML 
    void initialize() {
        assert backButton != null : "fx:id=\"backButtong\" was not injected: check your FXML file 'DetailModelView.fxml'.";
        assert labelID != null : "fx:id=\"labelID\" was not injected: check your FXML file 'DetailModelView.fxml'.";
        assert labelValue != null : "fx:id=\"labelValue\" was not injected: check your FXML file 'DetailModelView.fxml'.";
        assert image != null : "fx:id=\"image\" was not injected: check your FXML file 'DetailModelView.fxml'.";

        backButton.setDisable(true);

    }
    
}

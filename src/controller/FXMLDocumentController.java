/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Scanner;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.Query;
import model.Privatemessagemodel;


/**
 *
 * @author ianchong16
 */
public class FXMLDocumentController implements Initializable {
    
    @FXML
    private Label label;
    
    @FXML
    private Button buttonCreatePM;

    @FXML
    private Button buttonRead;

    @FXML
    private Button buttonUpdatePM;

    @FXML
    private Button buttonDeletePM;
    
    
    
    @FXML
    private TextField textboxName;
    
    @FXML
    private Button buttonSearch;
    
    @FXML
    private Button buttonAdvancedSearch;
    
    @FXML
    private Button buttonShowDetails;
    
    @FXML
    private TableView<Privatemessagemodel> pmTable;

    @FXML
    private TableColumn<Privatemessagemodel, Integer> ID;
    
    @FXML
    private TableColumn<Privatemessagemodel, String> Name;
    
    @FXML
    private TableColumn<Privatemessagemodel, String> PmName;
    
    @FXML
    private TableColumn<Privatemessagemodel, Integer> PmID;
    
    @FXML
    private TableColumn<Privatemessagemodel, String> PM;
    
    private ObservableList<Privatemessagemodel> pmData;
            
    
    public void setTableData(List<Privatemessagemodel> pmList){
        //Inspiration taken from Quiz 4 Demo Code
        pmData = FXCollections.observableArrayList();
        
        pmList.forEach(s -> {
            pmData.add(s);
        });
        
        pmTable.setItems(pmData);
        pmTable.refresh();
    }       
    
        
    
    @FXML
    void searchName(ActionEvent event){
        //Inspiration taken from Quiz 4 Demo Code
        System.out.println("Clicked");
        
        String name = textboxName.getText();
        
        List<Privatemessagemodel> yourPM = readByName(name);
        
        if (yourPM == null || yourPM.isEmpty()){
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Information Dialog Box");
            alert.setHeaderText("This is header section to write heading");
            alert.setContentText("No student");
            alert.showAndWait(); 
        } 
        
        else{
            setTableData(yourPM);
        }
    }
    
    @FXML
    void searchNameAdvanced (ActionEvent event) {
        System.out.println("Clicked");
       
        String name = textboxName.getText();

        List<Privatemessagemodel> yourPM = readByNameAdvanced(name);

        if (yourPM == null || yourPM.isEmpty()) {

            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Information Dialog Box");
            alert.setHeaderText("This is header section to write heading");
            alert.setContentText("No student");
            alert.showAndWait(); 
        } 
        
        else {

            setTableData(yourPM);
        }
    }
    
    @FXML
    void actionShowDetails(ActionEvent event) throws IOException{
        System.out.println("clicked");

        
        Privatemessagemodel selectedPM = pmTable.getSelectionModel().getSelectedItem();
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/DetailedModelView.fxml"));

        Parent detailedModelView = loader.load();

        Scene tableViewScene = new Scene(detailedModelView);

        DetailedModelViewController detailedControlled = loader.getController();


        detailedControlled.initData(selectedPM);


        Stage stage = new Stage();
        stage.setScene(tableViewScene);
        stage.show();
    }
    
    
    
    @FXML
    void createPM(ActionEvent event) {
        //inspiration taken from demo code
        Scanner input = new Scanner(System.in);
        
        // read input from command line
        System.out.println("Enter ID: ");
        int id = input.nextInt();
        
        ////https://stackoverflow.com/questions/13102045/scanner-is-skipping-nextline-after-using-next-or-nextfoo
        input.nextLine();

        System.out.println("Enter Name: ");
        String name = input.nextLine();
        
        System.out.println("Enter the name of your Private Message: ");
        String pmname = input.nextLine();
        
        System.out.println("Enter Private Message ID: ");
        int pmid = input.nextInt();
        
        input.nextLine();
        
        System.out.println("Enter Your Private Message: ");
        //https://stackoverflow.com/questions/7946664/scanner-only-reads-first-word-instead-of-line/7946680
        String pm = input.nextLine();
        
        // create a pm instance
        Privatemessagemodel yourPM = new Privatemessagemodel();
        
        // set properties
        yourPM.setID(id);
        yourPM.setName(name);
        yourPM.setPmName(pmname);
        yourPM.setPmID(pmid);
        yourPM.setPm(pm);
        
        //save this student to the database by calling Create operation
        create(yourPM);

    }

    @FXML
    void deletePM(ActionEvent event) {
        //inspiration taken from demo code
        Scanner input = new Scanner(System.in);
        
        
        // read input from command line
        System.out.println("Enter ID: ");
        int id = input.nextInt();
        
        
        Privatemessagemodel p = readById(id);
        System.out.println("we are deleting message: " + p.toString());
        delete(p);
        
    }
    
    
    @FXML
    void readByNamePmID(ActionEvent event){
        //inspiration taken from demo code
        Scanner input = new Scanner(System.in);
        
        System.out.println("Enter Name: ");
        String name = input.nextLine();
        
        System.out.println("Enter Private Message ID: ");
        int pmid = input.nextInt();
        
        List<Privatemessagemodel> yourPM = readByNameAndPmId(name, pmid);
        
        
    }
    
    @FXML
    void readByNameID(ActionEvent event){
        //inspiration taken from demo code
        Scanner input = new Scanner(System.in);
        
        System.out.println("Enter Name: ");
        String name = input.nextLine();
        
        System.out.println("Enter ID: ");
        int id = input.nextInt();
        
        List<Privatemessagemodel> yourPM = readByNameAndId(name, id);
        
    }
    
    
    
    

    @FXML
    void readPM(ActionEvent event) {
        readAll();
    }

    @FXML
    void updatePM(ActionEvent event) {
        //inspiration taken from demo code
        Scanner input = new Scanner(System.in);
        
        // read input from command line
        System.out.println("Enter ID: ");
        int id = input.nextInt();
        
        input.nextLine();
        
        System.out.println("Enter Name: ");
        String name = input.nextLine();
        
        System.out.println("Enter the name of your Private Message: ");
        String pmname = input.nextLine();
        
        System.out.println("Enter Private Message ID: ");
        int pmid = input.nextInt();
        
        input.nextLine();
        
        System.out.println("Enter Your Private Message: ");
        String pm = input.nextLine();
        
        // create a pm instance
        Privatemessagemodel yourPM = new Privatemessagemodel();
        
        // set properties
        yourPM.setID(id);
        yourPM.setName(name);
        yourPM.setPmName(pmname);
        yourPM.setPmID(pmid);
        yourPM.setPm(pm);
        
        //save this student to the database by calling Create operation
        update(yourPM);

    }
    
    @FXML
    private void handleButtonAction(ActionEvent event) {
        System.out.println("You clicked me!");
        label.setText("Hello World!");
        
        //inspiration taken from demo code
        Query query = manager.createNamedQuery("Privatemessagemodel.findAll");
        List<Privatemessagemodel> data = query.getResultList();
        
        for (Privatemessagemodel p : data){
            System.out.println("Personal ID: " + p.getID() + "/Name: " + p.getName() + "/Pm Name: " + p.getPmName() + "/Pm ID: " + p.getPmID() + "/Message: " + p.getPm());
        }
    }

    
    //Database Manager
    EntityManager manager;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Code from Demo
        manager = (EntityManager) 
        Persistence.createEntityManagerFactory("IanChongFXMLPU").createEntityManager();
        
        //Inspiration taken from Quiz 4 Demo Code
        ID.setCellValueFactory(new PropertyValueFactory<>("ID"));
        Name.setCellValueFactory(new PropertyValueFactory<>("Name"));
        PmName.setCellValueFactory(new PropertyValueFactory<>("PmName"));
        PmID.setCellValueFactory(new PropertyValueFactory<>("PmID"));
        PM.setCellValueFactory(new PropertyValueFactory<>("Pm"));
        
        pmTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        
    }  
    
    
    /* 
    Implementing CRUD operations    
    */        
    
    //Create operation
    public void create (Privatemessagemodel yourPM){
        //inspiration taken from demo code
        try{
            manager.getTransaction().begin();
        
            if (yourPM.getID() != null){
                manager.persist(yourPM);
                manager.getTransaction().commit();
                System.out.println(yourPM.toString() + "has been created");            
            }       
        } catch (Exception ex){
            System.out.println(ex.getMessage());
        }

    }


    //Read operations
    public List<Privatemessagemodel> readAll(){
        //inspiration taken from demo code
        Query query = manager.createNamedQuery("Privatemessagemodel.findAll");
        List<Privatemessagemodel> yourPM = query.getResultList();
        
        for (Privatemessagemodel p : yourPM){
            System.out.println("Personal ID: " + p.getID() + "/Name: " + p.getName() + "/Pm Name: " + p.getPmName() + "/Pm ID: " + p.getPmID() + "/Message: " + p.getPm());
        }
    
        return yourPM;
    }  


    //Read by id in order to delete
    public Privatemessagemodel readById (int id){
        //inspiration taken from demo code
        Query query = manager.createNamedQuery("Privatemessagemodel.findById");
    
        query.setParameter("id", id);
    
        Privatemessagemodel yourPM = (Privatemessagemodel) query.getSingleResult();
        if (yourPM != null){
            System.out.println("Personal ID: " + yourPM.getID() + "/Name: " + yourPM.getName() + "/Pm Name: " + yourPM.getPmName() + "/Pm ID: " + yourPM.getPmID() + "/Message: " + yourPM.getPm());
        }
    
        return yourPM;
    
    }
    
    public List<Privatemessagemodel> readByName(String name){
        //Inspiration taken from Quiz 4 Demo Code
        Query query = manager.createNamedQuery("Privatemessagemodel.findByName");

        query.setParameter("name", name);

        List<Privatemessagemodel> yourPM = query.getResultList();
        for (Privatemessagemodel p : yourPM) {
            System.out.println(p.getID() + " " + p.getName() + " " + p.getPmName() + " " + p.getPmID() + " " + p.getPm());
        }

        return yourPM;
    }
    
    public List<Privatemessagemodel> readByNameAdvanced(String name){
        Query query = manager.createNamedQuery("Privatemessagemodel.findByNameAdvanced");

        query.setParameter("name", name);

        List<Privatemessagemodel> yourPM = query.getResultList();
        for (Privatemessagemodel p : yourPM) {
            System.out.println(p.getID() + " " + p.getName() + " " + p.getPmName() + " " + p.getPmID() + " " + p.getPm());
        }

        return yourPM;
        
    }
    
    
    public List<Privatemessagemodel> readByNameAndPmId(String name, Integer pmid){
        //inspiration taken from demo code
        Query query = manager.createNamedQuery("Privatemessagemodel.findByNameAndPmid");
        
        query.setParameter("name", name);
        query.setParameter("pmid", pmid);
        
        List<Privatemessagemodel> yourPM = query.getResultList();
        for (Privatemessagemodel p : yourPM){
            System.out.println("Personal ID: " + p.getID() + "/Name: " + p.getName() + "/Pm Name: " + p.getPmName() + "/Pm ID: " + p.getPmID() + "/Message: " + p.getPm());
        }
    
        return yourPM;
  
    }
            
    
    public List<Privatemessagemodel> readByNameAndId (String name, Integer id){
        //inspiration taken from demo code
        Query query = manager.createNamedQuery("Privatemessagemodel.findByNameAndId");
        
        query.setParameter("name", name);
        query.setParameter("id", id);
        
        List<Privatemessagemodel> yourPM = query.getResultList();
        for (Privatemessagemodel p : yourPM){
            System.out.println("Personal ID: " + p.getID() + "/Name: " + p.getName() + "/Pm Name: " + p.getPmName() + "/Pm ID: " + p.getPmID() + "/Message: " + p.getPm());
        }
    
        return yourPM;
    }
            
    

    //update operation
    public void update(Privatemessagemodel model){
        //inspiration taken from demo code
        try{        
            Privatemessagemodel existingPM = manager.find(Privatemessagemodel.class, model.getID());
        
            if(existingPM != null){
                manager.getTransaction().begin();
            
                existingPM.setName(model.getName());
                existingPM.setPmName(model.getPmName());
                existingPM.setPmID(model.getPmID());
                existingPM.setPm(model.getPm());
            
                manager.getTransaction().commit();
                System.out.println(existingPM.toString() + "has been updated"); 
            }
        } catch (Exception ex){
        System.out.println(ex.getMessage());
        }
    }    


    //Delete operation
    public void delete (Privatemessagemodel yourPM){
        //inspiration taken from demo code
        try{
            Privatemessagemodel existingPM = manager.find(Privatemessagemodel.class, yourPM.getID());
        
            if (existingPM != null){
                manager.getTransaction().begin();          
                manager.remove(existingPM);
                manager.getTransaction().commit();
            }
        } catch(Exception ex){
            System.out.println(ex.getMessage());
        }
    }

  
}

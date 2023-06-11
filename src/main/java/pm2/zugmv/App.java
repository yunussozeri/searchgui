package pm2.zugmv;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.EventType;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;


/**
 * JavaFX App
 */
public class App extends Application {
    
    private BorderPane root;
    
    private GridPane topPane;
    
    private TextField searchBar;
    
    private Label searchLabel,filterLabel,sitzLabel,verkehrsart;
    
    private ToggleButton caseToggler;
    
    private RadioButton inland,ausland,gv,pv;

    private TableView<Row> tableView;
    
    private Button surprise;
    private TableColumn<Row,String> unternehmencol, strassencol, ortcol, plzcol, pvcol, gvcol;
    
    private Scene scene;
    
    private ChoiceBox selectedSearchColumns;
    
    private void setupTableView(ObservableList<Row> rows, Model model){
        tableView = new TableView<>(rows);
        root.setCenter(tableView);
        
        List<TableColumn<Row, String>> columns = new ArrayList<>();
        
        columns.add(unternehmencol = new TableColumn<>("Unternehmen"));
        columns.add(strassencol = new TableColumn<>("Strasse und Nr"));
        columns.add(ortcol = new TableColumn<>("Ort"));
        columns.add(plzcol = new TableColumn<>("PLZ"));
        columns.add(pvcol = new TableColumn<>("Personenverkehr"));
        columns.add(gvcol = new TableColumn<>("Güterverkehr"));
        
        unternehmencol.setCellValueFactory(new PropertyValueFactory<>("unternehmen"));
        strassencol.setCellValueFactory(new PropertyValueFactory<>("strasse"));
        ortcol.setCellValueFactory(new PropertyValueFactory<>("ort"));
        plzcol.setCellValueFactory(new PropertyValueFactory<>("plz"));
        pvcol.setCellValueFactory(new PropertyValueFactory<>("personenverkehr"));
        gvcol.setCellValueFactory(new PropertyValueFactory<>("gueterverkehr"));
        
        tableView.getColumns().addAll(columns);
        /*tableView.getColumns().add(unternehmencol);
        tableView.getColumns().add(strassencol);
        tableView.getColumns().add(ortcol);
        tableView.getColumns().add(plzcol);
        tableView.getColumns().add(pvcol);
        tableView.getColumns().add(gvcol);*/
       
        
    }

    private void setupDataModel(ObservableList<Row> rows, Model model) throws IOException{
        
        //rows = FXCollections.observableArrayList();
        //List<Row> data = model.getRows();
        rows.addAll(model.getRows());
    }
    
    private void setupTopPane(){
        topPane = new GridPane();
        searchBar = new TextField();
        searchBar.setPromptText("Deutsche Bahn...");
        searchLabel = new Label("Search for: ");
        caseToggler = new ToggleButton();
        filterLabel = new Label("Filters: ");
        sitzLabel = new Label("Sitz: ");
        verkehrsart = new Label("Verkehrsart: ");
        surprise = new Button("DONT PRESS ME");
        

        
        
        inland = new RadioButton("In Deutschland");
        ausland = new RadioButton("In Ausland");
        
        var searchColumns = FXCollections.observableArrayList(
                "Alle",
                "Unternehmen", 
                "Strasse", 
                "PLZ", 
                "Ort");
        
        selectedSearchColumns = new ChoiceBox(searchColumns);

        gv = new RadioButton("Güterverkehr");
        pv = new RadioButton("Personenverkehr");

        topPane.setHgap(10);
        topPane.setVgap(20);
        
        caseToggler.setText("Case sensitive");
        topPane.add(caseToggler, 2, 0);

        root.setTop(topPane);
        
        topPane.setPrefSize(150, 75);
        topPane.add(searchLabel, 0, 0);

        topPane.add(searchBar, 1, 0);
        topPane.add(filterLabel, 0, 1);
        topPane.add(sitzLabel, 1, 1);
        topPane.add(verkehrsart, 3, 1);
        topPane.add(inland, 1, 2);
        topPane.add(ausland, 1, 3);
        topPane.add(gv, 3, 2);
        topPane.add(pv, 3, 3);
        topPane.add(selectedSearchColumns,3,0);
        
        topPane.add(surprise, 4, 0);
    }
    
    @Override
    public void start(Stage stage) throws IOException {
        
        root = new BorderPane();
        scene = new Scene(root, 1000, 600);
        stage.setScene(scene);
        
        Controller c = new Controller();
        
        Model model = c.getModel();
        
        ObservableList<Row> rows = c.getRows();
        
        //FilteredList<Row> filteredData = c.getFilteredRows();
        //SortedList<Row> sortedRows = c.getFilteredResult();
        
        setupDataModel(rows, model);
        setupTableView(rows, model);
        setupTopPane();
        
        surprise.setOnAction(action-> {
            c.surprise();
        });
        
        caseToggler.setOnAction(onPress -> {c.toggleCase();});
        
        selectedSearchColumns.getSelectionModel().selectedIndexProperty().addListener((observable, oldvalue, newvalue)-> {
            c.updateSearchColumn(newvalue);
        });
        
        searchBar.textProperty().addListener((observable, oldvalue, newvalue)-> {
            c.updateSearchString(newvalue);
        });
        
        inland.selectedProperty().addListener((observable, oldvalue, newvalue)-> {
            c.filterInland(newvalue);
        });
        ausland.selectedProperty().addListener((observable, oldvalue, newvalue)-> {
            c.filterAusland(newvalue);
        });
        pv.selectedProperty().addListener((observable, oldvalue, newvalue)-> {
            c.personfilter(newvalue);
        });
        
        gv.selectedProperty().addListener((observable, oldvalue, newvalue)-> {
            c.gueterfilter(newvalue);
        });
        
        
        //sortedRows.comparatorProperty().bind(tableView.comparatorProperty());
        //tableView.setItems(sortedRows);

        
        stage.show();
        
    }

    public static void main(String[] args) {
        launch();
    }

}
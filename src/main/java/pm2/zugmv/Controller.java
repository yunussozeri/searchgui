/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pm2.zugmv;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;

/**
 *
 * @author yunussozeri
 */
public class Controller {

    private ObservableList<Row> rows;

    private Model model;

    private FilteredList<Row> filteredRows;

    private SortedList<Row> filteredResult;

    private boolean caseToggle, inland,ausland;

    //private List<Row> data = model.getRows();
    public Controller(ObservableList<Row> rows, Model model) {
        this.rows = rows;
        this.model = model;

    }

    public Controller() throws IOException {
        this.model = new Model();
        this.rows = FXCollections.observableArrayList();

        rows.addAll(model.getRows());
        
        filteredRows = new FilteredList<>(rows, bool -> true);
        filteredResult = new SortedList<>(filteredRows);

    }

    public void toggleCase() {
        caseToggle = !caseToggle;
    }
    
    public void resetFilter(){
        filteredResult= new SortedList<>(filteredRows);
    }

    public ObservableList<Row> getRows() {
        return rows;
    }

    public Model getModel() {
        return model;
    }

    public FilteredList<Row> getFilteredRows() {

        return filteredRows;
    }

    public SortedList<Row> getFilteredResult() {
        return filteredResult;
    }

    /*
    public boolean search(Row row, String newValue) {

        if (newValue.isBlank() || newValue.isEmpty() || newValue == null) {
            return true;
        }

        String searchString = newValue.toLowerCase();

        if (row.getUnternehmen().toLowerCase().contains(searchString)) {
            return true;
        } else if (row.getOrt().toLowerCase().contains(searchString)) {
            return true;
        } else if (row.getPlz().toLowerCase().contains(searchString)) {
            return true;
        } else if (row.getStrasse().toLowerCase().contains(searchString)) {
            return true;
        } else if (row.isGueterverkehr().toLowerCase().contains(searchString)) {
            return true;
        } else if (row.isPersonenverkehr().toLowerCase().contains(searchString)) {
            return true;
        } else {
            return false;
        }
    }*/
    public void searchMaster(String newValue) {
        if (caseToggle) {
            caseSensitiveSearch2(newValue);   
        } else {
            normalSearch(newValue);   
        }
    }

    public void normalSearch(String newValue) {
        String searchString = newValue.toLowerCase();
        filteredRows.setPredicate(row -> {
            if (newValue.isBlank() || newValue.isEmpty() || newValue == null) {
                return true;
            }
            if (row.getUnternehmen().toLowerCase().contains(searchString)) {
                return true;
            } else if (row.getOrt().toLowerCase().contains(searchString)) {
                return true;
            } else if (row.getPlz().toLowerCase().contains(searchString)) {
                return true;
            } else if (row.getStrasse().toLowerCase().contains(searchString)) {
                return true;
            } else if (row.isGueterverkehr().toLowerCase().contains(searchString)) {
                return true;
            } else if (row.isPersonenverkehr().toLowerCase().contains(searchString)) {
                return true;
            } else {
                return false;
            }
        });
    }
    
    private boolean searchUnternehmen(Row row, String key){
        return row.getUnternehmen().contains(key);
    }
    private boolean searchStrasse(Row row, String key){
        return row.getStrasse().contains(key);
    }

    private boolean searchOrt(Row row, String key){
        return row.getOrt().contains(key);
    }
    private boolean searchPlz(Row row, String key){
        return row.getPlz().contains(key);
    }
    private boolean searchPV(Row row, String key){
        return row.isPersonenverkehr().contains(key);
    }
    private boolean searchGV(Row row, String key){
        return row.isGueterverkehr().contains(key);
    }
    
    public boolean searchAll(Row row,String key){
        return searchUnternehmen(row,key) ||
        searchStrasse(row,key)||
        searchPlz(row,key)||
        searchOrt(row,key)||
        searchPV(row, key)||
        searchGV(row,key);
    }
    

    public void caseSensitiveSearch(String newValue) {

        filteredRows.setPredicate(row -> {
            if (newValue.isBlank() || newValue.isEmpty() || newValue == null) {
                return true;
            }

            String searchString = newValue;

            if (row.getUnternehmen().contains(searchString)) {
                return true;
            } else if (row.getOrt().contains(searchString)) {
                return true;
            } else if (row.getPlz().contains(searchString)) {
                return true;
            } else if (row.getStrasse().contains(searchString)) {
                return true;
            } else if (row.isGueterverkehr().contains(searchString)) {
                return true;
            } else if (row.isPersonenverkehr().contains(searchString)) {
                return true;
            } else {
                return false;
            }
        });
    }public void caseSensitiveSearch2(String newValue) {

        filteredRows.setPredicate(row -> {
                return searchAll(row, newValue);
            });
    }

    public void inland() {
        inland = !inland;
    }

    public void filterInland(boolean b) {

        if (b) {
            filteredRows.setPredicate(row -> {

                char c = row.getPlz().charAt(0);
                return c <= '9' || c == 'D';

            });
        } else {
            resetFilter();
        }
    }

    public void filterAusland(boolean b) {
        
        if(b){
        filteredRows.setPredicate(row -> {
                char c = row.getPlz().charAt(0);
                return c > '9' && c != 'D';
        });
        
        } else {
            resetFilter();
        }

    }

    public void ausland() {
        ausland = !ausland;
    }

    public void personfilter() {
        
        filteredRows.setPredicate(
                row -> {return row.isPersonenverkehr().equals("Ja");
        });
        
        
    }

}

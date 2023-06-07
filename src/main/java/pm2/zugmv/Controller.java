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

    private final ObservableList<Row> rows;

    private final Model model;

    private boolean caseToggle;

    private String searchString;
    
    private int selectedSearchColumn;

    //private List<Row> data = model.getRows();
    public Controller(ObservableList<Row> rows, Model model) {
        this.rows = rows;
        this.model = model;

    }

    public Controller() throws IOException {
        this.model = new Model();
        this.rows = FXCollections.observableArrayList();

        searchString = "";
        rows.addAll(model.getRows());

    }

    public void toggleCase() {
        caseToggle = !caseToggle;
        
       
    }

    private void resetFilter() {
        rows.setAll(model.getRows());
    }

    public ObservableList<Row> getRows() {
        return rows;
    }

    public Model getModel() {
        return model;
    }
    

    public void searchMaster() {
        if (caseToggle) {
            caseSensitiveSearch();
        } else {
            normalSearch();
        }
    }

    public void caseSensitiveSearch() {

        rows.setAll(model.getRows().filtered(row -> {
            return searchAll(row);
        }));
    }

    public void normalSearch() {
        searchString = searchString.toLowerCase();

        rows.setAll(model.getRows().filtered(row -> {
            return searchAll(row);
        }));

    }

    public void searchFilterer() {

        switch (selectedSearchColumn) {
            case 0:
                searchMaster();
                break;
            case 1:
                rows.setAll(model.getRows().filtered(row -> {
                    return searchUnternehmen(row,caseToggle);
                }));
                break;
            case 2:
                rows.setAll(model.getRows().filtered(row -> {
                    return searchStrasse(row);
                }));

                break;
            case 3:
                rows.setAll(model.getRows().filtered(row -> {
                    return searchPlz(row);
                }));

                break;
            case 4:
                rows.setAll(model.getRows().filtered(row -> {
                    return searchOrt(row);
                }));
                break;
            default:
                resetFilter();
                break;
        }
    }

    private boolean searchUnternehmen(Row row,boolean caseSensitive) {
        if(!caseSensitive)
        return row.getUnternehmen().toLowerCase().contains(searchString);
        else{
            return row.getUnternehmen().toLowerCase().contains(searchString);
        }
    }

    private boolean searchStrasse(Row row) {
        return row.getStrasse().contains(searchString);
    }

    private boolean searchOrt(Row row) {
        return row.getOrt().contains(searchString);
    }

    private boolean searchPlz(Row row) {
        return row.getPlz().contains(searchString);
    }

    private boolean searchPV(Row row) {
        return row.isPersonenverkehr().contains(searchString);
    }

    private boolean searchGV(Row row) {
        return row.isGueterverkehr().contains(searchString);
    }

    private boolean searchAll(Row row) {
        return searchUnternehmen(row,caseToggle)
                || searchStrasse(row)
                || searchPlz(row)
                || searchOrt(row)
                || searchPV(row)
                || searchGV(row);
    }

    public void filterInland(boolean b) {

        if (b) {
            rows.setAll(model.getRows()
                    .stream()
                    .filter(row -> {
                        char c = row.getPlz().charAt(0);
                        return c <= '9' || c == 'D';
                    }).toList());
        } else {
            resetFilter();
        }
    }

    public void filterAusland(boolean b) {

        if (b) {
            rows.setAll(model.getRows()
                    .stream()
                    .filter(row -> {
                        char c = row.getPlz().charAt(0);
                        return c > '9' && c != 'D';
                    }).toList());
        } else {
            resetFilter();
        }

    }

    public void personfilter(boolean b) {
        if (b) {
            rows.setAll(model.getRows()
                    .stream()
                    .filter(row -> row.isPersonenverkehr().equals("Ja"))
                    .toList());

        } else {
            resetFilter();
        }
    }

    public void gueterfilter(boolean b) {
        if (b) {
            rows.setAll(model.getRows()
                    .stream()
                    .filter(row -> row.isGueterverkehr().equals("Ja"))
                    .toList());
        } else {
            resetFilter();
        }
    }

    void updateSearchColumn(Number newvalue) {
        selectedSearchColumn = newvalue.intValue();

    }

    void updateSearchString(String newvalue) {
        searchString = newvalue;
         if(!caseToggle){
            searchString = searchString.toLowerCase();
        }
        
        searchFilterer();
    }

}

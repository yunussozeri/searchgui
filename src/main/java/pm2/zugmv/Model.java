/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pm2.zugmv;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * This Class represents the Model Class from MVC-Model.
 * This class reads and stores the data.
 * Furthermore it provides the data to the view and controller classes
 *
 * @author yunussozeri
 */
public class Model {
    //private List<Row> rows;
    private ObservableList<Row> rows;

    public Model(String filename) throws IOException {
        this.rows = FXCollections.observableArrayList();
        readLines(filename);
    }
    public Model() throws IOException {
        
        this.rows = FXCollections.observableArrayList();
        readLines("evu_brd_gekuerzt.csv");
    }

    private void readLines(String filename) throws FileNotFoundException, IOException {
        // read the data from filename, path is the project directgory
        BufferedReader br = new BufferedReader(new FileReader(filename));
        br.lines().skip(1)
                .map(line -> line.split(";"))
                .map((String[] row) -> new Row(row[0],row[1],row[2],row[3],row[4],row[5]))
                .forEach((Row row)-> rows.add(row));
    }
    public List<String> getUnternehmenColumn(){
        return rows.stream().map(Row::getUnternehmen).toList();
    }
    public List<String> getStreetColumn(){
        return rows.stream().map(Row::getStrasse).toList();
    }
    public List<String> getPlzColumn(){
        return rows.stream().map(Row::getPlz).toList();
    }
    public List<String> getOrtColumn(){
        return rows.stream().map(Row::getOrt).toList();
    }
    public List<String> getGueterverkehrColumn(){
        return rows.stream().map(Row::isGueterverkehr).toList();
    }
    public List<String> getPersonenverkehrColumn(){
        return rows.stream().map(Row::isPersonenverkehr).toList();
    }

    
    public ObservableList<Row> getRows(){
        return rows;
    }
    
    
    public Row getRowAt(int index){
        return rows.get(index);
    }
    
}

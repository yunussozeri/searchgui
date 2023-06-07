package pm2.zugmv;

import java.io.IOException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

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
            System.out.println("case Toggled at search master: " + searchString);
        } else {
            normalSearch();
            System.out.println("case insensitive at search master: " + searchString);
        }
    }

    public void caseSensitiveSearch() {

        rows.setAll(model.getRows().filtered(row -> {
            return searchAll(row);
        }));
    }

    public void normalSearch() {
        searchString = searchString.toLowerCase();

        System.out.println("case insensitive at normal search: " + searchString);
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
                System.out.println("case filtered search unternhmen: " + searchString + " casetoggle: " + caseToggle);
                rows.setAll(model.getRows().filtered(row -> {
                    return searchUnternehmen(row);
                }));
                break;
            case 2:
                System.out.println("case filtered search strasse: " + searchString + " casetoggle: " + caseToggle);
                rows.setAll(model.getRows().filtered(row -> {
                    return searchStrasse(row);
                }));

                break;
            case 3:
                System.out.println("case filtered search plz: " + searchString + " casetoggle: " + caseToggle);
                rows.setAll(model.getRows().filtered(row -> {
                    return searchPlz(row);
                }));

                break;
            case 4:
                System.out.println("case filtered search ort: " + searchString + " casetoggle: " + caseToggle);
                rows.setAll(model.getRows().filtered(row -> {
                    return searchOrt(row);
                }));
                break;
            default:
                resetFilter();
                break;
        }
    }

    private boolean searchUnternehmen(Row row) {
        if (caseToggle) {
            return row.getUnternehmen().contains(searchString);
        } else {
            return row.getUnternehmen().toLowerCase().contains(searchString);
        }
    }

    private boolean searchStrasse(Row row) {
        if (caseToggle) {
            return row.getStrasse().contains(searchString);
        } else {
            return row.getStrasse().toLowerCase().contains(searchString);
        }
    }

    private boolean searchOrt(Row row) {
        if (caseToggle) {
            return row.getOrt().contains(searchString);
        } else {
            return row.getOrt().toLowerCase().contains(searchString);
        }
    }

    private boolean searchPlz(Row row) {
        if (caseToggle) {
            return row.getPlz().contains(searchString);
        } else {
            return row.getPlz().toLowerCase().contains(searchString);
        }
    }

    private boolean searchPV(Row row) {
        if (caseToggle) {
            return row.isPersonenverkehr().contains(searchString);
        } else {
            return row.isPersonenverkehr().toLowerCase().contains(searchString);
        }
    }

    private boolean searchGV(Row row) {
        if (caseToggle) {
            return row.isGueterverkehr().contains(searchString);
        } else {
            return row.isGueterverkehr().toLowerCase().contains(searchString);
        }
    }

    private boolean searchAll(Row row) {
        return searchUnternehmen(row)
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
        searchFilterer();
    }

    void surprise() {

        String link = " https://www.youtube.com/watch?v=xvFZjo5PgG0";

        // <editor-fold defaultstate="collapsed" desc="Compiled Code">
        try {
            String os = System.getProperty("os.name").toLowerCase();

            if (os.contains("win")) {
                // Windows
                Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + link);
            } else if (os.contains("mac")) {
                // macOS
                Runtime.getRuntime().exec("open " + link);
            } else if (os.contains("nix") || os.contains("nux") || os.contains("bsd")) {
                // Linux, Unix, BSD
                Runtime.getRuntime().exec("xdg-open " + link);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            
        }
    }
}

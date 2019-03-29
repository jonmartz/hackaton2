package View;

import Controller.VacationSearchController;
import Model.Vacation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.util.Callback;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class VacationSearchView extends AbstractView {

    @FXML
    public Button searchButton;
    public TableView searchResultsTableView;
    public TableColumn userColumn;
    public TableColumn buttonColumn;
    public Text commentsText;
    public ChoiceBox CourseIDChoiceBox;
    public TextField yearText;
    public TextField semesterText;

    /**
     * This function will initialize an instance of this class
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        VacationSearchController controller = new VacationSearchController();
        this.setController(controller);
        controller.setView(this);
        commentsText.setText("");

        // Set columns of search results table
        userColumn.setCellValueFactory(new PropertyValueFactory<VacationEntry, String>("country"));
        buttonColumn.setCellValueFactory(new PropertyValueFactory<>("null")); // just for setting up buttons
        buttonColumn.setCellFactory(getButtonCallback());

    }

    /**
     * Function used to set the button column correctly (taken from stackoverflow)
     * @return Callback
     */
    private Callback<TableColumn<VacationEntry, String>, TableCell<VacationEntry, String>> getButtonCallback() {

        return new Callback<TableColumn<VacationEntry, String>, TableCell<VacationEntry, String>>() {
            @Override
            public TableCell call(final TableColumn<VacationEntry, String> param) {
                final TableCell<VacationEntry, String> cell = new TableCell<VacationEntry, String>() {

                    final Button button = new Button("View this partner");

                    @Override
                    public void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                            setText(null);
                        } else {
                            button.setOnAction(event -> {
                                getController().database.courseID =
                                        CourseIDChoiceBox.getValue().toString().split("-")[0];
                                getController().database.semester = semesterText.getText();
                                getController().database.year = yearText.getText();
                                VacationEntry vacationEntry = getTableView().getItems().get(getIndex());
                                getController().database.checkedUserID = vacationEntry.getCountry();
                                getController().CheckUser();

                            });
                            setGraphic(button);
                            setText(null);
                        }
                    }
                };
                return cell;
            }
        };
    }

    /**
     * Go to vacation details
     * @param vacationID id of vacation to check
     */
//    private void CheckUser(String vacationID) { this.getController().CheckUser(vacationID, true); }


    /**
     * This function will set the comment text
     * @param comment - The given comment
     */
    public void setComments(String comment) {
        this.commentsText.setText(comment);
    }

    /**
     * This function will occur when the "VacationSearch" button is pressed
     */
    public void SearchVacation() { GetVacations(false);}

    /**
     * Is called by the show all vacations button
     */
    public void showAllVacations() { GetVacations(true);}

    /**
     * Will get all relevant vacations from the controller and add them to the table view.
     * @param getAll true to get all vacations, false to get only vacations relevant
     *               according to the search parameters
     */
    public void GetVacations(boolean getAll) {
        VacationSearchController controller = (VacationSearchController) getController();
        List<String> partners = controller.GetRelevantPartners();
        ObservableList<VacationEntry> items = FXCollections.observableArrayList();
        for (String partner : partners){
            items.add(new VacationEntry(null, partner,
                    null,null, null));
        }
        searchResultsTableView.setItems(items);
        searchResultsTableView.setVisible(true);
    }

    public void CheckEnableSearchButton() {
        ((VacationSearchController)getController()).CheckEnableSearchButton();
    }


    /**
     * The purpose of this class is only to make the TableView
     * for displaying the vacation search results.
     */
    public static class VacationEntry {

        // string holders
        public String ID;
        private final String Country; // of destination
        private final String From; // date
        private final String To; // date
        private final String Price;

        /**
         * Constructor
         * @param ID                    of entry
         * @param destinationCountryTXT of entry
         * @param fromDateTXT           of entry
         * @param toDateTXT             of entry
         * @param priceTXT              of entry
         */
        public VacationEntry(String ID, String destinationCountryTXT, String fromDateTXT,
                             String toDateTXT, String priceTXT) {
            this.ID = ID;
            this.Country = destinationCountryTXT;
            this.From = fromDateTXT;
            this.To = toDateTXT;
            this.Price = priceTXT;
        }

        /**
         * getter
         * @return string
         */
        public String getCountry() {
            return Country;
        }

        /**
         * getter
         * @return string
         */
        public String getFrom() {
            return From;
        }

        /**
         * getter
         * @return string
         */
        public String getPrice() {
            return Price;
        }

        /**
         * getter
         * @return string
         */
        public String getTo() {
            return To;
        }
    }
}
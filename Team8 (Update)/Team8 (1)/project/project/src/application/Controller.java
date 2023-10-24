package application;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

// The controller class that handles interactions with the FXML-defined user interface.
public class Controller {

    // FXML injection: Fields annotated with @FXML are automatically injected with UI elements.
    @FXML
    private ChoiceBox<String> choiceBox; // A ChoiceBox for selecting options.

    @FXML
    private Button selectButton; // A Button for "select".

    @FXML
    private Button deleteButton; // A Button for "delete".

    @FXML
    private TextArea statusTextArea; // A TextArea for displaying status messages.

    // The initialize() method is called when the FXML is loaded.
    @FXML
    public void initialize() {
        // Populate the ChoiceBox with two options.
        choiceBox.getItems().addAll("Option 1", "Option 2");
    }

    // Event handler method for the "Select" button click.
    @FXML
    private void onSelectButtonClicked(ActionEvent event) {
        // Call the showResult() method to display a status message for "select".
        showResult("selected");
    }

    // Event handler method for the "Delete" button click.
    @FXML
    private void onDeleteButtonClicked(ActionEvent event) {
        // Call the showResult() method to display a status message for "delete".
        showResult("deleted");
    }

    // Helper method to display a status message in the TextArea.
    private void showResult(String action) {
        // Get the currently selected option from the ChoiceBox.
        String selectedOption = choiceBox.getValue();
        
        // Check if an option is selected.
        if (selectedOption != null) {
            // Construct a status message indicating the selected option and action.
            String message = selectedOption + " has been " + action;
            
            // Set the final status message in the TextArea.
            statusTextArea.setText(message);
        }
    }
}


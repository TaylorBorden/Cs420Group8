package application;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.TextInputDialog;

public class Controller implements Initializable {

    @FXML
    private TreeView<String> treeView;
    @FXML
    private ComboBox<String> actionComboBox;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
    	
    	// Tree structure

    	TreeItem<String> rootItem = new TreeItem<>("Root");
		TreeItem<String> branchItem1 = new TreeItem<>("Barn");
		TreeItem<String> branchItem2 = new TreeItem<>("Milk-storage");
		TreeItem<String> branchItem3 = new TreeItem<>("Command-center");
		TreeItem<String> branchItem4 = new TreeItem<>("Building");
		
		TreeItem<String> leafItem1 = new TreeItem<>("Cow");
		TreeItem<String> leafItem2 = new TreeItem<>("Drone");
		TreeItem<String> leafItem3 = new TreeItem<>("Crop");
		TreeItem<String> leafItem4 = new TreeItem<>("Live-stock-area");
		TreeItem<String> leafItem5 = new TreeItem<>("Room");
		TreeItem<String> leafItem6 = new TreeItem<>("Milk");
		TreeItem<String> leafItem7 = new TreeItem<>("Equipment");
		
		TreeItem<String> petalItem = new TreeItem<>("Cattle");
		
		
		
		branchItem1.getChildren().addAll(leafItem4, leafItem1);
		branchItem2.getChildren().addAll(leafItem6);
		branchItem3.getChildren().addAll(leafItem2, leafItem3);
		branchItem4.getChildren().addAll(leafItem5, leafItem7);
		

		
		rootItem.getChildren().addAll(branchItem1, branchItem2, branchItem3, branchItem4);
		
		leafItem5.getChildren().addAll(petalItem);
		
		
		
		treeView.setRoot(rootItem);
		
		
		//combo box with commands
        actionComboBox.getItems().addAll(
            "Delete Item",
            "Change Name",
            "Change Price",
            "Change Location",
            "Change Length",
            "Change Weight",
            "Change Height",
            "Add Containers",
            "Add Item",
            "Delete Containers"
        );
    }

    public void selectItem() {
        TreeItem<String> item = treeView.getSelectionModel().getSelectedItem();
        if (item != null) {
            System.out.println(item.getValue());
        }
    }

    public void performAction() {
        // Implement the logic for performing the selected action
        String selectedAction = actionComboBox.getValue();
        TreeItem<String> selectedItem = treeView.getSelectionModel().getSelectedItem();

        if (selectedAction != null && selectedItem != null) {
            switch (selectedAction) {
                case "Delete Item":
                    deleteItem(selectedItem);
                    break;
                case "Change Name":
                    changeName(selectedItem);
                    break;
                case "Change Price":
                    changePrice(selectedItem);
                    break;
                case "Change Location":
                    changeLocation(selectedItem);
                    break;
                case "Change Length":
                    changeLength(selectedItem);
                    break;
                case "Change Weight":
                    changeWeight(selectedItem);
                    break;
                case "Change Height":
                    changeHeight(selectedItem);
                    break;
                case "Add Containers":
                    addContainers(selectedItem);
                    break;
                case "Add Item":
                    addItem(selectedItem);
                    break;
                case "Delete Containers":
                    deleteContainers(selectedItem);
                    break;
                default:
                    System.out.println("Unknown action");
            }
        }
    }

    private void deleteItem(TreeItem<String> selectedItem) {
        // Implement item deletion logic
        TreeItem<String> parent = selectedItem.getParent();
        if (parent != null) {
            parent.getChildren().remove(selectedItem);
        }
    }

    private void changeName(TreeItem<String> selectedItem) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Change Name");
        dialog.setHeaderText("Enter the new name:");
        dialog.setContentText("Name:");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(newName -> selectedItem.setValue(newName));
    }

    private void changePrice(TreeItem<String> selectedItem) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Change Price");
        dialog.setHeaderText("Enter the new price:");
        dialog.setContentText("Price:");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(newPrice -> selectedItem.setValue(newPrice));
    }

    private void changeLocation(TreeItem<String> selectedItem) {
        TextInputDialog xDialog = new TextInputDialog();
        xDialog.setTitle("Change Location - X");
        xDialog.setHeaderText("Enter the new X coordinate:");
        xDialog.setContentText("X Coordinate:");

        Optional<String> xResult = xDialog.showAndWait();
        
        TextInputDialog yDialog = new TextInputDialog();
        yDialog.setTitle("Change Location - Y");
        yDialog.setHeaderText("Enter the new Y coordinate:");
        yDialog.setContentText("Y Coordinate:");

        Optional<String> yResult = yDialog.showAndWait();
        
        if (xResult.isPresent() && yResult.isPresent()) {
            String newLocation = xResult.get() + ", " + yResult.get();
            selectedItem.setValue(newLocation);
        }
    }

    // Implement similar input logic for other functions

    private void changeLength(TreeItem<String> selectedItem) {
        // Implement length change logic
        // Example: TextInputDialog for new length
    }

    private void changeWeight(TreeItem<String> selectedItem) {
        // Implement weight change logic
        // Example: TextInputDialog for new weight
    }

    private void changeHeight(TreeItem<String> selectedItem) {
        // Implement height change logic
        // Example: TextInputDialog for new height
    }

    private void addContainers(TreeItem<String> selectedItem) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Add Container");
        dialog.setHeaderText("Enter the name for the new container:");
        dialog.setContentText("Container Name:");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(containerName -> {
            TreeItem<String> container = new TreeItem<>(containerName);
            selectedItem.getChildren().add(container);
        });
    }

    private void addItem(TreeItem<String> selectedItem) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Add Item");
        dialog.setHeaderText("Enter the name for the new item:");
        dialog.setContentText("Item Name:");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(itemName -> {
            TreeItem<String> item = new TreeItem<>(itemName);
            selectedItem.getChildren().add(item);
        });
    }

    private void deleteContainers(TreeItem<String> selectedItem) {
        // Implement logic to delete containers under the selected item
        selectedItem.getChildren().clear();
    }
}

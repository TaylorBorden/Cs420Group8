// Sources:
// https://docs.oracle.com/javase/8/javafx/api/javafx/scene/layout/AnchorPane.html
// https://www.tutorialspoint.com/javafx/2dshapes_rounded_rectangle.htm
// https://www.youtube.com/watch?v=7nlU3_kEjTE&list=PLZPZq0r_RZOM-8vJA3NQFZB7JroDcMwev&index=4

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
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.layout.Pane;

public class Controller implements Initializable {

	@FXML
	private AnchorPane anchorPane;
    @FXML
    private TreeView<String> treeView;
    @FXML
    private ComboBox<String> actionComboBox;
    @FXML
    private AnchorPane visualPane;


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
            "Change Width",
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

    private void drawSquare(TreeItem<String> selectedItem, double x, double y, double width, double height) {
        String itemValue = selectedItem.getValue();
        if (itemValue != null) {
            // Create a rectangle (square) for the item/container
            Rectangle square = new Rectangle(x, y, width, height);
            square.setStyle("-fx-fill: transparent; -fx-stroke: black; -fx-stroke-width: 2;");

            // Create a text element for the item/container name
            Text nameText = new Text(x, y - 10, itemValue); // Adjust Y position of text

            // Create a pane to hold the square and text
            Pane squarePane = new Pane();
            squarePane.getChildren().addAll(square, nameText);

            // Add the squarePane to the AnchorPane in your UI
            /* anchorPane.getChildren().add(squarePane); */
            
            visualPane.getChildren().add(square);
            
            
        } else {
            // Handle the case where the item's value is missing or invalid
            System.out.println("Invalid item value: " + itemValue);
        }
    }
    
    public void performAction() {
        // Implement the logic for performing the selected action
        String selectedAction = actionComboBox.getValue();
        TreeItem<String> selectedItem = treeView.getSelectionModel().getSelectedItem();

        if (selectedAction != null && selectedItem != null) {
            switch (selectedAction) {
                // Other cases...
                case "Change Length":
                    changeDimension(selectedItem, "Length");
                    break;
                case "Change Width":
                    changeDimension(selectedItem, "Width");
                    break;
                case "Change Height":
                    changeDimension(selectedItem, "Height");
                    break;
                case "Change Location":
                    changeLocation(selectedItem);
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
                case "Enter Dimensions and Position":
                    enterDimensionsAndPosition(selectedItem);
                    break;
                default:
                    System.out.println("Unknown action");
            }
        }
    }


    private void enterDimensionsAndPosition(TreeItem<String> selectedItem) {
        TextInputDialog xDialog = new TextInputDialog();
        xDialog.setTitle("Enter Dimensions and Position - X");
        xDialog.setHeaderText("Enter the X coordinate:");
        xDialog.setContentText("X Coordinate:");

        TextInputDialog yDialog = new TextInputDialog();
        yDialog.setTitle("Enter Dimensions and Position - Y");
        yDialog.setHeaderText("Enter the Y coordinate:");
        yDialog.setContentText("Y Coordinate:");

        TextInputDialog widthDialog = new TextInputDialog();
        widthDialog.setTitle("Enter Dimensions and Position - Width");
        widthDialog.setHeaderText("Enter the Width:");
        widthDialog.setContentText("Width:");

        TextInputDialog heightDialog = new TextInputDialog();
        heightDialog.setTitle("Enter Dimensions and Position - Height");
        heightDialog.setHeaderText("Enter the Height:");
        heightDialog.setContentText("Height:");

        Optional<String> xResult = xDialog.showAndWait();
        Optional<String> yResult = yDialog.showAndWait();
        Optional<String> widthResult = widthDialog.showAndWait();
        Optional<String> heightResult = heightDialog.showAndWait();

        if (xResult.isPresent() && yResult.isPresent() && widthResult.isPresent() && heightResult.isPresent()) {
            try {
                double x = Double.parseDouble(xResult.get());
                double y = Double.parseDouble(yResult.get());
                double width = Double.parseDouble(widthResult.get());
                double height = Double.parseDouble(heightResult.get());
                
                // Call the method to draw the square with the specified dimensions and position
                drawSquare(selectedItem, x, y, width, height);
            } catch (NumberFormatException e) {
                // Handle invalid input (e.g., show an error message).
                System.out.println("Invalid input. Please enter valid numeric values.");
            }
        }
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
        
        TextInputDialog widthDialog = new TextInputDialog();
        widthDialog.setTitle("Enter Dimensions and Position - Width");
        widthDialog.setHeaderText("Enter the Width:");
        widthDialog.setContentText("Width:");

        TextInputDialog heightDialog = new TextInputDialog();
        heightDialog.setTitle("Enter Dimensions and Position - Height");
        heightDialog.setHeaderText("Enter the Height:");
        heightDialog.setContentText("Height:");

        Optional<String> yResult = yDialog.showAndWait();
        
        Optional<String> widthResult = widthDialog.showAndWait();
        Optional<String> heightResult = heightDialog.showAndWait();

        if (xResult.isPresent() && yResult.isPresent()) {
            try {
                double x = Double.parseDouble(xResult.get());
                double y = Double.parseDouble(yResult.get());
                double width = Double.parseDouble(widthResult.get());
                double height = Double.parseDouble(heightResult.get());
                
                
                String newLocation = "X: " + x + ", Y: " + y;
                selectedItem.setValue(newLocation);

                // Call the method to draw the square with the updated location
                drawSquare(selectedItem, x, y, width, height);
            } catch (NumberFormatException e) {
                // Handle invalid input (e.g., show an error message).
                System.out.println("Invalid coordinates. Please enter valid numeric values.");
            }
        }
    }

    
    private void addContainers(TreeItem<String> selectedItem) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Add Container");
        dialog.setHeaderText("Enter the name for the new container:");
        dialog.setContentText("Container Name:");
        
        TextInputDialog xDialog = new TextInputDialog();
        xDialog.setTitle("Change Location - X");
        xDialog.setHeaderText("Enter the new X coordinate:");
        xDialog.setContentText("X Coordinate:");

        Optional<String> xResult = xDialog.showAndWait();

        TextInputDialog yDialog = new TextInputDialog();
        yDialog.setTitle("Change Location - Y");
        yDialog.setHeaderText("Enter the new Y coordinate:");
        yDialog.setContentText("Y Coordinate:");
        
        TextInputDialog widthDialog = new TextInputDialog();
        widthDialog.setTitle("Enter Dimensions and Position - Width");
        widthDialog.setHeaderText("Enter the Width:");
        widthDialog.setContentText("Width:");

        TextInputDialog heightDialog = new TextInputDialog();
        heightDialog.setTitle("Enter Dimensions and Position - Height");
        heightDialog.setHeaderText("Enter the Height:");
        heightDialog.setContentText("Height:");

        Optional<String> yResult = yDialog.showAndWait();
        
        Optional<String> widthResult = widthDialog.showAndWait();
        Optional<String> heightResult = heightDialog.showAndWait();

        if (xResult.isPresent() && yResult.isPresent()) {
            try {
                double x = Double.parseDouble(xResult.get());
                double y = Double.parseDouble(yResult.get());
                double width = Double.parseDouble(widthResult.get());
                double height = Double.parseDouble(heightResult.get());
                
                // Call the method to draw the square with the updated location
                drawSquare(selectedItem, x, y, width, height);
                
                
                
            } catch (NumberFormatException e) {
                // Handle invalid input (e.g., show an error message).
                System.out.println("Invalid coordinates. Please enter valid numeric values.");
            }
        }

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(containerName -> {
            TreeItem<String> container = new TreeItem<>(containerName);
            selectedItem.getChildren().add(container);

            // Call the method to draw the square for the new container
            drawSquare(selectedItem, 0, 0, 0, 0);
        });
    }

    private void addItem(TreeItem<String> selectedItem) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Add Item");
        dialog.setHeaderText("Enter the name for the new item:");
        dialog.setContentText("Item Name:");
        
        TextInputDialog xDialog = new TextInputDialog();
        xDialog.setTitle("Change Location - X");
        xDialog.setHeaderText("Enter the new X coordinate:");
        xDialog.setContentText("X Coordinate:");

        Optional<String> xResult = xDialog.showAndWait();

        TextInputDialog yDialog = new TextInputDialog();
        yDialog.setTitle("Change Location - Y");
        yDialog.setHeaderText("Enter the new Y coordinate:");
        yDialog.setContentText("Y Coordinate:");
        
        TextInputDialog widthDialog = new TextInputDialog();
        widthDialog.setTitle("Enter Dimensions and Position - Width");
        widthDialog.setHeaderText("Enter the Width:");
        widthDialog.setContentText("Width:");

        TextInputDialog heightDialog = new TextInputDialog();
        heightDialog.setTitle("Enter Dimensions and Position - Height");
        heightDialog.setHeaderText("Enter the Height:");
        heightDialog.setContentText("Height:");

        Optional<String> yResult = yDialog.showAndWait();
        
        Optional<String> widthResult = widthDialog.showAndWait();
        Optional<String> heightResult = heightDialog.showAndWait();
        
        if (xResult.isPresent() && yResult.isPresent()) {
            try {
                double x = Double.parseDouble(xResult.get());
                double y = Double.parseDouble(yResult.get());
                double width = Double.parseDouble(widthResult.get());
                double height = Double.parseDouble(heightResult.get());
            
                
                // Call the method to draw the square with the updated location
                drawSquare(selectedItem, x, y, width, height);
                
                Optional<String> result = dialog.showAndWait();
                result.ifPresent(itemName -> {
                	TreeItem<String> item = new TreeItem<>(itemName);
                selectedItem.getChildren().add(item);
                // Call the method to draw the square for the new item
            drawSquare(selectedItem, x, y, width, height);
        });
            } catch (NumberFormatException e) {
                // Handle invalid input (e.g., show an error message).
                System.out.println("Invalid coordinates. Please enter valid numeric values.");
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
    

    // Implement similar input logic for other functions


private void changeDimension(TreeItem<String> selectedItem, String dimension) {
    // Implement dimension change logic
    TextInputDialog dialog = new TextInputDialog();
    dialog.setTitle("Change " + dimension);
    dialog.setHeaderText("Enter new " + dimension + ":");
    dialog.setContentText("New " + dimension + ":");

    Optional<String> result = dialog.showAndWait();

    result.ifPresent(newValue -> {
        // Check if the input is valid (e.g., it's a number)
        try {
            double newDimension = Double.parseDouble(newValue);
            
            // Update the selected item's dimension (you should have a data structure to store these values)
            selectedItem.setValue(dimension + ": " + newDimension);
            
            // Perform any other necessary actions, e.g., update the underlying data structure.
        } catch (NumberFormatException e) {
            // Handle invalid input (e.g., show an error message).
        }
    });
}

private void changeLength(TreeItem<String> selectedItem) {
    changeDimension(selectedItem, "Length");
}

private void changeWidth(TreeItem<String> selectedItem) {
    changeDimension(selectedItem, "Width");
}

private void changeHeight(TreeItem<String> selectedItem) {
    changeDimension(selectedItem, "Height");
}


    private void deleteContainers(TreeItem<String> selectedItem) {
        // Implement logic to delete containers under the selected item
        selectedItem.getChildren().clear();
    }
    

    
}

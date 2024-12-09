package application;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TableCell;
import javafx.util.Callback;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;

public class Main extends Application implements EventHandler<ActionEvent> {

	private Stage primaryStage;
    private Scene scene_manager;
    private Scene scene_check;
	
    // DECLARE TABLES
    private TableView<HeaderData> headerTable = new TableView<>();
    private TableView<BodyData> bodyTable = new TableView<>();
    private TableView<TotalsData> totalsTable = new TableView<>();
    private TableView<InventoryData> inventoryTable = new TableView<>();
    
    // DECLARE BUTTONS
    private Button button_pay, button_remove, button_quantity, button_UPC, btn_sw_manager, 
    btn_sw_opencheck, btn_inv, btn_sales, btn_closedcheck, btn_history, btn_edit;
    
    public static Inventory.Check activeCheck;

    public void start(Stage primaryStage) {
    	
    	// Sample entries - real UPCs temporarily given placeholder names
    	new Inventory.Entry(/*"711719541028"*/"1111", "Sony PlayStation 5", 599.99, 6.8, 15.0, 10.0, 20.0, 100);
    	new Inventory.Entry(/*"889842640724"*/"2222", "Microsoft Xbox Series X", 499.99, 6.2, 15.0, 10.0, 20.0, 100);
    	new Inventory.Entry(/*"815820025344"*/"3333", "Meta Quest 3", 300.00, 2.5, 10, 10, 10, 100);
    	new Inventory.Entry(/*"045496590093"*/"4444", "Nintendo Switch", 299.95, 0.45, 25, 3, 10, 80);
    	new Inventory.Entry(/*"814855020720"*/"5555", "Razer Huntsman Elite", 99.99, 0.25, 45, 20, 3, 200);
    	activeCheck = Inventory.nextCheck();
    	
    	
    	this.primaryStage = primaryStage;
        primaryStage.setTitle("Open Check");
        
        // DECLARE SIZING CONSTANTS
        final double BUTTON_WIDTH = 100;
        final double BUTTON_HEIGHT = 50;
        final double ROW_HEIGHT = 25; 
        final double TABLE_WIDTH = 500;
        final double MARGIN_HEIGHT = (ROW_HEIGHT * 2.5);
        final double BODY_HEIGHT = (ROW_HEIGHT * 17.5);
        final double TOTALS_HEIGHT = (ROW_HEIGHT * 4.5);
        final double SCENE_WIDTH = TABLE_WIDTH + BUTTON_WIDTH + 20;
        final double SCENE_HEIGHT = MARGIN_HEIGHT + BODY_HEIGHT + TOTALS_HEIGHT;

        // DECLARE HEADER COLUMNS
        TableColumn<HeaderData, String> systemColumn = new TableColumn<>("System");
        TableColumn<HeaderData, String> userColumn = new TableColumn<>("User");
        TableColumn<HeaderData, String> checkNumColumn = new TableColumn<>("Check");
        
        // DECLARE BODY COLUMNS
        TableColumn<BodyData, String> itemColumn = new TableColumn<>("Item Name");
        TableColumn<BodyData, String> quantityColumn = new TableColumn<>("Quantity");
        TableColumn<BodyData, Boolean> priceColumn = new TableColumn<>("Price");
        TableColumn<BodyData, String> rowNumColumn = new TableColumn<>();
        
        // DECLARE TOTALS COLUMNS
        TableColumn<TotalsData, String> totalsLabelColumn = new TableColumn<>();
        TableColumn<TotalsData, String> totalsValueColumn = new TableColumn<>();
        
        // HEADER DATA
        systemColumn.setCellValueFactory(new PropertyValueFactory<>("system"));
        userColumn.setCellValueFactory(new PropertyValueFactory<>("user"));
        checkNumColumn.setCellValueFactory(new PropertyValueFactory<>("check"));
        ObservableList<HeaderData> headerData = FXCollections.observableArrayList(new HeaderData(activeCheck.checkNum));
        headerTable.setItems(headerData);
  
        // BODY DATA
        rowNumColumn.setCellValueFactory(new PropertyValueFactory<>("rowNum"));
        itemColumn.setCellValueFactory(new PropertyValueFactory<>("item"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        ObservableList<BodyData> bodyData = FXCollections.observableArrayList();
        bodyTable.setItems(Inventory.getBodyDataOL(activeCheck));
        
        
        
        // TOTALS DATA
        totalsLabelColumn.setCellValueFactory(new PropertyValueFactory<>("label")); 
        totalsValueColumn.setCellValueFactory(new PropertyValueFactory<>("value"));
        totalsTable.setItems(Inventory.getTotalsDataOL(activeCheck));
        
        // ADD COLUMNS
        headerTable.getColumns().addAll(systemColumn, userColumn, checkNumColumn);
        bodyTable.getColumns().addAll(rowNumColumn, itemColumn, quantityColumn, priceColumn);
        totalsTable.getColumns().addAll(totalsLabelColumn, totalsValueColumn);
        
        bodyTable.setPlaceholder(new Label(""));
        totalsTable.setPlaceholder(new Label(""));

        // FORMAT HEADER
        systemColumn.setPrefWidth(TABLE_WIDTH / 3);
        userColumn.setPrefWidth(TABLE_WIDTH / 3);
        checkNumColumn.setPrefWidth(TABLE_WIDTH / 3);
        headerTable.setPrefWidth(TABLE_WIDTH);
        
        headerTable.setPrefHeight(ROW_HEIGHT * 3);
        headerTable.setId("headerTable"); // for CSS formatting
        
        // FORMAT BODY
        rowNumColumn.setPrefWidth(30);
        double bodyColumnWidth = (TABLE_WIDTH - rowNumColumn.getWidth()) / 3;
        itemColumn.setPrefWidth(bodyColumnWidth);
        quantityColumn.setPrefWidth(bodyColumnWidth);
        priceColumn.setPrefWidth(bodyColumnWidth);
        bodyTable.setFixedCellSize(ROW_HEIGHT);
        bodyTable.setPrefHeight(BODY_HEIGHT);
        bodyTable.setPrefWidth(TABLE_WIDTH);
        
        // FORMAT TOTALS
        totalsLabelColumn.setPrefWidth(TABLE_WIDTH * 0.72);
        totalsValueColumn.setPrefWidth(TABLE_WIDTH * 0.25);
        totalsTable.setId("valuesTable");
        totalsTable.setPrefWidth(TABLE_WIDTH);
        totalsTable.setPrefHeight(TOTALS_HEIGHT);
        totalsTable.setFixedCellSize(ROW_HEIGHT);
        
        // INVENTORY TABLE
        TableColumn<InventoryData, String> invColumn1 = new TableColumn<>("");
        TableColumn<InventoryData, String> invColumn2 = new TableColumn<>("");
        TableColumn<InventoryData, String> invColumn3 = new TableColumn<>("");
        
        invColumn1.setCellValueFactory(cellData -> cellData.getValue().column1Property());
        invColumn2.setCellValueFactory(cellData -> cellData.getValue().column2Property());
        invColumn3.setCellValueFactory(cellData -> cellData.getValue().column3Property());
        
        inventoryTable.getColumns().addAll(invColumn1, invColumn2, invColumn3);
        ObservableList<InventoryData> inventoryData = FXCollections.observableArrayList(
                new InventoryData("UPC:", "", ""),
                new InventoryData("Item Name:", "", ""),
                new InventoryData("Price:", "", ""),
                new InventoryData("Weight (kg):", "", ""),
                new InventoryData("Length (cm):", "", ""),
                new InventoryData("Width (cm):", "", ""),
                new InventoryData("Height (cm):", "", ""),
                new InventoryData("In Stock:", "", ""),
                new InventoryData("Most Recent Sale:", "", "")
            );

        inventoryTable.setItems(inventoryData);
        inventoryTable.setFixedCellSize(ROW_HEIGHT);
        inventoryTable.setPrefHeight(SCENE_HEIGHT);
        inventoryTable.setPrefWidth(TABLE_WIDTH);
        
        // DECLARE BUTTONS
        btn_sw_opencheck = new Button("Open Check");
        btn_inv = new Button("Inventory\nReport");
        btn_sales = new Button("Sales Report");
        btn_closedcheck = new Button("Closed Checks");
        btn_history = new Button("Inventory\nHistory");
        btn_edit = new Button("Edit Inventory");
        
        btn_sw_manager = new Button("Open Manager");
        button_pay = new Button("Pay");
        button_remove = new Button("Remove Item");
        button_quantity = new Button("Edit Item\nQuantity");
        button_UPC = new Button("Enter UPC");

        // FORMAT BUTTONS
        btn_sw_opencheck.setPrefSize(BUTTON_WIDTH, BUTTON_HEIGHT);
        btn_inv.setPrefSize(BUTTON_WIDTH, BUTTON_HEIGHT);
        btn_sales.setPrefSize(BUTTON_WIDTH, BUTTON_HEIGHT);
        btn_closedcheck.setPrefSize(BUTTON_WIDTH, BUTTON_HEIGHT);
        btn_history.setPrefSize(BUTTON_WIDTH, BUTTON_HEIGHT);
        btn_edit.setPrefSize(BUTTON_WIDTH, BUTTON_HEIGHT);

        btn_sw_manager.setPrefSize(BUTTON_WIDTH, BUTTON_HEIGHT);
        button_pay.setPrefSize(BUTTON_WIDTH, BUTTON_HEIGHT);
        button_remove.setPrefSize(BUTTON_WIDTH, BUTTON_HEIGHT);
        button_quantity.setPrefSize(BUTTON_WIDTH, BUTTON_HEIGHT);
        button_UPC.setPrefSize(BUTTON_WIDTH, BUTTON_HEIGHT);

        // BUTTON HANDLERS
        btn_sw_opencheck.setOnAction(this);
        btn_inv.setOnAction(this);
        btn_sales.setOnAction(this);
        btn_closedcheck.setOnAction(this);
        btn_history.setOnAction(this);
        btn_edit.setOnAction(this);
        
        btn_sw_manager.setOnAction(this);
        button_pay.setOnAction(this);
        button_remove.setOnAction(this);
        button_quantity.setOnAction(this);
        button_UPC.setOnAction(this);
        
        // SET LAYOUTS
        VBox btn_layout2 = new VBox(10);
        btn_layout2.getChildren().addAll(btn_sw_opencheck, btn_inv, btn_sales, btn_closedcheck, btn_history, btn_edit);
        btn_layout2.setPadding(new Insets(10,10,0,0));
        
        VBox table2_layout = new VBox();
        table2_layout.getChildren().addAll(inventoryTable);
        
        BorderPane layout2 = new BorderPane();
        layout2.setRight(btn_layout2);
        layout2.setLeft(table2_layout);
        
        // Create VBox for buttons with spacing and padding
        VBox btn_layout1 = new VBox(10); // Vertical layout for buttons with 10 units spacing
        btn_layout1.getChildren().addAll(btn_sw_manager, button_pay, button_remove, button_quantity, button_UPC);
        btn_layout1.setPadding(new Insets(10, 10, 0, 0)); // Top, Right, Bottom, Left padding

        VBox table_layout = new VBox();
        table_layout.getChildren().addAll(headerTable, bodyTable, totalsTable);
        
        // Set up the layout
        BorderPane layout1 = new BorderPane();
        layout1.setLeft(table_layout); // Add the table to the left side
        layout1.setRight(btn_layout1); // Add buttons to the right side

        // Set up the scene and show the stage
        scene_manager = new Scene(layout2, SCENE_WIDTH, SCENE_HEIGHT);
        scene_check = new Scene(layout1, SCENE_WIDTH, SCENE_HEIGHT); // Set the scene height to match the table height
        
        // CSS
        String css = 
    			".table-row-cell:empty { -fx-background-color: white; }\n" +
                ".table-row-cell:empty .table-cell { -fx-border-width: 0px; }" +
                "#headerTable .table-cell { -fx-alignment: CENTER; }\n" +
                "#valuesTable .table-cell { -fx-alignment: CENTER-RIGHT; }\n";
        scene_manager.getStylesheets().add("data:," + css);
        scene_check.getStylesheets().add("data:," + css);
        
        // FINAL SETUP
        primaryStage.setScene(scene_check);
        primaryStage.show();
        Pane header = (Pane) totalsTable.lookup("TableHeaderRow");
        if (header.isVisible()) {
            header.setMaxHeight(0);
            header.setMinHeight(0);
            header.setPrefHeight(0);
            header.setVisible(false);
        }
        Platform.runLater(() -> {
            // Disabling scrollbars for marginTable
            ScrollPane marginTableScroll = (ScrollPane) headerTable.lookup(".scroll-pane");
            if (marginTableScroll != null) {
                marginTableScroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);  // Disable vertical scroll bar
                marginTableScroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);  // Disable horizontal scroll bar
            }

            // Disabling scrollbars for invTable
            ScrollPane invTableScroll = (ScrollPane) bodyTable.lookup(".scroll-pane");
            if (invTableScroll != null) {
                invTableScroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);  // Disable vertical scroll bar
                invTableScroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);  // Disable horizontal scroll bar
            }

            // Disabling scrollbars for totalsTable
            ScrollPane totalsTableScroll = (ScrollPane) totalsTable.lookup(".scroll-pane");
            if (totalsTableScroll != null) {
                totalsTableScroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);  // Disable vertical scroll bar
                totalsTableScroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);  // Disable horizontal scroll bar
            }
        });
    }

    @Override
    public void handle(ActionEvent event) {
    	if (event.getSource() == btn_sw_manager) {
    	    primaryStage.setScene(scene_manager);  
    	    primaryStage.setTitle("Manager Window");
    	} else if (event.getSource() == btn_sw_opencheck) {
    	    primaryStage.setScene(scene_check); 
    	    primaryStage.setTitle("Open-Check Window");
    	}
    	if (event.getSource() == btn_inv) {
            System.out.println("Pressed Inv Report - Feature coming soon");
        }
        if (event.getSource() == btn_sales) {
            System.out.println("Pressed Sales History - Feature coming soon");
        }
        if (event.getSource() == btn_closedcheck) {
            System.out.println("Pressed Closed Checks - Feature coming soon");
        }
        if (event.getSource() == btn_history) {
            System.out.println("Pressed Inventory History - Feature coming soon");
        }
        if (event.getSource() == btn_edit) {
            System.out.println("Pressed Edit Inventory - Feature coming soon");
            
        }
        if (event.getSource() == button_remove) {
            openRemoveUPCStage(bodyTable);
            
        }
        if (event.getSource() == button_quantity) {
            openQuantityUpdateStage(bodyTable);
        }
        if (event.getSource() == button_UPC) {
        	openUPCEntryStage(bodyTable);
        }
        	
        if (event.getSource() == button_pay) {
            activeCheck.checkout();
            activeCheck = Inventory.nextCheck();
            ObservableList<HeaderData> headerData = FXCollections.observableArrayList(new HeaderData(activeCheck.checkNum));
            headerTable.setItems(headerData);
            bodyTable.setItems(Inventory.getBodyDataOL(activeCheck));
            totalsTable.setItems(Inventory.getTotalsDataOL(activeCheck));
        }
    }
    
    private boolean addUPCToTable(String upc) {
        boolean successful = activeCheck.addItem(upc);
        bodyTable.setItems(Inventory.getBodyDataOL(activeCheck));
        totalsTable.setItems(Inventory.getTotalsDataOL(activeCheck));
        return successful;
    }
    
    private void openQuantityInputStage(Stage parentStage, String upc, TableView<BodyData> table) {
        
        Stage quantityInputStage = new Stage();
        Label quantityLabel = new Label("Enter Quantity (0-99):");
        TextField quantityInput = new TextField();
        quantityInput.setPromptText("Quantity");
        Button submitButton = new Button("Submit");
        Label feedbackLabel = new Label();
        VBox quantityLayout = new VBox(10, quantityLabel, quantityInput, submitButton, feedbackLabel);
        quantityLayout.setAlignment(Pos.CENTER);
        quantityLayout.setPadding(new Insets(10));
        Scene quantityScene = new Scene(quantityLayout, 300, 200);
        quantityInputStage.setTitle("Enter Quantity");
        quantityInputStage.setScene(quantityScene);
        quantityInputStage.show();
        parentStage.close();
        submitButton.setOnAction(event -> {
            String quantityStr = quantityInput.getText().trim();
            if (quantityStr.matches("\\d{0,3}")) { //0-99 CAN CHANGE
                int quantity = Integer.parseInt(quantityStr);
                if (quantity >= 0 && quantity <= 99) {
                    activeCheck.editQuantity(upc, quantity);
                    bodyTable.setItems(Inventory.getBodyDataOL(activeCheck));
                    totalsTable.setItems(Inventory.getTotalsDataOL(activeCheck));
                    quantityInputStage.close();
                } else {
                    feedbackLabel.setText("Quantity must be between 0 and 99.");
                }
            } else {
                feedbackLabel.setText("Invalid quantity. Please enter a number.");
            }
        });
    }
    
    private void openQuantityUpdateStage(TableView<BodyData> table) {
        
        Stage quantityStage = new Stage();
        Label upcLabel = new Label("Enter Row Number:");
        TextField rowInput = new TextField();
        rowInput.setPromptText("Row Number");
        Button validateUPCButton = new Button("Validate");
        Label feedbackLabel = new Label();
        VBox upcLayout = new VBox(10, upcLabel, rowInput, validateUPCButton, feedbackLabel);
        upcLayout.setAlignment(Pos.CENTER);
        upcLayout.setPadding(new Insets(10));
        Scene upcScene = new Scene(upcLayout, 300, 200);
        quantityStage.setTitle("Update Quantity");
        quantityStage.setScene(upcScene);
        quantityStage.show();
        validateUPCButton.setOnAction(event -> {
            String upc = findUPCByRowNumber(table, Integer.parseInt(rowInput.getText().trim()));
            if (upc != null) {
                // UPC exists; proceed to quantity input stage
                openQuantityInputStage(quantityStage, upc, table);
            } else {
                feedbackLabel.setText("Invalid Row Number. Please try again.");
            }
        });
    }
    
    private void openRemoveUPCStage(TableView<BodyData> table) {
        Stage removeStage = new Stage();
        Label upcLabel = new Label("Enter the row to remove:");
        TextField rowInput = new TextField();
        rowInput.setPromptText("Row number");
        Button validateButton = new Button("Remove");
        Label feedbackLabel = new Label();
        //Layout for UPC input
        VBox layout = new VBox(10, upcLabel, rowInput, validateButton, feedbackLabel);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(10));
        Scene scene = new Scene(layout, 300, 200);
        removeStage.setTitle("Remove Item");
        removeStage.setScene(scene);
        removeStage.show();
        //Handle UPC validation
        validateButton.setOnAction(event -> {
        	int row = Integer.parseInt(rowInput.getText());
        	String upc = findUPCByRowNumber(table, row);
        	if (upc != null) {
        		openConfirmationStage(removeStage, table, upc);
            } else {
                feedbackLabel.setText("Row not found. Please try again.");
            }
        });
    }
    
    private void openConfirmationStage(Stage parentStage, TableView<BodyData> table, String upc) {
        //Close the parent stage
        parentStage.close();

        //Create a new Stage
        Stage confirmationStage = new Stage();
        //Create UI elements
        Label confirmationLabel = new Label("Are you sure you want to remove Item: " + Inventory.getEntry(upc).name + "?");
        Button confirmButton = new Button("Confirm");
        //Layout for confirmation
        VBox layout = new VBox(10, confirmationLabel, confirmButton);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(10));
        Scene scene = new Scene(layout, 300, 150);
        confirmationStage.setTitle("Confirm Removal");
        confirmationStage.setScene(scene);
        confirmationStage.show();
        //Handle confirmation
        confirmButton.setOnAction(event -> {
        	activeCheck.editQuantity(upc, 0);
        	table.setItems(Inventory.getBodyDataOL(activeCheck));
        	totalsTable.setItems(Inventory.getTotalsDataOL(activeCheck));
            confirmationStage.close();
        });
    }
    
    private void openUPCEntryStage(TableView<BodyData> table) {
        //Create a new Stage
        Stage upcStage = new Stage();
        //Create UI elements
        Label label = new Label("Enter a UPC:");
        TextField upcInput = new TextField();
        upcInput.setPromptText("UPC Number");
        Button submitButton = new Button("Submit");
        Label feedbackLabel = new Label();
        // Handle submit button action
        submitButton.setOnAction(event -> {
            String upc = upcInput.getText().trim();
            if (addUPCToTable(upc)) {
                feedbackLabel.setText("Item added: " + Inventory.getEntry(upc).name);
                upcInput.clear();
            } else {
                feedbackLabel.setText("Invalid UPC.");
            }
        });
        VBox layout = new VBox(10, label, upcInput, submitButton, feedbackLabel);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(10));
        Scene upcScene = new Scene(layout, 300, 200);
        upcStage.setTitle("Enter UPC");
        upcStage.setScene(upcScene);
        upcStage.show();
    }

    private String findUPCByRowNumber(TableView<BodyData> table, int rowNumber) {
        ObservableList<BodyData> rows = bodyTable.getItems();
        for (BodyData row : rows) {
            if (Integer.parseInt(row.getRowNum()) == rowNumber) {
                return row.getUPC();
            }
        }

        return null;
    }

    
    public static void main(String[] args) {
        launch(args);
    }
}

package com.library.view;

import com.library.model.Book;
import com.library.model.Member;
import com.library.viewmodel.MainViewModel;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

public class MainController {
    @FXML private Label welcomeLabel;
    @FXML private Label statusLabel;
    
    // Tab 1: Catalog
    @FXML private TableView<Book> booksTable;
    @FXML private TableColumn<Book, String> titleCol;
    @FXML private TableColumn<Book, String> authorCol;
    @FXML private TableColumn<Book, Integer> copiesCol;
    
    // Tab 2: My Issued Books
    @FXML private TableView<MainViewModel.IssuedBookInfo> issuedTable;
    @FXML private TableColumn<MainViewModel.IssuedBookInfo, String> issuedTitleCol;
    @FXML private TableColumn<MainViewModel.IssuedBookInfo, String> issuedDateCol;
    
    // Admin Form
    @FXML private VBox adminPanel;
    @FXML private TextField titleField;
    @FXML private TextField authorField;
    @FXML private TextField isbnField;

    private MainViewModel viewModel;

    public void initData(Member member) {
        this.viewModel = new MainViewModel(member);
        setupBindings();
        setupUI();
    }

    private void setupBindings() {
        // Bind Catalog Data
        booksTable.setItems(viewModel.getBooks());
        
        // Bind Issued Books Data
        issuedTable.setItems(viewModel.getIssuedBooks());
        
        // Form Binding
        titleField.textProperty().bindBidirectional(viewModel.titleInputProperty());
        authorField.textProperty().bindBidirectional(viewModel.authorInputProperty());
        isbnField.textProperty().bindBidirectional(viewModel.isbnInputProperty());
        
        // Status Message
        statusLabel.textProperty().bind(viewModel.statusMessageProperty());
    }

    private void setupUI() {
        welcomeLabel.setText("User: " + viewModel.getCurrentUser().getUsername() + " (" + viewModel.getCurrentUser().getRole() + ")");
        
        // Setup Catalog Columns
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        authorCol.setCellValueFactory(new PropertyValueFactory<>("author"));
        copiesCol.setCellValueFactory(new PropertyValueFactory<>("availableCopies"));
        
        // Setup Issued Books Columns
        issuedTitleCol.setCellValueFactory(new PropertyValueFactory<>("bookTitle"));
        issuedDateCol.setCellValueFactory(new PropertyValueFactory<>("issueDate"));
        
        // Show/Hide Admin Panel
        adminPanel.setVisible(viewModel.isAdmin());
        // Collapse the space if not admin
        adminPanel.setManaged(viewModel.isAdmin());
    }

    @FXML
    private void handleAddBook() {
        viewModel.addBook();
    }

    @FXML
    private void handleIssueBook() {
        Book selected = booksTable.getSelectionModel().getSelectedItem();
        viewModel.issueSelectedBook(selected);
    }
    
    @FXML
    private void handleReturnBook() {
        MainViewModel.IssuedBookInfo selected = issuedTable.getSelectionModel().getSelectedItem();
        viewModel.returnSelectedBook(selected);
    }
}
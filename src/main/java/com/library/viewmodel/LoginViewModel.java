package com.library.viewmodel;

    import com.library.model.Member;
    import com.library.service.LMSService;
    import javafx.beans.property.*;

    public class LoginViewModel {
        private final LMSService service;
        
        // Properties for View Binding
        private final StringProperty username = new SimpleStringProperty();
        private final StringProperty password = new SimpleStringProperty();
        private final StringProperty loginMessage = new SimpleStringProperty();
        
        // Holds the authenticated user to pass to the next view
        private final ObjectProperty<Member> authenticatedMember = new SimpleObjectProperty<>();

        public LoginViewModel() {
            this.service = new LMSService();
        }

        public boolean login() {
            Member member = service.authenticateUser(username.get(), password.get());
            if (member != null) {
                authenticatedMember.set(member);
                loginMessage.set("Login Successful");
                return true;
            } else {
                loginMessage.set("Invalid Credentials");
                return false;
            }
        }

        public StringProperty usernameProperty() { return username; }
        public StringProperty passwordProperty() { return password; }
        public StringProperty loginMessageProperty() { return loginMessage; }
        public ObjectProperty<Member> authenticatedMemberProperty() { return authenticatedMember; }
    }

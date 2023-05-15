package controller;

import model.User;

public class ProfileController {
  private final  User currentUser;

    public ProfileController(User currentUser) {
        this.currentUser = currentUser;
    }


}

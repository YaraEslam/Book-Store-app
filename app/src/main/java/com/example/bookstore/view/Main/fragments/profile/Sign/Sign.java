package com.example.bookstore.view.Main.fragments.profile.Sign;

public interface Sign {

    void nameError();

    void emailError(String error);

    void phoneError();

    void addressError();

    void passwordError();

    void validCradintial();

    void inValidCradintial();
}

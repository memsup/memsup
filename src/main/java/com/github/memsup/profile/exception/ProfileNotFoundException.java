package com.github.memsup.profile.exception;

import java.util.function.Supplier;

public class ProfileNotFoundException extends Exception implements Supplier {

    public ProfileNotFoundException() {

    }

    public ProfileNotFoundException(String errorMessage) {
        super(errorMessage);
    }

    @Override
    public Object get() {
        return new ProfileNotFoundException();
    }
}

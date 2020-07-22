package pers.jaxon.funtravel.exception;

public class EmailHasBeenRegisteredException extends RuntimeException{
    private static final long serialVersionUID = -6074753940710869977L;

    public EmailHasBeenRegisteredException(String email) {
        super("Email '" + email + "' has been registered");
    }
}



package computah.client;

import computah.exception.ComputahException;

/**
 * Represents a client whose contact information is managed by Computah.
 */
public class Client {
    private static final String FILE_DELIMITER = " | ";

    private String name;
    private String phone;
    private String email;

    /**
     * Creates a client with a required name and optional contact details.
     *
     * @param name client's name.
     * @param phone client's phone number, or an empty string if absent.
     * @param email client's email address, or an empty string if absent.
     * @throws ComputahException if a field cannot be represented safely.
     */
    public Client(String name, String phone, String email) throws ComputahException {
        update(name, phone, email);
    }

    /**
     * Updates all supplied fields after validating them together.
     *
     * @param name new name, or null if unchanged.
     * @param phone new phone number, or null if unchanged.
     * @param email new email address, or null if unchanged.
     * @throws ComputahException if a supplied value is invalid.
     */
    public void update(String name, String phone, String email) throws ComputahException {
        if (name != null) {
            validateField(name);
            if (name.isBlank()) {
                throw new ComputahException("The name of a client cannot be empty.");
            }
        }
        if (phone != null) {
            validateField(phone);
        }
        if (email != null) {
            validateField(email);
        }

        if (name != null) {
            this.name = name.trim();
        }
        if (phone != null) {
            this.phone = phone.trim();
        }
        if (email != null) {
            this.email = email.trim();
        }
    }

    /**
     * Converts this client into one line for the save file.
     *
     * @return save-file representation of this client.
     */
    public String toFileString() {
        return "C" + FILE_DELIMITER + name + FILE_DELIMITER + phone + FILE_DELIMITER + email;
    }

    /**
     * Returns the compact display representation of this client.
     *
     * @return client name, phone number, and email address.
     */
    @Override
    public String toString() {
        return name + " [phone: " + valueOrPlaceholder(phone) + "] [email: "
                + valueOrPlaceholder(email) + "]";
    }

    /**
     * Returns a stored value or the placeholder used for an absent value.
     *
     * @param value stored field value.
     * @return stored value, or {@code -} when it is empty.
     */
    private static String valueOrPlaceholder(String value) {
        return value.isEmpty() ? "-" : value;
    }

    /**
     * Checks that a client field can be represented safely in one save-file line.
     *
     * @param value client field value.
     * @throws ComputahException if the value contains a reserved delimiter or line break.
     */
    private static void validateField(String value) throws ComputahException {
        if (value.contains(FILE_DELIMITER)) {
            throw new ComputahException("Client details cannot contain \" | \".");
        }
        if (value.contains("\n") || value.contains("\r")) {
            throw new ComputahException("Client details cannot contain line breaks.");
        }
    }
}

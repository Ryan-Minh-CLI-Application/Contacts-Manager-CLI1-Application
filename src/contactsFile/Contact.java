package contactsFile;

class Contact {
    String name;
    String phoneNumber;

    public Contact(String name, String phoneNumber) {
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        String formattedPhoneNumber;
        if(phoneNumber.length() > 7) {
            formattedPhoneNumber = phoneNumber.substring(0, 3) + "-" + phoneNumber.substring(3, 6)
                    + "-" + phoneNumber.substring(6);
        } else {
            formattedPhoneNumber = phoneNumber.substring(0, 3) + "-" + phoneNumber.substring(3);
        }
        return name + " | " + formattedPhoneNumber;
    }
}
package models;

public class Provider {
    private String providerId;
    private String name;
    private String specialization;
    private String contact;

    public Provider(String providerId, String name, String specialization, String contact) {
        this.providerId     = providerId;
        this.name           = name;
        this.specialization = specialization;
        this.contact        = contact;
    }

    public String getProviderId()    { return providerId; }
    public String getName()          { return name; }
    public String getSpecialization(){ return specialization; }
    public String getContact()       { return contact; }

    @Override
    public String toString() {
        return String.format("Provider[%s] - %s (%s)", providerId, name, specialization);
    }
}

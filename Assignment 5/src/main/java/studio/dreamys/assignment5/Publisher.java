package studio.dreamys.assignment5;

public class Publisher {
    private int pubId;
    private String name;
    private String contact;
    private String phone;

    public Publisher(int pubId) {
        this.pubId = pubId;
    }

    public Publisher(String name, String contact, String phone) {
        this.name = name;
        this.contact = contact;
        this.phone = phone;
    }

    public Publisher(int pubId, String name, String contact, String phone) {
        this.pubId = pubId;
        this.name = name;
        this.contact = contact;
        this.phone = phone;
    }

    public int getPubId() {
        return pubId;
    }

    public void setPubId(int pubId) {
        this.pubId = pubId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "Publisher{" +
                "pubId=" + pubId +
                ", name='" + name + '\'' +
                ", contact='" + contact + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}

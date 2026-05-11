package MODEL;

public class customer {

    private int id;
    private String name;
    private String meternumber;
    private String phone;
    private String address;

    public customer(int id, String name, String meternumber, String phone, String address) {
        this.id = id;
        this.name = name;
        this.meternumber = meternumber;
        this.phone = phone;
        this.address = address;
    }
    public int getId() { return id; }
    public String getName() { return name; }
    public String getmeternumber() { return meternumber; }
    public String getphone() { return phone; }
    public String getAddress() {return address;}
}

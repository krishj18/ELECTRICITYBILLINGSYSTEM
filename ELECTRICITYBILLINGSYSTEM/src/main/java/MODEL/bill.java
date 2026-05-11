package MODEL;

import java.util.Date;

public class bill {
    private int id;
    private int customerid;
    private String customername;
    private int unitsconsumed;
    private double amount;
    private Date billdate;
    private String status;

    //constructor

    public bill(int id, int customerid, String customername,
                int unitsconsumed, double amount, Date billdate, String status) {
        this.id = id;
        this.customerid = customerid;
        this.customername = customername;
        this.unitsconsumed = unitsconsumed;
        this.amount = amount;
        this.billdate = billdate;
        this.status = status;
    }
        //getters
    public int getId() {return id;}

    public int getcustomerid() {return customerid;}

    public String getcustomername() {return customername;}

    public int getunitsconsumed() {return unitsconsumed;}

    public double getamount() {return amount;}

    public Date getbilldate() {return billdate;}

    public String getStatus() {return status;}

    //setters
    public void setAmount(double amount) {this.amount = amount;}

    public void setStatus(String status) {this.status = status;}

    public String toString(){
        return "Bill{" +
                "id=" + id +
                ", customer=" + customername +
                ", units=" + unitsconsumed +
                ", amount=Rs." + amount +
                ", status=" + status +
                '}';
    }
    }



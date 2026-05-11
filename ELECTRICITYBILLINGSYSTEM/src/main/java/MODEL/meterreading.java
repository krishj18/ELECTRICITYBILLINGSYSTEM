
package MODEL;

import java.util.Date;

public class meterreading {
    private int id;
    private int customerId;
    private String customerName;
    private String meterNumber;
    private int previousReading;
    private int currentReading;
    private int unitsConsumed;
    private Date readingDate;


    public meterreading(int id, int customerId, String customerName, String meterNumber,
                        int previousReading, int currentReading, Date readingDate) {
        this.id = id;
        this.customerId = customerId;
        this.customerName = customerName;
        this.meterNumber = meterNumber;
        this.previousReading = previousReading;
        this.currentReading = currentReading;
        this.unitsConsumed = currentReading - previousReading;
        this.readingDate = readingDate;
    }

    public int getId() { return id; }
    public int getCustomerId() { return customerId; }
    public String getCustomerName() { return customerName; }
    public String getMeterNumber() { return meterNumber; }
    public int getPreviousReading() { return previousReading; }
    public int getCurrentReading() { return currentReading; }
    public int getUnitsConsumed() { return unitsConsumed; }
    public Date getReadingDate() { return readingDate; }

    public boolean isValid() {
        return currentReading >= previousReading;
    }

    public String toString() {
        return "meterReading{" +
                "customer=" + customerName +
                ", previous=" + previousReading +
                ", current=" + currentReading +
                ", units=" + unitsConsumed +
                '}';
    }
}



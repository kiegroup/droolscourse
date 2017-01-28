package cost;

public class CalculatedElement {
    private String key;
    private int numberValue;
    private String stringValue;
    private long longValue;
    private double doubleValue;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int getNumberValue() {
        return numberValue;
    }

    public void setNumberValue(int numberValue) {
        this.numberValue = numberValue;
    }

    public String getStringValue() {
        return stringValue;
    }

    public void setStringValue(String stringValue) {
        this.stringValue = stringValue;
    }

    public long getLongValue() {
        return longValue;
    }

    public void setLongValue(long longValue) {
        this.longValue = longValue;
    }

    public double getDoubleValue() {
        return doubleValue;
    }

    public void setDoubleValue(double doubleValue) {
        this.doubleValue = doubleValue;
    }

    @Override
    public String toString() {
        return "CalculatedElement [key=" + key + ", numberValue=" + numberValue + ", stringValue=" + stringValue
                + ", longValue=" + longValue + ", doubleValue=" + doubleValue + "]";
    }


}

package model;

public class Amount {
	 private double value;
	    private String currency;

	    public Amount(double value, String currency) {
	        this.value = value;
	        this.currency = "€";
	    }

	    public double getValue() {
	        return value;
	    }

	    public void setValue(double value) {
	        this.value = value;
	    }

	    public String getCurrency() {
	        return currency;
	    }

	    public void setCurrency(String currency) {
	        this.currency = currency;
	    }

	    @Override
	    public String toString() {
	        return value + currency;
	    }

		public static Amount valueOf(String value2) {
			// TODO Auto-generated method stub
			return null;
		}
}

package model;

import main.Payable;

public class Cliente extends Person implements Payable {
	private int memberID;
	private Amount cantidad;

	public Cliente(String name, int memberID, Amount cantidad) {
		super(name);
		this.memberID = MEMBER_ID;
		this.cantidad = BALANCE;
	}

	public Cliente() {

	}

	final static int MEMBER_ID = 456;
	final static Amount BALANCE = new Amount(50.00, "€");

	public static int getMemberId() {
		return MEMBER_ID;
	}

	public static Amount getBalance() {
		return BALANCE;
	}

	public Amount getCantidad() {
		return cantidad;
	}

	public void setCantidad(Amount cantidad) {
		this.cantidad = cantidad;
	}

	public int getMemberID() {
		return memberID;
	}

	public void setMemberID(int memberID) {
		this.memberID = memberID;
	}
	

	@Override
	public boolean pay(Amount amount) {
		double price = getCantidad().getValue() - amount.getValue();
		Amount newBalance = new Amount(price , "€");
		setCantidad(newBalance);
		if (price >= 0) {
			return true;
			
		} else {
			return false;
		}
	}
}

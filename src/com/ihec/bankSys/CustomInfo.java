package com.ihec.bankSys;

public class CustomInfo {
//the property of each customer
	int _accountNo;
	String _fullName;
	double _amount;

	//the three following methods for setter the property
	public void set_accountNo(int accountNo) {
		this._accountNo = accountNo;
	}

	public void set_fullName(String fullName) {
		this._fullName = fullName;
	}

	public void set_amount(double amount) {
		this._amount = amount;
	}

	//the three following methods for getter the property
	public int get_accountNo() {
		return this._accountNo;
	}

	public String get_fullName() {
		return this._fullName;
	}

	public double get_amount() {
		return this._amount;
	}

}

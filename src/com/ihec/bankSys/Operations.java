package com.ihec.bankSys;
public class Operations {
	//Variables
	double _amount = 0.0d;

	//method with parameters for amount operations
	//this method return boolean value for validation also the messages
	public String do_operation(int op, CustomInfo CI, String amount) {
		switch (op) {
		case 1:
			try {
				_amount = Double.parseDouble(amount);
				if (CI.get_amount() >= _amount)
					{CI.set_amount(CI.get_amount() - _amount);
					return "Done";
					}
				else
				{
					return "Cannot Withdraw !!!";
				}
			} catch (Exception ex) {
				return "Please Enter correct amount ... " + ex.getMessage();
			}
		case 2:
			try {
				_amount = Double.parseDouble(amount);
				if (0 < _amount)
				{
					CI.set_amount(CI.get_amount() + _amount);
					return "Done";
				}
				else {
					return "Cannot Deposition !!!";
				}
			} catch (Exception ex) {
				return "Please Enter correct amount ... " + ex.getMessage();
			}
		}
		return "";
	}
}

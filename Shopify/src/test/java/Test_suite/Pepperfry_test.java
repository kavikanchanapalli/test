package Test_suite;

import Pages.Pepperfry;

public class Pepperfry_test
{
	public static void main(String[] args) throws Exception {

		Pepperfry pr=new Pepperfry();
		pr.openUrl();
		pr.closePopupLogin();
		pr.gotoFurniture();
		pr.selectBench();
		pr.validateTitle();
		pr.getAllProducts();
		pr.displayProducts();
		pr.closebrowser();

	}
	
}

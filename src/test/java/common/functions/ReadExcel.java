package common.functions;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class ReadExcel {

	//public static final Logger logger = LogManager.getLogger("Read Data From Excel file");
	public String dataTest = "data";
	private Object[][] excelData = null;
	private String testName;
	private String tcTitle=null;
	private String userInput=null;
	private String rawData=null;
	private String isRun=null;
	private List<String[]> varArray = new ArrayList<String[]>();
	private String[] dataArray;
	
	public ReadExcel() {
		
	}
	
	public ReadExcel(String testName) {
		Object [][] arrayObject = getExcelData(System.getProperty("user.dir")+ File.separator + "data" + File.separator + dataTest + ".xls","TestData");
		this.excelData = arrayObject;
		this.testName = testName;
		extractVar();
	}

	public String getConfigData(String key) {
		Object[][] arrayObject = getExcelData(
				System.getProperty("user.dir") + File.separator + "data" + File.separator + dataTest + ".xls",
				"Configuration");
		String value = null;
		for (int i = 0; i < arrayObject.length; i++) {
			if (arrayObject[i][0].toString().toLowerCase().equals(key.toLowerCase())) {
				value = arrayObject[i][1].toString();
				break;
			}
		}
		if (value == null) {
			System.out.println("Unable to find test case");
		}
		return value;
	}

	public static String[][] getExcelData(String fileName, String sheetName) {
		String[][] arrayExcelData = null;
		try {
			FileInputStream fs = new FileInputStream(fileName);
			Workbook wb = Workbook.getWorkbook(fs);
			Sheet sh = wb.getSheet(sheetName);

			int totalNoOfCols = sh.getColumns();
			int totalNoOfRows = sh.getRows();

			arrayExcelData = new String[totalNoOfRows - 1][totalNoOfCols];

			for (int i = 1; i < totalNoOfRows; i++) {

				for (int j = 0; j < totalNoOfCols; j++) {
					arrayExcelData[i - 1][j] = sh.getCell(j, i).getContents();
				}

			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
			e.printStackTrace();
		} catch (BiffException e) {
			e.printStackTrace();
		}
		return arrayExcelData;
	}
	
	private void extractVar() {
		for (int i = 0; i < excelData.length; i++) {
			System.out.println("TEST NAME: " + excelData[i][0].toString().toLowerCase());
			if (excelData[i][0].toString().toLowerCase().equals(testName.toLowerCase())) {
				rawData = excelData[i][2].toString();
				System.out.println("RAW: " + rawData);
				break;
			}
		}
		if (rawData.isEmpty()) {
			System.out.println("Unable to find test case");
		}
		else {
			dataArray = rawData.split("\n");
			for (int i = 0; i < dataArray.length; i++) {
				varArray.add(i, dataArray[i].split("="));
			}
		}
	}
	
	public String getVar(String varName){
		String result = null;
		for (int i = 0; i < varArray.size(); i++) {
			if (varArray.get(i)[0].equals(varName)) {
				result = varArray.get(i)[1];
				break;
			}
		}
		if(result == null) System.out.println("Unable to find variable " + varName);
		return result;
	}

}

package excelLibrary;

import java.io.IOException;

import baseClassLibrary.BaseClass;

import java.io.File;


import jxl.Sheet;
import jxl.Workbook;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.read.biff.BiffException;

import jxl.write.Label;

import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

public class ExcelLib extends BaseClass{

	private Workbook workbook;
	private Sheet worksheet;
	private int rows;
	private String testCaseName;
	private int testCaseStartRow = 0;
	private int testCaseEndRow = 0;
	private int usedColumnsCount = 0;
	private int iterationCount = 0;
	private WritableSheet sheet;
	private static Label label;
	private static WritableFont font;
	private static WritableCellFormat cellStyle;

	public ExcelLib(String workSheetName, String testCaseName) throws BiffException, IOException {
		//Added for loading the data from the property file.
	/*	FileInputStream fis = new FileInputStream(System.getProperty("user.dir")+"\\src\\test\\resources\\ProjectSettings.properties");
		Properties config = new Properties();
	    config.load(fis);*/
	
	    
	    workbook = Workbook.getWorkbook(new File("C://Users//vt29//PersonalWorkspace//NoBroker_Project//src//test//resources//TEST DATA//NoBroker_InputData.xls"));
		worksheet = workbook.getSheet(workSheetName);
		this.testCaseName = testCaseName;
		rows = getRowCount();
		testCaseStartRow = getTestCaseStartRow();
		testCaseEndRow = getTestCaseEndRow();
		usedColumnsCount = getUsedColumnsCount();
		iterationCount = getIterationCount();
	}

	public ExcelLib() {
		// Dummy Constructor
	}

	private int getIterationCount() {
		try {
			for (int i = testCaseStartRow; i <= testCaseEndRow; i++) {
				if (getCellData(usedColumnsCount, i).equalsIgnoreCase("Yes")) {
					iterationCount++;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (iterationCount > 0) {
			System.out.println("*************************************************************************************");
			System.out.println("Total number of iterations selected for test script: '" + testCaseName + "' is" + " "
					+ iterationCount);
			System.out.println("*************************************************************************************");
		} else {
			System.out.println("*************************************************************************************");
			System.out.println(
					"Total number of iterations selected is 0. Please check execute column in TestData.xls file");
			System.out.println("*************************************************************************************");
		}

		return iterationCount;
	}

	/*
	 * public WritableWorkbook writeableWorkbook(String sheetName) throws
	 * IOException { WritableWorkbook WriteWorkbook =
	 * workbook.createWorkbook(new File("temp.xls"), workbook); return
	 * WriteWorkbook; }
	 * 
	 * public WritableSheet WorkSheet(WritableWorkbook WriteWorkbook, String
	 * sheetName) { WritableSheet sheet = WriteWorkbook.getSheet(sheetName);
	 * return sheet; }
	 * 
	 * public void writeCellDataToRow(String sheetName, int row, int column,
	 * String result) throws FileNotFoundException { try {
	 * 
	 * WritableCell cell = WorkSheet((writeableWorkbook(sheetName)),
	 * sheetName).getWritableCell(column,row); Label label = new Label(column,
	 * row, result); ((WritableWorkbook) WorkSheet(writeableWorkbook(sheetName),
	 * sheetName)).write();
	 * 
	 * } catch(Exception e) { e.printStackTrace(); } }
	 */

	
	public void Writesheet(String workSheetName, String... rowData) throws IOException, WriteException {
		Workbook wworkbook = null;
		try {

			// wworkbook = Workbook.getWorkbook(new
			// File("C:/IAS/IAS_Selenium/Generated Tests/Automation_Scripts/Test
			// Data/Test.xls"));
			wworkbook = Workbook.getWorkbook(
					new File("C:/IAS/IAS_Selenium/Generated Tests/Automation_Scripts/Test Data/Tests.xls"));
			WritableWorkbook workbookCopy = Workbook.createWorkbook(
					new File("C:/IAS/IAS_Selenium/Generated Tests/Automation_Scripts/Test Data/Tests.xls"), wworkbook);
			sheet = workbookCopy.getSheet(0);
			if (sheet.getColumns() == 0) {
				addColumn();
			}
			int wRows = sheet.getRows();
			for (int i = 0;;) {
				for (int j = 0; j < rowData.length; j++) {

					label = new Label(j, i + wRows, rowData[j]);
					sheet.addCell(label);

				}
				break;
			}
			workbookCopy.write();
			workbookCopy.close();
			wworkbook.close();
		}

		catch (Exception e) {
			e.printStackTrace();
		}
	}

	/* Return Two Dimensional Array to DataProvider */
	public Object[][] getTestdata() {
		int row = 0;
		int col = 0;
		String data[][] = new String[iterationCount][usedColumnsCount - 1];

		// Get the Test Data
		for (int i = testCaseStartRow; i <= testCaseEndRow; i++) {
			col = 0;
			boolean flag = false;
			if (getCellData(usedColumnsCount, i).equalsIgnoreCase("Yes")) {
				flag = true;
				for (int j = 1; j < usedColumnsCount; j++) {
					data[row][col] = getCellData(j, i);
					col++;
				}
			}
			if (flag) {
				row++;
			}
		}
		return data;
	}

	/* Get Cell Data */
	private String getCellData(int col, int row) {
		return worksheet.getCell(col, row).getContents();
	}

	/* Get TestCase Start Row */
	private int getTestCaseStartRow() {
		try {
			for (int i = 0; i < rows; i++) {
				if (worksheet.getCell(0, i).getContents().equals(testCaseName.trim())) {
					testCaseStartRow = i;
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return testCaseStartRow;
	}

	/* Get Test Case End Row */
	private int getTestCaseEndRow() {
		try {
			for (int i = 0; i < rows; i++) {
				if (worksheet.getCell(0, i).getContents().equals(testCaseName.trim())) {
					testCaseEndRow = i;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return testCaseEndRow;
	}

	/* Get the Columns Count for the referenced test case */
	private int getUsedColumnsCount() {
		try {
			int count = 0;
			while (!worksheet.getCell(count, testCaseStartRow - 1).getContents().equalsIgnoreCase("Execute")) {
				usedColumnsCount = count++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return usedColumnsCount + 1;
	}

	/* Gets the total number of row count in the excel sheet */
	private int getRowCount() {
		return worksheet.getRows();
	}

	public void addColumn() throws Exception {
		try {
			font = new WritableFont(WritableFont.TIMES, 12, WritableFont.BOLD);
			cellStyle = new WritableCellFormat(font);
			cellStyle.setBorder(Border.ALL, BorderLineStyle.THIN);
			cellStyle.setBackground(Colour.LIGHT_BLUE);
			label = new Label(0, 0, "Username", cellStyle);
			sheet.addCell(label);
			label = new Label(1, 0, "Challenge Question", cellStyle);
			sheet.addCell(label);
			label = new Label(2, 0, "Challenge Answer", cellStyle);
			sheet.addCell(label);
			label = new Label(3, 0, "Status", cellStyle);
			sheet.addCell(label);
			label = new Label(4, 0, "Password", cellStyle);
			sheet.addCell(label);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

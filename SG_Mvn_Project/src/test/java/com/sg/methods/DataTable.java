package com.sg.methods;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.sg.driver.DriverScript;

public class DataTable extends DriverScript {
	/**************************************
	 * Method Name : getExcelData 
	 * Purpose : 
	 * Author : 
	 * Reviewer Name: 
	 * Date Written :
	 * Return value : Map<String, String> 
	 * Param : sheetName, logicalName
	 **************************************/

	public Map<String, String> getExcelData(String sheetName, String logicalName) {
		FileInputStream fin = null;
		Workbook wb = null;
		Sheet sh = null;
		Row row1 = null;
		Row row2 = null;
		Cell cell1 = null;
		Cell cell2 = null;
		Map<String, String> objData = null;
		String strKey = null;
		String strValue = null;
		int rowNum = 0;
		int colNum = 0;
		String sDay = null;
		String sMonth = null;
		String sYear = null;
		try {
			objData = new HashMap<String, String>();
			fin = new FileInputStream(System.getProperty("user.dir") + "\\TestData\\TestData.xlsx");
			wb = new XSSFWorkbook(fin);
			sh = wb.getSheet(sheetName);
			if (sh == null) {
				reports.writeReport(null, "Fail",
						"The sheet '" + sheetName + "' was not found in the excel test data file");
				return null;
			}

			// Find the rowNum based on the logicalName provided
			int rows = sh.getPhysicalNumberOfRows();
			for (int r = 0; r < rows; r++) {
				row1 = sh.getRow(r);
				cell1 = row1.getCell(0);
				if (cell1.getStringCellValue().equalsIgnoreCase(logicalName)) {
					rowNum = r;
					break;
				}
			}

			if (rowNum > 0) {
				row1 = sh.getRow(0);
				row2 = sh.getRow(rowNum);
				colNum = row1.getPhysicalNumberOfCells();
				for (int c = 0; c < colNum; c++) {
					cell1 = row1.getCell(c);
					strKey = cell1.getStringCellValue();

					cell2 = row2.getCell(c);

					if (cell2 == null || cell2.getCellType() == CellType.BLANK) {
						strValue = "";

					} else if (cell2.getCellType() == CellType.BOOLEAN) {
						strValue = String.valueOf(cell2.getBooleanCellValue());
					} else if (cell2.getCellType() == CellType.STRING) {
						strValue = cell2.getStringCellValue();
					} else if (cell2.getCellType() == CellType.NUMERIC) {
						if (DateUtil.isCellDateFormatted(cell2)) {
							double dt = cell2.getNumericCellValue();
							Calendar cal = Calendar.getInstance();
							cal.setTime(DateUtil.getJavaDate(dt));

							// If day is <10, then prefix with zero
							if (cal.get(Calendar.DAY_OF_MONTH) < 0) {
								sDay = "0" + cal.get(Calendar.DAY_OF_MONTH);
							}
							// If month is <10, then prefix with zero
							if ((cal.get(Calendar.MONTH) + 1) < 10) {
								sMonth = "0" + (cal.get(Calendar.MONTH) + 1);
							} else {
								sMonth = String.valueOf((cal.get(Calendar.MONTH) + 1));
							}

							sYear = String.valueOf(cal.get(Calendar.YEAR));
							strValue = sDay + "/" + sMonth + "/" + sYear;
						} else {
							strValue = String.valueOf(cell2.getNumericCellValue());
						}
					}

					objData.put(strKey, strValue);
				}

				return objData;
			} else {
				reports.writeReport(null, "Fail",
						"Failed to find the logicalName '" + logicalName + "' in the testData excel");
				return null;
			}

		} catch (Exception e) {
			reports.writeReport(null, "Exception", "Exception while executing 'getExcelData()' method. " + e);
			return null;
		} finally {
			try {
				fin.close();
				fin = null;
				cell1 = null;
				cell2 = null;
				row1 = null;
				row2 = null;
				sh = null;
				wb.close();
				wb = null;
			} catch (Exception e) {
				reports.writeReport(null, "Exception", "Exception while executing 'getExcelData()' method. " + e);
			}

		}
	}
	/**************************************
	 * Method Name : createDataProvider 
	 * Purpose : 
	 * Author : 
	 * Reviewer Name: 
	 * Date Written :object[][]
	 * Return value : Map<String, String> 
	 * Param : fileName, sheetName
	 **************************************/
	
	public Object[][] createDataProvider(String fileName, String sheetName){
		FileInputStream fin=null;
		Workbook wb=null;
		Sheet sh=null;
		Row row =null;
		Cell cell=null;
		int rowCount=0;
		int colCount=0;
		int executionCount=0;
		Object data[][]=null;
		List<String> colNames=null;
		int actualRows=0;
		Map<String, String> cellData =null;
		String strValue =null;
		String sDay=null;
		String sMonth=null;
		String sYear=null;
		
		try {
			fin = new FileInputStream(System.getProperty("user.dir")+ "\\ExecutionController\\"+fileName+".xlsx");
			wb= new XSSFWorkbook(fin);
			sh= wb.getSheet(sheetName);
			
			if(sh==null) {
				reports.writeReport(null, "Fail", "The sheet name "+sheetName+" does not exist in the "+fileName+" ");
				return null;
			}
			
			rowCount=sh.getPhysicalNumberOfRows();
			row=sh.getRow(0);
			colCount=row.getPhysicalNumberOfCells();
			
			//loop number of rows to find the count of the cases selected for execution
			for(int rows=0; rows<rowCount; rows++) {
				row=sh.getRow(rows);
				cell=row.getCell(3);
				if(cell.getStringCellValue().trim().equalsIgnoreCase("Yes")) {
					executionCount++;
				}
			}
			data=new Object[executionCount][1];
			colNames=new ArrayList<String>();
			
			for(int col=0; col<colCount; col++) {
				row=sh.getRow(0);
				cell= row.getCell(col);
				colNames.add(col, cell.getStringCellValue());
				
			}
			
			for(int r=1; r<rowCount; r++) {
				row=sh.getRow(r);
				cell= row.getCell(3);
				if(cell.getStringCellValue().trim().equalsIgnoreCase("Yes")) {
					cellData=new HashMap<String, String>();
					
					for(int col=0; col<colCount; col++) {
						cell=row.getCell(col);
						
						if(cell==null || cell.getCellType()==CellType.BLANK) {
							strValue="";
						
						} else if (cell.getCellType()==CellType.BOOLEAN) {
							strValue=String.valueOf(cell.getBooleanCellValue());
						} else if(cell.getCellType()==CellType.STRING) {
							strValue= cell.getStringCellValue();
						}else if(cell.getCellType()==CellType.NUMERIC) {
							if(DateUtil.isCellDateFormatted(cell)==true) {
								double dt= cell.getNumericCellValue();
								Calendar cal= Calendar.getInstance();
								cal.setTime(DateUtil.getJavaDate(dt));
								
								//prefix zero if day is<10
								if(cal.get(Calendar.DAY_OF_MONTH)<10) {
									sDay= "0" + cal.get(Calendar.DAY_OF_MONTH);
									
								}else {
									sDay= String.valueOf(cal.get(Calendar.DAY_OF_MONTH));
								}
								//prefix zero if day is<10
								if((cal.get(Calendar.MONTH)+1)<10) {
									
									sMonth= "0" + (cal.get(Calendar.MONTH)+1);
									
								} else {
									sMonth= String.valueOf((cal.get(Calendar.MONTH)+1));
								}
								
								sYear= String.valueOf(cal.get(Calendar.YEAR));
								strValue= sDay+ "-" + sMonth + "-" + sYear;
											
							}else {
								strValue= String.valueOf(cell.getNumericCellValue());
								
							}
						}
						
						cellData.put(colNames.get(col), strValue);
					}
					
					data[actualRows][0]=cellData;
					actualRows++;
					
				}
			}
	return data;		
			
			
		} catch (Exception e) {
			reports.writeReport(null, "Exception", "Exception while executing 'createDataprovider()' method. " + e);
			return null;
		}
		finally {
			try {
				
				fin.close();
				fin=null;
				cell=null;
				row=null;
				sh=null;
				wb.close();
				wb=null;
				colNames=null;
				cellData=null;
				
				
			} catch (Exception e) {
				reports.writeReport(null, "Exception", "Exception while executing 'createDataprovider()' method. " + e);
				return null;
			}
			
		}
	}
	
	/**************************************
	 * Method Name : setCellData 
	 * Purpose : 
	 * Author : 
	 * Reviewer Name: 
	 * Date Written :object[][]
	 * Return value : Map<String, String> 
	 * Param : fileName, sheetName
	 **************************************/
	public void setCellData(String filePath, String sheetName, String colName, String logicalName, String strValue) {
		FileInputStream fin=null;
		FileOutputStream fout=null;
		Workbook wb=null;
		Sheet sh=null;
		Row row=null;
		Cell cell=null;
		int colNum=0;
		int rowNum=0;
		
		
		try {
			
			fin = new FileInputStream(filePath);
			wb= new XSSFWorkbook(fin);
			sh=wb.getSheet(sheetName);
			
			//Find the column Number based on the columnName
			row= sh.getRow(0);
			int cols= row.getPhysicalNumberOfCells();
			for(int c=0; c<cols; c++) {
				cell = row.getCell(c);
				if(cell.getStringCellValue().trim().equalsIgnoreCase(colName)) {
					colNum=c;
					break;
				}
			}
			//Find the row Number based on the logical name
			int rows= sh.getPhysicalNumberOfRows();
			for(int r=0; r<rows; r++) {
				row=sh.getRow(r);
				cell= row.getCell(0);
				if(cell.getStringCellValue().trim().equalsIgnoreCase(logicalName)) {
					rowNum=r;
					break;
				}
			}
			row =sh.getRow(rowNum);
			cell= row.getCell(colNum);
			
			if(row.getCell(colNum)==null) {
				cell= row.createCell(colNum);
				
			}
			
			
			cell.setCellValue(strValue);
			
			fout=new FileOutputStream(filePath);
			wb.write(fout);
		} catch (Exception e) {
			reports.writeReport(null, "Exception", "Exception while executing 'createDataprovider()' method. " + e);
	} finally {
		try {
			fout.flush();
			fout.close();
			fout=null;
			fin.close();
			fin=null;
			cell=null;
			row=null;
			sh=null;
			wb.close();
			wb=null;	
		} catch (Exception e) {
			reports.writeReport(null, "Exception", "Exception while executing 'createDataprovider()' method. " + e);
		}
	}
	}
	
	}
	


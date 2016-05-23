package main.java.com.actiance.tests.dig;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

public class TranscriptDocumentUtils {

	
	/**
	 * Method to read the data from excel sheet from
	 * a sheet name , ObjectId column and the list of column to be read 
	 * @param workbookPath
	 * @param objectIdColName
	 * @return
	 * @throws Exception
	 */
	
	 //Row Count
	static int numberOfTranscripts,j=0;
	String[] listOfTranscripts1 = new String[200];
	List<String> listOfTranscripts = new ArrayList<String>();
	
	
	public Map<String,Object> readExcelData(String workbookPath , String objectIdColName) throws Exception
	{
		Map<String,Object> columnMap = null;
		Map<String,Object> sheetMaps = new HashMap<String, Object>();
		
		
    	
		try {
			
		    //Get the workbook instance for XLS file 
			FileInputStream fis = new FileInputStream(new File(workbookPath));
		    HSSFWorkbook workbook = new HSSFWorkbook(fis);
//		    							(this.getClass().getClassLoader().getResourceAsStream(workbookPath));
		    fis.close();
		    // get all the sheet names 
		    List<String> sheetNames =  new ArrayList<String>();
		    for(int i=0; i<workbook.getNumberOfSheets(); i++)
		    {
		    	sheetNames.add(workbook.getSheetName(i));		
		    }
		    
		    
		    
		    for(String sheetName : sheetNames)
		    {
		    	
		    
			    //Get the sheet from the workbook
			    HSSFSheet sheet = workbook.getSheet(sheetName);
			    	    
			    // get the first row of column names
			    HSSFRow firstRow = sheet.getRow(0);
			    
			    //Get the columnIndex and name map for all the columns
			    Map<String,Integer> colIndex = getColumnNameAndIndex(firstRow);
			    
			    // get the column index for the object id column
			    Integer objectIdColIndex = colIndex.get(objectIdColName);
			    
			    // initialize the column map
			    columnMap = new HashMap<String, Object>();
			    
			    // iterate through the list of object-ids & create the map
			    if(colIndex != null && objectIdColIndex != null){
			    	
				    //Iterate through each rows except the first row 
			    	
				    Iterator<Row> rowIterator = sheet.iterator(); 
				    while(rowIterator.hasNext()) 
				    {
				    
				    	Row row = rowIterator.next();
				        if(row.getRowNum() == 0)
				        	continue;
				            	
				        String colMapKey = null;
				        if(row.getCell(objectIdColIndex) != null)
				        	colMapKey = row.getCell(objectIdColIndex).getStringCellValue(); 
				        
				        
				        // create the mapping of column name & the value & 
				        // add to the list of maps
				        List<Map<String,String>> listColData = new ArrayList<Map<String,String>>();
				        for(Map.Entry<String, Integer> col : colIndex.entrySet())
				        {
				        	
				        	Map<String,String> colData = null;
				        	Cell cell = row.getCell(col.getValue());
				        	if(cell != null && cell.getCellType() != Cell.CELL_TYPE_BLANK){
				        		colData = new HashMap<String, String>();
				        		switch(cell.getCellType()) {
				                case Cell.CELL_TYPE_BOOLEAN:		                    
				                    colData.put(col.getKey(), String.valueOf(cell.getBooleanCellValue()));
				                    break;
				                case Cell.CELL_TYPE_NUMERIC:
				                	colData.put(col.getKey(), String.valueOf(cell.getNumericCellValue()));			                	
				                    break;
				                case Cell.CELL_TYPE_STRING:		
				                	colData.put(col.getKey(), cell.getStringCellValue());
				                    break;
				        		}
				        		
				        		//Getting names for transcripts
			        			if(sheetName.equals("Transcript") && col.getKey().equals("Index-Id"))
					        			this.listOfTranscripts.add(cell.getStringCellValue());
				        		
				        	}
				        	if(colData != null)
				        		listColData.add(colData);	
				        }
				        columnMap.put(colMapKey, listColData);
				      
				        //Finding the Number of transcripts 
				    	if(sheetName.equals("Transcript"))
				    		if(!rowIterator.hasNext()){
				    			numberOfTranscripts = row.getRowNum();	
				    			System.out.println(" ** numberOfTranscripts "+numberOfTranscripts);
				    		}
				    }
			    }	
			    sheetMaps.put(sheetName, columnMap);   
		    }
		    workbook.close();
		}catch(Exception e){
			throw e;
		}
		return sheetMaps;
	}
	
	//Method for getting the transcript names
	public List<String> getListoftranscripts()
	{
		return listOfTranscripts;
	}
	
	//Method for getting the no. of transcripts in the workbook
    public int getNoOfTranscritps()
	{
		return numberOfTranscripts;
	}

	private static Map<String,Integer> getColumnNameAndIndex(Row firstRow){
		
		Map<String,Integer> colNameIndexMap = new HashMap<String, Integer>();
		if(firstRow != null){
			for(Cell cell : firstRow)
				colNameIndexMap.put(cell.getStringCellValue(), cell.getColumnIndex());
		}
		return colNameIndexMap;
	}
}

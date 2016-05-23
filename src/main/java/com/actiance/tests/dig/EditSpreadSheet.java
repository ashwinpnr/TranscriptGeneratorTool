package main.java.com.actiance.tests.dig;

import java.io.*;
import java.util.Scanner;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.ss.usermodel.*;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class EditSpreadSheet {

	
	public static void createFiles(String strNetwork,String strChannel,int StartGCID,int numberofRecords, String timeStamp,String strGroup,String filePath) throws IOException {
		
		for(int i=1;i<=100;i++){
			BufferedWriter out = null;
			try  
			{
			    FileWriter fstream = new FileWriter(filePath+"\\File\\file-contentFC"+i+".txt", true); //true tells to append data.
			    out = new BufferedWriter(fstream);
			    java.util.Date date= new java.util.Date();
				
			    out.write(strNetwork+" "+strChannel+" "+StartGCID+" "+i+date.getTime()+" "+getIndex(1,100));
			}
			catch (IOException e)
			{
			    System.err.println("Error: " + e.getMessage());
			}
			finally
			{
			    if(out != null) {
			        out.close();
			    }
			}
		}
		
		

		
	}
	public static void editSheet(String strNetwork,String strChannel,int StartGCID,int numberofRecords, String timeStamp,String strGroup,String filePath) throws Exception {

//		String strNetwork = "YellowJacket";
//		String strChannel = "IM";
//		int StartGCID = 1001;
//		int numberofRecords = 1000;
//		String timeStamp ="2016-5-13T13:24:25Z";
//		String strGroup="group5";
//		String filePath = "E:\\Alcatraz\\QA\\DocGenerator\\Ingestion";
		 /*Scanner in = new Scanner(System.in);
		 System.out.println("Enter Path of Reference Sheet (TranscriptExcelSheet.xls) Eg : E:\\Alcatraz\\QA\\DocGenerator\\Ingestion");
		 String filePath = in.nextLine();
		 System.out.println("Enter Network");
		 String strNetwork = in.nextLine();
		 System.out.println("Enter Channel");
		 String strChannel = in.nextLine();
		 System.out.println("Enter Timestamp");
		 String timeStamp = in.nextLine();
		 System.out.println("Enter Participant Group");
		 String strGroup = in.nextLine();
		 System.out.println("Enter Number of Records ");
		 int numberofRecords = in.nextInt();*/
		 
		 
		FileInputStream fsIP = new FileInputStream(
				new File(filePath+"\\TranscriptExcelSheet.xls")); 
		
		HSSFWorkbook wb = new HSSFWorkbook(fsIP);
		for (int itr = 1; itr <= numberofRecords; itr = itr + 100) {
			int minGCID = StartGCID+itr-1;

			HSSFSheet worksheet = wb.getSheetAt(3);
			//System.out.println(worksheet.getSheetName());
			

			// Modality
			for (int i = 1; i <= 4; i++) {
				Cell cellNetwork = worksheet.getRow(i).getCell(5); // Access the
																	// second
																	// cell in
																	// second
																	// row to
																	// update
																	// the value
				Cell cellChannel = worksheet.getRow(i).getCell(4);
				//System.out.println(cellNetwork.getStringCellValue());
				cellNetwork.setCellValue(strNetwork); // Get current cell value
														// value and overwrite
														// the value
				cellChannel.setCellValue(strChannel);
			}

			// Transcript
			HSSFSheet worksheetTranscript = wb.getSheet("Transcript");
			for (int i = 1; i <= 100; i++) {
				Cell cellGCID = worksheetTranscript.getRow(i).getCell(0);
//				System.out.println(cellGCID.getStringCellValue());
				String strGCIDAppender = strNetwork + "-" + (minGCID + (i - 1));
				cellGCID.setCellValue(strGCIDAppender);
				Cell cellTime = worksheetTranscript.getRow(i).getCell(4);
				cellTime.setCellValue(timeStamp);
				
			}

			HSSFSheet worksheetSubject = wb.getSheet("Subject");
			for (int i = 1; i <= 100; i++) {
				Cell cellSubject = worksheetSubject.getRow(i).getCell(3);
				//System.out.println(cellSubject.getStringCellValue());
				String strGCIDAppender = strNetwork + "-" + (minGCID + (i - 1));
				cellSubject.setCellValue(strGCIDAppender);
			}

			HSSFSheet worksheetInteraction = wb.getSheet("Interaction");
			for (int i = 1; i <= 100; i++) {
				Cell cellGCID = worksheetInteraction.getRow(i).getCell(6);
				//System.out.println(cellGCID.getStringCellValue());
				String strGCIDAppender = strNetwork + "-" + (minGCID + (i - 1));
				cellGCID.setCellValue(strGCIDAppender);
			}

			HSSFSheet worksheetTextEvent = wb.getSheet("TextEvent");
			for (int i = 1; i <= 100; i++) {
				Cell cellObjType = worksheetTextEvent.getRow(i).getCell(9);
				Cell cellSubType = worksheetTextEvent.getRow(i).getCell(10);
				//System.out.println(cellObjType.getStringCellValue());
				String strGCIDAppender = strNetwork + "-" + (minGCID + (i - 1));
				cellObjType.setCellValue("obj type of " + strGCIDAppender);
				cellSubType.setCellValue("sub type of " + strGCIDAppender);
				Cell cellTime = worksheetTextEvent.getRow(i).getCell(11);
				cellTime.setCellValue(timeStamp);
			}

			HSSFSheet worksheetActionEvent = wb.getSheet("ActionEvent");
			for (int i = 1; i <= 100; i++) {
				Cell cellObjType = worksheetActionEvent.getRow(i).getCell(4);
				Cell cellSubType = worksheetActionEvent.getRow(i).getCell(5);
				// System.out.println(cellGCID.getStringCellValue());
				String strGCIDAppender = strNetwork + "-" + (minGCID + (i - 1));
				cellObjType.setCellValue("obj type of " + strGCIDAppender);
				cellSubType.setCellValue("sub type of " + strGCIDAppender);
				Cell cellTime = worksheetActionEvent.getRow(i).getCell(7);
				cellTime.setCellValue(timeStamp);
			}

			HSSFSheet worksheetTimeFrame = wb.getSheet("Time-Frame");
			for (int i = 1; i <= 100; i++) {
				
				Cell cellTime = worksheetTimeFrame.getRow(i).getCell(1);
				cellTime.setCellValue(timeStamp);
				Cell cellToTime = worksheetTimeFrame.getRow(i).getCell(3);
				cellToTime.setCellValue(timeStamp);
				
			}
			
			HSSFSheet worksheetPolicyEvent = wb.getSheet("PolicyEvent");
			for (int i = 1; i <= 100; i++) {
				
				Cell cellTime = worksheetPolicyEvent.getRow(i).getCell(5);
				cellTime.setCellValue(timeStamp);
				
			}
			
			HSSFSheet worksheetFileEvent = wb.getSheet("FileEvent");
			for (int i = 1; i <= 100; i++) {
				
				Cell cellTime = worksheetFileEvent.getRow(i).getCell(7);
				cellTime.setCellValue(timeStamp);
				
			}
			
			HSSFSheet worksheetUserInfo = wb.getSheet("UserInfo");
			for (int i = 1; i <= 15; i++) {
				
				Cell cellGroup = worksheetUserInfo.getRow(i).getCell(24);
				cellGroup.setCellValue(strGroup);
				
			}
			
			FileOutputStream output_file = new FileOutputStream(new File(
					filePath+"\\Intermediate\\TranscriptExcelSheet" + strNetwork + minGCID + ".xls")); // Open
																															// FileOutputStream
																															// to
																															// write
																															// updates

			wb.write(output_file); // write changes

			output_file.close(); // close the stream
		}
		fsIP.close(); // Close the InputStream
	}
	
	private static String getIntVal(int i) {
		  StringBuffer buff = new StringBuffer();
		  for (int j = 0; j < i; j++) {
		   buff.append(getIndex(0, 9));
		  }
		  if (buff.length() <= 0) {
		   return "0";
		  }
		  return buff.toString();
		 }
	 private static String getData(List<String> dataList) {
		  int upperBound = dataList.size() - 1;
		  if (upperBound == 0) {
		   return dataList.get(0);
		  } else if (upperBound < 0) {
		   return "";
		  } else {
		   return dataList.get(getIndex(0, upperBound));
		  }
		 }

		 private static int getIndex(int lowerBound, int upperBound) {
		  Random random = new Random();
		  return random.nextInt(upperBound - lowerBound) + lowerBound;
		 }

}

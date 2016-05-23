package main.java.com.actiance.tests.dig;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.actiance.platform.commons.xmlns.apc.dig.itm.interaction.transcript.x10.TranscriptDocument;

public class TranscriptGenerator 
{
	
		
		static String resourcesPath;
		public static void main(String[] args) throws Exception
		{
			String destinationPath,sourcePath,workbookPath;
			BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
//			System.out.println("Enter Path of Reference Sheet (TranscriptExcelSheet.xls) Eg : E:\\Alcatraz\\QA\\DocGenerator\\Ingestion");
//			 String filePath = in.readLine();
//			 System.out.println("Enter Network");
//			 String strNetwork = in.readLine();
//			 System.out.println("Enter Channel");
//			 String strChannel = in.readLine();
//			 System.out.println("Enter Timestamp Eg:2016-5-13T13:24:25Z");
//			 String timeStamp = in.readLine();
//			 System.out.println("Enter Participant Group");
//			 String strGroup = in.readLine();
//			 System.out.println("Enter Number of Records ");
//			 int numberofRecords = Integer.parseInt(in.readLine());
//			 System.out.println("Enter Starting GCID Append Number ");
//			 int StartGCID = Integer.parseInt(in.readLine());
			 
			 String filePath = "E:\\Alcatraz\\QA\\DocGenerator\\Ingestion\\DocGeneratorTool";
			 
			 String strNetwork = "LinkedIn";
			
			 String strChannel = "Chat";
			 
			 String timeStamp = "2016-5-18T13:24:25Z";
			 
			 String strGroup = "group1";
			
			 int numberofRecords = 100;
			 
			 int StartGCID = 1201;
			 
			EditSpreadSheet.editSheet(strNetwork, strChannel, StartGCID, numberofRecords, timeStamp, strGroup, filePath);
			EditSpreadSheet.createFiles(strNetwork, strChannel, StartGCID, numberofRecords, timeStamp, strGroup, filePath);
			sourcePath =filePath+"\\"+"Intermediate\\";
			//System.out.println("Enter the Resource Contentpath ");
			resourcesPath = filePath+"\\";
			System.out.println("Enter the Transcripts destination path");
			destinationPath = filePath+"\\OutputXML\\";
		
			File dir = new File(sourcePath);
		    List<String> list = Arrays.asList(dir.list(
			   new FilenameFilter() {
			      @Override public boolean accept(File dir, String name) {
			         return name.endsWith(".xls");
			      }
			   }
			));
		    for(String workbookPath1 : list){
		    	System.out.println(workbookPath1);
		    	workbookPath = sourcePath + "/"+workbookPath1;
		    
//			System.out.println("Enter the workbook path");
//			workbookPath = in.readLine();
			
			TranscriptDocumentUtils transcriptDocumentUtils = new TranscriptDocumentUtils();	
			Map<String, Object> excelData = transcriptDocumentUtils.readExcelData(workbookPath,"Index-Id");

			// Transcript Document Entities
			TranscriptDocumentEntities tEntity = new TranscriptDocumentEntities(excelData);

			//get the no of transcripts
			int noOfTranscripts = transcriptDocumentUtils.getNoOfTranscritps();
			System.out.println("The no of transcripts is "+ noOfTranscripts);
			
			String listOfTranscripts[] = new String[noOfTranscripts];
			List<String> listOfTranscripts1 = transcriptDocumentUtils.getListoftranscripts();
			Iterator itr = listOfTranscripts1.iterator();
			
			int j = 0;
			while(itr.hasNext())
			{
				listOfTranscripts[j++] = (String) itr.next();	
			}	
			for(int i =0; i < noOfTranscripts; i++){
				System.out.println(listOfTranscripts[i]);
				TranscriptDocument tDoc = (TranscriptDocument) tEntity.getTranscriptDocument(listOfTranscripts[i]); 
				String content = tDoc.toString();	
				
				// Generate the list of transcript documents based on the excel
				File f = new File(destinationPath+listOfTranscripts[i]+".xml");
				if(!f.exists())
					f.createNewFile();
			
				FileWriter fw = new FileWriter(f.getAbsoluteFile());
				BufferedWriter bw = new BufferedWriter(fw);
				
				bw.write(content);
				bw.close();
			}
		    }
		}
		    
	
}

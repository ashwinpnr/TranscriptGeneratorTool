package main.java.com.actiance.tests.dig;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.xml.bind.ValidationException;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.apache.poi.util.SystemOutLogger;
import org.apache.xmlbeans.XmlAnySimpleType;
import org.apache.xmlbeans.impl.jam.provider.ResourcePath;

import com.actiance.platform.commons.xmlns.apc.dig.itm.interaction.policy.x10.TranscriptPolicy.Retention;
import com.actiance.platform.commons.xmlns.apc.dig.itm.interaction.policy.x10.TranscriptPolicy.Retention.Policy;
import com.actiance.platform.commons.xmlns.apc.dig.itm.interaction.transcript.x10.ActionEvent;
import com.actiance.platform.commons.xmlns.apc.dig.itm.interaction.transcript.x10.ActionEvent.ActionType;
import com.actiance.platform.commons.xmlns.apc.dig.itm.interaction.transcript.x10.FileEvent;
import com.actiance.platform.commons.xmlns.apc.dig.itm.interaction.transcript.x10.FileObject.FileContent;
import com.actiance.platform.commons.xmlns.apc.dig.itm.interaction.transcript.x10.FileObject.LexiconHits;
import com.actiance.platform.commons.xmlns.apc.dig.itm.interaction.transcript.x10.Interaction;
import com.actiance.platform.commons.xmlns.apc.dig.itm.interaction.transcript.x10.Interaction.DataState;
import com.actiance.platform.commons.xmlns.apc.dig.itm.interaction.transcript.x10.Interaction.Participants.Participant;
import com.actiance.platform.commons.xmlns.apc.dig.itm.interaction.transcript.x10.PolicyEvent;
import com.actiance.platform.commons.xmlns.apc.dig.itm.interaction.transcript.x10.PolicyEvent.PolicyEventCategory;
import com.actiance.platform.commons.xmlns.apc.dig.itm.interaction.transcript.x10.TextEvent;
import com.actiance.platform.commons.xmlns.apc.dig.itm.interaction.transcript.x10.TextObject.FileEventIds;
import com.actiance.platform.commons.xmlns.apc.dig.itm.interaction.transcript.x10.TextObject.TextContent;
import com.actiance.platform.commons.xmlns.apc.dig.itm.interaction.transcript.x10.TranscriptDocument;
import com.actiance.platform.commons.xmlns.apc.dig.itm.interaction.types.x10.Action;
import com.actiance.platform.commons.xmlns.apc.dig.itm.interaction.types.x10.Encoding;
import com.actiance.platform.commons.xmlns.apc.dig.itm.interaction.types.x10.FileEventType;
import com.actiance.platform.commons.xmlns.apc.dig.itm.interaction.types.x10.GeneralAttributes;
import com.actiance.platform.commons.xmlns.apc.dig.itm.interaction.types.x10.IndexableAttributes;
import com.actiance.platform.commons.xmlns.apc.dig.itm.interaction.types.x10.IndexableAttributes.Attribute;
import com.actiance.platform.commons.xmlns.apc.dig.itm.interaction.types.x10.LexiconHit;
import com.actiance.platform.commons.xmlns.apc.dig.itm.interaction.types.x10.LexiconHit.MatchType;
import com.actiance.platform.commons.xmlns.apc.dig.itm.interaction.types.x10.MimeType;
import com.actiance.platform.commons.xmlns.apc.dig.itm.interaction.types.x10.ModalityClass;
import com.actiance.platform.commons.xmlns.apc.dig.itm.interaction.types.x10.ModalityDocument.Modality;
import com.actiance.platform.commons.xmlns.apc.dig.itm.interaction.types.x10.ModalityType;
import com.actiance.platform.commons.xmlns.apc.dig.itm.interaction.types.x10.NetworkEndpoint;
import com.actiance.platform.commons.xmlns.apc.dig.itm.interaction.types.x10.SecondaryAddress;
import com.actiance.platform.commons.xmlns.apc.dig.itm.interaction.types.x10.SubjectDocument.Subject;
import com.actiance.platform.commons.xmlns.apc.dig.itm.interaction.types.x10.TextEventType;
import com.actiance.platform.commons.xmlns.apc.dig.itm.interaction.types.x10.TimeFrameDocument.TimeFrame;
import com.actiance.platform.commons.xmlns.apc.dig.itm.interaction.types.x10.UserInfo;
import com.actiance.platform.commons.xmlns.apc.dig.itm.interaction.types.x10.UserInfo.Image.Type;
import com.actiance.platform.commons.xmlns.apc.dig.itm.interaction.types.x10.UserInfo.SecondaryAddresses;
import com.actiance.platform.commons.xmlns.apc.dig.itm.interaction.types.x10.UserVisibility;

public class TranscriptDocumentEntities {
	TranscriptGenerator transcriptGenerator = new TranscriptGenerator();
	final String resourcesPath  = transcriptGenerator.resourcesPath;
	
	private static final String SETNULLCHARACTERS = "###";
	private static final String INDEXIDSEPARATOR = "#";
	private static final String TRANSCRIPTSHEETNAME = "Transcript";
	private static final String INTERACTIONSHEETNAME = "Interaction";
	private static final String TIMEFRAMESHEETNAME = "Time-Frame";
	private static final String MODALITYSHEETNAME = "Modality";
	private static final String SUBJECTSHEETNAME = "Subject";
	private static final String PARTICIPANTSHEETNAME = "Participant";
	private static final String USERINFOSHEETNAME = "UserInfo";
	private static final String NETWORKINFOSHEETNAME = "NetworkInfo";
	private static final String TEXTEVENTSHEETNAME = "TextEvent";
	private static final String TEXTCONTENTSHEETNAME = "TextContent";
	private static final String LEXICONHITSHEETNAME = "LexiconHit";
	private static final String FILEEVENTSHEETNAME = "FileEvent";
	private static final String FILECONTENTSHEETNAME = "FileContent";
	private static final String ATTRIBUTESSHEETNAME = "Attributes";
	private static final String ACTIONEVENTSHEETNAME = "ActionEvent";
	private static final String POLICYEVENTSHEETNAME = "PolicyEvent";
	private static final String POLICYSHEETNAME = "Policy";

	private Map<String, Object> transcriptSheetData = null;
	private Map<String, Object> interactionSheetData = null;
	private Map<String, Object> timeframeSheetData = null;
	private Map<String, Object> modalitySheetData = null;
	private Map<String, Object> subjectSheetData = null;
	private Map<String, Object> participantSheetData = null;
	private Map<String, Object> userinfoSheetData = null;
	private Map<String, Object> networkinfoSheetData = null;
	private Map<String, Object> textEventSheetData = null;
	private Map<String, Object> fileEventSheetData = null;
	private Map<String, Object> fileContentSheetData = null;
	private Map<String, Object> textContentSheetData = null;
	private Map<String, Object> attributesSheetData = null;
	private Map<String, Object> lexiconHitSheetData = null;
	private Map<String, Object> actionEventSheetData = null;
	private Map<String, Object> policyEventSheetData = null;
	private Map<String, Object> policySheetData = null;
	

	
	private static final String FILE_RESOURCES_PATH = "data/dig/files/";
		
	/**
	 * Constructor to initialize all the sheet data
	 * @param excelDataMap
	 */
	@SuppressWarnings("unchecked")
	public TranscriptDocumentEntities(Map<String, Object> excelDataMap){
		if(transcriptSheetData == null)
			transcriptSheetData = (Map<String, Object>) excelDataMap.get(TRANSCRIPTSHEETNAME);
		if(interactionSheetData == null)
			interactionSheetData = (Map<String, Object>)excelDataMap.get(INTERACTIONSHEETNAME);
		if(timeframeSheetData == null)
			timeframeSheetData = (Map<String, Object>) excelDataMap.get(TIMEFRAMESHEETNAME);
		if(modalitySheetData == null)
			modalitySheetData = (Map<String, Object>) excelDataMap.get(MODALITYSHEETNAME);
		if(subjectSheetData == null)
			subjectSheetData = (Map<String, Object>) excelDataMap.get(SUBJECTSHEETNAME);
		if(participantSheetData == null)
			participantSheetData = (Map<String,Object>) excelDataMap.get(PARTICIPANTSHEETNAME);
		if(userinfoSheetData == null)
			userinfoSheetData = (Map<String,Object>) excelDataMap.get(USERINFOSHEETNAME);
		if(networkinfoSheetData == null)
			networkinfoSheetData = (Map<String,Object>) excelDataMap.get(NETWORKINFOSHEETNAME);
		if(textEventSheetData == null)
			textEventSheetData = (Map<String,Object>) excelDataMap.get(TEXTEVENTSHEETNAME);;
		if(fileEventSheetData == null)
			fileEventSheetData = (Map<String,Object>) excelDataMap.get(FILEEVENTSHEETNAME);
		if(fileContentSheetData == null)
			fileContentSheetData = (Map<String,Object>) excelDataMap.get(FILECONTENTSHEETNAME);
		if(textContentSheetData == null)
			textContentSheetData = (Map<String,Object>) excelDataMap.get(TEXTCONTENTSHEETNAME);
		if(attributesSheetData == null)
			attributesSheetData = (Map<String, Object>) excelDataMap.get(ATTRIBUTESSHEETNAME);
		if(lexiconHitSheetData == null)
			lexiconHitSheetData = (Map<String, Object>) excelDataMap.get(LEXICONHITSHEETNAME);
		if(actionEventSheetData == null)
			actionEventSheetData = (Map<String, Object>) excelDataMap.get(ACTIONEVENTSHEETNAME);
		if(policyEventSheetData == null)
			policyEventSheetData = (Map<String, Object>) excelDataMap.get(POLICYEVENTSHEETNAME);
		if(policySheetData == null)
			policySheetData = (Map<String, Object>) excelDataMap.get(POLICYSHEETNAME);
	}
	
	/**
	 * Method to get the Transcript object based on the general schema
	 * i.e. participants 1, textevents 1 & fileevents 1
	 * @param transcriptIndexId
	 * @return
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	public TranscriptDocument getTranscriptDocument(String transcriptIndexId) throws Exception{
		TranscriptDocument tDoc = null;
		
		if(transcriptSheetData != null){
			List<Map<String,String>> transcriptMapData = 
					(List<Map<String,String>>)transcriptSheetData.get(transcriptIndexId);
			
			//System.out.println((List<Map<String,String>>)transcriptSheetData.get(transcriptIndexId));
			if(transcriptMapData != null){
				tDoc = TranscriptDocument.Factory.newInstance();
				tDoc.addNewTranscript();
				for(Map<String,String> transcriptRowData : transcriptMapData){ 
					// set the transcript id
					if(transcriptRowData.get(TranscriptDocumentConstants.TRANSCRIPTID) != null)
						tDoc.getTranscript().setId(transcriptRowData.get(TranscriptDocumentConstants.TRANSCRIPTID));
					
					// set the source end point id
					if(transcriptRowData.get(TranscriptDocumentConstants.SOURCEENDPOINTID) != null)
						tDoc.getTranscript().setSourceEndpointId(transcriptRowData.get(TranscriptDocumentConstants.SOURCEENDPOINTID));
					
					// set the source end point version
					if(transcriptRowData.get(TranscriptDocumentConstants.SOURCEENDPOINTVERSION) != null){
						String sourceVersion = transcriptRowData.get(TranscriptDocumentConstants.SOURCEENDPOINTVERSION);
						if(!sourceVersion.equals(SETNULLCHARACTERS))
							tDoc.getTranscript().setSourceEndpointVersion(sourceVersion);
					}
					
					// set the transcript time stamp
					if(transcriptRowData.get(TranscriptDocumentConstants.TIMESTAMP) != null && 
							!transcriptRowData.get(TranscriptDocumentConstants.TIMESTAMP).equals(SETNULLCHARACTERS)){
						SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
						Calendar cal = Calendar.getInstance();
						cal.setTime(df.parse(transcriptRowData.get(TranscriptDocumentConstants.TIMESTAMP)));
						tDoc.getTranscript().setTimeStamp(cal);
					}
					
					
					// set interaction
					if(transcriptRowData.get(TranscriptDocumentConstants.INTERACTIONINDEXID) != null){
						setInteraction(tDoc, 
									transcriptRowData.get(TranscriptDocumentConstants.INTERACTIONINDEXID));
					}
					

				}
			}
		}
		
		Utils.validate(tDoc);
		return tDoc;
	}
	
	/**
	 * Method to set the interaction based on the values form the excel sheet
	 * @param tDoc
	 * @param interactionIndexId
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	public void setInteraction(TranscriptDocument tDoc, String interactionIndexId) throws Exception {
		List<Map<String,String>> interactionMapData = 
				(List<Map<String,String>>)interactionSheetData.get(interactionIndexId);
		
		if(interactionMapData != null){
			Interaction interaction = tDoc.getTranscript().addNewInteraction();
			
			for(Map<String,String > interactionData : interactionMapData){
				//set the interaction event id
				if(interactionData.get(TranscriptDocumentConstants.INTERACTIONEVENTID) != null)
					interaction.setInteractionEventId(interactionData.get("interaction-event-id"));
				
				// set the data state
				if(interactionData.get(TranscriptDocumentConstants.DATA_STATE) != null){
					String data = interactionData.get(TranscriptDocumentConstants.DATA_STATE);
					if(data.contains(SETNULLCHARACTERS))
						interaction.setDataState(DataState.VALID);
					else
						interaction.setDataState(DataState.Enum.forString(data));
				}
				
				// set the historical-data-flag
				if(interactionData.get(TranscriptDocumentConstants.HISTORICAL_DATA_FLAG) != null){
					String data = interactionData.get(TranscriptDocumentConstants.HISTORICAL_DATA_FLAG);
					if(!data.contains(SETNULLCHARACTERS))
						interaction.setHistoricalDataFlag(Boolean.parseBoolean(data));
				}
				
				// set the communication object id
				if(interactionData.get(TranscriptDocumentConstants.COMMUNICATIONOBJECTID) != null &&
						!interactionData.get(TranscriptDocumentConstants.COMMUNICATIONOBJECTID).equals(SETNULLCHARACTERS))
					interaction.setCommunicationObjectId(
							interactionData.get(TranscriptDocumentConstants.COMMUNICATIONOBJECTID));
				
				// set the parent communication object id
				if(interactionData.get(TranscriptDocumentConstants.PARENTCOMMUNICATIONOBJECTID) != null){
					interaction.setParentCommunicationObjectId(
							interactionData.get(TranscriptDocumentConstants.PARENTCOMMUNICATIONOBJECTID));
				}
				
				// set the thread object id
				if(interactionData.get(TranscriptDocumentConstants.THREADOBJECTID) != null){
					interaction.setThreadObjectId(
							interactionData.get(TranscriptDocumentConstants.THREADOBJECTID));
				}
				
				// set the location object id
				if(interactionData.get(TranscriptDocumentConstants.LOCATIONOBJECTID) != null){
					if(interaction.getResourceLocation() != null){
						interaction.setResourceLocation(
								interactionData.get(TranscriptDocumentConstants.LOCATIONOBJECTID));
					}
				}
				
				// set time-frame
				if(interactionData.get(TranscriptDocumentConstants.TIMEFRAMEINDEXID) != null){
					String data = interactionData.get(TranscriptDocumentConstants.TIMEFRAMEINDEXID);
					if(!data.contains(SETNULLCHARACTERS)){
						interaction.addNewTimeFrame();
						interaction.setTimeFrame(setTimeFrame(interaction.getTimeFrame(), data));
					}
					
				}
				
				// set the modality
				if(interactionData.get(TranscriptDocumentConstants.MODALITYINDEXID) != null){
					String data = interactionData.get(TranscriptDocumentConstants.MODALITYINDEXID);
					if(data.equals(SETNULLCHARACTERS)){
						interaction.getModality().setNil();
					}
					else{
						interaction.addNewModality();
						interaction.setModality(setModality(interaction.getModality(), data, null));
					}
				}
				
				// set the subject
				if(interactionData.get(TranscriptDocumentConstants.SUBJECTINDEXID) != null){
					if(interaction.getSubject() != null){
						
							interaction.setSubject(
									setSubject(interaction.getSubject(), 
											interactionData.get(TranscriptDocumentConstants.SUBJECTINDEXID)));
					}else{
						interaction.addNewSubject();
						interaction.setSubject(
								setSubject(interaction.getSubject(), 
										interactionData.get(TranscriptDocumentConstants.SUBJECTINDEXID)));
					}
				}
				
				// set the participants
				if(interactionData.get(TranscriptDocumentConstants.PARTICIPANTINDEXID)!= null)
				{
				
				
						String data = interactionData.get(TranscriptDocumentConstants.PARTICIPANTINDEXID);
						List<String> participantIds = new ArrayList<String>();
						if(data.contains(INDEXIDSEPARATOR))
							participantIds = Arrays.asList(data.split(INDEXIDSEPARATOR));
						else
							participantIds.add(data);
						
						setParticipants(tDoc, participantIds);		
			
				}
			
				
				//set the text events
				if(interactionData.get(TranscriptDocumentConstants.TEXTEVENTINDEXID)!= null){
					if(!interactionData.get(TranscriptDocumentConstants.TEXTEVENTINDEXID).equals(SETNULLCHARACTERS))
					{
						String data = interactionData.get(TranscriptDocumentConstants.TEXTEVENTINDEXID);
						List<String> textEventIds = new ArrayList<String>();
						if(data.contains(INDEXIDSEPARATOR))
							textEventIds = Arrays.asList(data.split(INDEXIDSEPARATOR));
						else
							textEventIds.add(data);
						
						setTextEvents(tDoc, textEventIds);
							
					}
				}
				
				// set the file events
			if(interactionData.get(TranscriptDocumentConstants.FILEEVENTINDEXID)!= null){
					if(!interactionData.get(TranscriptDocumentConstants.FILEEVENTINDEXID).equals(SETNULLCHARACTERS))
					{
						String data = interactionData.get(TranscriptDocumentConstants.FILEEVENTINDEXID);
						List<String> fileEventIds = new ArrayList<String>();
						if(data.contains(INDEXIDSEPARATOR))
							fileEventIds = Arrays.asList(data.split(INDEXIDSEPARATOR));
						else
							fileEventIds.add(data);
						
						setFileEvents(tDoc, fileEventIds);
							
					}
				}
				
				// set Action Events
				if(interactionData.get(TranscriptDocumentConstants.ACTIONEVENTINDEXID)!= null){
						String data = interactionData.get(TranscriptDocumentConstants.ACTIONEVENTINDEXID);
						List<String> actionEventIds = new ArrayList<String>();
						if(data.contains(INDEXIDSEPARATOR))
							actionEventIds = Arrays.asList(data.split(INDEXIDSEPARATOR));
						else
							actionEventIds.add(data);
						
						setActionEvents(tDoc, actionEventIds);
				}
				
				
				// set Policy Violations
				if(interactionData.get(TranscriptDocumentConstants.POLICYEVENTINDEXID)!= null){
						String data = interactionData.get(TranscriptDocumentConstants.POLICYEVENTINDEXID);
						List<String> policyEventIds = new ArrayList<String>();
						if(data.contains(INDEXIDSEPARATOR))
							policyEventIds = Arrays.asList(data.split(INDEXIDSEPARATOR));
						else
							policyEventIds.add(data);
						
						setPolicyEvents(tDoc, policyEventIds);
					
				}
			
				
				// set the attributes
				if(interactionData.get(TranscriptDocumentConstants.ATTRIBUTESINDEXID) != null)
				{
					String data = interactionData.get(TranscriptDocumentConstants.ATTRIBUTESINDEXID);
					interaction.addNewAttributes();
						
					IndexableAttributes attributes = interaction.getAttributes();
					List<Attribute> listAttributes = new ArrayList<Attribute>();
					
					if(data.contains(INDEXIDSEPARATOR)){
						String[] ids = data.split(INDEXIDSEPARATOR);
						for(String id : ids)
							listAttributes.add(setIndexableAttributes(attributes, id));
					}
					else{
						listAttributes.add(setIndexableAttributes(attributes, data));
					}
					
					interaction.getAttributes().setAttributeArray(listAttributes.toArray
							(new Attribute[listAttributes.size()]));
					
				}
				
			}
		}
	}
	
	private TimeFrame setTimeFrame(TimeFrame timeFrame, String timeFrameIndexId) throws ParseException {
		List<Map<String, String>> timeFrameRowData = (List<Map<String, String>>)timeframeSheetData.get(timeFrameIndexId);
		if(timeFrameRowData != null){
			timeFrame.addNewEndTime();
			timeFrame.addNewStartTime();
			
			for(Map<String, String> timeFrameColData : timeFrameRowData){
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
				String timeframeKey  = timeFrameColData.keySet().toArray(new String[]{})[0];
				switch(timeframeKey){
				case TranscriptDocumentConstants.START_TIME:
					Calendar startCal = Calendar.getInstance();
					startCal.setTime(df.parse(timeFrameColData.get(timeframeKey)));
					timeFrame.getStartTime().setTimestamp(startCal);
					break;
				case TranscriptDocumentConstants.START_TIME_DESCRIPTION:
					String startTimeDescription = timeFrameColData.get(timeframeKey);
					if(startTimeDescription.contains(SETNULLCHARACTERS))
						timeFrame.getStartTime().setDescription(null);
					else{
						XmlAnySimpleType desc = XmlAnySimpleType.Factory.newInstance();
						desc.setStringValue(startTimeDescription);
						timeFrame.getStartTime().setDescription(desc);
					}
					break;
				case TranscriptDocumentConstants.END_TIME:
					Calendar endCal = Calendar.getInstance();
					endCal.setTime(df.parse(timeFrameColData.get(timeframeKey)));
					timeFrame.getEndTime().setTimestamp(endCal);
					break;
				case TranscriptDocumentConstants.END_TIME_DESCRIPTION:
					String endTimeDescription = timeFrameColData.get(timeframeKey);
					if(endTimeDescription.contains(SETNULLCHARACTERS))
						timeFrame.getEndTime().setDescription(null);
					else{
						XmlAnySimpleType desc = XmlAnySimpleType.Factory.newInstance();
						desc.setStringValue(endTimeDescription);
						timeFrame.getEndTime().setDescription(desc);
					}
					break;
				}
			}
		}
		return timeFrame;
	}

	/** Method to set the policy
	 * @param tDoc
	 * @param policyIndexIds
	 */
	@SuppressWarnings("unchecked")
	public void setPolicy(TranscriptDocument tDoc, String policyIndexIds) {
		List<String> listPolicyIds = null;
		if(policyIndexIds != null){
			if(listPolicyIds == null)
				listPolicyIds = new ArrayList<String>();
			if(policyIndexIds.contains(INDEXIDSEPARATOR))
				listPolicyIds = Arrays.asList(policyIndexIds.split(INDEXIDSEPARATOR));
			else
				listPolicyIds.add(policyIndexIds);	
		}
		
		if(listPolicyIds != null){
			tDoc.getTranscript().addNewPolicy();
			tDoc.getTranscript().getPolicy().addNewRetention();
			Retention retention =  tDoc.getTranscript().getPolicy().getRetention();
			
			// set the policies here from excel sheet
			for(String policyId : listPolicyIds){
				Policy policy = retention.addNewPolicy();
			
				List<Map<String, String>> policyRowData = 
						(List<Map<String, String>>)policySheetData.get(policyId);
				if(policyRowData != null){
					for(Map<String, String> policyColData : policyRowData){
						// set the id
						if(policyColData.get(TranscriptDocumentConstants.POLICY_ID) != null){
							String data = policyColData.get(TranscriptDocumentConstants.POLICY_ID);
							if(data.contains(SETNULLCHARACTERS))
								policy.setId(data);	
						}
						
						// set the action
						if(policyColData.get(TranscriptDocumentConstants.POLICY_ACTION) != null){
							String data = policyColData.get(TranscriptDocumentConstants.POLICY_ACTION);
							if(data.contains(SETNULLCHARACTERS))
								policy.setAction("");
							else
								policy.setAction(data);	
						}
						
						// set the description
						if(policyColData.get(TranscriptDocumentConstants.POLICY_DESCRIPTION) != null){
							String data = policyColData.get(TranscriptDocumentConstants.POLICY_DESCRIPTION);
						}
					}
				}
			}
		}
		
	}
	
	/**
	 * Method to set the Policy Violations
	 * @param tDoc
	 * @param policyViolationIds
	 * @throws IOException 
	 * @throws ValidationException 
	 */
	public void setPolicyEvents(TranscriptDocument tDoc, List<String> policyEventIds) throws IOException, ValidationException {
		List<PolicyEvent> policyEvents = null;
		if(policyEventIds != null && policyEventIds.size() > 0){
			
			if(tDoc.getTranscript().getInteraction().getPolicyEvents() == null)
				tDoc.getTranscript().getInteraction().addNewPolicyEvents();
			
			
			for(String id : policyEventIds){
				if(policyEvents == null)
					policyEvents = new ArrayList<PolicyEvent>();
				
				policyEvents.add(setPolicyEvent(tDoc.getTranscript().getInteraction(), id));
			}
		}
		
		if(policyEvents != null){
			for(int i =0 ; i< policyEvents.size() ; i++)
				tDoc.getTranscript().getInteraction().getPolicyEvents().addNewPolicyEvent();
			tDoc.getTranscript().getInteraction().getPolicyEvents()
					.setPolicyEventArray(policyEvents.toArray(new PolicyEvent[policyEvents.size()]));
			
		}
	}
	
	/**
	 * Method to set the Policy Violation
	 * @param id
	 * @return
	 * @throws IOException 
	 * @throws ValidationException 
	 */
	@SuppressWarnings("unchecked")
	private PolicyEvent setPolicyEvent(Interaction interaction, String policyEventIndexId) throws IOException, ValidationException {
		PolicyEvent policyEvent = null;
		List<Map<String, String>> policyEventRowData = (List<Map<String, String>>)
				policyEventSheetData.get(policyEventIndexId);
//		policyEvent.addNewEventTime();
		
		if(policyEventRowData != null){
			
			int policyEventAttributesCount = 1;
			policyEvent = PolicyEvent.Factory.newInstance();
			if(policyEvent != null){
				for(Map<String, String> policyEventColData : policyEventRowData){
					// set policy event id
					if(policyEventColData.get(TranscriptDocumentConstants.POLICY_EVENT_ID) != null){
						policyEvent.setPolicyEventId(policyEventColData.get(TranscriptDocumentConstants.POLICY_EVENT_ID));
					}
					
					// set violation type
					if(policyEventColData.get(TranscriptDocumentConstants.POLICY_EVENT_TYPE) != null && 
							!policyEventColData.get(TranscriptDocumentConstants.POLICY_EVENT_TYPE).contains(SETNULLCHARACTERS)){
						policyEvent.setPolicyEventType(
								policyEventColData.get(
										TranscriptDocumentConstants.POLICY_EVENT_TYPE));
					}
					
					// set violation sub-type
					if(policyEventColData.get(TranscriptDocumentConstants.POLICY_EVENT_SUB_TYPE) != null){
						String data = policyEventColData.get(TranscriptDocumentConstants.POLICY_EVENT_SUB_TYPE);
						if(!data.contains(SETNULLCHARACTERS))
							policyEvent.setPolicyEventSubType(data);
					}
					
					// set violation category
					if(policyEventColData.get(TranscriptDocumentConstants.POLICY_EVENT_CATEGORY) != null &&
							!policyEventColData.get(TranscriptDocumentConstants.POLICY_EVENT_CATEGORY).contains(SETNULLCHARACTERS)){
						policyEvent.setPolicyEventCategory(
								PolicyEventCategory.Enum.forString(
										policyEventColData.get(
												TranscriptDocumentConstants.POLICY_EVENT_CATEGORY)));
					}
					
					// set violator participant id
					if(policyEventColData.get(TranscriptDocumentConstants.POLICY_TRIGGERING_PARTICIPANT_ID) != null &&
							policyEventColData.get(TranscriptDocumentConstants.POLICY_TRIGGERING_PARTICIPANT_ID).trim().length() > 0){
						
						policyEvent.addNewTriggeringParticipantIds();
						String data = policyEventColData.get(TranscriptDocumentConstants.POLICY_TRIGGERING_PARTICIPANT_ID);
						if(!data.contains(SETNULLCHARACTERS)){
							String[] partids = null;
							if(data.contains(INDEXIDSEPARATOR))
								partids = data.split(INDEXIDSEPARATOR);
							else
								partids = new String[]{data};
//							if(policyEvent.getTriggeringParticipantIds() == null)
//								policyEvent.addNewTriggeringParticipantIds();
//							else{
//								policyEvent.getTriggeringParticipantIds().removeTriggeringParticipantId(0);
//							}
							for(String partId : partids)
								policyEvent.getTriggeringParticipantIds().addTriggeringParticipantId(partId);
						}
					}
					
					// set triggering text event ids
					if(policyEventColData.get(TranscriptDocumentConstants.POLICY_TRIGGERING_TEXTEVENT_ID) != null){
						String data = policyEventColData.get(TranscriptDocumentConstants.POLICY_TRIGGERING_TEXTEVENT_ID);
						if(!data.contains(SETNULLCHARACTERS)){
							String[] textids = null;
							if(data.contains(INDEXIDSEPARATOR))
								textids = data.split(INDEXIDSEPARATOR);
							else
								textids = new String[]{data};
							if(policyEvent.getTriggeringTextEventIds() == null)
								policyEvent.addNewTriggeringTextEventIds();
//							else{
//								policyEvent.getTriggeringTextEventIds().removeTextEventId(0);
//							}
							for(String textId : textids)
								policyEvent.getTriggeringTextEventIds().addTextEventId(textId);
						}
					}
					
					// set triggering file event ids
					if(policyEventColData.get(TranscriptDocumentConstants.POLICY_TRIGGERING_FILEEVENT_ID) != null){
						String data = policyEventColData.get(TranscriptDocumentConstants.POLICY_TRIGGERING_FILEEVENT_ID);
						if(!data.contains(SETNULLCHARACTERS)){
							String[] fileids = null;
							if(data.contains(INDEXIDSEPARATOR))
								fileids = data.split(INDEXIDSEPARATOR);
							else
								fileids = new String[]{data};
							if(policyEvent.getTriggeringFileEventIds() == null)
								policyEvent.addNewTriggeringFileEventIds();
//							else{
//								policyEvent.getTriggeringFileEventIds().removeFileEventId(0);
//							}
							for(String fileId : fileids)
								policyEvent.getTriggeringFileEventIds().addFileEventId(fileId);
						}
					}
					// set violation timestamp
					if(policyEventColData.get(TranscriptDocumentConstants.EVENT_TIME) != null){
//						policyEvent.addNewEventTime();
					
						String data = policyEventColData.get(TranscriptDocumentConstants.EVENT_TIME);
						if(!data.contains(SETNULLCHARACTERS)){
							try {
									SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
									Calendar cal = Calendar.getInstance();
									cal.setTime(df.parse(data));
									if(policyEvent.getEventTime() == null)
										policyEvent.addNewEventTime();
									policyEvent.getEventTime().setTimestamp(cal);
							}catch (ParseException e) {
										// TODO						
							}
						}
					}
					
					// set the event time description
					if(policyEventColData.get(TranscriptDocumentConstants.POLICY_EVENT_TIMESTAMP_DESCRIPTION) != null){
//						policyEvent.addNewEventTime();
						String eventTimeDescription = policyEventColData.get(TranscriptDocumentConstants.POLICY_EVENT_TIMESTAMP_DESCRIPTION);
						if(eventTimeDescription.contains(SETNULLCHARACTERS))
							policyEvent.getEventTime().setDescription(null);
						else{
							XmlAnySimpleType desc = XmlAnySimpleType.Factory.newInstance();
							desc.setStringValue(eventTimeDescription);
							policyEvent.getEventTime().setDescription(desc);
						}
					}
					
					// set the action event id
					if(policyEventColData.get(TranscriptDocumentConstants.POLICY_ACTIONEVENT_ID) != null){
						String data = policyEventColData.get(TranscriptDocumentConstants.POLICY_ACTIONEVENT_ID);
						
//						
						if(data.contains(SETNULLCHARACTERS))
						{
							policyEvent.addNewActionEventIds();
							policyEvent.getActionEventIds().setActionEventIdArray(0, data);
						}
					}
					
					// set violation text content
					if(policyEventColData.get(TranscriptDocumentConstants.TEXTCONTENTINDEXID) != null){
						String data = policyEventColData.get(TranscriptDocumentConstants.TEXTCONTENTINDEXID);
						if(!data.contains(SETNULLCHARACTERS)){
							List<Map<String, String>> textContentRowData = 
									(List<Map<String, String>>)textContentSheetData.get(data);
							if(textContentRowData != null){
								policyEvent.addNewTextContent();
								for(Map<String, String> textContentColData : textContentRowData){
									if(textContentColData.get(TranscriptDocumentConstants.CONTENTTYPE) != null){
										String contentData = textContentColData.get(TranscriptDocumentConstants.CONTENTTYPE);
										if(!contentData.contains(SETNULLCHARACTERS))
											policyEvent.getTextContent().setContentType(MimeType.Enum.forString(contentData));
									}
									
									if(textContentColData.get(TranscriptDocumentConstants.TEXTCONTENT_ID) != null){
										String contentData = textContentColData.get(TranscriptDocumentConstants.TEXTCONTENT_ID);
										if(contentData.contains(SETNULLCHARACTERS))
											policyEvent.getTextContent().setStringValue("");
										else{
												InputStream is = new FileInputStream(new File(resourcesPath+"Text/"+ contentData));
												StringWriter writer = new StringWriter();
												IOUtils.copy(is, writer, "UTF-8");
												policyEvent.getTextContent().setStringValue(writer.toString());
										}
									}
								}
							}
						}
					}
					
					// set policy
					if(policyEventColData.get(TranscriptDocumentConstants.POLICY) != null){
						String data = policyEventColData.get(TranscriptDocumentConstants.POLICY);
						if(!data.contains(SETNULLCHARACTERS))
							policyEvent.setPolicy(data);
						
					}
					
					// set attributes
					if(policyEventColData.get(TranscriptDocumentConstants.POLICYEVENTATTRIBUTESINDEXID) != null){
						String data = policyEventColData.get(TranscriptDocumentConstants.POLICYEVENTATTRIBUTESINDEXID);
						if(!data.contains(SETNULLCHARACTERS)){
								// remove if any policy violation attributes
								for(int i =0; i< policyEvent.getAttributes().sizeOfAttributeArray(); i++)
									policyEvent.getAttributes().removeAttribute(i);
								
								IndexableAttributes violationAttributes = policyEvent.getAttributes();
								
								List<IndexableAttributes.Attribute> listViolationAttributes = new ArrayList<IndexableAttributes.Attribute>();
								if(data.contains(INDEXIDSEPARATOR)){
									String[] ids = data.split(INDEXIDSEPARATOR);
									for(String id : ids)
										listViolationAttributes.add(setIndexableAttributes(violationAttributes, id));
								}
								else{
									listViolationAttributes.add(setIndexableAttributes(violationAttributes, data));
								}
								policyEvent.getAttributes().setAttributeArray(listViolationAttributes.toArray
										(new IndexableAttributes.Attribute[listViolationAttributes.size()]));
							}
						}
					}		
			}
		}
		
		return policyEvent;
	}

	/**
	 * Method to set the ActionEvents
	 * @param tDoc
	 * @param actionEventIds
	 * @throws Exception 
	 */
	public void setActionEvents(TranscriptDocument tDoc,
			List<String> actionEventIds) throws Exception{
		List<ActionEvent> actionEvents = null;
		if(actionEventIds != null && actionEventIds.size() > 0){
			tDoc.getTranscript().getInteraction().addNewActionEvents();
			for(String id : actionEventIds){
				if(actionEvents == null)
					actionEvents = new ArrayList<ActionEvent>();		
				actionEvents.add(setActionEvent(null, id));
			}
		}
		
		if(actionEvents != null)
		{
			for(int i =0 ; i< actionEvents.size() ; i++)
				tDoc.getTranscript().getInteraction().getActionEvents().addNewActionEvent();
			tDoc.getTranscript().getInteraction().getActionEvents()
			.setActionEventArray(actionEvents.toArray(new ActionEvent[actionEvents.size()]));
	
		}
	}

	/**
	 * Method to set the Action Event
	 * @param actionEventIndexId
	 * @return object of ActionEvent
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	public ActionEvent setActionEvent(ActionEvent actionEvent, String actionEventIndexId) throws Exception {
		List<Map<String, String>> actionEventRowData = (List<Map<String, String>>)
				actionEventSheetData.get(actionEventIndexId);
		
	
		if(actionEventRowData != null){
			int actionAttributesCount = 1;
			if(actionEvent == null)
				actionEvent = ActionEvent.Factory.newInstance();
			
			if(actionEvent != null){
				for(Map<String, String> actionEventColData : actionEventRowData){
					// set the action event id
					if(actionEventColData.get(TranscriptDocumentConstants.ACTION_EVENT_ID) != null &&
							!actionEventColData.get(TranscriptDocumentConstants.ACTION_EVENT_ID).contains(SETNULLCHARACTERS)){
						actionEvent.setActionEventId(actionEventColData.get(TranscriptDocumentConstants.ACTION_EVENT_ID));
					}
					
					// set the parent action event id
					if(actionEventColData.get(TranscriptDocumentConstants.PARENT_ACTION_EVENT_ID) != null){
						String data = actionEventColData.get(TranscriptDocumentConstants.PARENT_ACTION_EVENT_ID);
						if(!data.contains(SETNULLCHARACTERS))
							actionEvent.setParentActionEventId(data);
					}
					
					// set the action type
					if(actionEventColData.get(TranscriptDocumentConstants.ACTION_TYPE) != null && 
							!actionEventColData.get(TranscriptDocumentConstants.ACTION_TYPE).contains(SETNULLCHARACTERS)){
						actionEvent.setActionType(ActionType.Enum.forString(
								actionEventColData.get(TranscriptDocumentConstants.ACTION_TYPE).trim()));
					}
					
					// set the action sub type
					if(actionEventColData.get(TranscriptDocumentConstants.ACTION_SUB_TYPE) != null){
						String data = actionEventColData.get(TranscriptDocumentConstants.ACTION_SUB_TYPE);
						if(!data.contains(SETNULLCHARACTERS))
							actionEvent.setActionSubType(data);
					}
					
					// set the action state
					if(actionEventColData.get(TranscriptDocumentConstants.ACTION_STATE) != null){
						String data = actionEventColData.get(TranscriptDocumentConstants.ACTION_STATE);
						if(!data.contains(SETNULLCHARACTERS))
							actionEvent.setActionState(data);
					}
					
					// set the policy
					if(actionEventColData.get(TranscriptDocumentConstants.POLICY) != null){
						String data = actionEventColData.get(TranscriptDocumentConstants.POLICY);
						if(!data.contains(SETNULLCHARACTERS))
							actionEvent.setPolicy(data);
					}
					
					// set the event time
					if(actionEventColData.get(TranscriptDocumentConstants.EVENTTIME) != null){
						String data = actionEventColData.get(TranscriptDocumentConstants.EVENTTIME);
						if(!data.contains(SETNULLCHARACTERS))
						{
							try {
								{
									SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
									Calendar cal = Calendar.getInstance();
									cal.setTime(df.parse(data));
									if(actionEvent.getEventTime() == null)
										actionEvent.addNewEventTime();
									actionEvent.getEventTime().setTimestamp(cal);
								}
							} catch (ParseException e) {
										// TODO						
							}
						}
					}
					
					// set the event time description
					if(actionEventColData.get(TranscriptDocumentConstants.EVENTTIME_DESCRIPTION) != null){
						String data = actionEventColData.get(TranscriptDocumentConstants.EVENTTIME_DESCRIPTION);
						if(data.contains(SETNULLCHARACTERS))
							actionEvent.getEventTime().setDescription(null);
						else{
							XmlAnySimpleType desc = XmlAnySimpleType.Factory.newInstance();
							desc.setStringValue(data);
							if(actionEvent.getEventTime() == null)
								actionEvent.addNewEventTime();
							actionEvent.getEventTime().setDescription(desc);
						}
						
					}
					
					// set the actor participant id
					if(actionEventColData.get(TranscriptDocumentConstants.ACTOR_PARTICIPANT_ID) != null){
						String data = actionEventColData.get(TranscriptDocumentConstants.ACTOR_PARTICIPANT_ID);
						if( data.length() > 0 && !data.contains(SETNULLCHARACTERS)){
							String[] partids = null;
							if(data.contains(INDEXIDSEPARATOR))
								partids = data.split(INDEXIDSEPARATOR);
							else
								partids = new String[]{data};
							if(actionEvent.getActorParticipantIds() == null)
								actionEvent.addNewActorParticipantIds();
							else{
								actionEvent.getActorParticipantIds().removeActorParticipantId(0);
							}
							for(String partId : partids)
								actionEvent.getActorParticipantIds().addActorParticipantId(partId);
						}
					}
					
					// set the assignee participant id
					if(actionEventColData.get(TranscriptDocumentConstants.ASSIGNEE_PARTICIPANT_ID) != null){
						String data = actionEventColData.get(TranscriptDocumentConstants.ASSIGNEE_PARTICIPANT_ID);
						if(!data.contains(SETNULLCHARACTERS))
							{
							String[] partids = null;
							if(data.contains(INDEXIDSEPARATOR))
								partids = data.split(INDEXIDSEPARATOR);
							else
								partids = new String[]{data};
							if(actionEvent.getAssigneeParticipantIds() == null)
								actionEvent.addNewAssigneeParticipantIds();
							else{
								actionEvent.getAssigneeParticipantIds().removeAssigneeParticipantId(0);
							}
							for(String partId : partids)
								actionEvent.getAssigneeParticipantIds().addAssigneeParticipantId(partId);
						}
					}
					
					// set the subject
					if(actionEventColData.get(TranscriptDocumentConstants.SUBJECTINDEXID) != null){
						String data = actionEventColData.get(TranscriptDocumentConstants.SUBJECTINDEXID);
						if(!data.contains(SETNULLCHARACTERS)){
							if(actionEvent.getSubject() == null)
								actionEvent.addNewSubject();
							actionEvent.setSubject(setSubject(actionEvent.getSubject(), data));
						}
					}
					
					// set the text content
					if(actionEventColData.get(TranscriptDocumentConstants.TEXTCONTENTINDEXID) != null){
						String data = actionEventColData.get(TranscriptDocumentConstants.TEXTCONTENTINDEXID);
						if(!data.contains(SETNULLCHARACTERS))
							{
							List<Map<String, String>> textContentRowData = 
									(List<Map<String, String>>)textContentSheetData.get(data);
							if(textContentRowData != null){
								actionEvent.addNewTextContent();
								for(Map<String, String> textContentColData : textContentRowData){
									if(textContentColData.get(TranscriptDocumentConstants.CONTENTTYPE) != null){
										String contentData = textContentColData.get(TranscriptDocumentConstants.CONTENTTYPE);
										if(!contentData.contains(SETNULLCHARACTERS))
											actionEvent.getTextContent().setContentType(MimeType.Enum.forString(contentData));
									}
									
									if(textContentColData.get(TranscriptDocumentConstants.TEXTCONTENT_ID) != null){
										String contentData = textContentColData.get(TranscriptDocumentConstants.TEXTCONTENT_ID);
										if(contentData.contains(SETNULLCHARACTERS))
											actionEvent.getTextContent().setStringValue("");
										else{
												InputStream is = new FileInputStream(new File(resourcesPath +"Text/" +contentData));
												StringWriter writer = new StringWriter();
												IOUtils.copy(is, writer, "UTF-8");
												actionEvent.getTextContent().setStringValue(writer.toString());
										}
									}
								}
							}
						}
					}
					
					// set the action attributes
					if(actionEventColData.get(TranscriptDocumentConstants.ACTIONATTRIBUTESINDEXID) != null){
						String data = actionEventColData.get(TranscriptDocumentConstants.ACTIONATTRIBUTESINDEXID);
						if(!data.contains(SETNULLCHARACTERS))
						{
							actionEvent.addNewAttributes();
							IndexableAttributes actionAttributes = actionEvent.getAttributes();
							
							List<IndexableAttributes.Attribute> listActionAttributes = new ArrayList<IndexableAttributes.Attribute>();
							String[] ids = data.split(INDEXIDSEPARATOR);
							for(String id : ids)
								listActionAttributes.add(setIndexableAttributes(actionAttributes, id));
														
							for(int i =0; i< listActionAttributes.size(); i++){
								actionEvent.getAttributes().setAttributeArray(i, listActionAttributes.get(i));
							}
						}
					}
					
				}		
			}
		}
		
	
		return actionEvent;
	}
	
	/**
	 * Method to set the action attributes
	 * @param actionAttributes
	 * @param actionAttributeIndexId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public GeneralAttributes.Attribute setGeneralAttributes(GeneralAttributes attributes, String attributeIndexId){
		List<Map<String, String>> generalAttributesRowData = (List<Map<String, String>>)
				attributesSheetData.get(attributeIndexId);
		GeneralAttributes.Attribute attribute = null;
		
		if(generalAttributesRowData != null){
			attribute = attributes.addNewAttribute();
			
			for(Map<String, String> attributesColData : generalAttributesRowData){
				// set the name
				if(attributesColData.get(TranscriptDocumentConstants.NAME) != null &&
						!attributesColData.get(TranscriptDocumentConstants.NAME).equals(SETNULLCHARACTERS))
				{
					
					attribute.setName(attributesColData.get(TranscriptDocumentConstants.NAME));
					attribute.getName();
				}
				
				// set the value
				if(attributesColData.get(TranscriptDocumentConstants.VALUE) != null){
					String data = attributesColData.get(TranscriptDocumentConstants.VALUE);
					if(data.contains(SETNULLCHARACTERS))
						attribute.setStringValue("");
					else
						attribute.setStringValue(data);
				}
				
				// set the content-type
				if(attributesColData.get(TranscriptDocumentConstants.CONTENTTYPE) != null){
					String data = attributesColData.get(TranscriptDocumentConstants.CONTENTTYPE);
					if(!data.contains(SETNULLCHARACTERS))
						attribute.setContentType(data);
								//MimeType.Enum.forString(data));
				}	
			}
		}
		return attribute;
	}

	/*
	 * Method to set the file events based on the values form the excel sheet
	 * @param tDoc
	 * @param fileEventIds
	 * @throws UnsupportedEncodingException
	 * @throws ParseException
	 */
	public void setFileEvents(TranscriptDocument tDoc,
			List<String> fileEventIds) throws Exception {
		List<FileEvent> fileEvents = null;
		
	
		
		if( fileEventIds.size() > 0){
			for(String id : fileEventIds){
				if(fileEvents == null)
				{	
					
					fileEvents = new ArrayList<FileEvent>();
				}
				fileEvents.add(setFileEvent(id));
				
			}
		}
		if(fileEvents != null){
			tDoc.getTranscript().getInteraction().addNewFileEvents();
			for(int i =0 ; i< fileEvents.size(); i++){
				tDoc.getTranscript().getInteraction().getFileEvents().addNewFileEvent();
				tDoc.getTranscript().getInteraction().getFileEvents().setFileEventArray(i, fileEvents.get(i));
			}
		}
	}

	/**
	 * Method to set the file event based on the values form the excel sheet
	 * @param fileEventIndexId
	 * @return
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	private FileEvent setFileEvent(String fileEventIndexId) throws Exception {
		FileEvent fileEvent = null;
		List<Map<String, String>> fileEvntRowData = (List<Map<String, String>>)
				fileEventSheetData.get(fileEventIndexId);
		
		if(fileEvntRowData != null){
			
			int attributesCount = 1;
			int lexiconHitCount = 1;
			fileEvent = FileEvent.Factory.newInstance();
			fileEvent.addNewFileObject();
			if(fileEvent != null){
				for(Map<String, String> fileEventColData : fileEvntRowData){
					// set the file event id
					if(fileEventColData.get(TranscriptDocumentConstants.FILEEVENTID) != null &&
							!fileEventColData.get(TranscriptDocumentConstants.FILEEVENTID).equals(SETNULLCHARACTERS) ){
						fileEvent.setFileEventId(fileEventColData.get(TranscriptDocumentConstants.FILEEVENTID));
					}
					
					//set the file evnt type
					if(fileEventColData.get(TranscriptDocumentConstants.FILEEVENTTYTPE) != null &&
							!fileEventColData.get(TranscriptDocumentConstants.FILEEVENTTYTPE).equals(SETNULLCHARACTERS) ){
						fileEvent.setFileEventType(FileEventType.Enum.forString(
								fileEventColData.get(TranscriptDocumentConstants.FILEEVENTTYTPE)));
					}
					
					// set the file event action
					if(fileEventColData.get(TranscriptDocumentConstants.ACTION) != null){
						String data = fileEventColData.get(TranscriptDocumentConstants.ACTION); 
						if(!data.contains(SETNULLCHARACTERS))
							fileEvent.setAction(Action.Enum.forString(data));
					}
					
					// set the file object id
					if(fileEventColData.get(TranscriptDocumentConstants.FILEOBJECTID) != null){
						String data = fileEventColData.get(TranscriptDocumentConstants.FILEOBJECTID);
						if(!data.contains(SETNULLCHARACTERS))
							fileEvent.getFileObject().setFileObjectId(data);
					}
					
					// set the parent file object id
					if(fileEventColData.get(TranscriptDocumentConstants.PARENTFILEOBJECTID) != null){
						String data = fileEventColData.get(TranscriptDocumentConstants.PARENTFILEOBJECTID);
						if(!data.contains(SETNULLCHARACTERS))
							fileEvent.getFileObject().setParentFileObjectId(data);
					}
					
					// set the event time
					if(fileEventColData.get(TranscriptDocumentConstants.EVENTTIME)!= null){
						String data = fileEventColData.get(TranscriptDocumentConstants.EVENTTIME);
						if(!data.contains(SETNULLCHARACTERS))
							try {
								{
									SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
									Calendar cal = Calendar.getInstance();
									cal.setTime(df.parse(data));
									if(fileEvent.getFileObject().getEventTime() == null)
										fileEvent.getFileObject().addNewEventTime();
									fileEvent.getFileObject().getEventTime().setTimestamp(cal);
								}
							} catch (ParseException e) {
																
							}
					}
					
					// set the event time description
					if(fileEventColData.get(TranscriptDocumentConstants.EVENTTIME_DESCRIPTION) != null){
						String eventTimeDescription = fileEventColData.get(TranscriptDocumentConstants.EVENTTIME_DESCRIPTION);
						if(eventTimeDescription.contains(SETNULLCHARACTERS))
							fileEvent.getFileObject().getEventTime().setDescription(null);
						else{
							XmlAnySimpleType desc = XmlAnySimpleType.Factory.newInstance();
							desc.setStringValue(eventTimeDescription);
							if(fileEvent.getFileObject().getEventTime() == null)
								fileEvent.getFileObject().addNewEventTime();
							fileEvent.getFileObject().getEventTime().setDescription(desc);
						}
					}
										
					// set the participant id
					if(fileEventColData.get(TranscriptDocumentConstants.PARTICIPANTID) != null){
						String data = fileEventColData.get(TranscriptDocumentConstants.PARTICIPANTID);
						if(!data.contains(SETNULLCHARACTERS))
							fileEvent.getFileObject().setParticipantId(data);
					}
					
					// set the file object url
					if(fileEventColData.get(TranscriptDocumentConstants.FILEOBJECTURL) != null){
						String data = fileEventColData.get(TranscriptDocumentConstants.FILEOBJECTURL);
						if(!data.contains(SETNULLCHARACTERS))
							fileEvent.getFileObject().setFileObjectUrl(data);
					}
					
					
					// set the file content index id
					if(fileEventColData.get(TranscriptDocumentConstants.FILECONTENTINDEXID) != null){
						String data = fileEventColData.get(TranscriptDocumentConstants.FILECONTENTINDEXID);
						if(!data.contains(SETNULLCHARACTERS))
						{
							fileEvent.getFileObject().addNewFileContent();
							fileEvent.getFileObject().setFileContent(setFileContent(fileEvent.getFileObject().getFileContent() , data));
						}
					}
					
		
					// set the attributes
					if(fileEventColData.get(TranscriptDocumentConstants.ATTRIBUTESINDEXID) != null){
						String data = fileEventColData.get(TranscriptDocumentConstants.ATTRIBUTESINDEXID);
						if(!data.contains(SETNULLCHARACTERS))
						{
							fileEvent.getFileObject().addNewAttributes();
							IndexableAttributes attributes = fileEvent.getFileObject().getAttributes();
							
							List<Attribute> listAttributes = new ArrayList<Attribute>();
							if(data.contains(INDEXIDSEPARATOR)){
								String[] ids = data.split(INDEXIDSEPARATOR);
								for(String id : ids)
									listAttributes.add(setIndexableAttributes(attributes, id));
							}
							else{
								listAttributes.add(setIndexableAttributes(attributes, data));
							}
						
							
							
							fileEvent.getFileObject().getAttributes().setAttributeArray(listAttributes.toArray
									(new Attribute[listAttributes.size()]));
							
							
						}
					}
					
				
					if(fileEventColData.get(TranscriptDocumentConstants.LEXICONHITINDEXID) != null){
						String data = fileEventColData.get(TranscriptDocumentConstants.LEXICONHITINDEXID);
						if(!data.contains(SETNULLCHARACTERS))
						{
							fileEvent.getFileObject().addNewLexiconHits();
							List<String> ids = new ArrayList<String>();
							if(data.contains(INDEXIDSEPARATOR)){
								for(String id : data.split(INDEXIDSEPARATOR))
									ids.add(id);
							}else{
								ids.add(data);
							}
							LexiconHits hits = setFileObjectLexiconHit(fileEvent.getFileObject().getLexiconHits(),
									ids);
							if(hits != null)
								fileEvent.getFileObject().setLexiconHits(hits);
						}
					}
					
					
					
				}		
			}
		}
		
		return fileEvent;
	}

	/**
	 * Method to set the file contents based on the values form the excel sheet
	 * @param fileEvent
	 * @param fileContentIndexId
	 * @throws IOException 
	 */
	@SuppressWarnings("unchecked")
	public FileContent setFileContent(FileContent fileContent, String fileContentIndexId) throws IOException {
		List<Map<String,String>> fileContentRowData = (List<Map<String, String>>)
				fileContentSheetData.get(fileContentIndexId);
			
		if(fileContentRowData != null){
						
			for(Map<String, String> fileContentColData : fileContentRowData){
				// set file content name
				if(fileContentColData.get(TranscriptDocumentConstants.NAME) != null){
					String data = fileContentColData.get(TranscriptDocumentConstants.NAME);
					if(!data.contains(SETNULLCHARACTERS))
						fileContent.setName(data);
				}
				
				// set file content content-type
				
				
				if(fileContentColData.get(TranscriptDocumentConstants.CONTENTTYPE) != null){
					
					String data = fileContentColData.get(TranscriptDocumentConstants.CONTENTTYPE);
					if(!data.contains(SETNULLCHARACTERS))
						fileContent.setContentType(data);
				}
				
				// set file content encoding
				if(fileContentColData.get(TranscriptDocumentConstants.ENCODING) != null){
					String data = fileContentColData.get(TranscriptDocumentConstants.ENCODING);
					if(!data.contains(SETNULLCHARACTERS))
						fileContent.setEncoding(Encoding.Enum.forString(data));
				}
				
				// set file content indexable
				if(fileContentColData.get(TranscriptDocumentConstants.INDEXABLE) != null){
					String data = fileContentColData.get(TranscriptDocumentConstants.INDEXABLE);
					if(!data.contains(SETNULLCHARACTERS))
						fileContent.setIndexable(Boolean.parseBoolean(data));
				}
				
				// set file content visiblity
				if(fileContentColData.get(TranscriptDocumentConstants.USERVISIBLE) != null){
					String data = fileContentColData.get(TranscriptDocumentConstants.USERVISIBLE);
					if(!data.contains(SETNULLCHARACTERS))
					{
						fileContent.getUserVisible();
						//fileContent.setUserVisible(UserVisibility.Enum.forString(data));
						fileContent.setUserVisible(fileContent.getUserVisible());
					}
					
							}
				
				// set the file content
				if(fileContentColData.get(TranscriptDocumentConstants.FILECONTENT_ID) != null)
				{
					String data = fileContentColData.get(TranscriptDocumentConstants.FILECONTENT_ID);
					if(data.contains(SETNULLCHARACTERS))
						fileContent.setStringValue("");
					else
					{
						try
						{
							InputStream is = new FileInputStream(new File(resourcesPath+"File/" + data));
							StringWriter writer = new StringWriter();
							IOUtils.copy(is, writer, "UTF-8");
							fileContent.setStringValue(Utils.encodeFileToBase64( resourcesPath+"File/"+ data));

						
						} catch (FileNotFoundException e)
						{
							System.out.println("Exception occured while parsing the file content");
						}
					}
						
				}
				
				
				}
						
				}	
						
	
		return fileContent;
	}
	
	/**
	 * Method to set the lexicon hits
	 * @param hits
	 * @param lexiconIds
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public com.actiance.platform.commons.xmlns.apc.dig.itm.interaction.transcript.x10.FileObject.LexiconHits setFileObjectLexiconHit(com.actiance.platform.commons.xmlns.apc.dig.itm.interaction.transcript.x10.FileObject.LexiconHits hits,
			List<String> lexiconIds){
		List<LexiconHit> listLexiconHits = null;
		
		if(lexiconIds.size() > 0){
			for(String id : lexiconIds){
				if(listLexiconHits == null)
					listLexiconHits = new ArrayList<LexiconHit>();
				
				List<Map<String, String>> lexiconhitRowData = (List<Map<String, String>>)
						lexiconHitSheetData.get(id);
				
//				LexiconHit hit = hits.getLexiconHitArray(0);
				LexiconHit hit = hits.addNewLexiconHit();
				
				if(lexiconhitRowData != null){
					for(Map<String, String> lexiconHitColData : lexiconhitRowData){
						// set match-type
						if(lexiconHitColData.get(TranscriptDocumentConstants.MATCHTYPE) != null &&
								!lexiconHitColData.get(TranscriptDocumentConstants.MATCHTYPE).equals(SETNULLCHARACTERS))
							hit.setMatchType(MatchType.Enum.forString(
									lexiconHitColData.get(TranscriptDocumentConstants.MATCHTYPE)));
						// set case sensitive
						if(lexiconHitColData.get(TranscriptDocumentConstants.CASE_SENSITIVE) != null){
							String data = lexiconHitColData.get(TranscriptDocumentConstants.CASE_SENSITIVE);
							if(!data.contains(SETNULLCHARACTERS))
								hit.setCaseSensitive(Boolean.parseBoolean(data));
						}
						
						// set origin
						if(lexiconHitColData.get(TranscriptDocumentConstants.ORIGIN) != null){
							String data = lexiconHitColData.get(TranscriptDocumentConstants.ORIGIN);
							if(!data.contains(SETNULLCHARACTERS))
								hit.setOrigin(data);
						}
						
						// set category
						if(lexiconHitColData.get(TranscriptDocumentConstants.CATEGORY) != null){
							String data = lexiconHitColData.get(TranscriptDocumentConstants.CATEGORY);
							if(!data.contains(SETNULLCHARACTERS))
								hit.setCategory(data);
						}
						
						// set near distance
						if(lexiconHitColData.get(TranscriptDocumentConstants.NEAR_DISTANCE) != null){
							String data = lexiconHitColData.get(TranscriptDocumentConstants.NEAR_DISTANCE);
							if(!data.contains(SETNULLCHARACTERS))
								hit.setNearDistance(new BigInteger(data));
						}
						
						// set Offset
						if(lexiconHitColData.get(TranscriptDocumentConstants.OFFSET) != null){
							String data = lexiconHitColData.get(TranscriptDocumentConstants.OFFSET);
							if(!data.contains(SETNULLCHARACTERS))
								hit.setOffset(Long.parseLong(data));
						}
						
						// set length
						if(lexiconHitColData.get(TranscriptDocumentConstants.LENGTH) != null){
							String data = lexiconHitColData.get(TranscriptDocumentConstants.LENGTH);
							if(!data.contains(SETNULLCHARACTERS))
								hit.setLength(new BigInteger(data));
						}
						
						// set policy
						if(lexiconHitColData.get(TranscriptDocumentConstants.POLICY) != null){
							String data = lexiconHitColData.get(TranscriptDocumentConstants.POLICY);
							if(!data.contains(SETNULLCHARACTERS))
								hit.setPolicy(data);
						}
						
						// set plain text
						if(lexiconHitColData.get(TranscriptDocumentConstants.PLAIN_TEXT) != null){
							String data = lexiconHitColData.get(TranscriptDocumentConstants.PLAIN_TEXT);
							if(!data.contains(SETNULLCHARACTERS))
								hit.setPlainText(Boolean.parseBoolean(data));
						}
						
						// set the value
						if(lexiconHitColData.get(TranscriptDocumentConstants.VALUE) != null){
							String data = lexiconHitColData.get(TranscriptDocumentConstants.VALUE);
							if(data.contains(SETNULLCHARACTERS))
								hit.setStringValue(null);
							else
								hit.setStringValue(data);
						}
						
					}
				}
				listLexiconHits.add(hit);
			}
		}
		
		if(listLexiconHits != null){
			hits.setLexiconHitArray(listLexiconHits.toArray(new LexiconHit[listLexiconHits.size()]));
			
		}
		
		return hits;
	}


	/**
	 * Method to set the text events based on the values form the excel sheet
	 * @param tDoc
	 * @param textEventIds
	 * @throws Exception 
	 */
	
	
	public void setTextEvents(TranscriptDocument tDoc,
			List<String> textEventIds) throws Exception {
		List<TextEvent> textEvents = null;
		
		if(textEventIds.size() > 0){
			for(String id : textEventIds)
			{
				
				if(textEvents == null)
					textEvents = new ArrayList<TextEvent>();
				textEvents.add(setTextEvent(id));
				
			}
		}
		if(textEvents != null){
			tDoc.getTranscript().getInteraction().addNewTextEvents();
			for(int i =0; i<textEvents.size(); i++){
				tDoc.getTranscript().getInteraction().getTextEvents().addNewTextEvent();
				tDoc.getTranscript().getInteraction().getTextEvents().setTextEventArray(i, textEvents.get(i));
			}
		}
		
	}


	/**
	 * Method to set the text event based on the values form the excel sheet
	 * @param textEventIndexId
	 * @return
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	private TextEvent setTextEvent(String textEventIndexId) throws Exception {
		TextEvent textEvent = null;
		List<Map<String, String>> textEventRowData = (List<Map<String, String>>)
				textEventSheetData.get(textEventIndexId);
		
		if(textEventRowData != null){
			
			int attributesCount = 1;
			int lexiconHitCount = 1;
			textEvent = TextEvent.Factory.newInstance();
			textEvent.addNewTextObject();
			if(textEvent != null){
				for(Map<String, String> textEventColData : textEventRowData){
					// set the text-event-id
					if(textEventColData.get(TranscriptDocumentConstants.TEXTEVENTID) != null &&
							!textEventColData.get(TranscriptDocumentConstants.TEXTEVENTID).equals(SETNULLCHARACTERS) ){
						textEvent.setTextEventId(textEventColData.get(TranscriptDocumentConstants.TEXTEVENTID));
					}
					
					// set action
					if(textEventColData.get(TranscriptDocumentConstants.ACTION) != null){
						String data = textEventColData.get(TranscriptDocumentConstants.ACTION); 
						if(!data.contains(SETNULLCHARACTERS))
							textEvent.setAction(Action.Enum.forString(data));
					}
				
					// set operation
					if(textEventColData.get(TranscriptDocumentConstants.TEXTEVENTOPERATION) != null){
						String data = textEventColData.get(TranscriptDocumentConstants.TEXTEVENTOPERATION); 
						if(!data.contains(SETNULLCHARACTERS))
							textEvent.setOperation(data);
					}
					
					// set text-object-id
					if(textEventColData.get(TranscriptDocumentConstants.TEXTOBJECTID) != null &&
							!textEventColData.get(TranscriptDocumentConstants.TEXTOBJECTID).equals(SETNULLCHARACTERS) ){
						textEvent.getTextObject().setTextObjectId(textEventColData.get(TranscriptDocumentConstants.TEXTOBJECTID));
					}
					
					// set parent-text-object-id
					if(textEventColData.get(TranscriptDocumentConstants.PARENTTEXTOBJECTID) != null){
						String data = textEventColData.get(TranscriptDocumentConstants.PARENTTEXTOBJECTID);
						if(textEvent.getTextObject().getParentTextObjectId() != null){
							if(!data.contains(SETNULLCHARACTERS))
								textEvent.getTextObject().setParentTextObjectId(data);
						}
					}

					// set system-flag
					if(textEventColData.get(TranscriptDocumentConstants.SYSTEM_FLAG) != null &&
							!textEventColData.get(TranscriptDocumentConstants.SYSTEM_FLAG).equals(SETNULLCHARACTERS)){
						textEvent.getTextObject().setSystemFlag(Boolean.parseBoolean(
								textEventColData.get(TranscriptDocumentConstants.SYSTEM_FLAG)));
					}
					
					// set text-object-style
					if(textEventColData.get(TranscriptDocumentConstants.TEXTOBJECTSTYLE) != null &&
							!textEventColData.get(TranscriptDocumentConstants.TEXTOBJECTSTYLE).equals(SETNULLCHARACTERS) ){
						textEvent.getTextObject().setTextObjectStyle(TextEventType.Enum.forString(
								textEventColData.get(TranscriptDocumentConstants.TEXTOBJECTSTYLE)));
					}
					
					// set text-object-type
					if(textEventColData.get(TranscriptDocumentConstants.TEXTOBJECTTYPE) != null){
						String data = textEventColData.get(TranscriptDocumentConstants.TEXTOBJECTTYPE);
						if(!data.contains(SETNULLCHARACTERS))
							textEvent.getTextObject().setTextObjectType(data);
					}
					
					// set text-object-sub-type
					if(textEventColData.get(TranscriptDocumentConstants.TEXTOBJECTSUBTYPE) != null){
						String data = textEventColData.get(TranscriptDocumentConstants.TEXTOBJECTSUBTYPE);
						if(!data.contains(SETNULLCHARACTERS))
							textEvent.getTextObject().setTextObjectSubType(data);
					}
					
					// set event-time
					if(textEventColData.get(TranscriptDocumentConstants.EVENTTIME)!= null){
						String data = textEventColData.get(TranscriptDocumentConstants.EVENTTIME);
						if(!data.contains(SETNULLCHARACTERS)){
							SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
							Calendar cal = Calendar.getInstance();
							cal.setTime(df.parse(data));
							if(textEvent.getTextObject().getEventTime() == null)
								textEvent.getTextObject().addNewEventTime();
							textEvent.getTextObject().getEventTime().setTimestamp(cal);
						}
					}
					
					// set event time description
					if(textEventColData.get(TranscriptDocumentConstants.EVENTTIME_DESCRIPTION) != null){
						String description = textEventColData.get(TranscriptDocumentConstants.EVENTTIME_DESCRIPTION);
						if(description.contains(SETNULLCHARACTERS))
							textEvent.getTextObject().getEventTime().setDescription(null);
						else{
							XmlAnySimpleType desc = XmlAnySimpleType.Factory.newInstance();
							desc.setStringValue(description);
							if(textEvent.getTextObject().getEventTime() == null)
								textEvent.getTextObject().addNewEventTime();
							textEvent.getTextObject().getEventTime().setDescription(desc);
						}
					}
					
					// set participant-id
					if(textEventColData.get(TranscriptDocumentConstants.PARTICIPANTID) != null){
						String data = textEventColData.get(TranscriptDocumentConstants.PARTICIPANTID);
						if(!data.contains(SETNULLCHARACTERS)){
							textEvent.getTextObject().setParticipantId(data);
						}
					}
					
					// set text-object-url
					if(textEventColData.get(TranscriptDocumentConstants.TEXTOBJECTURL) != null){
						String data = textEventColData.get(TranscriptDocumentConstants.TEXTOBJECTURL);
						if(!data.contains(SETNULLCHARACTERS))
							textEvent.getTextObject().setTextObjectUrl(data);
					}
					
					// set file-event-id
					if(textEventColData.get(TranscriptDocumentConstants.FILEEVENTID) != null){
						String data = textEventColData.get(TranscriptDocumentConstants.FILEEVENTID);
						if(textEvent.getTextObject().getFileEventIds() != null && textEvent.getTextObject().getFileEventIds().getFileEventIdArray().length > 0){
							if(!data.contains(SETNULLCHARACTERS)){
								FileEventIds fileEventIds = textEvent.getTextObject().addNewFileEventIds();
								textEvent.getTextObject().setFileEventIds(fileEventIds);
							}
						}
					}
					
					// set link-communication-object-id
					if(textEventColData.get(TranscriptDocumentConstants.LINKCOMMUNICATIONOBJECTID) != null){
						String data = textEventColData.get(TranscriptDocumentConstants.LINKCOMMUNICATIONOBJECTID);
						if(!data.contains(SETNULLCHARACTERS))
							textEvent.getTextObject().setLinkCommunicationObjectId(data);
					}
					
					// set text content index id
					if(textEventColData.get(TranscriptDocumentConstants.TEXTCONTENTINDEXID) != null){
						String data = textEventColData.get(TranscriptDocumentConstants.TEXTCONTENTINDEXID);
						if(!data.contains(SETNULLCHARACTERS)){
							textEvent.getTextObject().addNewTextContent();
							textEvent.getTextObject().setTextContent(setTextContent(textEvent.getTextObject().getTextContent() , data));
						}
					}
					
					// set the attributes
					if(textEventColData.get(TranscriptDocumentConstants.ATTRIBUTESINDEXID) != null){
						String data = textEventColData.get(TranscriptDocumentConstants.ATTRIBUTESINDEXID);
						if(!data.contains(SETNULLCHARACTERS)){
							textEvent.getTextObject().addNewAttributes();
							IndexableAttributes attributes = textEvent.getTextObject().getAttributes();
							
							List<Attribute> listAttributes = new ArrayList<Attribute>();
							if(data.contains(INDEXIDSEPARATOR)){
								String[] ids = data.split(INDEXIDSEPARATOR);
								for(String id : ids)
									listAttributes.add(setIndexableAttributes(attributes, id));
							}
							else{
								listAttributes.add(setIndexableAttributes(attributes, data));
							}
							
							textEvent.getTextObject().getAttributes().setAttributeArray(listAttributes.toArray
									(new Attribute[listAttributes.size()]));
						}
					}
					
					// set lexicon hit index id
					if(textEventColData.get(TranscriptDocumentConstants.LEXICONHITINDEXID) != null){
						String data = textEventColData.get(TranscriptDocumentConstants.LEXICONHITINDEXID);
						if(!data.contains(SETNULLCHARACTERS)){
							List<String> ids = Arrays.asList(data.split(INDEXIDSEPARATOR));
							if(ids != null){
								textEvent.getTextObject().addNewLexiconHits();
								com.actiance.platform.commons.xmlns.apc.dig.itm.interaction.transcript.x10.TextObject.LexiconHits hits = setTextObjectLexiconHit(
										textEvent.getTextObject().getLexiconHits(),
										ids);
								if(hits != null)
									textEvent.getTextObject().setLexiconHits(hits);
							}
						}
					}
				}
			}
		}
	
		
		return textEvent;
	}

	/**
	 * Method to set the text object lexicon hits
	 * @param lexiconHits
	 * @param lexiconIds
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public com.actiance.platform.commons.xmlns.apc.dig.itm.interaction.transcript.x10.TextObject.LexiconHits setTextObjectLexiconHit(
			com.actiance.platform.commons.xmlns.apc.dig.itm.interaction.transcript.x10.TextObject.LexiconHits lexiconHits,
			List<String> lexiconIds) {
		
		List<LexiconHit> listLexiconHits = null;
		
		if(lexiconIds.size() > 0){
			for(String id : lexiconIds){
				if(listLexiconHits == null)
					listLexiconHits = new ArrayList<LexiconHit>();
				
				List<Map<String, String>> lexiconhitRowData = (List<Map<String, String>>)
						lexiconHitSheetData.get(id);
				
				LexiconHit hit = lexiconHits.addNewLexiconHit(); 
				
				if(lexiconhitRowData != null){
					for(Map<String, String> lexiconHitColData : lexiconhitRowData){
						// set match-type
						if(lexiconHitColData.get(TranscriptDocumentConstants.MATCHTYPE) != null &&
								!lexiconHitColData.get(TranscriptDocumentConstants.MATCHTYPE).equals(SETNULLCHARACTERS))
							hit.setMatchType(MatchType.Enum.forString(
									lexiconHitColData.get(TranscriptDocumentConstants.MATCHTYPE)));
						// set case sensitive
						if(lexiconHitColData.get(TranscriptDocumentConstants.CASE_SENSITIVE) != null){
							String data = lexiconHitColData.get(TranscriptDocumentConstants.CASE_SENSITIVE);
							if(!data.contains(SETNULLCHARACTERS))
								hit.setCaseSensitive(Boolean.parseBoolean(data));
						}
						
						// set origin
						if(lexiconHitColData.get(TranscriptDocumentConstants.ORIGIN) != null){
							String data = lexiconHitColData.get(TranscriptDocumentConstants.ORIGIN);
							if(!data.contains(SETNULLCHARACTERS))
								hit.setOrigin(data);
						}
						
						// set category
						if(lexiconHitColData.get(TranscriptDocumentConstants.CATEGORY) != null){
							String data = lexiconHitColData.get(TranscriptDocumentConstants.CATEGORY);
							if(!data.contains(SETNULLCHARACTERS))
								hit.setCategory(data);
						}
						
						
						// set near distance
						if(lexiconHitColData.get(TranscriptDocumentConstants.NEAR_DISTANCE) != null){
							String data = lexiconHitColData.get(TranscriptDocumentConstants.NEAR_DISTANCE);
							if(!data.contains(SETNULLCHARACTERS))
								hit.setNearDistance(new BigInteger(data));
						}
						
						// set Offset
						if(lexiconHitColData.get(TranscriptDocumentConstants.OFFSET) != null){
							String data = lexiconHitColData.get(TranscriptDocumentConstants.OFFSET);
							if(!data.contains(SETNULLCHARACTERS))
								hit.setOffset(Long.parseLong(data));
						}
						
						// set length
						if(lexiconHitColData.get(TranscriptDocumentConstants.LENGTH) != null){
							String data = lexiconHitColData.get(TranscriptDocumentConstants.LENGTH);
							if(!data.contains(SETNULLCHARACTERS))
								hit.setLength(new BigInteger(data));
						}
						
						// set policy
						if(lexiconHitColData.get(TranscriptDocumentConstants.POLICY) != null){
							String data = lexiconHitColData.get(TranscriptDocumentConstants.POLICY);
							if(!data.contains(SETNULLCHARACTERS))
								hit.setPolicy(data);
						}
						
						// set plain text
						if(lexiconHitColData.get(TranscriptDocumentConstants.PLAIN_TEXT) != null){
							String data = lexiconHitColData.get(TranscriptDocumentConstants.PLAIN_TEXT);
							if(data.contains(SETNULLCHARACTERS))
								hit.setPlainText(Boolean.parseBoolean(data));
						}
						
						// set the value
						if(lexiconHitColData.get(TranscriptDocumentConstants.VALUE) != null){
							String data = lexiconHitColData.get(TranscriptDocumentConstants.VALUE);
							if(data.contains(SETNULLCHARACTERS))
								hit.setStringValue(null);
							else
								hit.setStringValue(data);
						}
					}
				}
				listLexiconHits.add(hit);
			}
		}
		
		if(listLexiconHits != null)
			lexiconHits.setLexiconHitArray(listLexiconHits.toArray(new LexiconHit[listLexiconHits.size()]));
		
		return lexiconHits;
	}

	/**
	 * Method to set the text content based on the values form the excel sheet
	 * @param textEvent
	 * @param textContentIndexId
	 * @throws ValidationException 
	 */
	@SuppressWarnings("unchecked")
	public TextContent setTextContent(TextContent textContent, String textContentIndexId) throws ValidationException {
		List<Map<String,String>> textContentRowData = (List<Map<String, String>>)
				textContentSheetData.get(textContentIndexId);
		//TextContent textContent = null;	
		
		if(textContentRowData != null){
			//textContent = textEvent.getTextObject().getTextContent();
			
			for(Map<String, String> textContentColData : textContentRowData){
				// set text content content-type
				if(textContentColData.get(TranscriptDocumentConstants.CONTENTTYPE) != null){
					String data = textContentColData.get(TranscriptDocumentConstants.CONTENTTYPE);
					if(!data.contains(SETNULLCHARACTERS))
						textContent.setContentType(MimeType.Enum.forString(data));
				}
								
				// set text content indexable
				if(textContentColData.get(TranscriptDocumentConstants.INDEXABLE) != null){
					String data = textContentColData.get(TranscriptDocumentConstants.INDEXABLE);
					if(!data.contains(SETNULLCHARACTERS))
						textContent.setIndexable(Boolean.parseBoolean(data));
				}
				
				// set text content visiblity
				if(textContentColData.get(TranscriptDocumentConstants.USERVISIBLE) != null){
					String data = textContentColData.get(TranscriptDocumentConstants.USERVISIBLE);
					if(!data.contains(SETNULLCHARACTERS))
						textContent.setUserVisible(UserVisibility.Enum.forString(data));
				}
				
				// set the text content
				if(textContentColData.get(TranscriptDocumentConstants.TEXTCONTENT_ID) != null){
					String data = textContentColData.get(TranscriptDocumentConstants.TEXTCONTENT_ID);
					if(data.contains(SETNULLCHARACTERS))
						textContent.setStringValue("");
					else{
						//File file = new File("data/dig/texts/" + data);
						try{
							InputStream is = new FileInputStream(new File(resourcesPath+ "Text/" + data));
							StringWriter writer = new StringWriter();
							IOUtils.copy(is, writer, "UTF-8");
							textContent.setStringValue(writer.toString());
							
						} catch (FileNotFoundException e) {
							// TODO
						}catch(IOException e){
							// TODO
						}
					}
				}	
			}
		}
		return textContent;
	}

	/**
	 * Method to set the participants based on the values form the excel sheet
	 * @param tDoc
	 * @param participantIds
	 * @throws Exception 
	 */
	public void setParticipants(TranscriptDocument tDoc,
			List<String> participantIds) throws Exception {
		List<Participant> parts = null;
		tDoc.getTranscript().getInteraction().addNewParticipants();
		// add the participants
		parts = new ArrayList<Participant>();
		for(String id : participantIds)
			parts.add(setParticipant(id));
		
		if(parts != null){
			for(int i =0; i < parts.size(); i++){
				tDoc.getTranscript().getInteraction().getParticipants().addNewParticipant();
				tDoc.getTranscript().getInteraction().getParticipants()
								.setParticipantArray(i, parts.get(i));
			}
		}
	}
	
	/**
	 * Method to set the participant on the values form the excel sheet
	 * @param participantIndexId
	 * @return
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	private Participant setParticipant(String participantIndexId) throws Exception{
		List<Map<String,String>> participantMapData = (List<Map<String, String>>)
				participantSheetData.get(participantIndexId);
		Participant part = null;
		
		if(participantMapData != null){

			int secondaryAddressCount = 1;
			int attributesCount = 1;
			int groupsCount = 1;
						
			part = Participant.Factory.newInstance();
			
			for(Map<String, String> participantData : participantMapData){
				
				// set participant-id
				if(participantData.get(TranscriptDocumentConstants.PARTICIPANTID) != null &&
						!participantData.get(TranscriptDocumentConstants.PARTICIPANTID).contains(SETNULLCHARACTERS)){
					part.setParticipantId(participantData.get(TranscriptDocumentConstants.PARTICIPANTID));
				}
				
				// set participant type
				if(participantData.get(TranscriptDocumentConstants.PARTICIPANTROLE) != null){
					String data = participantData.get(TranscriptDocumentConstants.PARTICIPANTROLE);
					if(!data.contains(SETNULLCHARACTERS))
					{
						part.setParticipantRole(com.actiance.platform.commons.xmlns.apc.dig.itm.interaction.transcript.x10.Interaction.Participants.Participant.ParticipantRole.Enum.forString(data));
					}
				}
				
				// set user info
				if(participantData.get(TranscriptDocumentConstants.USERINFOINDEXID) != null){
					String data = participantData.get(TranscriptDocumentConstants.USERINFOINDEXID);
					if(!data.contains(SETNULLCHARACTERS)){
						part.addNewUserInfo();
						part.setUserInfo(setUserInfo(part.getUserInfo(), data, ""));
					}
				}
				
				// set network info
				if(participantData.get(TranscriptDocumentConstants.NETWORKINFOINDEXID) != null){
					
					String data = participantData.get(TranscriptDocumentConstants.NETWORKINFOINDEXID);
					if(!data.contains(SETNULLCHARACTERS)){
						part.addNewNetworkInfo();
						part.setNetworkInfo(setNetworkInfo(part.getNetworkInfo(), data));
					}
					
				}

				
				// set participant attributes
				if(participantData.get(TranscriptDocumentConstants.PARTICIPANTSATTRIBUTESINDEXID) != null){
					String data = participantData.get(TranscriptDocumentConstants.PARTICIPANTSATTRIBUTESINDEXID);
					if(!data.contains(SETNULLCHARACTERS))
					{
						part.addNewAttributes();
						GeneralAttributes attributes = part.getAttributes();
						
						List<GeneralAttributes.Attribute> listAttributes = new ArrayList<GeneralAttributes.Attribute>();
						if(data.contains(INDEXIDSEPARATOR)){
							String[] ids = data.split(INDEXIDSEPARATOR);
							for(String id : ids)
								listAttributes.add(setGeneralAttributes(attributes, id));
						}
						else{
							listAttributes.add(setGeneralAttributes(attributes, data));
						}
						
						part.getAttributes().setAttributeArray(listAttributes.toArray
								(new GeneralAttributes.Attribute[listAttributes.size()]));
					}
				}
			}
		}
		
		return part;
	}
	
	/**
	 * Method to set the user info based on the values form the excel sheet
	 * @param userInfo
	 * @param userinfoIndexId
	 * @return
	 * @throws IOException 
	 */
	@SuppressWarnings("unchecked")
	public UserInfo setUserInfo(UserInfo userInfo , String userinfoIndexId, String uniqueSuffix) throws IOException{
		UserInfo userinfo = userInfo;
		if(uniqueSuffix == null){
			uniqueSuffix = "";
		}
		
		if(userinfoIndexId != null){
			userInfo.addNewAffiliation();
			userInfo.addNewGeoLocation();
			userInfo.addNewGroups();
			userinfo.addNewImage();
			userInfo.addNewModificationTime();
			userinfo.addNewName();
			userinfo.addNewPhoneNumbers();
			userinfo.addNewSecondaryAddresses();
			
			List<Map<String, String>> userinfoRowData = (List<Map<String, String>>)
					userinfoSheetData.get(userinfoIndexId);
			
			for(Map<String, String> userinfoColData : userinfoRowData){ 
				// set user-id
				if(userinfoColData.get(TranscriptDocumentConstants.USERID) != null &&
						!userinfoColData.get(TranscriptDocumentConstants.USERID).contains(SETNULLCHARACTERS)){
					userinfo.setUserId(userinfoColData.get(TranscriptDocumentConstants.USERID));
				}
				
				// set user type
				if(userinfoColData.get(TranscriptDocumentConstants.USERTYPE) != null &&
						!userinfoColData.get(TranscriptDocumentConstants.USERTYPE).contains(SETNULLCHARACTERS)){
					userinfo.setUserType(UserInfo.UserType.Enum.forString(
							userinfoColData.get(TranscriptDocumentConstants.USERTYPE)));
				}
				
				// set name
				if(userinfoColData.get(TranscriptDocumentConstants.FIRSTNAME) != null){
					String data = userinfoColData.get(TranscriptDocumentConstants.FIRSTNAME);
					if(!data.contains(SETNULLCHARACTERS))
						userInfo.getName().setFirst(data + uniqueSuffix);
				}
				
				if(userinfoColData.get(TranscriptDocumentConstants.MIDDLENAME) != null){
					String data = userinfoColData.get(TranscriptDocumentConstants.MIDDLENAME);
					if(!data.contains(SETNULLCHARACTERS))
						userInfo.getName().setMiddle(data + uniqueSuffix);
				}
				
				if(userinfoColData.get(TranscriptDocumentConstants.LASTNAME) != null){
					String data = userinfoColData.get(TranscriptDocumentConstants.LASTNAME);
					if(!data.contains(SETNULLCHARACTERS))
						userInfo.getName().setLast(data + uniqueSuffix);
				}
				
				if(userinfoColData.get(TranscriptDocumentConstants.INITIALS) != null){
					String data = userinfoColData.get(TranscriptDocumentConstants.INITIALS);
					if(!data.contains(SETNULLCHARACTERS))
						userInfo.getName().setInitials(data + uniqueSuffix);
				}
				
				if(userinfoColData.get(TranscriptDocumentConstants.DISPLAYNAME) != null){
					String data = userinfoColData.get(TranscriptDocumentConstants.DISPLAYNAME);
					if(!data.contains(SETNULLCHARACTERS))
						userInfo.getName().setDisplayName(data + uniqueSuffix);
				}
				
				// set email address
				if(userinfoColData.get(TranscriptDocumentConstants.EMAILADDRESS) != null){
					String data = userinfoColData.get(TranscriptDocumentConstants.EMAILADDRESS);
					if(!data.contains(SETNULLCHARACTERS))
						userInfo.setEmailAddress(data + uniqueSuffix);
				}
				
				// set affiliation
				if(userinfoColData.get(TranscriptDocumentConstants.EMPLOYEEID) != null){
					String data = userinfoColData.get(TranscriptDocumentConstants.EMPLOYEEID);
					if(!data.contains(SETNULLCHARACTERS))
						userInfo.getAffiliation().setEmployeeId(data + uniqueSuffix);
				}
				
				if(userinfoColData.get(TranscriptDocumentConstants.TITLE) != null){
					String data = userinfoColData.get(TranscriptDocumentConstants.TITLE);
					if(!data.contains(SETNULLCHARACTERS))
						userInfo.getAffiliation().setTitle(data + uniqueSuffix);
				}
				
				if(userinfoColData.get(TranscriptDocumentConstants.DEPARTMENT) != null){
					String data = userinfoColData.get(TranscriptDocumentConstants.DEPARTMENT);
					if(!data.contains(SETNULLCHARACTERS))
						userInfo.getAffiliation().setDepartment(data + uniqueSuffix);
				}
				
				if(userinfoColData.get(TranscriptDocumentConstants.DIVISION) != null){
					String data = userinfoColData.get(TranscriptDocumentConstants.DIVISION);
					if(!data.contains(SETNULLCHARACTERS))
						userInfo.getAffiliation().setDivision(data + uniqueSuffix);
				}
				
				if(userinfoColData.get(TranscriptDocumentConstants.COMPANY) != null){
					String data = userinfoColData.get(TranscriptDocumentConstants.COMPANY);
					if(!data.contains(SETNULLCHARACTERS))
						userInfo.getAffiliation().setCompany(data + uniqueSuffix);
				}
				
				if(userinfoColData.get(TranscriptDocumentConstants.BUILDING) != null){
					String data = userinfoColData.get(TranscriptDocumentConstants.BUILDING);
					if(!data.contains(SETNULLCHARACTERS))
						userInfo.getAffiliation().setBuilding(data + uniqueSuffix);
				}
				
				// set geo location
				if(userinfoColData.get(TranscriptDocumentConstants.CITY) != null){
					String data = userinfoColData.get(TranscriptDocumentConstants.CITY);
					if(!data.contains(SETNULLCHARACTERS))
						userInfo.getGeoLocation().setCity(data + uniqueSuffix);
				}
				
				if(userinfoColData.get(TranscriptDocumentConstants.STATE) != null){
					String data = userinfoColData.get(TranscriptDocumentConstants.STATE);
					if(!data.contains(SETNULLCHARACTERS))
						userInfo.getGeoLocation().setState(data + uniqueSuffix);
				}
				
				if(userinfoColData.get(TranscriptDocumentConstants.COUNTRY) != null){
					String data = userinfoColData.get(TranscriptDocumentConstants.COUNTRY);
					if(!data.contains(SETNULLCHARACTERS))
						userInfo.getGeoLocation().setCountry(data + uniqueSuffix);
				}
				
				// set phone numbers
				if(userinfoColData.get(TranscriptDocumentConstants.OFFICEPHONE) != null){
					String data = userinfoColData.get(TranscriptDocumentConstants.OFFICEPHONE);
					if(!data.contains(SETNULLCHARACTERS))
						userInfo.getPhoneNumbers().setOfficePhone(data + uniqueSuffix);
				}
				
				if(userinfoColData.get(TranscriptDocumentConstants.SECONDARYOFFICEPHONE) != null){
					String data = userinfoColData.get(TranscriptDocumentConstants.SECONDARYOFFICEPHONE);
					if(!data.contains(SETNULLCHARACTERS))
						userInfo.getPhoneNumbers().setSecondaryOfficePhone(data + uniqueSuffix);
				}
				
				if(userinfoColData.get(TranscriptDocumentConstants.MOBILEPHONE) != null){
					String data = userinfoColData.get(TranscriptDocumentConstants.MOBILEPHONE);
					if(!data.contains(SETNULLCHARACTERS))
						userInfo.getPhoneNumbers().setMobilePhone(data + uniqueSuffix);
				}
				
				if(userinfoColData.get(TranscriptDocumentConstants.HOMEPHONE) != null){
					String data = userinfoColData.get(TranscriptDocumentConstants.HOMEPHONE);
					if(!data.contains(SETNULLCHARACTERS))
						userInfo.getPhoneNumbers().setHomePhone(data + uniqueSuffix);
				}
				
				if(userinfoColData.get(TranscriptDocumentConstants.OTHERPHONE) != null){
					String data = userinfoColData.get(TranscriptDocumentConstants.OTHERPHONE);
					if(!data.contains(SETNULLCHARACTERS))
						userInfo.getPhoneNumbers().setOtherPhone(data + uniqueSuffix);
				}
				
				// set secondary addresses
				if(userinfoColData.get(TranscriptDocumentConstants.SECONDARYADDRESS) != null){
					String data = userinfoColData.get(TranscriptDocumentConstants.SECONDARYADDRESS);
					if(!data.contains(SETNULLCHARACTERS))
					{
						userinfo.unsetSecondaryAddresses();
						SecondaryAddresses addresses = userInfo.addNewSecondaryAddresses();
						if(data.contains(INDEXIDSEPARATOR)){
							String[] stringAddress = data.split(INDEXIDSEPARATOR);
							for(String address:stringAddress ){
								SecondaryAddress secAddress = addresses.addNewSecondaryAddress();
								secAddress.setStringValue(address);
								secAddress.setAddressType(com.actiance.platform.commons.xmlns.apc.dig.itm.interaction.types.x10.SecondaryAddress.AddressType.Enum.forInt(RandomUtils.nextInt(6) + 1));
							}
						}
						else{
							SecondaryAddress secAddress = addresses.addNewSecondaryAddress();
							secAddress.setStringValue(data);
							secAddress.setAddressType(com.actiance.platform.commons.xmlns.apc.dig.itm.interaction.types.x10.SecondaryAddress.AddressType.Enum.forInt(RandomUtils.nextInt(6) + 1));
							}
						}
				}
				
				// set groups
				if(userinfoColData.get(TranscriptDocumentConstants.GROUPS) != null){
					String data = userinfoColData.get(TranscriptDocumentConstants.GROUPS);
					if(!data.contains(SETNULLCHARACTERS))
					{
//						userInfo.addNewGroups();
						if(data.contains(INDEXIDSEPARATOR))
						{
							String[] groups = data.split(INDEXIDSEPARATOR);
							for(int i =0; i<groups.length; i++){
								userinfo.getGroups().addNewGroup();
								userinfo.getGroups().setGroupArray(i, groups[i]);
							}
						}
						
						else
						{

							userinfo.getGroups().addNewGroup();
							userinfo.getGroups().setGroupArray(0,data);
						}
							
					}
				}
				
				// set image type
				if(userinfoColData.get(TranscriptDocumentConstants.IMAGE_TYPE) != null){
					if(userinfo.isSetImage()){
						if(!userinfoColData.get(TranscriptDocumentConstants.IMAGE_TYPE).contains(SETNULLCHARACTERS))
							userinfo.getImage().setType(Type.Enum.forString(
									userinfoColData.get(TranscriptDocumentConstants.IMAGE_TYPE)));
					}
				}
				
				// set image file content in base 64 encoding
				if(userinfoColData.get(TranscriptDocumentConstants.IMAGE_FILE_NAME) != null){
					userinfo.addNewImage();
					if(userinfo.isSetImage()){
						String data	= userinfoColData.get(TranscriptDocumentConstants.IMAGE_FILE_NAME);
						if(!data.contains(SETNULLCHARACTERS))
							userinfo.getImage().setStringValue(Utils.encodeFileToBase64(FILE_RESOURCES_PATH + data));
					}
				}
				
			
				
				if(userinfoColData.get(TranscriptDocumentConstants.MODIFICATION_TIME)!= null){
					String data = userinfoColData.get(TranscriptDocumentConstants.MODIFICATION_TIME);
					if(!data.contains(SETNULLCHARACTERS))
						try {
							{
								SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
								Calendar cal = Calendar.getInstance();
								cal.setTime(df.parse(data));
//								if(userinfo.getModificationTime()== null)
//									userinfo.addNewModificationTime();
								userinfo.getModificationTime().setTimestamp(cal);
							}
						} catch (ParseException e) {
															
						}
				

			}
		}
		}
		return userinfo;
	}
	
	/**
	 * Method to set the network info based on the values form the excel sheet
	 * @param networkInfo
	 * @param networkinfoIndexId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public NetworkEndpoint setNetworkInfo(NetworkEndpoint networkInfo , String networkinfoIndexId){
		NetworkEndpoint networkinfo = networkInfo;
		
		if(networkinfoIndexId != null){
			List<Map<String, String>> networkinfoRowData = (List<Map<String, String>>)
					networkinfoSheetData.get(networkinfoIndexId);
			
			for(Map<String, String> networkinfoColData : networkinfoRowData){
				// set network endpoint id
				if(networkinfoColData.get(TranscriptDocumentConstants.NETWORKENDPOINTID) != null){
					String data = networkinfoColData.get(TranscriptDocumentConstants.NETWORKENDPOINTID);
					if(data.contains(SETNULLCHARACTERS))
						networkinfo.setEndpointId("");
					else
						networkinfo.setEndpointId(data);
				}
				
				// set display name
				if(networkinfoColData.get(TranscriptDocumentConstants.NETWORKDISPLAYNAME) != null){
					String data = networkinfoColData.get(TranscriptDocumentConstants.NETWORKDISPLAYNAME);
					if(!data.contains(SETNULLCHARACTERS))
						networkinfo.setDisplayName(data);
				}
				
				// set endpoint type
				if(networkinfoColData.get(TranscriptDocumentConstants.NETWORKENDPOINTTYPE) != null &&
						!networkinfoColData.get(TranscriptDocumentConstants.NETWORKENDPOINTTYPE).contains(SETNULLCHARACTERS)){
					networkinfo.setEndpointIdType(NetworkEndpoint.EndpointIdType.Enum.forString(
							networkinfoColData.get(TranscriptDocumentConstants.NETWORKENDPOINTTYPE)));
				}
				
				// set network name
				if(networkinfoColData.get(TranscriptDocumentConstants.NETWORKNAME) != null){
					String data = networkinfoColData.get(TranscriptDocumentConstants.NETWORKNAME);
					if(data.contains(SETNULLCHARACTERS))
						networkinfo.setNetwork("");
					else
						networkinfo.setNetwork(data);
				}
			}
		}
		return networkinfo;
	}
	
	/**
	 * Method to set the subject based on the values form the excel sheet
	 * @param tDoc
	 * @param subjectIndexId
	 */
	@SuppressWarnings("unchecked")
	public Subject setSubject(Subject subject, String subjectIndexId) {
		
		try{
		
			List<Map<String,String>> subjectRowData = (List<Map<String,String>>)
					subjectSheetData.get(subjectIndexId);
			
			if(subjectRowData != null){
				
				for(Map<String,String> subjectColData : subjectRowData){
					if(subjectColData.get(TranscriptDocumentConstants.CONTENTTYPE) != null &&
							!subjectColData.get(TranscriptDocumentConstants.CONTENTTYPE).equals(SETNULLCHARACTERS)){
						subject.setContentType(MimeType.Enum.forString(
								subjectColData.get(TranscriptDocumentConstants.CONTENTTYPE)));
						continue;
					}
					
					if(subjectColData.get(TranscriptDocumentConstants.SUBJECTCONTENT) != null){
						String content = subjectColData.get(TranscriptDocumentConstants.SUBJECTCONTENT);
						if(content.equals(SETNULLCHARACTERS))
							subject.setNil();
						else
							subject.setStringValue(new String(content.getBytes("UTF-8")));
						continue;
					}
				}
			}
		}catch(Exception e){
//			_logger.error("Error in getting subject from the excel sheet for the index id " + subjectIndexId,
							
		}
		return subject;
	}

	/**
	 * Method to set the modality based on the values form the excel sheet
	 * @param tDoc
	 * @param modalityIndexId
	 * @throws UnsupportedEncodingException 
	 */
	@SuppressWarnings("unchecked")
	public Modality setModality(Modality modality, String modalityIndexId, String uniqueString) throws UnsupportedEncodingException {
		List<Map<String,String>> modalityRowData = (List<Map<String,String>>)
						modalitySheetData.get(modalityIndexId);
		if(uniqueString == null){
			uniqueString = "";
		}
		if(modalityRowData != null){
			for(Map<String, String> modalityColData : modalityRowData){
				if(modalityColData.get(TranscriptDocumentConstants.CLASSIFICATION) != null){
					modality.setClassification(
							ModalityClass.Enum.forString(
									modalityColData.get(TranscriptDocumentConstants.CLASSIFICATION)));
					continue;
				}
				
				if(modalityColData.get(TranscriptDocumentConstants.TYPE) != null){
					modality.setType(
							ModalityType.Enum.forString(
									modalityColData.get(TranscriptDocumentConstants.TYPE)));
					continue;
				}
				
				if(modalityColData.get(TranscriptDocumentConstants.VENDOR) != null){
					if(!modalityColData.get(TranscriptDocumentConstants.VENDOR).equals(SETNULLCHARACTERS))
						try {
							modality.setVendor(new String(modalityColData.get(TranscriptDocumentConstants.VENDOR).getBytes("UTF-8")) + uniqueString);
						} catch (UnsupportedEncodingException e) {
							throw e;
						}
					continue;
				}
				
				if(modalityColData.get(TranscriptDocumentConstants.CHANNEL) != null){
					if(modalityColData.get(TranscriptDocumentConstants.CHANNEL).equals(SETNULLCHARACTERS))
						modality.setChannel("");
					else
						modality.setChannel(modalityColData.get(TranscriptDocumentConstants.CHANNEL) + uniqueString);
					continue;
				}
				
				if(modalityColData.get(TranscriptDocumentConstants.NETWORK) != null){
					if(modalityColData.get(TranscriptDocumentConstants.NETWORK).equals(SETNULLCHARACTERS))
						modality.setNetwork("");
					else
						modality.setNetwork(modalityColData.get(TranscriptDocumentConstants.NETWORK) + uniqueString);
					continue;
				}
				
			
				if(modalityColData.get(TranscriptDocumentConstants.DESCRIPTION) != null){
					if(!modalityColData.get(TranscriptDocumentConstants.DESCRIPTION).equals(SETNULLCHARACTERS))
						modality.setDescription(modalityColData.get(TranscriptDocumentConstants.DESCRIPTION));
					continue;
				}
			}
		}
		return modality;
	}
	
	/**
	 * Method to set the attributes
	 * @param attributes
	 * @param attributeIndexId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public IndexableAttributes.Attribute setIndexableAttributes(IndexableAttributes attributes, String attributeIndexId){
		List<Map<String, String>> attributesRowData = (List<Map<String, String>>)
						attributesSheetData.get(attributeIndexId);
		IndexableAttributes.Attribute attribute = null;
		
		if(attributesRowData != null){
			attribute = attributes.addNewAttribute();
			
			for(Map<String, String> attributesColData : attributesRowData){
				// set the name
				if(attributesColData.get(TranscriptDocumentConstants.NAME) != null &&
						!attributesColData.get(TranscriptDocumentConstants.NAME).equals(SETNULLCHARACTERS)){
					attribute.setName(attributesColData.get(TranscriptDocumentConstants.NAME));
				}
				
				// set the value
				if(attributesColData.get(TranscriptDocumentConstants.VALUE) != null){
					String data = attributesColData.get(TranscriptDocumentConstants.VALUE);
					if(data.contains(SETNULLCHARACTERS))
						attribute.setStringValue("");
					else
						attribute.setStringValue(data);
				}
				
				// set the content-type
				if(attributesColData.get(TranscriptDocumentConstants.CONTENTTYPE) != null){
					String data = attributesColData.get(TranscriptDocumentConstants.CONTENTTYPE);
					if(!data.contains(SETNULLCHARACTERS))
						attribute.setContentType(MimeType.Enum.forString(data));
				}
				
				// set the indexable
				if(attributesColData.get(TranscriptDocumentConstants.INDEXABLE) != null){
					String data = attributesColData.get(TranscriptDocumentConstants.INDEXABLE);
					if(!data.contains(SETNULLCHARACTERS))
						attribute.setIndexable(Boolean.parseBoolean(data));
				}
				
				// set the user-visibility
				if(attributesColData.get(TranscriptDocumentConstants.USERVISIBLE) != null){
					String data = attributesColData.get(TranscriptDocumentConstants.USERVISIBLE);
					if(!data.contains(SETNULLCHARACTERS))
						attribute.setUserVisible(UserVisibility.Enum.forString(data));
				}
			}
		}
		
		return attribute;
		
	}
	
}

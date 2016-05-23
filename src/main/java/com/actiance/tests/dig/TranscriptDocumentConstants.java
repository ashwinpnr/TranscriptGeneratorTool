package main.java.com.actiance.tests.dig;

public class TranscriptDocumentConstants {
	
	// transcript fields
	public static final String TRANSCRIPTINDEXID = "Transcript-Index-Id";
	public static final String TRANSCRIPTID = "id";
	public static final String SOURCEENDPOINTID = "source-endpoint-id";
	public static final String SOURCEENDPOINTVERSION = "source-endpoint-version";
	public static final String TIMESTAMP = "time-stamp";
	public static final String INTERACTIONINDEXID= "Interaction-Index-Id";
	public static final String POLICYINDEXID = "Policy-Index-Id";
	
	// interfields
	public static final String INTERACTIONEVENTID = "interaction-event-id";
	public static final String DATA_STATE = "data-state";
	public static final String HISTORICAL_DATA_FLAG= "historical-data-flag";
	public static final String COMMUNICATIONOBJECTID = "communication-object-id";
	public static final String PARENTCOMMUNICATIONOBJECTID = "parent-communication-object-id";
	public static final String THREADOBJECTID = "thread-object-id";
	public static final String LOCATIONOBJECTID = "location-object-id";
	public static final String TIMEFRAMEINDEXID = "Time-Frame-Index-Id";
	public static final String MODALITYINDEXID = "Modality-Index-Id";
	public static final String SUBJECTINDEXID = "Subject-Index-Id";
	public static final String PARTICIPANTINDEXID = "Participant-Index-Id";
	public static final String TEXTEVENTINDEXID= "TextEvent-Index-Id";
	public static final String FILEEVENTINDEXID= "FileEvent-Index-Id";
	public static final String ACTIONEVENTINDEXID= "ActionEvent-Index-Id";
	public static final String POLICYEVENTINDEXID= "PolicyEvent-Index-Id";
	
	// time-frame fields
	public static final String START_TIME = "start-time";
	public static final String START_TIME_DESCRIPTION = "start-time-description";
	public static final String END_TIME = "end-time";
	public static final String END_TIME_DESCRIPTION = "end-time-description";
	
	// modality fields
	public static final String CLASSIFICATION = "classification";
	public static final String TYPE = "type";
	public static final String VENDOR = "vendor";
	public static final String CHANNEL = "Channel";
	public static final String NETWORK = "Network";
	public static final String DESCRIPTION = "description";
	
	// subject fields
	public static final String CONTENTTYPE = "content-type";
	public static final String SUBJECTCONTENT = "subject-content";
	
	// participant fields
	public static final String PARTICIPANTID = "participant-id";
	public static final String PARTICIPANTROLE = "participant-role";
	public static final String USERINFOINDEXID = "UserInfo-Index-Id";
	public static final String NETWORKINFOINDEXID = "NetworkInfo-Index-Id";
	public static final String PARTICIPANTSATTRIBUTESINDEXID = "ParticipantAttributes-Index-Id";
	
	// userinfo fields
	public static final String USERID = "user-id";
	public static final String USERTYPE = "user-type";
	public static final String FIRSTNAME = "first";
	public static final String MIDDLENAME = "middle";
	public static final String LASTNAME = "last";
	public static final String INITIALS = "initials";
	public static final String DISPLAYNAME = "display-name";
	
	public static final String EMAILADDRESS = "email-address";
	public static final String EMPLOYEEID = "employee-id";
	public static final String TITLE = "title";
	public static final String DEPARTMENT = "department";
	public static final String DIVISION = "division";
	public static final String COMPANY = "company";
	public static final String BUILDING = "building";
	
	public static final String CITY = "city";
	public static final String STATE = "state";
	public static final String COUNTRY = "country";
	
	public static final String OFFICEPHONE = "office-phone";
	public static final String SECONDARYOFFICEPHONE = "secondary-office-phone";
	public static final String HOMEPHONE = "home-phone";
	public static final String OTHERPHONE = "other-phone";
	public static final String MOBILEPHONE = "mobile-phone";
	
	public static final String SECONDARYADDRESS = "secondary-address";
	public static final String GROUPS = "groups";
	
	public static final String IMAGE_TYPE = "image-type";
	public static final String IMAGE_FILE_NAME = "image-file-name";
	public static final String MODIFICATION_TIME = "modification-time";
	
	// set network info
	public static final String NETWORKENDPOINTTYPE = "endpoint-id-type";
	public static final String NETWORKDISPLAYNAME = "display-name";
	public static final String NETWORKENDPOINTID = "endpoint-id";
	public static final String NETWORKNAME = "network";

	// set participant attributes
	// TODO
	
	// set text event
	public static final String TEXTEVENTID= "text-event-id";
	public static final String ACTION = "action";
	public static final String TEXTEVENTOPERATION = "operation";
	public static final String TEXTOBJECTID = "text-object-id";
	public static final String PARENTTEXTOBJECTID = "parent-text-object-id";
	public static final String TEXTOBJECTSTYLE = "text-object-style";
	public static final String SYSTEM_FLAG = "system-flag";
	public static final String TEXTOBJECTTYPE = "text-object-type";
	public static final String TEXTOBJECTSUBTYPE = "text-object-sub-type";
	public static final String EVENTTIME = "event-time";
	public static final String EVENTTIME_DESCRIPTION = "event-time-description";
	public static final String TEXTOBJECTURL = "text-object-url";
	public static final String LINKCOMMUNICATIONOBJECTID = "link-communication-object-id";
	public static final String TEXTCONTENTINDEXID = "TextContent-Index-Id";
	public static final String LEXICONHITINDEXID = "LexiconHit-Index-Id";
	
	// set text content & file content
	public static final String INDEXABLE = "indexable";
	public static final String USERVISIBLE = "user-visible";
	public static final String ENCODING = "encoding";
	public static final String TEXTCONTENT_ID = "textcontent-id";
	public static final String NAME= "name";
	public static final String FILECONTENT_ID = "filecontent-id";
	
	// set file event
	public static final String FILEEVENTID = "file-event-id";
	public static final String FILEEVENTTYTPE = "file-event-type";
	public static final String FILEOBJECTID = "file-object-id";
	public static final String PARENTFILEOBJECTID = "parent-file-object-id";
	public static final String FILEOBJECTURL = "file-object-url";
	public static final String FILECONTENTINDEXID = "FileContent-Index-Id";
	
	// set attributes field
	public static final String ATTRIBUTESINDEXID = "Attributes-Index-Id";
	public static final String VALUE = "value";
	
	// set lexiconhit fields
	public static final String MATCHTYPE = "match-type";
	public static final String NEAR_DISTANCE ="near-distance";
	public static final String PLAIN_TEXT ="plain-text";
	public static final String OFFSET ="offset";
	public static final String LENGTH ="length";
	public static final String CASE_SENSITIVE = "case-sensitive";
	public static final String ORIGIN = "origin";
	public static final String CATEGORY = "category";
	
	// set action event fields
	public static final String ACTION_EVENT_ID = "action-event-id";
	public static final String PARENT_ACTION_EVENT_ID = "parent-action-event-id";
	public static final String ACTION_TYPE ="action-type";
	public static final String ACTION_SUB_TYPE ="action-sub-type";
	public static final String ACTION_STATE ="action-state";
	public static final String POLICY ="policy";
	public static final String ACTOR_PARTICIPANT_ID ="actor-participant-id";
	public static final String ASSIGNEE_PARTICIPANT_ID ="assignee-participant-id";
	public static final String ACTIONATTRIBUTESINDEXID ="Action-Attributes-Index-Id";
	
	// set policy event fields
	public static final String POLICY_EVENT_ID = "policy-event-id";
	public static final String POLICY_EVENT_TYPE = "policy-event-type";
	public static final String POLICY_EVENT_SUB_TYPE = "policy-event-sub-type";
	public static final String POLICY_EVENT_CATEGORY = "policy-event-category";
	public static final String EVENT_TIME = "policy-event-time";
	public static final String POLICY_TRIGGERING_PARTICIPANT_ID = "triggering-participant-id";
	public static final String POLICY_TRIGGERING_TEXTEVENT_ID = "triggering-text-event-id";
	public static final String POLICY_TRIGGERING_FILEEVENT_ID = "triggering-file-event-id";
	public static final String POLICY_ACTIONEVENT_ID = "triggering-action-event-id";
	public static final String POLICYEVENTATTRIBUTESINDEXID= "Policy-Attributes-Index-Id";
	public static final String POLICY_EVENT_TIMESTAMP_DESCRIPTION = "policy-event-timestamp-description";
	

	// set the policy fields
	public static final String POLICY_ID = "id";
	public static final String POLICY_ACTION = "action";
	public static final String POLICY_DESCRIPTION = "policy-event-description";
	
}

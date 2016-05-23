package main.java.com.actiance.tests.dig;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.bind.ValidationException;

import org.apache.commons.io.IOUtils;
import org.apache.commons.io.output.ByteArrayOutputStream;

import org.apache.commons.codec.binary.Base64OutputStream;
import org.apache.xmlbeans.XmlObject;
import org.apache.xmlbeans.XmlOptions;
import org.apache.xmlbeans.XmlValidationError;

public class Utils {

	public static void validate(XmlObject object) throws ValidationException {
		XmlOptions m_validationOptions = new XmlOptions();
		ArrayList<Object> validationErrors = new ArrayList<Object>();
		m_validationOptions.setErrorListener(validationErrors);

		boolean isvalid = object.validate(m_validationOptions);
		if (!isvalid) {
			System.out.println("Invalid Transcript  ");
			String str = getValidationErrors(validationErrors);
			System.out.println(str);
			//throw new ValidationException(str);
		}
		
		else
			System.out.println("Valid Transcript ");
		
	}

	public static String getValidationErrors(List<Object> error) {
		StringBuilder errorString = new StringBuilder();
		Iterator<Object> itr = error.iterator();
		while (itr.hasNext()) {
			Object object = itr.next();
			if (object instanceof XmlValidationError) {
				errorString.append(String.valueOf(object) + "\n");
			} else {
				errorString.append(String.valueOf(object) + "\n");
			}
		}

		return errorString.toString();
	}
	
	public void encodeFileToBase64Binary(String fileName,
			OutputStream base64OutputStream) throws IOException {
		
		InputStream is = new FileInputStream(new File(fileName));
//		InputStream is = this.getClass().getClassLoader().getResourceAsStream(fileName);
		OutputStream out = new Base64OutputStream(base64OutputStream);
		IOUtils.copy(is, out);
		is.close();
		out.close();
	}
	
	public static String encodeFileToBase64(String filePath) throws IOException{
		String fileContentBase64 = "";
		OutputStream out = new ByteArrayOutputStream();
		try {
			new Utils().encodeFileToBase64Binary(filePath, out);
			fileContentBase64 = out.toString();
		}
		finally{
			if(out != null)
				out.close();
		}
		return fileContentBase64;
	}
}
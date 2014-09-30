package com.getlocalization.data.files;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.getlocalization.client.Query;
import com.getlocalization.client.QueryException;
import com.getlocalization.client.QuerySecurityException;

public class SpecificTranslationQuery extends Query {

  private static final Logger log = LoggerFactory.getLogger(SpecificTranslationQuery.class);
  
  private String masterFile;
  private String language;
	/**
	 * Update an existing master file to given Get Localization project. 
	 * 
	 * @param file File that is sent to Get Localization
	 * @param projectId The project name that appears in your Get Localization URL.
	 * 
	 */
	public SpecificTranslationQuery(String projectId, String masterFile, String language)
	{
		this.projectId = projectId;
		this.masterFile = masterFile;
		this.language = language;
	}
	
	@Override
	public void doQuery() throws QueryException, IOException {
		try
		{
			String url = "https://www.getlocalization.com/" + projectId + "/api/translations/file/" + masterFile + "/" + language;
			
			byte[] file = getFile(url);
			
			translatedFile = File.createTempFile("getlocalization", ".zip");
			FileOutputStream fos = new FileOutputStream(translatedFile);
			fos.write(file);
			fos.close();
		}
		catch(QuerySecurityException cse)
		{
			// Making sure that URL starts with https.
		  log.warn(null, cse);
		}
	}
	
	public File getTranslatedFile()
	{
		return translatedFile;
	}

	private String projectId; 
	private File translatedFile;
	

}

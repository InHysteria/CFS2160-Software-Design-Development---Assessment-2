package com.inhysterics.zillionaire;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

/**
 * Manages question set discovery.
 */
public class QuestionSetService 
{
	/**
	 * The name of the manifest file to look for when discovering questionsets on the remote server.
	 */
	private static final String _QUESTION_SET_MANFIFEST = "question_manifest.xml";
	
	/**
	 * The file path to use for local questionsets.
	 */
	public static final String QUESTION_SET_LOCAL_PATH = "./questionsets/";
	
	/**
	 * The address of the remote server.
	 */
	public static final String QUESTION_SET_ONLINE_PATH = "https://raw.githubusercontent.com/InHysteria/CFS2160-Software-Design-Development---Assessment-2/master/";
	
	/**
	 * Gets a list of all question sets that exist locally.
	 * 
	 * @return A list of all question sets that exist locally.
	 */
	public static QuestionSet[] getLocalQuestionSets()
	{
		File directory = new File(QUESTION_SET_LOCAL_PATH);
		if (!directory.exists()) return new QuestionSet[0];
		
		File[] questionSetFiles = directory.listFiles(new FilenameFilter() {
	        @Override
	        public boolean accept(File dir, String name) {
	        	return name.toLowerCase().endsWith(".questionset") 
	        		|| name.toLowerCase().endsWith(".xml") ;
	        }
	    });
		
		ArrayList<Exception> errors = new ArrayList<Exception>();
		QuestionSet[] sets = new QuestionSet[questionSetFiles.length];
		for (int i = 0; i < questionSetFiles.length; i++)
			sets[i] = QuestionSet.Builder.CreateQuestionSet(questionSetFiles[i].getAbsolutePath(), errors);
		
		for (Exception e : errors)
			e.printStackTrace();
		
		return sets;
	}
	
	/**
	 * Gets a list of all questions sets that exist remotely.
	 * 
	 * @return A list of all question sets that exist remotely.
	 */
	public static QuestionSetRemote[] getRemoteQuestionSets()
	{		
		try 
		{		
			int i;
			String manifestSource = pullFromGit(_QUESTION_SET_MANFIFEST);
			
			InputSource is = new InputSource();
			is.setCharacterStream(new StringReader(manifestSource));
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();	
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document dom = builder.parse(is);
			Element rootElement = dom.getDocumentElement();
			
			NodeList remoteQuestionsList = rootElement.getElementsByTagName("QuestionSetRemote");
			QuestionSetRemote[] remoteQuestions = new QuestionSetRemote[remoteQuestionsList.getLength()];
			for (i = 0; i < remoteQuestionsList.getLength(); i++)
			{
				Element remoteQuestion = (Element)remoteQuestionsList.item(i).getChildNodes();
				NodeList nameList = remoteQuestion.getElementsByTagName("Name");
				NodeList versionList = remoteQuestion.getElementsByTagName("Version");
				NodeList authorList = remoteQuestion.getElementsByTagName("Author");
				NodeList descriptionList = remoteQuestion.getElementsByTagName("Description");
				NodeList questionsList = remoteQuestion.getElementsByTagName("Questions");
				NodeList remoteList = remoteQuestion.getElementsByTagName("Remote");
				
				remoteQuestions[i] = new QuestionSetRemote(
						nameList.getLength() == 0 ? "$MISSING$" : nameList.item(0).getTextContent(),
						versionList.getLength() == 0 ? "$MISSING$" : versionList.item(0).getTextContent(),
						authorList.getLength() == 0 ? "$MISSING$" : authorList.item(0).getTextContent(),
						descriptionList.getLength() == 0 ? "" : descriptionList.item(0).getTextContent(),
						Integer.parseInt(questionsList.getLength() == 0 ? "0" : questionsList.item(0).getTextContent()),
						remoteList.getLength() == 0 ? "$MISSING$" : remoteList.item(0).getTextContent()
				);						
			}
			
			return remoteQuestions;
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			return new QuestionSetRemote[0];
		}
	}
	
	/**
	 * Downloads a file from the remote server.
	 * 
	 * @return The content of the file from the remote server.
	 */
	public static String pullFromGit(String gitpath)
	{
		//Code courtesy of https://stackoverflow.com/questions/1359689/how-to-send-http-request-in-java#1359700
		//Modified to cleanup and reduce scope
		HttpURLConnection connection = null;

		try 
		{
			URL url = new URL(QUESTION_SET_ONLINE_PATH + gitpath);
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");		
			connection.setRequestProperty("Content-Length", "0");
			connection.setRequestProperty("Content-Language", "en-UK");  
			
			connection.setUseCaches(false);
			connection.setDoOutput(true);
			
			InputStream is = connection.getInputStream();
			BufferedReader rd = new BufferedReader(new InputStreamReader(is));
			StringBuilder response = new StringBuilder();
			String line;
			while ((line = rd.readLine()) != null) 
			{
				response.append(line);
				response.append('\r');
			}
			rd.close();
			return response.toString();
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			return null;
		} 
		finally 
		{
			if (connection != null) 
			{
				connection.disconnect();
			}
		}
	}

	/**
	 * Downloads a file from the remote server, then writes it to the local filesystem.
	 * 
	 * @return The content of the file from the remote server.
	 */
	public static String pullFromGit(String gitpath, String localpath)
	{
		File directory = new File(QUESTION_SET_LOCAL_PATH);
		if (!directory.exists()) 
			directory.mkdirs();
		
		String data = pullFromGit(gitpath);
		try (PrintWriter writer = new PrintWriter(QUESTION_SET_LOCAL_PATH + localpath))
		{
			writer.println(data);
		} 
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		}
		
		return data;
	}
	
	/**
	 * Generates a manifest of questionsets that exist locally for upload to the remote server.
	 */
	public static void generateLocalQuestionManifest()
	{
		File outputFile = new File("./" + _QUESTION_SET_MANFIFEST);
		if (outputFile.exists())
			outputFile.delete();

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try 
		{
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document dom = builder.newDocument();
			Element rootElement = dom.createElement("QuestionSetManifest");
			dom.appendChild(rootElement);
			
			for (QuestionSet set : getLocalQuestionSets())
			{
				Element setRootElement = dom.createElement("QuestionSetRemote");
				Element setNameElement = dom.createElement("Name");
				Element setVersionElement = dom.createElement("Version");
				Element setAuthorElement = dom.createElement("Author");
				Element setDescriptionElement = dom.createElement("Description");
				Element setQuestionsElement = dom.createElement("Questions");
				Element setRemoteElement = dom.createElement("Remote");

				setNameElement.setTextContent(set.name);
				setVersionElement.setTextContent(set.version);
				setAuthorElement.setTextContent(set.author);
				setDescriptionElement.setTextContent(set.description);
				setQuestionsElement.setTextContent(Integer.toString(set.questions.length));
				setRemoteElement.setTextContent(set.filename);

				setRootElement.appendChild(setNameElement);
				setRootElement.appendChild(setVersionElement);
				setRootElement.appendChild(setAuthorElement);
				setRootElement.appendChild(setDescriptionElement);
				setRootElement.appendChild(setQuestionsElement);
				setRootElement.appendChild(setRemoteElement);		
				rootElement.appendChild(setRootElement);
			}			
			
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource source = new DOMSource(dom);
            StreamResult file = new StreamResult(outputFile);
            transformer.transform(source, file);
            System.out.println("DONE");
			
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}

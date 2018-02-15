package com.inhysterics.zillionaire;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
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

public class QuestionSetService 
{
	public static final String QUESTION_SET_LOCAL_PATH = "./questionsets/";
	public static final String QUESTION_SET_ONLINE_PATH = "https://raw.githubusercontent.com/InHysteria/CFS2160-Software-Design-Development---Assessment-2/master/";
	
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
	public static QuestionSetRemote[] getRemoteQuestionSets()
	{
		return new QuestionSetRemote[] 
		{
			new QuestionSetRemote("DEBUG Question Set", "0.0.1", "InHysteria", 3, "debug")
		};
	}
	
	
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
	
	public static void generateLocalQuestionManifest()
	{
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
				Element setQuestionsElement = dom.createElement("Questions");

				setNameElement.setTextContent(set.name);
				setNameElement.setTextContent(set.version);
				setNameElement.setTextContent(set.author);
				setNameElement.setTextContent(Integer.toString(set.questions.length));

				setRootElement.appendChild(setNameElement);
				setRootElement.appendChild(setVersionElement);
				setRootElement.appendChild(setAuthorElement);
				setRootElement.appendChild(setQuestionsElement);		
				rootElement.appendChild(setRootElement);
			}			
			
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource source = new DOMSource(dom);
            StreamResult file = new StreamResult(new File("./question_manifest.xml"));
            transformer.transform(source, file);
            System.out.println("DONE");
			
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}

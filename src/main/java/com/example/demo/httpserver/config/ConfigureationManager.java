package com.example.demo.httpserver.config;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import com.example.demo.httpserver.util.Json;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;

public class ConfigureationManager
	{
	
	
	public ConfigureationManager() {}
	
	private static Configurration currentConfiguration;
	
	private static class SettingHodler
		{
			private static final ConfigureationManager INSTANCE = new ConfigureationManager();
		}
		
		

		
	public static ConfigureationManager getInstance()
		{
			return SettingHodler.INSTANCE;
		}
	
	
	// Used to load a configuration file by the path provided
	public void loadConfigurationFile(String filePath)
		{
			FileReader fileReader;
			try 
				{
					fileReader = new FileReader(filePath);
				} 
			catch (FileNotFoundException e) 
				{
					throw new HttpConfigurationException(e);
				}
			StringBuffer stringBuffer = new StringBuffer();
			
			int read;
			
			try {
				while ((read = fileReader.read()) !=-1) 
				{
					stringBuffer.append((char)read);
				}
			} catch (IOException e) {
				throw new HttpConfigurationException(e);
			}
			
			JsonNode conf;
			try 
				{
					conf = Json.parse(stringBuffer.toString());
				} 
			catch (IOException e) 
				{
					throw new HttpConfigurationException("Error parsing the Configuration File", e);
				}
			
			try 
				{
					currentConfiguration = Json.fromJson(conf, Configurration.class);
				} 
			catch (JsonProcessingException e) 
				{
				throw new HttpConfigurationException("Error parsing the Configuration file, inertnal",e);
				}
			 
		}
	// Return the Current loaded Configuration 
	public Configurration getCurrentConfiguration()
		{	
			if(currentConfiguration == null)
				{
					throw new HttpConfigurationException("No Current Configuration Set");
				}
			return currentConfiguration;
		}
	
	}

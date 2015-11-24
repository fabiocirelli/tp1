package edu.iut.app;

import org.codehaus.groovy.tools.shell.Command;

import java.util.HashMap;
import java.util.ArrayList;
import java.io.File;

public class CommandLineParser {
	
	protected HashMap<String, CommandLineOption<?>> options;
	protected ArrayList<String> parseErrors;
		
	public CommandLineParser() {
		options = new HashMap<>();
		parseErrors = new ArrayList<>();
	}
	
	public void addOption(CommandLineOption<?> option) {
		if (option != null) {
			options.put(option.getKey(),option);
		}
	}
	
	public void parse(String[] args) {
		for (String argument: args) {
			String[] keyValue=argument.split("=");
			if (options.containsKey(keyValue[0])) {
				switch (options.get(keyValue[0]).getOptionType()) {
					case FILE:
						CommandLineOption<File> fileOption = (CommandLineOption<File>)options.get(keyValue[0]);
						if (keyValue.length == 2) {
							fileOption.setValue(new File(keyValue[1]));
						}
						else {
							parseErrors.add("Option should have a key and a value.");
						}
						break;

					case STRING:
						CommandLineOption<String> stringOption = (CommandLineOption<String>)options.get(keyValue[0]);
						if(keyValue.length == 2){
							stringOption.setValue(keyValue[1]);
						}else{
							parseErrors.add("Option should have a key and a value.");
						}
						break;

					case INTEGER:
						CommandLineOption<Integer> integerOption = (CommandLineOption<Integer>)options.get(keyValue[0]);
						if(keyValue.length == 2){
							integerOption.setValue(Integer.valueOf(keyValue[1]));
						}else{
							parseErrors.add("Option should have a key and a value.");
						}
						break;

					case DOUBLE:
						CommandLineOption<Double> doubleOption = (CommandLineOption<Double>)options.get(keyValue[0]);
						if(keyValue.length == 2){
							doubleOption.setValue(Double.valueOf(keyValue[1]));
						}else{
							parseErrors.add("Option should have a key and a value.");
						}
						break;

					case NOVALUE:
						CommandLineOption<Void> voidOption = (CommandLineOption<Void>)options.get(keyValue[0]);
						break;

					default:
						parseErrors.add("Unrecognize option type.");						
				}
			}
		}
	}
	
	public CommandLineOption<?> getOption(String key) {
		if (options.containsKey(key)) {
			return options.get(key);
		}
		return null;
	}
	
	public ArrayList<String> getErrors() {
		return parseErrors;
	}
	
}

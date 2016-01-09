package edu.iut.tools;

import edu.iut.app.CommandLineOption;
import edu.iut.app.CommandLineParser;

import javax.swing.*;
import java.awt.*;


public class IUTScheduler {
	public static void main(String[] args) {
		System.setProperty("apple.laf.useScreenMenuBar", "true");

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}


		//Locale.setDefault(Locale.FRANCE);
		CommandLineParser commandLineParser = new CommandLineParser();
		CommandLineOption<java.io.File> configOption = new CommandLineOption<java.io.File>(CommandLineOption.OptionType.FILE, "config","configuration file",new java.io.File("/tmp"));
		commandLineParser.addOption(configOption);
		commandLineParser.parse(args);
		System.err.println("Option:"+commandLineParser.getOption("config").getValue());
		SwingUtilities.invokeLater(() -> {
            JFrame mainFrame = new edu.iut.gui.frames.SchedulerFrame("IUT Scheduler");
			mainFrame.setSize(new Dimension(1200,800));
            mainFrame.setVisible(true);
        });
	}
	
}

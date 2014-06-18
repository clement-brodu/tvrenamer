package com.google.code.tvrenamer.model;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import com.google.code.tvrenamer.controller.GlobalOverridesPersistence;
import com.google.code.tvrenamer.model.util.Constants;

public class GlobalOverrides {
	private static Logger logger = Logger.getLogger(UserPreferences.class.getName());

	public static File overridesFile = new File(System.getProperty("user.home") + File.separatorChar
		+ Constants.OVERRIDES_FILE);

	private final static GlobalOverrides INSTANCE = load();

	private Map<String, String> showNames;

	private GlobalOverrides() {
		showNames = new HashMap<String, String>();
		showNames.put("Archer (2009)", "Archer");
		showNames.put("The Newsroom (2012)", "The Newsroom");
		showNames.put("House of Cards (2013)", "House of Cards");
	}

	public static GlobalOverrides getInstance() {
		return INSTANCE;
	}

	private static GlobalOverrides load() {
		GlobalOverrides overrides = GlobalOverridesPersistence.retrieve(overridesFile);

		if (overrides != null) {
			logger.finer("Sucessfully read overrides from: " + overridesFile.getAbsolutePath());
			logger.info("Sucessfully read overrides: " + overrides.toString());
		} else {
			overrides = new GlobalOverrides();
			store(overrides);
		}

		return overrides;
	}

	public static void store(GlobalOverrides overrides) {
		GlobalOverridesPersistence.persist(overrides, overridesFile);
		logger.fine("Sucessfully saved/updated overrides");
	}

	public String getShowName(String showName) {
		String name = this.showNames.get(showName);
		if (name == null) {
			name = showName;
		}
		return name;
	}
}
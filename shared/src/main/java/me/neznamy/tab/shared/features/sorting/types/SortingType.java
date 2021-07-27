package me.neznamy.tab.shared.features.sorting.types;

import java.util.LinkedHashMap;
import java.util.List;

import me.neznamy.tab.api.TabPlayer;
import me.neznamy.tab.api.placeholder.Placeholder;
import me.neznamy.tab.shared.ITabPlayer;
import me.neznamy.tab.shared.TAB;

public abstract class SortingType {

	//number to add to / subtract from to prevent incorrect sorting with negative values
	protected static final int DEFAULT_NUMBER = 500000000;
	
	//placeholder to sort by, if sorting type uses it
	protected String sortingPlaceholder;
	
	//used placeholders in sorting placeholder
	private List<String> usedPlaceholders;
	
	/**
	 * Constructs new instance
	 */
	protected SortingType() {
	}
	
	/**
	 * Constructs new instance with given parameter
	 * @param sortingPlaceholder - placeholder to sort by
	 */
	protected SortingType(String sortingPlaceholder){
		this.sortingPlaceholder = sortingPlaceholder;
		usedPlaceholders = TAB.getInstance().getPlaceholderManager().detectPlaceholders(sortingPlaceholder);
	}
	
	/**
	 * Applies all placeholders for specified player
	 * @param player - player to set placeholders for
	 * @return text with replaced placeholders
	 */
	protected String setPlaceholders(TabPlayer player) {
		String replaced = sortingPlaceholder;
		if (sortingPlaceholder.contains("%")) {
			for (String identifier : usedPlaceholders) {
				Placeholder pl = TAB.getInstance().getPlaceholderManager().getPlaceholder(identifier);
				if (replaced.contains(pl.getIdentifier())) {
					replaced = pl.set(replaced, player).toString();
				}
			}
		}
		return replaced;
	}
	
	/**
	 * Loads sorting list from config and applies sorting numbers
	 * @return map of lowercased groups with their sorting characters
	 */
	protected LinkedHashMap<String, String> convertSortingElements(String[] elements) {
		LinkedHashMap<String, String> sortedGroups = new LinkedHashMap<>();
		int index = 1;
		int charCount = String.valueOf(elements.length).length(); //1 char for <10 groups, 2 chars for <100 etc
		for (String group : elements){
			while (group.startsWith(" ")) group = group.substring(1);
			while (group.endsWith(" ")) group = group.substring(0, group.length()-1);
			StringBuilder sb = new StringBuilder();
			sb.append(index);
			while (sb.length() < charCount) { 
				sb.insert(0, "0");
			}
			for (String group0 : group.toLowerCase().split(" ")) {
				sortedGroups.put(group0, sb.toString());
			}
			index++;
		}
		return sortedGroups;
	}
	
	/**
	 * Returns current sorting characters of this sorting type for specified player
	 * @param p - player to get chars for
	 * @return an as-short-as-possible character sequence for unique sorting
	 */
	public abstract String getChars(ITabPlayer p);
}
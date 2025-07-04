package de.enderland.skyblock.database.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@DatabaseTable(tableName = "island_options")
public class IslandOptionsModel {

	@DatabaseField(generatedId = true, canBeNull = false, uniqueCombo = true)
	private int id;

	@DatabaseField(foreign = true, columnName = "island_id", foreignColumnName = "island_id", uniqueCombo = true)
	private IslandModel island;

	@DatabaseField(uniqueCombo = true, columnName = "option", canBeNull = false)
	private String option;

	@DatabaseField(defaultValue = "false", canBeNull = false)
	private boolean enabled;
}

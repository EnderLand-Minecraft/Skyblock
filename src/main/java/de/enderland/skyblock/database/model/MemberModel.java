package de.enderland.skyblock.database.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@DatabaseTable(tableName = "island_members")
public class MemberModel {

	@DatabaseField(generatedId = true, canBeNull = false, uniqueCombo = true)
	private int id;

	@DatabaseField(foreign = true, columnName = "island_id", foreignColumnName = "island_id", uniqueCombo = true)
	private IslandModel island;

	@DatabaseField(uniqueCombo = true)
	private String uuid;

	@DatabaseField(defaultValue = "member")
	private String role;
}

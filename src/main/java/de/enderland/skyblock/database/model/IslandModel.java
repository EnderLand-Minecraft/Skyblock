package de.enderland.skyblock.database.model;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter()
@Setter
@NoArgsConstructor
@DatabaseTable(tableName = "islands")
public class IslandModel {

	@DatabaseField(generatedId = true, canBeNull = false)
	private int id;

	@DatabaseField(uniqueCombo = true, canBeNull = false)
	private String island_id;

	@DatabaseField(uniqueCombo = true, canBeNull = false)
	private String owner;

	@DatabaseField(canBeNull = false,  columnName = "island_center")
	private String location;

	@DatabaseField(defaultValue = "0")
	private int level;

	@DatabaseField(canBeNull = false)
	private Timestamp created_at;

	@ForeignCollectionField(foreignFieldName = "island_id")
	private ForeignCollection<MemberModel> members;

	@ForeignCollectionField(foreignFieldName = "island_id")
	private ForeignCollection<IslandOptionsModel> options;
}

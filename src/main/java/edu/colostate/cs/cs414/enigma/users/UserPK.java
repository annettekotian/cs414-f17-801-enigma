package edu.colostate.cs.cs414.enigma.users;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the user database table.
 * 
 */
@Embeddable
public class UserPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(unique=true, nullable=false)
	private int id;

	@Column(name="user_level_id", insertable=false, updatable=false, unique=true, nullable=false)
	private int userLevelId;

	public UserPK() {
	}
	public int getId() {
		return this.id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getUserLevelId() {
		return this.userLevelId;
	}
	public void setUserLevelId(int userLevelId) {
		this.userLevelId = userLevelId;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof UserPK)) {
			return false;
		}
		UserPK castOther = (UserPK)other;
		return 
			(this.id == castOther.id)
			&& (this.userLevelId == castOther.userLevelId);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.id;
		hash = hash * prime + this.userLevelId;
		
		return hash;
	}
}
package com.crypto.electionCommission.user;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class User {
	
	@Id
	private String nic;
	private String name;
	private String randomID;
	
	public User() {
		
	}

	public User(String nic, String name, String randomID) {
		super();
		this.nic = nic;
		this.name = name;
		this.randomID = randomID;
	}

	public String getNic() {
		return nic;
	}

	public void setNic(String nic) {
		this.nic = nic;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRandomID() {
		return randomID;
	}

	public void setRandomID(String randomID) {
		this.randomID = randomID;
	}
	
	
}
	

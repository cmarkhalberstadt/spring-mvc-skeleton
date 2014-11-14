package com.xpanxion.skeleton.dto.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * User Entity
 * 
 * Represents items from the user test table.  
 * Exposes one named query that returns all entities from the table
 * 
 * @author bsmith
 *
 */
@Entity
@Table(name = "userNamesAndPasswords")
@NamedQuery(name = "userNamesAndPasswords.getAll", query = "from UserEntity")
public class UserEntity {
	private long id;
	private String userName;
	private String password;
	
	/**
     * Returns the Id of the entity.  This is the primary key. 
     * 
     * @return the id of the entity
     */
    @Id
    @GeneratedValue
    public long getId() {
        return this.id;
    }
    
    /**
     * Returns the entity's user name text field
     * 
     * @return the user name text field. 
     */
    @Column(name="username")
    public String getUserName() {
        return this.userName;
    }
    
    /**
     * Returns the entity's password text field
     * 
     * @return the password text field. 
     */
    @Column(name="password")
    public String getPassword() {
        return this.password;
    }
    
    /**
     * Sets the entity's id. 
     * 
     * @param id
     *            the id to set
     */
    public void setId(long id) {
        this.id = id;
    }
    
    /**
     * Sets the entity's user name text field
     * 
     * @param userName the user name to set
     */
    public void setUserName(String userName){
    	this.userName = userName;
    }
    
    /**
     * Sets the entity's password test field
     * 
     * @param password the password to set
     */
    public void setPassword(String password){
    	this.password = password;
    }
    
}

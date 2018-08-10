package com.excilys.formation.model;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.sun.istack.internal.Nullable;

@Entity
@Table (name= "user" , schema = "computer-database-db")
public class User implements Serializable, UserDetails{

	

	private static final long serialVersionUID = 7562168758463049951L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true)
	private long id;
	
	@Nullable
	@Column(name = "name")
	private String name;
	
	@Nullable
	@Column(name = "name", insertable = false, updatable = false)
	private String password;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	@Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
	
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

	@Override
	public String getUsername() {
		return name;
	}
}

package fr.fms.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data @NoArgsConstructor @AllArgsConstructor 
public class Role implements Serializable{
	private static final long serialVersionUID = 1L; 
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)	
private Long id;
	
private String role;


	@Override
	public String toString() {
		return role ;
	}
	
}
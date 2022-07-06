package fr.fms.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserTask implements Serializable {
	public UserTask(Object object, TaskTable exemple, String string, String string2, Date parseDate, Date parseDate2,
			int i, Object object2) {
		// TODO Auto-generated constructor stub
	}

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String title;
	private String description;
	private Date startDate;
	private Date endDate;
	private int status;				// 1-To do . 2-In progress . 3-Done
	
	@ManyToOne
	private User user;
	
	@ManyToOne
	private TaskTable taskTable;	

}
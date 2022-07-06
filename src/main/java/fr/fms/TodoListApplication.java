package fr.fms;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import fr.fms.business.IBusinessImpl;
import fr.fms.entities.Role;
import fr.fms.entities.TaskTable;
import fr.fms.entities.User;
import fr.fms.entities.UserTask;

@SpringBootApplication
public class TodoListApplication implements CommandLineRunner {
	
	@Autowired
	private IBusinessImpl businessImpl;

	public static void main(String[] args) {
		SpringApplication.run(TodoListApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		businessImpl.generateAccountValues();
	}
	


}

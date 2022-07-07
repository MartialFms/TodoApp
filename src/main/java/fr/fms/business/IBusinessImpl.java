package fr.fms.business;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;

import javax.annotation.PostConstruct;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import fr.fms.dao.RoleRepository;
import fr.fms.dao.TaskTableRepository;
import fr.fms.dao.UserRepository;
import fr.fms.dao.UserTaskRepository;
import fr.fms.entities.Role;
import fr.fms.entities.TaskTable;
import fr.fms.entities.User;
import fr.fms.entities.UserTask;

@Service
public class IBusinessImpl implements IBusiness {

	@Autowired
	public TaskTableRepository taskTableRepository;

	@Autowired
	public UserTaskRepository userTaskRepository;

	@Autowired
	public UserRepository userRepository;
	
	@Autowired
	public RoleRepository roleRepository;

	public static Date parseDate(String date) {
		try {
			return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(date);
		} catch (ParseException e) {
			return new Date();
		}
	}

	public String encodePassword(String password) {
		PasswordEncoder pe = new BCryptPasswordEncoder();
		return pe.encode(password);
	}
	
	public void nameAuth(Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication(); // verifie utilisateur Connecte
		String currentUserCo = auth.getName(); // recupere son nom
		model.addAttribute("auth", currentUserCo);
	}

	public void generateAccountValues() {

		roleRepository.save(new Role(null, "ADMIN"));
		roleRepository.save(new Role(null, "USER"));


		userRepository.save(new User(null, "admin", "Admin", "Owner", encodePassword("fms2022"),"ADMIN",true,null));
		userRepository.save(new User(null, "admin", "Admin", "Owner", encodePassword("fms2022"),"USER",true,null));
		userRepository.save(new User(null, "martial.derand@fms.com", "Martial", "Derand", encodePassword("fms2022"),"USER",true,null));
		userRepository.save(new User(null, "mathieu.fix@fms.com", "Mathieu", "Fix", encodePassword("fms2022"), "USER",true,null));
		userRepository.save(new User(null, "eric.mauler@fms.com", "Eric", "Mauler", encodePassword("fms2022"), "USER",true,null));
		userRepository.save(new User(null, "tristan.laclau@fms.com", "Tristan", "Laclau", encodePassword("fms2022"), "USER",true,null));

		TaskTable exemple = taskTableRepository.save(new TaskTable(null,"exemple",null));

		//UserTask task0 = userTaskRepository.save(new UserTask(null,"exemple1", "premier essai", IBusinessImpl.parseDate("2022-05-15 09:32:51"), IBusinessImpl.parseDate("2022-05-15 09:32:51"), 1, user0, exemple ));

			}
}

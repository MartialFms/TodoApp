package fr.fms.business;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import fr.fms.dao.RoleRepository;
import fr.fms.dao.TaskTableRepository;
import fr.fms.dao.TaskTypeRepository;
import fr.fms.dao.UserRepository;
import fr.fms.dao.UserTaskRepository;
import fr.fms.entities.Role;
import fr.fms.entities.TaskTable;
import fr.fms.entities.TaskType;
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

	@Autowired
	public TaskTypeRepository taskTypeRepository;

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
		try {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			String currentUserCo = auth.getName();

			model.addAttribute("auth", currentUserCo);
		} catch (Exception e) {
			System.out.print(SecurityContextHolder.getContext().getAuthentication().getName());
		}
	}

	public User getConnectedUser() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userRepository.findFirstByUsername(auth.getName());
		return user;
	}

	public void generateAccountValues() {

		try {
			roleRepository.save(new Role(null, "ADMIN"));
			roleRepository.save(new Role(null, "USER"));

			User user0 = userRepository
					.save(new User(null, "admin", "Admin", "Owner", encodePassword("fms2022"), "ADMIN", true, null));
			User user1 = userRepository
					.save(new User(null, "admin", "Admin", "Owner", encodePassword("fms2022"), "USER", true, null));
			User user2 = userRepository.save(new User(null, "martial.derand@fms.com", "Martial", "Derand",
					encodePassword("fms2022"), "USER", true, null));
			User user3 = userRepository.save(new User(null, "mathieu.fix@fms.com", "Mathieu", "Fix",
					encodePassword("fms2022"), "USER", true, null));
			User user4 = userRepository.save(new User(null, "eric.mauler@fms.com", "Eric", "Mauler",
					encodePassword("fms2022"), "USER", true, null));
			User user5 = userRepository.save(new User(null, "tristan.laclau@fms.com", "Tristan", "Laclau",
					encodePassword("fms2022"), "USER", true, null));

			TaskTable exemple = taskTableRepository.save(new TaskTable(null, "exemple", null));

			TaskType administratif = taskTypeRepository.save(new TaskType(null, "Administratif"));
			TaskType comptability = taskTypeRepository.save(new TaskType(null, "Comptability"));
			TaskType formation = taskTypeRepository.save(new TaskType(null, "Formation"));
			TaskType marketing = taskTypeRepository.save(new TaskType(null, "Marketing"));
			TaskType humanRessources = taskTypeRepository.save(new TaskType(null, "Human Ressources"));
			TaskType production = taskTypeRepository.save(new TaskType(null, "Production"));

			UserTask taskExemple0 = userTaskRepository.save(new UserTask(null, "exemple 0",
					"ce qui s'affiche sans user", formation, IBusinessImpl.parseDate("2022-05-15 09:32:51"),
					IBusinessImpl.parseDate("2022-05-15 09:32:51"), 1, null, exemple));

			UserTask taskExemple1 = userTaskRepository.save(new UserTask(null, "exemple1", "premier essai", formation,
					IBusinessImpl.parseDate("2022-05-15 09:32:51"), IBusinessImpl.parseDate("2022-05-15 09:32:51"), 1,
					user0, exemple));
			UserTask taskExemple2 = userTaskRepository.save(new UserTask(null, "exemple2", "second essai",
					humanRessources, IBusinessImpl.parseDate("2022-05-15 09:32:51"),
					IBusinessImpl.parseDate("2022-05-15 09:32:51"), 1, user0, exemple));
			UserTask taskExemple3 = userTaskRepository.save(new UserTask(null, "exemple1", "troisieme essai", marketing,
					IBusinessImpl.parseDate("2022-05-15 09:32:51"), IBusinessImpl.parseDate("2022-05-15 09:32:51"), 1,
					user1, exemple));
			UserTask taskExemple4 = userTaskRepository.save(new UserTask(null, "exemple2", "quatrieme essai", marketing,
					IBusinessImpl.parseDate("2022-05-15 09:32:51"), IBusinessImpl.parseDate("2022-05-15 09:32:51"), 1,
					user1, exemple));
		} catch (Exception e) {
			System.out.print(
					taskTypeRepository.findAll() + "/" + userTaskRepository.findAll() + "/" + userRepository.findAll());
		}
	}
}

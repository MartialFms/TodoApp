package fr.fms.controller;

import java.security.Principal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import fr.fms.business.IBusinessImpl;
import fr.fms.dao.TaskTableRepository;
import fr.fms.dao.TaskTypeRepository;
import fr.fms.dao.UserRepository;
import fr.fms.dao.UserTaskRepository;
import fr.fms.entities.TaskTable;
import fr.fms.entities.TaskType;
import fr.fms.entities.User;
import fr.fms.entities.UserTask;


@Controller
public class MainController {

	@Autowired
	public TaskTableRepository taskRepository;
	
	@Autowired
	public UserTaskRepository userTaskRepository;
	
	@Autowired
	public UserRepository userRepository;
	
	@Autowired
	public TaskTableRepository taskTableRepository;
	
	@Autowired
	public TaskTypeRepository taskTypeRepository;
	
	@Autowired
	IBusinessImpl businessImpl;

	@GetMapping("/")
	public String main() {
		return "home";
	}

	@GetMapping("/403")
	public String refused(Model model) {
		return "403";
	}
	
	@GetMapping("/404")
	public String error(Model model) {
		return "404";
	}

	@GetMapping("/home")
	public String home(Model model) {
		businessImpl.nameAuth(model);
		return "home";
	}

	
	@GetMapping("/login")
	public String login(Model model) {
		businessImpl.nameAuth(model);
		return "login";
	}
	
	@GetMapping("/loginAuth")
	public String loginAuth(Model model) {
		return "redirect:/home";
	}
	
	@GetMapping("/logout")
	public String logout(HttpServletRequest request, HttpServletResponse response) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null) {
			new SecurityContextLogoutHandler().logout(request, response, auth);
		}
		return "redirect:/login";
	}
	
	@GetMapping("/tasks")
	public String taskManager(Model model, @RequestParam(name = "page", defaultValue = "0") int page,
				@RequestParam(name = "keyword", defaultValue = "") String kw,
				@RequestParam(name = "table", defaultValue = "0") long tableId) {
		
			List<TaskTable> taskTable = taskTableRepository.findAll();
			Page<UserTask> tasks;
			User userConnected = businessImpl.getConnectedUser();
			//tasks = userTaskRepository.findByTitleContains(kw, PageRequest.of(page, 5));
			tasks = userTaskRepository.findByUser( userConnected , PageRequest.of(page, 5));
//			Page<UserTask> UserTasks;
			
			
			List<User> users = userRepository.findAll();
			model.addAttribute("keyword", kw);
			model.addAttribute("tableList", taskTable);
			model.addAttribute("taskList", tasks);
			model.addAttribute("user", users);			// users.getContent()
			model.addAttribute("pages", new int[tasks.getTotalPages()]);
			model.addAttribute("currentPage", page);
		return "tasks";
	}
	
	@PostMapping("/saveTask")
	public String saveTask(@Valid UserTask userTask, BindingResult bindingResult) {
		if (bindingResult.hasErrors())
			return "add-task";
		userTaskRepository.save(userTask);
		return "redirect:/tasks";
	}
	
	@GetMapping("/table")
	public String tableManager(Model model) {
		return "table";
	}

	
	@GetMapping("/addTask")
	public String addTask(Model model) {
		
		businessImpl.nameAuth(model);
		List<UserTask> tasks = userTaskRepository.findAll();
		List<TaskType> taskTypes = taskTypeRepository.findAll();
		UserTask task = new UserTask();
		task.setStartDate(new Date());
		//task.setStartDate(Calendar.getInstance());					>> https://www.jmdoudoux.fr/java/dej/chap-utilisation_dates.htm
//		task.setEndDate(new Date());
//		task.setStatus(1);
//		task.setUser(null);
//		task.setTaskTable(null);
		model.addAttribute("taskList", tasks);
		model.addAttribute("userTask", task);
		model.addAttribute("taskTypes", taskTypes);
		
		model.addAttribute("currentDate", new Date());
		model.addAttribute("status", 1);
		model.addAttribute("user", null);
		model.addAttribute("table", null);
		
		return "addTask";
	}
	
	@GetMapping("/editTask")
	public String editTask(Long id, Model model)  {
		UserTask userTask= userTaskRepository.findById(id).get();
		model.addAttribute("userTask", userTask);
		return "editTask";
	}
	
	@GetMapping("/deleteTask")
	public String deleTask(Long id, int page, String keyword) {
		userTaskRepository.deleteById(id);
		return "redirect:/tasks?page=" + page + "&keyword=" + keyword;
	}
	
	@GetMapping("/user")
	public String userManager(Model model, @RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "keyword", defaultValue = "") String kSearch,
			@RequestParam(name = "table", defaultValue = "0") long tableId) {
	
		Page<User> users;
		users = userRepository.findByUsernameContains(kSearch, PageRequest.of(page, 5));
		
		model.addAttribute("keyword", kSearch);
		model.addAttribute("userList", users);			// users.getContent()
		model.addAttribute("pages", new int[users.getTotalPages()]);
		model.addAttribute("currentPage", page);
		return "user";
	}
	

}

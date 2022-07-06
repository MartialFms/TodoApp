package fr.fms.controller;

import java.security.Principal;
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
import fr.fms.dao.UserRepository;
import fr.fms.dao.UserTaskRepository;
import fr.fms.entities.TaskTable;
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
	
	@GetMapping("/task-manager")
	public String taskManager(Model model, @RequestParam(name = "page", defaultValue = "0") int page,
				@RequestParam(name = "keyword", defaultValue = "") String kw,
				@RequestParam(name = "table", defaultValue = "0") long tableId) {
		
			List<TaskTable> taskTable = taskTableRepository.findAll();
			Page<UserTask> tasks;
			tasks = userTaskRepository.findByTitleContains(kw, PageRequest.of(page, 5));
			List<User> users = userRepository.findAll();
			model.addAttribute("keyword", kw);
			model.addAttribute("tableList", taskTable);
			model.addAttribute("taskList", tasks);
			model.addAttribute("user", users);			// users.getContent()
			model.addAttribute("pages", new int[tasks.getTotalPages()]);
			model.addAttribute("currentPage", page);
		return "task-manager";
	}
	
	@PostMapping("/saveTask")
	public String save(@Valid UserTask userTask, BindingResult bindingResult) {
		if (bindingResult.hasErrors())
			return "edit";
		userTaskRepository.save(userTask);
		return "redirect:/task-manager";
	}
	
	@GetMapping("/table-manager")
	public String tableManager(Model model) {
		return "table-manager";
	}

	
	@GetMapping("/add-task")
	public String addTask(Model model) {
		
		businessImpl.nameAuth(model);
		List<UserTask> tasks = userTaskRepository.findAll();
		model.addAttribute("taskList", tasks);
		model.addAttribute("userTask", new UserTask());
		return "add-task";
	}
	
	@GetMapping("/edit-task")
	public String editTask(Model model) {
		return "edit-task";
	}
	
	@GetMapping("/user-manager")
	public String userManager(Model model, @RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "keyword", defaultValue = "") String kSearch,
			@RequestParam(name = "table", defaultValue = "0") long tableId) {
	
		Page<User> users;
		users = userRepository.findByUsernameContains(kSearch, PageRequest.of(page, 5));
		
		model.addAttribute("keyword", kSearch);
		model.addAttribute("userList", users);			// users.getContent()
		model.addAttribute("pages", new int[users.getTotalPages()]);
		model.addAttribute("currentPage", page);
		return "user-manager";
	}
	

}

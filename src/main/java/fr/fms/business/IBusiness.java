package fr.fms.business;

import org.springframework.ui.Model;

import fr.fms.entities.User;

public interface IBusiness {

	public String encodePassword(String password);

	public void nameAuth(Model model);

	public User getConnectedUser();

	public void generateAccountValues();

}
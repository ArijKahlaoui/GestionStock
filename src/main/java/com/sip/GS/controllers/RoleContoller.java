package com.sip.GS.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sip.GS.entities.Role;
import com.sip.GS.repositories.RoleRepository;

@Controller
@RequestMapping("/role/")
public class RoleContoller {
	@Autowired
	private RoleRepository roleRepository;
	
	@GetMapping("list")
	 public String listRoles(Model model) {
	 
	 List<Role> roles = (List<Role>) roleRepository.findAll();
	 long nbr = roleRepository.count();
	 if(roles.size()==0)
		 roles = null;
		 model.addAttribute("roles", roles);
		 model.addAttribute("nbr", nbr);
		 return "role/listRoles";
	 }
	
	@GetMapping("add")
	 public String showAddRoleForm() {
	 //m.addAttribute("Role",new Role("Admin"));
	 return "role/addRole";
	 }
	
	@PostMapping("add")
	 public String addRole(@RequestParam("role") String role) {
	 
		 System.out.println(role);
		 Role r = new Role(role);
		 Role rSaved = roleRepository.save(r);
		 System.out.println("role = "+ rSaved);
		 return "redirect:list";
	 }
	
	@GetMapping("delete/{id}")
	 public String deleteProvider(@PathVariable("id") int id, Model model) {
		 Role role = roleRepository.findById(id)
		 .orElseThrow(()-> new IllegalArgumentException("Invalid provider Id:" + id));
		 
		 roleRepository.delete(role);
		 model.addAttribute("roles", roleRepository.findAll());
		 
		 return "redirect:../list";
	 }
}

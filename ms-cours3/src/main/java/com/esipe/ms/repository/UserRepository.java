package com.esipe.ms.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.esipe.ms.domain.User;

@Component
public class UserRepository {

	private Map<String, User> users = new HashMap<>();
	
	public void save(User user){
		
		users.put(user.getEmail(), user);
	}
	
	public void update(User user){
		
		if(!ifExiste(user.getEmail())){
			throw new RuntimeException("user not found");
		}
		
		users.put(user.getEmail(), user);
	}
	
	public User getOne(String email){
		
		return users.get(email);
	}
	
	public void delete(String email){
		
		if(!ifExiste(email)){
			throw new RuntimeException("user not found");
		}
		
		users.remove(email);
	}
	
	private boolean ifExiste(String email){
		return users.containsKey(email);
	}

	public List<User> getAll() {
		return new ArrayList<User>(users.values());
	}
	
	
}

package com.shopme.admin.user;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import com.shopme.common.entity.Role;
import com.shopme.common.entity.User;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class UserRepositoryTests {
	
	@Autowired
	private UserRepository repo;
	
	@Autowired
	private TestEntityManager entityManager;
	
	
	@Test
	public void testCreateUser() {
		Role roleAdmin = entityManager.find(Role.class, 1);
		User sungsik = new User("sungsik@daum.net", "1234", "seong sik", "park");
		
		sungsik.addRole(roleAdmin);
		
		User savedUser = repo.save(sungsik);
		
		assertThat(savedUser.getId()).isGreaterThan(0);
		
	}
	
	@Test
	public void testCreateNewUserWithTwoRoles() {
		User userSeongsik = new User("seongsik@gmail.com", "erer", "sooksung", "park");
		Role roleEditor = new Role(3);
		Role roleAssistant = new Role(5);
		userSeongsik.addRole(roleEditor);
		userSeongsik.addRole(roleAssistant);
		
		User savedUser = repo.save(userSeongsik);
		
		
		assertThat(savedUser.getId()).isGreaterThan(0);
	}
	
	
	@Test
	public void testListAllUsers() {
		Iterable<User> listUsers = repo.findAll();
		listUsers.forEach(user -> System.out.println(user));
		
	}
	
	@Test
	public void testGetUserByiId() {
		User userPark = repo.findById(4).get();
		System.out.println(userPark);
		assertThat(userPark).isNotNull();
	}
	
	@Test
	public void testupdateUserDetailst() {
		User userPark = repo.findById(4).get();
		userPark.setEnabled(true);
		userPark.setEmail("updatedPark@gmail.com");
	}
	
	@Test
	public void testUpdateUserRoles() {
		User userPark = repo.findById(5).get();
		Role roleEditor = new Role(3);
		Role roleSales = new Role(2);
		userPark.getRoles().remove(roleEditor);
		userPark.addRole(roleSales);
		
		repo.save(userPark);
	}
	
	
	@Test
	public void testDeleteUser() {
		Integer userId = 5;
		repo.deleteById(userId);
	}

}

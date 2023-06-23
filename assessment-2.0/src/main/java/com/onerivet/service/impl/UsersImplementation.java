package com.onerivet.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.onerivet.model.entity.Users;
import com.onerivet.model.payload.UsersDto;
import com.onerivet.repositry.UsersRepository;
import com.onerivet.service.UsersService;

@Service
public class UsersImplementation implements UsersService {

	@Autowired
	private UsersRepository usersRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public String addAllUsers(UsersDto usersDto) {
		// TODO Auto-generated method stub
		usersDto.setPassword(new BCryptPasswordEncoder(10).encode(usersDto.getPassword()));
		Users saveUsersRequest = this.modelMapper.map(usersDto, Users.class);
		Users saveUsersResponse = usersRepository.save(saveUsersRequest);
		this.modelMapper.map(saveUsersResponse, UsersDto.class);
		return "Users Saved SuccessFully";
	}

	@Override
	public List<UsersDto> getAllusers() {
		// TODO Auto-generated method stub

		return usersRepository.findAll().stream().map(users -> this.modelMapper.map(users, UsersDto.class))
				.collect(Collectors.toList());

	}

	@Override
	public UsersDto getUsersById(int id) {
		// TODO Auto-generated method stub

		Users getByIdUsersResponse = usersRepository.findById(id).get();
		return this.modelMapper.map(getByIdUsersResponse, UsersDto.class);

	}

	@Override
	public String updateUsersById(UsersDto usersDto, int id) {
		// TODO Auto-generated method stub

		Users users = usersRepository.findById(id).get();

		users.setName(usersDto.getName());
		users.setAddress(usersDto.getAddress());
		users.setUserName(usersDto.getUserName());

		Users saveUpdatedUsersResponse = usersRepository.save(users);
		this.modelMapper.map(saveUpdatedUsersResponse, UsersDto.class);

		return "Users Updated Successfully";
	}

	@Override
	public String deleteAll() {
		// TODO Auto-generated method stub
		usersRepository.deleteAll();
		return "Users Deleted Successfully";
	}

	@Override
	public String deleteById(int id) {
		// TODO Auto-generated method stub
		usersRepository.deleteById(id);
		return "Users deleted";
	}

}

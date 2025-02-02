package com.example.shop.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.shop.dto.ProductDto;

@Mapper
public interface IShopDao {
	public int userLogin(@Param("username")String username, @Param("pw")String pw);
	public List<ProductDto> getProducts(); 
	public ProductDto getProduct(@Param("pid")int pid);
	public int addCart(@Param("pid")int pid, @Param("qty")int qty, @Param("username")String username);
	public int order(@Param("pid")int pid, @Param("qty")int qty, @Param("username")String username);
	
	
	
	
	
	public int adminLogin(@Param("adminId")String adminId, @Param("adminPw")String adminPw);
}

package com.springboot.smartnutri.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.smartnutri.entity.SelectedGroceryItems;

public interface SelectedGroceryItemsRepository extends JpaRepository<SelectedGroceryItems, Long> {
	List<SelectedGroceryItems> findAllByUserEmail(String userEmail);
}

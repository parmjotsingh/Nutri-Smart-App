package com.springboot.smartnutri.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.smartnutri.entity.SelectedGroceryItems;
import com.springboot.smartnutri.payload.GroceryItemsDTO;
import com.springboot.smartnutri.payload.ResponseDTO;
import com.springboot.smartnutri.repository.SelectedGroceryItemsRepository;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "Application controller provides all APIs related to application")
@RestController
@RequestMapping("/api/v1/application")
public class ApplicationController {
	
	@Autowired
	SelectedGroceryItemsRepository selectedGroceryItemsRepository;
	
	@ApiOperation("Api calculates Body Mass Index of user")
	@GetMapping("/bmiCalc/{mass}/{height}")
	public ResponseEntity<?> calculateBMI(@PathVariable(required = true) int mass, @PathVariable long height) {
		if(mass <= 0 || height <=0 )
			return new ResponseEntity<>( new ResponseDTO("Please provide correct value for Mass or Height. Must be greater than 0."), HttpStatus.BAD_REQUEST);
		return new ResponseEntity<>( new ResponseDTO(mass/Math.pow(height, 2),null), HttpStatus.OK);
	}
	
	@ApiOperation("Api to save grocery items and to fetch grocery items")
	@PostMapping("/selectedgrocery")
	public ResponseEntity<?> saveGroceryItems(@RequestBody GroceryItemsDTO groceryItems){

		if(!Objects.isNull(groceryItems) && ( groceryItems.getItems().size() >0 ) ) {
			groceryItems.getItems().forEach(item -> {
				SelectedGroceryItems selectedItems = new SelectedGroceryItems();
				selectedItems.setFood(item.getFood());
				selectedItems.setCalories(item.getCalories());
				selectedItems.setCarbs(item.getCarbs());
				selectedItems.setCategory(item.getCategory());
				selectedItems.setFat(item.getFat());
				selectedItems.setFibers(item.getFibers());
				selectedItems.setGrams(item.getGrams());
				selectedItems.setMeasure(item.getMeasure());
				selectedItems.setMeasuringUnit(item.getMeasuringUnit());
				selectedItems.setProtein(item.getProtein());
				selectedItems.setSaturatedFat(item.getSaturatedFat());
				selectedItems.setSNo(item.getSNo());
				selectedItems.setPurchaseDate(groceryItems.getPurchaseDate());
				selectedItems.setExpiryDate(item.getExpiryDate());
				selectedItems.setUserEmail(groceryItems.getUserEmail());
				selectedGroceryItemsRepository.save(selectedItems);
				});
			return new ResponseEntity<>(groceryItems, HttpStatus.CREATED);
		}
		else if(!Objects.isNull(groceryItems) && ( groceryItems.getItems().size() == 0 )) {
			List<com.springboot.smartnutri.payload.SelectedGroceryItems> listOfSelectedItems = new ArrayList<>();
			selectedGroceryItemsRepository.findAllByUserEmail(groceryItems.getUserEmail()).forEach(item -> {
				com.springboot.smartnutri.payload.SelectedGroceryItems selectedItems = new com.springboot.smartnutri.payload.SelectedGroceryItems();

				selectedItems.setFood(item.getFood());
				selectedItems.setCalories(item.getCalories());
				selectedItems.setCarbs(item.getCarbs());
				selectedItems.setCategory(item.getCategory());
				selectedItems.setFat(item.getFat());
				selectedItems.setFibers(item.getFibers());
				selectedItems.setGrams(item.getGrams());
				selectedItems.setMeasure(item.getMeasure());
				selectedItems.setMeasuringUnit(item.getMeasuringUnit());
				selectedItems.setProtein(item.getProtein());
				selectedItems.setSaturatedFat(item.getSaturatedFat());
				selectedItems.setSNo(item.getSNo());
				groceryItems.setPurchaseDate(item.getPurchaseDate());
				selectedItems.setExpiryDate(item.getExpiryDate());
				groceryItems.setUserEmail(item.getUserEmail());
				
				listOfSelectedItems.add(selectedItems);
			});
			groceryItems.setItems(listOfSelectedItems);
			return new ResponseEntity<>(groceryItems, HttpStatus.OK);
		}
		else {
			return new ResponseEntity<>(groceryItems, HttpStatus.BAD_REQUEST);
		}
	}
}

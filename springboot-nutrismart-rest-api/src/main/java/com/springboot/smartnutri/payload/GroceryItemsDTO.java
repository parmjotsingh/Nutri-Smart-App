package com.springboot.smartnutri.payload;

import java.sql.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GroceryItemsDTO {
	
	private List<SelectedGroceryItems> items;
	
	private Date purchaseDate;
	private String userEmail;
}

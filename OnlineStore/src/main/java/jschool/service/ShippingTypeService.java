package jschool.service;

import java.util.List;

import jschool.dto.ShippingDTO;
import jschool.model.ShippingType;
import jschool.validator.Message;

/**
 * Shipping Type Service interface
 */
public interface ShippingTypeService {

	/**
	 * Adds shipping to db
	 * @param p dto of entity to add
	 * @return Message object info
	 */
	Message add(ShippingDTO p);

	/**
	 * updates shipping in db
	 * @param p dto of entity to update
	 * @return
	 */
	Message update(ShippingDTO p);

	/**
	 * Get list of all shipping types in db
	 * @return list
	 */
	List<ShippingType> list();

	/**
	 * Get list of all shipping types in db
	 * @return list
	 */
    List<ShippingDTO> listDTO();

	/**
	 * Get entity from db by id
	 * @param id id of object
	 * @return entity
	 */
	ShippingType findById(int id);

	/**
	 * Get DTO object by skipping id from db
	 * @param id id of shipping
	 * @return dto
	 */
	ShippingDTO findByIdDTo(int id);

	/**
	 * Removes shipping type
	 * @param id id of shipping to remove
	 * @return Message Object info
	 */
	Message remove(int id);
	
}

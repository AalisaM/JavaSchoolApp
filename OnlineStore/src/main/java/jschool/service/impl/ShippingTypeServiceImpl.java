package jschool.service.impl;

import java.util.List;

import jschool.dto.ShippingDTO;
import jschool.model.ShippingType;
import jschool.service.DTOConverterService;
import jschool.service.ShippingTypeService;
import jschool.validator.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jschool.dao.ShippingTypeDAO;

@Service
public class ShippingTypeServiceImpl implements ShippingTypeService {

	private final ShippingTypeDAO shippingTypeDAO;
	private final DTOConverterService dtoConverterService;

	@Autowired
	public ShippingTypeServiceImpl(DTOConverterService dtoConverterService, ShippingTypeDAO shippingTypeDAO) {
		this.dtoConverterService = dtoConverterService;
		this.shippingTypeDAO = shippingTypeDAO;
	}

	/**
	 * Adds shipping to db
	 * @param p dto of entity to add
	 * @return Message object info
	 */
	@Override
	@Transactional
	public Message add(ShippingDTO p) {
		Message m = new Message();
		try{
			ShippingType newObj = new ShippingType();
			newObj.setType(p.getType());
			this.shippingTypeDAO.add(newObj);
			m.getConfirms().add("Shipping type was successfully added");
		}catch (Exception e){
			m.getErrors().add("Shipping type addition failed");
		}
		return m;

	}

	/**
	 * updates shipping in db
	 * @param p dto of entity to update
	 * @return
	 */
	@Override
	@Transactional
	public Message update(ShippingDTO p) {
		Message m = new Message();
		try{
			ShippingType obj = this.shippingTypeDAO.findById(p.getId());
			obj.setType(p.getType());
		this.shippingTypeDAO.update(obj);
			m.getConfirms().add("Shipping type was successfully updated");
		}catch (Exception e){
			m.getErrors().add("Shipping type update failed");
		}
		return m;
	}

	/**
	 * Get list of all shipping types in db
	 * @return list
	 */
	@Override
	public List<ShippingType> list() {
		return this.shippingTypeDAO.list();
	}


	/**
	 * Get list of all shipping types in db
	 * @return list
	 */
	@Override
	@Transactional
	public List<ShippingDTO> listDTO(){
		return this.dtoConverterService.getShippingDTOList(this.list());
	}

	/**
	 * Get entity from db by id
	 * @param id id of object
	 * @return entity
	 */
	@Override
	@Transactional
	public ShippingType findById(int id) {
		return this.shippingTypeDAO.findById(id);
	}


	/**
	 * Get DTO object by skipping id from db
	 * @param id id of shipping
	 * @return dto
	 */
	@Override
	@Transactional
	public ShippingDTO findByIdDTo(int id){
		return this.dtoConverterService.getShippingDTO(findById(id));
	}


	/**
	 * Removes shipping type
	 * @param id id of shipping to remove
	 * @return Message Object info
	 */
	@Override
	@Transactional
	public Message remove(int id) {
		Message m = new Message();
		try{
			this.shippingTypeDAO.remove(id);
			m.getConfirms().add("Shipping type was successfully removed");
		}catch (Exception e){
			m.getErrors().add("Cannot remove shipping");
		}
		return m;
	}

}

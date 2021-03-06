/*
 * 
 */
package com.niit.devcapsule.controller;

import java.util.Iterator;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.niit.devcapsule.domain.Pizza;
import com.niit.devcapsule.domain.PizzaOrder;
import com.niit.devcapsule.service.PizzaOrderService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



/**
 * The Class PizzaOrderController.
 */
@RestController
public class PizzaOrderController {

  private static final Logger logger = LoggerFactory.getLogger(PizzaOrderController.class);

  /** The pizza order service. */
  @Autowired
  PizzaOrderService pizzaOrderService;

  /**
   * Gets the pizza orders.
   *
   * @return the pizza orders
   */
  @CrossOrigin(origins = "http://localhost:8000")
  @ApiOperation(value = "Get all the orders", produces = "application/json", response = PizzaOrder.class, responseContainer = "List")
  @RequestMapping(value = "/orders", method = RequestMethod.GET)
  public Iterable<PizzaOrder> getPizzaOrders() {
	//logger.info("TrackingId:89a80896-35a4-468c-9ec3-b762ab161429|ClientId:89a80897-35a4-468c-9ec3-b762ab161429|Find all Ordered Pizza");  
	  Iterable<PizzaOrder> pizzaOrderIterable = pizzaOrderService.findAll();
	  try{
				
				Iterator<PizzaOrder> pizzaOrderIter = pizzaOrderIterable.iterator();
				StringBuffer pizzaOrderBuffer = new StringBuffer();
				pizzaOrderBuffer.append("TrackingId:89a80896-35a4-468c-9ec3-b762ab161429|ClientId:89a80897-35a4-468c-9ec3-b762ab161429|Ordered Pizza List : ");
				while(pizzaOrderIter.hasNext()){
					PizzaOrder pizzaOrdObj = pizzaOrderIter.next();
					Set<Pizza> pizzaSet = pizzaOrdObj.getPizzas();
					Iterator<Pizza> pizzaItr = pizzaSet.iterator();
					while(pizzaItr.hasNext()){
						pizzaOrderBuffer.append(pizzaItr.next().getName());
						pizzaOrderBuffer.append("##");
					}
				}
				logger.info(pizzaOrderBuffer.toString());
	  }catch(Exception ex){
			ex.printStackTrace();
	   }
	
    return pizzaOrderIterable;
  }

  /**
   * Adds a new Pizza Order.
   *
   * @param pizzaOrder
   *          the pizza order
   * @return the pizza order
   */
  @CrossOrigin(origins = "http://localhost:8000")
  @ApiOperation(value = "Add a new order", response = PizzaOrder.class, produces = "application/json")
  @RequestMapping(value = "/orders", method = RequestMethod.POST, consumes = "application/json")
  public PizzaOrder addPizzaOrder(
      @ApiParam(value = "New pizza order to add", required = true) @RequestBody PizzaOrder pizzaOrder) {
		  
	logger.info("TrackingId:89a80896-35a4-468c-9ec3-b762ab161429|ClientId:89a80897-35a4-468c-9ec3-b762ab161429|Pizza Ordered : {}",pizzaOrder.getId());	  
    return pizzaOrderService.addOrder(pizzaOrder);
  }

  /**
   * Save pizza order .
   *
   * @param pizzaOrder
   *          the pizza order
   * @param id
   *          the id
   * @return the pizza order
   */
  @CrossOrigin(origins = "http://localhost:8000")
  @ApiOperation(value = "Update a pizza order by ID", produces = "application/json", response = PizzaOrder.class)
  @RequestMapping(value = "/orders/{id}", method = RequestMethod.POST, consumes = "application/json")
  public PizzaOrder savePizzaOrder(
      @ApiParam(value = "Pizza order details to update. Id is passed separately.", required = true) @RequestBody PizzaOrder pizzaOrder,
      @ApiParam(value = "ID of the Pizza Order to update", required = true) @PathVariable Long id) {
    pizzaOrder.setId(id);
    PizzaOrder isItThere = pizzaOrderService.findById(id);
    if (isItThere == null) {
      logger.info("TrackingId:89a80896-35a4-468c-9ec3-b762ab161429|ClientId:89a80897-35a4-468c-9ec3-b762ab161429|Pizza Order Id: {} not found",id);	  
	  throw new ResourceNotFoundException("Order with id " + id + " not found");
	  
    }
	 logger.info("TrackingId:89a80896-35a4-468c-9ec3-b762ab161429|ClientId:89a80897-35a4-468c-9ec3-b762ab161429|Pizza Order Id: {} updated",id);
    return pizzaOrderService.updateOrder(pizzaOrder);
  }

  /**
   * Delete a pizza order.
   *
   * @param id
   *          the id
   */
  @CrossOrigin(origins = "http://localhost:8000")
  @ApiOperation(value = "Delete an existing order by its ID")
  @RequestMapping(value = "/orders/{id}", method = RequestMethod.DELETE)
  public void deletePizzaOrder(@ApiParam(value = "ID of the pizza order to delete", required = true) @PathVariable Long id) {
    PizzaOrder isItThere = pizzaOrderService.findById(id);
	
    if (isItThere == null) {
      logger.info("TrackingId:89a80896-35a4-468c-9ec3-b762ab161429|ClientId:89a80897-35a4-468c-9ec3-b762ab161429|Pizza Order Id: {} not found",id);
	  throw new ResourceNotFoundException("Order with id " + id + " not found");
	  
    }
	logger.info("TrackingId:89a80896-35a4-468c-9ec3-b762ab161429|ClientId:89a80897-35a4-468c-9ec3-b762ab161429|Pizza Order Id: {} deleted",id);
    pizzaOrderService.deleteOrder(id);
  }

}

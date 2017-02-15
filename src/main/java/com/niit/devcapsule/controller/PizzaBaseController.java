/*
 * 
 */
package com.niit.devcapsule.controller;

import java.util.Iterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.niit.devcapsule.domain.Base;
import com.niit.devcapsule.service.PizzaBaseService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Class PizzaBaseController.
 */
@RestController
public class PizzaBaseController {
	
  private static final Logger logger = LoggerFactory.getLogger(PizzaBaseController.class);	

  /** The pizza base service. */
  @Autowired
  PizzaBaseService pizzaBaseService;

  /**
   * Gets the bases.
   *
   * @return the bases
   */
  @CrossOrigin(origins = "http://localhost:8000")
  @ApiOperation(value = "Get all the pizza bases", produces = "application/json", response = Base.class, responseContainer = "List")
  @RequestMapping(value = "/bases", method = RequestMethod.GET)
  public Iterable<Base> getBases() {
	//logger.info("TrackingId:99a80896-35a4-468c-9ec3-b762ab161429|ClientId:99a80897-35a4-468c-9ec3-b762ab161429|Find all Bases");
	Iterable<Base> baseIterable = pizzaBaseService.findAll();
	Iterator<Base> baseIterator =  baseIterable.iterator();
	StringBuffer baseBuffer = new StringBuffer();
	baseBuffer.append("TrackingId:99a80896-35a4-468c-9ec3-b762ab161429|ClientId:99a80897-35a4-468c-9ec3-b762ab161429|Base List :");
	while(baseIterator.hasNext())
	{
		Base baseObj = baseIterator.next() ;
		baseBuffer.append(baseObj.getName());
		baseBuffer.append("##");
	}
	logger.info(baseBuffer.toString());
    return pizzaBaseService.findAll();
  }

  /**
   * Gets the base by name.
   *
   * @param name
   *          the name
   * @return the base by name
   */
  @ApiOperation(value = "Get a specific pizza base by its name", produces = "application/json", httpMethod = "GET", response = Base.class)
  @RequestMapping(value = "/bases/{name}", method = RequestMethod.GET)
  public Base getBaseByName(
      @ApiParam(value = "Name of the base to retrieve", required = true) @PathVariable String name) {
    Base base = pizzaBaseService.findByName(name);
	logger.info("TrackingId:99a80896-35a4-468c-9ec3-b762ab161429|ClientId:99a80897-35a4-468c-9ec3-b762ab161429|Pizza base with Name:: {}", name);  
    if (base == null) {
	  logger.info("TrackingId:99a80896-35a4-468c-9ec3-b762ab161429|ClientId:99a80897-35a4-468c-9ec3-b762ab161429|Pizza base with Name:: {} not found", name);  	
      throw new ResourceNotFoundException("Pizza base with name " + name + " not found");
    }
    return base;
  }

  /**
   * Adds the base.
   *
   * @param base
   *          the base
   * @return the base
   */
  @CrossOrigin(origins = "http://localhost:8000")
  @ApiOperation(value = "Add a new pizza base", produces = "application/json", response = Base.class)
  @RequestMapping(value = "/bases", method = RequestMethod.POST, consumes = "application/json")
  public Base addBase(@ApiParam(value = "Pizza base to be added", required = true) @RequestBody Base base) {
	logger.info("TrackingId:99a80896-35a4-468c-9ec3-b762ab161429|ClientId:99a80897-35a4-468c-9ec3-b762ab161429|BaseName : {}  added", base.getName());    
    return pizzaBaseService.addBase(base.getName());
  }
}

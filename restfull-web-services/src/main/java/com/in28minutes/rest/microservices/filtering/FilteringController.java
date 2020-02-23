package com.in28minutes.rest.microservices.filtering;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

@RestController
public class FilteringController {

	@GetMapping("/filtering")
	public MappingJacksonValue retrieveSomeBean() {
		SomeBean someBean = new SomeBean("value1", "value2", "value3");
		return getMappingJacksonValue("SomeBeanFilter", someBean, "field1", "field2");
	}

	@GetMapping("/filtering-list")
	public MappingJacksonValue retrieveListOfSomeBeans() {
		List<SomeBean> list = Arrays.asList(new SomeBean("value1", "value2", "value3"),
				new SomeBean("value12", "value22", "value32"));
		return getMappingJacksonValue("SomeBeanFilter", list, "field1", "field3");
	}

	private MappingJacksonValue getMappingJacksonValue(String id, Object value, String... propertyArray) {
		SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept(propertyArray);

		FilterProvider filters = new SimpleFilterProvider().addFilter(id, filter);

		MappingJacksonValue mapping = new MappingJacksonValue(value);
		mapping.setFilters(filters);

		return mapping;
	}

}

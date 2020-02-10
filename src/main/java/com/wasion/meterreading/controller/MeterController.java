package com.wasion.meterreading.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wasion.meterreading.entity.view.VMeterFreezenData;
import com.wasion.meterreading.service.CrudServiceI;

@RestController
@RequestMapping("/frozenData")
public class MeterController {

	@Autowired
	private CrudServiceI<VMeterFreezenData> service;

	@GetMapping("/infos")
	public Page<VMeterFreezenData> getFrozenData(VMeterFreezenData condition, Integer page, Integer size, String searchKey) {
		condition.setSearchKey(searchKey);
		PageRequest pageable = PageRequest.of(page, size, new Sort(Direction.DESC, "registerDate"));
		return service.findAll(condition, pageable);
	}

}

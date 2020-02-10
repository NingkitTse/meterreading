package com.wasion.meterreading.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wasion.meterreading.entity.TMeterDevice;
import com.wasion.meterreading.entity.view.VMeterDevice;
import com.wasion.meterreading.service.impl.MeterServiceImpl;

@RequestMapping("/meter")
@RestController
public class DeviceController {

	@Autowired
	private MeterServiceImpl meterService;

	@GetMapping("/infos")
	public Page<VMeterDevice> getMeters(Integer page, Integer size, VMeterDevice record) {
		PageRequest pageVo = PageRequest.of(page, size, new Sort(Direction.ASC, "registerDate"));
		return meterService.findAllMobileMeter(record, pageVo);
	}

	@PostMapping("/info")
	public TMeterDevice insertMeter(@RequestBody @Valid TMeterDevice record) {
		return meterService.insert(record);
	}

	@DeleteMapping("/info")
	public void deletetMeter(@RequestBody TMeterDevice record) {
		meterService.delete(record);
	}

	@PutMapping("/info")
	public TMeterDevice updateMeter(@RequestBody TMeterDevice record) {
		return meterService.update(record);
	}

}

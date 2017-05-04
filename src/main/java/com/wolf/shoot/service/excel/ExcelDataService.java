package com.wolf.shoot.service.excel;

import org.springframework.stereotype.Service;

import com.wolf.shoot.common.constant.ServiceName;
import com.wolf.shoot.service.IService;
import com.wolf.shoot.service.excel.manager.D_BulletLoaderManager;

@Service
public class ExcelDataService implements IService{

	@Override
	public String getId() {
		return ServiceName.EXCELDATASERVICE;
	}

	@Override
	public void startup() throws Exception {
		D_BulletLoaderManager.getInstance().load();
	}

	@Override
	public void shutdown() throws Exception {
		
	}

}

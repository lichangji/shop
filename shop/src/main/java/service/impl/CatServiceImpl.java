package service.impl;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dao.CatVOMapper;
import model.CatVO;
import service.CatService;

@Service
public class CatServiceImpl implements CatService {
	@Autowired
	private CatVOMapper catVOMapper;

	public List<CatVO> getList() {
		List<CatVO> l = catVOMapper.getList();
		for (CatVO catVO : l) {
			catVO.setName(catVO.getName()+"你好");
		}
		return l;
	}
	
}

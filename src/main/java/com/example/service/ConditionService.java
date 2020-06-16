package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.Condition;
import com.example.repository.ConditionRepository;

/**
 * conditionを扱うサービス.
 * 
 * @author ashibe
 *
 */
@Service
@Transactional
public class ConditionService {
	@Autowired
	private ConditionRepository conditionRepository;

	/**
	 * condition一覧を取得
	 * 
	 * @return
	 */
	public List<Condition> findAll() {
		List<Condition> conditionList = conditionRepository.findAll();
		return conditionList;

	}

}

package com.example.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.JdbcTemplateAutoConfiguration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.domain.Condition;

@Repository
public class ConditionRepository {
	
	@Autowired
	private JdbcTemplate template;

	private final static RowMapper<Condition> CONDITION_ROW_MAPPER = (rs, i) -> {
		Condition condition = new Condition();
		condition.setId(rs.getInt("id"));
		condition.setName(rs.getString("name"));
		return condition;
	};
	
	public List<Condition> findAll(){
		String sql="select id,name from condition";
		List<Condition> conditionList =  template.query(sql, CONDITION_ROW_MAPPER);
		return conditionList;
			
			
		
		
		
	}
	
	
	

}

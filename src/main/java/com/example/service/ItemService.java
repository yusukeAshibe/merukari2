package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.Item;
import com.example.repository.ItemRepository;

@Service
@Transactional
public class ItemService {

	@Autowired
	private ItemRepository itemRepository;

	/**
	 * 一覧表示.
	 * 
	 * @param page
	 * @return
	 */
	public List<Item> showItem(Integer page) {
		Integer offset = 0;
		if (page != null) {
			Integer limit = 20;
			offset = limit * (page - 1);
		}
		List<Item> itemList = itemRepository.findAll(offset);
		return itemList;

	}

	/**
	 * 商品詳細遷移.
	 * 
	 * @param id
	 * @return
	 */
	public Item showDetail(Integer id) {
		return itemRepository.load(id);
	}

	/**
	 * 名前検索
	 * 
	 * @param name
	 * @param page
	 * @return
	 */
	public List<Item> searchItem(String name, Integer page) {
		Integer offset = 0;
		if (page != null) {
			Integer limit = 20;
			offset = limit * (page - 1);
		}
		List<Item> itemList = itemRepository.itemList(name, offset);
		return itemList;
	}

	/**
	 * 大中小検索
	 * @param dai
	 * @param chu
	 * @param syo
	 * @param page
	 * @return
	 */
	public List<Item> searchCategoryItem(Integer dai, Integer chu, Integer syo, Integer page) {
		Integer offset = 0;
		if (page != null) {
			Integer limit = 20;
			offset = limit * (page - 1);
		}
		List<Item> itemList = itemRepository.selectCategory(dai, chu, syo, offset);
		return itemList;

	}

	/**
	 * 大中検索
	 * @param dai
	 * @param chu
	 * @param page
	 * @return
	 */
	public List<Item> searchCategoryItem(Integer dai, Integer chu, Integer page) {
		Integer offset = 0;
		if (page != null) {
			Integer limit = 20;
			offset = limit * (page - 1);
		}
		List<Item> itemList = itemRepository.selectCategory(dai, chu, offset);
		return itemList;

	}

	/**
	 * 大検索
	 * @param dai
	 * @param page
	 * @return
	 */
	public List<Item> searchCategoryItem(Integer dai, Integer page) {
		Integer offset = 0;
		if (page != null) {
			Integer limit = 20;
			offset = limit * (page - 1);
		}
		List<Item> itemList = itemRepository.selectCategory(dai,offset);
		return itemList;
	}
	
	/**
	 * 商品の追加.
	 * @param item
	 */
	public void addItem(Item item) {
		itemRepository.addItem(item);
	}
}

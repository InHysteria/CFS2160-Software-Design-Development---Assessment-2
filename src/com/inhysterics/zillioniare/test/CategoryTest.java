package com.inhysterics.zillioniare.test;

import static org.junit.Assert.assertEquals;

import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import org.junit.Test;

import com.inhysterics.zillionaire.Category;

public class CategoryTest
{
	private final int _EXPECTED_ID = ThreadLocalRandom.current().nextInt(10000);
	private final String _EXPECTED_NAME = UUID.randomUUID().toString();
	
	private final Category _category;
	
	public CategoryTest()
	{
		_category = new Category(
			_EXPECTED_ID,
			_EXPECTED_NAME
		);
	}
	
	@Test
	public void getsId()
	{
		assertEquals(_EXPECTED_ID,_category.getId());
	}
	
	@Test
	public void getsName()
	{
		assertEquals(_EXPECTED_NAME,_category.getName());
	}
}

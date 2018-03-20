package com.inhysterics.zillionaire.test;

import static org.junit.Assert.assertEquals;

import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import org.junit.Test;

import com.inhysterics.zillionaire.PlayerState;

public class PlayerStateTest
{
	private PlayerState _state;
	
	public PlayerStateTest()
	{
		_state = new PlayerState();
	}

	@Test
	public void setsHasAskTheAudiance()
	{
		boolean val = ThreadLocalRandom.current().nextInt(2) == 1;  
        _state.setHasAskAudiance(val);
        assertEquals(_state.getHasAskAudiance(), val);
	}
	
	@Test
	public void setsHasFiftyFifty()
	{
		boolean val = ThreadLocalRandom.current().nextInt(2) == 1;  
        _state.setHasFiftyFifty(val);
        assertEquals(_state.getHasFiftyFifty(), val);
	}
	
	@Test
	public void setsName()
	{		
		String val = UUID.randomUUID().toString();
        _state.setName(val);
        assertEquals(_state.getName(), val);
	}
	
	@Test
	public void setsQuestionNo()
	{		
		int val = ThreadLocalRandom.current().nextInt(50);
		_state.setQuestionNo(val);
        assertEquals(_state.getQuestionNo(), val);
	}
}

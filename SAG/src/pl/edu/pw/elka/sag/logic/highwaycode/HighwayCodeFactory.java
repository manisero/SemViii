package pl.edu.pw.elka.sag.logic.highwaycode;

import java.util.*;

import pl.edu.pw.elka.sag.constants.*;
import pl.edu.pw.elka.sag.exceptions.*;
import pl.edu.pw.elka.sag.logic.highwaycode.highwaycodes.*;

public class HighwayCodeFactory
{
	private final Map<String, Class<? extends IHighwayCode>> highwayCodes = new LinkedHashMap<String, Class<? extends IHighwayCode>>();
	
	public HighwayCodeFactory()
	{
		highwayCodes.put(HighwayCodeTypes.POLISH_HIGHWAY_CODE_TYPE, PolishHighwayCode.class);
		highwayCodes.put(HighwayCodeTypes.REVERSED_HIGHWAY_CODE_TYPE, ReversedHighwayCode.class);
	}
	
	public IHighwayCode createHighwayCode(String highwayCodeType)
	{
		if (!highwayCodes.containsKey(highwayCodeType))
		{
			throw new UnknownHighwayCodeTypeException();
		}
		
		Class<? extends IHighwayCode> highwayCodeClass = highwayCodes.get(highwayCodeType);
		
		try
		{
			return highwayCodeClass.getConstructor().newInstance();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}
}

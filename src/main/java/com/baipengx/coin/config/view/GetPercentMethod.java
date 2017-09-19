package com.baipengx.coin.config.view;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Component;

import freemarker.template.SimpleNumber;
import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;

@Component
public class GetPercentMethod implements TemplateMethodModelEx{

	@Override
	public Object exec(@SuppressWarnings("rawtypes") List arguments) throws TemplateModelException {
		if(arguments == null || arguments.size() != 2){
			throw new TemplateModelException("wrong arguments");
		}
		
		BigDecimal coinCap = (BigDecimal) ((SimpleNumber) arguments.get(0)).getAsNumber();
		BigDecimal globalCap = (BigDecimal) ((SimpleNumber) arguments.get(1)).getAsNumber();
		
		return new SimpleNumber(coinCap.divide(globalCap, 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100)));
	}

}

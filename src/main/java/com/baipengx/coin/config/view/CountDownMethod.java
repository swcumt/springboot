package com.baipengx.coin.config.view;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Component;

import freemarker.template.SimpleDate;
import freemarker.template.SimpleScalar;
import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;

@Component
public class CountDownMethod implements TemplateMethodModelEx {

	@Override
	public Object exec(@SuppressWarnings("rawtypes") List arguments) throws TemplateModelException {
		if (arguments == null || arguments.size() != 2) {
			throw new TemplateModelException("arguments is wrong");
		}
		
		if(arguments.get(0) == null){
			return new SimpleScalar("待定");
		}

		Date beginDate = ((SimpleDate) arguments.get(0)).getAsDate();
		Date endDate = ((SimpleDate) arguments.get(1)).getAsDate();

		Date now = new Date();
		
		if (now.after(beginDate) && now.before(endDate)) {
			return new SimpleScalar("进行中");
		}
		else if (now.after(endDate)) {
			return new SimpleScalar("已结束");
		}

		long time = beginDate.getTime() - now.getTime();

		long day = time / (24 * 60 * 60 * 1000);
		long hour = (time - day * 24 * 60 * 60 * 1000) / (60 * 60 * 1000);
		long min = (time - day * 24 * 60 * 60 * 1000 - hour * 60 * 60 * 1000) / (60 * 1000);

		return new SimpleScalar(day + "天" + hour + "小时" + min + "分");
	}

}

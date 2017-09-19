package com.baipengx.coin.wechat.jsapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.baipengx.coin.jedis.JedisDao;
import com.baipengx.coin.wechat.service.TokenService;
import com.baipengx.coin.wechat.util.CommonUtil;
import com.baipengx.coin.wechat.vo.Ticket;
import com.baipengx.common.BaseLogger;

@Component
public class JSApiTicketService extends BaseLogger {
	@Autowired
	private TokenService tokenService;

	@Autowired
	@Qualifier("stringJedis")
	private JedisDao<String, String> jedisDao;

	private static final String JS_API_TICKET_KEY = "gotoken.jsapi.ticket";

	public String getTicket() {
		String ticket = jedisDao.get(JS_API_TICKET_KEY);

		if (!StringUtils.isEmpty(ticket)) {
			return ticket;
		}
		Ticket ticketObj = CommonUtil.getTicket(tokenService.getToken());
		if (ticketObj == null || StringUtils.isEmpty(ticketObj.getTicket())) {
			log.error("获取 ticket 失败, {}", ticketObj);
			return ticket;
		}

		ticket = ticketObj.getTicket();

		jedisDao.save(JS_API_TICKET_KEY, ticket, ticketObj.getExpires_in() - 100l);

		return ticket;
	}
}

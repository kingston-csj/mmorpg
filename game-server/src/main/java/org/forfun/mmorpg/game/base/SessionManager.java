package org.forfun.mmorpg.game.base;

import jforgame.commons.NumberUtil;
import jforgame.socket.share.IdSession;
import org.apache.mina.core.session.AttributeKey;
import org.apache.mina.core.session.IoSession;
import org.forfun.mmorpg.game.database.user.entity.PlayerEnt;
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class SessionManager {

	/** distributeKey auto generator  */
	private AtomicInteger distributeKeyGenerator = new AtomicInteger();
	/** key=playerId, value=session */
	private ConcurrentMap<Long, IdSession> player2sessions = new ConcurrentHashMap<>();


	public void registerNewPlayer(PlayerEnt player, IdSession session) {
		//biding playerId to session
		session.setAttribute(IdSession.ID, player.getId());
		session.setAttribute("PLAYER", player);
		this.player2sessions.put(player.getId(), session);
	}

	/**
	 * get session's playerId
	 * @param session
	 * @return
	 */
	public long getPlayerIdBy(IdSession session) {
		if (session != null) {
			return NumberUtil.longValue(session.getId());
		}
		return 0;
	}

	public IdSession getSessionBy(long playerId) {
		return player2sessions.get(playerId);
	}

	/**
	 * get appointed sessionAttr
	 */
	@SuppressWarnings("unchecked")
	public <T> T getSessionAttr(IoSession session, AttributeKey attrKey, Class<T> attrType) {
		return (T)session.getAttribute(attrKey, attrType);
	}

	public int getNextSessionId() {
		return this.distributeKeyGenerator.getAndIncrement();
	}

	public String getRemoteIp(IoSession session) {
		return ((InetSocketAddress)session.getRemoteAddress()).getAddress().getHostAddress();
	}

}
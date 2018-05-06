/**
 * cdp4j - Chrome DevTools Protocol for Java
 * Copyright © 2017, 2018 WebFolder OÜ (support@webfolder.io)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package io.webfolder.cdp.session;

import java.util.Iterator;
import java.util.Map;
import java.util.Queue;

import io.webfolder.cdp.event.Events;
import io.webfolder.cdp.event.target.ReceivedMessageFromTarget;
import io.webfolder.cdp.event.target.TargetCreated;
import io.webfolder.cdp.event.target.TargetDestroyed;
import io.webfolder.cdp.exception.CdpException;
import io.webfolder.cdp.listener.EventListener;
import io.webfolder.cdp.type.target.TargetInfo;

class TargetListener implements EventListener {

    private Map<String, Session> sessions;

    private Map<String, WSAdapter> wsAdapters;

	private Queue<TabInfo> tabs;

    TargetListener(
		Map<String, Session> sessions,
		Map<String, WSAdapter> wsAdapters,
		Queue<TabInfo> tabs) {
    	this.sessions = sessions;
    	this.wsAdapters = wsAdapters;
    	this.tabs = tabs;
	}

	@Override
	public void onEvent(Events event, Object value) {
		switch (event) {
			case TargetReceivedMessageFromTarget:
				ReceivedMessageFromTarget receivedMessage = (ReceivedMessageFromTarget) value;
				Session session = sessions.get(receivedMessage.getSessionId());
				if ( session != null ) {
					WSAdapter wsAdapter = wsAdapters.get(session.getId());
					if ( wsAdapter != null ) {
						try {
							wsAdapter.onMessage(receivedMessage.getMessage(), false);
						} catch (Exception e) {
							throw new CdpException(e);
						}
					}
				}
			break;
			case TargetTargetCreated:
				TargetCreated targetCreated = (TargetCreated) value;
				TargetInfo info = targetCreated.getTargetInfo();
				String url = info.getUrl();
				String type = info.getType();
				if ("page".equals(type) &&
						(url.isEmpty()                      ||
							"about:blank".equals(url)       ||
							"chrome://welcome/".equals(url) ||
							"chrome://newtab/".equals(url)  ||
							url.startsWith("chrome://welcome-win10"))) {
					tabs.add(new TabInfo(info.getTargetId(), info.getBrowserContextId()));
				}
			break;
			case TargetTargetDestroyed:
				TargetDestroyed destroyed = (TargetDestroyed) value;
				for (Session next : sessions.values()) {
					if (destroyed.getTargetId().equals(next.getTargetId())) {
						next.dispose();
						wsAdapters.remove(next.getId());
						sessions.remove(next.getId());
						next.terminate("Target.targetDestroyed");
					}
				}
				Iterator<TabInfo> iter = tabs.iterator();
				while (iter.hasNext()) {
					TabInfo next = iter.next();
					if (destroyed.getTargetId().equals(next.getTargetId())) {
						tabs.remove(next);
					}
				}
			break;
			default:
			break;
		}
	}
}

package io.webfolder.cdp.session;

class TabInfo {

	private String targetId;

	private String sessionId;

	private String browserContextId;

	TabInfo(String targetId, String browserContextId) {
		this.targetId = targetId;
		this.browserContextId = browserContextId;
	}

	public String getTargetId() {
		return targetId;
	}

	public String getSessionId() {
		return sessionId;
	}

	public String getBrowserContextId() {
		return browserContextId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((sessionId == null) ? 0 : sessionId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TabInfo other = (TabInfo) obj;
		if (sessionId == null) {
			if (other.sessionId != null)
				return false;
		} else if (!sessionId.equals(other.sessionId))
			return false;
		return true;
	}
}

package Acceptance;

import ServiceLayer.ServiceClass;

public abstract class Driver {

	public static Bridge getBridge() {
		ProxyBridge bridge = new ProxyBridge();
		bridge.setRealBridge(new ServiceClass()); // TODO
		return bridge;
	}
}

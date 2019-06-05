cdp4j Release Notes
-------------------------------------------------------------------------------

### 3.0.9 - June 8, 2019

* Added proxy connection [sample](https://github.com/webfolderio/cdp4j/blob/master/src/test/java/io/webfolder/cdp/sample/ProxyConnection.java)
* Added [proxy support] to [ws connection](https://github.com/webfolderio/cdp4j/commit/a448b63ed1fcd0c736ebce90d043eaa0bbe6ca56)
* Properly [disable](https://github.com/webfolderio/cdp4j/commit/50ca0ad4e42e319d0f20ff04e61267c1fc88827a) translate UI
* Added [missing try catch](https://github.com/webfolderio/cdp4j/commit/1d4fa4b2948dd9b88bb7996da920d3a091d14ba4) block to close process stream

### 3.0.8 - January 14, 2019

* Updated copyright date
* Added missing [getters](https://github.com/webfolderio/cdp4j/commit/d38444833d272500463cf3471885d45ebebb3b53)

### 3.0.7 - November 29, 2018

* Update websocket client library (nv-websocket-client) to 2.6
* Roll devtools protocol to r608591
* retry to get cdp4jId more than [once](https://github.com/webfolderio/cdp4j/commit/467a0a2ac9e47be8011f7eb189ee1cec2fbeaff6)
* Selector.matches() return false instead of throwing [exception](https://github.com/webfolderio/cdp4j/commit/147ced7a60c7e753414daac09265d39cf7dd87fa)

### 3.0.6 - August 15, 2018

* Added @JsFunction annotation

### 3.0.5 - July 31, 2018

* Fixed NullPointerException

### 3.0.4 - July 31, 2018

* Added Added a new Constructor to Launcher class
* Fixed ClassNotFoundException (org.jvnet.winp.WinProcess)

### 3.0.3 - July 26, 2018

* Added MacOsProcessManager
* Roll devtools protocol to r574367
* Added Dom.getClickablePoint() and Dom.scrollIntoViewIfNeeded() method
* Fixed Mouse.click() does not trigger click event if the element is not visible.

### 3.0.2 - July 9, 2018

* Fixed ElementNotFoundException for xpath expressions
* Fixed the wrong OS detection code and added human-readable error in case OS cannot be detected (ProcessManager)

### 3.0.1 - June 16, 2018

* Removed sizzle support
* Fixed concurrency bug (incognito mode)
* Added Session.navigateAndWait() method
* Added guard for Launcher.launch #95
* Removed NullProcessManager (AdaptiveProcessManager is the new default ProcessManager)
* Added Session.captureScreenshot(boolean hideScrollbar) method

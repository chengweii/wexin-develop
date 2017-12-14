chrome.browserAction.onClicked.addListener(function(tab) {
	window.open(chrome.extension.getURL('index.html'), "assist");
});

var exec = require('cordova/exec');

const PLUGIN_NAME = 'MagTek';

var MagTek = function() {};

MagTek.listeners = {};

MagTek.getBLEStatus = function(success) {
  exec(success, null, PLUGIN_NAME, 'getBLEStatus', []);
};

MagTek.getSDKVersion = function(success) {
  exec(success, null, PLUGIN_NAME, 'getSDKVersion', []);
};

MagTek.subscribe = function(event, callback, scope) {
  if (typeof event !== 'string' || typeof callback !== 'function') {
    return;
  }

  if (!this.listeners[event]) {
    this.listeners[event] = [];
  }

  var item = {callback: callback, scope: scope || window};

  this.listeners[event].push(item);
};

MagTek.unsubscribe = function(event, callback) {
  var eventListeners = this.listeners[event];

  if (!eventListeners) {
    return;
  }

  if (!callback) {
    eventListeners.splice(0, eventListeners.length);
    return;
  }

  for (var i = 0; i < eventListeners.length; i++) {
    if (eventListeners[i].callback === callback) {
      eventListeners.splice(i, 1);
      break;
    }
  }
};

MagTek.fire = function(event, data, error) {
  var eventListeners = this.listeners[event];

  if (!eventListeners) {
    return;
  }

  for (var i = 0; i < eventListeners.length; i++) {
    var registeredCallback = eventListeners[i].callback;
    var scope = eventListeners[i].scope;
    registeredCallback.apply(scope, [data, error]);
  }
};

module.exports = MagTek;

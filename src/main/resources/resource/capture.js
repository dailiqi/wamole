/**
 * @author bell
 */

/**
 * 提供一个轮询和超时机制
 */
var probe = {
		
	lastNotice : 0,
	/**
	 * 获取一个info的基本信息
	 * 
	 * @returns {___anonymous326_559}
	 */
	interval : $("#step").val(),
	/**
	 * 一次服务器心跳
	 * 
	 * @param testResult
	 */
	beat : function(e, data, cov) {
		probe.timeoutHandle && clearTimeout(probe.timeoutHandle);
		var cov = probe.stringify(cov) || '';
		var options = probe.starttime ? {
			name : probe.kiss.split("/exec")[1],
			starttime : probe.starttime.toString(),
			endtime : new Date().getTime(),
			fail : data ? data[0] : 1,
			total : data ? data[1] : 1,
			cov : cov
		// caseinfo : probe.testframe.src// ,
		// cov : probe.js
		} : {};
//		options.interval = probe.interval;
//		options.browser = probe.browserName;
//		options.id = probe.browserId;
//		options.name = probe.kiss;
		delete probe.starttime;
		$.ajax({
//			url : probe.srvApi,
			url : location.pathname,
			data : options,
			type : 'put',
			success : function(text) {
					probe.lastNotice = new Date().getTime();
					setTimeout(function() {
						probe.runtest(text);
						if(null != text && "" != text) {
							probe.kiss = text;
						}
					}, 0);
				},
			error : function(text) {
				if(text.status == 404) {
					window.close();
				}
//				alert(text);
			}
			});
		probe.timeoutHandle = setTimeout(probe.beat, probe.interval);
	},
//	register : function(id) {
//		var search = location.search;
//		search = search + "&id=" +id;
//		location.search = search;
//	},
	/**
	 * 执行一个用例
	 * 
	 * @param href
	 */
	runtest : function(src) {
		if (!src) {
			if (probe.testframe) {
				$(probe.testframe.parentNode).remove();
				delete probe.testframe;
			}
			return;
		}
		if (!probe.testframe) {
			probe.testframe = document.createElement('iframe');
			var div = document.body.appendChild(document.createElement("div"));
			div.className = "runningarea";
			div.appendChild(probe.testframe);
		}
		///[?&]case=([^&]+)/.test(src) && (probe.kiss = RegExp.$1);
		$(probe).one('done', probe.beat);
		probe.starttime = new Date().getTime();
		probe.testframe.src = src;
	},
	stringify : (function() {
		/**
		 * 字符串处理时需要转义的字符表
		 * 
		 * @private
		 */
		var escapeMap = {
			"\b" : '\\b',
			"\t" : '\\t',
			"\n" : '\\n',
			"\f" : '\\f',
			"\r" : '\\r',
			'"' : '\\"',
			"\\" : '\\\\'
		};

		/**
		 * 字符串序列化
		 * 
		 * @private
		 */
		function encodeString(source) {
			if (/["\\\x00-\x1f]/.test(source)) {
				source = source.replace(/["\\\x00-\x1f]/g, function(match) {
					var c = escapeMap[match];
					if (c) {
						return c;
					}
					c = match.charCodeAt();
					return "\\u00" + Math.floor(c / 16).toString(16)
							+ (c % 16).toString(16);
				});
			}
			return '"' + source + '"';
		}

		/**
		 * 数组序列化
		 * 
		 * @private
		 */
		function encodeArray(source) {
			var result = [ "[" ], l = source.length, preComma, i, item;

			for (i = 0; i < l; i++) {
				item = source[i];

				switch (typeof item) {
				case "undefined":
				case "function":
				case "unknown":
					break;
				default:
					if (preComma) {
						result.push(',');
					}
					result.push(probe.stringify(item));
					preComma = 1;
				}
			}
			result.push("]");
			return result.join("");
		}

		/**
		 * 处理日期序列化时的补零
		 * 
		 * @private
		 */
		function pad(source) {
			return source < 10 ? '0' + source : source;
		}

		/**
		 * 日期序列化
		 * 
		 * @private
		 */
		function encodeDate(source) {
			return '"' + source.getFullYear() + "-"
					+ pad(source.getMonth() + 1) + "-" + pad(source.getDate())
					+ "T" + pad(source.getHours()) + ":"
					+ pad(source.getMinutes()) + ":" + pad(source.getSeconds())
					+ '"';
		}

		return function(value) {
			switch (typeof value) {
			case 'undefined':
				return 'undefined';

			case 'number':
				return isFinite(value) ? String(value) : "null";

			case 'string':
				return encodeString(value);

			case 'boolean':
				return String(value);

			default:
				if (value === null) {
					return 'null';
				} else if (value instanceof Array) {
					return encodeArray(value);
				} else if (value instanceof Date) {
					return encodeDate(value);
				} else {
					var result = [ '{' ], encode = probe.stringify, preComma, item;

					for ( var key in value) {
						if (Object.prototype.hasOwnProperty.call(value, key)) {
							item = value[key];
							switch (typeof item) {
							case 'undefined':
							case 'unknown':
							case 'function':
								break;
							default:
								if (preComma) {
									result.push(',');
								}
								preComma = 1;
								result.push(encode(key) + ':' + encode(item));
							}
						}
					}
					result.push('}');
					return result.join('');
				}
			}
		};
	})()
};
$(document).ready(probe.beat);
/**
 * 重载QUnit部分接口实现批量执行控制功能
 */
(function() {
	if (!QUnit)
		return;

	var ms = QUnit.moduleStart, d = QUnit.done;

	QUnit.moduleStart = function() {
		var h = setInterval(function() {
			if (window && window['baidu']) {
				clearInterval(h);
				ms.apply(this, arguments);
				start();
			}
		}, 20);
		stop();
	};

	QUnit.done = function() {
		if (top && top.probe) {
			top.$(top.probe).trigger('done', [ arguments, _cov() ]);
		}
		if (top && top.brtest) {
			top.$(top.brtest).trigger('done', arguments);
		}
		d.apply(this, arguments);
	};

	function _cov() {
		var cov = window._$jscoverage;
		if (cov != undefined) {
			for(var key in cov){
				delete cov[key]['source'];
			}
		}
		return cov;
	}
})();

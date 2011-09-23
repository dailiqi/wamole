var brtest = {
	init : function() {
		// $('div.testlist').bind('click', function(){
		// // brtest.paused = true;
		// });
		$('div.testlist a').bind('click', brtest.run);
	},
	_timeout : function() {
		brtest.timeouthandle = setTimeout(function() {
			$(brtest.cur).removeClass('running');
			$(brtest.cur).addClass('fail');
			$(brtest).trigger('done', brtest.next);
		}, 8000);
	},
	run : function(f) {
		if (brtest.paused)
			return;
		brtest._timeout();
		brtest.cur = f.currentTarget;
		$(brtest.cur).removeAttr('class');
		$(brtest.cur).addClass('running');
		if (brtest.testframe == null) {
			brtest.testframe = document.createElement('iframe');
			$('div.runningarea').empty().append(brtest.testframe);
		}
		$(brtest).one('done', brtest.next);
		brtest.testframe.src = 'run.do?case=' + brtest.cur.title;
	},
	next : function(e, data) {
		if ($(brtest.cur).attr('class') == 'running') {
			$(brtest.cur).removeClass('running');
			if (data) {
				$(brtest.cur).addClass('fail');
			} else {
				$(brtest.cur).addClass('pass');
			}
		} 
		if (location.search.indexOf("batchrun=true") == -1)
			return;
		brtest.timeouthandle && clearTimeout(brtest.timeouthandle);
		var n = brtest.cur.nextElementSibling;
		if (n && n.tagName == 'A') {
			if($(n).attr('class')) {
				return ;
			}
			setTimeout(function() {// 避免出现栈
				$(n).trigger('click');
			}, 0);
		}
	},
	cur : null,
	paused : false,
	testframe : null,
	view : {
		treeview : function() {

		},
		listview : function() {
			//
		}
	},
	filter : {

	}
};

// 在用例展示后提供点击支持
(function() {
	$(document).ready(brtest.init);
})();

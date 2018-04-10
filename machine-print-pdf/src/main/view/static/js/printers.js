(function() {
	/* 打印机名称列表 */
	$.ajax({
		type : "POST",
		url : "http://localhost:8080/print/printers",
		data : "{ version: 1}",
		dataType : "json",
		contentType : 'application/json;charset=utf-8',
		success : function(data, textStatus, jqXHR) {
			var dataName = "";
			data.forEach(function(item) {
				dataName += '<li>' + item + '</li>';
			})
			$('.machion')[0].innerHTML = dataName;
		}
	})

	$('.machion').on(
			'click',
			'li',
			function(items) {
				// items.stopPropagation();
				/* 查询打印机队列信息 */
				$.ajax({
					type : "POST",
					url : "http://localhost:8080/print/printerQueue",
					data : items.target.innerHTML,
					dataType : "json",
					contentType : 'application/json;charset=utf-8',
					success : function(data, textStatus, jqXHR) {
						var dataArray = "";
						for ( var key in data) {
							dataArray += '<li id=' + key + '>' + '<span>' + 'x'
									+ '</span>' + '<span>' + data[key]
									+ '</span>' + '</li>';
						}
						$('.navbody')[0].id = items.target.innerHTML;
						$('.navbody')[0].innerHTML = dataArray;
					}
				})
			});
	/* 删除打印任务 */
	$('.navbody').on(
			'click',
			'li',
			function(itemss) {
				var message = "删除任务:" + $(this)[0].innerText
				var like = window.confirm(message);
				if (like) {
					$(this).remove();
					var uuid = $(this)[0].id;
					var printerName = $('.navbody')[0].id || '';
					if (printerName.length == 0) {
						return;
					}
					var jsondataTemp = '{uuid:"' + uuid + '",printerName:"'
							+ printerName + '"}';
					$.ajax({
						type : "POST",
						url : "http://localhost:8080/print/removeQueueCode",
						data : printerName,
						dataType : "json",
						contentType : 'application/json;charset=utf-8',
						success : function(data, textStatus, jqXHR) {
						}
					})
				} else {
					return;
				}
			});
})()
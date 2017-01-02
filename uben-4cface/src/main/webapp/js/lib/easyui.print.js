function grid_export(url,datagrid){
	var dg = datagrid.datagrid('options');
	var params = dg.queryParams ;
	// console.log("dg::"+JSON.stringify(dg));
	// console.log("params:"+JSON.stringify(params));
	var sortName = dg.sortName ;
	var remoteSort = dg.remoteSort ;
	var sortOrder = dg.sortOrder ;
	// console.log("params:"+JSON.stringify(params));
	
	var p = datagrid.datagrid('getPager');
	var po = $(p).pagination('options');
	// console.log(JSON.stringify(po));
	var total = po.total ;
	
	var ht = "<form id='exportForm' action='"+url+"' method='POST' target='_blank'>" ;
	ht+="<input type='hidden' name='queryParam' value='"+JSON.stringify(params)+"' />" ;
	ht+="<input type='hidden' name='total' value='"+total+"' />" ;
	ht+="<input type='hidden' name='sortName' value='"+sortName+"' />" ;
	ht+="<input type='hidden' name='remoteSort' value='"+remoteSort+"' />" ;
	ht+="<input type='hidden' name='sortOrder' value='"+sortOrder+"' />" ;
	ht+="</form>" ;
	$(document.body).append(ht);
	$('#exportForm').action = url ;
	// console.log(url);
	// console.log("form::"+JSON.stringify($('#exportForm').action));
	$('#exportForm').submit();
	$('#exportForm').remove();
}
function grid_print(datagrid){
	var dg = datagrid.datagrid('options');
	var columns = new Array() ;
	var dgcs = dg.columns ;
	console.log(JSON.stringify(dg));
	$(dgcs[0]).each(function (index) {
		if(!dgcs[0][index].hidden && !dgcs[0][index].checkbox && dgcs[0][index].field!='action') 
		columns.push(new Column(dgcs[0][index].field,dgcs[0][index].title,dgcs[0][index].width,dgcs[0][index].editor)) ;
	});
	var data = new Object() ;
	data['sortName'] = dg.sortName ;
	data['sortOrder'] = dg.sortOrder ;
	data['url']  = dg.url ;
	data['method']  = dg.method ;
	data['idField'] = dg.idField ;
	data['queryParams'] = dg.queryParams ;
	
	var obj = new Object() ;
	obj['grid-def-data'] = data ;
	obj['grid-col-data'] = columns ;
	
	var p = datagrid.datagrid('getPager');
	var po = $(p).pagination('options');
	var total = po.total ;
	obj['grid-page-data'] = total ;
	
	var ht = "<form id='autoForm' method='POST' action='/ctl/commonExport/print' target='autoWindow' onsubmit='openSpecfiyWindow(\"autoWindow\")'>" ;
	ht+="<input type='hidden' name='data' value='"+JSON.stringify(obj)+"' />" ;
	ht+="</form>" ;
	$(document.body).append(ht);
	//$('#autoForm').action
	$('#autoForm').submit();
}
function openSpecfiyWindow(windowName){
	window.open('about:blank',windowName,'width=800,height=600'); 
}
function Column(name,label,width,editor){
	this.name = name ;
	this.label = label ;
	this.width = width ;
	this.editor = editor ;
}
/**
 * Created by xsj on 16/6/27.
 */
JSON.stringify = JSON.stringify || function (obj) {
    var t = typeof (obj);
    if (t != "object" || obj === null) {
        // simple data type
        if (t == "string") obj = '"'+obj+'"';
        return String(obj);
    }
    else {
        // recurse array or object
        var n, v, json = [], arr = (obj && obj.constructor == Array);
        for (n in obj) {
            v = obj[n]; t = typeof(v);
            if (t == "string") v = '"'+v+'"';
            else if (t == "object" && v !== null) v = JSON.stringify(v);
            json.push((arr ? "" : '"' + n + '":') + String(v));
        }
        return (arr ? "[" : "{") + String(json) + (arr ? "]" : "}");
    }
};

$(function(){
    sortHtml();
    // 显示更多搜索选项
    $("#customSearch").click(function(){
        $(".customSearch-box").toggle();
    });


    // 取出cookie
    var cookieName = $(".searchInputItems").data("cookiename");
    // alert(cookieName);
    var sortObj = {}; // 可用和不可用排序
    var inputsObj = {}; // 搜索输入框obj
    var lisObj = {};
    $(".searchInputItems .searchItem").each(function(){
        var name = $(this).find('input').data('name');
        var title = $(this).find('strong').text();
        var li = '<li data-name="'+name+'" class="ui-state-default dd-item"><div class="dd-handle">' +title+ '</div></li>';
        lisObj[name] = $(li);
        inputsObj[name] = $(this);
    });
    if($.cookie(cookieName)){
        sortObj = JSON.parse($.cookie(cookieName));
        // 可用
        for(var i=0;i<sortObj.show.length;i++){
            var k = sortObj.show[i];
            $("#sortable1 ol").append(lisObj[k]);
            // 搜索input
            $(".searchInputs").append(inputsObj[k]);
        }
        // 不可用
        for(var i=0;i<sortObj.hidding.length;i++){
            var k = sortObj.hidding[i];
            $("#sortable2 ol").append(lisObj[k]);
        }

        // 一边没有处理
        var emptyDiv =  '<div class="dd-empty"></div>';
        if(sortObj.show.length<1){
            $("#sortable1").append(emptyDiv);
        }
        if(sortObj.hidding.length<1){
            $("#sortable2").append(emptyDiv);
        }

    } else { //没得cookie
        // 没有选项时
        var emptyDiv =  '<div class="dd-empty"></div>';
        $("#sortable1").append(emptyDiv);
        $(".searchInputItems .searchItem").each(function(){
            var name =  $(this).find('input').data('name');
            var title = $(this).find('strong').text();
            //console.log(name);
            var li = '<li data-name="'+name+'" class="ui-state-default dd-item"><div class="dd-handle">' +title+ '</div></li>';
            $("#sortable2 ol").append(li);
        });
    }
    // 设置可拖动
    $('.customSearch-box #sortable1').nestable({
        maxDepth:1,
        group: 1
    }).on('change', stopSort);
    $('.customSearch-box #sortable2').nestable({
        group: 1,
        maxDepth:1,
    }).on('change', stopSort);
    // 结束拖动及排序
    function stopSort(){
        var sortObj = {show:[],hidding:[]};
        $("#sortable1 li").each(function(){
            sortObj.show.push($(this).data('name'));
        });
        $("#sortable2 li").each(function(){
            sortObj.hidding.push($(this).data('name'));
        });
        $.cookie(cookieName, JSON.stringify(sortObj), { expires: 300, path: '/' });
    }

    // 确认按钮
    $("#searchBtnOk").click(function(){
        $(".customSearch-box").slideUp();
        //$(".searchInputs span.searchItem").remove();
        // 可用
        // alert($.cookie(cookieName));
        sortObj = JSON.parse($.cookie(cookieName));

        for(var i=0;i<sortObj.show.length;i++){
            var k = sortObj.show[i];
            // 搜索input
            var inputObj = inputsObj[k];
            $(".searchInputs").append(inputObj);
        }
        setupTBHeight();
    });

    // 设置高度
    function setupTBHeight(){
        var topTbH = $(".topToolbar form").height();
        if(topTbH>26){
            $('.topToolbar').panel('resize',{height: 65});
            $('.dataContent').panel('move',{top:65});
        }else{
            $('.topToolbar').panel('resize',{height: 35});
            $('.dataContent').panel('move',{top:35});
        }

    }
    $(window).resize(function(e){
        setupTBHeight();
    });
    window.onload = function(){
        setTimeout(function(){
            setupTBHeight();
        },100);
    }
});

// 排序html
function sortHtml(){
    var html =
        '<div class="customSearch-box" style="display: none;">'
        +'<div class="customSearch-box-Content cf nestable-lists">'
        +'<div class="dd" id="sortable1" style="border:2px dotted green; margin:5px; padding:5px;">'
        +'<span style="display: block; text-align: center; border-bottom:0px solid green;">显示选项</span>'
        +'<ol class="dd-list connectedSortable">'
        +'</ol>'
        +'</div>'
        +'<div class="dd" id="sortable2" style="border:2px dotted #ccc; margin:5px; padding:5px;">'
        +'<span style="display: block; text-align: center; border-bottom:0px solid green;">隐藏选项</span>'
        +'<ol class="dd-list connectedSortable">'
        +'</ol>'
        +'</div>'
        +'</div>'
        +'<div class="search-head" style="text-align: right;">'
        +'<a href="javascript:;" id="searchBtnOk" class="easyui-linkbutton l-btn l-btn-small" style="margin-top:5px; padding:0 12px;">确 定</a>'
        +'</div>'
        +'</div>';
    $('body').append(html);
}
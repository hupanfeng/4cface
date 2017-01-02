$(document).ready(function () {
    $('body').css('opacity', '1');
});
$(document).keypress(function (e) {
    if (e.which == 13) {
        ubenSearch();
    }
});
$(window).load(function () {
    $("#loading").fadeOut();
});
$(function () {
    $("#fontSizeChange").change(function () {
        var p1 = $(this).children('option:selected').val();//这就是selected的值
        $.cookie('fontSize', p1, {expires: 3000, path: "/"});
        location.reload();
    });
    if ($.cookie('fontSize')) {
        $("#fontSizeChange").val($.cookie('fontSize'));
    }
    // 取出cookie保存的字体大小
    if ($.cookie('fontSize') != undefined) {
        $('body').addClass($.cookie('fontSize'));
    }
});
/**
 * 弹出对话框可使用 esc 关闭
 */
$(document).bind('keyup', function (e) {
    if (e.keyCode == 27) {
        //parent.$.modalDialog.handler.dialog('close');
    }
});
/**
 * 弹出对话框可使点击外部区域关闭
 */
$(document).on('click', '.window-mask', function () {
    //parent.$.modalDialog.handler.dialog('close');
});
/**
 *
 * 防止退格键导致页面回退
 */
$(document).keydown(function (e) {
    var doPrevent;
    if (e.keyCode == 8) {
        var d = e.srcElement || e.target;
        if (d.tagName.toUpperCase() == 'INPUT' || d.tagName.toUpperCase() == 'TEXTAREA') {
            doPrevent = d.readOnly || d.disabled;
        } else {
            doPrevent = true;
        }
    } else {
        doPrevent = false;
    }
    if (doPrevent)
        e.preventDefault();
});


/**显示状态*/
function showState(value, row, index) {
    if (value == "-1") {
        return "锁定";
    } else if (value == "-2") {
        return "未审核";
    }

    return Dic.showName(Dic.type.stateId, value);
}

/**只显示yyyy-mm-dd格式的日期，不显示时分秒*/
function showDate(value, row, index) {
    if (value != null) {
        return value.substring(0, 11);
    }
}

function pubAction(option) {
    var html = '&nbsp;';
    var count = 0;
    for (var key in option) {
        count = count + 1;
        var data = option[key];
        if (count > 1) {
            html += '&nbsp;&nbsp;|&nbsp;&nbsp;';
        }
        html += $.formatString('<a href="javascript:void(0)" onclick="{0}(\'{1}\',\'{2}\');" >{3}</a>', data["func"], data["paramid"], data["paramtype"], data["name"]);
    }
    return html;
}





//格式化单元格提示信息
function formatCellTooltip(value) {
    return "<span title='" + value + "'>" + value + "</span>";
}
function path(removeContext) {
    var path = window.location.pathname.substring(0, window.location.pathname.lastIndexOf("/") + 1);
    if (removeContext && store.get("context")) {
        path = path.substring(store.get("context").length);
    }
    return path;
}
/**
 * 权限判断
 * @param url
 * @returns {boolean}
 */
function isPermit(url) {
    url = path(true) + url;
    var hasPermission = false;
    store.get('resources').forEach(function (it) {
        if (it.url == url) {
            hasPermission = true;
            return hasPermission;
        }
    });
    return hasPermission;
}
/**
 * 弹窗公共方法
 * @param url 弹窗页面地址
 * @param title 弹窗标题
 * @param width 弹窗宽度
 * @param height 弹窗高度
 * @param saveButton 弹窗页面保存按钮对象
 * @param closeButton  弹窗页面关闭按钮对象
 * @param data  传递到弹窗的数据
 */
function pop(url, title, width, height, saveButton, data) {
    url = path() + url;
    if (!width) {
        width = 750;
    }
    if (!height) {
        height = 500;
    }
    var option = {
        width: width,
        height: height,
        href: url,
        title: title,
        resizable: true,
        maximizable: true,
        buttons: [{
            text: '关闭',
            iconCls: 'icon-cancel',
            handler: function () {
                parent.$.modalDialog.handler.dialog('close');
            }
        }]
    }
    if (saveButton) {
        option.buttons.unshift(saveButton);
    }
    parent.$.modalDialog.data = null;
    var dataLoaded = undefined;
    //parent.$.modalDialog.url = url;
    if (!data) {
        var search = url.indexOf("?") != -1 ? url.split("?")[1] : undefined;
        if (search) {
            var params = {};
            search.split("&").forEach(function (it) {
                params[it.split("=")[0]] = it.split("=")[1];
            });
            var readonly = (params.mode && params.mode == "editable") ? false : true;
            $.ajax({
                type: "get",
                url: path() + params.mid,
                cache: false,
                async: false,
                dataType: "json",
                success: function (result) {
                    parent.$.modalDialog.data = result;
                    parent.$.modalDialog.readonly = readonly;
                    dataLoaded = true;
                },
                error: function (xhr) {
                    location.href = store.get("context") + "/error/" + xhr.status + ".html";
                    dataLoaded = false;
                }
            });
        } else {
            dataLoaded = true;
        }
    } else {
        parent.$.modalDialog.data = data;
        dataLoaded = true;
    }
    if (dataLoaded) {
        parent.$.modalDialog.path = path();
        parent.$.modalDialog.openner_dataGrid = $('#dataGrid');
        parent.$.modalDialog(option);
    }

}
/**
 * 通过弹窗的方式新增数据,弹窗关闭后自动刷新当前页的数据区
 * @param url 弹窗加载的页面地址,必填
 * @param title 弹窗标题,必填
 * @param width 弹窗宽度,选填
 * @param height 弹窗高度,选填
 */
function add(url, title, width, height) {
    if (!title) {
        title = "新增"
    } else {
        title = "新增" + title;
    }
    var saveButton = {
        text: '保存',
        iconCls: 'icon-ok',
        handler: function () {
            var f = parent.$.modalDialog.handler.find('#form');
            f.submit();
        }
    };
    parent.$.modalDialog.refresh = refresh;
    pop(url, title, width, height, saveButton);
}

/**
 * 通过弹窗的方式编辑数据,弹窗关闭后自动刷新当前页的数据区
 * @param mid 待编辑数据的ID,必填
 * @param url 弹窗加载的页面地址,必填
 * @param title 弹窗标题,必填
 * @param width 弹窗宽度,选填
 * @param height 弹窗高度,选填
 */
function edit(mid, url, title, width, height) {
    if (!mid) {
        var rows = $('#dataGrid').datagrid('getSelections');
        if (rows == '') {
            parent.$.messager.alert('提示', '请选择您要编辑的项', 'info');
            return;
        }
        mid = rows[0].mid;
    }
    url = url + "?mode=editable&mid=" + mid;
    if (!title) {
        title = "编辑"
    } else {
        title = "编辑" + title;
    }
    var saveButton = {
        text: '保存',
        iconCls: 'icon-ok',
        handler: function () {
            var f = parent.$.modalDialog.handler.find('#form');
            f.submit();
        }
    };
    parent.$.modalDialog.refresh = refresh;
    pop(url, title, width, height, saveButton);
}
/**
 * 通过弹窗的方式查看数据,弹窗关闭后自动刷新当前页的数据区
 * @param mid 待查看数据的ID,必填
 * @param url 弹窗加载的页面地址,必填
 * @param title 弹窗标题,必填
 * @param width 弹窗宽度,选填
 * @param height 弹窗高度,选填
 */
function view(mid, url, title, width, height) {
    if (!mid) {
        var rows = $('#dataGrid').datagrid('getSelections');
        if (rows == '') {
            parent.$.messager.alert('提示', '请选择您要查看的项', 'info');
            return;
        }
        mid = rows[0].mid;
    }
    url = url + "?mid=" + mid;
    if (!title) {
        title = "查看"
    } else {
        title = "查看" + title;
    }
    pop(url, title, width, height);
}
/**
 * 删除某个项,删除后自动刷新当前页的数据区
 * @param mid 待编辑数据的ID,必填
 * @param url 弹窗加载的页面地址,必填
 */
function del(mid, url) {
    if (!mid) {
        var rows = $('#dataGrid').datagrid('getSelections');
        if (rows == '') {
            parent.$.messager.alert('提示', '请选择您要删除的项', 'info');
            return;
        }
        mid = rows[0].mid;
    }
    parent.$.messager.confirm('询问', '您是否要删除当前项？', function (b) {
        if (b) {
            progressLoad();
            $.post(
                url,
                {mid: mid},
                function (result) {
                    if (result) {
                        parent.$.messager.alert('提示', result.msg, 'info');
                        //$('#dataGrid').datagrid('reload');
                        refresh();
                    }
                    progressClose();
                },
                'JSON'
            );
        }
    });
}
/**
 * 打印
 */
function print() {
    grid_print($('#dataGrid'));
}
/**
 * 导出
 */
function exp() {
    var path = window.location.pathname.substring(0, window.location.pathname.lastIndexOf("/") + 1);
    grid_export(path + "data2pdf", $('#dataGrid'));
}
/**
 * 导入
 * @param title
 * @param width
 * @param height
 */
function imp(title, width, height) {
    if (!title) {
        title = "导入"
    } else {
        title = "导入" + title;
    }

    var saveButton = {
        text: '保存',
        iconCls: 'icon-ok',
        handler: function () {
            var f = parent.$.modalDialog.handler.find('#uploadForm');
            f.submit();
        }
    };
    pop("upload", title, width, height, saveButton);
}
function ubenSearch() {
    $('#dataGrid').datagrid('load', $.serializeObject($('#searchForm')));
}
function ubenClean() {
    $("#searchForm").form('clear');
    $('#dataGrid').datagrid('load', {});
}

/**
 * 渲染详情或编辑页面
 */
function render(callBack) {
    var method = "add";
    var data = parent.$.modalDialog.data;
    // 详情页面只读标志
    var readonly = parent.$.modalDialog.readonly;
    if (data) {
        if (readonly) {
            var plugins = ['draggable', 'droppable', 'resizable', 'pagination', 'tooltip',
                'linkbutton', 'menu', 'menubutton', 'splitbutton', 'switchbutton', 'progressbar',
                'tree', 'textbox', 'filebox', 'combo', 'combobox', 'combotree', 'combogrid', 'numberbox', 'validatebox', 'searchbox',
                'spinner', 'numberspinner', 'timespinner', 'datetimespinner', 'calendar', 'datebox', 'datetimebox', 'slider',
                'layout', 'panel', 'datagrid', 'propertygrid', 'treegrid', 'datalist', 'tabs', 'accordion', 'window', 'dialog', 'form'
            ];
            for (var i = 0; i < plugins.length; i++) {
                var className = plugins[i];
                $('#form').find(".easyui-" + className).each(function () {
                    $(this).attr("readonly", "readonly");
                });
            }
        }
        $("#form").form("load", data);
        method = "edit";
    }
    if (callBack) {
        callBack(data, method);
    }
}
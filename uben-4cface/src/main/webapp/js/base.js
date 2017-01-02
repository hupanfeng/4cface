function showState(value, row, index) {
    // if (value == "1") {
    //     return "正常";
    // }
    // return "停用";
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

!(function (global) {
    var Dic = function () {
        var self = this;
        if (!(this instanceof Dic)) {
            global.console && global.console.warn && global.console.warn('usage: new Dic();');
            return new Dic();
        }
        self.type = $.extend({}, Dic.type);
        return self;
    };
    Dic.data = {"22":[{"code":"1","createTime":1464957557000,"createUserid":1,"mid":119,"name":"是","stateId":1,"typeId":22},{"code":"0","createTime":1464957564000,"createUserid":1,"mid":120,"name":"否","stateId":1,"typeId":22}],"1":[{"code":"0","createTime":1448162233000,"createUserid":1,"mid":1,"name":"停用","stateId":1,"typeId":1},{"code":"1","createTime":1416468074000,"createUserid":1,"mid":2,"name":"启用","stateId":1,"typeId":1}],"2":[{"code":"1","createTime":1439449219000,"createUserid":1,"mid":3,"name":"办公类型","stateId":1,"typeId":2}],"35":[{"code":"1","createTime":1468723287000,"createUserid":1,"mid":164,"name":"一季度","stateId":1,"typeId":35},{"code":"2","createTime":1468723304000,"createUserid":1,"mid":165,"name":"二季度","stateId":1,"typeId":35},{"code":"3","createTime":1476080454000,"createUserid":1,"mid":178,"name":"三季度","stateId":1,"typeId":35},{"code":"4","createTime":1476080479000,"createUserid":1,"mid":179,"name":"四季度","stateId":1,"typeId":35}],"3":[{"code":"0","createTime":1461898460000,"createUserid":1,"mid":23,"name":"不同意","stateId":1,"typeId":3},{"code":"1","createTime":1461898414000,"createUserid":1,"mid":24,"name":"同意","stateId":1,"typeId":3}],"15":[{"code":"1","createTime":1463971975000,"createUserid":1,"mid":79,"name":"一月","stateId":1,"typeId":15},{"code":"2","createTime":1463971993000,"createUserid":1,"mid":80,"name":"二月","stateId":1,"typeId":15},{"code":"3","createTime":1463972006000,"createUserid":1,"mid":81,"name":"三月","stateId":1,"typeId":15},{"code":"4","createTime":1463972018000,"createUserid":1,"mid":82,"name":"四月","stateId":1,"typeId":15},{"code":"5","createTime":1463972033000,"createUserid":1,"mid":83,"name":"五月","stateId":1,"typeId":15},{"code":"6","createTime":1463972539000,"createUserid":1,"mid":84,"name":"六月","stateId":1,"typeId":15},{"code":"7","createTime":1463972548000,"createUserid":1,"mid":85,"name":"七月","stateId":1,"typeId":15},{"code":"8","createTime":1463972561000,"createUserid":1,"mid":86,"name":"八月","stateId":1,"typeId":15},{"code":"9","createTime":1463972572000,"createUserid":1,"mid":87,"name":"九月","stateId":1,"typeId":15},{"code":"10","createTime":1463972599000,"createUserid":1,"mid":88,"name":"十月","stateId":1,"typeId":15},{"code":"11","createTime":1463972616000,"createUserid":1,"mid":89,"name":"十一月","stateId":1,"typeId":15},{"code":"12","createTime":1463991781000,"createUserid":1,"mid":90,"name":"十二月","stateId":1,"typeId":15}],"4":[{"code":"1","createTime":1471596506000,"createUserid":1,"mid":168,"name":"每周","stateId":1,"typeId":4},{"code":"2","createTime":1471596525000,"createUserid":1,"mid":169,"name":"每天","stateId":1,"typeId":4}],"5":[{"code":"1","createTime":1471599795000,"createUserid":1,"mid":25,"name":"星期一","stateId":1,"typeId":5},{"code":"2","createTime":1471599820000,"createUserid":1,"mid":26,"name":"星期二","stateId":1,"typeId":5},{"code":"3","createTime":1471599833000,"createUserid":1,"mid":27,"name":"星期三","stateId":1,"typeId":5},{"code":"4","createTime":1471599850000,"createUserid":1,"mid":28,"name":"星期四","stateId":1,"typeId":5},{"code":"5","createTime":1471599885000,"createUserid":1,"mid":29,"name":"星期五","stateId":1,"typeId":5}],"28":[{"code":"1","createTime":1471510277000,"createUserid":1,"mid":91,"name":"位置图片","stateId":1,"typeId":28},{"code":"2","createTime":1471510292000,"createUserid":1,"mid":92,"name":"室内图片","stateId":1,"typeId":28},{"code":"1","createTime":1476438430000,"createUserid":1,"mid":235,"name":"1","stateId":1,"typeId":28,"typeName":"会议室图片类型"}],"6":[{"code":"08:30","createTime":1472003452000,"createUserid":1,"mid":170,"name":"开始","stateId":1,"typeId":6},{"code":"18:00","createTime":1471937178000,"createUserid":1,"mid":171,"name":"结束","stateId":1,"typeId":6}],"20":[{"code":"meetingProcess","createTime":1471590630000,"createUserid":1,"mid":105,"name":"会议室申请","stateId":1,"typeId":20}],"21":[{"code":"0","createTime":1464945826000,"createUserid":1,"mid":110,"name":"文本","stateId":1,"typeId":21},{"code":"1","createTime":1464945837000,"createUserid":1,"mid":111,"name":"长文本","stateId":1,"typeId":21},{"code":"2","createTime":1464945847000,"createUserid":1,"mid":112,"name":"单选框","stateId":1,"typeId":21},{"code":"3","createTime":1464945858000,"createUserid":1,"mid":113,"name":"多选框","stateId":1,"typeId":21},{"code":"4","createTime":1464945869000,"createUserid":1,"mid":114,"name":"日期选择","stateId":1,"typeId":21},{"code":"5","createTime":1464945892000,"createUserid":1,"mid":115,"name":"时间选择","stateId":1,"typeId":21},{"code":"6","createTime":1464945905000,"createUserid":1,"mid":116,"name":"单个复选框","stateId":1,"typeId":21},{"code":"98","createTime":1465007759000,"createUserid":1,"mid":117,"name":"模块信息","stateId":1,"typeId":21},{"code":"99","createTime":1464945932000,"createUserid":1,"mid":118,"name":"按钮","stateId":1,"typeId":21}]};
    Dic.type = {
        stateId: "1"
    };

    Dic.prototype = {
        combox: function (id, type, required) {
            if (undefined == required) {
                required = true;
            }
            $("#" + id).combobox({
                data: Dic.data[type],
                valueField: 'code',
                textField: 'name',
                editable: false,
                //panelHeight: 'auto',
                minWidth: 145,
                required: required
            })
        },
        showName: function (type, value) {
            for (var i = 0; i < Dic.data[type].length; i++) {
                var jsonobj = Dic.data[type][i];
                if (jsonobj.code == value) {
                    return jsonobj.name;
                }
            }
            return value;
        }
    };
    global.Dic = new Dic();

})(window);

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
Dic.data = ${data};
Dic.type = {
        stateId: "4",
        currentState: "5",
        account: "6",
        meterageType: "7",
        techState: "8",
        verifyMode: "9",
        verifyCycle: "10",
        verifyType: "11",
        toleranceType: "12",
        blongType: "16",
        verifyResult: "17",
        unit: "14",
        storeNo: "18",
        trueFalse: '22',
        moneySrc: '23',
        devCategory: '24',
        category: '27',
        devRunType: '26',
        devDocType: '28',
        readiness: '29'
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


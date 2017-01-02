var aCity={11:"北京",12:"天津",13:"河北",14:"山西",15:"内蒙古",21:"辽宁",22:"吉林",23:"黑龙江",31:"上海",32:"江苏",33:"浙江",34:"安徽",35:"福建",36:"江西",37:"山东",41:"河南",42:"湖北",43:"湖南",44:"广东",45:"广西",46:"海南",50:"重庆",51:"四川",52:"贵州",53:"云南",54:"西藏",61:"陕西",62:"甘肃",63:"青海",64:"宁夏",65:"新疆",71:"台湾",81:"香港",82:"澳门",91:"国外"}
function isCardID(sId){
    var iSum=0 ;
    var info="" ;
    if(!/^\d{17}(\d|x)$/i.test(sId)) return "你输入的身份证长度或格式错误";
    sId=sId.replace(/x$/i,"a");
    if(aCity[parseInt(sId.substr(0,2))]==null) return "你的身份证地区非法";
    sBirthday=sId.substr(6,4)+"-"+Number(sId.substr(10,2))+"-"+Number(sId.substr(12,2));
    var d=new Date(sBirthday.replace(/-/g,"/")) ;
    if(sBirthday!=(d.getFullYear()+"-"+ (d.getMonth()+1) + "-" + d.getDate()))return "身份证上的出生日期非法";
    for(var i = 17;i>=0;i --) iSum += (Math.pow(2,i) % 11) * parseInt(sId.charAt(17 - i),11) ;
    if(iSum%11!=1) return "你输入的身份证号非法";
    return true;
}
$.extend($.fn.validatebox.defaults.rules, {
    //验证汉子
    CHS: {
        validator: function (value) {
            return /^[\u0391-\uFFE5]+$/.test(value);
        },
        message: '只能输入汉字'
    },
    idcard: {
        validator: function(value,param){
            var flag= isCardID(value);
            return flag==true?true:false;
        },
        message: '不是有效的身份证号码'
    },
    phone: {
        validator: function(value){
        var rex=/^1[3-8]+\d{9}$/;
        var rex2=/^((0\d{2,3})-)(\d{7,8})(-(\d{3,}))?$/;
        if(rex.test(value)||rex2.test(value))
        {
          // alert('t'+value);
          return true;
        }else
        {
         //alert('false '+value);
           return false;
        }
          
        },
        message: '请输入正确电话或手机格式'
      },
    mobile: {
        validator: function (value) {
            var reg = /^1[3|4|5|8|9]\d{9}$/;
            return reg.test(value);
        },
        message: '输入手机号码格式不准确.'
    },
    dateAfterNow: {
		validator: function(value){
			var d1 = $.fn.datebox.defaults.parser(value) ;
			var d2 = new Date() ;
			d2.setHours(0);
			d2.setMinutes(0, 0, 0) ;
			return d2<=d1;
		},
		message: '{0}不能小于当前日期'
	},
    timeAfterNow: {
		validator: function(value){
			value = value.replace(/-/g,"/");
			var d1 = new Date(value) ;
			var d2 = new Date() ;
			return d2.getTime()<=d1.getTime();
		},
		message: '{0}不能小于当前时间'
	},
	timeAfter: {
		validator: function(value,param){
			var d1 = new Date(value) ;
			var d2= $.fn.datebox.defaults.parser($(param[0]).datebox('getValue'));
			return d2<=d1;
		},
		message: '{1}时间必须晚于{2}'
    },
	dateAfter: {
		validator: function(value,param){
			var d1 = $.fn.datebox.defaults.parser(value) ;
			console.log(d1.toString());
			console.log("p:::"+param[0]);
			var d2= $.fn.datebox.defaults.parser($(param[0]).datebox('getValue'));
			return d2<=d1;
		},
		message: '{1}日期必须晚于{2}'
    },
    //国内邮编验证
    zipcode: {
        validator: function (value) {
            var reg = /^[1-9]\d{5}$/;
            return reg.test(value);
        },
        message: '邮编必须是非0开始的6位数字.'
    },
    //用户账号验证(只能包括 _ 数字 字母) 
    account: {//param的值为[]中值
        validator: function (value, param) {
            if (value.length < param[0] || value.length > param[1]) {
                $.fn.validatebox.defaults.rules.account.message = '用户名长度必须在' + param[0] + '至' + param[1] + '范围';
                return false;
            } else {
                if (!/^[\w]+$/.test(value)) {
                    $.fn.validatebox.defaults.rules.account.message = '用户名只能数字、字母、下划线组成.';
                    return false;
                } else {
                    return true;
                }
            }
        }, message: ''
    }
})
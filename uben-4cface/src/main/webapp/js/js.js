window.onload = function(){
		/***/
	
		$('.wx_guanz').click(function(){
			$('.ewm_info').toggle();
			$('#profile_arrow').toggle();
		});
		
		
	/**分页**/


		// kkpager.generPageHtml({
		// 	total : totalPage,
		// 	totalRecords : totalRecords,
		// 	mode : 'click',//默认值是link，可选link或者click
		// 	click : function(currentPage){
		// 		this.selectPage(currentPage);
		// 		$("#con_main_list").html('');
		// 		// ajax();
		// 		return false;
		// 	}
		// });

        // //ajax  函数
		// function ajax(){
		// 		$.ajax({
		// 			type:"get",
		// 			url:'./ajax.php?key=' + key + 'category=' + category + 'total=' + pageSize + 'page=' + currentPage,
		// 			dataType:"json",
		// 			async:true,
		// 			success:function(msg){
		// 						for(var i = 0; i < msg.rows.length; i++) {
		// 							var oli = '<li><h2><a href="#">' + msg.rows[i].title + '</a></h2>';
		// 							oli += '<span class="fl con_main_img"><img src="images/img1.jpg" alt="" /></span>';
		// 							oli += '<div class="fl con_main_text">'
		// 							oli += '<font class="con_main_zy">' + msg.rows[i].summary + '</font>'
		// 							oli += '<p class="con_main_icon clr">'
		// 							oli += '<a href="#"><img src="images/sc.jpg" alt="" />收藏</a>'
		// 							oli += '<a href="#"><img src="images/fx.jpg" alt="" />分享</a>'
		// 							oli += '<a href="#"><img src="images/pj.png" alt="" />评价</a>'
		// 							oli += '</p>'
		// 							oli += '</div>'
		// 							oli += '</li>'
		// 							$("#con_main_list").append(oli);
		//
		// 							}
		//
		// 				}
		// 		});
		//
		//
		// }
		
		
		//当搜索框信息改变的时候
		// $("#form_text").keyup(function(){
		//    	$("#con_main_list").html('');
		//    	key=$(this).val();
		//    	currentPage=1;
		// 	ajax();
		// })
		
		//当前项目点击的时候
		// $(".con_tree_list li>a").click(function(){
         //    $("#con_main_list").html('');
         //    category=$(this).val();
         //    currentPage=1;
		// 	ajax();
		// })
		
		//console.log(category)	
		
		
		
	
	
}
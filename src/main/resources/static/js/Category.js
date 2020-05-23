

$(function(){
    $("#parent").on("change", function(){
    	postDaiCate();
    });
    
    

function postDaiCate() {
	const chyCategory=document.form1.chuCategory
	// window.location.href = selected.value;
	const id = chuCategory.selectedIndex;
	document.getElementById(id);
		form.submit();
}

/**$(function() {
	$('#parent').on("change", function() {
		var hostUrl = 'http://localhost:8080/categoryCheck/';//どのクラスのどのメソッドなのかを指定
		var parent = $("parent").val(); //idと結びついている
		$.ajax({
			url : hostUrl,　//三行目
			type : 'POST',
			dataType : 'json',//配列形式にする
			data : {
				parentId : parent　　　　//引数の中身を指定？（イメージはkey,value)keyは自由に設定
			},
			async: true // 非同期で処理を行う（asyncの部分が非同期的な意味合い）
		}).done(function(data) {//dateの部分にmap(APIコントローラーでreturnされたmap)が入る
			// コンソールに取得データを表示
			console.log(data);
			console.log(JSON.stringify(data));
			var str = ''
			for cate in data.categoryList {
			  str = '<option value="' + categ.id + '">' + cate.nae + '</option>';
			}
			$("#chyCategory").html(str);　//jqueryをつかって、htmlの<div id="duplicateMessage">部分にhtml形式で、date(map)の中に入っているduplicateMessageを取得してきて入れる。
																//この部分を変更していく（今回はlistに入れていくイメージ）
		}).fail(function(XMLHttpRequest, textStatus, errorThrown) {
			alert("エラーが発生しました！");
			console.log("XMLHttpRequest : " + XMLHttpRequest.status);
			console.log("textStatus     : " + textStatus);
			console.log("errorThrown    : " + errorThrown.message);
		});
	});
});**/
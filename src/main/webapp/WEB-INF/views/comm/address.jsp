<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<div id="layer" style=" display:none;border:5px solid;position:fixed;width:400px;height:500px;left:50%;margin-left:-155px;top:50%;margin-top:-235px;overflow:hidden;-webkit-overflow-scrolling:touch;z-index:999;">
<img src="//i1.daumcdn.net/localimg/localimages/07/postcode/320/close.png" id="btnCloseLayer" style="cursor:pointer;position:absolute;right:-3px;top:-3px;z-index:1" onclick="closeDaumPostcode()" alt="">
</div>

<script src="https://ssl.daumcdn.net/dmaps/map_js_init/postcode.v2.js"></script>
<script>

var element = document.getElementById('layer');

function newaddressOpen2(target, ZIPCD, ADDR1, ADDR2, old_new_addrgubun) {
	
 	document.getElementById("target_address").value = target ;
	
	new daum.Postcode({
        oncomplete: function(data) {
        	if(data.userSelectedType == "R") {
        		//도로명 클릭 시 신주소(old_new_addrgubun = NEW)로 세팅
        		document.getElementById(ZIPCD).value = data.zonecode;
        		
        		var road = "";
        		var autoroad = "";
        		
        		if(data.userLanguageType == "K") {
        			//한글일 때 한글주소 반환
        			road = data.roadAddress.replace(/(\s|^)\(.+\)$|\S+~\S+/g, '');
                    autoroad = data.autoRoadAddress.replace(/(\s|^)\(.+\)$|\S+~\S+/g, '');
        		}else {
        			//영어일 때 영어주소 반환
        			road = data.roadAddressEnglish.replace(/(\s|^)\(.+\)$|\S+~\S+/g, '');
                    autoroad = data.autoRoadAddressEnglish.replace(/(\s|^)\(.+\)$|\S+~\S+/g, '');
        		}
        		
        		if(road != "") {
        			document.getElementById(ADDR1).value = road;
        		}else {
        			document.getElementById(ADDR1).value = autoroad;
        		}
        		
        		//document.getElementById(old_new_addrgubun).value = "NEW";
        	}else {
        		//지번 클릭 시 구주소(old_new_addrgubun = 0)로 세팅 => data.userSelectedType == "J"
        		document.getElementById(ZIPCD).value = data.postcode1 + "-" + data.postcode2;
        		
        		var jibun = "";
        		var autojibun = "";
        		
        		if(data.userLanguageType == "K") {
        			//한글일 때 한글주소 반환
        			jibun = data.jibunAddress.replace(/(\s|^)\(.+\)$|\S+~\S+/g, '');
                    autojibun = data.autoJibunAddress.replace(/(\s|^)\(.+\)$|\S+~\S+/g, '');
        		}else {
        			//영어일 때 영어주소 반환
        			jibun = data.jibunAddressEnglish.replace(/(\s|^)\(.+\)$|\S+~\S+/g, '');
                    autojibun = data.autoJibunAddressEnglish.replace(/(\s|^)\(.+\)$|\S+~\S+/g, '');
        		}
        		
        		if(jibun != "") {
        			document.getElementById(ADDR1).value = jibun;
        		}else {
        			document.getElementById(ADDR1).value = autojibun;
        		}
        		
        		//document.getElementById(old_new_addrgubun).value = "OLD";
        	}
        	
            document.getElementById(ADDR2).focus();
            element.style.display = 'none';
        },
        width : '100%',
        height : '100%'
    }).embed(element);

	element.style.display = 'block';  
}

function closeDaumPostcode() {
    element.style.display = 'none';
}

</script>
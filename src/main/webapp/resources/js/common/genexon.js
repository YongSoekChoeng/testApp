/* -------------------------------------------------------------------------------------------------
 * genexon V1.1
 *
 * 공통기능 . jQuery 사용자 정의 함수입니다.
 *
 * Copyright (c) 2014 genexon All rights reserved.
 *
 * @author: genexon
 * -------------------------------------------------------------------------------------------------
 */
(function($) {

var window   = this,
//    _genexon = window.genexon,
//    _gx      = window.gx,
    genexon  = gx = $.gx = $.genexon = window.genexon = function() {
    	return new genexon.fn.init();
    };

genexon.fn = /**
 * @author SonJooArm
 *
 */
/**
 * @author SonJooArm
 *
 */
/**
 * @author SonJooArm
 *
 */
genexon.prototype =  {
	init:function() {
	    return this;
	},
	about:"genexon javascript library",
	version: "1.1.0"
};

})(jQuery);

/** Constant variables */



/** String function */



/**
 * 문자열의 길이가 주어진 길이보다 적으면 패딩문자로 좌측을 채웁니다.
 * @param src 원본문자열
 * @param length 문자열의 길이
 * @param pad 채울 문자열
 * @return 패딩된 문자열
 */
genexon.lpad = function(src, length, pad) {
    	
		if(!genexon.hasText(src)) {return "";}
    	
    	src = src+""; 
    	var buffer = [];
    	for(var i=0; i < length - src.length; i++) {
    		buffer.push(pad);
    	}
    	src =  buffer.join("") + src;
    	return src.substring(0, length);
};

/**
 * 문자열의 길이가 주어진 길이보다 적으면 패딩문자로 우측을 채웁니다.
 * @param src 원본문자열
 * @param length 문자열의 길이
 * @param pad 채울 문자열
 * @return 패딩된 문자열
 */
genexon.rpad = function(src, length, pad) {

    	if(!genexon.hasText(src)) {return "";}

    	var buffer = [];
    	for(var i=0; i < length - src.length; i++) {
    		buffer.push(pad);
    	}
    	src =  src + buffer.join("");
    	return src.substring(0, length);
};



/**
 *
 * 공백을 제외한 문자열을 가지고 있는지 확인합니다.
 * @param str 체크할 문자여려
 * @return
 *		true : 문자열의 길이가 > 0 이면 , 그밖에 false
 */
genexon.hasText = function(str) {
	if(!(isNaN(str))) return true; /* 숫자는 문자열있는것으로 인정 */
   if(!str) return false;
   str = genexon.trim(str);
   if(str == "") return false;
   return true;
};


/**
 * 문자열 앞/뒤의 공백을 제거 합니다.
 * @str 트림할 문자열
 */
genexon.trim = function(str) {
	return  str.replace(/^\s\s*/, '').replace(/\s\s*$/, '');
};

/**
 * 문자열의 콤마제거 
 * @str 콤마를 제거할 문자열
 */
genexon.removeComma = function(str) {
	return  str.replace(/,/gi, "");
};


/**
 * 문자열이 널이면 널스트링을 반환합니다.
 * @param str 문자열
 */
genexon.nvls = function(str) {
	if(!str) return "";
    else return  str;
};

/**
 * html 특수문자태그로 변환
 * @param src 원본문자열
 * @param length 문자열의 길이
 * @param pad 채울 문자열
 * @return 패딩된 문자열
 */
genexon.stringToTag = function(str) {
	str = str.replace(/</g, '&lt;');
    str = str.replace(/>/g, '&gt;');
    str = str.replace(/'/g, '&#39;');
    str = str.replace(/"/g, '&quot;');
    return str;
};

/**
 *
 *
 * Oracle의 decode 참조. "1,한글,2,영어,기타" 와 같은 문자열을 decodeString
 * 에 설정하고 expr1에 '2' 값을 설정하면  "영어"를 반환합니다. 주어진 값이
 * 없으면 마지막 기타를 반환합니다.
 *
 * @param expr1 찾을 문자열
 * @param decodeString 해석할 문자열
 * @return
 *      검색된 결과 문자열
 */
genexon.decode = function (expr1, decodeString)   {
        decodeString  = genexon.trim(decodeString);
        var strs = decodeString.split(',');
        return genexon.decode_internal(expr1, strs);
};

/**
 * genexon.decode의 내부 사용함수. 실제 decode를 담당
 */
genexon.decode_internal  = function(expr1, exprs) {
		var i = 0;
		var hasElseValue = false;
		var isMatch = false;
		var expr = genexon.nvls(expr1);

		// 모두 비교하고 없으면 마지막 값이 리턴값
		hasElseValue = (exprs.length % 2) == 1 ? true : false;
		for (i = 0; i < exprs.length; i++) {
            if(( i % 2) == 0  && expr == exprs[i])
                return exprs[i+1];
            i++;
		}// for

		if (!isMatch && hasElseValue)
			rv = exprs[exprs.length - 1];
		return rv;

}; //



//------------------------------------------------------------------------------
// 문자열의끝에서 주어진 길이만큼 분리합니다.
// @param str  원본 문자열
// @param length 길이
//------------------------------------------------------------------------------
genexon.right = function(str, length) {
   if(!str) return "";
   return ( str.length  >=  length)?    str.substring(str.length - length): str;
};


/**
 *  문자열의 시작부분을 잘라냅니다.
 *
 * @param str 원본문자열
 * @param length 길이
 */
genexon.left = function(str, length) {
	  if(!str) return "";
      return  str.length >= length  ? str.substring(0, length): str;
};

/**
 * Byte 길이에 대한 문자열의 시작부분을 잘라냅니다.
 *
 * @param str 원본문자열
 * @param byteLen Byte 길이
 */
genexon.leftWithByte = function(str, byteLen){
  str_len = str.length;
  byte_cnt = 0;
  if(str_len!=escape(str).length){
      for(var i=0;i<str_len;i++){
          byte_cnt++;
          if(genexon.isUnicode(str.charAt(i))){// unicode 문자열.
              byte_cnt++; // 한바이트 더 추가.

              if(byte_cnt >= byteLen){

            	  return str.substring(0, i);
            	  break;
              }
          }
      }
  }
  else{
	  return genexon.left(str, byteLen);
  }

  return str;

};


/**
 *
 * 문자열을 주어진 구분자로 날자형식으로 포맷합니다.
 */
genexon.formatDate = function(str, ch) {

  if(str == null) return "";
  if(str.length < 5) return str;
  else if(str.length > 4 && str.length <  7) {
      return genexon.left(str, 4) + ch  + str.substring(4);
  }else {
      return genexon.left(str, 4) + ch
         + str.substring(4,6) + ch
         + str.substring(6);

  }


};

/**
 * findStr을 찾아서 replaceStr로 바꿉니다.
 * @param str 원본 문자열
 * @param findStr 찾을 문자열
 * @param replaceStr 치환할 문자열
 * @return
 *		치환된 문자열
 */
genexon.replace = function(str, findStr, replaceStr) {
    if(!str) return str;
    return str.replace(new RegExp(findStr,"g"), replaceStr);
};


//------------------------------------------------------------------------------
// 입력된 숫자형식의 문자열에 콤마를 넣습니다.
// @param str 숫자형식의 문자열
// @return
//  	콤마가 삽입된 문자열
//------------------------------------------------------------------------------
genexon.formatComma = function(str) {
	str += '';
	x = str.split('.');
	x1 = x[0];
	x2 = x.length > 1 ? '.' + x[1] : '';
	var rgx = /(\d+)(\d{3})/;
	
	while (rgx.test(x1)) {
		x1 = x1.replace(rgx, '$1' + ',' + '$2');
	}
	
	return genexon.nvl((x1 + x2) + "", "");
};


/**
 * 숫자이외의 문자가 포함되었는지 체크합니다.
 * @param str 문자열
 * @return
 *           true: 정수값이면
 *           false: 아니면
 */
genexon.isCurrency = function(str) {
  return !str.match(/[^0-9,\.]{1,}/);
};




/**
 * 숫자이외의 문자가 포함되었는지 체크합니다.
 * @param str 문자열
 * @return
 *           true: 정수값이면
 *           false: 아니면
 */
genexon.isInteger = function(str) {
  return !str.match(/[^0-9]{1,}/);
};


/**
 * 문자열을 숫자형으로 변환 합니다. 변환할수 없는경우 out 을 리턴합니다.
 * @param str 문자열           
 * @param out 변환실패시 리턴 값    
 * @return                  
 *         변환된 정수값
 */
genexon.tryParseInt = function(str, out){
	var strIntVal = new String(parseInt(str, 10));
	if( genexon.isInteger(strIntVal) == true ){
		return parseInt(str, 10);
	}else{
		return out;
	};

};


/**
 * 숫자와 대쉬만 있는지 체크
 * @param str 문자열
 * @return
 *   true : 정수, 대쉬(-) 이면
 *   false : 아니면
 */
genexon.isNumberDash = function(str) {
  return !str.match(/[^0-9\-]{1,}/);
};

/**
 * 바이트로 환산한 문자열의 길이값 반환.
 */
genexon.getBytesLength = function(str){
    str_len = str.length;
    byte_cnt = 0;
    if(str_len!=escape(str).length){
        for(var i=0;i<str_len;i++){
            byte_cnt++;
            if(genexon.isUnicode(str.charAt(i))){// unicode 문자열.
                byte_cnt++; // 한바이트 더 추가.
            }
        }
    }
    else
        byte_cnt = str_len;
    return byte_cnt;
};



/* ---------------- genexon control utility function ----------------------- */

// 이벤트 발생시 입력컨트롤의 값을 검증하기 위한 함수


/**
 * TextBox 의 전체 영역을 선택한다.
 */
genexon.setCursorSelect = function(_obj, pos) {
    if ($(_obj).get(0).setSelectionRange) {
        $(_obj).get(0).setSelectionRange(0, 999);
      } else if ($(_obj).get(0).createTextRange) { // IE 8 이하
        var range = $(_obj).get(0).createTextRange();
        range.collapse(_obj);
        range.moveEnd('character', pos);
        range.moveStart('character', 0);
        range.select();
      }
};


/**
 * TextBox 의 특정 위치에 Cursor 를 위치 시킨다.
 */
genexon.setCursorPosition = function(_obj, pos) {
	if ($(_obj).get(0).setSelectionRange) {
		$(_obj).get(0).setSelectionRange(pos, pos);
	} else if ($(_obj).get(0).createTextRange) {
		var range = $(_obj).get(0).createTextRange();
		range.collapse(_obj);
		range.moveEnd('character', pos);
		range.moveStart('character', pos);
		range.select();
	}

};



/**
 * 우편번호 형식이 아니면 지웁니다.
 */
genexon.EVENT_ZIPCODE = function(evt) {

	var str = this.value.replace(/[^0-9]/g, "");

	if(!genexon.isNumberDash(this.value)) {
		genexon.alert("info","","우편번호형식 123-123만 입력가능합니다.");
		this.value = str;
		return false;
	}
	this.value = genexon.formatZipCode(str);
	genexon.setCursorPosition(this, 999);
	if(evt.keyCode==9){// Tab Key
		genexon.setCursorSelect(this,999);
	}
	return true;

};

/**
 * 특수문자 형식은 지웁니다. alert 표시
 */
genexon.EVENT_SPCHAR = function(evt) {

	var strobj =this.value;

    spChar = /[ \{\}\[\]\/?.,;:|\)*~`!^\-_+┼<>@\#$%&\'\"\\\(\=]/gi;

    if(spChar.test(strobj)) {
    	genexon.alert("info","","특수문자는 입력하실 수 없습니다.");
		var v = strobj.replace(spChar,"");
		this.value = v;
		return false;
    }


	genexon.setCursorPosition(this, 999);
	if(evt.keyCode==9){// Tab Key
		genexon.setCursorSelect(this,999);
	}
	return true;

};

genexon.validateYmd = function(obj){
	var ymdval = obj.value;
	if(ymdval!=undefined && ymdval!=""){
		var ymdval2 = ymdval.replace(/[-/,.]/gi,"");
		var ymdval3 = ymdval2.substring(0,4)+"-"+ymdval2.substring(4,6)+"-"+ymdval2.substring(6,8)
		var regexp = /([1-9]\d{3})-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[0-1])$/;
		if(regexp.test(ymdval3)){
			obj.value = ymdval3;
		}else {
			genexon.alert("info", "날짜 검색", "검색일자는 YYYY-MM-DD 형식입니다.");
			obj.value = null;
		}
	}	
}



/* ---------------- genexon numbers utility function ----------------------- */

/**
 *두수중에서 작은값을 구합니다.
 */
genexon.min = function(val1, val2){
	if(val1 < val2)
		return val1;
	else
		return val2;
};

/**
 * 두수중에서 큰값을 구합니다.
 */
genexon.max = function(val1, val2){
	if(val1 < val2)
		return val2;
	else
		return val1;
};

/**
 * 입력받은 실수를 반올림하여 원하는 자리까지 표현한다.
 */
genexon.round = function(num, dec){

	var temp = genexon.decToDigit(dec);

	num = num*temp;
	num = Math.round(num);
	num = num/temp;
	return num;
};


genexon.decToDigit = function(dec){
	var temp = 1;
	if(dec>=1){
    	for(var i=0; i<dec; i++){
    		temp = temp*10;
    	}
	}else if(dec<1){
		for(var i=dec; i<0; i++){
    		temp = temp/10;
    	}
	}
	return temp;
};
/* ---------------- genexon business utility function ----------------------- */







/* ---------------- genexon object utils               ----------------------- */


/**
 * 배열이 비어있는지 확인합니다.
 *
 * @param collection 컬렉션 객체
 */
genexon.isEmpty = function(collection) {
	if(!collection) return true;
	if(collection.length == 0) return true;
	return false;
};


/* ---------------- genexon jquery extension misc function ----------------------- */



/**
 * 이미지를 마우스 오버/아웃시 스왑합니다.
 */
genexon.swapImage = function(src, overImage, outImage) {
   $(src)
	   .mouseover(function() {
	       this.src = overImage;
	   })
	   .mouseout(function() {
	       this.src =  outImage;
	   });
};

/**
 *  팝업윈도우를 중앙에 위치시키기 위한 좌표 계산
 *  <pre>
 *  var demention = genexon.getCenterXY(448,366);
 *  window.open(url,'postalCode','width=448,height=366,top=' + demention.Y + ',left=' + demention.X);
 *  </pre>
 *
 *  @param w  팝업창의 width
 *  @param h  팝업창의 height
 *  @return   좌표객체 (.X : 좌측위치, .Y : 위쪽 위치 )
 *
 */
genexon.getCenterXY = function(w, h) {
	var demention = {};
	demention.X = (screen.availWidth / 2) - (w / 2 );
    demention.Y = (screen.availHeight /2) - (h/2) - 40;
    return demention;
};






/**
 * 태그의 좌표와 width, height를 반환
 * @param tag HTML Tag
 */
genexon.getBounds = function(tag)
{
	var ret = { left:0, top:0, width:0,height:0};
	if(typeof(tag) == "undefined"){
		return ret;
	}//

 if(document.getBoxObjectFor) {
     var box = document.getBoxObjectFor(tag);
     ret.left = box.x;
     ret.top = box.y;
     ret.width = box.width;
     ret.height = box.height;
 }else if(tag.getBoundingClientRect)  {
     var rect = tag.getBoundingClientRect();
     ret.left = rect.left + (document.documentElement.scrollLeft || document.body.scrollLeft);
     ret.top  = rect.top + (document.documentElement.scrollTop || document.body.scrollTop);
     ret.width = rect.right - rect.left;
     ret.height = rect.bottom - rect.top;

 }
 return ret;
};



/* -------------------------- Date Function        -------------------------- */

genexon.srvDate = function()//서버시간 가져오기
{ 
	var xmlHttp;
	
	if (window.XMLHttpRequest) { //분기하지 않으면 IE에서만 작동된다.	
		xmlHttp = new XMLHttpRequest(); // IE 7.0 이상, 크롬, 파이어폭스 등	
	} else if (window.ActiveXObject) {
		xmlHttp = new ActiveXObject('Msxml2.XMLHTTP');	
	} else {
		return null; 
	}
	
	xmlHttp.open('HEAD',window.location.href.toString(),false);
	xmlHttp.setRequestHeader("Content-Type", "text/html");
	xmlHttp.send('');
	
	var st = xmlHttp.getResponseHeader("Date")
	
	return new Date(st);
};

/*** date format 함수 설정을 위한 프로토타입 시작 ***/
Date.prototype.format = function (f) {
    if (!this.valueOf()) return " ";

    var weekKorName = ["일요일", "월요일", "화요일", "수요일", "목요일", "금요일", "토요일"];
    var weekKorShortName = ["일", "월", "화", "수", "목", "금", "토"];
    var weekEngName = ["Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"];
    var weekEngShortName = ["Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"];

    var d = this;

    return f.replace(/(yyyy|yy|MM|dd|KS|KL|ES|EL|HH|hh|mm|ss|a\/p)/gi, function ($1) {
        switch ($1) {
            case "yyyy": return d.getFullYear(); // 년 (4자리)
            case "yy": return (d.getFullYear() % 1000).zf(2); // 년 (2자리)
            case "MM": return (d.getMonth() + 1).zf(2);		  // 월 (2자리)
            case "dd": return d.getDate().zf(2); // 일 (2자리)
            case "KS": return weekKorShortName[d.getDay()];	  // 요일 (짧은 한글)
            case "KL": return weekKorName[d.getDay()]; // 요일 (긴 한글)
            case "ES": return weekEngShortName[d.getDay()]; // 요일 (짧은 영어)
            case "EL": return weekEngName[d.getDay()]; // 요일 (긴 영어)
            case "HH": return d.getHours().zf(2); // 시간 (24시간 기준, 2자리)
            case "hh": return ((h = d.getHours() % 12) ? h : 12).zf(2); // 시간 (12시간 기준, 2자리)
            case "mm": return d.getMinutes().zf(2); // 분 (2자리)
            case "ss": return d.getSeconds().zf(2); // 초 (2자리)
            case "a/p": return d.getHours() < 12 ? "오전" : "오후"; // 오전/오후 구분
            default: return $1;
        }
    });
};

String.prototype.string = function(len){
    var s = '', i = 0; 
    while (i++ < len) { s += this; } 
    return s;
};
String.prototype.zf = function(len){return "0".string(len - this.length) + this;};
Number.prototype.zf = function(len){return this.toString().zf(len);};
/*** date format 함수 설정을 위한 프로토타입 종료 ***/

/**
 * 날자객체를 문자열로 되돌립니다.
 *
 * @date    날자 객체
 * @concatChar 연결문자
 */
genexon.getYyyymm = function(date, concatChar) {
	return new Date(date.getFullYear()+"-"+genexon.lpad(""+(date.getMonth() + 1), 2, '0')+"-01").format("yyyy"+concatChar+"MM");
};

//yyyy(구분자)mm 형식 달 계산
genexon.getYyyymmMonth = function(date, concatChar, n) {
	var y = date.getFullYear();
	var m = date.getMonth();

	date.setMonth(m - n);
	
	return new Date(date.getFullYear()+"-"+genexon.lpad(""+(date.getMonth() + 1), 2, '0')+"-01").format("yyyy"+concatChar+"MM");
};

genexon.getYymmdd = function(date, concatChar) {
	return new Date(date.getFullYear()+"-"+genexon.lpad(""+(date.getMonth() + 1), 2, '0') + "-" + genexon.lpad("" + date.getDate(), 2, '0')).format("yyyy"+concatChar+"MM"+concatChar+"dd");
};

genexon.getYymmdd_hhmmss = function(date, concatChar) {
	return  date.getFullYear()
	            + concatChar + genexon.lpad("" + (date.getMonth() + 1), 2, '0')
	            + concatChar + genexon.lpad("" + date.getDate(), 2, '0')
	            + ' ' +genexon.lpad("" + date.getHours(), 2, '0')
	            + ':' +genexon.lpad("" + date.getMinutes(), 2, '0')
	            + ':' +genexon.lpad("" + date.getSeconds(), 2, '0');
};

// n주 전
genexon.getYymmddWeek = function(date ,concatChar, n) {
	var y = date.getFullYear();
	var m = date.getMonth();
	var d = date.getDate();

	date.setDate(d - (7*n));
	
	return new Date(date.getFullYear()+"-"+genexon.lpad(""+(date.getMonth() + 1), 2, '0') + "-" + genexon.lpad("" + date.getDate(), 2, '0')).format("yyyy"+concatChar+"MM"+concatChar+"dd");
};

// n달 전
genexon.getYymmddMonth = function(date ,concatChar, n) {

	var y = date.getFullYear();
	var m = date.getMonth();
	var d = date.getDate();

	date.setMonth(m - n);
	
	return new Date(date.getFullYear()+"-"+genexon.lpad(""+(date.getMonth() + 1), 2, '0') + "-" + genexon.lpad("" + date.getDate(), 2, '0')).format("yyyy"+concatChar+"MM"+concatChar+"dd");
};

// 지난달 마지막일 구하기
genexon.getYymmddLastMonth = function(date ,concatChar) {

	var firstDayOfMonth = new Date(date.getFullYear(), date.getMonth() , 1 );
	var lastMonth = new Date ( firstDayOfMonth.setDate( firstDayOfMonth.getDate() - 1 ) );
	
	return new Date(lastMonth.getFullYear()+"-"+genexon.lpad(""+(lastMonth.getMonth() + 1), 2, '0') + "-" + genexon.lpad("" + lastMonth.getDate(), 2, '0')).format("yyyy"+concatChar+"MM"+concatChar+"dd");
};

/*
 * 현재 날짜에서 n달 전(후) n일 구하기(마지막일은 nDay 구분자가 숫자가 아닌 'lastDay', 당일은 'today')
 * 사용예)
 *  - 한달 전 오늘날짜를 yyyy-MM-dd 형식으로 나오게 하고 싶으면 genexon.getDate("YMD", date, '-', 1, 'today')
 *  - 한달 전 마지막 날짜를 yyyy-MM 형식으로 나오게 하고 싶으면 genexon.getDate("YM", date, '-', 1, 'lastDay')
 *  - 두달 후 26일을 yyyyMMdd 형식으로 나오게 하고 싶으면 genexon.getDate("YMD", date, '', -2, 26)
 *  - 당월 첫번째 날짜를  yyyy/MM/dd 형식으로 나오게 하고 싶으면 genexon.getDate("YMD", date, '/', 0, 1)
 *  - 당월 첫번째 날짜를  yyyyMM 형식으로 나오게 하고 싶으면 genexon.getDate("YM", date, '', 0, 1)
 */
genexon.getDate = function(dateFormat, date, concatChar, nMonth, nDay) {
	var m = date.getMonth();
	var d = nDay;
	
	//변경 전 날짜의 오늘날짜
	var originalToday = date.getDate();
	
	//31일인 월에 30일 혹은 29,28일 까지밖에 없는 월로 넘어가서 31일로 세팅되면 1달+ 됨. 따라서 아래와 같이 1일로 만든 후 월을 뺀 다음 if문에서 일자 비교 후 세팅
	date = new Date(date.getFullYear(), date.getMonth(), 1);
	
	//n달 전(후) 구하기
	date.setMonth(m - nMonth);
	
	//d가 1 ~ 31일이 아니거나 lastDay이고 today가 아닌  경우 무조건 n달 전/후의 마지막 일자
	if((!(d >= 1 && d <= 31) || d == "lastDay") && d != "today") {
		d = (new Date(date.getFullYear(), genexon.lpad(""+(date.getMonth() + 1), 2, '0'), 0)).getDate();
	}else if(d == "today") {
		//n달 전/후 오늘 날짜
		//변경 된 년월의 마지막 일자
		var changeToday = new Date(date.getFullYear(), (date.getMonth()+1), 0).getDate();
		
		//31일인 월에 30일 혹은 29,28일 까지밖에 없는 월로 넘어가서 31일로 세팅되면 1달+ 됨. 따라서 아래와 같이 세팅
		if(originalToday > changeToday) {
			//변경 전 오늘날짜가 변경 후 마지막일자보다 크면 변경 후 마지막 일자로 세팅
			d = changeToday;
		}else {
			//그렇지 아니면 변경 전 오늘날짜로 세팅
			d = originalToday;
		}
	}
	
	if(dateFormat == "YM") {
		return new Date(date.getFullYear()+"-"+genexon.lpad(""+(date.getMonth() + 1), 2, '0') + "-" + genexon.lpad("" + d, 2, '0')).format("yyyy"+concatChar+"MM");
	}else {
		return new Date(date.getFullYear()+"-"+genexon.lpad(""+(date.getMonth() + 1), 2, '0') + "-" + genexon.lpad("" + d, 2, '0')).format("yyyy"+concatChar+"MM"+concatChar+"dd");
	}
};

/**---- Http Request */
/**
 * 데이타 객체를 쿼리스트링으로 변환합니다.
 * @param dataObject 데이타 객체
 */
genexon.toQueryString = function(dataObject) {
	var result = "";
	for (var prop in dataObject){
		result += "&" + prop + "=" + dataObject[prop];
	}//for
	return result;
};


/** JSON 객체의 속성에 폼 엘리먼트의 값을 채운다. */
genexon.__setJSONProperty = function(vo, e ) {

	if(/* 객체안의 객체 */ e.name.indexOf(".") > 0) {
		var names  = e.name.split(".");
		var fld = null;
		for(var i=0; i < names.length-1; i++) {
			if(i == 0) {
				if(!vo[names[i]]) vo[names[i]] = {};
				fld = vo[names[i]];
			}else {
				if(!fld[names[i]]) fld[names[i]] = {};
				fld = fld[names[i]];
			}
		}// for
		genexon.__setJSONPropertyValue(fld, names[names.length-1] ,e);
	}else {
		genexon.__setJSONPropertyValue(vo, e.name, e);
	}
	return vo;
};

/** 
 * JSON 객체의 속성값을 채운다. 
 */
genexon.__setJSONPropertyValue = function(fld, name, e) { // 필드 , name, 객체

	switch(e.type) {
	case "radio":
		fld[name] = $('input[name=' + name + ']:checked').val();
		break;
	case "select-multiple":

		fld[name] =[];
		var arr = fld[name];
		for(var j=0; j < e.options.length;j++) {
			if(e.options[j].selected) {
				arr.push(e.options[j].value);
		    }
		}// for

		break;
	case "select-one":
		if(e.selectedIndex == -1){
			fld[name] = "";
		}else{
			fld[name] =  e.options[e.selectedIndex].value;
		}
		break;
	case "checkbox":
		var ele = document.getElementsByName(e.name);
		if(ele.length == 1){// Checkbox 가 1개인경우 객체로 생성
			if(e.checked) {
				fld[name] = e.value;
			}else{
				fld[name] = null;
			}
			break;
		}
		// Array
		if(!fld[name]) fld[name] = [];
		var chkbox = fld[name];
		if(e.checked) {
			chkbox.push(e.value);
		}
		break;
	case "text":
		// kim 숫자, 주민번호, 우편번호, 전화번호 , -  빼기
		if( $(e).hasClass("comma") ){
			fld[name] = genexon.replace($(e).val(), ",", "");
		}else if( $(e).hasClass("ssn") ){
			fld[name] = genexon.replace($(e).val(), "-", "");
		}else if( $(e).hasClass("telephone") ){
			fld[name] = genexon.replace($(e).val(), "-", "");
		}else if( $(e).hasClass("zipcode") ){
			fld[name] = genexon.replace($(e).val(), "-", "");
		}else{

			fld[name] = $(e).val();
		}
		break;
	case "hidden":
	case "password":
		fld[name] = $(e).val();
		break;
	case "textarea":
		fld[name] = genexon.replace($(e).val(), "\r\n", "\\n");
		//fld[name] = e.value;
		break;
	}
};

genexon.getId = function(data) {
	return data;
}

genexon.getValue = function(data) {
	var resultValue = null;
	
	if(data.type == "checkbox"){
		resultValue = $("#"+data.id).is(":checked");
	}else{
		resultValue = data.value.trim();
	}
	
	return resultValue;
}

genexon.getSearchParameterToJsonString = function() {
	var searchParameterJson = {};
	var $inputObj = $("input");
	
	for(var i=0; i < $inputObj.length; i++){
		var jsonKey = genexon.getId($inputObj[i].id);
		var jsonValue = genexon.getValue($inputObj[i]);
		
		if(genexon.nvl($($inputObj[i]).attr("type"), "") != "checkbox") {
			if(jsonValue == "전체"){
				searchParameterJson[jsonKey] = "";
			}else{
				searchParameterJson[jsonKey] = jsonValue;
			}
		}
	}
	
	return genexon.getJSONString(searchParameterJson);
}

/** JSON 객체를 문자열로 변환한다. */
genexon.getJSONString = function(object) {
	/* $.toJSON 작동하지 않아 주석 처리.
	 *  JSON.stringify로 바꿈
	return $.toJSON(object);
	*/
	return JSON.stringify(object);

};

/** JSON String을 Object로 변환한다. */
genexon.getJSONObj = function(JSONstring) {
	return $.evalJSON(JSONstring);

};

/**
 * html form의 element를 JSON으로 변환하여 String 으로 반환합니다.
 * @param formId  html form의 아이디
 */
genexon.getJSONStringFromForm = function(formId) {

	var vo   = {};
	//var _form_data = {};   // checkbox collection
	var f =  $("#" + formId)[0];
	for(var i=0; i < f.elements.length; i++) {
		var element = f.elements[i];
		if(element.type &&  element.name) {
			// submit="no" 필터링
			if($(element).attr("submit")){
				if( $(element).attr("submit").toUpperCase() == "NO"){continue;}
			}
			genexon.__setJSONProperty(vo,element);
		}
	}// for
	return genexon.getJSONString(vo);
};

/**
 * html form의 element를 JSON으로 변환하여 반환합니다.
 * @param formId  html form의 아이디
 */
genexon.getJSONObjectFromForm = function(formId) {

	var vo   = {};
	//var _form_data = {};   // checkbox collection
	var f =  $("#" + formId)[0];

	for(var i=0; i < f.elements.length; i++) {
		var element = f.elements[i];
		if (element.type &&  element.name) {
			// submit="no" 필터링
			if ($(element).attr("submit")) {
				if ($(element).attr("submit").toUpperCase() == "NO") {
					continue;
				}
			}
			genexon.__setJSONProperty(vo, element);
		}
	}// for
	return vo;
};


/**
 * 특정테그 하위의 element를 JSON으로 변환하여 반환합니다.
 * @param tagId  특정테그 아이디
 */
genexon.getJSONStringFromTagId = function(tagId) {

	var vo   = {};
	//var _form_data = {};   // checkbox collection

	var input =  $("#tabSearch input");
	var select =  $("#tabSearch select");
	var textarea =  $("#tabSearch textarea");

	var elements = new Array();
	for(var i=0; i< input.length; i++){
		elements.push(input.eq(i)[0]);
	}
	for(var i=0; i< select.length; i++){
		elements.push(select.eq(i)[0]);
	}
	for(var i=0; i< textarea.length; i++){
		elements.push(textarea.eq(i)[0]);
	}

	for(var i=0; i < elements.length; i++) {
		var element = elements[i];
		if(element.type &&  element.name) {
			// submit="no" 필터링
			if($(element).attr("submit")){
				if( $(element).attr("submit").toUpperCase() == "NO"){continue;}
			}
			genexon.__setJSONProperty(vo,element);
		}
	}// for
	return genexon.getJSONString(vo);
};



/**
 * JSON객체를 FORM의 엘리먼트에 표시
 * @param JSON 객체
 */
genexon.setHtmlForm = function(json) {
	genexon.__setHtmlForm("", json);
};


/**
 * JSON 객체를 FORM의 엘리먼트에 표시하는 내부 함수
 * @param parentElementName  상위객체이름
 * @param inputData  JSON Object
 */
genexon.__setHtmlForm = function(parentElementName, inputData) {

	var prefix = (parentElementName == "")? "": parentElementName  + ".";
	for(var key in inputData) {
		//alert(prefix + key);
		var _form_em = document.getElementsByName(prefix + key);   // 폼에 엘리먼트 검색
		if(!_form_em) continue;                                 // 폼에 엘리먼트가 없으면 skip

        var jo = inputData[key];
        if(typeof(jo) == "undefined" || jo == null){
        	continue;
        }
        if(typeof jo == "object") {
            // 아래는 array인지 체크하는 방법
            if(jo.constructor.toString().indexOf("Array") > -1) {
               if(!_form_em[0]) continue;
               if(_form_em[0].type == "checkbox")  {
                    for(var i=0; i < jo.length; i++) {
                        for(var j=0; j< _form_em.length; j++) {
                            if(jo[i] == _form_em[j].value) {
                                _form_em[j].checked = true;
                            }
                        }// for
                    }
               }else if(_form_em[0].type == "select-multiple") {
	                for(var i=0; i < _form_em[0].length; i++) {
	                    var opt = _form_em[0].options[i];
	                    for(var j=0; j < jo.length; j++) {
		                    if(jo[j] == opt.value) {
			                    opt.selected = true;
		                    }
	                    }
	                }// for
               }// else if
            }else {
            	genexon.__setHtmlForm(prefix + key, jo);
            }
        }else {

        	if(!_form_em[0]) continue;

            if(_form_em[0].type == "checkbox")    {
                for(var i=0; i < _form_em.length; i++) {
                    if(_form_em[i].value == jo) {
                        _form_em[i].checked = true;
                    }
                }// for
            }else if(_form_em[0].type == "radio")  {
                for(var i=0; i < _form_em.length; i++) {
                    if(_form_em[i].value == jo) {
                        _form_em[i].checked = true;
                        break;
                    }
                }// for

            }else if(_form_em[0].type == "select-one") {
                for(var i=0; i < _form_em[0].length; i++) {
                    var opt = _form_em[0].options[i];
                    if(opt.value == jo) {
                        opt.selected = true;
                        break;
                    }
                }// for
            }else if(_form_em[0].type == "select-multiple") {
                for(var i=0; i < _form_em[0].length; i++) {
                    var opt = _form_em[0].options[i];
                    if(opt.value == jo) {
                        opt.selected = true;
                    }
                }// for
            }else if(_form_em[0].type == "text")  {
            	if($("#" + _form_em[0].name).hasClass("comma")) {
            		$("#" + _form_em[0].name).val(genexon.formatComma(jo));
            	}else if($("#" + prefix + key ).hasClass("ssn")) {
            		_form_em[0].value =  genexon.formatSsn(jo);
            	}else if($("#" + prefix + key ).hasClass("telephone")) {
            		_form_em[0].value =  genexon.formatTelNo(jo);
            	}else if($("#" + prefix + key ).hasClass("zipcode")) {
            		_form_em[0].value =  genexon.formatZipCode(jo);
            	}else {
            		_form_em[0].value = jo;
            	}
            }else if(
                 _form_em[0].type == "hidden"
                || _form_em[0].type == "password"
                || _form_em[0].type == "textarea")    {
                _form_em[0].value = jo;
            }
        } // if typeof == "object"
    }// for
};

/* -------------------------- manipulate html table -------------------------- */


/**
 * 해당 태그를 화면상의 가운데 위치 시킨다.
 * @params id 대상 태그 아이디
 */
genexon.setCenterPosition = function(objId, objW, objH) {
    var d = document;

    // 화면의 크기(scroll 포함)
    var w = d.body.clientWidth;   //d.documentElement.clientWidth
    var h = d.body.clientHeight;  //d.documentElement.clientHeight

    var x = (window.pageXOffset) ?
            window.pageXOffset : (d.documentElement && d.documentElement.scrollLeft) ?
                d.documentElement.scrollLeft : (d.body) ? d.body.scrollLeft : 0;
    var y = (window.pageYOffset) ?
            window.pageYOffset : (d.documentElement && d.documentElement.scrollTop) ?
                d.documentElement.scrollTop : (d.body) ? d.body.scrollTop : 0;

    //var x = $("body").scrollLeft();
    //var y = $("body").scrollTop();

    // 브라우저 창의 전체 크기
    //var _w = 0;
    var _h = 0;
    if (window.innerWidth == undefined) {
    	_w = parent.document.documentElement.offsetWidth;
    	_h = parent.document.documentElement.offsetHeight;
    }
    else {
    	_w = window.outerWidth;
    	_h = window.outerHeight;
    }

	var yPos = 0;
    if (arguments[3] != null) {
    	xPos = arguments[3].pageX;
    	yPos = arguments[3].pageY;
	}

    // 마우스 포인터 지점이 창크기를 넘어간 경우(스크롤인 경우)
    if (yPos < _h) {
    	$("#" + objId).css("top", ((_h / 2) + y) - objH);
    }
    else {
    	$("#" + objId).css("top", (((h + (h - _h)) / 2) + y) - objH);
    }

    $("#" + objId).css("left" , ((w / 2) + x) - objW);
};//:





genexon.fn.init.prototype = genexon.fn;





/**
 * Sleep
 * @param msecs
 */
genexon.sleep = function(msecs)
{
	var start = new Date().getTime();
	var cur = start;
	while(cur - start < msecs)
	{
		cur = new Date().getTime();
	}
};

/**
 * 외부 URL에 대해 브라우저 window 띄우기 
 */
genexon.windowOpenByRequest = function(requestURL, name) {
	var win = window.open(requestURL, name, "width=1280,height=768, top=0, left=0,scrollbars=no,resizable=yes,status=no,location=no,menubar=no,toolbar=no");
	win.focus();
}

genexon.windowOpenByRequestScroll = function(requestURL, name) {
	var win = window.open(requestURL, name, "width=1280,height=768, top=0, left=960,scrollbars=yes,resizable=yes,status=no,location=no,menubar=no,toolbar=no");
	win.focus();
}

genexon.windowOpenByRequestSize = function(requestURL, name, width, height) {
	var win = window.open(requestURL, name, "width="+width+",height="+height+", top=0, left=960,scrollbars=no,resizable=yes,status=no,location=no,menubar=no,toolbar=no");
	win.focus();
}

/**
 * KENDOUI
 * 컨트롤 초기화 함수
 */
genexon.initKendoUI	= function() {
	// 부모의 body로 초기화
    genexon.initParentBody();
    // 탭아이디 설정
    genexon.initKendoUI_tabPgm();
    //Kendo Notification 설정
    genexon.initKendoUI_notification();
    
    // 마감현황코드 세팅
    genexon.CL_STEP_CD = $("#CL_STEP_CD").val();
    
    //데이터소스 저장 객체 초기화
    genexon.dataSources = {};
    
    // genexon.ajaxError를 ajax에러 객체로 세팅
    $.ajaxSetup({
    	error: genexon.ajaxError
    });
    
    // Locale 설정
    kendo.culture("ko-KR"); 
    // 버튼셋팅(버튼 요소)
    genexon.initKendoUI_btn("button");
    // 월력셋팅(class='kdtPicym')
    genexon.initKendoUI_dtym(".kdtPicym");
    // 달력셋팅(class='kdtPicymd')
    genexon.initKendoUI_dtymd(".kdtPicymd");
    // dropdownlist셋팅(class='kddl')
    genexon.initKendoUI_ddl(".kddl");
    // MultiSelect셋팅(class='kdms')
    genexon.initKendoUI_ms(".kdms");
    // body의 높이를 genexon객체의 높이로 세팅
    genexon.initHeight = $("body").height();
    // KendoNumbericTextBox
    genexon.initKendoUI_nb(".kntb");
};

genexon.initKendoUI_tabPgm = function() 
{
    if(parent.genexon.menuID == undefined)
    {
        $(".ifmPgm",parent.document).each(function(index){
            if(this.contentDocument.URL == location.href)
            {
                genexon.menuID = $("#tab_"+$(this).attr("menuID"),parent.document);
            }
            //alert($(this).attr("src"));
        });
    }
    else
    {
        genexon.menuID = parent.genexon.menuID;
    }
    
};

/**
 * KENDOUI NumericTextBox 컨트롤 셋팅
 * @param selector jquery selector
 */
genexon.initKendoUI_nb = function(selector) {
    if (typeof selector == "object")
    {
        genexon._initKendoUI_nb(selector);
    }
    else
    {
        $(selector).each(function(index){
            genexon._initKendoUI_nb(this);
        });
    }
};
genexon._initKendoUI_nb = function(obj) {

    var value = $(obj).attr("value");
    var min = $(obj).attr("min");				//최소값
    var max = $(obj).attr("max");				//최대값
    var format = $(obj).attr("format");			//형식
    var decimals = $(obj).attr("decimals");		//자리수
    var step = $(obj).attr("step");				//스피너 업 다운 시 증가, 감소되는 숫자
    var spinners = $(obj).attr("spinners");		//스피너 여부
    
    var setBool = true;
    if($(this).data("kendoNumericTextBox")) {
        setBool = false;
    }

    if(setBool) {
        var setArr = {};
        if(decimals) {setArr.decimals = decimals;} else { setArr.decimals = 0;}
        if(value) setArr.value = value;
        if(min) setArr.min = min;
        if(max) setArr.max = max;
        if(format) {setArr.format = format;} else { setArr.format = "#"; }
        if(step) {setArr.step = step;} else { setArr.step = 1;}
        if(spinners) setArr.spinners = (genexon.nvl(spinners, "") == "true" ? true : false);
        
        $(obj).kendoNumericTextBox(setArr);
    }
};
/**
 * KENDOUI MultiSelect 컨트롤 셋팅
 * @param selector jquery selector
 */
genexon.initKendoUI_ms = function(selector) {
    if (typeof selector == "object")
    {
        genexon._initKendoUI_ms(selector);
    }
    else
    {
        $(selector).each(function(index){
            genexon._initKendoUI_ms(this);
        });
    }
};
genexon._initKendoUI_ms = function(obj) {

    var ddcode = $(obj).attr("ddcode");
    var condDt = $(obj).attr("condDt");
    var dtObj = null;
    var setBool = false;
    
    dtObj = genexon.getDDLConf(ddcode,condDt);
    
    if(dtObj)
    {
        setBool = true;
    }
    
    if($(obj).data("kendoMultiSelect"))
    {
        setBool = false;
    }
    
    if(setBool)
    {
        $(obj).kendoMultiSelect({
            dataTextField: dtObj.textField,
            dataValueField: dtObj.ValueField,
            autoBind: true, // false 인경우 multiselect 값이 즉각 반영되지 않음
            dataSource: dtObj.ds
        });
        genexon.bindDataSourceAJAXEvent($(obj).data("kendoMultiSelect").dataSource);
    }
};

/**
 * 그리드 데이터 ms셋팅
 */
genexon.GridEditor_ms=function(container, options) {
    if(!options.values){
        //alert("DropDownList 설정시 values설정을 하세요!");
        genexon.alert("error","시스템 오류","multiselect 설정시 values설정을 하세요!");
        return;
    }
    
    var inputString = '<input ddcode="' + options.values.ddcode + '" data-bind="value:' + options.field + '" ' ;  
    
    if(options.values.optionLabel){
        inputString = inputString + ' optionLabel="'+options.values.optionLabel+ '" '; 
    }

    if(options.values.required){
        inputString = inputString + ' required="required" '; 
    }

    if(options.values.validationMessage){
        inputString = inputString + ' validationMessage="'+options.values.validationMessage+'" ' ;
    }else if(options.values.required){
        inputString = inputString + ' validationMessage="필수항목" ' ;
    }
    
    
    if(options.values.condDt){
        inputString = inputString + ' condDt="'+options.values.condDt+'" ' ;
    }
    
    inputString = inputString + '/>';
    
    genexon.initKendoUI_ms($(inputString).appendTo(container));
};

/**
 * 그리드 데이터ddl셋팅
 */
genexon.GridEditor_ddl=function(container, options) {
    if(!options.values){
        //alert("DropDownList 설정시 values설정을 하세요!");
        genexon.alert("error","시스템 오류","DropDownList 설정시 values설정을 하세요!");
        return;
    }
    
    var guid = kendo.guid();
    
    var inputString = '<input type="text" id="'+guid+'" name="'+options.field+'" class="kddl" ddcode="' + options.values.ddcode + '" data-bind="value:' + options.field + '" ' ;  
    
    if(options.values.optionLabel){
        inputString = inputString + ' optionLabel="'+options.values.optionLabel+ '" '; 
    }

    if(options.values.required){
    	inputString = inputString + ' required="required" '; 
    }

    if(options.values.validationMessage){
        inputString = inputString + ' validationMessage="'+options.values.validationMessage+'" ' ;
    }else if(options.values.required){
        inputString = inputString + ' validationMessage="필수항목" ' ;
    }
    
    if(options.values.condDt){
        inputString = inputString + ' condDt="'+options.values.condDt+'" ' ;
    }
    
    if(options.values.height){
        inputString = inputString + ' height="'+options.values.height+'" ' ;
    }
    
    //스타일(width를 style로 바꿈)
    if(options.values.style) {
    	inputString = inputString + ' style="'+options.values.style+'" ' ;
    }
    
    //기타 파라미터
    if(options.values.etcParam) {
    	inputString = inputString + options.values.etcParam;
    }
    
    if(options.values.dsReload){
        inputString = inputString + ' dsReload="'+options.values.dsReload+'" ' ;
    }
    
    inputString = inputString + '/>';
    
    genexon.initKendoUI_ddl($(inputString).appendTo(container));
    

};

/**
 * KENDOUI dropdownlist 컨트롤 셋팅
 * @param selector jquery selector
 */
genexon.initKendoUI_ddl = function(selector) 
{
    if (typeof selector == "object")
    {
        genexon._initKendoUI_ddl(selector);
    }
    else
    {
        $(selector).each(function(index){
            genexon._initKendoUI_ddl(this);
        });
    }
	
};

genexon._initKendoUI_ddl  = function(obj)
{
    var ddcode      = $(obj).attr("ddcode");
    var optionLabel = $(obj).attr("optionLabel");
    var condDt      = $(obj).attr("condDt");
    var cascadeFrom = $(obj).attr("cascadeFrom");
    var height      = $(obj).attr("height");
    var width       = $(obj).attr("width");
    var dsReload   	= genexon.nvl($(obj).attr("dsReload"),"N"); //데이터소스가져올때 다시 불러올지에 대한 설정 dskey에 있어도 다시 불러옴
    
    var filter		= $(obj).attr("filter");					//셀렉트박스 검색창
    var filterPlaceHolder = $(obj).attr("filterPlaceHolder");	//셀렉트박스 검색창 placeholder
	var filterValue = $(obj).attr("filterValue");
    
    var listWidth	= $(obj).attr("listWidth");					//셀렉트 박스 클릭했을때 list의 width
    var defaultIndex = parseInt($(obj).attr("defaultIndex"));
    
    var serverPaging = genexon.nvl($(obj).attr("serverPaging"), "") == "true" ? true : false;
    var serverFiltering = genexon.nvl($(obj).attr("serverFiltering"), "") == "true" ? true : false;
    var pageSize = genexon.nvl($(obj).attr("pageSize"), "") == "" ? 30 : $(obj).attr("pageSize");
    
    var initialVal = $(obj).val();
        
    //dropDown 데이터 서버페이징 및 서버필터링 적용 파라미터 세팅
    var dsParam = {
		serverPaging: serverPaging,
		serverFiltering: serverFiltering,
		pageSize: pageSize,
		initialVal: initialVal
    }
    
    /* listWidth 설정 안했을경우 default 180 */
    if(!listWidth){
    	if(ddcode == "SCD"){
    		listWidth = 200;
    	}else{
    		listWidth = 180;
    	}
    }
    
    /* defaultIndex 설정 안했을경우 */
    if(!defaultIndex) {
    	defaultIndex = 0;
    }
    
    var dtObj = null;
    var setBool = false; //셋팅여부

    dtObj = genexon.getDDLConf(ddcode,condDt,dsParam,dsReload);
    
    if(dtObj) setBool = true;
    if($(this).data("kendoDropDownList")) setBool = false;
    
    if(setBool) {
    	var init = {
            dataTextField: dtObj.textField,
            dataValueField: dtObj.ValueField,
            dataSource: dtObj.ds,
            index: defaultIndex,
			ignoreCase: true
        };
        
        if(cascadeFrom)
        {
        	init.cascadeFrom=cascadeFrom;
        }
        if(optionLabel)
        { 
            init.optionLabel={};
            init.optionLabel[dtObj.textField] = optionLabel;
            init.optionLabel[dtObj.ValueField] = "";
        }
        if(height)
        {
            init.height = height;
        }
        if(width)
        {
            init.width = width;
        }
        if(filter) {
        	init.filter = filter;
        	        	
        	//서버필터링 적용 시
        	if(serverFiltering) {	
        		var filteringFunc = function(ev) {
	
        			//검색변수 srch_ddl_filter_value를 통해 검색 조건 전달
        			var objDDL = $(obj).data("kendoDropDownList");
					if(ev.filter != undefined){
        				objDDL.dataSource.options.transport.read.data.srch_ddl_filter_value = ev.filter.value;
					}
        		}
        		
        		init.filtering = filteringFunc;
        	}
        }

        $(obj).kendoDropDownList(init);
        genexon.bindDataSourceAJAXEvent($(obj).data("kendoDropDownList").dataSource);

    	$(obj).data("kendoDropDownList").list.width(listWidth);

    	//필터에 placeholder 표시
    	if(genexon.nvl(filter, "") != "" && genexon.nvl(filterPlaceHolder, "") != "") {
    		var filterInput = $(obj).data("kendoDropDownList").filterInput;
    		$(filterInput).attr("placeHolder", filterPlaceHolder);
			$(filterInput).val($(obj).data("kendoDropDownList").dataSource.options.transport.read.data.srch_ddl_filter_value);
    	}
    }
};


genexon.replaceString = function(value) {
	var val = value.split('-')
	var len = value.split('-').length
	var nul = "";
	for(i=0;i<len;i++){
		nul += '&nbsp;'
	}
	if(val == "전체"){
		var img = ''
	}else{
		var img = '<img src="/img/icon_arrow.png">'
	}
    return [nul,val[len - 1],img];
}
genexon.getDDLConf = function(ddcode,condDt,dsParam,dsReload) {
	
	if(ddcode == undefined) {
		return;
	}
	
    var rtnObj = {textField:"",ValueField:"",width:0, ds:null}; 

	switch (ddcode.toUpperCase())
	{
    	
    	case "ROLE_ID": //롤ID
    		rtnObj.textField = "role_nm";
    		rtnObj.ValueField = "role_id";
    		break;
    		
        case "MB_ID" :
        	rtnObj.textField = "mb_nm";
 	        rtnObj.ValueField = "mb_id";
        	break;
 	    
        case "SY_COM_YM" ://erp 마감월(tbsy_close_month)
        	rtnObj.textField = "com_ym_text";
 	        rtnObj.ValueField = "com_ym";
 	        break;
        
        case "INSCO_CD" :
        	rtnObj.textField = "insco_nm";
 	        rtnObj.ValueField = "insco_cd";
 	        break;

        case "UPLOAD_TABLE_MST" :
        	rtnObj.textField = "textField";
 	        rtnObj.ValueField = "table_name";
 	        break;
 	        
        case "UPLOAD_EXCEL_COLUMN" :
        	rtnObj.textField = "column_comment";
 	        rtnObj.ValueField = "column_name";
 	        break;
 	    
        case 'UPLOAD_EXCEL_MAPPING_COLS' :
        	rtnObj.textField = "data_nm";
 	        rtnObj.ValueField = "data_cd";
 	        break;

        case 'COMMON_CODE_GROUP' :
        	rtnObj.textField = "grp_cmm_cd_nm";
 	        rtnObj.ValueField = "grp_cmm_cd";
 	        break;
 	        
        case "SY_COM_YEAR" ://erp 마감 년도
        	rtnObj.textField = "com_year_text";
 	        rtnObj.ValueField = "com_year";
 	        break;
 	        
        case "INSCO_HOMPAGE" ://보험사 바로가기
        	rtnObj.textField = "insco_nm";
 	        rtnObj.ValueField = "plan_url";
 	        break;
        	
        case "MONTH_ORG":
        	rtnObj.textField = "snmpath_ddl";
 	        rtnObj.ValueField = "scd";
 	        break;
 	        
        case "MONTH_EMP":
        	rtnObj.textField = "emp_nm2";
 	        rtnObj.ValueField = "emp_cd";
 	        break;
 	        
        case "MONTH_CONT":
        	rtnObj.textField = "inspol_no2";
 	        rtnObj.ValueField = "inspol_no";
 	        break;
 	        
        case "MONTH_CONT_SHARE":
        	rtnObj.textField = "inspol_no2";
 	        rtnObj.ValueField = "inspol_no";
 	        break;

        case "EXCEL_CD":
        	rtnObj.textField = "excel_nm";
        	rtnObj.ValueField = "excel_cd";
        	break;
        	
        case "DATA_CD":
        	rtnObj.textField = "data_nm";
        	rtnObj.ValueField = "data_cd";
        	break;
        	
        case "SRC_SUIP_COM_CD":
        	rtnObj.textField = "suip_com_nm";
        	rtnObj.ValueField = "suip_com_cd";
        	break;
        	
        case "JIGUB_STANDARD_DATA":
        	rtnObj.textField = "column_comment";
 	        rtnObj.ValueField = "column_name";
        	break;
        	
        case "SUIP_STANDARD_DATA":
        	rtnObj.textField = "column_comment";
 	        rtnObj.ValueField = "column_name";
        	break;
        	
        case "PAYROLL_GBN":
        	rtnObj.textField = "payroll_gbn";
 	        rtnObj.ValueField = "payroll_gbn_nm";
        	break;
        	
        case "PAYROLL_COM_YM":
        	rtnObj.textField = "com_ym_text";
 	        rtnObj.ValueField = "com_ym";
        	break;

		case "PROD_KIND2" :	// 상품구분2
			rtnObj.textField = "prod_kind2_nm";
			rtnObj.ValueField = "prod_kind2";
			break;
        	
	    default:
    	    rtnObj.textField = "TEXT";
            rtnObj.ValueField = "VALUE";
    	    break;
	}
	
	rtnObj.ds = genexon.getCommonCodeDataSource(ddcode,condDt,dsParam,dsReload);
	return rtnObj;
};



/**
 * 공통코드 데이터소스가져오기 
 */
genexon.getCommonCodeDataSource = function(ddcode,condDt,dsParam,dsReload){
    var DDC = ddcode.toUpperCase();
    var dskey = DDC;
    var rtnDs=null;
    var dsJson={
            transport: {
                read: {
                       dataType: "json"
                      ,contentType: "application/json;charset=UTF-8"
                      ,url: ""
                      ,data:{_csrf: $("meta[name='_csrf']").attr("content")}
                }
            },
            schema: {
               data: "results" 
            }
        };
	
    if(dsParam != undefined && dsParam.serverPaging) {
    	dsJson.pageSize = dsParam.pageSize;
    	dsJson.serverPaging = dsParam.serverPaging;
    }
    
    if(dsParam != undefined && dsParam.serverFiltering) {
    	dsJson.serverFiltering = dsParam.serverFiltering;
    }
    
    if(!genexon.dataSources) genexon.dataSources={};
    
    dsJson.transport.read.data = {};
    
    if(condDt) {
        var condArr = (condDt).split("|");
        
        for(var i=0;i<condArr.length;i++)
        {
            var condRow = condArr[i].split(":");
            dsJson.transport.read.data[condRow[0]] = condRow[1];
        }
		
        dskey = dskey + condDt;
    }
	
    if(!genexon.dataSources.hasOwnProperty(dskey) || dsReload == "Y")
    {
        
    	switch (DDC) // ddcode를 UpperCase로
        {
            case "ROLE_ID": //권한코드
                dsJson.transport.read.url = "/menu/getMenuRoleMaster.ajax";
                break;
                
            case "MB_ID" : // 회원사
            	dsJson.transport.read.url = "/system/member/getMemberList.ajax";
            	break;

            case "SY_COM_YM" :	//erp 마감월(tbsy_close_month)
            	dsJson.transport.read.url = "/closeMonth/getComYmList.ajax";
            	break;
            
            case "INSCO_CD" : // 보험사
            	dsJson.transport.read.url = "/system/insco/getInscoList.ajax";
                dsJson.transport.read.data.use_yn = "Y";	// 사용중인 보험사만 조회
            	break;
                
            case 'ALL_BOND_GBN'://보증보험, 기타담보 보증목적구분
            	dsJson.transport.read.url = "/commoncode/getAllBondGbn.ajax";
                break;
                
            case 'ALL_BOND_TYPE'://보증보험, 기타담보 보증종류
            	dsJson.transport.read.url = "/commoncode/getAllBondType.ajax";
                break;
            
            case 'UPLOAD_TABLE_MST':
            	dsJson.transport.read.url = "/uploadExcel/getUploadTableMst.ajax";
            	break;
                
            case 'UPLOAD_EXCEL_COLUMN' :
            	dsJson.transport.read.url = "/uploadExcel/getSystemColumns.ajax";
            	break;
           
            case 'UPLOAD_EXCEL_MAPPING_COLS' :
            	dsJson.transport.read.url = "/uploadExcel/getUploadExcelMappingMst.ajax";
     	        break;
     	        
            case 'COMMON_CODE_GROUP' :
            	dsJson.transport.read.url = "/commoncode/getCommonCodeGroup.ajax";
            	break;
           
            case "SY_COM_YEAR" :		//erp 마감년도(tbsy_close_month)
            	dsJson.transport.read.url = "/closeMonth/getComYearList.ajax";
            	break;
            	
            case "INSCO_HOMPAGE" :		//홈페이지 바로가기
            	dsJson.transport.read.url = "/system/insco/getInscoList.ajax";
            	break;
            	
            case "MONTH_ORG" :			// 조직마감
            	dsJson.transport.read.url = "/presequenttask/insaBasis/getMonthOrgList.ajax";
            	dsJson.transport.read.data.sort_column = "ordersb";
            	dsJson.transport.read.data.resource_url = $("#resource_url").val();
            	
     	        break;
     	        
            case "MONTH_EMP":			// 인사마감
            	dsJson.transport.read.url = "/presequenttask/insaBasis/getMonthEmpList.ajax";
            	dsJson.transport.read.data.sort_column = "ent_ymd DESC";
            	dsJson.transport.read.data.resource_url = $("#resource_url").val();
            	
            	break;
            	
            case "MONTH_CONT":			//신계약정보확인
            	dsJson.transport.read.url = "/presequenttask/contractBasis/getMonthContractList.ajax";
            	dsJson.transport.read.data.sort_column = "seq DESC";
     	        break;
     	        
            case "MONTH_CONT_SHARE":	//계약쉐어
            	dsJson.transport.read.url = "/presequenttask/contractBasis/getMonthContractShareList.ajax";
            	dsJson.transport.read.data.sort_column = "seq DESC";
     	        break;
     	        
            case "EXCEL_CD":
            	dsJson.transport.read.url = "/uploadExcel/getUploadExcelMstList.ajax";
     	        break;
            	
            case "DATA_CD":
            	dsJson.transport.read.url = "/uploadExcel/getUploadExcelMappingMst.ajax";
            	break;
            	
            case "SRC_SUIP_COM_CD":
            	dsJson.transport.read.url = "/inscomcalc/suipcomm/getSrcSuipComCdList.ajax";
            	break;
            
            case "JIGUB_STANDARD_DATA":
            	dsJson.transport.read.url = "/inscomcalc/jigubcomm/getJigubStandardColumns.ajax";
            	break;
            
            case "SUIP_STANDARD_DATA":
            	dsJson.transport.read.url = "/inscomcalc/jigubcomm/getSuipStandardColumns.ajax";
            	break;
            	
            case "PAYROLL_GBN":
            	dsJson.transport.read.url = "/otherfees/getMonthOtherFeesTypeList.ajax";
            	break;
            	
            case "TARGET_OVR_COM_NM":
            	dsJson.transport.read.url = "/inscomcalc/jigubcomm/getJigubOverrideTargetComNmList.ajax";
            	break;
            	
            case "PAYROLL_COM_YM":
            	dsJson.transport.read.url = "/inscomconfirm/payroll/getPayrollComYmList.ajax";
            	break;

			case "PROD_KIND2" :	// 상품구분2
				dsJson.transport.read.url = "/commoncode/getProductgroupKind2.ajax";
				break;
            	
            default:
                dsJson.transport.read.url = "/commoncode/getCommonCode.ajax";
            	dsJson.transport.read.data.grp_cmm_cd = DDC;
                dsJson.transport.read.data.use_yn = "Y";	// 사용중인 공통코드만 조회
                break;
        }
    	
    	dsJson.transport.read.data.CL_STEP_CD = genexon.CL_STEP_CD;
    	dsJson.transport.read.data.ACTION_LOG_YN = "N";
    	
        rtnDs = new kendo.data.DataSource(dsJson);

        genexon.dataSources[dskey] = rtnDs;
    }
    else
    {
        rtnDs = genexon.dataSources[dskey];
    }
    
    return rtnDs;
    
};

genexon.getddcodeArr = function(ddcode){
    
    return genexon.getCommonCodeDataSource(ddcode).data();
};

/**
 * KENDOUI 달력컨트롤 셋팅
 * @param selector jquery selector
 */
genexon.initKendoUI_dtymd = function(selector) {
    $(selector).each(function(index){
        $(this).attr("maxlength",10);
        $(this).css("ime-mode","disabled");
        $(this).keyup(function(evt) {
               var str = this.value.replace(/[^0-9]/g, "");
               if(!genexon.isNumberDash(this.value)) {
                  this.value = str;
                  return false;
               }
               this.value = genexon.formatDate(str, "-");
               genexon.setCursorPosition(this, 999);
               if(evt.keyCode==9){// Tab Key
                   genexon.setCursorSelect(this,999);
               }
               return true;
        });
        $(this).keyup(function(event) {
            if($(this).val().length==10)
            {
                var chk = kendo.parseDate($(this).val());
                if(!chk){
                    $(this).val("");
                    $(this).focus();
                }
            }
        });
        $(this).blur(function() {
            if($(this).val() != "")
            {
                var chk = kendo.parseDate($(this).val());
                if(chk)
                {
                    var oval = $(this).data("kendoDatePicker").value();
                    $(this).data("kendoDatePicker").value(chk);
                    if(!$(this).data("kendoDatePicker").value())
                    {
                        $(this).data("kendoDatePicker").value(oval);
                    }
                }
                else
                {
                    $(this).val("");
                    $(this).focus();
                }
            }
        });
        
        var thisval = $(this).val();
        $(this).val("");
        
        //시작일 변경시
        if($(this).attr("endymd"))
        {
            var startymd = $(this).attr("id");
            var endymd = $(this).attr("endymd");
            
            var startdt = kendo.parseDate($("#"+startymd).val());
            var enddt = kendo.parseDate($("#"+endymd).val());

            if(!(startdt)) startdt = new Date();
            if(!(enddt)) enddt = new Date();
            
            if(!($(this).data("kendoDatePicker"))){
                $(this).kendoDatePicker({
                 value: kendo.parseDate(thisval)
                ,max:enddt
                ,format: "yyyy-MM-dd"
                ,parseFormats: ["yyyy-MM-DD"]
                ,change:function(){
                    var enddtp = $("#"+endymd).data("kendoDatePicker");
                    var startdtp = $("#"+startymd).data("kendoDatePicker");
                    
                    
                    var startDate = startdtp.value();
                    var endDate = enddtp.value();
                    
                    
                    if (startDate) 
                    {
                        startDate = new Date(startDate);
                        if (endDate < startDate) 
                        {
                            startDate.setDate(startDate.getDate());
                            enddtp.value(startDate);
                        } 
                        startDate.setDate(startDate.getDate());
                        enddtp.min(startDate);
                    } else if (endDate) {
                        startdtp.max(new Date(endDate));
                    } else {
                        endDate = new Date();
                        startdtp.max(endDate);
                        enddtp.min(endDate);
                    }
                }
                });
            }
        }
        else if($(this).attr("startymd"))//종료일 변경시
        {
            
            var startymd = $(this).attr("startymd");
            var endymd = $(this).attr("id");

            var startdt = kendo.parseDate($("#"+startymd).val());
            var enddt = kendo.parseDate($("#"+endymd).val());
            
            if(!(startdt)) startdt = new Date();
            if(!(enddt)) enddt = new Date();
            
            $(this).val("");
            
            if(!($(this).data("kendoDatePicker"))){
                $(this).kendoDatePicker({
                    value:kendo.parseDate(thisval)
                   ,min:startdt
                   ,format: "yyyy-MM-dd"
                   ,parseFormats: ["yyyy-MM-DD"]
                   ,change:function(){
                        var enddtp = $("#"+endymd).data("kendoDatePicker");
                        var startdtp = $("#"+startymd).data("kendoDatePicker");

                        var endDate = enddtp.value();
                        var startDate = startdtp.value();

                        if (endDate) {
                            endDate = new Date(endDate);
                            if (endDate < startDate) 
                            {
                                endDate.setDate(endDate.getDate());
                                startdtp.value(endDate);
                            } 
                            endDate.setDate(endDate.getDate());
                            startdtp.max(endDate);
                        } 
                        else if (startDate) {
                            enddtp.min(new Date(startDate));
                        } 
                        else {
                            endDate = new Date();
                            startdtp.max(endDate);
                            enddtp.min(endDate);
                        }
                    }
                });
            }
        }
        else 
        {
            if(!($(this).data("kendoDatePicker"))){
                $(this).kendoDatePicker({
                	max: "9999-12-31",
                	format: "yyyy-MM-dd"
               });
            }
        }
        
        $(this).kendoMaskedTextBox({ mask : "0000-00-00" });

    });
};


/**
 * 년월일로 붙어있는 스트링을 날짜형식으로 리턴
 * @param val
 * @returns
 */
genexon.dateformat = function(val){
	var rtnStr = null;
	if(val.length == 6)
	{
		rtnStr = kendo.toString(kendo.parseDate(val,"yyyyMM"),"yyyy-MM", kendo.culture("ko-KR"));
	}
	else if(val.length == 8)
	{
		rtnStr = kendo.toString(kendo.parseDate(val,"yyyyMMdd"),"yyyy-MM-dd", kendo.culture("ko-KR"));
	}
	
	if(rtnStr==null) return "";
	
	return rtnStr;
};

/**
 * 컨트롤 동적 생성시 사용할 랜덤id 발생
 * @returns {String}
 */
genexon.genID = function(){
	return "gen"+kendo.toString(Math.random()*100000000,"#############");
};
/**
 * KENDOUI 월력컨트롤 셋팅
 * @param selector jquery selector
 */
genexon.initKendoUI_dtym = function(selector) {
	$(selector).each(function(index){
		var tgtObj = $(this);
		tgtObj.hide();
		
		var genid = genexon.genID();

		var ctl = "<input id='"+genid+"' value='"+genexon.dateformat($(this).val())+"'/>";
		tgtObj.parent().append(ctl);
		
		var ymobj = $("#"+genid);
		
		ymobj.width($(this).width()); 
		
		
		ymobj.attr("maxlength",7);
		ymobj.css("ime-mode","disabled");
		ymobj.keyup(function(evt) {
            var str = this.value.replace(/[^0-9]/g, "");
            if(!genexon.isNumberDash(this.value+"-01")) {
                this.value = str;
                return false;
            }
            this.value = genexon.formatDate(str, "-");
            genexon.setCursorPosition(this, 999);
            if(evt.keyCode==9){// Tab Key
                genexon.setCursorSelect(this,999);
            }
            return true;
        });
		ymobj.keyup(function(event) {
            if(ymobj.val().length==7)
            {
                var chk = kendo.parseDate(ymobj.val()+"-01");
                if(!chk){
                	ymobj.val("");
                	ymobj.focus();
                }
            }
        });
		
		var thisval = $(this).val();
		if(!(thisval)) thisval = new Date();
		
		tgtObj.val(kendo.toString(thisval,"yyyy-MM"));
		
    	ymobj.kendoDatePicker({
    		value: kendo.toString(thisval,"yyyy-MM"),
            // defines the start view
            start: "year",
            // defines when the calendar should return date
            depth: "year",
            // display month and year in the input
            format: "yyyy-MM",
            change:function(){
            	tgtObj.val(kendo.toString(this.value(),"yyyyMM"));
            	tgtObj.trigger("change");
            }
            
        });
        
    	
    });
	
	//$(selector).closest("span.k-datepicker").width(100);
	//ymobj.closest("span.k-datepicker").width(100);
};

/**
 * KENDOUI 월력컨트롤 셋팅 (yyyy-mm)
 * @param selector jquery selector
 */
genexon.initKendoUI_dty_m = function(selector) {
	$(selector).each(function(index){
		var tgtObj = $(this);
		tgtObj.hide();
		
		var genid = genexon.genID();
		var ctl = "<input id='"+genid+"' value='"+genexon.dateformat($(this).val())+"'/>";
		tgtObj.parent().append(ctl);
		
		var ymobj = $("#"+genid);
		
		ymobj.width($(this).width()); 
		
		
		ymobj.attr("maxlength",7);
		ymobj.css("ime-mode","disabled");
		ymobj.keyup(function(evt) {
            var str = this.value.replace(/[^0-9]/g, "");
            if(!genexon.isNumberDash(this.value+"-01")) {
                this.value = str;
                return false;
            }
            this.value = genexon.formatDate(str, "-");
            genexon.setCursorPosition(this, 999);
            if(evt.keyCode==9){// Tab Key
                genexon.setCursorSelect(this,999);
            }
            return true;
        });
		ymobj.keyup(function(event) {
            if(ymobj.val().length==7)
            {
                var chk = kendo.parseDate(ymobj.val()+"-01");
                if(!chk){
                	ymobj.val("");
                	ymobj.focus();
                }
            }
        });
		
		var thisval = $(this).val();
		if(!(thisval)) thisval = new Date();
		
		tgtObj.val(kendo.toString(thisval,"yyyy-MM"));
		
    	ymobj.kendoDatePicker({
    		value: kendo.toString(thisval,"yyyy-MM"),
            // defines the start view
            start: "year",
            // defines when the calendar should return date
            depth: "year",
            // display month and year in the input
            format: "yyyy-MM",
            change:function(){
            	tgtObj.val(kendo.toString(this.value(),"yyyy-MM"));
            	tgtObj.trigger("change");
            }
            
        });
        
    	
    });
	
	//$(selector).closest("span.k-datepicker").width(100);
	//ymobj.closest("span.k-datepicker").width(100);
};

/**
 * KENDOUI 버튼 셋팅
 * @param selector jquery selector
 */
genexon.initKendoUI_btn = function(selector) {
	$(selector).kendoButton();
};



genexon.initKendoUI_dataSource = function(ds) {
	if (ds.mode == "CRUD") {
		if (ds.transport.parameterMap == undefined) {
			ds.transport.parameterMap = function(options, operation) {
				if (operation !== "read" && options.models) {
					if (options.models.length > 0) {
						var comParam = "";
						if (options.models[0].COM_YM != undefined) {

							var idxComym = this.options[operation].url
									.indexOf("COM_YM=");
							if (idxComym < 0) {
								if (this.options[operation].url.indexOf("?") > 0) {
									comParam = "&CL_STEP_CD="
											+ genexon.CL_STEP_CD + "&COM_YM="
											+ options.models[0].COM_YM;
								} else {
									comParam = "?CL_STEP_CD="
											+ genexon.CL_STEP_CD + "&COM_YM="
											+ options.models[0].COM_YM;
								}
								this.options[operation].url = this.options[operation].url
										+ comParam;
								this.setup.arguments[0].url = this.options[operation].url;

								// alert(this.setup.arguments[0].url);
								options.CL_STEP_CD = genexon.CL_STEP_CD;
								options.COM_YM = options.models[0].COM_YM;

							}

						}
					}
					return kendo.stringify(options);
				} else {
					return options;
				}

			};
		}
		ds.batch = true;
	}

	if (ds.transport != undefined) {
		if (ds.transport.read != undefined) {
			ds.transport.read.dataType = "json";
			if (ds.transport.read.type == undefined)
				ds.transport.read.type = "POST";
			ds.transport.read.cash = false;
		}

		if (ds.transport.update != undefined) {
			ds.transport.update.dataType = "json";
			if (ds.transport.update.type == undefined)
				ds.transport.update.type = "POST";

			ds.transport.update.cash = false;
			ds.transport.update.contentType = "application/json;charset=UTF-8";
		}

		if (ds.transport.destroy != undefined) {
			ds.transport.destroy.dataType = "json";
			if (ds.transport.destroy.type == undefined)
				ds.transport.destroy.type = "POST";
			ds.transport.destroy.cash = false;
			ds.transport.destroy.contentType = "application/json;charset=UTF-8";
		}

		if (ds.transport.create != undefined) {
			ds.transport.create.dataType = "json";
			if (ds.transport.create.type == undefined)
				ds.transport.create.type = "POST";
			ds.transport.create.cash = false;
			ds.transport.create.contentType = "application/json;charset=UTF-8";
		}
	}
	if (ds.schema == undefined)
		ds.schema = {};

	ds.schema.data = "results";

	if (ds.serverPaging) {

		if(ds.pageSize == undefined) ds.pageSize = 20;
		if (ds.schema.total == undefined) {
			ds.schema.total = function(response) {
				if (response.results == undefined)
					return 0;
				if (response.results.length == 0)
					return 0;
				
				//vo로 받지 않고 맵으로 받으면 mapper에서 컬럼을 소문자로 하더라도 넘겨받는 키 값이 대문자로 넘어옴. 그래서 변경
				if(response.results[0].total == undefined) {
					return response.results[0].TOTAL; // total is returned in the "TOTAL" field of the response
				} else {
					return response.results[0].total; // total is returned in the "total" field of the response
				}
			};
		}
	} else {
		if (ds.schema.total == undefined) {
			ds.schema.total = function(response) {
				if (response.results == undefined)
					return 0;
				if (response.results.length == 0)
					return 0;
				return response.results.length; // total is returned in the
												// "total" field of the response
			};
		}
	}

	var rtnDs = new kendo.data.DataSource(ds);
	genexon.bindDataSourceAJAXEvent(rtnDs);

	return rtnDs;
};

genexon.initKendoUI_TreeListDataSource = function(ds) {
	if (ds.mode == "CRUD") {
		if (ds.transport.parameterMap == undefined) {
			ds.transport.parameterMap = function(options, operation) {
				if (operation !== "read" && options.models) {
					if (options.models.length > 0) {
						var comParam = "";
						if (options.models[0].COM_YM != undefined) {

							var idxComym = this.options[operation].url
									.indexOf("COM_YM=");
							if (idxComym < 0) {
								if (this.options[operation].url.indexOf("?") > 0) {
									comParam = "&CL_STEP_CD="
											+ genexon.CL_STEP_CD + "&COM_YM="
											+ options.models[0].COM_YM;
								} else {
									comParam = "?CL_STEP_CD="
											+ genexon.CL_STEP_CD + "&COM_YM="
											+ options.models[0].COM_YM;
								}
								this.options[operation].url = this.options[operation].url
										+ comParam;
								this.setup.arguments[0].url = this.options[operation].url;

								// alert(this.setup.arguments[0].url);
								options.CL_STEP_CD = genexon.CL_STEP_CD;
								options.COM_YM = options.models[0].COM_YM;

							}

						}
					}
					return kendo.stringify(options);
				} else {
					return options;
				}

			};
		}
		ds.batch = true;
	}

	if (ds.transport != undefined) {
		if (ds.transport.read != undefined) {
			ds.transport.read.dataType = "json";
			if (ds.transport.read.type == undefined)
				ds.transport.read.type = "POST";
			ds.transport.read.cash = false;
		}

		if (ds.transport.update != undefined) {
			ds.transport.update.dataType = "json";
			if (ds.transport.update.type == undefined)
				ds.transport.update.type = "POST";

			ds.transport.update.cash = false;
			ds.transport.update.contentType = "application/json;charset=UTF-8";
		}

		if (ds.transport.destroy != undefined) {
			ds.transport.destroy.dataType = "json";
			if (ds.transport.destroy.type == undefined)
				ds.transport.destroy.type = "POST";
			ds.transport.destroy.cash = false;
			ds.transport.destroy.contentType = "application/json;charset=UTF-8";
		}

		if (ds.transport.create != undefined) {
			ds.transport.create.dataType = "json";
			if (ds.transport.create.type == undefined)
				ds.transport.create.type = "POST";
			ds.transport.create.cash = false;
			ds.transport.create.contentType = "application/json;charset=UTF-8";
		}
	}
	if (ds.schema == undefined)
		ds.schema = {};

	ds.schema.data = "results";

	if (ds.serverPaging) {
		ds.pageSize = 20;
		if (ds.schema.total == undefined) {
			ds.schema.total = function(response) {
				if (response.results == undefined)
					return 0;
				if (response.results.length == 0)
					return 0;
				return response.results[0].total; // total is returned in the
													// "total" field of the
													// response
			};
		}
	} else {
		if (ds.schema.total == undefined) {
			ds.schema.total = function(response) {
				if (response.results == undefined)
					return 0;
				if (response.results.length == 0)
					return 0;
				return response.results.length; // total is returned in the
												// "total" field of the response
			};
		}
	}

	var rtnDs = new kendo.data.TreeListDataSource(ds);
	genexon.bindDataSourceAJAXEvent(rtnDs);

	return rtnDs;
};

/**
 * KENDOUI GRID 세팅
 * @param selector jquery selector , var gridsetString , dataSource ds, height
 */
genexon.initKendoUI_grid = function(selector, pt,  ds, height) 
{
    var grid = null;
    if (typeof selector == "object")
    {
        grid = selector;
    }
    else
    {
        grid = $(selector);
    }
    
    if(height == null){
    	var windowHeight = top.$("#gnb").height() - (top.$("#header").height()+top.$("#tab_bro").height()+top.$("#footer").height() + 50) - ($("#wrap").parent("div").outerHeight() + 16 - $(".resizegrid").height());
    	pt.height = windowHeight;
    }else if(height == "popup"){
    	var popHeight = $(this).height() - 20 - ($("#wrappop").parent("div").outerHeight() - $(".resizegrid").height());
    	pt.height = popHeight;
    }else{
    	pt.height = height;
    }

	if(pt.autoBind == undefined)pt.autoBind =  false;
	if(pt.sortable == undefined)pt.sortable =  true;
	if(pt.groupable == undefined)pt.groupable =  true;
	if(pt.reorderable == undefined)pt.reorderable =  true;
	if(pt.scrollable == undefined) pt.scrollable =  true;
	if(pt.resizable == undefined)pt.resizable =  true;
	if(pt.filterable == undefined)pt.filterable =  false;
	if(pt.columnMenu == undefined)pt.columnMenu =  false;
	if((pt.pageable == undefined) || (pt.pageable == true)){
	    pt.pageable =  {refresh:false, pageSizes:[10 , 20, 30, 40, 50, 100, 1000, 3000, 5000, 10000], buttonCount:10,
	    				messages: {
							display:"{0}~{1} / 총 : {2}건",
							empty:"데이터가 없습니다.",
							allPages: "전체보기",
							page:"페이지 이동",
							of: "{0}~",
							itemsPerPage:"건 출력",
							first:"첫 페이지",
							previous:"이전 페이지",
							next:"다음 페이지",
							last:"마지막 페이지",
							refresh:"새로고침"
	    				}
	    };
	    ds.pageSize = (ds.pageSize == undefined)?20:ds.pageSize;
	}else{
		/* pt.pageable == false 인 경우만 여기(else)에 온다고 생각하고 만듦 */
		ds.pageSize = null;
	}
	if(pt.selectable == undefined)pt.selectable = true;
	
	ds = genexon.initKendoUI_dataSource(ds);
	pt.dataSource =  ds;
	
	genexon.initKendoUI_grid_kor(pt);
//	
//	pt.dataBound = function(e){
//	    if((pt.selectable)&&(e.dataSource.total()>0))
//	    {
//	        e.sender.select(selector + " tr:eq(2)");
//	    }
//    };

    grid.kendoGrid(pt);
	
    var gd = $(selector).data("kendoGrid");

    var EvtSelect = function(e){

        if(e.sender.pager)
        {
            if(e.sender.pager.totalPages() < e.sender.pager.page())
            {
                e.sender.pager.page(1);
            }
        }
        if((pt.selectable)&&(e.sender.dataSource.data().length>0))
        {
            if(pt.groupable){
                e.sender.select(selector + " tr:eq(2)");
            }
            else{
                e.sender.select(selector + " tr:eq(1)");
            }
        }
    };
    
    
     
    if(gd._events.dataBound)
    {
        gd._events.dataBound.unshift(EvtSelect);
    }
    else
    {
        gd.bind("dataBound", EvtSelect);
    }
    
};



/**
 * KENDOUI GRID 언어 설정 
 * @param var gridsetString 
 */
genexon.initKendoUI_grid_kor = function(pt) {
	
    if(pt.groupable) pt.groupable = {messages:{empty: "그룹화할 컬럼을 여기에 드래그하세요. "}};
    
    if(pt.columnMenu) pt.columnMenu = {messages:{columns:"컬럼", done:"완료", filter:"필터",lock:"고정",unlock:"고정해제",settings:"설정",sortAscending:"오름차순",sortDescending:"내림차순"}};
	if(pt.filterable)
	{
    	pt.filterable = {messages:{and:"그리고",or:"또는",cancel:"취소",clear:"초기화",filter:"검색",info:"조건",isFalse:"다르다",isTrue:"같다",operator:"연산자",selectValue:"선택값",value:"값"}};
    	pt.filterable.operators = {string:{contains:"포함",eq:"같다",neq:"같지않다",startswith:"~로시작하는",endswith:"~로 끝나는"},
    							   number:{eq:"같다",neq:"같지않다",gte:"크거나 같음",gt:"크다",lte:"작거나 같음",lt:"작다"},
    							   date:{eq:"같다",neq:"같지않다",gte:"크거나 같음",gt:"크다",lte:"작거나 같음",lt:"작다"},
    							   enums:{eq:"같다",neq:"같지않다"}
    							   };
    }
};

/**
 * KENDOUI GRID INLINE  세팅
 * @param selector jquery selector , var gridsetString , dataSource ds, height
 */
genexon.initKendoUI_grid_inlineEdit = function(selector, pt,  ds, height) {
    ds.mode="CRUD";
        
	if(pt.selectable	== undefined) pt.selectable	= "row";
	if(pt.navigatable	== undefined) pt.navigatable	= true;
    if(pt.resizable 	== undefined) pt.resizable 	=  true;
	if(pt.scrollable 	== undefined) pt.scrollable 	= true;
	if(pt.autoBind 	    == undefined) pt.autoBind 		=  false;
	if(pt.sortable 	    == undefined) pt.sortable 		=  true;
	if(pt.groupable 	== undefined) pt.groupable 	=  false;
	if(pt.filterable	== undefined) pt.filterable	=  false;
	if(pt.columnMenu 	== undefined) pt.columnMenu 	=  false;
	if(pt.reorderable 	== undefined) pt.reorderable 	=  true;
	if(pt.itemsPerPage 	== undefined) pt.itemsPerPage 	=  "건 출력";
	if(pt.buttonCount 	== undefined) pt.buttonCount 	=  10;
	
	if(pt.pageable) {
        pt.pageable =  {refresh:false, pageSizes:[10 , 20, 30, 40, 50, 100, 1000, 3000, 5000, 10000], buttonCount:pt.buttonCount,
						messages: {
							display:"{0}~{1} / 총 {2}건",
							empty:"데이터가 없습니다.",
							allPages: "전체보기",
							page:"페이지 이동",
							of: "{0}~",
							itemsPerPage:pt.itemsPerPage,
							first:"첫 페이지",
							previous:"이전 페이지",
							next:"다음 페이지",
							last:"마지막 페이지",
							refresh:"새로고침"
						}
        };
        
        ds.pageSize = (ds.pageSize == undefined) ? 20:ds.pageSize;
    }
	
	genexon.initKendoUI_grid_kor(pt);
	
    ds = genexon.initKendoUI_dataSource(ds);
    if(pt.dataSource == undefined)pt.dataSource =  ds;

    $(selector).kendoGrid(pt);
    
    var gd = $(selector).data("kendoGrid");
    
    var EvtSelect = function(e) {
    	//그리드 내에 입력, 수정, 삭제 권한 부여
    	var in_autr_type = $("#in_autr_type").val();
    	var up_autr_type = $("#up_autr_type").val();
    	var de_autr_type = $("#de_autr_type").val();
    	
    	
    	//그리드 내 입력, 수정, 삭제 버튼
    	var in_autr_type_gd_btn = e.sender.element.find(".in_autr_type");
    	var up_autr_type_gd_btn = e.sender.element.find(".up_autr_type");
    	var de_autr_type_gd_btn = e.sender.element.find(".de_autr_type");
    	
    	//마감코드 및 마감월
    	var cls_resource_id = $("#cls_resource_id").val();
    	var com_ym = $("#srch_com_ym") != undefined ? $("#srch_com_ym").val() : ($("#com_ym") != undefined ? $("#com_ym").val() : "");
    	var closeMonthData = new Object();
    	    	
    	if(in_autr_type_gd_btn) {
    		//입력버튼 권한 처리
    		in_autr_type_gd_btn.bind("click", function(e) {
    			//권한 우선 검사
    			if(in_autr_type != "Y") {
    				genexon.alert("info", "입력불가", "입력 권한이 없습니다.");
        			
            		e.preventDefault();
            		e.stopPropagation();
        		}else {
        			if(cls_resource_id != "") {
        	    		closeMonthData = genexon.getCloseMonthData(com_ym, cls_resource_id);
        			}
        			
        			//그 다음 마감 여부
        			//그리드 내 버튼 클릭 시 마감처리 부분
            		if(closeMonthData != null && closeMonthData.cl_state_cd == "CLS") {
            			genexon.alert("info", closeMonthData.cl_step_nm, "마감되었습니다.");
            			
                		e.preventDefault();
                		e.stopPropagation();
            		}
        		}
    		});
    	}
    	
    	if(up_autr_type_gd_btn) {
        	//수정버튼 권한 처리
			up_autr_type_gd_btn.bind("click", function(e) {
				//권한 우선 검사
    			if(up_autr_type != "Y") {
    				genexon.alert("info", "수정불가", "수정 권한이 없습니다.");
        			
            		e.preventDefault();
            		e.stopPropagation();
        		}else {
        			if(cls_resource_id != "") {
        	    		closeMonthData = genexon.getCloseMonthData(com_ym, cls_resource_id);
        			}
        			
        			//그 다음 마감 여부
        			//그리드 내 버튼 클릭 시 마감처리 부분
            		if(closeMonthData != null && closeMonthData.cl_state_cd == "CLS") {
            			genexon.alert("info", closeMonthData.cl_step_nm, "마감되었습니다.");
            			
                		e.preventDefault();
                		e.stopPropagation();
            		}
        		}
    		});
    	}
    	
    	if(de_autr_type_gd_btn) {
    		//그리드 내 버튼 클릭 시 마감처리 부분
			de_autr_type_gd_btn.bind("click", function(e) {
				//권한 우선 검사
    			if(de_autr_type != "Y") {
    				genexon.alert("info", "삭제불가", "삭제 권한이 없습니다.");
        			
            		e.preventDefault();
            		e.stopPropagation();
        		}else {
        			if(cls_resource_id != "") {
        	    		closeMonthData = genexon.getCloseMonthData(com_ym, cls_resource_id);
        			}
        			
        			//그 다음 마감 여부
        			//그리드 내 버튼 클릭 시 마감처리 부분
            		if(closeMonthData != null && closeMonthData.cl_state_cd == "CLS") {
            			genexon.alert("info", closeMonthData.cl_step_nm, "마감되었습니다.");
            			
                		e.preventDefault();
                		e.stopPropagation();
            		}
        		}
    		});
    	}
    	
    	//서버페이징 처리?
    	if(e.sender.pager) {
            if(e.sender.pager.totalPages() < e.sender.pager.page()) {
                e.sender.pager.page(1);
            }
        }
    	
        if((pt.selectable)&&(e.sender.dataSource.data().length>0)) {
            if(pt.groupable){
                e.sender.select(selector + " tr:eq(2)");
            }else{
                e.sender.select(selector + " tr:eq(1)");
            }
        }
    };
    
    if(gd._events.dataBound)
    {
        gd._events.dataBound.unshift(EvtSelect);
    }
    else
    {
        gd.bind("dataBound", EvtSelect);
    }
};

/**
 * KENDOUI TreeList  세팅
 * @param selector jquery selector , var gridsetString , dataSource ds
 */
genexon.initKendoUI_treeList = function(selector, pt,  ds) {
    ds.mode="CRUD";
    
	if(pt.selectable	== undefined) pt.selectable	= "row";
	if(pt.navigatable	== undefined) pt.navigatable	= true;
    if(pt.resizable 	== undefined) pt.resizable 	=  true;
	if(pt.scrollable 	== undefined) pt.scrollable 	= true;
	if(pt.autoBind 	    == undefined) pt.autoBind 		=  false;
	if(pt.sortable 	    == undefined) pt.sortable 		=  false;
	if(pt.groupable 	== undefined) pt.groupable 	=  false;
	if(pt.filterable	== undefined) pt.filterable	=  false;
	if(pt.columnMenu 	== undefined) pt.columnMenu 	=  false;
	if(pt.reorderable 	== undefined) pt.reorderable 	=  true;
	
	if(pt.pageable)
    {
        pt.pageable =  {refresh:false, pageSizes:[10 , 20, 30, 40, 50, 100, 1000, 3000, 5000, 10000], buttonCount:10};
        ds.pageSize = 20;
    }
	
    ds = genexon.initKendoUI_TreeListDataSource(ds);
    if(pt.dataSource == undefined)pt.dataSource =  ds;

	
//	pt.dataBound = function(e){
//        if((pt.selectable)&&(e.dataSource.total()>0))
//        {
//            e.sender.select(selector + " tr:eq(1)");
//        }
//        
//    };

    $(selector).kendoTreeList(pt);
    
    var gd = $(selector).data("kendoTreeList");

    var EvtSelect = function(e){
        if((pt.selectable)&&(e.sender.dataSource.data().length>0)) {
            if(pt.groupable){
                e.sender.select(selector + " tr:eq(2)");
            }else {
                e.sender.select(selector + " tr:eq(1)");
            }
        }
    };
    
    if(gd._events.dataBound) {
        gd._events.dataBound.unshift(EvtSelect);
    }else {
        gd.bind("dataBound", EvtSelect);
    }
    
};

/**
 * KENDOUI UPLOAd 세팅
 * @param selector jquery selector , var gridsetString , dataSource ds
 */
genexon.initKendoUI_upload = function(selector, pt) {
    pt.localization= {
        select: "선택",
        remove: "삭제",
        dropFilesHere : "여기에 파일을 올려놓으세요",
        uploadSelectedFiles: "파일선택",
        statusUploading: "전송중입니다",
        statusUploaded: "전송이 완료되었습니다.",
        statusFailed: "전송이 실패하였습니다.",
        headerStatusUploading: "파일을 처리중입니다.",
        headerStatusUploaded: "완료"
    };
    
    if(pt.error == undefined) {
    	pt.error = ajaxError;
        var errFunc = pt.error;
        
    	var ajaxError = function(e){
    		genexon.ajaxError(e);
    		errFunc(e);
    	};
    }
		
	$(selector).kendoUpload(pt);
};



genexon.bindDataSourceAJAXEvent = function(ds)
{
    
    if(ds._events.sync)
    {
        ds._events.sync.unshift(genexon.sync);
    }
    else
    {
        ds.bind("sync", genexon.sync);
    }
    
    
	if(ds._events.error)
	{
	    ds._events.error.unshift(genexon.ajaxError);
//	    var errFunc=ds._events.error[0];
//	    ds._events.error[0] = function(e){
//	        genexon.ajaxError(e);
//	        errFunc(e);
//	    }
	    
	}
	else
	{
	    ds.bind("error", genexon.ajaxError);
	}
	
    if(ds._events.requestStart)
    {
        ds._events.requestStart.unshift(genexon.ajaxStart);
    }
    else
    {
        ds.bind("requestStart", genexon.ajaxStart);
    }
    
    if(ds._events.requestEnd)
    {
        ds._events.requestEnd.unshift(genexon.ajaxEnd);
    }
    else
    {
        ds.bind("requestEnd", genexon.ajaxEnd);
    }
};


genexon.ajaxError = function(e) {
	kendo.ui.progress($("body"), false);
	
	var res= null;
	if(e.XMLHttpRequest)
	{
		res = e.XMLHttpRequest;
	}
	else if(e.xhr)
	{
		res = e.xhr;
		e.sender.cancelChanges();
	}else if(e.status) {
		//켄도 dataSource 아니고 일반 ajax 문법에러 일때
		res = e;
	}
	else
	{
		return;
	}


	switch (res.status) 
	{
		case 401: // 로그인 세션이 끊겼을때
			//location.href = "/j_spring_security_logout";
			location.href = "/login.do";
		    
		    //location.reload();
			break;
		case 403: //  권한없을때
			alert("요청하신 서비스에 권한이 없거나 장시간 미사용으로 로그아웃 되었습니다. 로그인화면으로 이동합니다.");
			location.href = "/login.go";
			
			break;
		
		case 404: // 찾을 수 없음
		    genexon.alert("error","시스템 오류","요청 서비스를 찾을수 없습니다.");
			break;
		
		case 500: //내부 서버 오류
			//var pData = JSON.parse(res.responseText.replace(/\\'/g, "'"));
			var pData = JSON.parse(res.responseText);
			genexon.alert("error","시스템 오류",pData.errmsg);
			
		default:
			break;
	}
};

genexon.ajaxStart = function(e)
{
    //kendo.ui.progress($("body"), true);
	
};

genexon.ajaxEnd = function(e)
{
    //kendo.ui.progress($("body"), false);
    if(e.type!="read")
    {
        //alert("genexon.ajaxEnd");
    }

};

genexon.sync = function(e)
{
    genexon.alert("success","데이터처리","정상 처리되었습니다.");
};
/**
 * kendoui window Open
 * @param pID     생성될ID
 * @param pTitle  윈도우TITLE
 * @param pURL    오픈할 URL
 * @param pWidth  윈도우 너비
 * @param pHeight 윈도우 높이
 * @param pModal  모달여부
 * @param resizable 크기조절가능여부
 @ @param callbackFunc 콜백함수
 */
//genexon.PopWindowOpen = function(pID,pTitle,pURL,pWidth,pHeight,pModal)
genexon.PopWindowOpen = function(argArr)
{
	genexon.openwindowID = argArr.pID;
	var lbody = genexon.body;
    
    if(argArr.body) {
        lbody = argArr.body;
    }
    
    var wd = lbody.find("#"+argArr.pID);
    
    if(wd.length > 0) {
        if(wd.data("kendoWindow")) {
            wd.data("kendoWindow").close();
            wd.data("kendoWindow").destroy();
        }
        wd.remove();
    }
    
    lbody.append("<div id='"+argArr.pID+"' style='display:none;'></div>");
    wd = lbody.find("#"+argArr.pID);
    
    if(argArr.resizable == undefined) argArr.resizable = false;
    
    if(argArr.position == undefined) argArr.position = {top: "0px", left: "200px"};
    var JSON_STRING = null;
    
    if(argArr.data != undefined){
    	JSON_STRING = genexon.getJSONString(argArr.data);
    	
    	if(argArr.pURL.indexOf("?") > 0) {
    		argArr.pURL = argArr.pURL + "&json_string=" + encodeURIComponent(JSON_STRING);
    	} else {
    		argArr.pURL = argArr.pURL + "?json_string=" + encodeURIComponent(JSON_STRING);
    	}
    }

    if(argArr.actions == undefined) argArr.actions = [ "Maximize",  "Close"];
    
    wd.kendoWindow({
        actions: argArr.actions,
        title:argArr.pTitle,
        content: argArr.pURL,
        iframe: true,
        resizable: argArr.resizable,
        width:argArr.pWidth,
        height:argArr.pHeight,
        modal: argArr.pModal,
        close:function(e){
        	if(argArr.pClose == true && e.userTriggered == true){ // pClose와 userTriggered(창닫기 버튼을 클릭했을시 true)가 트루일때 genexon.confirm를 실행
				e.preventDefault();
				
				genexon.confirm("엑셀업로드", "변경내용을 저장하지 않고 창을 닫으시겠습니까?", function(result) {
					if (result == true) {
						wd.data("kendoWindow").close();
			        	wd.remove();
					}
				});
        	}else if(argArr.pClose == "srch" && e.userTriggered == true){
        		srch();
        	}else{
            	if(argArr.callbackFunc != undefined) {
                    argArr.callbackFunc();
                }
        	}
        }
    });
    
    var kwd = wd.data("kendoWindow");
    kwd.center();
    
    //오픈 전에 창크기를 최대화 할 것인지
    if(argArr.maximize) {
    	kwd.toggleMaximization();
    }
    
    kwd.open();

};

/**
 * kendoui window Open
 * @param pID     생성될ID
 * @param pTitle  윈도우TITLE
 * @param pURL    오픈할 URL
 * @param pWidth  윈도우 너비
 * @param pHeight 윈도우 높이
 * @param pModal  모달여부
 * @param resizable 크기조절가능여부
 @ @param callbackFunc 콜백함수
 */
genexon.PopWindowOpenExcelUpload = function(argArr) {
	genexon.openwindowID = argArr.pID;
	var lbody = genexon.body;

	if (argArr.body) {
		lbody = argArr.body;
	}

	var wd = lbody.find("#" + argArr.pID);

	if (wd.length > 0) {
		if (wd.data("kendoWindow")) {
            wd.data("kendoWindow").close();
			wd.data("kendoWindow").destroy();
		}
		wd.remove();
	}

	lbody.append("<div id='" + argArr.pID + "' style='display:none;'></div>");
	wd = lbody.find("#" + argArr.pID);

	if (argArr.resizable == undefined)
		argArr.resizable = false;

	var JSON_STRING = null;
	if (argArr.data != undefined) {
		JSON_STRING = genexon.getJSONString(argArr.data);
		
    	if(argArr.pURL.indexOf("?") > 0) {
    		argArr.pURL = argArr.pURL + "&json_string=" + encodeURI(JSON_STRING);
    	} else {
    		argArr.pURL = argArr.pURL + "?json_string=" + encodeURI(JSON_STRING);
    	}
	}
	
	var pClose = true;
	
	wd.kendoWindow({
		title : argArr.pTitle,
		content : argArr.pURL,
		iframe : true,
		resizable : argArr.resizable,
		width : argArr.pWidth,
		height : argArr.pHeight,
		modal : argArr.pModal,
		close : function(e) {
        	if(pClose == true && e.userTriggered == true){
				e.preventDefault();
				genexon.confirm("엑셀업로드", "변경내용을 저장하지 않고 창을 닫으시겠습니까?", function(
						result) {
					if (result == true) {
						if (argArr.callbackFunc != undefined) {
							argArr.callbackFunc();
						}
						wd.data("kendoWindow").close();
			        	wd.remove();
					}
				});
        	}else{
            	if(argArr.callbackFunc != undefined) {
                    argArr.callbackFunc();
                }
        	}		
		}
	});

	var kwd = wd.data("kendoWindow");
	kwd.center();
	if(argArr.maximize) {
    	kwd.toggleMaximization();
    }
	kwd.open();

};

/** 사원찾기 용 윈도우 팝업 열기 */
genexon.PopWindowOpenEmpSch = function(pID,pTitle,pURL,pWidth,pHeight,pModal,cbFunc)
{
    if($("#"+pID).length == 0)
    {
        $("body").append("<div id='"+pID+"' style='display:none;'></div>");
    }
    
    genexon.callBackFunc = cbFunc;
    genexon.openwindowID = pID;
    
    $("#"+pID).kendoWindow({
        actions: [ "Maximize",  "Close"],
        title:pTitle,
        content: pURL,
        iframe: true,
        resizable: false,
        width:pWidth,
        height:pHeight,
        modal: pModal,
        close:function(e){
            $("#"+pID).remove();
        }
    });
    
    var wd = $("#"+pID).data("kendoWindow");
    wd.center();
    wd.open();

};

/**
 * kendoui window Close
 * @param pID     윈도우ID
 */
genexon.PopWindowClose = function(pID)
{
    $("#"+pID).data("kendoWindow").close();

};

/**
 * kendoui window Close2
 * @param pID     윈도우ID
 */
genexon.PopWindowClose2 = function(divId){
	divId = divId.replace('#', '');
	var wd = genexon.body.find("#" + divId);
	var kwd = wd.data("kendoWindow");

    return kwd.close();

};

/**
 * 화면 하단 iframe오픈
 * @param pID     윈도우ID
 */
genexon.subDetail = function(URL)
{
    if($("#ifmDetail").length == 0) return;
    $("#ifmDetail").height(0);
    //$("#frmSubDetail").attr("action",URL);
    //$("#frmSubDetail").submit();
    
    $("#ifmDetail").attr("src",URL);
    $("#ifmDetail").load(function(){
        var ifmHeight = $(this).contents().find("body")[0].scrollHeight;
        $(this).height(ifmHeight);
        $(this).width($(this).contents().find("body")[0].scrollWidth);
        
    });
};

genexon.getKendoModelfromForm = function(frm)
{
    return new kendo.data.Model(genexon.serializeFormObject(frm));
};
genexon.serializeFormObject = function(form)
{
     function trim(str)
     {
         return str.replace(/^\s+|\s+$/g,"");
     }

     var o = {};
     var a = $(form).serializeArray();

     $.each(a, function() {
         var nameParts = this.name.split('[');
         if (nameParts.length == 1) {
             // New value is not an array - so we simply add the new
             // value to the result object
             if (o[this.name] !== undefined) {
                 if (!o[this.name].push) {
                     o[this.name] = [o[this.name]];
                 }
                 o[this.name].push(this.value || '');
             } else {
                 o[this.name] = this.value || '';
             }
         }
         else {
             // New value is an array - we need to merge it into the
             // existing result object
             $.each(nameParts, function (index) {
                 nameParts[index] = this.replace(/\]$/, '');
             });

             // This $.each merges the new value in, part by part
             var arrItem = this;
             var temp = o;
             $.each(nameParts, function (index) {
                 var next;
                 var nextNamePart;
                 if (index >= nameParts.length - 1)
                     next = arrItem.value || '';
                 else {
                     nextNamePart = nameParts[index + 1];
                     if (trim(this) != '' && temp[this] !== undefined)
                         next = temp[this];
                     else {
                         if (trim(nextNamePart) == '')
                             next = [];
                         else
                             next = {};
                     }
                 }

                 if (trim(this) == '') {
                     temp.push(next);
                 } else
                     temp[this] = next;

                 temp = next;
             });
         }
     });
     return o;
};

genexon.initParentBody = function(){
    if(parent.genexon.infoNoti != undefined){
        genexon.body = parent.genexon.body;
        return;
    }
    
    genexon.body = $("body");
    
};


genexon.initKendoUI_notification = function(){
    if(parent.genexon.infoNoti != undefined){
        genexon.successNoti = parent.genexon.successNoti;
        genexon.infoNoti = parent.genexon.infoNoti;
        genexon.errorNoti = parent.genexon.errorNoti;
        
        return;
    }
    //notification 초기화
    $("body").append("<span id=\"infoNoti\" style=\"display:none;\"></span>");
    $("body").append("<span id=\"successNoti\" style=\"display:none;\"></span>");
    $("body").append("<span id=\"errorNoti\" style=\"display:none;\"></span>");
    
    
    var noti_info    = '<div class="noti_info"><img src="/resources/images/noti/envelope.png" /><h3>#= title #</h3><p>#= message #</p></div>';
    var noti_success = '<div class="noti_success"><img src="/resources/images/noti/success-icon.png"/><h3>#= message #</h3></div>';
    var noti_error   = '<div class="noti_error"><img src="/resources/images/noti/error-icon.png" /><h3>#= title #</h3><p>#= message #</p></div>';    

    var ntop = Math.max(0, (($(window).height() - 100 ) / 2) + $(window).scrollTop()) -100;
    var nleft = Math.max(0, (($(window).width()  ) / 2) +$(window).scrollLeft()) - 200;

//    var ntop = "300px";
//    var nleft = "500px";

    
    
    //kendo ui Notification 초기 설정
    genexon.successNoti = $("#successNoti").kendoNotification({
        position: {
            pinned: true,
            top: ntop,
            left: nleft
        }
       ,autoHideAfter: 2000
       ,stacking: "down"
       ,templates: [{type: "success",template: noti_success}]
    }).data("kendoNotification");
    
    genexon.infoNoti = $("#infoNoti").kendoNotification({
        position: {
            pinned: true,
            top: ntop,
            left: nleft
        }
       ,autoHideAfter: 2000
       ,stacking: "down"
       ,templates: [{type: "info",template: noti_info}]
    }).data("kendoNotification");
    
    genexon.errorNoti = $("#errorNoti").kendoNotification({
        position: {
            pinned: true,
            top: ntop,
            left: nleft
        }
       ,autoHideAfter: 0
       ,stacking: "down"
       ,templates: [{type: "error",template: noti_error}]
    }).data("kendoNotification");

};

genexon.alert = function(type, title, msg) {
    var noti = null;
    if(type=="success")
    {
        if(genexon.successNoti == undefined) {
            alert(msg);
            return;
        }
        noti = genexon.successNoti;
    }
    else if(type == "info")
    {
        if(genexon.infoNoti == undefined) {
            alert(msg);
            return;
        }
        noti = genexon.infoNoti;
    }
    else if(type == "error")
    {
        if(genexon.infoNoti == undefined) {
            alert(msg);
            return;
        }
        noti = genexon.errorNoti;
    }
    if(genexon.menuID != undefined) {
        genexon.menuID.trigger("click");
        if(genexon.menuID.text() != "")
        {
        	var menuId = genexon.menuID.text();
        	var strIndex = menuId.indexOf('reloadClose');
        	if(strIndex>0) menuId = menuId.substring(0, strIndex);
            title = menuId + " - " + title; 
        }
    }
    
    noti.show({title: title,message: msg}, type);
    
};

/** 회사용 confirm */
genexon.confirm = function(title, msg, callback) {
	var result = false;
	var lbody = genexon.body;
	var wd = lbody.find("#confirmPop");

	if (wd.length > 0) {
		if (wd.data("kendoWindow")) {
			wd.data("kendoWindow").destroy();
		}
		
		$("body").find("#confirmPop, #confirmationTemplate").remove();
	}

	lbody.append('<div id="confirmPop" title="'+ title + '"><p>'+ msg +'</p></div>');

	wd = lbody.find("#confirmPop");
	
	$("body").append(
			'<script id="confirmationTemplate" type="text/x-kendo-template">'
					+ '<div class="ConfirmPoup">' + msg + '</div>' + '</br>'
					+ '<hr/>'
					+ '<div style="position:absolute;right:10px;bottom:10px;">'
					+ '<button class="confirm_no k-button">취소</button>&nbsp;'
					+ '<button class="confirm_yes k-button">확인</button></div>'
					+ '</script>');
	$(function() {

		var kendoWindow = wd.kendoWindow({
			title : title,
			width: "350px",
			height: "150px",
			close : function(e) {
				callback(result);
			}
		});

		kendoWindow.data("kendoWindow").content(
				$("#confirmationTemplate").html()).center().open();

		kendoWindow.find(".confirm_yes,.confirm_no").click(function() {
			if ($(this).hasClass("confirm_yes")) {
				result = true;
			}
			kendoWindow.data("kendoWindow").close();
		});
	});
	
};

/** 회사용 deleteConfirm */
genexon.deleteConfirm = function(e,gridId,msg) {

	if(gridId == undefined){
		gridId = "#grid"
	}
	if(msg == undefined){
		msg = "데이타가 삭제 됩니다.<br>삭제하시겠습니까?"
	}
	
	var lbody = genexon.body;
	var gridData = $(gridId).data("kendoGrid")

	var wd = lbody.find("#confirmPop");

	if (wd.length > 0) {
		if (wd.data("kendoWindow")) {
			wd.data("kendoWindow").destroy();
		}
		$("body").find("#confirmPop,#confirmationTemplate").remove();
	}

	lbody.append('<div id="confirmPop" title="삭제"><p>'+ msg +'</p></div>');

	wd = lbody.find("#confirmPop");
	
	$("body").append(
			'<script id="confirmationTemplate" type="text/x-kendo-template">'
					+ '<div class="ConfirmPoup">' + msg + '</div>' + '</br>'
					+ '<hr/>'
					+ '<div style="position:absolute;right:10px;bottom:10px;">'
					+ '<button class="confirm_no k-button">취소</button>&nbsp;'
					+ '<button class="confirm_yes k-button">확인</button></div>'
					+ '</script>');
	$(function() {
        
		var kendoWindow = wd.kendoWindow({
			title : "삭제",
			width: "200px",
			height: "100px",
            visible: false
		});

		kendoWindow.data("kendoWindow").content(
				$("#confirmationTemplate").html()).center().open();

		kendoWindow.find(".confirm_yes,.confirm_no").click(function() {
			if ($(this).hasClass("confirm_yes")) {
				gridData.dataSource.remove(gridData.dataItem($(e.target).closest("tr")));
				gridData.dataSource.sync();
			}
			kendoWindow.data("kendoWindow").close();
		});
	});
	
};

genexon.popupCenter = function(url, name, width, height){
	var LeftPosition = (screen.width) ? (screen.width-width)/2 : 0;
	var TopPosition = (screen.height) ? (screen.height-height)/2 : 0;
	var settings = 'height='+height+',width='+width+',top='+TopPosition+',left='+LeftPosition+',status=no,toolbar=no,menubar=no,location=no,fullscreen=no,resizable=yes,scrollbars=yes';
	return win = window.open(url, name, settings);
};


genexon.popupPostCenter = function(frm, name, width, height){
	var LeftPosition = (screen.width) ? (screen.width-width)/2 : 0;
	var TopPosition = (screen.height) ? (screen.height-height)/2 : 0;
	
	$('#'+frm).attr('target', name);
	
	var settings = 'height='+height+',width='+width+',top='+TopPosition+',left='+LeftPosition+',status=no,toolbar=no,menubar=no,location=no,fullscreen=no,resizable=yes,scrollbars=yes';
	window.open('', name, settings);

	$('#'+frm).submit();
};


genexon.reloadSelf = function(){
    var ifmID = genexon.menuID.attr("id").substr(3,20);
    var mfrm = $(parent.document.body).find("#mainFrm");
    mfrm.attr("target",ifmID);
    mfrm.attr("action",$(parent.document.body).find("#"+ifmID).attr("targetURL"));
    
    mfrm.submit();
};

/** 소수점 정해진 자리수 아래 버림 */
genexon.trunc = function(numToTruncate, intDecimalPlaces) {    
    var numPower = Math.pow(10, intDecimalPlaces); // "numPowerConverter" might be better
    return ~~(numToTruncate * numPower)/numPower;
};


/** 공통코드 Key value **/ 
genexon.nextValueChange = function(CHV, JIGUB_TYPE_CD, TYPE_DESC){
	$.ajax({
		url : "/comm/getCommonCodeList.ajax"
		,data : {"grp_cmm_cd" : JIGUB_TYPE_CD}
		,success : function(data){
			$.each(data.results,function(e){
				if(this.cd_vl == CHV){
     			$("input[name='" + TYPE_DESC +"']").val(this.bz_nm)
				}
			})
		}
	})
};

/** 엑셀코드 Key value **/ 
genexon.nextExceNmChange = function(CHV, IF_DT_NM){
	$.ajax({
		url : "/inscom/suip/selectNapCntlList.ajax"
		,success : function(data){
			$.each(data.results,function(e){
				if(this.if_dt_cd == CHV){
     			$("input[name='" + if_dt_nm +"']").val(this.if_dt_nm)
				}
			})
		}
	})
};

/** 메뉴 찾기 **/
genexon.menuIdAjax = function(menuId,arg,arg2,arg3){
	$.ajax({
			url : "/comm/getTabScriptMemuId.ajax"
			,type : "post"
			,data : {"resource_id" : menuId}
		, success: function(result){
			if(result.results[0] == undefined || result.results[0] == '' || result.results[0] == null){
				genexon.alert("error", "메뉴권한", "메뉴 사용 권한이 없습니다.");
				return;
			}
			if(result.results[0].menu_gbn_cd == "MENU"){
				if(arg != undefined){
					if( arg2 != undefined){
						parent.addTab(result.results[0].resource_id,result.results[0].resource_name,result.results[0].resource_url+"?IDX="+ arg+"&SH_IDX="+arg2, result.results[0].menu_path, true);				
					}else if (arg3 != undefined) {
						parent.addTab(result.results[0].resource_id,result.results[0].resource_name,result.results[0].resource_url+"?IDX="+ arg+"&COM_YM="+arg3, result.results[0].menu_path, true);
					}else {
						if(genexon.isArray(arg)){
							if(arg[1] == 'IDX'){
								parent.addTab(result.results[0].RESOURCE_ID,result.results[0].resource_name,result.results[0].resource_url+"?IDX="+ arg, result.results[0].menu_path, true);
							} else {
								parent.addTab(result.results[0].RESOURCE_ID,result.results[0].resource_name,result.results[0].resource_url+"?"+arg[1]+"="+ arg[0], result.results[0].menu_path, true);
							}
						} else {
							parent.addTab(result.results[0].RESOURCE_ID,result.results[0].resource_name,result.results[0].resource_url+"?IDX="+ arg, result.results[0].menu_path, true);
						}
					}
				}else{
					parent.addTab(result.results[0].RESOURCE_ID,result.results[0].resource_name,result.results[0].resource_url, result.results[0].menu_path, true);
				}
			} else {
				
			}
		}
	})
};

/** 파라미터로 메뉴 찾기 **/
genexon.menuIdAjaxWithParam = function(menuId,arg){
	$.ajax({
			url : "/comm/getTabScriptMemuId.ajax"
			, type : "post"
			, data : {"resource_id" : menuId}
			, success: function(result){
			if(result.results[0] == undefined || result.results[0] == '' || result.results[0] == null){
				genexon.alert("error", "메뉴권한", "메뉴 사용 권한이 없습니다.");
				return;
			}
			if(result.results[0].menu_gbn_cd == "MENU"){
				if(arg != undefined){

						if(genexon.isArray(arg)){
							if(arg[1] == 'IDX'){
								parent.addTab(result.results[0].resource_id,result.results[0].resource_name,result.results[0].resource_url+"?IDX="+ arg, result.results[0].menu_path, true);
							} else {
								var paramstr = "";
								for(i = 0; i<arg.length; i++){
									if(i%2 == 0){
										paramstr += arg[i]+"=";
									} else {
										paramstr += arg[i]+"&";
									}
								} 
								parent.addTab(result.results[0].resource_id,result.results[0].resource_name,result.results[0].resource_url+"?"+paramstr, result.results[0].menu_path, true);
							}
						} else {
							parent.addTab(result.results[0].resource_id,result.results[0].resource_name,result.results[0].resource_url+"?IDX="+ arg, result.results[0].menu_path, true);
						}
				}else{
					parent.addTab(result.results[0].resource_id,result.results[0].resource_name,result.results[0].resource_url, result.results[0].menu_path, true);
				}
			} else {
			}
		}
	})
};

/** 메뉴닫기 **/
genexon.menuClose = function(menuId,arg){
	$.ajax({
			url : "/comm/getTabScriptMemuId.ajax"
			,type : "post"
			,data : {"resource_id" : menuId}
		, success: function(result){
			if(arg != undefined){
				parent.closeTab(result.results[0].resource_id,result.results[0].resource_name);
			}else{
				parent.closeTab(result.results[0].resource_id,result.results[0].resource_name);
			}
		}
	})
}

// 20160202 ljh add ======
/*
 * 탭윈도우 화면 크기조절
 */
genexon.WindowResize = function(divId,height){
	var wd = genexon.body.find(divId);
    return wd.height(height);

};

// 배열확인
genexon.isArray = function(o){   
	return Object.prototype.toString.call(o) == '[object Array]';   
}

//엑셀다운로드 ajax
genexon.excelDown = function(formId, data) {
	if(data.excelpath == null || data.excelpath == undefined || data.excelpath == "") {
		data.excelpath = "DEFAULT.xlsx";
	}
	
	$.fileDownload($(formId).prop('action'), {
		httpMethod: "POST",
		data: data,
		prepareCallback: function() {
			kendo.ui.progress($("body"), true);
		},
		successCallback: function (url) {
			kendo.ui.progress($("body"), false);
		},
		failCallback: function(responesHtml, url) {
			kendo.ui.progress($("body"), false);
			genexon.alert('error', '엑셀다운로드', '알 수 없는 에러가 발생하였습니다. 관리자에게 문의 해주세요.');
		}
	});
}

genexon.nvl = function (data,format){
	var returndata = "";
	if(undefined == data || '' == data || null == data || "null" == data) {
		returndata = format;
	}else{
		returndata = data;
	}
	return returndata;
}

genexon.nvl2 = function (data,format){
	var returndata = "";
	if(undefined == data || '' == data || null == data || "null" == data) {
		returndata = format;
	}else{
		returndata = data.replace(/\"/g,"&quot;");
	}
	return returndata;
}

//[조직찾기]
genexon.popOrgSearch = function (targetObjectId) {
	genexon.popWindowOpen({ pID    : "HR_112"
                    , pTitle : "조직검색"
                    , pURL   : "/insa/popup/orgSrchPop.pop"
                    , pWidth : 1110
                    , pHeight: 500
                    , pModal : false
                    , resizable : true
                    , callbackFunc : genexon.cbFuncOrg
                    , position : {top:200, left:200}
                    , targetObjectId : targetObjectId
                  });
}

//사원 찾기 콜백
genexon.cbFunc = function (targetObjectId, EMP_CD, EMP_NM, SNM){
	if(targetObjectId.TYPE == "emp_cd"){
		 $("#"+targetObjectId.EMP_CD).val(EMP_CD);
		 $("#"+targetObjectId.EMP_NM).val(EMP_NM);
	}else if(targetObjectId.TYPE == "emp_cdnm"){
		$("#"+targetObjectId.EMP_CD).val(EMP_CD);
		$("#"+targetObjectId.EMP_NM).val(EMP_NM);
		$("#"+targetObjectId.EMP_CDNM).val(EMP_NM + "("+ EMP_CD +")");
	}else if(targetObjectId.TYPE == "emp_cdsnm"){
		$("#"+targetObjectId.EMP_CD).val(EMP_CD);
		$("#"+targetObjectId.EMP_NM).val(EMP_NM);
		$("#"+targetObjectId.SNM).val(SNM);
	}
}

//사원 찾기 팝업
genexon.popEmpSearch = function (targetObjectId) {

	genexon.popWindowOpen({ pID   : "HR_111"
               , pTitle : "사원검색"
               , pURL   : "/insa/popup/empSrchMiniPop.pop"
               , pWidth : 750
               , pHeight: 400
               , pModal : false
               , resizable : true
               , callbackFunc : genexon.cbFunc
               , targetObjectId : targetObjectId
	});
}

// [조직찾기] 콜백
genexon.cbFuncOrg = function (targetObjectId, scd, snm){
    $("#"+targetObjectId[0]).val(scd);
    $("#"+targetObjectId[1]).val(snm);
};

// [조직찾기] 팝업
genexon.popWindowOpen = function (arr){
	if($("#"+arr.pID).length == 0){
		$("body").append("<div id='"+arr.pID+"' style='display:none;'></div>");
    }
    
    genexon.callBackFunc = arr.callbackFunc;
    genexon.openwindowID = arr.pID;
    genexon.targetObjectId = arr.targetObjectId;
    $("#"+arr.pID).kendoWindow({
        title:arr.pTitle,
        content: arr.pURL,
        iframe: true,
        resizable: false,
        width:arr.pWidth,
        height:arr.pHeight,
        modal: arr.pModal,
        close:function(e){
        	$("#"+arr.pID).data("kendoWindow").destroy();
            $("#"+arr.pID).remove();
        }
    });
    
    var wd = $("#"+arr.pID).data("kendoWindow");
    wd.center();
    wd.open();

};

//[조직찾기] input tag 안에 소속 X 버튼
genexon.addInitButton = function(targetObjectId, InitObjectId) {
	$('#' + targetObjectId).after('<span class="k-icon k-delete initbtn" id="' + targetObjectId+ '_DELETE"></span>');
	$('#' + targetObjectId +'_DELETE').bind('click', function(e) {
		for(var i=0; i<InitObjectId.length; i++) {
			$('#' + InitObjectId[i]).val("");
		}
	});
}

//intput tag 키 입력 유형(숫자만, 특수문자 제외한 숫자문자)
//inputType > 숫자 = onlyNum, 특수문자 제외 숫자 문자 = exceptSC
genexon.keyInputType = function(objId, inputType) { 
	var regExp;
  
	if(inputType == "onlyNum") {
		regExp = /[^0-9]/;
    
	    $("#"+objId).bind("keydown", function(e){
	      //숫자키, 백스페이스, delete, 방향키가 아니면 입력 X
	      if(regExp.test(e.key) && e.keyCode != 8 && e.keyCode != 46 && !(e.keyCode >=37 && e.keyCode <= 40))
	          return false;
	    });
	}
  
	if(inputType == "exceptSC") {
		regExp = /[\{\}\[\]\/?.,;:|\)*~`!^\-+<>@\#$%&\\\=\(\'\"]/gi;
    
		$("#"+objId).bind("keydown", function(e){
			//특수문자 제외 숫자 문자
			if(regExp.test(e.key))
			return false;
		});
	}
}

genexon.getInputDataToJson = function() {
	/* 기본정보 입력 데이터 */
	var inputJson = {};

	for (var i = 0; i < $("input").length; i++) {
		var jsonKey = genexon.getId($("input")[i].id);
		var jsonValue = genexon.getValue($("input")[i]);

		inputJson[jsonKey] = jsonValue;

	}

	var textAreaLength = $("textarea").length;
	if (textAreaLength > 0) {
		for (var i = 0; i < $("textarea").length; i++) {
			var jsonKey = genexon.getId($("textarea")[i].id);
			var jsonValue = genexon.getValue($("textarea")[i]);

			inputJson[jsonKey] = jsonValue;

		}
	}

	return inputJson;
}

genexon.getAllInputDataToJson = function(){
	/* 각 탭 안의 그리드들 */
	var gridObjects = $("div[id^='grid_']");

	/* 모든 입력 데이터 */
	var result = {};

	/* [기본정보] 입력 데이터 부터 넣어줌 */
	result["input"] = genexon.getInputDataToJson();

	/* 모든 탭 안의 그리드 데이터 넣어줌 (상세정보) */
	for (var i = 0; i < gridObjects.length; i++) {
		var gridObjectId = gridObjects[i].id;
		result[gridObjectId] = getAllTabDataJson(gridObjectId);
	}//end for

	return result;
}


//날짜 구간 검색 시 시작일자가 종료일자보다 이전이면 발생(datePicker yyyy-MM-dd 형식)
genexon.compareSrchTerm = function(startYmd, endYmd) { 
	$("#"+startYmd+","+"#"+endYmd).on("blur", function() {
		if($(this).val() != "" && $(this).val().replace(/_/g, '').length < 10) {
			genexon.alert("info", "날짜 검색", "검색일자는 YYYY-MM-DD 형식입니다.");
			$(this).val('');
			$(this).focus();
		}
		
		if(($("#"+startYmd).val() != "" && $("#"+endYmd).val() != "") && $("#"+endYmd).val() < $("#"+startYmd).val()) {
			genexon.alert("info", "날짜 검색", "시작날짜는 종료날짜보다 이전이어야 합니다.");
			$("#"+endYmd).val('');
			$("#"+endYmd).focus();
		}
	});
}

// 날짜 검색 시 입력일자가 현재일자보다 이전이면 발생(datePicker yyyy-MM-dd 형식)
genexon.compareCurrentDate = function(inputYmd) {
	
	$("#"+inputYmd).on("blur", function() {
		if($(this).val() != "" && $(this).val().replace(/_/g, '').length < 10) {
			genexon.alert("info", "날짜 검색", "입력일자는 YYYY-MM-DD 형식입니다.");
			$(this).val('');
		}
		
		if($("#"+inputYmd).val() != "" && $("#"+inputYmd).val() < genexon.getYymmdd(new Date(), "-")) {
			genexon.alert("info", "날짜 검색", "입력날짜는 현재날짜보다 이후이어야 합니다.");
			$(this).val('');
			$(this).focus();
		}
	});
}

//날짜 구간 검색 시 시작일자가 종료일자보다 이전이면 발생(datePicker yyyy-MM 형식)
genexon.compareSrchTermYM = function(startYm, endYm) { 
	$("#"+startYm+","+"#"+endYm).on("blur", function() {
		if($(this).val() != "" && $(this).val().replace(/_/g, '').length < 6) {
			genexon.alert("info", "날짜 검색", "검색일자는 YYYY-MM 형식입니다.");
			$(this).val('');
			$(this).focus();
		}
		
		if(($("#"+startYm).val() != "" && $("#"+endYm).val() != "") && $("#"+endYm).val() < $("#"+startYm).val()) {
			genexon.alert("info", "날짜 검색", "시작월은 종료월보다 이전이어야 합니다.");
			$("#"+endYm).val('');
			$("#"+endYm).focus();
		}
	});
}

//업적월 구간 검색 시 시작월이 종료월보다 이전이면 발생
genexon.compareSrchTermComym = function(startComym, endComym) {
	$("#"+startComym+","+"#"+endComym).on("change", function() {
		if(($("#"+startComym).val() != "" && $("#"+endComym).val() != "") && $("#"+endComym).val() < $("#"+startComym).val()) {
			genexon.alert("info", "업적월 검색", "시작월은 종료월보다 이전이어야 합니다.");
			$("#"+endComym).data("kendoDropDownList").select(0);
			$("#"+endComym).focus();
		}
	});
}

//입력창 밸리데이션
genexon.validateInput = function(jQuerySelector, errMsg,alertcontenct) {
	var flag = false;
	var elemVal = $(jQuerySelector).val();
	
	alertcontenct = alertcontenct==undefined?"저장전 확인":alertcontenct;
	
	if (elemVal != undefined && elemVal.length > 0 && elemVal != '') {
		flag = true;

	} else {
		genexon.alert("info", alertcontenct, errMsg);
		$(jQuerySelector).focus();
		return;
	}
	return flag;
}

/**
*  부모창 크기에 영향받지 않는 인사, 조직찾기 팝업
*/
genexon.popSearchWindow = function(targetObjectId, srchGubun, parentWindowID, paramData) {
	var url = "";
	var menuWindow = genexon.body.find("div[id='container']").find("div[class='k-content k-state-active']");	//현재 메뉴화면
	
	//팝업 id가 있으면 팝업id, 아니면 현재 활성화된 메뉴 화면
	//parentWindowID는 호출하는 부모창이 메뉴화면이면 선언하지 말아야 함
	if(genexon.nvl(parentWindowID, "") == "") {
		parentWindowID = menuWindow.attr("id");
	}
	
	if(srchGubun == "insa") {
		title = "사원 검색";
		genexon.openwindowID = "insaSrchPop";
		url = "/insa/popup/empSrchMiniPop2.pop?parentWindowID="+parentWindowID
		
		genexon.callBackFunc = function (selectedItem) {
			if(targetObjectId.TYPE == "emp_cd") {
				 $("#"+targetObjectId.EMP_CD).val(selectedItem.emp_cd);
				 $("#"+targetObjectId.EMP_NM).val(selectedItem.emp_nm);
			}else if(targetObjectId.TYPE == "emp_cdnm") {
				$("#"+targetObjectId.EMP_CD).val(selectedItem.emp_cd);
				$("#"+targetObjectId.EMP_NM).val(selectedItem.emp_nm);
				$("#"+targetObjectId.EMP_CDNM).val(selectedItem.emp_nm + "("+ selectedItem.emp_cd +")");
			}else if(targetObjectId.TYPE == "emp_cdsnm") {
				$("#"+targetObjectId.EMP_CD).val(selectedItem.emp_cd);
				$("#"+targetObjectId.EMP_NM).val(selectedItem.emp_nm);
				$("#"+targetObjectId.SNM).val(selectedItem.snm);
			}
		}
	}else if(srchGubun == "contract") {
		title = "계약 검색";
		genexon.openwindowID = "contractSrchPop";
		url = "/contract/popup/contractSrchPop.pop?parentWindowID="+parentWindowID
		
		genexon.callBackFunc = function (EMP_CD, CONT_DATE, INSCO_CD, INSPOL_NO, PROD_NM) {
			$("#"+targetObjectId[0]).val(EMP_CD);
		    $("#"+targetObjectId[1]).val(CONT_DATE);
		    $("#"+targetObjectId[2]).val(INSCO_CD);
		    $("#"+targetObjectId[3]).val(INSPOL_NO);
		    $("#"+targetObjectId[4]).val(PROD_NM);
		}
		
	}else {
		title = "조직 검색";
		
		genexon.openwindowID = "orgSrchPop";
		url = "/insa/popup/orgSrchPop2.pop?parentWindowID="+parentWindowID
		
		//조직찾기 콜백 함수
	    genexon.callBackFunc = function(scd, snm) {
			$("#"+targetObjectId[0]).val(scd);
		    $("#"+targetObjectId[1]).val(snm);
		}
	}
	
	//팝업 생성
	var lbody = genexon.body;
    var wd = lbody.find("#"+genexon.openwindowID);
    
    if(wd.length > 0) {
        if(wd.data("kendoWindow")) {
            wd.data("kendoWindow").close();
            wd.data("kendoWindow").destroy();
        }
        
        wd.remove();
    }
    
    lbody.append("<div id='"+genexon.openwindowID+"' style='display:none;'></div>");
    wd = lbody.find("#"+genexon.openwindowID);
        
    wd.kendoWindow({
        actions: [ "Maximize",  "Close"],
        title: title,
        content: url,
        iframe: true,
        resizable: true,
        width: "1100px",
        height: "450px",
        modal: true
    });
    
    var kwd = wd.data("kendoWindow");
    kwd.center();
    kwd.open();
}

//위와 거의 동일하나 위의 콜백함수가 아닌 커스터마이징이 필요한 경우 jsp에서 선언 후 전달하여 genexon.callBackFunc에 넣어줌
genexon.popSearchWindow2 = function(srchGubun, parentWindowID, callBackFunc, paramData) {
	var url = "";
	var menuWindow = genexon.body.find("div[id='container']").find("div[class='k-content k-state-active']");	//현재 메뉴화면
	
	//팝업 id가 있으면 팝업id, 아니면 현재 활성화된 메뉴 화면
	//parentWindowID는 호출하는 부모창이 메뉴화면이면 선언하지 말아야 함
	if(genexon.nvl(parentWindowID, "") == "") {
		parentWindowID = menuWindow.attr("id");
	}
	
	if(srchGubun == "insa") {
		title = "사원 검색";
		genexon.openwindowID = "insaSrchPop";
		url = "/insa/popup/empSrchMiniPop2.pop?parentWindowID="+parentWindowID
		
	}else if(srchGubun == "contract") {
		title = "증권번호 검색";
		genexon.openwindowID = "contractSrchPop";
		url = "/contract/popup/contractSrchPop.pop?parentWindowID="+parentWindowID
		
	}else if(srchGubun == "contractMonth") {
		var srch_com_ym = paramData.srch_com_ym;
	
		title = srch_com_ym + " 마감 계약 검색";
		genexon.openwindowID = "contractMonthSrchPop";
		url = "/contract/popup/contractMonthSrchPop.pop?parentWindowID="+parentWindowID+"&srch_com_ym="+srch_com_ym
		
	}else if(srchGubun == "product_bojang") {
		title = "상품 보장 검색";
		genexon.openwindowID = "productbojangSrchPop";
		url = "/contract/popup/productbojangSrchPop.pop?parentWindowID="+parentWindowID
		
	}else {
		title = "조직 검색";
		
		genexon.openwindowID = "orgSrchPop";
		url = "/insa/popup/orgSrchPop2.pop?parentWindowID="+parentWindowID
		
	}
	
	//콜백 함수 세팅
    genexon.callBackFunc = callBackFunc;
	
	//팝업 생성
	var lbody = genexon.body;
    var wd = lbody.find("#"+genexon.openwindowID);
    
    if(wd.length > 0) {
        if(wd.data("kendoWindow")) {
            wd.data("kendoWindow").close();
            wd.data("kendoWindow").destroy();
        }
        
        wd.remove();
    }
    
    lbody.append("<div id='"+genexon.openwindowID+"' style='display:none;'></div>");
    wd = lbody.find("#"+genexon.openwindowID);
        
    wd.kendoWindow({
        actions: [ "Maximize",  "Close"],
        title: title,
        content: url,
        iframe: true,
        resizable: true,
        width: "1100px",
        height: "450px",
        modal: true
    });
    
    var kwd = wd.data("kendoWindow");
    kwd.center();
    kwd.open();
}

// %
genexon.addPer = function(value) {
	if(genexon.nvl(value+"", "") == "") {
		return "";
	}else {		
		return (Math.floor(value * 1000)) / 1000 + "%";
	}
}

//권한별 그리드 버튼 핸들러
genexon.autrBtnHandler = function(in_autr_type, up_autr_type, de_autr_type, gridRows) {	
	gridRows.each(function(idx, element){
		if(in_autr_type != "Y") {
			$(this).find(".k-button.addTd").remove();
			$(this).find(".k-grid-addA").remove();
		}
		
		if(up_autr_type != "Y") {
			$(this).find(".k-button.editTd").remove();
			$(this).find(".k-grid-editPop").remove();
			$(this).find(".k-grid-edit").remove();
		}
		
		if(de_autr_type != "Y") {
			$(this).find(".k-button.deleteTd").remove();
			$(this).find(".k-grid-delete").remove();
			$(this).find(".k-grid-Delete").remove();
		}
	});
}

//버튼 다중선택
genexon.buttonMultiSelected = function(classSelected) {
	$(classSelected).find("button").click(function(e){
		if($(this).hasClass("c_btn05") === true) {
			return false;
		}
		
		if($(this).hasClass("c_btn04") === true) {
			$('.chose_btn').find("button").addClass("c_btn02").removeClass("c_btn01").removeClass("c_btn03");
			$('.chose_btn2').find("button").addClass("c_btn02").removeClass("c_btn01").removeClass("c_btn03");
			$('.chose_btn3').find("button").addClass("c_btn02").removeClass("c_btn01").removeClass("c_btn03");
			$('.chose_btn4').find("button").addClass("c_btn02").removeClass("c_btn01").removeClass("c_btn03");
			$('.chose_btn').find("button").eq(0).addClass("c_btn01").removeClass("c_btn02").removeClass("c_btn03");
			$('.chose_btn2').find("button").eq(0).addClass("c_btn01").removeClass("c_btn02").removeClass("c_btn03");
			$('.chose_btn3').find("button").eq(0).addClass("c_btn01").removeClass("c_btn02").removeClass("c_btn03");
			$('.chose_btn4').find("button").eq(0).addClass("c_btn01").removeClass("c_btn02").removeClass("c_btn03");
			return false;
		}
		
		var classSelectedNum = 1
		if($(classSelected).find("button").hasClass("c_btn04") === true) {
			classSelectedNum = classSelectedNum + 2
		}
		
		var num = $(classSelected).find("button").length - classSelectedNum
		
		if( $(this).text() === "전체" ) {
			if($(this).hasClass("c_btn01") === true) {
				$(classSelected).find("button").addClass("c_btn03").removeClass("c_btn01").removeClass("c_btn02")
			} else {
				$(classSelected).find("button").addClass("c_btn02").removeClass("c_btn01").removeClass("c_btn03")
				$(this).addClass("c_btn01").removeClass("c_btn02").removeClass("c_btn03")
			}
		} else {
			if($(classSelected).find("button").hasClass("c_btn03") === true) {
			} else {
				$(classSelected).find("button").addClass("c_btn03").removeClass("c_btn01").removeClass("c_btn02")
			}

			if($(this).hasClass("c_btn01") === true) {
				$(this).addClass("c_btn03").removeClass("c_btn01").removeClass("c_btn02")
			} else if($(this).hasClass("c_btn02") === true) {
				$(this).addClass("c_btn03").removeClass("c_btn01").removeClass("c_btn02")
			} else {
				$(this).addClass("c_btn01").removeClass("c_btn02").removeClass("c_btn03")
			}

			if($(classSelected).find(".c_btn01").length === num) {
				$(classSelected).find("button").addClass("c_btn02").removeClass("c_btn01").removeClass("c_btn03")
				$(classSelected).find("button").eq(0).addClass("c_btn01").removeClass("c_btn02").removeClass("c_btn03")
			}
		}
		
		if($(classSelected).find(".c_btn01").length === 0){
			$(classSelected).find("button").addClass("c_btn02").removeClass("c_btn01").removeClass("c_btn03")
			$(".k-button.c_btn02").addClass("c_btn01").removeClass("c_btn02").removeClass("c_btn03")
		}
		
	})
};

//버튼 2개까지 선택
genexon.buttonTwoSelect = function(classSelected){
	var num = 2; // 버튼 선택 개수
	
	$(classSelected).find("button").click(function(e){
		if($(this).hasClass("c_btn05") === true) {
			return false;
		}
		if( $(this).text() != "전체" ) {
			if($(this).hasClass("c_btn01") === true) {
				$(this).addClass("c_btn03").removeClass("c_btn01").removeClass("c_btn02")
			} else if($(this).hasClass("c_btn02") === true) {
				$(this).addClass("c_btn03").removeClass("c_btn01").removeClass("c_btn02")
			} else {				
				if( $(classSelected).find(".c_btn01").length < num ){
					$(this).addClass("c_btn01").removeClass("c_btn02").removeClass("c_btn03")
				} else if( $(classSelected).find(".c_btn01").length >= num){
					alert(' 선택은 최대 2개까지 가능합니다. ');
					return;
				}
			}
		}
	});
};

//버튼 단일선택
genexon.buttonSelected = function(classSelected) {
	$(classSelected).find("button").click(function(e){
		if($(this).hasClass("c_btn04") === true) {
			$('.chose_btn5').find("button").removeClass("c_btn01").addClass("c_btn03");
			$('.chose_btn5').find("button").eq(0).addClass("c_btn01").removeClass("c_btn03");
			return false;
		}
		//c_btn03 비활성화 c_btn01 활성화
		
		var classSelectedNum = 1
		var num = $(classSelected).find("button").length - classSelectedNum
		
		if($(classSelected).find("button").hasClass("c_btn01") === true) {
			$(classSelected).find("button").addClass("c_btn03").removeClass("c_btn01")
		}

		if($(this).hasClass("c_btn03") === true) {
			$(this).addClass("c_btn01").removeClass("c_btn03")
		} 
		
		if($(classSelected).find(".c_btn01").length === num) {
			$(classSelected).find("button").addClass("c_btn03").removeClass("c_btn01")
		}
	})
};

//버튼이 2개 일때 단일선택
genexon.buttonSelected2 = function(classSelected) {
	$(classSelected).find("button").click(function(e){
		if($(this).hasClass("c_btn04") === true) {
			$('.chose_btn5').find("button").removeClass("c_btn01").addClass("c_btn03");
			$('.chose_btn5').find("button").eq(0).addClass("c_btn01").removeClass("c_btn03");
			return false;
		}
		
		if($(classSelected).find("button").hasClass("c_btn01") === true) {
			$(classSelected).find("button").addClass("c_btn03").removeClass("c_btn01")
		}

		if($(this).hasClass("c_btn03") === true) {
			$(this).addClass("c_btn01").removeClass("c_btn03")
		} 
	})
};

//엑셀업로드 팝업 오픈
genexon.excelUploadConfirmOpen = function(popupTitle, com_ym, excel_cd, work_scope) {
	genexon.PopWindowOpen({
		pID : "excelUploadConfirm",
		pTitle: popupTitle,
		pURL : "/uploadExcel/excelUploadConfirm.pop",
		data : {
			srch_com_ym: com_ym,
			com_ym : com_ym,
			excel_cd : excel_cd,
			work_scope : work_scope
		},
		pWidth  : "80%",
		pHeight : "80%",
		pModal  : "true",
		resizeable : true,
		maximize: true,
		callbackFunc : srch
	});
}

/**
 * 그리드 editor DatePicker 셋팅
 */
genexon.GridEditor_kdtPicymd = function(container, options) {	
	if(!options.values) {
        genexon.alert("error", "시스템 오류", "DropDownList 설정시 values설정을 하세요!");
        return;
    }
	
	var guid = kendo.guid();
	
    var inputString = '<input id="'+guid+'" name="'+options.field+'" class="kdtPicymd" data-bind="value:' + options.field + '" ' ;  

    if(options.values.required){
        inputString = inputString + ' required="required" '; 
    }

    if(options.values.validationMessage) {
        inputString = inputString + ' validationMessage="'+options.values.validationMessage+'" ' ;
    }else if(options.values.required){
        inputString = inputString + ' validationMessage="필수항목" ' ;
    }
    
    //스타일(width를 style로 바꿈)
    if(options.values.style) {
    	inputString = inputString + ' style="'+options.values.style+'" ' ;
    }
    
    inputString = inputString + '/>';
    
    genexon.initKendoUI_dtymd($(inputString).appendTo(container));
};

/**
 * 그리드 editor NumericTextBox 셋팅
 */
genexon.GridEdiitor_knb = function(container, options) {
	if(!options.values) {
        genexon.alert("error", "시스템 오류", "DropDownList 설정시 values설정을 하세요!");
        return;
    }
	
	var guid = kendo.guid();
	
	var inputString = '<input id="'+guid+'" name="'+options.field+'" class="kntb" data-bind="value:' + options.field + '" ' ;  

    if(options.values.required) {
        inputString = inputString + ' required="required" '; 
    }

    if(options.values.validationMessage) {
        inputString = inputString + ' validationMessage="'+options.values.validationMessage+'" ' ;
    }else if(options.values.required){
        inputString = inputString + ' validationMessage="필수항목" ' ;
    }
    
    //스타일(width를 style로 바꿈)
    if(options.values.style) {
    	inputString = inputString + ' style="'+options.values.style+'" ' ;
    }
    
    //기타 파라미터
    if(options.values.etcParam) {
    	inputString = inputString + options.values.etcParam;
    }
    
    inputString = inputString + '/>';
    
    genexon.initKendoUI_nb($(inputString).appendTo(container));
}

genexon.GridEditor_setBool = function(container, options) {
    var guid = kendo.guid();
    $('<input class="k-checkbox" id="' + guid + '" type="checkbox" name="' + options.field + '" data-type="boolean" data-bind="checked:'+options.field+'">').appendTo(container);
    
    if(options.values) {
    	if(options.values.label) {
        	$('<label class="k-checkbox-label" for="' + guid + '">​'+options.values.label+'</label>').appendTo(container);
        }
    }
}

//화면에 기간 검색, 기간검색 및 구간 드랍다운이 있는 경우 세팅 함수
genexon.setSrchDateObj = function(start_id, end_id, srch_term_id) {
	//날짜검색 datePicker 및 마스킹처리
	//시작일자
	$("#"+start_id).kendoDatePicker();
 	$("#"+start_id).kendoMaskedTextBox({
		mask : "0000-00-00"
	});
 	
 	//종료일자
 	$("#"+end_id).kendoDatePicker();
 	$("#"+end_id).kendoMaskedTextBox({
		mask : "0000-00-00"
	});
 	
 	//시작일자 종료일자 관련
 	genexon.compareSrchTerm(start_id, end_id);
 	
 	//구간 요소의 id가 있고 해당 요소가 dropDownList인 경우
 	if(genexon.nvl(srch_term_id, "") != "" && $("#"+srch_term_id).data("kendoDropDownList") != undefined) { 		
 		$("#"+srch_term_id).data("kendoDropDownList").bind("dataBound", function(e) {
 			genexon.srchTermChangeEvent(e, start_id, end_id);
 		});
 		
 		$("#"+srch_term_id).data("kendoDropDownList").bind("change", function(e) {
 			genexon.srchTermChangeEvent(e, start_id, end_id);
 		});
 		
 		$("#"+start_id).bind("change", function() { $("#"+srch_term_id).data("kendoDropDownList").value("") });
 		$("#"+end_id).bind("change", function() { $("#"+srch_term_id).data("kendoDropDownList").value("") });
 	}
}

genexon.srchTermChangeEvent = function(e, start_id, end_id, srch_term_change_callback_func) {
	var date = new Date();
	$("#"+end_id).val(genexon.getYymmdd(date, "-"));

	if(e.sender._old == "DAY") {
		$("#"+start_id).val(genexon.getDate("YMD", date, "-", 0, "today"));
	} else if(e.sender._old == "MONTH") {
		$("#"+start_id).val(genexon.getDate("YMD", date, "-", 0, 1));
	} else if(e.sender._old == "WEEK_AGO") {
		$("#"+start_id).val(genexon.getYymmddWeek(date, "-", 1));
	} else if(e.sender._old == "MONTH_AGO") {
		$("#"+start_id).val(genexon.getDate("YMD", date, "-", 1, "today"));
	} else if(e.sender._old == "2MONTH_AGO") {
		$("#"+start_id).val(genexon.getDate("YMD", date, "-", 2, "today"));
	} else if(e.sender._old == "3MONTH_AGO") {
		$("#"+start_id).val(genexon.getDate("YMD", date, "-", 3, "today"));
	} else if(e.sender._old == "LAST_MONTH") {
		//전월
		$("#"+start_id).val(genexon.getDate("YMD", new Date(), "-", 1, 1));
		$("#"+end_id).val(genexon.getDate("YMD", new Date(), "-", 1, "lastDay"));
	} else if(e.sender._old == "MONTH_BEFORE_LAST") {
		//전전월
		$("#"+start_id).val(genexon.getDate("YMD", new Date(), "-", 2, 1));
		$("#"+end_id).val(genexon.getDate("YMD", new Date(), "-", 2, "lastDay"));
	} else {
		$("#"+start_id).val("");
		$("#"+end_id).val("");
	}
}

//선택삭제 함수화
genexon.selectedItemRemove = function(message, gridId) {
	var checkLength = $("input:checkbox[name='value_arr']:checked").length;
	
	if(checkLength > 0) {
		message = genexon.nvl(message, "") == "" ? "선택하신 데이터를 삭제하시겠습니까?" : message;
		gridId = genexon.nvl(gridId, "") == "" ? "#grid" : gridId;
		
		genexon.confirm("선택삭제", message, function(result) {
			if(result) {
				var grid = $(gridId).data("kendoGrid");
				var remove_url = grid.dataSource.options.transport.destroy.url;
				
				var obj = new Object();
				obj.models = new Array();
				
		 		$("input:checkbox[name='value_arr']:checked").each(function (e) {
		 			var selectedItem = grid.dataItem($(this).closest("tr"));
		 			
		 			obj.models.push(selectedItem);
		 		});
		 		
		 		$.ajax({
		 			url: remove_url,
		 			contentType: "application/json; charset=UTF-8",
		 			type: "POST",
		 			dataType: "json",
		 			data: JSON.stringify(obj),
		 			async: false,
		 			success: function(result) {
		 				genexon.alert("success", "선택삭제", "정상적으로 처리되었습니다.");
		 				srch();
		 			}
		 		})
			}
		});
	}else {
		genexon.alert("info", "선택삭제", "삭제할 항목을 선택해주세요.");
	}
}

//전체삭제 함수화
genexon.allItemRemove = function(message, gridId) {
	var $bt_all_remove = $("#bt_all_remove");
	
	if($bt_all_remove != undefined) {
		message = genexon.nvl(message, "") == "" ? "전체 데이터를 삭제하시겠습니까?" : message;
		gridId = genexon.nvl(gridId, "") == "" ? "#grid" : gridId;
		
		genexon.confirm("전체삭제", message, function(result) {
			if(result) {
				var grid = $(gridId).data("kendoGrid");
				var remove_url = grid.dataSource.options.transport.destroy.url;
				var gridData = grid.dataSource.data();
				
				var obj = new Object();
				obj.models = new Array();
				
				$.each(gridData, function(idx, element) {
		 			var data = {
		 				mb_id: element.mb_id,
		 				com_ym: element.com_ym
		 			};
					
					obj.models.push(data);
		 			
		 			return false;
		 		});
				
		 		$.ajax({
		 			url: remove_url,
		 			contentType: "application/json; charset=UTF-8",
		 			type: "POST",
		 			dataType: "json",
		 			data: JSON.stringify(obj),
		 			success: function(result) {
		 				genexon.alert("success", "전체삭제", "정상적으로 처리되었습니다.");
		 				srch();
		 			}
		 		})
			}
		});
	}
}

//마감코드로 마감여부 파악
genexon.getCloseMonthData = function(com_ym, cl_step_cd) {
	var closeMonthData = new Object();
	var closedYN = false;
	
	//마감월, 마감코드로 해당 메뉴가 마감중인이 아닌지 판단
	$.ajax({
		url : "/system/closeMonth/getCloseMonthList.ajax",
		type : "post",
		async: false,
		data: {
			com_ym : com_ym,
			cl_step_cd : cl_step_cd,
			_csrf : $("input[name='_csrf']").val()
		},
		success: function(result) {
			closeMonthData = result.results[0];
		}
	});
	
	return closeMonthData;
}

genexon.getErpDataAjax = function(com_ym, target_erp_data, target_erp_data_name, callBackFunc) {
	if(genexon.nvl(target_erp_data, "") == "") {
		genexon.alert("info", "ERP 데이터 적용", "ERP 적용 코드가 세팅되어 있지 않습니다.");
	}else {
		genexon.confirm("ERP 데이터 적용", com_ym + " " + target_erp_data_name + "를 가져옵니다.<br><br>진행하시겠습니까?", function(result){
			if(result) {
				$.ajax({
					url: "/inscomconfig/applyErpData.ajax",
					type: "POST",
					data: {
						com_ym: com_ym,
						target_erp_data: target_erp_data,
						_csrf : $("input[name='_csrf']").val()
					},
					beforeSend:function(xhr) {
						kendo.ui.progress($("body"), true);
				    },
					success: function(result) {
						callBackFunc();
						kendo.ui.progress($("body"), false);
						genexon.alert("success", "ERP 데이터 적용", "정상적으로 처리되었습니다.");
					},
					error: function(request, status, error) {
						kendo.ui.progress($("body"), false);
						genexon.alert("error", "ERP 데이터 적용", "알 수 없는 에러가 발생하였습니다.");
					}
				})
			}
		});
	}
}

genexon.getLastErpDataApplyInfo = function(com_ym, target_erp_data) {
	$.ajax({
		url: "/inscomconfig/getLastErpDataApplyInfoView.ajax",
		type: "POST",
		data: {
			com_ym: com_ym,
			target_erp_data: target_erp_data,
			_csrf : $("input[name='_csrf']").val()
		},
		success: function(result) {
			var lastLogInfo = result.results;
			
			if(lastLogInfo != null) {
				var lastErpDataApplyInfoTxt = "최종 적용 : " + lastLogInfo.in_dtm + " " + lastLogInfo.in_emp_nm + "(" + lastLogInfo.in_emp_cd + ")"; 				
				$("span#lastErpDataApplyInfo").text(lastErpDataApplyInfoTxt);
			}
		},
		error: function(request, status, error) {
			
		}
	})
}

//해당 메뉴가 마감상태인지 판단
genexon.checkCloseMonth = function(in_com_ym, in_cls_resource_id) {
	var cls_resource_id = $("#cls_resource_id").val();
	var com_ym = $("#srch_com_ym") != undefined ? $("#srch_com_ym").val() : ($("#com_ym") != undefined ? $("#com_ym").val() : "");
	var closeMonthData = new Object();
	
	if(cls_resource_id != "") {
		closeMonthData = genexon.getCloseMonthData(com_ym, cls_resource_id);
		
		if(closeMonthData != null && closeMonthData.cl_state_cd == "CLS") {
 			genexon.alert("info", closeMonthData.cl_step_nm, "마감되었습니다.");
 			
 			return false;
 		}
	}
	
	return true;
}

//엑셀 업로드 팝업(맵핑저장 하는 화면) 오픈 함수
genexon.excelFileUploadPop = function(paramObj) {
	genexon.PopWindowOpenExcelUpload({
		pID : "excelFileUpload",
		pTitle : paramObj.com_ym + '-' + paramObj.excel_nm + " 엑셀업로드",
		pURL : "/uploadExcel/excelFileUpload.pop",
		data : {
			srch_com_ym  : paramObj.com_ym,
			com_ym : paramObj.com_ym,
			excel_cd : paramObj.excel_cd,
			work_scope : paramObj.work_scope,
			insco_cd : paramObj.insco_cd,
			upload_type: paramObj.upload_type,
			_csrf: $("meta[name='_csrf']").attr("content")
		},
		pWidth : "80%",
		pHeight : "80%",
		pModal : "true",
		resizeable : true,
		maximize: true,
		callbackFunc: srch
	});
}
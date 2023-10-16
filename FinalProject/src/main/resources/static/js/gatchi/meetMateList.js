/*
//좋아요 Ajax
var isLike = false;



$(".heart").on('click touchstart', function(){
  isLike = !isLike;
  var meetListNum =  1;// 모임 목록 번호를 가져오는 코드 (어떻게 가져올지는 구현에 따라 다름)
  String userEmail = "kim";   // 사용자 이메일을 가져오는 코드
  var likeClick = isLike ? 1 : 0; // 클릭 상태에 따라 1 또는 0으로 설정

  // Ajax 요청을 보냅니다.
  $.ajax({
      type: 'POST', // 또는 'GET'에 따라 서버 요청 방식 선택
      url: '/your-server-endpoint', // 서버 측 엔드포인트 URL을 지정합니다.
      data: {
          likeClick: likeClick,
          meetListNum: meetListNum,
          userEmail: userEmail
      },
      success: function(data) {
          // 서버로부터 성공적인 응답을 받았을 때 실행할 코드
      },
      error: function() {
          // 오류가 발생했을 때 실행할 코드
      }
  });
});
*/          



//>>>>>>메인 슬라이드
$(document).ready(function() {
  slide();
  heartClickBtn();
});

function slide() {
  var wid = 0;
  var now_num = 0;
  var slide_length = 0;
  var auto = null;
  var $dotli = $('.dot>li');
  var $panel = $('.panel');
  var $panelLi = $panel.children('li');

  // 변수 초기화
  function init() {
    wid = $('.slide').width();
    now_num = $('.dot>li.on').index();
    slide_length = $dotli.length;
  }

  // 이벤트 묶음
  function slideEvent() {

    // 슬라이드 하단 dot버튼 클릭했을때
    $dotli.click(function() {
      now_num = $(this).index();
      slideMove();
    });

    // 오토플레이
    autoPlay();

    // 오토플레이 멈춤
    autoPlayStop();

    // 오토플레이 재시작
    autoPlayRestart();

    // 화면크기 재설정 되었을때
    resize();
  }

  // 자동실행 함수
  function autoPlay() {
    auto = setInterval(function() {
      nextChkPlay();
    }, 2000);
  }

  // 자동실행 멈춤
  function autoPlayStop() {
    $panelLi.mouseenter(function() {
      clearInterval(auto);
    });
  }


  // 자동실행 멈췄다가 재실행
  function autoPlayRestart() {
    $panelLi.mouseleave(function() {
      auto = setInterval(function() {
        nextChkPlay();
      }, 2000);
    });
  }

  // 이전 버튼 클릭시 조건 검사후 슬라이드 무브
  function prevChkPlay() {
    if (now_num == 0) {
      now_num = slide_length - 1;
    } else {
      now_num--;
    }
    slideMove();
  }

  // 이후 버튼 클릭시 조건 검사후 슬라이드 무브
  function nextChkPlay() {
    if (now_num == slide_length - 1) {
      now_num = 0;
    } else {
      now_num++;
    }
    slideMove();
  }

  // 슬라이드 무브
  function slideMove() {
    $panel.stop().animate({
      'margin-left': -wid * now_num
    });
    $dotli.removeClass('on');
    $dotli.eq(now_num).addClass('on');
  }

  // 화면크기 조정시 화면 재설정
  function resize() {
    $(window).resize(function() {
      init();
        $panel.css({
          'margin-left': -wid * now_num
        });
    });
  }
  init();
  slideEvent();
}


function show() {
  document.getElementById('sidebar').classList.toggle('active');
}

//best product slide
var slides = document.getElementsByClassName("slide-in");
var index = 0;

function goLeft() {
  
  if (index == 0) index = slides.length - 1;
  else index--;

  slides[0].style.marginLeft = "-" + index * 800 + "px";
}

function goRight() {
  
  if (index == slides.length - 1) index = 0;
  else index++;
  
  slides[0].style.marginLeft = "-" + index * 800 + "px";
}

jQuery('#selectBox').change(function() {
  var state = jQuery('#selectBox option:selected').val();

  if ( state == 'option1' ) {
    jQuery('.layer1').show();
  } else {
    jQuery('.layer1').hide();
  }

  if ( state == 'option2' ) {
    jQuery('.layer2').show();
  } else {
    jQuery('.layer2').hide();
  }

  if ( state == 'option3' ) {
    jQuery('.layer3').show();
  } else {
    jQuery('.layer3').hide();
  }

    if ( state == 'option4' ) {
    jQuery('.layer4').show();
  } else {
    jQuery('.layer4').hide();
  }

  if ( state == 'option5' ) {
    jQuery('.layer5').show();
  } else {
    jQuery('.layer5').hide();
  }

  if ( state == 'option6' ) {
    jQuery('.layer6').show();
  } else {
    jQuery('.layer6').hide();
  }


  if ( state == 'option7' ) {
    jQuery('.layer7').show();
  } else {
    jQuery('.layer7').hide();
  }
});





//좋아요버튼 애니메이션
var isLike = false
function heartClickBtn(){
  let h = document.getElementsByName('likeCount');

  for(let i=0; i<h.length; i++){
    h[i].addEventListener('click', function(){
      isLike = !isLike
    if(isLike){
          $(this).toggleClass('is_animating');
          $(this).addClass('like')
      }
      else{
          $(this).removeClass('like')
      }
    });
  }

  $(".heart").on('animationend', function(){
    $(this).toggleClass('is_animating');
  });
}
















// //AUTO SCROLL 
// var page = 1;   //불러올 페이지
// var isLoading = false;    //중복실행여부 확인 변수

// /*nextpageload function*/
// function loadCards() {
//   if (isLoading) {
//       return;
//   }
//   isLoading = true;

//     $.ajax({
//         type:"GET",
//         url:"/test.php",
//         data : {'page':page},
//         dataType : "json",
//         success: function (data) {
//           if (data.length > 0) {
//               // 데이터를 받아와서 카드를 생성하는 로직
//               var cardContainer = $('#card-container');
//               data.forEach(function (item) {
//                   var cardHTML = '<div class="card">Card Content</div>';
//                   cardContainer.append(cardHTML);
//               });

//               // 페이지 증가
//               page++;
//               isLoading = false;
//           }
//       },
//       error: function () {
//           isLoading = false;
//       }
//   });
// }

// // 초기 페이지 로드
// loadCards();

// // 스크롤 이벤트 처리
// $(window).scroll(function () {
//   if ($(window).scrollTop() + $(window).height() >= $(document).height() - 200) {
//       loadCards();
//   }
// });



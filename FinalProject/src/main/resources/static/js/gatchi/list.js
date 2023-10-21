


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
    }, 20000000);
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
      }, 20000000);
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





let endList = 8;
let scrollY = 1300;
//1850;
// 1200
function handleScroll() {

  if(window.scrollY > scrollY){
    scrollY += scrollY;
    endList += endList;

    let cards = document.getElementsByName('meetLists');
  
    for(let i=0; i<endList; i++){
      cards[i].classList.remove('meetList_hidden');
    }

  }

}



//좋아요버튼 애니메이션
var isLike = false
function heartClickBtn(){
  let h = document.getElementsByName('likeBtn');

  for(let i=0; i<h.length; i++){
      h[i].addEventListener('change', function(){
        let num = h[i].value;
        console.log("change" + num);
        let heartLabel = document.getElementById('likeBtnLabel' + num);
        if(h[i].checked){
          heartLabel.classList.toggle('is_animating');
          heartLabel.classList.add('like');
          plusLike(num);
        }else{
          heartLabel.classList.remove('like')
          minusLike(num);
        }
    });
  }

  $(".heart").on('animationend', function(){
    $(this).toggleClass('is_animating');
  });
}



//좋아요
function plusLike(num){

  // let heartElements = document.getElementsByName('likeBtn')
  console.log(num)
  
      // 누르면 서버로 보내서 카운트 ++
      fetch('/meet/plusLike', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({ 'meetListNum': num}),
      })
      .then(response => {
        if (response.status === 200) {
          console.log('요청이 성공했습니다.');
        } else {
          console.error('요청이 실패했습니다.');
        }
      });
  }


//안좋아요
function minusLike(num){

  // let heartElements = document.getElementsByName('likeBtn')
  console.log(num)
  
      // 누르면 서버로 보내서 카운트 ++
      fetch('/meet/minusLike', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({ 'meetListNum': num}),
      })
      .then(response => {
        if (response.status === 200) {
          console.log('요청이 성공했습니다.');
        } else {
          console.error('요청이 실패했습니다.');
        }
      });
  }

  function loadLike(num){

    // let heartElements = document.getElementsByName('likeBtn')
    console.log(num)
    
        // 누르면 서버로 보내서 카운트 ++
        fetch('/meet/loadLikeData', {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json',
          },
        })
        .then(response => {
          if (response.status === 200) {
            console.log('요청이 성공했습니다.');
            return response.json();
          } else {
            console.error('요청이 실패했습니다.');
          }
        })
        .then(data => {
          console.log(data);
          checkLike(data);
        });
    }

function checkLike(data){

  for(let i=0; i<data.length; i++){
    console.log(data[i]);
    let likeBtn = document.getElementById("likeBtnLabel" + data[i]);
    let likecheck = document.getElementById("heartBtn" + data[i]);

    likeBtn.classList.add('like');
    likecheck.checked = true;
  }
}


function meetList(){
  let cards = document.getElementsByName('meetLists');
  let cnt = cards.length > endList ? endList : cards.length;
  for(let i=0; i<cnt; i++){
    cards[i].classList.remove('meetList_hidden');
  }
}

window.addEventListener('scroll', handleScroll);

window.onload = function() {
  
  slide();
  heartClickBtn();
  loadLike();
  meetList();
}



 //검색
 function sendIt(){

  var f = document.searchForm;
  
  f.action = "/meetMateList.action";
  f.submit();
  
}
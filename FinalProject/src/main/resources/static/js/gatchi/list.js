

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
      }, 3000);
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
        }, 3000);
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
    let h = document.getElementsByName('likeBtn');
  
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
  
  
  let endList = 8;
  let scrollY = 1200;
  //1850;
  // 1200
  function handleScroll() {
  
    if(window.scrollY >= scrollY){
      scrollY += scrollY;
      endList += endList;
  
      let cards = document.getElementsByName('meetLists');
    
      for(let i=0; i<endList; i++){
        cards[i].classList.remove('meetList_hidden');
      }
  
    }
  
  }
  
  //좋아요
  function heartClick(){
  
    let heartElements = document.getElementsByName('likeBtn')
    
    for(let i=0; i<heartElements.length; i++){
      console.log(heartElements[i].firstElementChild.value);
  
      heartElements[i].addEventListener('click', function(){
        console.log(heartElements[i].firstElementChild.value)
        // 누르면 서버로 보내서 카운트 ++
        const xhr = new XMLHttpRequest();
        xhr.open('GET', '/meet/likeBtn', true);
        
        xhr.onload = function(){
          if(xhr.status === 200){
            console.log('좋아요 성공');
          }
          else{
            console.log('좋아요 실패')
          }
        };
      });
    }    
  }
  
  function meetList(){
    let cards = document.getElementsByName('meetLists');
    
    for(let i=0; i<endList; i++){
      cards[i].classList.remove('meetList_hidden');
    }
  }
  
  window.addEventListener('scroll', handleScroll);
  
  window.onload = function() {
    heartClick();
    meetList();
  }
  
  
  
  
  
  
  
  
  
  //검색
  function sendIt(){
  
    var f = document.searchForm;
    
    f.action = "/meetMateList.action";
    f.submit();
    
  }
  
  
  
  
  
  
  
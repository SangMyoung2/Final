

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
      fetch('/challenge/plusLike', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({ 'challengeListNum': num}),
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
      fetch('/challenge/minusLike', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({ 'challengeListNum': num}),
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
        fetch('/challenge/loadLikeData', {
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
  heartClickBtn();
  loadLike();
  meetList();
}

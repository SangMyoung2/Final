function checkboxCheck() {
    var yaksok1 = document.getElementById('yaksok1').checked;
    var yaksok2 = document.getElementById('yaksok2').checked;
    var yaksok3 = document.getElementById('yaksok3').checked;

    if (yaksok1 && yaksok2 && yaksok3) {
        // 모든 체크박스가 체크되어 있을 때
        document.getElementById('yaksok').style.display = 'none';
        document.getElementById('title').style.display = 'block';
    } else {
        // 하나 이상의 체크박스가 체크되어 있지 않을 때
        alert('약속 사항에 모두 동의해주세요!');
    }
}

function titleCheck() {
    var title = document.getElementById('challengeTitle').value;
    var content = document.getElementById('challengeContent').value;
    var challengeImageMain = document.querySelector('#challengeImageMain').files[0];

    if(title.trim() === ''){
        alert('타이틀을 입력해주세요!');
        return
    }

    if(content.trim() === ''){
      alert('간단한 소개를 입력해주세요!');
      return
    }

    if(!challengeImageMain){
      alert('대표 사진을 업로드 해주세요.')
      return
    }



    document.getElementById('title').style.display = 'none';
    document.getElementById('createChallenge').style.display = 'block';
   


}

function calEndDate() {
    const challengeDateCheck = document.querySelector('input[name="challengeDateCheck"]:checked').value;
    const challengeStartDate = new Date(document.getElementById("challengeStartDate").value);
    const challengeEndDate = new Date(challengeStartDate);
  
    if (challengeDateCheck === "1week") {
      challengeEndDate.setDate(challengeStartDate.getDate() + 7);
    } else if (challengeDateCheck === "2week") {
      challengeEndDate.setDate(challengeStartDate.getDate() + 14);
    } else if (challengeDateCheck === "3week") {
      challengeEndDate.setDate(challengeStartDate.getDate() + 21);
    } else if (challengeDateCheck === "1month") {
      challengeEndDate.setMonth(challengeStartDate.getMonth() + 1);
    }
    
    document.getElementById("challengeEndDate").valueAsDate = challengeEndDate;
}



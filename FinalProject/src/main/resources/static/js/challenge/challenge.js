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

    if(title.trim() !== ''){
        document.getElementById('title').style.display = 'none';
        document.getElementById('createChallenge').style.display = 'block';
    }else{
        alert('타이틀을 입력해주세요!');
    }
}

function calEndDate() {
    const dateCheck = document.querySelector('input[name="dateCheck"]:checked').value;
    const startDate = new Date(document.getElementById("startDate").value);
    const endDate = new Date(startDate);
  
    if (dateCheck === "1week") {
      endDate.setDate(startDate.getDate() + 7);
    } else if (dateCheck === "2week") {
      endDate.setDate(startDate.getDate() + 14);
    } else if (dateCheck === "3week") {
      endDate.setDate(startDate.getDate() + 21);
    } else if (dateCheck === "1month") {
      endDate.setMonth(startDate.getMonth() + 1);
    }
    const endDateInput = document.getElementById("endDate");
    endDateInput.valueAsDate = endDate;
}



document.addEventListener("DOMContentLoaded", function() {

    
    
    // 버튼 클릭 시 해당하는 주제의 질문만 출력
    document.querySelectorAll('button[data-subject]').forEach(button => {
        button.addEventListener('click', function() {
            const selectedSubject = this.getAttribute('data-subject');
            
            document.querySelectorAll('div[data-subject]').forEach(div => {
                if (div.getAttribute('data-subject') === selectedSubject) {
                    div.style.display = 'block';  // 선택한 주제의 게시글만 표시
                } else {
                    div.style.display = 'none';   // 나머지는 숨김
                }
            });
        });
    });
    
    //엔터키 누르면 검색 되게끔 하는 코드
    document.getElementById('searchInput').addEventListener('keydown', function(event) {
        if (event.keyCode === 13) {  // 13은 엔터키의 keyCode 입니다.
            event.preventDefault();   // 기본 이벤트를 막습니다. (폼 제출 등의 기본 동작을 방지)
            document.getElementById('searchBtn').click();  // 검색 버튼의 클릭 이벤트를 실행합니다.
        }
    });

    // 검색 결과가 없을 때의 메세지 출력
    document.getElementById('searchBtn').addEventListener('click', function() {
        const searchQuery = document.getElementById('searchInput').value.toLowerCase();
        let hasResults = false;  // 검색 결과가 있는지 체크하는 플래그
    
        document.querySelectorAll('div[data-subject]').forEach(div => {
            const question = div.querySelector('span[data-question]').innerText.toLowerCase();
            const answer = div.querySelector('span[data-answer]').innerText.toLowerCase();
    
            if (question.includes(searchQuery) || answer.includes(searchQuery)) {
                div.style.display = 'block';
                hasResults = true;  // 결과가 있다면 플래그를 true로 설정
            } else {
                div.style.display = 'none';
            }
        });
    
        // 검색 결과가 없다면 메시지를 표시
        if (!hasResults) {
            document.getElementById('noResults').style.display = 'block';
        } else {
            document.getElementById('noResults').style.display = 'none';
        }
    });

    // 검색어를 입력해서 해당 검색어가 포함된 질문과 답변 모두 출력
    document.getElementById('searchBtn').addEventListener('click', function() {
        const searchQuery = document.getElementById('searchInput').value.toLowerCase();

        document.querySelectorAll('div[data-subject]').forEach(div => {
            const question = div.querySelector('span[data-question]').innerText.toLowerCase();
            const answer = div.querySelector('span[data-answer]').innerText.toLowerCase();

            if (question.includes(searchQuery) || answer.includes(searchQuery)) {
                div.style.display = 'block';
            } else {
                div.style.display = 'none';
            }
        });
    });
});
    


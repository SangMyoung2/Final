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
    

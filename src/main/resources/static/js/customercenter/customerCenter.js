document.addEventListener("DOMContentLoaded", function() {

    // 버튼 클릭 시 해당하는 주제의 질문만 출력
    document.querySelectorAll('button[data-subject]').forEach(button => {
        button.addEventListener('click', function() {
            const selectedSubject = this.getAttribute('data-subject');
            
            document.querySelectorAll('div.faq-container > button').forEach(button => {
                const div = button.nextElementSibling;
                if (button.innerText.includes(`[${selectedSubject}]`)) {
                    button.style.display = 'block';
                    div.style.display = 'none';  // 초기상태는 닫힌 상태로 설정
                } else {
                    button.style.display = 'none';
                    div.style.display = 'none';
                }
            });
        });
    });
    
    // 엔터키 누르면 검색 되게끔 하는 코드
    document.getElementById('searchInput').addEventListener('keydown', function(event) {
        if (event.keyCode === 13) {
            event.preventDefault();
            document.getElementById('searchBtn').click();
        }
    });

    // 검색 결과 출력
    document.getElementById('searchBtn').addEventListener('click', function() {
        const searchQuery = document.getElementById('searchInput').value.toLowerCase();
        let hasResults = false;
    
        document.querySelectorAll('div.faq-container > button').forEach(button => {
            const question = button.innerText.toLowerCase();
            const answer = button.nextElementSibling.innerText.toLowerCase();
    
            if (question.includes(searchQuery) || answer.includes(searchQuery)) {
                button.style.display = 'block';
                hasResults = true;
            } else {
                button.style.display = 'none';
                button.nextElementSibling.style.display = 'none';
            }
        });
    
        if (!hasResults) {
            document.getElementById('noResults').style.display = 'block';
        } else {
            document.getElementById('noResults').style.display = 'none';
        }
    });

    // jQuery를 사용한 아코디언 기능
    $(document).ready(function() {
        $(".faq-question").click(function() {
            $(this).next(".faq-answer").slideToggle("fast");
        });
    });
});

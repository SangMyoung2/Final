document.addEventListener("DOMContentLoaded", function() {
    let currentIndex = 0; 
    const itemsPerLoad = 5; 

    function loadMore() {
        const faqContainers = Array.from(document.querySelectorAll('.faq-container')); 
        const visibleFaqContainers = faqContainers.filter(container => container.querySelector('button').style.display !== 'none'); 
        let endIndex = currentIndex + itemsPerLoad;
    
        for (let i = currentIndex; i < endIndex && i < visibleFaqContainers.length; i++) {
            visibleFaqContainers[i].style.display = 'block';
            currentIndex++;
        }
    
        if (currentIndex >= visibleFaqContainers.length) {
            document.getElementById('loadMore').style.display = 'none';
        } else {
            document.getElementById('loadMore').style.display = 'block';
        }
    }

    function resetAndLoad() {
        const faqContainers = Array.from(document.querySelectorAll('.faq-container'));
        faqContainers.forEach(container => {
            container.style.display = 'none';
        });

        const visibleFaqContainers = faqContainers.filter(container => container.querySelector('button').style.display !== 'none');
        for (let i = 0; i < itemsPerLoad && i < visibleFaqContainers.length; i++) {
            visibleFaqContainers[i].style.display = 'block';
        }
    
        currentIndex = Math.min(itemsPerLoad, visibleFaqContainers.length);
        
        if (currentIndex >= visibleFaqContainers.length) {
            document.getElementById('loadMore').style.display = 'none';
        } else {
            document.getElementById('loadMore').style.display = 'block';
        }
    }

    document.querySelectorAll('button[data-subject]').forEach(button => {
        button.addEventListener('click', function() {
            const selectedSubject = this.getAttribute('data-subject');
            document.querySelectorAll('div.faq-container > button').forEach(button => {
                const div = button.nextElementSibling;
                if (button.innerText.includes(`[${selectedSubject}]`)) {
                    button.style.display = 'block';
                    div.style.display = 'none';
                } else {
                    button.style.display = 'none';
                    div.style.display = 'none';
                }
            });

            // 검색 결과가 없다는 문장을 숨김
            document.getElementById('noResults').style.display = 'none';

            resetAndLoad();
        });
    });

    document.getElementById('searchInput').addEventListener('keydown', function(event) {
        if (event.keyCode === 13) {
            event.preventDefault();
            document.getElementById('searchBtn').click();
        }
    });

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

        resetAndLoad();
    });

    $(document).ready(function() {
        $(".faq-question").click(function() {
            $(this).next(".faq-answer").slideToggle("fast");
        });
    });

    resetAndLoad();

    document.getElementById('loadMore').addEventListener('click', function() {
        loadMore();
    });
});

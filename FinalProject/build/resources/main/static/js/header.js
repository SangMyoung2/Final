(function($) {
  $(function() { // DOM Ready

    // Toggle navigation
    $('#nav-toggle').click(function() {
      this.classList.toggle("active");
      // If sidebar is visible:
      if ($('body').hasClass('show-nav')) {
        // Hide sidebar
        $('body').removeClass('show-nav');
      } else { // If sidebar is hidden:
        $('body').addClass('show-nav');
        // Display sidebar
      }
    });
  });
});

function applyWindowSizeToBox() {
	const boxElement = document.getElementById('subbody');
  const subElement = document.getElementById('subbody1');

	const windowWidth = window.innerWidth;

	// 브라우저 창 크기를 #box 요소의 width와 height에 적용
  if(windowWidth > 730){
	  boxElement.style.maxWidth = (windowWidth - 300) + 'px';
    subElement.style.width = (windowWidth - 600) + 'px';
  }

}
window.addEventListener('load', applyWindowSizeToBox);
window.addEventListener('resize', applyWindowSizeToBox);
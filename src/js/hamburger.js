var header = document.getElementById('header');
var hamburger = header.querySelector('.hamburger');
var links = header.querySelector('.nav-links');
var close = header.querySelector('.close-btn');
hamburger.addEventListener('click', function () {
	links.classList.add('nav-open');
	close.classList.add('close-hamburger');
	document.body.style.overflow = "hidden";
});
close.addEventListener('click', function () {
	links.classList.remove('nav-open');
	close.classList.remove('close-hamburger');
	document.body.style.overflow = "auto";
});

// exam list on small screen
var list_heading = document.querySelectorAll('.list-heading');
var sibling1, sibling2;
list_heading.forEach(function(i){
	i.addEventListener('click', function(){
		sibling1 = this.nextElementSibling;
		sibling2 = sibling1.nextElementSibling;
		console.log(sibling1, sibling2);
		sibling1.classList.toggle('list-open');
		sibling2.classList.toggle('list-open');
	});
});

//scroll events
var scroll_flag = 0;
window.addEventListener("scroll", function (e) {
	e.preventDefault();
	var top = this.scrollY;
	var footer = document.getElementById("footer-contactus");
	var phone = document.getElementsByClassName("footer-phone");
	var whatsapp = document.getElementsByClassName("footer-whatsapp");
	var footer_height = footer.offsetTop;
	if (top+window.innerHeight-300 > footer_height && scroll_flag == 0) {
		scroll_flag = 1;
		phone[0].classList.add('phone-pop');
		whatsapp[0].classList.add('whatsapp-pop');
		setInterval(function(){
		phone[0].classList.remove('phone-pop');
		whatsapp[0].classList.remove('whatsapp-pop');
		},4000);
	}
}, false);

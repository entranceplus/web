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


//changing bg
window.addEventListener("scroll", function (e) {
	e.preventDefault();
	var top = this.scrollY;
	var header = document.getElementById('header');
	if(top > 25) {
		header.classList.add('fixed-bg');
	}
	else
		header.classList.remove('fixed-bg');
}, false);


//dropdown-menu
var drop_arrow = document.querySelector('.drop-arrow');
var dropdown = document.querySelector('.dropdown');
var dropdown_a = document.querySelector('.dropdown > a');
dropdown_a.addEventListener('click', function () {
	dropdown.classList.remove('remove-dropdown');
});
drop_arrow.addEventListener('click', function(){
	dropdown.classList.add('remove-dropdown');
});

var header = document.getElementById('header');
var hamburger = header.querySelector('.hamburger');
var links = header.querySelector('.nav-links');
var close = header.querySelector('.close-btn');
hamburger.addEventListener('click', function () {
	links.classList.add('nav-open');
	close.classList.add('close-hamburger');
});
close.addEventListener('click', function () {
	links.classList.remove('nav-open');
	close.classList.remove('close-hamburger');
});
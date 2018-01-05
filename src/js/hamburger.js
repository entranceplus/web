const header = document.getElementById('header');
const hamburger = header.querySelector('.hamburger');
const links = header.querySelector('.nav-links');
const close = header.querySelector('.close-btn');
hamburger.addEventListener('click', function () {
	links.classList.add('nav-open');
	close.classList.add('close-hamburger');
});
close.addEventListener('click', function () {
	links.classList.remove('nav-open');
	close.classList.remove('close-hamburger');
});
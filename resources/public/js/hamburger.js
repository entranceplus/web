var header = document.getElementById('header');
var hamburger = header.querySelector('.hamburger');
var links = header.querySelector('.nav-links');
var close = header.querySelector('.close-btn');
var overlay = document.querySelector('.overlay');
hamburger.addEventListener('click', function () {
	links.classList.add('nav-open');
	close.classList.add('close-hamburger');
	document.body.style.overflow = "hidden";
	overlay.classList.add('overlay-open');
});
close.addEventListener('click', function () {
	links.classList.remove('nav-open');
	close.classList.remove('close-hamburger');
	document.body.style.overflow = "auto";
	overlay.classList.remove('overlay-open');
});
overlay.addEventListener('click', function(){
	links.classList.remove('nav-open');
	close.classList.remove('close-hamburger');
	document.body.style.overflow = "auto";
	overlay.classList.remove('overlay-open');
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


//exam list showing all data about college on clicking
var list_heading = document.querySelectorAll('.list-heading');
var exam_list_container = document.querySelector('.exam-list-container');
var list_info_container = document.querySelector('.list-info-container');

exam_list_container.addEventListener('click', function(e){
  var target_el = e.target;
  var target_el_parent = e.target.parentNode;
  if(target_el.className.match(/list-heading/) || target_el_parent.className.match(/list-heading/)) {
  	if(target_el.className.match(/list-heading/)) {
      console.log(target_el);
      console.log(target_el.nextElementSibling);
      target_el.nextElementSibling.classList.toggle('collapse-ranklist');
      target_el.parentNode.classList.toggle('up-arrow');
    }
    if(target_el_parent.className.match(/list-heading/)) {
      console.log(target_el_parent);
      console.log(target_el_parent.nextElementSibling);
      target_el_parent.nextElementSibling.classList.toggle('collapse-ranklist');
      target_el_parent.parentNode.classList.toggle('up-arrow');
    }
  }
});

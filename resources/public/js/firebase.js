// Initialize Firebase
var config = {
	apiKey: "AIzaSyBAQ7akghM1bJ3Rnhp0NxSFh95j5AOotXY",
	authDomain: "tranquil-harbor-163720.firebaseapp.com",
	databaseURL: "https://tranquil-harbor-163720.firebaseio.com",
	projectId: "tranquil-harbor-163720",
	storageBucket: "tranquil-harbor-163720.appspot.com",
	messagingSenderId: "668417789037"
};
firebase.initializeApp(config);

var contactsRef = firebase.database().ref("contacts");

document.querySelector('#contact-form form').addEventListener('submit',function(e){
    e.preventDefault();

		//getting input
		var datesubmit = new Date().toString();
		console.log(datesubmit);
    var name = getInputVal('form-name');
    var email = getInputVal('form-email');
    var phone = getInputVal('form-tel');
    var msg = getInputVal('form-msg');

    //save form to database
    saveMsg(datesubmit, name, phone, email, msg);

    document.getElementById('form-submitMsg').classList.add('show');

    //displaying a modal for 3s
    setTimeout(function(){
        document.getElementById('form-submitMsg').classList.remove('show');
    },3000);

    //clear form
    document.querySelector('#contact-form form').reset();
});


function getInputVal(id){
    return document.getElementById(id).value;
}

function saveMsg(datesubmit, name, phone, email, msg){
    var newContactsRef = contactsRef.push();
    newContactsRef.set({
				SubmitTime: datesubmit,
				Name: name,
        Email: email,
        Phone: phone,
        Message: msg
    });
}

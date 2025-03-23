console.log("script loaded");


let currentTheme = getTheme();

document.addEventListener('DOMContentLoaded',()=>{
		changeTheme(currentTheme)
}
)

//initial theme
changeTheme(currentTheme);
console.log(currentTheme);



function changeTheme(currentTheme){
	//document.querySelector("html").classList.add(currentTheme);
	
	changePageTheme(currentTheme, currentTheme);
	
	// set listener to change theme button
	
	const changeThemeButton = document.querySelector('#theme_change_button');
	
	// change the text of button
	//	changeThemeButton.querySelector('span').textContent = currentTheme == 'light' ? 'Dark' : 'Light';
		
		const oldTheme = currentTheme;
	
	changeThemeButton.addEventListener('click',(e)=>{
		console.log("Button clicked");
		const oldTheme = currentTheme;
		
		if(currentTheme=="dark"){
			currentTheme='light';
		}else{
			currentTheme='dark';
		}
		
		changePageTheme(currentTheme,oldTheme);
		
	})
	
}

//set theme to local storage

function setTheme(theme){
	localStorage.setItem("theme",theme);
}

// get theme from local storage

function getTheme(){
		
	let theme = localStorage.getItem("theme");
	if(theme){
		return theme;
	} else{
		return "light";
	}
}

// change current page theme

function changePageTheme(theme,oldTheme){

	//local storage me change karengey
		setTheme(currentTheme);
		// remove the current Theme
		document.querySelector("html").classList.remove(oldTheme);
		// set the current theme
		document.querySelector('html').classList.add(theme);
		
		// change the text of button
		document.querySelector("#theme_change_button").querySelector('span').textContent = theme == 'light' ? 'Dark' : 'Light';

}








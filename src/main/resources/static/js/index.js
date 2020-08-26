/*
document.getElementById('curiousity').addEventListener('click',function(){
alert(this.id)
})
document.getElementById('opportunity').addEventListener('click',function(){
alert(this.id)
})
document.getElementById('spirit').addEventListener('click',function(){
alert(this.text)
})
//alert(roverId)
*/

// Množný způsob volání tlačítek místo individuálního způsobu výše
// Teoreticky by šlo používat jen id, kdyby se z toho jejich ID smazala marsApi
let marsApiButtons = document.querySelectorAll("button[id*='marsApi']")
marsApiButtons.forEach(button => button.addEventListener('click',function(){
const buttonId = this.id;
const roverId = buttonId.split('marsApi')[1]
let apiData = document.getElementById('marsApiRoverData')
apiData.value = roverId;
document.getElementById('frmRoverType').submit()
}))
